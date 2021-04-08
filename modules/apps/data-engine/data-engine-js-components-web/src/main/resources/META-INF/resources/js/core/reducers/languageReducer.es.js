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

import {FieldSupport} from 'dynamic-data-mapping-form-builder';

import {
	generateInstanceId,
	generateName,
	getRepeatedIndex,
} from '../../utils/repeatable.es';
import {PagesVisitor} from '../../utils/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';

const deleteLanguageId = (languageId, pages) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields((field) => {
		const {localizedValue} = field;
		const newLocalizedValue = {...localizedValue};

		delete newLocalizedValue[languageId];

		return {
			...field,
			localizedValue: newLocalizedValue,
		};
	});
};

const getLocalizedValue = ({
	defaultLanguageId,
	editingLanguageId,
	localizable,
	localizedValue,
	localizedValueEdited,
	type,
	value,
}) => {
	if (!localizable) {
		return value;
	}

	let _value;

	const defaultValue = localizedValue[defaultLanguageId];

	if (localizedValue) {
		if (localizedValue[editingLanguageId] != null) {
			if (
				Array.isArray(localizedValue[editingLanguageId]) &&
				!localizedValue[editingLanguageId]?.length &&
				!localizedValueEdited?.[editingLanguageId]
			) {
				_value = defaultValue;
			}
			else {
				_value = localizedValue[editingLanguageId];
			}
		}
		else if (defaultValue) {
			_value = defaultValue;
		}
	}

	try {
		_value = type === 'numeric' ? _value : JSON.parse(_value);
	}
	catch (e) {}

	if (type === 'image') {
		try {
			return JSON.parse(value);
		}
		catch (e) {}
	}

	return _value;
};

const getLocalizedPages = (pages, defaultLanguageId, editingLanguageId) => {
	const settingsVisitor = new PagesVisitor(pages);

	return settingsVisitor.mapFields((field) =>
		FieldSupport.localizeField(field, defaultLanguageId, editingLanguageId)
	);
};

const updateFieldLanguage = ({
	availableLanguageIds,
	dataSourceType,
	defaultLanguageId,
	editingLanguageId,
	instanceId,
	name,
	options,
	settingsContext,
	type,
}) => {
	const newSettingsContext = {
		...settingsContext,
		availableLanguageIds,
		defaultLanguageId,
		pages: getLocalizedPages(
			settingsContext.pages,
			defaultLanguageId,
			editingLanguageId
		),
	};

	const newField = {
		...FieldSupport.getFieldProperties(
			newSettingsContext,
			defaultLanguageId,
			editingLanguageId
		),
		name: generateName(name, {
			instanceId: instanceId || generateInstanceId(),
			repeatedIndex: getRepeatedIndex(name),
		}),
		settingsContext: newSettingsContext,
	};

	if (
		type === 'select' &&
		dataSourceType &&
		dataSourceType.includes('data-provider')
	) {
		return {
			...newField,
			options,
		};
	}

	return newField;
};

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.LANGUAGE.CHANGE: {
			const {
				availableLanguageIds,
				defaultLanguageId: prevDefaultLanguageId,
				focusedField,
			} = state;
			const {
				defaultLanguageId = prevDefaultLanguageId,
				editingLanguageId,
				pages,
			} = action.payload;

			const visitor = new PagesVisitor(pages ?? state.pages);

			let newFocusedField = focusedField;

			const newPages = visitor.mapFields(
				({
					localizable,
					localizedValue,
					localizedValueEdited,
					value: previousValue,
					...field
				}) => {

					// When languageReducer is used in the context of the
					// Form Builder, the fields contain settingsContext
					// which we also need to update but do not exist within
					// the fields in the settingsContext structure.

					if (field.settingsContext) {
						const newField = {
							...field,
							...updateFieldLanguage({
								...field,
								availableLanguageIds,
								defaultLanguageId,
								editingLanguageId,
							}),
							value: previousValue,
						};

						if (field.fieldName === newFocusedField.fieldName) {
							newFocusedField = newField;
						}

						return newField;
					}

					return {
						value: getLocalizedValue({
							defaultLanguageId,
							editingLanguageId,
							localizable,
							localizedValue,
							localizedValueEdited,
							type: field.type,
							value: previousValue,
						}),
					};
				},
				true,
				true,
				true
			);

			return {
				defaultLanguageId,
				editingLanguageId,
				focusedField: newFocusedField,
				pages: newPages,
			};
		}
		case EVENT_TYPES.LANGUAGE.ADD: {
			const {languageId} = action.payload;
			const {availableLanguageIds} = state;

			if (availableLanguageIds.includes(languageId)) {
				return state;
			}

			return {
				availableLanguageIds: [...availableLanguageIds, languageId],
			};
		}
		case EVENT_TYPES.LANGUAGE.DELETE: {
			const {languageIds} = action.payload;
			const {availableLanguageIds, focusedField, pages} = state;

			// The TranslationManager API does not return the removed languageId
			// but the list of new locales available but the operation will always
			// be synchronous, the user removes a locale and `availableLocales` is
			// triggered and then we call the reducer, it will not be an asynchronous
			// operation where the locales are removed are accumulated and sent in
			// a batch.

			const [languageId] = languageIds;

			let newFocusedField = focusedField;

			if (newFocusedField.settingsContext) {
				newFocusedField = {
					...newFocusedField,
					settingsContext: {
						...newFocusedField.settingsContext,
						pages: deleteLanguageId(
							languageId,
							newFocusedField.settingsContext.pages
						),
					},
				};
			}

			const visitor = new PagesVisitor(pages);

			const newPages = visitor.mapPages((page) => {
				const {
					contentRenderer,
					localizedDescription,
					localizedTitle,
				} = page;

				if (contentRenderer === 'success') {
					return page;
				}

				delete localizedDescription[languageId];
				delete localizedTitle[languageId];

				return {
					...page,
					localizedDescription,
					localizedTitle,
				};
			});

			visitor.setPages(newPages);

			return {
				availableLanguageIds: availableLanguageIds.filter(
					(id) => !languageIds.includes(id)
				),
				focusedField: newFocusedField,
				pages: visitor.mapFields((field) => {
					const {settingsContext} = field;

					return {
						...field,
						settingsContext: {
							...settingsContext,
							pages: deleteLanguageId(
								languageId,
								settingsContext.pages
							),
						},
					};
				}),
			};
		}
		default:
			return state;
	}
};
