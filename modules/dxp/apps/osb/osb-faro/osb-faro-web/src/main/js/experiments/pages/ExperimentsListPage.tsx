import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import ClayLink from '@clayui/link';
import ExperimentListCard from '../hocs/ExperimentListCard';
import React from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import URLConstants from 'shared/util/url-constants';
import {
	createOrderIOMap,
	getGraphQLVariablesFromPagination,
	MODIFIED_DATE
} from 'shared/util/pagination';
import {EXPERIMENT_LIST_QUERY} from '../queries/ExperimentQuery';
import {get} from 'lodash';
import {Routes, toRoute} from 'shared/util/router';
import {useChannelContext} from 'shared/context/channel';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDataSource} from 'shared/hooks/useDataSource';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {useQueryPagination} from 'shared/hooks/useQueryPagination';
import {useTimeZone} from 'shared/hooks/useTimeZone';

const ExperimentsListPage = () => {
	const {channelId, groupId} = useParams();
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(MODIFIED_DATE)
	});
	const dataSourceStates = useDataSource();
	const {selectedChannel} = useChannelContext();
	const currentUser = useCurrentUser();
	const {timeZoneId} = useTimeZone();

	const {data = {}, error, loading} = useQuery(EXPERIMENT_LIST_QUERY, {
		fetchPolicy: 'network-only',
		variables: {
			...getGraphQLVariablesFromPagination({
				delta,
				orderIOMap,
				page,
				query
			}),
			channelId
		}
	});

	const authorized = currentUser.isAdmin();

	return (
		<BasePage documentTitle={Liferay.Language.get('tests')}>
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
				<BasePage.Header.TitleSection
					title={Liferay.Language.get('tests')}
				/>
			</BasePage.Header>

			<BasePage.Body>
				<StatesRenderer {...dataSourceStates}>
					<StatesRenderer.Empty
						description={
							<>
								{authorized
									? Liferay.Language.get(
											'connect-a-data-source-with-sites-data'
									  )
									: Liferay.Language.get(
											'please-contact-your-workspace-administrator-to-add-data-sources'
									  )}

								<ClayLink
									className='d-block mb-3'
									href={URLConstants.DataSourceConnection}
									key='DOCUMENTATION'
									target='_blank'
								>
									{Liferay.Language.get(
										'access-our-documentation-to-learn-more'
									)}
								</ClayLink>

								{authorized && (
									<ClayLink
										button
										className='button-root'
										displayType='primary'
										href={toRoute(
											Routes.SETTINGS_ADD_DATA_SOURCE,
											{
												groupId
											}
										)}
									>
										{Liferay.Language.get(
											'connect-data-source'
										)}
									</ClayLink>
								)}
							</>
						}
						displayCard
						title={Liferay.Language.get(
							'no-sites-synced-from-data-sources'
						)}
					/>

					<StatesRenderer.Success>
						<ExperimentListCard
							{...get(data, 'experiments', {})}
							delta={delta}
							error={error}
							loading={loading}
							orderIOMap={orderIOMap}
							page={page}
							query={query}
							timeZoneId={timeZoneId}
						/>
					</StatesRenderer.Success>
				</StatesRenderer>
			</BasePage.Body>
		</BasePage>
	);
};

export default ExperimentsListPage;
