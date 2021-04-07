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

import {PagesVisitor} from 'data-engine-js-components-web';
import {fetch} from 'frontend-js-web';

import formatFieldValue from '../../utils/formatFieldValue.es';
import setDataRecord from '../../utils/setDataRecord.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';

const getDataRecordValues = ({
	nextEditingLanguageId,
	pages,
	preserveValue,
	prevEditingLanguageId,
}) => {
	const visitor = new PagesVisitor(pages);

	const dataRecordValues = {};

	visitor.mapFields(
		(field) => {
			setDataRecord(
				{
					...field,
					value: formatFieldValue(
						field,
						preserveValue,
						nextEditingLanguageId,
						prevEditingLanguageId
					),
				},
				dataRecordValues,
				preserveValue ? nextEditingLanguageId : prevEditingLanguageId,
				preserveValue
			);
		},
		true,
		true
	);

	return dataRecordValues;
};

const getFieldProperties = (fieldName, pages) => {
	const visitor = new PagesVisitor(pages);

	const {
		itemSelectorURL,
		locale,
		localizedValueEdited = {[locale]: true},
	} = visitor.findField((field) => field.fieldName === fieldName);

	return {itemSelectorURL, localizedValueEdited};
};

export default function pageLanguageUpdate({
	ddmStructureLayoutId,
	defaultLanguageId,
	nextEditingLanguageId,
	pages,
	portletNamespace,
	preserveValue,
	prevEditingLanguageId,
	readOnly,
}) {
	return (dispatch) => {
		const dataRecordValues = getDataRecordValues({
			nextEditingLanguageId,
			pages,
			preserveValue,
			prevEditingLanguageId,
		});

		fetch(
			`/o/data-engine/v2.0/data-layouts/${ddmStructureLayoutId}/context`,
			{
				body: JSON.stringify({
					dataRecordValues,
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
				const visitor = new PagesVisitor(response.pages);
				const newPages = visitor.mapFields(
					(field) => {
						if (!field.localizedValue) {
							field.localizedValue = {};
						}

						if (!field.localizable) {
							field.localizedValue = {
								[defaultLanguageId]: field.value,
							};
						}

						return {
							...field,
							...getFieldProperties(field.fieldName, pages),
							editingLanguageId: nextEditingLanguageId,
						};
					},
					true,
					true
				);

				dispatch({
					payload: {
						defaultLanguageId,
						editingLanguageId: nextEditingLanguageId,
						pages: newPages,
					},
					type: EVENT_TYPES.LANGUAGE.CHANGE,
				});
			});
	};
}
