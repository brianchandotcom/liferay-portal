import 'clay-button';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import templates from './RuleList.soy.js';

/**
 * LayoutRenderer.
 * @extends Component
 */

class RuleList extends Component {

	static STATE = {
		pages: Config.array().required(),

		/**
		 * @default 0
		 * @instance
		 * @memberof RuleList
		 * @type {?array}
		 */

		rules: Config.arrayOf(
			Config.shapeOf(
				{
					actions: Config.arrayOf(
						Config.shapeOf(
							{
								action: Config.string(),
								expression: Config.string(),
								label: Config.string(),
								target: Config.string()
							}
						)
					),
					conditions: Config.arrayOf(
						Config.shapeOf(
							{
								operands: Config.arrayOf(
									Config.shapeOf(
										{
											label: Config.string(),
											repeatable: Config.bool(),
											type: Config.string(),
											value: Config.string()
										}
									)
								),
								operator: Config.string()
							}
						)
					),
					logicalOperator: Config.string()
				}
			)
		),

		/**
		 * @default undefined
		 * @instance
		 * @memberof RuleList
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof RuleList
		 * @type {!string}
		 */

		strings: Config.object().value(
			{
				and: 'and',
				'auto-fill': 'autofill-x-from-data-provider-x',
				'belongs-to': 'belongs-to',
				'calculate-field': 'calculate-field-x-as-x',
				contains: 'contains',
				delete: 'delete',
				edit: 'edit',
				emptyListText: 'there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first',
				'enable-field': 'enable-x',
				'equals-to': 'is-equal-to',
				field: 'field',
				'greater-than': 'is-greater-than',
				'greater-than-equals': 'is-greater-than-or-equal-to',
				if: 'if',
				'is-empty': 'is-empty',
				'jump-to-page': 'jump-to-page-x',
				'less-than': 'is-less-than',
				'less-than-equals': 'is-less-than-or-equal-to',
				'not-contains': 'does-not-contain',
				'not-equals-to': 'is-not-equal-to',
				'not-is-empty': 'is-not-empty',
				or: 'or',
				'require-field': 'require-x',
				ruleBuilder: 'rule-builder',
				'show-field': 'show-x',
				value: 'value'
			}
		)
	}

	_getFieldLabel(fieldName) {
		const pages = this.pages;

		let fieldLabel;

		if (pages) {
			for (let page = 0; page < pages.length; page++) {
				const rows = pages[page].rows;
				for (let row = 0; row < rows.length; row++) {
					const cols = rows[row].columns;
					for (let col = 0; col < cols.length; col++) {
						const fields = cols[col].fields;
						for (let field = 0; field < fields.length; field++) {
							if (pages[page].rows[row].columns[col].fields[field].fieldName === fieldName) {
								fieldLabel = pages[page].rows[row].columns[col].fields[field].label;
								break;
							}
						}
					}
				}
			}
		}

		return fieldLabel;
	}

	_formatActions(actions) {
		actions.forEach(
			action => {
				action.label = this._getFieldLabel(action.target);

				const expression = action.expression;

				if (expression) {
					action.expression = expression.replace(/\[|\]/g, '');
				}
			}
		);

		return actions;
	}

	_setRules(newRules) {
		for (let rule = 0; rule < newRules.length; rule++) {
			let actions = newRules[rule].actions;

			actions = this._formatActions(actions);

			newRules[rule].actions = actions;

			const logicalOperator = newRules[rule]['logical-operator'].toLowerCase();

			newRules[rule].logicalOperator = logicalOperator;
		}

		return newRules;
	}

	prepareStateForRender(states) {
		return {
			...states,
			rules: states.rules.map(
				rule => {
					return {
						...rule,
						conditions: rule.conditions.map(
							condition => {
								return {
									...condition,
									operands: condition.operands.map(
										operand => {
											return {
												...operand,
												value: operand.type === 'field' ? this._getFieldLabel(operand.value) : operand.value
											};
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

	created() {
		this._eventHandler = new EventHandler();

		const newRules = this.rules;

		this._setRules(newRules);

		this.setState({rules: newRules});
	}

	attached() {
		const addButton = document.querySelector('#addFieldButton');

		const translationManager = document.querySelector('.ddm-translation-manager');

		const formBasicInfo = document.querySelector('.ddm-form-basic-info');

		if (addButton) {
			addButton.classList.remove('hide');
		}

		if (translationManager && formBasicInfo) {
			formBasicInfo.classList.add('hide');
			translationManager.classList.add('hide');
		}

		this._eventHandler.add(
			dom.on('.rule-card-delete', 'click', this.deleteRule.bind(this))
		);
	}

	deleteRule(event) {
		const {delegateTarget} = event;
		const {cardId} = delegateTarget.dataset;

		const currentRules = this.rules;

		currentRules.splice(cardId, 1);

		this.rules = currentRules;

		this.setState(
			{
				rules: currentRules
			}
		);
	}
}

Soy.register(RuleList, templates);

export default RuleList;