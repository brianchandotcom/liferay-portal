import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import DownloadPDFReport from 'shared/components/download-report/DownloadPDFReport';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {CSVType} from 'shared/components/download-report/utils';
import {DownloadStaticCSVReport} from 'shared/components/download-report/DownloadStaticCSVReport';
import {getMatchedRoute, Routes} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {Switch, useParams} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';
import {useDataSource} from 'shared/hooks/useDataSource';

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
	const dataSourceStates = useDataSource();
	const {selectedChannel} = useChannelContext();
	const {channelId, groupId} = useParams();
	const matchedRoute = getMatchedRoute(NAV_ITEMS);

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

			{matchedRoute === Routes.CONTACTS_INDIVIDUALS && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadPDFReport
							dateRangeDescription={
								sub(
									Liferay.Language.get(
										'only-select-a-date-range-if-you-want-to-modify-the-current-date-filter-for-the-x-report'
									),
									[Liferay.Language.get('active-individuals')]
								) as string
							}
							disabled={dataSourceStates.empty}
							subtitle={selectedChannel?.name}
							title={Liferay.Language.get(
								'individuals-dashboard'
							)}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			{matchedRoute === Routes.CONTACTS_INDIVIDUALS_KNOWN_INDIVIDUALS && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadStaticCSVReport
							disabled={dataSourceStates.empty}
							type={CSVType.Individual}
							typeLang={Liferay.Language.get('individuals')}
						/>
					</div>
				</BasePage.SubHeader>
			)}

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
