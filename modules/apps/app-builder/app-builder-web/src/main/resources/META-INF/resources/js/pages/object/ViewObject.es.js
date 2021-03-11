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

import React from 'react';
import {Route, Switch} from 'react-router-dom';

import ControlMenu from '../../components/control-menu/ControlMenu.es';
import NavigationBar from '../../components/navigation-bar/NavigationBar.es';
import useDataDefinition from '../../hooks/useDataDefinition.es';
import ListApps from '../apps/ListApps.es';
import EditApp from '../apps/edit/EditApp.es';
import EditFormView from '../form-view/EditFormView.es';
import ListFormViews from '../form-view/ListFormViews.es';
import EditTableView from '../table-view/EditTableView.es';
import ListTableViews from '../table-view/ListTableViews.es';

const URL = {
	'custom-object': '/',
	'native-object': '/native-objects',
};

const queryFields = ['availableLanguageIds', 'name', 'defaultLanguageId'].join(
	','
);

export default ({
	match: {
		params: {dataDefinitionId, objectType},
		path,
	},
}) => {
	const {
		availableLanguageIds,
		defaultLanguageId,
		title = '',
	} = useDataDefinition({
		dataDefinitionId,
		defaultState: {
			availableLanguageIds: [],
			defaultLanguageId: '',
			name: {},
		},
		queryFields,
	});

	return (
		<Switch>
			<Route
				component={EditFormView}
				path={[
					`${path}/form-views/add`,
					`${path}/form-views/:dataLayoutId(\\d+)`,
				]}
			/>

			<Route
				component={EditTableView}
				path={[
					`${path}/table-views/add`,
					`${path}/table-views/:dataListViewId(\\d+)`,
				]}
			/>

			<Route
				path={[`${path}/apps/deploy`, `${path}/apps/:appId(\\d+)`]}
				render={(props) => (
					<EditApp
						{...props}
						availableLanguageIds={availableLanguageIds}
						defaultLanguageId={defaultLanguageId}
					/>
				)}
			/>

			<Route
				path={path}
				render={() => (
					<>
						<ControlMenu backURL={URL[objectType]} title={title} />
						<NavigationBar
							tabs={[
								{
									active: true,
									label: Liferay.Language.get('form-views'),
									path: (url) => `${url}/form-views`,
								},
								{
									label: Liferay.Language.get('table-views'),
									path: (url) => `${url}/table-views`,
								},
								{
									label: Liferay.Language.get('apps'),
									path: (url) => `${url}/apps`,
								},
							]}
						/>

						<Switch>
							<Route
								path={`${path}/form-views`}
								render={(props) => (
									<ListFormViews
										{...props}
										defaultLanguageId={defaultLanguageId}
									/>
								)}
							/>

							<Route
								path={`${path}/table-views`}
								render={(props) => (
									<ListTableViews
										{...props}
										defaultLanguageId={defaultLanguageId}
									/>
								)}
							/>

							<Route
								path={`${path}/apps`}
								render={(props) => (
									<ListApps
										{...props}
										defaultLanguageId={defaultLanguageId}
									/>
								)}
							/>
						</Switch>
					</>
				)}
			/>
		</Switch>
	);
};
