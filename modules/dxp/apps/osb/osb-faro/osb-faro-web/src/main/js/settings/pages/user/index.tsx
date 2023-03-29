import * as API from 'shared/api';
import BasePage from 'settings/components/BasePage';
import BundleRouter from 'route-middleware/BundleRouter';
import Card from 'shared/components/Card';
import ClayBadge from '@clayui/badge';
import getCN from 'classnames';
import Loading from 'shared/pages/Loading';
import Nav from 'shared/components/Nav';
import React, {lazy, Suspense, useState} from 'react';
import {compose, withCurrentUser} from 'shared/hoc';
import {getMatchedRoute, Routes, toRoute} from 'shared/util/router';
import {Switch, withRouter} from 'react-router-dom';
import {User as UserRecord} from 'shared/util/records';
import {UserStatuses} from 'shared/util/constants';

const UserList = lazy(
	() => import(/* webpackChunkName: "UserManagement" */ './UserList')
);
const UserRequest = lazy(
	() => import(/* webpackChunkName: "UserRequest" */ './UserRequest')
);

interface IUserProps extends React.HTMLAttributes<HTMLElement> {
	currentUser: UserRecord;
	groupId: string;
}

export const User: React.FC<IUserProps> = ({
	className,
	currentUser,
	groupId
}) => {
	const [userRequest, setUserRequest] = useState<number>(0);

	const onSetUserRequest = userRequest => setUserRequest(userRequest);

	API.user
		.fetchCount({
			groupId,
			statuses: [UserStatuses.Requested]
		})
		.then(setUserRequest);

	const NAV_ITEMS = [
		{
			exact: true,
			label: Liferay.Language.get('manage-users'),
			route: Routes.SETTINGS_USERS
		},
		{
			exact: true,
			label: (
				<>
					{Liferay.Language.get('requests')}
					{userRequest > 0 && (
						<ClayBadge className='ml-2' label={userRequest} />
					)}
				</>
			),
			route: Routes.SETTINGS_USERS_REQUESTS
		}
	];

	const matchedRoute = getMatchedRoute(NAV_ITEMS);

	return (
		<BasePage
			className={getCN('user-list-page-root', className)}
			groupId={groupId}
			key='userListPage'
			pageDescription={Liferay.Language.get(
				'invite-new-users-to-analytics-cloud-and-or-configure-existing-users'
			)}
			pageTitle={Liferay.Language.get('user-management')}
		>
			<Card key='cardContainer' pageDisplay>
				{currentUser.isAdmin() && (
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
				)}

				<Suspense fallback={<Loading />}>
					<Switch>
						<BundleRouter
							componentProps={{currentUser}}
							data={UserList}
							exact
							path={Routes.SETTINGS_USERS}
						/>

						<BundleRouter
							componentProps={{onSetUserRequest}}
							data={UserRequest}
							exact
							path={Routes.SETTINGS_USERS_REQUESTS}
						/>
					</Switch>
				</Suspense>
			</Card>
		</BasePage>
	);
};

export default compose<any>(withRouter, withCurrentUser)(User);
