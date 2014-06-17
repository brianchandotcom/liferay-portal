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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sergio González
 */
public class AddDefaultLayoutSetPrototypesAction
	extends BaseDefaultLayoutPrototypesAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected LayoutSet addLayoutSetPrototype(
			long companyId, long defaultUserId, String nameKey,
			String descriptionKey, List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		String name = LanguageUtil.get(LocaleUtil.getDefault(), nameKey);
		String description = LanguageUtil.get(
			LocaleUtil.getDefault(), descriptionKey);

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			String curName = layoutSetPrototype.getName(
				LocaleUtil.getDefault());
			String curDescription = layoutSetPrototype.getDescription(
				LocaleUtil.getDefault());

			if (name.equals(curName) && description.equals(curDescription)) {
				return null;
			}
		}

		Map<Locale, String> nameMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			nameMap.put(locale, LanguageUtil.get(locale, nameKey));
			descriptionMap.put(
				locale, LanguageUtil.get(locale, descriptionKey));
		}

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				defaultUserId, companyId, nameMap, descriptionMap, true, true,
				new ServiceContext());

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		ServiceContext serviceContext = new ServiceContext();

		LayoutLocalServiceUtil.deleteLayouts(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			serviceContext);

		return layoutSetPrototype.getLayoutSet();
	}

	protected void addPrivateSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId,
			"layout-set-prototype-intranet-site-title",
			"layout-set-prototype-intranet-site-description",
			layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "home", "/home", "2_columns_i");

		addPortletId(layout, PortletKeys.ACTIVITIES, "column-1");

		String portletId = addPortletId(layout, PortletKeys.SEARCH, "column-2");

		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		updatePortletSetup(layout, portletId, preferences);

		portletId = addPortletId(layout, PortletKeys.LANGUAGE, "column-2");

		preferences = new HashMap<String, String>();

		preferences.put("displayStyle", "3");

		updatePortletSetup(layout, portletId, preferences);

		portletId = addPortletId(
			layout, PortletKeys.ASSET_PUBLISHER, "column-2");

		preferences = new HashMap<String, String>();

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "recent-content"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		updatePortletSetup(layout, portletId, preferences);

		// Documents layout

		layout = addLayout(
			layoutSet, "documents-and-media", "/documents", "1_column");

		portletId = addPortletId(
			layout, PortletKeys.DOCUMENT_LIBRARY, "column-1");

		preferences = new HashMap<String, String>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		updatePortletSetup(layout, portletId, preferences);

		portletId = addPortletId(
			layout, PortletKeys.ASSET_PUBLISHER, "column-2");

		preferences = new HashMap<String, String>();

		preferences.put("anyAssetType", Boolean.FALSE.toString());

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "upcoming-events"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		updatePortletSetup(layout, portletId, preferences);

		// News layout

		layout = addLayout(layoutSet, "news", "/news", "2_columns_iii");

		portletId = addPortletId(layout, PortletKeys.RSS, "column-1");

		preferences = new HashMap<String, String>();

		preferences.put("expandedEntriesPerFeed", "3");

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "technology-news"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());
		preferences.put(
			"urls", "http://partners.userland.com/nytRss/technology.xml");

		updatePortletSetup(layout, portletId, preferences);

		portletId = addPortletId(layout, PortletKeys.RSS, "column-2");

		preferences = new HashMap<String, String>();

		preferences.put("expandedEntriesPerFeed", "0");

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "liferay-news"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());
		preferences.put(
			"urls", "http://www.liferay.com/en/about-us/news/-/blogs/rss");
		preferences.put("titles", "Liferay Press Releases");

		updatePortletSetup(layout, portletId, preferences);
	}

	protected void addPublicSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId,
			"layout-set-prototype-community-site-title",
			"layout-set-prototype-community-site-description",
			layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "home", "/home", "2_columns_iii");

		addPortletId(layout, PortletKeys.MESSAGE_BOARDS, "column-1");

		String portletId = addPortletId(layout, PortletKeys.SEARCH, "column-2");

		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		updatePortletSetup(layout, portletId, preferences);

		addPortletId(layout, PortletKeys.POLLS_DISPLAY, "column-2");
		addPortletId(layout, PortletKeys.USER_STATISTICS, "column-2");

		portletId = addPortletId(
			layout, PortletKeys.ASSET_PUBLISHER, "column-2");

		preferences = new HashMap<String, String>();

		preferences.put("anyAssetType", Boolean.FALSE.toString());

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			preferences.put(
				"portletSetupTitle_" + locale,
				LanguageUtil.get(locale, "upcoming-events"));
		}

		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		updatePortletSetup(layout, portletId, preferences);

		// Wiki layout

		layout = addLayout(layoutSet, "wiki", "/wiki", "2_columns_iii");

		addPortletId(layout, PortletKeys.WIKI, "column-1");
		addPortletId(
			layout, PortletKeys.ASSET_CATEGORIES_NAVIGATION, "column-2");
		addPortletId(layout, PortletKeys.TAGS_CLOUD, "column-2");
	}

	protected void doRun(long companyId) throws Exception {
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		List<LayoutSetPrototype> layoutSetPrototypes =
			LayoutSetPrototypeLocalServiceUtil.search(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addPublicSite(companyId, defaultUserId, layoutSetPrototypes);
		addPrivateSite(companyId, defaultUserId, layoutSetPrototypes);
	}

}