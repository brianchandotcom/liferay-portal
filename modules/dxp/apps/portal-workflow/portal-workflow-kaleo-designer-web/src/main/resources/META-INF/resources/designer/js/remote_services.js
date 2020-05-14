/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

AUI.add(
	'liferay-kaleo-designer-remote-services',
	(A) => {
		var KaleoDesignerRemoteServices = {
			_invokeResourceURL(params) {
				var url = Liferay.PortletURL.createResourceURL();

				url.setParameters(params.queryParameters);
				url.setPortletId(
					'com_liferay_portal_workflow_kaleo_designer_web_portlet_KaleoDesignerPortlet'
				);
				url.setResourceId(params.resourceId);

				A.io.request(url.toString(), {
					dataType: 'JSON',
					on: {
						success() {
							params.callback(this.get('responseData'));
						},
					},
					sync: params.sync,
				});
			},

			getRole(roleId, callback) {
				var instance = this;

				instance._invokeResourceURL({
					callback,
					queryParameters: {
						roleIds: roleId,
					},
					resourceId: 'roles',
					sync: false,
				});
			},

			getScriptLanguages(callback) {
				var instance = this;

				instance._invokeResourceURL({
					callback,
					queryParameters: {},
					resourceId: 'scriptLanguages',
					sync: true,
				});
			},

			getUser(emailAddress, screenName, userId, callback) {
				var instance = this;

				instance._invokeResourceURL({
					callback,
					queryParameters: {
						emailAddresses: emailAddress,
						screenNames: screenName,
						userIds: userId,
					},
					resourceId: 'users',
					sync: false,
				});
			},
		};

		Liferay.KaleoDesignerRemoteServices = KaleoDesignerRemoteServices;
	},
	'',
	{
		requires: ['aui-io', 'liferay-portlet-url'],
	}
);
