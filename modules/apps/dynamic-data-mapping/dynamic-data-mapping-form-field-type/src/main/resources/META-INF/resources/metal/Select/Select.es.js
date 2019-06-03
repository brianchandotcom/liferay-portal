import '../FieldBase/FieldBase.es';
import '../Text/Text.es';
import './SelectRegister.soy.js';
import 'clay-dropdown';
import 'clay-icon';
import 'clay-label';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import templates from './Select.soy.js';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';

class Select extends Component {
	addValue(value) {
		const currentValue = this._getArrayValue(this.value);
		const newValue = [...currentValue];

		if (value) {
			newValue.push(value);
		}

		return newValue;
	}

	attached() {
		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			dom.on(document, 'click', this._handleDocumentClicked.bind(this))
		);
	}

	deleteValue(value) {
		const currentValue = this._getArrayValue(this.value);

		return currentValue.filter(v => v !== value);
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	prepareStateForRender(state) {
		const {predefinedValue, value} = state;
		const {fixedOptions, multiple, options} = this;
		const predefinedValueArray = this._getArrayValue(predefinedValue);
		let valueArray = this._getArrayValue(value);

		valueArray = this._isEmptyArray(valueArray)
			? predefinedValueArray
			: valueArray;

		valueArray = valueArray.filter((value, index) => {
			return multiple ? true : index === 0;
		});

		const emptyOption = {
			label: Liferay.Language.get('choose-an-option'),
			value: ''
		};

		let newOptions = [...options]
			.map((option, index) => {
				return {
					...this._prepareOption(option, valueArray),
					separator:
						fixedOptions.length > 0 && index === options.length - 1
				};
			})
			.concat(
				fixedOptions.map(option =>
					this._prepareOption(option, valueArray)
				)
			)
			.filter(({value}) => value !== '');

		if (!multiple) {
			newOptions = [emptyOption, ...newOptions];
		}

		return {
			...state,
			options: newOptions,
			value: valueArray.filter(value =>
				newOptions.some(option => value === option.value)
			)
		};
	}

	setValue(value) {
		const newValue = [];

		if (value) {
			newValue.push(value);
		}

		return newValue;
	}

	_getArrayValue(value) {
		let newValue = value || '';

		if (!Array.isArray(newValue)) {
			newValue = [newValue];
		}

		return newValue;
	}

	_handleDocumentClicked({target}) {
		const {base} = this.refs;
		const {dropdown} = base.refs;
		const {menu} = dropdown.refs.portal.refs;
		const {expanded} = this;

		if (
			expanded &&
			!this.element.contains(target) &&
			!dropdown.element.contains(target) &&
			!menu.contains(target)
		) {
			this.setState({expanded: false});
		}
	}

	_handleItemClicked({data, preventDefault}) {
		const {multiple} = this;
		const currentValue = this._getArrayValue(this.value);
		const itemValue = data.item.value;

		let newValue;

		if (multiple) {
			if (currentValue.includes(itemValue)) {
				newValue = this.deleteValue(itemValue);

				if (document.activeElement) {
					document.activeElement.blur();
				}
			} else {
				newValue = this.addValue(itemValue);
			}
		} else {
			newValue = this.setValue(itemValue);
		}

		preventDefault();

		this.setState(
			{
				expanded: multiple,
				value: newValue
			},
			() =>
				this.emit('fieldEdited', {
					fieldInstance: this,
					value: newValue
				})
		);
	}

	_handleLabelClosed({target, preventDefault, stopPropagation}) {
		const {value} = target.data;

		preventDefault();
		stopPropagation();

		const newValue = this.deleteValue(value);

		this.setState(
			{
				value: newValue
			},
			() =>
				this.emit('fieldEdited', {
					fieldInstance: this,
					value: newValue
				})
		);
	}

	_isEmptyArray(array) {
		return array.some(value => value !== '') === false;
	}

	_prepareOption(option, valueArray) {
		const {multiple} = this;
		const included = valueArray.includes(option.value);

		return {
			...option,
			active: !multiple && included,
			checked: multiple && included,
			type: multiple ? 'checkbox' : 'item'
		};
	}
}

Select.STATE = {
	/**
	 * @default 'string'
	 * @instance
	 * @memberof Select
	 * @type {?(string|undefined)}
	 */

	dataSourceType: Config.string(),

	/**
	 * @default 'string'
	 * @instance
	 * @memberof Select
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('string'),

	/**
	 * @default 'boolean'
	 * @instance
	 * @memberof Select
	 * @type {?(boolean|undefined)}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Select
	 * @type {?bool}
	 */

	expanded: Config.bool()
		.internal()
		.value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	fixedOptions: Config.arrayOf(
		Config.shapeOf({
			active: Config.bool().value(false),
			disabled: Config.bool().value(false),
			id: Config.string(),
			inline: Config.bool().value(false),
			label: Config.string(),
			name: Config.string(),
			showLabel: Config.bool().value(true),
			value: Config.string()
		})
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
	 * @type {?(string|undefined)}
	 */

	multiple: Config.bool(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Select
	 * @type {?array<object>}
	 */

	options: Config.arrayOf(
		Config.shapeOf({
			active: Config.bool().value(false),
			disabled: Config.bool().value(false),
			id: Config.string(),
			inline: Config.bool().value(false),
			label: Config.string(),
			name: Config.string(),
			showLabel: Config.bool().value(true),
			value: Config.string()
		})
	).value([]),

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

	predefinedValue: Config.oneOfType([Config.array(), Config.string()]).value(
		[]
	),

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
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

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

	type: Config.string().value('select'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Select
	 * @type {?(string|undefined)}
	 */

	value: Config.oneOfType([Config.array(), Config.string()])
};

Soy.register(Select, templates);

export default Select;
