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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link LayoutLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutLocalService
 * @generated
 */
public class LayoutLocalServiceWrapper
	implements LayoutLocalService, ServiceWrapper<LayoutLocalService> {

	public LayoutLocalServiceWrapper(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	/**
	 * Adds the layout to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layout the layout
	 * @return the layout that was added
	 */
	@Override
	public Layout addLayout(Layout layout) {
		return _layoutLocalService.addLayout(layout);
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
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
	 {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param classNameId the class name ID of the entity
	 * @param classPK the primary key of the entity
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
	 * @param system whether the layout is of system type
	 * @param masterLayoutPlid the primary key of the master layout
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			long masterLayoutPlid,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.addLayout(
			userId, groupId, privateLayout, parentLayoutId, classNameId,
			classPK, nameMap, titleMap, descriptionMap, keywordsMap, robotsMap,
			type, typeSettings, hidden, system, masterLayoutPlid,
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
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
	 {@link LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	 * @param classNameId the class name ID of the entity
	 * @param classPK the primary key of the entity
	 * @param nameMap the layout's locales and localized names
	 * @param titleMap the layout's locales and localized titles
	 * @param descriptionMap the layout's locales and localized
	 descriptions
	 * @param keywordsMap the layout's locales and localized keywords
	 * @param robotsMap the layout's locales and localized robots
	 * @param type the layout's type (optionally {@link
	 LayoutConstants#TYPE_PORTLET}). The possible types can be
	 found in {@link LayoutConstants}.
	 * @param typeSettings the settings to load the unicode properties
	 object. See {@link UnicodeProperties #fastLoad(String)}.
	 * @param hidden whether the layout is hidden
	 * @param system whether the layout is of system type
	 * @param friendlyURLMap the layout's locales and localized friendly
	 URLs. To see how the URL is normalized when accessed, see
	 {@link
	 com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	 String)}.
	 * @param serviceContext the service context to be applied. Must set
	 the UUID for the layout. Can set the creation date,
	 modification date, and expando bridge attributes for the
	 layout. For layouts that belong to a layout set prototype, an
	 attribute named <code>layoutUpdateable</code> can be set to
	 specify whether site administrators can modify this page
	 within their site. For layouts that are created from a layout
	 prototype, attributes named <code>layoutPrototypeUuid</code>
	 and <code>layoutPrototypeLinkedEnabled</code> can be
	 specified to provide the unique identifier of the source
	 prototype and a boolean to determine whether a link to it
	 should be enabled to activate propagation of changes made to
	 the linked page in the prototype.
	 * @return the layout
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #addLayout(long,
	 long, boolean, long, long, long, Map, Map, Map, Map, Map,
	 String, String, boolean, boolean, long, Map, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.addLayout(
			userId, groupId, privateLayout, parentLayoutId, classNameId,
			classPK, nameMap, titleMap, descriptionMap, keywordsMap, robotsMap,
			type, typeSettings, hidden, system, friendlyURLMap, serviceContext);
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
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
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
	 * @param system whether the layout is of system type
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden, boolean system,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.addLayout(
			userId, groupId, privateLayout, parentLayoutId, nameMap, titleMap,
			descriptionMap, keywordsMap, robotsMap, type, typeSettings, hidden,
			system, friendlyURLMap, serviceContext);
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
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			String typeSettings, boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.addLayout(
			userId, groupId, privateLayout, parentLayoutId, nameMap, titleMap,
			descriptionMap, keywordsMap, robotsMap, type, typeSettings, hidden,
			friendlyURLMap, serviceContext);
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
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
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
	 * @param system whether the layout is of system type
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, boolean system, String friendlyURL,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.addLayout(
			userId, groupId, privateLayout, parentLayoutId, name, title,
			description, type, hidden, system, friendlyURL, serviceContext);
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
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout (optionally
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.addLayout(
			userId, groupId, privateLayout, parentLayoutId, name, title,
			description, type, hidden, friendlyURL, serviceContext);
	}

	/**
	 * Creates a new layout with the primary key. Does not add the layout to the database.
	 *
	 * @param plid the primary key for the new layout
	 * @return the new layout
	 */
	@Override
	public Layout createLayout(long plid) {
		return _layoutLocalService.createLayout(plid);
	}

	/**
	 * Deletes the layout from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layout the layout
	 * @return the layout that was removed
	 * @throws PortalException
	 */
	@Override
	public Layout deleteLayout(Layout layout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.deleteLayout(layout);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), As of (7.2.x), replaced by {@link
	 #deleteLayout(Layout, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void deleteLayout(
			Layout layout, boolean updateLayoutSet,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutLocalService.deleteLayout(
			layout, updateLayoutSet, serviceContext);
	}

	/**
	 * Deletes the layout, its child layouts, and its associated resources.
	 *
	 * @param layout the layout
	 * @param serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteLayout(Layout layout, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutLocalService.deleteLayout(layout, serviceContext);
	}

	/**
	 * Deletes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param plid the primary key of the layout
	 * @return the layout that was removed
	 * @throws PortalException if a layout with the primary key could not be found
	 */
	@Override
	public Layout deleteLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.deleteLayout(plid);
	}

	/**
	 * Deletes the layout with the layout ID, also deleting the layout's child
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

		_layoutLocalService.deleteLayout(
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

		_layoutLocalService.deleteLayout(plid, serviceContext);
	}

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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteLayouts(
			long groupId, boolean privateLayout, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutLocalService.deleteLayouts(
			groupId, privateLayout, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _layoutLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _layoutLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _layoutLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _layoutLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _layoutLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public Layout fetchDefaultLayout(long groupId, boolean privateLayout) {
		return _layoutLocalService.fetchDefaultLayout(groupId, privateLayout);
	}

	@Override
	public Layout fetchFirstLayout(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return _layoutLocalService.fetchFirstLayout(
			groupId, privateLayout, parentLayoutId);
	}

	@Override
	public Layout fetchFirstLayout(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden) {

		return _layoutLocalService.fetchFirstLayout(
			groupId, privateLayout, parentLayoutId, hidden);
	}

	@Override
	public Layout fetchLayout(long plid) {
		return _layoutLocalService.fetchLayout(plid);
	}

	@Override
	public Layout fetchLayout(
		long groupId, boolean privateLayout, long layoutId) {

		return _layoutLocalService.fetchLayout(
			groupId, privateLayout, layoutId);
	}

	@Override
	public Layout fetchLayout(long classNameId, long classPK) {
		return _layoutLocalService.fetchLayout(classNameId, classPK);
	}

	@Override
	public Layout fetchLayout(
		String uuid, long groupId, boolean privateLayout) {

		return _layoutLocalService.fetchLayout(uuid, groupId, privateLayout);
	}

	@Override
	public Layout fetchLayoutByFriendlyURL(
		long groupId, boolean privateLayout, String friendlyURL) {

		return _layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, privateLayout, friendlyURL);
	}

	@Override
	public Layout fetchLayoutByIconImageId(
			boolean privateLayout, long iconImageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.fetchLayoutByIconImageId(
			privateLayout, iconImageId);
	}

	/**
	 * Returns the layout matching the UUID, group, and privacy.
	 *
	 * @param uuid the layout's UUID
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the matching layout, or <code>null</code> if a matching layout could not be found
	 */
	@Override
	public Layout fetchLayoutByUuidAndGroupId(
		String uuid, long groupId, boolean privateLayout) {

		return _layoutLocalService.fetchLayoutByUuidAndGroupId(
			uuid, groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _layoutLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the primary key of the default layout for the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the primary key of the default layout for the group (optionally
	 {@link LayoutConstants#DEFAULT_PLID})
	 */
	@Override
	public long getDefaultPlid(long groupId) {
		return _layoutLocalService.getDefaultPlid(groupId);
	}

	/**
	 * Returns primary key of the matching default layout for the group
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the primary key of the default layout for the group; {@link
	 LayoutConstants#DEFAULT_PLID}) otherwise
	 */
	@Override
	public long getDefaultPlid(long groupId, boolean privateLayout) {
		return _layoutLocalService.getDefaultPlid(groupId, privateLayout);
	}

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
	@Override
	public long getDefaultPlid(
			long groupId, boolean privateLayout, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getDefaultPlid(
			groupId, privateLayout, portletId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _layoutLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns the layout for the friendly URL.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param friendlyURL the friendly URL of the layout
	 * @return the layout for the friendly URL
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout getFriendlyURLLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getFriendlyURLLayout(
			groupId, privateLayout, friendlyURL);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout with the primary key.
	 *
	 * @param plid the primary key of the layout
	 * @return the layout
	 * @throws PortalException if a layout with the primary key could not be found
	 */
	@Override
	public Layout getLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayout(plid);
	}

	/**
	 * Returns the layout matching the layout ID, group, and privacy; throws a
	 * {@link NoSuchLayoutException} otherwise.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @return the matching layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout getLayout(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayout(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout for the icon image; throws a {@link
	 * NoSuchLayoutException} otherwise.
	 *
	 * @param iconImageId the primary key of the icon image
	 * @return Returns the layout for the icon image
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout getLayoutByIconImageId(long iconImageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutByIconImageId(iconImageId);
	}

	/**
	 * Returns the layout matching the UUID, group, and privacy.
	 *
	 * @param uuid the layout's UUID
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the matching layout
	 * @throws PortalException if a matching layout could not be found
	 */
	@Override
	public Layout getLayoutByUuidAndGroupId(
			String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutByUuidAndGroupId(
			uuid, groupId, privateLayout);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #getLayoutChildLayouts(List)}
	 */
	@Deprecated
	@Override
	public java.util.Map<Long, java.util.List<Layout>> getLayoutChildLayouts(
		com.liferay.portal.kernel.model.LayoutSet layoutSet,
		java.util.List<Layout> parentLayouts) {

		return _layoutLocalService.getLayoutChildLayouts(
			layoutSet, parentLayouts);
	}

	@Override
	public java.util.Map<Long, java.util.List<Layout>> getLayoutChildLayouts(
		java.util.List<Layout> parentLayouts) {

		return _layoutLocalService.getLayoutChildLayouts(parentLayouts);
	}

	/**
	 * Returns a range of all the layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the range of layouts
	 */
	@Override
	public java.util.List<Layout> getLayouts(int start, int end) {
		return _layoutLocalService.getLayouts(start, end);
	}

	@Override
	public java.util.List<Layout> getLayouts(long companyId) {
		return _layoutLocalService.getLayouts(companyId);
	}

	/**
	 * Returns all the layouts belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, boolean privateLayout) {

		return _layoutLocalService.getLayouts(groupId, privateLayout);
	}

	/**
	 * Returns a range of all the layouts belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param obc the comparator to order the layouts
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Layout> obc) {

		return _layoutLocalService.getLayouts(
			groupId, privateLayout, start, end, obc);
	}

	/**
	 * Returns all the layouts belonging to the group that are children of the
	 * parent layout.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return _layoutLocalService.getLayouts(
			groupId, privateLayout, parentLayoutId);
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
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout
	 * @param incomplete whether the layout is incomplete
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean incomplete, int start, int end) {

		return _layoutLocalService.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete, start, end);
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
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param obc the comparator to order the layouts
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean incomplete, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Layout> obc) {

		return _layoutLocalService.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete, start, end,
			obc);
	}

	/**
	 * Returns all the layouts that match the layout IDs and belong to the
	 * group.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutIds the layout IDs of the layouts
	 * @return the matching layouts, or an empty list if no matches were found
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List<Layout> getLayouts(
			long groupId, boolean privateLayout, long[] layoutIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayouts(
			groupId, privateLayout, layoutIds);
	}

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
	@Override
	public java.util.List<Layout> getLayouts(
			long groupId, boolean privateLayout, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayouts(groupId, privateLayout, type);
	}

	/**
	 * Returns a range of all the layouts belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param keywords keywords
	 * @param types layout types
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param obc the comparator to order the layouts
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
			long groupId, boolean privateLayout, String keywords,
			String[] types, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<Layout> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayouts(
			groupId, privateLayout, keywords, types, start, end, obc);
	}

	/**
	 * Returns a range of all the layouts belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param obc the comparator to order the layouts
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Layout> obc) {

		return _layoutLocalService.getLayouts(groupId, start, end, obc);
	}

	@Override
	public java.util.List<Layout> getLayouts(
		long groupId, long masterLayoutPlid) {

		return _layoutLocalService.getLayouts(groupId, masterLayoutPlid);
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
	public com.liferay.portal.kernel.model.LayoutReference[] getLayouts(
		long companyId, String portletId, String preferencesKey,
		String preferencesValue) {

		return _layoutLocalService.getLayouts(
			companyId, portletId, preferencesKey, preferencesValue);
	}

	/**
	 * Returns a range of all the layouts belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param keywords keywords
	 * @param types layout types
	 * @param start the lower bound of the range of layouts
	 * @param end the upper bound of the range of layouts (not inclusive)
	 * @param obc the comparator to order the layouts
	 * @return the matching layouts, or <code>null</code> if no matches were
	 found
	 */
	@Override
	public java.util.List<Layout> getLayouts(
			long groupId, String keywords, String[] types, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<Layout> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayouts(
			groupId, keywords, types, start, end, obc);
	}

	@Override
	public java.util.List<Layout> getLayoutsByLayoutPrototypeUuid(
		String layoutPrototypeUuid) {

		return _layoutLocalService.getLayoutsByLayoutPrototypeUuid(
			layoutPrototypeUuid);
	}

	@Override
	public int getLayoutsByLayoutPrototypeUuidCount(
		String layoutPrototypeUuid) {

		return _layoutLocalService.getLayoutsByLayoutPrototypeUuidCount(
			layoutPrototypeUuid);
	}

	/**
	 * Returns all the layouts matching the UUID and company.
	 *
	 * @param uuid the UUID of the layouts
	 * @param companyId the primary key of the company
	 * @return the matching layouts, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<Layout> getLayoutsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutLocalService.getLayoutsByUuidAndCompanyId(
			uuid, companyId);
	}

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
	@Override
	public java.util.List<Layout> getLayoutsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Layout>
			orderByComparator) {

		return _layoutLocalService.getLayoutsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layouts.
	 *
	 * @return the number of layouts
	 */
	@Override
	public int getLayoutsCount() {
		return _layoutLocalService.getLayoutsCount();
	}

	@Override
	public int getLayoutsCount(
			com.liferay.portal.kernel.model.Group group, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutsCount(group, privateLayout);
	}

	@Override
	public int getLayoutsCount(
			com.liferay.portal.kernel.model.Group group, boolean privateLayout,
			boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutsCount(
			group, privateLayout, includeUserGroups);
	}

	@Override
	public int getLayoutsCount(
		com.liferay.portal.kernel.model.Group group, boolean privateLayout,
		long parentLayoutId) {

		return _layoutLocalService.getLayoutsCount(
			group, privateLayout, parentLayoutId);
	}

	@Override
	public int getLayoutsCount(
		com.liferay.portal.kernel.model.Group group, boolean privateLayout,
		long[] layoutIds) {

		return _layoutLocalService.getLayoutsCount(
			group, privateLayout, layoutIds);
	}

	@Override
	public int getLayoutsCount(
			com.liferay.portal.kernel.model.Group group, boolean privateLayout,
			String keywords, String[] types)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutsCount(
			group, privateLayout, keywords, types);
	}

	@Override
	public int getLayoutsCount(long groupId) {
		return _layoutLocalService.getLayoutsCount(groupId);
	}

	@Override
	public int getLayoutsCount(long groupId, long masterLayoutPlid) {
		return _layoutLocalService.getLayoutsCount(groupId, masterLayoutPlid);
	}

	@Override
	public int getLayoutsCount(long groupId, String keywords, String[] types)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutsCount(groupId, keywords, types);
	}

	@Override
	public int getLayoutsCount(
			com.liferay.portal.kernel.model.User user, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutsCount(user, privateLayout);
	}

	@Override
	public int getLayoutsCount(
			com.liferay.portal.kernel.model.User user, boolean privateLayout,
			boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getLayoutsCount(
			user, privateLayout, includeUserGroups);
	}

	/**
	 * Returns the layout ID to use for the next layout.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @return the layout ID to use for the next layout
	 */
	@Override
	public long getNextLayoutId(long groupId, boolean privateLayout) {
		return _layoutLocalService.getNextLayoutId(groupId, privateLayout);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public Layout getParentLayout(Layout layout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getParentLayout(layout);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<Layout> getScopeGroupLayouts(long parentGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getScopeGroupLayouts(parentGroupId);
	}

	/**
	 * Returns all the layouts within scope of the group.
	 *
	 * @param parentGroupId the primary key of the group's parent group
	 * @param privateLayout whether the layout is private to the group
	 * @return the layouts within scope of the group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List<Layout> getScopeGroupLayouts(
			long parentGroupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.getScopeGroupLayouts(
			parentGroupId, privateLayout);
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

		return _layoutLocalService.hasLayout(uuid, groupId, privateLayout);
	}

	@Override
	public boolean hasLayouts(com.liferay.portal.kernel.model.Group group)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.hasLayouts(group);
	}

	@Override
	public boolean hasLayouts(
			com.liferay.portal.kernel.model.Group group, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.hasLayouts(group, privateLayout);
	}

	@Override
	public boolean hasLayouts(
			com.liferay.portal.kernel.model.Group group, boolean privateLayout,
			boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.hasLayouts(
			group, privateLayout, includeUserGroups);
	}

	/**
	 * Returns <code>true</code> if the group has any layouts;
	 * <code>false</code> otherwise.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout
	 * @return <code>true</code> if the group has any layouts;
	 <code>false</code> otherwise
	 */
	@Override
	public boolean hasLayouts(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return _layoutLocalService.hasLayouts(
			groupId, privateLayout, parentLayoutId);
	}

	@Override
	public boolean hasLayouts(
			com.liferay.portal.kernel.model.User user, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.hasLayouts(user, privateLayout);
	}

	@Override
	public boolean hasLayouts(
			com.liferay.portal.kernel.model.User user, boolean privateLayout,
			boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.hasLayouts(
			user, privateLayout, includeUserGroups);
	}

	@Override
	public boolean hasLayoutSetPrototypeLayout(
			long layoutSetPrototypeId, String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.hasLayoutSetPrototypeLayout(
			layoutSetPrototypeId, layoutUuid);
	}

	@Override
	public boolean hasLayoutSetPrototypeLayout(
			String layoutSetPrototypeUuid, long companyId, String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.hasLayoutSetPrototypeLayout(
			layoutSetPrototypeUuid, companyId, layoutUuid);
	}

	/**
	 * Sets the layouts for the group, replacing and prioritizing all layouts of
	 * the parent layout.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param parentLayoutId the layout ID of the parent layout
	 * @param layoutIds the layout IDs of the layouts
	 * @param serviceContext the service context to be applied
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutLocalService.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds, serviceContext);
	}

	@Override
	public void updateAsset(
			long userId, Layout layout, long[] assetCategoryIds,
			String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutLocalService.updateAsset(
			userId, layout, assetCategoryIds, assetTagNames);
	}

	/**
	 * Updates the friendly URL of the layout.
	 *
	 * @param userId the primary key of the user
	 * @param plid the primary key of the layout
	 * @param friendlyURL the friendly URL to be assigned
	 * @param languageId the primary key of the language
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateFriendlyURL(
			long userId, long plid, String friendlyURL, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateFriendlyURL(
			userId, plid, friendlyURL, languageId);
	}

	@Override
	public Layout updateIconImage(long plid, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateIconImage(plid, bytes);
	}

	/**
	 * Updates the layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layout the layout
	 * @return the layout that was updated
	 */
	@Override
	public Layout updateLayout(Layout layout) {
		return _layoutLocalService.updateLayout(layout);
	}

	/**
	 * Updates the layout replacing its draft publish date.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param publishDate the date when draft was last published
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			java.util.Date publishDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, publishDate);
	}

	/**
	 * Updates the layout replacing its entity class name ID and primary key.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param classNameId the class name ID of the entity
	 * @param classPK the primary key of the entity
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, classNameId, classPK);
	}

	/**
	 * Updates the layout.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param parentLayoutId the layout ID of the layout's new parent layout
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
	 * @param hasIconImage whether the icon image will be updated
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
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> keywordsMap,
			java.util.Map<java.util.Locale, String> robotsMap, String type,
			boolean hidden,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			boolean hasIconImage, byte[] iconBytes,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, nameMap, titleMap,
			descriptionMap, keywordsMap, robotsMap, type, hidden,
			friendlyURLMap, hasIconImage, iconBytes, serviceContext);
	}

	/**
	 * Updates the layout replacing its type settings.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @param layoutId the layout ID of the layout
	 * @param typeSettings the settings to load the unicode properties object.
	 See {@link UnicodeProperties #fastLoad(String)}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateLayout(
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

		return _layoutLocalService.updateLookAndFeel(
			groupId, privateLayout, layoutId, themeId, colorSchemeId, css);
	}

	/**
	 * Updates the name of the layout.
	 *
	 * @param layout the layout to be updated
	 * @param name the layout's new name
	 * @param languageId the primary key of the language. For more information
	 see {@link Locale}.
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updateName(Layout layout, String name, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateName(layout, name, languageId);
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

		return _layoutLocalService.updateName(
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

		return _layoutLocalService.updateName(plid, name, languageId);
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

		return _layoutLocalService.updateParentLayoutId(
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

		return _layoutLocalService.updateParentLayoutId(plid, parentPlid);
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

		return _layoutLocalService.updateParentLayoutIdAndPriority(
			plid, parentPlid, priority);
	}

	/**
	 * Updates the priorities of the layouts.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout is private to the group
	 * @throws PortalException
	 */
	@Override
	public void updatePriorities(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutLocalService.updatePriorities(groupId, privateLayout);
	}

	/**
	 * Updates the priority of the layout.
	 *
	 * @param layout the layout to be updated
	 * @param priority the layout's new priority
	 * @return the updated layout
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Layout updatePriority(Layout layout, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updatePriority(layout, priority);
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

		return _layoutLocalService.updatePriority(
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

		return _layoutLocalService.updatePriority(
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

		return _layoutLocalService.updatePriority(plid, priority);
	}

	@Override
	public Layout updateType(long plid, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutLocalService.updateType(plid, type);
	}

	@Override
	public CTPersistence<Layout> getCTPersistence() {
		return _layoutLocalService.getCTPersistence();
	}

	@Override
	public Class<Layout> getModelClass() {
		return _layoutLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<Layout>, R, E> updateUnsafeFunction)
		throws E {

		return _layoutLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public LayoutLocalService getWrappedService() {
		return _layoutLocalService;
	}

	@Override
	public void setWrappedService(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	private LayoutLocalService _layoutLocalService;

}