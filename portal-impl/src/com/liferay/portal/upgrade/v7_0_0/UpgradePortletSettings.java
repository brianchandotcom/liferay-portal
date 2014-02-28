/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 */
public class UpgradePortletSettings extends UpgradeProcess {

	public UpgradePortletSettings() {
		_serviceNames.put(PortletKeys.MESSAGE_BOARDS, MBConstants.SERVICE_NAME);
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (Map.Entry<String, String> entry : _serviceNames.entrySet()) {
			String portletId = entry.getKey();
			String serviceName = entry.getValue();

			upgradePortletSettings(portletId, serviceName);
		}
	}

	protected void upgradePortletSettings(String portletId, String serviceName)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("update PortletPreferences portletId = '");
		sb.append(serviceName);
		sb.append("' where ownerType = ");
		sb.append(PortletKeys.PREFS_OWNER_TYPE_GROUP);
		sb.append(" and portletId like '");
		sb.append(portletId);
		sb.append("'");

		runSQL(sb.toString());
	}

	private Map<String, String> _serviceNames = new HashMap<String, String>();

}