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

import 'clay-button';

import 'clay-dropdown';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';
import Component from 'metal-component';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {maxPageIndex, pageOptions} from '../../util/pageSupport.es';
import {getFieldProperty} from '../LayoutProvider/util/fields.es';
import templates from './RuleList.soy.js';

/**
 * RuleList.
 * @extends Component
 */

class RuleList extends Component {
	attached() {
		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			dom.on(
				document,
				'mouseup',
				this._handleDocumentMouseUp.bind(this),
				true
			)
		);
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	prepareStateForRender(states) {
		const rules = this._setDataProviderNames(states);

		return {
			...states,
			rules: rules.map(rule => {
				let logicalOperator;

				if (rule['logical-operator']) {
					logicalOperator = rule['logical-operator'].toLowerCase();
				} else if (rule.logicalOperator) {
					logicalOperator = rule.logicalOperator.toLowerCase();
				}

				return {
					...rule,
					actions: rule.actions.map(action => {
						let newAction;

						if (action.action === 'auto-fill') {
							const {inputs, outputs} = action;

							const inputLabel = Object.values(inputs).map(
								input => this._getFieldLabel(input)
							);
							const outputLabel = Object.values(outputs).map(
								output => this._getFieldLabel(output)
							);

							newAction = {
								...action,
								inputLabel,
								outputLabel
							};
						} else if (action.action == 'jump-to-page') {
							newAction = {
								...action,
								label: this._getJumpToPageLabel(rule, action)
							};
						} else {
							newAction = {
								...action,
								label: this._getFieldLabel(action.target)
							};
						}

						return newAction;
					}),
					conditions: rule.conditions.map(condition => {
						if (
							condition.operands.length < 2 &&
							condition.operands[0].type === 'list'
						) {
							condition.operands = [
								{
									label: 'user',
									repeatable: false,
									type: 'user',
									value: 'user'
								},
								{
									...condition.operands[0],
									label: condition.operands[0].value
								}
							];
						}

						return {
							...condition,
							operands: condition.operands.map(
								(operand, index) => {
									const label = this._getOperandLabel(
										condition.operands,
										index
									);

									return {
										...operand,
										label,
										value: label
									};
								}
							)
						};
					}),
					logicalOperator
				};
			}),
			rulesCardOptions: this._getRulesCardOptions()
		};
	}

	_getDataProviderName(id) {
		const {dataProvider} = this;

		return dataProvider.find(data => data.uuid == id).label;
	}

	_getFieldLabel(fieldName) {
		const pages = this.pages;

		return getFieldProperty(pages, fieldName, 'label');
	}

	_getFieldType(fieldName) {
		const pages = this.pages;

		return getFieldProperty(pages, fieldName, 'type');
	}

	_getJumpToPageLabel(rule, action) {
		const {pages} = this;
		let pageLabel = '';

		const fieldTarget = (parseInt(action.target, 10) + 1).toString();
		const maxPageIndexRes = maxPageIndex(rule.conditions, pages);
		const pageOptionsList = pageOptions(pages, maxPageIndexRes);
		const selectedPage = pageOptionsList.find(option => {
			return option.value == fieldTarget;
		});

		if (selectedPage) {
			pageLabel = selectedPage.label;
		}

		return pageLabel;
	}

	_getOperandLabel(operands, index) {
		let label = '';
		const operand = operands[index];

		if (operand.type === 'field') {
			label = this._getFieldLabel(operand.value);
		} else if (operand.type === 'user') {
			label = Liferay.Language.get('user');
		} else if (operand.type !== 'field') {
			const fieldType = this._getFieldType(operands[0].value);

			if (fieldType == 'select' || fieldType === 'radio') {
				label = this._getOptionLabel(operands[0].value, operand.value);
			} else {
				label = operand.value;
			}
		} else {
			label = operand.value;
		}

		return label;
	}

	_getOptionLabel(fieldName, optionValue) {
		const pages = this.pages;

		let fieldLabel = null;

		if (pages && optionValue) {
			const visitor = new PagesVisitor(pages);

			visitor.findField(field => {
				let found = false;

				if (field.fieldName === fieldName && field.options) {
					field.options.some(option => {
						if (option.value == optionValue) {
							fieldLabel = option.label;

							found = true;
						}

						return found;
					});
				}

				return found;
			});
		}

		return fieldLabel;
	}

	_getRulesCardOptions() {
		const rulesCardOptions = [
			{
				label: Liferay.Language.get('edit'),
				settingsItem: 'edit'
			},
			{
				label: Liferay.Language.get('delete'),
				settingsItem: 'delete'
			}
		];

		return rulesCardOptions;
	}

	_handleDocumentMouseUp({target}) {
		const dropdownSettings = dom.closest(target, '.ddm-rule-list-settings');

		if (dropdownSettings) {
			return;
		}

		this.setState({
			dropdownExpandedIndex: -1
		});
	}

	_handleDropdownClicked(event) {
		event.preventDefault();

		const {dropdownExpandedIndex} = this;
		const ruleNode = dom.closest(event.delegateTarget, '.component-action');

		let ruleIndex = parseInt(ruleNode.dataset.ruleIndex, 10);

		if (ruleIndex === dropdownExpandedIndex) {
			ruleIndex = -1;
		}

		this.setState({
			dropdownExpandedIndex: ruleIndex
		});
	}

	_handleRuleCardClicked({data, target}) {
		const cardElement = dom.closest(target.element, '[data-card-id]');
		const cardId = parseInt(cardElement.getAttribute('data-card-id'), 10);

		if (data.item.settingsItem == 'edit') {
			this.emit('ruleEdited', {
				ruleId: cardId
			});
		} else if (data.item.settingsItem == 'delete') {
			this.emit('ruleDeleted', {
				ruleId: cardId
			});
		}
	}

	_setDataProviderNames(states) {
		const {rules} = states;

		if (this.dataProvider) {
			for (let rule = 0; rule < rules.length; rule++) {
				const actions = rules[rule].actions;

				actions.forEach(action => {
					if (action.action === 'auto-fill') {
						const dataProviderName = this._getDataProviderName(
							action.ddmDataProviderInstanceUUID
						);

						action.dataProviderName = dataProviderName;
					}
				});
			}
		}

		return rules;
	}
}

RuleList.STATE = {
	dataProvider: Config.arrayOf(
		Config.shapeOf({
			id: Config.string(),
			name: Config.string(),
			uuid: Config.string()
		})
	),

	dropdownExpandedIndex: Config.number().internal(),

	pages: Config.array().required(),

	roles: Config.arrayOf(
		Config.shapeOf({
			id: Config.string(),
			name: Config.string()
		})
	).value([]),

	/**
	 * @default 0
	 * @instance
	 * @memberof RuleList
	 * @type {?array}
	 */

	rules: Config.arrayOf(
		Config.shapeOf({
			actions: Config.arrayOf(
				Config.shapeOf({
					action: Config.string(),
					ddmDataProviderInstanceUUID: Config.string(),
					expression: Config.string(),
					inputs: Config.object(),
					label: Config.string(),
					outputs: Config.object(),
					target: Config.string()
				})
			),
			conditions: Config.arrayOf(
				Config.shapeOf({
					operands: Config.arrayOf(
						Config.shapeOf({
							label: Config.string(),
							repeatable: Config.bool(),
							type: Config.string(),
							value: Config.string()
						})
					),
					operator: Config.string()
				})
			),
			['logical-operator']: Config.string()
		})
	).value([]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof RuleList
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * @default strings
	 * @instance
	 * @memberof RuleList
	 * @type {!object}
	 */

	strings: Config.object().value({
		'belongs-to': Liferay.Language.get('belongs-to'),
		'calculate-field': Liferay.Language.get('calculate-field-x-as-x'),
		contains: Liferay.Language.get('contains'),
		'equals-to': Liferay.Language.get('is-equal-to'),
		'greater-than': Liferay.Language.get('is-greater-than'),
		'greater-than-equals': Liferay.Language.get(
			'is-greater-than-or-equal-to'
		),
		'is-empty': Liferay.Language.get('is-empty'),
		'less-than': Liferay.Language.get('is-less-than'),
		'less-than-equals': Liferay.Language.get('is-less-than-or-equal-to'),
		'not-contains': Liferay.Language.get('does-not-contain'),
		'not-equals-to': Liferay.Language.get('is-not-equal-to'),
		'not-is-empty': Liferay.Language.get('is-not-empty')
	})
};

Soy.register(RuleList, templates);

export default RuleList;
