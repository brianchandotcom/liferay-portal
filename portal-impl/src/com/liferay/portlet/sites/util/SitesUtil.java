/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.LayoutTypePortletImpl;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutSettings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
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

	public static void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception {

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setAttribute("layoutPrototypeLinkEnabled", linkEnabled);
		serviceContext.setAttribute(
			"layoutPrototypeUuid", layoutPrototype.getUuid());

		targetLayout = LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(),
			targetLayout.getParentLayoutId(), targetLayout.getNameMap(),
			targetLayout.getTitleMap(), targetLayout.getDescriptionMap(),
			targetLayout.getKeywordsMap(), targetLayout.getRobotsMap(),
			layoutPrototypeLayout.getType(), targetLayout.getHidden(),
			targetLayout.getFriendlyURL(), targetLayout.getIconImage(), null,
			serviceContext);

		targetLayout = LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(),
			layoutPrototypeLayout.getTypeSettings());

		copyLayoutPrototypePermissions(targetLayout, layoutPrototype);

		copyPortletPermissions(targetLayout, layoutPrototypeLayout);

		copyPortletSetups(layoutPrototypeLayout, targetLayout);

		copyLookAndFeel(targetLayout, layoutPrototypeLayout);

		targetLayout = LayoutLocalServiceUtil.getLayout(targetLayout.getPlid());

		UnicodeProperties typeSettingsProperties =
			targetLayout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"last-merge-time",
			String.valueOf(targetLayout.getModifiedDate().getTime()));

		LayoutLocalServiceUtil.updateLayout(targetLayout, false);

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		prototypeTypeSettingsProperties.setProperty("merge-fail-count", "0");

		LayoutLocalServiceUtil.updateLayout(layoutPrototypeLayout, false);
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

		setLayoutSetPrototypeLinkEnabledParameter(
			parameterMap, targetLayoutSet, serviceContext);

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

	public static void copyLookAndFeel(Layout targetLayout, Layout sourceLayout)
		throws Exception {

		LayoutLocalServiceUtil.updateLookAndFeel(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), sourceLayout.getThemeId(),
			sourceLayout.getColorSchemeId(), sourceLayout.getCss(), false);

		LayoutLocalServiceUtil.updateLookAndFeel(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), sourceLayout.getWapThemeId(),
			sourceLayout.getWapColorSchemeId(), sourceLayout.getCss(), true);
	}

	public static void copyPortletPermissions(
		Layout targetLayout, Layout sourceLayout)
		throws Exception {

		long companyId = targetLayout.getCompanyId();

		List<Role> roles = RoleLocalServiceUtil.getRoles(companyId);

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			String resourceName = PortletConstants.getRootPortletId(
				sourcePortletId);

			String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				sourceLayout.getPlid(), sourcePortletId);

			String targetResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				targetLayout.getPlid(), sourcePortletId);

			List<String> actionIds =
				ResourceActionsUtil.getPortletResourceActions(resourceName);

			for (Role role : roles) {
				String roleName = role.getName();

				if (roleName.equals(RoleConstants.ADMINISTRATOR)) {
					continue;
				}

				List<String> actions =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							companyId, resourceName,
							ResourceConstants.SCOPE_INDIVIDUAL,
							sourceResourcePrimKey, role.getRoleId(), actionIds);

				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
					targetResourcePrimKey, role.getRoleId(),
					actions.toArray(new String[actions.size()]));
			}
		}
	}

	public static void copyPortletSetups(
			Layout sourceLayout, Layout targetLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			PortletPreferences sourcePreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					sourceLayout, sourcePortletId, null);

			PortletPreferencesImpl sourcePreferencesImpl =
				(PortletPreferencesImpl)sourcePreferences;

			PortletPreferences targetPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					targetLayout, sourcePortletId, null);

			PortletPreferencesImpl targetPreferencesImpl =
				(PortletPreferencesImpl)targetPreferences;

			PortletPreferencesLocalServiceUtil.updatePreferences(
				targetPreferencesImpl.getOwnerId(),
				targetPreferencesImpl.getOwnerType(),
				targetPreferencesImpl.getPlid(), sourcePortletId,
				sourcePreferences);

			if ((sourcePreferencesImpl.getOwnerId() !=
					PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
				(sourcePreferencesImpl.getOwnerType() !=
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) {

				sourcePreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						sourceLayout, sourcePortletId);

				sourcePreferencesImpl =
					(PortletPreferencesImpl)sourcePreferences;

				targetPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						targetLayout, sourcePortletId);

				targetPreferencesImpl =
					(PortletPreferencesImpl)targetPreferences;

				PortletPreferencesLocalServiceUtil.updatePreferences(
					targetPreferencesImpl.getOwnerId(),
					targetPreferencesImpl.getOwnerType(),
					targetPreferencesImpl.getPlid(), sourcePortletId,
					sourcePreferences);
			}
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
					(!isLayoutUpdateable(linkedLayout) ||
					 isLayoutToBeUpdatedFromSourcePrototype(linkedLayout))) {

					LayoutServiceUtil.deleteLayout(
						linkedLayout.getPlid(), serviceContext);
				}
			}
		}

		LayoutServiceUtil.deleteLayout(
			groupId, privateLayout, layoutId, serviceContext);

		long newPlid = layout.getParentPlid();

		if (newPlid <= 0) {
			Layout firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
				layoutSet.getGroupId(), layoutSet.getPrivateLayout(),
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (firstLayout != null) {
				newPlid = firstLayout.getPlid();
			}
		}

		return new Object[] {group, oldFriendlyURL, newPlid};
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
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
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

		setLayoutSetPrototypeLinkEnabledParameter(
			parameterMap, layoutSet, serviceContext);

		LayoutServiceUtil.importLayouts(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			parameterMap, inputStream);
	}

	public static boolean isLayoutModifiedSinceLastMerge(Layout layout)
		throws PortalException, SystemException {

		if ((layout == null) ||
			Validator.isNull(layout.getSourcePrototypeLayoutUuid()) ||
			layout.isLayoutPrototypeLinkActive()) {

			return false;
		}

		LayoutSet existingLayoutSet = layout.getLayoutSet();

		long lastMergeTime = GetterUtil.getLong(
			existingLayoutSet.getSettingsProperty("last-merge-time"));

		Date existingLayoutModifiedDate = layout.getModifiedDate();

		if ((existingLayoutModifiedDate != null) &&
			(existingLayoutModifiedDate.getTime() > lastMergeTime) &&
			isLayoutUpdateable(layout)) {

			return true;
		}

		return false;
	}

	public static boolean isLayoutSetPrototypeUpdateable(LayoutSet layoutSet) {
		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return true;
		}

		try {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuid(
						layoutSet.getLayoutSetPrototypeUuid());

			String layoutsUpdateable = layoutSetPrototype.getSettingsProperty(
				"layoutsUpdateable");

			if (Validator.isNotNull(layoutsUpdateable)) {
				return GetterUtil.getBoolean(layoutsUpdateable, true);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return true;
	}

	public static boolean isLayoutToBeUpdatedFromSourcePrototype(Layout layout)
		throws Exception {

		if (layout == null) {
			return false;
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return false;
		}

		Layout sourcePrototypeLayout =
			LayoutTypePortletImpl.getSourcePrototypeLayout(layout);

		if (sourcePrototypeLayout == null) {
			return false;
		}

		Date layoutModifiedDate = layout.getModifiedDate();

		Date lastCopyDate = null;

		String lastCopyDateString = layout.getTypeSettingsProperty(
			"layoutSetPrototypeLastCopyDate");

		if (Validator.isNotNull(lastCopyDateString)) {
			lastCopyDate = new Date(GetterUtil.getLong(lastCopyDateString));
		}

		if ((lastCopyDate != null) &&
			lastCopyDate.after(sourcePrototypeLayout.getModifiedDate())) {

			return false;
		}

		if (!isLayoutUpdateable(layout)) {
			return true;
		}

		if ((layoutModifiedDate == null) ||
			((lastCopyDate != null) &&
			 layoutModifiedDate.before(lastCopyDate))) {

			return true;
		}

		return false;
	}

	public static boolean isLayoutUpdateable(Layout layout) {
		try {
			if (layout instanceof VirtualLayout) {
				return false;
			}

			if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
				return true;
			}

			LayoutSet layoutSet = layout.getLayoutSet();

			if (layoutSet.isLayoutSetPrototypeLinkActive()) {
				boolean layoutSetPrototypeUpdateable =
					isLayoutSetPrototypeUpdateable(layoutSet);

				if (!layoutSetPrototypeUpdateable) {
					return false;
				}

				LayoutTypePortlet layoutTypePortlet = new LayoutTypePortletImpl(
					layout);

				String layoutUpdateable =
					layoutTypePortlet.getSourcePrototypeLayoutProperty(
						"layoutUpdateable");

				if (Validator.isNull(layoutUpdateable)) {
					return true;
				}

				return GetterUtil.getBoolean(layoutUpdateable);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return true;
	}

	public static boolean isUserGroupLayoutSetViewable(
			PermissionChecker permissionChecker, Group userGroupGroup)
		throws PortalException, SystemException {

		if (!userGroupGroup.isUserGroup()) {
			return false;
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, userGroupGroup.getGroupId(),
				ActionKeys.VIEW)) {

			return true;
		}

		UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
			userGroupGroup.getClassPK());

		if (UserLocalServiceUtil.hasUserGroupUser(
				userGroup.getUserGroupId(), permissionChecker.getUserId())) {

			return true;
		}
		else {
			return false;
		}
	}

	protected static void copyLayoutPrototypePermissions(
			Layout targetLayout,
			LayoutPrototype sourceLayoutPrototype)
		throws Exception {

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			targetLayout.getCompanyId());

		for (Role role : roles) {
			String roleName = role.getName();

			if (roleName.equals(RoleConstants.ADMINISTRATOR)) {
				continue;
			}

			List<String> actionIds = ResourceActionsUtil.getResourceActions(
				LayoutPrototype.class.getName());

			List<String> actions =
				ResourcePermissionLocalServiceUtil.
					getAvailableResourcePermissionActionIds(
						targetLayout.getCompanyId(),
						LayoutPrototype.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(
							sourceLayoutPrototype.getLayoutPrototypeId()),
						role.getRoleId(), actionIds);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				targetLayout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(targetLayout.getPlid()), role.getRoleId(),
				actions.toArray(new String[actions.size()]));
		}
	}

	protected static void setLayoutSetPrototypeLinkEnabledParameter(
		Map<String, String[]> parameterMap, LayoutSet targetLayoutSet,
		ServiceContext serviceContext) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) ||
			!PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE)) {

			return;
		}

		if (targetLayoutSet.isPrivateLayout()) {
			boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				serviceContext, "privateLayoutSetPrototypeLinkEnabled", true);

			if (!privateLayoutSetPrototypeLinkEnabled) {
				privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
					serviceContext, "layoutSetPrototypeLinkEnabled");
			}

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
				new String[] {
					String.valueOf(privateLayoutSetPrototypeLinkEnabled)
				});
		}
		else {
			boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				serviceContext, "publicLayoutSetPrototypeLinkEnabled");

			if (!publicLayoutSetPrototypeLinkEnabled) {
				publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
					serviceContext, "layoutSetPrototypeLinkEnabled", true);
			}

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
				new String[] {
					String.valueOf(publicLayoutSetPrototypeLinkEnabled)
				});
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SitesUtil.class);

}