import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'settings/components/base-page/BasePage';
import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import DemandbaseEntities from './DemandbaseEntities';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import Loading from 'shared/components/Loading';
import React, {useEffect, useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {AssignedPropertiesTable} from '../AssignedPropertiesTable';
import {Card} from 'shared/components/revamping/Card';
import {ClayInput} from '@clayui/form';
import {close, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {ConnectLiferayDXPTokenFragment} from '../liferay/ConnectLiferayDXPTokenFragment';
import {DataSource} from 'shared/util/records';
import {DataSourceEditableTitle} from '../data-source/DataSourceEditableTitle';
import {DataSourceStatuses} from 'shared/util/constants';
import {
	fetch,
	fetchDemandbaseAccountsCount,
	fetchDemandbaseBuyingCommitteeCount,
	fetchDemandbaseCustomAttributesCount,
	fetchDemandbaseIntentDataCount,
	fetchToken,
	updateDemandbase
} from 'shared/api/data-source';
import {getDataSourceDisplayObject} from 'shared/util/data-sources';
import {Text} from '@clayui/core';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDisconnectDataSource} from '../data-source/utils';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';
import {withSelectionProvider} from 'shared/context/selection';

const TIMEOUT_INTERVAL = 5000;

const connector = connect(null, {
	addAlert,
	close,
	open
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IDemandbaseOverviewProps extends PropsFromRedux {
	dataSource: DataSource;
}

const DemandbaseOverview: React.FC<IDemandbaseOverviewProps> = ({
	addAlert,
	close,
	dataSource: initialDataSource,
	open
}) => {
	const [loading, setLoading] = useState(false);
	const [dataSource, setDataSource] = useState(initialDataSource);
	const [token, setToken] = useState('');

	const {groupId, id} = useParams();
	const currentUser = useCurrentUser();

	type Alert = {
		displayType: DisplayType;
		message: string;
	};

	const [alert, setAlert] = useState<Alert>({
		displayType: 'success',
		message: ''
	});

	const dataSourceActive = dataSource.status === DataSourceStatuses.Active;

	const entitiesStatus = {
		accountsStatus: dataSource.provider.getIn([
			'accountsConfiguration',
			'accountsStatus'
		]),
		buyingCommitteeStatus: dataSource.provider.getIn([
			'buyingCommitteeConfiguration',
			'buyingCommitteeStatus'
		]),
		customAttributesStatus: dataSource.provider.getIn([
			'customAttributesConfiguration',
			'customAttributesStatus'
		]),
		intentDataStatus: dataSource.provider.getIn([
			'intentDataConfiguration',
			'intentDataStatus'
		])
	};

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
				'you-have-successfully-authenticated-your-token-with-liferay-analytics-cloud.-demandbase-data-will-appear-here-once-it-is-sent-and-processed'
			)
		};

		if (!dataSourceActive) {
			alert.displayType = 'warning';

			alert.message = Liferay.Language.get(
				'the-data-source-is-disconnected.-data-is-no-longer-being-synced-from-demandbase,-but-you-can-reconnect-to-resume-syncing.'
			);
		} else if (
			entitiesStatus.accountsStatus ||
			entitiesStatus.buyingCommitteeStatus ||
			entitiesStatus.customAttributesStatus ||
			entitiesStatus.intentDataStatus
		) {
			alert.message = Liferay.Language.get(
				'all-data-coming-from-this-data-source-is-up-to-date.-there-are-no-errors-to-report'
			);
		}

		setAlert(alert);
	}, [dataSourceActive, entitiesStatus]);

	let _tokenRequest;

	const getNextToken = async (prevToken?: string) => {
		const nextToken = await fetchToken(groupId, id);

		if (!prevToken || prevToken === nextToken) {
			_tokenRequest = setTimeout(
				() => getNextToken(nextToken),
				TIMEOUT_INTERVAL
			);
		} else {
			handleUpdateDataSource();
		}

		return nextToken;
	};

	useEffect(() => {
		if (!dataSourceActive) {
			_tokenRequest = getNextToken().then(setToken);
		}

		return () => {
			clearTimeout(_tokenRequest);
		};
	}, [dataSourceActive]);

	const {handleDisconnect} = useDisconnectDataSource({
		addAlert,
		close,
		groupId,
		id,
		onSubmit: async () => {
			await handleUpdateDataSource();
		},
		open
	});

	const {display, label} = getDataSourceDisplayObject(dataSource);

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
			<DataSourceEditableTitle
				dataSource={dataSource}
				displayType={display}
				editable={currentUser.isAdmin()}
				groupId={groupId}
				label={label}
				onUpdateName={async name => {
					await updateDemandbase({groupId, id, name} as any);

					await handleUpdateDataSource();
				}}
			/>

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
										'to-reestablish-the-connection-between-demandbase-instance-and-liferay-analytics-cloud,-copy-the-token-below-and-go-to-demandbase-instance-settings-to-continue-the-data-source-configuration'
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
							<label>
								<Text size={3} weight='semi-bold'>
									{Liferay.Language.get(
										'copy-this-endpoint-url-to-your-demandbase-instance'
									)}
								</Text>
							</label>
							<ConnectLiferayDXPTokenFragment
								addAlert={addAlert}
								disabled={false}
								token={token}
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
								value={Liferay.Language.get('demandbase')}
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
						onClick={handleDisconnect}
						outline
						size='sm'
					>
						<ClayIcon className='mr-2' symbol='logout' />

						{Liferay.Language.get('disconnect-data-source')}
					</ClayButton>
				)}
			</Card>

			<Card title={Liferay.Language.get('synced-data')}>
				<DemandbaseEntityList
					dataSource={dataSource}
					entitiesStatus={entitiesStatus}
					groupId={groupId}
				/>
			</Card>

			<Card
				innerPadding={false}
				title={Liferay.Language.get('assigned-properties')}
			>
				<AssignedPropertiesTable
					addAlert={addAlert}
					close={close}
					customColumns={[
						{
							accessor: 'accounts',
							cellRenderer: ({data}) => (
								<td key={data.channelId}>
									<div className='table-title text-truncate'>
										<Text>{data.count}</Text>
									</div>
								</td>
							),
							label: Liferay.Language.get('accounts')
						},
						{
							accessor: 'intentData',
							cellRenderer: ({data}) => (
								<td key={data.channelId}>
									<div className='table-title text-truncate'>
										<Text>{data.count}</Text>
									</div>
								</td>
							),
							label: Liferay.Language.get('intent-data')
						},
						{
							accessor: 'buyingCommittee',
							cellRenderer: ({data}) => (
								<td key={data.channelId}>
									<div className='table-title text-truncate'>
										<Text>{data.count}</Text>
									</div>
								</td>
							),
							label: Liferay.Language.get('buying-committee')
						},
						{
							accessor: 'customAttributes',
							cellRenderer: ({data}) => (
								<td key={data.channelId}>
									<div className='table-title text-truncate'>
										<Text>{data.count}</Text>
									</div>
								</td>
							),
							label: Liferay.Language.get('custom-attributes')
						}
					]}
					dataSource={dataSource}
					handleUpdateDataSource={handleUpdateDataSource}
					loading={loading}
					open={open}
					updateDataSourceFn={updateDemandbase}
				/>
			</Card>
		</BasePage>
	);
};

const DemandbaseEntityList = ({dataSource, entitiesStatus, groupId}) => {
	const {
		accountsStatus,
		buyingCommitteeStatus,
		customAttributesStatus,
		intentDataStatus
	} = entitiesStatus;

	const accountsCountResponse = useRequest({
		dataSourceFn: fetchDemandbaseAccountsCount,
		variables: {groupId, id: dataSource.id}
	});

	const intentDataCountResponse = useRequest({
		dataSourceFn: fetchDemandbaseIntentDataCount,
		variables: {groupId, id: dataSource.id}
	});

	const buyingCommitteeCountResponse = useRequest({
		dataSourceFn: fetchDemandbaseBuyingCommitteeCount,
		variables: {groupId, id: dataSource.id}
	});

	const customAttributesCountResponse = useRequest({
		dataSourceFn: fetchDemandbaseCustomAttributesCount,
		variables: {groupId, id: dataSource.id}
	});

	if (
		accountsCountResponse.error ||
		intentDataCountResponse.error ||
		buyingCommitteeCountResponse.error ||
		customAttributesCountResponse.error
	) {
		return <ErrorDisplay />;
	}

	if (
		accountsCountResponse.loading ||
		intentDataCountResponse.loading ||
		buyingCommitteeCountResponse.loading ||
		customAttributesCountResponse.loading
	) {
		return <Loading spacer />;
	}

	return (
		<div>
			<ClayAlert displayType='info'>
				{Liferay.Language.get(
					'your-data-may-take-some-time-to-sync.-if-it-has-already-synced,-you-can-dismiss-this-message'
				)}
			</ClayAlert>

			<div className='mb-2'>
				<Text color='secondary' size={4}>
					{Liferay.Language.get(
						'to-configure-your-demandbase-data-source,-go-to-demandbase-account-connector-settings-and-use-the-token-and-endpoint-url-provided-by-liferay-analytics-cloud'
					)}
				</Text>
			</div>

			<div className='mt-3 text-dark'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			<DemandbaseEntities
				accountConnectionStatus={accountsStatus}
				accountsSyncedCount={accountsCountResponse.data}
				buyingCommitteeConnectionStatus={buyingCommitteeStatus}
				buyingCommitteeSyncedCount={buyingCommitteeCountResponse.data}
				customAttributesConnectionStatus={customAttributesStatus}
				customAttributesSyncedCount={customAttributesCountResponse.data}
				intentDataConnectionStatus={intentDataStatus}
				intentDataSyncedCount={intentDataCountResponse.data}
			/>
		</div>
	);
};

export default compose(connector, withSelectionProvider)(DemandbaseOverview);
