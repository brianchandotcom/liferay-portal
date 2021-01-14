/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {
	ACTIONS,
	PermissionsContext,
} from 'app-builder-web/js/pages/entry/PermissionsContext.es';
import React from 'react';

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
