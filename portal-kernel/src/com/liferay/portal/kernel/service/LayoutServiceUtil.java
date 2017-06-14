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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Layout. This utility wraps
 * {@link com.liferay.portal.service.impl.LayoutServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutService
 * @see com.liferay.portal.service.base.LayoutServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutServiceImpl
 * @generated
 */
@ProviderType
public class LayoutServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static boolean hasLayout(java.lang.String uuid, long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasLayout(uuid, groupId, privateLayout);
	}

	/**
	* Exports all layouts that match the criteria as a byte array.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in
	the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the layout as a byte array
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayouts(groupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	/**
	* Exports the layouts that match the primary keys and the criteria as a
	* byte array.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIds the primary keys of the layouts to be exported
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in
	the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the layouts as a byte array
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayouts(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static byte[] exportPortletInfo(long companyId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfo(companyId, portletId, parameterMap,
			startDate, endDate);
	}

	/**
	* Exports the portlet information (categories, permissions, ... etc.) as a
	* byte array.
	*
	* @param plid the primary key of the layout
	* @param groupId the primary key of the group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in
	the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the portlet information as a byte array
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfo(plid, groupId, portletId, parameterMap,
			startDate, endDate);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportLayoutsFile(
	ExportImportConfiguration, File)}
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportLayoutsFile(exportImportConfiguration, file);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportLayoutsFile(
	ExportImportConfiguration, InputStream)}
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportLayoutsFile(exportImportConfiguration,
			inputStream);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportLayoutsFile(
		long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportLayoutsFile(groupId, privateLayout,
			parameterMap, file);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportLayoutsFile(
		long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportLayoutsFile(groupId, privateLayout,
			parameterMap, inputStream);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportPortletInfo(
	ExportImportConfiguration, File)}
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportPortletInfo(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportPortletInfo(exportImportConfiguration, file);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportPortletInfo(
	ExportImportConfiguration, InputStream)}
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportPortletInfo(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportPortletInfo(exportImportConfiguration,
			inputStream);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportPortletInfo(
		long plid, long groupId, java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportPortletInfo(plid, groupId, portletId,
			parameterMap, file);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.exportimport.kernel.lar.MissingReferences validateImportPortletInfo(
		long plid, long groupId, java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportPortletInfo(plid, groupId, portletId,
			parameterMap, inputStream);
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
	* @param parentLayoutId the primary key of the parent layout (optionally
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
	public static com.liferay.portal.kernel.model.Layout addLayout(
		long groupId, boolean privateLayout, long parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayout(groupId, privateLayout, parentLayoutId, name,
			title, description, type, hidden, friendlyURL, serviceContext);
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
	* @param parentLayoutId the primary key of the parent layout (optionally
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
	public static com.liferay.portal.kernel.model.Layout addLayout(
		long groupId, boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> localeNamesMap,
		java.util.Map<java.util.Locale, java.lang.String> localeTitlesMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, java.lang.String typeSettings, boolean hidden,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayout(groupId, privateLayout, parentLayoutId,
			localeNamesMap, localeTitlesMap, descriptionMap, keywordsMap,
			robotsMap, type, typeSettings, hidden, friendlyURLMap,
			serviceContext);
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
	public static com.liferay.portal.kernel.model.Layout getLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutByUuidAndGroupId(uuid, groupId, privateLayout);
	}

	public static com.liferay.portal.kernel.model.Layout updateIconImage(
		long plid, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateIconImage(plid, bytes);
	}

	/**
	* Updates the layout replacing its type settings.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param typeSettings the settings to load the unicode properties object.
	See {@link com.liferay.portal.kernel.util.UnicodeProperties
	#fastLoad(String)}.
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updateLayout(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId, typeSettings);
	}

	/**
	* Updates the layout with additional parameters.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param parentLayoutId the primary key of the layout's new parent layout
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
	* @param iconImage whether the icon image will be updated
	* @param iconBytes the byte array of the layout's new icon image
	* @param serviceContext the service context to be applied. Can set the
	modification date and expando bridge attributes for the layout.
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updateLayout(
		long groupId, boolean privateLayout, long layoutId,
		long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> localeNamesMap,
		java.util.Map<java.util.Locale, java.lang.String> localeTitlesMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, boolean hidden,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		boolean iconImage, byte[] iconBytes, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, localeNamesMap, localeTitlesMap, descriptionMap,
			keywordsMap, robotsMap, type, hidden, friendlyURLMap, iconImage,
			iconBytes, serviceContext);
	}

	/**
	* Updates the look and feel of the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param themeId the primary key of the layout's new theme
	* @param colorSchemeId the primary key of the layout's new color scheme
	* @param css the layout's new CSS
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updateLookAndFeel(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLookAndFeel(groupId, privateLayout, layoutId,
			themeId, colorSchemeId, css);
	}

	/**
	* Updates the name of the layout matching the group, layout ID, and
	* privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param name the layout's new name
	* @param languageId the primary key of the language. For more information
	see {@link Locale}.
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updateName(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateName(groupId, privateLayout, layoutId, name,
			languageId);
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
	public static com.liferay.portal.kernel.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateName(plid, name, languageId);
	}

	/**
	* Updates the parent layout ID of the layout matching the group, layout ID,
	* and privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param parentLayoutId the primary key to be assigned to the parent
	layout
	* @return the matching layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updateParentLayoutId(
		long groupId, boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateParentLayoutId(groupId, privateLayout, layoutId,
			parentLayoutId);
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
	public static com.liferay.portal.kernel.model.Layout updateParentLayoutId(
		long plid, long parentPlid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateParentLayoutId(plid, parentPlid);
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
	public static com.liferay.portal.kernel.model.Layout updateParentLayoutIdAndPriority(
		long plid, long parentPlid, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateParentLayoutIdAndPriority(plid, parentPlid, priority);
	}

	/**
	* Updates the priority of the layout matching the group, layout ID, and
	* privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updatePriority(
		long groupId, boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePriority(groupId, privateLayout, layoutId, priority);
	}

	/**
	* Updates the priority of the layout matching the group, layout ID, and
	* privacy, setting the layout's priority based on the priorities of the
	* next and previous layouts.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param nextLayoutId the primary key of the next layout
	* @param previousLayoutId the primary key of the previous layout
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updatePriority(
		long groupId, boolean privateLayout, long layoutId, long nextLayoutId,
		long previousLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePriority(groupId, privateLayout, layoutId,
			nextLayoutId, previousLayoutId);
	}

	/**
	* Updates the priority of the layout matching the primary key.
	*
	* @param plid the primary key of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.kernel.model.Layout updatePriority(
		long plid, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updatePriority(plid, priority);
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
	public static com.liferay.portal.kernel.model.LayoutReference[] getLayoutReferences(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue) {
		return getService()
				   .getLayoutReferences(companyId, portletId, preferencesKey,
			preferencesValue);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addTempFileEntry(
		long groupId, java.lang.String folderName, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addTempFileEntry(groupId, folderName, fileName,
			inputStream, mimeType);
	}

	public static int getLayoutsCount(long groupId, boolean privateLayout) {
		return getService().getLayoutsCount(groupId, privateLayout);
	}

	public static int getLayoutsCount(long groupId, boolean privateLayout,
		long parentLayoutId) {
		return getService()
				   .getLayoutsCount(groupId, privateLayout, parentLayoutId);
	}

	public static int getLayoutsCount(long groupId, boolean privateLayout,
		long parentLayoutId, int priority) {
		return getService()
				   .getLayoutsCount(groupId, privateLayout, parentLayoutId,
			priority);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFile(
	ExportImportConfiguration)}
	*/
	@Deprecated
	public static java.io.File exportLayoutsAsFile(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().exportLayoutsAsFile(exportImportConfiguration);
	}

	/**
	* Exports all layouts that match the primary keys and criteria as a file.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIds the primary keys of the layouts to be exported
	(optionally <code>null</code>)
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in
	the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the layouts as a File
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static java.io.File exportLayoutsAsFile(long groupId,
		boolean privateLayout, long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayoutsAsFile(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportPortletInfoAsFile(
	ExportImportConfiguration)}
	*/
	@Deprecated
	public static java.io.File exportPortletInfoAsFile(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().exportPortletInfoAsFile(exportImportConfiguration);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static java.io.File exportPortletInfoAsFile(
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfoAsFile(portletId, parameterMap, startDate,
			endDate);
	}

	/**
	* Exports the portlet information (categories, permissions, ... etc.) as a
	* file.
	*
	* @param plid the primary key of the layout
	* @param groupId the primary key of the group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in
	the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the portlet information as a file
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static java.io.File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfoAsFile(plid, groupId, portletId,
			parameterMap, startDate, endDate);
	}

	/**
	* Returns the name of the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param languageId the primary key of the language. For more information
	See {@link Locale}.
	* @return the layout's name
	* @throws PortalException if a portal exception occurred
	*/
	public static java.lang.String getLayoutName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutName(groupId, privateLayout, layoutId, languageId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.String[] getTempFileNames(long groupId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTempFileNames(groupId, folderName);
	}

	/**
	* Returns all the ancestor layouts of the layout.
	*
	* @param plid the primary key of the layout
	* @return the ancestor layouts of the layout
	* @throws PortalException if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Layout> getAncestorLayouts(
		long plid) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAncestorLayouts(plid);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout> getLayouts(
		long groupId, boolean privateLayout) {
		return getService().getLayouts(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayouts(groupId, privateLayout, parentLayoutId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean incomplete, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayouts(groupId, privateLayout, parentLayoutId,
			incomplete, start, end);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFileInBackground(
	ExportImportConfiguration)}
	*/
	@Deprecated
	public static long exportLayoutsAsFileInBackground(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayoutsAsFileInBackground(exportImportConfiguration);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long exportLayoutsAsFileInBackground(
		java.lang.String taskName, long groupId, boolean privateLayout,
		long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayoutsAsFileInBackground(taskName, groupId,
			privateLayout, layoutIds, parameterMap, startDate, endDate);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long exportLayoutsAsFileInBackground(
		java.lang.String taskName, long groupId, boolean privateLayout,
		long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayoutsAsFileInBackground(taskName, groupId,
			privateLayout, layoutIds, parameterMap, startDate, endDate, fileName);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFileInBackground(
	long)}
	*/
	@Deprecated
	public static long exportLayoutsAsFileInBackground(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayoutsAsFileInBackground(exportImportConfigurationId);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long exportPortletInfoAsFileInBackground(
		java.lang.String taskName, java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfoAsFileInBackground(taskName, portletId,
			parameterMap, startDate, endDate, fileName);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long exportPortletInfoAsFileInBackground(
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfoAsFileInBackground(taskName, plid,
			groupId, portletId, parameterMap, startDate, endDate, fileName);
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
	public static long getDefaultPlid(long groupId, long scopeGroupId,
		boolean privateLayout, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getDefaultPlid(groupId, scopeGroupId, privateLayout,
			portletId);
	}

	public static long getDefaultPlid(long groupId, long scopeGroupId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDefaultPlid(groupId, scopeGroupId, portletId);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long importLayoutsInBackground(java.lang.String taskName,
		long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importLayoutsInBackground(taskName, groupId, privateLayout,
			parameterMap, file);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long importLayoutsInBackground(java.lang.String taskName,
		long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importLayoutsInBackground(taskName, groupId, privateLayout,
			parameterMap, inputStream);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long importPortletInfoInBackground(
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importPortletInfoInBackground(taskName, plid, groupId,
			portletId, parameterMap, file);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static long importPortletInfoInBackground(
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importPortletInfoInBackground(taskName, plid, groupId,
			portletId, parameterMap, is);
	}

	/**
	* Deletes the layout with the primary key, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param serviceContext the service context to be applied
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteLayout(long groupId, boolean privateLayout,
		long layoutId, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteLayout(groupId, privateLayout, layoutId, serviceContext);
	}

	/**
	* Deletes the layout with the plid, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param plid the primary key of the layout
	* @param serviceContext the service context to be applied
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteLayout(long plid, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLayout(plid, serviceContext);
	}

	public static void deleteTempFileEntry(long groupId,
		java.lang.String folderName, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTempFileEntry(groupId, folderName, fileName);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importLayouts(
	ExportImportConfiguration, File)}
	*/
	@Deprecated
	public static void importLayouts(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayouts(exportImportConfiguration, file);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importLayouts(
	ExportImportConfiguration, InputStream)}
	*/
	@Deprecated
	public static void importLayouts(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayouts(exportImportConfiguration, is);
	}

	/**
	* Imports the layouts from the byte array.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param bytes the byte array with the data
	* @throws PortalException if a portal exception occurred
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayouts(groupId, privateLayout, parameterMap, bytes);
	}

	/**
	* Imports the layouts from the file.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param file the LAR file with the data
	* @throws PortalException if a portal exception occurred
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayouts(groupId, privateLayout, parameterMap, file);
	}

	/**
	* Imports the layouts from the input stream.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param is the input stream
	* @throws PortalException if a portal exception occurred
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayouts(groupId, privateLayout, parameterMap, is);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importPortletInfo(
	ExportImportConfiguration, File)} (
	*/
	@Deprecated
	public static void importPortletInfo(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importPortletInfo(exportImportConfiguration, file);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importPortletInfo(
	ExportImportConfiguration, InputStream)} (
	*/
	@Deprecated
	public static void importPortletInfo(
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importPortletInfo(exportImportConfiguration, is);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importPortletInfo(java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importPortletInfo(portletId, parameterMap, file);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importPortletInfo(java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importPortletInfo(portletId, parameterMap, is);
	}

	/**
	* Imports the portlet information (categories, permissions, ... etc.) from
	* the file.
	*
	* @param plid the primary key of the layout
	* @param groupId the primary key of the group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param file the LAR file with the data
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.importPortletInfo(plid, groupId, portletId, parameterMap, file);
	}

	/**
	* Imports the portlet information (categories, permissions, ... etc.) from
	* the input stream.
	*
	* @param plid the primary key of the layout
	* @param groupId the primary key of the group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param is the input stream
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.importPortletInfo(plid, groupId, portletId, parameterMap, is);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importPortletInfoInBackground(
		java.lang.String taskName, java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.importPortletInfoInBackground(taskName, portletId, parameterMap,
			file);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static void importPortletInfoInBackground(
		java.lang.String taskName, java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.importPortletInfoInBackground(taskName, portletId, parameterMap, is);
	}

	/**
	* Schedules a range of layouts to be published.
	*
	* @param sourceGroupId the primary key of the source group
	* @param targetGroupId the primary key of the target group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIdMap the layouts considered for publishing, specified
	by the layout IDs and booleans indicating whether they have
	children
	* @param parameterMap the mapping of parameters indicating which
	information will be used. See {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param scope the scope of the pages
	* @param startDate the start date
	* @param endDate the end date
	* @param groupName the group name (optionally {@link
	DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	DestinationNames}.
	* @param cronText the cron text. See {@link
	com.liferay.portal.kernel.cal.RecurrenceSerializer
	#toCronText}
	* @param schedulerStartDate the scheduler start date
	* @param schedulerEndDate the scheduler end date
	* @param description the scheduler description
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #schedulePublishToLive(long,
	long, boolean, long[], Map, String, Date, Date, String,
	String, Date, Date, String)}
	*/
	@Deprecated
	public static void schedulePublishToLive(long sourceGroupId,
		long targetGroupId, boolean privateLayout,
		java.util.Map<java.lang.Long, java.lang.Boolean> layoutIdMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String scope, java.util.Date startDate,
		java.util.Date endDate, java.lang.String groupName,
		java.lang.String cronText, java.util.Date schedulerStartDate,
		java.util.Date schedulerEndDate, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.schedulePublishToLive(sourceGroupId, targetGroupId, privateLayout,
			layoutIdMap, parameterMap, scope, startDate, endDate, groupName,
			cronText, schedulerStartDate, schedulerEndDate, description);
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
	public static void schedulePublishToLive(long sourceGroupId,
		long targetGroupId, boolean privateLayout, long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String groupName, java.lang.String cronText,
		java.util.Date schedulerStartDate, java.util.Date schedulerEndDate,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.schedulePublishToLive(sourceGroupId, targetGroupId, privateLayout,
			layoutIds, parameterMap, groupName, cronText, schedulerStartDate,
			schedulerEndDate, description);
	}

	/**
	* Schedules a range of layouts to be published.
	*
	* @param sourceGroupId the primary key of the source group
	* @param targetGroupId the primary key of the target group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIds the layouts considered for publishing, specified by
	the layout IDs
	* @param parameterMap the mapping of parameters indicating which
	information will be used. See {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param scope the scope of the pages. It can be
	<code>all-pages</code> or <code>selected-pages</code>.
	* @param startDate the start date
	* @param endDate the end date
	* @param groupName the group name (optionally {@link
	DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	DestinationNames}.
	* @param cronText the cron text. See {@link
	com.liferay.portal.kernel.cal.RecurrenceSerializer
	#toCronText}
	* @param schedulerStartDate the scheduler start date
	* @param schedulerEndDate the scheduler end date
	* @param description the scheduler description
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #schedulePublishToLive(long,
	long, boolean, long[], Map, String, String, Date, Date,
	String)}
	*/
	@Deprecated
	public static void schedulePublishToLive(long sourceGroupId,
		long targetGroupId, boolean privateLayout, long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String scope, java.util.Date startDate,
		java.util.Date endDate, java.lang.String groupName,
		java.lang.String cronText, java.util.Date schedulerStartDate,
		java.util.Date schedulerEndDate, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.schedulePublishToLive(sourceGroupId, targetGroupId, privateLayout,
			layoutIds, parameterMap, scope, startDate, endDate, groupName,
			cronText, schedulerStartDate, schedulerEndDate, description);
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
	public static void schedulePublishToRemote(long sourceGroupId,
		boolean privateLayout,
		java.util.Map<java.lang.Long, java.lang.Boolean> layoutIdMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String remoteAddress, int remotePort,
		java.lang.String remotePathContext, boolean secureConnection,
		long remoteGroupId, boolean remotePrivateLayout,
		java.util.Date startDate, java.util.Date endDate,
		java.lang.String groupName, java.lang.String cronText,
		java.util.Date schedulerStartDate, java.util.Date schedulerEndDate,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.schedulePublishToRemote(sourceGroupId, privateLayout, layoutIdMap,
			parameterMap, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId, remotePrivateLayout, startDate,
			endDate, groupName, cronText, schedulerStartDate, schedulerEndDate,
			description);
	}

	/**
	* Sets the layouts for the group, replacing and prioritizing all layouts of
	* the parent layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout
	* @param layoutIds the primary keys of the layouts
	* @param serviceContext the service context to be applied
	* @throws PortalException if a portal exception occurred
	*/
	public static void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.setLayouts(groupId, privateLayout, parentLayoutId, layoutIds,
			serviceContext);
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
	public static void unschedulePublishToLive(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unschedulePublishToLive(groupId, jobName, groupName);
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
	public static void unschedulePublishToRemote(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unschedulePublishToRemote(groupId, jobName, groupName);
	}

	public static LayoutService getService() {
		if (_service == null) {
			_service = (LayoutService)PortalBeanLocatorUtil.locate(LayoutService.class.getName());

			ReferenceRegistry.registerReference(LayoutServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static LayoutService _service;
}