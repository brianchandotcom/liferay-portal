import 'clay-button';
import 'clay-dropdown';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import Component from 'metal-component';
import Soy from 'metal-soy';
import dom from 'metal-dom';

import templates from './RuleList.soy.js';
import {PagesVisitor} from '../../util/visitors.es';

/**
 * LayoutRenderer.
 * @extends Component
 */

class RuleList extends Component {

	static STATE = {
		dataProvider: Config.arrayOf(
			Config.shapeOf(
				{
					id: Config.string(),
					name: Config.string(),
					uuid: Config.string()
				}
			)
		),

		dropdownExpandedIndex: Config.number().internal(),

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
								ddmDataProviderInstanceUUID: Config.string(),
								expression: Config.string(),
								inputs: Config.object(),
								label: Config.string(),
								outputs: Config.object(),
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
					['logical-operator']: Config.string()
				}
			)
		),

		roles: Config.arrayOf(
			Config.shapeOf(
				{
					id: Config.string(),
					name: Config.string()
				}
			)
		).value([]),

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
				and: Liferay.Language.get('and'),
				'auto-fill': Liferay.Language.get('autofill-x-from-data-provider-x'),
				'belongs-to': Liferay.Language.get('belongs-to'),
				'calculate-field': Liferay.Language.get('calculate-field-x-as-x'),
				contains: Liferay.Language.get('contains'),
				delete: Liferay.Language.get('delete'),
				edit: Liferay.Language.get('edit'),
				emptyListText: Liferay.Language.get('there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first'),
				'enable-field': Liferay.Language.get('enable-x'),
				'equals-to': Liferay.Language.get('is-equal-to'),
				field: Liferay.Language.get('field'),
				fromDataProvider: Liferay.Language.get('from-data-provider'),
				'greater-than': Liferay.Language.get('is-greater-than'),
				'greater-than-equals': Liferay.Language.get('is-greater-than-or-equal-to'),
				if: Liferay.Language.get('if'),
				'is-empty': Liferay.Language.get('is-empty'),
				'jump-to-page': Liferay.Language.get('jump-to-page-x'),
				'less-than': Liferay.Language.get('is-less-than'),
				'less-than-equals': Liferay.Language.get('is-less-than-or-equal-to'),
				'not-contains': Liferay.Language.get('does-not-contain'),
				'not-equals-to': Liferay.Language.get('is-not-equal-to'),
				'not-is-empty': Liferay.Language.get('is-not-empty'),
				or: Liferay.Language.get('or'),
				'require-field': Liferay.Language.get('require-x'),
				rules: Liferay.Language.get('rules'),
				'show-field': Liferay.Language.get('show-x'),
				value: Liferay.Language.get('value')
			}
		)
	}

	created() {
		this._eventHandler = new EventHandler();

		const newRules = this.rules;

		this._setRules(newRules);

		this.setState({rules: newRules});
	}

	attached() {
		this._eventHandler.add(
			dom.on(document, 'mousedown', this._handleDocumentMouseDown.bind(this), true)
		);
	}

	/**
	 * @inheritDoc
	 */

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	_handleDocumentMouseDown({target}) {
		const dropdownNode = dom.closest(target, '.dropdown-menu');
		const dropdownSettings = dom.closest(target, '.ddm-rule-list-settings');

		if (dropdownNode || dropdownSettings) {
			return;
		}

		this.setState(
			{
				dropdownExpandedIndex: -1
			}
		);
	}

	/**
	 * Find a field label based on fieldName
	 * @param {string} fieldName
	 * @return {string} the field label
	 */
	_getFieldLabel(fieldName) {
		const pages = this.pages;

		let labelField = null;

		if (pages && fieldName) {
			const visitor = new PagesVisitor(pages);

			const {label} = visitor.findField(field => field.fieldName == fieldName);

			labelField = label;
		}

		return labelField;
	}

	_handleRuleCardClicked({data, target}) {
		const cardId = target.element.closest('[data-card-id]').getAttribute('data-card-id');

		if (data.item.settingsItem == 'edit') {
			this.emit(
				'ruleEdited',
				{
					ruleId: cardId
				}
			);
		}
		else if (data.item.settingsItem == 'delete') {
			this.emit(
				'ruleDeleted',
				{
					ruleId: cardId
				}
			);
		}
	}

	_handleDropdownClicked(event) {
		event.preventDefault();

		const {dropdownExpandedIndex} = this;
		const ruleNode = dom.closest(event.delegateTarget, '.component-action');

		let ruleIndex = parseInt(ruleNode.dataset.ruleIndex, 10);

		if (ruleIndex === dropdownExpandedIndex) {
			ruleIndex = -1;
		}

		this.setState(
			{
				dropdownExpandedIndex: ruleIndex
			}
		);
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

	_getDataProviderName(id) {
		const {dataProvider} = this;

		return dataProvider.find(data => data.uuid == id).label;
	}

	_setRules(newRules) {
		for (let rule = 0; rule < newRules.length; rule++) {
			const actions = newRules[rule].actions;
			const conditions = newRules[rule].conditions;

			actions.forEach(
				action => {
					if (action.action === 'auto-fill') {
						const inputValue = Object.values(action.inputs).map(fieldName => this._getFieldLabel(fieldName));

						action.inputValue = inputValue.toString();
						const outputValue = Object.values(action.outputs).map(fieldName => this._getFieldLabel(fieldName));

						action.outputValue = outputValue.toString();
					}
				}
			);

			newRules[rule].conditions = conditions.map(
				condition => {
					if (condition.operands.length < 2 && condition.operands[0].type === 'list') {
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

					return condition;
				}
			);

			let logicalOperator;

			if (newRules[rule]['logical-operator']) {
				logicalOperator = newRules[rule]['logical-operator'].toLowerCase();
				newRules[rule].logicalOperator = logicalOperator;

			}
			else if (newRules[rule].logicalOperator) {
				logicalOperator = newRules[rule].logicalOperator.toLowerCase();
				newRules[rule].logicalOperator = logicalOperator;
			}
		}

		return newRules;
	}

	_setDataProviderNames(states) {
		const newRules = states.rules;

		if (this.dataProvider) {
			for (let rule = 0; rule < newRules.length; rule++) {
				const actions = newRules[rule].actions;

				actions.forEach(
					action => {
						if (action.action === 'auto-fill') {
							const dataProviderName = this._getDataProviderName(action.ddmDataProviderInstanceUUID);

							action.dataProviderName = dataProviderName;
						}
					}
				);
			}
		}

		return newRules;
	}

	_getRulesCardOptions() {
		const rulesCardOptions = [
			{
				'label': Liferay.Language.get('edit'),
				'settingsItem': 'edit'
			},
			{
				'label': Liferay.Language.get('delete'),
				'settingsItem': 'delete'
			}
		];

		return rulesCardOptions;
	}

	prepareStateForRender(states) {
		const {roles} = this;
		const rules = this._setDataProviderNames(states);

		return {
			...states,
			rules: rules.map(
				rule => {
					return {
						...rule,
						actions: rule.actions.map(
							actionItem => {
								return {
									...actionItem,
									label: this._getFieldLabel(actionItem.target),
									target: this._getFieldLabel(actionItem.target)
								};
							}
						),
						conditions: rule.conditions.map(
							condition => {
								return {
									...condition,
									operands: condition.operands.map(
										(operand, index) => {
											let {label} = operand;

											if (operand.type === 'field') {
												label = this._getFieldLabel(operand.value);
											}
											else if (index == 1 && condition.operands[0].type === 'user' && roles.length) {
												label = roles.find(role => role.id === operand.value).label;
											}

											return {
												...operand,
												label,
												value: this._setOperandValue(operand)
											};
										}
									)
								};
							}
						)
					};
				}
			),
			rulesCardOptions: this._getRulesCardOptions()
		};
	}

	_setOperandValue(operand) {
		let field = '';

		if (operand.type === 'field') {
			field = this._getFieldLabel(operand.value);
		}
		else {
			field = operand.value;
		}

		if ((field != '') && (typeof (field) == 'undefined')) {
			field = operand.value;
		}

		return field;
	}
}

Soy.register(RuleList, templates);

export default RuleList;