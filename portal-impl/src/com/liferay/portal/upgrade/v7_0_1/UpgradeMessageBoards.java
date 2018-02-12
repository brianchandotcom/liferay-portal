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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortletKeys;
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
				"priorities" + CharPool.UNDERLINE +
					LanguageUtil.getLanguageId(availableLocale);

			String[] oldThreadPriorities = portletPreferences.getValues(
				key, StringPool.EMPTY_ARRAY);

			if (ArrayUtil.isEmpty(oldThreadPriorities)) {
				continue;
			}

			String[] newThreadPriorities =
				new String[oldThreadPriorities.length];

			for (int i = 0; i < oldThreadPriorities.length; i++) {
				newThreadPriorities[i] = StringUtil.replace(
					oldThreadPriorities[i], CharPool.COMMA, CharPool.PIPE);
			}

			portletPreferences.setValues(key, newThreadPriorities);
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