/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as FormSupport from '../../../utils/FormSupport.es';
import {getUid} from '../../../utils/formId.es';
import {EVENT_TYPES} from '../eventTypes';

const getLocalizedValue = (languageId, key, value) => {
	return {
		[`localized${key.replace(/^\w/, (c) => c.toUpperCase())}`]: {
			[languageId]: value,
		},
	};
};

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default function pageReducer(state, action) {
	switch (action.type) {
		case EVENT_TYPES.SUCCESS_PAGE: {
			const {defaultLanguageId, pages, paginationMode} = state;
			const {enabled} = action.payload;

			const lastPage = pages[pages.length - 1];

			let newPages = pages.filter(
				({contentRenderer}) => contentRenderer !== 'success'
			);

			if (enabled) {
				const successPage =
					lastPage.contentRenderer === 'success'
						? lastPage
						: {id: getUid()};

				newPages = [
					...newPages,
					{
						...successPage,
						contentRenderer: 'success',
						defaultLanguageId,
						paginationItemRenderer: `${paginationMode}_success`,
						rows: [],
						successPageSettings: action.payload,
					},
				];
			}

			return {
				pages: newPages,
				successPageSettings: action.payload,
			};
		}
		case EVENT_TYPES.PAGE.ADD: {
			const {pageIndex} = action.payload;
			const {editingLanguageId, pages} = state;

			return {
				activePage: pageIndex + 1,
				pages: [
					...pages.slice(0, pageIndex + 1),
					{
						description: '',
						enabled: true,
						headerRenderer: 'editable',
						id: getUid(),
						rows: [FormSupport.implAddRow(12, [])],
						showRequiredFieldsWarning: true,
						title: '',
						...getLocalizedValue(editingLanguageId, 'title', ''),
						...getLocalizedValue(
							editingLanguageId,
							'description',
							''
						),
					},
					...pages.slice(pageIndex + 1),
				],
			};
		}
		case EVENT_TYPES.PAGE.DELETE: {
			const {pages} = state;
			const {pageIndex} = action.payload;

			return {
				activePage: Math.max(0, pageIndex - 1),
				pages: pages.filter((page, index) => index !== pageIndex),
			};
		}
		case EVENT_TYPES.PAGE.RESET: {
			const {pageIndex} = action.payload;
			const {editingLanguageId, pages} = state;

			pages[pageIndex] = {
				...pages[pageIndex],
				description: '',
				enabled: true,
				rows: [FormSupport.implAddRow(12, [])],
				showRequiredFieldsWarning: true,
				title: '',
				...getLocalizedValue(editingLanguageId, 'title', ''),
				...getLocalizedValue(editingLanguageId, 'description', ''),
			};

			return {
				pages: [...pages],
			};
		}
		case EVENT_TYPES.PAGE.SWAP: {
			const {pages} = state;
			const {firstIndex, secondIndex} = action.payload;

			const [firstPage, secondPage] = [
				pages[firstIndex],
				pages[secondIndex],
			];

			return {
				pages: pages.map((page, index) => {
					if (index === firstIndex) {
						return secondPage;
					}
					else if (index === secondIndex) {
						return firstPage;
					}

					return page;
				}),
			};
		}
		case EVENT_TYPES.PAGE.TITLE_CHANGE: {
			const {pageIndex, value} = action.payload;
			const {editingLanguageId, pages} = state;

			const {localizedTitle, ...page} = pages[pageIndex];

			pages[pageIndex] = {
				...page,
				localizedTitle: {
					...localizedTitle,
					[editingLanguageId]: value,
				},
				title: value,
			};

			return {
				pages: [...pages],
			};
		}
		case EVENT_TYPES.PAGE.DESCRIPTION_CHANGE: {
			const {pageIndex, value} = action.payload;
			const {editingLanguageId, pages} = state;

			const {localizedDescription, ...page} = pages[pageIndex];

			pages[pageIndex] = {
				...page,
				description: value,
				localizedDescription: {
					...localizedDescription,
					[editingLanguageId]: value,
				},
			};

			return {
				pages: [...pages],
			};
		}
		default:
			return state;
	}
}
