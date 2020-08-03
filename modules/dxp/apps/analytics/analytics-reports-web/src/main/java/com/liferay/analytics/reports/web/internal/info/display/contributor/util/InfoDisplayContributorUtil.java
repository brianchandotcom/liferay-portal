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

package com.liferay.analytics.reports.web.internal.info.display.contributor.util;

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class InfoDisplayContributorUtil {

	public static InfoDisplayObjectProvider<?> getInfoDisplayObjectProvider(
		HttpServletRequest httpServletRequest,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Portal portal) {

		InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
			(InfoDisplayObjectProvider<?>)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		if (infoDisplayObjectProvider != null) {
			return infoDisplayObjectProvider;
		}

		InfoDisplayContributor<?> infoDisplayContributor =
			infoDisplayContributorTracker.getInfoDisplayContributor(
				portal.getClassName(
					ParamUtil.getLong(httpServletRequest, "classNameId")));

		try {
			infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(
					ParamUtil.getLong(httpServletRequest, "classPK"));
		}
		catch (Exception exception) {
			_log.error("Unable to get info display object provider", exception);
		}

		return infoDisplayObjectProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoDisplayContributorUtil.class);

}