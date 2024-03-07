/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {enableSubmitButton} from 'data-engine-js-components-web';

import {EVENT_TYPES} from '../eventTypes';

export default function sidebarReducer(state, action, config) {
	switch (action.type) {
		case EVENT_TYPES.SIDEBAR.FIELD.BLUR: {
			enableSubmitButton(config.submitButtonId);

			return {
				focusedField: {},
			};
		}
		default:
			return state;
	}
}
