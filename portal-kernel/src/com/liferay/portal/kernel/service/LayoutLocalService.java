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
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutReference;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for Layout. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutLocalServiceUtil
 * @see com.liferay.portal.service.base.LayoutLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutLocalServiceUtil} to access the layout local service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns true if there is a matching layout for UUID, groupId and
	* privateLayout
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return true if the layout is found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayout(java.lang.String uuid, long groupId,
		boolean privateLayout) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayoutSetPrototypeLayout(
		java.lang.String layoutSetPrototypeUuid, long companyId,
		java.lang.String layoutUuid) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayoutSetPrototypeLayout(long layoutSetPrototypeId,
		java.lang.String layoutUuid) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayouts(Group group) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayouts(Group group, boolean privateLayout)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayouts(Group group, boolean privateLayout,
		boolean includeUserGroups) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayouts(User user, boolean privateLayout)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayouts(User user, boolean privateLayout,
		boolean includeUserGroups) throws PortalException;

	/**
	* Returns <code>true</code> if the group has any layouts;
	* <code>false</code> otherwise.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout
	* @return <code>true</code> if the group has any layouts;
	<code>false</code> otherwise
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasLayouts(long groupId, boolean privateLayout,
		long parentLayoutId);

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
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public byte[] exportLayouts(long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* Exports layouts with the primary keys and criteria as a byte array.
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
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @throws PortalException
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
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportLayoutsFile(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportLayoutsFile(
	ExportImportConfiguration, InputStream)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(long userId,
		long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportLayoutsFile(long userId,
		long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		InputStream inputStream) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportPortletInfo(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#validateImportPortletInfo(
	ExportImportConfiguration, InputStream)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(long userId, long plid,
		long groupId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences validateImportPortletInfo(long userId, long plid,
		long groupId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap,
		InputStream inputStream) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Adds the layout to the database. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Layout addLayout(Layout layout);

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
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout (optionally
	{@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID}). The possible
	values can be found in {@link LayoutConstants}.
	* @param name the layout's name (optionally {@link
	PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_NAME} or {@link
	PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_NAME}). The default values
	can be overridden in <code>portal-ext.properties</code> by
	specifying new values for the corresponding properties defined in
	{@link PropsValues}
	* @param title the layout's title
	* @param description the layout's description
	* @param type the layout's type (optionally {@link
	LayoutConstants#TYPE_PORTLET}). The possible types can be found
	in {@link LayoutConstants}.
	* @param hidden whether the layout is hidden
	* @param friendlyURL the friendly URL of the layout (optionally {@link
	PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL} or {@link
	PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL}). The
	default values can be overridden in
	<code>portal-ext.properties</code> by specifying new values for
	the corresponding properties defined in {@link PropsValues}. To
	see how the URL is normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param serviceContext the service context to be applied. Must set the
	UUID for the layout. Can set the creation date and modification
	date for the layout. For layouts that belong to a layout set
	prototype, an attribute named <code>layoutUpdateable</code> can
	be set to specify whether site administrators can modify this
	page within their site.
	* @return the layout
	*/
	public Layout addLayout(long userId, long groupId, boolean privateLayout,
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
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout (optionally
	{@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	* @param nameMap the layout's locales and localized names
	* @param titleMap the layout's locales and localized titles
	* @param descriptionMap the layout's locales and localized descriptions
	* @param keywordsMap the layout's locales and localized keywords
	* @param robotsMap the layout's locales and localized robots
	* @param type the layout's type (optionally {@link
	LayoutConstants#TYPE_PORTLET}). The possible types can be found
	in {@link LayoutConstants}.
	* @param typeSettings the settings to load the unicode properties object.
	See {@link UnicodeProperties #fastLoad(String)}.
	* @param hidden whether the layout is hidden
	* @param friendlyURLMap the layout's locales and localized friendly URLs.
	To see how the URL is normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param serviceContext the service context to be applied. Must set the
	UUID for the layout. Can set the creation date, modification
	date, and expando bridge attributes for the layout. For layouts
	that belong to a layout set prototype, an attribute named
	<code>layoutUpdateable</code> can be set to specify whether site
	administrators can modify this page within their site. For
	layouts that are created from a layout prototype, attributes
	named <code>layoutPrototypeUuid</code> and
	<code>layoutPrototypeLinkedEnabled</code> can be specified to
	provide the unique identifier of the source prototype and a
	boolean to determine whether a link to it should be enabled to
	activate propagation of changes made to the linked page in the
	prototype.
	* @return the layout
	*/
	public Layout addLayout(long userId, long groupId, boolean privateLayout,
		long parentLayoutId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> keywordsMap,
		Map<Locale, java.lang.String> robotsMap, java.lang.String type,
		java.lang.String typeSettings, boolean hidden,
		Map<Locale, java.lang.String> friendlyURLMap,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new layout with the primary key. Does not add the layout to the database.
	*
	* @param plid the primary key for the new layout
	* @return the new layout
	*/
	public Layout createLayout(long plid);

	/**
	* Deletes the layout from the database. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public Layout deleteLayout(Layout layout);

	/**
	* Deletes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the layout
	* @return the layout that was removed
	* @throws PortalException if a layout with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public Layout deleteLayout(long plid) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchDefaultLayout(long groupId, boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchFirstLayout(long groupId, boolean privateLayout,
		long parentLayoutId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchLayout(java.lang.String uuid, long groupId,
		boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchLayout(long groupId, boolean privateLayout, long layoutId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchLayout(long plid);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchLayoutByFriendlyURL(long groupId, boolean privateLayout,
		java.lang.String friendlyURL);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchLayoutByIconImageId(boolean privateLayout,
		long iconImageId) throws PortalException;

	/**
	* Returns the layout matching the UUID, group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout fetchLayoutByUuidAndGroupId(java.lang.String uuid,
		long groupId, boolean privateLayout);

	/**
	* Returns the layout for the friendly URL
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param friendlyURL the friendly URL of the layout
	* @return the layout for the friendly URL
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout getFriendlyURLLayout(long groupId, boolean privateLayout,
		java.lang.String friendlyURL) throws PortalException;

	/**
	* Returns the layout matching the primary key, group, and privacy; throws a
	* {@link NoSuchLayoutException} otherwise.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @return the matching layout
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout getLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException;

	/**
	* Returns the layout with the primary key.
	*
	* @param plid the primary key of the layout
	* @return the layout
	* @throws PortalException if a layout with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout getLayout(long plid) throws PortalException;

	/**
	* Returns the layout for the icon image; throws a {@link
	* NoSuchLayoutException} otherwise.
	*
	* @param iconImageId the primary key of the icon image
	* @return Returns the layout for the icon image
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout getLayoutByIconImageId(long iconImageId)
		throws PortalException;

	/**
	* Returns the layout matching the UUID, group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layout
	* @throws PortalException if a matching layout could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout getLayoutByUuidAndGroupId(java.lang.String uuid,
		long groupId, boolean privateLayout) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Layout getParentLayout(Layout layout) throws PortalException;

	/**
	* Updates the friendly URL of the layout.
	*
	* @param plid the primary key of the layout
	* @param friendlyURL the friendly URL to be assigned
	* @param languageId the primary key of the language
	* @return the updated layout
	* @deprecated As of 7.0.0, replaced by {@link #updateFriendlyURL(long,
	long, String, String)}
	*/
	@java.lang.Deprecated
	public Layout updateFriendlyURL(long plid, java.lang.String friendlyURL,
		java.lang.String languageId) throws PortalException;

	/**
	* Updates the friendly URL of the layout.
	*
	* @param userId the primary key of the user
	* @param plid the primary key of the layout
	* @param friendlyURL the friendly URL to be assigned
	* @param languageId the primary key of the language
	* @return the updated layout
	*/
	public Layout updateFriendlyURL(long userId, long plid,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws PortalException;

	public Layout updateIconImage(long plid, byte[] bytes)
		throws PortalException;

	/**
	* Updates the layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Layout updateLayout(Layout layout);

	/**
	* Updates the layout replacing its type settings.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param typeSettings the settings to load the unicode properties object.
	See {@link UnicodeProperties #fastLoad(String)}.
	* @return the updated layout
	*/
	public Layout updateLayout(long groupId, boolean privateLayout,
		long layoutId, java.lang.String typeSettings) throws PortalException;

	/**
	* Updates the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param parentLayoutId the primary key of the layout's new parent layout
	* @param nameMap the locales and localized names to merge (optionally
	<code>null</code>)
	* @param titleMap the locales and localized titles to merge (optionally
	<code>null</code>)
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
	To see how the URL is normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param iconImage whether the icon image will be updated
	* @param iconBytes the byte array of the layout's new icon image
	* @param serviceContext the service context to be applied. Can set the
	modification date and expando bridge attributes for the layout.
	For layouts that are linked to a layout prototype, attributes
	named <code>layoutPrototypeUuid</code> and
	<code>layoutPrototypeLinkedEnabled</code> can be specified to
	provide the unique identifier of the source prototype and a
	boolean to determine whether a link to it should be enabled to
	activate propagation of changes made to the linked page in the
	prototype.
	* @return the updated layout
	*/
	public Layout updateLayout(long groupId, boolean privateLayout,
		long layoutId, long parentLayoutId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> titleMap,
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
	* Updates the name of the layout.
	*
	* @param layout the layout to be updated
	* @param name the layout's new name
	* @param languageId the primary key of the language. For more information
	see {@link Locale}.
	* @return the updated layout
	*/
	public Layout updateName(Layout layout, java.lang.String name,
		java.lang.String languageId) throws PortalException;

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
	* Updates the priority of the layout.
	*
	* @param layout the layout to be updated
	* @param priority the layout's new priority
	* @return the updated layout
	*/
	public Layout updatePriority(Layout layout, int priority)
		throws PortalException;

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
	public LayoutReference[] getLayouts(long companyId,
		java.lang.String portletId, java.lang.String preferencesKey,
		java.lang.String preferencesValue);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsByLayoutPrototypeUuidCount(
		java.lang.String layoutPrototypeUuid);

	/**
	* Returns the number of layouts.
	*
	* @return the number of layouts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(Group group, boolean privateLayout)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(Group group, boolean privateLayout,
		boolean includeUserGroups) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(Group group, boolean privateLayout,
		long parentLayoutId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(Group group, boolean privateLayout,
		long[] layoutIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(User user, boolean privateLayout)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutsCount(User user, boolean privateLayout,
		boolean includeUserGroups) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#exportLayoutsAsFile(
	ExportImportConfiguration)}
	*/
	@java.lang.Deprecated
	public File exportLayoutsAsFile(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* Exports the layouts that match the primary keys and criteria as a file.
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
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public File exportLayoutsAsFile(long groupId, boolean privateLayout,
		long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#exportPortletInfoAsFile(
	ExportImportConfiguration)}}
	*/
	@java.lang.Deprecated
	public File exportPortletInfoAsFile(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public File exportPortletInfoAsFile(long companyId,
		java.lang.String portletId,
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
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the range of layouts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long companyId);

	/**
	* Returns all the layouts belonging to the group.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layouts, or <code>null</code> if no matches were
	found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout);

	/**
	* Returns all the layouts that match the type and belong to the group.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param type the type of the layouts (optionally {@link
	LayoutConstants#TYPE_PORTLET})
	* @return the matching layouts, or <code>null</code> if no matches were
	found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout,
		java.lang.String type);

	/**
	* Returns all the layouts belonging to the group that are children of the
	* parent layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout
	* @return the matching layouts, or <code>null</code> if no matches were
	found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout,
		long parentLayoutId);

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
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout
	* @param incomplete whether the layout is incomplete
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @return the matching layouts, or <code>null</code> if no matches were
	found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, boolean incomplete, int start, int end);

	/**
	* Returns all the layouts that match the layout IDs and belong to the
	* group.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIds the primary keys of the layouts
	* @return the matching layouts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayouts(long groupId, boolean privateLayout,
		long[] layoutIds) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayoutsByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid);

	/**
	* Returns all the layouts matching the UUID and company.
	*
	* @param uuid the UUID of the layouts
	* @param companyId the primary key of the company
	* @return the matching layouts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayoutsByUuidAndCompanyId(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of layouts matching the UUID and company.
	*
	* @param uuid the UUID of the layouts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of layouts
	* @param end the upper bound of the range of layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching layouts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getLayoutsByUuidAndCompanyId(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<Layout> orderByComparator);

	/**
	* Returns all the layouts without resource permissions
	*
	* @param roleId the primary key of the role
	* @return all the layouts without resource permissions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getNoPermissionLayouts(long roleId);

	/**
	* Returns all the layouts whose friendly URLs are <code>null</code>
	*
	* @return all the layouts whose friendly URLs are <code>null</code>
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getNullFriendlyURLLayouts();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getScopeGroupLayouts(long parentGroupId)
		throws PortalException;

	/**
	* Returns all the layouts within scope of the group
	*
	* @param privateLayout whether the layout is private to the group
	* @return the layouts within scope of the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Layout> getScopeGroupLayouts(long parentGroupId,
		boolean privateLayout) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<java.lang.Long, List<Layout>> getLayoutChildLayouts(
		LayoutSet layoutSet, List<Layout> parentLayouts);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#exportLayoutsAsFileInBackground(
	long, ExportImportConfiguration)}
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(long userId,
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(long userId,
		java.lang.String taskName, long groupId, boolean privateLayout,
		long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(long userId,
		java.lang.String taskName, long groupId, boolean privateLayout,
		long[] layoutIds,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate, java.lang.String fileName) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#exportLayoutsAsFileInBackground(
	long, long)}
	*/
	@java.lang.Deprecated
	public long exportLayoutsAsFileInBackground(long userId,
		long exportImportConfigurationId) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#exportPortletInfoAsFileInBackground(
	long, ExportImportConfiguration)}}
	*/
	@java.lang.Deprecated
	public long exportPortletInfoAsFileInBackground(long userId,
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportPortletInfoAsFileInBackground(long userId,
		java.lang.String taskName, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate, java.lang.String fileName) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long exportPortletInfoAsFileInBackground(long userId,
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, Date startDate,
		Date endDate, java.lang.String fileName) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#exportPortletInfoAsFileInBackground(
	long, long)}}
	*/
	@java.lang.Deprecated
	public long exportPortletInfoAsFileInBackground(long userId,
		long exportImportConfigurationId) throws PortalException;

	/**
	* Returns the primary key of the default layout for the group
	*
	* @param groupId the primary key of the group
	* @return the primary key of the default layout for the group (optionally
	{@link LayoutConstants#DEFAULT_PLID})
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getDefaultPlid(long groupId);

	/**
	* Returns primary key of the matching default layout for the group
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the primary key of the default layout for the group; {@link
	LayoutConstants#DEFAULT_PLID}) otherwise
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getDefaultPlid(long groupId, boolean privateLayout);

	/**
	* Returns primary key of the default portlet layout for the group
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param portletId the primary key of the portlet
	* @return the primary key of the default portlet layout for the group;
	{@link LayoutConstants#DEFAULT_PLID} otherwise
	* @throws PortalException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getDefaultPlid(long groupId, boolean privateLayout,
		java.lang.String portletId) throws PortalException;

	/**
	* Returns the primary key to use for the next layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the primary key to use for the next layout
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getNextLayoutId(long groupId, boolean privateLayout);

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayoutsInBackground(
	long, ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public long importLayoutsInBackground(long userId,
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importLayoutsInBackground(long userId,
		java.lang.String taskName, long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importLayoutsInBackground(long userId,
		java.lang.String taskName, long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayoutsInBackground(
	long, long, File)}
	*/
	@java.lang.Deprecated
	public long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, File file) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfoInBackground(
	long, ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(long userId,
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(long userId,
		java.lang.String taskName, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(long userId,
		java.lang.String taskName, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(long userId,
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(long userId,
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfoInBackground(
	long, long, File)}
	*/
	@java.lang.Deprecated
	public long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, File file) throws PortalException;

	/**
	* Deletes the layout, its child layouts, and its associated resources.
	*
	* @param layout the layout
	* @param updateLayoutSet whether the layout set's page counter needs to be
	updated
	* @param serviceContext the service context to be applied
	*/
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public void deleteLayout(Layout layout, boolean updateLayoutSet,
		ServiceContext serviceContext) throws PortalException;

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

	/**
	* Deletes the group's private or non-private layouts, also deleting the
	* layouts' child layouts, and associated resources.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param serviceContext the service context to be applied. The parent
	layout set's page count will be updated by default, unless an
	attribute named <code>updatePageCount</code> is set to
	<code>false</code>.
	*/
	public void deleteLayouts(long groupId, boolean privateLayout,
		ServiceContext serviceContext) throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayouts(
	ExportImportConfiguration, File)}}
	*/
	@java.lang.Deprecated
	public void importLayouts(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayouts(
	ExportImportConfiguration, InputStream)}}
	*/
	@java.lang.Deprecated
	public void importLayouts(
		ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException;

	/**
	* Imports the layouts from the byte array.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param bytes the byte array with the data
	* @throws PortalException
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importLayouts(long userId, long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, byte[] bytes)
		throws PortalException;

	/**
	* Imports the layouts from the file.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param file the LAR file with the data
	* @throws PortalException
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importLayouts(long userId, long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* Imports the layouts from the input stream.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param is the input stream
	* @throws PortalException
	* @see com.liferay.exportimport.kernel.lar.LayoutImporter
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importLayouts(long userId, long groupId, boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importLayoutsDataDeletions(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public void importLayoutsDataDeletions(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletDataDeletions(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public void importPortletDataDeletions(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfo(
	ExportImportConfiguration, File)}
	*/
	@java.lang.Deprecated
	public void importPortletInfo(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.exportimport.kernel.service.ExportImportLocalService#importPortletInfo(
	ExportImportConfiguration, InputStream)}
	*/
	@java.lang.Deprecated
	public void importPortletInfo(
		ExportImportConfiguration exportImportConfiguration, InputStream is)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(long userId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(long userId, java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

	/**
	* Imports the portlet information (categories, permissions, ... etc.) from
	* the file.
	*
	* @param userId the primary key of the user
	* @param plid the primary key of the target layout
	* @param groupId the primary key of the target group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param file the LAR file with the data
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, File file)
		throws PortalException;

	/**
	* Imports the portlet information (categories, permissions, ... etc.) from
	* the input stream.
	*
	* @param userId the primary key of the user
	* @param plid the primary key of the layout
	* @param groupId the primary key of the group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys
	used in the map see {@link
	com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys}.
	* @param is the input stream
	* @throws PortalException
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		Map<java.lang.String, java.lang.String[]> parameterMap, InputStream is)
		throws PortalException;

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

	public void updateAsset(long userId, Layout layout,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws PortalException;

	/**
	* Updates the priorities of the layouts.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @throws PortalException
	*/
	public void updatePriorities(long groupId, boolean privateLayout)
		throws PortalException;
}