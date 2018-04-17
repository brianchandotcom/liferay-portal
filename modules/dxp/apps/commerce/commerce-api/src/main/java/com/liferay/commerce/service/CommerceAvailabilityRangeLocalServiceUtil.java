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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceAvailabilityRange. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceAvailabilityRangeLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRangeLocalService
 * @see com.liferay.commerce.service.base.CommerceAvailabilityRangeLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceAvailabilityRangeLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceAvailabilityRangeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce availability range to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRange the commerce availability range
	* @return the commerce availability range that was added
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		com.liferay.commerce.model.CommerceAvailabilityRange commerceAvailabilityRange) {
		return getService()
				   .addCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	public static com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceAvailabilityRange(titleMap, priority,
			serviceContext);
	}

	/**
	* Creates a new commerce availability range with the primary key. Does not add the commerce availability range to the database.
	*
	* @param commerceAvailabilityRangeId the primary key for the new commerce availability range
	* @return the new commerce availability range
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange createCommerceAvailabilityRange(
		long commerceAvailabilityRangeId) {
		return getService()
				   .createCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	/**
	* Deletes the commerce availability range from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRange the commerce availability range
	* @return the commerce availability range that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange deleteCommerceAvailabilityRange(
		com.liferay.commerce.model.CommerceAvailabilityRange commerceAvailabilityRange)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	/**
	* Deletes the commerce availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range that was removed
	* @throws PortalException if a commerce availability range with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange deleteCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	public static void deleteCommerceAvailabilityRanges(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceAvailabilityRanges(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceAvailabilityRange fetchCommerceAvailabilityRange(
		long commerceAvailabilityRangeId) {
		return getService()
				   .fetchCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	/**
	* Returns the commerce availability range matching the UUID and group.
	*
	* @param uuid the commerce availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange fetchCommerceAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCommerceAvailabilityRangeByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce availability range with the primary key.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range
	* @throws PortalException if a commerce availability range with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	/**
	* Returns the commerce availability range matching the UUID and group.
	*
	* @param uuid the commerce availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce availability range
	* @throws PortalException if a matching commerce availability range could not be found
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceAvailabilityRangeByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the commerce availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @return the range of commerce availability ranges
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		int start, int end) {
		return getService().getCommerceAvailabilityRanges(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		return getService()
				   .getCommerceAvailabilityRanges(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the commerce availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the commerce availability ranges
	* @param companyId the primary key of the company
	* @return the matching commerce availability ranges, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommerceAvailabilityRangesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the commerce availability ranges
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce availability ranges, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		return getService()
				   .getCommerceAvailabilityRangesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce availability ranges.
	*
	* @return the number of commerce availability ranges
	*/
	public static int getCommerceAvailabilityRangesCount() {
		return getService().getCommerceAvailabilityRangesCount();
	}

	public static int getCommerceAvailabilityRangesCount(long groupId) {
		return getService().getCommerceAvailabilityRangesCount(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce availability range in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRange the commerce availability range
	* @return the commerce availability range that was updated
	*/
	public static com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		com.liferay.commerce.model.CommerceAvailabilityRange commerceAvailabilityRange) {
		return getService()
				   .updateCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	public static com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		long commerceAvailabilityId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceAvailabilityRange(commerceAvailabilityId,
			titleMap, priority, serviceContext);
	}

	public static CommerceAvailabilityRangeLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAvailabilityRangeLocalService, CommerceAvailabilityRangeLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceAvailabilityRangeLocalService.class);

		ServiceTracker<CommerceAvailabilityRangeLocalService, CommerceAvailabilityRangeLocalService> serviceTracker =
			new ServiceTracker<CommerceAvailabilityRangeLocalService, CommerceAvailabilityRangeLocalService>(bundle.getBundleContext(),
				CommerceAvailabilityRangeLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}