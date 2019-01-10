import 'clay-dropdown';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Calculator.soy.js';

/**
 * Calculator.
 * @extends Component
 */

class Calculator extends Component {

	static STATE = {
		calculatorOptions: Config.arrayOf(
			Config.shapeOf(
				{
					label: Config.string(),
					tooltip: Config.string(),
					value: Config.string()
				}
			)
		).value([]),

		deletedFields: Config.arrayOf(Config.string()),

		disableCalculatorField: Config.bool().internal().value(false),

		expression: Config.string().value(''),

		expressionArray: Config.array().valueFn('_expressionArrayValueFn'),

		index: Config.number().value(0),

		resultSelected: Config.string(),

		options: Config.arrayOf(
			Config.shapeOf(
				{
					dataType: Config.string(),
					name: Config.string(),
					options: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								name: Config.string(),
								value: Config.string()
							}
						)
					),
					type: Config.string(),
					value: Config.string()
				}
			)
		).value([]),

		optionsRepeatable: Config.arrayOf(
			Config.shapeOf(
				{
					dataType: Config.string(),
					name: Config.string(),
					options: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								name: Config.string(),
								value: Config.string()
							}
						)
					),
					type: Config.string(),
					value: Config.string()
				}
			)
		).internal().value([]),

		strings: {
			value: {
				addField: Liferay.Language.get('add-field')
			}
		}
	}

	attached() {
		this._resetExpression();
	}

	willReceiveState(changes) {
		if (changes.hasOwnProperty('resultSelected')) {
			this._resetExpression();
		}
		else if (changes.hasOwnProperty('options')) {
			this._keepLatestExpression(changes);
		}
	}

	/**
	 * @private
	 */
	_expressionArrayValueFn() {
		let expressionArray = [];
		const regex = /\(|\)|numeric[0-9]*|[0-9]+|sum\((numeric[0-9]*|[0-9])\)|[+-/*]/g;

		if (this.expression.trim()) {
			expressionArray = this.expression.match(regex);
		}

		return expressionArray;
	}

	_addItemIntoExpression(calculatorOperationFieldSelected, calculatorSymbol, dropdownItemWasSelected, dropdownItemName) {
		let {disableCalculatorField, expressionArray} = this;

		if (dropdownItemWasSelected) {
			if (calculatorOperationFieldSelected) {
				this._setFieldsRepeatable();

				disableCalculatorField = true;

				expressionArray.push(`${dropdownItemName}(`);
			}
			else if (disableCalculatorField) {
				expressionArray.push(`${dropdownItemName})`);

				disableCalculatorField = false;
			}
			else {
				expressionArray.push(dropdownItemName);
			}
		}
		else if (calculatorSymbol == 'backspace') {
			expressionArray.pop();

			const lastElement = expressionArray[expressionArray.length - 1];

			if (lastElement && lastElement.indexOf('(') > 0) {
				disableCalculatorField = true;
			}
			else {
				disableCalculatorField = false;
			}
		}
		else if (calculatorSymbol) {
			expressionArray.push(calculatorSymbol);
		}

		expressionArray = this._formatExpression(expressionArray, expressionArray.join(''));

		this.setState(
			{
				disableCalculatorField
			}
		);

		return expressionArray;
	}

	_formatExpression(expressionArray, expression) {
		const regexRepeatableArithSigns = /[\+\-\*/]{2,}/;
		const regexRepeatableDotSign = /[\.]{2,}/;

		if (expression.match(regexRepeatableArithSigns) || expression.match(regexRepeatableDotSign)) {
			expressionArray.splice(expressionArray.length - 2, 2, expressionArray[expressionArray.length - 1]);
		}

		return expressionArray;
	}

	_handleItemSelection(event) {
		let calculatorOperationFieldSelected;
		let calculatorSymbol = '';
		let dropdownItemName = '';
		let expressionArray = [];

		const {index} = this;

		const keyWasClicked = event.target.dataset;

		const dropdownItemWasSelected = event.data;

		if (dropdownItemWasSelected) {
			dropdownItemName = event.data.item.fieldName;

			if (!dropdownItemName) {
				calculatorOperationFieldSelected = true;
				dropdownItemName = event.data.item.value;
			}
		}
		else if (keyWasClicked) {
			calculatorSymbol = event.target.dataset.calculatorKey;
		}

		expressionArray = this._addItemIntoExpression(calculatorOperationFieldSelected, calculatorSymbol, dropdownItemWasSelected, dropdownItemName);

		this.setState(
			{
				expression: expressionArray.join(''),
				expressionArray
			}
		);

		if (calculatorSymbol || dropdownItemWasSelected) {
			this.emit(
				'editExpression',
				{
					expression: expressionArray.join(''),
					index
				}
			);
		}
	}

	_keepLatestExpression(changes) {
		const {deletedFields} = this;
		let {expressionArray} = this;

		expressionArray = changes.expressionArray.prevVal;

		if (deletedFields) {
			expressionArray = this._updateExpression(expressionArray);
		}

		this.setState(
			{
				expression: expressionArray.join(''),
				expressionArray
			}
		);
	}

	_resetExpression() {
		let {expression, expressionArray} = this;

		expression = '';
		expressionArray = [];

		this.setState(
			{
				expression,
				expressionArray
			}
		);
	}

	_setFieldsRepeatable() {
		const {options} = this;

		let {optionsRepeatable} = this;

		optionsRepeatable = options.filter(
			({repeatable}) => repeatable === true
		);

		this.setState(
			{
				optionsRepeatable
			}
		);
	}

	_updateExpression(expressionArray) {
		const {deletedFields} = this;

		deletedFields.forEach(
			field => {
				const index = expressionArray.indexOf(field);

				if (index != -1) {
					expressionArray.splice(index, 1);
				}

				const indexWithParenthesis = expressionArray.indexOf(`${field})`);

				if (indexWithParenthesis != -1) {
					expressionArray.splice(indexWithParenthesis, 1);
					expressionArray.splice(indexWithParenthesis - 1, 1);
				}
			}
		);

		return expressionArray;
	}
}

Soy.register(Calculator, templates);

export default Calculator;