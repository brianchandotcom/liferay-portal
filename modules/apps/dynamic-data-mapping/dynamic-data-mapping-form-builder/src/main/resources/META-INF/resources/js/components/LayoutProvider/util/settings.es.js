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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';

export const updateSettingsContextProperty = (
	editingLanguageId,
	settingsContext,
	propertyName,
	propertyValue
) => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields(field => {
			if (propertyName === field.fieldName) {
				field = {
					...field,
					value: propertyValue
				};

				if (field.localizable) {
					field.localizedValue = {
						...field.localizedValue,
						[editingLanguageId]: propertyValue
					};
				}
			}

			return field;
		})
	};
};
