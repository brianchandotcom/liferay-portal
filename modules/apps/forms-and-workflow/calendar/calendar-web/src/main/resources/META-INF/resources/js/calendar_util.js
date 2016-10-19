AUI.add(
	'liferay-calendar-util',
	function(A) {
		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;

		var Workflow = Liferay.Workflow;

		var isDate = Lang.isDate;

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var Time = {
			DAY: 86400000,
			HOUR: 3600000,
			MINUTE: 60000,
			SECOND: 1000,
			WEEK: 604800000,

			TIME_DESC: ['weeks', 'days', 'hours', 'minutes'],

			getDescription: function(milliseconds) {
				var instance = this;

				var desc = 'minutes';
				var value = 0;

				if (milliseconds > 0) {
					var timeArray = [Time.WEEK, Time.DAY, Time.HOUR, Time.MINUTE];

					timeArray.some(
						function(item, index) {
							value = milliseconds / item;
							desc = Time.TIME_DESC[index];

							return milliseconds % item === 0;
						}
					);
				}

				return {
					desc: desc,
					value: value
				};
			}
		};

		Liferay.Time = Time;

		var CalendarUtil = {
			NOTIFICATION_DEFAULT_TYPE: 'email',

			adjustSchedulerEventDisplayTime: function(schedulerEvent) {
				var instance = this;

				var allDay = schedulerEvent.get('allDay');

				var timeAdjuster = instance.toLocalTime;

				if (allDay) {
					timeAdjuster = instance.toLocalTimeWithoutUserTimeZone;
				}

				var endDate = schedulerEvent.get('endDate');
				var startDate = schedulerEvent.get('startDate');

				schedulerEvent.setAttrs(
					{
						endDate: timeAdjuster.call(instance, endDate),
						startDate: timeAdjuster.call(instance, startDate)
					},
					{
						silent: true
					}
				);
			},

			createSchedulerEvent: function(calendarBooking) {
				var instance = this;

				var endDate = new Date(calendarBooking.endTimeYear, calendarBooking.endTimeMonth, calendarBooking.endTimeDay, calendarBooking.endTimeHour, calendarBooking.endTimeMinute);
				var startDate = new Date(calendarBooking.startTimeYear, calendarBooking.startTimeMonth, calendarBooking.startTimeDay, calendarBooking.startTimeHour, calendarBooking.startTimeMinute);

				var schedulerEvent = new Liferay.SchedulerEvent(
					{
						allDay: calendarBooking.allDay,
						calendarBookingId: calendarBooking.calendarBookingId,
						calendarId: calendarBooking.calendarId,
						content: calendarBooking.title,
						description: calendarBooking.description,
						endDate: endDate.getTime(),
						firstReminder: calendarBooking.firstReminder,
						firstReminderType: calendarBooking.firstReminderType,
						hasChildCalendarBookings: calendarBooking.hasChildCalendarBookings,
						hasWorkflowInstanceLink: calendarBooking.hasWorkflowInstanceLink,
						instanceIndex: calendarBooking.instanceIndex,
						location: calendarBooking.location,
						parentCalendarBookingId: calendarBooking.parentCalendarBookingId,
						recurrence: calendarBooking.recurrence,
						recurringCalendarBookingId: calendarBooking.recurringCalendarBookingId,
						secondReminder: calendarBooking.secondReminder,
						secondReminderType: calendarBooking.secondReminderType,
						startDate: startDate.getTime(),
						status: calendarBooking.status
					}
				);

				return schedulerEvent;
			},

			destroyEvent: function(schedulerEvent) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				scheduler.removeEvents(schedulerEvent);

				scheduler.syncEventsUI();
			},

			getCalendarName: function(name, calendarResourceName) {
				var instance = this;

				if (name !== calendarResourceName) {
					name = [calendarResourceName, STR_DASH, name].join(STR_SPACE);
				}

				return name;
			},

			getDateFromObject: function(object) {
				var day = toInt(object.day);
				var hour = toInt(object.hour);
				var minute = toInt(object.minute);
				var month = toInt(object.month);
				var year = toInt(object.year);

				return new Date(year, month, day, hour, minute);
			},

			getDatesList: function(startDate, total) {
				var instance = this;

				var ADate = A.Date;

				var output = [];

				if (ADate.isValidDate(startDate)) {
					for (var i = 0; i < total; i++) {
						output.push(ADate.addDays(startDate, i));
					}
				}

				return output;
			},

			getLocalizationMap: function(value) {
				var instance = this;

				var map = {};

				map[themeDisplay.getLanguageId()] = value;

				return JSON.stringify(map);
			},

			setEventAttrs: function(schedulerEvent, data) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				var newCalendarId = data.calendarId;

				var oldCalendarId = schedulerEvent.get('calendarId');

				if (scheduler) {
					var calendarContainer = scheduler.get('calendarContainer');

					var newCalendar = calendarContainer.getCalendar(newCalendarId);
					var oldCalendar = calendarContainer.getCalendar(oldCalendarId);

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
							parentCalendarBookingId: data.parentCalendarBookingId,
							recurrence: data.recurrence,
							recurringCalendarBookingId: data.recurringCalendarBookingId,
							status: data.status
						},
						{
							silent: true
						}
					);

					scheduler.syncEventsUI();
				}
			},

			toLocalTime: function(utc) {
				var instance = this;

				if (!isDate(utc)) {
					utc = new Date(utc);
				}

				return DateMath.add(utc, DateMath.MINUTES, utc.getTimezoneOffset());
			},

			toUTC: function(date) {
				var instance = this;

				if (!isDate(date)) {
					date = new Date(date);
				}

				return DateMath.subtract(date, DateMath.MINUTES, date.getTimezoneOffset());
			},

			updateSchedulerEvents: function(schedulerEvents, calendarBooking) {
				A.each(
					schedulerEvents,
					function(schedulerEvent) {
						if (schedulerEvent.isRecurring()) {
							var scheduler = schedulerEvent.get('scheduler');

							scheduler.load();
						}

						if (calendarBooking.status === CalendarWorkflow.STATUS_DENIED) {
							CalendarUtil.destroyEvent(schedulerEvent);
						}
						else {
							schedulerEvent.set('status', calendarBooking.status);
						}
					}
				);
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
		requires: ['aui-datatype', 'aui-scheduler', 'aui-toolbar', 'autocomplete', 'autocomplete-highlighters', 'liferay-portlet-url']
	}
);