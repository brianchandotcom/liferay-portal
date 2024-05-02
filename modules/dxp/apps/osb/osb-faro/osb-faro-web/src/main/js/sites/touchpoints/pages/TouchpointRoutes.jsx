import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import ClayLink from '@clayui/link';
import DownloadCSVReport from 'shared/components/download-report/DownloadCSVReport';
import DownloadPDFReport from 'shared/components/download-report/DownloadPDFReport';
import FilterBySegment from '../components/FilterBySegment';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useEffect, useState} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import TextTruncate from 'shared/components/TextTruncate';
import {CSVType} from 'shared/components/download-report/utils';
import {DropdownRangeKey} from 'shared/components/dropdown-range-key/DropdownRangeKey';
import {getMatchedRoute, Routes} from 'shared/util/router';
import {pickBy} from 'lodash';
import {PropTypes} from 'prop-types';
import {Switch} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';
import {useDataSource} from 'shared/hooks/useDataSource';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

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
	import(/* webpackChunkName: "TouchpointPathPage" */ './PagePath')
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
	const dataSourceStates = useDataSource();
	const rangeSelectors = useQueryRangeSelectors();
	const {channelId, groupId, title, touchpoint} = router.params;
	const [pathRangeSelectors, setPathRangeSelectors] = useState(
		rangeSelectors
	);
	const {selectedChannel} = useChannelContext();
	const matchedRoute = getMatchedRoute(NAV_ITEMS);
	const decodedTitle = decodeURIComponent(title);
	const decodedTouchpoint = decodeURIComponent(touchpoint);
	const [selectedSegment, setSelectedSegment] = useState({});

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
							<ClayLink href={decodedTouchpoint} target='_blank'>
								{/* It should have double decode for cases when there are special characters */}

								{decodeURIComponent(decodedTouchpoint)}
							</ClayLink>
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

			{matchedRoute === Routes.SITES_TOUCHPOINTS_OVERVIEW && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadPDFReport
							disabled={dataSourceStates.empty}
							subtitle={`${
								selectedChannel.name
							} | ${Liferay.Language.get('page-dashboard')}`}
							title={decodedTitle}
							url={decodedTouchpoint}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			{matchedRoute === Routes.SITES_TOUCHPOINTS_KNOWN_INDIVIDUALS && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadCSVReport
							assetId={decodedTouchpoint}
							assetType='page'
							disabled={dataSourceStates.empty}
							type={CSVType.Individual}
							typeLang={Liferay.Language.get('known-individuals')}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			<BasePage.Context.Provider
				value={{
					filters: {},
					router
				}}
			>
				{matchedRoute === Routes.SITES_TOUCHPOINTS_PATH && (
					<BasePage.SubHeader>
						<FilterBySegment
							onFilterChange={setSelectedSegment}
							rangeSelectors={pathRangeSelectors}
						/>

						<DropdownRangeKey
							legacy={false}
							onRangeSelectorChange={setPathRangeSelectors}
							rangeSelectors={pathRangeSelectors}
						/>
					</BasePage.SubHeader>
				)}

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
									rangeSelectors: pathRangeSelectors,
									selectedSegment
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
