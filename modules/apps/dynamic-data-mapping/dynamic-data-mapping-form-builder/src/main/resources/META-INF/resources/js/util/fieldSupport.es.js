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

export const formatFieldName = (instanceId, languageId, value) => {
	return `ddm$$${value}$${instanceId}$0$$${languageId}`;
};

export const generateInstanceId = length => {
	let text = '';

	const possible =
		'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

	for (let i = 0; i < length; i++) {
		text += possible.charAt(Math.floor(Math.random() * possible.length));
	}

	return text;
};

export const normalizeSettingsContextPages = (
	pages,
	editingLanguageId,
	fieldType,
	generatedFieldName
) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(field => {
		const {fieldName} = field;

		if (fieldName === 'name') {
			field = {
				...field,
				value: generatedFieldName,
				visible: true
			};
		} else if (fieldName === 'label') {
			field = {
				...field,
				localizedValue: {
					...field.localizedValue,
					[editingLanguageId]: fieldType.label
				},
				type: 'text',
				value: fieldType.label
			};
		} else if (fieldName === 'type') {
			field = {
				...field,
				value: fieldType.name
			};
		} else if (fieldName === 'validation') {
			field = {
				...field,
				validation: {
					...field.validation,
					fieldName: generatedFieldName
				}
			};
		}
		return {
			...field
		};
	});
};

export const getFieldProperties = (
	{pages},
	defaultLanguageId,
	editingLanguageId
) => {
	const properties = {};
	const visitor = new PagesVisitor(pages);

	visitor.mapFields(
		({fieldName, localizable, localizedValue = {}, type, value}) => {
			if (localizable && localizedValue[editingLanguageId]) {
				properties[fieldName] = localizedValue[editingLanguageId];
			} else if (localizable && localizedValue[defaultLanguageId]) {
				properties[fieldName] = localizedValue[defaultLanguageId];
			} else if (type == 'options') {
				if (!value[editingLanguageId] && value[defaultLanguageId]) {
					properties[fieldName] = value[defaultLanguageId];
				} else {
					properties[fieldName] = value[editingLanguageId];
				}
			} else {
				properties[fieldName] = value;
			}
		}
	);

	return properties;
};
