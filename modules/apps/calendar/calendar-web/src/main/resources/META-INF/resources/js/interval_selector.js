/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-interval-selector',
	(A) => {
		const AArray = A.Array;

		const EVENT_SELECTION_CHANGE = 'selectionChange';

		const IntervalSelector = A.Component.create({
			ATTRS: {
				endDatePicker: {
					value: null,
				},

				endTimePicker: {
					value: null,
				},

				startDatePicker: {
					value: null,
				},

				startTimePicker: {
					value: null,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'interval-selector',

			prototype: {
				_initPicker(picker) {
					const attrs = picker.getAttrs();

					// eslint-disable-next-line @liferay/aui/no-one
					const inputNode = A.one(attrs.container._node.children[0]);

					picker.useInputNodeOnce(inputNode);
				},

				_onEndDatePickerSelectionChange() {
					const instance = this;

					instance._setEndDate();

					const endDateValue = instance._endDate.valueOf();

					if (
						instance._validDate &&
						instance._startDate.valueOf() >= endDateValue
					) {
						instance._startDate = new Date(
							endDateValue - instance._duration
						);

						instance._setStartDatePickerDate();
					}

					instance._setDuration();
					instance._validate();
				},

				_onEndTimePickerSelectionChange() {
					const instance = this;

					instance._setEndTime();

					const endDateValue = instance._endDate.valueOf();

					if (
						instance._validDate &&
						instance._startDate.valueOf() >= endDateValue
					) {
						instance._startDate = new Date(
							endDateValue - instance._duration
						);

						instance._setStartDatePickerDate();
						instance._setStartTimePickerTime();
					}

					instance._setDuration();
					instance._validate();
				},

				_onStartDatePickerSelectionChange() {
					const instance = this;

					instance._setStartDate();

					if (instance._validDate) {
						instance._endDate = new Date(
							instance._startDate.valueOf() + instance._duration
						);

						instance._setEndDatePickerDate();
					}

					instance._setDuration();
					instance._validate();
				},

				_onStartTimePickerSelectionChange() {
					const instance = this;

					instance._setStartTime();

					if (instance._validDate) {
						instance._endDate = new Date(
							instance._startDate.valueOf() + instance._duration
						);

						instance._setEndDatePickerDate();
						instance._setEndTimePickerTime();
					}

					instance._setDuration();
					instance._validate();
				},

				_setDuration() {
					const instance = this;

					instance._duration =
						instance._endDate.valueOf() -
						instance._startDate.valueOf();
				},

				_setEndDate() {
					const instance = this;

					const endDatePicker = instance.get('endDatePicker');

					const endDateObj = endDatePicker.getDate();

					const endDate = instance._endDate;

					endDate.setMonth(
						endDateObj.getMonth(),
						endDateObj.getDate()
					);
					endDate.setYear(endDateObj.getFullYear());
				},

				_setEndDatePickerDate() {
					const instance = this;

					const endDatePicker = instance.get('endDatePicker');

					endDatePicker.clearSelection(true);

					endDatePicker.selectDates([instance._endDate]);
				},

				_setEndTime() {
					const instance = this;

					const endTimePicker = instance.get('endTimePicker');

					const endTime = endTimePicker.getTime();

					instance._endDate.setHours(endTime.getHours());
					instance._endDate.setMinutes(endTime.getMinutes());
				},

				_setEndTimePickerTime() {
					const instance = this;

					const endTimePicker = instance.get('endTimePicker');

					endTimePicker.selectDates([instance._endDate]);
				},

				_setStartDate() {
					const instance = this;

					const startDatePicker = instance.get('startDatePicker');

					const startDateObj = startDatePicker.getDate();

					const startDate = instance._startDate;

					startDate.setMonth(
						startDateObj.getMonth(),
						startDateObj.getDate()
					);
					startDate.setYear(startDateObj.getFullYear());
				},

				_setStartDatePickerDate() {
					const instance = this;

					const startDatePicker = instance.get('startDatePicker');

					startDatePicker.clearSelection(true);

					startDatePicker.selectDates([instance._startDate]);
				},

				_setStartTime() {
					const instance = this;

					const startTimePicker = instance.get('startTimePicker');

					const startTime = startTimePicker.getTime();

					const startDate = instance._startDate;

					startDate.setHours(startTime.getHours());
					startDate.setMinutes(startTime.getMinutes());
				},

				_setStartTimePickerTime() {
					const instance = this;

					const startTimePicker = instance.get('startTimePicker');

					startTimePicker.selectDates([instance._startDate]);
				},

				_validate() {
					const instance = this;

					const validDate = instance._duration > 0;

					instance._validDate = validDate;

					const meetingEventDate = instance._containerNode;

					if (meetingEventDate) {
						meetingEventDate.toggleClass('error', !validDate);

						const helpInline = meetingEventDate.one('.help-inline');

						if (validDate && helpInline) {
							helpInline.remove();
						}

						if (!validDate && !helpInline) {
							const inlineHelp = A.Node.create(
								'<div class="help-inline">' +
									Liferay.Language.get(
										'the-end-time-must-be-after-the-start-time'
									) +
									'</div>'
							);

							meetingEventDate.insert(inlineHelp);
						}

						const submitButton = instance._submitButtonNode;

						if (submitButton) {
							submitButton.attr('disabled', !validDate);
						}
					}
				},

				bindUI() {
					const instance = this;

					instance.startDurationPreservation();
				},

				destructor() {
					const instance = this;

					instance.stopDurationPreservation();

					instance.eventHandlers = null;
				},

				initializer(config) {
					const instance = this;

					instance.eventHandlers = [];

					instance._containerNode = instance.byId(config.containerId);
					instance._submitButtonNode = instance.byId(
						config.submitButtonId
					);

					instance._duration = 0;
					instance._endDate = new Date();
					instance._startDate = new Date();
					instance._validDate = true;

					instance._initPicker(instance.get('endDatePicker'));
					instance._initPicker(instance.get('endTimePicker'));
					instance._initPicker(instance.get('startDatePicker'));
					instance._initPicker(instance.get('startTimePicker'));

					instance._setEndDate();
					instance._setEndTime();
					instance._setStartDate();
					instance._setStartTime();
					instance._setDuration();

					instance.bindUI();
				},

				setDuration(duration) {
					const instance = this;

					instance._duration = duration;
				},

				startDurationPreservation() {
					const instance = this;

					instance.eventHandlers.push(
						instance
							.get('endDatePicker')
							.after(
								EVENT_SELECTION_CHANGE,
								instance._onEndDatePickerSelectionChange,
								instance
							),
						instance
							.get('endTimePicker')
							.after(
								EVENT_SELECTION_CHANGE,
								instance._onEndTimePickerSelectionChange,
								instance
							),
						instance
							.get('startDatePicker')
							.after(
								EVENT_SELECTION_CHANGE,
								instance._onStartDatePickerSelectionChange,
								instance
							),
						instance
							.get('startTimePicker')
							.after(
								EVENT_SELECTION_CHANGE,
								instance._onStartTimePickerSelectionChange,
								instance
							)
					);
				},

				stopDurationPreservation() {
					const instance = this;

					AArray.invoke(instance.eventHandlers, 'detach');
				},
			},
		});

		Liferay.IntervalSelector = IntervalSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base'],
	}
);
