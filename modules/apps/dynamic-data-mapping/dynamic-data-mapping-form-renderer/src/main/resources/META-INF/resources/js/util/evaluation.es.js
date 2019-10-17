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

import {debounce} from 'frontend-js-web';

import {convertToFormData, makeFetch} from './fetch.es';
import {PagesVisitor} from './visitors.es';

const EVALUATOR_URL =
	themeDisplay.getPathContext() +
	'/o/dynamic-data-mapping-form-context-provider/';

let controller = null;

const doEvaluate = debounce((fieldName, evaluatorContext, callback) => {
	const {
		defaultLanguageId,
		editingLanguageId,
		pages,
		portletNamespace
	} = evaluatorContext;

	if (controller) {
		controller.abort();
	}

	if (window.AbortController) {
		controller = new AbortController();
	}

	makeFetch({
		body: convertToFormData({
			languageId: editingLanguageId,
			p_auth: Liferay.authToken,
			portletNamespace,
			serializedFormContext: JSON.stringify({
				...evaluatorContext,
				groupId: themeDisplay.getScopeGroupId(),
				portletNamespace
			}),
			trigger: fieldName
		}),
		signal: controller && controller.signal,
		url: EVALUATOR_URL
	})
		.then(newPages => {
			const mergedPages = mergePages(
				defaultLanguageId,
				editingLanguageId,
				newPages,
				pages
			);

			callback(null, mergedPages);
		})
		.catch(error => callback(error));
}, 300);

export const evaluate = (fieldName, evaluatorContext) => {
	return new Promise((resolve, reject) => {
		doEvaluate(fieldName, evaluatorContext, (error, pages) => {
			if (error) {
				return reject(error);
			}

			resolve(pages);
		});
	});
};

export const mergeFieldOptions = (field, newField) => {
	let newValue = {...newField.value};

	Object.keys(newValue).forEach(languageId => {
		newValue = {
			...newValue,
			[languageId]: newValue[languageId].map(option => {
				const existingOption = field.value[languageId].find(
					({value}) => value === option.value
				);

				return {
					...option,
					edited: existingOption && existingOption.edited
				};
			})
		};
	});

	return newValue;
};

export const mergePages = (
	defaultLanguageId,
	editingLanguageId,
	newPages,
	sourcePages
) => {
	const visitor = new PagesVisitor(newPages);

	return visitor.mapFields(
		(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
			const sourceField =
				sourcePages[pageIndex].rows[rowIndex].columns[columnIndex]
					.fields[fieldIndex];

			let newField = {
				...sourceField,
				...field,
				defaultLanguageId,
				editingLanguageId,
				valid: field.valid !== false
			};

			if (sourceField.nestedFields && newField.nestedFields) {
				newField = {
					...newField,
					nestedFields: sourceField.nestedFields.map(nestedField => {
						return {
							...nestedField,
							...(newField.nestedFields.find(({fieldName}) => {
								return fieldName === nestedField.fieldName;
							}) || {})
						};
					})
				};
			}

			if (newField.type === 'options') {
				newField = {
					...newField,
					value: mergeFieldOptions(sourceField, newField)
				};
			}

			if (newField.localizable) {
				newField = {
					...newField,
					localizedValue: {
						...sourceField.localizedValue
					}
				};
			}

			return newField;
		}
	);
};
