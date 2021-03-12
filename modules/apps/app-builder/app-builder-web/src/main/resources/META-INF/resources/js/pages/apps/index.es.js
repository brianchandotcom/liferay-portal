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

import {ClayModalProvider} from '@clayui/modal';
import React from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from '../../AppContext.es';
import useDataDefinition from '../../hooks/useDataDefinition.es';
import useLazy from '../../hooks/useLazy.es';
import ListAppsTabs from './ListAppsTabs.es';

const DataDefinitionWrapper = ({children, dataDefinitionId}) => {
	const {availableLanguageIds, defaultLanguageId} = useDataDefinition({
		dataDefinitionId,
		defaultState: {
			availableLanguageIds: [],
			defaultLanguageId: '',
		},
	});

	return children({availableLanguageIds, defaultLanguageId});
};

export default (props) => {
	const {appsTabs} = props;

	const appsTabsKeys = Object.keys(appsTabs);
	const EditPage = useLazy();

	const appProps = {appsTabsKeys, ...props};

	const editRoutes = appsTabsKeys.map((scope) => {
		appsTabs[scope] = {
			...appsTabs[scope],
			editPath: [
				`/${scope}/:dataDefinitionId(\\d+)?/deploy`,
				`/${scope}/:dataDefinitionId(\\d+)?/:appId(\\d+)`,
			],
			scope,
		};

		const {editEntryPoint, editPath} = appsTabs[scope];

		return {
			component: (props) => (
				<DataDefinitionWrapper
					dataDefinitionId={props.match.params.dataDefinitionId}
				>
					{({availableLanguageIds, defaultLanguageId}) => (
						<EditPage
							module={editEntryPoint}
							props={{
								availableLanguageIds,
								defaultLanguageId,
								scope,
								...props,
							}}
						/>
					)}
				</DataDefinitionWrapper>
			),
			path: editPath,
		};
	});

	return (
		<div className="app-builder-root">
			<AppContextProvider {...appProps}>
				<ClayModalProvider>
					<Router>
						<Switch>
							{editRoutes.map((route, index) => (
								<Route key={index} {...route} />
							))}

							<Route component={ListAppsTabs} path="/:tab?" />
						</Switch>
					</Router>
				</ClayModalProvider>
			</AppContextProvider>
		</div>
	);
};
