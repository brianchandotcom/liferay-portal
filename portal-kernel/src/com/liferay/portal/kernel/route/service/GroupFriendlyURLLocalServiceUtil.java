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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for GroupFriendlyURL. This utility wraps
 * {@link com.liferay.portal.route.service.impl.GroupFriendlyURLLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see GroupFriendlyURLLocalService
 * @see com.liferay.portal.route.service.base.GroupFriendlyURLLocalServiceBaseImpl
 * @see com.liferay.portal.route.service.impl.GroupFriendlyURLLocalServiceImpl
 * @generated
 */
@ProviderType
public class GroupFriendlyURLLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.route.service.impl.GroupFriendlyURLLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the group friendly url to the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURL the group friendly url
	* @return the group friendly url that was added
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL addGroupFriendlyURL(
		com.liferay.portal.kernel.route.model.GroupFriendlyURL groupFriendlyURL) {
		return getService().addGroupFriendlyURL(groupFriendlyURL);
	}

	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL addGroupFriendlyURL(
		long userId, long companyId, long groupId,
		java.lang.String friendlyURL, java.lang.String languageId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addGroupFriendlyURL(userId, companyId, groupId,
			friendlyURL, languageId, serviceContext);
	}

	/**
	* Creates a new group friendly url with the primary key. Does not add the group friendly url to the database.
	*
	* @param groupFriendlyURLId the primary key for the new group friendly url
	* @return the new group friendly url
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL createGroupFriendlyURL(
		long groupFriendlyURLId) {
		return getService().createGroupFriendlyURL(groupFriendlyURLId);
	}

	/**
	* Deletes the group friendly url from the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURL the group friendly url
	* @return the group friendly url that was removed
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL deleteGroupFriendlyURL(
		com.liferay.portal.kernel.route.model.GroupFriendlyURL groupFriendlyURL) {
		return getService().deleteGroupFriendlyURL(groupFriendlyURL);
	}

	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL deleteGroupFriendlyURL(
		long companyId, long groupId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteGroupFriendlyURL(companyId, groupId, languageId);
	}

	/**
	* Deletes the group friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url that was removed
	* @throws PortalException if a group friendly url with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL deleteGroupFriendlyURL(
		long groupFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteGroupFriendlyURL(groupFriendlyURLId);
	}

	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURL(
		long companyId, long groupId, java.lang.String languageId) {
		return getService().fetchGroupFriendlyURL(companyId, groupId, languageId);
	}

	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURL(
		long groupFriendlyURLId) {
		return getService().fetchGroupFriendlyURL(groupFriendlyURLId);
	}

	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURLByFriendlyURL(
		long companyId, java.lang.String friendlyURL) {
		return getService()
				   .fetchGroupFriendlyURLByFriendlyURL(companyId, friendlyURL);
	}

	/**
	* Returns the group friendly url matching the UUID and group.
	*
	* @param uuid the group friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL fetchGroupFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchGroupFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the group friendly url with the primary key.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url
	* @throws PortalException if a group friendly url with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL getGroupFriendlyURL(
		long groupFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupFriendlyURL(groupFriendlyURLId);
	}

	/**
	* Returns the group friendly url matching the UUID and group.
	*
	* @param uuid the group friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching group friendly url
	* @throws PortalException if a matching group friendly url could not be found
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL getGroupFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the group friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURL the group friendly url
	* @return the group friendly url that was updated
	*/
	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL updateGroupFriendlyURL(
		com.liferay.portal.kernel.route.model.GroupFriendlyURL groupFriendlyURL) {
		return getService().updateGroupFriendlyURL(groupFriendlyURL);
	}

	public static com.liferay.portal.kernel.route.model.GroupFriendlyURL updateGroupFriendlyURL(
		long userId, long companyId, long groupId,
		java.lang.String friendlyURL, java.lang.String languageId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateGroupFriendlyURL(userId, companyId, groupId,
			friendlyURL, languageId, serviceContext);
	}

	/**
	* Returns the number of group friendly urls.
	*
	* @return the number of group friendly urls
	*/
	public static int getGroupFriendlyURLsCount() {
		return getService().getGroupFriendlyURLsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> addGroupFriendlyURLs(
		long userId, long companyId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addGroupFriendlyURLs(userId, companyId, groupId,
			friendlyURLMap, serviceContext);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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
	public static java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLs(
		int start, int end) {
		return getService().getGroupFriendlyURLs(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLs(
		long companyId, long groupId) {
		return getService().getGroupFriendlyURLs(companyId, groupId);
	}

	/**
	* Returns all the group friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the group friendly urls
	* @param companyId the primary key of the company
	* @return the matching group friendly urls, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getGroupFriendlyURLsByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> getGroupFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.route.model.GroupFriendlyURL> orderByComparator) {
		return getService()
				   .getGroupFriendlyURLsByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.route.model.GroupFriendlyURL> updateGroupFriendlyURLs(
		long userId, long companyId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateGroupFriendlyURLs(userId, companyId, groupId,
			friendlyURLMap, serviceContext);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static void deleteGroupFriendlyURLs(long companyId, long groupId) {
		getService().deleteGroupFriendlyURLs(companyId, groupId);
	}

	public static GroupFriendlyURLLocalService getService() {
		if (_service == null) {
			_service = (GroupFriendlyURLLocalService)PortalBeanLocatorUtil.locate(GroupFriendlyURLLocalService.class.getName());

			ReferenceRegistry.registerReference(GroupFriendlyURLLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static GroupFriendlyURLLocalService _service;
}