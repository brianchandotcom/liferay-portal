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

package com.liferay.analytics.layout.page.template.web.internal.servlet.taglib.util;

import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Cristina González
 */
public class AnalyticsRenderFragmentLayoutUtil {

	public static String getAnalyticsAssetType(String className) {
		return _analyticsCloudAssetTypes.get(className);
	}

	public static boolean isTrackeable(
		ClassNameLocalService classNameLocalService,
		LayoutDisplayPageObjectProvider layoutDisplayPageObjectProvider) {

		if (layoutDisplayPageObjectProvider == null) {
			return false;
		}

		try {
			ClassName className = classNameLocalService.getClassName(
				layoutDisplayPageObjectProvider.getClassNameId());

			if (Validator.isNull(
					_analyticsCloudAssetTypes.get(className.getClassName()))) {

				return false;
			}

			return true;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsRenderFragmentLayoutUtil.class);

	private static final Map<String, String> _analyticsCloudAssetTypes =
		HashMapBuilder.put(
			"com.liferay.blogs.model.BlogsEntry", "blog"
		).build();

}