/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../eventTypes';

/**
 * This reducer was created to consume datafrom
 * FormBuilder inside FormFieldSettings
 */
export default function formBuilderReducer(state, action) {
	switch (action.type) {
		case EVENT_TYPES.FORM_BUILDER.PAGES.UPDATE: {
			const {pages} = action.payload;

			return {
				formBuilder: {
					...state.formBuilder,
					pages,
				},
			};
		}
		case EVENT_TYPES.FORM_BUILDER.FOCUSED_FIELD.CHANGE: {
			const {focusedField} = action.payload;

			return {
				formBuilder: {
					...state.formBuilder,
					focusedField,
				},
			};
		}
		default:
			return state;
	}
}
