const identity = value => value;

class PagesVisitor {
	constructor(pages) {
		this._pages = [...pages];
	}

	_map(pageMapper, rowMapper, columnMapper, fieldFn) {
		return this._pages.map(
			(page, pageIndex) => {
				const newPage = {
					...page,
					...pageMapper(page, pageIndex)
				};

				return {
					...newPage,
					rows: newPage.rows.map(
						(row, rowIndex) => {
							const newRow = {
								...row,
								...rowMapper(row, rowIndex, pageIndex)
							};

							return {
								...newRow,
								columns: newRow.columns.map(
									(column, columnIndex) => {
										const newColumn = {
											...column,
											...columnMapper(column, columnIndex, rowIndex, pageIndex)
										};

										return {
											...newColumn,
											fields: fieldFn(newColumn.fields, columnIndex, rowIndex, pageIndex)
										};
									}
								)
							};
						}
					)
				};
			}
		);
	}

	dispose() {
		this._pages = null;
	}

	mapFields(mapper) {
		return this._map(
			identity,
			identity,
			identity,
			(fields, ...args) => {
				return fields.map(
					(field, fieldIndex) => {
						const newField = {
							...field,
							...mapper(field, fieldIndex, ...args)
						};

						return newField;
					}
				);
			}
		);
	}

	mapPages(mapper) {
		return this._map(mapper, identity, identity, identity);
	}

	mapRows(mapper) {
		return this._map(identity, mapper, identity, identity);
	}

	mapColumns(mapper) {
		return this._map(identity, identity, mapper, identity);
	}

	/**
	 * Find a field based on the fieldName property
	 * @param {string} fieldName
	 * @returns {object} a form field
	 */
	findField(condition) {
		let conditionField;

		this._map(
			identity,
			identity,
			identity,
			(fields, ...args) => {
				const field = fields.find(
					(field, fieldIndex) => {
						condition(field, fieldIndex, ...args);

						return condition(field, fieldIndex, ...args);
					}
				);

				if (field) {
					conditionField = field;
				}
			}
		);

		return conditionField;
	}
}

export {PagesVisitor};