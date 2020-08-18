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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public interface ContentDashboardItem<T> {

	public List<AssetCategory> getAssetCategories();

	public List<AssetCategory> getAssetCategories(long vocabularyId);

	public List<AssetTag> getAssetTags();

	public List<Locale> getAvailableLocales();

	public String getClassName();

	public Long getClassPK();

	public List<ContentDashboardItemAction> getContentDashboardItemActions(
		HttpServletRequest httpServletRequest,
		ContentDashboardItemAction.Type... types);

	public ContentDashboardItemType getContentDashboardItemType();

	public Date getCreateDate();

	public Map<String, Object> getData(Locale locale);

	public Locale getDefaultLocale();

	public Date getModifiedDate();

	public String getScopeName(Locale locale);

	public String getTitle(Locale locale);

	public long getUserId();

	public String getUserName();

	public String getUserPortraitURL(HttpServletRequest httpServletRequest);

	public List<Version> getVersions(Locale locale);

	public boolean isViewable(HttpServletRequest httpServletRequest);

	public static class Version {

		public Version(String label, String style, double version) {
			_label = label;
			_style = style;
			_version = version;
		}

		public String getLabel() {
			return _label;
		}

		public String getStyle() {
			return _style;
		}

		public double getVersion() {
			return _version;
		}

		public JSONObject toJSONObject() {
			return JSONUtil.put(
				"statusLabel", getLabel()
			).put(
				"statusStyle", getStyle()
			).put(
				"version", getVersion()
			);
		}

		private final String _label;
		private final String _style;
		private final double _version;

	}

}