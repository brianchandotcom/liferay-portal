/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function objectRelationshipReducer(
	state: State,
	{payload, type}: Action
): State;
interface Action {
	payload: {
		[key: string]: number;
	};
	type: string;
}
interface State {
	objectRelationships: {
		[key: string]: number;
	};
}
export {};
