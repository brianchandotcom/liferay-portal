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

import {PagesVisitor} from './visitors.es';

export const implAddColumn = (size, fields = []) => {
	return {
		fields,
		size,
	};
};

export const implAddRow = (size, fields) => {
	return {
		columns: [implAddColumn(size, fields)],
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
					...page.rows.slice(indexToAddRow),
				],
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
					...row.columns.slice(indexToAddColumn),
				],
			};
		}

		return newRow;
	});
};

export const isEmptyColumn = (pages, pageIndex, rowIndex, columnIndex) => {
	return (
		pages[pageIndex].rows[rowIndex].columns[columnIndex].fields.length === 0
	);
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
		const newRow = implAddRow(12, [field]);

		return addRow(pages, numberOfRows, pageIndex, newRow);
	}

	if (!isEmptyColumn(pages, pageIndex, rowIndex, columnIndex)) {
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
					fields: [...column.fields, field],
				};
			}

			return column;
		}
	);
};

export const getFieldIndexes = (pages, fieldName) => {
	let indexes = {};
	const visitor = new PagesVisitor(pages);

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (
			(typeof field === 'string' && field === fieldName) ||
			(typeof field === 'object' && field.fieldName === fieldName)
		) {
			indexes = {
				columnIndex,
				fieldIndex,
				pageIndex,
				rowIndex,
			};
		}
	});

	return indexes;
};

export const getParentField = (pages, fieldName) => {
	let parentField;
	const visitor = new PagesVisitor(pages);

	visitor.mapFields(
		(
			field,
			fieldIndex,
			columnIndex,
			rowIndex,
			pageIndex,
			currentParentField
		) => {
			if (field.fieldName === fieldName) {
				parentField = currentParentField;
			}
		},
		true,
		true
	);

	return parentField;
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

export const isEmpty = (pages) => {
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
				}),
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

export const rowHasFields = (pages, pageIndex, rowIndex) => {
	let hasFields = false;
	const page = pages[Number(pageIndex)];

	if (page) {
		const row = page.rows[Number(rowIndex)];

		if (row && row.columns) {
			hasFields = row.columns.some((column) => column.fields.length);
		}
	}

	return hasFields;
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

export const visitNestedFields = ({nestedFields}, fn) => {
	if (Array.isArray(nestedFields)) {
		nestedFields.forEach((nestedField) => {
			fn(nestedField);

			visitNestedFields(nestedField, fn);
		});
	}
};

export const findField = (pages, predicate) => {
	const visitor = new PagesVisitor(pages);

	return visitor.findField(predicate);
};

export const findFieldByFieldName = (pages, fieldName) => {
	return findField(pages, (field) => field.fieldName === fieldName);
};

export const findFieldByName = (pages, name) => {
	return findField(pages, (field) => field.name === name);
};

export const getRow = (pages, pageIndex, rowIndex) => {
	const currentPage = pages[Number(pageIndex)];

	return currentPage.rows[Number(rowIndex)];
};

export const getColumn = (pages, pageIndex, rowIndex, columnIndex) => {
	const row = getRow(pages, pageIndex, rowIndex);

	return row.columns[Number(columnIndex)];
};

export const getColumnPosition = (pages, pageIndex, rowIndex, columnIndex) => {
	const currentPage = pages[pageIndex];

	let currentRow = null;

	currentRow = currentPage.rows[rowIndex];

	if (!currentPage) {
		console.error(
			`Row Index ${rowIndex} cannot be retrieved from ${currentPage}`
		);

		return;
	}

	return columnIndex != -1 && currentRow.columns
		? currentRow.columns.reduce((result, _, index) => {
				if (index <= columnIndex) {
					const column = getColumn(pages, pageIndex, rowIndex, index);

					result += column.size;
				}

				return result;
		  }, 0)
		: 0;
};

export const getField = (context, pageIndex, rowIndex, columnIndex) => {
	let field = getColumn(context, pageIndex, rowIndex, columnIndex).fields[0];

	if (context[pageIndex].nestedFields) {
		field = context[pageIndex].nestedFields.find(
			(nestedField) => nestedField.fieldName === field
		);
	}

	return field;
};

export const getIndexes = (node) => {
	const {ddmFieldColumn, ddmFieldPage, ddmFieldRow} = node.dataset;

	return {
		columnIndex: Number(ddmFieldColumn) || 0,
		pageIndex: Number(ddmFieldPage) || 0,
		rowIndex: Number(ddmFieldRow) || 0,
	};
};

export const getNestedIndexes = (node) => {
	let indexes = [];

	if (node.dataset.ddmFieldRow) {
		indexes = [getIndexes(node)];
	}

	if (!node.parentElement.classList.contains('ddm-form-page')) {
		indexes = [...getNestedIndexes(node.parentElement), ...indexes];
	}

	return indexes;
};

export const updateField = (pages, fieldName, properties) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(
		(field) => {
			if (fieldName === field.fieldName) {
				return {
					...field,
					...properties,
				};
			}

			return field;
		},
		true,
		true
	);
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
					...properties,
				};
			}

			return newColumn;
		}
	);
};
