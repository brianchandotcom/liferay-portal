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
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.info.item.InfoItemReference;
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

	public Clipboard getClipboard();

	public List<ContentDashboardItemAction> getContentDashboardItemActions(
		HttpServletRequest httpServletRequest,
		ContentDashboardItemAction.Type... types);

	public ContentDashboardItemSubtype getContentDashboardItemSubtype();

	public Date getCreateDate();

	public ContentDashboardItemAction getDefaultContentDashboardItemAction(
		HttpServletRequest httpServletRequest);

	public Locale getDefaultLocale();

	public String getDescription(Locale locale);

	public InfoItemReference getInfoItemReference();

	public Date getModifiedDate();

	public Preview getPreview();

	public String getScopeName(Locale locale);

	public Map<String, Object> getSpecificInformation(Locale locale);

	public String getTitle(Locale locale);

	public String getTypeLabel(Locale locale);

	public long getUserId();

	public String getUserName();

	public List<Version> getVersions(Locale locale);

	public boolean isViewable(HttpServletRequest httpServletRequest);

	public static class Clipboard {

		public static final Clipboard EMPTY = new Clipboard(null, null);

		public Clipboard(String name, String url) {
			_name = name;
			_url = url;
		}

		public String getName() {
			return _name;
		}

		public String getUrl() {
			return _url;
		}

		public JSONObject toJSONObject() {
			return JSONUtil.put(
				"name", getName()
			).put(
				"url", getUrl()
			);
		}

		private final String _name;
		private final String _url;

	}

	public static class Preview {

		public static final Preview EMPTY = new Preview(null, null);

		public Preview(String imageURL, String url) {
			_imageURL = imageURL;
			_url = url;
		}

		public String getImageURL() {
			return _imageURL;
		}

		public String getUrl() {
			return _url;
		}

		public JSONObject toJSONObject() {
			return JSONUtil.put(
				"imageURL", getImageURL()
			).put(
				"url", getUrl()
			);
		}

		private final String _imageURL;
		private final String _url;

	}

	public static class Version {

		public Version(String label, String style, String version) {
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

		public String getVersion() {
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
		private final String _version;

	}

}