/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-kaleo-designer-remote-services',
	(A) => {
		const KaleoDesignerRemoteServices = {
			_invokeResourceURL(params) {
				const url = Liferay.PortletURL.createResourceURL();

				url.setParameters(params.queryParameters);
				url.setPortletId(
					'com_liferay_portal_workflow_kaleo_designer_web_portlet_KaleoDesignerPortlet'
				);
				url.setResourceId(params.resourceId);

				// eslint-disable-next-line @liferay/aui/no-io
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
				const instance = this;

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
				const instance = this;

				instance._invokeResourceURL({
					callback,
					queryParameters: {},
					resourceId: 'scriptLanguages',
					sync: true,
				});
			},

			getUser(emailAddress, screenName, userId, callback) {
				const instance = this;

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
