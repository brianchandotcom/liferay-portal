import BundleRouter from 'route-middleware/BundleRouter';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import Loading from 'shared/pages/Loading';
import Nav from 'shared/components/Nav';
import React, {lazy, Suspense} from 'react';
import {getMatchedRoute, Routes, toRoute} from 'shared/util/router';
import {Switch} from 'react-router';

const CustomEventList = lazy(
	() => import(/* webpackChunkName: "CustomEventList" */ './CustomEventList')
);

const EventList = lazy(
	() => import(/* webpackChunkName: "EventList" */ './EventList')
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('default-events'),
		route: Routes.SETTINGS_DEFINITIONS_EVENTS_DEFAULT
	},
	{
		exact: true,
		label: Liferay.Language.get('custom-events'),
		route: Routes.SETTINGS_DEFINITIONS_EVENTS_CUSTOM
	}
];

interface ITabsCardProps {
	groupId: string;
}

const TabsCard: React.FC<ITabsCardProps> = ({groupId}) => {
	const matchedRoute = getMatchedRoute(NAV_ITEMS);

	const customEventTab =
		matchedRoute === Routes.SETTINGS_DEFINITIONS_EVENTS_CUSTOM;

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

				{customEventTab && (
					<div className='d-flex align-items-center mx-4'>
						<ClayLink
							borderless
							button
							className='button-root'
							displayType='secondary'
							href={toRoute(
								Routes.SETTINGS_DEFINITIONS_EVENTS_BLOCK_LIST,
								{groupId}
							)}
							small
						>
							<ClayIcon
								className='icon-root mr-2'
								symbol='ac-block'
							/>

							{Liferay.Language.get('block-list')}
						</ClayLink>
					</div>
				)}
			</div>

			<Suspense fallback={<Loading />}>
				<Switch>
					<BundleRouter
						data={EventList}
						exact
						path={Routes.SETTINGS_DEFINITIONS_EVENTS_DEFAULT}
					/>

					<BundleRouter
						data={CustomEventList}
						exact
						path={Routes.SETTINGS_DEFINITIONS_EVENTS_CUSTOM}
					/>
				</Switch>
			</Suspense>
		</Card>
	);
};

export default TabsCard;
