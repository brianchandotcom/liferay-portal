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

export default (props, state, {addedToPlaceholder, source, target}) => {
	let {pages} = state;
	const {columnIndex, pageIndex, rowIndex} = source;

	const column = FormSupport.getColumn(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);
	const {fields} = column;

	pages = FormSupport.removeFields(pages, pageIndex, rowIndex, columnIndex);

	if (
		target.rowIndex > pages[pageIndex].rows.length - 1 ||
		addedToPlaceholder
	) {
		pages = FormSupport.addRow(
			pages,
			target.rowIndex,
			target.pageIndex,
			FormSupport.implAddRow(12, fields)
		);
	} else {
		pages = FormSupport.addFieldToColumn(
			pages,
			target.pageIndex,
			target.rowIndex,
			target.columnIndex,
			fields[0]
		);
	}

	pages[pageIndex].rows = FormSupport.removeEmptyRows(pages, pageIndex);

	return {
		pages
	};
};
