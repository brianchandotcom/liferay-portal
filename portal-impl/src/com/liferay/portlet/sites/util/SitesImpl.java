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

package com.liferay.portlet.sites.util;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportServiceUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredLayoutException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.service.persistence.LayoutSetUtil;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.sites.kernel.util.Sites;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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
public class SitesImpl implements Sites {

	@Override
	public void addMergeFailFriendlyURLLayout(Layout layout)
		throws PortalException {

		LayoutSet layoutSet = layout.getLayoutSet();

		layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout());

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		String oldMergeFailFriendlyURLLayouts = settingsProperties.getProperty(
			MERGE_FAIL_FRIENDLY_URL_LAYOUTS, StringPool.BLANK);

		String newMergeFailFriendlyURLLayouts = StringUtil.add(
			oldMergeFailFriendlyURLLayouts, layout.getUuid());

		if (!oldMergeFailFriendlyURLLayouts.equals(
				newMergeFailFriendlyURLLayouts)) {

			settingsProperties.setProperty(
				MERGE_FAIL_FRIENDLY_URL_LAYOUTS,
				newMergeFailFriendlyURLLayouts);

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}
	}

	@Override
	public void addPortletBreadcrumbEntries(
			Group group, HttpServletRequest httpServletRequest,
			PortletURL portletURL)
		throws Exception {

		List<Group> ancestorGroups = group.getAncestors();

		Collections.reverse(ancestorGroups);

		for (Group ancestorGroup : ancestorGroups) {
			portletURL.setParameter(
				"groupId", String.valueOf(ancestorGroup.getGroupId()));

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, ancestorGroup.getDescriptiveName(),
				portletURL.toString());
		}

		Group unescapedGroup = group.toUnescapedModel();

		portletURL.setParameter(
			"groupId", String.valueOf(unescapedGroup.getGroupId()));

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, unescapedGroup.getDescriptiveName(),
			portletURL.toString());
	}

	@Override
	public void addPortletBreadcrumbEntries(
			Group group, HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		PortletURL portletURL = renderResponse.createRenderURL();

		addPortletBreadcrumbEntries(group, httpServletRequest, portletURL);
	}

	@Override
	public void addPortletBreadcrumbEntries(
			Group group, String pagesName, PortletURL redirectURL,
			HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws Exception {

		if (renderResponse == null) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group unescapedGroup = group.toUnescapedModel();

		Locale locale = themeDisplay.getLocale();

		if (group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, LanguageUtil.get(locale, "page-template"),
				null);

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, unescapedGroup.getDescriptiveName(),
				redirectURL.toString());
		}
		else {
			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, unescapedGroup.getDescriptiveName(), null);
		}

		if (!group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, LanguageUtil.get(locale, pagesName),
				redirectURL.toString());
		}
	}

	@Override
	public void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception {

		Locale siteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		LayoutTypePortlet targetLayoutType =
			(LayoutTypePortlet)targetLayout.getLayoutType();

		List<String> targetLayoutPortletIds = targetLayoutType.getPortletIds();

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		byte[] iconBytes = null;

		if (layoutPrototypeLayout.isIconImage()) {
			Image image = ImageLocalServiceUtil.getImage(
				layoutPrototypeLayout.getIconImageId());

			iconBytes = image.getTextObj();
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Serializable originalLayoutPrototypeLinkEnabled =
			serviceContext.getAttribute("layoutPrototypeLinkEnabled");
		Serializable originalLayoutPrototypeUuid = serviceContext.getAttribute(
			"layoutPrototypeUuid");

		try {
			serviceContext.setAttribute(
				"layoutPrototypeLinkEnabled", linkEnabled);
			serviceContext.setAttribute(
				"layoutPrototypeUuid", layoutPrototype.getUuid());
			Locale targetSiteDefaultLocale = PortalUtil.getSiteDefaultLocale(
				targetLayout.getGroupId());

			LocaleThreadLocal.setSiteDefaultLocale(targetSiteDefaultLocale);

			targetLayout = LayoutLocalServiceUtil.updateLayout(
				targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
				targetLayout.getLayoutId(), targetLayout.getParentLayoutId(),
				targetLayout.getNameMap(), targetLayout.getTitleMap(),
				targetLayout.getDescriptionMap(), targetLayout.getKeywordsMap(),
				targetLayout.getRobotsMap(), layoutPrototypeLayout.getType(),
				targetLayout.isHidden(), targetLayout.getFriendlyURLMap(),
				layoutPrototypeLayout.isIconImage(), iconBytes, serviceContext);
		}
		finally {
			if (originalLayoutPrototypeLinkEnabled == null) {
				serviceContext.removeAttribute("layoutPrototypeLinkEnabled");
			}
			else {
				serviceContext.setAttribute(
					"layoutPrototypeLinkEnabled",
					originalLayoutPrototypeLinkEnabled);
			}

			if (originalLayoutPrototypeUuid == null) {
				serviceContext.removeAttribute("layoutPrototypeUuid");
			}
			else {
				serviceContext.setAttribute(
					"layoutPrototypeUuid", originalLayoutPrototypeUuid);
			}

			LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);
		}

		targetLayout = LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(),
			layoutPrototypeLayout.getTypeSettings());

		copyPortletPermissions(targetLayout, layoutPrototypeLayout);

		copyPortletSetups(layoutPrototypeLayout, targetLayout);

		copyLookAndFeel(targetLayout, layoutPrototypeLayout);

		deleteUnreferencedPortlets(
			targetLayoutPortletIds, targetLayout, layoutPrototypeLayout);

		targetLayout = LayoutLocalServiceUtil.getLayout(targetLayout.getPlid());

		UnicodeProperties typeSettingsProperties =
			targetLayout.getTypeSettingsProperties();

		Date modifiedDate = targetLayout.getModifiedDate();

		typeSettingsProperties.setProperty(
			LAST_MERGE_TIME, String.valueOf(modifiedDate.getTime()));

		LayoutLocalServiceUtil.updateLayout(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), targetLayout.getTypeSettings());

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		if (prototypeTypeSettingsProperties.containsKey(MERGE_FAIL_COUNT)) {
			prototypeTypeSettingsProperties.remove(MERGE_FAIL_COUNT);

			LayoutLocalServiceUtil.updateLayout(layoutPrototypeLayout);
		}
	}

	@Override
	public void copyLayout(
			long userId, Layout sourceLayout, Layout targetLayout,
			ServiceContext serviceContext)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, sourceLayout.getGroupId(),
					sourceLayout.isPrivateLayout(),
					new long[] {sourceLayout.getLayoutId()}, parameterMap);

		ExportImportConfiguration exportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		File file = ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportConfiguration);

		try {
			Map<String, Serializable> importLayoutSettingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportLayoutSettingsMap(
						userId, targetLayout.getGroupId(),
						targetLayout.isPrivateLayout(), null, parameterMap,
						user.getLocale(), user.getTimeZone());

			ExportImportConfiguration importConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addDraftExportImportConfiguration(
						userId,
						ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
						importLayoutSettingsMap);

			ExportImportLocalServiceUtil.importLayouts(
				importConfiguration, file);
		}
		finally {
			file.delete();
		}
	}

	@Override
	public void copyLookAndFeel(Layout targetLayout, Layout sourceLayout)
		throws Exception {

		LayoutLocalServiceUtil.updateLookAndFeel(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), sourceLayout.getThemeId(),
			sourceLayout.getColorSchemeId(), sourceLayout.getCss());
	}

	@Override
	public void copyPortletPermissions(Layout targetLayout, Layout sourceLayout)
		throws Exception {

		List<Role> roles = RoleLocalServiceUtil.getGroupRelatedRoles(
			targetLayout.getGroupId());
		Group targetGroup = targetLayout.getGroup();

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			String resourceName = PortletIdCodec.decodePortletName(
				sourcePortletId);

			String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				sourceLayout.getPlid(), sourcePortletId);

			String targetResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				targetLayout.getPlid(), sourcePortletId);

			List<String> actionIds =
				ResourceActionsUtil.getPortletResourceActions(resourceName);

			for (Role role : roles) {
				String roleName = role.getName();

				if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
					(!targetGroup.isLayoutSetPrototype() &&
					 targetLayout.isPrivateLayout() &&
					 roleName.equals(RoleConstants.GUEST))) {

					continue;
				}

				List<String> actions =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							targetLayout.getCompanyId(), resourceName,
							ResourceConstants.SCOPE_INDIVIDUAL,
							sourceResourcePrimKey, role.getRoleId(), actionIds);

				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					targetLayout.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, targetResourcePrimKey,
					role.getRoleId(), actions.toArray(new String[0]));
			}
		}
	}

	@Override
	public void copyPortletSetups(Layout sourceLayout, Layout targetLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			PortletPreferences sourcePreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					sourceLayout, sourcePortletId, null);

			PortletPreferencesImpl sourcePortletPreferencesImpl =
				(PortletPreferencesImpl)sourcePreferences;

			PortletPreferences targetPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					targetLayout, sourcePortletId, null);

			PortletPreferencesImpl targetPortletPreferencesImpl =
				(PortletPreferencesImpl)targetPreferences;

			PortletPreferencesLocalServiceUtil.updatePreferences(
				targetPortletPreferencesImpl.getOwnerId(),
				targetPortletPreferencesImpl.getOwnerType(),
				targetPortletPreferencesImpl.getPlid(), sourcePortletId,
				sourcePreferences);

			if ((sourcePortletPreferencesImpl.getOwnerId() !=
					PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
				(sourcePortletPreferencesImpl.getOwnerType() !=
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) {

				sourcePreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						sourceLayout, sourcePortletId);

				sourcePortletPreferencesImpl =
					(PortletPreferencesImpl)sourcePreferences;

				targetPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						targetLayout, sourcePortletId);

				targetPortletPreferencesImpl =
					(PortletPreferencesImpl)targetPreferences;

				PortletPreferencesLocalServiceUtil.updatePreferences(
					targetPortletPreferencesImpl.getOwnerId(),
					targetPortletPreferencesImpl.getOwnerType(),
					targetPortletPreferencesImpl.getPlid(), sourcePortletId,
					sourcePreferences);
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			updateLayoutScopes(
				serviceContext.getUserId(), sourceLayout, targetLayout,
				sourcePreferences, targetPreferences, sourcePortletId,
				serviceContext.getLanguageId());
		}
	}

	@Override
	public void copyTypeSettings(Group sourceGroup, Group targetGroup)
		throws Exception {

		GroupServiceUtil.updateGroup(
			targetGroup.getGroupId(), sourceGroup.getTypeSettings());
	}

	@Override
	public Object[] deleteLayout(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long selPlid = ParamUtil.getLong(httpServletRequest, "selPlid");

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(httpServletRequest, "layoutId");

		Layout layout = null;

		if (selPlid <= 0) {
			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);
		}
		else {
			layout = LayoutLocalServiceUtil.getLayout(selPlid);

			groupId = layout.getGroupId();
			privateLayout = layout.isPrivateLayout();
			layoutId = layout.getLayoutId();
		}

		Group group = layout.getGroup();
		String oldFriendlyURL = themeDisplay.getLayoutFriendlyURL(layout);

		if (group.isStagingGroup() &&
			!GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, Group.class.getName(), group.getGroupId(),
				ActionKeys.MANAGE_STAGING, ActionKeys.PUBLISH_STAGING);
		}

		if (LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.DELETE)) {

			LayoutType layoutType = layout.getLayoutType();

			EventsProcessorUtil.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE,
				layoutType.getConfigurationActionDelete(), httpServletRequest,
				httpServletResponse);
		}

		if (group.isGuest() && !layout.isPrivateLayout() &&
			layout.isRootLayout()) {

			int count = LayoutLocalServiceUtil.getLayoutsCount(
				group, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (count == 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		LayoutServiceUtil.deleteLayout(
			groupId, privateLayout, layoutId, serviceContext);

		return new Object[] {
			group, oldFriendlyURL, LayoutConstants.DEFAULT_PLID
		};
	}

	@Override
	public Object[] deleteLayout(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return deleteLayout(
			PortalUtil.getHttpServletRequest(portletRequest),
			PortalUtil.getHttpServletResponse(portletResponse));
	}

	@Override
	public void deleteLayout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		deleteLayout(
			PortalUtil.getHttpServletRequest(renderRequest),
			PortalUtil.getHttpServletResponse(renderResponse));
	}

	@Override
	public File exportLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype,
			ServiceContext serviceContext)
		throws PortalException {

		User user = UserLocalServiceUtil.fetchUser(serviceContext.getUserId());

		if (user == null) {
			BackgroundTask backgroundTask =
				BackgroundTaskManagerUtil.fetchBackgroundTask(
					BackgroundTaskThreadLocal.getBackgroundTaskId());

			if (backgroundTask != null) {
				user = UserLocalServiceUtil.getUser(backgroundTask.getUserId());
			}
		}

		if (user == null) {
			user = UserLocalServiceUtil.getUser(
				GetterUtil.getLong(PrincipalThreadLocal.getName()));
		}

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		List<Layout> layoutSetPrototypeLayouts =
			LayoutLocalServiceUtil.getLayouts(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout());

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.FALSE.toString()});

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
					ExportImportHelperUtil.getLayoutIds(
						layoutSetPrototypeLayouts),
					parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		return ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	@Override
	public Long[] filterGroups(List<Group> groups, String[] groupKeys) {
		List<Long> groupIds = new ArrayList<>();

		for (Group group : groups) {
			if (!ArrayUtil.contains(groupKeys, group.getGroupKey())) {
				groupIds.add(group.getGroupId());
			}
		}

		return ArrayUtil.toArray(ArrayUtil.toLongArray(groupIds));
	}

	@Override
	public Layout getLayoutSetPrototypeLayout(Layout layout) {
		try {
			LayoutSet layoutSet = layout.getLayoutSet();

			if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
				return null;
			}

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSet.getLayoutSetPrototypeUuid(),
						layout.getCompanyId());

			return LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout.getSourcePrototypeLayoutUuid(),
				layoutSetPrototype.getGroupId(), true);
		}
		catch (Exception e) {
			_log.error(
				"Unable to fetch the the layout set prototype's layout", e);
		}

		return null;
	}

	@Override
	public Map<String, String[]> getLayoutSetPrototypeParameters(
		ServiceContext serviceContext) {

		Map<String, String[]> parameterMap = new LinkedHashMap<>();

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
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			});
		parameterMap.put(
			PortletDataHandlerKeys.LOGO,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	/**
	 * Returns the number of failed merge attempts for the layout prototype
	 * since its last reset or update.
	 *
	 * @param  layoutPrototype the page template being checked for failed merge
	 *         attempts
	 * @return the number of failed merge attempts for the layout prototype
	 */
	@Override
	public int getMergeFailCount(LayoutPrototype layoutPrototype)
		throws PortalException {

		if ((layoutPrototype == null) ||
			(layoutPrototype.getLayoutPrototypeId() == 0)) {

			return 0;
		}

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		return GetterUtil.getInteger(
			prototypeTypeSettingsProperties.getProperty(MERGE_FAIL_COUNT));
	}

	/**
	 * Returns the number of failed merge attempts for the layout set prototype
	 * since its last reset or update.
	 *
	 * @param  layoutSetPrototype the site template being checked for failed
	 *         merge attempts
	 * @return the number of failed merge attempts for the layout set prototype
	 */
	@Override
	public int getMergeFailCount(LayoutSetPrototype layoutSetPrototype)
		throws PortalException {

		if ((layoutSetPrototype == null) ||
			(layoutSetPrototype.getLayoutSetPrototypeId() == 0)) {

			return 0;
		}

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		return GetterUtil.getInteger(
			layoutSetPrototypeSettingsProperties.getProperty(MERGE_FAIL_COUNT));
	}

	@Override
	public List<Layout> getMergeFailFriendlyURLLayouts(LayoutSet layoutSet)
		throws PortalException {

		if (layoutSet == null) {
			return Collections.emptyList();
		}

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		String uuids = settingsProperties.getProperty(
			MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

		if (Validator.isNotNull(uuids)) {
			List<Layout> layouts = new ArrayList<>();

			for (String uuid : StringUtil.split(uuids)) {
				Layout layout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						uuid, layoutSet.getGroupId(),
						layoutSet.isPrivateLayout());

				layouts.add(layout);
			}

			return layouts;
		}

		return Collections.emptyList();
	}

	@Override
	public List<String> getOrganizationNames(Group group, User user)
		throws Exception {

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getGroupUserOrganizations(
				group.getGroupId(), user.getUserId());

		return ListUtil.toList(organizations, Organization.NAME_ACCESSOR);
	}

	@Override
	public List<String> getUserGroupNames(Group group, User user)
		throws Exception {

		List<UserGroup> userGroups =
			UserGroupLocalServiceUtil.getGroupUserUserGroups(
				group.getGroupId(), user.getUserId());

		return ListUtil.toList(userGroups, UserGroup.NAME_ACCESSOR);
	}

	@Override
	public void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		Map<String, String[]> parameterMap = getLayoutSetPrototypeParameters(
			serviceContext);

		parameterMap.put(
			PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
			new String[] {Boolean.FALSE.toString()});

		setLayoutSetPrototypeLinkEnabledParameter(
			parameterMap, layoutSet, serviceContext);

		User user = UserLocalServiceUtil.fetchUser(serviceContext.getUserId());

		if (user == null) {
			BackgroundTask backgroundTask =
				BackgroundTaskManagerUtil.fetchBackgroundTask(
					BackgroundTaskThreadLocal.getBackgroundTaskId());

			if (backgroundTask != null) {
				user = UserLocalServiceUtil.getUser(backgroundTask.getUserId());
			}
		}

		if (user == null) {
			user = UserLocalServiceUtil.getUser(
				GetterUtil.getLong(PrincipalThreadLocal.getName()));
		}

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					user.getUserId(), layoutSet.getGroupId(),
					layoutSet.isPrivateLayout(), null, parameterMap,
					user.getLocale(), user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		ExportImportServiceUtil.importLayouts(
			exportImportConfiguration, inputStream);
	}

	@Override
	public boolean isContentSharingWithChildrenEnabled(Group group) {
		int companyContentSharingEnabled = PrefsPropsUtil.getInteger(
			group.getCompanyId(),
			PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);

		if (companyContentSharingEnabled ==
				CONTENT_SHARING_WITH_CHILDREN_DISABLED) {

			return false;
		}

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		int groupContentSharingEnabled = GetterUtil.getInteger(
			typeSettingsProperties.getProperty(
				"contentSharingWithChildrenEnabled"),
			CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE);

		if ((groupContentSharingEnabled ==
				CONTENT_SHARING_WITH_CHILDREN_ENABLED) ||
			((companyContentSharingEnabled ==
				CONTENT_SHARING_WITH_CHILDREN_ENABLED_BY_DEFAULT) &&
			 (groupContentSharingEnabled ==
				 CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isFirstLayout(
		long groupId, boolean privateLayout, long layoutId) {

		Layout firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if ((firstLayout != null) && (firstLayout.getLayoutId() == layoutId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLayoutDeleteable(Layout layout) {
		try {
			if (layout instanceof VirtualLayout) {
				return false;
			}

			if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
				return true;
			}

			LayoutSet layoutSet = layout.getLayoutSet();

			if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
				return true;
			}

			if (LayoutLocalServiceUtil.hasLayoutSetPrototypeLayout(
					layoutSet.getLayoutSetPrototypeUuid(),
					layout.getCompanyId(),
					layout.getSourcePrototypeLayoutUuid())) {

				return false;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return true;
	}

	@Override
	public boolean isLayoutModifiedSinceLastMerge(Layout layout) {
		if ((layout == null) ||
			Validator.isNull(layout.getSourcePrototypeLayoutUuid()) ||
			layout.isLayoutPrototypeLinkActive() ||
			!isLayoutUpdateable(layout)) {

			return false;
		}

		long lastMergeTime = GetterUtil.getLong(
			layout.getTypeSettingsProperty(LAST_MERGE_TIME));

		if (lastMergeTime == 0) {
			return false;
		}

		Date existingLayoutModifiedDate = layout.getModifiedDate();

		if ((existingLayoutModifiedDate != null) &&
			(existingLayoutModifiedDate.getTime() > lastMergeTime)) {

			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the linked site template can be merged into
	 * the layout set. This method checks the current number of merge fail
	 * attempts stored for the linked site template and, if greater than the
	 * merge fail threshold, will return <code>false</code>.
	 *
	 * @param  group the site template's group, which is about to be merged into
	 *         the layout set
	 * @param  layoutSet the site in which the site template is attempting to
	 *         merge into
	 * @return <code>true</code> if the linked site template can be merged into
	 *         the layout set; <code>false</code> otherwise
	 */
	@Override
	public boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException {

		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return false;
		}

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		long lastMergeTime = GetterUtil.getLong(
			settingsProperties.getProperty(LAST_MERGE_TIME));

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		Date modifiedDate = layoutSetPrototype.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return false;
		}

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			layoutSetPrototypeSettingsProperties.getProperty(MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Merge not performed because the fail threshold ");
				sb.append("was reached for layoutSetPrototypeId ");
				sb.append(layoutSetPrototype.getLayoutSetPrototypeId());
				sb.append(" and layoutId ");
				sb.append(layoutSetPrototypeLayoutSet.getLayoutSetId());
				sb.append(". Update the count in the database to try again.");

				_log.warn(sb.toString());
			}

			return false;
		}

		return true;
	}

	@Override
	public boolean isLayoutSetPrototypeUpdateable(LayoutSet layoutSet) {
		if (!layoutSet.isLayoutSetPrototypeLinkActive()) {
			return true;
		}

		try {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSet.getLayoutSetPrototypeUuid(),
						layoutSet.getCompanyId());

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

	@Override
	public boolean isLayoutSortable(Layout layout) {
		return isLayoutDeleteable(layout);
	}

	@Override
	public boolean isLayoutUpdateable(Layout layout) {
		try {
			if (layout instanceof VirtualLayout) {
				return false;
			}

			if (Validator.isNull(layout.getLayoutPrototypeUuid()) &&
				Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {

				return true;
			}

			LayoutSet layoutSet = layout.getLayoutSet();

			if (layoutSet.isLayoutSetPrototypeLinkActive()) {
				boolean layoutSetPrototypeUpdateable =
					isLayoutSetPrototypeUpdateable(layoutSet);

				if (!layoutSetPrototypeUpdateable) {
					return false;
				}

				Layout layoutSetPrototypeLayout = getLayoutSetPrototypeLayout(
					layout);

				if (layoutSetPrototypeLayout == null) {
					return true;
				}

				String layoutUpdateable =
					layoutSetPrototypeLayout.getTypeSettingsProperty(
						LAYOUT_UPDATEABLE);

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

	@Override
	public boolean isUserGroupLayout(Layout layout) throws PortalException {
		if (!(layout instanceof VirtualLayout)) {
			return false;
		}

		VirtualLayout virtualLayout = (VirtualLayout)layout;

		Layout sourceLayout = virtualLayout.getSourceLayout();

		Group sourceGroup = sourceLayout.getGroup();

		if (sourceGroup.isUserGroup()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isUserGroupLayoutSetViewable(
			PermissionChecker permissionChecker, Group userGroupGroup)
		throws PortalException {

		if (!userGroupGroup.isUserGroup()) {
			return false;
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, userGroupGroup, ActionKeys.VIEW)) {

			return true;
		}

		UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
			userGroupGroup.getClassPK());

		if (UserLocalServiceUtil.hasUserGroupUser(
				userGroup.getUserGroupId(), permissionChecker.getUserId())) {

			return true;
		}

		return false;
	}

	@Override
	public void mergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception {

		String sourcePrototypeLayoutUuid =
			layout.getSourcePrototypeLayoutUuid();

		if (Validator.isNull(sourcePrototypeLayoutUuid)) {
			doMergeLayoutPrototypeLayout(group, layout);

			return;
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		long layoutSetPrototypeId = layoutSet.getLayoutSetPrototypeId();

		if (layoutSetPrototypeId > 0) {
			Group layoutSetPrototypeGroup =
				GroupLocalServiceUtil.getLayoutSetPrototypeGroup(
					layout.getCompanyId(), layoutSetPrototypeId);

			Layout sourcePrototypeLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					sourcePrototypeLayoutUuid,
					layoutSetPrototypeGroup.getGroupId(), true);

			if (sourcePrototypeLayout != null) {
				doMergeLayoutPrototypeLayout(
					layoutSetPrototypeGroup, sourcePrototypeLayout);
			}
		}

		doMergeLayoutPrototypeLayout(group, layout);
	}

	@Override
	public void mergeLayoutSetPrototypeLayouts(Group group, LayoutSet layoutSet)
		throws Exception {

		layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		if (!isLayoutSetMergeable(group, layoutSet)) {
			return;
		}

		String owner = _acquireLock(
			LayoutSet.class.getName(), layoutSet.getLayoutSetId(),
			PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_LOCK_MAX_TIME);

		if (owner == null) {
			return;
		}

		EntityCacheUtil.clearLocalCache();

		layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		try {
			MergeLayoutPrototypesThreadLocal.setInProgress(true);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Applying layout set prototype ",
						layoutSetPrototype.getUuid(), " (mvccVersion ",
						layoutSetPrototype.getMvccVersion(), ") to layout set ",
						layoutSet.getLayoutSetId(), " (mvccVersion ",
						layoutSet.getMvccVersion(), ")"));
			}

			boolean importData = true;

			long lastMergeTime = GetterUtil.getLong(
				settingsProperties.getProperty(LAST_MERGE_TIME));
			long lastResetTime = GetterUtil.getLong(
				settingsProperties.getProperty(LAST_RESET_TIME));

			if ((lastMergeTime > 0) || (lastResetTime > 0)) {
				importData = false;
			}

			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				layoutSet.getLayoutSetId());

			if (!isLayoutSetMergeable(group, layoutSet)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping actual merge");
				}

				return;
			}

			removeMergeFailFriendlyURLLayouts(layoutSet);

			Map<String, String[]> parameterMap =
				getLayoutSetPrototypesParameters(importData);

			importLayoutSetPrototype(
				layoutSetPrototype, layoutSet.getGroupId(),
				layoutSet.isPrivateLayout(), parameterMap, importData);
		}
		catch (Exception e) {
			LayoutSet layoutSetPrototypeLayoutSet =
				layoutSetPrototype.getLayoutSet();

			UnicodeProperties layoutSetPrototypeSettingsProperties =
				layoutSetPrototypeLayoutSet.getSettingsProperties();

			int mergeFailCount = GetterUtil.getInteger(
				layoutSetPrototypeSettingsProperties.getProperty(
					MERGE_FAIL_COUNT));

			mergeFailCount++;

			StringBundler sb = new StringBundler(6);

			sb.append("Merge fail count increased to ");
			sb.append(mergeFailCount);
			sb.append(" for layout set prototype ");
			sb.append(layoutSetPrototype.getLayoutSetPrototypeId());
			sb.append(" and layout set ");
			sb.append(layoutSet.getLayoutSetId());

			_log.error(sb.toString(), e);

			layoutSetPrototypeSettingsProperties.setProperty(
				MERGE_FAIL_COUNT, String.valueOf(mergeFailCount));

			// Invoke updateImpl so that we do not trigger the listeners

			LayoutSetUtil.updateImpl(layoutSetPrototypeLayoutSet);
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setInProgress(false);

			_releaseLock(
				LayoutSet.class.getName(), layoutSet.getLayoutSetId(), owner);
		}
	}

	@Override
	public void removeMergeFailFriendlyURLLayouts(LayoutSet layoutSet)
		throws PortalException {

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		if (settingsProperties.containsKey(MERGE_FAIL_FRIENDLY_URL_LAYOUTS)) {
			settingsProperties.remove(MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}
	}

	/**
	 * Checks the permissions necessary for resetting the layout. If sufficient,
	 * the layout is reset by calling {@link #doResetPrototype(Layout)}.
	 *
	 * @param layout the page being checked for sufficient permissions
	 */
	@Override
	public void resetPrototype(Layout layout) throws PortalException {
		checkResetPrototypePermissions(layout.getGroup(), layout);

		doResetPrototype(layout);
	}

	/**
	 * Checks the permissions necessary for resetting the layout set. If
	 * sufficient, the layout set is reset by calling {@link
	 * #doResetPrototype(LayoutSet)}.
	 *
	 * @param layoutSet the site being checked for sufficient permissions
	 */
	@Override
	public void resetPrototype(LayoutSet layoutSet) throws PortalException {
		checkResetPrototypePermissions(layoutSet.getGroup(), null);

		doResetPrototype(layoutSet);
	}

	/**
	 * Sets the number of failed merge attempts for the layout prototype to a
	 * new value.
	 *
	 * @param layoutPrototype the page template of the counter being updated
	 * @param newMergeFailCount the new value of the counter
	 */
	@Override
	public void setMergeFailCount(
			LayoutPrototype layoutPrototype, int newMergeFailCount)
		throws PortalException {

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		boolean updateLayoutPrototypeLayout = false;

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		if (newMergeFailCount == 0) {
			if (prototypeTypeSettingsProperties.containsKey(MERGE_FAIL_COUNT)) {
				prototypeTypeSettingsProperties.remove(MERGE_FAIL_COUNT);

				updateLayoutPrototypeLayout = true;
			}
		}
		else {
			prototypeTypeSettingsProperties.setProperty(
				MERGE_FAIL_COUNT, String.valueOf(newMergeFailCount));

			updateLayoutPrototypeLayout = true;
		}

		if (updateLayoutPrototypeLayout) {
			LayoutServiceUtil.updateLayout(
				layoutPrototypeLayout.getGroupId(),
				layoutPrototypeLayout.isPrivateLayout(),
				layoutPrototypeLayout.getLayoutId(),
				layoutPrototypeLayout.getTypeSettings());
		}
	}

	/**
	 * Sets the number of failed merge attempts for the layout set prototype to
	 * a new value.
	 *
	 * @param layoutSetPrototype the site template of the counter being updated
	 * @param newMergeFailCount the new value of the counter
	 */
	@Override
	public void setMergeFailCount(
			LayoutSetPrototype layoutSetPrototype, int newMergeFailCount)
		throws PortalException {

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		boolean updateLayoutSetPrototypeLayoutSet = false;

		UnicodeProperties layoutSetPrototypeSettingsProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		if (newMergeFailCount == 0) {
			if (layoutSetPrototypeSettingsProperties.containsKey(
					MERGE_FAIL_COUNT)) {

				layoutSetPrototypeSettingsProperties.remove(MERGE_FAIL_COUNT);

				updateLayoutSetPrototypeLayoutSet = true;
			}
		}
		else {
			layoutSetPrototypeSettingsProperties.setProperty(
				MERGE_FAIL_COUNT, String.valueOf(newMergeFailCount));

			updateLayoutSetPrototypeLayoutSet = true;
		}

		if (updateLayoutSetPrototypeLayoutSet) {
			LayoutSetServiceUtil.updateSettings(
				layoutSetPrototypeLayoutSet.getGroupId(),
				layoutSetPrototypeLayoutSet.isPrivateLayout(),
				layoutSetPrototypeLayoutSet.getSettings());
		}
	}

	@Override
	public void updateLayoutScopes(
			long userId, Layout sourceLayout, Layout targetLayout,
			PortletPreferences sourcePreferences,
			PortletPreferences targetPreferences, String sourcePortletId,
			String languageId)
		throws Exception {

		String scopeType = GetterUtil.getString(
			sourcePreferences.getValue("lfrScopeType", null));

		if (Validator.isNull(scopeType) || !scopeType.equals("layout")) {
			return;
		}

		Layout targetScopeLayout =
			LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
				targetLayout.getUuid(), targetLayout.getGroupId(),
				targetLayout.isPrivateLayout());

		if (!targetScopeLayout.hasScopeGroup()) {
			GroupLocalServiceUtil.addGroup(
				userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
				Layout.class.getName(), targetLayout.getPlid(),
				GroupConstants.DEFAULT_LIVE_GROUP_ID, targetLayout.getNameMap(),
				null, 0, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				null, false, true, null);
		}

		String portletTitle = PortalUtil.getPortletTitle(
			PortletIdCodec.decodePortletName(sourcePortletId), languageId);

		String newPortletTitle = PortalUtil.getNewPortletTitle(
			portletTitle, String.valueOf(sourceLayout.getLayoutId()),
			targetLayout.getName(languageId));

		targetPreferences.setValue(
			"groupId", String.valueOf(targetLayout.getGroupId()));
		targetPreferences.setValue("lfrScopeType", "layout");
		targetPreferences.setValue(
			"lfrScopeLayoutUuid", targetLayout.getUuid());
		targetPreferences.setValue(
			"portletSetupTitle_" + languageId, newPortletTitle);
		targetPreferences.setValue(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString());

		targetPreferences.store();
	}

	@Override
	public void updateLayoutSetPrototypesLinks(
			Group group, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId,
			boolean publicLayoutSetPrototypeLinkEnabled,
			boolean privateLayoutSetPrototypeLinkEnabled)
		throws Exception {

		updateLayoutSetPrototypeLink(
			group.getGroupId(), true, privateLayoutSetPrototypeId,
			privateLayoutSetPrototypeLinkEnabled);
		updateLayoutSetPrototypeLink(
			group.getGroupId(), false, publicLayoutSetPrototypeId,
			publicLayoutSetPrototypeLinkEnabled);
	}

	/**
	 * Checks the permissions necessary for resetting the layout or site. If the
	 * permissions are not sufficient, a {@link PortalException} is thrown.
	 *
	 * @param group the site being checked for sufficient permissions
	 * @param layout the page being checked for sufficient permissions
	 *        (optionally <code>null</code>). If <code>null</code>, the
	 *        permissions are only checked for resetting the site.
	 */
	protected void checkResetPrototypePermissions(Group group, Layout layout)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((layout != null) &&
			!LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.UPDATE)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, layout.getName(), layout.getLayoutId(),
				ActionKeys.UPDATE);
		}
		else if (!GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.UPDATE) &&
				 (!group.isUser() ||
				  (permissionChecker.getUserId() != group.getClassPK()))) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, group.getName(), group.getGroupId(),
				ActionKeys.UPDATE);
		}
	}

	protected void deleteUnreferencedPortlets(
			List<String> targetLayoutPortletIds, Layout targetLayout,
			Layout sourceLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutType =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> unreferencedPortletIds = new ArrayList<>(
			targetLayoutPortletIds);

		unreferencedPortletIds.removeAll(sourceLayoutType.getPortletIds());

		PortletLocalServiceUtil.deletePortlets(
			targetLayout.getCompanyId(),
			unreferencedPortletIds.toArray(new String[0]),
			targetLayout.getPlid());
	}

	protected void doMergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception {

		if (!layout.isLayoutPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.hasStagingGroup()) {

			return;
		}

		long lastMergeTime = GetterUtil.getLong(
			layout.getTypeSettingsProperty(LAST_MERGE_TIME));

		if (lastMergeTime == 0) {
			try {
				MergeLayoutPrototypesThreadLocal.setInProgress(true);

				Layout targetLayout = LayoutLocalServiceUtil.getLayout(
					layout.getPlid());

				if (targetLayout != null) {
					lastMergeTime = GetterUtil.getLong(
						targetLayout.getTypeSettingsProperty(LAST_MERGE_TIME));
				}
			}
			finally {
				MergeLayoutPrototypesThreadLocal.setInProgress(false);
			}
		}

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.
				getLayoutPrototypeByUuidAndCompanyId(
					layout.getLayoutPrototypeUuid(), layout.getCompanyId());

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		Date modifiedDate = layoutPrototypeLayout.getModifiedDate();

		if (lastMergeTime >= modifiedDate.getTime()) {
			return;
		}

		UnicodeProperties prototypeTypeSettingsProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			prototypeTypeSettingsProperties.getProperty(MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Merge not performed because the fail threshold ");
				sb.append("was reached for layoutPrototypeId ");
				sb.append(layoutPrototype.getLayoutPrototypeId());
				sb.append(" and layoutId ");
				sb.append(layoutPrototypeLayout.getLayoutId());
				sb.append(". Update the count in the database to try again.");

				_log.warn(sb.toString());
			}

			return;
		}

		String owner = _acquireLock(
			Layout.class.getName(), layout.getPlid(),
			PropsValues.LAYOUT_PROTOTYPE_MERGE_LOCK_MAX_TIME);

		if (owner == null) {
			return;
		}

		EntityCacheUtil.clearLocalCache();

		layout = LayoutLocalServiceUtil.fetchLayout(layout.getPlid());

		try {
			MergeLayoutPrototypesThreadLocal.setInProgress(true);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Applying layout prototype ", layoutPrototype.getUuid(),
						" (mvccVersion ", layoutPrototype.getMvccVersion(),
						") to layout ", layout.getPlid(), " (mvccVersion ",
						layout.getMvccVersion(), ")"));
			}

			applyLayoutPrototype(layoutPrototype, layout, true);
		}
		catch (Exception e) {
			_log.error(e, e);

			prototypeTypeSettingsProperties.setProperty(
				MERGE_FAIL_COUNT, String.valueOf(++mergeFailCount));

			// Invoke updateImpl so that we do not trigger the listeners

			LayoutUtil.updateImpl(layoutPrototypeLayout);
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setInProgress(false);

			_releaseLock(Layout.class.getName(), layout.getPlid(), owner);
		}
	}

	/**
	 * Resets the modified timestamp on the layout, and then calls {@link
	 * #doResetPrototype(LayoutSet)} to reset the modified timestamp on the
	 * layout's site.
	 *
	 * <p>
	 * After the timestamps are reset, the modified page template and site
	 * template are merged into their linked layout and site when they are first
	 * accessed.
	 * </p>
	 *
	 * @param layout the page having its timestamp reset
	 */
	protected void doResetPrototype(Layout layout) throws PortalException {
		layout.setModifiedDate(null);

		LayoutLocalServiceUtil.updateLayout(layout);

		doResetPrototype(layout.getLayoutSet());
	}

	/**
	 * Resets the modified timestamp on the layout set.
	 *
	 * <p>
	 * After the timestamp is reset, the modified site template is merged into
	 * its linked layout set when it is first accessed.
	 * </p>
	 *
	 * @param layoutSet the site having its timestamp reset
	 */
	protected void doResetPrototype(LayoutSet layoutSet)
		throws PortalException {

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		settingsProperties.remove(LAST_MERGE_TIME);

		settingsProperties.setProperty(
			LAST_RESET_TIME, String.valueOf(System.currentTimeMillis()));

		LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
	}

	protected File exportLayoutSetPrototype(
		User user, LayoutSetPrototype layoutSetPrototype,
		Map<String, String[]> parameterMap, String cacheFileName) {

		File cacheFile = null;

		if (cacheFileName != null) {
			cacheFile = new File(cacheFileName);

			if (cacheFile.exists()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using cached layout set prototype LAR file " +
							cacheFile.getAbsolutePath());
				}

				return cacheFile;
			}
		}

		long layoutSetPrototypeGroupId = 0;

		try {
			layoutSetPrototypeGroupId = layoutSetPrototype.getGroupId();
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get groupId for layout set prototype " +
					layoutSetPrototype.getLayoutSetPrototypeId(),
				pe);

			return null;
		}

		List<Layout> layoutSetPrototypeLayouts = null;

		layoutSetPrototypeLayouts = LayoutLocalServiceUtil.getLayouts(
			layoutSetPrototypeGroupId, true);

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layoutSetPrototypeGroupId, true,
					ExportImportHelperUtil.getLayoutIds(
						layoutSetPrototypeLayouts),
					parameterMap);

		ExportImportConfiguration exportImportConfiguration = null;

		try {
			exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						exportLayoutSettingsMap);
		}
		catch (PortalException pe) {
			_log.error("Unable to add draft export-import configuration", pe);

			return null;
		}

		File file = null;

		try {
			file = ExportImportLocalServiceUtil.exportLayoutsAsFile(
				exportImportConfiguration);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to export layout set prototype " +
					layoutSetPrototype.getLayoutSetPrototypeId(),
				pe);

			return null;
		}

		if (cacheFile == null) {
			return file;
		}

		try {
			FileUtil.copyFile(file, cacheFile);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Copied ", file.getAbsolutePath(), " to ",
						cacheFile.getAbsolutePath()));
			}
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to copy file ", file.getAbsolutePath(), " to ",
					cacheFile.getAbsolutePath()),
				e);
		}

		return cacheFile;
	}

	protected Map<String, String[]> getLayoutSetPrototypesParameters(
		boolean importData) {

		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		if (importData) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
			parameterMap.put(
				PortletDataHandlerKeys.LOGO,
				new String[] {Boolean.TRUE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.TRUE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.TRUE.toString()});
		}
		else {
			if (PropsValues.LAYOUT_SET_PROTOTYPE_PROPAGATE_LOGO) {
				parameterMap.put(
					PortletDataHandlerKeys.LOGO,
					new String[] {Boolean.TRUE.toString()});
			}
			else {
				parameterMap.put(
					PortletDataHandlerKeys.LOGO,
					new String[] {Boolean.FALSE.toString()});
			}

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.FALSE.toString()});
		}

		return parameterMap;
	}

	protected void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, long groupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			boolean importData)
		throws PortalException {

		File file = null;

		User user = UserLocalServiceUtil.getDefaultUser(
			layoutSetPrototype.getCompanyId());

		if (importData) {
			file = exportLayoutSetPrototype(
				user, layoutSetPrototype, parameterMap, null);
		}
		else {
			String cacheFileName = StringBundler.concat(
				_TEMP_DIR, layoutSetPrototype.getUuid(), ".v",
				layoutSetPrototype.getMvccVersion(), ".lar");

			file = _exportInProgressMap.computeIfAbsent(
				cacheFileName,
				fileName -> exportLayoutSetPrototype(
					user, layoutSetPrototype, parameterMap, fileName));

			_exportInProgressMap.remove(cacheFileName);
		}

		if (file == null) {
			return;
		}

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					user.getUserId(), groupId, privateLayout, null,
					parameterMap, user.getLocale(), user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					user.getUserId(), groupId, StringPool.BLANK,
					StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap, WorkflowConstants.STATUS_DRAFT,
					new ServiceContext());

		ExportImportLocalServiceUtil.importLayoutsDataDeletions(
			exportImportConfiguration, file);

		ExportImportLocalServiceUtil.importLayouts(
			exportImportConfiguration, file);
	}

	protected void setLayoutSetPrototypeLinkEnabledParameter(
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

	protected void updateLayoutSetPrototypeLink(
			long groupId, boolean privateLayout, long layoutSetPrototypeId,
			boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		String layoutSetPrototypeUuid = null;

		if (layoutSetPrototypeId > 0) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
					layoutSetPrototypeId);

			if (layoutSetPrototype != null) {
				layoutSetPrototypeUuid = layoutSetPrototype.getUuid();

				// Merge without enabling the link

				if (!layoutSetPrototypeLinkEnabled &&
					(layoutSetPrototypeId > 0)) {

					Map<String, String[]> parameterMap =
						getLayoutSetPrototypesParameters(true);

					importLayoutSetPrototype(
						layoutSetPrototype, groupId, privateLayout,
						parameterMap, true);
				}
			}
		}

		LayoutSetServiceUtil.updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled,
			layoutSetPrototypeUuid);

		LayoutLocalServiceUtil.updatePriorities(groupId, privateLayout);

		// Force propagation from site template to site. See LPS-48206.

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		mergeLayoutSetPrototypeLayouts(group, layoutSet);
	}

	private String _acquireLock(
		String className, long classPK, long mergeLockMaxTime) {

		String owner = PortalUUIDUtil.generate();

		try {
			Lock lock = LockManagerUtil.lock(
				SitesImpl.class.getName(), String.valueOf(classPK), owner);

			// Double deep check

			if (!owner.equals(lock.getOwner())) {
				Date createDate = lock.getCreateDate();

				if ((System.currentTimeMillis() - createDate.getTime()) >=
						mergeLockMaxTime) {

					// Acquire lock if the lock is older than the lock max time

					lock = LockManagerUtil.lock(
						SitesImpl.class.getName(), String.valueOf(classPK),
						lock.getOwner(), owner);

					// Check if acquiring the lock succeeded or if another
					// process has the lock

					if (!owner.equals(lock.getOwner())) {
						return null;
					}
				}
				else {
					return null;
				}
			}
		}
		catch (Exception e) {
			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Acquired lock for ", SitesImpl.class.getName(),
					" to update ", className, StringPool.POUND, classPK));
		}

		return owner;
	}

	private void _releaseLock(String className, long classPK, String owner) {
		LockManagerUtil.unlock(
			SitesImpl.class.getName(), String.valueOf(classPK), owner);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Released lock for ", SitesImpl.class.getName(),
					" to update ", className, StringPool.POUND, classPK));
		}
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/layout_set_prototype/";

	private static final Log _log = LogFactoryUtil.getLog(SitesImpl.class);

	private final ConcurrentHashMap<String, File> _exportInProgressMap =
		new ConcurrentHashMap<>();

}