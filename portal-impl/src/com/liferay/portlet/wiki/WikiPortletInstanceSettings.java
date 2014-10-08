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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class WikiPortletInstanceSettings {

	public static final String[] ALL_KEYS = {
		"displayStyle", "displayStyleGroupId", "hiddenNodes", "rssDelta",
		"rssDisplayStyle", "rssFeedType", "visibleNodes",
		"enableCommentRatings", "enableComments", "enablePageRatings",
		"enableRelatedAssets", "enableRss", "hiddenNodes", "visibleNodes"
	};

	public static WikiPortletInstanceSettings getInstance(
			Layout layout, String portletId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		return new WikiPortletInstanceSettings(settings);
	}

	public static WikiPortletInstanceSettings getInstance(
			Layout layout, String portletId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		return new WikiPortletInstanceSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	public WikiPortletInstanceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getDisplayStyle() {
		return _typedSettings.getValue("displayStyle");
	}

	public long getDisplayStyleGroupId(long defaultDisplayStyleGroupId) {
		return _typedSettings.getLongValue(
			"displayStyleGroupId", defaultDisplayStyleGroupId);
	}

	public String[] getHiddenNodes() {
		return _typedSettings.getValues("hiddenNodes");
	}

	public int getRssDelta() {
		return _typedSettings.getIntegerValue("rssDelta");
	}

	public String getRssDisplayStyle() {
		return _typedSettings.getValue("rssDisplayStyle");
	}

	public String getRssFeedType() {
		return _typedSettings.getValue("rssFeedType");
	}

	public String[] getVisibleNodes() {
		return _typedSettings.getValues("visibleNodes");
	}

	public boolean isEnableCommentRatings() {
		return _typedSettings.getBooleanValue("enableCommentRatings");
	}

	public boolean isEnableComments() {
		return _typedSettings.getBooleanValue("enableComments");
	}

	public boolean isEnablePageRatings() {
		return _typedSettings.getBooleanValue("enablePageRatings");
	}

	public boolean isEnableRelatedAssets() {
		return _typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean isEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return _typedSettings.getBooleanValue("enableRss");
	}

	public void setHiddenNodes(String[] hiddenNodes) {
		_typedSettings.setValues("hiddenNodes", hiddenNodes);
	}

	public void setVisibleNodes(String[] visibleNodes) {
		_typedSettings.setValues("visibleNodes", visibleNodes);
	}

	public void store() throws IOException, ValidatorException {
		Settings settings = _typedSettings.getWrappedSettings();

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.store();
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add("displayStyle", PropsKeys.WIKI_DISPLAY_STYLE);
		fallbackKeys.add(
			"enableComments", PropsKeys.WIKI_PAGE_COMMENTS_ENABLED);
		fallbackKeys.add(
			"enableCommentRatings", PropsKeys.WIKI_COMMENT_RATINGS_ENABLED);
		fallbackKeys.add(
			"enablePageRatings", PropsKeys.WIKI_PAGE_RATINGS_ENABLED);
		fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.WIKI_RELATED_ASSETS_ENABLED);
		fallbackKeys.add("enableRss", PropsKeys.WIKI_RSS_ENABLED);
		fallbackKeys.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		fallbackKeys.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		fallbackKeys.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);

		return fallbackKeys;
	}

	private static final String[] _MULTI_VALUED_KEYS = {
		"hiddenNodes", "visibleNodes"
	};

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			PortletKeys.WIKI, _getFallbackKeys(), _MULTI_VALUED_KEYS);
		settingsFactory.registerSettingsMetadata(
			PortletKeys.WIKI_ADMIN, _getFallbackKeys(), _MULTI_VALUED_KEYS);
		settingsFactory.registerSettingsMetadata(
			PortletKeys.WIKI_DISPLAY, _getFallbackKeys(), _MULTI_VALUED_KEYS);
	}

	private final TypedSettings _typedSettings;

}