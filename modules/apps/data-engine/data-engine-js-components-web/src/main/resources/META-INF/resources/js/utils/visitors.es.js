/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		const recursiveMapFields = (fields, ...args) => {
			return fields.map((field, fieldIndex) => {
				const newField = {
					...(merge ? field : null),
					...mapper(field, fieldIndex, ...args),
				};

				const nestedFields = newField.nestedFields;
				if (includeNestedFields && nestedFields) {
					newField.nestedFields = recursiveMapFields(
						nestedFields,
						...args,
						newField
					);
				}

				return newField;
			});
		};

		return this._map(identity, identity, identity, recursiveMapFields);
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

	visitFields(evaluateField) {
		const evaluateNode = (node) => {
			if (!node) {
				return false;
			}

			if (node.fieldName) {
				return evaluateField(node) || evaluateNode(node.nestedFields);
			}

			if (Array.isArray(node)) {
				return node.some((item) => evaluateNode(item));
			}

			return evaluateNode(node.fields ?? node.rows ?? node.columns);
		};

		return evaluateNode(this._pages);
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
