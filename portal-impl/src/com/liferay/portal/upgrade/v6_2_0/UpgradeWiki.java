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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Roberto Díaz
 */
public class UpgradeWiki extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"36"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		// Email Subscription Subject

		portletPreferences = upgradeSubscriptionSubject(
			"mailPageAddedSubject", "mailPageAddedSubjectPrefix",
			portletPreferences);

		portletPreferences = upgradeSubscriptionSubject(
			"mailPageUpdatedSubject", "mailPageUpdatedSubjectPrefix",
			portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected PortletPreferences upgradeSubscriptionSubject(
			String valueKey, String oldValueKey,
			PortletPreferences portletPreferences)
		throws Exception {

		String subjectPrefixValue = GetterUtil.getString(
			portletPreferences.getValue(oldValueKey, StringPool.BLANK));

		if (Validator.isNotNull(subjectPrefixValue)) {
			String subjectValue = subjectPrefixValue;

			if (!subjectPrefixValue.contains("[$PAGE_TITLE$]")) {
				subjectValue = subjectValue.concat(" [$PAGE_TITLE$]");
			}

			portletPreferences.setValue(valueKey, subjectValue);
		}

		portletPreferences.reset(oldValueKey);

		return portletPreferences;
	}

}