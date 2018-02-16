AUI.add(
	'liferay-kaleo-designer-remote-services',
	function(A) {
		var KaleoDesignerRemoteServices = {
			getRole: function(roleId, callback) {
				var instance = this;

				instance._invokeResourceURL(
					{
						callback: callback,
						queryParameters: {
							roleIds: roleId
						},
						resourceId: 'roles'
					}
				);
			},

			getScriptLanguages: function(callback) {
				var instance = this;

				instance._invokeResourceURL(
					{
						callback: callback,
						queryParameters: {},
						resourceId: 'scriptLanguages'
					}
				);
			},

			getUser: function(userId, callback) {
				var instance = this;

				instance._invokeResourceURL(
					{
						callback: callback,
						queryParameters: {
							userIds: userId
						},
						resourceId: 'users'
					}
				);
			},

			_invokeResourceURL: function(params) {
				var instance = this;

				var url = Liferay.PortletURL.createResourceURL();

				url.setParameters(params.queryParameters);
				url.setPortletId('com_liferay_portal_workflow_kaleo_designer_web_portlet_KaleoDesignerPortlet');
				url.setResourceId(params.resourceId);

				A.io.request(
					url.toString(),
					{
						dataType: 'JSON',
						on: {
							success: function() {
								params.callback(this.get('responseData'));
							}
						}
					}
				);
			}
		};

		Liferay.KaleoDesignerRemoteServices = KaleoDesignerRemoteServices;
	},
	'',
	{
		requires: ['aui-io', 'liferay-portlet-url']
	}
);