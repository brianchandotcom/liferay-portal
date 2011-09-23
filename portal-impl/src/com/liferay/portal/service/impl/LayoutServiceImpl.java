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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *	The implementation of the layout service.
 *
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class LayoutServiceImpl extends LayoutServiceBaseImpl {

	/**
	 * Adds a Layout
	 *
	 * <p>
	 * This method handles the creation of the layout including its resources,
	 * metadata, and internal data structures. It is not necessary to make
	 * subsequent calls to any methods to setup default groups, resources, etc.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group, or not
	 * @param  parentLayoutId the primary key of the parent layout (optionally
	 *         <code>DEFAULT_PARENT_LAYOUT_ID</code>). See {@link 
	 *         com.liferay.portal.model.LayoutConstants</code>}.
	 * @param  localeNamesMap the layout's locales and localized names
	 * @param  localeTitlesMap the layout's locales and localized titles
	 * @param  descriptionMap the layout's locales and localized  
	 * 		   descriptions
	 * @param  keywordsMap the layout's locales and localized keywords 
	 * @param  robotsMap the layout's locales and localized robotses 
	 * @param  type the layout's type (optionally <code>TYPE_PORTLET</code>). 
	 * 		   See {@link com.liferay.portal.model.LayoutConstants}.
	 * @param  hidden whether the layout is hidden, or not.
	 * @param  friendlyURL the layout's friendly URL (optionally 
	 * 		   <code>DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL</code> or
	 * 		   <code>DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL</code>). See {@link
	 * 		   com.liferay.portal.util.PropsValues} and {@link 
	 *         com.liferay.portal.util.FriendlyURLNormalizer}.
	 * @param  locked whether the layout is locked or not.
	 * @param  serviceContext the organization's service context. Must specify
	 * 		   the replacement Uuid and can specify the replacement create date,
	 *  	   replacement modified date and the new expando bridge attributes.
	 * @return the added Layout
	 * @throws PortalException if a group or user with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Adds a Layout
	 *
	 * <p>
	 * This method handles the creation of the layout including its resources,
	 * metadata, and internal data structures. It is not necessary to make
	 * subsequent calls to any methods to setup default groups, resources, etc.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  parentLayoutId the primary key of the parent layout (optionally
	 *         <code>DEFAULT_PARENT_LAYOUT_ID</code>). See {@link 
	 *         com.liferay.portal.model.LayoutConstants</code>}.
	 * @param  name the layout's name. See  DEFAULT_USER_PRIVATE_LAYOUT_NAME and
	 *         DEFAULT_USER_PUBLIC_LAYOUT_NAME at PropsValues to set a default
	 *         value
	 * @param  title the layout's title.
	 * @param  description the layout's description.
	 * @param  type the layout's type (optionally <code>TYPE_PORTLET</code>).
	 * 		   See {@link com.liferay.portal.model.LayoutConstants</code>}.
	 * @param  hidden whether the layout is hidden or not.
	 * @param  friendlyURL the layout's friendly URL (optionally 
	 * 		   <code>DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL</code> or
	 * 		   <code>DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL</code>). See {@link
	 * 		   com.liferay.portal.util.PropsValues} and {@link 
	 *         com.liferay.portal.util.FriendlyURLNormalizer#normalize(String)}.
	 * @param  locked whether the layout is locked or not.
	 * @param  serviceContext the organization's service context. Must specify
	 * 		   the replacement Uuid and can specify the replacement create date,
	 *  	   replacement modified date and the new expando bridge attributes.
	 * @return the added Layout
	 * @throws PortalException if a group or user with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Deletes a layout
	 *
	 * @param  plid the primary key of the layout
	 * @param  serviceContext the organization's service context
	 * @throws PortalException if a layout or any object with the primary key
	 *         at layout or could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLayout(long plid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.DELETE);

		layoutLocalService.deleteLayout(plid, serviceContext);
	}

	/**
	 * Deletes a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  serviceContext the organization's service context
	 * @throws PortalException if a group or any object with the primary key at
	 *         layout or could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Exports layouts as a byte array
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutIds the primary keys of the layouts to be exported
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be exported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  startDate the start date of the export
	 * @param  endDate the end date of the export
	 * @return the layout as a byte array
	 * @throws PortalException if a group or any layout with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Exports layouts as a byte array
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be exported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  startDate the start date of the export
	 * @param  endDate the end date of the export
	 * @return the layout as a byte array
	 * @throws PortalException if a group with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportLayouts(
			groupId, privateLayout, parameterMap, startDate, endDate);
	}

	/**
	 * Exports layouts as a File
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutIds the primary keys of the layouts to be exported
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be exported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  startDate the start date of the export
	 * @param  endDate the end date of the export
	 * @return the layout as a File
	 * @throws PortalException if a group or any layout with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Exports the portlet information (categories, permissions, etc.) as a
	 * byte array
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be exported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  startDate the start date of the export
	 * @param  endDate the end date of the export
	 * @return the portlet information as a byte array
	 * @throws PortalException if a group or portlet with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Exports the portlet information (categories, permissions, etc.) as a
	 * file
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be exported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  startDate the start date of the export
	 * @param  endDate the end date of the export
	 * @return the portlet information as a file
	 * @throws PortalException if a group, layout or portlet with the primary
	 *         key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Returns the primary key of the default layout for the group
	 *
	 * @param  groupId the primary key of the group
	 * @param  scopeGroupId the primary key of the scope group. See 
	 * 		   {@link ServiceContext#getScopeGroupId()}
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  portletId the primary key of the portlet
	 * @return Returns the primary key of the default layout, or
	 * 		   DEFAULT_PLID.
	 * 		   See {@link com.liferay.portal.model.LayoutConstants#DEFAULT_PLID}
	 * @throws PortalException if a group, layout and portlet with the primary
	 *         key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public long getDefaultPlid(
			long groupId, long scopeGroupId, boolean privateLayout,
			String portletId)
		throws PortalException, SystemException {

		if (groupId <= 0) {
			return LayoutConstants.DEFAULT_PLID;
		}

		PermissionChecker permissionChecker = getPermissionChecker();

		String scopeGroupLayoutUuid = null;

		Group scopeGroup = groupLocalService.getGroup(scopeGroupId);

		if (scopeGroup.isLayout()) {
			Layout scopeGroupLayout = layoutLocalService.getLayout(
				scopeGroup.getClassPK());

			scopeGroupLayoutUuid = scopeGroupLayout.getUuid();
		}

		List<Layout> layouts = new ArrayList<Layout>();

		layouts.addAll(
			layoutPersistence.filterFindByG_P(groupId, privateLayout));
		layouts.addAll(
			layoutPersistence.filterFindByG_P(scopeGroupId, privateLayout));

		for (Layout layout : layouts) {
			if (!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				continue;
			}

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
				jxPreferences.getValue("lfrScopeType", null));

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

	/**
	 * Returns the name of the layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  languageId the primary key of the language. For more information 
	 *		   See {@link java.util.Locale}
	 * @return the layout's name
	 * @throws PortalException if a group, or layout with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public String getLayoutName(
			long groupId, boolean privateLayout, long layoutId,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		return layout.getName(languageId);
	}

	/**
	 * Returns all the layout references belonging to the company and the
	 * portlet.
	 *
	 * @param  companyId the primary key of the company
	 * @param  portletId the primary key of the portlet
	 * @param  preferencesKey the preferences key
	 * @param  preferencesValue the preferences value
	 * @return all the layout references belonging to the company and the
	 * 		   portlet.
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutReference[] getLayoutReferences(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue)
		throws SystemException {

		return layoutLocalService.getLayouts(
			companyId, portletId, preferencesKey, preferencesValue);
	}

	/**
	 * Imports a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be imported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  bytes the byte array with the data
	 * @throws PortalException if a group or user with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, bytes);
	}

	/**
	 * Imports a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be imported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  file the LAR file with the data
	 * @throws PortalException if a group or user with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Imports a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be imported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  is the file's InputStream to create the LAR file 
	 * @throws PortalException if a group or user with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, is);
	}

	/**
	 * Imports information of a portlet
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be imported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  file the LAR file with the data
	 * @throws PortalException if a group, layout, portlet or user with the
	 *         primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Imports information of a portlet
	 *
	 * @param  plid the primary key of the layout
	 * @param  groupId the primary key of the group
	 * @param  portletId the primary key of the portlet
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be imported. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  is the file's InputStream
	 * @throws PortalException if a group, layout, portlet or user with the
	 *         primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Schedule a range of layouts to be published
	 *
	 * @param  sourceGroupId the primary key of the source group
	 * @param  targetGroupId the primary key of the target group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutIdMap the layout id and a boolean indicating if it has  
	 * 		   children
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be used. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  scope the scope of the pages. It can be <code>all-pages</code>
	 * 		   or <code>selected-pages</code>.
	 * @param  startDate the start date
	 * @param  endDate the end date
	 * @param  groupName the group name. Optionally
	 * 		   <code>LAYOUTS_LOCAL_PUBLISHER</code>. 
	 *		   See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @param  cronText the cron text. See 
	 * 		   {@link com.liferay.portal.kernel.cal.RecurrenceSerializer
	 * 		   #toCronText}
	 * @param  schedulerStartDate the scheduler start date
	 * @param  schedulerEndDate the scheduler end date
	 * @param  description the scheduler description
	 * @throws PortalException if the user had not permission to execute this 
	 * 		   action
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Schedule a range of layouts to be stored
	 *
	 * @param  sourceGroupId the primary key of the source group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutIdMap the layout id and a boolean indicating if it has  
	 * 		   children
	 * @param  parameterMap the mapping of some parameters indicating which
	 *         information will be used. See 
	 *         {@link com.liferay.portal.kernel.lar.PortletDataHandlerKeys}
	 * @param  remoteAddress the remote address
	 * @param  remotePort the remote port
	 * @param  secureConnection whether to the connection is secure or not
	 * @param  remoteGroupId the primary key of the remote group
	 * @param  remotePrivateLayout whether remote group's layout is private 
	 * 		   or not
	 * @param  startDate the start date
	 * @param  endDate the end date
	 * @param  groupName the group name. Optionally
	 * 		   <code>LAYOUTS_LOCAL_PUBLISHER</code>. 
	 *		   See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @param  cronText the cron text. See 
	 * 		   {@link com.liferay.portal.kernel.cal.RecurrenceSerializer
	 * 		   #toCronText}
	 * @param  schedulerStartDate the scheduler start date
	 * @param  schedulerEndDate the scheduler end date
	 * @param  description the scheduler description
	 * @throws PortalException if the user had not permission to execute this 
	 * 		   action
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Deletes all the layouts at <code>groupId</code> which are not at
	 * <code>layoutIds</code> and updates the priority of the others.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  parentLayoutId the primary key of the parent layout
	 * @param  layoutIds the primary keys of the layouts
	 * @param  serviceContext the organization's service context.
	 * @throws PortalException if a group or layout with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds, serviceContext);
	}

	/**
	 * Deletes a job from the schedule's queue
	 *
	 * @param  groupId the primary key of the group
	 * @param  jobName the job name
	 * @param  groupName the group name. Optionally
	 * 		   <code>LAYOUTS_LOCAL_PUBLISHER</code>. 
	 *		   See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @throws PortalException if the user had not permission to execute this 
	 * 		   action
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Deletes a job from the schedule's persistent queue 
	 * 
	 * @param  groupId the primary key of the group
	 * @param  jobName the job name
	 * @param  groupName the group name. Optionally
	 * 		   <code>LAYOUTS_LOCAL_PUBLISHER</code>. 
	 *		   See {@link com.liferay.portal.kernel.messaging.DestinationNames}.
	 * @throws PortalException if a group with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Updates a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  parentLayoutId the primary key of the parent layout
	 * @param  localeNamesMap the layout's locales and localized names 
	 * @param  localeTitlesMap the layout's locales and localized titles
	 * @param  descriptionMap the layout's locales and localized descriptions
	 * @param  keywordsMap the layout's locales and localized keywords
	 * @param  robotsMap the layout's locales and localized robotses
	 * @param  type the layout's type (optionally <code>TYPE_PORTLET</code>).
	 * 		   See {@link com.liferay.portal.model.LayoutConstant}.
	 * @param  hidden whether the layout is hidden or not
	 * @param  friendlyURL the layout's friendly URL (optionally 
	 * 		   <code>DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL</code> or
	 * 		   <code>DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL</code>). See {@link
	 * 		   com.liferay.portal.util.PropsValues} and {@link 
	 *         com.liferay.portal.util.FriendlyURLNormalizer#normalize(String)}.
	 * @param  iconImage whether the icon image will be updated or not
	 * @param  iconBytes the byte array of the icon image
	 * @param  locked whether the layout is locked or not.
	 * @param  serviceContext the organization's service context. Can specify 
	 * 		   the replacement modified date and new expando bridge attributes.
	 * @return the updated layout
	 * @throws PortalException if a group, layout or user with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Updates a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  typeSettings the settings to load the UnicodeProperties object.
	 * 		   See {@link com.liferay.portal.kernel.util.UnicodeProperties
	 * 		   #fastLoad(String)}.
	 * @return the updated layout
	 * @throws PortalException if a group or layout with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Updates the look and feel of a layout
	 *
	 * @param  groupId the primary key of the something's group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  themeId the primary key of the theme
	 * @param  colorSchemeId the primary key of the color scheme
	 * @param  css the css to be assigned
	 * @param  wapTheme whether the theme is for wap browsers or not
	 * @return the updated layout
	 * @throws PortalException if a group, theme or layout with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Updates the name of a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  name the name of the layout to be assigned
	 * @param  languageId the primary key of the language. For more information 
	 *		   see {@link java.util.Locale}
	 * @return the updated layout
	 * @throws PortalException if a language with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Updates the name of a layout
	 *
	 * @param  plid the primary key of the layout
	 * @param  name the name of the layout to be assigned
	 * @param  languageId the primary key of the language. For more information 
	 *		   see {@link java.util.Locale}
	 * @return the updated layout
	 * @throws PortalException if a layout or language with the primary key
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateName(long plid, String name, String languageId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateName(plid, name, languageId);
	}

	/**
	 * Updates the parent layout id of a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  parentLayoutId the primary key of the parent layout
	 * @return the updated layout
	 * @throws PortalException if a layout or group with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Updates the parent layout id of a layout
	 *
	 * @param  plid the primary key of the layout
	 * @param  parentPlid the primary key of the parent layout
	 * @return the updated layout
	 * @throws PortalException if a layout with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateParentLayoutId(plid, parentPlid);
	}

	/**
	 * Updates the priority of a layout
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to a group or not
	 * @param  layoutId the primary key of the layout
	 * @param  priority the priority to be assigned
	 * @return the updated layout
	 * @throws PortalException if a layout or group with the primary key could
	 *         not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(
			groupId, privateLayout, layoutId, priority);
	}

	/**
	 * Updates the priority of a layout
	 *
	 * @param  plid the primary key of the layout
	 * @param  priority the priority to be assigned
	 * @return the updated layout
	 * @throws PortalException if a layout with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public Layout updatePriority(long plid, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(plid, priority);
	}

}