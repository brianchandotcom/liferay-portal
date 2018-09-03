import './OptionsRegister.soy.js';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import 'dynamic-data-mapping-form-field-type/metal/Text/index.es';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Options.soy.js';

/**
 * Options.
 * @extends Component
 */

class Options extends Component {
	static STATE = {

		/**
		 * @default false
		 * @instance
		 * @memberof Options
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?(string|undefined)}
		 */

		items: Config.arrayOf(
			Config.shapeOf(
				{
					checked: Config.bool().value(false),
					disabled: Config.bool().value(false),
					id: Config.string(),
					inline: Config.bool().value(false),
					label: Config.string(),
					name: Config.string(),
					showLabel: Config.bool().value(true),
					value: Config.string()
				}
			)
		).internal(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Options
		 * @type {?bool}
		 */

		required: Config.bool().value(false),

		/**
		 * @default true
		 * @instance
		 * @memberof Options
		 * @type {?bool}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		key: Config.string(),

		placeholder: Config.string()
	};

	_getFieldIndex(element) {
		return Array.prototype.indexOf.call(
			Array.prototype.filter.call(
				element.parentElement.children,
				childrenElement => childrenElement.className === 'form-group'
			),
			element
		);
	}

	_handleTextChanged(data) {
		const {originalEvent, value} = data;
		const fieldIndex = this._getFieldIndex(
			originalEvent.delegateTarget.parentNode
		);

		if (typeof this.items[fieldIndex] === 'undefined') {
			const newItem = {label: value};

			this.items.push(newItem);
		}
		else {
			this.items[fieldIndex].label = value;
		}

		this.setState({items: this.items});

		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent,
				value: this.items
			}
		);
	}
}

Soy.register(Options, templates);

export default Options;