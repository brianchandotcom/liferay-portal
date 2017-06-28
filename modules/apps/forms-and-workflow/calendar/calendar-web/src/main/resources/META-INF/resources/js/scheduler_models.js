AUI.add(
	'liferay-scheduler-models',
	function(A) {
		var AObject = A.Object;

		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;
		var LString = Lang.String;

		var CalendarWorkflow = Liferay.CalendarWorkflow;

		var isBoolean = Lang.isBoolean;
		var isFunction = Lang.isFunction;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var toInitialCap = A.cached(
			function(str) {
				return str.substring(0, 1).toUpperCase() + str.substring(1);
			}
		);

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var STR_BLANK = '';

		var CalendarUtil = Liferay.CalendarUtil;

		var SchedulerModelSync = function(config) {};

		SchedulerModelSync.prototype = {
			sync: function(action, options, callback) {
				var instance = this;

				var actionMethod = instance['_do' + toInitialCap(action)];

				if (isFunction(actionMethod)) {
					actionMethod.apply(instance, [options, callback]);
				}
			},

			_doRead: function() {
				var instance = this;

				var args = arguments;

				var callback = args[args.length - 1];

				if (isFunction(callback)) {
					callback();
				}
			}
		};

		Liferay.SchedulerModelSync = SchedulerModelSync;

		var SchedulerEvent = A.Component.create(
			{
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
						getter: function(val) {
							var content = val;

							if (val) {
								content = LString.escapeHTML(val);
							}

							return content;
						},
						setter: function(val) {
							var content = val;

							if (val) {
								content = LString.unescapeHTML(val);
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
						getter: function() {
							var instance = this;

							return instance.get('firstReminder') > 0 || instance.get('secondReminder') > 0;
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

				PROPAGATE_ATTRS: A.SchedulerEvent.PROPAGATE_ATTRS.concat(['calendarBookingId', 'calendarId', 'calendarResourceId', 'parentCalendarBookingId', 'recurrence', 'recurringCalendarBookingId', 'status']),

				prototype: {
					eventModel: Liferay.SchedulerEvent,

					initializer: function() {
						var instance = this;

						instance._uiSetLoading(instance.get('loading'));
						instance._uiSetStartDate(instance.get('startDate'));
						instance._uiSetStatus(instance.get('status'));

						instance.on('loadingChange', instance._onLoadingChange);
						instance.on('startDateChange', instance._onStartDateChange);
						instance.on('statusChange', instance._onStatusChange);
					},

					syncUI: function() {
						var instance = this;

						Liferay.SchedulerEvent.superclass.syncUI.apply(instance, arguments);

						instance._uiSetStatus(instance.get('status'));
					},

					isMasterBooking: function() {
						var instance = this;

						return instance.get('parentCalendarBookingId') === instance.get('calendarBookingId');
					},

					isRecurring: function() {
						var instance = this;

						return (instance.get('recurrence') !== STR_BLANK) || (instance.get('calendarBookingId') != instance.get('recurringCalendarBookingId'));
					},

					syncNodeColorUI: function() {
						var instance = this;

						Liferay.SchedulerEvent.superclass.syncNodeColorUI.apply(instance, arguments);

						var node = instance.get('node');
						var scheduler = instance.get('scheduler');

						if (scheduler && !instance.get('editingEvent')) {
							var activeViewName = scheduler.get('activeView').get('name');

							if (activeViewName === 'month' && !instance.get('allDay')) {
								node.setStyles(
									{
										backgroundColor: instance.get('color'),
										border: 'none',
										color: '#111',
										padding: '0 2px'
									}
								);
							}
						}
					},

					syncWithServer: function() {
						var instance = this;

						var calendarBookingId = instance.get('calendarBookingId');

						var scheduler = instance.get('scheduler');

						var schedulerEvents = scheduler.getEventsByCalendarBookingId(calendarBookingId);

						var remoteServices = scheduler.get('remoteServices');

						remoteServices.getEvent(
							calendarBookingId,
							A.bind(CalendarUtil.updateSchedulerEvents, CalendarUtil, schedulerEvents)
						);
					},

					_isPastEvent: function() {
						var instance = this;

						var endDate = instance.get('endDate');

						var result;

						var scheduler = instance.get('scheduler');

						if (scheduler) {
							var currentTime = scheduler.get('currentTime');

							result = endDate.getTime() < currentTime.getTime();
						}
						else {
							result = false;
						}

						return result;
					},

					_onLoadingChange: function(event) {
						var instance = this;

						instance._uiSetLoading(event.newVal);
					},

					_onStartDateChange: function(event) {
						var instance = this;

						instance._uiSetStartDate(event.newVal);
					},

					_onStatusChange: function(event) {
						var instance = this;

						instance._uiSetStatus(event.newVal);
					},

					_uiSetEndDate: function(val) {
						var instance = this;

						Liferay.SchedulerEvent.superclass._uiSetEndDate.apply(instance, arguments);

						var node = instance.get('node');

						node.attr('data-endDate', instance._formatDate(val, '%m/%d/%Y'));
						node.attr('data-endTime', instance._formatDate(val, '%I:%M %p'));
					},

					_uiSetLoading: function(val) {
						var instance = this;

						instance.get('node').toggleClass('calendar-portlet-event-loading', val);
					},

					_uiSetStartDate: function(val) {
						var instance = this;

						var node = instance.get('node');

						node.attr('data-startDate', instance._formatDate(val, '%m/%d/%Y'));
						node.attr('data-startTime', instance._formatDate(val, '%I:%M %p'));
					},

					_uiSetStatus: function(val) {
						var instance = this;

						var node = instance.get('node');

						node.toggleClass('calendar-portlet-event-approved', val === CalendarWorkflow.STATUS_APPROVED);
						node.toggleClass('calendar-portlet-event-denied', val === CalendarWorkflow.STATUS_DENIED);
						node.toggleClass('calendar-portlet-event-draft', val === CalendarWorkflow.STATUS_DRAFT);
						node.toggleClass('calendar-portlet-event-maybe', val === CalendarWorkflow.STATUS_MAYBE);
						node.toggleClass('calendar-portlet-event-pending', val === CalendarWorkflow.STATUS_PENDING);
					}
				}
			}
		);

		Liferay.SchedulerEvent = SchedulerEvent;

		var Calendar = A.Component.create(
			{
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
						setter: function(val) {
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
					getDisplayName: function() {
						var instance = this;

						var name = instance.get('name');

						var showCalendarResourceName = instance.get('showCalendarResourceName');

						if (showCalendarResourceName) {
							var calendarResourceName = instance.get('calendarResourceName');

							name = CalendarUtil.getCalendarName(name, calendarResourceName);
						}

						return name;
					},

					_afterColorChange: function(event) {
						var instance = this;

						Calendar.superclass._afterColorChange.apply(instance, arguments);

						var calendarId = instance.get('calendarId');

						var color = event.newVal;

						if (instance.get('permissions.UPDATE')) {
							var scheduler = instance.get('scheduler');

							var remoteServices = scheduler.get('remoteServices');

							remoteServices.updateCalendarColor(calendarId, color);
						}
						else {
							Liferay.Store('com.liferay.calendar.web_calendar' + calendarId + 'Color', color);
						}
					},

					_afterVisibleChange: function(event) {
						var instance = this;

						Calendar.superclass._afterVisibleChange.apply(instance, arguments);

						var scheduler = instance.get('scheduler');

						scheduler.syncEventsUI();
					}
				}
			}
		);

		Liferay.SchedulerCalendar = Calendar;

		Liferay.SchedulerEvents = A.Base.create(
			'scheduler-events',
			A.SchedulerEvents,
			[Liferay.SchedulerModelSync],
			{
				getEventsPerPage: function(activeView, eventsPerPage) {
					var instance = this;

					var viewName = activeView.get('name');

					if (viewName !== 'agenda') {
						eventsPerPage = -1;
					}

					return eventsPerPage;
				},

				getLoadEndDate: function(activeView, maxDaysDisplayed) {
					var instance = this;

					var date = activeView.getNextDate();

					var viewName = activeView.get('name');

					if (viewName === 'agenda') {
						date = DateMath.add(date, DateMath.DAY, maxDaysDisplayed);

						date = DateMath.subtract(date, DateMath.MINUTES, 1);
					}
					else if (viewName === 'month') {
						date = DateMath.add(date, DateMath.WEEK, 1);
					}

					return date;
				},

				getLoadStartDate: function(activeView) {
					var instance = this;

					var scheduler = activeView.get('scheduler');
					var viewName = activeView.get('name');

					var date = scheduler.get('viewDate');

					if (viewName === 'month') {
						date = DateMath.subtract(date, DateMath.WEEK, 1);
					}

					return date;
				},

				_doRead: function(options, callback) {
					var instance = this;

					var scheduler = instance.get('scheduler');

					var activeView = scheduler.get('activeView');
					var eventsPerPage = scheduler.get('eventsPerPage');
					var filterCalendarBookings = scheduler.get('filterCalendarBookings');
					var maxDaysDisplayed = scheduler.get('maxDaysDisplayed');

					var calendarContainer = scheduler.get('calendarContainer');

					var calendarIds = AObject.keys(calendarContainer.get('availableCalendars'));

					var remoteServices = scheduler.get('remoteServices');

					remoteServices.getEvents(
						calendarIds,
						instance.getEventsPerPage(activeView, eventsPerPage),
						instance.getLoadStartDate(activeView),
						instance.getLoadEndDate(activeView, maxDaysDisplayed),
						[CalendarWorkflow.STATUS_APPROVED, CalendarWorkflow.STATUS_DENIED, CalendarWorkflow.STATUS_DRAFT, CalendarWorkflow.STATUS_MAYBE, CalendarWorkflow.STATUS_PENDING],
						function(calendarBookings) {
							if (filterCalendarBookings) {
								calendarBookings = calendarBookings.filter(filterCalendarBookings);
							}

							callback(null, calendarBookings);
						}
					);
				}
			},
			{}
		);
	},
	'',
	{
		requires: ['aui-datatype', 'dd-plugin', 'liferay-calendar-util', 'liferay-store']
	}
);