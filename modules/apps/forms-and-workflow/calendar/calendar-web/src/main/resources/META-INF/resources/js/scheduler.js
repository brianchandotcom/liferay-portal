AUI.add(
	'liferay-scheduler',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;

		var CalendarWorkflow = Liferay.CalendarWorkflow;
		var RecurrenceUtil = Liferay.RecurrenceUtil;

		var isBoolean = Lang.isBoolean;
		var isFunction = Lang.isFunction;
		var isValue = Lang.isValue;

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var CONTROLS_NODE = 'controlsNode';

		var DAYS_OF_WEEK = ['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'];

		var ICON_ADD_EVENT_NODE = 'iconAddEventNode';

		var STR_BLANK = '';

		var STR_COMMA_SPACE = ', ';

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
					currentTimeFn: {
						value: A.bind(CalendarUtil.getCurrentTime, CalendarUtil)
					},

					filterCalendarBookings: {
						validator: isFunction
					},

					iconAddEventNode: {
						valueFn: function() {
							return A.Node.create(TPL_ICON_ADD_EVENT_NODE);
						}
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

						A.each(
							Liferay.CalendarUtil.availableCalendars,
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

						CalendarUtil.message(STR_BLANK);
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

						var defaultUserCalendar = CalendarUtil.getDefaultUserCalendar();

						var calendarId = defaultUserCalendar.get('calendarId');

						var editCalendarBookingURL = decodeURIComponent(recorder.get('editCalendarBookingURL'));

						var data = {
							activeView: activeViewName,
							calendarId: calendarId,
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
									destroyOnHide: false,
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

						var success = function() {
							instance.load();
							instance.get('eventRecorder').hidePopover();
						};

						if (schedulerEvent.isRecurring()) {
							RecurrenceUtil.openConfirmationPanel(
								'delete',
								function() {
									CalendarUtil.deleteEventInstance(schedulerEvent, false, success);
								},
								function() {
									CalendarUtil.deleteEventInstance(schedulerEvent, true, success);
								},
								function() {
									CalendarUtil.deleteEvent(schedulerEvent, success);
								}
							);
						}
						else if (schedulerEvent.isMasterBooking() && confirm(Liferay.Language.get('deleting-this-event-will-cancel-the-meeting-with-your-guests-would-you-like-to-delete'))) {
							CalendarUtil.deleteEvent(schedulerEvent, success);
						}

						event.preventDefault();
					},

					_onLoadSchedulerEvents: function(event) {
						var instance = this;

						instance.plotCalendarBookings(event.parsed);
					},

					_onSaveEvent: function(event) {
						var instance = this;

						CalendarUtil.updateEvent(
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
							CalendarUtil.updateEvent(schedulerEvent, !!answers.updateInstance, !!answers.allFollowing, showNextQuestion);
						}
					},

					_updateSchedulerEvent: function(schedulerEvent, changedAttributes) {
						var instance = this;

						var calendar = Liferay.CalendarUtil.availableCalendars[schedulerEvent.get('calendarId')];

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

		Liferay.SchedulerDayView = A.SchedulerDayView;

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

		Liferay.SchedulerAgendaView = A.SchedulerAgendaView;

		var SchedulerEventRecorder = A.Component.create(
			{
				ATTRS: {
					calendarId: {
						setter: toInt,
						value: 0
					},

					editCalendarBookingURL: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					permissionsCalendarBookingURL: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					portletNamespace: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					},

					status: {
						setter: toInt,
						value: CalendarWorkflow.STATUS_DRAFT
					},

					toolbar: {
						value: {
							children: []
						}
					},

					viewCalendarBookingURL: {
						setter: String,
						validator: isValue,
						value: STR_BLANK
					}
				},

				EXTENDS: A.SchedulerEventRecorder,

				NAME: 'scheduler-event-recorder',

				prototype: {
					initializer: function() {
						var instance = this;

						var popoverBB = instance.popover.get('boundingBox');

						popoverBB.delegate('click', instance._handleEventAnswer, '.calendar-event-answer', instance);
					},

					getTemplateData: function() {
						var instance = this;

						var editing = true;

						var schedulerEvent = instance.get('event');

						if (!schedulerEvent) {
							editing = false;

							schedulerEvent = instance;
						}

						var availableCalendars = CalendarUtil.availableCalendars;

						var calendar = availableCalendars[schedulerEvent.get('calendarId')];

						var permissions = calendar.get('permissions');

						var templateData = SchedulerEventRecorder.superclass.getTemplateData.apply(this, arguments);

						return A.merge(
							templateData,
							{
								acceptLinkEnabled: instance._hasWorkflowStatusPermission(schedulerEvent, CalendarWorkflow.STATUS_APPROVED),
								allDay: schedulerEvent.get('allDay'),
								availableCalendars: availableCalendars,
								calendar: calendar,
								calendarIds: AObject.keys(availableCalendars),
								declineLinkEnabled: instance._hasWorkflowStatusPermission(schedulerEvent, CalendarWorkflow.STATUS_DENIED),
								editing: editing,
								endTime: templateData.endDate,
								hasWorkflowInstanceLink: schedulerEvent.get('hasWorkflowInstanceLink'),
								instanceIndex: schedulerEvent.get('instanceIndex'),
								maybeLinkEnabled: instance._hasWorkflowStatusPermission(schedulerEvent, CalendarWorkflow.STATUS_MAYBE),
								permissions: permissions,
								startTime: templateData.startDate,
								status: schedulerEvent.get('status'),
								workflowStatus: CalendarWorkflow
							}
						);
					},

					getUpdatedSchedulerEvent: function(optAttrMap) {
						var instance = this;

						var	attrMap = {
							color: instance.get('color')
						};

						var event = instance.get('event');

						if (event) {
							var calendar = CalendarUtil.availableCalendars[event.get('calendarId')];

							if (calendar) {
								attrMap.color = calendar.get('color');
							}
						}

						return SchedulerEventRecorder.superclass.getUpdatedSchedulerEvent.call(instance, A.merge(attrMap, optAttrMap));
					},

					isMasterBooking: Lang.emptyFnFalse,

					populateForm: function() {
						var instance = this;

						var bodyTemplate = instance.get('bodyTemplate');

						var headerTemplate = instance.get('headerTemplate');

						var templateData = instance.getTemplateData();

						if (A.instanceOf(bodyTemplate, A.Template) && A.instanceOf(headerTemplate, A.Template)) {
							instance.popover.setStdModContent('body', bodyTemplate.parse(templateData));
							instance.popover.setStdModContent('header', headerTemplate.parse(templateData));

							instance.popover.addToolbar(instance._getFooterToolbar(), 'footer');
						}
						else {
							SchedulerEventRecorder.superclass.populateForm.apply(instance, arguments);
						}

						instance.popover.addToolbar(
							[
								{
									cssClass: 'close',
									label: '\u00D7',
									on: {
										click: A.bind(instance._handleCancelEvent, instance)
									},
									render: true
								}
							],
							'body'
						);

						if (instance.popover.headerNode) {
							instance.popover.headerNode.toggleClass('hide', !templateData.permissions.VIEW_BOOKING_DETAILS);
						}

						instance._showResources();
					},

					_getFooterToolbar: function() {
						var instance = this;

						var schedulerEvent = instance.get('event');

						var schedulerEventCreated = false;

						if (schedulerEvent) {
							schedulerEventCreated = true;
						}
						else {
							schedulerEvent = instance;
						}

						var children = [];
						var editGroup = [];
						var respondGroup = [];

						var calendar = CalendarUtil.availableCalendars[schedulerEvent.get('calendarId')];
						var status = schedulerEvent.get('status');

						if (calendar) {
							var permissions = calendar.get('permissions');

							if (instance._hasSaveButton(permissions, calendar, status)) {
								editGroup.push(
									{
										icon: 'icon-hdd',
										id: 'saveBtn',
										label: Liferay.Language.get('save'),
										on: {
											click: A.bind(instance._handleSaveEvent, instance)
										},
										primary: true
									}
								);
							}

							if (instance._hasEditButton(permissions, calendar, status)) {
								editGroup.push(
									{
										icon: 'icon-edit',
										id: 'editBtn',
										label: Liferay.Language.get('edit'),
										on: {
											click: A.bind(instance._handleEditEvent, instance)
										}
									}
								);
							}

							if (schedulerEventCreated === true && permissions.VIEW_BOOKING_DETAILS) {
								editGroup.push(
									{
										icon: 'icon-eye-open',
										id: 'viewBtn',
										label: Liferay.Language.get('view-details'),
										on: {
											click: A.bind(instance._handleViewEvent, instance)
										}
									}
								);
							}

							if (schedulerEvent.isMasterBooking() && instance._hasDeleteButton(permissions, calendar, status)) {
								editGroup.push(
									{
										icon: 'icon-trash',
										id: 'deleteBtn',
										label: Liferay.Language.get('delete'),
										on: {
											click: A.bind(instance._handleDeleteEvent, instance)
										}
									}
								);
							}

							if (editGroup.length) {
								children.push(editGroup);
							}

							if (respondGroup.length) {
								children.push(respondGroup);
							}
						}

						return children;
					},

					_handleEditEvent: function(event) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						var activeViewName = scheduler.get('activeView').get('name');

						var date = scheduler.get('date');

						var schedulerEvent = instance.get('event');

						var editCalendarBookingURL = decodeURIComponent(instance.get('editCalendarBookingURL'));

						var data = instance.serializeForm();

						data.activeView = activeViewName;

						data.date = date.getTime();

						var endTime = new Date(data.endTime);

						data.endTimeDay = endTime.getDate();
						data.endTimeHour = endTime.getHours();
						data.endTimeMinute = endTime.getMinutes();
						data.endTimeMonth = endTime.getMonth();
						data.endTimeYear = endTime.getFullYear();

						var startTime = new Date(data.startTime);

						data.startTimeDay = startTime.getDate();
						data.startTimeHour = startTime.getHours();
						data.startTimeMinute = startTime.getMinutes();
						data.startTimeMonth = startTime.getMonth();
						data.startTimeYear = startTime.getFullYear();

						data.titleCurrentValue = encodeURIComponent(data.content);

						if (schedulerEvent) {
							data.allDay = schedulerEvent.get('allDay');
							data.calendarBookingId = schedulerEvent.get('calendarBookingId');
						}

						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function(event) {
											scheduler.load();
										}
									},
									destroyOnHide: true,
									modal: true
								},
								refreshWindow: window,
								title: Liferay.Language.get('edit-calendar-booking'),
								uri: Lang.sub(editCalendarBookingURL, data)
							}
						);

						instance.hidePopover();
					},

					_handleEventAnswer: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var schedulerEvent = instance.get('event');

						var linkEnabled = A.DataType.Boolean.parse(currentTarget.hasClass('calendar-event-answer-true'));

						var statusData = toInt(currentTarget.getData('status'));

						if (schedulerEvent && linkEnabled) {
							CalendarUtil.invokeTransition(schedulerEvent, statusData);
						}
					},

					_handleViewEvent: function(event) {
						var instance = this;

						var viewCalendarBookingURL = decodeURIComponent(instance.get('viewCalendarBookingURL'));

						var data = instance.serializeForm();

						var schedulerEvent = instance.get('event');

						data.calendarBookingId = schedulerEvent.get('calendarBookingId');

						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function(event) {
											schedulerEvent.syncWithServer();
										}
									},
									destroyOnHide: true,
									modal: true
								},
								refreshWindow: window,
								title: Liferay.Language.get('view-calendar-booking-details'),
								uri: Lang.sub(viewCalendarBookingURL, data)
							}
						);

						event.domEvent.preventDefault();
					},

					_hasDeleteButton: function(permissions, calendar, status) {
						return permissions.MANAGE_BOOKINGS && calendar;
					},

					_hasEditButton: function(permissions, calendar, status) {
						return permissions.MANAGE_BOOKINGS;
					},

					_hasSaveButton: function(permissions, calendar, status) {
						return permissions.MANAGE_BOOKINGS;
					},

					_hasWorkflowStatusPermission: function(schedulerEvent, newStatus) {
						var instance = this;

						var hasPermission = false;

						if (schedulerEvent) {
							var calendarId = schedulerEvent.get('calendarId');

							var calendar = CalendarUtil.availableCalendars[calendarId];

							var permissions = calendar.get('permissions');

							var status = schedulerEvent.get('status');

							hasPermission = permissions.MANAGE_BOOKINGS && status !== newStatus && status !== CalendarWorkflow.STATUS_DRAFT;
						}

						return hasPermission;
					},

					_renderPopOver: function() {
						var instance = this;

						var popoverBB = instance.popover.get('boundingBox');

						SchedulerEventRecorder.superclass._renderPopOver.apply(this, arguments);

						popoverBB.delegate(
							['change', 'keypress'],
							function(event) {
								var schedulerEvent = instance.get('event') || instance;

								var calendarId = toInt(event.currentTarget.val());

								var selectedCalendar = CalendarUtil.availableCalendars[calendarId];

								if (selectedCalendar) {
									schedulerEvent.set(
										'color',
										selectedCalendar.get('color'),
										{
											silent: true
										}
									);
								}
							},
							'#' + instance.get('portletNamespace') + 'eventRecorderCalendar'
						);
					},

					_showResources: function() {
						var instance = this;

						var schedulerEvent = instance.get('event');

						var popoverBB = instance.popover.get('boundingBox');

						popoverBB.toggleClass('calendar-portlet-event-recorder-editing', !!schedulerEvent);

						var defaultUserCalendar = CalendarUtil.getDefaultUserCalendar();

						var calendarId = defaultUserCalendar.get('calendarId');
						var color = defaultUserCalendar.get('color');

						var eventInstance = instance;

						if (schedulerEvent) {
							calendarId = schedulerEvent.get('calendarId');

							var calendar = CalendarUtil.availableCalendars[calendarId];

							if (calendar) {
								color = calendar.get('color');

								eventInstance = schedulerEvent;
							}
						}

						eventInstance.set(
							'color',
							color,
							{
								silent: true
							}
						);

						var portletNamespace = instance.get('portletNamespace');

						var eventRecorderCalendar = A.one('#' + portletNamespace + 'eventRecorderCalendar');

						if (eventRecorderCalendar) {
							eventRecorderCalendar.val(calendarId.toString());
						}

						instance._syncInvitees();
					},

					_syncInvitees: function() {
						var instance = this;

						var schedulerEvent = instance.get('event');

						if (schedulerEvent) {
							var calendar = CalendarUtil.availableCalendars[schedulerEvent.get('calendarId')];

							if (calendar) {
								var permissions = calendar.get('permissions');

								if (permissions.VIEW_BOOKING_DETAILS) {
									var parentCalendarBookingId = schedulerEvent.get('parentCalendarBookingId');
									var portletNamespace = instance.get('portletNamespace');

									CalendarUtil.getCalendarBookingInvitees(
										parentCalendarBookingId,
										function(data) {
											var results = AArray.partition(
													data,
													function(item) {
														return toInt(item.classNameId) === CalendarUtil.USER_CLASS_NAME_ID;
													}
											);

											instance._syncInviteesContent('#' + portletNamespace + 'eventRecorderUsers', results.matches);
											instance._syncInviteesContent('#' + portletNamespace + 'eventRecorderResources', results.rejects);
										}
									);
								}
							}
						}
					},

					_syncInviteesContent: function(contentNode, calendarResources) {
						var instance = this;

						var values = calendarResources.map(
							function(item) {
								return item.name;
							}
						);

						contentNode = A.one(contentNode);

						var messageNode = contentNode.one('.calendar-portlet-invitees');

						var messageHTML = '&mdash;';

						if (values.length > 0) {
							contentNode.show();

							messageHTML = values.join(STR_COMMA_SPACE);
						}

						messageNode.html(messageHTML);
					}
				}
			}
		);

		Liferay.SchedulerEventRecorder = SchedulerEventRecorder;
	},
	'',
	{
		requires: ['async-queue', 'aui-datatype', 'aui-scheduler', 'dd-plugin', 'liferay-calendar-message-util', 'liferay-calendar-recurrence-converter', 'liferay-calendar-recurrence-util', 'liferay-calendar-util', 'liferay-node', 'liferay-scheduler-models', 'liferay-store', 'promise', 'resize-plugin']
	}
);