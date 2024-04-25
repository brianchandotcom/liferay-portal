import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import DownloadCSVReport from 'shared/components/download-report/DownloadCSVReport';
import DownloadPDFReport from 'shared/components/download-report/DownloadPDFReport';
import Filter from '../hocs/Filter';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useState} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {CSVType} from 'shared/components/download-report/utils';
import {ENABLE_GLOBAL_FILTER} from 'shared/util/constants';
import {getMatchedRoute, Routes} from 'shared/util/router';
import {pickBy} from 'lodash';
import {Router} from 'shared/types';
import {sub} from 'shared/util/lang';
import {Switch} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';
import {useDataSource} from 'shared/hooks/useDataSource';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

const Overview = lazy(
	() =>
		import(/* webpackChunkName: "DocumentsAndMediaOverview" */ './Overview')
);
const KnownIndividuals = lazy(
	() =>
		import(
			/* webpackChunkName: "DocumentsAndMediaKnownIndividuals" */ './KnownIndividuals'
		)
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.ASSETS_DOCUMENTS_AND_MEDIA_OVERVIEW
	},
	{
		exact: true,
		label: Liferay.Language.get('known-individuals'),
		route: Routes.ASSETS_DOCUMENTS_AND_MEDIA_KNOWN_INDIVIDUALS
	}
];

const DocumentAndMedia: React.FC<{
	className: string;
	router: Router;
}> = ({className, router}) => {
	const {
		params: {assetId, channelId, groupId, title, touchpoint}
	} = router;

	const [filters, setFilters] = useState({});

	const dataSourceStates = useDataSource();

	const decodedTitle = decodeURIComponent(title);

	const rangeSelectorsFromQuery = useQueryRangeSelectors();

	const {selectedChannel} = useChannelContext();

	return (
		<BasePage
			className={getCN(className)}
			documentTitle={Liferay.Language.get('assets')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel?.name
					}),
					breadcrumbs.getAssets({channelId, groupId}),
					breadcrumbs.getDocumentsAndMedia({channelId, groupId}),
					breadcrumbs.getEntityName({label: decodedTitle})
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection title={decodedTitle} />

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{
						assetId,
						channelId,
						groupId,
						title,
						touchpoint
					}}
					routeQueries={pickBy(rangeSelectorsFromQuery)}
				/>
			</BasePage.Header>

			{getMatchedRoute(NAV_ITEMS) ===
				Routes.ASSETS_DOCUMENTS_AND_MEDIA_OVERVIEW && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadPDFReport
							disabled={dataSourceStates.empty}
							subtitle={selectedChannel?.name}
							title={
								sub(Liferay.Language.get('x-dashboard'), [
									decodedTitle
								]) as string
							}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			{getMatchedRoute(NAV_ITEMS) ===
				Routes.ASSETS_DOCUMENTS_AND_MEDIA_KNOWN_INDIVIDUALS && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadCSVReport
							assetId={assetId}
							assetType='document'
							disabled={dataSourceStates.empty}
							type={CSVType.Individual}
							typeLang={Liferay.Language.get('known-individuals')}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			<BasePage.Context.Provider value={{filters, router}}>
				{ENABLE_GLOBAL_FILTER && (
					<BasePage.SubHeader>
						<Filter onChange={setFilters} />
					</BasePage.SubHeader>
				)}

				<BasePage.Body>
					<Suspense fallback={<Loading />}>
						<Switch>
							<BundleRouter
								data={Overview}
								destructured={false}
								exact
								path={
									Routes.ASSETS_DOCUMENTS_AND_MEDIA_OVERVIEW
								}
							/>

							<BundleRouter
								data={KnownIndividuals}
								destructured={false}
								exact
								path={
									Routes.ASSETS_DOCUMENTS_AND_MEDIA_KNOWN_INDIVIDUALS
								}
							/>

							<RouteNotFound />
						</Switch>
					</Suspense>
				</BasePage.Body>
			</BasePage.Context.Provider>
		</BasePage>
	);
};

export default DocumentAndMedia;
