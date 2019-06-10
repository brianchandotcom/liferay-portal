import '../FieldBase/FieldBase.es';
import './FieldsetRegister.soy.js';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Fieldset.soy.js';
import {Config} from 'metal-state';

class Fieldset extends Component {
	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}
}

Fieldset.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(number|undefined)}
	 */

	columnSize: Config.number(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Fieldset
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(array|undefined)}
	 */

	nestedFields: Config.array(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Fieldset
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @instance
	 * @memberof Fieldset
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Fieldset
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('fieldset'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Fieldset
	 * @type {?(string|undefined)}
	 */

	value: Config.string().value('')
};

Soy.register(Fieldset, templates);

export default Fieldset;
