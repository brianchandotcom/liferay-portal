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

package com.liferay.portal.util;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.CustomizedPages;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;

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

	public static Layout addLayout(long groupId, String name) throws Exception {
		return addLayout(groupId, name, false);
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

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

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

		HashMap<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		return LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			nameMap, null, true, ServiceTestUtil.getServiceContext());
	}

	public static LayoutSetPrototype addLayoutSetPrototype(String name)
		throws Exception {

		HashMap<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		return LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			nameMap, null, true, true, ServiceTestUtil.getServiceContext());
	}

	public static Layout addLayoutTypeArticle(
			long groupId, String name, String articleId)
		throws Exception {

		Layout layout = addLayout(groupId, name);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty("article-id", articleId);

		layout.setType(LayoutConstants.TYPE_ARTICLE);

		LayoutLocalServiceUtil.updateLayout(layout);

		return layout;
	}

	public static Layout addLayoutTypeLinkedToLayout(
			long groupId, String name, long linkedToLayoutId)
		throws Exception {

		Layout layout = addLayout(groupId, name);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"linkToLayoutId", String.valueOf(linkedToLayoutId));

		layout.setType(LayoutConstants.TYPE_LINK_TO_LAYOUT);

		LayoutLocalServiceUtil.updateLayout(layout);

		return layout;
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

		PortletPreferences portletPreferences = getPortletPreferences(
			layout.getCompanyId(), layout.getPlid(), newPortletId);

		for (String key : preferenceMap.keySet()) {
			portletPreferences.setValues(key, preferenceMap.get(key));
		}

		updatePortletPreferences(
			layout.getPlid(), newPortletId, portletPreferences);

		return newPortletId;
	}

	public static String getLayoutTemplateId(Layout layout) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getLayoutTemplateId();
	}

	public static PortletPreferences getPortletPreferences(
			Layout layout, String portletId)
		throws Exception {

		return getPortletPreferences(
				layout.getCompanyId(), layout.getPlid(), portletId);
	}

	public static PortletPreferences getPortletPreferences(
			long companyId, long plid, String portletId)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
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

	public static void updatePortletPreferences(
			long plid, String portletId, PortletPreferences portletPreferences)
		throws Exception {

		PortletPreferencesLocalServiceUtil.updatePreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
			portletPreferences);
	}

}