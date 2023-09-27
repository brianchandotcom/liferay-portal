/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public class ContentDashboardGroupUtil {

	public static String getGroupName(Group group, Locale locale) {
		try {
			String descriptiveName = group.getDescriptiveName(locale);

			if (descriptiveName == null) {
				return group.getName(locale);
			}

			return descriptiveName;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return group.getName(locale);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardGroupUtil.class);

}