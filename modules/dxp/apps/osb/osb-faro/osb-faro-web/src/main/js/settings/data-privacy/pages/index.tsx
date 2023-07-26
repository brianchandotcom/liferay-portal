import BundleRouter from 'route-middleware/BundleRouter';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {Routes} from 'shared/util/router';
import {Switch} from 'react-router-dom';

const Overview = lazy(
	() => import(/* webpackChunkName: "DataPrivacyOverview" */ './Overview')
);

const RequestLog = lazy(
	() => import(/* webpackChunkName: "RequestLog" */ './RequestLog')
);

const SuppressedUsers = lazy(
	() => import(/* webpackChunkName: "SupressedUsers" */ './SuppressedUsers')
);

interface IDataPrivacyProps extends React.HTMLAttributes<HTMLDivElement> {
	groupId: string;
}

const DataPrivacy: React.FC<IDataPrivacyProps> = ({groupId}) => (
	<Suspense fallback={<Loading />}>
		<Switch>
			<BundleRouter
				data={Overview}
				exact
				path={Routes.SETTINGS_DATA_PRIVACY}
			/>

			<BundleRouter
				componentProps={{groupId}}
				data={SuppressedUsers}
				destructured={false}
				exact
				path={Routes.SETTINGS_DATA_PRIVACY_SUPPRESSED_USERS}
			/>

			<BundleRouter
				componentProps={{groupId}}
				data={RequestLog}
				destructured={false}
				path={Routes.SETTINGS_DATA_PRIVACY_REQUEST_LOG}
			/>

			<RouteNotFound />
		</Switch>
	</Suspense>
);

export default DataPrivacy;
