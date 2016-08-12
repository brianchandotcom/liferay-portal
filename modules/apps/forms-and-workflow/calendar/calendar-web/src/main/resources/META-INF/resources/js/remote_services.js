AUI.add(
	'liferay-calendar-remote-services',
	function(A) {
		var Lang = A.Lang;

		var isString = Lang.isString;

		var CalendarRemoteServices = A.Base.create(
			'calendar-remote-services',
			A.Base,
			[Liferay.PortletBase],
			{
				invokeActionURL: function(params) {
					var instance = this;

					var url = Liferay.PortletURL.createActionURL();

					url.setName(params.actionName);
					url.setParameters(params.queryParameters);
					url.setPortletId('com_liferay_calendar_web_portlet_CalendarPortlet');

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

				invokeResourceURL: function(params) {
					var instance = this;

					var url = Liferay.PortletURL.createResourceURL();

					url.setParameters(params.queryParameters);
					url.setPortletId('com_liferay_calendar_web_portlet_CalendarPortlet');
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

				invokeService: function(payload, callback) {
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