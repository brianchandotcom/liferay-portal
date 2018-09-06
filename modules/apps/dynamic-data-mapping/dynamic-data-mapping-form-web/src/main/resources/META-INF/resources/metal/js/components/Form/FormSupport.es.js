import {PagesVisitor} from '../../util/visitors.es';

const implAddRow = (size, fields) => {
	return {
		columns: [
			{
				fields,
				size
			}
		]
	};
};

const addRow = (pages, indexToAddRow, pageIndex, newRow = implAddRow(12, [])) => {
	pages[Number(pageIndex)].rows.splice(Number(indexToAddRow), 0, newRow);

	return pages;
};

const addFieldToColumn = (pages, pageIndex, rowIndex, columnIndex, field) => {
	if (!field) {
		throw new Error(
			`It is not possible to add the field to column (${pageIndex}, ${rowIndex}, ${columnIndex}) when the field is not passed.`
		);
	}
	else {
		const numberOfRows = pages[Number(pageIndex)].rows.length;
		if (rowIndex >= numberOfRows) {
			pages = addRow(pages, numberOfRows, pageIndex);
		}
		pages[Number(pageIndex)].rows[Number(rowIndex)].columns[
			Number(columnIndex)
		].fields.push(field);
	}

	return pages;
};

const checkEmptyPage = pages => {
	let empty = true;
	const visitor = new PagesVisitor(pages);

	visitor.mapFields(
		field => {
			empty = false;
		}
	);

	return empty;
};

const setColumnFields = (pages, pageIndex, rowIndex, columnIndex, fields = []) => {
	if (!fields.length) {
		throw new Error(
			`Can not add empty fields to column (${pageIndex}, ${rowIndex}, ${columnIndex}), use removeFields for this.`
		);
	}
	else {
		pages[Number(pageIndex)].rows[Number(rowIndex)].columns[Number(columnIndex)].fields = fields;
	}

	return pages;
};

const removeColumn = (pages, pageIndex, rowIndex, columnIndex) => {
	pages[Number(pageIndex)].rows[Number(rowIndex)].columns.splice(
		Number(columnIndex)
	);

	return pages;
};

const removeFields = (pages, pageIndex, rowIndex, columnIndex) => {
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

const removeRow = (pages, pageIndex, rowIndex) => {
	pages[Number(pageIndex)].rows.splice(Number(rowIndex), 1);

	return pages;
};

const generateFieldName = type => `${type}${Date.now()}`;

const getColumn = (pages, pageIndex, rowIndex, columnIndex) => {
	const row = getRow(pages, pageIndex, rowIndex);

	return row.columns[Number(columnIndex)];
};

const getField = (pages, pageIndex, rowIndex, columnIndex) => {
	return getColumn(pages, pageIndex, rowIndex, columnIndex).fields[0];
};

const getRow = (pages, pageIndex, rowIndex) => {
	return pages[Number(pageIndex)].rows[Number(rowIndex)];
};

const rowHasFields = (pages, pageIndex, rowIndex) => {
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

const getIndexes = node => {
	const columnIndex = node.getAttribute('data-ddm-field-column');
	const pageIndex = node.getAttribute('data-ddm-field-page');
	const rowIndex = node.getAttribute('data-ddm-field-row');

	return {
		columnIndex: Number(columnIndex),
		pageIndex: Number(pageIndex),
		rowIndex: Number(rowIndex)
	};
};

const updateField = (
	pages,
	fieldName,
	properties
) => {
	const visitor = new PagesVisitor(pages);

	const newPages = visitor.mapFields(
		field => {
			if (fieldName === field.fieldName) {
				field = {
					...field,
					...properties
				};
			}
			return field;
		}
	);

	return newPages;
};

export default {
	addFieldToColumn,
	addRow,
	checkEmptyPage,
	generateFieldName,
	getColumn,
	getField,
	getIndexes,
	getRow,
	implAddRow,
	removeColumn,
	removeFields,
	removeRow,
	rowHasFields,
	setColumnFields,
	updateField
};