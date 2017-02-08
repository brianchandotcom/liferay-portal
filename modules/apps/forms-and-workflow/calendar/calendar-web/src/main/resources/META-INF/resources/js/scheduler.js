AUI.add(
	'liferay-scheduler',
	function(A) {
		var AArray = A.Array;
		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;

		var RecurrenceUtil = Liferay.RecurrenceUtil;

		var isBoolean = Lang.isBoolean;
		var isFunction = Lang.isFunction;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var CONTROLS_NODE = 'controlsNode';

		var DAYS_OF_WEEK = ['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'];

		var ICON_ADD_EVENT_NODE = 'iconAddEventNode';

		var STR_BLANK = '';

		var TPL_ICON_ADD_EVENT_NODE = '<div class="btn-group">' +
				'<button class="btn btn-primary calendar-add-event-btn" type="button">' +
					Liferay.Language.get('add-calendar-booking') +
				'</button>' +
			'</div>';

		var WEEKLY = 'WEEKLY';

		var Time = Liferay.Time;

		A.mix(
			A.DataType.DateMath,
			{
				getWeeksInMonth: function(date) {
					var daysInMonth = DateMath.getDaysInMonth(date.getFullYear(), date.getMonth());
					var firstWeekDay = DateMath.getDate(date.getFullYear(), date.getMonth(), 1).getDay();

					var daysInFirstWeek = DateMath.WEEK_LENGTH - firstWeekDay;

					return Math.ceil((daysInMonth - daysInFirstWeek) / DateMath.WEEK_LENGTH) + 1;
				}
			}
		);

		var CalendarUtil = Liferay.CalendarUtil;

		var Scheduler = A.Component.create(
			{
				ATTRS: {
					calendarContainer: {
						validator: isObject,
						value: null
					},

					eventsPerPage: {
					},

					filterCalendarBookings: {
						validator: isFunction
					},

					iconAddEventNode: {
						valueFn: function() {
							return A.Node.create(TPL_ICON_ADD_EVENT_NODE);
						}
					},

					maxDaysDisplayed: {
					},

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
					calendarModel: Liferay.SchedulerCalendar,
					eventModel: Liferay.SchedulerEvent,
					eventsModel: Liferay.SchedulerEvents,
					queue: null,

					renderUI: function() {
						var instance = this;

						Scheduler.superclass.renderUI.apply(this, arguments);

						instance.navDateNode.replaceClass('hidden-xs', 'hidden');
						instance.viewDateNode.removeClass('visible-xs');

						var showAddEventBtn = instance.get('showAddEventBtn');

						if (showAddEventBtn) {
							instance[ICON_ADD_EVENT_NODE] = instance.get(ICON_ADD_EVENT_NODE);

							instance[CONTROLS_NODE].prepend(instance[ICON_ADD_EVENT_NODE]);

							instance[ICON_ADD_EVENT_NODE].on('click', instance._onClickAddEvent, instance);
						}
					},

					bindUI: function() {
						var instance = this;

						instance.after(
							{
								'scheduler-base:dateChange': instance._afterDateChange,
								'scheduler-event:change': instance._afterSchedulerEventChange
							}
						);

						instance.on(
							{
								'*:load': instance._onLoadSchedulerEvents,
								'scheduler-event-recorder:delete': instance._onDeleteEvent,
								'scheduler-event-recorder:save': instance._onSaveEvent
							}
						);

						Scheduler.superclass.bindUI.apply(this, arguments);
					},

					getEventsByCalendarBookingId: function(calendarBookingId) {
						var instance = this;

						return instance.getEvents(
							function(schedulerEvent) {
								return schedulerEvent.get('calendarBookingId') === calendarBookingId;
							}
						);
					},

					load: function() {
						var instance = this;

						var events = instance._events;

						return events.load.apply(events, arguments);
					},

					plotCalendarBookings: function(calendarBookings) {
						var instance = this;

						var calendarEvents = {};
						var events = [];

						calendarBookings.forEach(
							function(item, index) {
								var calendarId = item.calendarId;

								if (!calendarEvents[calendarId]) {
									calendarEvents[calendarId] = [];
								}

								var schedulerEvent = CalendarUtil.createSchedulerEvent(item);

								schedulerEvent.set(
									'scheduler',
									instance,
									{
										silent: true
									}
								);

								events.push(schedulerEvent);
								calendarEvents[calendarId].push(schedulerEvent);
							}
						);

						instance.resetEvents(events);

						var calendarContainer = instance.get('calendarContainer');

						A.each(
							calendarContainer.get('availableCalendars'),
							function(item, index) {
								item.reset(
									calendarEvents[index],
									{
										skipSyncUI: true
									}
								);
							}
						);

						if (instance.get('rendered')) {
							instance.syncEventsUI();
						}
					},

					sync: function() {
						var instance = this;

						var events = instance._events;

						return events.sync.apply(events, arguments);
					},

					_afterActiveViewChange: function(event) {
						var instance = this;

						Scheduler.superclass._afterActiveViewChange.apply(this, arguments);

						Liferay.Store('com.liferay.calendar.web_defaultView', event.newVal.get('name'));

						instance.load();
					},

					_afterAddEventModalLoad: function(event) {
						var instance = this;

						event.target.node.getDOMNode().contentWindow.focus();
					},

					_afterDateChange: function(event) {
						var instance = this;

						instance.load();
					},

					_afterSchedulerEventChange: function(event) {
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

							A.each(
								changedAttributes,
								function(item, index) {
									persist = persistentAttrMap.hasOwnProperty(index);
								}
							);

							if (persist) {
								var schedulerEvent = event.target;

								instance._updateSchedulerEvent(schedulerEvent, changedAttributes);
							}
						}
					},

					_createViewTriggerNode: function(view, tpl) {
						var instance = this;

						var node = Scheduler.superclass._createViewTriggerNode.apply(this, arguments);

						var schedulerViewText = '';

						var viewName = view.get('name');

						if (viewName == 'agenda') {
							schedulerViewText = Liferay.Language.get('agenda-view');
						}
						else if (viewName == 'day') {
							schedulerViewText = Liferay.Language.get('day-view');
						}
						else if (viewName == 'month') {
							schedulerViewText = Liferay.Language.get('month-view');
						}
						else if (viewName == 'week') {
							schedulerViewText = Liferay.Language.get('week-view');
						}

						if (node.get('nodeName') === 'OPTION') {
							node.text(schedulerViewText);
						}

						return node;
					},

					_getCalendarBookingDuration: function(schedulerEvent) {
						var instance = this;

						var duration = schedulerEvent.getSecondsDuration() * Time.SECOND;

						return duration;
					},

					_getCalendarBookingOffset: function(schedulerEvent, changedAttributes) {
						var instance = this;

						var offset = 0;

						if (changedAttributes.startDate) {
							offset = changedAttributes.startDate.newVal.getTime() - changedAttributes.startDate.prevVal.getTime();
						}

						return offset;
					},

					_getNewRecurrence: function(schedulerEvent, changedAttributes) {
						var instance = this;

						var recurrence = instance.parseRecurrence(schedulerEvent.get('recurrence'));

						if (recurrence && changedAttributes.startDate && changedAttributes.endDate) {
							var rrule = recurrence.rrule;

							var newDate = changedAttributes.startDate.newVal;

							var prevDate = changedAttributes.startDate.prevVal;

							if (DateMath.isDayOverlap(prevDate, newDate)) {
								if (rrule.freq === WEEKLY) {
									var index = rrule.byday.indexOf(DAYS_OF_WEEK[prevDate.getDay()]);

									AArray.remove(rrule.byday, index);

									rrule.byday.push(DAYS_OF_WEEK[newDate.getDay()]);
								}
								else if (rrule.byday) {
									var position = Math.ceil(newDate.getDate() / DateMath.WEEK_LENGTH);

									var futureDate = DateMath.add(newDate, DateMath.WEEK, 1);

									var lastDayOfWeek = futureDate.getMonth() !== newDate.getMonth();

									if ((position > 4) || (lastDayOfWeek && (rrule.byday.position === -1))) {
										position = -1;
									}

									rrule.byday.position = position;
									rrule.byday.dayOfWeek = DAYS_OF_WEEK[newDate.getDay()];

									if (rrule.bymonth) {
										rrule.bymonth = newDate.getMonth() + 1;
									}
								}
							}
						}

						return recurrence;
					},

					_onClickAddEvent: function(event) {
						var instance = this;

						var recorder = instance.get('eventRecorder');

						var activeViewName = instance.get('activeView').get('name');

						var calendarContainer = instance.get('calendarContainer');

						var defaultCalendar = calendarContainer.get('defaultCalendar');

						var calendarId = defaultCalendar.get('calendarId');

						var editCalendarBookingURL = decodeURIComponent(recorder.get('editCalendarBookingURL'));

						var startTimeDate = instance.get('date');

						var data = {
							activeView: activeViewName,
							calendarId: calendarId,
							startTimeDay: startTimeDate.getDate(),
							startTimeMonth: startTimeDate.getMonth(),
							startTimeYear: startTimeDate.getFullYear(),
							titleCurrentValue: ''
						};

						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function(event) {
											instance.load();
										}
									},
									destroyOnHide: true,
									modal: true
								},
								title: Liferay.Language.get('new-calendar-booking'),
								uri: Lang.sub(editCalendarBookingURL, data)
							},
							function(modal) {
								modal.iframe.on(
									'load',
									A.bind(instance._afterAddEventModalLoad, instance)
								);
							}
						);
					},

					_onDeleteEvent: function(event) {
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
								function() {
									remoteServices.deleteEventInstance(schedulerEvent, false, success);
								},
								function() {
									remoteServices.deleteEventInstance(schedulerEvent, true, success);
								},
								function() {
									remoteServices.deleteEvent(schedulerEvent, success);
								}
							);
						}
						else if (schedulerEvent.isMasterBooking()) {
							var confirmationMessage;

							if (schedulerEvent.get('hasChildCalendarBookings')) {
								confirmationMessage = Liferay.Language.get('deleting-this-event-will-cancel-the-meeting-with-your-guests-would-you-like-to-delete');
							}
							else {
								confirmationMessage = Liferay.Language.get('would-you-like-to-delete-this-event');
							}

							if (confirm(confirmationMessage)) {
								remoteServices.deleteEvent(schedulerEvent, success);
							}
						}

						event.preventDefault();
					},

					_onLoadSchedulerEvents: function(event) {
						var instance = this;

						instance.plotCalendarBookings(event.parsed);
					},

					_onSaveEvent: function(event) {
						var instance = this;

						var remoteServices = instance.get('remoteServices');

						remoteServices.updateEvent(
							event.newSchedulerEvent,
							false,
							false,
							function() {
								instance.load();
								instance.get('eventRecorder').hidePopover();
							}
						);
					},

					_queueableQuestionResolver: function(data) {
						var instance = this;

						var answers = data.answers;
						var newRecurrence = data.newRecurrence;
						var schedulerEvent = data.schedulerEvent;

						var showNextQuestion = A.bind(instance.load, instance);

						if (newRecurrence && (!answers.updateInstance || answers.allFollowing)) {
							schedulerEvent.set('recurrence', instance.encodeRecurrence(newRecurrence));
						}

						if (answers.cancel) {
							A.soon(showNextQuestion);
						}
						else {
							var remoteServices = instance.get('remoteServices');

							remoteServices.updateEvent(schedulerEvent, !!answers.updateInstance, !!answers.allFollowing, showNextQuestion);
						}
					},

					_updateSchedulerEvent: function(schedulerEvent, changedAttributes) {
						var instance = this;

						var calendarContainer = instance.get('calendarContainer');

						var calendar = calendarContainer.getCalendar(schedulerEvent.get('calendarId'));

						Liferay.CalendarMessageUtil.promptSchedulerEventUpdate(
							{
								calendarName: calendar.get('name'),
								duration: instance._getCalendarBookingDuration(schedulerEvent),
								hasChild: schedulerEvent.get('hasChildCalendarBookings'),
								masterBooking: schedulerEvent.isMasterBooking(),
								newRecurrence: instance._getNewRecurrence(schedulerEvent, changedAttributes),
								offset: instance._getCalendarBookingOffset(schedulerEvent, changedAttributes),
								recurring: schedulerEvent.isRecurring(),
								resolver: A.bind(instance._queueableQuestionResolver, instance),
								schedulerEvent: schedulerEvent
							}
						);
					}
				}
			}
		);

		Liferay.Scheduler = Scheduler;

		var SchedulerDayView = A.Component.create(
			{
				EXTENDS: A.SchedulerDayView,

				NAME: 'scheduler-day-view',

				ATTRS: {
					navigationDateFormatter: {
						value: function(date) {
							var options = { 
									weekday: 'long',
									year: 'numeric',
									month: 'long',
									day: 'numeric' 
							};

							var language = A.config.lang;

							return date.toLocaleString(language, options);
						},
						validator: isFunction
					}
				}
			}
		);

		Liferay.SchedulerDayView = SchedulerDayView;

		Liferay.SchedulerWeekView = A.SchedulerWeekView;

		var SchedulerMonthView = A.Component.create(
			{
				EXTENDS: A.SchedulerMonthView,

				NAME: 'scheduler-month-view',

				prototype: {
					_syncCellDimensions: function() {
						var instance = this;

						var scheduler = instance.get('scheduler');

						var viewDate = scheduler.get('viewDate');

						var weeks = DateMath.getWeeksInMonth(viewDate);

						SchedulerMonthView.superclass._syncCellDimensions.apply(this, arguments);

						instance.gridCellHeight = instance.rowsContainerNode.get('offsetHeight') / weeks;
					},

					_uiSetDate: function(date) {
						var instance = this;

						var weeks = DateMath.getWeeksInMonth(date);

						A.each(
							instance.tableRows,
							function(item, index) {
								if (index < weeks) {
									item.removeClass('hide');
								}
								else {
									item.addClass('hide');
								}
							}
						);

						SchedulerMonthView.superclass._uiSetDate.apply(this, arguments);
					}
				}
			}
		);

		Liferay.SchedulerMonthView = SchedulerMonthView;

		var SchedulerAgendaView = A.Component.create(
			{
				EXTENDS: A.SchedulerAgendaView,

				NAME: 'scheduler-view-agenda',

				prototype: {
					plotEvents: function() {
						var instance = this;

						var scheduler = instance.get('scheduler');

						SchedulerAgendaView.superclass.plotEvents.apply(instance, arguments);

						var headerContent = instance.get('headerContent');

						if (scheduler.get('showHeader')) {
							headerContent.show();
						}
						else {
							headerContent.hide();
						}
					}
				}
			}
		);

		Liferay.SchedulerAgendaView = SchedulerAgendaView;
	},
	'',
	{
		requires: ['async-queue', 'aui-datatype', 'aui-scheduler', 'dd-plugin', 'liferay-calendar-message-util', 'liferay-calendar-recurrence-converter', 'liferay-calendar-recurrence-util', 'liferay-calendar-util', 'liferay-node', 'liferay-scheduler-event-recorder', 'liferay-scheduler-models', 'liferay-store', 'promise', 'resize-plugin']
	}
);