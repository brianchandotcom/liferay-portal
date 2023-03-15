import BundleRouter from 'route-middleware/BundleRouter';
import Card from 'shared/components/Card';
import Loading from 'shared/pages/Loading';
import Nav from 'shared/components/Nav';
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
			<div className='d-flex justify-content-between'>
				<Nav className='page-subnav mx-4 my-3' display='underline'>
					{NAV_ITEMS.map(({label, route}) => (
						<Nav.Item
							active={matchedRoute === route}
							href={toRoute(route, {groupId})}
							key={route}
						>
							<div className='mb-2'>
								<b>{label}</b>
							</div>
						</Nav.Item>
					))}
				</Nav>
			</div>

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
