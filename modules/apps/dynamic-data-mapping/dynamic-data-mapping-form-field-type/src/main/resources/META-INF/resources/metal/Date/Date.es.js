import '../FieldBase/FieldBase.es';
import './DateRegister.soy.js';
import 'clay-icon';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Date.soy.js';

/**
 * Date.
 * @extends Component
 */

class Date extends Component {
	static STATE = {

		/**
		 * @default 'string'
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		dataType: Config.string().value('date'),

		/**
		 * @default false
		 * @instance
		 * @memberof Date
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Date
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Date
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Date
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default __/__/____
		 * @instance
		 * @memberof Date
		 * @type {?string}
		 */

		placeholder: Config.string().value('__/__/____'),

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
		 * @memberof Date
		 * @type {?bool}
		 */

		required: Config.bool().value(false),

		/**
		 * @default true
		 * @instance
		 * @memberof Date
		 * @type {?bool}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Date
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('date'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Date
		 * @type {?(string|undefined)}
		 */

		value: Config.string()
	};
}

Soy.register(Date, templates);

export default Date;