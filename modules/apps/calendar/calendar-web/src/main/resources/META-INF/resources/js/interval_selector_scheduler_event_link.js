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
	'liferay-calendar-interval-selector-scheduler-event-link',
	A => {
		var AArray = A.Array;

		var IntervalSelectorSchedulerEventLink = A.Component.create({
			ATTRS: {
				intervalSelector: {},

				schedulerEvent: {}
			},

			EXTENDS: A.Base,

			NAME: 'interval-selector-scheduler-event-link',

			prototype: {
				_updateEndDate() {
					var instance = this;

					var intervalSelector = instance.get('intervalSelector');

					var endDatePicker = intervalSelector.get('endDatePicker');

					var endTimePicker = intervalSelector.get('endTimePicker');

					var endDate = endDatePicker.getDate();

					var endTime = endTimePicker.getTime();

					endDate.setHours(endTime.getHours());

					endDate.setMinutes(endTime.getMinutes());

					instance._updateSchedulerEvent('endDate', endDate);
				},

				_updateIntervalSelector(event) {
					var instance = this;

					var prevDate = event.prevVal;

					var newDate = event.newVal;

					if (
						!instance._intervalSelectorUpdated &&
						prevDate.getTime() !== newDate.getTime()
					) {
						var intervalSelector = instance.get('intervalSelector');

						var schedulerEvent = instance.get('schedulerEvent');

						var attribute = event.attrName;

						var prefix = attribute.replace('Date', '');

						var date = schedulerEvent.get(attribute);

						var datePicker = intervalSelector.get(
							prefix + 'DatePicker'
						);

						var timePicker = intervalSelector.get(
							prefix + 'TimePicker'
						);

						intervalSelector.stopDurationPreservation();

						datePicker.deselectDates();
						datePicker.selectDates([date]);
						timePicker.selectDates([date]);

						intervalSelector.startDurationPreservation();
					}
				},

				_updateSchedulerEvent(eventDateType, eventDate) {
					var instance = this;

					var schedulerEvent = instance.get('schedulerEvent');

					var scheduler = schedulerEvent.get('scheduler');

					instance._intervalSelectorUpdated = true;

					schedulerEvent.set(eventDateType, eventDate);

					instance._intervalSelectorUpdated = false;

					scheduler.syncEventsUI();
				},

				_updateStartDate() {
					var instance = this;

					var intervalSelector = instance.get('intervalSelector');

					var startDatePicker = intervalSelector.get(
						'startDatePicker'
					);

					var startTimePicker = intervalSelector.get(
						'startTimePicker'
					);

					var startDate = startDatePicker.getDate();

					var startTime = startTimePicker.getTime();

					startDate.setHours(startTime.getHours());

					startDate.setMinutes(startTime.getMinutes());

					instance._updateSchedulerEvent('startDate', startDate);
				},

				bindUI() {
					var instance = this;

					var intervalSelector = instance.get('intervalSelector');

					var schedulerEvent = instance.get('schedulerEvent');

					var endDatePicker = intervalSelector.get('endDatePicker');

					var endTimePicker = intervalSelector.get('endTimePicker');

					var startDatePicker = intervalSelector.get(
						'startDatePicker'
					);

					var startTimePicker = intervalSelector.get(
						'startTimePicker'
					);

					instance.eventHandlers = [
						endDatePicker.after(
							'selectionChange',
							A.bind(instance._updateEndDate, instance)
						),
						endTimePicker.after(
							'selectionChange',
							A.bind(instance._updateEndDate, instance)
						),
						startDatePicker.after(
							'selectionChange',
							A.bind(instance._updateStartDate, instance)
						),
						startTimePicker.after(
							'selectionChange',
							A.bind(instance._updateStartDate, instance)
						),
						schedulerEvent.after(
							'endDateChange',
							A.bind(instance._updateIntervalSelector, instance)
						),
						schedulerEvent.after(
							'startDateChange',
							A.bind(instance._updateIntervalSelector, instance)
						)
					];
				},

				destructor() {
					var instance = this;

					instance.unlink();

					instance.eventHandlers = null;
				},

				initializer() {
					var instance = this;

					instance._intervalSelectorUpdated = false;

					instance.bindUI();
				},

				unlink() {
					var instance = this;

					AArray.invoke(instance.eventHandlers, 'detach');
				}
			}
		});

		Liferay.IntervalSelectorSchedulerEventLink = IntervalSelectorSchedulerEventLink;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base']
	}
);
