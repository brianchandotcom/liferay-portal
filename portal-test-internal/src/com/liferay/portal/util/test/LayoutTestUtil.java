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

package com.liferay.portal.util.test;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.CustomizedPages;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.randomizerbumpers.FriendlyURLRandomizerBumper;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Manuel de la Peña
 * @author Mate Thurzo
 */
public class LayoutTestUtil {

	public static Layout addLayout(Group group) throws Exception {
		return addLayout(group.getGroupId());
	}

	public static Layout addLayout(Group group, boolean privateLayout)
		throws Exception {

		return addLayout(group.getGroupId(), privateLayout);
	}

	public static Layout addLayout(
			Group group, boolean privateLayout, LayoutPrototype layoutPrototype,
			boolean linkEnabled)
		throws Exception {

		return addLayout(
			group.getGroupId(), privateLayout, layoutPrototype, linkEnabled);
	}

	public static Layout addLayout(Group group, long parentLayoutPlid)
		throws Exception {

		return addLayout(group.getGroupId(), parentLayoutPlid);
	}

	public static Layout addLayout(long groupId) throws Exception {
		return addLayout(groupId, false);
	}

	public static Layout addLayout(long groupId, boolean privateLayout)
		throws Exception {

		return addLayout(groupId, privateLayout, null, false);
	}

	public static Layout addLayout(
			long groupId, boolean privateLayout,
			LayoutPrototype layoutPrototype, boolean linkEnabled)
		throws Exception {

		return addLayout(
			groupId,
			RandomTestUtil.randomString(
				FriendlyURLRandomizerBumper.INSTANCE,
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			privateLayout, layoutPrototype, linkEnabled);
	}

	public static Layout addLayout(
			long groupId, boolean privateLayout, Map<Locale, String> nameMap,
			Map<Locale, String> friendlyURLMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap, nameMap,
			new HashMap<Locale, String>(), new HashMap<Locale, String>(),
			new HashMap<Locale, String>(), LayoutConstants.TYPE_PORTLET,
			StringPool.BLANK, false, friendlyURLMap, serviceContext);
	}

	public static Layout addLayout(long groupId, long parentLayoutPlid)
		throws Exception {

		Layout layout = addLayout(groupId, false);

		LayoutLocalServiceUtil.updateParentLayoutId(
			layout.getPlid(), parentLayoutPlid);

		return LayoutLocalServiceUtil.fetchLayout(layout.getPlid());
	}

	public static Layout addLayout(
			long groupId, String name, boolean privateLayout)
		throws Exception {

		return addLayout(groupId, name, privateLayout, null, false);
	}

	public static Layout addLayout(
			long groupId, String name, boolean privateLayout,
			LayoutPrototype layoutPrototype, boolean linkEnabled)
		throws Exception {

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, false, friendlyURL);

			return layout;
		}
		catch (NoSuchLayoutException nsle) {
		}

		String description = "This is a test page.";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		if (layoutPrototype != null) {
			serviceContext.setAttribute(
				"layoutPrototypeLinkEnabled", linkEnabled);
			serviceContext.setAttribute(
				"layoutPrototypeUuid", layoutPrototype.getUuid());
		}

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null, description,
			LayoutConstants.TYPE_PORTLET, false, friendlyURL, serviceContext);
	}

	public static Layout addLayout(
			long groupId, String name, long parentLayoutPlid)
		throws Exception {

		Layout layout = addLayout(groupId, name, false);

		LayoutLocalServiceUtil.updateParentLayoutId(
			layout.getPlid(), parentLayoutPlid);

		return LayoutLocalServiceUtil.fetchLayout(layout.getPlid());
	}

	public static LayoutPrototype addLayoutPrototype(String name)
		throws Exception {

		HashMap<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		return LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			nameMap, (Map<Locale, String>)null, true,
			ServiceContextTestUtil.getServiceContext());
	}

	public static LayoutSetPrototype addLayoutSetPrototype(String name)
		throws Exception {

		HashMap<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		return LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			nameMap, (Map<Locale, String>)null, true, true,
			ServiceContextTestUtil.getServiceContext());
	}

	public static String addPortletToLayout(Layout layout, String portletId)
		throws Exception {

		Map<String, String[]> preferenceMap = null;

		return addPortletToLayout(layout, portletId, preferenceMap);
	}

	public static String addPortletToLayout(
			Layout layout, String portletId,
			Map<String, String[]> preferenceMap)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		String columnId = columns.get(0);

		return addPortletToLayout(
			userId, layout, portletId, columnId, preferenceMap);
	}

	public static String addPortletToLayout(
			long userId, Layout layout, String portletId, String columnId,
			Map<String, String[]> preferenceMap)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String newPortletId = layoutTypePortlet.addPortletId(
			userId, portletId, columnId, -1);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		if (preferenceMap == null) {
			return newPortletId;
		}

		PortletPreferences portletPreferences = getPortletPreferences(
			layout, newPortletId);

		for (String key : preferenceMap.keySet()) {
			portletPreferences.setValues(key, preferenceMap.get(key));
		}

		portletPreferences.store();

		return newPortletId;
	}

	public static Layout addTypeArticleLayout(long groupId, String articleId)
		throws Exception {

		Layout layout = addLayout(groupId, false);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty("article-id", articleId);

		layout.setType(LayoutConstants.TYPE_ARTICLE);

		LayoutLocalServiceUtil.updateLayout(layout);

		return layout;
	}

	public static Layout addTypeLinkToLayoutLayout(
			long groupId, long linkedToLayoutId)
		throws Exception {

		Layout layout = addLayout(groupId, false);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"linkToLayoutId", String.valueOf(linkedToLayoutId));

		layout.setType(LayoutConstants.TYPE_LINK_TO_LAYOUT);

		LayoutLocalServiceUtil.updateLayout(layout);

		return layout;
	}

	public static String getLayoutTemplateId(Layout layout) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getLayoutTemplateId();
	}

	public static PortletPreferences getPortletPreferences(
			Layout layout, String portletId)
		throws Exception {

		return PortletPreferencesFactoryUtil.getPortletSetup(
			layout, portletId, null);
	}

	public static PortletPreferences getPortletPreferences(
			long plid, String portletId)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		return getPortletPreferences(layout, portletId);
	}

	public static List<Portlet> getPortlets(Layout layout) throws Exception {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getPortlets();
	}

	public static boolean isLayoutColumnCustomizable(
		Layout layout, String columnId) {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.isColumnCustomizable(columnId);
	}

	public static Layout updateLayoutColumnCustomizable(
			Layout layout, String columnId, boolean customizable)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setTypeSettingsProperty(
			CustomizedPages.namespaceColumnId(columnId),
			String.valueOf(customizable));

		layoutTypePortlet.setUpdatePermission(customizable);

		return LayoutServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	public static Layout updateLayoutPortletPreference(
			Layout layout, String portletId, String portletPreferenceName,
			String portletPreferenceValue)
		throws Exception {

		PortletPreferences layoutPortletPreferences = getPortletPreferences(
			layout, portletId);

		layoutPortletPreferences.setValue(
			portletPreferenceName, portletPreferenceValue);

		layoutPortletPreferences.store();

		return LayoutLocalServiceUtil.getLayout(layout.getPlid());
	}

	public static Layout updateLayoutPortletPreferences(
			Layout layout, String portletId,
			Map<String, String> portletPreferences)
		throws Exception {

		PortletPreferences layoutPortletPreferences = getPortletPreferences(
			layout, portletId);

		for (Map.Entry<String, String> entry : portletPreferences.entrySet()) {
			layoutPortletPreferences.setValue(entry.getKey(), entry.getValue());
		}

		layoutPortletPreferences.store();

		return LayoutLocalServiceUtil.getLayout(layout.getPlid());
	}

	public static Layout updateLayoutTemplateId(
			Layout layout, String layoutTemplateId)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			TestPropsValues.getUserId(), layoutTemplateId);

		return LayoutServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

}