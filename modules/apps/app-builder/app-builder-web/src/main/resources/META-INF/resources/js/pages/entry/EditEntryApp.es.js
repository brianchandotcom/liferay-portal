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

import React, {useCallback, useState} from 'react';

import {AppContextProvider} from '../../AppContext.es';
import useLazy from '../../hooks/useLazy.es';
import PermissionTunnel from './PermissionTunnel.es';
import {PermissionsContextProvider} from './PermissionsContext.es';
import PortalEntry, {getStorageLanguageId} from './PortalEntry.es';

export default ({appTab, ...props}) => {
	const EditPage = useLazy(true);
	const {appId, dataDefinitionId} = props;
	const defaultLanguageId = getStorageLanguageId(appId);
	const [userLanguageId, setUserLanguageId] = useState(defaultLanguageId);

	const newProps = {
		...props,
		userLanguageId,
	};

	const formTaglib = document.getElementById('app-entry-taglib');

	const onPermissionChange = useCallback(
		(withPermission) => {
			if (formTaglib) {
				if (withPermission) {
					formTaglib.classList.remove('hide');
				}
				else {
					formTaglib.classList.add('hide');
				}
			}
		},
		[formTaglib]
	);

	return (
		<AppContextProvider {...newProps}>
			<PermissionsContextProvider dataDefinitionId={dataDefinitionId}>
				<PortalEntry
					dataDefinitionId={props.dataDefinitionId}
					setUserLanguageId={setUserLanguageId}
					userLanguageId={userLanguageId}
				/>

				<PermissionTunnel
					onPermissionChange={onPermissionChange}
					permissionType={['add', 'update']}
				>
					<EditPage module={appTab.editEntryPoint} props={newProps} />
				</PermissionTunnel>
			</PermissionsContextProvider>
		</AppContextProvider>
	);
};
