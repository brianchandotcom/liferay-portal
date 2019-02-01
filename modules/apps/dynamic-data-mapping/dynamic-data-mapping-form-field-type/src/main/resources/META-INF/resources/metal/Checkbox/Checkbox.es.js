import '../FieldBase/FieldBase.es';
import './CheckboxRegister.soy.js';
import 'clay-checkbox';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './Checkbox.soy.js';

/**
 * Checkbox.
 * @extends Component
 */

class Checkbox extends Component {
	static STATE = {

		/**
		 * @default 'string'
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		dataType: Config.string().value('boolean'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?bool}
		 */

		evaluable: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof Checkbox
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

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
		 * @memberof Checkbox
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Checkbox
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?string}
		 */

		predefinedValue: Config.bool(),

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
		 * @memberof Checkbox
		 * @type {?bool}
		 */

		required: Config.bool().value(false),

		/**
		 * @default true
		 * @instance
		 * @memberof Checkbox
		 * @type {?bool}
		 */

		showAsSwitcher: Config.bool().value(true),

		/**
		 * @default true
		 * @instance
		 * @memberof Checkbox
		 * @type {?bool}
		 */

		showLabel: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Checkbox
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('checkbox'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Checkbox
		 * @type {?(bool)}
		 */

		value: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Checkbox
		 * @type {?(string|undefined)}
		 */

		key: Config.string()
	};

	handleInputChangeEvent(event) {
		const value = event.delegateTarget.checked;

		this.setState(
			{
				value
			}
		);

		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent: event,
				value
			}
		);
	}
}

Soy.register(Checkbox, templates);

export default Checkbox;