/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.util;

import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Eduardo García
 */
public class AsahUtil {

	public static boolean isSkipAsahEvent(
			AnalyticsSettingsManager analyticsSettingsManager, long companyId,
			long groupId)
		throws Exception {

		if (!analyticsSettingsManager.isSiteIdSynced(companyId, groupId)) {
			return true;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext != null) &&
			!GetterUtil.getBoolean(
				serviceContext.getAttribute("updateAsah"), true)) {

			return true;
		}

		return false;
	}

	private AsahUtil() {
	}

}