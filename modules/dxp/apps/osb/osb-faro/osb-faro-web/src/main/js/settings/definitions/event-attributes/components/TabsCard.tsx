import BundleRouter from 'route-middleware/BundleRouter';
import Card from 'shared/components/Card';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import {getMatchedRoute, Routes, toRoute} from 'shared/util/router';
import {Switch} from 'react-router';

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

	return (
		<Card key='cardContainer' pageDisplay>
			<ClayNavigationBar
				className='page-subnav mx-4 my-3'
				triggerLabel={matchedRoute}
			>
				{NAV_ITEMS.map(({label, route}) => (
					<ClayNavigationBar.Item
						active={matchedRoute === route}
						key={route}
					>
						<ClayLink href={toRoute(route, {groupId})}>
							{label}
						</ClayLink>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>

			<Suspense fallback={<Loading />}>
				<Switch>
					<BundleRouter
						data={AttributeList}
						exact
						path={
							Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_LOCAL
						}
					/>

					<BundleRouter
						data={GlobalAttributeList}
						exact
						path={
							Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL
						}
					/>
				</Switch>
			</Suspense>
		</Card>
	);
};
export default TabsCard;
