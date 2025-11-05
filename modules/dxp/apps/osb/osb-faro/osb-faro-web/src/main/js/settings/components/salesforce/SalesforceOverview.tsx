import * as API from 'shared/api';
import BasePage from 'settings/components/base-page/BasePage';
import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import InputWithEditToggle, {
	FontSize
} from 'shared/components/InputWithEditToggle';
import List from '@clayui/list';
import React, {useCallback, useEffect, useRef, useState} from 'react';
import {addAlert} from '../../../shared/actions/alerts';
import {Alert} from 'shared/types';
import {ClayInput, ClayToggle} from '@clayui/form';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {ConnectSalesforceAuth} from './ConnectSalesforceAuth';
import {DataSource} from 'shared/util/records';
import {DataSourceStates} from 'shared/util/constants';
import {
	fetchDataSource,
	updateSalesforceDataSource
} from 'shared/actions/data-sources';
import {sequence} from 'shared/util/promise';
import {sub} from 'shared/util/lang';
import {SubHeader} from 'shared/components/revamping/SubHeader';
import {Text} from '@clayui/core';
import {
	toPromise,
	validateMaxLength,
	validateRequired
} from 'shared/components/form';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useParams} from 'react-router-dom';
import {validateUniqueName} from 'shared/util/data-sources';

const connector = connect(null, {
	addAlert,
	close,
	fetchDataSource,
	open,
	updateSalesforceDataSource
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ISalesforceOverviewProps extends PropsFromRedux {
	dataSource: DataSource;
}

const SalesforceOverview: React.FC<ISalesforceOverviewProps> = ({
	addAlert,
	close,
	dataSource,
	fetchDataSource,
	open,
	updateSalesforceDataSource
}) => {
	const {groupId, id} = useParams();
	const currentUser = useCurrentUser();

	type Alert = {
		displayType: DisplayType;
		message: string;
	};

	const initialAlert: Alert = {
		displayType: 'success',
		message: ''
	};

	const [accounts, setAccounts] = useState<boolean>(false);
	const [alert, setAlert] = useState(initialAlert);
	const [individuals, setIndividuals] = useState<boolean>(false);

	const [isDataSourceConnected, setIsDataSourceConnected] = useState<boolean>(
		dataSource.state === DataSourceStates.CredentialsValid
	);

	useEffect(() => {
		let displayType = 'success' as DisplayType;

		let messageKey = Liferay.Language.get(
			'you-have-successfully-authenticated-your-token-with-liferay-analytics-cloud.-you-can-now-select-the-data-to-sync'
		);

		if (!isDataSourceConnected) {
			displayType = 'warning';

			messageKey = Liferay.Language.get(
				'the-data-source-is-disconnected.-data-is-no-longer-being-synced-from-dxp,-but-you-can-reconnect-to-resume-syncing'
			);
		} else if (accounts || individuals) {
			messageKey = Liferay.Language.get(
				'all-data-coming-from-this-data-source-is-up-to-date.-there-are-no-errors-to-report'
			);
		}

		setAlert({
			displayType,
			message: messageKey
		});
	}, [isDataSourceConnected, accounts, individuals]);

	const cachedNameValues = useRef(new Map());

	const handleDisconnectClick = useCallback(() => {
		open(modalTypes.CONFIRMATION_MODAL, {
			message: (
				<Text as='p' size={4}>
					{Liferay.Language.get(
						'this-action-will-stop-syncing-data-from-your-dxp-instance-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
					)}
				</Text>
			),
			modalVariant: 'modal-warning',
			onClose: close,
			onSubmit: () =>
				API.dataSource
					.disconnect({
						groupId,
						id
					})
					.then(() => {
						fetchDataSource({
							groupId,
							id
						});

						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'data-source-disconnected'
							)
						});

						setIsDataSourceConnected(false);

						close();
					})
					.catch(() => {
						addAlert({
							alertType: Alert.Types.Error,
							message: Liferay.Language.get(
								'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
							),
							timeout: false
						});

						fetchDataSource({
							groupId,
							id
						});
					}),
			submitButtonDisplay: 'warning',
			submitMessage: Liferay.Language.get('disconnect'),
			title: Liferay.Language.get('disconnect-data-source'),
			titleIcon: 'warning-full'
		});
	}, [addAlert, close, fetchDataSource, groupId, id, open]);

	const handleUpdateName = useCallback(
		name => updateSalesforceDataSource({groupId, id, name}),
		[groupId, id, updateSalesforceDataSource]
	);

	const handleValidate = useCallback(
		value => {
			let error = null;

			if (value !== dataSource.name) {
				if (cachedNameValues.current.has(value)) {
					error = cachedNameValues.current.get(value);
				} else {
					error = validateUniqueName({groupId, value});

					cachedNameValues.current.set(value, error);
				}
			}

			return toPromise(error);
		},
		[dataSource.name, groupId]
	);

	return (
		<BasePage>
			<ClayLayout.ContainerFluid view>
				<ClayLayout.SheetHeader>
					<InputWithEditToggle
						editable={currentUser?.isAdmin()}
						hasBoldTitle
						hasDataSourceLabel
						inputWidth={30}
						isDataSourceConnected={isDataSourceConnected}
						name='dataSourceName'
						onSubmit={name => toPromise(handleUpdateName(name))}
						required
						validate={sequence([
							validateRequired,
							validateMaxLength(75),
							handleValidate
						])}
						value={dataSource.name || ''}
						valueFontSize={FontSize.Size6}
					/>
				</ClayLayout.SheetHeader>

				<ClayLayout.Sheet className='p-4'>
					<ClayLayout.Row justify='center'>
						<ClayLayout.Col size={12}>
							<div className='mb-5'>
								<Text size={6} weight='bold'>
									{Liferay.Language.get('authentication')}
								</Text>
							</div>

							<ClayLayout.SheetSection>
								<Text
									color='secondary'
									size={3}
									weight='semi-bold'
								>
									{Liferay.Language.get(
										'connection-status'
									).toUpperCase()}
								</Text>

								<SubHeader
									title={Liferay.Language.get(
										'data-source-details'
									)}
								/>

								{alert && (
									<ClayAlert
										className='font-weight-semi-bold mb-4 mt-3'
										displayType={alert.displayType}
									>
										{alert.message}
									</ClayAlert>
								)}

								<ClayLayout.SheetSection>
									<SubHeader
										title={Liferay.Language.get(
											'data-source-details'
										)}
									/>

									<ClayInput.Group className='d-flex mt-3'>
										<ClayInput.GroupItem
											className='mr-3'
											shrink
										>
											<label htmlFor='dataSourceType'>
												{Liferay.Language.get(
													'data-source-type'
												)}
											</label>

											<ClayInput
												readOnly
												type='text'
												value={Liferay.Language.get(
													'salesforce'
												)}
											/>
										</ClayInput.GroupItem>

										<ClayInput.GroupItem
											className='ml-0'
											shrink
										>
											<label htmlFor='dataSourceId'>
												{Liferay.Language.get(
													'data-source-id'
												)}
											</label>

											<ClayInput
												readOnly
												type='text'
												value={dataSource.id}
											/>
										</ClayInput.GroupItem>
									</ClayInput.Group>
								</ClayLayout.SheetSection>

								{!isDataSourceConnected ? (
									<>
										<div className='mb-3'>
											<Text color='secondary' size={4}>
												{Liferay.Language.get(
													'to-reestablish-the-connection-between-salesforce-and-liferay-analytics-cloud,-generate-a-token-and-paste-the-code-on-the-input-below'
												)}
											</Text>

											<ClayLink
												className='ml-1'
												href=''
												key='DOCUMENTATION'
												target='_blank'
											>
												{Liferay.Language.get(
													'learn-more-about-data-sources'
												)}
											</ClayLink>
										</div>

										<ConnectSalesforceAuth
											addAlert={
												(addAlert as unknown) as Alert.AddAlert
											}
											onSubmit={() => {
												// TODO: Verify data source status after submitting.

												setIsDataSourceConnected(true);
											}}
										/>
									</>
								) : (
									<ClayButton
										aria-label={Liferay.Language.get(
											'disconnect-data-source'
										)}
										displayType='danger'
										onClick={handleDisconnectClick}
										outline
										size='sm'
									>
										<ClayIcon
											className='mr-2'
											symbol='logout'
										/>

										{Liferay.Language.get(
											'disconnect-data-source'
										)}
									</ClayButton>
								)}
							</ClayLayout.SheetSection>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.Sheet>

				<ClayLayout.Sheet className='mt-4 p-4'>
					<ClayLayout.Row justify='center'>
						<ClayLayout.Col size={12}>
							<div className='mb-5'>
								<Text size={6} weight='bold'>
									{Liferay.Language.get('synced-data')}
								</Text>
							</div>

							<ClayLayout.SheetSection>
								{isDataSourceConnected &&
									!accounts &&
									!individuals && (
										<ClayAlert
											className='mt-3'
											displayType='warning'
											title='Warning'
										>
											{Liferay.Language.get(
												'the-data-source-setup-is-almost-complete.-sync-data-to-start-seeing-results-as-activities-occur-on-your-sites'
											)}
										</ClayAlert>
									)}

								<Text color='secondary' size={4}>
									{Liferay.Language.get(
										'to-configure-your-salesforce-data-source,-go-to-your-salesforce-environment-to-update-this-app-connection'
									)}
								</Text>

								<div className='pt-1'>
									<ClayList className='mb-0'>
										<ClayList.Item flex>
											<ClayList.ItemField>
												<ClayIcon
													aria-label={Liferay.Language.get(
														'accounts'
													)}
													className='mr-2 mt-1 text-secondary'
													symbol='briefcase'
												/>
											</ClayList.ItemField>

											<ClayList.ItemField expand>
												<ClayList.ItemTitle>
													{Liferay.Language.get(
														'accounts'
													)}
												</ClayList.ItemTitle>

												<List.ItemText>
													{Liferay.Language.get(
														'represents-fields-from-the-account-object-within-salesforce'
													)}
												</List.ItemText>

												<List.ItemText>
													{/* TODO: replace [0] to a dynamic variable when endpoint is ready */}

													{sub(
														Liferay.Language.get(
															'x-items-synced'
														),
														[0]
													)}
												</List.ItemText>
											</ClayList.ItemField>

											<ClayList.ItemField>
												<ClayToggle
													disabled={
														!isDataSourceConnected
													}
													id='accounts'
													label={Liferay.Language.get(
														'disconnected'
													)}
													onToggle={value => {
														setAccounts(!accounts);

														// TODO: fire SyncAccounts function when endpoint is ready

														if (value) {
															addAlert({
																alertType:
																	Alert.Types
																		.Success,
																message: Liferay.Language.get(
																	'the-data-source-setup-is-now-complete,-and-you-will-begin-to-see-data-as-activities-occur-on-your-sites'
																)
															});
														}
													}}
													toggled={
														accounts &&
														isDataSourceConnected
													}
												/>
											</ClayList.ItemField>
										</ClayList.Item>

										<ClayList.Item flex>
											<ClayList.ItemField>
												<ClayIcon
													aria-label={Liferay.Language.get(
														'individuals'
													)}
													className='mr-2 mt-1 text-secondary'
													symbol='users'
												/>
											</ClayList.ItemField>

											<ClayList.ItemField expand>
												<ClayList.ItemTitle>
													{Liferay.Language.get(
														'individuals'
													)}
												</ClayList.ItemTitle>

												<List.ItemText>
													{Liferay.Language.get(
														'represents-fields-from-the-contact-or-lead-object-within-salesforce'
													)}
												</List.ItemText>

												<List.ItemText>
													{/* TODO: replace [0] to a dynamic variable when endpoint is ready */}

													{sub(
														Liferay.Language.get(
															'x-items-synced'
														),
														[0]
													)}
												</List.ItemText>
											</ClayList.ItemField>

											<ClayList.ItemField>
												<ClayToggle
													disabled={
														!isDataSourceConnected
													}
													id='individuals'
													label={Liferay.Language.get(
														'disconnected'
													)}
													onToggle={value => {
														setIndividuals(
															!individuals
														);

														// TODO: fire SyncIndividuals function when endpoint is ready

														if (value) {
															addAlert({
																alertType:
																	Alert.Types
																		.Success,
																message: Liferay.Language.get(
																	'the-data-source-setup-is-now-complete,-and-you-will-begin-to-see-data-as-activities-occur-on-your-sites'
																)
															});
														}
													}}
													toggled={
														individuals &&
														isDataSourceConnected
													}
												/>
											</ClayList.ItemField>
										</ClayList.Item>
									</ClayList>
								</div>
							</ClayLayout.SheetSection>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.Sheet>
			</ClayLayout.ContainerFluid>
		</BasePage>
	);
};

export default connector(SalesforceOverview);
