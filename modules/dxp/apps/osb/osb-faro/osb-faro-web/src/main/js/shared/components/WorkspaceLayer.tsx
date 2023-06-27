import BundleRouter from 'route-middleware/BundleRouter';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useEffect} from 'react';
import useModalNotifications from 'shared/hooks/useModalNotifications';
import {close, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {matchPath} from 'react-router';
import {Project} from 'shared/util/records';
import {RootState} from 'shared/store';
import {Routes} from 'shared/util/router';
import {Switch} from 'react-router-dom';
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
		const {
			params: {groupId}
		} = matchPath(pathname, {
			path: Routes.WORKSPACE_WITH_ID
		});

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

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IWorkspaceLayerProps extends PropsFromRedux {}

const WorkspaceLayer: React.FC<IWorkspaceLayerProps> = ({
	close,
	currentUserId,
	groupId,
	open,
	serverLocation,
	subscriptionName,
	workspaceName
}) => {
	useEffect(() => {
		if (groupId !== '0' && workspaceName) {
			analytics?.track(
				'User accessed workspace',
				{
					groupId,
					serverLocation,
					subscriptionName,
					userId: currentUserId,
					workspaceName
				},
				{ip: '0'}
			);
		}
	}, [groupId, workspaceName]);

	useModalNotifications(close, groupId, open);

	return (
		<Suspense fallback={<Loading />}>
			<Switch>
				<BundleRouter data={Settings} path={Routes.SETTINGS} />

				<BundleRouter data={AppSidebarRoutes} path={Routes.CHANNEL} />
			</Switch>
		</Suspense>
	);
};

export default compose(connector, withHelpWidget)(WorkspaceLayer);
