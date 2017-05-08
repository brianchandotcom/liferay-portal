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

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;

import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutReference;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for Layout. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutServiceUtil
 * @see com.liferay.portal.service.base.LayoutServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutServiceUtil} to access the layout remote service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns true if the layout matching the UUID, group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return true if the layout is found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayout(java.lang.String uuid, long groupId,
		boolean privateLayout) throws PortalException;

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
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public byte[] exportLayouts(long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

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
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public byte[] exportPortletInfo(long companyId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

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
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportLayoutsFile(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportLayoutsFile(
	ExportImportConfiguration, InputStream)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(long groupId,
		boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(long groupId,
		boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		InputStream inputStream) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportPortletInfo(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#validateImportPortletInfo(
	ExportImportConfiguration, InputStream)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		InputStream inputStream) throws PortalException;

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
	*/
	public Layout addLayout(long groupId, boolean privateLayout,
		long parentLayoutId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, ServiceContext serviceContext)
		throws PortalException;

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
	*/
	public Layout addLayout(long groupId, boolean privateLayout,
		long parentLayoutId, Map<Locale, java.lang.String> localeNamesMap,
		Map<Locale, java.lang.String> localeTitlesMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> keywordsMap,
		Map<Locale, java.lang.String> robotsMap, java.lang.String type,
		java.lang.String typeSettings, boolean hidden,
		Map<Locale, java.lang.String> friendlyURLMap,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the layout matching the UUID, group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layout
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout getLayoutByUuidAndGroupId(java.lang.String uuid,
		long groupId, boolean privateLayout) throws PortalException;

	public Layout updateIconImage(long plid, byte[] bytes)
		throws PortalException;

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
	*/
	public Layout updateLayout(long groupId, boolean privateLayout,
		long layoutId, java.lang.String typeSettings) throws PortalException;

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
	*/
	public Layout updateLayout(long groupId, boolean privateLayout,
		long layoutId, long parentLayoutId,
		Map<Locale, java.lang.String> localeNamesMap,
		Map<Locale, java.lang.String> localeTitlesMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> keywordsMap,
		Map<Locale, java.lang.String> robotsMap, java.lang.String type,
		boolean hidden, Map<Locale, java.lang.String> friendlyURLMap,
		boolean iconImage, byte[] iconBytes, ServiceContext serviceContext)
		throws PortalException;

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
	*/
	public Layout updateLookAndFeel(long groupId, boolean privateLayout,
		long layoutId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css)
		throws PortalException;

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
	*/
	public Layout updateName(long groupId, boolean privateLayout,
		long layoutId, java.lang.String name, java.lang.String languageId)
		throws PortalException;

	/**
	* Updates the name of the layout matching the primary key.
	*
	* @param plid the primary key of the layout
	* @param name the name to be assigned
	* @param languageId the primary key of the language. For more information
	see {@link Locale}.
	* @return the updated layout
	*/
	public Layout updateName(long plid, java.lang.String name,
		java.lang.String languageId) throws PortalException;

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
	*/
	public Layout updateParentLayoutId(long groupId, boolean privateLayout,
		long layoutId, long parentLayoutId) throws PortalException;

	/**
	* Updates the parent layout ID of the layout matching the primary key. If a
	* layout matching the parent primary key is found, the layout ID of that
	* layout is assigned, otherwise {@link
	* LayoutConstants#DEFAULT_PARENT_LAYOUT_ID} is assigned.
	*
	* @param plid the primary key of the layout
	* @param parentPlid the primary key of the parent layout
	* @return the layout matching the primary key
	*/
	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException;

	/**
	* Updates the parent layout ID and priority of the layout.
	*
	* @param plid the primary key of the layout
	* @param parentPlid the primary key of the parent layout
	* @param priority the layout's new priority
	* @return the layout matching the primary key
	*/
	public Layout updateParentLayoutIdAndPriority(long plid, long parentPlid,
		int priority) throws PortalException;

	/**
	* Updates the priority of the layout matching the group, layout ID, and
	* privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	*/
	public Layout updatePriority(long groupId, boolean privateLayout,
		long layoutId, int priority) throws PortalException;

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
	*/
	public Layout updatePriority(long groupId, boolean privateLayout,
		long layoutId, long nextLayoutId, long previousLayoutId)
		throws PortalException;

	/**
	* Updates the priority of the layout matching the primary key.
	*
	* @param plid the primary key of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	*/
	public Layout updatePriority(long plid, int priority)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutReference[] getLayoutReferences(long companyId,
		java.lang.String portletId, java.lang.String preferencesKey,
		java.lang.String preferencesValue);

	public FileEntry addTempFileEntry(long groupId,
		java.lang.String folderName, java.lang.String fileName,
		InputStream inputStream, java.lang.String mimeType)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(long groupId, boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(long groupId, boolean privateLayout,
		long parentLayoutId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(long groupId, boolean privateLayout,
		long parentLayoutId, int priority);

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFile(
	ExportImportConfiguration)}
	*/
	@java.lang.Deprecated
	public File exportLayoutsAsFile(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

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
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public File exportLayoutsAsFile(long groupId, boolean privateLayout,
		long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportPortletInfoAsFile(
	ExportImportConfiguration)}
	*/
	@java.lang.Deprecated
	public File exportPortletInfoAsFile(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public File exportPortletInfoAsFile(java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

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
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* Returns the name of the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param languageId the primary key of the language. For more information
	See {@link Locale}.
	* @return the layout's name
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getLayoutName(long groupId, boolean privateLayout,
		long layoutId, java.lang.String languageId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempFileNames(long groupId,
		java.lang.String folderName) throws PortalException;

	/**
	* Returns all the ancestor layouts of the layout.
	*
	* @param plid the primary key of the layout
	* @return the ancestor layouts of the layout
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getAncestorLayouts(long plid) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout,
		long parentLayoutId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, boolean incomplete, int start, int end)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFileInBackground(
	ExportImportConfiguration)}
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(java.lang.String taskName,
		long groupId, boolean privateLayout, long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(java.lang.String taskName,
		long groupId, boolean privateLayout, long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate, java.lang.String fileName) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#exportLayoutsAsFileInBackground(
	long)}
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(
		long exportImportConfigurationId) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportPortletInfoAsFileInBackground(java.lang.String taskName,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate, java.lang.String fileName) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportPortletInfoAsFileInBackground(java.lang.String taskName,
		long plid, long groupId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate, java.lang.String fileName) throws PortalException;

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
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getDefaultPlid(long groupId, long scopeGroupId,
		boolean privateLayout, java.lang.String portletId)
		throws PortalException;

	@ThreadLocalCachable
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getDefaultPlid(long groupId, long scopeGroupId,
		java.lang.String portletId) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importLayoutsInBackground(java.lang.String taskName,
		long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importLayoutsInBackground(java.lang.String taskName,
		long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		InputStream inputStream) throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(java.lang.String taskName,
		long plid, long groupId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(java.lang.String taskName,
		long plid, long groupId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* Deletes the layout with the primary key, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param serviceContext the service context to be applied
	*/
	public void deleteLayout(long groupId, boolean privateLayout,
		long layoutId, ServiceContext serviceContext) throws PortalException;

	/**
	* Deletes the layout with the plid, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param plid the primary key of the layout
	* @param serviceContext the service context to be applied
	*/
	public void deleteLayout(long plid, ServiceContext serviceContext)
		throws PortalException;

	public void deleteTempFileEntry(long groupId, java.lang.String folderName,
		java.lang.String fileName) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importLayouts(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public void importLayouts(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importLayouts(
	ExportImportConfiguration, InputStream)}
	*/
	@java.lang.Deprecated
	public void importLayouts(
		ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException;

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
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importLayouts(long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, byte[] bytes)
		throws PortalException;

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
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importLayouts(long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

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
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importLayouts(long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importPortletInfo(
	ExportImportConfiguration, File)} (
	*/
	@java.lang.Deprecated
	public void importPortletInfo(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportService#importPortletInfo(
	ExportImportConfiguration, InputStream)} (
	*/
	@java.lang.Deprecated
	public void importPortletInfo(
		ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

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
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

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
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfoInBackground(java.lang.String taskName,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfoInBackground(java.lang.String taskName,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

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
	* @param groupName the group name (optionally {@link
	DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	DestinationNames}.
	* @param cronText the cron text. See {@link
	com.liferay.portal.kernel.cal.RecurrenceSerializer
	#toCronText}
	* @param schedulerStartDate the scheduler start date
	* @param schedulerEndDate the scheduler end date
	* @param description the scheduler description
	* @deprecated As of 7.0.0, replaced by {@link #schedulePublishToLive(long,
	long, boolean, long[], Map, String, Date, Date, String,
	String, Date, Date, String)}
	*/
	@java.lang.Deprecated
	public void schedulePublishToLive(long sourceGroupId, long targetGroupId,
		boolean privateLayout,
		Map<java.lang.Long, java.lang.Boolean> layoutIdMap,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String scope, Date startDate, Date endDate,
		java.lang.String groupName, java.lang.String cronText,
		Date schedulerStartDate, Date schedulerEndDate,
		java.lang.String description) throws PortalException;

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
	*/
	public void schedulePublishToLive(long sourceGroupId, long targetGroupId,
		boolean privateLayout, long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String groupName, java.lang.String cronText,
		Date schedulerStartDate, Date schedulerEndDate,
		java.lang.String description) throws PortalException;

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
	* @deprecated As of 7.0.0, replaced by {@link #schedulePublishToLive(long,
	long, boolean, long[], Map, String, String, Date, Date,
	String)}
	*/
	@java.lang.Deprecated
	public void schedulePublishToLive(long sourceGroupId, long targetGroupId,
		boolean privateLayout, long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String scope, Date startDate, Date endDate,
		java.lang.String groupName, java.lang.String cronText,
		Date schedulerStartDate, Date schedulerEndDate,
		java.lang.String description) throws PortalException;

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
	*/
	public void schedulePublishToRemote(long sourceGroupId,
		boolean privateLayout,
		Map<java.lang.Long, java.lang.Boolean> layoutIdMap,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		java.lang.String remoteAddress, int remotePort,
		java.lang.String remotePathContext, boolean secureConnection,
		long remoteGroupId, boolean remotePrivateLayout, Date startDate,
		Date endDate, java.lang.String groupName, java.lang.String cronText,
		Date schedulerStartDate, Date schedulerEndDate,
		java.lang.String description) throws PortalException;

	/**
	* Sets the layouts for the group, replacing and prioritizing all layouts of
	* the parent layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout
	* @param layoutIds the primary keys of the layouts
	* @param serviceContext the service context to be applied
	*/
	public void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Deletes the job from the scheduler's queue.
	*
	* @param groupId the primary key of the group
	* @param jobName the job name
	* @param groupName the group name (optionally {@link
	DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	DestinationNames}.
	*/
	public void unschedulePublishToLive(long groupId, java.lang.String jobName,
		java.lang.String groupName) throws PortalException;

	/**
	* Deletes the job from the scheduler's persistent queue.
	*
	* @param groupId the primary key of the group
	* @param jobName the job name
	* @param groupName the group name (optionally {@link
	DestinationNames#LAYOUTS_LOCAL_PUBLISHER}). See {@link
	DestinationNames}.
	*/
	public void unschedulePublishToRemote(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws PortalException;
}