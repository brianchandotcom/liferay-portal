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