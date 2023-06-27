import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import Filter from '../hocs/Filter';
import getCN from 'classnames';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useState} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {ENABLE_GLOBAL_FILTER} from 'shared/util/constants';
import {getRangeSelectorsFromQuery} from 'shared/util/util';
import {pickBy} from 'lodash';
import {Router} from 'shared/types';
import {Routes} from 'shared/util/router';
import {Switch} from 'react-router-dom';
import {useChannelContext} from 'shared/context/channel';

const Overview = lazy(
	() => import(/* webpackChunkName: "FormsOverview" */ './Overview')
);
const KnownIndividuals = lazy(
	() =>
		import(
			/* webpackChunkName: "FormsKnownIndividuals" */ './KnownIndividuals'
		)
);

const Form: React.FC<{
	className: string;
	router: Router;
}> = ({className, router}) => {
	const {
		params: {assetId, channelId, groupId, title, touchpoint},
		query
	} = router;

	const [filters, setFilters] = useState({});

	const decodedTitle = decodeURIComponent(title);

	const rangeSelectorsFromQuery = getRangeSelectorsFromQuery(query);

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
					breadcrumbs.getForms({channelId, groupId}),
					breadcrumbs.getEntityName({label: decodedTitle})
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection title={decodedTitle} />

				<BasePage.Header.NavBar
					items={[
						{
							exact: true,
							label: Liferay.Language.get('overview'),
							route: Routes.ASSETS_FORMS_OVERVIEW
						},
						{
							exact: true,
							label: Liferay.Language.get('known-individuals'),
							route: Routes.ASSETS_FORMS_KNOWN_INDIVIDUALS
						}
					]}
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
								path={Routes.ASSETS_FORMS_OVERVIEW}
							/>

							<BundleRouter
								data={KnownIndividuals}
								destructured={false}
								exact
								path={Routes.ASSETS_FORMS_KNOWN_INDIVIDUALS}
							/>

							<RouteNotFound />
						</Switch>
					</Suspense>
				</BasePage.Body>
			</BasePage.Context.Provider>
		</BasePage>
	);
};

export default Form;
