import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {Routes} from 'shared/util/router';
import {Switch, useParams} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';

const Distribution = lazy(
	() =>
		import(
			/* webpackChunkName: "IndividualsDashboardDistribution" */ './Distribution'
		)
);

const KnownIndividuals = lazy(
	() =>
		import(
			/* webpackChunkName: "IndividualsDashboardKnownIndividuals" */ './KnownIndividuals'
		)
);

const InterestDetails = lazy(
	() =>
		import(
			/* webpackChunkName: "IndividualsDashboardInterestDetails" */ './InterestDetails'
		)
);

const Interests = lazy(
	() =>
		import(
			/* webpackChunkName: "IndividualsDashboardInterests" */ './Interests'
		)
);

const Overview = lazy(
	() =>
		import(
			/* webpackChunkName: "IndividualsDashboardOverview" */ './Overview'
		)
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.CONTACTS_INDIVIDUALS
	},
	{
		exact: true,
		label: Liferay.Language.get('known-individuals'),
		route: Routes.CONTACTS_INDIVIDUALS_KNOWN_INDIVIDUALS
	},
	{
		exact: false,
		label: Liferay.Language.get('interests'),
		route: Routes.CONTACTS_INDIVIDUALS_INTERESTS
	},
	{
		exact: true,
		label: Liferay.Language.get('distribution'),
		route: Routes.CONTACTS_INDIVIDUALS_DISTRIBUTION
	}
];

const Dashboard: React.FC<React.HTMLAttributes<HTMLDivElement>> = () => {
	const {selectedChannel} = useChannelContext();
	const {channelId, groupId} = useParams();

	return (
		<BasePage
			className='individuals-dashboard-root'
			documentTitle={Liferay.Language.get('individuals')}
		>
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
					title={Liferay.Language.get('individuals')}
				/>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{channelId, groupId}}
				/>
			</BasePage.Header>

			<Suspense fallback={<Loading />}>
				<Switch>
					<BundleRouter
						data={Overview}
						destructured={false}
						exact
						path={Routes.CONTACTS_INDIVIDUALS}
					/>

					<BundleRouter
						data={KnownIndividuals}
						path={Routes.CONTACTS_INDIVIDUALS_KNOWN_INDIVIDUALS}
					/>

					<BundleRouter
						data={Distribution}
						exact
						path={Routes.CONTACTS_INDIVIDUALS_DISTRIBUTION}
					/>

					<BundleRouter
						data={InterestDetails}
						destructured={false}
						exact
						path={Routes.CONTACTS_INDIVIDUALS_INTEREST_DETAILS}
					/>

					<BundleRouter
						data={Interests}
						destructured={false}
						exact
						path={Routes.CONTACTS_INDIVIDUALS_INTERESTS}
					/>

					<RouteNotFound />
				</Switch>
			</Suspense>
		</BasePage>
	);
};

export default Dashboard;
