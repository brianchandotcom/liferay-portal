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

import {FormSupport} from 'dynamic-data-mapping-form-renderer';
import createSection from './sectionAddedHandler.es';

export default (props, state, {addedToPlaceholder, source, target}) => {
	let {pages} = state;
	const {columnIndex, pageIndex, rowIndex} = source;

	const targetIndexes = FormSupport.getIndexes(target);

	const currentColumn = FormSupport.getColumn(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);

	pages = FormSupport.removeFields(pages, pageIndex, rowIndex, columnIndex);

	if (target.dataset.ddmFieldColumn) {
		pages = createSection(props, {...state, pages}, {
			data: {
				target: target.children[0]
			},
			newField: currentColumn.fields[0]
		}).pages;
	} else if (
		target.rowIndex > pages[pageIndex].rows.length - 1 ||
		addedToPlaceholder
	) {
		pages = FormSupport.addRow(
			pages,
			targetIndexes.rowIndex,
			targetIndexes.pageIndex,
			FormSupport.implAddRow(12, currentColumn.fields)
		);
	}
	else {
		pages = FormSupport.addFieldToColumn(
			pages,
			targetIndexes.pageIndex,
			targetIndexes.rowIndex,
			targetIndexes.columnIndex,
			currentColumn.fields[0]
		);
	}

	pages[pageIndex].rows = FormSupport.removeEmptyRows(pages, pageIndex);

	return {
		pages,
	};
};
