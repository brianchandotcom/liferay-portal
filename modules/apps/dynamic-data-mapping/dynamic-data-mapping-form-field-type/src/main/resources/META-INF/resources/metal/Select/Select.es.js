import './SelectRegister.soy.js';
import 'clay-icon';
import 'dynamic-data-mapping-form-field-type/metal/FieldBase/index.es';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import Component from 'metal-component';
import dom from 'metal-dom';
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
		 * @memberof Select
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		key: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?array<object>}
		 */

		options: Config.arrayOf(
			Config.shapeOf(
				{
					active: Config.bool().value(false),
					disabled: Config.bool().value(false),
					id: Config.string(),
					inline: Config.bool().value(false),
					label: Config.string(),
					name: Config.string(),
					showLabel: Config.bool().value(true),
					value: Config.string()
				}
			)
		).value(
			[
				{
					value: 'Option 1'
				},
				{
					value: 'Option 2'
				}
			]
		),

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

		predefinedValue: Config.array(),

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

		value: Config.array()
	};

	attached() {
		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			dom.on(document, 'click', this._handleDocumentClicked.bind(this))
		);
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	prepareStateForRender(states) {
		const {predefinedValue, value} = states;
		let newValue = value;
		if (typeof (newValue) === 'string') {
			newValue = [value];
		}
		return {
			...states,
			predefinedValue: predefinedValue && predefinedValue.length ? predefinedValue[0] : '',
			value: newValue && newValue.length ? newValue[0] : ''
		};
	}

	_handleDocumentClicked({target}) {
		if (!this.element.contains(target)) {
			this.setState({open: false});
		}
	}

	_handleItemClicked(event) {
		this.setState(
			{
				predefinedValue: event.target.innerText,
				value: event.target.innerText
			}
		);

		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent: event,
				value: event.target.innerText
			}
		);

		this.setState({open: !this.open});
	}

	_handleClick() {
		if (!this.readOnly) {
			this.setState({open: !this.open});
		}
	}
}

Soy.register(Select, templates);

export default Select;