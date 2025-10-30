import BundleRouter from 'route-middleware/BundleRouter';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {DataSource} from 'shared/util/records';
import {Routes} from 'shared/util/router';
import {Switch} from 'react-router-dom';

const SalesforceOverview = lazy(
	() =>
		import(
			/* webpackChunkName: "SalesforceOverview" */ '../../components/salesforce/SalesforceOverview'
		)
);

interface ISalesforceProps {
	dataSource: DataSource;
	groupId: string;
	id: string;
}

const Salesforce: React.FC<ISalesforceProps> = ({dataSource, groupId, id}) => (
	<Suspense fallback={<Loading />}>
		<Switch>
			<BundleRouter
				componentProps={{dataSource, groupId, id}}
				data={SalesforceOverview}
				path={Routes.SETTINGS_DATA_SOURCE}
			/>

			<RouteNotFound />
		</Switch>
	</Suspense>
);

export default Salesforce;
