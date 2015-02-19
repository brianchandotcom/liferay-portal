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

package com.liferay.wiki.settings;

import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

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

	public static final String[] MULTI_VALUED_KEYS = {
		"hiddenNodes", "visibleNodes"
	};

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

	public boolean isEnablePageComments() {
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

	private final TypedSettings _typedSettings;

}