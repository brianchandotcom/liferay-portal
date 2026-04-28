import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'settings/components/base-page/BasePage';
import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ConnectorEntities from './ConnectorEntities';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import Loading from 'shared/components/Loading';
import React, {ComponentType, useEffect, useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {AssignedPropertiesTable} from '../AssignedPropertiesTable';
import {Card} from 'shared/components/revamping/Card';
import {close, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {ConnectorConfig} from './types';
import {CopyInputValue} from '../CopyInputValue';
import {DataSource} from 'shared/util/records';
import {DataSourceEditableTitle} from '../data-source/DataSourceEditableTitle';
import {DataSourceStatuses} from 'shared/util/constants';
import {fetch} from 'shared/api/data-source';
import {generateConnectorToken, updateConnector} from 'shared/api/connector';
import {getDataSourceDisplayObject} from 'shared/util/data-sources';
import {Text} from '@clayui/core';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDisconnectDataSource} from '../data-source/utils';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';
import {withSelectionProvider} from 'shared/context/selection';

const connector = connect(null, {
	addAlert,
	close,
	open
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IConnectorOverviewProps extends PropsFromRedux {
	config: ConnectorConfig;
	dataSource: DataSource;
}

type AlertState = {
	displayType: DisplayType;
	message: string;
};

const ConnectorOverview: React.FC<IConnectorOverviewProps> = ({
	addAlert,
	close,
	config,
	dataSource: initialDataSource,
	open
}) => {
	const [loading, setLoading] = useState(false);
	const [dataSource, setDataSource] = useState(initialDataSource);
	const [token, setToken] = useState('');

	const {groupId = '', id = ''} = useParams<{
		groupId: string;
		id: string;
	}>();
	const currentUser = useCurrentUser();

	const [alert, setAlert] = useState<AlertState>({
		displayType: 'success',
		message: ''
	});

	const dataSourceActive = dataSource.status === DataSourceStatuses.Active;

	const accountStatus = dataSource.provider?.getIn([
		'accountsConfiguration',
		'accountsStatus'
	]);

	const endpointURL = `${window.location.origin}${config.endpointPath}`;

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
		const next: AlertState = {
			displayType: 'success',
			message: config.languages.successAlert
		};

		if (!dataSourceActive) {
			next.displayType = 'warning';
			next.message = config.languages.disconnectedAlert;
		} else if (accountStatus) {
			next.message = Liferay.Language.get(
				'all-data-coming-from-this-data-source-is-up-to-date.-there-are-no-errors-to-report'
			);
		}

		setAlert(next);
	}, [accountStatus, config.languages, dataSourceActive]);

	useEffect(() => {
		if (dataSourceActive) {
			return;
		}

		const fetchConnectorTokenForGroup = async () => {
			try {
				const data = await generateConnectorToken({
					groupId,
					type: config.slug
				});

				if (data?.token) {
					setToken(data.token);
				}
			} catch (error) {
				addAlert({
					alertType: Alert.Types.Error,
					message: (error as Error).message,
					timeout: false
				});
			}
		};

		fetchConnectorTokenForGroup();
	}, [config.slug, dataSourceActive, groupId]);

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

	const updateDataSourceFn = (params: {[key: string]: any}) =>
		updateConnector(
			config.slug,
			params as Parameters<typeof updateConnector>[1]
		);

	return (
		<BasePage
			breadcrumbItems={[
				breadcrumbs.getDataSources({groupId}),
				breadcrumbs.getDataSourceName({
					active: true,
					label: dataSource.name ?? ''
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
				onUpdateName={async (name: string) => {
					await updateConnector(config.slug, {groupId, id, name});

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
				</div>

				<div>
					<Card.SubHeader
						title={Liferay.Language.get('data-source-details')}
					/>

					<ClayLayout.Row className='mt-4'>
						<ClayLayout.Col size={6}>
							<CopyInputValue
								addAlert={addAlert}
								disabled={false}
								title={Liferay.Language.get('endpoint-url')}
								value={endpointURL}
							/>
						</ClayLayout.Col>

						<ClayLayout.Col size={6}>
							<CopyInputValue
								addAlert={addAlert}
								disabled={false}
								title={Liferay.Language.get('token')}
								value={token}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<ClayLayout.Row>
						<ClayLayout.Col size={6}>
							<ClayForm.Group className='mb-0'>
								<label htmlFor='dataSourceType'>
									{Liferay.Language.get('data-source-type')}
								</label>

								<ClayInput
									id='dataSourceType'
									readOnly
									type='text'
									value={config.displayName}
								/>
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col size={6}>
							<ClayForm.Group className='mb-0'>
								<label htmlFor='dataSourceId'>
									{Liferay.Language.get('data-source-id')}
								</label>

								<ClayInput
									id='dataSourceId'
									readOnly
									type='text'
									value={dataSource.id}
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
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
				<ConnectorEntityList
					accountStatus={accountStatus}
					config={config}
					dataSource={dataSource}
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
					customColumns={config.columns ?? []}
					dataSource={dataSource}
					handleUpdateDataSource={handleUpdateDataSource}
					loading={loading}
					open={open}
					updateDataSourceFn={updateDataSourceFn}
				/>
			</Card>
		</BasePage>
	);
};

interface IConnectorEntityListProps {
	accountStatus: 'connected' | 'disconnected';
	config: ConnectorConfig;
	dataSource: DataSource;
	groupId: string;
}

const ConnectorEntityList: React.FC<IConnectorEntityListProps> = ({
	accountStatus,
	config,
	dataSource,
	groupId
}) => {
	const primaryEntity = config.entities[0];

	const countResponse = useRequest({
		dataSourceFn: (params: {[key: string]: any}) =>
			primaryEntity?.fetchCount
				? primaryEntity.fetchCount({
						groupId: params.groupId,
						id: params.id
				  })
				: Promise.resolve(undefined),
		variables: {groupId, id: dataSource.id}
	});

	if (countResponse.error) {
		return <ErrorDisplay />;
	}

	if (countResponse.loading) {
		return <Loading spacer />;
	}

	const syncedCounts: {[accessor: string]: number | undefined} = {};

	if (primaryEntity) {
		syncedCounts[primaryEntity.accessor] = countResponse.data as
			| number
			| undefined;
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
					{config.languages.syncHelper}
				</Text>
			</div>

			<div className='mt-3 text-dark'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			<ConnectorEntities
				connectionStatus={accountStatus}
				entities={config.entities}
				syncedCounts={syncedCounts}
			/>
		</div>
	);
};

export default compose(
	connector,
	withSelectionProvider
)(ConnectorOverview) as ComponentType<{
	config: ConnectorConfig;
	dataSource: DataSource;
}>;
