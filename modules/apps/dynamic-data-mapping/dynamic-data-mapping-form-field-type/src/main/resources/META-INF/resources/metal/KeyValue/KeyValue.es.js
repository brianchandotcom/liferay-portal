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

		readOnly: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof KeyValue
		 * @type {?bool}
		 */

		generateKey: Config.bool().internal(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

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

		key: Config.string(),

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

		value: Config.string()
	}

	_handleKeyInputChanged(event) {
		const {target} = event;
		const originalEvent = target.originalEvent;

		this._updateKey(target.value);

		this.generateKey = false;

		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent,
				property: 'key',
				value: this.key
			}
		);
	}

	_handleValueInputChanged(event) {
		const {target} = event;
		const {value} = target;
		const originalEvent = target.originalEvent;

		if (this.generateKey) {
			this._updateKey(value);
		}

		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent,
				value
			}
		);
	}

	_updateKey(str) {
		this.setState(
			{
				key: this.getGeneratedKey(str)
			}
		);
	}

	getGeneratedKey(str) {
		const key = str.replace(
			/\s(.)/g,
			x => {
				return x.toUpperCase();
			}
		);

		return key.replace(/_|\W/g, '');
	}
}

Soy.register(KeyValue, templates);
export default KeyValue;