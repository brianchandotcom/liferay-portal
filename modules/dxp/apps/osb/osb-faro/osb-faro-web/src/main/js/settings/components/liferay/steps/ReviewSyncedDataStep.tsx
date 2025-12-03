import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import DataSourceQuery, {
	DataSource,
	DataSourceSyncData
} from 'shared/queries/DataSourceQuery';
import React, {useEffect, useState} from 'react';
import {CREATE_DATE} from 'shared/util/pagination';
import {
	CredentialTypes,
	DataSourceTypes,
	OrderByDirections
} from 'shared/util/constants';
import {Text} from '@clayui/core';
import {updateSearchParams} from 'settings/components/base-page/utis';
import {useHistory} from 'react-router-dom';
import {useInterval} from 'shared/hooks/useInterval';
import {useLazyQuery} from '@apollo/react-hooks';
import {WizardPageButtonGroup} from 'settings/components/base-page/WizardPageButtonGroup';

const TIMEOUT_INTERVAL = 5000;

const DATA_SOURCE_STATUSES = {
	CONFIGURED: {
		display: 'success',
		label: Liferay.Language.get('connected')
	},
	UNCONFIGURED: {
		display: 'secondary',
		label: Liferay.Language.get('disconnected')
	}
};

const ReviewSyncedDataStep = ({onNext, onPrev}) => {
	const history = useHistory();
	const [dataSource, setDataSource] = useState<DataSource>({
		contactsSyncDetails: {selected: false},
		id: '',
		sitesSyncDetails: {selected: false}
	});
	const [getDataSources, {data}] = useLazyQuery<DataSourceSyncData>(
		DataSourceQuery,
		{
			fetchPolicy: 'network-only',
			variables: {
				credentialsType: CredentialTypes.Token,
				size: 1,
				sort: {
					column: CREATE_DATE,
					type: OrderByDirections.Descending
				},
				type: DataSourceTypes.Liferay
			}
		}
	);

	useInterval<void>(getDataSources, TIMEOUT_INTERVAL);

	const getLabelProps = (selected: boolean) =>
		selected
			? DATA_SOURCE_STATUSES.CONFIGURED
			: DATA_SOURCE_STATUSES.UNCONFIGURED;

	const {display: contactsDisplay, label: contactslabel} = getLabelProps(
		dataSource?.contactsSyncDetails?.selected
	);

	const {display: sitesDisplay, label: sitesLabel} = getLabelProps(
		dataSource?.sitesSyncDetails?.selected
	);

	useEffect(() => {
		if (data) {
			const dataSource = data.dataSources[0];

			setDataSource(dataSource);
		}
	}, [data]);

	useEffect(() => {
		getDataSources();
	}, []);

	return (
		<ClayForm
			onSubmit={async event => {
				event.preventDefault();

				updateSearchParams(history, 'dataSourceId', dataSource.id);

				onNext();
			}}
		>
			<div className='mb-2'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			<ClayList>
				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='nodes'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>
							{Liferay.Language.get('properties')}
						</ClayList.ItemTitle>
						<ClayList.ItemText>
							{Liferay.Language.get(
								'used-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels'
							)}
						</ClayList.ItemText>
					</ClayList.ItemField>

					<ClayList.ItemField className='justify-content-center'>
						<ClayLabel displayType={sitesDisplay as any}>
							{sitesLabel}
						</ClayLabel>
					</ClayList.ItemField>
				</ClayList.Item>

				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='picture'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>
							{Liferay.Language.get('sites')}
						</ClayList.ItemTitle>
						<ClayList.ItemText>
							{Liferay.Language.get(
								'represents-the-sites-synced-from-liferay-portal,-under-dxp-instance-settings-analytics-cloud'
							)}
						</ClayList.ItemText>
					</ClayList.ItemField>

					<ClayList.ItemField className='justify-content-center'>
						<ClayLabel displayType={sitesDisplay as any}>
							{sitesLabel}
						</ClayLabel>
					</ClayList.ItemField>
				</ClayList.Item>

				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='users'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>
							{Liferay.Language.get('individuals')}
						</ClayList.ItemTitle>
						<ClayList.ItemText>
							{Liferay.Language.get(
								'represents-the-fields-synced-from-the-contact-object-within-liferay-portal,-under-dxp-instance-settings-analytics-cloud'
							)}
						</ClayList.ItemText>
					</ClayList.ItemField>

					<ClayList.ItemField className='justify-content-center'>
						<ClayLabel displayType={contactsDisplay as any}>
							{contactslabel}
						</ClayLabel>
					</ClayList.ItemField>
				</ClayList.Item>
			</ClayList>

			<WizardPageButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={() => {
					updateSearchParams(history, 'dataSourceId', dataSource.id);

					onPrev();
				}}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {ReviewSyncedDataStep};
