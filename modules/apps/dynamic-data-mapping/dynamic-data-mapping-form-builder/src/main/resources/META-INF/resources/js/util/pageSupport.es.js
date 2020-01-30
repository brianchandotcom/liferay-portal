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

import {sub} from './strings.es.js';

export function pageOptions(pages, maxPageIndex = 0) {
	const pageOptions = [];

	for (
		let pageIndex = maxPageIndex + 2;
		pageIndex <= pages.length;
		pageIndex++
	) {
		let pageTitle = `${pageIndex} ${sub(
			Liferay.Language.get('untitled-page-x-of-x'),
			[pageIndex, pages.length]
		)}`;

		if (pages[pageIndex - 1].title) {
			pageTitle = `${pageIndex} ${pages[pageIndex - 1].title}`;
		}

		pageOptions.push({
			label: pageTitle,
			name: pageIndex.toString(),
			value: pageIndex.toString()
		});
	}

	return pageOptions;
}

export function maxPageIndex(conditions, pages) {
	const pageIndexes = [];
	const visitor = new PagesVisitor(pages);

	if (conditions.length && conditions[0].operands[0].value) {
		conditions.forEach(condition => {
			visitor.mapFields(
				(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
					if (field.fieldName === condition.operands[0].value) {
						pageIndexes.push(pageIndex);
					}
				}
			);
		});
	}

	const maxPageIndex = Math.max(...pageIndexes);

	return isFinite(maxPageIndex) ? maxPageIndex : 0;
}
