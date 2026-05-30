import BundleRouter from 'route-middleware/BundleRouter';
import Card from 'shared/components/Card';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import ErrorPage from 'shared/pages/ErrorPage';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useState} from 'react';
import {getMatchedRoute, Routes, toRoute} from 'shared/util/router';
import {Route, Routes as RouterRoutes} from 'react-router-dom';

const AttributeList = lazy(
	() => import(/* webpackChunkName: "AttributeList" */ './AttributeList')
);

const GlobalAttributeList = lazy(
	() =>
		import(
			/* webpackChunkName: "GlobalAttributeList" */ './GlobalAttributeList'
		)
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('global-attributes'),
		route: Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL
	},
	{
		exact: true,
		label: Liferay.Language.get('attributes'),
		route: Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_LOCAL
	}
];

interface ITabsCardProps {
	groupId: string;
}

const TabsCard: React.FC<ITabsCardProps> = ({groupId}) => {
	const matchedRoute = getMatchedRoute(NAV_ITEMS);

	const initialItem =
		NAV_ITEMS.find(item => item.route === matchedRoute) ?? NAV_ITEMS[0];

	const [activeLabel, setActiveLabel] = useState(initialItem.label);

	return (
		<Card key='cardContainer' pageDisplay>
			<ClayNavigationBar
				className='page-subnav mx-4 my-3'
				triggerLabel={activeLabel}
			>
				{NAV_ITEMS.map(({label, route}) => (
					<ClayNavigationBar.Item
						active={matchedRoute === route}
						key={route}
					>
						<ClayLink
							href={toRoute(route, {groupId})}
							onClick={() => setActiveLabel(label)}
						>
							{label}
						</ClayLink>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>

			<Suspense fallback={<Loading />}>
				<RouterRoutes>
					<Route
						element={<BundleRouter data={AttributeList} />}
						path='local'
					/>

					<Route
						element={<BundleRouter data={GlobalAttributeList} />}
						path='global'
					/>

					<Route element={<ErrorPage />} path='*' />
				</RouterRoutes>
			</Suspense>
		</Card>
	);
};
export default TabsCard;
