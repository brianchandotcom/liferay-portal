/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link LayoutLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutLocalService
 * @generated
 */
public class LayoutLocalServiceWrapper implements LayoutLocalService,
	ServiceWrapper<LayoutLocalService> {
	public LayoutLocalServiceWrapper(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	/**
	* Adds the layout to the database. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout addLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.addLayout(layout);
	}

	/**
	* Creates a new layout with the primary key. Does not add the layout to the database.
	*
	* @param plid the primary key for the new layout
	* @return the new layout
	*/
	@Override
	public com.liferay.portal.model.Layout createLayout(long plid) {
		return _layoutLocalService.createLayout(plid);
	}

	/**
	* Deletes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the layout
	* @return the layout that was removed
	* @throws PortalException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout deleteLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.deleteLayout(plid);
	}

	/**
	* Deletes the layout from the database. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout deleteLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.deleteLayout(layout);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.dynamicQuery(dynamicQuery);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.dynamicQuery(dynamicQuery, start, end);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.Layout fetchLayout(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.fetchLayout(plid);
	}

	/**
	* Returns the layout with the matching UUID and company.
	*
	* @param uuid the layout's UUID
	* @param companyId the primary key of the company
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout fetchLayoutByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.fetchLayoutByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the layout matching the UUID, group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout fetchLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.fetchLayoutByUuidAndGroupId(uuid, groupId,
			privateLayout);
	}

	/**
	* Returns the layout with the primary key.
	*
	* @param plid the primary key of the layout
	* @return the layout
	* @throws PortalException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout getLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayout(plid);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the layout with the matching UUID and company.
	*
	* @param uuid the layout's UUID
	* @param companyId the primary key of the company
	* @return the matching layout
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout getLayoutByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the layout matching the UUID, group, and privacy.
	*
	* @param uuid the layout's UUID
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layout
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout getLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutByUuidAndGroupId(uuid, groupId,
			privateLayout);
	}

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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayouts(start, end);
	}

	/**
	* Returns the number of layouts.
	*
	* @return the number of layouts
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getLayoutsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutsCount();
	}

	/**
	* Updates the layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layout the layout
	* @return the layout that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateLayout(layout);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _layoutLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a layout.
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
	* @param parentLayoutId the primary key of the parent layout
	(optionally {@link
	com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	* @param nameMap the layout's locales and localized names
	* @param titleMap the layout's locales and localized titles
	* @param descriptionMap the layout's locales and localized
	descriptions
	* @param keywordsMap the layout's locales and localized keywords
	* @param robotsMap the layout's locales and localized robots
	* @param type the layout's type (optionally {@link
	com.liferay.portal.model.LayoutConstants#TYPE_PORTLET}). The
	possible types can be found in {@link
	com.liferay.portal.model.LayoutConstants}.
	* @param hidden whether the layout is hidden
	* @param friendlyURL the layout's friendly URL (optionally {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}
	or {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL}).
	The default values can be overridden in
	<code>portal-ext.properties</code> by specifying new values
	for the corresponding properties defined in {@link
	com.liferay.portal.util.PropsValues}. To see how the URL is
	normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param serviceContext the service context to be applied. Must set
	the UUID for the layout. Can set the creation date,
	modification date and the expando bridge attributes for the
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
	* @throws PortalException if a group or user with the primary key could
	not be found, if layout values were invalid, or if a portal
	exception occurred
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #addLayout(long, long,
	boolean, long, Map, Map, Map, Map, Map, String, String,
	boolean, Map, ServiceContext)}
	*/
	@Override
	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, nameMap, titleMap, descriptionMap, keywordsMap,
			robotsMap, type, hidden, friendlyURL, serviceContext);
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
	* @param parentLayoutId the primary key of the parent layout (optionally
	{@link
	com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID})
	* @param nameMap the layout's locales and localized names
	* @param titleMap the layout's locales and localized titles
	* @param descriptionMap the layout's locales and localized descriptions
	* @param keywordsMap the layout's locales and localized keywords
	* @param robotsMap the layout's locales and localized robots
	* @param type the layout's type (optionally {@link
	com.liferay.portal.model.LayoutConstants#TYPE_PORTLET}). The
	possible types can be found in {@link
	com.liferay.portal.model.LayoutConstants}.
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
	* @throws PortalException if a group or user with the primary key could not
	be found, if layout values were invalid, or if a portal exception
	occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, java.lang.String typeSettings, boolean hidden,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, nameMap, titleMap, descriptionMap, keywordsMap,
			robotsMap, type, typeSettings, hidden, friendlyURLMap,
			serviceContext);
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
	* @param parentLayoutId the primary key of the parent layout (optionally
	{@link
	com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID}).
	The possible values can be found in {@link
	com.liferay.portal.model.LayoutConstants}.
	* @param name the layout's name (optionally {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_NAME}
	or {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_NAME}).
	The default values can be overridden in
	<code>portal-ext.properties</code> by specifying new values for
	the corresponding properties defined in {@link
	com.liferay.portal.util.PropsValues}
	* @param title the layout's title
	* @param description the layout's description
	* @param type the layout's type (optionally {@link
	com.liferay.portal.model.LayoutConstants#TYPE_PORTLET}). The
	possible types can be found in {@link
	com.liferay.portal.model.LayoutConstants}.
	* @param hidden whether the layout is hidden
	* @param friendlyURL the friendly URL of the layout (optionally {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}
	or {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL}).
	The default values can be overridden in
	<code>portal-ext.properties</code> by specifying new values for
	the corresponding properties defined in {@link
	com.liferay.portal.util.PropsValues}. To see how the URL is
	normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param serviceContext the service context to be applied. Must set the
	UUID for the layout. Can set the creation date and modification
	date for the layout. For layouts that belong to a layout set
	prototype, an attribute named <code>layoutUpdateable</code> can
	be set to specify whether site administrators can modify this
	page within their site.
	* @return the layout
	* @throws PortalException if a group or user with the primary key could not
	be found, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, name, title, description, type, hidden,
			friendlyURL, serviceContext);
	}

	/**
	* Deletes the layout, its child layouts, and its associated resources.
	*
	* @param layout the layout
	* @param updateLayoutSet whether the layout set's page counter needs to be
	updated
	* @param serviceContext the service context to be applied
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.deleteLayout(layout, updateLayoutSet, serviceContext);
	}

	/**
	* Deletes the layout with the primary key, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param serviceContext the service context to be applied
	* @throws PortalException if a matching layout could not be found , or if
	some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteLayout(long groupId, boolean privateLayout,
		long layoutId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.deleteLayout(groupId, privateLayout, layoutId,
			serviceContext);
	}

	/**
	* Deletes the layout with the plid, also deleting the layout's child
	* layouts, and associated resources.
	*
	* @param plid the primary key of the layout
	* @param serviceContext the service context to be applied
	* @throws PortalException if a layout with the primary key could not be
	found , or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteLayout(long plid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
	* @throws PortalException if a group with the primary key could not be
	found or if a layout set for the group and privacy could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteLayouts(long groupId, boolean privateLayout,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.deleteLayouts(groupId, privateLayout, serviceContext);
	}

	/**
	* Exports layouts with the primary keys and criteria as a byte array.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIds the primary keys of the layouts to be exported
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in the
	map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the layouts as a byte array
	* @throws PortalException if a group or any layout with the primary key
	could not be found, or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.exportLayouts(groupId, privateLayout,
			layoutIds, parameterMap, startDate, endDate);
	}

	/**
	* Exports all layouts that match the criteria as a byte array.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in the
	map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the layout as a byte array
	* @throws PortalException if a group with the primary key could not be
	found or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.exportLayouts(groupId, privateLayout,
			parameterMap, startDate, endDate);
	}

	/**
	* Exports the layouts that match the primary keys and criteria as a file.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIds the primary keys of the layouts to be exported
	(optionally <code>null</code>)
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in the
	map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the layouts as a File
	* @throws PortalException if a group or any layout with the primary key
	could not be found, or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.io.File exportLayoutsAsFile(long groupId,
		boolean privateLayout, long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.exportLayoutsAsFile(groupId, privateLayout,
			layoutIds, parameterMap, startDate, endDate);
	}

	@Override
	public long exportLayoutsAsFileInBackground(long userId,
		java.lang.String taskName, long groupId, boolean privateLayout,
		long[] layoutIds,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.exportLayoutsAsFileInBackground(userId,
			taskName, groupId, privateLayout, layoutIds, parameterMap,
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
	information to export. For information on the keys used in the
	map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the portlet information as a byte array
	* @throws PortalException if a group or portlet with the primary key could
	not be found, or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.exportPortletInfo(plid, groupId, portletId,
			parameterMap, startDate, endDate);
	}

	/**
	* Exports the portlet information (categories, permissions, ... etc.) as a
	* file.
	*
	* @param plid the primary key of the layout
	* @param groupId the primary key of the group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information to export. For information on the keys used in the
	map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param startDate the export's start date
	* @param endDate the export's end date
	* @return the portlet information as a file
	* @throws PortalException if a group or portlet with the primary key could
	not be found, or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.io.File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.exportPortletInfoAsFile(plid, groupId,
			portletId, parameterMap, startDate, endDate);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(long userId,
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.exportPortletInfoAsFileInBackground(userId,
			taskName, plid, groupId, portletId, parameterMap, startDate, endDate);
	}

	@Override
	public com.liferay.portal.model.Layout fetchFirstLayout(long groupId,
		boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.fetchFirstLayout(groupId, privateLayout,
			parentLayoutId);
	}

	@Override
	public com.liferay.portal.model.Layout fetchLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.fetchLayout(groupId, privateLayout, layoutId);
	}

	@Override
	public com.liferay.portal.model.Layout fetchLayoutByFriendlyURL(
		long groupId, boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.fetchLayoutByFriendlyURL(groupId,
			privateLayout, friendlyURL);
	}

	/**
	* Returns the primary key of the default layout for the group
	*
	* @param groupId the primary key of the group
	* @return the primary key of the default layout for the group (optionally
	{@link com.liferay.portal.model.LayoutConstants#DEFAULT_PLID})
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long getDefaultPlid(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getDefaultPlid(groupId);
	}

	/**
	* Returns primary key of the matching default layout for the group
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the primary key of the default layout for the group; {@link
	com.liferay.portal.model.LayoutConstants#DEFAULT_PLID}) otherwise
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long getDefaultPlid(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getDefaultPlid(groupId, privateLayout);
	}

	/**
	* Returns primary key of the default portlet layout for the group
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param portletId the primary key of the portlet
	* @return the primary key of the default portlet layout for the group;
	{@link com.liferay.portal.model.LayoutConstants#DEFAULT_PLID}
	otherwise
	* @throws PortalException if a portlet with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long getDefaultPlid(long groupId, boolean privateLayout,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getDefaultPlid(groupId, privateLayout,
			portletId);
	}

	/**
	* Returns the layout for the friendly URL
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param friendlyURL the friendly URL of the layout
	* @return the layout for the friendly URL
	* @throws PortalException if the friendly URL is <code>null</code> or a
	matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout getFriendlyURLLayout(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getFriendlyURLLayout(groupId, privateLayout,
			friendlyURL);
	}

	/**
	* Returns the layout matching the primary key, group, and privacy; throws a
	* {@link com.liferay.portal.NoSuchLayoutException} otherwise.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @return the matching layout
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout getLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayout(groupId, privateLayout, layoutId);
	}

	/**
	* Returns the layout for the icon image; throws a {@link
	* com.liferay.portal.NoSuchLayoutException} otherwise.
	*
	* @param iconImageId the primary key of the icon image
	* @return Returns the layout for the icon image
	* @throws PortalException if an icon image with the primary key could not
	be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout getLayoutByIconImageId(
		long iconImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutByIconImageId(iconImageId);
	}

	/**
	* Returns all the layouts belonging to the group.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the matching layouts, or <code>null</code> if no matches were
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout);
	}

	/**
	* Returns all the layouts belonging to the group that are children of the
	* parent layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout
	* @return the matching layouts, or <code>null</code> if no matches were
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout,
			parentLayoutId);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean incomplete, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout,
			parentLayoutId, incomplete, start, end);
	}

	/**
	* Returns all the layouts that match the layout IDs and belong to the
	* group.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutIds the primary keys of the layouts
	* @return the matching layouts, or an empty list if no matches were found
	* @throws PortalException if a group or layout with the primary key could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long[] layoutIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout, layoutIds);
	}

	/**
	* Returns all the layouts that match the type and belong to the group.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param type the type of the layouts (optionally {@link
	com.liferay.portal.model.LayoutConstants#TYPE_PORTLET})
	* @return the matching layouts, or <code>null</code> if no matches were
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout, type);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayouts(companyId, portletId,
			preferencesKey, preferencesValue);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getLayoutsByLayoutPrototypeUuid(
		java.lang.String layoutPrototypeUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutsByLayoutPrototypeUuid(layoutPrototypeUuid);
	}

	@Override
	public int getLayoutsByLayoutPrototypeUuidCount(
		java.lang.String layoutPrototypeUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutsByLayoutPrototypeUuidCount(layoutPrototypeUuid);
	}

	@Override
	public int getLayoutsCount(com.liferay.portal.model.Group group,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutsCount(group, privateLayout);
	}

	@Override
	public int getLayoutsCount(com.liferay.portal.model.Group group,
		boolean privateLayout, boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutsCount(group, privateLayout,
			includeUserGroups);
	}

	@Override
	public int getLayoutsCount(com.liferay.portal.model.User user,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutsCount(user, privateLayout);
	}

	@Override
	public int getLayoutsCount(com.liferay.portal.model.User user,
		boolean privateLayout, boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getLayoutsCount(user, privateLayout,
			includeUserGroups);
	}

	/**
	* Returns the primary key to use for the next layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the primary key to use for the next layout
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long getNextLayoutId(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getNextLayoutId(groupId, privateLayout);
	}

	/**
	* Returns all the layouts without resource permissions
	*
	* @param roleId the primary key of the role
	* @return all the layouts without resource permissions
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getNoPermissionLayouts(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getNoPermissionLayouts(roleId);
	}

	/**
	* Returns all the layouts whose friendly URLs are <code>null</code>
	*
	* @return all the layouts whose friendly URLs are <code>null</code>
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getNullFriendlyURLLayouts()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getNullFriendlyURLLayouts();
	}

	@Override
	public com.liferay.portal.model.Layout getParentLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getParentLayout(layout);
	}

	/**
	* Returns all the layouts within scope of the group
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @return the layouts within scope of the group
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Layout> getScopeGroupLayouts(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.getScopeGroupLayouts(groupId, privateLayout);
	}

	@Override
	public boolean hasLayouts(com.liferay.portal.model.Group group,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.hasLayouts(group, privateLayout);
	}

	@Override
	public boolean hasLayouts(com.liferay.portal.model.Group group,
		boolean privateLayout, boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.hasLayouts(group, privateLayout,
			includeUserGroups);
	}

	/**
	* Returns <code>true</code> if the group has any layouts;
	* <code>false</code> otherwise.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parentLayoutId the primary key of the parent layout
	* @return <code>true</code> if the group has any layouts;
	<code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasLayouts(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.hasLayouts(groupId, privateLayout,
			parentLayoutId);
	}

	@Override
	public boolean hasLayouts(com.liferay.portal.model.User user,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.hasLayouts(user, privateLayout);
	}

	@Override
	public boolean hasLayouts(com.liferay.portal.model.User user,
		boolean privateLayout, boolean includeUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.hasLayouts(user, privateLayout,
			includeUserGroups);
	}

	@Override
	public boolean hasLayoutSetPrototypeLayout(long layoutSetPrototypeId,
		java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.hasLayoutSetPrototypeLayout(layoutSetPrototypeId,
			layoutUuid);
	}

	@Override
	public boolean hasLayoutSetPrototypeLayout(
		java.lang.String layoutSetPrototypeUuid, long companyId,
		java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.hasLayoutSetPrototypeLayout(layoutSetPrototypeUuid,
			companyId, layoutUuid);
	}

	/**
	* Imports the layouts from the byte array.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys used in
	the map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param bytes the byte array with the data
	* @throws PortalException if a group or user with the primary key could not
	be found, or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.lar.LayoutImporter
	*/
	@Override
	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, bytes);
	}

	/**
	* Imports the layouts from the file.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys used in
	the map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param file the LAR file with the data
	* @throws PortalException if a group or user with the primary key could not
	be found, or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.lar.LayoutImporter
	*/
	@Override
	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, file);
	}

	/**
	* Imports the layouts from the input stream.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys used in
	the map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param is the input stream
	* @throws PortalException if a group or user with the primary key could not
	be found, or if some other portal exception occurred
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portal.lar.LayoutImporter
	*/
	@Override
	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, is);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		java.lang.String taskName, long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.importLayoutsInBackground(userId, taskName,
			groupId, privateLayout, parameterMap, file);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		java.lang.String taskName, long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.importLayoutsInBackground(userId, taskName,
			groupId, privateLayout, parameterMap, is);
	}

	/**
	* Imports the portlet information (categories, permissions, ... etc.) from
	* the file.
	*
	* @param userId the primary key of the user
	* @param plid the primary key of the target layout
	* @param groupId the primary key of the target group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys used in
	the map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param file the LAR file with the data
	* @throws PortalException if a group, layout, portlet or user with the
	primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.importPortletInfo(userId, plid, groupId, portletId,
			parameterMap, file);
	}

	/**
	* Imports the portlet information (categories, permissions, ... etc.) from
	* the input stream.
	*
	* @param userId the primary key of the user
	* @param plid the primary key of the layout
	* @param groupId the primary key of the group
	* @param portletId the primary key of the portlet
	* @param parameterMap the mapping of parameters indicating which
	information will be imported. For information on the keys used in
	the map see {@link
	com.liferay.portal.kernel.lar.PortletDataHandlerKeys}.
	* @param is the input stream
	* @throws PortalException if a group, portlet, layout or user with the
	primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.importPortletInfo(userId, plid, groupId, portletId,
			parameterMap, is);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.importPortletInfoInBackground(userId,
			taskName, plid, groupId, portletId, parameterMap, file);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		java.lang.String taskName, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.importPortletInfoInBackground(userId,
			taskName, plid, groupId, portletId, parameterMap, is);
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
	* @throws PortalException if a group or layout with the primary key could
	not be found, if no layouts were specified, if the first layout
	was not page-able, if the first layout was hidden, or if some
	other portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.setLayouts(groupId, privateLayout, parentLayoutId,
			layoutIds, serviceContext);
	}

	/**
	* Updates the friendly URL of the layout.
	*
	* @param plid the primary key of the layout
	* @param friendlyURL the friendly URL to be assigned
	* @param languageId the primary key of the language
	* @return the updated layout
	* @throws PortalException if a group or layout with the primary key could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateFriendlyURL(long plid,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateFriendlyURL(plid, friendlyURL,
			languageId);
	}

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
	com.liferay.portal.model.LayoutConstants#TYPE_PORTLET})
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
	* @throws PortalException if a group or layout with the primary key could
	not be found, if a unique friendly URL could not be generated, if
	a valid parent layout ID to use could not be found, if the layout
	parameters were invalid, or if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, boolean hidden,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		java.lang.Boolean iconImage, byte[] iconBytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, parentLayoutId, nameMap, titleMap, descriptionMap,
			keywordsMap, robotsMap, type, hidden, friendlyURLMap, iconImage,
			iconBytes, serviceContext);
	}

	/**
	* Updates the layout.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout
	* @param parentLayoutId the primary key of the layout's new parent
	layout
	* @param nameMap the locales and localized names to merge (optionally
	<code>null</code>)
	* @param titleMap the locales and localized titles to merge
	(optionally <code>null</code>)
	* @param descriptionMap the locales and localized descriptions to
	merge (optionally <code>null</code>)
	* @param keywordsMap the locales and localized keywords to merge
	(optionally <code>null</code>)
	* @param robotsMap the locales and localized robots to merge
	(optionally <code>null</code>)
	* @param type the layout's new type (optionally {@link
	com.liferay.portal.model.LayoutConstants#TYPE_PORTLET})
	* @param hidden whether the layout is hidden
	* @param friendlyURL the layout's new friendly URL (optionally {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}
	or {@link
	com.liferay.portal.util.PropsValues#DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL}).
	The default values can be overridden in
	<code>portal-ext.properties</code> by specifying new values
	for the corresponding properties defined in {@link
	com.liferay.portal.util.PropsValues}. To see how the URL is
	normalized when accessed, see {@link
	com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil#normalize(
	String)}.
	* @param iconImage whether the icon image will be updated
	* @param iconBytes the byte array of the layout's new icon image
	* @param serviceContext the service context to be applied. Can set the
	modification date and expando bridge attributes for the
	layout. For layouts that are linked to a layout prototype,
	attributes named <code>layoutPrototypeUuid</code> and
	<code>layoutPrototypeLinkedEnabled</code> can be specified to
	provide the unique identifier of the source prototype and a
	boolean to determine whether a link to it should be enabled
	to activate propagation of changes made to the linked page in
	the prototype.
	* @return the updated layout
	* @throws PortalException if a group or layout with the primary key
	could not be found, if a unique friendly URL could not be
	generated, if a valid parent layout ID to use could not be
	found, if the layout parameters were invalid, or if a portal
	exception occurred
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #updateLayout(long, boolean,
	long, long, Map, Map, Map, Map, Map, String, boolean, Map,
	Boolean, byte[], ServiceContext)}
	*/
	@Override
	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> keywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> robotsMap,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		java.lang.Boolean iconImage, byte[] iconBytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, parentLayoutId, nameMap, titleMap, descriptionMap,
			keywordsMap, robotsMap, type, hidden, friendlyURL, iconImage,
			iconBytes, serviceContext);
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
	* @throws PortalException if a matching layout could not be found or if a
	portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, typeSettings);
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
	* @param wapTheme whether the theme is for WAP browsers
	* @return the updated layout
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateLookAndFeel(long groupId,
		boolean privateLayout, long layoutId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateLookAndFeel(groupId, privateLayout,
			layoutId, themeId, colorSchemeId, css, wapTheme);
	}

	/**
	* Updates the name of the layout.
	*
	* @param layout the layout to be updated
	* @param name the layout's new name
	* @param languageId the primary key of the language. For more information
	see {@link java.util.Locale}.
	* @return the updated layout
	* @throws PortalException if the new name was <code>null</code>
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateName(
		com.liferay.portal.model.Layout layout, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateName(layout, name, languageId);
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
	see {@link java.util.Locale}.
	* @return the updated layout
	* @throws PortalException if a matching layout could not be found or if the
	new name was <code>null</code>
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateName(groupId, privateLayout, layoutId,
			name, languageId);
	}

	/**
	* Updates the name of the layout matching the primary key.
	*
	* @param plid the primary key of the layout
	* @param name the name to be assigned
	* @param languageId the primary key of the language. For more information
	see {@link java.util.Locale}.
	* @return the updated layout
	* @throws PortalException if a layout with the primary key could not be
	found or if the name was <code>null</code>
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateName(plid, name, languageId);
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
	* @throws PortalException if a valid parent layout ID to use could not be
	found or if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateParentLayoutId(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateParentLayoutId(groupId, privateLayout,
			layoutId, parentLayoutId);
	}

	/**
	* Updates the parent layout ID of the layout matching the primary key. If a
	* layout matching the parent primary key is found, the layout ID of that
	* layout is assigned, otherwise {@link
	* com.liferay.portal.model.LayoutConstants#DEFAULT_PARENT_LAYOUT_ID} is
	* assigned.
	*
	* @param plid the primary key of the layout
	* @param parentPlid the primary key of the parent layout
	* @return the layout matching the primary key
	* @throws PortalException if a layout with the primary key could not be
	found or if a valid parent layout ID to use could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updateParentLayoutId(long plid,
		long parentPlid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updateParentLayoutId(plid, parentPlid);
	}

	/**
	* Updates the priorities of the layouts.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void updatePriorities(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.updatePriorities(groupId, privateLayout);
	}

	/**
	* Updates the priority of the layout.
	*
	* @param layout the layout to be updated
	* @param priority the layout's new priority
	* @return the updated layout
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updatePriority(
		com.liferay.portal.model.Layout layout, int priority)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updatePriority(layout, priority);
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
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updatePriority(groupId, privateLayout,
			layoutId, priority);
	}

	/**
	* Updates the priority of the layout matching the primary key.
	*
	* @param plid the primary key of the layout
	* @param priority the layout's new priority
	* @return the updated layout
	* @throws PortalException if a layout with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Layout updatePriority(long plid,
		int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.updatePriority(plid, priority);
	}

	/**
	* @deprecated As of 6.2.0, with no direct replacement
	*/
	@Override
	public void updateScopedPortletNames(long groupId, boolean privateLayout,
		long layoutId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.List<java.util.Locale> nameMapModifiedLocales)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.updateScopedPortletNames(groupId, privateLayout,
			layoutId, nameMap, nameMapModifiedLocales);
	}

	/**
	* Updates the names of the portlets within scope of the group, the scope of
	* the layout's UUID, and the privacy.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout is private to the group
	* @param layoutId the primary key of the layout whose UUID to match
	* @param name the new name for the portlets
	* @param languageId the primary key of the language
	* @throws PortalException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	* @see com.liferay.portlet.portletconfiguration.action.EditScopeAction
	* @deprecated As of 6.2.0, with no direct replacement
	*/
	@Override
	public void updateScopedPortletNames(long groupId, boolean privateLayout,
		long layoutId, java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutLocalService.updateScopedPortletNames(groupId, privateLayout,
			layoutId, name, languageId);
	}

	@Override
	public com.liferay.portal.kernel.lar.MissingReferences validateImportLayoutsFile(
		long userId, long groupId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.validateImportLayoutsFile(userId, groupId,
			privateLayout, parameterMap, file);
	}

	@Override
	public com.liferay.portal.kernel.lar.MissingReferences validateImportPortletInfo(
		long userId, long groupId, long plid, java.lang.String portletId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutLocalService.validateImportPortletInfo(userId, groupId,
			plid, portletId, parameterMap, file);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public LayoutLocalService getWrappedLayoutLocalService() {
		return _layoutLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedLayoutLocalService(
		LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
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