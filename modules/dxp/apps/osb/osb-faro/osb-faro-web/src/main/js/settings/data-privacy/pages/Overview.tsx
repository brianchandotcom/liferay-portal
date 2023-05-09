import BasePage from 'settings/components/BasePage';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import fetch from 'shared/util/fetch';
import PreferenceMutation from '../queries/PreferenceMutation';
import PreferenceQuery from '../queries/PreferenceQuery';
import React from 'react';
import {close, modalTypes, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect} from 'react-redux';
import {get} from 'lodash';
import {Option, Picker} from '@clayui/core';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {useMutation, useQuery} from '@apollo/react-hooks';
import {User} from 'shared/util/records';
import {withCurrentUser} from 'shared/hoc';

const DATA_RETENTION_PERIOD_KEY = 'data-retention-period';

const SEVEN_MONTHS = '18144000000';
const THIRTEEN_MONTHS = '33696000000';

const RETENTION_OPTIONS = [SEVEN_MONTHS, THIRTEEN_MONTHS];

const convertMillisecondsToMonths = (milliseconds: number): number =>
	Math.round(milliseconds / 1000 / 60 / 60 / 24 / 30);

const getRetentionLabel = (milliseconds: number): string =>
	sub(Liferay.Language.get('x-months'), [
		convertMillisecondsToMonths(milliseconds)
	]) as string;

const fetchDownload = ({fromDate, groupId, toDate, type}) =>
	fetch(
		`/o/proxy/download/${type}/logs?projectGroupId=${groupId}&filter=(createDate ge '${fromDate}' and createDate le '${toDate}')`,
		{method: 'GET'}
	).then(({response, status}) => {
		if (status === 200) {
			return response;
		}

		throw new Error('Request Error');
	});

interface IOverviewProps {
	close: () => void;
	currentUser: User;
	groupId: string;
	open: (modalType: string, options: object) => void;
}

export const Overview: React.FC<IOverviewProps> = ({
	close,
	currentUser,
	groupId,
	open
}) => {
	const [updatePreference] = useMutation(PreferenceMutation);

	const {data} = useQuery(PreferenceQuery, {
		variables: {key: DATA_RETENTION_PERIOD_KEY}
	});

	const handleDateRetentionPeriodChange = value => {
		const curVal = parseInt(data.preference.value);
		const newVal = parseInt(value);

		const updateDateRetentionPeriod = () =>
			updatePreference({
				update: (cache, {data}) => {
					cache.writeQuery({
						data,
						query: PreferenceQuery,
						variables: {key: DATA_RETENTION_PERIOD_KEY}
					});
				},
				variables: {
					key: DATA_RETENTION_PERIOD_KEY,
					value
				}
			}).then(response => {
				analytics.track('Updated Retention Period', {
					retentionPeriod: Number(value)
				});

				return response;
			});

		if (curVal > newVal) {
			open(modalTypes.CONFIRMATION_MODAL, {
				message: (
					<div>
						<p className='text-secondary'>
							{sub(
								Liferay.Language.get(
									'are-you-sure-you-want-to-change-the-retention-period-to-x'
								),
								[getRetentionLabel(newVal).toLowerCase()]
							)}
						</p>

						<h5>
							{sub(
								Liferay.Language.get(
									'you-will-permanently-lose-analytics-data-that-has-been-recorded-over-x-ago.-you-will-not-be-able-to-undo-this-operation'
								),
								[getRetentionLabel(newVal).toLowerCase()]
							)}
						</h5>
					</div>
				),
				modalVariant: 'modal-warning',
				onClose: close,
				onSubmit: updateDateRetentionPeriod,
				submitButtonDisplay: 'warning',
				submitMessage: Liferay.Language.get('change-period'),
				title: Liferay.Language.get('changing-retention-period'),
				titleIcon: 'warning-full'
			});
		} else {
			updateDateRetentionPeriod();
		}
	};

	const handleOpenRequestModal = () =>
		open(modalTypes.EXPORT_LOG_MODAL, {
			description: Liferay.Language.get(
				'select-a-date-range-to-export-your-request-log.-your-download-may-take-a-couple-minutes-to-process'
			),
			fileName: 'request-log.csv',
			groupId,
			onClose: close,
			onSubmit: ({fromDate, toDate}) =>
				fetchDownload({
					fromDate,
					groupId,
					toDate,
					type: 'data-control-tasks'
				}),
			title: Liferay.Language.get('export-request-log')
		});

	const handleOpenSuppressionModal = () =>
		open(modalTypes.EXPORT_LOG_MODAL, {
			description: Liferay.Language.get(
				'select-a-date-range-to-export-your-suppression-list.-your-download-may-take-a-couple-minutes-to-process'
			),
			fileName: 'suppression-list.csv',
			groupId,
			onClose: close,
			onSubmit: ({fromDate, toDate}) =>
				fetchDownload({
					fromDate,
					groupId,
					toDate,
					type: 'suppressions'
				}),
			title: Liferay.Language.get('export-suppression-list')
		});

	const authorized = currentUser.isAdmin();

	return (
		<BasePage
			className='data-privacy-overview-root'
			groupId={groupId}
			pageTitle={Liferay.Language.get('data-control-&-privacy')}
		>
			<div className='row'>
				<div className='col-xl-8'>
					<Card>
						<Card.Body>
							<div className='container'>
								<div className='row justify-content-between'>
									<div className='col-lg-8'>
										<h4>
											{Liferay.Language.get(
												'retention-period'
											)}
										</h4>

										<p className='text-secondary'>
											{Liferay.Language.get(
												'analytics-cloud-stores-event-data-and-inactive-anonymous-individuals-for-the-period-specified.-known-profile-data-will-be-stored-indefinitely-unless-it-is-removed-from-the-source-or-requested-to-be-deleted.-contact-sales-to-customize-retention-period'
											)}
										</p>
									</div>

									<div className='col-lg-auto align-self-center'>
										<Picker
											data-testid='data-retention-period-select-input'
											disabled={!currentUser.isAdmin()}
											items={RETENTION_OPTIONS}
											onSelectionChange={
												handleDateRetentionPeriodChange
											}
											selectedKey={get(data, [
												'preference',
												'value'
											])}
										>
											{item => (
												<Option key={item}>
													{getRetentionLabel(
														parseInt(item)
													)}
												</Option>
											)}
										</Picker>
									</div>
								</div>

								<hr />

								<div className='row mt-3 justify-content-between'>
									<div className='col-lg-8'>
										<h4>
											{Liferay.Language.get(
												'request-log'
											)}
										</h4>

										<p className='text-secondary'>
											{Liferay.Language.get(
												'data-subjects-and-your-organization-can-request-access,-deletion-and-suppression-of-their-data-in-analytics-cloud.-some-requests-may-take-up-to-7-days-to-complete.-we-will-notify-the-requestor-by-email-once-the-download-is-ready'
											)}
										</p>
									</div>

									<div className='col-lg-auto'>
										<ClayLink
											block
											button
											className='button-root mb-2'
											displayType='secondary'
											href={toRoute(
												Routes.SETTINGS_DATA_PRIVACY_REQUEST_LOG,
												{groupId}
											)}
										>
											{Liferay.Language.get('manage')}
										</ClayLink>

										<ClayButton
											block
											className='button-root'
											displayType='secondary'
											onClick={handleOpenRequestModal}
										>
											{Liferay.Language.get('export-log')}
										</ClayButton>
									</div>
								</div>

								<hr />

								<div className='row mt-3 justify-content-between'>
									<div className='col-lg-8'>
										<h4>
											{Liferay.Language.get(
												'suppressed-users'
											)}
										</h4>

										<p className='text-secondary'>
											{Liferay.Language.get(
												'suppressed-data-subjects-will-be-excluded-in-further-identity-resolution-activity.-deleted-data-subjects-will-automatically-be-suppressed-by-their-user-id-and-their-identity-will-not-be-resolveable'
											)}
										</p>
									</div>

									<div className='col-lg-auto'>
										<ClayLink
											block
											button
											className='button-root mb-2'
											displayType='secondary'
											href={
												authorized
													? toRoute(
															Routes.SETTINGS_DATA_PRIVACY_SUPPRESSED_USERS,
															{groupId}
													  )
													: undefined
											}
										>
											{Liferay.Language.get('manage')}
										</ClayLink>

										<ClayButton
											block
											className='button-root'
											data-testid='export-suppressed-user-button'
											disabled={!currentUser.isAdmin()}
											displayType='secondary'
											onClick={handleOpenSuppressionModal}
										>
											{Liferay.Language.get(
												'export-list'
											)}
										</ClayButton>
									</div>
								</div>
							</div>
						</Card.Body>
					</Card>
				</div>
			</div>
		</BasePage>
	);
};

export default compose<any>(
	withCurrentUser,
	connect(null, {close, open})
)(Overview);
