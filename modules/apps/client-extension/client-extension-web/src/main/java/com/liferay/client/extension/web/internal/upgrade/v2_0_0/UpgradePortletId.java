/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId
	extends com.liferay.client.extension.web.internal.upgrade.v1_0_0.
				UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return ArrayUtil.append(
			new String[][] {
				{
					"com_liferay_remote_app_admin_web_portlet_" +
						"RemoteAppAdminPortlet",
					"com_liferay_client_extension_web_internal_" +
						"portlet_ClientExtensionAdminPortlet"
				}
			},
			getRenamePortletIdsArray(
				connection,
				"com_liferay_remote_app_web_internal_portlet_" +
					"RemoteAppEntryPortlet_",
				"com_liferay_client_extension_web_internal_" +
					"portlet_ClientExtensionEntryPortlet_"));
	}

}