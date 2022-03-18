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

package com.liferay.layout.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CustomizedPages;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Manuel de la Peña
 * @author Máté Thurzó
 */
public class LayoutTestUtil {

	public static LayoutPrototype addLayoutPrototype(String name)
		throws Exception {

		return LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), name
			).build(),
			(Map<Locale, String>)null, true,
			ServiceContextTestUtil.getServiceContext());
	}

	public static LayoutSetPrototype addLayoutSetPrototype(String name)
		throws Exception {

		return LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), name
			).build(),
			(Map<Locale, String>)null, true, true,
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

		for (Map.Entry<String, String[]> entry : preferenceMap.entrySet()) {
			portletPreferences.setValues(entry.getKey(), entry.getValue());
		}

		portletPreferences.store();

		return newPortletId;
	}

	public static Layout addTypeContentLayout(Group group) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_CONTENT, false, StringPool.BLANK,
			serviceContext);
	}

	public static Layout addTypeContentLayout(
			Group group, boolean privateLayout, boolean system)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_CONTENT, false, system, StringPool.BLANK,
			serviceContext);
	}

	public static Layout addTypeContentLayout(Group group, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);
	}

	public static Layout addTypeContentLayout(
			Group group, String name, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, title,
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);
	}

	public static Layout addTypeContentPublishedLayout(
			Group group, int status, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		Layout layout = addTypeContentLayout(group, name);

		serviceContext.setAttribute("published", Boolean.TRUE);

		Layout draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			layout.getPlid());

		if (draftLayout != null) {
			LayoutLocalServiceUtil.updateStatus(
				draftLayout.getUserId(), draftLayout.getPlid(), status,
				serviceContext);
		}

		return LayoutLocalServiceUtil.updateStatus(
			layout.getUserId(), layout.getPlid(), status, serviceContext);
	}

	public static Layout addTypeLinkToLayoutLayout(
			long groupId, long linkedToLayoutId)
		throws Exception {

		Layout layout = addTypePortletLayout(groupId, false);

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty(
			"linkToLayoutId", String.valueOf(linkedToLayoutId));

		layout.setType(LayoutConstants.TYPE_LINK_TO_LAYOUT);

		LayoutLocalServiceUtil.updateLayout(layout);

		return layout;
	}

	public static Layout addTypeLinkToURLLayout(long groupId, String url)
		throws Exception {

		Layout layout = addTypePortletLayout(groupId, false);

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty("url", url);

		layout.setType(LayoutConstants.TYPE_URL);

		LayoutLocalServiceUtil.updateLayout(layout);

		return layout;
	}

	public static Layout addTypePortletLayout(Group group) throws Exception {
		return addTypePortletLayout(group.getGroupId());
	}

	public static Layout addTypePortletLayout(
			Group group, boolean privateLayout)
		throws Exception {

		return addTypePortletLayout(group.getGroupId(), privateLayout);
	}

	public static Layout addTypePortletLayout(
			Group group, boolean privateLayout, LayoutPrototype layoutPrototype,
			boolean linkEnabled)
		throws Exception {

		return addTypePortletLayout(
			group.getGroupId(), privateLayout, layoutPrototype, linkEnabled);
	}

	public static Layout addTypePortletLayout(
			Group group, long parentLayoutPlid)
		throws Exception {

		return addTypePortletLayout(group.getGroupId(), parentLayoutPlid);
	}

	public static Layout addTypePortletLayout(long groupId) throws Exception {
		return addTypePortletLayout(groupId, false);
	}

	public static Layout addTypePortletLayout(
			long groupId, boolean privateLayout)
		throws Exception {

		return addTypePortletLayout(groupId, privateLayout, null, false);
	}

	public static Layout addTypePortletLayout(
			long groupId, boolean privateLayout,
			LayoutPrototype layoutPrototype, boolean linkEnabled)
		throws Exception {

		return addTypePortletLayout(
			groupId,
			RandomTestUtil.randomString(
				LayoutFriendlyURLRandomizerBumper.INSTANCE,
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			privateLayout, layoutPrototype, linkEnabled);
	}

	public static Layout addTypePortletLayout(
			long groupId, boolean privateLayout, Map<Locale, String> nameMap,
			Map<Locale, String> friendlyURLMap)
		throws Exception {

		return addTypePortletLayout(
			groupId, privateLayout, nameMap, friendlyURLMap, false);
	}

	public static Layout addTypePortletLayout(
			long groupId, boolean privateLayout, Map<Locale, String> nameMap,
			Map<Locale, String> friendlyURLMap, boolean hidden)
		throws Exception {

		return addTypePortletLayout(
			groupId, privateLayout, nameMap, nameMap,
			new HashMap<Locale, String>(), new HashMap<Locale, String>(),
			new HashMap<Locale, String>(), StringPool.BLANK, friendlyURLMap,
			hidden);
	}

	public static Layout addTypePortletLayout(
			long groupId, boolean privateLayout, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String typeSettings, Map<Locale, String> friendlyURLMap,
			boolean hidden)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap, titleMap,
			descriptionMap, keywordsMap, robotsMap,
			LayoutConstants.TYPE_PORTLET, typeSettings, hidden, friendlyURLMap,
			serviceContext);
	}

	public static Layout addTypePortletLayout(
			long groupId, long parentLayoutPlid)
		throws Exception {

		Layout layout = addTypePortletLayout(groupId, false);

		LayoutLocalServiceUtil.updateParentLayoutId(
			layout.getPlid(), parentLayoutPlid);

		return LayoutLocalServiceUtil.fetchLayout(layout.getPlid());
	}

	public static Layout addTypePortletLayout(long groupId, String typeSettings)
		throws Exception {

		return addTypePortletLayout(
			groupId, false, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), typeSettings,
			new HashMap<Locale, String>(), false);
	}

	public static Layout addTypePortletLayout(
			long groupId, String name, boolean privateLayout)
		throws Exception {

		return addTypePortletLayout(groupId, name, privateLayout, null, false);
	}

	public static Layout addTypePortletLayout(
			long groupId, String name, boolean privateLayout,
			LayoutPrototype layoutPrototype, boolean linkEnabled)
		throws Exception {

		return addTypePortletLayout(
			groupId, name, privateLayout, layoutPrototype, linkEnabled, false);
	}

	public static Layout addTypePortletLayout(
			long groupId, String name, boolean privateLayout,
			LayoutPrototype layoutPrototype, boolean linkEnabled,
			boolean hidden)
		throws Exception {

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		try {
			return LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, false, friendlyURL);
		}
		catch (NoSuchLayoutException noSuchLayoutException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchLayoutException);
			}
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
			LayoutConstants.TYPE_PORTLET, hidden, friendlyURL, serviceContext);
	}

	public static Layout addTypePortletLayout(
			long groupId, String name, long parentLayoutPlid)
		throws Exception {

		Layout layout = addTypePortletLayout(groupId, name, false);

		LayoutLocalServiceUtil.updateParentLayoutId(
			layout.getPlid(), parentLayoutPlid);

		return LayoutLocalServiceUtil.fetchLayout(layout.getPlid());
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

		return getPortletPreferences(
			LayoutLocalServiceUtil.getLayout(plid), portletId);
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

	private static final Log _log = LogFactoryUtil.getLog(LayoutTestUtil.class);

}