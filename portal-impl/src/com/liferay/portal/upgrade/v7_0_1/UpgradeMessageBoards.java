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

package com.liferay.portal.upgrade.v7_0_1;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Roberto Díaz
 */
public class UpgradeMessageBoards extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			PortletKeys.MESSAGE_BOARDS, PortletKeys.MESSAGE_BOARDS_ADMIN
		};
	}

	protected void upgradeLocalizedThreadPriorities(
			PortletPreferences portletPreferences)
		throws Exception {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		for (Locale availableLocale : availableLocales) {
			String key =
				"priorities" + StringPool.UNDERLINE +
					LanguageUtil.getLanguageId(availableLocale);

			String[] threadPriorities = portletPreferences.getValues(
				key, StringPool.EMPTY_ARRAY);

			if (ArrayUtil.isNotEmpty(threadPriorities)) {
				String[] upgradedThreadPriorities =
					new String[threadPriorities.length];

				for (int i = 0; i < threadPriorities.length; i++) {
					String[] parts = StringUtil.split(threadPriorities[i]);

					upgradedThreadPriorities[i] = StringUtil.merge(
						parts, StringPool.PIPE);
				}

				portletPreferences.setValues(key, upgradedThreadPriorities);
			}
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

		upgradeLocalizedThreadPriorities(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}