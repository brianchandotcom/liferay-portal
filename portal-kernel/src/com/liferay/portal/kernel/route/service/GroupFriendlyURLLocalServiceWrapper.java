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

package com.liferay.portal.kernel.route.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link GroupFriendlyURLLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see GroupFriendlyURLLocalService
 * @generated
 */
@ProviderType
public class GroupFriendlyURLLocalServiceWrapper
	implements GroupFriendlyURLLocalService,
		ServiceWrapper<GroupFriendlyURLLocalService> {
	public GroupFriendlyURLLocalServiceWrapper(
		GroupFriendlyURLLocalService groupFriendlyURLLocalService) {
		_groupFriendlyURLLocalService = groupFriendlyURLLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _groupFriendlyURLLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _groupFriendlyURLLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _groupFriendlyURLLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _groupFriendlyURLLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the group friendly url to the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURL the group friendly url
	* @return the group friendly url that was added
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL addGroupFriendlyURL(
		com.liferay.portal.kernel.route.model.GroupFriendlyURL groupFriendlyURL) {
		return _groupFriendlyURLLocalService.addGroupFriendlyURL(groupFriendlyURL);
	}

	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL addGroupFriendlyURL(
		long userId, long companyId, long groupId,
		java.lang.String friendlyURL, java.lang.String languageId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.addGroupFriendlyURL(userId,
			companyId, groupId, friendlyURL, languageId, serviceContext);
	}

	/**
	* Creates a new group friendly url with the primary key. Does not add the group friendly url to the database.
	*
	* @param groupFriendlyURLId the primary key for the new group friendly url
	* @return the new group friendly url
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL createGroupFriendlyURL(
		long groupFriendlyURLId) {
		return _groupFriendlyURLLocalService.createGroupFriendlyURL(groupFriendlyURLId);
	}

	/**
	* Deletes the group friendly url from the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURL the group friendly url
	* @return the group friendly url that was removed
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL deleteGroupFriendlyURL(
		com.liferay.portal.kernel.route.model.GroupFriendlyURL groupFriendlyURL) {
		return _groupFriendlyURLLocalService.deleteGroupFriendlyURL(groupFriendlyURL);
	}

	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL deleteGroupFriendlyURL(
		long companyId, long groupId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.deleteGroupFriendlyURL(companyId,
			groupId, languageId);
	}

	/**
	* Deletes the group friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url that was removed
	* @throws PortalException if a group friendly url with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL deleteGroupFriendlyURL(
		long groupFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.deleteGroupFriendlyURL(groupFriendlyURLId);
	}

	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURL(
		long companyId, long groupId, java.lang.String languageId) {
		return _groupFriendlyURLLocalService.fetchGroupFriendlyURL(companyId,
			groupId, languageId);
	}

	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURL(
		long groupFriendlyURLId) {
		return _groupFriendlyURLLocalService.fetchGroupFriendlyURL(groupFriendlyURLId);
	}

	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURLByFriendlyURL(
		long companyId, java.lang.String friendlyURL) {
		return _groupFriendlyURLLocalService.fetchGroupFriendlyURLByFriendlyURL(companyId,
			friendlyURL);
	}

	/**
	* Returns the group friendly url matching the UUID and group.
	*
	* @param uuid the group friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _groupFriendlyURLLocalService.fetchGroupFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the group friendly url with the primary key.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url
	* @throws PortalException if a group friendly url with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL getGroupFriendlyURL(
		long groupFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.getGroupFriendlyURL(groupFriendlyURLId);
	}

	/**
	* Returns the group friendly url matching the UUID and group.
	*
	* @param uuid the group friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching group friendly url
	* @throws PortalException if a matching group friendly url could not be found
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL getGroupFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.getGroupFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the group friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURL the group friendly url
	* @return the group friendly url that was updated
	*/
	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL updateGroupFriendlyURL(
		com.liferay.portal.kernel.route.model.GroupFriendlyURL groupFriendlyURL) {
		return _groupFriendlyURLLocalService.updateGroupFriendlyURL(groupFriendlyURL);
	}

	@Override
	public com.liferay.portal.kernel.route.model.GroupFriendlyURL updateGroupFriendlyURL(
		long userId, long companyId, long groupId,
		java.lang.String friendlyURL, java.lang.String languageId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.updateGroupFriendlyURL(userId,
			companyId, groupId, friendlyURL, languageId, serviceContext);
	}

	/**
	* Returns the number of group friendly urls.
	*
	* @return the number of group friendly urls
	*/
	@Override
	public int getGroupFriendlyURLsCount() {
		return _groupFriendlyURLLocalService.getGroupFriendlyURLsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _groupFriendlyURLLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> addGroupFriendlyURLs(
		long userId, long companyId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.addGroupFriendlyURLs(userId,
			companyId, groupId, friendlyURLMap, serviceContext);
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
		return _groupFriendlyURLLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.route.model.impl.GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _groupFriendlyURLLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.route.model.impl.GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _groupFriendlyURLLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns a range of all the group friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.route.model.impl.GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of group friendly urls
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLs(
		int start, int end) {
		return _groupFriendlyURLLocalService.getGroupFriendlyURLs(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLs(
		long companyId, long groupId) {
		return _groupFriendlyURLLocalService.getGroupFriendlyURLs(companyId,
			groupId);
	}

	/**
	* Returns all the group friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the group friendly urls
	* @param companyId the primary key of the company
	* @return the matching group friendly urls, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _groupFriendlyURLLocalService.getGroupFriendlyURLsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of group friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the group friendly urls
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching group friendly urls, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.route.model.GroupFriendlyURL> orderByComparator) {
		return _groupFriendlyURLLocalService.getGroupFriendlyURLsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> updateGroupFriendlyURLs(
		long userId, long companyId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _groupFriendlyURLLocalService.updateGroupFriendlyURLs(userId,
			companyId, groupId, friendlyURLMap, serviceContext);
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
		return _groupFriendlyURLLocalService.dynamicQueryCount(dynamicQuery);
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
		return _groupFriendlyURLLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteGroupFriendlyURLs(long companyId, long groupId) {
		_groupFriendlyURLLocalService.deleteGroupFriendlyURLs(companyId, groupId);
	}

	@Override
	public GroupFriendlyURLLocalService getWrappedService() {
		return _groupFriendlyURLLocalService;
	}

	@Override
	public void setWrappedService(
		GroupFriendlyURLLocalService groupFriendlyURLLocalService) {
		_groupFriendlyURLLocalService = groupFriendlyURLLocalService;
	}

	private GroupFriendlyURLLocalService _groupFriendlyURLLocalService;
}