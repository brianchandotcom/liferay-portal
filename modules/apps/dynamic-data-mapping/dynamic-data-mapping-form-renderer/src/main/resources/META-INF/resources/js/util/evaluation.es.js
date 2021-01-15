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

export const mergeFieldOptions = (field, newField) => {
	let newValue = {...newField.value};

	Object.keys(newValue).forEach((languageId) => {
		newValue = {
			...newValue,
			[languageId]: newValue[languageId].map((option) => {
				const existingOption =
					field.value &&
					field.value[languageId] &&
					field.value[languageId].find(
						({value}) => value === option.value
					);

				return {
					...option,
					edited: existingOption && existingOption.edited,
				};
			}),
		};
	});

	return newValue;
};

export const mergePages = (
	defaultLanguageId,
	editingLanguageId,
	fieldName,
	newPages,
	sourcePages
) => {
	const newPagesVisitor = new PagesVisitor(newPages);
	const sourcePagesVisitor = new PagesVisitor(sourcePages);

	return newPagesVisitor.mapFields(
		(field) => {
			let sourceField =
				sourcePagesVisitor.findField(({name}) => name === field.name) ||
				{};

			if (!sourceField || Object.keys(sourceField).length === 0) {
				sourceField =
					sourcePagesVisitor.findField(
						({fieldName}) => fieldName === field.fieldName
					) || {};
			}

			let fieldValue = field.valueChanged
				? field.value
				: sourceField.value;

			if (field.visible !== sourceField.visible && field.visible) {
				fieldValue = '';
			}

			let newField = {
				...sourceField,
				...field,
				defaultLanguageId,
				displayErrors:
					sourceField.displayErrors || field.fieldName === fieldName,
				editingLanguageId,
				valid: field.valid !== false,
				value: fieldValue,
			};

			if (newField.type === 'options') {
				newField = {
					...newField,
					value: mergeFieldOptions(sourceField, newField),
				};
			}

			if (newField.localizable) {
				newField = {
					...newField,
					localizedValue: {
						...sourceField.localizedValue,
					},
				};
			}

			return newField;
		},
		false,
		true
	);
};

const doEvaluate = debounce((fieldName, evaluatorContext, callback) => {
	const {
		defaultLanguageId,
		editingLanguageId,
		groupId,
		pages,
		portletNamespace,
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
				groupId: groupId ? groupId : themeDisplay.getScopeGroupId(),
				portletNamespace,
			}),
			trigger: fieldName,
		}),
		signal: controller && controller.signal,
		url: EVALUATOR_URL,
	})
		.then((newPages) => {
			const mergedPages = mergePages(
				defaultLanguageId,
				editingLanguageId,
				fieldName,
				newPages,
				pages
			);

			callback(null, mergedPages);
		})
		.catch((error) => callback(error));
}, 600);

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
