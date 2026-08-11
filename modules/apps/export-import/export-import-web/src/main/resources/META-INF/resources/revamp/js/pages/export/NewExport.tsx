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
import {PreviewParams, getPreview} from '../../services/getPreview';
import {postExportProcess} from '../../services/postExportProcess';
import {ExportPreview} from '../../types/exportImportPreview';
import {
	getSelectedDeletionCount,
	getSelectedItemsCount,
	toProcessRequestFlags,
	withSelectedLayoutSetCount,
} from '../../utils/contentSelection';
import {toRequestPortletDataHandlers} from '../../utils/toRequestPortletDataHandlers';

export function NewExport({
	backURL,
	commentsAndRatingsEnabled = false,
	exportPreview,
	exportPreviewAPIURL,
	exportProcessAPIURL,
	lookAndFeelEnabled = false,
	pageTreeModalConfiguration,
}: {
	backURL: string;
	commentsAndRatingsEnabled?: boolean;
	exportPreview?: ExportPreview;
	exportPreviewAPIURL: string;
	exportProcessAPIURL: string;
	lookAndFeelEnabled?: boolean;
	pageTreeModalConfiguration: PageTreeModalConfiguration;
}) {
	const [preview, setPreview] = useState<ExportPreview | undefined>(
		exportPreview
	);
	const [error, setError] = useState<string | null>(null);
	const [loading, setLoading] = useState(!exportPreview);
	const initialPreviewRef = useRef<ExportPreview | undefined>(exportPreview);
	const appliedDateFilterRef = useRef<NormalizedDateFilter>({});

	const loadPreview = useCallback((previewParams: PreviewParams) => {
		setLoading(true);
		setError(null);

		getPreview(previewParams).then((exportPreviewResponse) => {
			if (exportPreviewResponse.error !== null) {
				setError(exportPreviewResponse.error);
			}
			else {
				setPreview(exportPreviewResponse.data);

				if (!initialPreviewRef.current) {
					initialPreviewRef.current = exportPreviewResponse.data;
				}
			}

			setLoading(false);
		});
	}, []);

	useEffect(() => {
		if (exportPreview) {
			return;
		}

		loadPreview({url: exportPreviewAPIURL});
	}, [exportPreview, exportPreviewAPIURL, loadPreview]);

	if (error) {
		return <ClayAlert displayType="danger">{error}</ClayAlert>;
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
			url: exportPreviewAPIURL,
		});
	};

	return (
		<Formik
			initialValues={{
				contentSelection: undefined,
				dateFilter: {range: Range.All} as DateFilterValues,
				deletions: false,
				name: '',
				permissions: false,
			}}
			onSubmit={async (values) => {
				const contentSelection = values.contentSelection as
					| ContentSelection
					| undefined;

				const result = await postExportProcess({
					exportProcessRequest: {
						...appliedDateFilterRef.current,
						...toProcessRequestFlags(contentSelection),
						deletions: !!values.deletions,
						name: values.name,
						permissions: !!values.permissions,
						requestPortletDataHandlers:
							toRequestPortletDataHandlers(
								previewPortletDataHandlerSections,
								values.contentSelection
							),
					},
					url: exportProcessAPIURL,
				});

				if (result.error) {
					Liferay.Util.openToast({
						message: result.error,
						type: 'danger',
					});

					return;
				}

				Liferay.Util.navigate(backURL);
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

				return errors;
			}}
			validateOnMount
		>
			{(formik) => {
				const contentSelection = formik.values.contentSelection as
					| ContentSelection
					| undefined;

				return (
					<Form noValidate>
						<Setup
							placeholder={Liferay.Language.get(
								'add-an-export-name'
							)}
							subtitle={Liferay.Language.get(
								'provide-a-descriptive-name-for-your-file'
							)}
							title={sub(
								Liferay.Language.get('x-details'),
								Liferay.Language.get('export')
							)}
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
								'deletions-help-export'
							)}
							deletionsLabel={Liferay.Language.get(
								'export-individual-deletions'
							)}
							itemsCount={getSelectedItemsCount(
								preview?.additionCount,
								previewPortletDataHandlerSections,
								contentSelection
							)}
							loading={loading}
							lookAndFeelEnabled={lookAndFeelEnabled}
							onApplyFilter={handleApplyFilter}
							pageTreeModalConfiguration={
								pageTreeModalConfiguration
							}
							permissionsDescription={Liferay.Language.get(
								'export-import-permissions-help'
							)}
							permissionsLabel={Liferay.Language.get(
								'export-permissions'
							)}
							previewPortletDataHandlerSections={withSelectedLayoutSetCount(
								previewPortletDataHandlerSections,
								contentSelection
							)}
							subtitle={Liferay.Language.get(
								'select-and-filter-the-data-you-want-to-include-in-your-export'
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
											symbol="export"
										/>
									</span>

									{Liferay.Language.get('export')}
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
