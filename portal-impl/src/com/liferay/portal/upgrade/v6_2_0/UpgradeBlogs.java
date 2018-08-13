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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.v6_2_0.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.upgrade.v6_2_0.util.BlogsEntryTable;
import com.liferay.portal.upgrade.v6_2_0.util.RSSUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Sergio González
 * @author Eduardo Garcia
 */
public class UpgradeBlogs extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		updateEntries();
		updateStatus();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"33"};
	}

	protected void updateEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alter(
				BlogsEntryTable.class,
				new AlterColumnType("description", "STRING null"));
		}
	}

	protected void updateStatus() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update BlogsEntry set status = " +
					WorkflowConstants.STATUS_APPROVED +
						" where status is null");
		}
	}

	protected void upgradeDisplayStyle(PortletPreferences portletPreferences)
		throws Exception {

		String pageDisplayStyle = GetterUtil.getString(
			portletPreferences.getValue("pageDisplayStyle", null));

		if (Validator.isNotNull(pageDisplayStyle)) {
			portletPreferences.setValue("displayStyle", pageDisplayStyle);
		}

		portletPreferences.reset("pageDisplayStyle");
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeDisplayStyle(portletPreferences);
		upgradeRss(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeRss(PortletPreferences portletPreferences)
		throws Exception {

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
	}

}