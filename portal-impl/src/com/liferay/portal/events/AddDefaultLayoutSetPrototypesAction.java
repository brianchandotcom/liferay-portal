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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.layoutprototypes.LayoutConfigurator;
import com.liferay.portlet.layoutprototypes.LayoutSetPrototypesCreator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public class AddDefaultLayoutSetPrototypesAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void addPrivateSite(
			LayoutSetPrototypesCreator layoutSetPrototypesCreator)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypesCreator.addLayoutSetPrototype(
					"layout-set-prototype-intranet-site-title",
					"layout-set-prototype-intranet-site-description");

		if (layoutSetPrototype == null) {
			return;
		}

		// Home layout

		Layout layout = layoutSetPrototypesCreator.addLayout(
			layoutSetPrototype, "home", "/home", "2_columns_i");

		LayoutConfigurator layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet("column-1", PortletKeys.ACTIVITIES);

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.SEARCH,
			_getPrivateHomeSearchPortletSetup());

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.LANGUAGE, _getLanguagePortletSetup());

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.ASSET_PUBLISHER,
			_getHomeAssetPublisherPortletSetup());

		// Documents layout

		layout = layoutSetPrototypesCreator.addLayout(
			layoutSetPrototype, "documents-and-media", "/documents",
			"1_column");

		layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet(
			"column-1", PortletKeys.DOCUMENT_LIBRARY,
			_getDocumentLibraryPortletSetup());

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.ASSET_PUBLISHER,
			_getDocumentsAssetPublisherPortletSetup());

		// News layout

		layout = layoutSetPrototypesCreator.addLayout(
			layoutSetPrototype, "news", "/news", "2_columns_iii");

		layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet(
			"column-1", PortletKeys.RSS, _getNewsColumn1RssPortletSetup());

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.RSS, _getNewsColumn2RssPortletSetup());
	}

	protected void addPublicSite(
			LayoutSetPrototypesCreator layoutSetPrototypesCreator)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypesCreator.addLayoutSetPrototype(
					"layout-set-prototype-community-site-title",
					"layout-set-prototype-community-site-description");

		if (layoutSetPrototype == null) {
			return;
		}

		// Home layout

		Layout layout = layoutSetPrototypesCreator.addLayout(
			layoutSetPrototype, "home", "/home", "2_columns_iii");

		LayoutConfigurator layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet("column-1", PortletKeys.MESSAGE_BOARDS);

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.SEARCH, _getPublicHomeSearchPortletSetup());

		layoutConfigurator.addPortlet("column-2", PortletKeys.POLLS_DISPLAY);

		layoutConfigurator.addPortlet("column-2", PortletKeys.USER_STATISTICS);

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.ASSET_PUBLISHER,
			_getPrivateHomeAssetPublisherPortletSetup());

		// Wiki layout

		layout = layoutSetPrototypesCreator.addLayout(
			layoutSetPrototype, "wiki", "/wiki", "2_columns_iii");

		layoutConfigurator.addPortlet("column-1", PortletKeys.WIKI);

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.ASSET_CATEGORIES_NAVIGATION);

		layoutConfigurator.addPortlet("column-2", PortletKeys.TAGS_CLOUD);
	}

	protected void doRun(long companyId) throws Exception {
		LayoutSetPrototypesCreator layoutSetPrototypesCreator =
			new LayoutSetPrototypesCreator(companyId);

		addPublicSite(layoutSetPrototypesCreator);
		addPrivateSite(layoutSetPrototypesCreator);
	}

	private Map<String, String> _getDocumentLibraryPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		return preferences;
	}

	private Map<String, String> _getDocumentsAssetPublisherPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("anyAssetType", Boolean.FALSE.toString());

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "upcoming-events"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		return preferences;
	}

	private Map<String, String> _getHomeAssetPublisherPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "recent-content"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		return preferences;
	}

	private Map<String, String> _getLanguagePortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("displayStyle", "3");

		return preferences;
	}

	private Map<String, String> _getNewsColumn1RssPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("expandedEntriesPerFeed", "3");

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "technology-news"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());
		preferences.put(
			"urls", "http://partners.userland.com/nytRss/technology.xml");

		return preferences;
	}

	private Map<String, String> _getNewsColumn2RssPortletSetup() {
		HashMap<String, String> preferences = new HashMap<String, String>();

		preferences.put("expandedEntriesPerFeed", "0");

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "liferay-news"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());
		preferences.put(
			"urls", "http://www.liferay.com/en/about-us/news/-/blogs/rss");
		preferences.put("titles", "Liferay Press Releases");

		return preferences;
	}

	private Map<String, String> _getPrivateHomeAssetPublisherPortletSetup() {
		HashMap<String, String> preferences = new HashMap<String, String>();

		preferences.put("anyAssetType", Boolean.FALSE.toString());

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "upcoming-events"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		return preferences;
	}

	private Map<String, String> _getPrivateHomeSearchPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		return preferences;
	}

	private Map<String, String> _getPublicHomeSearchPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		return preferences;
	}

}