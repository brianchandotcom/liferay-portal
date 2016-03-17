AUI.add(
	'liferay-calendar-session-listener',
	function(A) {
		var CalendarSessionListener = A.Component.create(
			{
				ATTRS: {
					calendars: {
						value: []
					},

					scheduler: {
						value: null
					}
				},

				NAME: 'calendar-session-listener',

				prototype: {
					initializer: function() {
						var instance = this;

						Liferay.on('sessionExpired', A.bind(instance._onSessionExpired, instance));
					},

					_disableCalendars: function() {
						var instance = this;

						var calendars = instance.get('calendars');

						A.Object.each(
							calendars,
							function(calendar) {
								var permissions = calendar.get('permissions');

								permissions.DELETE = false;
								permissions.MANAGE_BOOKINGS = false;
								permissions.UPDATE = false;
								permissions.PERMISSIONS = false;
							}
						);
					},

					_disableEvents: function() {
						var instance = this;

						var scheduler = instance.get('scheduler');

						scheduler.getEvents().forEach(
							function(event) {
								event.set('disabled', true);
							}
						);
					},

					_disableScheduler: function() {
						var instance = this;

						var addEventButtons = A.all('.calendar-add-event-btn');

						var scheduler = instance.get('scheduler');

						addEventButtons.set('disabled', true);

						scheduler.set('eventRecorder', null);
					},

					_onSessionExpired: function() {
						var instance = this;

						instance._disableCalendars();

						instance._disableScheduler();

						instance._disableEvents();
					}
				}
			}
		);

		Liferay.CalendarSessionListener = CalendarSessionListener;
	},
	'',
	{
		requires: ['aui-base', 'aui-component']
	}
);