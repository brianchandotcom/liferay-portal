import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import DropdownRangeKey from 'shared/hoc/DropdownRangeKey';
import Filter from '../hocs/Filter';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useEffect, useState} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import TextTruncate from 'shared/components/TextTruncate';
import {ENABLE_GLOBAL_FILTER} from 'shared/util/constants';
import {getMatchedRoute, Routes} from 'shared/util/router';
import {getRangeSelectorsFromQuery} from 'shared/util/util';
import {pickBy} from 'lodash';
import {PropTypes} from 'prop-types';
import {Switch} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';

const KnownIndividuals = lazy(() =>
	import(
		/* webpackChunkName: "TouchpointKnownIndividualsPage" */ './KnownIndividuals'
	)
);
const TouchpointOverviewPage = lazy(() =>
	import(
		/* webpackChunkName: "TouchpointOverviewPage" */ './TouchpointOverviewPage'
	)
);
const TouchpointPathPage = lazy(() =>
	import(/* webpackChunkName: "TouchpointPathPage" */ './TouchpointPathPage')
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.SITES_TOUCHPOINTS_OVERVIEW
	},
	{
		exact: true,
		label: Liferay.Language.get('path'),
		route: Routes.SITES_TOUCHPOINTS_PATH
	},
	{
		exact: true,
		label: Liferay.Language.get('known-individuals'),
		route: Routes.SITES_TOUCHPOINTS_KNOWN_INDIVIDUALS
	}
];

function TouchpointRoutes({className, router}) {
	const rangeSelectors = getRangeSelectorsFromQuery(router.query);

	const {channelId, groupId, title, touchpoint} = router.params;

	const [filters, setFilters] = useState({});
	const [pathRangeSelectors, setPathRangeSelectors] = useState(
		rangeSelectors
	);

	const {selectedChannel} = useChannelContext();

	const matchedRoute = getMatchedRoute(NAV_ITEMS);

	const decodedTitle = decodeURIComponent(title);

	const decodedTouchpoint = decodeURIComponent(touchpoint);

	useEffect(() => {
		setPathRangeSelectors(rangeSelectors);
	}, [matchedRoute]);

	return (
		<BasePage
			className={getCN(className)}
			documentTitle={Liferay.Language.get('pages')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name
					}),
					breadcrumbs.getSites({channelId, groupId}),
					breadcrumbs.getPages({channelId, groupId}),
					breadcrumbs.getEntityName({label: decodedTitle})
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					subtitle={
						<TextTruncate title={decodedTouchpoint}>
							<a href={decodedTouchpoint} target='_blank'>
								{decodedTouchpoint}
							</a>
						</TextTruncate>
					}
					title={decodedTitle}
				/>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{
						channelId,
						groupId,
						title,
						touchpoint
					}}
					routeQueries={pickBy({...rangeSelectors})}
				/>
			</BasePage.Header>

			<BasePage.Context.Provider
				value={{
					filters,
					router
				}}
			>
				<BasePage.SubHeader>
					{ENABLE_GLOBAL_FILTER && (
						<Filter onChange={setFilters} router={router} />
					)}

					{matchedRoute === Routes.SITES_TOUCHPOINTS_PATH && (
						<DropdownRangeKey
							legacy={false}
							onChange={setPathRangeSelectors}
							rangeSelectors={pathRangeSelectors}
						/>
					)}
				</BasePage.SubHeader>

				<BasePage.Body>
					<Suspense fallback={<Loading />}>
						<Switch>
							<BundleRouter
								data={TouchpointOverviewPage}
								destructured={false}
								exact
								path={Routes.SITES_TOUCHPOINTS_OVERVIEW}
							/>

							<BundleRouter
								data={KnownIndividuals}
								destructured={false}
								exact
								path={
									Routes.SITES_TOUCHPOINTS_KNOWN_INDIVIDUALS
								}
							/>

							<BundleRouter
								componentProps={{
									pathRangeSelectors
								}}
								data={TouchpointPathPage}
								destructured={false}
								exact
								path={Routes.SITES_TOUCHPOINTS_PATH}
							/>

							<RouteNotFound />
						</Switch>
					</Suspense>
				</BasePage.Body>
			</BasePage.Context.Provider>
		</BasePage>
	);
}

TouchpointRoutes.propTypes = {
	/**
	 * @type {object}
	 * @default undefined
	 */
	router: PropTypes.object,

	/**
	 * @type {string}
	 * @default undefined
	 */
	title: PropTypes.string
};

export default TouchpointRoutes;
