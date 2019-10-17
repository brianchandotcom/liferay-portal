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
	'liferay-calendar-container',
	A => {
		var Lang = A.Lang;

		var isObject = Lang.isObject;

		var toInt = Lang.toInt;

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var CalendarContainer = A.Component.create({
			ATTRS: {
				availableCalendars: {
					validator: isObject,
					value: {}
				},

				defaultCalendar: {
					validator: isObject,
					value: null
				},

				groupCalendarResourceId: {
					setter: toInt,
					value: 0
				},

				userCalendarResourceId: {
					setter: toInt,
					value: 0
				},

				visibleCalendars: {
					validator: isObject,
					value: {}
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'calendar-container',

			prototype: {
				createCalendarsAutoComplete(resourceURL, input, afterSelectFn) {
					var instance = this;

					input.plug(A.Plugin.AutoComplete, {
						activateFirstItem: true,
						after: {
							select: afterSelectFn
						},
						maxResults: 20,
						requestTemplate:
							'&' +
							instance.get('namespace') +
							'keywords={query}',
						resultFilters(_query, results) {
							return results.filter(item => {
								return !instance.getCalendar(
									item.raw.calendarId
								);
							});
						},
						resultFormatter(query, results) {
							return results.map(result => {
								var calendar = result.raw;
								var calendarResourceName =
									calendar.calendarResourceName;
								var name = calendar.name;

								if (name !== calendarResourceName) {
									name = [
										calendarResourceName,
										STR_DASH,
										name
									].join(STR_SPACE);
								}

								return A.Highlight.words(name, query);
							});
						},
						resultHighlighter: 'wordMatch',
						resultTextLocator: 'calendarResourceName',
						source: resourceURL,
						width: 'auto'
					});

					input.ac
						.get('boundingBox')
						.setStyle('min-width', input.outerWidth());
				},

				getCalendar(calendarId) {
					var instance = this;

					var availableCalendars = instance.get('availableCalendars');

					return availableCalendars[calendarId];
				},

				getCalendarsMenu(config) {
					var instance = this;

					var availableCalendars = instance.get('availableCalendars');

					var toggler = new A.Toggler({
						after: {
							expandedChange(event) {
								if (event.newVal) {
									var activeView = config.scheduler.get(
										'activeView'
									);

									activeView._fillHeight();
								}
							}
						},
						animated: true,
						content: config.content,
						expanded: false,
						header: config.header
					});

					var items = [
						{
							caption: Liferay.Language.get('check-availability'),
							fn() {
								var instance = this;

								A.each(availableCalendars, item => {
									item.set('visible', false);
								});

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
						items
					};

					if (config.invitable) {
						items.push({
							caption: Liferay.Language.get('remove'),
							fn() {
								var instance = this;

								var calendarList = instance.get('host');

								calendarList.remove(calendarList.activeItem);

								instance.hide();
							},
							id: 'remove'
						});

						calendarsMenu.on = {
							visibleChange(event) {
								var instance = this;

								if (event.newVal) {
									var calendarList = instance.get('host');

									var calendar = calendarList.activeItem;

									var hiddenItems = [];

									if (
										calendar.get('calendarId') ===
										config.defaultCalendarId
									) {
										hiddenItems.push('remove');
									}

									instance.set('hiddenItems', hiddenItems);
								}
							}
						};
					}

					return calendarsMenu;
				},

				syncCalendarsMap(calendarLists) {
					var instance = this;

					var defaultCalendar = instance.get('defaultCalendar');

					var availableCalendars = {};

					var visibleCalendars = {};

					calendarLists.forEach(calendarList => {
						var calendars = calendarList.get('calendars');

						A.each(calendars, item => {
							var calendarId = item.get('calendarId');

							availableCalendars[calendarId] = item;

							if (item.get('visible')) {
								visibleCalendars[calendarId] = item;
							}

							if (item.get('defaultCalendar')) {
								var calendarResourceId = item.get(
									'calendarResourceId'
								);

								if (
									calendarResourceId ==
										instance.get(
											'groupCalendarResourceId'
										) &&
									item.get('permissions').MANAGE_BOOKINGS
								) {
									defaultCalendar = item;
								}

								if (
									defaultCalendar == null &&
									calendarResourceId ==
										instance.get('userCalendarResourceId')
								) {
									defaultCalendar = item;
								}

								if (
									defaultCalendar == null &&
									calendarResourceId ==
										instance.get(
											'groupCalendarResourceId'
										) &&
									item.get('permissions').VIEW_BOOKING_DETAILS
								) {
									defaultCalendar = item;
								}
							}
						});
					});

					instance.set('availableCalendars', availableCalendars);
					instance.set('visibleCalendars', visibleCalendars);
					instance.set('defaultCalendar', defaultCalendar);

					return availableCalendars;
				}
			}
		});

		Liferay.CalendarContainer = CalendarContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'liferay-portlet-base']
	}
);
