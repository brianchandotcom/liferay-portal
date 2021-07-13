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

import {EVENT_TYPES} from '../eventTypes.es';

/**
 * This reducer was created to consume datafrom
 * FormBuilder inside FormFieldSettings
 */
export default (state, action) => {
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
};
