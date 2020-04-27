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

const identity = (value) => value;

class PagesVisitor {
	constructor(pages) {
		this.setPages(pages);
	}

	dispose() {
		this._pages = null;
	}

	containsField(fieldName) {
		return !!this.findField((field) => field.fieldName === fieldName);
	}

	findField(condition) {
		let conditionField;

		this.visitFields((field) => {
			if (condition(field)) {
				conditionField = field;

				return true;
			}

			return false;
		});

		return conditionField;
	}

	mapColumns(mapper) {
		return this._map(identity, identity, mapper, identity);
	}

	mapFields(mapper, merge = true, includeNestedFields = false) {
		return this._map(identity, identity, identity, (fields, ...args) => {
			return fields.map((field, fieldIndex) => {
				let mappedField;

				if (merge) {
					mappedField = {
						...field,
						...mapper(field, fieldIndex, ...args),
					};
				}
				else {
					mappedField = mapper(field, fieldIndex, ...args);
				}

				if (includeNestedFields && mappedField.nestedFields) {
					const mapNestedFields = (field) => {
						return {
							...field,
							nestedFields: (field.nestedFields || []).map(
								(nestedField) => {
									let mappedNestedField = mapper(
										nestedField,
										fieldIndex,
										...args,
										field
									);

									if (merge) {
										mappedNestedField = {
											...nestedField,
											...mappedNestedField,
										};
									}

									return mapNestedFields(mappedNestedField);
								}
							),
						};
					};

					return mapNestedFields(mappedField);
				}

				return mappedField;
			});
		});
	}

	mapPages(mapper) {
		return this._map(mapper, identity, identity, identity);
	}

	mapRows(mapper) {
		return this._map(identity, mapper, identity, identity);
	}

	setPages(pages) {
		this._pages = [...pages];
	}

	visitFields(fn) {
		const isFieldNode = (node) =>
			Object.prototype.hasOwnProperty.call(node, 'fieldName');

		const getChildren = (node) => {
			if (isFieldNode(node)) {
				return node.nestedFields || [];
			}

			return node.fields || node.rows || node.columns || [];
		};

		const collection = [...this._pages];

		while (collection.length) {
			const node = collection.shift();

			if (isFieldNode(node) && fn(node)) {
				return true;
			}

			collection.unshift(...getChildren(node));
		}

		return false;
	}

	_map(pageMapper, rowMapper, columnMapper, fieldFn) {
		return this._pages.map((page, pageIndex) => {
			const newPage = {
				...page,
				...pageMapper(page, pageIndex),
			};

			return {
				...newPage,
				rows: newPage.rows.map((row, rowIndex) => {
					const newRow = {
						...row,
						...rowMapper(row, rowIndex, pageIndex),
					};

					return {
						...newRow,
						columns: newRow.columns.map((column, columnIndex) => {
							const newColumn = {
								...column,
								...columnMapper(
									column,
									columnIndex,
									rowIndex,
									pageIndex
								),
							};

							return {
								...newColumn,
								fields: fieldFn(
									newColumn.fields,
									columnIndex,
									rowIndex,
									pageIndex
								),
							};
						}),
					};
				}),
			};
		});
	}
}

class RulesVisitor {
	constructor(rules) {
		this.setRules(rules);
	}

	containsField(fieldName) {
		return this._rules.some((rule) => {
			const actionsResult = rule.actions.some(({target}) => {
				return target === fieldName;
			});

			const conditionsResult = rule.conditions.some((condition) => {
				return condition.operands.some(({type, value}) => {
					return type === 'field' && value === fieldName;
				});
			});

			return actionsResult || conditionsResult;
		});
	}

	containsFieldExpression(fieldName) {
		return this._rules.some((rule) => {
			return rule.actions.some(({action, expression}) => {
				return action === 'calculate' && expression.includes(fieldName);
			});
		});
	}

	dispose() {
		this._rules = null;
	}

	mapActions(actionMapper) {
		return this._rules.map((rule) => {
			return {
				...rule,
				actions: rule.actions.map(actionMapper),
			};
		});
	}

	mapConditions(conditionMapper) {
		return this._rules.map((rule) => {
			return {
				...rule,
				conditions: rule.conditions.map(conditionMapper),
			};
		});
	}

	setRules(rules) {
		this._rules = [...rules];
	}
}

export {PagesVisitor, RulesVisitor};
