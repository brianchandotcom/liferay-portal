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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceUserSegmentEntry. This utility wraps
 * {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntryLocalService
 * @see com.liferay.commerce.user.segment.service.base.CommerceUserSegmentEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce user segment entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was added
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry commerceUserSegmentEntry) {
		return getService().addCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		java.util.Map<java.util.Locale, String> nameMap, String key,
		boolean active, boolean system, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceUserSegmentEntry(nameMap, key, active, system,
			priority, serviceContext);
	}

	/**
	* Creates a new commerce user segment entry with the primary key. Does not add the commerce user segment entry to the database.
	*
	* @param commerceUserSegmentEntryId the primary key for the new commerce user segment entry
	* @return the new commerce user segment entry
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry createCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId) {
		return getService()
				   .createCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	public static void deleteCommerceUserSegmentEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceUserSegmentEntries(groupId);
	}

	/**
	* Deletes the commerce user segment entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	/**
	* Deletes the commerce user segment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws PortalException if a commerce user segment entry with the primary key could not be found
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry fetchCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId) {
		return getService()
				   .fetchCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry fetchCommerceUserSegmentEntry(
		long groupId, String key) {
		return getService().fetchCommerceUserSegmentEntry(groupId, key);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce user segment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of commerce user segment entries
	*/
	public static java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		int start, int end) {
		return getService().getCommerceUserSegmentEntries(start, end);
	}

	public static java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> orderByComparator) {
		return getService()
				   .getCommerceUserSegmentEntries(groupId, start, end,
			orderByComparator);
	}

	public static java.util.ArrayList<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceUserSegmentEntries(groupId, userId);
	}

	/**
	* Returns the number of commerce user segment entries.
	*
	* @return the number of commerce user segment entries
	*/
	public static int getCommerceUserSegmentEntriesCount() {
		return getService().getCommerceUserSegmentEntriesCount();
	}

	public static int getCommerceUserSegmentEntriesCount(long groupId) {
		return getService().getCommerceUserSegmentEntriesCount(groupId);
	}

	/**
	* Returns the commerce user segment entry with the primary key.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry
	* @throws PortalException if a commerce user segment entry with the primary key could not be found
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry getCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void importSystemCommerceUserSegmentEntries(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importSystemCommerceUserSegmentEntries(serviceContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommerceUserSegmentEntries(companyId, groupId,
			keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCommerceUserSegmentEntries(searchContext);
	}

	/**
	* Updates the commerce user segment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was updated
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry commerceUserSegmentEntry) {
		return getService()
				   .updateCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId,
		java.util.Map<java.util.Locale, String> nameMap, String key,
		boolean active, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceUserSegmentEntry(commerceUserSegmentEntryId,
			nameMap, key, active, priority, serviceContext);
	}

	public static CommerceUserSegmentEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceUserSegmentEntryLocalService, CommerceUserSegmentEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceUserSegmentEntryLocalService.class);

		ServiceTracker<CommerceUserSegmentEntryLocalService, CommerceUserSegmentEntryLocalService> serviceTracker =
			new ServiceTracker<CommerceUserSegmentEntryLocalService, CommerceUserSegmentEntryLocalService>(bundle.getBundleContext(),
				CommerceUserSegmentEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}