import '../FieldBase/FieldBase.es';
import './TextRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Text.soy.js';
import {Config} from 'metal-state';

class Text extends Component {
	willReceiveState(changes) {
		if (changes.value) {
			this.setState({
				_value: changes.value.newVal
			});
		}
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value
		});
	}

	_handleFieldChanged(event) {
		this.setState(
			{
				value: event.target.value
			},
			() => {
				this.emit('fieldEdited', {
					fieldInstance: this,
					originalEvent: event,
					value: event.target.value
				});
			}
		);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value
		});
	}

	_internalValueFn() {
		const {value} = this;

		return value;
	}
}

Text.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	_value: Config.string()
		.internal()
		.valueFn('_internalValueFn'),

	/**
	 * @default 'string'
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('string'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	displayStyle: Config.string().value('singleline'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	errorMessage: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	predefinedValue: Config.string().value(''),

	/**
	 * @default false
	 * @instance
	 * @memberof Text
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
	 * @memberof Text
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Text
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
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

	type: Config.string().value('text'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	value: Config.string().value('')
};

Soy.register(Text, templates);

export default Text;
