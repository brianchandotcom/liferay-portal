import Authorization from './Authorization';
import ConfigurationStatus from './ConfigurationStatus';
import React from 'react';
import TabRoutes from 'settings/components/data-source/TabRoutes';
import {Routes} from 'shared/util/router';
import {withCurrentUser} from 'shared/hoc';

const TAB_ROUTES = [
	{
		component: withCurrentUser(Authorization),
		path: Routes.SETTINGS_DATA_SOURCE
	},
	{
		component: ConfigurationStatus,
		path: Routes.SETTINGS_SALESFORCE_CONFIGURATION_STATUS
	}
];

export default props => <TabRoutes {...props} routes={TAB_ROUTES} />;
