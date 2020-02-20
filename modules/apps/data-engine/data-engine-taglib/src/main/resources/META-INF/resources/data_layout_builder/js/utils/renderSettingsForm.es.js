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

const UNIMPLEMENTED_PROPERTIES = [
	'fieldNamespace',
	'indexType',
	'localizable',
	'readOnly',
	'type',
	'validation',
	'visibilityExpression'
];

export const getFilteredSettingsContext = settingsContext => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapColumns(column => {
			return {
				...column,
				fields: column.fields
					.filter(
						({fieldName}) =>
							UNIMPLEMENTED_PROPERTIES.indexOf(fieldName) === -1
					)
					.map(field => {
						if (field.fieldName === 'dataSourceType') {
							field = {
								...field,
								predefinedValue: '["manual"]',
								readOnly: true
							};
						}

						return field;
					})
			};
		})
	};
};

export default ({dispatchEvent, settingsContext}, container) => {
	const handleFieldBlurred = ({fieldInstance, value}) => {
		if (fieldInstance && !fieldInstance.isDisposed()) {
			const {fieldName} = fieldInstance;

			dispatchEvent('fieldBlurred', {
				editingLanguageId: settingsContext.editingLanguageId,
				propertyName: fieldName,
				propertyValue: value
			});
		}
	};

	const handleFieldEdited = ({fieldInstance, value}) => {
		if (fieldInstance && !fieldInstance.isDisposed()) {
			const {fieldName} = fieldInstance;

			dispatchEvent('fieldEdited', {
				editingLanguageId: settingsContext.editingLanguageId,
				propertyName: fieldName,
				propertyValue: value
			});
		}
	};

	const handleFormAttached = function() {
		this.evaluate();
	};

	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	return new Form(
		{
			...settingsContext,
			editable: true,
			events: {
				attached: handleFormAttached,
				fieldBlurred: handleFieldBlurred,
				fieldEdited: handleFieldEdited
			},
			spritemap
		},
		container
	);
};
