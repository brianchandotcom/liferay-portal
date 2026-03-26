import * as API from 'shared/api';
import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import Link from '@clayui/link';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {CUSTOM_DATE_FORMAT, formatUTCDate} from 'shared/util/date';
import {frontendDataSetColumns} from 'shared/util/table-columns';
import {isNil} from 'lodash/fp';
import {LifecycleStages} from './utils/constants';
import {pagination} from 'shared/util/frontend-data-set';
import {Routes, toRoute} from 'shared/util/router';
import {Sizes} from 'shared/util/constants';
import {useChannelContext} from 'shared/context/channel';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {useRequest} from 'shared/hooks/useRequest';

const mockItems = {
	items: [
		{
			accountName: 'Acme Corporation',
			id: '001xx000003DGbYAAW',
			industry: 'Manufacturing',
			lastActive: '2026-03-24T15:01:15Z',
			lifecycleStage: LifecycleStages.ESTABLISHED
		},
		{
			accountName: 'Globex Corporation',
			id: '001xx000003DGbZAAW',
			industry: 'Technology',
			lastActive: '2026-03-20T12:45:30Z',
			lifecycleStage: LifecycleStages.AWARE
		},
		{
			accountName: 'Initech',
			id: '001xx000003DGbXAAW',
			industry: 'Software',
			lastActive: '2026-03-22T09:30:00Z',
			lifecycleStage: LifecycleStages.AT_RISK
		},
		{
			accountName: 'Umbrella Corporation',
			id: '001xx000003DGbWAAW',
			industry: 'Pharmaceuticals',
			lastActive: '2026-03-18T14:15:45Z',
			lifecycleStage: LifecycleStages.PIPELINE
		},
		{
			accountName: 'Hooli',
			id: '001xx000003DGbVAAW',
			industry: 'Technology',
			lastActive: '2026-03-21T11:00:00Z',
			lifecycleStage: LifecycleStages.ENGAGED
		},
		{
			accountName: 'Stark Industries',
			id: '001xx000003DGbUAAW',
			industry: 'Defense',
			lastActive: '2026-03-19T16:45:30Z',
			lifecycleStage: LifecycleStages.ONBOARDING
		}
	],
	total: 1
};

const lifecycleStagesLabelMap = {
	[LifecycleStages.AT_RISK]: {
		displayType: 'danger',
		label: Liferay.Language.get('at-risk')
	},
	[LifecycleStages.AWARE]: {
		displayType: 'secondary',
		label: Liferay.Language.get('aware')
	},
	[LifecycleStages.ENGAGED]: {
		displayType: 'warning',
		label: Liferay.Language.get('engaged')
	},
	[LifecycleStages.ESTABLISHED]: {
		displayType: 'success',
		label: Liferay.Language.get('established')
	},
	[LifecycleStages.ONBOARDING]: {
		displayType: 'secondary',
		label: Liferay.Language.get('onboarding')
	},
	[LifecycleStages.PIPELINE]: {
		displayType: 'info',
		label: Liferay.Language.get('pipeline')
	}
};

const lifecycleStageFilter = {
	items: Object.entries(lifecycleStagesLabelMap).map(([stage]) => ({
		label: lifecycleStagesLabelMap[stage as LifecycleStages].label,
		value: stage
	}))
};

interface IListProps {
	channelId: string;
	groupId: string;
}

const List: React.FC<IListProps> = ({channelId, groupId}) => {
	const currentUser = useCurrentUser();
	const {selectedChannel} = useChannelContext();

	const {data: dataSourceData, loading: dataSourceLoading} = useRequest({
		dataSourceFn: API.dataSource.search,
		variables: {
			delta: 1,
			groupId
		}
	});

	const authorized = currentUser.isAdmin();

	const dataSourceConnected =
		!isNil(dataSourceData?.total) && dataSourceData?.total > 0;

	const NoDataSourcesConnected = () => (
		<NoResultsDisplay
			description={
				<>
					{Liferay.Language.get(
						'connect-a-data-source-to-start-syncing-accounts'
					)}

					{authorized && (
						<>
							<p>
								<Link
									className='d-block mb-3'
									href={URLConstants.DataSourceConnection}
									key='DOCUMENTATION'
									target='_blank'
								>
									{Liferay.Language.get(
										'access-our-documentation-to-learn-more'
									)}
								</Link>
							</p>
							<Link
								button
								className='button-root'
								displayType='primary'
								href={toRoute(
									Routes.SETTINGS_DATA_SOURCE_LIST,
									{
										groupId
									}
								)}
							>
								{Liferay.Language.get('connect-data-source')}
							</Link>
						</>
					)}
				</>
			}
			displayCard
			icon={{
				border: false,
				size: Sizes.XXXLarge,
				symbol: 'ac_satellite'
			}}
			spacer
			title={Liferay.Language.get('no-data-sources-connected')}
		/>
	);

	const FrontendDataSet = useFrontendDataSet();

	if (dataSourceLoading) {
		return <Loading />;
	}

	return (
		<BasePage documentTitle={Liferay.Language.get('accounts')}>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name
					})
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection
						title={Liferay.Language.get('accounts')}
					/>
				</BasePage.Row>
			</BasePage.Header>
			<BasePage.Body>
				<Card>
					{dataSourceConnected && FrontendDataSet ? (
						<FrontendDataSet
							// TODO => Use the correct endpoint
							// apiURL={`o/contacts/${groupId}/account/search`}
							// Use this to test empty states
							// apiURL='/o/headless-admin-taxonomy/v1.0/taxonomy-categories/ranked'
							customDataRenderers={{
								accountLifecycleStageRenderer: ({value}) =>
									frontendDataSetColumns.cmsLabel({
										displayType:
											lifecycleStagesLabelMap[value]
												.displayType,
										label:
											lifecycleStagesLabelMap[value].label
									}),
								lastActiveRenderer: ({value}) => (
									<div>
										{formatUTCDate(
											value,
											CUSTOM_DATE_FORMAT
										)}
									</div>
								),
								testRenderer: ({value}) => (
									<span>
										<b>{value}</b>
									</span>
								)
							}}
							emptyState={{
								description: Liferay.Language.get(
									'no-accounts-were-synced-from-the-connected-data-sources'
								),
								image: '/states/satellite.svg',
								title: Liferay.Language.get('no-accounts-found')
							}}
							filters={[
								{
									id: 'lifecycleStatus',
									items: lifecycleStageFilter.items,
									label: Liferay.Language.get('status'),
									name: 'status',
									type: 'selection'
								},
								{
									id: 'company',
									items: [
										{
											label: Liferay.Language.get(
												'liferay'
											),
											value: 'liferay'
										}
									],
									label: Liferay.Language.get('industry'),
									name: 'companyId',
									type: 'selection'
								}
							]}
							items={mockItems.items}
							loading={dataSourceLoading}
							pagination={pagination}
							showPagination
							snapshotsEnabled
							sort={[
								{
									active: true,
									direction: 'asc',
									key: 'alternateName',
									label: Liferay.Language.get(
										'alternate-name'
									)
								},
								{
									active: false,
									direction: 'asc',
									key: 'emailAddress',
									label: Liferay.Language.get('email')
								}
							]}
							views={[
								{
									contentRenderer: 'table',
									default: false,
									label: 'table',
									name: 'table',
									schema: {
										fields: [
											{
												fieldName: 'accountName',
												label: Liferay.Language.get(
													'account'
												),
												sortable: true,
												truncate: true
											},
											{
												fieldName: 'industry',
												label: Liferay.Language.get(
													'industry'
												),
												sortable: false
											},
											{
												contentRenderer:
													'accountLifecycleStageRenderer',
												fieldName: 'lifecycleStage',
												label: Liferay.Language.get(
													'lifecycle-stage'
												),
												sortable: false
											},
											{
												contentRenderer:
													'lastActiveRenderer',
												fieldName: 'lastActive',
												label: Liferay.Language.get(
													'last-active'
												),
												sortable: true
											}
										]
									},
									thumbnail: 'table'
								}
							]}
						/>
					) : (
						<NoDataSourcesConnected />
					)}
				</Card>
			</BasePage.Body>
		</BasePage>
	);
};

export default List;
