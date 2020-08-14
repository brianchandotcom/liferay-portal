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

import {PagesVisitor, generateName} from 'dynamic-data-mapping-form-renderer';

const getPredefinedValues = ({locale, localizedValue, options}) => {
	if (Array.isArray(localizedValue[locale])) {
		return localizedValue[locale].filter((value) => {
			if (options.find((option) => value === option.value)) {
				return value;
			}
		});
	}

	return localizedValue[locale];
};

export const getFilteredSettingsContext = ({
	config,
	editingLanguageId,
	settingsContext,
}) => {
	const defaultLanguageId = themeDisplay.getDefaultLanguageId();
	const unsupportedTabs = [...config.disabledTabs];

	const pages = settingsContext.pages.filter(
		(page) => !unsupportedTabs.includes(page.title)
	);

	const visitor = new PagesVisitor(pages);

	const unsupportedProperties = [
		...config.unimplementedProperties,
		...config.disabledProperties,
	];

	return {
		...settingsContext,
		pages: visitor.mapColumns((column) => {
			return {
				...column,
				fields: column.fields.map((field) => {
					const {fieldName, name} = field;
					const updatedField = {
						...field,
						defaultLanguageId,
						editingLanguageId,
					};

					if (unsupportedProperties.includes(fieldName)) {
						return {
							...updatedField,
							name: generateName(name, updatedField),
							visibilityExpression: 'FALSE',
							visible: false,
						};
					}

					if (fieldName === 'dataSourceType') {
						return {
							...updatedField,
							name: generateName(name, updatedField),
							predefinedValue: '["manual"]',
							readOnly: true,
						};
					}

					if (fieldName === 'name') {
						return {
							...updatedField,
							readOnly: true,
						};
					}

					if (fieldName === 'predefinedValue') {
						field.localizedValue[
							field.locale
						] = getPredefinedValues(field);
					}

					return {
						...updatedField,
						name: generateName(name, updatedField),
					};
				}),
			};
		}),
	};
};
