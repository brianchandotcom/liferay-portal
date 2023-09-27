/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-scheduler-models',
	(A) => {
		const DateMath = A.DataType.DateMath;
		const Lang = A.Lang;

		const CalendarWorkflow = Liferay.CalendarWorkflow;

		const isBoolean = Lang.isBoolean;
		const isFunction = Lang.isFunction;
		const isObject = Lang.isObject;
		const isValue = Lang.isValue;

		const toInitialCap = A.cached((str) => {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		});

		const toInt = function (value) {
			return Lang.toInt(value, 10, 0);
		};

		const STR_BLANK = '';

		const CalendarUtil = Liferay.CalendarUtil;

		const SchedulerModelSync = function () {};

		SchedulerModelSync.prototype = {
			_doRead() {
				const args = arguments;

				const callback = args[args.length - 1];

				if (isFunction(callback)) {
					callback();
				}
			},

			sync(action, options, callback) {
				const instance = this;

				const actionMethod = instance['_do' + toInitialCap(action)];

				if (isFunction(actionMethod)) {
					actionMethod.apply(instance, [options, callback]);
				}
			},
		};

		Liferay.SchedulerModelSync = SchedulerModelSync;

		const SchedulerEvent = A.Component.create({
			ATTRS: {
				calendarBookingId: {
					setter: toInt,
					value: 0,
				},

				calendarId: {
					setter: toInt,
					value: 0,
				},

				calendarResourceName: {
					setter: String,
					validator: isValue,
					value: STR_BLANK,
				},

				content: {
					getter(val) {
						let content = val;

						if (val) {
							content = Liferay.Util.escapeHTML(val);
						}

						return content;
					},
					setter(val) {
						let content = val;

						if (val) {
							content = Liferay.Util.unescapeHTML(val + '');
						}

						return content;
					},
				},

				description: {
					setter: String,
					validator: isValue,
					value: STR_BLANK,
				},

				editingEvent: {
					validator: isBoolean,
					value: false,
				},

				firstReminder: {
					setter: toInt,
					value: 0,
				},

				firstReminderType: {
					setter: String,
					validator: isValue,
					value: CalendarUtil.NOTIFICATION_DEFAULT_TYPE,
				},

				hasChildCalendarBookings: {
					validator: isBoolean,
					value: false,
				},

				hasWorkflowInstanceLink: {
					validator: isBoolean,
					value: false,
				},

				instanceIndex: {
					setter: toInt,
					value: 0,
				},

				loading: {
					validator: isBoolean,
					value: false,
				},

				location: {
					setter: String,
					validator: isValue,
					value: STR_BLANK,
				},

				parentCalendarBookingId: {
					setter: toInt,
					value: 0,
				},

				recurrence: {
					setter: String,
					validator: isValue,
					value: STR_BLANK,
				},

				recurringCalendarBookingId: {
					setter: toInt,
					value: 0,
				},

				reminder: {
					getter() {
						const instance = this;

						return (
							instance.get('firstReminder') > 0 ||
							instance.get('secondReminder') > 0
						);
					},
				},

				repeated: {
					getter: 'isRecurring',
				},

				secondReminder: {
					setter: toInt,
					value: 0,
				},

				secondReminderType: {
					setter: String,
					validator: isValue,
					value: CalendarUtil.NOTIFICATION_DEFAULT_TYPE,
				},

				status: {
					setter: toInt,
					value: 0,
				},
			},

			EXTENDS: A.SchedulerEvent,

			NAME: 'scheduler-event',

			PROPAGATE_ATTRS: A.SchedulerEvent.PROPAGATE_ATTRS.concat([
				'calendarBookingId',
				'calendarId',
				'calendarResourceId',
				'parentCalendarBookingId',
				'recurrence',
				'recurringCalendarBookingId',
				'status',
			]),

			prototype: {
				_isPastEvent() {
					const instance = this;

					const endDate = instance.get('endDate');

					let result;

					const scheduler = instance.get('scheduler');

					if (scheduler) {
						const currentTime = scheduler.get('currentTime');

						result = endDate.getTime() < currentTime.getTime();
					}
					else {
						result = false;
					}

					return result;
				},

				_isShortDurationEventIntersecting(evtStartDate) {
					const instance = this;
					let shortDurationEventIntersecting = false;

					if (instance.getMinutesDuration() < 30) {
						const earlierEvtStartDate = DateMath.subtract(
							DateMath.clone(evtStartDate),
							DateMath.MINUTES,
							30
						);
						const endDate = instance.get('endDate');

						shortDurationEventIntersecting =
							DateMath.between(
								endDate,
								earlierEvtStartDate,
								evtStartDate
							) || DateMath.compare(endDate, evtStartDate);
					}

					return shortDurationEventIntersecting;
				},

				_onColorChange(event) {
					const instance = this;

					instance._uiSetColor(event.newVal);
				},

				_onLoadingChange(event) {
					const instance = this;

					instance._uiSetLoading(event.newVal);
				},

				_onStartDateChange(event) {
					const instance = this;

					instance._uiSetStartDate(event.newVal);
				},

				_onStatusChange(event) {
					const instance = this;

					instance._uiSetStatus(event.newVal);
				},

				_uiSetColor(value) {
					const instance = this;
					const node = instance.get('node');
					const opacity = instance._isPastEvent() ? '26' : 'B3';

					node.setStyles({
						backgroundColor: value + opacity,
					});
				},

				_uiSetEndDate(val) {
					const instance = this;

					Liferay.SchedulerEvent.superclass._uiSetEndDate.apply(
						instance,
						arguments
					);

					const node = instance.get('node');

					node.attr(
						'data-endDate',
						instance._formatDate(val, '%m/%d/%Y')
					);
					node.attr(
						'data-endTime',
						instance._formatDate(val, '%I:%M %p')
					);
				},

				_uiSetLoading(val) {
					const instance = this;

					instance
						.get('node')
						.toggleClass('calendar-portlet-event-loading', val);
				},

				_uiSetStartDate(val) {
					const instance = this;

					const node = instance.get('node');

					node.attr(
						'data-startDate',
						instance._formatDate(val, '%m/%d/%Y')
					);
					node.attr(
						'data-startTime',
						instance._formatDate(val, '%I:%M %p')
					);
				},

				_uiSetStatus(val) {
					const instance = this;

					const node = instance.get('node');

					node.toggleClass(
						'calendar-portlet-event-approved',
						val === CalendarWorkflow.STATUS_APPROVED
					);
					node.toggleClass(
						'calendar-portlet-event-denied',
						val === CalendarWorkflow.STATUS_DENIED
					);
					node.toggleClass(
						'calendar-portlet-event-draft',
						val === CalendarWorkflow.STATUS_DRAFT
					);
					node.toggleClass(
						'calendar-portlet-event-maybe',
						val === CalendarWorkflow.STATUS_MAYBE
					);
					node.toggleClass(
						'calendar-portlet-event-pending',
						val === CalendarWorkflow.STATUS_PENDING
					);
				},

				eventModel: Liferay.SchedulerEvent,

				initializer() {
					const instance = this;

					instance._uiSetLoading(instance.get('loading'));
					instance._uiSetStartDate(instance.get('startDate'));
					instance._uiSetStatus(instance.get('status'));

					instance.on('colorChange', instance._onColorChange);
					instance.on('loadingChange', instance._onLoadingChange);
					instance.on('startDateChange', instance._onStartDateChange);
					instance.on('statusChange', instance._onStatusChange);
				},

				intersects(event) {
					const instance = this;

					const endDate = instance.get('endDate');
					const startDate = instance.get('startDate');

					const evtStartDate = event.get('startDate');

					return (
						DateMath.between(evtStartDate, startDate, endDate) ||
						instance._isShortDurationEventIntersecting(
							evtStartDate
						) ||
						instance.sameStartDate(event)
					);
				},

				isMasterBooking() {
					const instance = this;

					return (
						instance.get('parentCalendarBookingId') ===
						instance.get('calendarBookingId')
					);
				},

				isRecurring() {
					const instance = this;

					return (
						instance.get('recurrence') !== STR_BLANK ||
						instance.get('calendarBookingId') !==
							instance.get('recurringCalendarBookingId')
					);
				},

				syncNodeColorUI() {
					const instance = this;

					Liferay.SchedulerEvent.superclass.syncNodeColorUI.apply(
						instance,
						arguments
					);

					const node = instance.get('node');
					const scheduler = instance.get('scheduler');

					if (scheduler && !instance.get('editingEvent')) {
						const activeViewName = scheduler
							.get('activeView')
							.get('name');

						if (
							activeViewName === 'month' &&
							!instance.get('allDay')
						) {
							node.setStyles({
								backgroundColor: instance.get('color'),
								border: 'none',
								color: '#111',
								padding: '0 2px',
							});
						}
					}
				},

				syncNodeTitleUI() {
					const instance = this;
					const format = instance.get('titleDateFormat');
					const startDate = instance.get('startDate');
					const endDate = instance.get('endDate');
					const title = [];

					if (format.startDate) {
						title.push(
							instance._formatDate(startDate, format.startDate) +
								' '
						);
					}

					if (format.endDate) {
						title.push(
							instance._formatDate(endDate, format.endDate)
						);
					}

					instance.setTitle(title.join(''));
				},

				syncUI() {
					const instance = this;

					Liferay.SchedulerEvent.superclass.syncUI.apply(
						instance,
						arguments
					);

					instance._uiSetStatus(instance.get('status'));
				},

				syncWithServer() {
					const instance = this;

					const calendarBookingId = instance.get('calendarBookingId');

					const scheduler = instance.get('scheduler');

					const schedulerEvents = scheduler.getEventsByCalendarBookingId(
						calendarBookingId
					);

					const remoteServices = scheduler.get('remoteServices');

					remoteServices.getEvent(
						calendarBookingId,
						A.bind(
							CalendarUtil.updateSchedulerEvents,
							CalendarUtil,
							schedulerEvents
						)
					);
				},
			},
		});

		Liferay.SchedulerEvent = SchedulerEvent;

		const Calendar = A.Component.create({
			ATTRS: {
				calendarId: {
					setter: toInt,
					value: 0,
				},

				calendarResourceId: {
					setter: toInt,
					value: 0,
				},

				calendarResourceName: {
					setter: String,
					validator: isValue,
					value: STR_BLANK,
				},

				classNameId: {
					setter: toInt,
					value: 0,
				},

				classPK: {
					setter: toInt,
					value: 0,
				},

				defaultCalendar: {
					setter: A.DataType.Boolean.parse,
					value: false,
				},

				groupId: {
					setter: toInt,
					value: 0,
				},

				hasMenuItems: {
					setter: A.DataType.Boolean.parse,
					value: true,
				},

				manageable: {
					setter: A.DataType.Boolean.parse,
					value: true,
				},

				permissions: {
					lazyAdd: false,
					setter(val) {
						const instance = this;

						instance.set('disabled', !val.MANAGE_BOOKINGS);

						return val;
					},
					validator: isObject,
					value: {},
				},

				showCalendarResourceName: {
					value: true,
				},
			},

			EXTENDS: A.SchedulerCalendar,

			NAME: 'scheduler-calendar',

			prototype: {
				_afterColorChange(event) {
					const instance = this;

					Calendar.superclass._afterColorChange.apply(
						instance,
						arguments
					);

					const calendarId = instance.get('calendarId');

					const color = event.newVal;

					if (instance.get('permissions.UPDATE')) {
						const scheduler = instance.get('scheduler');

						const remoteServices = scheduler.get('remoteServices');

						remoteServices.updateCalendarColor(calendarId, color);
					}
					else {
						Liferay.Util.Session.set(
							'com.liferay.calendar.web_calendar' +
								calendarId +
								'Color',
							color
						);
					}
				},

				_afterVisibleChange() {
					const instance = this;

					Calendar.superclass._afterVisibleChange.apply(
						instance,
						arguments
					);

					const scheduler = instance.get('scheduler');

					scheduler.syncEventsUI();
				},

				getDisplayName() {
					const instance = this;

					let name = instance.get('name');

					const showCalendarResourceName = instance.get(
						'showCalendarResourceName'
					);

					if (showCalendarResourceName) {
						const calendarResourceName = instance.get(
							'calendarResourceName'
						);

						name = CalendarUtil.getCalendarName(
							name,
							calendarResourceName
						);
					}

					return name;
				},
			},
		});

		Liferay.SchedulerCalendar = Calendar;

		Liferay.SchedulerEvents = A.Base.create(
			'scheduler-events',
			A.SchedulerEvents,
			[Liferay.SchedulerModelSync],
			{
				_doRead(_options, callback) {
					const instance = this;

					const scheduler = instance.get('scheduler');

					const activeView = scheduler.get('activeView');
					const eventsPerPage = scheduler.get('eventsPerPage');
					const filterCalendarBookings = scheduler.get(
						'filterCalendarBookings'
					);
					const maxDaysDisplayed = scheduler.get('maxDaysDisplayed');

					const calendarContainer = scheduler.get(
						'calendarContainer'
					);

					const calendarIds = Object.keys(
						calendarContainer.get('availableCalendars')
					);

					const remoteServices = scheduler.get('remoteServices');

					remoteServices.getEvents(
						calendarIds,
						instance.getEventsPerPage(activeView, eventsPerPage),
						instance.getLoadStartDate(activeView),
						instance.getLoadEndDate(activeView, maxDaysDisplayed),
						[
							CalendarWorkflow.STATUS_APPROVED,
							CalendarWorkflow.STATUS_DENIED,
							CalendarWorkflow.STATUS_DRAFT,
							CalendarWorkflow.STATUS_MAYBE,
							CalendarWorkflow.STATUS_PENDING,
						],
						(calendarBookings) => {
							if (filterCalendarBookings) {
								calendarBookings = calendarBookings.filter(
									filterCalendarBookings
								);
							}

							callback(null, calendarBookings);
						}
					);
				},

				getEventsPerPage(activeView, eventsPerPage) {
					const viewName = activeView.get('name');

					if (viewName !== 'agenda') {
						eventsPerPage = -1;
					}

					return eventsPerPage;
				},

				getLoadEndDate(activeView, maxDaysDisplayed) {
					let date = activeView.getNextDate();

					const viewName = activeView.get('name');

					if (viewName === 'agenda') {
						date = DateMath.add(
							date,
							DateMath.DAY,
							maxDaysDisplayed - 1
						);

						date = DateMath.subtract(date, DateMath.MINUTES, 1);
					}
					else if (viewName === 'month') {
						date = DateMath.add(date, DateMath.WEEK, 1);
					}

					return date;
				},

				getLoadStartDate(activeView) {
					const scheduler = activeView.get('scheduler');
					const viewName = activeView.get('name');

					let date = scheduler.get('viewDate');

					if (viewName === 'month') {
						date = DateMath.subtract(date, DateMath.WEEK, 1);
					}

					return date;
				},
			},
			{}
		);
	},
	'',
	{
		requires: ['aui-datatype', 'dd-plugin', 'liferay-calendar-util'],
	}
);
