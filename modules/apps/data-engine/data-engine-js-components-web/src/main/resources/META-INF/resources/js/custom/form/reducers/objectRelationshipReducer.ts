/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../eventTypes';

export default function objectRelationshipReducer(
	state: State,
	{payload, type}: Action
) {
	switch (type) {
		case EVENT_TYPES.OBJECT.RELATIONSHIPS_CHANGE: {
			return {
				objectRelationships: {
					...state.objectRelationships,
					...payload,
				},
			};
		}
		default:
			return state;
	}
}

interface Action {
	payload: {[key: string]: number};
	type: string;
}

interface State {
	objectRelationships: {[key: string]: number};
}
