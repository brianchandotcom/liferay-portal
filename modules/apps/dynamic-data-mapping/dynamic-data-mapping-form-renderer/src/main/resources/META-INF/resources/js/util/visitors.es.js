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

const identity = value => value;

class PagesVisitor {
	constructor(pages) {
		this.setPages(pages);
	}

	dispose() {
		this._pages = null;
	}

	findField(condition) {
		let conditionField;

		this._map(identity, identity, identity, (fields, ...args) => {
			const field = fields.find((field, fieldIndex) => {
				condition(field, fieldIndex, ...args);

				return condition(field, fieldIndex, ...args);
			});

			if (field) {
				conditionField = field;
			}
		});

		return conditionField;
	}

	mapColumns(mapper) {
		return this._map(identity, identity, mapper, identity);
	}

	mapFields(mapper, merge = true) {
		return this._map(identity, identity, identity, (fields, ...args) => {
			return fields.map((field, fieldIndex) => {
				if (merge) {
					return {
						...field,
						...mapper(field, fieldIndex, ...args)
					};
				}

				return mapper(field, fieldIndex, ...args);
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

	_map(pageMapper, rowMapper, columnMapper, fieldFn) {
		return this._pages.map((page, pageIndex) => {
			const newPage = {
				...page,
				...pageMapper(page, pageIndex)
			};

			return {
				...newPage,
				rows: newPage.rows.map((row, rowIndex) => {
					const newRow = {
						...row,
						...rowMapper(row, rowIndex, pageIndex)
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
								)
							};

							return {
								...newColumn,
								fields: fieldFn(
									newColumn.fields,
									columnIndex,
									rowIndex,
									pageIndex
								)
							};
						})
					};
				})
			};
		});
	}
}

class RulesVisitor {
	constructor(rules) {
		this.setRules(rules);
	}

	containsField(fieldName) {
		return this._rules.some(rule => {
			const actionsResult = rule.actions.some(({target}) => {
				return target === fieldName;
			});

			const conditionsResult = rule.conditions.some(condition => {
				return condition.operands.some(({type, value}) => {
					return type === 'field' && value === fieldName;
				});
			});

			return actionsResult || conditionsResult;
		});
	}

	containsFieldExpression(fieldName) {
		return this._rules.some(rule => {
			return rule.actions.some(({action, expression}) => {
				return action === 'calculate' && expression.includes(fieldName);
			});
		});
	}

	dispose() {
		this._rules = null;
	}

	mapActions(actionMapper) {
		return this._rules.map(rule => {
			return {
				...rule,
				actions: rule.actions.map(actionMapper)
			};
		});
	}

	mapConditions(conditionMapper) {
		return this._rules.map(rule => {
			return {
				...rule,
				conditions: rule.conditions.map(conditionMapper)
			};
		});
	}

	setRules(rules) {
		this._rules = [...rules];
	}
}

export {PagesVisitor, RulesVisitor};
