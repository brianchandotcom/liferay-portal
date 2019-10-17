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
	'liferay-scheduler-models',
	A => {
		var AObject = A.Object;

		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;
		var LString = Lang.String;

		var CalendarWorkflow = Liferay.CalendarWorkflow;

		var isBoolean = Lang.isBoolean;
		var isFunction = Lang.isFunction;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var toInitialCap = A.cached(str => {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		});

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var STR_BLANK = '';

		var CalendarUtil = Liferay.CalendarUtil;

		var SchedulerModelSync = function() {};

		SchedulerModelSync.prototype = {
			_doRead() {
				var args = arguments;

				var callback = args[args.length - 1];

				if (isFunction(callback)) {
					callback();
				}
			},

			sync(action, options, callback) {
				var instance = this;

				var actionMethod = instance['_do' + toInitialCap(action)];

				if (isFunction(actionMethod)) {
					actionMethod.apply(instance, [options, callback]);
				}
			}
		};

		Liferay.SchedulerModelSync = SchedulerModelSync;

		var SchedulerEvent = A.Component.create({
			ATTRS: {
				calendarBookingId: {
					setter: toInt,
					value: 0
				},

				calendarId: {
					setter: toInt,
					value: 0
				},

				content: {
					getter(val) {
						var content = val;

						if (val) {
							content = LString.escapeHTML(val);
						}

						return content;
					},
					setter(val) {
						var content = val;

						if (val) {
							content = LString.unescapeHTML(val + '');
						}

						return content;
					}
				},

				description: {
					setter: String,
					validator: isValue,
					value: STR_BLANK
				},

				editingEvent: {
					validator: isBoolean,
					value: false
				},

				firstReminder: {
					setter: toInt,
					value: 0
				},

				firstReminderType: {
					setter: String,
					validator: isValue,
					value: CalendarUtil.NOTIFICATION_DEFAULT_TYPE
				},

				hasChildCalendarBookings: {
					validator: isBoolean,
					value: false
				},

				hasWorkflowInstanceLink: {
					validator: isBoolean,
					value: false
				},

				instanceIndex: {
					setter: toInt,
					value: 0
				},

				loading: {
					validator: isBoolean,
					value: false
				},

				location: {
					setter: String,
					validator: isValue,
					value: STR_BLANK
				},

				parentCalendarBookingId: {
					setter: toInt,
					value: 0
				},

				recurrence: {
					setter: String,
					validator: isValue,
					value: STR_BLANK
				},

				recurringCalendarBookingId: {
					setter: toInt,
					value: 0
				},

				reminder: {
					getter() {
						var instance = this;

						return (
							instance.get('firstReminder') > 0 ||
							instance.get('secondReminder') > 0
						);
					}
				},

				repeated: {
					getter: 'isRecurring'
				},

				secondReminder: {
					setter: toInt,
					value: 0
				},

				secondReminderType: {
					setter: String,
					validator: isValue,
					value: CalendarUtil.NOTIFICATION_DEFAULT_TYPE
				},

				status: {
					setter: toInt,
					value: 0
				}
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
				'status'
			]),

			prototype: {
				_isPastEvent() {
					var instance = this;

					var endDate = instance.get('endDate');

					var result;

					var scheduler = instance.get('scheduler');

					if (scheduler) {
						var currentTime = scheduler.get('currentTime');

						result = endDate.getTime() < currentTime.getTime();
					} else {
						result = false;
					}

					return result;
				},

				_isShortDurationEventIntersecting(evtStartDate) {
					var instance = this;
					var shortDurationEventIntersecting = false;

					if (instance.getMinutesDuration() < 30) {
						var earlierEvtStartDate = DateMath.subtract(
							DateMath.clone(evtStartDate),
							DateMath.MINUTES,
							30
						);
						var endDate = instance.get('endDate');

						shortDurationEventIntersecting =
							DateMath.between(
								endDate,
								earlierEvtStartDate,
								evtStartDate
							) || DateMath.compare(endDate, evtStartDate);
					}

					return shortDurationEventIntersecting;
				},

				_onLoadingChange(event) {
					var instance = this;

					instance._uiSetLoading(event.newVal);
				},

				_onStartDateChange(event) {
					var instance = this;

					instance._uiSetStartDate(event.newVal);
				},

				_onStatusChange(event) {
					var instance = this;

					instance._uiSetStatus(event.newVal);
				},

				_uiSetEndDate(val) {
					var instance = this;

					Liferay.SchedulerEvent.superclass._uiSetEndDate.apply(
						instance,
						arguments
					);

					var node = instance.get('node');

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
					var instance = this;

					instance
						.get('node')
						.toggleClass('calendar-portlet-event-loading', val);
				},

				_uiSetStartDate(val) {
					var instance = this;

					var node = instance.get('node');

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
					var instance = this;

					var node = instance.get('node');

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
					var instance = this;

					instance._uiSetLoading(instance.get('loading'));
					instance._uiSetStartDate(instance.get('startDate'));
					instance._uiSetStatus(instance.get('status'));

					instance.on('loadingChange', instance._onLoadingChange);
					instance.on('startDateChange', instance._onStartDateChange);
					instance.on('statusChange', instance._onStatusChange);
				},

				intersects(evt) {
					var instance = this;

					var endDate = instance.get('endDate');
					var startDate = instance.get('startDate');

					var evtStartDate = evt.get('startDate');

					return (
						DateMath.between(evtStartDate, startDate, endDate) ||
						instance._isShortDurationEventIntersecting(
							evtStartDate
						) ||
						instance.sameStartDate(evt)
					);
				},

				isMasterBooking() {
					var instance = this;

					return (
						instance.get('parentCalendarBookingId') ===
						instance.get('calendarBookingId')
					);
				},

				isRecurring() {
					var instance = this;

					return (
						instance.get('recurrence') !== STR_BLANK ||
						instance.get('calendarBookingId') !=
							instance.get('recurringCalendarBookingId')
					);
				},

				syncNodeColorUI() {
					var instance = this;

					Liferay.SchedulerEvent.superclass.syncNodeColorUI.apply(
						instance,
						arguments
					);

					var node = instance.get('node');
					var scheduler = instance.get('scheduler');

					if (scheduler && !instance.get('editingEvent')) {
						var activeViewName = scheduler
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
								padding: '0 2px'
							});
						}
					}
				},

				syncUI() {
					var instance = this;

					Liferay.SchedulerEvent.superclass.syncUI.apply(
						instance,
						arguments
					);

					instance._uiSetStatus(instance.get('status'));
				},

				syncWithServer() {
					var instance = this;

					var calendarBookingId = instance.get('calendarBookingId');

					var scheduler = instance.get('scheduler');

					var schedulerEvents = scheduler.getEventsByCalendarBookingId(
						calendarBookingId
					);

					var remoteServices = scheduler.get('remoteServices');

					remoteServices.getEvent(
						calendarBookingId,
						A.bind(
							CalendarUtil.updateSchedulerEvents,
							CalendarUtil,
							schedulerEvents
						)
					);
				}
			}
		});

		Liferay.SchedulerEvent = SchedulerEvent;

		var Calendar = A.Component.create({
			ATTRS: {
				calendarId: {
					setter: toInt,
					value: 0
				},

				calendarResourceId: {
					setter: toInt,
					value: 0
				},

				calendarResourceName: {
					setter: String,
					validator: isValue,
					value: STR_BLANK
				},

				classNameId: {
					setter: toInt,
					value: 0
				},

				classPK: {
					setter: toInt,
					value: 0
				},

				defaultCalendar: {
					setter: A.DataType.Boolean.parse,
					value: false
				},

				groupId: {
					setter: toInt,
					value: 0
				},

				manageable: {
					setter: A.DataType.Boolean.parse,
					value: true
				},

				permissions: {
					lazyAdd: false,
					setter(val) {
						var instance = this;

						instance.set('disabled', !val.MANAGE_BOOKINGS);

						return val;
					},
					validator: isObject,
					value: {}
				},

				showCalendarResourceName: {
					value: true
				}
			},

			EXTENDS: A.SchedulerCalendar,

			NAME: 'scheduler-calendar',

			prototype: {
				_afterColorChange(event) {
					var instance = this;

					Calendar.superclass._afterColorChange.apply(
						instance,
						arguments
					);

					var calendarId = instance.get('calendarId');

					var color = event.newVal;

					if (instance.get('permissions.UPDATE')) {
						var scheduler = instance.get('scheduler');

						var remoteServices = scheduler.get('remoteServices');

						remoteServices.updateCalendarColor(calendarId, color);
					} else {
						Liferay.Util.Session.set(
							'com.liferay.calendar.web_calendar' +
								calendarId +
								'Color',
							color
						);
					}
				},

				_afterVisibleChange() {
					var instance = this;

					Calendar.superclass._afterVisibleChange.apply(
						instance,
						arguments
					);

					var scheduler = instance.get('scheduler');

					scheduler.syncEventsUI();
				},

				getDisplayName() {
					var instance = this;

					var name = instance.get('name');

					var showCalendarResourceName = instance.get(
						'showCalendarResourceName'
					);

					if (showCalendarResourceName) {
						var calendarResourceName = instance.get(
							'calendarResourceName'
						);

						name = CalendarUtil.getCalendarName(
							name,
							calendarResourceName
						);
					}

					return name;
				}
			}
		});

		Liferay.SchedulerCalendar = Calendar;

		Liferay.SchedulerEvents = A.Base.create(
			'scheduler-events',
			A.SchedulerEvents,
			[Liferay.SchedulerModelSync],
			{
				_doRead(_options, callback) {
					var instance = this;

					var scheduler = instance.get('scheduler');

					var activeView = scheduler.get('activeView');
					var eventsPerPage = scheduler.get('eventsPerPage');
					var filterCalendarBookings = scheduler.get(
						'filterCalendarBookings'
					);
					var maxDaysDisplayed = scheduler.get('maxDaysDisplayed');

					var calendarContainer = scheduler.get('calendarContainer');

					var calendarIds = AObject.keys(
						calendarContainer.get('availableCalendars')
					);

					var remoteServices = scheduler.get('remoteServices');

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
							CalendarWorkflow.STATUS_PENDING
						],
						calendarBookings => {
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
					var viewName = activeView.get('name');

					if (viewName !== 'agenda') {
						eventsPerPage = -1;
					}

					return eventsPerPage;
				},

				getLoadEndDate(activeView, maxDaysDisplayed) {
					var date = activeView.getNextDate();

					var viewName = activeView.get('name');

					if (viewName === 'agenda') {
						date = DateMath.add(
							date,
							DateMath.DAY,
							maxDaysDisplayed
						);

						date = DateMath.subtract(date, DateMath.MINUTES, 1);
					} else if (viewName === 'month') {
						date = DateMath.add(date, DateMath.WEEK, 1);
					}

					return date;
				},

				getLoadStartDate(activeView) {
					var scheduler = activeView.get('scheduler');
					var viewName = activeView.get('name');

					var date = scheduler.get('viewDate');

					if (viewName === 'month') {
						date = DateMath.subtract(date, DateMath.WEEK, 1);
					}

					return date;
				}
			},
			{}
		);
	},
	'',
	{
		requires: ['aui-datatype', 'dd-plugin', 'liferay-calendar-util']
	}
);
