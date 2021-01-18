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

import {LoadingComponent} from '../../components/loading/Loading.es';
import usePermissions from '../../hooks/usePermissions.es';
import NoPermissionEntry from './NoPermissionEntry.es';

export default function PermissionTunnel({children, permissionType}) {
	const {isLoading, ...permissions} = usePermissions();
	const withPermission = Array.isArray(permissionType)
		? permissionType.some((key) => permissions[key])
		: permissions[permissionType];

	if (isLoading) {
		return <LoadingComponent />;
	}
	else {
		if (withPermission) {
			return children;
		}

		return <NoPermissionEntry />;
	}
}
