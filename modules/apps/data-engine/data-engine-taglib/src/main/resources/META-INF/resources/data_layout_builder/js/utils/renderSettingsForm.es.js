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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import Form from 'dynamic-data-mapping-form-renderer/js/containers/Form/Form.es';

export const getEvents = (dispatchEvent, settingsContext) => {
	const handleFieldBlurred = ({fieldInstance, value}) => {
		if (fieldInstance && !fieldInstance.isDisposed()) {
			const {fieldName} = fieldInstance;

			dispatchEvent('fieldBlurred', {
				editingLanguageId: settingsContext.editingLanguageId,
				propertyName: fieldName,
				propertyValue: value,
			});
		}
	};

	const handleFieldEdited = ({fieldInstance, value}) => {
		if (fieldInstance && !fieldInstance.isDisposed()) {
			const {fieldName} = fieldInstance;

			dispatchEvent('fieldEdited', {
				editingLanguageId: settingsContext.editingLanguageId,
				propertyName: fieldName,
				propertyValue: value,
			});
		}
	};

	const handleFormAttached = function () {
		this.evaluate();
	};

	const handleFormEvaluated = function (pages) {
		dispatchEvent('focusedFieldEvaluationEnded', {
			settingsContext: {
				...settingsContext,
				pages,
			},
		});
	};

	return {
		attached: handleFormAttached,
		evaluated: handleFormEvaluated,
		fieldBlurred: handleFieldBlurred,
		fieldEdited: handleFieldEdited,
	};
};

export const getFilteredSettingsContext = ({config, settingsContext}) => {
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
				fields: column.fields
					.filter(
						({fieldName}) =>
							!unsupportedProperties.includes(fieldName)
					)
					.map((field) => {
						if (field.fieldName === 'dataSourceType') {
							field = {
								...field,
								predefinedValue: '["manual"]',
								readOnly: true,
							};
						}

						return field;
					}),
			};
		}),
	};
};

export default (events, settingsContext, container) => {
	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	return new Form(
		{
			...settingsContext,
			editable: true,
			events,
			spritemap,
		},
		container
	);
};
