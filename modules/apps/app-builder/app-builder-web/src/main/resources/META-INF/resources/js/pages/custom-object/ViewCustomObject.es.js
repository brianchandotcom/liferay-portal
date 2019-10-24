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

import {Route, Switch} from 'react-router-dom';
import React, {useEffect, useState} from 'react';

import ControlMenu from '../../components/control-menu/ControlMenu.es';
import {getItem} from '../../utils/client.es';
import EditApp from '../app/EditApp.es';
import ListApps from '../app/ListApps.es';
import EditFormView from '../form-view/EditFormView.es';
import ListFormViews from '../form-view/ListFormViews.es';
import EditTableView from '../table-view/EditTableView.es';
import ListTableViews from '../table-view/ListTableViews.es';
import CustomObjectNavigationBar from './CustomObjectNavigationBar.es';

export default ({
	match: {
		params: {dataDefinitionId},
		path
	}
}) => {
	const [title, setTitle] = useState('');

	useEffect(() => {
		getItem(
			`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
		).then(dataDefinition => setTitle(dataDefinition.name.en_US));
	}, [dataDefinitionId]);

	return (
		<Switch>
			<Route
				component={EditFormView}
				path={[
					`${path}/form-views/add`,
					`${path}/form-views/:dataLayoutId(\\d+)`
				]}
			/>

			<Route
				component={EditTableView}
				path={[
					`${path}/table-views/add`,
					`${path}/table-views/:dataListViewId(\\d+)`
				]}
			/>

			<Route
				component={EditApp}
				path={[`${path}/apps/deploy`, `${path}/apps/:appId(\\d+)`]}
			/>

			<Route
				path={path}
				render={() => (
					<>
						<ControlMenu backURL="/" title={title} />
						<CustomObjectNavigationBar />

						<Switch>
							<Route
								component={ListFormViews}
								path={`${path}/form-views`}
							/>

							<Route
								component={ListTableViews}
								path={`${path}/table-views`}
							/>

							<Route component={ListApps} path={`${path}/apps`} />
						</Switch>
					</>
				)}
			/>
		</Switch>
	);
};
