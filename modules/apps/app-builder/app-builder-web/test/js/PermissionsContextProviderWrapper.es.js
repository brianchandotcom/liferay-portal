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

import {
	ACTIONS,
	PermissionsContext,
} from '../../src/main/resources/META-INF/resources/js/pages/entry/PermissionsContext.es';

const defaultActionIds = [
	ACTIONS.ADD_DATA_RECORD,
	ACTIONS.DELETE_DATA_RECORD,
	ACTIONS.UPDATE_DATA_RECORD,
	ACTIONS.VIEW,
	ACTIONS.VIEW_DATA_RECORD,
];

export default function PermissionsContextProviderWrapper({
	children,
	actionIds = defaultActionIds,
}) {
	const state = {
		actionIds,
		isLoading: false,
	};

	return (
		<PermissionsContext.Provider value={state}>
			{children}
		</PermissionsContext.Provider>
	);
}
