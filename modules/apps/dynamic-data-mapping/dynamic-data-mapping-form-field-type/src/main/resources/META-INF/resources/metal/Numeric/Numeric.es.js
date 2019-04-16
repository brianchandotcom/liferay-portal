import '../FieldBase/FieldBase.es';
import './NumericRegister.soy.js';
import Component from 'metal-component';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import Soy from 'metal-soy';
import templates from './Numeric.soy.js';
import vanillaTextMask from 'vanilla-text-mask';
import {Config} from 'metal-state';

class Numeric extends Component {
	static STATE = {

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		_value: Config.string().internal().valueFn('_internalValueFn'),

		/**
		 * @default 'integer'
		 * @instance
		 * @memberof Numeric
		 * @type {string}
		 */

		dataType: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		name: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		placeholder: Config.string(),

		/**
		 * @instance
		 * @memberof Numeric
		 * @type {?(string)}
		 */

		predefinedValue: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Numeric
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(bool|undefined)}
		 */

		repeatable: Config.bool(),

		/**
		 * @default false
		 * @instance
		 * @memberof Numeric
		 * @type {?(bool|undefined)}
		 */

		required: Config.bool().value(false),

		/**
		 * @default true
		 * @instance
		 * @memberof Numeric
		 * @type {?(bool|undefined)}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default object
		 * @instance
		 * @memberof Numeric
		 * @type {object}
		 */

		symbols: Config.shapeOf(
			{
				decimalSymbol: Config.string(),
				thousandsSeparator: Config.string()
			}
		).value(
			{
				decimalSymbol: '.',
				thousandsSeparator: ','
			}
		),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		tooltip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('numeric'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Numeric
		 * @type {?(string|undefined)}
		 */

		value: Config.string()
	};

	applyMask() {
		const {dataType, element} = this;
		const inputElement = element.querySelector('input');

		if (this.maskInstance) {
			this.maskInstance.destroy();
		}

		const numberMaskOptions = this.getMaskConfig(dataType);

		const mask = createNumberMask(numberMaskOptions);

		this.maskInstance = vanillaTextMask(
			{
				inputElement,
				mask
			}
		);
	}

	attached() {
		this.applyMask();
	}

	disposed() {
		if (this.maskInstance) {
			this.maskInstance.destroy();
		}
	}

	getMaskConfig(dataType) {
		const {symbols} = this;

		let config = {
			allowLeadingZeroes: true,
			includeThousandsSeparator: false,
			prefix: ''
		};

		if (dataType === 'double') {
			config = {
				...config,
				allowDecimal: true,
				decimalLimit: null,
				decimalSymbol: symbols.decimalSymbol
			};
		}

		return config;
	}

	willReceiveState(changes) {
		if (changes.dataType && changes.dataType.newVal) {
			this.applyMask();
		}

		if (changes.value) {
			this.setState(
				{
					_value: changes.value.newVal
				}
			);
		}
	}

	_handleFieldChanged(event) {
		const value = event.target.value;

		this.setState(
			{
				value
			},
			() => this.emit(
				'fieldEdited',
				{
					fieldInstance: this,
					originalEvent: event,
					value
				}
			)
		);
	}

	_internalValueFn() {
		const {value} = this;

		return value;
	}
}

Soy.register(Numeric, templates);

export default Numeric;