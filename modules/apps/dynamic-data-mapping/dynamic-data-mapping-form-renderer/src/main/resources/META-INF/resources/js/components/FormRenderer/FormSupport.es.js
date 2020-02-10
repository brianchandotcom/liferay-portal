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

import {PagesVisitor} from '../../util/visitors.es';

export const implAddColumn = (size, fields = []) => {
	return {
		fields,
		size
	};
};

export const implAddRow = (size, fields) => {
	return {
		columns: [implAddColumn(size, fields)]
	};
};

export const addRow = (
	pages,
	indexToAddRow,
	pageIndex,
	newRow = implAddRow(12, [])
) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapPages((page, currentPageIndex) => {
		let newPage = page;

		if (pageIndex === currentPageIndex) {
			newPage = {
				...page,
				rows: [
					...page.rows.slice(0, indexToAddRow),
					newRow,
					...page.rows.slice(indexToAddRow)
				]
			};
		}

		return newPage;
	});
};

export const addColumn = (
	pages,
	indexToAddColumn,
	pageIndex,
	rowIndex,
	newColumn = implAddColumn(11, [])
) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapRows((row, currentRowIndex, currentPageIndex) => {
		let newRow = row;

		if (currentRowIndex === rowIndex && currentPageIndex === pageIndex) {
			newRow = {
				...row,
				columns: [
					...row.columns.slice(0, indexToAddColumn),
					newColumn,
					...row.columns.slice(indexToAddColumn)
				]
			};
		}

		return newRow;
	});
};

export const addFieldToColumn = (
	pages,
	pageIndex,
	rowIndex,
	columnIndex,
	field
) => {
	const numberOfRows = pages[pageIndex].rows.length;

	if (rowIndex >= numberOfRows) {
		pages = addRow(pages, numberOfRows, pageIndex);
	}
	else if (!isEmptyColumn(pages, pageIndex, rowIndex, columnIndex)) {
		pages = addRow(pages, rowIndex, pageIndex);
	}

	const visitor = new PagesVisitor(pages);

	return visitor.mapColumns(
		(column, currentColumnIndex, currentRowIndex, currentPageIndex) => {
			if (
				currentColumnIndex === columnIndex &&
				currentRowIndex === rowIndex &&
				currentPageIndex === pageIndex
			) {
				return {
					...column,
					fields: [...column.fields, field]
				};
			}

			return column;
		}
	);
};

export const isEmptyColumn = (pages, pageIndex, rowIndex, columnIndex) => {
	return (
		pages[pageIndex].rows[rowIndex].columns[columnIndex].fields.length === 0
	);
};

export const isEmptyRow = (pages, pageIndex, rowIndex) => {
	return pages[pageIndex].rows[
		rowIndex
	].columns.every((column, columnIndex) =>
		isEmptyColumn(pages, pageIndex, rowIndex, columnIndex)
	);
};

export const isEmptyPage = (pages, pageIndex) => {
	return pages[pageIndex].rows.every((row, rowIndex) =>
		isEmptyRow(pages, pageIndex, rowIndex)
	);
};

export const isEmpty = pages => {
	return pages.every((page, pageIndex) => isEmptyPage(pages, pageIndex));
};

export const setColumnFields = (
	pages,
	pageIndex,
	rowIndex,
	columnIndex,
	fields = []
) => {
	const numberOfRows = pages[Number(pageIndex)].rows.length;

	if (numberOfRows - 1 < rowIndex) {
		pages = addRow(pages, rowIndex, pageIndex);
		pages = addFieldToColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex,
			fields
		);
	}
	else {
		pages[Number(pageIndex)].rows[Number(rowIndex)].columns[
			Number(columnIndex)
		].fields = fields;
	}

	return pages;
};

export const removeColumn = (pages, pageIndex, rowIndex, columnIndex) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapRows((row, currentRowIndex, currentPageIndex) => {
		let newRow = row;

		if (currentRowIndex === rowIndex && currentPageIndex === pageIndex) {
			newRow = {
				...row,
				columns: row.columns.filter((col, currentColumnIndex) => {
					return currentColumnIndex !== columnIndex;
				})
			};
		}

		return newRow;
	});
};

export const removeFields = (pages, pageIndex, rowIndex, columnIndex) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapColumns(
		(column, currentColumnIndex, currentRowIndex, currentPageIndex) => {
			const newColumn = {...column};

			if (
				currentPageIndex === pageIndex &&
				currentRowIndex === rowIndex &&
				currentColumnIndex === columnIndex
			) {
				newColumn.fields = [];
			}

			return newColumn;
		}
	);
};

export const removeEmptyRows = (pages, pageIndex) => {
	return pages[pageIndex].rows.reduce((result, next, index) => {
		if (rowHasFields(pages, pageIndex, index)) {
			result = [...result, next];
		}

		return result;
	}, []);
};

export const removeRow = (pages, pageIndex, rowIndex) => {
	pages[Number(pageIndex)].rows.splice(Number(rowIndex), 1);

	return pages;
};

export const findFieldByName = (pages, name) => {
	let field = null;
	const visitor = new PagesVisitor(pages);

	visitor.mapFields(currentField => {
		if (currentField.fieldName === name) {
			field = currentField;
		}
	});

	return field;
};

export const getColumn = (pages, pageIndex, rowIndex, columnIndex) => {
	const row = getRow(pages, pageIndex, rowIndex);

	return row.columns[Number(columnIndex)];
};

export const getColumnPosition = (pages, pageIndex, rowIndex, columnIndex) => {
	return columnIndex != -1
		? pages[pageIndex].rows[rowIndex].columns.reduce(
				(result, next, index) => {
					if (index <= columnIndex) {
						const column = getColumn(
							pages,
							pageIndex,
							rowIndex,
							index
						);

						result += column.size;
					}

					return result;
				},
				0
		  )
		: 0;
};

export const getField = (pages, pageIndex, rowIndex, columnIndex) => {
	return getColumn(pages, pageIndex, rowIndex, columnIndex).fields[0];
};

export const getRow = (pages, pageIndex, rowIndex) => {
	return pages[Number(pageIndex)].rows[Number(rowIndex)];
};

export const rowHasFields = (pages, pageIndex, rowIndex) => {
	let hasFields = false;
	const page = pages[Number(pageIndex)];

	if (page) {
		const row = page.rows[Number(rowIndex)];

		if (row) {
			hasFields = row.columns.some(column => column.fields.length);
		}
	}

	return hasFields;
};

export const getIndexes = node => {
	const {ddmFieldColumn, ddmFieldPage, ddmFieldRow} = node.dataset;

	return {
		columnIndex: Number(ddmFieldColumn) || 0,
		pageIndex: Number(ddmFieldPage) || 0,
		rowIndex: Number(ddmFieldRow) || 0
	};
};

export const updateField = (pages, fieldName, properties) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(field => {
		if (fieldName === field.fieldName) {
			field = {
				...field,
				...properties
			};
		}

		return field;
	});
};

export const updateColumn = (
	pages,
	pageIndex,
	rowIndex,
	columnIndex,
	properties
) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapColumns(
		(column, currentColumnIndex, currentRowIndex, currentPageIndex) => {
			let newColumn = column;

			if (
				currentColumnIndex === columnIndex &&
				currentRowIndex === rowIndex &&
				currentPageIndex === pageIndex
			) {
				newColumn = {
					...column,
					...properties
				};
			}

			return newColumn;
		}
	);
};
