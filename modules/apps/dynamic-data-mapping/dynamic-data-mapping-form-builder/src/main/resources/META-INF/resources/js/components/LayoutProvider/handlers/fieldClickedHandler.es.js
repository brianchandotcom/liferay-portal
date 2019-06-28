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

import * as FormSupport from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';

const handleFieldClicked = (state, event) => {
	const {columnIndex, pageIndex, rowIndex} = event;
	const {pages} = state;

	const fieldProperties = FormSupport.getField(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);
	const {settingsContext} = fieldProperties;
	const visitor = new PagesVisitor(settingsContext.pages);

	const focusedField = {
		...fieldProperties,
		columnIndex,
		pageIndex,
		rowIndex,
		settingsContext: {
			...settingsContext,
			pages: visitor.mapFields(field => {
				const {fieldName} = field;

				if (fieldName === 'name') {
					field.visible = true;
				} else if (fieldName === 'label') {
					field.type = 'text';
				} else if (fieldName === 'validation') {
					field = {
						...field,
						validation: {
							...field.validation,
							fieldName: fieldProperties.fieldName
						}
					};
				}

				return field;
			})
		}
	};

	return {
		focusedField: {
			...focusedField,
			columnIndex,
			pageIndex,
			rowIndex
		},
		previousFocusedField: focusedField
	};
};

export default handleFieldClicked;
