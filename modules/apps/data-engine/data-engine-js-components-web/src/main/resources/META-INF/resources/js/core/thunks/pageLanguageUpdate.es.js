/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, getPortletId} from 'frontend-js-web';

import formatFieldValue from '../../utils/formatFieldValue.es';
import setDataRecord from '../../utils/setDataRecord.es';
import {PagesVisitor} from '../../utils/visitors.es';
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

	const {itemSelectorURL, localizedValueEdited} = visitor.findField(
		(field) => field.fieldName === fieldName
	);

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
			`/o/data-engine/v2.0/data-layouts/${ddmStructureLayoutId}/context?p_l_id=${themeDisplay.getPlid()}&p_p_id=${getPortletId(
				portletNamespace
			)}`,
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
					'Accept-Language': nextEditingLanguageId.replaceAll(
						'_',
						'-'
					),
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
