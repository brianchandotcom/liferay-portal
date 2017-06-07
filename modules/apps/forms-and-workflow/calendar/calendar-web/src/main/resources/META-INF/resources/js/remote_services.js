AUI.add(
	'liferay-calendar-remote-services',
	function(A) {
		var Lang = A.Lang;
		var LString = Lang.String;

		var isString = Lang.isString;
		var toInt = Lang.toInt;

		var CalendarUtil = Liferay.CalendarUtil;

		var CalendarRemoteServices = A.Base.create(
			'calendar-remote-services',
			A.Base,
			[Liferay.PortletBase],
			{
				deleteCalendar: function(calendarId, callback) {
					var instance = this;

					instance._invokeService(
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

					instance._invokeService(
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

					instance._invokeService(
						{
							'/calendar.calendarbooking/delete-calendar-booking-instance': {
								allFollowing: allFollowing,
								calendarBookingId: schedulerEvent.get('calendarBookingId'),
								deleteRecurringCalendarBookings: true,
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

				getCalendar: function(calendarId, callback) {
					var instance = this;

					instance._invokeResourceURL(
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

					instance._invokeResourceURL(
						{
							callback: callback,
							queryParameters: {
								parentCalendarBookingId: calendarBookingId
							},
							resourceId: 'calendarBookingInvitees'
						}
					);
				},

				getCalendarRenderingRules: function(calendarIds, startDate, endDate, ruleName, callback) {
					var instance = this;

					instance._invokeResourceURL(
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

				getCurrentTime: function(callback) {
					var instance = this;

					instance._invokeResourceURL(
						{
							callback: function(dateObj) {
								callback(CalendarUtil.getDateFromObject(dateObj));
							},
							resourceId: 'currentTime'
						}
					);
				},

				getEvent: function(calendarBookingId, success, failure) {
					var instance = this;

					instance._invokeService(
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

				getEvents: function(calendarIds, eventsPerPage, startDate, endDate, status, callback) {
					var instance = this;

					instance._invokeResourceURL(
						{
							callback: callback,
							queryParameters: {
								calendarIds: calendarIds.join(','),
								endTimeDay: endDate.getDate(),
								endTimeHour: endDate.getHours(),
								endTimeMinute: endDate.getMinutes(),
								endTimeMonth: endDate.getMonth(),
								endTimeYear: endDate.getFullYear(),
								eventsPerPage: eventsPerPage,
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

				getResourceCalendars: function(calendarResourceId, callback) {
					var instance = this;

					instance._invokeResourceURL(
						{
							callback: callback,
							queryParameters: {
								calendarResourceId: calendarResourceId
							},
							resourceId: 'resourceCalendars'
						}
					);
				},

				hasExclusiveCalendarBooking: function(calendarId, startDate, endDate, callback) {
					var instance = this;

					instance._invokeResourceURL(
						{
							callback: function(result) {
								callback(result.hasExclusiveCalendarBooking);
							},
							queryParameters: {
								calendarId: calendarId,
								endTimeDay: endDate.getDate(),
								endTimeHour: endDate.getHours(),
								endTimeMinute: endDate.getMinutes(),
								endTimeMonth: endDate.getMonth(),
								endTimeYear: endDate.getFullYear(),
								startTimeDay: startDate.getDate(),
								startTimeHour: startDate.getHours(),
								startTimeMinute: startDate.getMinutes(),
								startTimeMonth: startDate.getMonth(),
								startTimeYear: startDate.getFullYear()
							},
							resourceId: 'hasExclusiveCalendarBooking'
						}
					);
				},

				invokeTransition: function(schedulerEvent, instanceIndex, status, updateInstance, allFollowing) {
					var instance = this;

					var scheduler = schedulerEvent.get('scheduler');

					instance._invokeService(
						{
							'/calendar.calendarbooking/invoke-transition': {
								allFollowing: allFollowing,
								calendarBookingId: schedulerEvent.get('calendarBookingId'),
								instanceIndex: instanceIndex,
								status: status,
								updateInstance: updateInstance,
								userId: instance.get('userId')
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

				updateCalendarColor: function(calendarId, color) {
					var instance = this;

					instance._invokeService(
						{
							'/calendar.calendar/update-color': {
								calendarId: calendarId,
								color: parseInt(color.substr(1), 16)
							}
						}
					);
				},

				updateEvent: function(schedulerEvent, updateInstance, allFollowing, success) {
					var instance = this;

					var endDate = schedulerEvent.get('endDate');
					var startDate = schedulerEvent.get('startDate');

					instance._invokeActionURL(
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
										CalendarUtil.destroyEvent(schedulerEvent);
									}
									else {
										CalendarUtil.setEventAttrs(schedulerEvent, data);

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

				_invokeActionURL: function(params) {
					var instance = this;

					var url = Liferay.PortletURL.createActionURL();

					url.setName(params.actionName);
					url.setParameters(params.queryParameters);
					url.setPortletId(instance.ID);

					var payload;

					if (params.payload) {
						payload = Liferay.Util.ns(instance.get('namespace'), params.payload);
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

				_invokeResourceURL: function(params) {
					var instance = this;

					var url = Liferay.PortletURL.createResourceURL();

					url.setParameters(params.queryParameters);
					url.setPortletId(instance.ID);
					url.setResourceId(params.resourceId);

					var payload;

					if (params.payload) {
						payload = Liferay.Util.ns(instance.get('namespace'), params.payload);
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

				_invokeService: function(payload, callback) {
					var instance = this;

					callback = callback || {};

					A.io.request(
						instance.get('invokerURL'),
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
				}
			},
			{
				ATTRS: {
					invokerURL: {
						validator: isString,
						value: ''
					},
					userId: {
						setter: toInt
					}
				}
			}
		);

		Liferay.CalendarRemoteServices = CalendarRemoteServices;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'aui-io', 'liferay-calendar-util', 'liferay-portlet-base', 'liferay-portlet-url']
	}
);