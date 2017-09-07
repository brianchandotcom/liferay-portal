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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPAvailabilityRange. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPAvailabilityRangeLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPAvailabilityRangeLocalService
 * @see com.liferay.commerce.product.service.base.CPAvailabilityRangeLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPAvailabilityRangeLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPAvailabilityRangeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPAvailabilityRangeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp availability range to the database. Also notifies the appropriate model listeners.
	*
	* @param cpAvailabilityRange the cp availability range
	* @return the cp availability range that was added
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange addCPAvailabilityRange(
		com.liferay.commerce.product.model.CPAvailabilityRange cpAvailabilityRange) {
		return getService().addCPAvailabilityRange(cpAvailabilityRange);
	}

	public static com.liferay.commerce.product.model.CPAvailabilityRange addCPAvailabilityRange(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCPAvailabilityRange(titleMap, serviceContext);
	}

	/**
	* Creates a new cp availability range with the primary key. Does not add the cp availability range to the database.
	*
	* @param CPAvailabilityRangeId the primary key for the new cp availability range
	* @return the new cp availability range
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange createCPAvailabilityRange(
		long CPAvailabilityRangeId) {
		return getService().createCPAvailabilityRange(CPAvailabilityRangeId);
	}

	/**
	* Deletes the cp availability range from the database. Also notifies the appropriate model listeners.
	*
	* @param cpAvailabilityRange the cp availability range
	* @return the cp availability range that was removed
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange deleteCPAvailabilityRange(
		com.liferay.commerce.product.model.CPAvailabilityRange cpAvailabilityRange) {
		return getService().deleteCPAvailabilityRange(cpAvailabilityRange);
	}

	/**
	* Deletes the cp availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range that was removed
	* @throws PortalException if a cp availability range with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange deleteCPAvailabilityRange(
		long CPAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPAvailabilityRange(CPAvailabilityRangeId);
	}

	public static void deleteCPAvailabilityRanges(long groupId) {
		getService().deleteCPAvailabilityRanges(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPAvailabilityRange fetchCPAvailabilityRange(
		long CPAvailabilityRangeId) {
		return getService().fetchCPAvailabilityRange(CPAvailabilityRangeId);
	}

	/**
	* Returns the cp availability range matching the UUID and group.
	*
	* @param uuid the cp availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange fetchCPAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCPAvailabilityRangeByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp availability range with the primary key.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range
	* @throws PortalException if a cp availability range with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange getCPAvailabilityRange(
		long CPAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPAvailabilityRange(CPAvailabilityRangeId);
	}

	/**
	* Returns the cp availability range matching the UUID and group.
	*
	* @param uuid the cp availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching cp availability range
	* @throws PortalException if a matching cp availability range could not be found
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange getCPAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPAvailabilityRangeByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cp availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @return the range of cp availability ranges
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPAvailabilityRange> getCPAvailabilityRanges(
		int start, int end) {
		return getService().getCPAvailabilityRanges(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAvailabilityRange> getCPAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAvailabilityRange> orderByComparator) {
		return getService()
				   .getCPAvailabilityRanges(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the cp availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the cp availability ranges
	* @param companyId the primary key of the company
	* @return the matching cp availability ranges, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPAvailabilityRange> getCPAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCPAvailabilityRangesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of cp availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the cp availability ranges
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp availability ranges, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPAvailabilityRange> getCPAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAvailabilityRange> orderByComparator) {
		return getService()
				   .getCPAvailabilityRangesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of cp availability ranges.
	*
	* @return the number of cp availability ranges
	*/
	public static int getCPAvailabilityRangesCount() {
		return getService().getCPAvailabilityRangesCount();
	}

	public static int getCPAvailabilityRangesCount(long groupId) {
		return getService().getCPAvailabilityRangesCount(groupId);
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
	* Updates the cp availability range in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpAvailabilityRange the cp availability range
	* @return the cp availability range that was updated
	*/
	public static com.liferay.commerce.product.model.CPAvailabilityRange updateCPAvailabilityRange(
		com.liferay.commerce.product.model.CPAvailabilityRange cpAvailabilityRange) {
		return getService().updateCPAvailabilityRange(cpAvailabilityRange);
	}

	public static com.liferay.commerce.product.model.CPAvailabilityRange updateCPAvailabilityRange(
		long cpAvailabilityRangeId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPAvailabilityRange(cpAvailabilityRangeId, titleMap,
			serviceContext);
	}

	public static CPAvailabilityRangeLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPAvailabilityRangeLocalService, CPAvailabilityRangeLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CPAvailabilityRangeLocalService.class);
}