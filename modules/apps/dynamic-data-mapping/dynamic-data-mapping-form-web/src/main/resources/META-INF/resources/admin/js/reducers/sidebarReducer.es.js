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

import {FieldSupport, RulesSupport} from 'dynamic-data-mapping-form-builder';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {EVENT_TYPES} from '../eventTypes.es';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.SIDEBAR.BLUR:
			return {
				focusedField: {},
			};
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
		case EVENT_TYPES.SIDEBAR.EVALUATE: {
			const {
				changedFieldType,
				instanceId,
				settingsContext,
			} = action.payload;
			const {focusedField, pages, rules} = state;

			const fieldName = FieldSupport.getField(
				settingsContext.pages,
				'name'
			);
			const focusedFieldName = FieldSupport.getField(
				focusedField.settingsContext.pages,
				'name'
			);

			if (
				fieldName.instanceId !== focusedFieldName.instanceId &&
				!changedFieldType
			) {
				return state;
			}

			const visitor = new PagesVisitor(pages);

			const newPages = visitor.mapFields((field) => {
				if (field.fieldName !== fieldName.value) {
					return field;
				}

				return {
					...field,
					settingsContext,
				};
			});

			return {
				focusedField: {
					...focusedField,
					instanceId: instanceId || focusedField.instanceId,
					settingsContext,
				},
				pages: newPages,
				rules: changedFieldType
					? RulesSupport.formatRules(newPages, rules)
					: rules,
			};
		}
		default:
			return state;
	}
};
