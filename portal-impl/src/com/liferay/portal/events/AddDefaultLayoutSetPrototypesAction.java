/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Sergio González
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

	protected Layout addLayout(
			LayoutSet layoutSet, String name, String friendlyURL,
			String layouteTemplateId)
		throws Exception {

		Group group = layoutSet.getGroup();

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = LayoutLocalServiceUtil.addLayout(
			group.getCreatorUserId(), group.getGroupId(),
			layoutSet.isPrivateLayout(),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, layouteTemplateId, false);

		return layout;
	}

	protected LayoutSet addLayoutSetPrototype(
			long companyId, long defaultUserId, String name, String description,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			String curName = layoutSetPrototype.getName(
				LocaleUtil.getDefault());
			String curDescription = layoutSetPrototype.getDescription();

			if (name.equals(curName) && description.equals(curDescription)) {
				return null;
			}
		}

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				defaultUserId, companyId, nameMap, description, true, true,
				new ServiceContext());

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		ServiceContext serviceContext = new ServiceContext();

		LayoutLocalServiceUtil.deleteLayouts(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			serviceContext);

		return layoutSetPrototype.getLayoutSet();
	}

	protected String addPortletId(
			Layout layout, String portletId, String columnId)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		portletId = layoutTypePortlet.addPortletId(
			0, portletId, columnId, -1, false);

		updateLayout(layout);

		addResourcePermissions(layout, portletId);

		return portletId;
	}

	protected void addPrivateSite(
			long companyId, long defaultUserId, List<LayoutSetPrototype>
			layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId, "Intranet Site",
			"Site with Documents and News", layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "Home", "/home", "2_columns_i");

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

		preferences.put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Recent Content");
		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		updatePortletSetup(layout, portletId, preferences);

		// Documents layout

		layout = addLayout(
			layoutSet, "Documents and Media", "/documents", "1_column");

		portletId = addPortletId(
			layout, PortletKeys.DOCUMENT_LIBRARY, "column-1");

		preferences = new HashMap<String, String>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		updatePortletSetup(layout, portletId, preferences);

		portletId = addPortletId(
			layout, PortletKeys.ASSET_PUBLISHER, "column-2");

		preferences = new HashMap<String, String>();

		preferences.put("anyAssetType", Boolean.FALSE.toString());

		preferences.put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Upcoming Events");
		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		updatePortletSetup(layout, portletId, preferences);

		// News layout

		layout = addLayout(layoutSet, "News", "/news", "2_columns_iii");

		portletId = addPortletId(layout, PortletKeys.RSS, "column-1");

		preferences = new HashMap<String, String>();

		preferences.put("expandedEntriesPerFeed", "3");
		preferences.put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Technology news");
		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());
		preferences.put(
			"urls", "http://partners.userland.com/nytRss/technology.xml");

		updatePortletSetup(layout, portletId, preferences);

		portletId = addPortletId(layout, PortletKeys.RSS, "column-2");

		preferences = new HashMap<String, String>();

		preferences.put("expandedEntriesPerFeed", "0");
		preferences.put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Liferay news");
		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());
		preferences.put(
			"urls", "http://www.liferay.com/en/about-us/news/-/blogs/rss");
		preferences.put("titles", "Liferay Press Releases");

		updatePortletSetup(layout, portletId, preferences);
	}

	protected void addPublicSite(
			long companyId, long defaultUserId, List<LayoutSetPrototype>
			layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId, "Community Site",
			"Site with Forums and Wiki", layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "Home", "/home", "2_columns_iii");

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

		preferences.put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Upcoming Events");
		preferences.put("portletSetupUseCustomTitle", Boolean.TRUE.toString());

		updatePortletSetup(layout, portletId, preferences);

		// Wiki layout

		layout = addLayout(layoutSet, "Wiki", "/wiki", "2_columns_iii");

		addPortletId(layout, PortletKeys.WIKI, "column-1");
		addPortletId(
			layout, PortletKeys.TAGS_CATEGORIES_NAVIGATION, "column-2");
		addPortletId(layout, PortletKeys.TAGS_CLOUD, "column-2");
	}

	protected void addResourcePermissions(Layout layout, String portletId)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		PortalUtil.addPortletDefaultResource(
			layout.getCompanyId(), layout, portlet);
	}

	protected void doRun(long companyId) throws Exception {
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		List<LayoutSetPrototype> layoutSetPrototypes =
			LayoutSetPrototypeLocalServiceUtil.search(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addPublicSite(companyId, defaultUserId, layoutSetPrototypes);
		addPrivateSite(companyId, defaultUserId, layoutSetPrototypes);
	}

	protected void updateLayout(Layout layout) throws Exception {
		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	protected PortletPreferences updatePortletSetup(
			Layout layout, String portletId, Map<String, String> preferences)
		throws Exception {

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		for (Map.Entry<String, String> entry : preferences.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			portletSetup.setValue(key, value);
		}

		portletSetup.store();

		return portletSetup;
	}

}