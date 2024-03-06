/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FieldUtil,
	PagesVisitor,
	SettingsContext,
} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-taglib';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default function sidebarReducer(state, action) {
	switch (action.type) {
		case CORE_EVENT_TYPES.SIDEBAR.FIELD.BLUR: {
			const {focusedField} = state;

			if (
				Object.keys(focusedField).length &&
				(focusedField.fieldReference === '' ||
					FieldUtil.findInvalidFieldReference(
						focusedField,
						state.pages,
						focusedField.fieldReference
					))
			) {
				const {defaultLanguageId, editingLanguageId, pages} = state;

				const visitor = new PagesVisitor(pages);

				return {
					focusedField: {},
					pages: visitor.mapFields(
						(field) => {
							if (field.fieldName === focusedField.fieldName) {
								return SettingsContext.updateField(
									{
										defaultLanguageId,
										editingLanguageId,
									},
									SettingsContext.updateFieldReference(
										focusedField,
										false,
										true
									),
									'fieldReference',
									focusedField.fieldName
								);
							}

							return field;
						},
						true,
						true
					),
				};
			}

			return {
				focusedField: {},
			};
		}

		default:
			return state;
	}
}
