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

import React, {useEffect, useState} from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from '../../AppContext.es';
import useLazy from '../../hooks/useLazy.es';
import PermissionTunnel from './PermissionTunnel.es';
import {PermissionsContextProvider} from './PermissionsContext.es';
import PortalEntry, {getStorageLanguageId} from './PortalEntry.es';

export default function ({appTab, ...props}) {
	const PageComponent = useLazy();
	const {appId, dataDefinitionId} = props;
	const defaultLanguageId = getStorageLanguageId(appId);
	const [userLanguageId, setUserLanguageId] = useState(defaultLanguageId);
	const [showAppName, setShowAppName] = useState(false);

	const newProps = {
		...props,
		userLanguageId,
	};

	const ListPage = (props) => {
		useEffect(() => {
			setShowAppName(true);
		}, []);

		return <PageComponent module={appTab.listEntryPoint} props={props} />;
	};

	const ViewPage = (props) => {
		useEffect(() => {
			setShowAppName(false);
		}, []);

		return <PageComponent module={appTab.viewEntryPoint} props={props} />;
	};

	return (
		<div className="app-builder-root">
			<AppContextProvider {...newProps}>
				<PermissionsContextProvider dataDefinitionId={dataDefinitionId}>
					<PortalEntry
						dataDefinitionId={dataDefinitionId}
						setUserLanguageId={setUserLanguageId}
						showAppName={showAppName}
						userLanguageId={userLanguageId}
					/>
					<PermissionTunnel permissionType="view">
						<Router>
							<Switch>
								<Route component={ListPage} exact path="/" />
								<Route
									component={ViewPage}
									path="/entries/:entryIndex(\d+)"
								/>
							</Switch>
						</Router>
					</PermissionTunnel>
				</PermissionsContextProvider>
			</AppContextProvider>
		</div>
	);
}
