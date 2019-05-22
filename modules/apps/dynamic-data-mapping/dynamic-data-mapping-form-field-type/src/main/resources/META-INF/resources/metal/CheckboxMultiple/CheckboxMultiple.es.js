import '../FieldBase/FieldBase.es';
import './CheckboxMultipleRegister.soy.js';
import 'clay-checkbox';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './CheckboxMultiple.soy.js';

/**
 * CheckboxMultiple.
 * @extends Component
 */

class CheckboxMultiple extends Component {
	handleInputChangeEvent(event) {
		const value = event.delegateTarget.checked;

		this.setState({
			value
		});

		this.emit('fieldEdited', {
			fieldInstance: this,
			originalEvent: event,
			value
		});
	}
}

Soy.register(CheckboxMultiple, templates);

CheckboxMultiple.STATE = {
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
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Checkbox
	 * @type {?bool}
	 */

	inline: Config.bool().value(false),

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
	 * @memberof Checkbox
	 * @type {?(string|undefined)}
	 */

	options: Config.arrayOf(
		Config.shapeOf({
			label: Config.string(),
			name: Config.string(),
			value: Config.string()
		})
	).value([
		{
			label: 'Option 1'
		},
		{
			label: 'Option 2'
		}
	]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Select
	 * @type {?string}
	 */

	predefinedValue: Config.array().value([]),

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
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

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

	value: Config.bool().value(true)
};

export default CheckboxMultiple;
