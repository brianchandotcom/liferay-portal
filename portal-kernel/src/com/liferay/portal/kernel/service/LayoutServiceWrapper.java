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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.model.Layout;

/**
 * Provides a wrapper for {@link LayoutService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutService
 * @generated
 */
public class LayoutServiceWrapper
	implements LayoutService, ServiceWrapper<LayoutService> {

	public LayoutServiceWrapper(LayoutService layoutService) {
		_layoutService = layoutService;
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
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
	 {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param classNameId the class name ID of the entity
	 * @param classPK the primary key of the entity
	 * @param localeNamesMap the layout's locales and localized names
	 * @param localeTitlesMap the layout's locales and localized titles
	 * @param descriptionMap the layout's locales and localized descriptions
	 * @param keywordsMap the layout's locales and localized keywords
	 * @param robotsMap the layout's locales and localized robots
	 * @param type the layout's type (optionally {@link
	 LayoutConstants#TYPE_PORTLET}). The possible types can be found
	 in {@link LayoutConstants}.
	 * @param typeSettings the settings to load the unicode properties object.
	 See {@link com.liferay.portal.kernel.util.UnicodeProperties
	 #fastLoad(String)}.
	 * @param hidden whether the layout is hidden
	 * @param system whether the layout is system
	 * @param masterLayoutPlid the primary key of the master layout
	 * @param friendlyURLMap the layout's locales and localized friendly URLs.
	 To see how the URL is normalized when accessed, see {@link
	 com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 String)}.
	 * @param serviceContext the service context to be applied. Must set the
	 UUID for the layout. Can set the creation date, modification
	 date, and expando bridge attributes for the layout. For layouts
	 that belong to a layout set prototype, an attribute named
	 <code>layoutUpdateable</code> can be used to specify whether site
	 administrators can modify this page within their site.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			long masterLayoutPlid,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.addLayout(
			groupId, privateLayout, parentLayoutId, classNameId, classPK,
			localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
			robotsMap, type, typeSettings, hidden, system, masterLayoutPlid,
			friendlyURLMap, serviceContext);
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
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
	 {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param classNameId the class name ID of the entity
	 * @param classPK the primary key of the entity
	 * @param localeNamesMap the layout's locales and localized names
	 * @param localeTitlesMap the layout's locales and localized titles
	 * @param descriptionMap the layout's locales and localized
	 descriptions
	 * @param keywordsMap the layout's locales and localized keywords
	 * @param robotsMap the layout's locales and localized robots
	 * @param type the layout's type (optionally {@link
	 LayoutConstants#TYPE_PORTLET}). The possible types can be
	 found in {@link LayoutConstants}.
	 * @param typeSettings the settings to load the unicode properties
	 object. See {@link
	 com.liferay.portal.kernel.util.UnicodeProperties
	 #fastLoad(String)}.
	 * @param hidden whether the layout is hidden
	 * @param system whether the layout is system
	 * @param friendlyURLMap the layout's locales and localized friendly
	 URLs. To see how the URL is normalized when accessed, see
	 {@link
	 com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 String)}.
	 * @param serviceContext the service context to be applied. Must set
	 the UUID for the layout. Can set the creation date,
	 modification date, and expando bridge attributes for the
	 layout. For layouts that belong to a layout set prototype, an
	 attribute named <code>layoutUpdateable</code> can be used to
	 specify whether site administrators can modify this page
	 within their site.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #addLayout(long,
	 boolean, long, long, long, Map, Map, Map, Map, Map, String,
	 String, boolean, boolean, long, Map, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.addLayout(
			groupId, privateLayout, parentLayoutId, classNameId, classPK,
			localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
			robotsMap, type, typeSettings, hidden, system, friendlyURLMap,
			serviceContext);
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
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
	 {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param localeNamesMap the layout's locales and localized names
	 * @param localeTitlesMap the layout's locales and localized titles
	 * @param descriptionMap the layout's locales and localized descriptions
	 * @param keywordsMap the layout's locales and localized keywords
	 * @param robotsMap the layout's locales and localized robots
	 * @param type the layout's type (optionally {@link
	 LayoutConstants#TYPE_PORTLET}). The possible types can be found
	 in {@link LayoutConstants}.
	 * @param typeSettings the settings to load the unicode properties object.
	 See {@link com.liferay.portal.kernel.util.UnicodeProperties
	 #fastLoad(String)}.
	 * @param hidden whether the layout is hidden
	 * @param masterLayoutPlid the primary key of the master layout
	 * @param friendlyURLMap the layout's locales and localized friendly URLs.
	 To see how the URL is normalized when accessed, see {@link
	 com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 String)}.
	 * @param serviceContext the service context to be applied. Must set the
	 UUID for the layout. Can set the creation date, modification
	 date, and expando bridge attributes for the layout. For layouts
	 that belong to a layout set prototype, an attribute named
	 <code>layoutUpdateable</code> can be used to specify whether site
	 administrators can modify this page within their site.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, long masterLayoutPlid,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.addLayout(
			groupId, privateLayout, parentLayoutId, localeNamesMap,
			localeTitlesMap, descriptionMap, keywordsMap, robotsMap, type,
			typeSettings, hidden, masterLayoutPlid, friendlyURLMap,
			serviceContext);
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
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
	 {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param localeNamesMap the layout's locales and localized names
	 * @param localeTitlesMap the layout's locales and localized titles
	 * @param descriptionMap the layout's locales and localized descriptions
	 * @param keywordsMap the layout's locales and localized keywords
	 * @param robotsMap the layout's locales and localized robots
	 * @param type the layout's type (optionally {@link
	 LayoutConstants#TYPE_PORTLET}). The possible types can be found
	 in {@link LayoutConstants}.
	 * @param typeSettings the settings to load the unicode properties object.
	 See {@link com.liferay.portal.kernel.util.UnicodeProperties
	 #fastLoad(String)}.
	 * @param hidden whether the layout is hidden
	 * @param friendlyURLMap the layout's locales and localized friendly URLs.
	 To see how the URL is normalized when accessed, see {@link
	 com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 String)}.
	 * @param serviceContext the service context to be applied. Must set the
	 UUID for the layout. Can set the creation date, modification
	 date, and expando bridge attributes for the layout. For layouts
	 that belong to a layout set prototype, an attribute named
	 <code>layoutUpdateable</code> can be used to specify whether site
	 administrators can modify this page within their site.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.addLayout(
			groupId, privateLayout, parentLayoutId, localeNamesMap,
			localeTitlesMap, descriptionMap, keywordsMap, robotsMap, type,
			typeSettings, hidden, friendlyURLMap, serviceContext);
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
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
	 {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param name the layout's locales and localized names
	 * @param title the layout's locales and localized titles
	 * @param description the layout's locales and localized descriptions
	 * @param type the layout's type (optionally {@link
	 LayoutConstants#TYPE_PORTLET}). The possible types can be found
	 in {@link LayoutConstants}.
	 * @param hidden whether the layout is hidden
	 * @param friendlyURL the layout's locales and localized friendly URLs. To
	 see how the URL is normalized when accessed, see {@link
	 com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 String)}.
	 * @param serviceContext the service context to be applied. Must set the
	 UUID for the layout. Can specify the creation date, modification
	 date, and expando bridge attributes for the layout. For layouts
	 that belong to a layout set prototype, an attribute named
	 <code>layoutUpdateable</code> can be used to specify whether site
	 administrators can modify this page within their site.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			String name, String title, String description, String type,
			boolean hidden, String friendlyURL, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.addLayout(
			groupId, privateLayout, parentLayoutId, name, title, description,
			type, hidden, friendlyURL, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			addTempFileEntry(
				long groupId, String folderName, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.addTempFileEntry(
			groupId, folderName, fileName, inputStream, mimeType);
	}

	/**
	 * Deletes the layout with the primary key, also deleting the layout's child
	 * layouts, and associated resources.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteLayout(
			long groupId, boolean privateLayout, long layoutId,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.deleteLayout(
			groupId, privateLayout, layoutId, serviceContext);
	}

	/**
	 * Deletes the layout with the plid, also deleting the layout's child
	 * layouts, and associated resources.
	 *
	 * @param plid the primary key of the layout
	 * @param serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteLayout(long plid, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.deleteLayout(plid, serviceContext);
	}

	@Override
	public void deleteTempFileEntry(
			long groupId, String folderName, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.deleteTempFileEntry(groupId, folderName, fileName);
	}

	/**
	 * Returns all the ancestor layouts of the layout.
	 *
	 * @param plid the primary key of the layout
	 * @return the ancestor layouts of the layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List<Layout> getAncestorLayouts(long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getAncestorLayouts(plid);
	}

	/**
	 * Returns the control panel layout's plid.
	 *
	 * @return the control panel layout's plid
	 * @throws PortalException if a portal exception is occured
	 */
	@Override
	public long getControlPanelLayoutPlid()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getControlPanelLayoutPlid();
	}

	/**
	 * Returns primary key of the matching default layout for the group.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the primary key of the default layout for the group; {@link
	 LayoutConstants#DEFAULT_PLID}) otherwise
	 */
	@Override
	public long getDefaultPlid(long groupId, boolean privateLayout) {
		return _layoutService.getDefaultPlid(groupId, privateLayout);
	}

	/**
	 * Returns the primary key of the default layout for the group.
	 *
	 * @param groupId the primary key of the group
	 * @param scopeGroupId the primary key of the scope group. See {@link
	 ServiceContext#getScopeGroupId()}.
	 * @param privateLayout whether the layout is private to the group
	 * @param portletId the primary key of the portlet
	 * @return Returns the primary key of the default layout group; {@link
	 LayoutConstants#DEFAULT_PLID} otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public long getDefaultPlid(
			long groupId, long scopeGroupId, boolean privateLayout,
			String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getDefaultPlid(
			groupId, scopeGroupId, privateLayout, portletId);
	}

	@Override
	public long getDefaultPlid(
			long groupId, long scopeGroupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getDefaultPlid(groupId, scopeGroupId, portletId);
	}

	/**
	 * Returns the layout matching the UUID, group, and privacy.
	 *
	 * @param uuid the layout's UUID
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the matching layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout getLayoutByUuidAndGroupId(
			String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getLayoutByUuidAndGroupId(
			uuid, groupId, privateLayout);
	}

	/**
	 * Returns the name of the layout.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param languageId the primary key of the language. For more information
	 See {@link Locale}.
	 * @return the layout's name
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public String getLayoutName(
			long groupId, boolean privateLayout, long layoutId,
			String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getLayoutName(
			groupId, privateLayout, layoutId, languageId);
	}

	/**
	 * Returns the layout's plid that matches the parameters.
	 *
	 * @param uuid the layout's UUID
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the matching layout's plid
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public long getLayoutPlid(String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getLayoutPlid(uuid, groupId, privateLayout);
	}

	/**
	 * Returns the layout references for all the layouts that belong to the
	 * company and belong to the portlet that matches the preferences.
	 *
	 * @param companyId the primary key of the company
	 * @param portletId the primary key of the portlet
	 * @param preferencesKey the portlet's preference key
	 * @param preferencesValue the portlet's preference value
	 * @return the layout references of the matching layouts
	 */
	@Override
	public com.liferay.portal.kernel.model.LayoutReference[]
		getLayoutReferences(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue) {

		return _layoutService.getLayoutReferences(
			companyId, portletId, preferencesKey, preferencesValue);
	}

	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, boolean privateLayout) {

		return _layoutService.getLayouts(groupId, privateLayout);
	}

	@Override
	public java.util.List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getLayouts(
			groupId, privateLayout, parentLayoutId);
	}

	@Override
	public java.util.List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean incomplete, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete, start, end);
	}

	@Override
	public java.util.List<Layout> getLayouts(
			long groupId, boolean privateLayout, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getLayouts(groupId, privateLayout, type);
	}

	@Override
	public java.util.List<Layout> getLayouts(long groupId, String type) {
		return _layoutService.getLayouts(groupId, type);
	}

	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, String type, int start, int end) {

		return _layoutService.getLayouts(groupId, type, start, end);
	}

	@Override
	public int getLayoutsCount(long groupId, boolean privateLayout) {
		return _layoutService.getLayoutsCount(groupId, privateLayout);
	}

	@Override
	public int getLayoutsCount(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return _layoutService.getLayoutsCount(
			groupId, privateLayout, parentLayoutId);
	}

	@Override
	public int getLayoutsCount(
		long groupId, boolean privateLayout, long parentLayoutId,
		int priority) {

		return _layoutService.getLayoutsCount(
			groupId, privateLayout, parentLayoutId, priority);
	}

	@Override
	public int getLayoutsCount(long groupId, String type) {
		return _layoutService.getLayoutsCount(groupId, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutService.getOSGiServiceIdentifier();
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.getTempFileNames(groupId, folderName);
	}

	/**
	 * Returns <code>true</code> if there is a matching layout with the UUID,
	 * group, and privacy.
	 *
	 * @param uuid the layout's UUID
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return <code>true</code> if the layout is found; <code>false</code>
	 otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public boolean hasLayout(String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.hasLayout(uuid, groupId, privateLayout);
	}

	@Override
	public boolean hasPortletId(long plid, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.hasPortletId(plid, portletId);
	}

	/**
	 * Schedules a range of layouts to be published.
	 *
	 * @param sourceGroupId the primary key of the source group
	 * @param targetGroupId the primary key of the target group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutIds the layouts considered for publishing, specified by the
	 layout IDs
	 * @param parameterMap the mapping of parameters indicating which
	 information will be used. See {@link
	 com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param groupName the group name (optionally {@link
	 DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	 DestinationNames}.
	 * @param cronText the cron text. See {@link
	 com.liferay.portal.kernel.cal.RecurrenceSerializer #toCronText}
	 * @param schedulerStartDate the scheduler start date
	 * @param schedulerEndDate the scheduler end date
	 * @param description the scheduler description
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void schedulePublishToLive(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			long[] layoutIds, java.util.Map<String, String[]> parameterMap,
			String groupName, String cronText,
			java.util.Date schedulerStartDate, java.util.Date schedulerEndDate,
			String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.schedulePublishToLive(
			sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, groupName, cronText, schedulerStartDate,
			schedulerEndDate, description);
	}

	/**
	 * Schedules a range of layouts to be stored.
	 *
	 * @param sourceGroupId the primary key of the source group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutIdMap the layouts considered for publishing, specified by
	 the layout IDs and booleans indicating whether they have children
	 * @param parameterMap the mapping of parameters indicating which
	 information will be used. See {@link
	 com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	 * @param remoteAddress the remote address
	 * @param remotePort the remote port
	 * @param remotePathContext the remote path context
	 * @param secureConnection whether the connection is secure
	 * @param remoteGroupId the primary key of the remote group
	 * @param remotePrivateLayout whether remote group's layout is private
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param groupName the group name. Optionally {@link
	 DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	 DestinationNames}.
	 * @param cronText the cron text. See {@link
	 com.liferay.portal.kernel.cal.RecurrenceSerializer #toCronText}
	 * @param schedulerStartDate the scheduler start date
	 * @param schedulerEndDate the scheduler end date
	 * @param description the scheduler description
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void schedulePublishToRemote(
			long sourceGroupId, boolean privateLayout,
			java.util.Map<Long, Boolean> layoutIdMap,
			java.util.Map<String, String[]> parameterMap, String remoteAddress,
			int remotePort, String remotePathContext, boolean secureConnection,
			long remoteGroupId, boolean remotePrivateLayout,
			java.util.Date startDate, java.util.Date endDate, String groupName,
			String cronText, java.util.Date schedulerStartDate,
			java.util.Date schedulerEndDate, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.schedulePublishToRemote(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, remotePrivateLayout, startDate, endDate, groupName,
			cronText, schedulerStartDate, schedulerEndDate, description);
	}

	/**
	 * Sets the layouts for the group, replacing and prioritizing all layouts of
	 * the parent layout.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout
	 * @param layoutIds the primary keys of the layouts
	 * @param serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds, serviceContext);
	}

	/**
	 * Deletes the job from the scheduler's queue.
	 *
	 * @param groupId the primary key of the group
	 * @param jobName the job name
	 * @param groupName the group name (optionally {@link
	 DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	 DestinationNames}.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void unschedulePublishToLive(
			long groupId, String jobName, String groupName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.unschedulePublishToLive(groupId, jobName, groupName);
	}

	/**
	 * Deletes the job from the scheduler's persistent queue.
	 *
	 * @param groupId the primary key of the group
	 * @param jobName the job name
	 * @param groupName the group name (optionally {@link
	 DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	 DestinationNames}.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void unschedulePublishToRemote(
			long groupId, String jobName, String groupName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutService.unschedulePublishToRemote(groupId, jobName, groupName);
	}

	@Override
	public Layout updateIconImage(long plid, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateIconImage(plid, bytes);
	}

	/**
	 * Updates the layout with additional parameters.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param parentLayoutId the layout ID of the layout's new parent layout
	 * @param localeNamesMap the layout's locales and localized names
	 * @param localeTitlesMap the layout's locales and localized titles
	 * @param descriptionMap the locales and localized descriptions to merge
	 (optionally <code>null</code>)
	 * @param keywordsMap the locales and localized keywords to merge
	 (optionally <code>null</code>)
	 * @param robotsMap the locales and localized robots to merge (optionally
	 <code>null</code>)
	 * @param type the layout's new type (optionally {@link
	 LayoutConstants#TYPE_PORTLET})
	 * @param hidden whether the layout is hidden
	 * @param friendlyURLMap the layout's locales and localized friendly URLs.
	 To see how the URL is normalized when accessed see {@link
	 com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 String)}.
	 * @param hasIconImage if the layout has a custom icon image
	 * @param iconBytes the byte array of the layout's new icon image
	 * @param serviceContext the service context to be applied. Can set the
	 modification date and expando bridge attributes for the layout.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId,
			java.util.Map<java.util.Locale, String> localeNamesMap,
			java.util.Map<java.util.Locale, String> localeTitlesMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			boolean hasIconImage, byte[] iconBytes,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, localeNamesMap,
			localeTitlesMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURLMap, hasIconImage, iconBytes, serviceContext);
	}

	/**
	 * Updates the layout replacing its type settings.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param typeSettings the settings to load the unicode properties object.
	 See {@link com.liferay.portal.kernel.util.UnicodeProperties
	 #fastLoad(String)}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateLayout(
			groupId, privateLayout, layoutId, typeSettings);
	}

	/**
	 * Updates the look and feel of the layout.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param themeId the primary key of the layout's new theme
	 * @param colorSchemeId the primary key of the layout's new color scheme
	 * @param css the layout's new CSS
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateLookAndFeel(
			groupId, privateLayout, layoutId, themeId, colorSchemeId, css);
	}

	/**
	 * Updates the name of the layout matching the group, layout ID, and
	 * privacy.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param name the layout's new name
	 * @param languageId the primary key of the language. For more information
	 see {@link Locale}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateName(
			groupId, privateLayout, layoutId, name, languageId);
	}

	/**
	 * Updates the name of the layout matching the primary key.
	 *
	 * @param plid the primary key of the layout
	 * @param name the name to be assigned
	 * @param languageId the primary key of the language. For more information
	 see {@link Locale}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateName(long plid, String name, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateName(plid, name, languageId);
	}

	/**
	 * Updates the parent layout ID of the layout matching the group, layout ID,
	 * and privacy.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param parentLayoutId the layout ID to be assigned to the parent layout
	 * @return the matching layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);
	}

	/**
	 * Updates the parent layout ID of the layout matching the primary key. If a
	 * layout matching the parent primary key is found, the layout ID of that
	 * layout is assigned, otherwise {@link
	 * LayoutConstants#DEFAULT_PARENT_LAYOUT_ID} is assigned.
	 *
	 * @param plid the primary key of the layout
	 * @param parentPlid the primary key of the parent layout
	 * @return the layout matching the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateParentLayoutId(plid, parentPlid);
	}

	/**
	 * Updates the parent layout ID and priority of the layout.
	 *
	 * @param plid the primary key of the layout
	 * @param parentPlid the primary key of the parent layout
	 * @param priority the layout's new priority
	 * @return the layout matching the primary key
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateParentLayoutIdAndPriority(
			long plid, long parentPlid, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateParentLayoutIdAndPriority(
			plid, parentPlid, priority);
	}

	/**
	 * Updates the priority of the layout matching the group, layout ID, and
	 * privacy.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updatePriority(
			groupId, privateLayout, layoutId, priority);
	}

	/**
	 * Updates the priority of the layout matching the group, layout ID, and
	 * privacy, setting the layout's priority based on the priorities of the
	 * next and previous layouts.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param nextLayoutId the layout ID of the next layout
	 * @param previousLayoutId the layout ID of the previous layout
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId,
			long nextLayoutId, long previousLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updatePriority(
			groupId, privateLayout, layoutId, nextLayoutId, previousLayoutId);
	}

	/**
	 * Updates the priority of the layout matching the primary key.
	 *
	 * @param plid the primary key of the layout
	 * @param priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(long plid, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updatePriority(plid, priority);
	}

	@Override
	public Layout updateType(long plid, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutService.updateType(plid, type);
	}

	@Override
	public LayoutService getWrappedService() {
		return _layoutService;
	}

	@Override
	public void setWrappedService(LayoutService layoutService) {
		_layoutService = layoutService;
	}

	private LayoutService _layoutService;

}