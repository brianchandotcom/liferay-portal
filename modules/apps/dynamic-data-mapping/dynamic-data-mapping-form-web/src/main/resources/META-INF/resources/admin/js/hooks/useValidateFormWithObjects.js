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

import {useFormState} from 'data-engine-js-components-web';
import {getFields} from 'data-engine-js-components-web/js/utils/fields.es';
import {useCallback} from 'react';

const getFieldsByColumn = (settingsContext, columnTitle) => {
	const column = ({title}) => title.toLowerCase() === columnTitle;

	return settingsContext.pages.find(column).rows[0].columns[0].fields;
};

const getFieldProperty = (fields, fieldName) =>
	fields.find((field) => field.fieldName === fieldName);

const getSelectedValue = (value) => {
	if (typeof value === 'string' && value !== '') {
		const newValue = JSON.parse(value);

		return Array.isArray(newValue) ? newValue[0] : newValue;
	}

	return value[0];
};

const getObjectFieldName = (settingsContext) => {
	const fieldsFromAdvancedColumn = getFieldsByColumn(
		settingsContext,
		'advanced'
	);
	const objectFieldName = getFieldProperty(
		fieldsFromAdvancedColumn,
		'objectFieldName'
	);

	return objectFieldName;
};

const getUnmappedFormFields = (formFields) => {
	return formFields.filter(({settingsContext}) => {
		const objectFieldName = getObjectFieldName(settingsContext);

		return objectFieldName && !getSelectedValue(objectFieldName.value);
	});
};

const getUnmappedRequiredObjectFields = (formFields, objectFields) => {
	const requiredObjectFields = objectFields.filter(({required}) => required);
	const formFieldNames = formFields
		.map(({settingsContext}) => {
			const objectFieldName = getObjectFieldName(settingsContext);

			return objectFieldName && getSelectedValue(objectFieldName.value);
		})
		.filter(Boolean);
	const unmappedRequiredObjectFields = requiredObjectFields.filter(
		({name}) => formFieldNames.indexOf(name) === -1
	);

	return unmappedRequiredObjectFields;
};

/**
 * This hook returns false when there is no objectDefinitionId
 * and if any Forms field is not mapped with Object fields
 */
export const useValidateFormWithObjects = () => {
	const {objectFields, pages} = useFormState();

	return useCallback(
		async (callbackFn) => {
			const settingsDDMForm = await Liferay.componentReady(
				'settingsDDMForm'
			);
			const objectDefinitionId = settingsDDMForm.reactComponentRef.current.getObjectDefinitionId();

			if (objectDefinitionId) {
				const formFields = getFields(pages);
				const unmappedFormFields = getUnmappedFormFields(formFields);
				const unmappedRequiredObjectFields = getUnmappedRequiredObjectFields(
					formFields,
					objectFields
				);

				if (
					unmappedFormFields.length ||
					unmappedRequiredObjectFields.length
				) {
					if (callbackFn) {
						callbackFn({
							unmappedFormFields,
							unmappedRequiredObjectFields,
						});
					}

					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
		},
		[objectFields, pages]
	);
};
