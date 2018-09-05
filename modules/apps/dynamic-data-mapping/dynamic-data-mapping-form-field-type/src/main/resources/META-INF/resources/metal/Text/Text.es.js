import './TextRegister.soy.js';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Text.soy.js';
import {Config} from 'metal-state';

class Text extends Component {
	static STATE = {

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		displayStyle: Config.string().value('singleline'),

		/**
		 * @default false
		 * @instance
		 * @memberof Text
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(true),

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

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

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

		value: Config.string()
	};

	_handleFieldChanged(event) {
		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent: event,
				value: event.target.value
			}
		);
	}
}

Soy.register(Text, templates);

export default Text;