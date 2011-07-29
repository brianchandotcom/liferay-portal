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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.messaging.LayoutsLocalPublisherRequest;
import com.liferay.portal.messaging.LayoutsRemotePublisherRequest;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class LayoutServiceImpl extends LayoutServiceBaseImpl {

	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> keywordsMap,
			Map<Locale, String> robotsMap, String type,	boolean hidden,
			String friendlyURL, boolean locked, ServiceContext serviceContext)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.addLayout(
			getUserId(), groupId, privateLayout, parentLayoutId, localeNamesMap,
			localeTitlesMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURL, locked, serviceContext);
	}

	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			String name, String title, String description, String type,
			boolean hidden, String friendlyURL, boolean locked,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), new HashMap<Locale, String>(),
			new HashMap<Locale, String>(), new HashMap<Locale, String>(),
			type, hidden, friendlyURL, locked, serviceContext);
	}

	public void deleteLayout(long plid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.DELETE);

		layoutLocalService.deleteLayout(plid, serviceContext);
	}

	public void deleteLayout(
			long groupId, boolean privateLayout, long layoutId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.DELETE);

		layoutLocalService.deleteLayout(
			groupId, privateLayout, layoutId, serviceContext);
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportLayouts(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportLayouts(
			groupId, privateLayout, parameterMap, startDate, endDate);
	}

	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	public byte[] exportPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportPortletInfo(
			plid, groupId, portletId, parameterMap, startDate, endDate);
	}

	public File exportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportPortletInfoAsFile(
			plid, groupId, portletId, parameterMap, startDate, endDate);
	}

	public long getDefaultPlid(
			long groupId, long scopeGroupId, boolean privateLayout,
			String portletId)
		throws PortalException, SystemException {

		if (groupId <= 0) {
			return LayoutConstants.DEFAULT_PLID;
		}

		String scopeGroupLayoutUuid = null;

		Group scopeGroup = groupLocalService.getGroup(scopeGroupId);

		if (scopeGroup.isLayout()) {
			Layout scopeGroupLayout = layoutLocalService.getLayout(
				scopeGroup.getClassPK());

			scopeGroupLayoutUuid = scopeGroupLayout.getUuid();
		}

		List<Layout> layouts = layoutPersistence.filterFindByG_P(
			groupId, privateLayout);

		for (Layout layout : layouts) {
			if (!layout.isTypePortlet()) {
				continue;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (!layoutTypePortlet.hasPortletId(portletId)) {
				continue;
			}

			javax.portlet.PortletPreferences jxPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);

			String scopeType = GetterUtil.getString(
				jxPreferences.getValue("lfr-scope-type", null));

			if (scopeGroup.isLayout()) {
				String scopeLayoutUuid = GetterUtil.getString(
					jxPreferences.getValue("lfrScopeLayoutUuid", null));

				if (Validator.isNotNull(scopeType) &&
					Validator.isNotNull(scopeLayoutUuid) &&
					scopeLayoutUuid.equals(scopeGroupLayoutUuid)) {

					return layout.getPlid();
				}
			}
			else if (scopeGroup.isCompany()) {
				if (Validator.isNotNull(scopeType) &&
					scopeType.equals("company")) {

					return layout.getPlid();
				}
			}
			else {
				if (Validator.isNull(scopeType)) {
					return layout.getPlid();
				}
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public String getLayoutName(
			long groupId, boolean privateLayout, long layoutId,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		return layout.getName(languageId);
	}

	public LayoutReference[] getLayoutReferences(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue)
		throws SystemException {

		return layoutLocalService.getLayouts(
			companyId, portletId, preferencesKey, preferencesValue);
	}

	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, bytes);
	}

	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_LAYOUTS) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, file);
	}

	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, is);
	}

	public void importPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importPortletInfo(
			getUserId(), plid, groupId, portletId, parameterMap, file);
	}

	public void importPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importPortletInfo(
			getUserId(), plid, groupId, portletId, parameterMap, is);
	}

	public void schedulePublishToLive(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String scope, Date startDate, Date endDate, String groupName,
			String cronText, Date schedulerStartDate, Date schedulerEndDate,
			String description)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!GroupPermissionUtil.contains(
				permissionChecker, targetGroupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, targetGroupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}

		String jobName = PortalUUIDUtil.generate();

		Trigger trigger = new CronTrigger(
			jobName, groupName, schedulerStartDate, schedulerEndDate,
			cronText);

		String command = StringPool.BLANK;

		if (scope.equals("all-pages")) {
			command = LayoutsLocalPublisherRequest.COMMAND_ALL_PAGES;
		}
		else if (scope.equals("selected-pages")) {
			command = LayoutsLocalPublisherRequest.COMMAND_SELECTED_PAGES;
		}

		LayoutsLocalPublisherRequest publisherRequest =
			new LayoutsLocalPublisherRequest(
				command, getUserId(), sourceGroupId, targetGroupId,
				privateLayout, layoutIdMap, parameterMap, startDate, endDate);

		SchedulerEngineUtil.schedule(
			trigger, StorageType.PERSISTED, description,
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, publisherRequest, 0);
	}

	public void schedulePublishToRemote(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, String remoteAddress,
			int remotePort, boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate,
			String groupName, String cronText, Date schedulerStartDate,
			Date schedulerEndDate, String description)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		Group group = groupLocalService.getGroup(sourceGroupId);

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		GroupPermissionUtil.contains(
			permissionChecker, sourceGroupId, ActionKeys.PUBLISH_STAGING);

		LayoutsRemotePublisherRequest publisherRequest =
			new LayoutsRemotePublisherRequest(
				getUserId(), sourceGroupId, privateLayout, layoutIdMap,
				parameterMap, remoteAddress, remotePort, secureConnection,
				remoteGroupId, remotePrivateLayout, startDate, endDate);

		String jobName = PortalUUIDUtil.generate();

		Trigger trigger =
			new CronTrigger(
				jobName, groupName, schedulerStartDate, schedulerEndDate,
				cronText);
		SchedulerEngineUtil.schedule(
			trigger, StorageType.PERSISTED, description,
			DestinationNames.LAYOUTS_REMOTE_PUBLISHER, publisherRequest, 0);
	}

	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds, serviceContext);
	}

	public void unschedulePublishToLive(
			long groupId, String jobName, String groupName)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}

		SchedulerEngineUtil.delete(jobName, groupName, StorageType.PERSISTED);
	}

	public void unschedulePublishToRemote(
			long groupId, String jobName, String groupName)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		Group group = groupLocalService.getGroup(groupId);

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		GroupPermissionUtil.contains(
			permissionChecker, groupId, ActionKeys.PUBLISH_STAGING);

		SchedulerEngineUtil.delete(jobName, groupName, StorageType.PERSISTED);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> keywordsMap,
			Map<Locale, String> robotsMap, String type, boolean hidden,
			String friendlyURL, Boolean iconImage, byte[] iconBytes,
			boolean locked, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, localeNamesMap,
			localeTitlesMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURL, iconImage, iconBytes, locked, serviceContext);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, typeSettings);
	}

	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		pluginSettingLocalService.checkPermission(
			getUserId(), themeId, Plugin.TYPE_THEME);

		return layoutLocalService.updateLookAndFeel(
			groupId, privateLayout, layoutId, themeId, colorSchemeId, css,
			wapTheme);
	}

	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateName(
			groupId, privateLayout, layoutId, name, languageId);
	}

	public Layout updateName(long plid, String name, String languageId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateName(plid, name, languageId);
	}

	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);
	}

	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateParentLayoutId(plid, parentPlid);
	}

	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(
			groupId, privateLayout, layoutId, priority);
	}

	public Layout updatePriority(long plid, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(plid, priority);
	}

}