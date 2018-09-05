import './RadioRegister.soy.js';
import 'clay-radio';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import {Config} from 'metal-state';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import templates from './Radio.soy.js';

/**
 * Radio.
 * @extends Component
 */

class Radio extends Component {
	static STATE = {

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Radio
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		options: Config.arrayOf(
			Config.shapeOf(
				{
					label: Config.string(),
					name: Config.string(),
					value: Config.string()
				}
			)
		).value(
			[
				{
					label: 'Option 1'
				},
				{
					label: 'Option 2'
				}
			]
		),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		inline: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default Choose an Option
		 * @instance
		 * @memberof Radio
		 * @type {?string}
		 */

		predefinedValue: Config.string().value('Option 1'),

		/**
		 * @default false
		 * @instance
		 * @memberof Radio
		 * @type {?bool}
		 */

		required: Config.bool().value(false),

		/**
		 * @default true
		 * @instance
		 * @memberof Radio
		 * @type {?bool}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Radio
		 * @type {?(string|undefined)}
		 */

		value: Config.string()
	};

	attached() {
		dom.delegate(
			this.element,
			'change',
			'input',
			this._handleValueChanged.bind(this)
		);
	}

	_handleValueChanged(event) {
		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent: event,
				value: event.delegateTarget.value
			}
		);
	}
}

Soy.register(Radio, templates);

export default Radio;