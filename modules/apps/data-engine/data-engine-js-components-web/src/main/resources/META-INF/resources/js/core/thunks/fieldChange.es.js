/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {evaluate, mergePages} from '../../utils/evaluation.es';
import {PagesVisitor} from '../../utils/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';
import {disableSubmitButton} from '../utils/submitButtonController.es';

let REVALIDATE_UPDATES = [];

const getEditedPages = ({
	editingLanguageId,
	key = 'value',
	name,
	pages,
	value,
}) => {
	const pageVisitor = new PagesVisitor(pages);

	return pageVisitor.mapFields(
		(field) => {
			if (field.name === name) {
				if (key !== 'value') {
					return {
						...field,
						[key]: value,
					};
				}

				return {
					...field,
					localizedValue: {
						...field.localizedValue,
						[editingLanguageId]: value,
					},
					localizedValueEdited: {
						...(field.localizedValueEdited ?? {}),
						[editingLanguageId]: true,
					},
					value,
				};
			}

			return field;
		},
		false,
		true
	);
};

let lastEditedPages = [];

export default function fieldChange({
	defaultLanguageId,
	editingLanguageId,
	formId,
	objectFields,
	pages,
	portletNamespace,
	properties,
	rules,
	submitButtonId,
	viewMode,
}) {
	return async (dispatch) => {
		const {fieldInstance, key, value} = properties;
		const {evaluable, fieldName} = fieldInstance;

		const editedPages = getEditedPages({
			editingLanguageId,
			key,
			name: fieldInstance.name,
			pages,
			value,
		});

		lastEditedPages = editedPages;

		// We want a synchronous update without waiting for an evaluation of
		// the field.

		dispatch({payload: editedPages, type: EVENT_TYPES.PAGE.UPDATE});

		if (evaluable) {
			try {
				disableSubmitButton(submitButtonId);

				let evaluatedPages = await evaluate(fieldName, {
					defaultLanguageId,
					editingLanguageId,
					formId,
					objectFields,
					pages: editedPages,
					portletNamespace,
					rules,
					viewMode,
				});

				dispatch({payload: properties, type: EVENT_TYPES.FIELD.CHANGE});

				if (REVALIDATE_UPDATES.length) {

					// All nonevaluable operations that were performed after the request
					// was sent are used here to revalidate the new data

					REVALIDATE_UPDATES.forEach((item) => {
						evaluatedPages = getEditedPages({
							...item,
							pages: evaluatedPages,
						});
					});

					// Redefine the list of updates to avoid leaking memory and avoid
					// more expensive operations in the next interactions

					REVALIDATE_UPDATES = [];
				}
				if (fieldInstance.isDisposed()) {
					return;
				}

				const mergedPages = mergePages(
					defaultLanguageId,
					editingLanguageId,
					fieldName,
					evaluatedPages,
					lastEditedPages,
					viewMode
				);

				dispatch({
					payload: mergedPages,
					type: EVENT_TYPES.PAGE.UPDATE,
				});

				dispatch({
					payload: mergedPages,
					type: EVENT_TYPES.FIELD.EVALUATE,
				});
			}
			catch (error) {
				dispatch({
					payload: error,
					type: EVENT_TYPES.FIELD_EVALUATION_ERROR,
				});
			}
		}
		else {
			dispatch({payload: properties, type: EVENT_TYPES.FIELD.CHANGE});

			REVALIDATE_UPDATES.push({
				editingLanguageId,
				name: fieldInstance.name,
				value,
			});
		}
	};
}
