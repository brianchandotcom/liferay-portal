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

package com.liferay.portal.service.impl;

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredLayoutException;
import com.liferay.portal.kernel.exception.SitemapChangeFrequencyException;
import com.liferay.portal.kernel.exception.SitemapIncludeException;
import com.liferay.portal.kernel.exception.SitemapPagePriorityException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutReference;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntry;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.LayoutComparator;
import com.liferay.portal.kernel.util.comparator.LayoutPriorityComparator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.base.LayoutLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Provides the local service for accessing, adding, deleting, exporting,
 * importing, and updating layouts.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Vilmos Papp
 * @author James Lefeu
 * @author Tibor Lipusz
 */
public class LayoutLocalServiceImpl extends LayoutLocalServiceBaseImpl {

	/**
	 * Returns the object counter's name.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether layout is private to the group
	 * @return the object counter's name
	 */
	public static String getCounterName(long groupId, boolean privateLayout) {
		StringBundler sb = new StringBundler(5);

		sb.append(Layout.class.getName());
		sb.append(StringPool.POUND);
		sb.append(groupId);
		sb.append(StringPool.POUND);
		sb.append(privateLayout);

		return sb.toString();
	}

	/**
	 * Adds a layout with additional parameters.
	 *
	 * <p>
	 * This method handles the creation of the layout including its resources,
	 * metadata, and internal data structures. It is not necessary to make
	 * subsequent calls to any methods to setup default groups, resources, ...
	 * etc.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout (optionally
	 *         {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param  nameMap the layout's locales and localized names
	 * @param  titleMap the layout's locales and localized titles
	 * @param  descriptionMap the layout's locales and localized descriptions
	 * @param  keywordsMap the layout's locales and localized keywords
	 * @param  robotsMap the layout's locales and localized robots
	 * @param  type the layout's type (optionally {@link
	 *         LayoutConstants#TYPE_PORTLET}). The possible types can be found
	 *         in {@link LayoutConstants}.
	 * @param  typeSettings the settings to load the unicode properties object.
	 *         See {@link UnicodeProperties #fastLoad(String)}.
	 * @param  hidden whether the layout is hidden
	 * @param  friendlyURLMap the layout's locales and localized friendly URLs.
	 *         To see how the URL is normalized when accessed, see {@link
	 *         com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 *         String)}.
	 * @param  serviceContext the service context to be applied. Must set the
	 *         UUID for the layout. Can set the creation date, modification
	 *         date, and expando bridge attributes for the layout. For layouts
	 *         that belong to a layout set prototype, an attribute named
	 *         <code>layoutUpdateable</code> can be set to specify whether site
	 *         administrators can modify this page within their site. For
	 *         layouts that are created from a layout prototype, attributes
	 *         named <code>layoutPrototypeUuid</code> and
	 *         <code>layoutPrototypeLinkedEnabled</code> can be specified to
	 *         provide the unique identifier of the source prototype and a
	 *         boolean to determine whether a link to it should be enabled to
	 *         activate propagation of changes made to the linked page in the
	 *         prototype.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, String typeSettings, boolean hidden,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		// Layout

		User user = userPersistence.findByPrimaryKey(userId);
		long layoutId = getNextLayoutId(groupId, privateLayout);
		parentLayoutId = layoutLocalServiceHelper.getParentLayoutId(
			groupId, privateLayout, parentLayoutId);

		String name = nameMap.get(LocaleUtil.getSiteDefault());

		friendlyURLMap = layoutLocalServiceHelper.getFriendlyURLMap(
			groupId, privateLayout, layoutId, name, friendlyURLMap);

		String friendlyURL = friendlyURLMap.get(LocaleUtil.getSiteDefault());

		int priority = layoutLocalServiceHelper.getNextPriority(
			groupId, privateLayout, parentLayoutId, null, -1);

		layoutLocalServiceHelper.validate(
			groupId, privateLayout, layoutId, parentLayoutId, name, type,
			hidden, friendlyURLMap, serviceContext);

		Date now = new Date();

		long plid = counterLocalService.increment();

		Layout layout = layoutPersistence.create(plid);

		layout.setUuid(serviceContext.getUuid());
		layout.setGroupId(groupId);
		layout.setCompanyId(user.getCompanyId());
		layout.setUserId(user.getUserId());
		layout.setUserName(user.getFullName());
		layout.setCreateDate(serviceContext.getCreateDate(now));
		layout.setModifiedDate(serviceContext.getModifiedDate(now));
		layout.setPrivateLayout(privateLayout);
		layout.setLayoutId(layoutId);
		layout.setParentLayoutId(parentLayoutId);
		layout.setNameMap(nameMap);
		layout.setTitleMap(titleMap);
		layout.setDescriptionMap(descriptionMap);
		layout.setKeywordsMap(keywordsMap);
		layout.setRobotsMap(robotsMap);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);
		layout.setPriority(priority);

		boolean layoutUpdateable = ParamUtil.getBoolean(
			serviceContext, Sites.LAYOUT_UPDATEABLE, true);

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(typeSettings);

		if (!layoutUpdateable) {
			typeSettingsProperties.put(
				Sites.LAYOUT_UPDATEABLE, String.valueOf(layoutUpdateable));
		}

		if (privateLayout) {
			typeSettingsProperties.put(
				"privateLayout", String.valueOf(privateLayout));
		}

		validateTypeSettingsProperties(layout, typeSettingsProperties);

		layout.setTypeSettingsProperties(typeSettingsProperties);

		String layoutPrototypeUuid = ParamUtil.getString(
			serviceContext, "layoutPrototypeUuid");
		boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
			serviceContext, "layoutPrototypeLinkEnabled",
			PropsValues.LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT);

		if (Validator.isNotNull(layoutPrototypeUuid)) {
			layout.setLayoutPrototypeUuid(layoutPrototypeUuid);
			layout.setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);
		}

		if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (Validator.isNull(layoutTypePortlet.getLayoutTemplateId())) {
				layoutTypePortlet.setLayoutTemplateId(
					0, PropsValues.LAYOUT_DEFAULT_TEMPLATE_ID, false);
			}
		}

		layout.setExpandoBridgeAttributes(serviceContext);

		layoutPersistence.update(layout);

		// Layout friendly URLs

		layoutFriendlyURLLocalService.updateLayoutFriendlyURLs(
			user.getUserId(), user.getCompanyId(), groupId, plid, privateLayout,
			friendlyURLMap, serviceContext);

		// Layout prototype

		if (Validator.isNotNull(layoutPrototypeUuid) &&
			!layoutPrototypeLinkEnabled) {

			LayoutPrototype layoutPrototype =
				layoutPrototypeLocalService.
					getLayoutPrototypeByUuidAndCompanyId(
						layoutPrototypeUuid, layout.getCompanyId());

			try {
				SitesUtil.applyLayoutPrototype(
					layoutPrototype, layout, layoutPrototypeLinkEnabled);
			}
			catch (PortalException pe) {
				throw pe;
			}
			catch (SystemException se) {
				throw se;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		// Resources

		boolean addGroupPermissions = true;

		Group group = groupLocalService.getGroup(groupId);

		if (privateLayout && (group.isUser() || group.isUserGroup())) {
			addGroupPermissions = false;
		}

		boolean addGuestPermissions = false;

		if (!privateLayout || type.equals(LayoutConstants.TYPE_CONTROL_PANEL) ||
			group.isLayoutSetPrototype()) {

			addGuestPermissions = true;
		}

		resourceLocalService.addResources(
			user.getCompanyId(), groupId, user.getUserId(),
			Layout.class.getName(), layout.getPlid(), false,
			addGroupPermissions, addGuestPermissions);

		// Group

		groupLocalService.updateSite(groupId, true);

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);

		layout.setLayoutSet(null);

		// Asset

		updateAsset(
			userId, layout, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return layoutLocalService.getLayout(layout.getPlid());
	}

	/**
	 * Adds a layout with single entry maps for name, title, and description to
	 * the default locale.
	 *
	 * <p>
	 * This method handles the creation of the layout including its resources,
	 * metadata, and internal data structures. It is not necessary to make
	 * subsequent calls to any methods to setup default groups, resources, ...
	 * etc.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout (optionally
	 *         {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID}). The possible
	 *         values can be found in {@link LayoutConstants}.
	 * @param  name the layout's name (optionally {@link
	 *         PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_NAME} or {@link
	 *         PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_NAME}). The default values
	 *         can be overridden in <code>portal-ext.properties</code> by
	 *         specifying new values for the corresponding properties defined in
	 *         {@link PropsValues}
	 * @param  title the layout's title
	 * @param  description the layout's description
	 * @param  type the layout's type (optionally {@link
	 *         LayoutConstants#TYPE_PORTLET}). The possible types can be found
	 *         in {@link LayoutConstants}.
	 * @param  hidden whether the layout is hidden
	 * @param  friendlyURL the friendly URL of the layout (optionally {@link
	 *         PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL} or {@link
	 *         PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL}). The
	 *         default values can be overridden in
	 *         <code>portal-ext.properties</code> by specifying new values for
	 *         the corresponding properties defined in {@link PropsValues}. To
	 *         see how the URL is normalized when accessed, see {@link
	 *         com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 *         String)}.
	 * @param  serviceContext the service context to be applied. Must set the
	 *         UUID for the layout. Can set the creation date and modification
	 *         date for the layout. For layouts that belong to a layout set
	 *         prototype, an attribute named <code>layoutUpdateable</code> can
	 *         be set to specify whether site administrators can modify this
	 *         page within their site.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL,
			ServiceContext serviceContext)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(locale, name);

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, title);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, description);

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.getSiteDefault(), friendlyURL);

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, nameMap, titleMap,
			descriptionMap, new HashMap<Locale, String>(),
			new HashMap<Locale, String>(), type, StringPool.BLANK, hidden,
			friendlyURLMap, serviceContext);
	}

	/**
	 * Deletes the layout, its child layouts, and its associated resources.
	 *
	 * @param  layout the layout
	 * @param  updateLayoutSet whether the layout set's page counter needs to be
	 *         updated
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteLayout(
			Layout layout, boolean updateLayoutSet,
			ServiceContext serviceContext)
		throws PortalException {

		// First layout validation

		if (layout.getParentLayoutId() ==
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			List<Layout> rootLayouts = layoutPersistence.findByG_P_P(
				layout.getGroupId(), layout.isPrivateLayout(),
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 2);

			if (rootLayouts.size() > 1) {
				Layout firstLayout = rootLayouts.get(0);

				if (firstLayout.getLayoutId() == layout.getLayoutId()) {
					Layout secondLayout = rootLayouts.get(1);

					layoutLocalServiceHelper.validateFirstLayout(secondLayout);
				}
			}
		}

		// Child layouts

		List<Layout> childLayouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		for (Layout childLayout : childLayouts) {
			layoutLocalService.deleteLayout(
				childLayout, updateLayoutSet, serviceContext);
		}

		// Layout friendly URLs

		layoutFriendlyURLLocalService.deleteLayoutFriendlyURLs(
			layout.getPlid());

		// Portlet preferences

		portletPreferencesLocalService.deletePortletPreferencesByPlid(
			layout.getPlid());

		// Asset

		assetEntryLocalService.deleteEntry(
			Layout.class.getName(), layout.getPlid());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			Layout.class.getName(), layout.getPlid());

		// Expando

		expandoRowLocalService.deleteRows(layout.getPlid());

		// Icon

		imageLocalService.deleteImage(layout.getIconImageId());

		// Scope group

		Group scopeGroup = layout.getScopeGroup();

		if (scopeGroup != null) {
			groupLocalService.deleteGroup(scopeGroup.getGroupId());
		}

		// Resources

		String primKey =
			layout.getPlid() + PortletConstants.LAYOUT_SEPARATOR + "%";

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_LikeP(
				layout.getCompanyId(), primKey);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission);
		}

		resourceLocalService.deleteResource(
			layout.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, layout.getPlid());

		// Layout

		layoutPersistence.remove(layout);

		// Layout set

		if (updateLayoutSet) {
			layoutSetLocalService.updatePageCount(
				layout.getGroupId(), layout.isPrivateLayout());
		}

		// System event

		SystemEventHierarchyEntry systemEventHierarchyEntry =
			SystemEventHierarchyEntryThreadLocal.peek();

		if ((systemEventHierarchyEntry != null) &&
			systemEventHierarchyEntry.hasTypedModel(
				Layout.class.getName(), layout.getPlid())) {

			systemEventHierarchyEntry.setExtraDataValue(
				"privateLayout", StringUtil.valueOf(layout.isPrivateLayout()));
		}
	}

	/**
	 * Deletes the layout with the primary key, also deleting the layout's child
	 * layouts, and associated resources.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteLayout(
			long groupId, boolean privateLayout, long layoutId,
			ServiceContext serviceContext)
		throws PortalException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layoutLocalService.deleteLayout(layout, true, serviceContext);
	}

	/**
	 * Deletes the layout with the plid, also deleting the layout's child
	 * layouts, and associated resources.
	 *
	 * @param  plid the primary key of the layout
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteLayout(long plid, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		layoutLocalService.deleteLayout(layout, true, serviceContext);
	}

	/**
	 * Deletes the group's private or non-private layouts, also deleting the
	 * layouts' child layouts, and associated resources.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  serviceContext the service context to be applied. The parent
	 *         layout set's page count will be updated by default, unless an
	 *         attribute named <code>updatePageCount</code> is set to
	 *         <code>false</code>.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteLayouts(
			long groupId, boolean privateLayout, ServiceContext serviceContext)
		throws PortalException {

		// Layouts

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new LayoutPriorityComparator(false));

		for (Layout layout : layouts) {
			try {
				layoutLocalService.deleteLayout(layout, false, serviceContext);
			}
			catch (NoSuchLayoutException nsle) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsle, nsle);
				}
			}
		}

		// Layout set

		if (GetterUtil.getBoolean(
				serviceContext.getAttribute("updatePageCount"), true)) {

			layoutSetLocalService.updatePageCount(groupId, privateLayout);
		}

		// Counter

		counterLocalService.reset(getCounterName(groupId, privateLayout));
	}

	/**
	 * Exports layouts with the primary keys and criteria as a byte array.
	 *
	 * @param      groupId the primary key of the group
	 * @param      privateLayout whether the layout is private to the group
	 * @param      layoutIds the primary keys of the layouts to be exported
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information to export. For information on the keys used in
	 *             the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      startDate the export's start date
	 * @param      endDate the export's end date
	 * @return     the layouts as a byte array
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Exports all layouts that match the criteria as a byte array.
	 *
	 * @param      groupId the primary key of the group
	 * @param      privateLayout whether the layout is private to the group
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information to export. For information on the keys used in
	 *             the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      startDate the export's start date
	 * @param      endDate the export's end date
	 * @return     the layout as a byte array
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#exportLayoutsAsFile(
	 *             ExportImportConfiguration)}
	 */
	@Deprecated
	@Override
	public File exportLayoutsAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Exports the layouts that match the primary keys and criteria as a file.
	 *
	 * @param      groupId the primary key of the group
	 * @param      privateLayout whether the layout is private to the group
	 * @param      layoutIds the primary keys of the layouts to be exported
	 *             (optionally <code>null</code>)
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information to export. For information on the keys used in
	 *             the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      startDate the export's start date
	 * @param      endDate the export's end date
	 * @return     the layouts as a File
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#exportLayoutsAsFileInBackground(
	 *             long, ExportImportConfiguration)}
	 */
	@Deprecated
	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#exportLayoutsAsFileInBackground(
	 *             long, long)}
	 */
	@Deprecated
	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, String taskName, long groupId, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, String taskName, long groupId, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate, String fileName)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Exports the portlet information (categories, permissions, ... etc.) as a
	 * byte array.
	 *
	 * @param      plid the primary key of the layout
	 * @param      groupId the primary key of the group
	 * @param      portletId the primary key of the portlet
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information to export. For information on the keys used in
	 *             the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      startDate the export's start date
	 * @param      endDate the export's end date
	 * @return     the portlet information as a byte array
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public byte[] exportPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public byte[] exportPortletInfo(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#exportPortletInfoAsFile(
	 *             ExportImportConfiguration)}}
	 */
	@Deprecated
	@Override
	public File exportPortletInfoAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Exports the portlet information (categories, permissions, ... etc.) as a
	 * file.
	 *
	 * @param      plid the primary key of the layout
	 * @param      groupId the primary key of the group
	 * @param      portletId the primary key of the portlet
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information to export. For information on the keys used in
	 *             the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      startDate the export's start date
	 * @param      endDate the export's end date
	 * @return     the portlet information as a file
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public File exportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public File exportPortletInfoAsFile(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#exportPortletInfoAsFileInBackground(
	 *             long, ExportImportConfiguration)}}
	 */
	@Deprecated
	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#exportPortletInfoAsFileInBackground(
	 *             long, long)}}
	 */
	@Deprecated
	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, String taskName, long plid, long groupId,
			String portletId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate, String fileName)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, String taskName, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate,
			String fileName)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Layout fetchDefaultLayout(long groupId, boolean privateLayout) {
		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout, 0, 1);

			if (!layouts.isEmpty()) {
				return layouts.get(0);
			}
		}

		return null;
	}

	@Override
	public Layout fetchFirstLayout(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return layoutPersistence.fetchByG_P_P_First(
			groupId, privateLayout, parentLayoutId,
			new LayoutPriorityComparator());
	}

	@Override
	public Layout fetchLayout(
		long groupId, boolean privateLayout, long layoutId) {

		return layoutPersistence.fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	@Override
	public Layout fetchLayout(
		String uuid, long groupId, boolean privateLayout) {

		return layoutPersistence.fetchByUUID_G_P(uuid, groupId, privateLayout);
	}

	@Override
	public Layout fetchLayoutByFriendlyURL(
		long groupId, boolean privateLayout, String friendlyURL) {

		return layoutPersistence.fetchByG_P_F(
			groupId, privateLayout, friendlyURL);
	}

	@Override
	public Layout fetchLayoutByIconImageId(
			boolean privateLayout, long iconImageId)
		throws PortalException {

		return layoutPersistence.fetchByP_I(privateLayout, iconImageId);
	}

	/**
	 * Returns the primary key of the default layout for the group
	 *
	 * @param  groupId the primary key of the group
	 * @return the primary key of the default layout for the group (optionally
	 *         {@link LayoutConstants#DEFAULT_PLID})
	 */
	@Override
	public long getDefaultPlid(long groupId) {
		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByGroupId(
				groupId, 0, 1);

			if (!layouts.isEmpty()) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	/**
	 * Returns primary key of the matching default layout for the group
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @return the primary key of the default layout for the group; {@link
	 *         LayoutConstants#DEFAULT_PLID}) otherwise
	 */
	@Override
	public long getDefaultPlid(long groupId, boolean privateLayout) {
		Layout layout = fetchDefaultLayout(groupId, privateLayout);

		if (layout != null) {
			return layout.getPlid();
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	/**
	 * Returns primary key of the default portlet layout for the group
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  portletId the primary key of the portlet
	 * @return the primary key of the default portlet layout for the group;
	 *         {@link LayoutConstants#DEFAULT_PLID} otherwise
	 * @throws PortalException
	 */
	@Override
	public long getDefaultPlid(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException {

		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout);

			for (Layout layout : layouts) {
				if (layout.isTypePortlet()) {
					LayoutTypePortlet layoutTypePortlet =
						(LayoutTypePortlet)layout.getLayoutType();

					if (layoutTypePortlet.hasPortletId(portletId)) {
						return layout.getPlid();
					}
				}
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	/**
	 * Returns the layout for the friendly URL.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  friendlyURL the friendly URL of the layout
	 * @return the layout for the friendly URL
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout getFriendlyURLLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException {

		if (Validator.isNull(friendlyURL)) {
			StringBundler sb = new StringBundler(5);

			sb.append("{groupId=");
			sb.append(groupId);
			sb.append(", privateLayout=");
			sb.append(privateLayout);
			sb.append("}");

			throw new NoSuchLayoutException(sb.toString());
		}

		friendlyURL = HttpUtil.decodeURL(friendlyURL);

		friendlyURL = layoutLocalServiceHelper.getFriendlyURL(friendlyURL);

		Layout layout = null;

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			layoutFriendlyURLPersistence.findByG_P_F(
				groupId, privateLayout, friendlyURL, 0, 1);

		if (!layoutFriendlyURLs.isEmpty()) {
			LayoutFriendlyURL layoutFriendlyURL = layoutFriendlyURLs.get(0);

			layout = layoutPersistence.findByPrimaryKey(
				layoutFriendlyURL.getPlid());
		}

		if ((layout == null) && friendlyURL.startsWith(StringPool.SLASH) &&
			Validator.isNumber(friendlyURL.substring(1))) {

			long layoutId = GetterUtil.getLong(friendlyURL.substring(1));

			layout = layoutPersistence.fetchByG_P_L(
				groupId, privateLayout, layoutId);
		}

		if (layout == null) {
			StringBundler sb = new StringBundler(7);

			sb.append("{groupId=");
			sb.append(groupId);
			sb.append(", privateLayout=");
			sb.append(privateLayout);
			sb.append(", friendlyURL=");
			sb.append(friendlyURL);
			sb.append("}");

			throw new NoSuchLayoutException(sb.toString());
		}

		return layout;
	}

	@Override
	public Layout getLayout(long plid) throws PortalException {
		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		if (_mergeLayout(layout, plid)) {
			return layoutPersistence.findByPrimaryKey(plid);
		}

		return layout;
	}

	/**
	 * Returns the layout matching the primary key, group, and privacy; throws a
	 * {@link NoSuchLayoutException} otherwise.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @return the matching layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout getLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (_mergeLayout(layout, groupId, privateLayout, layoutId)) {
			return layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutId);
		}

		return layout;
	}

	/**
	 * Returns the layout for the icon image; throws a {@link
	 * NoSuchLayoutException} otherwise.
	 *
	 * @param  iconImageId the primary key of the icon image
	 * @return Returns the layout for the icon image
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout getLayoutByIconImageId(long iconImageId)
		throws PortalException {

		return layoutPersistence.findByIconImageId(iconImageId);
	}

	@Override
	public Map<Long, List<Layout>> getLayoutChildLayouts(
		LayoutSet layoutSet, List<Layout> parentLayouts) {

		List<Layout> childLayouts = _getChildLayouts(
			layoutSet,
			ListUtil.toLongArray(parentLayouts, Layout::getLayoutId));

		Map<Long, List<Layout>> layoutChildLayouts = new HashMap<>();

		for (Layout childLayout : childLayouts) {
			List<Layout> layoutChildLayoutsList =
				layoutChildLayouts.computeIfAbsent(
					childLayout.getParentLayoutId(),
					(parentLayoutId) -> new ArrayList<>());

			layoutChildLayoutsList.add(childLayout);
		}

		for (List<Layout> layoutChildLayoutsList :
				layoutChildLayouts.values()) {

			layoutChildLayoutsList.sort(
				Comparator.comparing(Layout::getPriority));
		}

		return layoutChildLayouts;
	}

	@Override
	public List<Layout> getLayouts(long companyId) {
		return layoutPersistence.findByCompanyId(companyId);
	}

	/**
	 * Returns all the layouts belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @return the matching layouts, or <code>null</code> if no matches were
	 *         found
	 */
	@Override
	public List<Layout> getLayouts(long groupId, boolean privateLayout) {
		return layoutPersistence.findByG_P(groupId, privateLayout);
	}

	/**
	 * Returns all the layouts belonging to the group that are children of the
	 * parent layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout
	 * @return the matching layouts, or <code>null</code> if no matches were
	 *         found
	 */
	@Override
	public List<Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId) {

		if (MergeLayoutPrototypesThreadLocal.isInProgress()) {
			return layoutPersistence.findByG_P_P(
				groupId, privateLayout, parentLayoutId);
		}

		try {
			Group group = groupLocalService.getGroup(groupId);

			LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
				groupId, privateLayout);

			if (layoutSet.isLayoutSetPrototypeLinkActive() &&
				!_mergeLayouts(
					group, layoutSet, groupId, privateLayout, parentLayoutId)) {

				return layoutPersistence.findByG_P_P(
					groupId, privateLayout, parentLayoutId);
			}

			List<Layout> layouts = layoutPersistence.findByG_P_P(
				groupId, privateLayout, parentLayoutId);

			return _injectVirtualLayouts(
				group, layoutSet, layouts, parentLayoutId);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Returns a range of all the layouts belonging to the group that are
	 * children of the parent layout.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout
	 * @param  incomplete whether the layout is incomplete
	 * @param  start the lower bound of the range of layouts
	 * @param  end the upper bound of the range of layouts (not inclusive)
	 * @return the matching layouts, or <code>null</code> if no matches were
	 *         found
	 */
	@Override
	public List<Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean incomplete, int start, int end) {

		if (MergeLayoutPrototypesThreadLocal.isInProgress()) {
			return layoutPersistence.findByG_P_P(
				groupId, privateLayout, parentLayoutId, start, end);
		}

		try {
			Group group = groupLocalService.getGroup(groupId);

			LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
				groupId, privateLayout);

			if (!_mergeLayouts(
					group, layoutSet, groupId, privateLayout, parentLayoutId,
					start, end)) {

				return layoutPersistence.findByG_P_P(
					groupId, privateLayout, parentLayoutId);
			}

			List<Layout> layouts = layoutPersistence.findByG_P_P(
				groupId, privateLayout, parentLayoutId);

			return _injectVirtualLayouts(
				group, layoutSet, layouts, parentLayoutId);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Returns all the layouts that match the layout IDs and belong to the
	 * group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutIds the primary keys of the layouts
	 * @return the matching layouts, or an empty list if no matches were found
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long[] layoutIds)
		throws PortalException {

		List<Layout> layouts = new ArrayList<>();

		for (long layoutId : layoutIds) {
			Layout layout = getLayout(groupId, privateLayout, layoutId);

			layouts.add(layout);
		}

		return layouts;
	}

	/**
	 * Returns all the layouts that match the type and belong to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  type the type of the layouts (optionally {@link
	 *         LayoutConstants#TYPE_PORTLET})
	 * @return the matching layouts, or <code>null</code> if no matches were
	 *         found
	 */
	@Override
	public List<Layout> getLayouts(
		long groupId, boolean privateLayout, String type) {

		return layoutPersistence.findByG_P_T(groupId, privateLayout, type);
	}

	/**
	 * Returns the layout references for all the layouts that belong to the
	 * company and belong to the portlet that matches the preferences.
	 *
	 * @param  companyId the primary key of the company
	 * @param  portletId the primary key of the portlet
	 * @param  preferencesKey the portlet's preference key
	 * @param  preferencesValue the portlet's preference value
	 * @return the layout references of the matching layouts
	 */
	@Override
	public LayoutReference[] getLayouts(
		long companyId, String portletId, String preferencesKey,
		String preferencesValue) {

		List<LayoutReference> layoutReferences = layoutFinder.findByC_P_P(
			companyId, portletId, preferencesKey, preferencesValue);

		return layoutReferences.toArray(
			new LayoutReference[layoutReferences.size()]);
	}

	@Override
	public List<Layout> getLayoutsByLayoutPrototypeUuid(
		String layoutPrototypeUuid) {

		return layoutPersistence.findByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	@Override
	public int getLayoutsByLayoutPrototypeUuidCount(
		String layoutPrototypeUuid) {

		return layoutPersistence.countByLayoutPrototypeUuid(
			layoutPrototypeUuid);
	}

	@Override
	public int getLayoutsCount(Group group, boolean privateLayout)
		throws PortalException {

		return getLayoutsCount(group, privateLayout, true);
	}

	@Override
	public int getLayoutsCount(
			Group group, boolean privateLayout, boolean includeUserGroups)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			group.getGroupId(), privateLayout);

		int count = layoutSet.getPageCount();

		if (!group.isUser() || !includeUserGroups) {
			return count;
		}

		long[] userGroupIds = userPersistence.getUserGroupPrimaryKeys(
			group.getClassPK());

		if (userGroupIds.length != 0) {
			long userGroupClassNameId = classNameLocalService.getClassNameId(
				UserGroup.class);

			for (long userGroupId : userGroupIds) {
				Group userGroupGroup = groupPersistence.findByC_C_C(
					group.getCompanyId(), userGroupClassNameId, userGroupId);

				layoutSet = layoutSetPersistence.findByG_P(
					userGroupGroup.getGroupId(), privateLayout);

				count += layoutSet.getPageCount();
			}
		}

		return count;
	}

	@Override
	public int getLayoutsCount(
		Group group, boolean privateLayout, long parentLayoutId) {

		return layoutPersistence.countByG_P_P(
			group.getGroupId(), privateLayout, parentLayoutId);
	}

	@Override
	public int getLayoutsCount(
		Group group, boolean privateLayout, long[] layoutIds) {

		if (ArrayUtil.isEmpty(layoutIds)) {
			return 0;
		}

		DynamicQuery dynamicQuery = dynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(group.getGroupId()));

		Property privateLayoutProperty = PropertyFactoryUtil.forName(
			"privateLayout");

		dynamicQuery.add(privateLayoutProperty.eq(privateLayout));

		Property layoutIdProperty = PropertyFactoryUtil.forName("layoutId");

		dynamicQuery.add(layoutIdProperty.in(layoutIds));

		return GetterUtil.getInteger(dynamicQueryCount(dynamicQuery));
	}

	@Override
	public int getLayoutsCount(User user, boolean privateLayout)
		throws PortalException {

		return getLayoutsCount(user, privateLayout, true);
	}

	@Override
	public int getLayoutsCount(
			User user, boolean privateLayout, boolean includeUserGroups)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(User.class);

		Group group = groupPersistence.findByC_C_C(
			user.getCompanyId(), classNameId, user.getUserId());

		return getLayoutsCount(group, privateLayout, includeUserGroups);
	}

	/**
	 * Returns the primary key to use for the next layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @return the primary key to use for the next layout
	 */
	@Override
	public long getNextLayoutId(long groupId, boolean privateLayout) {
		long nextLayoutId = counterLocalService.increment(
			getCounterName(groupId, privateLayout));

		if (nextLayoutId == 1) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout, 0, 1, new LayoutComparator());

			if (!layouts.isEmpty()) {
				Layout layout = layouts.get(0);

				nextLayoutId = layout.getLayoutId() + 1;

				counterLocalService.reset(
					getCounterName(groupId, privateLayout), nextLayoutId);
			}
		}

		return nextLayoutId;
	}

	/**
	 * Returns all the layouts without resource permissions
	 *
	 * @param  roleId the primary key of the role
	 * @return all the layouts without resource permissions
	 */
	@Override
	public List<Layout> getNoPermissionLayouts(long roleId) {
		return layoutFinder.findByNoPermissions(roleId);
	}

	/**
	 * Returns all the layouts whose friendly URLs are <code>null</code>
	 *
	 * @return all the layouts whose friendly URLs are <code>null</code>
	 */
	@Override
	public List<Layout> getNullFriendlyURLLayouts() {
		return layoutFinder.findByNullFriendlyURL();
	}

	@Override
	public Layout getParentLayout(Layout layout) throws PortalException {
		Layout parentLayout = null;

		if (layout instanceof VirtualLayout) {
			VirtualLayout virtualLayout = (VirtualLayout)layout;

			Layout sourceLayout = virtualLayout.getSourceLayout();

			parentLayout = getLayout(
				sourceLayout.getGroupId(), sourceLayout.isPrivateLayout(),
				sourceLayout.getParentLayoutId());

			parentLayout = new VirtualLayout(parentLayout, layout.getGroup());
		}
		else {
			parentLayout = getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId());
		}

		return parentLayout;
	}

	@Override
	public List<Layout> getScopeGroupLayouts(long parentGroupId)
		throws PortalException {

		if (PropsValues.LAYOUT_SCOPE_GROUP_FINDER_ENABLED) {
			return layoutFinder.findByScopeGroup(parentGroupId);
		}

		Group parentGroup = groupPersistence.findByPrimaryKey(parentGroupId);

		if (PropsValues.LAYOUT_SCOPE_GROUP_FINDER_THRESHOLD >= 0) {
			int count = groupLocalService.getGroupsCount(
				parentGroup.getCompanyId(), Layout.class.getName(),
				parentGroupId);

			if (count >= PropsValues.LAYOUT_SCOPE_GROUP_FINDER_THRESHOLD) {
				return layoutFinder.findByScopeGroup(parentGroupId);
			}
		}

		List<Group> groups = groupLocalService.getGroups(
			parentGroup.getCompanyId(), Layout.class.getName(), parentGroupId);

		List<Layout> layouts = new ArrayList<>(groups.size());

		for (Group group : groups) {
			layouts.add(layoutPersistence.findByPrimaryKey(group.getClassPK()));
		}

		return layouts;
	}

	/**
	 * Returns all the layouts within scope of the group.
	 *
	 * @param  parentGroupId the primary key of the group's parent group
	 * @param  privateLayout whether the layout is private to the group
	 * @return the layouts within scope of the group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public List<Layout> getScopeGroupLayouts(
			long parentGroupId, boolean privateLayout)
		throws PortalException {

		if (PropsValues.LAYOUT_SCOPE_GROUP_FINDER_ENABLED) {
			return layoutFinder.findByScopeGroup(parentGroupId, privateLayout);
		}

		Group parentGroup = groupPersistence.findByPrimaryKey(parentGroupId);

		if (PropsValues.LAYOUT_SCOPE_GROUP_FINDER_THRESHOLD >= 0) {
			int count = groupLocalService.getGroupsCount(
				parentGroup.getCompanyId(), Layout.class.getName(),
				parentGroupId);

			if (count >= PropsValues.LAYOUT_SCOPE_GROUP_FINDER_THRESHOLD) {
				return layoutFinder.findByScopeGroup(
					parentGroupId, privateLayout);
			}
		}

		List<Group> groups = groupLocalService.getGroups(
			parentGroup.getCompanyId(), Layout.class.getName(), parentGroupId);

		List<Layout> layouts = new ArrayList<>(groups.size());

		for (Group group : groups) {
			Layout layout = layoutPersistence.findByPrimaryKey(
				group.getClassPK());

			if (layout.getPrivateLayout() == privateLayout) {
				layouts.add(layout);
			}
		}

		return layouts;
	}

	/**
	 * Returns <code>true</code> if there is a matching layout with the UUID,
	 * group, and privacy.
	 *
	 * @param  uuid the layout's UUID
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @return <code>true</code> if the layout is found; <code>false</code>
	 *         otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public boolean hasLayout(String uuid, long groupId, boolean privateLayout)
		throws PortalException {

		try {
			getLayoutByUuidAndGroupId(uuid, groupId, privateLayout);

			return true;
		}
		catch (NoSuchLayoutException nsle) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsle, nsle);
			}
		}

		return false;
	}

	@Override
	public boolean hasLayouts(Group group) throws PortalException {
		List<LayoutSet> groupLayoutSets = layoutSetPersistence.findByGroupId(
			group.getGroupId());

		for (LayoutSet layoutSet : groupLayoutSets) {
			if (layoutSet.getPageCount() > 0) {
				return true;
			}
		}

		if (!group.isUser()) {
			return false;
		}

		long[] userGroupIds = userPersistence.getUserGroupPrimaryKeys(
			group.getClassPK());

		if (userGroupIds.length != 0) {
			long userGroupClassNameId = classNameLocalService.getClassNameId(
				UserGroup.class);

			for (long userGroupId : userGroupIds) {
				Group userGroupGroup = groupPersistence.findByC_C_C(
					group.getCompanyId(), userGroupClassNameId, userGroupId);

				List<LayoutSet> userGroupGroupLayoutSets =
					layoutSetPersistence.findByGroupId(
						userGroupGroup.getGroupId());

				for (LayoutSet layoutSet : userGroupGroupLayoutSets) {
					if (layoutSet.getPageCount() > 0) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean hasLayouts(Group group, boolean privateLayout)
		throws PortalException {

		return hasLayouts(group, privateLayout, true);
	}

	@Override
	public boolean hasLayouts(
			Group group, boolean privateLayout, boolean includeUserGroups)
		throws PortalException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			group.getGroupId(), privateLayout);

		if (layoutSet.getPageCount() > 0) {
			return true;
		}

		if (!group.isUser() || !includeUserGroups) {
			return false;
		}

		long[] userGroupIds = userPersistence.getUserGroupPrimaryKeys(
			group.getClassPK());

		if (userGroupIds.length != 0) {
			long userGroupClassNameId = classNameLocalService.getClassNameId(
				UserGroup.class);

			for (long userGroupId : userGroupIds) {
				Group userGroupGroup = groupPersistence.findByC_C_C(
					group.getCompanyId(), userGroupClassNameId, userGroupId);

				layoutSet = layoutSetPersistence.findByG_P(
					userGroupGroup.getGroupId(), privateLayout);

				if (layoutSet.getPageCount() > 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the group has any layouts;
	 * <code>false</code> otherwise.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout
	 * @return <code>true</code> if the group has any layouts;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasLayouts(
		long groupId, boolean privateLayout, long parentLayoutId) {

		if (layoutPersistence.countByG_P_P(
				groupId, privateLayout, parentLayoutId) > 0) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasLayouts(User user, boolean privateLayout)
		throws PortalException {

		return hasLayouts(user, privateLayout, true);
	}

	@Override
	public boolean hasLayouts(
			User user, boolean privateLayout, boolean includeUserGroups)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(User.class);

		Group group = groupPersistence.findByC_C_C(
			user.getCompanyId(), classNameId, user.getUserId());

		return hasLayouts(group, privateLayout, includeUserGroups);
	}

	@Override
	public boolean hasLayoutSetPrototypeLayout(
			long layoutSetPrototypeId, String layoutUuid)
		throws PortalException {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypeLocalService.getLayoutSetPrototype(
				layoutSetPrototypeId);

		return layoutLocalServiceHelper.hasLayoutSetPrototypeLayout(
			layoutSetPrototype, layoutUuid);
	}

	@Override
	public boolean hasLayoutSetPrototypeLayout(
			String layoutSetPrototypeUuid, long companyId, String layoutUuid)
		throws PortalException {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSetPrototypeUuid, companyId);

		return layoutLocalServiceHelper.hasLayoutSetPrototypeLayout(
			layoutSetPrototype, layoutUuid);
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayouts(
	 *             ExportImportConfiguration, File)}}
	 */
	@Deprecated
	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayouts(
	 *             ExportImportConfiguration, InputStream)}}
	 */
	@Deprecated
	@Override
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Imports the layouts from the byte array.
	 *
	 * @param      userId the primary key of the user
	 * @param      groupId the primary key of the group
	 * @param      privateLayout whether the layout is private to the group
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information will be imported. For information on the keys
	 *             used in the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      bytes the byte array with the data
	 * @throws     PortalException
	 * @see        com.liferay.exportimport.kernel.lar.LayoutImporter
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Imports the layouts from the file.
	 *
	 * @param      userId the primary key of the user
	 * @param      groupId the primary key of the group
	 * @param      privateLayout whether the layout is private to the group
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information will be imported. For information on the keys
	 *             used in the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      file the LAR file with the data
	 * @throws     PortalException
	 * @see        com.liferay.exportimport.kernel.lar.LayoutImporter
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Imports the layouts from the input stream.
	 *
	 * @param      userId the primary key of the user
	 * @param      groupId the primary key of the group
	 * @param      privateLayout whether the layout is private to the group
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information will be imported. For information on the keys
	 *             used in the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      is the input stream
	 * @throws     PortalException
	 * @see        com.liferay.exportimport.kernel.lar.LayoutImporter
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayoutsDataDeletions(
	 *             ExportImportConfiguration, File)}
	 */
	@Deprecated
	@Override
	public void importLayoutsDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayoutsInBackground(
	 *             long, ExportImportConfiguration, File)}
	 */
	@Deprecated
	@Override
	public long importLayoutsInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration,
			File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayoutsInBackground(
	 *             long, long, File)}
	 */
	@Deprecated
	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long importLayoutsInBackground(
			long userId, String taskName, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long importLayoutsInBackground(
			long userId, String taskName, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletDataDeletions(
	 *             ExportImportConfiguration, File)}
	 */
	@Deprecated
	@Override
	public void importPortletDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfo(
	 *             ExportImportConfiguration, File)}
	 */
	@Deprecated
	@Override
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfo(
	 *             ExportImportConfiguration, InputStream)}
	 */
	@Deprecated
	@Override
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Imports the portlet information (categories, permissions, ... etc.) from
	 * the file.
	 *
	 * @param      userId the primary key of the user
	 * @param      plid the primary key of the target layout
	 * @param      groupId the primary key of the target group
	 * @param      portletId the primary key of the portlet
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information will be imported. For information on the keys
	 *             used in the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      file the LAR file with the data
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void importPortletInfo(
			long userId, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Imports the portlet information (categories, permissions, ... etc.) from
	 * the input stream.
	 *
	 * @param      userId the primary key of the user
	 * @param      plid the primary key of the layout
	 * @param      groupId the primary key of the group
	 * @param      portletId the primary key of the portlet
	 * @param      parameterMap the mapping of parameters indicating which
	 *             information will be imported. For information on the keys
	 *             used in the map see {@link
	 *             com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param      is the input stream
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void importPortletInfo(
			long userId, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void importPortletInfo(
			long userId, String portletId, Map<String, String[]> parameterMap,
			File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void importPortletInfo(
			long userId, String portletId, Map<String, String[]> parameterMap,
			InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfoInBackground(
	 *             long, ExportImportConfiguration, File)}
	 */
	@Deprecated
	@Override
	public long importPortletInfoInBackground(
			long userId, ExportImportConfiguration exportImportConfiguration,
			File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfoInBackground(
	 *             long, long, File)}
	 */
	@Deprecated
	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long importPortletInfoInBackground(
			long userId, String taskName, long plid, long groupId,
			String portletId, Map<String, String[]> parameterMap, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long importPortletInfoInBackground(
			long userId, String taskName, long plid, long groupId,
			String portletId, Map<String, String[]> parameterMap,
			InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long importPortletInfoInBackground(
			long userId, String taskName, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public long importPortletInfoInBackground(
			long userId, String taskName, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Sets the layouts for the group, replacing and prioritizing all layouts of
	 * the parent layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  parentLayoutId the primary key of the parent layout
	 * @param  layoutIds the primary keys of the layouts
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds, ServiceContext serviceContext)
		throws PortalException {

		if (layoutIds == null) {
			return;
		}

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			if (layoutIds.length < 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}

			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutIds[0]);

			LayoutType layoutType = layout.getLayoutType();

			if (!layoutType.isFirstPageable()) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_TYPE);
			}
		}

		Set<Long> layoutIdsSet = new LinkedHashSet<>();

		for (long layoutId : layoutIds) {
			layoutIdsSet.add(layoutId);
		}

		Set<Long> newLayoutIdsSet = new HashSet<>();

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			if (!layoutIdsSet.contains(layout.getLayoutId())) {
				deleteLayout(layout, true, serviceContext);
			}
			else {
				newLayoutIdsSet.add(layout.getLayoutId());
			}
		}

		int priority = 0;

		for (long layoutId : layoutIdsSet) {
			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutId);

			layout.setPriority(priority++);

			layoutPersistence.update(layout);
		}

		layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	@Override
	public void updateAsset(
			long userId, Layout layout, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException {

		assetEntryLocalService.updateEntry(
			userId, layout.getGroupId(), layout.getCreateDate(),
			layout.getModifiedDate(), Layout.class.getName(), layout.getPlid(),
			layout.getUuid(), 0, assetCategoryIds, assetTagNames, true, false,
			null, null, null, null, ContentTypes.TEXT_HTML,
			layout.getName(LocaleUtil.getDefault()), null, null, null, null, 0,
			0, null);
	}

	/**
	 * Updates the friendly URL of the layout.
	 *
	 * @param  userId the primary key of the user
	 * @param  plid the primary key of the layout
	 * @param  friendlyURL the friendly URL to be assigned
	 * @param  languageId the primary key of the language
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateFriendlyURL(
			long userId, long plid, String friendlyURL, String languageId)
		throws PortalException {

		Date now = new Date();

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		friendlyURL = layoutLocalServiceHelper.getFriendlyURL(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			StringPool.BLANK, friendlyURL);

		layoutLocalServiceHelper.validateFriendlyURL(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			friendlyURL);

		layoutFriendlyURLLocalService.updateLayoutFriendlyURL(
			userId, layout.getCompanyId(), layout.getGroupId(),
			layout.getPlid(), layout.isPrivateLayout(), friendlyURL, languageId,
			new ServiceContext());

		layout.setModifiedDate(now);

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		if (languageId.equals(defaultLanguageId)) {
			layout.setFriendlyURL(friendlyURL);
		}

		layoutPersistence.update(layout);

		return layout;
	}

	/**
	 * Updates the friendly URL of the layout.
	 *
	 * @param      plid the primary key of the layout
	 * @param      friendlyURL the friendly URL to be assigned
	 * @param      languageId the primary key of the language
	 * @return     the updated layout
	 * @throws     PortalException if a portal exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link #updateFriendlyURL(long,
	 *             long, String, String)}
	 */
	@Deprecated
	@Override
	public Layout updateFriendlyURL(
			long plid, String friendlyURL, String languageId)
		throws PortalException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updateFriendlyURL(
			layout.getUserId(), plid, friendlyURL, languageId);
	}

	@Override
	public Layout updateIconImage(long plid, byte[] bytes)
		throws PortalException {

		Layout layout = layoutPersistence.fetchByPrimaryKey(plid);

		if (layout == null) {
			return null;
		}

		PortalUtil.updateImageId(layout, true, bytes, "iconImageId", 0, 0, 0);

		layoutPersistence.update(layout);

		return layout;
	}

	/**
	 * Updates the layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  parentLayoutId the primary key of the layout's new parent layout
	 * @param  nameMap the locales and localized names to merge (optionally
	 *         <code>null</code>)
	 * @param  titleMap the locales and localized titles to merge (optionally
	 *         <code>null</code>)
	 * @param  descriptionMap the locales and localized descriptions to merge
	 *         (optionally <code>null</code>)
	 * @param  keywordsMap the locales and localized keywords to merge
	 *         (optionally <code>null</code>)
	 * @param  robotsMap the locales and localized robots to merge (optionally
	 *         <code>null</code>)
	 * @param  type the layout's new type (optionally {@link
	 *         LayoutConstants#TYPE_PORTLET})
	 * @param  hidden whether the layout is hidden
	 * @param  friendlyURLMap the layout's locales and localized friendly URLs.
	 *         To see how the URL is normalized when accessed, see {@link
	 *         com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 *         String)}.
	 * @param  iconImage whether the icon image will be updated
	 * @param  iconBytes the byte array of the layout's new icon image
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date and expando bridge attributes for the layout.
	 *         For layouts that are linked to a layout prototype, attributes
	 *         named <code>layoutPrototypeUuid</code> and
	 *         <code>layoutPrototypeLinkedEnabled</code> can be specified to
	 *         provide the unique identifier of the source prototype and a
	 *         boolean to determine whether a link to it should be enabled to
	 *         activate propagation of changes made to the linked page in the
	 *         prototype.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, boolean hidden, Map<Locale, String> friendlyURLMap,
			boolean iconImage, byte[] iconBytes, ServiceContext serviceContext)
		throws PortalException {

		// Layout

		parentLayoutId = layoutLocalServiceHelper.getParentLayoutId(
			groupId, privateLayout, parentLayoutId);

		String name = nameMap.get(LocaleUtil.getSiteDefault());

		friendlyURLMap = layoutLocalServiceHelper.getFriendlyURLMap(
			groupId, privateLayout, layoutId, name, friendlyURLMap);

		String friendlyURL = friendlyURLMap.get(LocaleUtil.getSiteDefault());

		layoutLocalServiceHelper.validate(
			groupId, privateLayout, layoutId, parentLayoutId, name, type,
			hidden, friendlyURLMap, serviceContext);

		layoutLocalServiceHelper.validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Date now = new Date();

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			int priority = layoutLocalServiceHelper.getNextPriority(
				groupId, privateLayout, parentLayoutId,
				layout.getSourcePrototypeLayoutUuid(), -1);

			layout.setPriority(priority);
		}

		layout.setModifiedDate(serviceContext.getModifiedDate(now));
		layout.setParentLayoutId(parentLayoutId);
		layout.setNameMap(nameMap);
		layout.setTitleMap(titleMap);
		layout.setDescriptionMap(descriptionMap);
		layout.setKeywordsMap(keywordsMap);
		layout.setRobotsMap(robotsMap);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);

		PortalUtil.updateImageId(
			layout, iconImage, iconBytes, "iconImageId", 0, 0, 0);

		boolean layoutUpdateable = ParamUtil.getBoolean(
			serviceContext, Sites.LAYOUT_UPDATEABLE, true);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.put(
			Sites.LAYOUT_UPDATEABLE, String.valueOf(layoutUpdateable));

		if (privateLayout) {
			typeSettingsProperties.put(
				"privateLayout", String.valueOf(privateLayout));
		}

		layout.setTypeSettingsProperties(typeSettingsProperties);

		String layoutPrototypeUuid = ParamUtil.getString(
			serviceContext, "layoutPrototypeUuid");
		boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
			serviceContext, "layoutPrototypeLinkEnabled");

		if (Validator.isNotNull(layoutPrototypeUuid)) {
			layout.setLayoutPrototypeUuid(layoutPrototypeUuid);
			layout.setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);
		}

		layout.setExpandoBridgeAttributes(serviceContext);

		layoutPersistence.update(layout);

		// Layout friendly URLs

		layoutFriendlyURLLocalService.updateLayoutFriendlyURLs(
			serviceContext.getUserId(), layout.getCompanyId(),
			layout.getGroupId(), layout.getPlid(), layout.isPrivateLayout(),
			friendlyURLMap, serviceContext);

		// Asset

		updateAsset(
			serviceContext.getUserId(), layout,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return layout;
	}

	/**
	 * Updates the layout replacing its type settings.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  typeSettings the settings to load the unicode properties object.
	 *         See {@link UnicodeProperties #fastLoad(String)}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException {

		Date now = new Date();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(typeSettings);

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		validateTypeSettingsProperties(layout, typeSettingsProperties);

		layout.setModifiedDate(now);
		layout.setTypeSettings(typeSettingsProperties.toString());

		layoutPersistence.update(layout);

		return layout;
	}

	/**
	 * Updates the look and feel of the layout.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  themeId the primary key of the layout's new theme
	 * @param  colorSchemeId the primary key of the layout's new color scheme
	 * @param  css the layout's new CSS
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css)
		throws PortalException {

		Date now = new Date();

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layout.setModifiedDate(now);

		layout.setThemeId(themeId);
		layout.setColorSchemeId(colorSchemeId);
		layout.setCss(css);

		return layoutPersistence.update(layout);
	}

	/**
	 * Updates the name of the layout.
	 *
	 * @param  layout the layout to be updated
	 * @param  name the layout's new name
	 * @param  languageId the primary key of the language. For more information
	 *         see {@link Locale}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateName(Layout layout, String name, String languageId)
		throws PortalException {

		Date now = new Date();

		layoutLocalServiceHelper.validateName(name, languageId);

		layout.setModifiedDate(now);
		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

		layout = layoutPersistence.update(layout);

		Group group = layout.getGroup();

		if (group.isLayoutPrototype()) {
			LayoutPrototype layoutPrototype =
				layoutPrototypeLocalService.getLayoutPrototype(
					group.getClassPK());

			layoutPrototype.setModifiedDate(now);
			layoutPrototype.setName(
				name, LocaleUtil.fromLanguageId(languageId));

			layoutPrototypePersistence.update(layoutPrototype);
		}

		return layout;
	}

	/**
	 * Updates the name of the layout matching the group, layout ID, and
	 * privacy.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  name the layout's new name
	 * @param  languageId the primary key of the language. For more information
	 *         see {@link Locale}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return updateName(layout, name, languageId);
	}

	/**
	 * Updates the name of the layout matching the primary key.
	 *
	 * @param  plid the primary key of the layout
	 * @param  name the name to be assigned
	 * @param  languageId the primary key of the language. For more information
	 *         see {@link Locale}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateName(long plid, String name, String languageId)
		throws PortalException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updateName(layout, name, languageId);
	}

	/**
	 * Updates the parent layout ID of the layout matching the group, layout ID,
	 * and privacy.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  parentLayoutId the primary key to be assigned to the parent
	 *         layout
	 * @return the matching layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException {

		parentLayoutId = layoutLocalServiceHelper.getParentLayoutId(
			groupId, privateLayout, parentLayoutId);

		layoutLocalServiceHelper.validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Date now = new Date();

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			int priority = layoutLocalServiceHelper.getNextPriority(
				groupId, privateLayout, parentLayoutId,
				layout.getSourcePrototypeLayoutUuid(), -1);

			layout.setPriority(priority);
		}

		layout.setModifiedDate(now);
		layout.setParentLayoutId(parentLayoutId);

		return layoutPersistence.update(layout);
	}

	/**
	 * Updates the parent layout ID of the layout matching the primary key. If a
	 * layout matching the parent primary key is found, the layout ID of that
	 * layout is assigned, otherwise {@link
	 * LayoutConstants#DEFAULT_PARENT_LAYOUT_ID} is assigned.
	 *
	 * @param  plid the primary key of the layout
	 * @param  parentPlid the primary key of the parent layout
	 * @return the layout matching the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException {

		Date now = new Date();

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		if (parentPlid > 0) {
			Layout parentLayout = layoutPersistence.fetchByPrimaryKey(
				parentPlid);

			if (parentLayout != null) {
				parentLayoutId = parentLayout.getLayoutId();
			}
		}

		parentLayoutId = layoutLocalServiceHelper.getParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

		layoutLocalServiceHelper.validateParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			parentLayoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			int priority = layoutLocalServiceHelper.getNextPriority(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId,
				layout.getSourcePrototypeLayoutUuid(), -1);

			layout.setPriority(priority);
		}

		layout.setModifiedDate(now);
		layout.setParentLayoutId(parentLayoutId);

		return layoutPersistence.update(layout);
	}

	/**
	 * Updates the parent layout ID and priority of the layout.
	 *
	 * @param  plid the primary key of the layout
	 * @param  parentPlid the primary key of the parent layout
	 * @param  priority the layout's new priority
	 * @return the layout matching the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateParentLayoutIdAndPriority(
			long plid, long parentPlid, int priority)
		throws PortalException {

		Layout layout = updateParentLayoutId(plid, parentPlid);

		return layoutLocalService.updatePriority(layout, priority);
	}

	/**
	 * Updates the priorities of the layouts.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @throws PortalException
	 */
	@Override
	public void updatePriorities(long groupId, boolean privateLayout)
		throws PortalException {

		List<Layout> layouts = layoutPersistence.findByG_P(
			groupId, privateLayout);

		for (Layout layout : layouts) {
			int nextPriority = layoutLocalServiceHelper.getNextPriority(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId(),
				layout.getSourcePrototypeLayoutUuid(), layout.getPriority());

			layout.setPriority(nextPriority);

			layoutPersistence.update(layout);
		}
	}

	/**
	 * Updates the priority of the layout.
	 *
	 * @param  layout the layout to be updated
	 * @param  priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(Layout layout, int priority)
		throws PortalException {

		if (layout.getPriority() == priority) {
			return layout;
		}

		int oldPriority = layout.getPriority();

		int nextPriority = layoutLocalServiceHelper.getNextPriority(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId(), layout.getSourcePrototypeLayoutUuid(),
			priority);

		if (oldPriority == nextPriority) {
			return layout;
		}

		layout.setModifiedDate(new Date());
		layout.setPriority(nextPriority);

		layout = layoutPersistence.update(layout);

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId());

		boolean lessThan = false;

		if (oldPriority < nextPriority) {
			lessThan = true;
		}

		layouts = ListUtil.sort(
			layouts, new LayoutPriorityComparator(layout, lessThan));

		if (layout.getParentLayoutId() ==
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout firstLayout = layouts.get(0);

			layoutLocalServiceHelper.validateFirstLayout(firstLayout);
		}

		int newPriority = LayoutConstants.FIRST_PRIORITY;

		for (Layout curLayout : layouts) {
			int curNextPriority = layoutLocalServiceHelper.getNextPriority(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId(),
				curLayout.getSourcePrototypeLayoutUuid(), newPriority++);

			if (curLayout.getPriority() == curNextPriority) {
				continue;
			}

			curLayout.setModifiedDate(layout.getModifiedDate());
			curLayout.setPriority(curNextPriority);

			curLayout = layoutPersistence.update(curLayout);

			if (curLayout.equals(layout)) {
				layout = curLayout;
			}
		}

		return layout;
	}

	/**
	 * Updates the priority of the layout matching the group, layout ID, and
	 * privacy.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return updatePriority(layout, priority);
	}

	/**
	 * Updates the priority of the layout matching the group, layout ID, and
	 * privacy, setting the layout's priority based on the priorities of the
	 * next and previous layouts.
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout is private to the group
	 * @param  layoutId the primary key of the layout
	 * @param  nextLayoutId the primary key of the next layout
	 * @param  previousLayoutId the primary key of the previous layout
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId,
			long nextLayoutId, long previousLayoutId)
		throws PortalException {

		Layout layout = getLayout(groupId, privateLayout, layoutId);

		int priority = layout.getPriority();

		Layout nextLayout = null;

		if (nextLayoutId > 0) {
			nextLayout = getLayout(groupId, privateLayout, nextLayoutId);
		}

		Layout previousLayout = null;

		if (previousLayoutId > 0) {
			previousLayout = getLayout(
				groupId, privateLayout, previousLayoutId);
		}

		if ((nextLayout != null) && (priority > nextLayout.getPriority())) {
			priority = nextLayout.getPriority();
		}
		else if ((previousLayout != null) &&
				 (priority < previousLayout.getPriority())) {

			priority = previousLayout.getPriority();
		}

		return updatePriority(layout, priority);
	}

	/**
	 * Updates the priority of the layout matching the primary key.
	 *
	 * @param  plid the primary key of the layout
	 * @param  priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(long plid, int priority)
		throws PortalException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updatePriority(layout, priority);
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportLayoutsFile(
	 *             ExportImportConfiguration, File)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportLayoutsFile(
	 *             ExportImportConfiguration, InputStream)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportLayoutsFile(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportLayoutsFile(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream inputStream)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportPortletInfo(
	 *             ExportImportConfiguration, File)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportPortletInfo(
	 *             ExportImportConfiguration, InputStream)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportPortletInfo(
			long userId, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @throws     PortalException
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public MissingReferences validateImportPortletInfo(
			long userId, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, InputStream inputStream)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	protected void validateTypeSettingsProperties(
			Layout layout, UnicodeProperties typeSettingsProperties)
		throws PortalException {

		String sitemapChangeFrequency = typeSettingsProperties.getProperty(
			"sitemap-changefreq");

		if (Validator.isNotNull(sitemapChangeFrequency) &&
			!sitemapChangeFrequency.equals("always") &&
			!sitemapChangeFrequency.equals("hourly") &&
			!sitemapChangeFrequency.equals("daily") &&
			!sitemapChangeFrequency.equals("weekly") &&
			!sitemapChangeFrequency.equals("monthly") &&
			!sitemapChangeFrequency.equals("yearly") &&
			!sitemapChangeFrequency.equals("never")) {

			throw new SitemapChangeFrequencyException();
		}

		String sitemapInclude = typeSettingsProperties.getProperty(
			"sitemap-include");

		if (Validator.isNotNull(sitemapInclude) &&
			!sitemapInclude.equals("0") && !sitemapInclude.equals("1")) {

			throw new SitemapIncludeException();
		}

		String sitemapPriority = typeSettingsProperties.getProperty(
			"sitemap-priority");

		if (Validator.isNotNull(sitemapPriority)) {
			try {
				double priority = Double.parseDouble(sitemapPriority);

				if ((priority < 0) || (priority > 1)) {
					throw new SitemapPagePriorityException();
				}
			}
			catch (NumberFormatException nfe) {
				throw new SitemapPagePriorityException(nfe);
			}
		}
	}

	@BeanReference(type = LayoutLocalServiceHelper.class)
	protected LayoutLocalServiceHelper layoutLocalServiceHelper;

	private List<Layout> _addChildUserGroupLayouts(
			Group group, List<Layout> layouts)
		throws PortalException {

		List<Layout> childLayouts = new ArrayList<>(layouts.size());

		for (Layout layout : layouts) {
			Group layoutGroup = layout.getGroup();

			if (layoutGroup.isUserGroup()) {
				childLayouts.add(new VirtualLayout(layout, group));
			}
			else {
				childLayouts.add(layout);
			}
		}

		return childLayouts;
	}

	private List<Layout> _addUserGroupLayouts(
			Group group, LayoutSet layoutSet, List<Layout> layouts,
			long parentLayoutId)
		throws PortalException {

		layouts = new ArrayList<>(layouts);

		List<UserGroup> userUserGroups =
			userGroupLocalService.getUserUserGroups(group.getClassPK());

		for (UserGroup userGroup : userUserGroups) {
			Group userGroupGroup = userGroup.getGroup();

			List<Layout> userGroupLayouts = getLayouts(
				userGroupGroup.getGroupId(), layoutSet.isPrivateLayout(),
				parentLayoutId);

			for (Layout userGroupLayout : userGroupLayouts) {
				layouts.add(new VirtualLayout(userGroupLayout, group));
			}
		}

		return layouts;
	}

	private List<Layout> _addUserGroupLayouts(
			Group group, LayoutSet layoutSet, List<Layout> layouts,
			long[] parentLayoutIds)
		throws PortalException {

		boolean copied = false;

		List<UserGroup> userUserGroups =
			userGroupLocalService.getUserUserGroups(group.getClassPK());

		for (UserGroup userGroup : userUserGroups) {
			Group userGroupGroup = userGroup.getGroup();

			List<Layout> userGroupLayouts = getLayouts(
				userGroupGroup.getGroupId(), layoutSet.isPrivateLayout(),
				parentLayoutIds);

			for (Layout userGroupLayout : userGroupLayouts) {
				if (!copied) {
					layouts = new ArrayList<>(layouts);

					copied = true;
				}

				layouts.add(new VirtualLayout(userGroupLayout, group));
			}
		}

		return layouts;
	}

	private List<Layout> _getChildLayouts(
		LayoutSet layoutSet, long[] parentLayoutIds) {

		if (MergeLayoutPrototypesThreadLocal.isInProgress()) {
			return layoutPersistence.findByG_P_P(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				parentLayoutIds);
		}

		try {
			Group group = groupPersistence.findByPrimaryKey(
				layoutSet.getGroupId());

			if (layoutSet.isLayoutSetPrototypeLinkActive() &&
				!_mergeLayouts(
					group, layoutSet, layoutSet.getGroupId(),
					layoutSet.isPrivateLayout(), parentLayoutIds)) {

				return layoutPersistence.findByG_P_P(
					layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
					parentLayoutIds);
			}

			List<Layout> layouts = layoutPersistence.findByG_P_P(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				parentLayoutIds);

			return _injectVirtualLayouts(
				group, layoutSet, layouts, parentLayoutIds);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private List<Layout> _injectVirtualLayouts(
			Group group, LayoutSet layoutSet, List<Layout> layouts,
			long parentLayoutId)
		throws PortalException {

		if (MergeLayoutPrototypesThreadLocal.isInProgress() ||
			PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {

			return layouts;
		}

		if (group.isUser()) {
			_virtualLayoutTargetGroupId.set(group.getGroupId());

			if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				return _addUserGroupLayouts(
					group, layoutSet, layouts, parentLayoutId);
			}

			return _addChildUserGroupLayouts(group, layouts);
		}

		if (group.isUserGroup() &&
			(parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

			long targetGroupId = _virtualLayoutTargetGroupId.get();

			if (targetGroupId != GroupConstants.DEFAULT_LIVE_GROUP_ID) {
				Group targetGroup = groupLocalService.getGroup(targetGroupId);

				return _addChildUserGroupLayouts(targetGroup, layouts);
			}
		}

		return layouts;
	}

	private List<Layout> _injectVirtualLayouts(
			Group group, LayoutSet layoutSet, List<Layout> layouts,
			long[] parentLayoutIds)
		throws PortalException {

		if (MergeLayoutPrototypesThreadLocal.isInProgress() ||
			PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {

			return layouts;
		}

		if (group.isUser()) {
			_virtualLayoutTargetGroupId.set(group.getGroupId());

			if (ArrayUtil.contains(
					parentLayoutIds,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

				_addUserGroupLayouts(
					group, layoutSet, layouts, parentLayoutIds);

				if (parentLayoutIds.length == 1) {
					return layouts;
				}
			}

			return _addChildUserGroupLayouts(group, layouts);
		}

		if (group.isUserGroup()) {
			long targetGroupId = _virtualLayoutTargetGroupId.get();

			if (targetGroupId != GroupConstants.DEFAULT_LIVE_GROUP_ID) {
				Group targetGroup = groupLocalService.getGroup(targetGroupId);

				return _addChildUserGroupLayouts(targetGroup, layouts);
			}
		}

		return layouts;
	}

	private boolean _mergeLayout(Layout layout, Object... arguments)
		throws PortalException {

		if (MergeLayoutPrototypesThreadLocal.isInProgress()) {
			return false;
		}

		Group group = layout.getGroup();

		if (MergeLayoutPrototypesThreadLocal.isMergeComplete(
				"getLayout", arguments) &&
			(!group.isUser() ||
			 PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE)) {

			return false;
		}

		if (Validator.isNull(layout.getLayoutPrototypeUuid()) &&
			Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {

			return false;
		}

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		LayoutSet layoutSet = layout.getLayoutSet();

		try {
			WorkflowThreadLocal.setEnabled(false);

			SitesUtil.mergeLayoutPrototypeLayout(group, layout);

			if (Validator.isNotNull(layout.getSourcePrototypeLayoutUuid())) {
				SitesUtil.mergeLayoutSetPrototypeLayouts(group, layoutSet);
			}
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setMergeComplete(
				"getLayout", arguments);
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}

		return true;
	}

	private boolean _mergeLayouts(
		Group group, LayoutSet layoutSet, Object... arguments) {

		if (MergeLayoutPrototypesThreadLocal.isMergeComplete(
				"getLayouts", arguments) &&
			(!group.isUser() ||
			 PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE)) {

			return false;
		}

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			if (SitesUtil.isLayoutSetMergeable(group, layoutSet)) {
				WorkflowThreadLocal.setEnabled(false);

				SitesUtil.mergeLayoutSetPrototypeLayouts(group, layoutSet);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to merge layouts for site template", e);
			}
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setMergeComplete(
				"getLayouts", arguments);
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutLocalServiceImpl.class);

	private static final ThreadLocal<Long> _virtualLayoutTargetGroupId =
		new AutoResetThreadLocal<>(
			LayoutLocalServiceImpl.class + "._virtualLayoutTargetGroupId",
			() -> GroupConstants.DEFAULT_LIVE_GROUP_ID);

}