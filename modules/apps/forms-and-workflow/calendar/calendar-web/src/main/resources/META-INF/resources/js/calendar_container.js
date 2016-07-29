AUI.add(
	'liferay-calendar-container',
	function(A) {
		var Lang = A.Lang;

		var isArray = Lang.isArray;
		var isObject = Lang.isObject;

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var CalendarContainer = A.Component.create(
			{
				ATTRS: {
					availableCalendars: {
						validator: isObject,
						value: {}
					},

					visibleCalendars: {
						validator: isObject,
						value: {}
					},

					defaultCalendar: {
						validator: isObject,
						value: null
					},
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'calendar-container',

				prototype: {
					getCalendar: function(calendarId) {
						var instance = this;

						var availableCalendars = instance.get('availableCalendars');

						return availableCalendars[calendarId];
					}
				}
			}
		);

		Liferay.CalendarContainer = CalendarContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'liferay-portlet-base']
	}
);