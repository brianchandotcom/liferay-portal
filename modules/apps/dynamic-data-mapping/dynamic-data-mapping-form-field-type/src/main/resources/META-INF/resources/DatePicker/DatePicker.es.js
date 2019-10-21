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

import './DatePickerRegister.soy.js';

import 'clay-button';

import 'clay-icon';
import Component from 'metal-component';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import moment from 'moment';
import {createAutoCorrectedDatePipe} from 'text-mask-addons';
import vanillaTextMask from 'vanilla-text-mask';

import templates from './DatePicker.soy.js';
import * as Helpers from './Helpers.es';

/**
 * Metal DatePicker component.
 * @extends Component
 */

class DatePicker extends Component {
	created() {
		this._eventHandler = new EventHandler();

		let newValue;

		if (this.value) {
			newValue = this.value;
		} else if (this.predefinedValue) {
			newValue = this.predefinedValue;
		} else {
			newValue = this.initialMonth;
		}

		const value = moment(newValue, this.dateFormat).toDate();

		this.currentMonth = this._setCurrentMonth(value);
		this._daySelected = Helpers.setDateSelected(value);
	}

	detached() {
		this._eventHandler.removeAllListeners();
	}

	disposeInternal() {
		super.disposeInternal();

		if (this._vanillaTextMask) {
			this._vanillaTextMask.destroy();
		}
	}

	getDateFormat() {
		const dateFormat = Liferay.AUI.getDateFormat();

		this._dateDelimiter = '/';
		this._endDelimiter = false;

		if (dateFormat.indexOf('.') != -1) {
			this._dateDelimiter = '.';

			if (dateFormat.lastIndexOf('.') == dateFormat.length - 1) {
				this._endDelimiter = true;
			}
		}

		if (dateFormat.indexOf('-') != -1) {
			this._dateDelimiter = '-';
		}

		return dateFormat;
	}

	getDateMask() {
		const dateFormat = this.getDateFormat();

		return dateFormat
			.split('')
			.map((item, index) => {
				if (item === this._dateDelimiter) {
					return this._dateDelimiter;
				} else if (item === '%') {
					return dateFormat[index + 1];
				}

				return item;
			})
			.join('');
	}

	getInputMask() {
		const dateFormat = this.getDateFormat();
		const inputMaskArray = [];

		dateFormat.split('').forEach(item => {
			if (item === this._dateDelimiter) {
				inputMaskArray.push(this._dateDelimiter);
			} else if (item === 'Y') {
				inputMaskArray.push(/\d/);
				inputMaskArray.push(/\d/);
				inputMaskArray.push(/\d/);
				inputMaskArray.push(/\d/);
			} else if (item === 'd' || item === 'm') {
				inputMaskArray.push(/\d/);
				inputMaskArray.push(/\d/);
			}
		});

		return inputMaskArray;
	}

	getYears() {
		const currentYear = this._year;
		const years = [];

		for (let year = currentYear - 5; year < currentYear + 5; year++) {
			years.push(year);
		}

		return years;
	}

	isEmptyValue(string) {
		if (!string) {
			return true;
		}

		return !this.getInputMask().some((validator, index) => {
			let hasValue = false;

			if (typeof validator !== 'string') {
				hasValue = string[index] !== '_';
			}

			return hasValue;
		});
	}

	prepareStateForRender(state) {
		const value = Helpers.formatDate(this._daySelected);

		return {
			...state,
			formattedValue: state.value,
			value: moment(value).format('YYYY-MM-DD'),
			years: this.getYears()
		};
	}

	rendered() {
		if (this._vanillaTextMask) {
			const {textMaskInputElement} = this._vanillaTextMask;

			textMaskInputElement.update();
		}
	}

	syncCurrentMonth(value) {
		if (moment(value).isValid()) {
			this._weeks = Helpers.getWeekArray(value, this.firstDayOfWeek);
			this._month = value.getMonth();
			this._year = value.getFullYear();
		}
	}

	syncExpanded() {
		if (this.expanded) {
			this._eventHandler.add(
				dom.on(document, 'click', this._handleDocClick.bind(this), true)
			);

			this.emit('fieldFocused', {
				fieldInstance: this,
				originalEvent: window.event
			});
		} else {
			this._eventHandler.removeAllListeners();

			this.emit('fieldBlurred', {
				fieldInstance: this,
				originalEvent: window.event
			});
		}
	}

	syncVisible() {
		if (this.visible) {
			const {base} = this.refs;
			const {inputElement} = base.refs;
			const dateMask = this._dateFormatValueFn().toLowerCase();

			this._vanillaTextMask = vanillaTextMask({
				guide: true,
				inputElement,
				keepCharPositions: true,
				mask: this.getInputMask(),
				pipe: createAutoCorrectedDatePipe(dateMask),
				showMask: true
			});
		} else if (this._vanillaTextMask) {
			this._vanillaTextMask.destroy();
		}
	}

	_dateFormatValueFn() {
		const dateFormat = this.getDateFormat();

		return dateFormat
			.split(this._dateDelimiter)
			.map(item => {
				let currentFormat;

				if (item === '%Y') {
					currentFormat = 'YYYY';
				} else if (item === '%m') {
					currentFormat = 'MM';
				} else {
					currentFormat = 'DD';
				}

				return currentFormat;
			})
			.join(this._dateDelimiter);
	}

	_getCurrentDate() {
		const today = new Date();

		const day = today.getDate();
		const month = today.getMonth();
		const year = today.getFullYear();

		return `${year} ${month} ${day}`;
	}

	_handleDayClicked(event) {
		const ariaLabel = event.target.getAttribute('ariaLabel');
		const selectedDate = Helpers.formatDate(ariaLabel);

		if (selectedDate.getMonth() > this.currentMonth.getMonth()) {
			this._handleNextMonth();
		} else if (selectedDate.getMonth() < this.currentMonth.getMonth()) {
			this._handlePreviousMonth();
		}

		this._daySelected = ariaLabel;
		this.expanded = false;
		this.value = selectedDate;

		this._handleFieldEdited();
	}

	_handleDocClick(event) {
		if (this.element.contains(event.target)) {
			return;
		}

		this.expanded = false;
	}

	_handleDotClicked() {
		this._daySelected = this._getCurrentDate();
		this.currentMonth = Helpers.formatDate(this._daySelected);
		this.value = this.currentMonth;
		this._handleFieldEdited();
	}

	_handleFieldEdited() {
		let value = Helpers.formatDate(this._daySelected);

		const {base} = this.refs;
		const {inputElement} = base.refs;

		if (this.isEmptyValue(inputElement.value)) {
			value = '';
		}

		this.emit('fieldEdited', {
			fieldInstance: this,
			value: this._setValue(value)
		});
	}

	_handleInput(event) {
		const {value} = event.target;
		const format = `${this.dateFormat}`;

		const date = moment(value, format);

		if (date.isValid() && date._i.length === 10) {
			this.currentMonth = date.toDate();
			this._daySelected = Helpers.setDateSelected(this.currentMonth);
		}

		if (!value) {
			this._daySelected = '';
		}

		this._handleFieldEdited();
	}

	_handleInputBlurred({target}) {
		if (!this.isEmptyValue(target.value)) {
			this.value = Helpers.formatDate(this._daySelected);
		}
	}

	_handleInputFocused() {
		this.expanded = true;
	}

	_handleNavigateChange(event) {
		const {month, year} = event.target.form;

		this.currentMonth = new Date(year.value, month.value);
	}

	_handleNextMonth() {
		this.currentMonth = moment(this.currentMonth)
			.clone()
			.add(1, 'M')
			.toDate();
	}

	_handlePreviousMonth() {
		this.currentMonth = moment(this.currentMonth)
			.clone()
			.add(-1, 'M')
			.toDate();
	}

	_handleToggle() {
		this.expanded = !this.expanded;
	}

	_setCurrentMonth(value) {
		const currentMonth = moment(value)
			.clone()
			.set('date', 1)
			.set('hour', 12)
			.toDate();

		return currentMonth;
	}

	_setValue(value) {
		let newValue;

		if (moment(value, this.dateFormat).isValid()) {
			if (typeof value == 'string') {
				newValue = value;
			} else {
				const date = moment(value)
					.clone()
					.format(this.dateFormat);

				newValue = date;
			}
		} else if (moment(value, 'YYYY-MM-DD').isValid()) {
			const date = moment(value, 'YYYY-MM-DD')
				.clone()
				.format(this.dateFormat);

			newValue = date;
		} else {
			newValue = value;
		}

		return newValue;
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */

DatePicker.STATE = {
	/**
	 * The day selected by the user.
	 * @default Date Month
	 * @instance
	 * @memberof DatePicker
	 * @type {!Date}
	 */

	_daySelected: Config.any().internal(),

	/**
	 * The selected month.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {!int}
	 */

	_month: Config.number().internal(),

	/**
	 * An array of the weeks and days list for the current month
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {!Array<Array>}
	 */

	_weeks: Config.array(Config.array()).internal(),

	/**
	 * The selected year.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {!int}
	 */

	_year: Config.number().internal(),

	/**
	 * Aria label attribute for the button element.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	ariaLabel: Config.string(),

	/**
	 * Indicates the current month rendered on the screen.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {!Date}
	 */

	currentMonth: Config.instanceOf(Date).internal(),

	/**
	 * Database type.
	 * @default string
	 * @instance
	 * @memberof DatePicker
	 * @type {!string}
	 */
	dataType: Config.string().value('string'),

	/**
	 * Set the format of how the date will appear in the input element.
	 * See available: https://momentjs.com/docs/#/parsing/string-format/
	 * @default YYYY-MM-DD
	 * @instance
	 * @memberof DatePicker
	 * @type {?string}
	 */

	dateFormat: Config.string().valueFn('_dateFormatValueFn'),

	/**
	 * CSS classes to be applied to the element.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	elementClasses: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof DatePicker
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * Flag to indicate if date is expanded.
	 * @default false
	 * @instance
	 * @memberof DatePicker
	 * @type {?bool}
	 */

	expanded: Config.bool()
		.internal()
		.value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * Set the first day of the week, starting from 0
	 * (Sunday) to 6 (Saturday).
	 * @default 0
	 * @instance
	 * @memberof DatePicker
	 * @type {?int}
	 */

	firstDayOfWeek: Config.oneOf([0, 1, 2, 3, 4, 5, 6]).value(0),

	/**
	 * Id to be applied to the element.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	id: Config.string(),

	/**
	 * The month to display in the calendar on the first render.
	 * @default Date
	 * @instance
	 * @memberof DatePicker
	 * @type {!Date}
	 */

	initialMonth: Config.instanceOf(Date).value(new Date()),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * The names of the months.
	 * @default January...
	 * @instance
	 * @memberof DatePicker
	 * @type {?array<String>}
	 */

	months: Config.array().value([
		'January',
		'February',
		'March',
		'April',
		'May',
		'June',
		'July',
		'August',
		'September',
		'October',
		'November',
		'December'
	]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * Describe a brief tip to help users interact.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string(),

	/**
	 * Set the initial value of the input.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(Date|string|undefined)}
	 */

	predefinedValue: Config.oneOfType([
		Config.instanceOf(Date),
		Config.string()
	]).setter('_setValue'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(bool|undefined)}
	 */
	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @instance
	 * @memberof DatePicker
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * Wether to show the field label or not.
	 * @default true
	 * @instance
	 * @memberof DatePicker
	 * @type {!boolean}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * The path to the SVG spritemap file containing the icons.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('text'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {?(string|undefined)}
	 */

	value: Config.oneOfType([Config.instanceOf(Date), Config.string()])
		.setter('_setValue')
		.internal(),

	/**
	 * Short names of days of the week to use in the header
	 * of the month. It should start from Sunday.
	 * @default S M T W T F S
	 * @instance
	 * @memberof DatePicker
	 * @type {!Array<String>}
	 */

	weekdaysShort: Config.array().value(['S', 'M', 'T', 'W', 'T', 'F', 'S']),

	/**
	 * List of years available for navigate that are added in the selector.
	 * @default undefined
	 * @instance
	 * @memberof DatePicker
	 * @type {!Array<String>}
	 */

	years: Config.array().value([
		'2018',
		'2019',
		'2020',
		'2021',
		'2022',
		'2023',
		'2024'
	])
};

Soy.register(DatePicker, templates);

export {DatePicker};
export default DatePicker;
