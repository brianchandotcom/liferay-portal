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
		console.warn(
			`It is not possible to add the field to column (${pageIndex}, ${rowIndex}, ${columnIndex}) when the field is not passed.`
		);
	}
	else {
		pages[Number(pageIndex)].rows[Number(rowIndex)].columns[
			Number(columnIndex)
		].fields.push(field);
	}

	return pages;
};

const setColumnFields = (pages, pageIndex, rowIndex, columnIndex, fields = []) => {
	if (!fields.length) {
		console.warn(
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
	pages[Number(pageIndex)].rows[Number(rowIndex)].columns[Number(columnIndex)].fields = [];

	return pages;
};

const removeRow = (pages, pageIndex, rowIndex) => {
	pages[Number(pageIndex)].rows.splice(Number(rowIndex), 1);

	return pages;
};

const generateFieldName = field => {
	return field.name + Date.now();
};

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

const hasFieldsRow = (pages, pageIndex, rowIndex) => {
	const row = pages[Number(pageIndex)].rows[Number(rowIndex)].columns;

	return !!row.filter(elem => elem.fields.length).length;
};

const getIndexes = node => {
	const columnIndex = node.getAttribute('data-ddm-field-column');
	const pageIndex = node.getAttribute('data-ddm-field-page');
	const rowIndex = node.getAttribute('data-ddm-field-row');

	return {
		columnIndex:
			typeof columnIndex === 'string' ? Number(columnIndex) : false,
		pageIndex: Number(pageIndex),
		rowIndex: Number(rowIndex)
	};
};

const changeFieldsFromColumn = (
	pages,
	pageIndex,
	rowIndex,
	columnIndex,
	newFields
) => {
	pages[Number(pageIndex)].rows[Number(rowIndex)].columns[Number(columnIndex)].fields = newFields;

	return pages;
};

export default {
	addFieldToColumn,
	addRow,
	changeFieldsFromColumn,
	generateFieldName,
	getColumn,
	getField,
	getIndexes,
	getRow,
	hasFieldsRow,
	implAddRow,
	removeColumn,
	removeFields,
	removeRow,
	setColumnFields
};