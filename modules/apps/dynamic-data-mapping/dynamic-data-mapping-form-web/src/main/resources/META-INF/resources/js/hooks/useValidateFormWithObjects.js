/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	PagesVisitor,
	getFields,
	getObjectFieldName,
	getSelectedValue,
	useFormState,
} from 'data-engine-js-components-web';
import {useCallback} from 'react';

const getUnmappedFormFields = (formFields) => {
	return formFields.filter((field) => {
		const objectFieldName = getObjectFieldName(field);

		return objectFieldName && !getSelectedValue(objectFieldName.value);
	});
};

const getUnmappedRequiredObjectFields = (formFields, objectFields) => {
	const requiredObjectFields = objectFields.filter(({required}) => required);
	const formFieldNames = formFields
		.map((field) => {
			const objectFieldName = getObjectFieldName(field);

			return objectFieldName && getSelectedValue(objectFieldName.value);
		})
		.filter(Boolean);
	const unmappedRequiredObjectFields = requiredObjectFields.filter(
		({name}) => formFieldNames.indexOf(name) === -1
	);

	return unmappedRequiredObjectFields;
};

/**
 * This hook is used to validate the Forms when the
 * storage type object is selected in the Forms settings
 */
export function useValidateFormWithObjects() {
	const {formSettingsContext, objectFields, pages} = useFormState();

	return useCallback(
		(callbackFn) => {
			if (!formSettingsContext) {
				return true;
			}

			let hasObjectDefinitionId = false;
			let isStorageTypeObject = false;

			const visitor = new PagesVisitor(formSettingsContext.pages);

			visitor.visitFields(({fieldName, value}) => {
				if (fieldName === 'objectDefinitionId') {
					hasObjectDefinitionId = !!value[0];
				}
				else if (
					fieldName === 'storageType' &&
					value[0] === 'object'
				) {
					isStorageTypeObject = true;
				}
			});

			if (
				(hasObjectDefinitionId && isStorageTypeObject) ||
				objectFields.length
			) {
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
		[formSettingsContext, objectFields, pages]
	);
}
