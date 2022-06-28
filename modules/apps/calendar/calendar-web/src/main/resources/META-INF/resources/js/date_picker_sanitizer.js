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

AUI.add(
	'liferay-calendar-date-picker-sanitizer',
	(A) => {
		const AArray = A.Array;

		const DateMath = A.DataType.DateMath;

		const DatePickerSanitizer = A.Component.create({
			ATTRS: {
				datePickers: {},

				defaultDate: {},

				maximumDate: {},

				minimumDate: {},
			},

			EXTENDS: A.Base,

			NAME: 'date-picker-sanitizer',

			prototype: {
				_onDatePickerSelectionChange: function _onDatePickerSelectionChange(
					event
				) {
					const instance = this;

					const date = event.newSelection[0];

					const datePicker = event.currentTarget;

					const defaultDate = instance.get('defaultDate');

					const maximumDate = instance.get('maximumDate');

					const minimumDate = instance.get('minimumDate');

					if (
						date &&
						!DateMath.between(date, minimumDate, maximumDate)
					) {
						event.halt();
						event.newSelection.pop();

						datePicker.deselectDates();
						datePicker.selectDates([defaultDate]);
					}
				},

				bindUI() {
					const instance = this;

					const datePickers = instance.get('datePickers');

					instance.eventHandlers = A.map(datePickers, (item) => {
						return item.on(
							'selectionChange',
							A.bind(
								instance._onDatePickerSelectionChange,
								instance
							)
						);
					});
				},

				destructor() {
					const instance = this;

					instance.unlink();

					instance.eventHandlers = null;
				},

				initializer() {
					const instance = this;

					instance.eventHandlers = [];

					instance.bindUI();
				},

				unlink() {
					const instance = this;

					AArray.invoke(instance.eventHandlers, 'detach');
				},
			},
		});

		Liferay.DatePickerSanitizer = DatePickerSanitizer;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype'],
	}
);
