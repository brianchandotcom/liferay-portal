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

package com.liferay.portlet.sites.util;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.impl.LayoutTypePortletImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutSettings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class SitesUtil {

	public static void addPortletBreadcrumbEntries(
			Group group, String pagesName, PortletURL redirectURL,
			HttpServletRequest request, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if ((renderResponse == null) ||
			portletName.equals(PortletKeys.GROUP_PAGES) ||
			portletName.equals(PortletKeys.MY_PAGES)) {

			return;
		}

		Locale locale = themeDisplay.getLocale();

		if (group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, LanguageUtil.get(locale, "page-template"), null);

			PortalUtil.addPortletBreadcrumbEntry(
				request, group.getDescriptiveName(), redirectURL.toString());
		}
		else {
			PortalUtil.addPortletBreadcrumbEntry(
				request, group.getDescriptiveName(), null);
		}

		if (!group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, LanguageUtil.get(locale, pagesName),
				redirectURL.toString());
		}
	}

	public static void applyLayoutSetPrototypes(
			Group group, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId, ServiceContext serviceContext)
		throws Exception {

		Group sourceGroup = null;

		if (publicLayoutSetPrototypeId > 0) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					publicLayoutSetPrototypeId);

			LayoutSet publicLayoutSet = group.getPublicLayoutSet();

			copyLayoutSet(
				layoutSetPrototype.getLayoutSet(), publicLayoutSet,
				serviceContext);

			sourceGroup = layoutSetPrototype.getGroup();
		}

		if (privateLayoutSetPrototypeId > 0) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					privateLayoutSetPrototypeId);

			LayoutSet privateLayoutSet = group.getPrivateLayoutSet();

			copyLayoutSet(
				layoutSetPrototype.getLayoutSet(), privateLayoutSet,
				serviceContext);

			if (sourceGroup == null) {
				sourceGroup = layoutSetPrototype.getGroup();
			}
		}

		if (sourceGroup != null) {
			copyTypeSettings(sourceGroup, group);
		}
	}

	public static void copyLayout(
			long userId, Layout sourceLayout, Layout targetLayout,
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String[]> parameterMap =
			getLayoutSetPrototypeParameters(serviceContext);

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		File file = LayoutLocalServiceUtil.exportLayoutsAsFile(
			sourceLayout.getGroupId(), sourceLayout.isPrivateLayout(),
			new long[] {sourceLayout.getLayoutId()}, parameterMap, null, null);

		try {
			LayoutLocalServiceUtil.importLayouts(
				userId, targetLayout.getGroupId(),
				targetLayout.isPrivateLayout(), parameterMap, file);
		}
		finally {
			file.delete();
		}
	}

	public static void copyLayoutSet(
			LayoutSet sourceLayoutSet, LayoutSet targetLayoutSet,
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		if (!targetLayoutSet.isPrivateLayout()) {
			parameterMap.put(
				PortletDataHandlerKeys.PUBLIC_LAYOUT_PERMISSIONS,
				new String[] {Boolean.TRUE.toString()});
		}

		File file = LayoutLocalServiceUtil.exportLayoutsAsFile(
			sourceLayoutSet.getGroupId(), sourceLayoutSet.isPrivateLayout(),
			null, parameterMap, null, null);

		try {
			LayoutServiceUtil.importLayouts(
				targetLayoutSet.getGroupId(), targetLayoutSet.isPrivateLayout(),
				parameterMap, file);
		}
		finally {
			file.delete();
		}
	}

	public static void copyTypeSettings(Group sourceGroup, Group targetGroup)
		throws Exception {

		GroupServiceUtil.updateGroup(
			targetGroup.getGroupId(), sourceGroup.getTypeSettings());
	}

	public static Object[] deleteLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		return deleteLayout(request, response);
	}

	public static Object[] deleteLayout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long plid = ParamUtil.getLong(request, "plid");

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
		long layoutId = ParamUtil.getLong(request, "layoutId");

		Layout layout = null;

		if (plid <= 0) {
			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);
		}
		else {
			layout = LayoutLocalServiceUtil.getLayout(plid);

			groupId = layout.getGroupId();
			privateLayout = layout.isPrivateLayout();
			layoutId = layout.getLayoutId();
		}

		Group group = layout.getGroup();
		String oldFriendlyURL = layout.getFriendlyURL();

		if (group.isStagingGroup() &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}

		if (LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.DELETE)) {

			LayoutSettings layoutSettings = LayoutSettings.getInstance(layout);

			EventsProcessorUtil.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE,
				layoutSettings.getConfigurationActionDelete(), request,
				response);
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		Group layoutSetGroup = layoutSet.getGroup();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		if (layoutSetGroup.isLayoutSetPrototype()) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					layoutSetGroup.getClassPK());

			List<LayoutSet> linkedLayoutSets =
				LayoutSetLocalServiceUtil.getLayoutSetsByLayoutSetPrototypeUuid(
					layoutSetPrototype.getUuid());

			for (LayoutSet linkedLayoutSet : linkedLayoutSets) {
				Layout linkedLayout =
					LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
						layout.getUuid(), linkedLayoutSet.getGroupId());

				if ((linkedLayout != null) &&
					(isLayoutLocked(linkedLayout) ||
					 isLayoutToBeUpdatedFromTemplate(linkedLayout))) {

					LayoutServiceUtil.deleteLayout(
						linkedLayout.getPlid(), serviceContext);
				}
			}
		}

		LayoutServiceUtil.deleteLayout(
			groupId, privateLayout, layoutId, serviceContext);

		return new Object[] {group, oldFriendlyURL};
	}

	public static void deleteLayout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			renderResponse);

		deleteLayout(request, response);
	}

	public static File exportLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		return LayoutLocalServiceUtil.exportLayoutsAsFile(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			null, parameterMap, null, null);
	}

	public static Map<String, String[]> getLayoutSetPrototypeParameters(
		ServiceContext serviceContext) {

		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_INHERITED,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			});
		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});

		return parameterMap;
	}

	public static void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		LayoutServiceUtil.importLayouts(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			parameterMap, inputStream);
	}

	public static boolean isLayoutLocked(Layout layout) {
		try {
			LayoutSet layoutSet = layout.getLayoutSet();

			if (layout.isLayoutPrototypeLinkEnabled() ||
				layoutSet.isLayoutSetPrototypeLinkEnabled()) {

				LayoutTypePortletImpl layoutTypePortlet =
					new LayoutTypePortletImpl(layout);

				return isLayoutLocked(layoutTypePortlet);
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	public static boolean isLayoutLocked(LayoutTypePortlet layoutTypePortlet) {
		Layout layout = layoutTypePortlet.getLayout();

		try {
			LayoutSet layoutSet = layout.getLayoutSet();

			if (layout.isLayoutPrototypeLinkEnabled() ||
				layoutSet.isLayoutSetPrototypeLinkEnabled()) {

				String locked = layoutTypePortlet.getTemplateProperty("locked");

				if (Validator.isNotNull(locked)) {
					return GetterUtil.getBoolean(locked);
				}
				else if (Validator.isNotNull(layout.getTemplateLayoutUuid())) {
					return isLayoutSetLocked(layoutSet);
				}
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	public static boolean isLayoutSetLocked(
		Group group, boolean privateLayout) {

		try {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				group.getGroupId(), privateLayout);

			return isLayoutSetLocked(layoutSet);
		}
		catch (Exception e) {
		}

		return true;
	}

	public static boolean isLayoutSetLocked(LayoutSet layoutSet) {
		if (layoutSet.isLayoutSetPrototypeLinkEnabled()) {
			try {
				LayoutSetPrototype layoutSetPrototype =
					LayoutSetPrototypeLocalServiceUtil.
						getLayoutSetPrototypeByUuid(
							layoutSet.getLayoutSetPrototypeUuid());

				String allowModifications =
					layoutSetPrototype.getSettingsProperty(
						"allowModifications");

				if (Validator.isNotNull(allowModifications)) {
					return !GetterUtil.getBoolean(allowModifications);
				}
			}
			catch (Exception e) {
			}
		}

		return false;
	}

	public static boolean isLayoutToBeUpdatedFromTemplate(Layout layout)
		throws Exception {

		if (layout == null) {
			return false;
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		if (!layoutSet.isLayoutSetPrototypeLinkEnabled()) {
			return false;
		}

		Layout templateLayout = LayoutTypePortletImpl.getTemplateLayout(layout);

		Date layoutModifiedDate = layout.getModifiedDate();

		Date lastCopyDate = null;

		String lastCopyDateString = layout.getTypeSettingsProperty(
			"layoutSetPrototypeLastCopyDate");

		if (Validator.isNotNull(lastCopyDateString)) {
			lastCopyDate = new Date(GetterUtil.getLong(lastCopyDateString));
		}

		if ((lastCopyDate != null) &&
			lastCopyDate.after(templateLayout.getModifiedDate())) {

			return false;
		}

		if (isLayoutLocked(layout)) {
			return true;
		}

		if ((layoutModifiedDate == null) ||
			((lastCopyDate != null) &&
			 layoutModifiedDate.before(lastCopyDate))) {

			return true;
		}

		return false;
	}

}