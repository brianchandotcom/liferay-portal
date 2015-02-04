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

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.RSSUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo Garcia
 * @author Jorge Ferrer
 */
public class UpgradeAssetPublisher extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {"101_INSTANCE_%"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeRss(portletPreferences);
		upgradeScopeIds(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeRss(PortletPreferences portletPreferences)
		throws Exception {

		String rssFormat = GetterUtil.getString(
			portletPreferences.getValue("rssFormat", null));

		if (Validator.isNotNull(rssFormat)) {
			portletPreferences.setValue(
				"rssFeedType",
				RSSUtil.getFeedType(
					RSSUtil.getFormatType(rssFormat),
					RSSUtil.getFormatVersion(rssFormat)));
		}

		portletPreferences.reset("rssFormat");
	}

	protected void upgradeScopeIds(PortletPreferences portletPreferences)
		throws Exception {

		String defaultScope = GetterUtil.getString(
			portletPreferences.getValue("defaultScope", null));

		if (Validator.isNull(defaultScope)) {
			return;
		}

		if (defaultScope.equals("true")) {
			portletPreferences.setValues(
				"scopeIds", new String[] {"Group_default"});
		}
		else if (!defaultScope.equals("false")) {
			portletPreferences.setValues(
				"scopeIds", new String[] {defaultScope});
		}

		portletPreferences.reset("defaultScope");
	}

}