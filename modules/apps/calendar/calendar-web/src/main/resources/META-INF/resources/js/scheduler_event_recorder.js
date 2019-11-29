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
	'liferay-scheduler-event-recorder',
	A => {
		var AArray = A.Array;
		var AObject = A.Object;
		var Lang = A.Lang;

		var CalendarWorkflow = Liferay.CalendarWorkflow;

		var isObject = Lang.isObject;
		var isString = Lang.isString;
		var isValue = Lang.isValue;

		var toInt = function(value) {
			return Lang.toInt(value, 10, 0);
		};

		var STR_BLANK = '';

		var STR_COMMA_SPACE = ', ';

		var CalendarUtil = Liferay.CalendarUtil;

		var SchedulerEventRecorder = A.Component.create({
			ATTRS: {
				calendarContainer: {
					validator: isObject,
					value: null
				},

				calendarId: {
					setter: toInt,
					value: 0
				},

				dateFormat: {
					validator: isString,
					value: Liferay.Language.get('a-b-d')
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

				remoteServices: {
					validator: isObject,
					value: null
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
				_getFooterToolbar() {
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

					var calendarContainer = instance.get('calendarContainer');

					var calendar = calendarContainer.getCalendar(
						schedulerEvent.get('calendarId')
					);
					var status = schedulerEvent.get('status');

					if (calendar) {
						var permissions = calendar.get('permissions');

						if (
							instance._hasSaveButton(
								permissions,
								calendar,
								status
							)
						) {
							editGroup.push({
								id: 'saveBtn',
								label: Liferay.Language.get('save'),
								on: {
									click: A.bind(
										instance._handleSaveEvent,
										instance
									)
								},
								primary: true
							});
						}

						if (
							instance._hasEditButton(
								permissions,
								calendar,
								status
							)
						) {
							editGroup.push({
								id: 'editBtn',
								label: Liferay.Language.get('edit'),
								on: {
									click: A.bind(
										instance._handleEditEvent,
										instance
									)
								}
							});
						}

						if (
							schedulerEventCreated === true &&
							permissions.VIEW_BOOKING_DETAILS
						) {
							editGroup.push({
								id: 'viewBtn',
								label: Liferay.Language.get('view-details'),
								on: {
									click: A.bind(
										instance._handleViewEvent,
										instance
									)
								}
							});
						}

						if (
							schedulerEvent.isMasterBooking() &&
							instance._hasDeleteButton(
								permissions,
								calendar,
								status
							)
						) {
							editGroup.push({
								id: 'deleteBtn',
								label: Liferay.Language.get('delete'),
								on: {
									click: A.bind(
										instance._handleDeleteEvent,
										instance
									)
								}
							});
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

				_handleEditEvent() {
					var instance = this;

					var scheduler = instance.get('scheduler');

					var activeViewName = scheduler
						.get('activeView')
						.get('name');

					var date = scheduler.get('date');

					var schedulerEvent = instance.get('event');

					var editCalendarBookingURL = decodeURIComponent(
						instance.get('editCalendarBookingURL')
					);

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
						data.calendarBookingId = schedulerEvent.get(
							'calendarBookingId'
						);
					}

					Liferay.Util.openWindow({
						dialog: {
							after: {
								destroy() {
									scheduler.load();
								}
							},
							destroyOnHide: true,
							modal: true
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						},
						refreshWindow: window,
						title: Liferay.Language.get('edit-calendar-booking'),
						uri: CalendarUtil.fillURLParameters(
							editCalendarBookingURL,
							data
						)
					});

					instance.hidePopover();
				},

				_handleEventAnswer(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					var schedulerEvent = instance.get('event');

					var linkEnabled = A.DataType.Boolean.parse(
						currentTarget.hasClass('calendar-event-answer-true')
					);

					var statusData = toInt(currentTarget.getData('status'));

					if (schedulerEvent && linkEnabled) {
						var remoteServices = instance.get('remoteServices');

						if (schedulerEvent.isRecurring()) {
							Liferay.RecurrenceUtil.openConfirmationPanel(
								'invokeTransition',
								() => {
									remoteServices.invokeTransition(
										schedulerEvent,
										schedulerEvent.get('instanceIndex'),
										statusData,
										true,
										false
									);
								},
								() => {
									remoteServices.invokeTransition(
										schedulerEvent,
										schedulerEvent.get('instanceIndex'),
										statusData,
										true,
										true
									);
								},
								() => {
									remoteServices.invokeTransition(
										schedulerEvent,
										schedulerEvent.get('instanceIndex'),
										statusData,
										false,
										false
									);
								}
							);
						}
						else {
							remoteServices.invokeTransition(
								schedulerEvent,
								0,
								statusData,
								false,
								false
							);
						}
					}
				},

				_handleViewEvent(event) {
					var instance = this;

					var viewCalendarBookingURL = decodeURIComponent(
						instance.get('viewCalendarBookingURL')
					);

					var data = instance.serializeForm();

					var schedulerEvent = instance.get('event');

					data.calendarBookingId = schedulerEvent.get(
						'calendarBookingId'
					);

					Liferay.Util.openWindow({
						dialog: {
							after: {
								destroy() {
									schedulerEvent.syncWithServer();
								}
							},
							destroyOnHide: true,
							modal: true
						},
						refreshWindow: window,
						title: Liferay.Language.get(
							'view-calendar-booking-details'
						),
						uri: CalendarUtil.fillURLParameters(
							viewCalendarBookingURL,
							data
						)
					});

					event.domEvent.preventDefault();
				},

				_hasDeleteButton(permissions, calendar, _status) {
					return permissions.MANAGE_BOOKINGS && calendar;
				},

				_hasEditButton(permissions, _calendar, _status) {
					return permissions.MANAGE_BOOKINGS;
				},

				_hasSaveButton(permissions, _calendar, _status) {
					return permissions.MANAGE_BOOKINGS;
				},

				_hasWorkflowStatusPermission(schedulerEvent, newStatus) {
					var instance = this;

					var hasPermission = false;

					if (schedulerEvent) {
						var calendarId = schedulerEvent.get('calendarId');

						var calendarContainer = instance.get(
							'calendarContainer'
						);

						var calendar = calendarContainer.getCalendar(
							calendarId
						);

						var permissions = calendar.get('permissions');

						var status = schedulerEvent.get('status');

						hasPermission =
							permissions.MANAGE_BOOKINGS &&
							status !== newStatus &&
							status !== CalendarWorkflow.STATUS_DRAFT;
					}

					return hasPermission;
				},

				_renderPopOver() {
					var instance = this;

					var popoverBB = instance.popover.get('boundingBox');

					SchedulerEventRecorder.superclass._renderPopOver.apply(
						this,
						arguments
					);

					popoverBB.delegate(
						['change', 'keypress'],
						event => {
							var schedulerEvent =
								instance.get('event') || instance;

							var calendarId = toInt(event.currentTarget.val());

							var calendarContainer = instance.get(
								'calendarContainer'
							);

							var selectedCalendar = calendarContainer.getCalendar(
								calendarId
							);

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
						'#' +
							instance.get('portletNamespace') +
							'eventRecorderCalendar'
					);
				},

				_showResources() {
					var instance = this;

					var schedulerEvent = instance.get('event');

					var popoverBB = instance.popover.get('boundingBox');

					popoverBB.toggleClass(
						'calendar-portlet-event-recorder-editing',
						!!schedulerEvent
					);

					var calendarContainer = instance.get('calendarContainer');

					var defaultCalendar = calendarContainer.get(
						'defaultCalendar'
					);

					var calendarId = defaultCalendar.get('calendarId');
					var color = defaultCalendar.get('color');

					var eventInstance = instance;

					if (schedulerEvent) {
						calendarId = schedulerEvent.get('calendarId');

						var calendar = calendarContainer.getCalendar(
							calendarId
						);

						if (calendar) {
							color = calendar.get('color');

							eventInstance = schedulerEvent;
						}
					}

					eventInstance.set('color', color, {
						silent: true
					});

					var portletNamespace = instance.get('portletNamespace');

					var eventRecorderCalendar = A.one(
						'#' + portletNamespace + 'eventRecorderCalendar'
					);

					if (eventRecorderCalendar) {
						eventRecorderCalendar.val(calendarId.toString());
					}

					instance._syncInvitees();
				},

				_syncInvitees() {
					var instance = this;

					var schedulerEvent = instance.get('event');

					if (schedulerEvent) {
						var calendarContainer = instance.get(
							'calendarContainer'
						);

						var calendar = calendarContainer.getCalendar(
							schedulerEvent.get('calendarId')
						);

						if (calendar) {
							var permissions = calendar.get('permissions');

							if (permissions.VIEW_BOOKING_DETAILS) {
								var parentCalendarBookingId = schedulerEvent.get(
									'parentCalendarBookingId'
								);

								var portletNamespace = instance.get(
									'portletNamespace'
								);

								var remoteServices = instance.get(
									'remoteServices'
								);

								remoteServices.getCalendarBookingInvitees(
									parentCalendarBookingId,
									data => {
										var results = AArray.partition(
											data,
											item => {
												return (
													toInt(item.classNameId) ===
													CalendarUtil.USER_CLASS_NAME_ID
												);
											}
										);

										instance._syncInviteesContent(
											'#' +
												portletNamespace +
												'eventRecorderUsers',
											results.matches
										);
										instance._syncInviteesContent(
											'#' +
												portletNamespace +
												'eventRecorderResources',
											results.rejects
										);
									}
								);
							}
						}
					}
				},

				_syncInviteesContent(contentNode, calendarResources) {
					var values = calendarResources.map(item => {
						return Lang.String.escapeHTML(item.name);
					});

					contentNode = A.one(contentNode);

					var messageNode = contentNode.one(
						'.calendar-portlet-invitees'
					);

					var messageHTML = '&mdash;';

					if (values.length > 0) {
						contentNode.show();

						messageHTML = values.join(STR_COMMA_SPACE);
					}

					messageNode.html(messageHTML);
				},

				getTemplateData() {
					var instance = this;

					var editing = true;

					var schedulerEvent = instance.get('event');

					if (!schedulerEvent) {
						editing = false;

						schedulerEvent = instance;
					}

					var calendarContainer = instance.get('calendarContainer');

					var calendar = calendarContainer.getCalendar(
						schedulerEvent.get('calendarId')
					);

					var permissions = calendar.get('permissions');

					var templateData = SchedulerEventRecorder.superclass.getTemplateData.apply(
						this,
						arguments
					);

					return A.merge(templateData, {
						acceptLinkEnabled: instance._hasWorkflowStatusPermission(
							schedulerEvent,
							CalendarWorkflow.STATUS_APPROVED
						),
						allDay: schedulerEvent.get('allDay'),
						availableCalendars: calendarContainer.get(
							'availableCalendars'
						),
						calendar,
						calendarIds: AObject.keys(
							calendarContainer.get('availableCalendars')
						),
						declineLinkEnabled: instance._hasWorkflowStatusPermission(
							schedulerEvent,
							CalendarWorkflow.STATUS_DENIED
						),
						editing,
						endTime: templateData.endDate,
						hasWorkflowInstanceLink: schedulerEvent.get(
							'hasWorkflowInstanceLink'
						),
						instanceIndex: schedulerEvent.get('instanceIndex'),
						maybeLinkEnabled: instance._hasWorkflowStatusPermission(
							schedulerEvent,
							CalendarWorkflow.STATUS_MAYBE
						),
						permissions,
						startTime: templateData.startDate,
						status: schedulerEvent.get('status'),
						workflowStatus: CalendarWorkflow
					});
				},

				getUpdatedSchedulerEvent(optAttrMap) {
					var instance = this;

					var attrMap = {
						color: instance.get('color')
					};

					var event = instance.get('event');

					if (event) {
						var calendarContainer = instance.get(
							'calendarContainer'
						);

						var calendar = calendarContainer.getCalendar(
							event.get('calendarId')
						);

						if (calendar) {
							attrMap.color = calendar.get('color');
						}
					}

					return SchedulerEventRecorder.superclass.getUpdatedSchedulerEvent.call(
						instance,
						A.merge(attrMap, optAttrMap)
					);
				},

				initializer() {
					var instance = this;

					var popoverBB = instance.popover.get('boundingBox');

					popoverBB.delegate(
						'click',
						instance._handleEventAnswer,
						'.calendar-event-answer',
						instance
					);
				},

				isMasterBooking: Lang.emptyFnFalse,

				populateForm() {
					var instance = this;

					var bodyTemplate = instance.get('bodyTemplate');

					var headerTemplate = instance.get('headerTemplate');

					var templateData = instance.getTemplateData();

					if (
						A.instanceOf(bodyTemplate, A.Template) &&
						A.instanceOf(headerTemplate, A.Template)
					) {
						instance.popover.setStdModContent(
							'body',
							bodyTemplate.parse(templateData)
						);
						instance.popover.setStdModContent(
							'header',
							headerTemplate.parse(templateData)
						);

						instance.popover.addToolbar(
							instance._getFooterToolbar(),
							'footer'
						);
					}
					else {
						SchedulerEventRecorder.superclass.populateForm.apply(
							instance,
							arguments
						);
					}

					instance.popover.addToolbar(
						[
							{
								cssClass: 'close',
								label: '\u00D7',
								on: {
									click: A.bind(
										instance._handleCancelEvent,
										instance
									)
								},
								render: true
							}
						],
						'body'
					);

					if (instance.popover.headerNode) {
						instance.popover.headerNode.toggleClass(
							'hide',
							!templateData.permissions.VIEW_BOOKING_DETAILS
						);
					}

					instance._showResources();
				}
			}
		});

		Liferay.SchedulerEventRecorder = SchedulerEventRecorder;
	},
	'',
	{
		requires: ['dd-plugin', 'liferay-calendar-util', 'resize-plugin']
	}
);
