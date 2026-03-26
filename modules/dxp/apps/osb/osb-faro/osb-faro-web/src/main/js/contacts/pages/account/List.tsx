import * as API from 'shared/api';
import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import Link from '@clayui/link';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {isNil} from 'lodash/fp';
import {pagination} from 'shared/util/frontend-data-set';
import {Routes, toRoute} from 'shared/util/router';
import {Sizes} from 'shared/util/constants';
import {useChannelContext} from 'shared/context/channel';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {User} from 'shared/util/records';
import {useRequest} from 'shared/hooks/useRequest';

interface IListProps {
	channelId: string;
	currentUser: User;
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
							apiURL='/o/headless-admin-user/v1.0/user-accounts'
							customDataRenderers={{
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
									items: [
										{
											label: Liferay.Language.get(
												'established'
											),
											value: 'established'
										},
										{
											label: Liferay.Language.get(
												'pipeline'
											),
											value: 'pipeline'
										},
										{
											label: Liferay.Language.get(
												'at-risk'
											),
											value: 'at-risk'
										}
									],
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
												fieldName: 'alternateName',
												label: Liferay.Language.get(
													'alternate-name'
												),
												sortable: true,
												truncate: true
											},
											{
												fieldName: 'familyName',
												label: Liferay.Language.get(
													'family-name'
												),
												sortable: false
											},
											{
												contentRenderer: 'testRenderer',
												fieldName: 'id',
												label: Liferay.Language.get(
													'id'
												),
												sortable: false
											},
											{
												contentRenderer: 'testRenderer',
												fieldName: 'emailAddress',
												label: Liferay.Language.get(
													'email'
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
