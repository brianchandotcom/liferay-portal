/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FieldUtil,
	FormSupport,
	PagesVisitor,
} from 'data-engine-js-components-web';

import {EVENT_TYPES} from '../eventTypes.es';

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default function elementSetReducer(state, {payload, type}) {
	switch (type) {
		case EVENT_TYPES.ELEMENT_SET_ADD: {
			const {elementSetPages, indexes} = payload;
			const {activePage, pages} = state;
			const {pageIndex, rowIndex} = indexes ?? {
				pageIndex: activePage,
				rowIndex: indexes.rowIndex,
			};

			const visitor = new PagesVisitor(elementSetPages);

			const newElementSetPages = visitor.mapFields((field) => {
				const name = FieldUtil.generateFieldName(
					pages,
					field.fieldName
				);

				const settingsContextVisitor = new PagesVisitor(
					field.settingsContext.pages
				);

				return {
					...field,
					fieldName: name,
					fieldReference: name,
					settingsContext: {
						...field.settingsContext,
						pages: settingsContextVisitor.mapFields(
							(settingsContextField) => {
								if (
									settingsContextField.fieldName ===
										'fieldReference' ||
									settingsContextField.fieldName === 'name'
								) {
									settingsContextField = {
										...settingsContextField,
										value: name,
									};
								}

								return settingsContextField;
							}
						),
					},
				};
			});

			return {
				pages: pages.map((page, index) => {
					if (index === pageIndex) {
						const rows = FormSupport.removeEmptyRows(pages, index);

						return {
							...page,
							rows: [
								...rows.slice(0, rowIndex),
								...newElementSetPages[0].rows,
								...rows.slice(rowIndex),
							],
						};
					}

					return page;
				}),
			};
		}
		default:
			return state;
	}
}
