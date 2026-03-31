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
import {toThousands} from 'shared/util/numbers';
import {useChannelContext} from 'shared/context/channel';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {useRequest} from 'shared/hooks/useRequest';

const mockItems = {
	items: [
		{
			accountName: 'Acme Corporation',
			annualRevenue: 10000000,
			country: 'Australia',
			id: '001xx000003DGbYAAW',
			industry: 'Manufacturing',
			lastActive: '2026-03-24T15:01:15Z',
			lastEnriched: '2026-03-25T10:00:00Z',
			lifecycleStage: LifecycleStages.ESTABLISHED
		},
		{
			accountName: 'Globex Corporation',
			annualRevenue: 25000000,
			country: 'Canada',
			id: '001xx000003DGbZAAW',
			industry: 'Technology',
			lastActive: '2026-03-20T12:45:30Z',
			lastEnriched: '2026-03-24T10:00:00Z',
			lifecycleStage: LifecycleStages.AWARE
		},
		{
			accountName: 'Initech',
			annualRevenue: 5000000,
			country: 'United States',
			id: '001xx000003DGbXAAW',
			industry: 'Software',
			lastActive: '2026-03-22T09:30:00Z',
			lastEnriched: '2026-03-23T10:00:00Z',
			lifecycleStage: LifecycleStages.AT_RISK
		},
		{
			accountName: 'Umbrella Corporation',
			annualRevenue: 15918240012,
			country: 'United States',
			id: '001xx000003DGbWAAW',
			industry: 'Pharmaceuticals',
			lastActive: '2026-03-18T14:15:45Z',
			lastEnriched: '2026-03-24T10:00:00Z',
			lifecycleStage: LifecycleStages.PIPELINE
		},
		{
			accountName: 'Hooli',
			annualRevenue: 7500000,
			country: 'United States',
			id: '001xx000003DGbVAAW',
			industry: 'Technology',
			lastActive: '2026-03-21T11:00:00Z',
			lastEnriched: '2026-03-22T10:00:00Z',
			lifecycleStage: LifecycleStages.ENGAGED
		},
		{
			accountName: 'Stark Industries',
			annualRevenue: 500000000,
			country: 'United States',
			id: '001xx000003DGbUAAW',
			industry: 'Defense',
			lastActive: '2026-03-19T16:45:30Z',
			lastEnriched: '2026-03-18T10:00:00Z',
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
				{dataSourceConnected && FrontendDataSet ? (
					<Card>
						<FrontendDataSet
							// TODO => Use the correct endpoint
							// apiURL={`o/contacts/${groupId}/account/search`}
							// Use this to test empty states
							// apiURL='/o/headless-admin-taxonomy/v1.0/taxonomy-categories/ranked'
							apiURL='/o/headless-admin-user/v1.0/roles'
							configInURLBehavior='off'
							customDataRenderers={{
								accountLifecycleStageRenderer: ({value}) => {
									value &&
										frontendDataSetColumns.cmsLabel({
											displayType:
												lifecycleStagesLabelMap[value]
													.displayType,
											label:
												lifecycleStagesLabelMap[value]
													.label
										});
								},
								accountNameRenderer: ({itemData, value}) => {
									const itemTitle = value || itemData.id;

									return (
										<Link
											className='font-weight-semi-bold text-dark'
											href={toRoute(
												Routes.CONTACTS_ACCOUNT,
												{
													groupId,
													id: itemData.id
												}
											)}
										>
											{itemTitle}
										</Link>
									);
								},
								annualRevenueRenderer: ({value}) => (
									<div>{toThousands(value)}</div>
								),
								dateRenderer: ({value}) => (
									<div>
										{value &&
											formatUTCDate(
												value,
												CUSTOM_DATE_FORMAT
											)}
									</div>
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
									apiURL: `/o/contacts/${groupId}/account/industries`,
									entityFieldType: 'string',
									id: 'industry',
									itemKey: 'industry',
									itemLabel: 'name',
									label: Liferay.Language.get('industry'),
									multiple: true,
									type: 'selection'
								},
								{
									apiURL: `/o/contacts/${groupId}/account/countries`,
									entityFieldType: 'string',
									id: 'country',
									itemKey: 'country',
									itemLabel: 'name',
									label: Liferay.Language.get('country'),
									multiple: true,
									type: 'selection'
								}
							]}
							// items={mockItems.items}
							id='accounts-list-dataset'
							loading={dataSourceLoading}
							pagination={pagination}
							showPagination
							snapshotsEnabled
							sort={[
								{
									active: true,
									direction: 'asc',
									key: 'accountName',
									label: Liferay.Language.get('account')
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
												contentRenderer:
													'accountNameRenderer',
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
												sortable: true
											},
											{
												contentRenderer:
													'accountLifecycleStageRenderer',
												fieldName: 'lifecycleStage',
												label: Liferay.Language.get(
													'lifecycle-stage'
												),
												sortable: true
											},
											{
												contentRenderer:
													'annualRevenueRenderer',
												fieldName: 'annualRevenue',
												label: Liferay.Language.get(
													'annual-revenue'
												),
												sortable: true
											},
											{
												fieldName: 'country',
												label: Liferay.Language.get(
													'country'
												),
												sortable: true
											},
											{
												contentRenderer: 'dateRenderer',
												fieldName: 'lastActive',
												label: Liferay.Language.get(
													'last-active'
												),
												sortable: true
											},
											{
												contentRenderer: 'dateRenderer',
												fieldName: 'lastEnriched',
												label: Liferay.Language.get(
													'last-enriched'
												),
												sortable: true,
												visible: false
											}
										]
									},
									thumbnail: 'table'
								}
							]}
						/>
					</Card>
				) : (
					<NoDataSourcesConnected />
				)}
			</BasePage.Body>
		</BasePage>
	);
};

export default List;
