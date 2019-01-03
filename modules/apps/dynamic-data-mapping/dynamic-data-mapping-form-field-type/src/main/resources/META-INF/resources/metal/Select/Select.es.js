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
		 * @default 'string'
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		dataType: Config.string().value('string'),

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
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		fixedOptions: Config.arrayOf(
			Config.shapeOf(
				{
					dataType: Config.string(),
					name: Config.string(),
					options: Config.arrayOf(
						Config.shapeOf(
							{
								label: Config.string(),
								value: Config.string()
							}
						)
					),
					type: Config.string(),
					value: Config.string()
				}
			)
		).value([]),

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
		).value([]),

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

		placeholder: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?string}
		 */

		predefinedValue: Config.oneOfType([Config.array(), Config.string()]),

		/**
		 * @default false
		 * @instance
		 * @memberof Select
		 * @type {?bool}
		 */

		required: Config.bool().value(false),

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
		 * @default {}
		 * @instance
		 * @memberof Select
		 * @type {object}
		 */

		strings: Config.object().value(
			{
				chooseAnOption: Liferay.Language.get('choose-an-option')
			}
		),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('select'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Select
		 * @type {?(string|undefined)}
		 */

		value: Config.oneOfType([Config.array(), Config.string()])
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

	prepareStateForRender(state) {
		const {predefinedValue, value} = state;
		const predefinedValueArray = this._getArrayValue(predefinedValue);
		const valueArray = this._getArrayValue(value);
		const selectedValue = valueArray[0] || '';

		return {
			...state,
			predefinedValue: predefinedValueArray[0] || '',
			selectedLabel: this._getSelectedLabel(selectedValue),
			value: selectedValue
		};
	}

	_getArrayValue(value) {
		let newValue = value;

		if (!Array.isArray(value)) {
			newValue = [value];
		}

		return newValue;
	}

	_getSelectedLabel(selectedValue) {
		const {fixedOptions, options, placeholder} = this;
		let selectedOption = options.find(option => option.value === selectedValue);

		if (!selectedOption) {
			selectedOption = fixedOptions.find(option => option.value === selectedValue);
		}

		return selectedOption ? selectedOption.label : placeholder;
	}

	_handleDocumentClicked({target}) {
		if (!this.element.contains(target)) {
			this.setState({open: false});
		}
	}

	_handleItemClicked(event) {
		const value = [event.target.dataset.optionValue];

		this.setState(
			{
				open: !this.open,
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

	_handleClick() {
		if (!this.readOnly) {
			this.setState(
				{
					open: !this.open
				}
			);
		}
	}
}

Soy.register(Select, templates);

export default Select;