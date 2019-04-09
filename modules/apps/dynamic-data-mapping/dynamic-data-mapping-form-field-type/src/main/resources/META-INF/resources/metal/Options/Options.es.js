import '../FieldBase/FieldBase.es';
import '../KeyValue/KeyValue.es';
import './OptionsRegister.soy.js';

import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import templates from './Options.soy.js';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import {
	normalizeFieldName
} from 'dynamic-data-mapping-form-builder/js/components/LayoutProvider/util/fields.es';

/**
 * Options.
 * @extends Component
 */

class Options extends Component {
	static STATE = {

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?string}
		 */

		defaultLanguageId: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Options
		 * @type {?bool}
		 */

		defaultOption: Config.bool().internal().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?string}
		 */

		editingLanguageId: Config.string(),

		/**
		 * @default 'boolean'
		 * @instance
		 * @memberof Options
		 * @type {?(boolean|undefined)}
		 */

		evaluable: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?string}
		 */

		fieldName: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Options
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

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
					disabled: Config.bool().value(false),
					label: Config.string(),
					name: Config.string(),
					value: Config.string()
				}
			)
		).internal().valueFn('_internalItemsValueFn'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Options
		 * @type {?string}
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
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(bool|undefined)}
		 */

		repeatable: Config.bool(),

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

		/**
		 * @default enter-an-option
		 * @instance
		 * @memberof Options
		 * @type {?(string)}
		 */

		placeholder: Config.string().value(Liferay.Language.get('enter-an-option')),

		/**
		 * @default options
		 * @instance
		 * @memberof Options
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('options'),

		/**
		 * @default {}
		 * @instance
		 * @memberof Options
		 * @type {?string}
		 */

		value: Config.object().value({}),

		/**
		 * @default true
		 * @instance
		 * @memberof Options
		 * @type {?string}
		 */

		visible: Config.bool().value(true)
	};

	attached() {
		let defaultOption = false;
		const defaultOptionLabel = Liferay.Language.get('option').toLowerCase();

		const options = this.getValue();

		if ((options.length == 1) && (options[0].label.toLowerCase() == defaultOptionLabel)) {
			defaultOption = true;
		}

		this.setState(
			{
				defaultOption
			}
		);

		this._createDragDrop();
	}

	/**
	 * @inheritDoc
	 */

	disposeInternal() {
		super.disposeInternal();
		this.disposeDragAndDrop();
	}

	disposeDragAndDrop() {
		if (this._dragAndDrop) {
			this._dragAndDrop.dispose();
		}
	}

	willReceiveState(changes) {
		const {editingLanguageId} = this;

		if (changes.value && changes.value.newVal[editingLanguageId]) {
			this.setState(
				{
					items: this.getItems(changes.value.newVal[editingLanguageId])
				}
			);
		}
	}

	deleteOption(deletedIndex) {
		const options = [...this.getValue()].filter((option, currentIndex) => currentIndex !== deletedIndex);

		this._handleFieldEdited({}, options);

		this.setState(
			{
				items: this.getItems(options)
			}
		);
	}

	findOptionByValue(options, name, limit = options.length) {
		return options.find(
			({value}, index) => {
				return index < limit && value === name;
			}
		);
	}

	getFieldIndex(element) {
		return parseInt(dom.closest(element, '.ddm-field-options').dataset.index, 10);
	}

	getItems(options = []) {
		const {defaultLanguageId, editingLanguageId} = this;
		const items = [...options];

		if (defaultLanguageId === editingLanguageId) {
			items.push({
				label: '',
				value: ''
			});
		}

		return items.map(
			option => {
				return {
					...option,
					generateKeyword: this.shouldGenerateOptionValue(option)
				};
			}
		);
	}

	getValue() {
		const {defaultLanguageId, editingLanguageId} = this;
		let value = [];

		if (this.value && this.value[editingLanguageId]) {
			value = this.value[editingLanguageId];
		}
		else if (this.value && this.value[defaultLanguageId]) {
			value = this.value[defaultLanguageId];
		}
		else {
			value = [];
		}

		return value;
	}

	moveOption(sourceIndex, targetIndex) {
		let options = [...this.getValue()];

		if (sourceIndex < options.length) {
			options.splice(
				targetIndex,
				0,
				{
					...options[sourceIndex]
				}
			);

			options = options.filter(
				(option, index) => {
					return sourceIndex > targetIndex ? index != (sourceIndex + 1) : index != sourceIndex;
				}
			);

			this._handleFieldEdited({}, options);
		}

		this.setState(
			{
				items: this.getItems(options)
			}
		);
	}

	normalizeOption(options, option, force) {
		const {label, value} = option;
		const desiredValue = value || label || (force ? Liferay.Language.get('option') : '');
		let normalizedValue = desiredValue;

		if (this.shouldGenerateOptionValue(option)) {
			let counter = 0;
			const optionIndex = options.indexOf(option);

			do {
				if (counter > 0) {
					normalizedValue = desiredValue + counter;
				}

				counter++;
			} while (this.findOptionByValue(options, normalizedValue, optionIndex));

			normalizedValue = normalizeFieldName(normalizedValue);
		}

		return {
			...option,
			value: normalizedValue
		};
	}

	normalizeOptions(options, force) {
		return options.map(option => this.normalizeOption(options, option, force));
	}

	normalizeValue(value, force = false) {
		const newValue = {};

		for (const locale in value) {
			const options = value[locale] || [];

			newValue[locale] = this.normalizeOptions(options, force);
		}

		return newValue;
	}

	shouldGenerateOptionValue(option) {
		const {defaultLanguageId, editingLanguageId} = this;

		return (
			defaultLanguageId === editingLanguageId &&
			(
				option.value === '' ||
				(new RegExp(`^${normalizeFieldName(option.label)}\\d*$`)).test(option.value)
			)
		);
	}

	_createDragDrop() {
		this._dragAndDrop = new DragDrop(
			{
				container: this.element,
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.ddm-options-drag:not(.disabled)',
				sources: '.ddm-field-options',
				targets: '.ddm-options-target',
				useShim: false
			}
		);

		this._dragAndDrop.on(DragDrop.Events.END, this._handleDragDropEvent.bind(this));
		this._dragAndDrop.on(DragDrop.Events.DRAG, this._handleDragEvent.bind(this));
	}

	_handleDragEvent({source}) {
		source.classList.add('ddm-source-dragging');
	}

	_handleDragDropEvent({target, source}) {
		const lastSource = document.querySelector('.ddm-source-dragging');
		const sourceIndex = parseInt(source.dataset.index, 10);

		if (lastSource) {
			lastSource.classList.remove('ddm-source-dragging');
		}

		if (target) {
			const targetIndex = parseInt(target.dataset.index, 10);

			this.moveOption(sourceIndex, targetIndex);
		}
	}

	_handleOptionBlurred(event) {
		const {editingLanguageId, value} = this;
		const normalizedValue = this.normalizeValue(value, true);

		this._handleFieldEdited(event, normalizedValue[editingLanguageId]);
	}

	_handleOptionDeleted(event) {
		const {delegateTarget} = event;
		const deletedIndex = this.getFieldIndex(delegateTarget);

		this.deleteOption(deletedIndex);
	}

	_getOptionIndex({name}) {
		return parseInt(name.replace('option', ''), 10);
	}

	_handleOptionEdited(event, property) {
		const {editingLanguageId} = this;
		const {fieldInstance, value} = event;
		let options = this.getValue();

		const optionExists = options.some(
			(option, index) => {
				return index === this._getOptionIndex(fieldInstance);
			}
		);

		if (optionExists) {
			options = options.map(
				(option, index) => {
					return index === this._getOptionIndex(fieldInstance) ? (
						{
							...option,
							[property]: value
						}
					) : option;
				}
			);
		}
		else {
			options = [
				...options,
				{
					label: property === 'label' ? value : '',
					value: property === 'value' ? value : ''
				}
			];
		}

		if (property === 'label') {
			options = this.normalizeOptions(options);
		}

		this.setState(
			{
				value: {
					...this.value,
					[editingLanguageId]: options
				}
			},
			() => {
				this._handleFieldEdited(event, options);
			}
		);
	}

	_handleOptionValueEdited(event) {
		this._handleOptionEdited(event, 'value');
	}

	_handleOptionLabelEdited(event) {
		this._handleOptionEdited(event, 'label');
	}

	_handleOptionFocused({originalEvent: {target}}) {
		if (this.defaultOption) {
			target.value = '';

			this.setState(
				{
					defaultOption: false
				}
			);
		}
	}

	_handleFieldEdited({originalEvent}, options) {
		this.emit(
			'fieldEdited',
			{
				fieldInstance: this,
				originalEvent,
				value: options
			}
		);
	}

	_internalItemsValueFn() {
		const options = this.getValue();

		return this.getItems(options || []);
	}
}

Soy.register(Options, templates);

export default Options;