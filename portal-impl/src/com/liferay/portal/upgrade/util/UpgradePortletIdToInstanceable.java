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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.impl.LayoutTypePortletImpl;

/**
 * @author Eduardo Garcia
 */
public class UpgradePortletIdToInstanceable extends UpgradePortletId {

	@Override
	protected void doUpgrade() throws Exception {
		String[] portletIds = getPortletIds();

		for (String portletId : portletIds) {
			if (portletId.contains(PortletConstants.INSTANCE_SEPARATOR)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Portlet " + portletId + " is already instanceable");
				}

				continue;
			}

			String instanceId = LayoutTypePortletImpl.generateInstanceId();

			String newPortletId = PortletConstants.assemblePortletId(
				portletId, instanceId);

			updateResourcePermission(portletId, newPortletId, false);
			updateInstanceablePortletPreferences(portletId, newPortletId);
			updateLayouts(portletId, newPortletId);
		}
	}

	protected String[] getPortletIds() {
		return new String[0];
	}

	private static Log _log = LogFactoryUtil.getLog(
		UpgradePortletIdToInstanceable.class);

}