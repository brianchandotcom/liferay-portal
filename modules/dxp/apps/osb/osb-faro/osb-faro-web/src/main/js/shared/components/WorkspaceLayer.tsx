import BundleRouter from 'route-middleware/BundleRouter';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from './RouteNotFound';
import {close, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect} from 'react-redux';
import {matchPath} from 'react-router';
import {Project} from 'shared/util/records';
import {RootState} from 'shared/store';
import {Routes} from 'shared/util/router';
import {Switch} from 'react-router-dom';
import {useModalNotifications} from 'shared/hooks/useModalNotifications';
import {withHelpWidget} from 'shared/hoc';

// App Routes with Sidebar
const AppSidebarRoutes = lazy(
	() =>
		import(
			/* webpackChunkName: "AppSidebarRoutes" */ 'shared/pages/AppSidebarRoutes'
		)
);

// Settings
const Settings = lazy(
	() => import(/* webpackChunkName: "Settings" */ 'settings/pages/Settings')
);

const connector = connect(
	(store: RootState, {location: {pathname}}: {location: Location}) => {
		const path = matchPath<any>(pathname, {
			path: Routes.WORKSPACE_WITH_ID
		});

		const groupId = path?.params?.groupId ?? '0';

		const project =
			store.getIn(['projects', groupId, 'data'], new Project()) ||
			new Project();

		const faroSubscriptionIMap = project.get('faroSubscription');

		return {
			currentUserId: String(store.getIn(['currentUser', 'data'])),
			groupId,
			serverLocation: project.get('serverLocation'),
			subscriptionName: faroSubscriptionIMap.get('name'),
			workspaceName: project.get('name')
		};
	},
	{close, open}
);

const WorkspaceLayer = ({close, groupId, open}) => {
	useModalNotifications(close, groupId, open);

	return (
		<Suspense fallback={<Loading />}>
			<Switch>
				<BundleRouter data={Settings} path={Routes.SETTINGS} />

				<BundleRouter data={AppSidebarRoutes} path={Routes.CHANNEL} />

				<RouteNotFound />
			</Switch>
		</Suspense>
	);
};

export default compose<any>(connector, withHelpWidget)(WorkspaceLayer);
