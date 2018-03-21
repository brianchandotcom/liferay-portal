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

package com.liferay.site.navigation.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SiteNavigationMenuLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuLocalService
 * @generated
 */
@ProviderType
public class SiteNavigationMenuLocalServiceWrapper
	implements SiteNavigationMenuLocalService,
		ServiceWrapper<SiteNavigationMenuLocalService> {
	public SiteNavigationMenuLocalServiceWrapper(
		SiteNavigationMenuLocalService siteNavigationMenuLocalService) {
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu addDefaultSiteNavigationMenu(
		long userId, long groupId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.addDefaultSiteNavigationMenu(userId,
			groupId, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		long userId, long groupId, java.lang.String name, int type,
		boolean auto,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.addSiteNavigationMenu(userId,
			groupId, name, type, auto, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		long userId, long groupId, java.lang.String name, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.addSiteNavigationMenu(userId,
			groupId, name, type, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		long userId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.addSiteNavigationMenu(userId,
			groupId, name, serviceContext);
	}

	/**
	* Adds the site navigation menu to the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenu the site navigation menu
	* @return the site navigation menu that was added
	*/
	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		com.liferay.site.navigation.model.SiteNavigationMenu siteNavigationMenu) {
		return _siteNavigationMenuLocalService.addSiteNavigationMenu(siteNavigationMenu);
	}

	/**
	* Creates a new site navigation menu with the primary key. Does not add the site navigation menu to the database.
	*
	* @param siteNavigationMenuId the primary key for the new site navigation menu
	* @return the new site navigation menu
	*/
	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu createSiteNavigationMenu(
		long siteNavigationMenuId) {
		return _siteNavigationMenuLocalService.createSiteNavigationMenu(siteNavigationMenuId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu that was removed
	* @throws PortalException if a site navigation menu with the primary key could not be found
	*/
	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu deleteSiteNavigationMenu(
		long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.deleteSiteNavigationMenu(siteNavigationMenuId);
	}

	/**
	* Deletes the site navigation menu from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenu the site navigation menu
	* @return the site navigation menu that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu deleteSiteNavigationMenu(
		com.liferay.site.navigation.model.SiteNavigationMenu siteNavigationMenu)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.deleteSiteNavigationMenu(siteNavigationMenu);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _siteNavigationMenuLocalService.dynamicQuery();
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
		return _siteNavigationMenuLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _siteNavigationMenuLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _siteNavigationMenuLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _siteNavigationMenuLocalService.dynamicQueryCount(dynamicQuery);
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
		return _siteNavigationMenuLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu fetchPrimarySiteNavigationMenu(
		long groupId) {
		return _siteNavigationMenuLocalService.fetchPrimarySiteNavigationMenu(groupId);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu fetchSiteNavigationMenu(
		long siteNavigationMenuId) {
		return _siteNavigationMenuLocalService.fetchSiteNavigationMenu(siteNavigationMenuId);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu fetchSiteNavigationMenu(
		long groupId, int type) {
		return _siteNavigationMenuLocalService.fetchSiteNavigationMenu(groupId,
			type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _siteNavigationMenuLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getAutoSiteNavigationMenus(
		long groupId) {
		return _siteNavigationMenuLocalService.getAutoSiteNavigationMenus(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _siteNavigationMenuLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _siteNavigationMenuLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the site navigation menu with the primary key.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu
	* @throws PortalException if a site navigation menu with the primary key could not be found
	*/
	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu getSiteNavigationMenu(
		long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.getSiteNavigationMenu(siteNavigationMenuId);
	}

	/**
	* Returns a range of all the site navigation menus.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of site navigation menus
	*/
	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		int start, int end) {
		return _siteNavigationMenuLocalService.getSiteNavigationMenus(start, end);
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		long groupId) {
		return _siteNavigationMenuLocalService.getSiteNavigationMenus(groupId);
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _siteNavigationMenuLocalService.getSiteNavigationMenus(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _siteNavigationMenuLocalService.getSiteNavigationMenus(groupId,
			keywords, start, end, orderByComparator);
	}

	/**
	* Returns the number of site navigation menus.
	*
	* @return the number of site navigation menus
	*/
	@Override
	public int getSiteNavigationMenusCount() {
		return _siteNavigationMenuLocalService.getSiteNavigationMenusCount();
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId) {
		return _siteNavigationMenuLocalService.getSiteNavigationMenusCount(groupId);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId,
		java.lang.String keywords) {
		return _siteNavigationMenuLocalService.getSiteNavigationMenusCount(groupId,
			keywords);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu updateSiteNavigationMenu(
		long userId, long siteNavigationMenuId, int type, boolean auto,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.updateSiteNavigationMenu(userId,
			siteNavigationMenuId, type, auto, serviceContext);
	}

	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu updateSiteNavigationMenu(
		long userId, long siteNavigationMenuId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _siteNavigationMenuLocalService.updateSiteNavigationMenu(userId,
			siteNavigationMenuId, name, serviceContext);
	}

	/**
	* Updates the site navigation menu in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenu the site navigation menu
	* @return the site navigation menu that was updated
	*/
	@Override
	public com.liferay.site.navigation.model.SiteNavigationMenu updateSiteNavigationMenu(
		com.liferay.site.navigation.model.SiteNavigationMenu siteNavigationMenu) {
		return _siteNavigationMenuLocalService.updateSiteNavigationMenu(siteNavigationMenu);
	}

	@Override
	public SiteNavigationMenuLocalService getWrappedService() {
		return _siteNavigationMenuLocalService;
	}

	@Override
	public void setWrappedService(
		SiteNavigationMenuLocalService siteNavigationMenuLocalService) {
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;
	}

	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;
}