/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {Fragment} from 'react';
import {Route, Switch} from 'react-router-dom';
import CustomObjectNavigationBar from './CustomObjectNavigationBar.es';
import ListApps from './ListApps.es';
import ListFormViews from './ListFormViews.es';
import ListTableViews from './ListTableViews.es';

export default ({match: {path, url}}) => {
	return (
		<Fragment>
			<CustomObjectNavigationBar url={url} />

			<Switch>
				<Route component={ListFormViews} path={`${path}/form-views`} />

				<Route
					component={ListTableViews}
					path={`${path}/table-views`}
				/>

				<Route component={ListApps} path={`${path}/deployments`} />
			</Switch>
		</Fragment>
	);
};
