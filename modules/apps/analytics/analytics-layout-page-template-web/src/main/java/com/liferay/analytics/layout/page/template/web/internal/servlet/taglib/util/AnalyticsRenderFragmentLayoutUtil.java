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
		LayoutDisplayPageObjectProvider layoutDisplayPageObjectProvider) {

		if ((layoutDisplayPageObjectProvider == null) ||
			Validator.isNull(
				_analyticsCloudAssetTypes.get(
					layoutDisplayPageObjectProvider.getClassName()))) {

			return false;
		}

		return true;
	}

	private static final Map<String, String> _analyticsCloudAssetTypes =
		HashMapBuilder.put(
			"com.liferay.blogs.model.BlogsEntry", "blog"
		).put(
			"com.liferay.journal.model.JournalArticle", "web-content"
		).build();

}