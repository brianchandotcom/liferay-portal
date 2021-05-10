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

import {PagesVisitor} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-taglib';
import {
	FieldUtil,
	RulesSupport,
	SettingsContext,
} from 'dynamic-data-mapping-form-builder';

import {EVENT_TYPES} from '../eventTypes.es';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default (state, action) => {
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
		case EVENT_TYPES.SIDEBAR.CHANGES_CANCEL: {
			const {focusedField, pages, previousFocusedField} = state;

			const {settingsContext} = previousFocusedField;

			const visitor = new PagesVisitor(pages);

			return {
				focusedField: previousFocusedField,
				pages: visitor.mapFields((field) => {
					if (field.fieldName === focusedField.fieldName) {
						return {
							...previousFocusedField,
							settingsContext,
						};
					}

					return field;
				}),
			};
		}
		case EVENT_TYPES.SIDEBAR.CHANGE_FIELD_TYPE: {
			const {pages, rules} = state;

			const visitor = new PagesVisitor(pages);

			const newPages = visitor.mapFields(
				(field) => {
					if (field.fieldName !== action.payload.fieldName) {
						return field;
					}

					return action.payload;
				},
				true,
				true
			);

			return {
				focusedField: action.payload,
				pages: newPages,
				rules: RulesSupport.formatRules(newPages, rules),
			};
		}
		default:
			return state;
	}
};
