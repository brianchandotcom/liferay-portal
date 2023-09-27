/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../eventTypes.es';

export default function formInfoReducer(state, action) {
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
}
