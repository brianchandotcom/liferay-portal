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

import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class LayoutDisplayPageObjectProviderUtil {

	public static LayoutDisplayPageObjectProvider<?>
		getLayoutDisplayPageObjectProvider(
			HttpServletRequest httpServletRequest,
			LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker,
			Portal portal) {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider != null) {
			return layoutDisplayPageObjectProvider;
		}

		String className = portal.getClassName(
			ParamUtil.getLong(httpServletRequest, "classNameId"));

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderTracker.getLayoutDisplayPageProvider(
				className);

		try {
			layoutDisplayPageObjectProvider =
				layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
					new InfoItemReference(
						className,
						ParamUtil.getLong(httpServletRequest, "classPK")));
		}
		catch (Exception exception) {
			_log.error("Unable to get info display object provider", exception);
		}

		return layoutDisplayPageObjectProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutDisplayPageObjectProviderUtil.class);

}