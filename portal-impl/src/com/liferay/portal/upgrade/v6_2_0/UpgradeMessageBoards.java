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
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.RSSUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.util.UpgradeUUIDUtil;

import java.sql.SQLException;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo Garcia
 * @author Daniel Kocsis
 */
public class UpgradeMessageBoards extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		updateUUID();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"19"};
	}

	protected void updateUUID() throws SQLException {
		UpgradeUUIDUtil.upgradeUUID(connection, "MBBan", "banId");
		UpgradeUUIDUtil.upgradeUUID(connection, "MBDiscussion", "discussionId");
		UpgradeUUIDUtil.upgradeUUID(connection, "MBThread", "threadId");
		UpgradeUUIDUtil.upgradeUUID(connection, "MBThreadFlag", "threadFlagId");
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
			String rssFeedType = RSSUtil.getFeedType(
				RSSUtil.getFormatType(rssFormat),
				RSSUtil.getFormatVersion(rssFormat));

			portletPreferences.setValue("rssFeedType", rssFeedType);
		}

		portletPreferences.reset("rssFormat");

		portletPreferences = upgradeSubscriptionSubject(
			"emailMessageAddedSubject", "emailMessageAddedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionSubject(
			"emailMessageUpdatedSubject", "emailMessageUpdatedSubjectPrefix",
			portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected PortletPreferences upgradeSubscriptionSubject(
			String subjectName, String subjectPrefixName,
			PortletPreferences portletPreferences)
		throws Exception {

		String subjectPrefix = GetterUtil.getString(
			portletPreferences.getValue(subjectPrefixName, StringPool.BLANK));

		if (Validator.isNotNull(subjectPrefix)) {
			String subject = subjectPrefix;

			if (!subjectPrefix.contains("[$MESSAGE_SUBJECT$]")) {
				subject = subject.concat(" [$MESSAGE_SUBJECT$]");
			}

			portletPreferences.setValue(subjectName, subject);
		}

		portletPreferences.reset(subjectPrefixName);

		return portletPreferences;
	}

}