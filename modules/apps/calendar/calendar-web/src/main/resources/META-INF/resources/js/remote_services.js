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
	'liferay-calendar-remote-services',
	function(A) {
		var Lang = A.Lang;
		var LString = Lang.String;

		var isString = Lang.isString;
		var toInt = Lang.toInt;

		var CalendarUtil = Liferay.CalendarUtil;
		var MessageUtil = Liferay.CalendarMessageUtil;

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
								calendarBookingId: schedulerEvent.get(
									'calendarBookingId'
								)
							}
						},
						{
							success: function(data) {
								if (success) {
									success.call(instance, data);
									MessageUtil.showSuccessMessage(
										instance.get('rootNode')
									);
								}
							}
						}
					);
				},

				deleteEventInstance: function(
					schedulerEvent,
					allFollowing,
					success
				) {
					var instance = this;

					instance._invokeService(
						{
							'/calendar.calendarbooking/delete-calendar-booking-instance': {
								allFollowing: allFollowing,
								calendarBookingId: schedulerEvent.get(
									'calendarBookingId'
								),
								deleteRecurringCalendarBookings: true,
								instanceIndex: schedulerEvent.get(
									'instanceIndex'
								)
							}
						},
						{
							success: function(data) {
								if (success) {
									success.call(instance, data);
									MessageUtil.showSuccessMessage(
										instance.get('rootNode')
									);
								}
							}
						}
					);
				},

				getCalendar: function(calendarId, callback) {
					var instance = this;

					instance._invokeResourceURL({
						callback: callback,
						queryParameters: {
							calendarId: calendarId
						},
						resourceId: 'calendar'
					});
				},

				getCalendarBookingInvitees: function(
					calendarBookingId,
					callback
				) {
					var instance = this;

					instance._invokeResourceURL({
						callback: callback,
						queryParameters: {
							parentCalendarBookingId: calendarBookingId
						},
						resourceId: 'calendarBookingInvitees'
					});
				},

				getCalendarRenderingRules: function(
					calendarIds,
					startDate,
					endDate,
					ruleName,
					callback
				) {
					var instance = this;

					instance._invokeResourceURL({
						callback: callback,
						payload: {
							calendarIds: calendarIds.join(),
							endTime: endDate.getTime(),
							ruleName: ruleName,
							startTime: startDate.getTime()
						},
						resourceId: 'calendarRenderingRules'
					});
				},

				getCurrentTime: function(callback) {
					var instance = this;

					var lastCurrentTime = instance.lastCurrentTime;

					if (lastCurrentTime) {
						var lastBrowserTime = instance.lastBrowserTime;

						var browserTime = new Date();

						var timeDiff = Math.abs(
							browserTime.getTime() - lastBrowserTime.getTime()
						);

						var currentTime = lastCurrentTime.getTime() + timeDiff;

						lastCurrentTime.setTime(currentTime);

						instance.lastCurrentTime = lastCurrentTime;

						instance.lastBrowserTime = browserTime;

						callback(instance.lastCurrentTime);

						return;
					}

					instance._invokeResourceURL({
						callback: function(dateObj) {
							instance.lastCurrentTime = CalendarUtil.getDateFromObject(
								dateObj
							);

							instance.lastBrowserTime = new Date();

							callback(instance.lastCurrentTime);
						},
						resourceId: 'currentTime'
					});
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

				getEvents: function(
					calendarIds,
					eventsPerPage,
					startDate,
					endDate,
					status,
					callback
				) {
					var instance = this;

					instance._invokeResourceURL({
						callback: callback,
						payload: {
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
					});
				},

				getResourceCalendars: function(calendarResourceId, callback) {
					var instance = this;

					instance._invokeResourceURL({
						callback: callback,
						queryParameters: {
							calendarResourceId: calendarResourceId
						},
						resourceId: 'resourceCalendars'
					});
				},

				hasExclusiveCalendarBooking: function(
					calendarId,
					startDate,
					endDate,
					callback
				) {
					var instance = this;

					instance._invokeResourceURL({
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
					});
				},

				invokeTransition: function(
					schedulerEvent,
					instanceIndex,
					status,
					updateInstance,
					allFollowing
				) {
					var instance = this;

					var scheduler = schedulerEvent.get('scheduler');

					instance._invokeService(
						{
							'/calendar.calendarbooking/invoke-transition': {
								allFollowing: allFollowing,
								calendarBookingId: schedulerEvent.get(
									'calendarBookingId'
								),
								instanceIndex: instanceIndex,
								status: status,
								updateInstance: updateInstance,
								userId: instance.get('userId')
							}
						},
						{
							start: function() {
								schedulerEvent.set('loading', true, {
									silent: true
								});
							},

							success: function(data) {
								schedulerEvent.set('loading', false, {
									silent: true
								});

								if (data && !data.exception && scheduler) {
									var eventRecorder = scheduler.get(
										'eventRecorder'
									);

									eventRecorder.hidePopover();

									scheduler.load();
								}
							}
						}
					);
				},

				updateCalendarColor: function(calendarId, color) {
					var instance = this;

					instance._invokeService({
						'/calendar.calendar/update-color': {
							calendarId: calendarId,
							color: parseInt(color.substr(1), 16)
						}
					});
				},

				updateEvent: function(
					schedulerEvent,
					updateInstance,
					allFollowing,
					success
				) {
					var instance = this;

					var endDate = schedulerEvent.get('endDate');
					var startDate = schedulerEvent.get('startDate');

					instance._invokeActionURL({
						actionName: 'updateSchedulerCalendarBooking',
						callback: function(data) {
							schedulerEvent.set('loading', false, {
								silent: true
							});

							if (data) {
								if (data.exception) {
									CalendarUtil.destroyEvent(schedulerEvent);
									MessageUtil.showErrorMessage(
										instance.get('rootNode'),
										data.exception
									);
								} else {
									CalendarUtil.setEventAttrs(
										schedulerEvent,
										data
									);

									if (success) {
										success.call(instance, data);
										MessageUtil.showSuccessMessage(
											instance.get('rootNode')
										);
									}
								}
							}
						},
						payload: {
							allDay: schedulerEvent.get('allDay'),
							allFollowing: allFollowing,
							calendarBookingId: schedulerEvent.get(
								'calendarBookingId'
							),
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
							title: LString.unescapeHTML(
								schedulerEvent.get('content')
							),
							updateInstance: updateInstance
						}
					});
				},

				_invokeActionURL: function(params) {
					var instance = this;

					var url = Liferay.PortletURL.createActionURL();

					url.setName(params.actionName);
					url.setParameters(params.queryParameters);
					url.setPortletId(instance.ID);

					var payload;

					if (params.payload) {
						payload = Liferay.Util.ns(
							instance.get('namespace'),
							params.payload
						);
					}

					const data = new URLSearchParams();

					Object.keys(payload).forEach(key => {
						data.append(key, payload[key]);
					});

					Liferay.Util.fetch(url.toString(), {
						body: data,
						method: 'POST'
					})
						.then(response => {
							return response.json();
						})
						.then(data => {
							params.callback(data);
						});
				},

				_invokeResourceURL: function(params) {
					var instance = this;

					var url = Liferay.PortletURL.createResourceURL();

					url.setDoAsUserId(
						Liferay.ThemeDisplay.getDoAsUserIdEncoded()
					);
					url.setParameters(params.queryParameters);
					url.setPortletId(instance.ID);
					url.setResourceId(params.resourceId);

					var payload;

					if (params.payload) {
						payload = Liferay.Util.ns(
							instance.get('namespace'),
							params.payload
						);
					}

					const data = new URLSearchParams();

					if (payload) {
						Object.keys(payload).forEach(key => {
							data.append(key, payload[key]);
						});
					}

					Liferay.Util.fetch(url.toString(), {
						body: data,
						method: 'POST'
					})
						.then(response => {
							return response.text();
						})
						.then(data => {
							if (data.length) {
								params.callback(JSON.parse(data));
							}
						});
				},

				_invokeService: function(payload, callback) {
					var instance = this;

					callback = callback || {};

					const data = new URLSearchParams();
					data.append('cmd', JSON.stringify(payload));
					data.append('payload', Liferay.authToken);

					Liferay.Util.fetch(instance.get('invokerURL'), {
						body: data,
						method: 'POST'
					})
						.then(response => {
							return response.json();
						})
						.then(data => {
							if (Liferay.Util.isFunction(callback.success)) {
								callback.success.apply(this, [data]);
							}
						})
						.catch(err => {
							if (Liferay.Util.isFunction(callback.failure)) {
								callback.failure(err);
							}
						});
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
		requires: [
			'aui-base',
			'aui-component',
			'liferay-calendar-message-util',
			'liferay-calendar-util',
			'liferay-portlet-base',
			'liferay-portlet-url'
		]
	}
);
