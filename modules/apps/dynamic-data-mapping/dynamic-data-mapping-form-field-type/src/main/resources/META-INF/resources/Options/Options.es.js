/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '../FieldBase/FieldBase.es';
import '../KeyValue/KeyValue.es';
import './OptionsRegister.soy.js';

import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import templates from './Options.soy.js';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer/js/util/fields.es';

/**
 * Options.
 * @extends Component
 */

class Options extends Component {
	attached() {
		let defaultOption = false;
		const defaultOptionLabel = Liferay.Language.get('option').toLowerCase();

		const options = this.getCurrentLocaleValue();

		if (
			options.length == 1 &&
			options[0].label.toLowerCase() == defaultOptionLabel
		) {
			defaultOption = true;
		}

		this.setState({
			defaultOption
		});

		this._createDragDrop();
	}

	deleteOption(deletedIndex) {
		let {value} = this;

		for (const languageId in value) {
			value = {
				...value,
				[languageId]: value[languageId].filter(
					(option, currentIndex) => currentIndex !== deletedIndex
				)
			};
		}

		this._handleFieldEdited({}, value);
	}

	disposeDragAndDrop() {
		if (this._dragAndDrop) {
			this._dragAndDrop.dispose();
		}
	}

	disposeInternal() {
		super.disposeInternal();

		this.disposeDragAndDrop();
	}

	findOptionByValue(options, name, limit = options.length) {
		return options.find(({value}, index) => {
			return index < limit && value === name;
		});
	}

	getCurrentLocaleValue() {
		const {defaultLanguageId, editingLanguageId} = this;
		let value = [];

		if (this.value && this.value[editingLanguageId]) {
			value = this.value[editingLanguageId];
		} else if (this.value && this.value[defaultLanguageId]) {
			value = this.value[defaultLanguageId];
		}

		return value;
	}

	getFieldIndex(element) {
		return parseInt(
			dom.closest(element, '.ddm-field-options').dataset.index,
			10
		);
	}

	getItems(options = []) {
		const {defaultLanguageId, editingLanguageId} = this;
		const items = options.filter(({value}) => !!value);

		if (defaultLanguageId === editingLanguageId) {
			items.push({
				label: '',
				value: ''
			});
		}

		return items.map(option => {
			return {
				...option,
				generateKeyword: this.shouldGenerateOptionValue(option)
			};
		});
	}

	moveOption(sourceIndex, targetIndex) {
		let {value} = this;

		for (const languageId in value) {
			const options = [...value[languageId]];

			if (sourceIndex < options.length) {
				options.splice(targetIndex, 0, {
					...options[sourceIndex]
				});

				value = {
					...value,
					[languageId]: options.filter((option, index) => {
						return sourceIndex > targetIndex
							? index != sourceIndex + 1
							: index != sourceIndex;
					})
				};
			}
		}

		this._handleFieldEdited({}, value);
	}

	normalizeOption(options, option, force) {
		const {label, value} = option;
		const desiredValue =
			value || label || (force ? Liferay.Language.get('option') : '');
		let normalizedValue = desiredValue;

		if (this.shouldGenerateOptionValue(option)) {
			let counter = 0;
			const optionIndex = options.indexOf(option);

			do {
				if (counter > 0) {
					normalizedValue = desiredValue + counter;
				}

				counter++;
			} while (
				this.findOptionByValue(options, normalizedValue, optionIndex)
			);

			normalizedValue = normalizeFieldName(normalizedValue);
		}

		return {
			...option,
			value: normalizedValue
		};
	}

	normalizeOptions(options, force) {
		return options.map(option =>
			this.normalizeOption(options, option, force)
		);
	}

	normalizeValue(value, force = false) {
		const newValue = {};

		for (const locale in value) {
			const options = value[locale] || [];

			newValue[locale] = this.normalizeOptions(options, force);
		}

		return newValue;
	}

	prepareStateForRender(state) {
		const {editingLanguageId} = this;
		const {value} = state;

		return {
			...state,
			items: this.getItems(value[editingLanguageId])
		};
	}

	shouldGenerateOptionValue(option) {
		const {defaultLanguageId, editingLanguageId} = this;

		return (
			defaultLanguageId === editingLanguageId &&
			(option.value === '' ||
				new RegExp(`^${normalizeFieldName(option.label)}\\d*$`).test(
					option.value
				))
		);
	}

	syncEditingLanguageId(editingLanguageId) {
		const {defaultLanguageId} = this;

		if (
			defaultLanguageId !== editingLanguageId &&
			!this.value[editingLanguageId]
		) {
			this.setState({
				value: {
					...this.value,
					[editingLanguageId]: this.value[defaultLanguageId].filter(
						({value}) => !!value
					)
				}
			});
		}
	}

	shouldUpdate(changes) {
		let changed = false;

		if (changes.items) {
			const {newVal, prevVal} = changes.items;

			if (!prevVal) {
				changed = true;
			} else if (newVal.length !== prevVal.length) {
				changed = true;
			} else {
				for (let i = 0; i < newVal.length; i++) {
					const {label, value} = newVal[i];

					if (
						label !== prevVal[i].label ||
						value !== prevVal[i].value
					) {
						changed = true;

						break;
					}
				}
			}
		}

		if (changes.visible) {
			changed = true;
		}

		return changed;
	}

	syncValue() {
		this.setState({
			items: this.getItems(this.getCurrentLocaleValue())
		});
	}

	_createDragDrop() {
		this._dragAndDrop = new DragDrop({
			container: this.element,
			dragPlaceholder: Drag.Placeholder.CLONE,
			handles: '.ddm-options-drag:not(.disabled)',
			sources: '.ddm-field-options',
			targets: '.ddm-options-target',
			useShim: false
		});

		this._dragAndDrop.on(
			DragDrop.Events.END,
			this._handleDragDropEvent.bind(this)
		);
		this._dragAndDrop.on(
			DragDrop.Events.DRAG,
			this._handleDragEvent.bind(this)
		);
	}

	_getOptionIndex({name}) {
		return parseInt(name.replace('option', ''), 10);
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

	_handleDragEvent({source}) {
		source.classList.add('ddm-source-dragging');
	}

	_handleFieldEdited({originalEvent}, value) {
		this.emit('fieldEdited', {
			fieldInstance: this,
			originalEvent,
			value
		});
	}

	_handleOptionDeleted(event) {
		const {delegateTarget} = event;
		const deletedIndex = this.getFieldIndex(delegateTarget);

		this.deleteOption(deletedIndex);
	}

	_handleOptionEdited(event, property) {
		const {defaultLanguageId, editingLanguageId} = this;
		const {fieldInstance, value} = event;
		let options = this.getCurrentLocaleValue();

		const optionExists = options.some((option, index) => {
			return index === this._getOptionIndex(fieldInstance);
		});

		if (optionExists) {
			options = options.map((option, index) => {
				return index === this._getOptionIndex(fieldInstance)
					? {
							...option,
							edited: property === 'label',
							[property]: value
					  }
					: option;
			});
		} else {
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

		let newValue = {
			...this.value,
			[editingLanguageId]: options
		};

		if (defaultLanguageId === editingLanguageId) {
			const generateLabels = (languageId, options) => {
				return options.map(({label, value}, index) => {
					const option = newValue[languageId][index];

					if (option && option.edited) {
						label = option.label;
					}

					return {
						edited: option && option.edited,
						label,
						value
					};
				});
			};

			for (const languageId in this.value) {
				if (defaultLanguageId === languageId) {
					continue;
				}

				newValue = {
					...newValue,
					[languageId]: generateLabels(languageId, options)
				};
			}
		}

		this.setState(
			{
				value: newValue
			},
			() => this._handleFieldEdited(event, newValue)
		);
	}

	_handleOptionFocused({originalEvent: {target}}) {
		if (this.defaultOption) {
			target.value = '';

			this.setState({
				defaultOption: false
			});
		}
	}

	_handleOptionLabelEdited(event) {
		this._handleOptionEdited(event, 'label');
	}

	_handleOptionValueEdited(event) {
		this._handleOptionEdited(event, 'value');
	}

	_setValue(value = {}) {
		const {defaultLanguageId} = this;
		const formattedValue = {...value};

		for (const languageId in value) {
			if (defaultLanguageId !== languageId) {
				formattedValue[languageId] = formattedValue[languageId].filter(
					({value}) => !!value
				);
			}
		}

		return formattedValue;
	}
}

Options.STATE = {
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

	defaultOption: Config.bool()
		.internal()
		.value(false),

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
	 * @default undefined
	 * @instance
	 * @memberof Options
	 * @type {?(string|undefined)}
	 */

	items: Config.arrayOf(
		Config.shapeOf({
			disabled: Config.bool().value(false),
			label: Config.string(),
			name: Config.string(),
			value: Config.string()
		})
	).internal(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Options
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default enter-an-option
	 * @instance
	 * @memberof Options
	 * @type {?(string)}
	 */

	placeholder: Config.string().value(Liferay.Language.get('enter-an-option')),

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
	 * @default undefined
	 * @instance
	 * @memberof Options
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

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

	value: Config.object()
		.setter('_setValue')
		.value({})
};

Soy.register(Options, templates);

export default Options;
