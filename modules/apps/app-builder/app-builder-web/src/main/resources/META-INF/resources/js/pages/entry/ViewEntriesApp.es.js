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

import React, {useState} from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from '../../AppContext.es';
import useLazy from '../../hooks/useLazy.es';
import {PermissionsContextProvider} from './PermissionsContext.es';
import TranslationManagerWrapper, {
	getStorageLocale,
} from './TranslationManagerWrapper.es';

export default function ({appTab, ...props}) {
	const PageComponent = useLazy();
	const defaultLanguageId = getStorageLocale(props.appId);
	const [userLanguageId, setUserLanguageId] = useState(defaultLanguageId);

	props.userLanguageId = userLanguageId;

	const ListPage = (props) => (
		<PageComponent module={appTab.listEntryPoint} props={props} />
	);

	const ViewPage = (props) => (
		<PageComponent module={appTab.viewEntryPoint} props={props} />
	);

	return (
		<div className="app-builder-root">
			<AppContextProvider {...props}>
				<TranslationManagerWrapper
					dataDefinitionId={props.dataDefinitionId}
					setUserLanguageId={setUserLanguageId}
					userLanguageId={userLanguageId}
				/>
				<PermissionsContextProvider
					dataDefinitionId={props.dataDefinitionId}
				>
					<Router>
						<Switch>
							<Route component={ListPage} exact path="/" />
							<Route
								component={ViewPage}
								path="/entries/:entryIndex(\d+)"
							/>
						</Switch>
					</Router>
				</PermissionsContextProvider>
			</AppContextProvider>
		</div>
	);
}
