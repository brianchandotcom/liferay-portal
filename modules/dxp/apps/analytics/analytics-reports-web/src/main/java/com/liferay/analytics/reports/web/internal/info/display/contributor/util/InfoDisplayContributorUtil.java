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

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class InfoDisplayContributorUtil {

	public static InfoDisplayObjectProvider<?> getInfoDisplayObjectProvider(
		HttpServletRequest httpServletRequest,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Portal portal) {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider != null) {
			return _getInfoDisplayObjectProvider(
				layoutDisplayPageObjectProvider);
		}

		InfoDisplayContributor<?> infoDisplayContributor =
			infoDisplayContributorTracker.getInfoDisplayContributor(
				portal.getClassName(
					ParamUtil.getLong(httpServletRequest, "classNameId")));

		try {
			return infoDisplayContributor.getInfoDisplayObjectProvider(
				ParamUtil.getLong(httpServletRequest, "classPK"));
		}
		catch (Exception exception) {
			_log.error("Unable to get info display object provider", exception);
		}

		return null;
	}

	private static InfoDisplayObjectProvider<?> _getInfoDisplayObjectProvider(
		LayoutDisplayPageObjectProvider layoutDisplayPageObjectProvider) {

		return new InfoDisplayObjectProvider() {

			@Override
			public long getClassNameId() {
				return layoutDisplayPageObjectProvider.getClassNameId();
			}

			@Override
			public long getClassPK() {
				return layoutDisplayPageObjectProvider.getClassPK();
			}

			@Override
			public long getClassTypeId() {
				return layoutDisplayPageObjectProvider.getClassTypeId();
			}

			@Override
			public String getDescription(Locale locale) {
				return layoutDisplayPageObjectProvider.getDescription(locale);
			}

			@Override
			public Object getDisplayObject() {
				return layoutDisplayPageObjectProvider.getDisplayObject();
			}

			@Override
			public long getGroupId() {
				return layoutDisplayPageObjectProvider.getGroupId();
			}

			@Override
			public String getKeywords(Locale locale) {
				return layoutDisplayPageObjectProvider.getKeywords(locale);
			}

			@Override
			public String getTitle(Locale locale) {
				return layoutDisplayPageObjectProvider.getTitle(locale);
			}

			@Override
			public String getURLTitle(Locale locale) {
				return layoutDisplayPageObjectProvider.getURLTitle(locale);
			}

		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoDisplayContributorUtil.class);

}