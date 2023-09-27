/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-util',
	(A) => {
		const DateMath = A.DataType.DateMath;
		const Lang = A.Lang;

		const Workflow = Liferay.Workflow;

		const isDate = Lang.isDate;

		const toInt = function (value) {
			return Lang.toInt(value, 10, 0);
		};

		const REGEX_UNFILLED_PARAMETER = /\{\s*([^|}]+?)\s*(?:\|([^}]*))?\s*\}/g;

		const STR_DASH = '-';

		const STR_SPACE = ' ';

		const Time = {
			DAY: 86400000,
			HOUR: 3600000,
			MINUTE: 60000,
			SECOND: 1000,
			WEEK: 604800000,

			// eslint-disable-next-line
			TIME_DESC: ['weeks', 'days', 'hours', 'minutes'],

			getDescription(milliseconds) {
				let description = 'minutes';
				let value = 0;

				if (milliseconds > 0) {
					const timeArray = [
						Time.WEEK,
						Time.DAY,
						Time.HOUR,
						Time.MINUTE,
					];

					timeArray.some((item, index) => {
						value = milliseconds / item;
						description = Time.TIME_DESC[index];

						return milliseconds % item === 0;
					});
				}

				return {
					description,
					value,
				};
			},
		};

		Liferay.Time = Time;

		const CalendarUtil = {
			NOTIFICATION_DEFAULT_TYPE: 'email',

			createSchedulerEvent(calendarBooking) {
				const endDate = new Date(
					calendarBooking.endTimeYear,
					calendarBooking.endTimeMonth,
					calendarBooking.endTimeDay,
					calendarBooking.endTimeHour,
					calendarBooking.endTimeMinute
				);
				const startDate = new Date(
					calendarBooking.startTimeYear,
					calendarBooking.startTimeMonth,
					calendarBooking.startTimeDay,
					calendarBooking.startTimeHour,
					calendarBooking.startTimeMinute
				);

				const schedulerEvent = new Liferay.SchedulerEvent({
					allDay: calendarBooking.allDay,
					calendarBookingId: calendarBooking.calendarBookingId,
					calendarId: calendarBooking.calendarId,
					calendarResourceName: calendarBooking.calendarResourceName,
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
					status: calendarBooking.status,
				});

				return schedulerEvent;
			},

			destroyEvent(schedulerEvent) {
				const scheduler = schedulerEvent.get('scheduler');

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
				const day = toInt(object.day);
				const hour = toInt(object.hour);
				const minute = toInt(object.minute);
				const month = toInt(object.month);
				const year = toInt(object.year);

				return new Date(year, month, day, hour, minute);
			},

			getDatesList(startDate, total) {
				const ADate = A.Date;

				const output = [];

				if (ADate.isValidDate(startDate)) {
					for (let i = 0; i < total; i++) {
						output.push(ADate.addDays(startDate, i));
					}
				}

				return output;
			},

			getLocalizationMap(value) {
				const map = {};

				map[themeDisplay.getLanguageId()] = value;

				return JSON.stringify(map);
			},

			setEventAttrs(schedulerEvent, data) {
				const scheduler = schedulerEvent.get('scheduler');

				const newCalendarId = data.calendarId;

				const oldCalendarId = schedulerEvent.get('calendarId');

				if (scheduler) {
					const calendarContainer = scheduler.get(
						'calendarContainer'
					);

					const newCalendar = calendarContainer.getCalendar(
						newCalendarId
					);
					const oldCalendar = calendarContainer.getCalendar(
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
							status: data.status,
						},
						{
							silent: true,
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
				A.each(schedulerEvents, (schedulerEvent) => {
					if (schedulerEvent.isRecurring()) {
						const scheduler = schedulerEvent.get('scheduler');

						scheduler.load();
					}

					schedulerEvent.set('status', calendarBooking.status);
				});
			},
		};

		Liferay.CalendarUtil = CalendarUtil;

		const CalendarWorkflow = {
			STATUS_MAYBE: 9,
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
			'autocomplete-highlighters',
		],
	}
);
