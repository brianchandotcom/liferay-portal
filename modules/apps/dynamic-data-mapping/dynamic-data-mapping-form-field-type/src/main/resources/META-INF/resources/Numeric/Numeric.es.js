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

import './NumericRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import vanillaTextMask from 'vanilla-text-mask';

import templates from './Numeric.soy';

class Numeric extends Component {
	applyMask() {
		const {dataType, element, symbols} = this;
		const inputElement = element.querySelector('input');
		const {value} = inputElement;

		if (dataType === 'integer' && value) {
			inputElement.value = Math.round(
				value.replace(symbols.decimalSymbol, '.')
			);
		}

		const mask = createNumberMask(this.getMaskConfig(dataType));

		this.maskInstance = vanillaTextMask({
			inputElement,
			mask
		});

		this.setState({value: inputElement.value});
	}

	disposed() {
		this.disposeMask();
	}

	disposeMask() {
		if (this.maskInstance) {
			this.maskInstance.destroy();
		}
	}

	getMaskConfig(dataType) {
		const {symbols} = this;

		let config = {
			allowLeadingZeroes: true,
			includeThousandsSeparator: false,
			prefix: ''
		};

		if (dataType === 'double') {
			config = {
				...config,
				allowDecimal: true,
				decimalLimit: null,
				decimalSymbol: symbols.decimalSymbol
			};
		}

		return config;
	}

	syncDataType() {
		const {visible} = this;

		this.syncVisible(visible);
	}

	syncVisible(visible) {
		if (visible) {
			this.applyMask();
		} else {
			this.disposeMask();
		}
	}

	willReceiveState(changes) {
		if (changes.value) {
			this.setState({
				_value: changes.value.newVal
			});
		}
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value
		});
	}

	_handleFieldChanged(event) {
		const value = event.target.value;

		if (value.substr(-1) === this.symbols.decimalSymbol) {
			return;
		}

		this.setState(
			{
				value
			},
			() =>
				this.emit('fieldEdited', {
					fieldInstance: this,
					originalEvent: event,
					value
				})
		);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', {
			fieldInstance: this,
			originalEvent: event,
			value: event.target.value
		});
	}

	_internalValueFn() {
		const {value} = this;

		return value;
	}
}

Numeric.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	_value: Config.oneOfType([Config.number(), Config.string()])
		.internal()
		.valueFn('_internalValueFn'),

	/**
	 * @default 'integer'
	 * @instance
	 * @memberof Numeric
	 * @type {string}
	 */

	dataType: Config.string().value('integer'),

	/**
	 * @default false
	 * @instance
	 * @memberof Numeric
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	id: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string(),

	/**
	 * @instance
	 * @memberof Numeric
	 * @type {?(string)}
	 */

	predefinedValue: Config.oneOfType([Config.number(), Config.string()]),

	/**
	 * @default false
	 * @instance
	 * @memberof Numeric
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
	 * @memberof Numeric
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Numeric
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default object
	 * @instance
	 * @memberof Numeric
	 * @type {object}
	 */

	symbols: Config.shapeOf({
		decimalSymbol: Config.string(),
		thousandsSeparator: Config.string()
	}).value({
		decimalSymbol: '.',
		thousandsSeparator: ','
	}),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('numeric'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Numeric
	 * @type {?(string|undefined)}
	 */

	value: Config.oneOfType([Config.number(), Config.string()])
};

Soy.register(Numeric, templates);

export default Numeric;
