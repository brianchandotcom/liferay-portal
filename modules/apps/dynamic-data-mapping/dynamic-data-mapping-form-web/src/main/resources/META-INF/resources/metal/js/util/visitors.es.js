const identity = value => value;

class PagesVisitor {
	constructor(pages) {
		this._pages = [...pages];
	}

	_map(pageMapper, rowMapper, columnMapper, fieldMapper) {
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
											fields: newColumn.fields.map(
												(field, fieldIndex) => {
													const newField = {
														...field,
														...fieldMapper(field, fieldIndex, columnIndex, rowIndex, pageIndex)
													};

													return newField;
												}
											)
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
		return this._map(identity, identity, identity, mapper);
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
	formatPageSettings(namespace, fieldType, newFieldName) {
		const translationManager = Liferay.component(`${namespace}translationManager`);

		return this.mapFields(
			field => {
				const {fieldName} = field;

				if (fieldName === 'name') {
					field = {
						...field,
						value: newFieldName,
						visible: true
					};
				}
				else if (fieldName === 'label') {
					field = {
						...field,
						localizedValue: {
							...field.localizedValue,
							[translationManager.get('editingLocale')]: fieldType.label
						},
						type: 'text',
						value: fieldType.label
					};
				}
				else if (fieldName === 'type') {
					field = {
						...field,
						value: fieldType.name
					};
				}
				return {
					...field
				};
			}
		);
	}
}

export {PagesVisitor};