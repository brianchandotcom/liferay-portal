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
import {useMemo, useRef} from 'react';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {evaluate, mergePages} from '../util/evaluation.es';
import {PagesVisitor} from '../util/visitors.es';
import {usePage} from './usePage.es';

let REVALIDATE_UPDATES = [];

const getEditedPages = ({editingLanguageId, name, pages, value}) => {
	const pageVisitor = new PagesVisitor(pages);

	return pageVisitor.mapFields(
		(field) => {
			if (field.name === name) {
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

const UPDATE_DELAY_MS = 50;

let lastEditedPages = [];

export default function useCreateFieldChange() {
	const optionsRef = useRef();

	optionsRef.current = usePage();

	const createFieldChange = useMemo(
		() =>
			debounce((event, dispatch) => {
				const {
					defaultLanguageId,
					editingLanguageId,
					groupId,
					pages,
					portletNamespace,
					rules,
				} = optionsRef.current;

				const {fieldInstance, value} = event;
				const {evaluable, fieldName} = fieldInstance;

				const editedPages = getEditedPages({
					editingLanguageId,
					name: fieldInstance.name,
					pages,
					value,
				});

				lastEditedPages = editedPages;

				// We want a synchronous update without waiting for an
				// evaluation of the field.

				dispatch({
					payload: editedPages,
					type: EVENT_TYPES.UPDATE_PAGES,
				});

				// We triggered a dispatch of FIELD_CHANGE just to propagate the
				// event to the upper layers.

				dispatch({payload: event, type: EVENT_TYPES.FIELD_CHANGE});

				if (fieldInstance.visible === false) {
					return;
				}

				if (evaluable) {
					evaluate(fieldName, {
						defaultLanguageId,
						editingLanguageId,
						groupId,
						pages: editedPages,
						portletNamespace,
						rules,
					})
						.then((evaluatedPages) => {
							if (REVALIDATE_UPDATES.length > 0) {

								// All non-evaluable operations that were performed after the request
								// was sent are used here to revalidate the new data.

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

							return evaluatedPages;
						})
						.then((evaluatedPages) => {
							if (fieldInstance.isDisposed()) {
								return;
							}

							const mergedPages = mergePages(
								defaultLanguageId,
								editingLanguageId,
								fieldName,
								evaluatedPages,
								lastEditedPages
							);

							dispatch({
								payload: mergedPages,
								type: EVENT_TYPES.UPDATE_PAGES,
							});
							dispatch({
								payload: mergedPages,
								type: EVENT_TYPES.FIELD_EVALUATED,
							});
						})
						.catch((error) =>
							dispatch({
								payload: error,
								type: EVENT_TYPES.FIELD_EVALUATION_ERROR,
							})
						);
				}
				else {
					REVALIDATE_UPDATES.push({
						editingLanguageId,
						name: fieldInstance.name,
						value,
					});
				}
			}, UPDATE_DELAY_MS),
		[]
	);

	return (event) => (dispatch) => createFieldChange(event, dispatch);
}
