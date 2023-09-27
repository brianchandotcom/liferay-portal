/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-session-listener',
	(A) => {
		const CalendarSessionListener = A.Component.create({
			ATTRS: {
				calendars: {
					value: [],
				},

				scheduler: {
					value: null,
				},
			},

			NAME: 'calendar-session-listener',

			prototype: {
				_disableCalendars() {
					const instance = this;

					const calendars = instance.get('calendars');

					// eslint-disable-next-line @liferay/aui/no-object
					A.Object.each(calendars, (calendar) => {
						const permissions = calendar.get('permissions');

						permissions.DELETE = false;
						permissions.MANAGE_BOOKINGS = false;
						permissions.UPDATE = false;
						permissions.PERMISSIONS = false;
					});
				},

				_disableEvents() {
					const instance = this;

					const scheduler = instance.get('scheduler');

					scheduler.getEvents().forEach((event) => {
						event.set('disabled', true);
					});
				},

				_disableScheduler() {
					const instance = this;

					const addEventButtons = A.all('.calendar-add-event-btn');

					const scheduler = instance.get('scheduler');

					addEventButtons.set('disabled', true);

					scheduler.set('eventRecorder', null);
				},

				_onSessionExpired() {
					const instance = this;

					instance._disableCalendars();

					instance._disableScheduler();

					instance._disableEvents();
				},

				initializer() {
					const instance = this;

					Liferay.on(
						'sessionExpired',
						A.bind(instance._onSessionExpired, instance)
					);
				},
			},
		});

		Liferay.CalendarSessionListener = CalendarSessionListener;
	},
	'',
	{
		requires: ['aui-base', 'aui-component'],
	}
);
