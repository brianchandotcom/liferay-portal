AUI.add(
	'liferay-calendar-util',
	function(A) {
		var AObject = A.Object;
		var DateMath = A.DataType.DateMath;
		var Lang = A.Lang;
		var LString = Lang.String;

		var Workflow = Liferay.Workflow;

		var isDate = Lang.isDate;

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var STR_BLANK = '';

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var USER_ID = toInt(themeDisplay.getUserId());

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
			INVOKER_URL: themeDisplay.getPathContext() + '/api/jsonws/invoke',
			NOTIFICATION_DEFAULT_TYPE: 'email',
			PORTLET_NAMESPACE: STR_BLANK,
			USER_TIME_ZONE: 'UTC',

			availableCalendars: {},
			visibleCalendars: {},

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

			createCalendarsAutoComplete: function(resourceURL, input, afterSelectFn) {
				var instance = this;

				input.plug(
					A.Plugin.AutoComplete,
					{
						activateFirstItem: true,
						after: {
							select: afterSelectFn
						},
						maxResults: 20,
						requestTemplate: '&' + instance.PORTLET_NAMESPACE + 'keywords={query}',
						resultFilters: function(query, results) {
							return results.filter(
								function(item, index) {
									return !instance.availableCalendars[item.raw.calendarId];
								}
							);
						},
						resultFormatter: function(query, results) {
							return results.map(
								function(result) {
									var calendar = result.raw;
									var calendarResourceName = calendar.calendarResourceName;
									var name = calendar.name;

									if (name !== calendarResourceName) {
										name = [calendarResourceName, STR_DASH, name].join(STR_SPACE);
									}

									return A.Highlight.words(name, query);
								}
							);
						},
						resultHighlighter: 'wordMatch',
						resultTextLocator: 'calendarResourceName',
						source: resourceURL,
						width: 'auto'
					}
				);

				input.ac.get('boundingBox').setStyle('min-width', input.outerWidth());
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
						secondReminder: calendarBooking.secondReminder,
						secondReminderType: calendarBooking.secondReminderType,
						startDate: startDate.getTime(),
						status: calendarBooking.status
					}
				);

				return schedulerEvent;
			},

			deleteCalendar: function(calendarId, callback) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar.calendar/delete-calendar': {
							calendarId: calendarId
						}
					},
					{
						success: function() {
							callback(this.get('responseData'));
						}
					}
				);
			},

			deleteEvent: function(schedulerEvent, success) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar.calendarbooking/move-calendar-booking-to-trash': {
							calendarBookingId: schedulerEvent.get('calendarBookingId')
						}
					},
					{
						success: function(data) {
							if (success) {
								success.call(instance, data);
							}
						}
					}
				);
			},

			deleteEventInstance: function(schedulerEvent, allFollowing, success) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar.calendarbooking/delete-calendar-booking-instance': {
							allFollowing: allFollowing,
							calendarBookingId: schedulerEvent.get('calendarBookingId'),
							instanceIndex: schedulerEvent.get('instanceIndex')
						}
					},
					{
						success: function(data) {
							if (success) {
								success.call(instance, data);
							}
						}
					}
				);
			},

			destroyEvent: function(schedulerEvent) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				scheduler.removeEvents(schedulerEvent);

				scheduler.syncEventsUI();
			},

			getCalendar: function(calendarId, callback) {
				var instance = this;

				instance.invokeResourceURL(
					{
						callback: callback,
						queryParameters: {
							calendarId: calendarId
						},
						resourceId: 'calendar'
					}
				);
			},

			getCalendarBookingInvitees: function(calendarBookingId, callback) {
				var instance = this;

				instance.invokeResourceURL(
					{
						callback: callback,
						queryParameters: {
							parentCalendarBookingId: calendarBookingId
						},
						resourceId: 'calendarBookingInvitees'
					}
				);
			},

			getCalendarName: function(name, calendarResourceName) {
				var instance = this;

				if (name !== calendarResourceName) {
					name = [calendarResourceName, STR_DASH, name].join(STR_SPACE);
				}

				return name;
			},

			getCalendarRenderingRules: function(calendarIds, startDate, endDate, ruleName, callback) {
				var instance = this;

				instance.invokeResourceURL(
					{
						callback: callback,
						queryParameters: {
							calendarIds: calendarIds.join(),
							endTime: endDate.getTime(),
							ruleName: ruleName,
							startTime: startDate.getTime()
						},
						resourceId: 'calendarRenderingRules'
					}
				);
			},

			getCalendarsMenu: function(config) {
				var instance = this;

				var toggler = new A.Toggler(
					{
						after: {
							expandedChange: function(event) {
								if (event.newVal) {
									var activeView = config.scheduler.get('activeView');

									activeView._fillHeight();
								}
							}
						},
						animated: true,
						content: config.content,
						expanded: false,
						header: config.header
					}
				);

				var items = [
					{
						caption: Liferay.Language.get('check-availability'),
						fn: function(event) {
							var instance = this;

							A.each(
								CalendarUtil.availableCalendars,
								function(item, index) {
									item.set('visible', false);
								}
							);

							var calendarList = instance.get('host');

							calendarList.activeItem.set('visible', true);

							toggler.expand();
							instance.hide();

							return false;
						},
						id: 'check-availability'
					}
				];

				var calendarsMenu = {
					items: items
				};

				if (config.invitable) {
					items.push(
						{
							caption: Liferay.Language.get('remove'),
							fn: function(event) {
								var instance = this;

								var calendarList = instance.get('host');

								calendarList.remove(calendarList.activeItem);

								instance.hide();
							},
							id: 'remove'
						}
					);

					calendarsMenu.on = {
						visibleChange: function(event) {
							var instance = this;

							if (event.newVal) {
								var calendarList = instance.get('host');

								var calendar = calendarList.activeItem;

								var hiddenItems = [];

								if (calendar.get('calendarId') === config.defaultCalendarId) {
									hiddenItems.push('remove');
								}

								instance.set('hiddenItems', hiddenItems);
							}
						}
					};
				}

				return calendarsMenu;
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

			getDefaultUserCalendar: function() {
				var instance = this;

				return instance.availableCalendars[CalendarUtil.DEFAULT_USER_CALENDAR_ID];
			},

			getEvent: function(calendarBookingId, success, failure) {
				var instance = this;

				instance.invokeService(
					{
						'/calendar.calendarbooking/get-calendar-booking': {
							calendarBookingId: calendarBookingId
						}
					},
					{
						failure: failure,
						success: success
					}
				);
			},

			getEvents: function(startDate, endDate, status, callback) {
				var instance = this;

				var calendarIds = AObject.keys(instance.availableCalendars);

				instance.invokeResourceURL(
					{
						callback: callback,
						queryParameters: {
							calendarIds: calendarIds.join(','),
							endTimeDay: endDate.getDate(),
							endTimeHour: endDate.getHours(),
							endTimeMinute: endDate.getMinutes(),
							endTimeMonth: endDate.getMonth(),
							endTimeYear: endDate.getFullYear(),
							startTimeDay: startDate.getDate(),
							startTimeHour: startDate.getHours(),
							startTimeMinute: startDate.getMinutes(),
							startTimeMonth: startDate.getMonth(),
							startTimeYear: startDate.getFullYear(),
							statuses: status.join(',')
						},
						resourceId: 'calendarBookings'
					}
				);
			},

			getLocalizationMap: function(value) {
				var instance = this;

				var map = {};

				map[themeDisplay.getLanguageId()] = value;

				return JSON.stringify(map);
			},

			getResourceCalendars: function(calendarResourceId, callback) {
				var instance = this;

				instance.invokeResourceURL(
					{
						callback: callback,
						queryParameters: {
							calendarResourceId: calendarResourceId
						},
						resourceId: 'resourceCalendars'
					}
				);
			},

			invokeActionURL: function(params) {
				var instance = this;

				var url = Liferay.PortletURL.createActionURL();

				url.setName(params.actionName);
				url.setParameters(params.queryParameters);
				url.setPortletId('com_liferay_calendar_web_portlet_CalendarPortlet');

				var payload;

				if (params.payload) {
					payload = Liferay.Util.ns(instance.PORTLET_NAMESPACE, params.payload);
				}

				A.io.request(
					url.toString(),
					{
						data: payload,
						dataType: 'JSON',
						on: {
							success: function() {
								params.callback(this.get('responseData'));
							}
						}
					}
				);
			},

			invokeResourceURL: function(params) {
				var instance = this;

				var url = Liferay.PortletURL.createResourceURL();

				url.setParameters(params.queryParameters);
				url.setPortletId('com_liferay_calendar_web_portlet_CalendarPortlet');
				url.setResourceId(params.resourceId);

				var payload;

				if (params.payload) {
					payload = Liferay.Util.ns(instance.PORTLET_NAMESPACE, params.payload);
				}

				A.io.request(
					url.toString(),
					{
						data: payload,
						dataType: 'JSON',
						on: {
							success: function() {
								params.callback(this.get('responseData'));
							}
						}
					}
				);
			},

			invokeService: function(payload, callback) {
				var instance = this;

				callback = callback || {};

				A.io.request(
					instance.INVOKER_URL,
					{
						cache: false,
						data: {
							cmd: JSON.stringify(payload),
							p_auth: Liferay.authToken
						},
						dataType: 'JSON',
						on: {
							failure: callback.failure,
							start: callback.start,
							success: function(event) {
								if (callback.success) {
									var data = this.get('responseData');

									callback.success.apply(this, [data, event]);
								}
							}
						}
					}
				);
			},

			invokeTransition: function(schedulerEvent, status) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				instance.invokeService(
					{
						'/calendar.calendarbooking/invoke-transition': {
							calendarBookingId: schedulerEvent.get('calendarBookingId'),
							status: status,
							userId: USER_ID
						}
					},
					{
						start: function() {
							schedulerEvent.set(
								'loading',
								true,
								{
									silent: true
								}
							);
						},

						success: function(data) {
							schedulerEvent.set(
								'loading',
								false,
								{
									silent: true
								}
							);

							if (data && !data.exception && scheduler) {
								var eventRecorder = scheduler.get('eventRecorder');

								eventRecorder.hidePopover();

								scheduler.load();
							}
						}
					}
				);
			},

			message: function(msg) {
				var instance = this;

				A.oneNS(instance.PORTLET_NAMESPACE, '#message').html(msg);
			},

			setEventAttrs: function(schedulerEvent, data) {
				var instance = this;

				var scheduler = schedulerEvent.get('scheduler');

				var newCalendarId = data.calendarId;

				var oldCalendarId = schedulerEvent.get('calendarId');

				if (scheduler) {
					var newCalendar = instance.availableCalendars[newCalendarId];
					var oldCalendar = instance.availableCalendars[oldCalendarId];

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
							status: data.status
						},
						{
							silent: true
						}
					);

					scheduler.syncEventsUI();
				}
			},

			syncCalendarsMap: function(calendarLists) {
				var instance = this;

				var availableCalendars = instance.availableCalendars = {};
				var visibleCalendars = instance.visibleCalendars = {};

				calendarLists.forEach(
					function(calendarList) {
						var calendars = calendarList.get('calendars');

						var defaultCalendarId = CalendarUtil.DEFAULT_USER_CALENDAR_ID;

						A.each(
							calendars,
							function(item, index) {
								var calendarId = item.get('calendarId');

								availableCalendars[calendarId] = item;

								if (item.get('visible')) {
									visibleCalendars[calendarId] = item;
								}

								if (item.get('defaultCalendar')) {
									var calendarResourceId = item.get('calendarResourceId');

									if (calendarResourceId == Liferay.CalendarUtil.GROUP_CALENDAR_RESOURCE_ID && item.get('permissions').MANAGE_BOOKINGS) {
										defaultCalendarId = calendarId;
									}
									else if (calendarResourceId == Liferay.CalendarUtil.USER_CALENDAR_RESOURCE_ID && defaultCalendarId == null) {
										defaultCalendarId = calendarId;
									}
								}
							}
						);

						if (defaultCalendarId != null) {
							CalendarUtil.DEFAULT_USER_CALENDAR_ID = defaultCalendarId;
						}
					}
				);

				return availableCalendars;
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

			updateEvent: function(schedulerEvent, updateInstance, allFollowing, success) {
				var instance = this;

				var endDate = schedulerEvent.get('endDate');
				var startDate = schedulerEvent.get('startDate');

				instance.invokeActionURL(
					{
						actionName: 'updateSchedulerCalendarBooking',
						callback: function(data) {
							schedulerEvent.set(
								'loading',
								false,
								{
									silent: true
								}
							);

							if (data) {
								if (data.exception) {
									instance.destroyEvent(schedulerEvent);
								}
								else {
									instance.setEventAttrs(schedulerEvent, data);

									if (success) {
										success.call(instance, data);
									}
								}
							}
						},
						payload: {
							allDay: schedulerEvent.get('allDay'),
							allFollowing: allFollowing,
							calendarBookingId: schedulerEvent.get('calendarBookingId'),
							calendarId: schedulerEvent.get('calendarId'),
							endTimeDay: endDate.getDate(),
							endTimeHour: endDate.getHours(),
							endTimeMinute: endDate.getMinutes(),
							endTimeMonth: endDate.getMonth(),
							endTimeYear: endDate.getFullYear(),
							instanceIndex: schedulerEvent.get('instanceIndex'),
							recurrence: schedulerEvent.get('recurrence'),
							startTimeDay: startDate.getDate(),
							startTimeHour: startDate.getHours(),
							startTimeMinute: startDate.getMinutes(),
							startTimeMonth: startDate.getMonth(),
							startTimeYear: startDate.getFullYear(),
							title: LString.unescapeHTML(schedulerEvent.get('content')),
							updateInstance: updateInstance
						}
					}
				);
			},

			updateSchedulerEvents: function(schedulerEvents, calendarBooking) {
				A.each(
					schedulerEvents,
					function(schedulerEvent) {
						if (calendarBooking.status === CalendarWorkflow.STATUS_DENIED) {
							var scheduler = schedulerEvent.get('scheduler');

							var eventRecorder = scheduler.get('eventRecorder');

							eventRecorder.hidePopover();

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
		requires: ['aui-datatype', 'aui-io', 'aui-scheduler', 'aui-toolbar', 'autocomplete', 'autocomplete-highlighters', 'liferay-portlet-url']
	}
);