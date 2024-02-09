/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
