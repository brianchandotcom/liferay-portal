import {Config} from 'metal-state';
import Component from 'metal-component';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import Soy from 'metal-soy';

import templates from './KeyValue.soy.js';
import './KeyValueRegister.soy.js';

/**
 * KeyValue.
 * @extends Component
 */

class KeyValue extends Component {
	static STATE = {

		/**
		 * @default false
		 * @instance
		 * @memberof KeyValue
		 * @type {?bool}
		 */

		editable: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof KeyValue
		 * @type {?bool}
		 */

		generateName: Config.bool().internal(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		helpText: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof KeyValue
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof KeyValue
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?string}
		 */

		predefinedValue: Config.string().value('Option 1'),

		/**
		 * @default false
		 * @instance
		 * @memberof KeyValue
		 * @type {?bool}
		 */

		required: Config.bool().value(false),

		/**
		 * @default true
		 * @instance
		 * @memberof KeyValue
		 * @type {?bool}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof KeyValue
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
			 * @default undefined
			 * @instance
			 * @memberof KeyValue
			 * @type {?(bool)}
			 */

		value: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof KeyValue
		 * @type {?(string|undefined)}
		*/

		key: Config.string(),

		/**
		 * @default undefined
		* @instance
		* @memberof KeyValue
		* @type {?(string|undefined)}
		*/

		keyValue: Config.string().internal()
	}

	_formatInput(str) {
		let key = '';

		key = str.replace(
			/\s(.)/g,
			x => {
				return x.toUpperCase();
			}
		);

		key = key.replace(/_|\W/g, '');

		return key;
	}

	_handleValueChange(event) {
		const {key} = this;

		if (!this.generateName) {
			this._handleKeyChange(
				{
					target: {
						originalEvent: event,
						value: event.target.value
					}
				}
			);
		}

		this.emit(
			'fieldEdit',
			{
				key,
				originalEvent: event,
				value: event.target.value
			}
		);
	}

	_handleKeyChange(event) {
		const {target} = event;
		const originalEvent = target.originalEvent;

		if (event.target.className == 'key-value-input') {
			this.generateName = false;
		}

		this.keyValue = this._formatInput(target.value);

		this.emit(
			'fieldEdit',
			{
				key: 'keyValue',
				originalEvent,
				value: this.keyValue
			}
		);
	}
}

Soy.register(KeyValue, templates);
export default KeyValue;