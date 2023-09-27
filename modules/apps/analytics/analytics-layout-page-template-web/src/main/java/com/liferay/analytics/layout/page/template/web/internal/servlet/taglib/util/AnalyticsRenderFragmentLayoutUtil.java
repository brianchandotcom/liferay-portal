/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.layout.page.template.web.internal.servlet.taglib.util;

import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Cristina González
 */
public class AnalyticsRenderFragmentLayoutUtil {

	public static AnalyticsAssetType getAnalyticsAssetType(String className) {
		return _analyticsAssetTypes.get(className);
	}

	public static boolean isTrackeable(
		LayoutDisplayPageObjectProvider layoutDisplayPageObjectProvider) {

		if ((layoutDisplayPageObjectProvider == null) ||
			Validator.isNull(
				_analyticsAssetTypes.get(
					layoutDisplayPageObjectProvider.getClassName()))) {

			return false;
		}

		return true;
	}

	public static class AnalyticsAssetType<T> {

		public AnalyticsAssetType(
			Map<String, Function<T, String>> attributes, String type) {

			_attributes = attributes;
			_type = type;
		}

		public AnalyticsAssetType(String type) {
			this(Collections.emptyMap(), type);
		}

		public Map<String, Function<T, String>> getAttributes() {
			return _attributes;
		}

		public String getType() {
			return _type;
		}

		private Map<String, Function<T, String>> _attributes;
		private String _type;

	}

	private static final Map<String, AnalyticsAssetType> _analyticsAssetTypes =
		HashMapBuilder.put(
			"com.liferay.blogs.model.BlogsEntry", new AnalyticsAssetType("blog")
		).put(
			"com.liferay.journal.model.JournalArticle",
			new AnalyticsAssetType("web-content")
		).put(
			"com.liferay.portal.kernel.repository.model.FileEntry",
			new AnalyticsAssetType(
				HashMapBuilder.<String, Function<FileEntry, String>>put(
					"data-analytics-asset-action", fileEntry -> "preview"
				).put(
					"data-analytics-asset-version",
					fileEntry -> fileEntry.getVersion()
				).build(),
				"file")
		).build();

}