/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.calendar.model.CalEvent;

import java.util.HashMap;
import java.util.Iterator;
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
			false, serviceContext);

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
				true, new ServiceContext());

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

		return portletId;
	}

	protected void addPrivateSite(
			long companyId, long defaultUserId, List<LayoutSetPrototype>
			layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId,
			"Site with Calendar, Documents and Images", StringPool.BLANK,
			layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "Home", "/home", "2_columns_iii");

		addPortletId(layout, PortletKeys.ANNOUNCEMENTS, "column-1");
		addPortletId(layout, PortletKeys.ACTIVITIES, "column-1");
		addPortletId(layout, PortletKeys.INVITATION, "column-2");
		addPortletId(layout, PortletKeys.DIRECTORY, "column-2");

		// Calendar layout

		layout = addLayout(layoutSet, "Calendar", "/calendar", "2_columns_iii");

		addPortletId(layout, PortletKeys.CALENDAR, "column-1");

		String portletId = addPortletId(
			layout, PortletKeys.ASSET_PUBLISHER, "column-2");

		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("any-asset-type", Boolean.FALSE.toString());

		long classNameId = PortalUtil.getClassNameId(CalEvent.class);

		preferences.put("class-name-ids", String.valueOf(classNameId));

		preferences.put(
			"portlet-setup-title-" + LocaleUtil.getDefault(),
			"Upcoming Events");
		preferences.put(
			"portlet-setup-use-custom-title", Boolean.TRUE.toString());

		updatePortletSetup(layout, portletId, preferences);

		// Documents layout

		layout = addLayout(
			layoutSet, "Documents", "/documents", "2_columns_iii");

		addPortletId(layout, PortletKeys.DOCUMENT_LIBRARY_DISPLAY, "column-1");
		addPortletId(layout, PortletKeys.RECENT_DOCUMENTS, "column-2");

		// Images layout

		layout = addLayout(layoutSet, "Images", "/images", "1_column");

		addPortletId(layout, PortletKeys.IMAGE_GALLERY, "column-1");
	}

	protected void addPublicSite(
			long companyId, long defaultUserId, List<LayoutSetPrototype>
			layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet = addLayoutSetPrototype(
			companyId, defaultUserId, "Site with Blog, Wiki and Forum",
			"Pages that are available to both guests and members of this site.",
			layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = addLayout(layoutSet, "Home", "/home", "1_2_columns_ii");

		addPortletId(layout, PortletKeys.JOURNAL_CONTENT_SEARCH, "column-1");
		addPortletId(layout, PortletKeys.JOURNAL_CONTENT, "column-1");
		addPortletId(layout, PortletKeys.ASSET_PUBLISHER, "column-2");
		addPortletId(layout, PortletKeys.POLLS_DISPLAY, "column-3");

		// Blog layout

		layout = addLayout(layoutSet, "Blog", "/blog", "2_columns_iii");

		addPortletId(layout, PortletKeys.BLOGS, "column-1");
		addPortletId(layout, PortletKeys.TAGS_CLOUD, "column-2");
		addPortletId(layout, PortletKeys.RECENT_BLOGGERS, "column-2");

		// Wiki layout

		layout = addLayout(layoutSet, "Wiki", "/wiki", "2_columns_iii");

		addPortletId(layout, PortletKeys.WIKI, "column-1");
		addPortletId(
			layout, PortletKeys.TAGS_CATEGORIES_NAVIGATION, "column-2");
		addPortletId(layout, PortletKeys.TAGS_ENTRIES_NAVIGATION, "column-2");

		// Forum layout

		layout = addLayout(layoutSet, "Forum", "/forum", "1_column");

		addPortletId(layout, PortletKeys.MESSAGE_BOARDS, "column-1");
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

		Iterator<Map.Entry<String, String>> itr =
			preferences.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();

			String key = entry.getKey();
			String value = entry.getValue();

			portletSetup.setValue(key, value);
		}

		portletSetup.store();

		return portletSetup;
	}

}