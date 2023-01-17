/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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