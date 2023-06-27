import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import URLConstants from 'shared/util/url-constants';
import {Routes, toRoute} from 'shared/util/router';
import {Switch, useParams} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';
import {useDataSource} from 'shared/hooks/useDataSource';
import {User} from 'shared/util/records';
import {withCurrentUser} from 'shared/hoc';

const InterestDetails = lazy(
	() =>
		import(
			/* webpackChunkName: "SitesDashboardInterestDetails" */ './InterestDetails'
		)
);
const Interests = lazy(
	() =>
		import(/* webpackChunkName: "SitesDashboardInterests" */ './Interests')
);
const Overview = lazy(
	() => import(/* webpackChunkName: "SitesDashboardOverview" */ './Overview')
);
const Touchpoints = lazy(
	() =>
		import(
			/* webpackChunkName: "SitesDashboardTouchpoints" */ './Touchpoints'
		)
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.SITES
	},
	{
		exact: true,
		label: Liferay.Language.get('pages'),
		route: Routes.SITES_TOUCHPOINTS
	},
	{
		exact: false,
		label: Liferay.Language.get('interests'),
		route: Routes.SITES_INTERESTS
	}
];

type RouterParams = {
	channelId: string;
	groupId: string;
};

type Router = {
	params: RouterParams;
	query: object;
};

interface IDashboardProps extends React.HTMLAttributes<HTMLDivElement> {
	currentUser: User;
	router: Router;
}

export const Dashboard: React.FC<IDashboardProps> = ({currentUser, router}) => {
	const {channelId, groupId} = useParams();
	const dataSourceStates = useDataSource();
	const {selectedChannel} = useChannelContext();

	const authorized = currentUser.isAdmin();

	const selectedChannelName = selectedChannel && selectedChannel.name;

	return (
		<BasePage
			className='sites-dashboard-root'
			documentTitle={Liferay.Language.get('sites')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannelName
					})
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					className={getCN({'no-sites-connected': !selectedChannel})}
					title={
						selectedChannel
							? Liferay.Language.get('sites')
							: Liferay.Language.get('no-sites-connected')
					}
				/>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{channelId, groupId}}
				/>
			</BasePage.Header>

			<BasePage.Context.Provider
				value={{
					filters: {},
					router
				}}
			>
				<BasePage.Body>
					<Suspense fallback={<Loading center />}>
						<StatesRenderer {...dataSourceStates}>
							<StatesRenderer.Empty
								description={
									<>
										{Liferay.Language.get(
											'connect-a-data-source-with-sites-data'
										)}

										<a
											className='d-block mb-3'
											href={
												URLConstants.DataSourceConnection
											}
											key='DOCUMENTATION'
											target='_blank'
										>
											{Liferay.Language.get(
												'access-our-documentation-to-learn-more'
											)}
										</a>

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
								<Switch>
									<BundleRouter
										data={InterestDetails}
										destructured={false}
										exact
										path={Routes.SITES_INTEREST_DETAILS}
									/>

									<BundleRouter
										data={Interests}
										destructured={false}
										exact
										path={Routes.SITES_INTERESTS}
									/>

									<BundleRouter
										data={Touchpoints}
										destructured={false}
										exact
										path={Routes.SITES_TOUCHPOINTS}
									/>

									<BundleRouter
										componentProps={{
											channelName: selectedChannelName
										}}
										data={Overview}
										destructured={false}
										exact
										path={Routes.SITES}
									/>

									<RouteNotFound />
								</Switch>
							</StatesRenderer.Success>
						</StatesRenderer>
					</Suspense>
				</BasePage.Body>
			</BasePage.Context.Provider>
		</BasePage>
	);
};

export default withCurrentUser(Dashboard);
