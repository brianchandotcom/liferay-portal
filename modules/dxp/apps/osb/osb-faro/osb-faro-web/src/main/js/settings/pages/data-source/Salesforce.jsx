import BundleRouter from 'route-middleware/BundleRouter';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import {DataSource} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes} from 'shared/util/router';
import {Switch} from 'react-router-dom';

const TabRoutes = lazy(() =>
	import(
		/* webpackChunkName: "SalesforceTabRoutes" */ './salesforce/TabRoutes'
	)
);
const SalesforceAccountFieldMapping = lazy(() =>
	import(
		/* webpackChunkName: "SalesforceAccountFieldMapping" */ './salesforce/AccountFieldMapping'
	)
);
const SalesforceIndividualFieldMapping = lazy(() =>
	import(
		/* webpackChunkName: "SalesforceIndividualFieldMapping" */ './salesforce/IndividualFieldMapping'
	)
);

export default class Salesforce extends React.Component {
	static propTypes = {
		dataSource: PropTypes.instanceOf(DataSource),
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string
	};

	render() {
		const {dataSource, groupId, id} = this.props;

		return (
			<Suspense fallback={<Loading />}>
				<Switch>
					<BundleRouter
						data={SalesforceAccountFieldMapping}
						exact
						path={Routes.SETTINGS_SALESFORCE_FIELD_MAPPING_ACCOUNTS}
					/>

					<BundleRouter
						data={SalesforceIndividualFieldMapping}
						exact
						path={
							Routes.SETTINGS_SALESFORCE_FIELD_MAPPING_INDIVIDUALS
						}
					/>

					<BundleRouter
						componentProps={{dataSource, groupId, id}}
						data={TabRoutes}
						path={Routes.SETTINGS_DATA_SOURCE}
					/>
				</Switch>
			</Suspense>
		);
	}
}
