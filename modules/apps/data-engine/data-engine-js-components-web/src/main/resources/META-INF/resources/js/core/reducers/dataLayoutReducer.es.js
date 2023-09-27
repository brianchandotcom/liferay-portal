/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';

export default function dataLayoutReducer(state, action) {
	switch (action.type) {
		case EVENT_TYPES.DATA_LAYOUT.NAME: {
			const {name} = action.payload;

			return {
				dataLayout: {
					...state.dataLayout,
					name,
				},
			};
		}
		default:
			return state;
	}
}
