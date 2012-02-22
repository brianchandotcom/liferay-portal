/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Samuel Kong
 * @author Brian Wing Shun Chan
 * @author Edward Shin
 */
public class UpgradeDocumentLibrary extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {"20"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		boolean changed = false;

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String fileEntryColumns = portletPreferences.getValue(
			"fileEntryColumns", StringPool.BLANK);

		if (Validator.isNotNull(fileEntryColumns)) {
			fileEntryColumns = StringUtil.replace(
				fileEntryColumns, "document", "name");

			portletPreferences.setValue("fileEntryColumns", fileEntryColumns);

			changed = true;
		}

		if (changed) {
			return PortletPreferencesFactoryUtil.toXML(portletPreferences);
		}
		else {
			return xml;
		}
	}

}