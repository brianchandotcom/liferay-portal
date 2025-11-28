import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'settings/components/base-page/BasePage';
import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import CrossPageSelect from 'shared/hoc/CrossPageSelect';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import InputWithEditToggle from 'shared/components/InputWithEditToggle';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useCallback, useEffect, useRef, useState} from 'react';
import SalesforceAccountsAndIndividuals from './SalesforceAccountsAndIndividuals';
import TextTruncate from 'shared/components/TextTruncate';
import URLConstants from 'shared/util/url-constants';
import {addAlert} from '../../../shared/actions/alerts';
import {Alert} from 'shared/types';
import {Card} from 'shared/components/revamping/Card';
import {ClayInput, ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import {close, modalTypes, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {ConnectSalesforceAuth} from './ConnectSalesforceAuth';
import {createOrderIOMap, NAME} from 'shared/util/pagination';
import {DataSource} from 'shared/util/records';
import {DataSourceStatuses, Sizes} from 'shared/util/constants';
import {
	disconnect,
	fetch,
	fetchAccountsCount,
	fetchChannelDatasources,
	fetchUserCount,
	updateSalesforce
} from 'shared/api/data-source';
import {
	getDataSourceDisplayObject,
	validateUniqueName
} from 'shared/util/data-sources';
import {Link, useParams} from 'react-router-dom';
import {Routes, toRoute} from 'shared/util/router';
import {sequence} from 'shared/util/promise';
import {Text} from '@clayui/core';
import {
	toPromise,
	validateMaxLength,
	validateRequired
} from 'shared/components/form';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useQueryPagination} from 'shared/hooks/useQueryPagination';
import {useRequest} from 'shared/hooks/useRequest';
import {withSelectionProvider} from 'shared/context/selection';

const connector = connect(null, {
	addAlert,
	close,
	open
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ISalesforceOverviewProps extends PropsFromRedux {
	dataSource: DataSource;
}

const SalesforceOverview: React.FC<ISalesforceOverviewProps> = ({
	addAlert,
	close,
	dataSource: initialDataSource,
	open
}) => {
	const [loading, setLoading] = useState(false);
	const [dataSource, setDataSource] = useState(initialDataSource);

	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(NAME)
	});

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

	const [alert, setAlert] = useState(initialAlert);

	const dataSourceActive = dataSource.status === DataSourceStatuses.Active;

	const enabledAllAccounts = dataSource.provider.getIn(
		['accountsConfiguration', 'enableAllAccounts'],
		false
	);

	const enabledAllContacts = dataSource.provider.getIn(
		['contactsConfiguration', 'enableAllContacts'],
		false
	);

	const enableAllLeads = dataSource.provider.getIn(
		['contactsConfiguration', 'enableAllLeads'],
		false
	);

	const enableAllChannels = dataSource.provider.getIn(
		['channelsConfiguration', 'enableAllChannels'],
		false
	);

	const handleUpdateDataSource = async () => {
		try {
			setLoading(true);

			const newDataSource = await fetch({
				groupId,
				id
			});

			setDataSource(new DataSource(newDataSource));
		} catch (error) {
			addAlert({
				alertType: Alert.Types.Error,
				message: Liferay.Language.get(
					'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
				)
			});
		} finally {
			setLoading(false);
		}
	};

	useEffect(() => {
		const alert: Alert = {
			displayType: 'success',
			message: Liferay.Language.get(
				'you-have-successfully-authenticated-your-token-with-liferay-analytics-cloud.-you-can-now-select-the-data-to-sync'
			)
		};

		if (!dataSourceActive) {
			alert.displayType = 'warning';

			alert.message = Liferay.Language.get(
				'the-data-source-is-disconnected.-data-is-no-longer-being-synced-from-salesforce,-but-you-can-reconnect-to-resume-syncing'
			);
		} else if (enabledAllAccounts || enabledAllContacts || enableAllLeads) {
			alert.message = Liferay.Language.get(
				'all-data-coming-from-this-data-source-is-up-to-date.-there-are-no-errors-to-report'
			);
		}

		setAlert(alert);
	}, [
		dataSourceActive,
		enableAllLeads,
		enabledAllAccounts,
		enabledAllContacts
	]);

	const cachedNameValues = useRef(new Map());

	const handleDisconnectClick = useCallback(() => {
		open(modalTypes.CONFIRMATION_MODAL, {
			message: (
				<Text as='p' size={4}>
					{Liferay.Language.get(
						'this-action-will-stop-syncing-data-from-salesforce-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
					)}
				</Text>
			),
			modalVariant: 'modal-warning',
			onClose: close,
			onSubmit: async () => {
				try {
					await disconnect({groupId, id});

					await handleUpdateDataSource();

					addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get(
							'data-source-disconnected'
						)
					});

					close();
				} catch (error) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
						)
					});
				}
			},
			submitButtonDisplay: 'warning',
			submitMessage: Liferay.Language.get('disconnect'),
			title: Liferay.Language.get('disconnect-data-source'),
			titleIcon: 'warning-full'
		});
	}, [addAlert, close, groupId, id, open]);

	const handleUpdateName = useCallback(
		async name => {
			await updateSalesforce({groupId, id, name} as any);

			await handleUpdateDataSource();
		},
		[groupId, id]
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

	const {display, label} = getDataSourceDisplayObject(dataSource, true);

	const channelDatasources = useRequest({
		dataSourceFn: fetchChannelDatasources,
		variables: {delta, groupId, id: dataSource.id, orderIOMap, page, query}
	});

	const channelsConfigurationRef = useRef(null);

	useEffect(() => {
		if (dataSource.provider?.get('channelsConfiguration')) {
			channelsConfigurationRef.current = {
				channels: dataSource.provider
					.getIn(['channelsConfiguration', 'channels'])
					.toJS(),
				enableAllChannels: dataSource.provider.get(
					'channelsConfiguration'
				).enableAllChannels
			};
		}
	}, [dataSource]);

	const handleToggleChannel = async item => {
		const selectedChannelIndex = channelsConfigurationRef.current.channels.findIndex(
			({channelId}) => channelId === item.channelId
		);

		if (selectedChannelIndex !== -1) {
			const updatedChannelsConfiguration = {
				...channelsConfigurationRef.current,
				channels: channelsConfigurationRef.current.channels.map(
					(channel, index) =>
						index === selectedChannelIndex
							? {...channel, enabled: !channel.enabled}
							: channel
				)
			};

			try {
				await updateSalesforce({
					channelsConfiguration: updatedChannelsConfiguration,
					groupId,
					id: dataSource.id
				} as any);
			} catch (error) {
				addAlert({
					alertType: Alert.Types.Error,
					message: Liferay.Language.get(
						'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
					)
				});
			} finally {
				channelsConfigurationRef.current = updatedChannelsConfiguration;
			}
		}
	};

	return (
		<BasePage
			breadcrumbItems={[
				breadcrumbs.getDataSources({groupId}),
				breadcrumbs.getDataSourceName({
					active: true,
					label: dataSource.name
				})
			]}
			documentTitle={Liferay.Language.get('configure-data-source')}
		>
			<div className='mb-5'>
				<ClayLabel className='mb-2' displayType={display as any}>
					{label}
				</ClayLabel>

				<InputWithEditToggle
					editable={currentUser?.isAdmin()}
					inputWidth={30}
					name='dataSourceName'
					onSubmit={name => toPromise(handleUpdateName(name))}
					required
					validate={sequence([
						validateRequired,
						validateMaxLength(75),
						handleValidate
					])}
					value={dataSource.name || ''}
				/>
			</div>

			<Card title={Liferay.Language.get('authentication')}>
				<div className='mb-4'>
					<Card.SubHeader
						title={Liferay.Language.get('connection-status')}
					/>

					{alert && (
						<ClayAlert displayType={alert.displayType}>
							{alert.message}
						</ClayAlert>
					)}

					{!dataSourceActive && (
						<>
							<div className='mb-3'>
								<Text color='secondary' size={4}>
									{Liferay.Language.get(
										'to-reestablish-the-connection-between-salesforce-and-liferay-analytics-cloud,-generate-a-token-and-paste-the-code-on-the-input-below'
									)}
								</Text>

								<ClayLink
									className='ml-1'
									href={URLConstants.HelpConnectDxp}
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
								buttonProps={{size: 'sm'}}
								dataSource={dataSource}
								onSubmit={handleUpdateDataSource}
							/>
						</>
					)}
				</div>

				<div className='mb-4'>
					<Card.SubHeader
						title={Liferay.Language.get('data-source-details')}
					/>

					<ClayInput.Group className='d-flex mt-3'>
						<ClayInput.GroupItem className='mr-3' shrink>
							<label htmlFor='dataSourceType'>
								{Liferay.Language.get('data-source-type')}
							</label>

							<ClayInput
								readOnly
								type='text'
								value={Liferay.Language.get('salesforce')}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem className='ml-0' shrink>
							<label htmlFor='dataSourceId'>
								{Liferay.Language.get('data-source-id')}
							</label>

							<ClayInput
								readOnly
								type='text'
								value={dataSource.id}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</div>

				{currentUser.isAdmin() && dataSourceActive && (
					<ClayButton
						aria-label={Liferay.Language.get(
							'disconnect-data-source'
						)}
						displayType='danger'
						onClick={handleDisconnectClick}
						outline
						size='sm'
					>
						<ClayIcon className='mr-2' symbol='logout' />

						{Liferay.Language.get('disconnect-data-source')}
					</ClayButton>
				)}
			</Card>

			<Card title={Liferay.Language.get('synced-data')}>
				<AccountAndIndividuals
					currentUser={currentUser}
					dataSource={dataSource}
					groupId={groupId}
					loading={loading}
					onSubmit={async ({
						enabledAllAccounts,
						enabledAllIndividuals
					}) => {
						await updateSalesforce({
							accountsConfiguration: {
								enableAllAccounts: enabledAllAccounts
							},
							contactsConfiguration: {
								enableAllContacts: enabledAllIndividuals,
								enableAllLeads: enabledAllIndividuals
							},
							groupId,
							id: dataSource.id
						} as any);

						await handleUpdateDataSource();

						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'synced-data-settings-have-been-saved'
							)
						});
					}}
				/>
			</Card>

			<Card
				innerPadding={false}
				title={Liferay.Language.get('assigned-properties')}
			>
				<div className='p-4'>
					<Text as='p' color='secondary' size={4}>
						{Liferay.Language.get(
							'properties-allow-you-to-aggregate-data-on-your-users-and-dxp-sites-and-channels.-the-data-source-data-will-be-available-in-any-property-they-are-assigned-to'
						)}
					</Text>

					<Card.SubHeader
						title={Liferay.Language.get('data-availability')}
					/>

					<Text as='p' color='secondary' size={4}>
						{Liferay.Language.get(
							'choose-the-properties-that-will-have-access-to-this-data-source'
						)}
					</Text>

					<ClayRadioGroup
						defaultValue={enableAllChannels ? 'all' : 'custom'}
						inline
						onChange={async value => {
							await updateSalesforce({
								channelsConfiguration: {
									channels:
										channelsConfigurationRef.current
											.channels,
									enableAllChannels: value === 'all'
								},
								groupId,
								id: dataSource.id
							} as any);

							await handleUpdateDataSource();
						}}
					>
						<ClayRadio
							disabled={
								!currentUser.isAdmin() || !dataSourceActive
							}
							label={Liferay.Language.get(
								'all-properties,-including-those-not-yet-created'
							)}
							value='all'
						/>

						<ClayRadio
							disabled={
								!currentUser.isAdmin() || !dataSourceActive
							}
							label={Liferay.Language.get('select-properties')}
							value='custom'
						/>
					</ClayRadioGroup>
				</div>

				{enableAllChannels ? (
					<div className='d-flex justify-content-center text-center my-6'>
						<NoResultsDisplay
							description={Liferay.Language.get(
								'all-properties-from-this-workspace-have-access-to-this-data-source'
							)}
							icon={{
								border: false,
								size: Sizes.XXXLarge,
								symbol: 'ac_no_sites'
							}}
							title={Liferay.Language.get('all-aboard')}
						/>
					</div>
				) : (
					<CrossPageSelect
						columns={[
							{
								accessor: 'name',
								cellRenderer: ({data, hrefFormatter}) => (
									<td
										className='table-cell-expand'
										key={data.channelId}
									>
										<div className='table-title text-truncate'>
											<Link to={hrefFormatter(data)}>
												<TextTruncate
													title={data.name}
												/>
											</Link>
										</div>
									</td>
								),
								cellRendererProps: {
									hrefFormatter: ({channelId}) =>
										toRoute(Routes.SETTINGS_CHANNELS_VIEW, {
											groupId,
											id: channelId
										})
								},
								className: 'table-cell-expand',
								label: Liferay.Language.get('property-name')
							},
							{
								accessor: 'enabled',
								cellRenderer: ({data}) => (
									<ToggleRenderer
										addAlert={addAlert}
										close={close}
										data={data}
										disabled={
											loading ||
											!currentUser.isAdmin() ||
											!dataSourceActive
										}
										key={`${data.channelId}-toggle`}
										onChange={handleToggleChannel}
										open={open}
									/>
								),
								label: '',
								sortable: false
							}
						]}
						delta={delta}
						entityLabel={Liferay.Language.get('properties')}
						error={channelDatasources.error}
						items={channelDatasources.data?.items}
						noResultsRenderer={
							<NoResultsDisplay
								className='py-6'
								description={
									<>
										{Liferay.Language.get(
											'go-to-properties-under-workspace-settings-to-create-a-property'
										)}

										<ClayLink
											className='d-block mb-3'
											href={URLConstants.CreateProperty}
											key='DOCUMENTATION'
											target='_blank'
										>
											{Liferay.Language.get(
												'access-our-documentation-to-learn-more'
											)}
										</ClayLink>
									</>
								}
								icon={{
									border: false,
									size: Sizes.XXXLarge,
									symbol: 'ac_satellite'
								}}
								title={Liferay.Language.get(
									'there-are-no-properties-found'
								)}
							/>
						}
						orderByOptions={[
							{
								label: Liferay.Language.get('property-name'),
								value: NAME
							}
						]}
						ordered
						orderIOMap={orderIOMap}
						page={page}
						query={query}
						renderNav={() => {
							if (currentUser.isAdmin()) {
								return (
									<ClayButton
										disabled={!dataSourceActive}
										onClick={() =>
											open(
												modalTypes.SELECT_CHANNELS_MODAL,
												{
													groupId,
													initialItems:
														channelsConfigurationRef.current?.channels?.map(
															({channelId}) =>
																channelId
														) ?? [],
													onClose: close,
													onSelect: async items => {
														await updateSalesforce({
															channelsConfiguration: {
																channels: items.map(
																	channelId => ({
																		channelId,
																		enabled: true
																	})
																),
																enableAllChannels: false
															},
															groupId,
															id: dataSource.id
														} as any);

														await handleUpdateDataSource();

														channelDatasources.refetch();
													}
												}
											)
										}
									>
										{Liferay.Language.get(
											'select-property'
										)}
									</ClayButton>
								);
							}

							return null;
						}}
						rowIdentifier='channelId'
						showCheckbox={false}
						total={channelDatasources.data?.total}
					/>
				)}
			</Card>
		</BasePage>
	);
};

const AccountAndIndividuals = ({
	currentUser,
	dataSource,
	groupId,
	loading,
	onSubmit
}) => {
	const [enabledAllAccounts, setEnabledAllAccount] = useState(
		dataSource.provider.getIn(
			['accountsConfiguration', 'enableAllAccounts'],
			false
		)
	);

	const [enabledAllIndividuals, setEnabledAllIndividuals] = useState(
		dataSource.provider.getIn(
			['contactsConfiguration', 'enableAllContacts'],
			false
		) ||
			dataSource.provider.getIn(
				['contactsConfiguration', 'enableAllLeads'],
				false
			)
	);

	const accountsCountResponse = useRequest({
		dataSourceFn: fetchAccountsCount,
		variables: {groupId, id: dataSource.id}
	});

	const userCountResponse = useRequest({
		dataSourceFn: fetchUserCount,
		variables: {groupId, id: dataSource.id}
	});

	const hasChangesRef = useRef(null);

	const dataSourceActive = dataSource.status === DataSourceStatuses.Active;

	if (accountsCountResponse.error || userCountResponse.error) {
		return <ErrorDisplay />;
	}

	if (accountsCountResponse.loading || userCountResponse.loading) {
		return <Loading spacer />;
	}

	return (
		<div>
			{!hasChangesRef.current &&
				dataSourceActive &&
				!enabledAllAccounts &&
				!enabledAllIndividuals && (
					<ClayAlert displayType='warning' title='Warning'>
						{Liferay.Language.get(
							'the-data-source-setup-is-almost-complete.-sync-data-to-start-seeing-results-as-activities-occur-on-your-sites'
						)}
					</ClayAlert>
				)}

			{hasChangesRef.current && (
				<ClayAlert displayType='info'>
					{Liferay.Language.get(
						'this-configuration-is-not-saved-yet'
					)}
				</ClayAlert>
			)}

			<div className='mb-2'>
				<Text color='secondary' size={4}>
					{Liferay.Language.get(
						'to-configure-your-salesforce-data-source,-go-to-your-salesforce-environment-to-update-this-app-connection'
					)}
				</Text>
			</div>

			<div className='mt-3 text-dark'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('select-items-to-sync').toUpperCase()}
				</Text>
			</div>

			<SalesforceAccountsAndIndividuals
				accountsSyncedCount={accountsCountResponse.data}
				disabled={!dataSourceActive || !currentUser.isAdmin()}
				enabledAccount={enabledAllAccounts}
				enabledIndividual={enabledAllIndividuals}
				individualsSyncedCount={userCountResponse.data}
				onAccountChange={() => {
					setEnabledAllAccount(!enabledAllAccounts);

					hasChangesRef.current = true;
				}}
				onIndividualChange={() => {
					setEnabledAllIndividuals(!enabledAllIndividuals);

					hasChangesRef.current = true;
				}}
			/>

			{dataSourceActive && currentUser.isAdmin() && (
				<ClayButton
					className='mt-3'
					loading={loading}
					onClick={async () => {
						hasChangesRef.current = false;

						await onSubmit({
							enabledAllAccounts,
							enabledAllIndividuals
						});
					}}
					size='sm'
				>
					{Liferay.Language.get('save')}
				</ClayButton>
			)}
		</div>
	);
};

const ToggleRenderer = ({addAlert, close, data, disabled, onChange, open}) => {
	const [state, setState] = useState(data.enabled);

	const handleChange = newState => {
		setState(newState);

		onChange({
			channelId: data.channelId,
			enabled: newState
		});
	};

	return (
		<td className='text-center'>
			<ClayToggle
				defaultChecked={state}
				disabled={disabled}
				onToggle={() => {
					if (state) {
						open(modalTypes.CONFIRMATION_MODAL, {
							cancelMessage: Liferay.Language.get('cancel'),
							message: Liferay.Language.get(
								'this-action-will-stop-syncing-new-data-from-your-liferay-dxp-instance-to-this-property.-data-that-was-already-synced-will-remain-available.-are-you-sure-you-want-to-continue'
							),
							modalVariant: 'modal-warning',
							onClose: close,
							onSubmit: () => {
								handleChange(!state);

								addAlert({
									alertType: Alert.Types.Success,
									message: Liferay.Language.get(
										'properties-settings-have-been-saved'
									)
								});
							},
							submitButtonDisplay: 'warning',
							submitMessage: Liferay.Language.get('stop-syncing'),
							title: Liferay.Language.get('stop-syncing-data'),
							titleIcon: 'warning-full'
						});
					} else {
						handleChange(!state);
					}
				}}
				sizing='sm'
				toggled={state}
			/>
		</td>
	);
};

export default compose(connector, withSelectionProvider)(SalesforceOverview);
