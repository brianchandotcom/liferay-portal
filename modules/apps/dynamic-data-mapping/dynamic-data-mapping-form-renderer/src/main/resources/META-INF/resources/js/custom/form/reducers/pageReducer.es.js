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

import * as FormSupport from '../../../util/FormSupport.es';
import {getUid} from '../../../util/formId.es';
import {EVENT_TYPES} from '../eventTypes.es';

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
export default (state, action, config) => {
	switch (action.type) {
		case EVENT_TYPES.SUCCESS_PAGE: {
			const {pages, paginationMode} = state;
			const {defaultLanguageId} = config;
			const {enabled} = action.payload;

			let newPages = [...pages];

			if (enabled) {
				newPages = [
					...newPages,
					{
						contentRenderer: 'success',
						defaultLanguageId,
						paginationItemRenderer: `${paginationMode}_success`,
						rows: [],
						successPageSettings: action.payload,
					},
				];
			}
			else {
				newPages = newPages.filter(
					({contentRenderer}) => contentRenderer !== 'success'
				);
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
		default:
			return state;
	}
};
