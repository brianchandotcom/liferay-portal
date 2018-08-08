import './SelectRegister.soy.js';
import 'clay-icon';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Select.soy.js';

class Select extends Component {
	static STATE = {
		/**
		 * @default false
		 * @instance
		 * @memberof Select
		 * @type {?bool}
		 */

		editable: Config.bool().value(false),

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
		 * @memberof Select
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?array<object>}
		 */

		items: Config.arrayOf(
			Config.shapeOf({
				active: Config.bool().value(false),
				disabled: Config.bool().value(false),
				id: Config.string(),
				inline: Config.bool().value(false),
				label: Config.string(),
				name: Config.string(),
				showLabel: Config.bool().value(true),
				value: Config.string(),
			})
		).value([
			{
				value: 'Option 1',
			},
			{
				value: 'Option 2',
			},
		]),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?bool}
		 */

		open: Config.bool().value(false),

		/**
		 * @default Choose an Option
		 * @instance
		 * @memberof Select
		 * @type {?string}
		 */

		placeholder: Config.string().value('Choose an Option'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?string}
		 */

		predefinedValue: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Select
		 * @type {?bool}
		 */

		required: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof Select
		 * @type {?bool}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?(string|undefined)}
		 */

		value: Config.string(),

		key: Config.string(),
	};

	_handleClickItem(event) {
		const {key} = this;

		this.setState({
			value: event.target.innerText,
			predefinedValue: event.target.innerText,
		});

		this.emit('fieldEdit', {
			value: event.target.innerText,
			key,
			originalEvent: event,
		});
	}

	_handleClick() {
		this.setState({open: !this.open});
	}
}

Soy.register(Select, templates);

export default Select;
