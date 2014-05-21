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
import com.liferay.portlet.layoutprototypes.LayoutSetPrototypeCreator;

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
			LayoutSetPrototypeCreator layoutSetPrototypeCreator)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypeCreator.addLayoutSetPrototype(
				"layout-set-prototype-intranet-site-title",
				"layout-set-prototype-intranet-site-description");

		if (layoutSetPrototype == null) {
			return;
		}

		// Home layout

		Layout layout = layoutSetPrototypeCreator.addLayout(
			layoutSetPrototype, "home", "/home", "2_columns_i");

		LayoutConfigurator layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet(PortletKeys.ACTIVITIES, "column-1");

		layoutConfigurator.addPortlet(
			_getPrivateHomeSearchPortletSetup(), PortletKeys.SEARCH,
			"column-2");

		layoutConfigurator.addPortlet(
			_getLanguagePortletSetup(), PortletKeys.LANGUAGE, "column-2");

		layoutConfigurator.addPortlet(
			_getHomeAssetPublisherPortletSetup(), PortletKeys.ASSET_PUBLISHER,
			"column-2");

		// Documents layout

		layout = layoutSetPrototypeCreator.addLayout(
			layoutSetPrototype, "documents-and-media", "/documents",
			"1_column");

		layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet(
			_getDocumentLibraryPortletSetup(), PortletKeys.DOCUMENT_LIBRARY,
			"column-1");

		layoutConfigurator.addPortlet(
			_getDocumentsAssetPublisherPortletSetup(),
			PortletKeys.ASSET_PUBLISHER, "column-2");

		// News layout

		layout = layoutSetPrototypeCreator.addLayout(
			layoutSetPrototype, "news", "/news", "2_columns_iii");

		layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet(
			_getNewsColumn1RssPortletSetup(), PortletKeys.RSS, "column-1");

		layoutConfigurator.addPortlet(
			_getNewsColumn2RssPortletSetup(), PortletKeys.RSS, "column-2");
	}

	protected void addPublicSite(
			LayoutSetPrototypeCreator layoutSetPrototypeCreator)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypeCreator.addLayoutSetPrototype(
				"layout-set-prototype-community-site-title",
				"layout-set-prototype-community-site-description");

		if (layoutSetPrototype == null) {
			return;
		}

		// Home layout

		Layout layout = layoutSetPrototypeCreator.addLayout(
			layoutSetPrototype, "home", "/home", "2_columns_iii");

		LayoutConfigurator layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet(PortletKeys.MESSAGE_BOARDS, "column-1");

		layoutConfigurator.addPortlet(
			_getPublicHomeSearchPortletSetup(), PortletKeys.SEARCH, "column-2");

		layoutConfigurator.addPortlet(PortletKeys.POLLS_DISPLAY, "column-2");

		layoutConfigurator.addPortlet(PortletKeys.USER_STATISTICS, "column-2");

		layoutConfigurator.addPortlet(
			_getPrivateHomeAssetPublisherPortletSetup(),
			PortletKeys.ASSET_PUBLISHER, "column-2");

		// Wiki layout

		layout = layoutSetPrototypeCreator.addLayout(
			layoutSetPrototype, "wiki", "/wiki", "2_columns_iii");

		layoutConfigurator.addPortlet(PortletKeys.WIKI, "column-1");

		layoutConfigurator.addPortlet(
			PortletKeys.ASSET_CATEGORIES_NAVIGATION, "column-2");

		layoutConfigurator.addPortlet(PortletKeys.TAGS_CLOUD, "column-2");
	}

	protected void doRun(long companyId) throws Exception {
		LayoutSetPrototypeCreator layoutSetPrototypeCreator =
			new LayoutSetPrototypeCreator(companyId);

		addPublicSite(layoutSetPrototypeCreator);
		addPrivateSite(layoutSetPrototypeCreator);
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