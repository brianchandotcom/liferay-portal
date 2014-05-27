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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.BlogsSettings;
import com.liferay.portlet.blogs.util.BlogsConstants;
import com.liferay.portlet.bookmarks.BookmarksSettings;
import com.liferay.portlet.bookmarks.util.BookmarksConstants;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.DLSettings;
import com.liferay.portlet.documentlibrary.util.DLConstants;
import com.liferay.portlet.messageboards.MBSettings;
import com.liferay.portlet.messageboards.util.MBConstants;
import com.liferay.portlet.shopping.ShoppingSettings;
import com.liferay.portlet.shopping.util.ShoppingConstants;
import com.liferay.portlet.wiki.WikiSettings;
import com.liferay.portlet.wiki.util.WikiConstants;

/**
 * @author Iván Zaera
 */
public class SettingsFactoryStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		initSettingsFactory();
	}

	protected void initSettingsFactory() {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerFallbackKeys(
			BlogsConstants.SERVICE_NAME, BlogsSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			BookmarksConstants.SERVICE_NAME, BookmarksSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			MBConstants.SERVICE_NAME, MBSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			ShoppingConstants.SERVICE_NAME, ShoppingSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			WikiConstants.SERVICE_NAME, WikiSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			DLConstants.SERVICE_NAME, DLSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			PortletKeys.DOCUMENT_LIBRARY,
			DLPortletInstanceSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			PortletKeys.DOCUMENT_LIBRARY_ADMIN,
			DLPortletInstanceSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			PortletKeys.DOCUMENT_LIBRARY_DISPLAY,
			DLPortletInstanceSettings.FALLBACK_KEYS);

		settingsFactory.registerFallbackKeys(
			PortletKeys.MEDIA_GALLERY_DISPLAY,
			DLPortletInstanceSettings.FALLBACK_KEYS);
	}

}