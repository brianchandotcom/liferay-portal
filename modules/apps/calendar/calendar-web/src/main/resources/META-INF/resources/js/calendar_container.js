/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-container',
	(A) => {
		const Lang = A.Lang;

		const isObject = Lang.isObject;

		const toInt = Lang.toInt;

		const STR_DASH = '-';

		const STR_SPACE = ' ';

		const CalendarContainer = A.Component.create({
			ATTRS: {
				availableCalendars: {
					validator: isObject,
					value: {},
				},

				defaultCalendar: {
					validator: isObject,
					value: null,
				},

				groupCalendarResourceId: {
					setter: toInt,
					value: 0,
				},

				userCalendarResourceId: {
					setter: toInt,
					value: 0,
				},

				visibleCalendars: {
					validator: isObject,
					value: {},
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'calendar-container',

			prototype: {
				createCalendarsAutoComplete(resourceURL, input, afterSelectFn) {
					const instance = this;

					input.plug(A.Plugin.AutoComplete, {
						activateFirstItem: true,
						after: {
							select: afterSelectFn,
						},
						maxResults: 20,
						requestTemplate:
							'&' +
							instance.get('namespace') +
							'keywords={query}',
						resultFilters(_query, results) {
							return results.filter((item) => {
								return !instance.getCalendar(
									item.raw.calendarId
								);
							});
						},
						resultFormatter(query, results) {
							return results.map((result) => {
								const calendar = result.raw;
								const calendarResourceName =
									calendar.calendarResourceName;
								let name = calendar.name;

								if (name !== calendarResourceName) {
									name = [
										calendarResourceName,
										STR_DASH,
										name,
									].join(STR_SPACE);
								}

								return A.Highlight.words(name, query);
							});
						},
						resultHighlighter: 'wordMatch',
						resultTextLocator: 'calendarResourceName',
						source: resourceURL,
						width: 'auto',
					});

					input.ac
						.get('boundingBox')
						.setStyle('min-width', input.outerWidth());
				},

				getCalendar(calendarId) {
					const instance = this;

					const availableCalendars = instance.get(
						'availableCalendars'
					);

					return availableCalendars[calendarId];
				},

				getCalendarsMenu(config) {
					const instance = this;

					const availableCalendars = instance.get(
						'availableCalendars'
					);

					const toggler = new A.Toggler({
						after: {
							expandedChange(event) {
								if (event.newVal) {
									const activeView = config.scheduler.get(
										'activeView'
									);

									activeView._fillHeight();
								}
							},
						},
						animated: true,
						content: config.content,
						expanded: false,
						header: config.header,
					});

					const items = [
						{
							caption: Liferay.Language.get('check-availability'),
							fn() {
								const instance = this;

								A.each(availableCalendars, (item) => {
									item.set('visible', false);
								});

								const calendarList = instance.get('host');

								calendarList.activeItem.set('visible', true);

								toggler.expand();
								instance.hide();

								return false;
							},
							id: 'check-availability',
						},
					];

					const calendarsMenu = {
						items,
					};

					if (config.invitable) {
						items.push({
							caption: Liferay.Language.get('remove'),
							fn() {
								const instance = this;

								const calendarList = instance.get('host');

								calendarList.remove(calendarList.activeItem);

								instance.hide();
							},
							id: 'remove',
						});

						calendarsMenu.on = {
							visibleChange(event) {
								const instance = this;

								if (event.newVal) {
									const calendarList = instance.get('host');

									const calendar = calendarList.activeItem;

									const hiddenItems = [];

									if (
										calendar.get('calendarId') ===
										config.defaultCalendarId
									) {
										hiddenItems.push('remove');
									}

									instance.set('hiddenItems', hiddenItems);
								}
							},
						};
					}

					return calendarsMenu;
				},

				syncCalendarsMap(calendarLists) {
					const instance = this;

					let defaultCalendar = instance.get('defaultCalendar');

					const availableCalendars = {};

					const visibleCalendars = {};

					calendarLists.forEach((calendarList) => {
						const calendars = calendarList.get('calendars');

						A.each(calendars, (item) => {
							const calendarId = item.get('calendarId');

							availableCalendars[calendarId] = item;

							if (item.get('visible')) {
								visibleCalendars[calendarId] = item;
							}

							const calendarResourceId = item.get(
								'calendarResourceId'
							);

							if (item.get('defaultCalendar')) {
								if (
									calendarResourceId ===
										instance.get(
											'groupCalendarResourceId'
										) &&
									item.get('permissions').MANAGE_BOOKINGS
								) {
									defaultCalendar = item;
								}

								if (
									calendarResourceId ===
									instance.get('userCalendarResourceId')
								) {
									defaultCalendar = item;
								}

								if (
									calendarResourceId ===
										instance.get(
											'groupCalendarResourceId'
										) &&
									item.get('permissions').VIEW_BOOKING_DETAILS
								) {
									defaultCalendar = item;
								}
							}

							if (
								(defaultCalendar === null ||
									defaultCalendar === undefined) &&
								calendarResourceId ===
									instance.get('groupCalendarResourceId') &&
								item.get('permissions').VIEW_BOOKING_DETAILS
							) {
								defaultCalendar = item;
							}
						});
					});

					instance.set('availableCalendars', availableCalendars);
					instance.set('visibleCalendars', visibleCalendars);
					instance.set('defaultCalendar', defaultCalendar);

					return availableCalendars;
				},
			},
		});

		Liferay.CalendarContainer = CalendarContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'liferay-portlet-base'],
	}
);
