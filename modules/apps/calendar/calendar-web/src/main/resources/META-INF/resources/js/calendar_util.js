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
	'liferay-calendar-util',
	A => {
		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;

		var Workflow = Liferay.Workflow;

		var isDate = Lang.isDate;

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var REGEX_UNFILLED_PARAMETER = /\{\s*([^|}]+?)\s*(?:\|([^}]*))?\s*\}/g;

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var Time = {
			DAY: 86400000,
			HOUR: 3600000,
			MINUTE: 60000,
			SECOND: 1000,
			WEEK: 604800000,

			// eslint-disable-next-line
			TIME_DESC: ['weeks', 'days', 'hours', 'minutes'],

			getDescription(milliseconds) {
				var desc = 'minutes';
				var value = 0;

				if (milliseconds > 0) {
					var timeArray = [
						Time.WEEK,
						Time.DAY,
						Time.HOUR,
						Time.MINUTE
					];

					timeArray.some((item, index) => {
						value = milliseconds / item;
						desc = Time.TIME_DESC[index];

						return milliseconds % item === 0;
					});
				}

				return {
					desc,
					value
				};
			}
		};

		Liferay.Time = Time;

		var CalendarUtil = {
			NOTIFICATION_DEFAULT_TYPE: 'email',

			createSchedulerEvent(calendarBooking) {
				var endDate = new Date(
					calendarBooking.endTimeYear,
					calendarBooking.endTimeMonth,
					calendarBooking.endTimeDay,
					calendarBooking.endTimeHour,
					calendarBooking.endTimeMinute
				);
				var startDate = new Date(
					calendarBooking.startTimeYear,
					calendarBooking.startTimeMonth,
					calendarBooking.startTimeDay,
					calendarBooking.startTimeHour,
					calendarBooking.startTimeMinute
				);

				var schedulerEvent = new Liferay.SchedulerEvent({
					allDay: calendarBooking.allDay,
					calendarBookingId: calendarBooking.calendarBookingId,
					calendarId: calendarBooking.calendarId,
					content: calendarBooking.title,
					description: calendarBooking.description,
					endDate: endDate.getTime(),
					firstReminder: calendarBooking.firstReminder,
					firstReminderType: calendarBooking.firstReminderType,
					hasChildCalendarBookings:
						calendarBooking.hasChildCalendarBookings,
					hasWorkflowInstanceLink:
						calendarBooking.hasWorkflowInstanceLink,
					instanceIndex: calendarBooking.instanceIndex,
					location: calendarBooking.location,
					parentCalendarBookingId:
						calendarBooking.parentCalendarBookingId,
					recurrence: calendarBooking.recurrence,
					recurringCalendarBookingId:
						calendarBooking.recurringCalendarBookingId,
					secondReminder: calendarBooking.secondReminder,
					secondReminderType: calendarBooking.secondReminderType,
					startDate: startDate.getTime(),
					status: calendarBooking.status
				});

				return schedulerEvent;
			},

			destroyEvent(schedulerEvent) {
				var scheduler = schedulerEvent.get('scheduler');

				scheduler.removeEvents(schedulerEvent);

				scheduler.syncEventsUI();
			},

			fillURLParameters(url, data) {
				url = Lang.sub(url, data);

				return url.replace(REGEX_UNFILLED_PARAMETER, '');
			},

			getCalendarName(name, calendarResourceName) {
				if (name !== calendarResourceName) {
					name = [calendarResourceName, STR_DASH, name].join(
						STR_SPACE
					);
				}

				return name;
			},

			getDateFromObject(object) {
				var day = toInt(object.day);
				var hour = toInt(object.hour);
				var minute = toInt(object.minute);
				var month = toInt(object.month);
				var year = toInt(object.year);

				return new Date(year, month, day, hour, minute);
			},

			getDatesList(startDate, total) {
				var ADate = A.Date;

				var output = [];

				if (ADate.isValidDate(startDate)) {
					for (var i = 0; i < total; i++) {
						output.push(ADate.addDays(startDate, i));
					}
				}

				return output;
			},

			getLocalizationMap(value) {
				var map = {};

				map[themeDisplay.getLanguageId()] = value;

				return JSON.stringify(map);
			},

			setEventAttrs(schedulerEvent, data) {
				var scheduler = schedulerEvent.get('scheduler');

				var newCalendarId = data.calendarId;

				var oldCalendarId = schedulerEvent.get('calendarId');

				if (scheduler) {
					var calendarContainer = scheduler.get('calendarContainer');

					var newCalendar = calendarContainer.getCalendar(
						newCalendarId
					);
					var oldCalendar = calendarContainer.getCalendar(
						oldCalendarId
					);

					if (oldCalendar !== newCalendar) {
						oldCalendar.remove(schedulerEvent);
					}

					if (newCalendar) {
						newCalendar.add(schedulerEvent);
					}

					schedulerEvent.setAttrs(
						{
							calendarBookingId: data.calendarBookingId,
							calendarId: newCalendarId,
							calendarResourceId: data.calendarResourceId,
							parentCalendarBookingId:
								data.parentCalendarBookingId,
							recurrence: data.recurrence,
							recurringCalendarBookingId:
								data.recurringCalendarBookingId,
							status: data.status
						},
						{
							silent: true
						}
					);

					scheduler.syncEventsUI();
				}
			},

			toLocalTime(utc) {
				if (!isDate(utc)) {
					utc = new Date(utc);
				}

				return DateMath.add(
					utc,
					DateMath.MINUTES,
					utc.getTimezoneOffset()
				);
			},

			toUTC(date) {
				if (!isDate(date)) {
					date = new Date(date);
				}

				return DateMath.subtract(
					date,
					DateMath.MINUTES,
					date.getTimezoneOffset()
				);
			},

			updateSchedulerEvents(schedulerEvents, calendarBooking) {
				A.each(schedulerEvents, schedulerEvent => {
					if (schedulerEvent.isRecurring()) {
						var scheduler = schedulerEvent.get('scheduler');

						scheduler.load();
					}

					schedulerEvent.set('status', calendarBooking.status);
				});
			}
		};

		Liferay.CalendarUtil = CalendarUtil;

		var CalendarWorkflow = {
			STATUS_MAYBE: 9
		};

		A.mix(CalendarWorkflow, Workflow);

		Liferay.CalendarWorkflow = CalendarWorkflow;
	},
	'',
	{
		requires: [
			'aui-datatype',
			'aui-scheduler',
			'aui-toolbar',
			'autocomplete',
			'autocomplete-highlighters'
		]
	}
);
