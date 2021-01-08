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
import {fetch} from 'frontend-js-web';

import {EVENT_TYPES} from '../actions/eventTypes.es';

const formatDataRecord = (languageId, pages, preserveValue) => {
	const dataRecordValues = {};
	let previousFieldName;
	let repeatableIndex = 0;

	const visitor = new PagesVisitor(pages);

	const setDataRecord = ({
		fieldName,
		localizable,
		localizedValue,
		localizedValueEdited,
		repeatable,
		type,
		value,
		visible,
	}) => {
		if (type === 'fieldset' || type === 'separator') {
			return;
		}

		let _value = null;

		try {
			_value = JSON.parse(value);
		}
		catch (e) {
			_value = value;
		}

		if (!visible) {
			_value = '';
		}

		if (previousFieldName !== fieldName) {
			repeatableIndex = 0;
		}

		previousFieldName = fieldName;

		if (localizable) {
			const edited =
				!!localizedValue?.[languageId] ||
				(localizedValueEdited && localizedValueEdited[languageId]);

			let availableLanguageIds;

			if (localizedValue) {
				availableLanguageIds = Object.keys(localizedValue);
			}
			else {
				availableLanguageIds = [];
			}

			if (!availableLanguageIds.includes(languageId)) {
				availableLanguageIds.push(languageId);
			}

			if (!dataRecordValues[fieldName]) {
				if (repeatable) {
					availableLanguageIds.forEach((key) => {
						dataRecordValues[fieldName] = {
							...dataRecordValues[fieldName],
							[key]: [],
							[languageId]: [],
						};
					});
				}
				else if (edited) {
					dataRecordValues[fieldName] = {...localizedValue};
				}
				else if (preserveValue) {
					dataRecordValues[fieldName] = {
						...localizedValue,
						[languageId]: value,
					};
				}
			}

			if (repeatable) {
				availableLanguageIds.forEach((key) => {
					if (edited && key === languageId) {
						dataRecordValues[fieldName][key][
							repeatableIndex
						] = _value;
					}
					else {
						dataRecordValues[fieldName][key][repeatableIndex] =
							localizedValue[key];
					}
				});

				repeatableIndex++;
			}
			else if (edited) {
				dataRecordValues[fieldName] = {
					...localizedValue,
					[languageId]: _value,
				};
			}
		}
		else {
			dataRecordValues[fieldName] = _value;
		}
	};

	visitor.mapFields(
		(field) => {
			setDataRecord(field);
		},
		true,
		true
	);

	return dataRecordValues;
};

const getDataRecordValues = ({
	defaultSiteLanguageId,
	nextEditingLanguageId,
	pages,
	preserveValue,
	prevDataRecordValues,
	prevEditingLanguageId,
}) => {
	if (preserveValue) {
		return formatDataRecord(nextEditingLanguageId, pages, true);
	}

	const dataRecordValues = formatDataRecord(prevEditingLanguageId, pages);
	const newDataRecordValues = {...prevDataRecordValues};

	Object.keys(dataRecordValues).forEach((key) => {
		if (newDataRecordValues[key]) {
			newDataRecordValues[key][defaultSiteLanguageId] =
				dataRecordValues[key][defaultSiteLanguageId];

			newDataRecordValues[key][prevEditingLanguageId] =
				dataRecordValues[key][prevEditingLanguageId];
		}
		else {
			newDataRecordValues[key] = dataRecordValues[key];
		}
	});

	return newDataRecordValues;
};

const getFieldProperties = (fieldName, pages) => {
	const visitor = new PagesVisitor(pages);

	const {itemSelectorURL, localizedValueEdited} = visitor.findField(
		(field) => field.fieldName === fieldName
	);

	return {itemSelectorURL, localizedValueEdited};
};

export default function pageLanguageUpdate({
	ddmStructureLayoutId,
	defaultSiteLanguageId,
	nextEditingLanguageId,
	pages,
	portletNamespace,
	preserveValue,
	prevDataRecordValues,
	prevEditingLanguageId,
	readOnly,
}) {
	return (dispatch) => {
		const newDataRecordValues = getDataRecordValues({
			defaultSiteLanguageId,
			nextEditingLanguageId,
			pages,
			preserveValue,
			prevDataRecordValues,
			prevEditingLanguageId,
		});

		fetch(
			`/o/data-engine/v2.0/data-layouts/${ddmStructureLayoutId}/context`,
			{
				body: JSON.stringify({
					dataRecordValues: newDataRecordValues,
					namespace: portletNamespace,
					pathThemeImages: themeDisplay.getPathThemeImages(),
					readOnly,
					scopeGroupId: themeDisplay.getScopeGroupId(),
					siteGroupId: themeDisplay.getSiteGroupId(),
				}),
				headers: {
					'Accept-Language': nextEditingLanguageId.replace('_', '-'),
					'Content-Type': 'application/json',
				},
				method: 'POST',
			}
		)
			.then((response) => response.json())
			.then((response) => {
				let previousField;
				let repeatableIndex = 0;

				const visitor = new PagesVisitor(response.pages);
				const newPages = visitor.mapFields(
					(field) => {
						if (previousField?.fieldName !== field.fieldName) {
							repeatableIndex = 0;
						}

						if (!field.localizedValue) {
							field.localizedValue = {};
						}

						const fieldRecordValue =
							newDataRecordValues[field.fieldName];

						if (field.repeatable && fieldRecordValue) {
							let values = {};
							Object.keys(fieldRecordValue).forEach((key) => {
								values = {
									...values,
									[key]:
										fieldRecordValue[key][repeatableIndex],
								};
							});
							field.localizedValue = values;

							repeatableIndex++;
						}
						else if (fieldRecordValue) {
							field.localizedValue = {
								...fieldRecordValue,
							};
						}

						previousField = field;

						return {
							...field,
							...getFieldProperties(field.fieldName, pages),
						};
					},
					true,
					true
				);

				dispatch({
					payload: {
						editingLanguageId: nextEditingLanguageId,
						pages: newPages,
					},
					type: EVENT_TYPES.ALL,
				});

				dispatch({
					payload: newDataRecordValues,
					type: EVENT_TYPES.UPDATE_DATA_RECORD_VALUES,
				});
			});
	};
}
