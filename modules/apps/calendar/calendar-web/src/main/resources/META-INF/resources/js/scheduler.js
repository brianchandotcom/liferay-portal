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
	'liferay-scheduler',
	A => {
		var AArray = A.Array;
		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;

		var RecurrenceUtil = Liferay.RecurrenceUtil;

		var isBoolean = Lang.isBoolean;
		var isDate = Lang.isDate;
		var isFunction = Lang.isFunction;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var CONTROLS_NODE = 'controlsNode';

		var DAYS_OF_WEEK = ['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'];

		var ICON_ADD_EVENT_NODE = 'iconAddEventNode';

		var STR_BLANK = '';

		var TPL_ICON_ADD_EVENT_NODE =
			'<div class="btn-group">' +
			'<button class="btn btn-primary calendar-add-event-btn" type="button">' +
			Liferay.Language.get('add-calendar-booking') +
			'</button>' +
			'</div>';

		var TPL_SCHEDULER_ICON_NEXT =
			'<button aria-label="{ariaLabel}"" role="button" type="button" class="btn btn-secondary scheduler-base-icon-next">' +
			Liferay.Util.getLexiconIconTpl('angle-right') +
			'</button>';

		var TPL_SCHEDULER_ICON_PREV =
			'<button aria-label="{ariaLabel}"" role="button" type="button" class="btn btn-secondary scheduler-base-icon-prev">' +
			Liferay.Util.getLexiconIconTpl('angle-left') +
			'</button>';

		var WEEKLY = 'WEEKLY';

		var Time = Liferay.Time;

		A.mix(A.DataType.DateMath, {
			getWeeksInMonth(date, firstDayOfWeek) {
				var daysInMonth = DateMath.getDaysInMonth(
					date.getFullYear(),
					date.getMonth()
				);
				var firstWeekDay =
					DateMath.getDate(
						date.getFullYear(),
						date.getMonth(),
						1
					).getDay() - firstDayOfWeek;

				if (firstWeekDay < 0) {
					firstWeekDay = firstWeekDay + 7;
				}

				var daysInFirstWeek = DateMath.WEEK_LENGTH - firstWeekDay;

				return (
					Math.ceil(
						(daysInMonth - daysInFirstWeek) / DateMath.WEEK_LENGTH
					) + 1
				);
			}
		});

		var CalendarUtil = Liferay.CalendarUtil;

		var Scheduler = A.Component.create({
			ATTRS: {
				calendarContainer: {
					validator: isObject,
					value: null
				},

				currentTime: {
					validator: isDate,
					value: new Date()
				},

				eventsPerPage: {},

				filterCalendarBookings: {
					validator: isFunction
				},

				iconAddEventNode: {
					valueFn() {
						return A.Node.create(TPL_ICON_ADD_EVENT_NODE);
					}
				},

				iconNextNode: {
					valueFn() {
						var instance = this;

						return A.Node.create(
							A.Lang.sub(TPL_SCHEDULER_ICON_NEXT, {
								ariaLabel: instance.getAriaLabel('next')
							})
						);
					}
				},

				iconPrevNode: {
					valueFn() {
						var instance = this;

						return A.Node.create(
							A.Lang.sub(TPL_SCHEDULER_ICON_PREV, {
								ariaLabel: instance.getAriaLabel('previous')
							})
						);
					}
				},

				maxDaysDisplayed: {},

				portletNamespace: {
					setter: String,
					validator: isValue,
					value: STR_BLANK
				},

				preventPersistence: {
					validator: isBoolean,
					value: false
				},

				remoteServices: {
					validator: isObject,
					value: null
				},

				showAddEventBtn: {
					validator: isBoolean,
					value: true
				}
			},

			AUGMENTS: [Liferay.RecurrenceConverter],

			EXTENDS: A.Scheduler,

			NAME: 'scheduler-base',

			prototype: {
				_afterActiveViewChange(event) {
					var instance = this;

					instance.resetEvents();

					Scheduler.superclass._afterActiveViewChange.apply(
						this,
						arguments
					);

					Liferay.Util.Session.set(
						'com.liferay.calendar.web_defaultView',
						event.newVal.get('name')
					);

					instance.load();
				},

				_afterDateChange() {
					var instance = this;

					instance.load();
				},

				_afterSchedulerEventChange(event) {
					var instance = this;

					if (!instance.get('preventPersistence')) {
						var changedAttributes = event.changed;

						var persistentAttrMap = {
							calendarId: 1,
							color: 1,
							content: 1,
							endDate: 1,
							endTime: 1,
							startDate: 1,
							startTime: 1
						};

						var persist = true;

						A.each(changedAttributes, (_item, index) => {
							persist = Object.prototype.hasOwnProperty.call(
								persistentAttrMap,
								index
							);
						});

						if (persist) {
							var schedulerEvent = event.target;

							instance._updateSchedulerEvent(
								schedulerEvent,
								changedAttributes
							);
						}
					}
				},

				_bindCurrentTimeInterval() {
					var instance = this;

					instance._currentTimeInterval = setInterval(
						A.bind(instance._updateCurrentTime, instance),
						60000
					);
				},

				_createViewTriggerNode(view) {
					var node = Scheduler.superclass._createViewTriggerNode.apply(
						this,
						arguments
					);

					var schedulerViewText = '';

					var viewName = view.get('name');

					if (viewName == 'agenda') {
						schedulerViewText = Liferay.Language.get('agenda-view');
					} else if (viewName == 'day') {
						schedulerViewText = Liferay.Language.get('day-view');
					} else if (viewName == 'month') {
						schedulerViewText = Liferay.Language.get('month-view');
					} else if (viewName == 'week') {
						schedulerViewText = Liferay.Language.get('week-view');
					}

					if (node.get('nodeName') === 'OPTION') {
						node.text(schedulerViewText);
					}

					return node;
				},

				_getCalendarBookingDuration(schedulerEvent) {
					var duration =
						schedulerEvent.getSecondsDuration() * Time.SECOND;

					return duration;
				},

				_getCalendarBookingOffset(schedulerEvent, changedAttributes) {
					var offset = 0;

					if (changedAttributes.startDate) {
						offset =
							changedAttributes.startDate.newVal.getTime() -
							changedAttributes.startDate.prevVal.getTime();
					}

					return offset;
				},

				_getNewRecurrence(schedulerEvent, changedAttributes) {
					var instance = this;

					var recurrence = instance.parseRecurrence(
						schedulerEvent.get('recurrence')
					);

					if (
						recurrence &&
						changedAttributes.startDate &&
						changedAttributes.endDate
					) {
						var rrule = recurrence.rrule;

						var newDate = changedAttributes.startDate.newVal;

						var prevDate = changedAttributes.startDate.prevVal;

						if (DateMath.isDayOverlap(prevDate, newDate)) {
							if (rrule.freq === WEEKLY) {
								var index = rrule.byday.indexOf(
									DAYS_OF_WEEK[prevDate.getDay()]
								);

								AArray.remove(rrule.byday, index);

								rrule.byday.push(
									DAYS_OF_WEEK[newDate.getDay()]
								);
							} else if (rrule.byday) {
								var position = Math.ceil(
									newDate.getDate() / DateMath.WEEK_LENGTH
								);

								var futureDate = DateMath.add(
									newDate,
									DateMath.WEEK,
									1
								);

								var lastDayOfWeek =
									futureDate.getMonth() !==
									newDate.getMonth();

								if (
									position > 4 ||
									(lastDayOfWeek &&
										rrule.byday.position === -1)
								) {
									position = -1;
								}

								rrule.byday.position = position;
								rrule.byday.dayOfWeek =
									DAYS_OF_WEEK[newDate.getDay()];

								if (rrule.bymonth) {
									rrule.bymonth = newDate.getMonth() + 1;
								}
							}
						}
					}

					return recurrence;
				},

				_onClickAddEvent() {
					var instance = this;

					var recorder = instance.get('eventRecorder');

					var activeViewName = instance.get('activeView').get('name');

					var calendarContainer = instance.get('calendarContainer');

					var defaultCalendar = calendarContainer.get(
						'defaultCalendar'
					);

					var calendarId = defaultCalendar.get('calendarId');

					var editCalendarBookingURL = decodeURIComponent(
						recorder.get('editCalendarBookingURL')
					);

					var startTimeDate = instance.get('date');

					var data = {
						activeView: activeViewName,
						calendarId,
						startTimeDay: startTimeDate.getDate(),
						startTimeMonth: startTimeDate.getMonth(),
						startTimeYear: startTimeDate.getFullYear(),
						titleCurrentValue: ''
					};

					Liferay.Util.openWindow({
						dialog: {
							after: {
								destroy() {
									instance.load();
								}
							},
							destroyOnHide: true,
							modal: true
						},
						title: Liferay.Language.get('new-calendar-booking'),
						uri: CalendarUtil.fillURLParameters(
							editCalendarBookingURL,
							data
						)
					});
				},

				_onDeleteEvent(event) {
					var instance = this;

					var schedulerEvent = event.schedulerEvent;

					var remoteServices = instance.get('remoteServices');

					var success = function() {
						instance.load();
						instance.get('eventRecorder').hidePopover();
					};

					if (schedulerEvent.isRecurring()) {
						RecurrenceUtil.openConfirmationPanel(
							'delete',
							() => {
								remoteServices.deleteEventInstance(
									schedulerEvent,
									false,
									success
								);
							},
							() => {
								remoteServices.deleteEventInstance(
									schedulerEvent,
									true,
									success
								);
							},
							() => {
								remoteServices.deleteEvent(
									schedulerEvent,
									success
								);
							}
						);
					} else if (schedulerEvent.isMasterBooking()) {
						var confirmationMessage;

						if (schedulerEvent.get('hasChildCalendarBookings')) {
							confirmationMessage = Liferay.Language.get(
								'deleting-this-event-will-cancel-the-meeting-with-your-guests-would-you-like-to-delete'
							);
						} else {
							confirmationMessage = Liferay.Language.get(
								'would-you-like-to-delete-this-event'
							);
						}

						if (confirm(confirmationMessage)) {
							remoteServices.deleteEvent(schedulerEvent, success);
						}
					}

					event.preventDefault();
				},

				_onLoadSchedulerEvents(event) {
					var instance = this;

					instance.plotCalendarBookings(event.parsed);
				},

				_onSaveEvent(event) {
					var instance = this;

					var remoteServices = instance.get('remoteServices');

					remoteServices.updateEvent(
						event.newSchedulerEvent,
						false,
						false,
						() => {
							instance.load();
							instance.get('eventRecorder').hidePopover();
						}
					);
				},

				_queueableQuestionResolver(data) {
					var instance = this;

					var answers = data.answers;
					var newRecurrence = data.newRecurrence;
					var schedulerEvent = data.schedulerEvent;

					var showNextQuestion = A.bind(instance.load, instance);

					if (
						newRecurrence &&
						(!answers.updateInstance || answers.allFollowing)
					) {
						schedulerEvent.set(
							'recurrence',
							instance.encodeRecurrence(newRecurrence)
						);
					}

					if (answers.cancel) {
						A.soon(showNextQuestion);
					} else {
						var remoteServices = instance.get('remoteServices');

						remoteServices.updateEvent(
							schedulerEvent,
							!!answers.updateInstance,
							!!answers.allFollowing,
							showNextQuestion
						);
					}
				},

				_updateCurrentTime() {
					var instance = this;

					var currentTimeFn = instance.get('currentTimeFn');

					currentTimeFn(time => {
						instance.set('currentTime', time);
					});
				},

				_updatePastEvents(event) {
					var instance = this;

					var currentTime = event.newVal;

					var pastSchedulerEvents = instance.getEvents(
						schedulerEvent => {
							var endDate = schedulerEvent.get('endDate');

							return endDate.getTime() <= currentTime;
						},
						false
					);

					A.each(pastSchedulerEvents, schedulerEvent => {
						return schedulerEvent._uiSetPast(true);
					});
				},

				_updateSchedulerEvent(schedulerEvent, changedAttributes) {
					var instance = this;

					var calendarContainer = instance.get('calendarContainer');

					var calendar = calendarContainer.getCalendar(
						schedulerEvent.get('calendarId')
					);

					Liferay.CalendarMessageUtil.promptSchedulerEventUpdate({
						calendarName: calendar.get('name'),
						duration: instance._getCalendarBookingDuration(
							schedulerEvent
						),
						hasChild: schedulerEvent.get(
							'hasChildCalendarBookings'
						),
						masterBooking: schedulerEvent.isMasterBooking(),
						newRecurrence: instance._getNewRecurrence(
							schedulerEvent,
							changedAttributes
						),
						offset: instance._getCalendarBookingOffset(
							schedulerEvent,
							changedAttributes
						),
						recurring: schedulerEvent.isRecurring(),
						resolver: A.bind(
							instance._queueableQuestionResolver,
							instance
						),
						schedulerEvent
					});
				},

				bindUI() {
					var instance = this;

					instance.after({
						'scheduler-base:dateChange': instance._afterDateChange,
						'scheduler-event:change':
							instance._afterSchedulerEventChange
					});

					instance.on({
						'*:load': instance._onLoadSchedulerEvents,
						'scheduler-event-recorder:delete':
							instance._onDeleteEvent,
						'scheduler-event-recorder:save': instance._onSaveEvent
					});

					instance._bindCurrentTimeInterval();

					instance.on(
						'currentTimeChange',
						instance._updatePastEvents
					);

					Scheduler.superclass.bindUI.apply(this, arguments);
				},

				calendarModel: Liferay.SchedulerCalendar,

				destructor() {
					var instance = this;

					clearInterval(instance._currentTimeInterval);

					instance.get('views').forEach(item => {
						item.destroy();
					});

					Scheduler.superclass.destructor.apply(instance, arguments);
				},

				eventModel: Liferay.SchedulerEvent,
				eventsModel: Liferay.SchedulerEvents,

				getEventsByCalendarBookingId(calendarBookingId) {
					var instance = this;

					return instance.getEvents(schedulerEvent => {
						return (
							schedulerEvent.get('calendarBookingId') ===
							calendarBookingId
						);
					});
				},

				load() {
					var instance = this;

					var events = instance._events;

					return events.load.apply(events, arguments);
				},

				plotCalendarBookings(calendarBookings) {
					var instance = this;

					var calendarEvents = {};
					var events = [];

					calendarBookings.forEach(item => {
						var calendarId = item.calendarId;

						if (!calendarEvents[calendarId]) {
							calendarEvents[calendarId] = [];
						}

						var schedulerEvent = CalendarUtil.createSchedulerEvent(
							item
						);

						schedulerEvent.set('scheduler', instance, {
							silent: true
						});

						events.push(schedulerEvent);
						calendarEvents[calendarId].push(schedulerEvent);
					});

					instance.resetEvents(events);

					var calendarContainer = instance.get('calendarContainer');

					A.each(
						calendarContainer.get('availableCalendars'),
						(item, index) => {
							item.reset(calendarEvents[index], {
								skipSyncUI: true
							});
						}
					);

					if (instance.get('rendered')) {
						instance.syncEventsUI();
					}
				},

				queue: null,

				renderUI() {
					var instance = this;

					Scheduler.superclass.renderUI.apply(this, arguments);

					instance.navDateNode.replaceClass(
						'hidden-xs',
						'd-none d-sm-block'
					);
					instance.viewDateNode.replaceClass(
						'visible-xs',
						'd-block d-sm-none'
					);
					instance.viewsNode
						.all('button')
						.replaceClass('hidden-xs', 'd-none d-sm-block');
					instance.viewsSelectNode.replaceClass(
						'visible-xs',
						'd-block d-sm-none'
					);

					var showAddEventBtn = instance.get('showAddEventBtn');

					if (showAddEventBtn) {
						instance[ICON_ADD_EVENT_NODE] = instance.get(
							ICON_ADD_EVENT_NODE
						);

						instance[CONTROLS_NODE].prepend(
							instance[ICON_ADD_EVENT_NODE]
						);

						instance[ICON_ADD_EVENT_NODE].on(
							'click',
							instance._onClickAddEvent,
							instance
						);
					}
				},

				sync() {
					var instance = this;

					var events = instance._events;

					return events.sync.apply(events, arguments);
				}
			}
		});

		Liferay.Scheduler = Scheduler;

		var SchedulerDayView = A.Component.create({
			ATTRS: {
				navigationDateFormatter: {
					validator: isFunction,
					value(date) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						return A.DataType.Date.format(date, {
							format: Liferay.Language.get('a-b-d-y'),
							locale: scheduler.get('locale')
						});
					}
				},

				syncCurrentTimeUI() {
					var instance = this;

					var scheduler = instance.get('scheduler');

					var currentTime = scheduler.get('currentTime');

					instance._moveCurrentTimeNode(currentTime);
				}
			},

			EXTENDS: A.SchedulerDayView,

			NAME: 'scheduler-day-view'
		});

		Liferay.SchedulerDayView = SchedulerDayView;

		Liferay.SchedulerWeekView = A.Component.create({
			ATTRS: {
				headerDateFormatter: {
					validator: isFunction,
					value(date) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						return A.DataType.Date.format(date, {
							format: Liferay.Language.get('a-d'),
							locale: scheduler.get('locale')
						});
					}
				},

				navigationDateFormatter: {
					validator: isFunction,
					value(date) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						var locale = scheduler.get('locale');

						var startDate = instance._firstDayOfWeek(date);

						var endDate = DateMath.add(
							startDate,
							DateMath.DAY,
							instance.get('days') - 1
						);

						var startDateFormat = Liferay.Language.get('b-d');

						var endDateFormat;

						if (DateMath.isMonthOverlapWeek(startDate)) {
							endDateFormat = Liferay.Language.get('b-d-y');
						} else {
							endDateFormat = Liferay.Language.get('d-y');
						}

						var startDateLabel = A.DataType.Date.format(startDate, {
							format: startDateFormat,
							locale
						});

						var endDateLabel = A.DataType.Date.format(endDate, {
							format: endDateFormat,
							locale
						});

						return [startDateLabel, '&mdash;', endDateLabel].join(
							' '
						);
					}
				}
			},

			EXTENDS: A.SchedulerWeekView,

			NAME: 'scheduler-week-view'
		});

		var SchedulerMonthView = A.Component.create({
			ATTRS: {
				navigationDateFormatter: {
					validator: isFunction,
					value(date) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						return A.DataType.Date.format(date, {
							format: Liferay.Language.get('b-y'),
							locale: scheduler.get('locale')
						});
					}
				}
			},

			EXTENDS: A.SchedulerMonthView,

			NAME: 'scheduler-month-view',

			prototype: {
				_syncCellDimensions() {
					var instance = this;

					var scheduler = instance.get('scheduler');

					var viewDate = scheduler.get('viewDate');

					var firstDayOfWeek = scheduler.get('firstDayOfWeek');

					var weeks = DateMath.getWeeksInMonth(
						viewDate,
						firstDayOfWeek
					);

					SchedulerMonthView.superclass._syncCellDimensions.apply(
						this,
						arguments
					);

					instance.gridCellHeight =
						instance.rowsContainerNode.get('offsetHeight') / weeks;
				},

				_uiSetDate(date) {
					var instance = this;

					var scheduler = instance.get('scheduler');

					var firstDayOfWeek = scheduler.get('firstDayOfWeek');

					var weeks = DateMath.getWeeksInMonth(date, firstDayOfWeek);

					A.each(instance.tableRows, (item, index) => {
						if (index >= weeks) {
							item.remove();
						} else if (index < weeks && !item.parentElement) {
							instance.tableRowContainer.appendChild(item);
						}
					});

					SchedulerMonthView.superclass._uiSetDate.apply(
						this,
						arguments
					);
				}
			}
		});

		Liferay.SchedulerMonthView = SchedulerMonthView;

		var SchedulerAgendaView = A.Component.create({
			ATTRS: {
				eventsDateFormatter: {
					validator: isFunction,
					value(startDate, endDate) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						var isoTime = scheduler
							.get('activeView')
							.get('isoTime');

						var startDateMask;

						var endDateMask;

						var startDateFormatter;

						var endDateFormatter;

						if (isoTime) {
							startDateMask = '%H:%M';

							endDateMask = '%H:%M';
						} else {
							startDateMask = '%l:%M';
							endDateMask = '%l:%M';

							if (startDate.getHours() >= 12) {
								startDateMask += 'pm';
							}

							if (endDate.getHours() >= 12) {
								endDateMask += 'pm';
							}
						}

						if (DateMath.isDayOverlap(startDate, endDate)) {
							startDateMask += ', ' + Liferay.Language.get('b-e');
							endDateMask += ', ' + Liferay.Language.get('b-e');
						}

						startDateFormatter = instance._getFormatter.call(
							instance,
							startDateMask
						);
						endDateFormatter = instance._getFormatter.call(
							instance,
							endDateMask
						);

						return [
							startDateFormatter.call(instance, startDate),
							'&mdash;',
							endDateFormatter.call(instance, endDate)
						].join(' ');
					}
				},

				headerDayDateFormatter: {
					validator: isFunction,
					value(date) {
						var instance = this;

						var todayDate = instance
							.get('scheduler')
							.get('todayDate');

						var mask;

						var formatter;

						if (!DateMath.isDayOverlap(date, todayDate)) {
							mask = Liferay.Language.get('today');
						} else {
							mask = Liferay.Language.get('a');
						}

						formatter = instance._getFormatter.call(instance, mask);

						return formatter.call(instance, date);
					}
				},

				headerExtraDateFormatter: {
					validator: isFunction,
					valueFn() {
						var instance = this;

						return instance._getFormatter(
							Liferay.Language.get('b-e')
						);
					}
				},

				infoDayDateFormatter: {
					validator: isFunction,
					valueFn() {
						var instance = this;

						return instance._getFormatter(
							Liferay.Language.get('e')
						);
					}
				},

				infoLabelBigDateFormatter: {
					validator: isFunction,
					valueFn() {
						var instance = this;

						return instance._getFormatter(
							Liferay.Language.get('a')
						);
					}
				},

				infoLabelSmallDateFormatter: {
					validator: isFunction,
					valueFn() {
						var instance = this;

						return instance._getFormatter(
							Liferay.Language.get('b-d-y')
						);
					}
				}
			},

			EXTENDS: A.SchedulerAgendaView,

			NAME: 'scheduler-view-agenda',

			prototype: {
				_getFormatter(mask) {
					return function(date) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						return A.DataType.Date.format(date, {
							format: mask,
							locale: scheduler.get('locale')
						});
					};
				},

				plotEvents() {
					var instance = this;

					var scheduler = instance.get('scheduler');

					SchedulerAgendaView.superclass.plotEvents.apply(
						instance,
						arguments
					);

					var headerContent = instance.get('headerContent');

					if (scheduler.get('showHeader')) {
						headerContent.show();
					} else {
						headerContent.hide();
					}
				}
			}
		});

		Liferay.SchedulerAgendaView = SchedulerAgendaView;
	},
	'',
	{
		requires: [
			'async-queue',
			'aui-datatype',
			'aui-scheduler',
			'dd-plugin',
			'liferay-calendar-message-util',
			'liferay-calendar-recurrence-converter',
			'liferay-calendar-recurrence-util',
			'liferay-calendar-util',
			'liferay-scheduler-event-recorder',
			'liferay-scheduler-models',
			'promise',
			'resize-plugin'
		]
	}
);
