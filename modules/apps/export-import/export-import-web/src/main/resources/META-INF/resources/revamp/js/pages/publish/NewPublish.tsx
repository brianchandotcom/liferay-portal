/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Form, Formik, FormikValues} from 'formik';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import DataSelection from '../../components/DataSelection';
import Footer from '../../components/Footer';
import {PageTreeModalConfiguration} from '../../components/PageTreeModal';
import Setup from '../../components/Setup';
import {
	DateFilterValues,
	NormalizedDateFilter,
	Range,
	normalizeDateFilter,
} from '../../components/date_filter';
import {ContentSelection} from '../../components/forms/content_selector/ContentSelector';
import {deleteScheduledPublishProcess} from '../../services/deleteScheduledPublishProcess';
import {PreviewParams, getPreview} from '../../services/getPreview';
import {getScheduledPublishProcess} from '../../services/getScheduledPublishProcess';
import {postPublishProcess} from '../../services/postPublishProcess';
import {ExportPreview} from '../../types/exportImportPreview';
import {
	getSelectedDeletionCount,
	getSelectedItemsCount,
	toProcessRequestFlags,
	withSelectedLayoutSetCount,
} from '../../utils/contentSelection';
import {toContentSelection} from '../../utils/toContentSelection';
import {toDateFilterValues} from '../../utils/toDateFilterValues';
import {toRequestPortletDataHandlers} from '../../utils/toRequestPortletDataHandlers';
import {FormikFieldPublishScheduler} from './components/scheduler/FormikFieldPublishScheduler';
import {
	fromCronExpression,
	toCronExpression,
	toWallClockDateTime,
	toZonedDate,
} from './components/scheduler/cron';
import {getDefaultTimeZoneId} from './components/scheduler/timeZones';
import {
	ScheduleValues,
	TimeZoneOption,
	getInitialScheduleValues,
} from './components/scheduler/types';

const MOVED_START_DATE_TIME_LEAD_MILLIS = 120000;

type PublishFormValues = {
	contentSelection: ContentSelection | undefined;
	dateFilter: DateFilterValues;
	deletions: boolean;
	name: string;
	permissions: boolean;
	scheduleValues: ScheduleValues;
};

export function NewPublish({
	backURL,
	commentsAndRatingsEnabled = false,
	defaultScheduled = false,
	lastPublishDate,
	lookAndFeelEnabled = false,
	pageTreeModalConfiguration,
	publishPreviewAPIURL,
	publishProcessAPIURL,
	scheduledBackURL,
	scheduledPublishProcessId,
	scheduledPublishProcessesAPIURL,
	timeZoneId,
	timeZones,
}: {
	backURL: string;
	commentsAndRatingsEnabled?: boolean;
	defaultScheduled?: boolean;
	lastPublishDate?: string;
	lookAndFeelEnabled?: boolean;
	pageTreeModalConfiguration: PageTreeModalConfiguration;
	publishPreviewAPIURL: string;
	publishProcessAPIURL: string;
	scheduledBackURL: string;
	scheduledPublishProcessId?: number;
	scheduledPublishProcessesAPIURL: string;
	timeZoneId: string;
	timeZones: TimeZoneOption[];
}) {
	const [preview, setPreview] = useState<ExportPreview | undefined>();
	const [error, setError] = useState<string | null>(null);
	const [loading, setLoading] = useState(true);
	const initialPreviewRef = useRef<ExportPreview | undefined>();
	const appliedDateFilterRef = useRef<NormalizedDateFilter>({});
	const [initialFormValues, setInitialFormValues] =
		useState<PublishFormValues>({
			contentSelection: undefined,
			dateFilter: {range: Range.All},
			deletions: false,
			name: '',
			permissions: false,
			scheduleValues: getInitialScheduleValues(
				getDefaultTimeZoneId(timeZones, timeZoneId),
				defaultScheduled
			),
		});
	const editedScheduledPublishProcessId =
		Number(scheduledPublishProcessId) || 0;

	const [seeding, setSeeding] = useState(!!editedScheduledPublishProcessId);
	const seededRef = useRef(false);
	const loadPreview = useCallback((previewParams: PreviewParams) => {
		setLoading(true);
		setError(null);

		getPreview(previewParams).then((publishPreviewResponse) => {
			if (publishPreviewResponse.error !== null) {
				setError(publishPreviewResponse.error);
			}
			else {
				setPreview(publishPreviewResponse.data);

				if (!initialPreviewRef.current) {
					initialPreviewRef.current = publishPreviewResponse.data;
				}
			}

			setLoading(false);
		});
	}, []);

	useEffect(() => {
		loadPreview({url: publishPreviewAPIURL});
	}, [publishPreviewAPIURL, loadPreview]);

	useEffect(() => {
		if (!editedScheduledPublishProcessId || !preview || seededRef.current) {
			return;
		}

		seededRef.current = true;

		getScheduledPublishProcess({
			url: `${scheduledPublishProcessesAPIURL}/${editedScheduledPublishProcessId}`,
		}).then((scheduledPublishProcessResponse) => {
			if (scheduledPublishProcessResponse.error !== null) {
				setError(scheduledPublishProcessResponse.error);

				return;
			}

			const scheduledPublishProcess =
				scheduledPublishProcessResponse.data;

			const publishParameters =
				scheduledPublishProcess.publishParameters ?? {};

			const dateFilter = toDateFilterValues(publishParameters);

			appliedDateFilterRef.current = normalizeDateFilter(dateFilter);

			if (dateFilter.range !== Range.All) {
				loadPreview({
					query: appliedDateFilterRef.current,
					url: publishPreviewAPIURL,
				});
			}

			const scheduledTimeZoneId =
				publishParameters.timeZoneId?.[0] ?? timeZoneId;

			let scheduleStartDate = scheduledPublishProcess.scheduleStartDate;

			if (
				scheduleStartDate &&
				scheduledPublishProcess.nextFireDate &&
				new Date(scheduleStartDate).getTime() < Date.now()
			) {
				scheduleStartDate = scheduledPublishProcess.nextFireDate;
			}

			const scheduleStartDateTime = scheduleStartDate
				? toWallClockDateTime(scheduleStartDate, scheduledTimeZoneId)
				: '';

			setInitialFormValues((currentInitialFormValues) => ({
				contentSelection: toContentSelection(
					preview.previewPortletDataHandlerSections ?? [],
					publishParameters,
					{commentsAndRatingsEnabled, lookAndFeelEnabled}
				),
				dateFilter,
				deletions: publishParameters.DELETIONS?.[0] === 'true',
				name: scheduledPublishProcess.name ?? '',
				permissions: publishParameters.PERMISSIONS?.[0] === 'true',
				scheduleValues: {
					...currentInitialFormValues.scheduleValues,
					enabled: true,
					endDateTime: scheduledPublishProcess.scheduleEndDate
						? toWallClockDateTime(
								scheduledPublishProcess.scheduleEndDate,
								scheduledTimeZoneId
							)
						: '',
					neverEnd: !scheduledPublishProcess.scheduleEndDate,
					startDateTime: scheduleStartDateTime,
					timeZoneId: scheduledTimeZoneId,
					...(scheduledPublishProcess.cronExpression
						? fromCronExpression(
								scheduledPublishProcess.cronExpression,
								scheduleStartDateTime
							)
						: {}),
				},
			}));

			setSeeding(false);
		});
	}, [
		commentsAndRatingsEnabled,
		editedScheduledPublishProcessId,
		loadPreview,
		lookAndFeelEnabled,
		preview,
		publishPreviewAPIURL,
		scheduledPublishProcessesAPIURL,
		timeZoneId,
	]);

	if (error) {
		return <ClayAlert displayType="danger">{error}</ClayAlert>;
	}

	if (seeding) {
		return (
			<div className="sheet">
				<span
					aria-hidden="true"
					className="loading-animation mb-9 mt-8"
				></span>
			</div>
		);
	}

	const previewPortletDataHandlerSections =
		preview?.previewPortletDataHandlerSections ?? [];

	const handleApplyFilter = (filterValues: DateFilterValues) => {
		appliedDateFilterRef.current = normalizeDateFilter(filterValues);

		if (filterValues.range === Range.All && initialPreviewRef.current) {
			setPreview(initialPreviewRef.current);

			return;
		}

		loadPreview({
			query: appliedDateFilterRef.current,
			url: publishPreviewAPIURL,
		});
	};

	return (
		<Formik
			initialValues={initialFormValues}
			onSubmit={async (values) => {
				const contentSelection = values.contentSelection;

				let scheduleValues = values.scheduleValues;

				const scheduled = scheduleValues.enabled;

				if (
					scheduled &&
					editedScheduledPublishProcessId &&
					toZonedDate(
						scheduleValues.startDateTime,
						scheduleValues.timeZoneId
					).getTime() < Date.now()
				) {
					scheduleValues = {
						...scheduleValues,
						startDateTime: toWallClockDateTime(
							new Date(
								Date.now() + MOVED_START_DATE_TIME_LEAD_MILLIS
							).toISOString(),
							scheduleValues.timeZoneId
						),
					};
				}

				const scheduleFields = scheduled
					? {
							cronExpression: toCronExpression(scheduleValues),
							scheduleEndDate:
								!scheduleValues.neverEnd &&
								scheduleValues.endDateTime
									? toZonedDate(
											scheduleValues.endDateTime,
											scheduleValues.timeZoneId
										).toISOString()
									: undefined,
							scheduleStartDate: toZonedDate(
								scheduleValues.startDateTime,
								scheduleValues.timeZoneId
							).toISOString(),
							timeZoneId: scheduleValues.timeZoneId,
						}
					: {};

				if (editedScheduledPublishProcessId) {
					const deleteResult = await deleteScheduledPublishProcess({
						url: `${scheduledPublishProcessesAPIURL}/${editedScheduledPublishProcessId}`,
					});

					if (deleteResult.error) {
						Liferay.Util.openToast({
							message: deleteResult.error,
							type: 'danger',
						});

						return;
					}
				}

				const result = await postPublishProcess({
					publishProcessRequest: {
						...appliedDateFilterRef.current,
						...toProcessRequestFlags(contentSelection),
						...scheduleFields,
						deletions: !!values.deletions,
						name: values.name,
						permissions: !!values.permissions,
						requestPortletDataHandlers:
							toRequestPortletDataHandlers(
								previewPortletDataHandlerSections,
								values.contentSelection
							),
					},
					url: publishProcessAPIURL,
				});

				if (result.error) {
					Liferay.Util.openToast({
						message: result.error,
						type: 'danger',
					});

					return;
				}

				Liferay.Util.navigate(scheduled ? scheduledBackURL : backURL);
			}}
			validate={(values: FormikValues) => {
				const errors: {[key: string]: string} = {};

				if (!values?.name) {
					errors.name = Liferay.Language.get(
						'this-field-is-required'
					);
				}

				if (!values?.contentSelection) {
					errors.contentSelection = Liferay.Language.get(
						'please-select-at-least-one-entity-type-to-continue'
					);
				}

				if (
					values?.scheduleValues.enabled &&
					!values.scheduleValues.startDateTime
				) {
					errors.scheduleValues = Liferay.Language.get(
						'please-set-a-start-date-and-time-to-schedule-the-publication'
					);
				}

				return errors;
			}}
			validateOnMount
		>
			{(formik) => {
				const contentSelection = formik.values.contentSelection;

				return (
					<Form noValidate>
						<Setup
							placeholder={Liferay.Language.get(
								'process-name-placeholder'
							)}
							subtitle={Liferay.Language.get(
								'name-your-process-and-choose-when-to-publish'
							)}
							title={sub(
								Liferay.Language.get('x-details'),
								Liferay.Language.get('publish')
							)}
						/>

						<FormikFieldPublishScheduler
							name="scheduleValues"
							timeZones={timeZones}
						/>

						<DataSelection
							commentsAndRatingsEnabled={
								commentsAndRatingsEnabled
							}
							deletionCount={getSelectedDeletionCount(
								preview?.deletionCount,
								previewPortletDataHandlerSections,
								contentSelection
							)}
							deletionsDescription={Liferay.Language.get(
								'deletions-help'
							)}
							deletionsLabel={Liferay.Language.get(
								'replicate-individual-deletions'
							)}
							itemsCount={getSelectedItemsCount(
								preview?.additionCount,
								previewPortletDataHandlerSections,
								contentSelection
							)}
							lastPublishDate={lastPublishDate}
							loading={loading}
							lookAndFeelEnabled={lookAndFeelEnabled}
							onApplyFilter={handleApplyFilter}
							pageTreeModalConfiguration={
								pageTreeModalConfiguration
							}
							permissionsDescription={Liferay.Language.get(
								'publish-permissions-help'
							)}
							permissionsLabel={Liferay.Language.get(
								'publish-permissions'
							)}
							previewPortletDataHandlerSections={withSelectedLayoutSetCount(
								previewPortletDataHandlerSections,
								contentSelection
							)}
							process="publish"
							subtitle={Liferay.Language.get(
								'select-and-filter-the-data-you-want-to-publish'
							)}
						/>

						<Footer
							actionButton={
								<ClayButton
									disabled={
										formik.isSubmitting || !formik.isValid
									}
									type="submit"
								>
									<span className="inline-item inline-item-before">
										<ClayIcon
											className="mr-1"
											symbol="change"
										/>
									</span>

									{formik.values.scheduleValues.enabled
										? Liferay.Language.get(
												'schedule-publish-to-live'
											)
										: Liferay.Language.get(
												'publish-to-live'
											)}
								</ClayButton>
							}
							backURL={backURL}
						/>
					</Form>
				);
			}}
		</Formik>
	);
}
