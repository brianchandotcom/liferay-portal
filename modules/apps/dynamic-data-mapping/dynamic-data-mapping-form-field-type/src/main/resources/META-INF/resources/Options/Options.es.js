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

import './OptionsRegister.soy';

import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';
import Component from 'metal-component';
import dom from 'metal-dom';
import {Drag, DragDrop} from 'metal-drag-drop';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Options.soy';

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

		Object.keys(value).forEach(languageId => {
			value = {
				...value,
				[languageId]: value[languageId].filter(
					(option, currentIndex) => currentIndex !== deletedIndex
				)
			};
		});

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

	getCurrentLocaleValue(localizedValue = this.value) {
		const {defaultLanguageId, editingLanguageId} = this;

		if (localizedValue && localizedValue[editingLanguageId]) {
			return localizedValue[editingLanguageId];
		} else if (localizedValue && localizedValue[defaultLanguageId]) {
			return localizedValue[defaultLanguageId];
		}

		return [];
	}

	getFieldIndex(element) {
		return parseInt(
			dom.closest(element, '.ddm-field-options').dataset.index,
			10
		);
	}

	getItems(options = []) {
		const items = [...options];
		const newItems = items.map(option => {
			return {
				...option,
				generateKeyword: this.shouldGenerateOptionValue(items, option)
			};
		});

		const {defaultLanguageId, editingLanguageId} = this;

		if (defaultLanguageId === editingLanguageId) {
			newItems.push({
				label: '',
				value: ''
			});
		}

		return newItems;
	}

	moveOption(sourceIndex, targetIndex) {
		let {value} = this;

		Object.keys(value).forEach(languageId => {
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
		});

		this._handleFieldEdited({}, value);
	}

	normalizeOption(options, option, editedIndex, editedProperty) {
		const {label, value} = option;
		let desiredValue =
			editedProperty === 'label' ? label : value ? value : label;
		const optionIndex = options.indexOf(option);

		if (!this.shouldGenerateOptionValue(options, option)) {
			return option;
		}

		if (!desiredValue) {
			desiredValue = Liferay.Language.get('option');
		}

		let normalizedValue = desiredValue;
		let counter = 0;

		do {
			if (counter > 0) {
				normalizedValue = desiredValue + counter;
			}

			counter++;
		} while (this.findOptionByValue(options, normalizedValue, optionIndex));

		normalizedValue = normalizeFieldName(normalizedValue);

		return {
			...option,
			value: normalizedValue
		};
	}

	normalizeOptions(options, editedIndex, editedProperty) {
		const normalizedOptions = [...options];

		normalizedOptions.forEach((option, index) => {
			if (editedIndex !== index) {
				return;
			}

			normalizedOptions[index] = this.normalizeOption(
				normalizedOptions,
				normalizedOptions[index],
				editedIndex,
				editedProperty
			);
		});

		return normalizedOptions;
	}

	prepareStateForRender(state) {
		const {editingLanguageId} = this;
		const {value} = state;

		return {
			...state,
			items: this.getItems(value[editingLanguageId])
		};
	}

	shouldGenerateOptionValue(options, option) {
		const {defaultLanguageId, editingLanguageId} = this;

		if (defaultLanguageId !== editingLanguageId) {
			return false;
		}

		if (option.value === '') {
			return true;
		}

		const optionIndex = options.indexOf(option);
		const duplicated = options.some(({value}, index) => {
			return value === option.value && index !== optionIndex;
		});

		if (duplicated) {
			return true;
		}

		if (option.edited) {
			return false;
		}

		if (
			new RegExp(`^${Liferay.Language.get('option')}\\d*$`).test(
				option.value
			)
		) {
			return true;
		}

		if (
			new RegExp(`^${option.value.replace(/\d+$/, '')}\\d*`).test(
				normalizeFieldName(option.label)
			)
		) {
			return true;
		}

		return true;
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

	syncValue(value) {
		this.setState({
			items: this.getCurrentLocaleValue(value)
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
		return parseInt(name.replace(/[^\d]/gi, ''), 10);
	}

	_handleDragDropEvent({source, target}) {
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
		const optionIndex = this._getOptionIndex(fieldInstance);

		if (optionIndex < options.length) {
			options = options.map((option, index) => {
				return index === optionIndex
					? {
							...option,
							edited:
								option.edited ||
								(value &&
									value !== option.value &&
									property === 'value'),
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

		options = this.normalizeOptions(options, optionIndex, property);

		let newValue = {
			...this.value,
			[editingLanguageId]: options
		};

		if (defaultLanguageId === editingLanguageId) {
			const copyLanguageLabels = (languageId, options) => {
				return options.map(({label, value}, index) => {
					const option = newValue[languageId][index];

					if (property === 'label') {
						label = option.label;
					}

					return {
						...option,
						label,
						value
					};
				});
			};

			Object.keys(this.value).forEach(languageId => {
				if (defaultLanguageId === languageId) {
					return;
				}

				newValue = {
					...newValue,
					[languageId]: copyLanguageLabels(languageId, options)
				};
			});
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

	_handleOptionValueBlurred({fieldInstance}) {
		this._handleOptionEdited(
			{fieldInstance, value: fieldInstance.keyword},
			'value'
		);
	}

	_setValue(value = {}) {
		const {defaultLanguageId} = this;
		const formattedValue = {...value};

		Object.keys(value).forEach(languageId => {
			if (defaultLanguageId !== languageId) {
				formattedValue[languageId] = formattedValue[languageId].filter(
					({value}) => !!value
				);
			}
		});

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

	defaultLanguageId: Config.string().value(themeDisplay.getLanguageId()),

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

	editingLanguageId: Config.string().value(themeDisplay.getLanguageId()),

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
