import * as API from 'shared/api';
import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import InputWithEditToggle, {
	FontSize
} from 'shared/components/InputWithEditToggle';
import List from '@clayui/list';
import React, {useCallback, useEffect, useRef, useState} from 'react';
import ReconnectSalesforce from './ReconnectSalesforce';
import {addAlert} from '../../../shared/actions/alerts';
import {Alert} from 'shared/types';
import {ClayInput, ClayToggle} from '@clayui/form';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {DataSource, User} from 'shared/util/records';
import {DataSourceStates} from 'shared/util/constants';
import {
	fetchDataSource,
	updateSalesforceDataSource
} from 'shared/actions/data-sources';
import {sequence} from 'shared/util/promise';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {
	toPromise,
	validateMaxLength,
	validateRequired
} from 'shared/components/form';
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
	currentUser: User;
	dataSource: DataSource;
	groupId: string;
	id: string;
}

const SalesforceOverview: React.FC<ISalesforceOverviewProps> = ({
	addAlert,
	close,
	currentUser,
	dataSource,
	fetchDataSource,
	groupId,
	id,
	open,
	updateSalesforceDataSource
}) => {
	type Alert = {
		displayType: string;
		message: string;
	};

	const initialAlert: Alert = {
		displayType: '',
		message: ''
	};

	const [accounts, setAccounts] = useState<boolean>(false);
	const [alert, setAlert] = useState(initialAlert);
	const [individuals, setIndividuals] = useState<boolean>(false);

	const [isDataSourceConnected, setIsDataSourceConnected] = useState<boolean>(
		dataSource.state === DataSourceStates.CredentialsValid
	);

	useEffect(() => {
		let displayType = 'success';

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
				<p className='text-4'>
					{Liferay.Language.get(
						'this-action-will-stop-syncing-data-from-your-dxp-instance-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
					)}
				</p>
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
		<>
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

								<hr className='my-2' />

								{alert && (
									<ClayAlert
										className='font-weight-semi-bold mt-3'
										displayType={alert.displayType as any}
									>
										{alert.message}
									</ClayAlert>
								)}

								{!isDataSourceConnected ? (
									<ReconnectSalesforce
										addAlert={addAlert}
										setAccounts={setAccounts}
										setIndividuals={setIndividuals}
										setIsDataSourceConnected={
											setIsDataSourceConnected
										}
									/>
								) : (
									<ClayButton
										aria-label={Liferay.Language.get(
											'disconnect-data-source'
										)}
										className='mt-5'
										displayType='danger'
										onClick={handleDisconnectClick}
										outline
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

							<ClayLayout.SheetSection>
								<Text
									color='secondary'
									size={3}
									weight='semi-bold'
								>
									{Liferay.Language.get(
										'data-source-details'
									).toUpperCase()}
								</Text>

								<hr className='my-2' />

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
											disabled
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
											disabled
											type='text'
											value={dataSource.id}
										/>
									</ClayInput.GroupItem>
								</ClayInput.Group>
							</ClayLayout.SheetSection>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.Sheet>

				<ClayLayout.Sheet className='p-4'>
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

								<p className='mb-0'>
									<Text color='secondary' size={4}>
										{Liferay.Language.get(
											'to-configure-your-salesforce-data-source,-go-to-your-salesforce-environment-to-update-this-app-connection'
										)}
									</Text>
								</p>

								<div className='p-1'>
									<ClayList>
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
													id='accounts'
													label='Disconnected'
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
													toggled={accounts}
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
													id='individuals'
													label='Disconnected'
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
													toggled={individuals}
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
		</>
	);
};

export default connector(SalesforceOverview);
