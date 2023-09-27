/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-interval-selector-scheduler-event-link',
	(A) => {
		const AArray = A.Array;

		const IntervalSelectorSchedulerEventLink = A.Component.create({
			ATTRS: {
				intervalSelector: {},

				schedulerEvent: {},
			},

			EXTENDS: A.Base,

			NAME: 'interval-selector-scheduler-event-link',

			prototype: {
				_updateEndDate() {
					const instance = this;

					const intervalSelector = instance.get('intervalSelector');

					const endDatePicker = intervalSelector.get('endDatePicker');

					const endTimePicker = intervalSelector.get('endTimePicker');

					const endDate = endDatePicker.getDate();

					const endTime = endTimePicker.getTime();

					endDate.setHours(endTime.getHours());

					endDate.setMinutes(endTime.getMinutes());

					instance._updateSchedulerEvent('endDate', endDate);
				},

				_updateIntervalSelector(event) {
					const instance = this;

					const prevDate = event.prevVal;

					const newDate = event.newVal;

					if (
						!instance._intervalSelectorUpdated &&
						prevDate.getTime() !== newDate.getTime()
					) {
						const intervalSelector = instance.get(
							'intervalSelector'
						);

						const schedulerEvent = instance.get('schedulerEvent');

						const attribute = event.attrName;

						const prefix = attribute.replace('Date', '');

						const date = schedulerEvent.get(attribute);

						const datePicker = intervalSelector.get(
							prefix + 'DatePicker'
						);

						const timePicker = intervalSelector.get(
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
					const instance = this;

					const schedulerEvent = instance.get('schedulerEvent');

					const scheduler = schedulerEvent.get('scheduler');

					instance._intervalSelectorUpdated = true;

					schedulerEvent.set(eventDateType, eventDate);

					instance._intervalSelectorUpdated = false;

					scheduler.syncEventsUI();
				},

				_updateStartDate() {
					const instance = this;

					const intervalSelector = instance.get('intervalSelector');

					const startDatePicker = intervalSelector.get(
						'startDatePicker'
					);

					const startTimePicker = intervalSelector.get(
						'startTimePicker'
					);

					const startDate = startDatePicker.getDate();

					const startTime = startTimePicker.getTime();

					startDate.setHours(startTime.getHours());

					startDate.setMinutes(startTime.getMinutes());

					instance._updateSchedulerEvent('startDate', startDate);
				},

				bindUI() {
					const instance = this;

					const intervalSelector = instance.get('intervalSelector');

					const schedulerEvent = instance.get('schedulerEvent');

					const endDatePicker = intervalSelector.get('endDatePicker');

					const endTimePicker = intervalSelector.get('endTimePicker');

					const startDatePicker = intervalSelector.get(
						'startDatePicker'
					);

					const startTimePicker = intervalSelector.get(
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
						),
					];
				},

				destructor() {
					const instance = this;

					instance.unlink();

					instance.eventHandlers = null;
				},

				initializer() {
					const instance = this;

					instance._intervalSelectorUpdated = false;

					instance.bindUI();
				},

				unlink() {
					const instance = this;

					AArray.invoke(instance.eventHandlers, 'detach');
				},
			},
		});

		Liferay.IntervalSelectorSchedulerEventLink = IntervalSelectorSchedulerEventLink;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base'],
	}
);
