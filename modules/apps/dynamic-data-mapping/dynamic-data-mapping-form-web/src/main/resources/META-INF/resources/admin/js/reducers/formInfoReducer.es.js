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

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.FORM_INFO.DESCRIPTION_CHANGE: {
			const {editingLanguageId, localizedDescription} = state;

			return {
				localizedDescription: {
					...localizedDescription,
					[editingLanguageId]: action.payload,
				},
			};
		}
		case EVENT_TYPES.FORM_INFO.NAME_CHANGE: {
			const {editingLanguageId, localizedName} = state;

			return {
				localizedName: {
					...localizedName,
					[editingLanguageId]: action.payload,
				},
			};
		}
		case EVENT_TYPES.FORM_INFO.LANGUAGE_DELETE: {
			const {languageId, localizedDescription, localizedName} = state;

			delete localizedName[languageId];
			delete localizedDescription[languageId];

			return {
				localizedDescription: {
					...localizedDescription,
				},
				localizedName: {
					...localizedName,
				},
			};
		}
		default:
			return state;
	}
};
