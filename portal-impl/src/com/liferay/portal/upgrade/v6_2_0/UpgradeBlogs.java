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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.v6_2_0.util.BlogsEntryTable;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.RSSUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Sergio González
 * @author Eduardo Garcia
 */
public class UpgradeBlogs extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		updateEntries();
		updatePortletPreferences();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"33"};
	}

	protected void updateEntries() throws Exception {
		try {
			runSQL("alter_column_type BlogsEntry description STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				BlogsEntryTable.TABLE_NAME, BlogsEntryTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(BlogsEntryTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(BlogsEntryTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String rssFormat = GetterUtil.getString(
			portletPreferences.getValue("rssFormat", null));

		if (Validator.isNotNull(rssFormat)) {
			String rssFormatType = RSSUtil.getFormatType(rssFormat);
			double rssFormatVersion = RSSUtil.getFormatVersion(rssFormat);

			String rssFeedType = RSSUtil.getFeedType(
				rssFormatType, rssFormatVersion);

			portletPreferences.setValue("rssFeedType", rssFeedType);
		}

		portletPreferences.reset("rssFormat");

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}