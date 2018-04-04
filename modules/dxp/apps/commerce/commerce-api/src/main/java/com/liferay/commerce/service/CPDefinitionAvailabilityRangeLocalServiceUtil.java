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
 * Provides the local service utility for CPDefinitionAvailabilityRange. This utility wraps
 * {@link com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRangeLocalService
 * @see com.liferay.commerce.service.base.CPDefinitionAvailabilityRangeLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionAvailabilityRangeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition availability range to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	* @return the cp definition availability range that was added
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange addCPDefinitionAvailabilityRange(
		com.liferay.commerce.model.CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return getService()
				   .addCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRange);
	}

	/**
	* Creates a new cp definition availability range with the primary key. Does not add the cp definition availability range to the database.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key for the new cp definition availability range
	* @return the new cp definition availability range
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange createCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) {
		return getService()
				   .createCPDefinitionAvailabilityRange(CPDefinitionAvailabilityRangeId);
	}

	/**
	* Deletes the cp definition availability range from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	* @return the cp definition availability range that was removed
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
		com.liferay.commerce.model.CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return getService()
				   .deleteCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRange);
	}

	/**
	* Deletes the cp definition availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range that was removed
	* @throws PortalException if a cp definition availability range with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionAvailabilityRange(CPDefinitionAvailabilityRangeId);
	}

	public static void deleteCPDefinitionAvailabilityRanges(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCPDefinitionAvailabilityRanges(commerceAvailabilityRangeId);
	}

	public static void deleteCPDefinitionAvailabilityRangesByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCPDefinitionAvailabilityRangesByCPDefinitionId(cpDefinitionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) {
		return getService()
				   .fetchCPDefinitionAvailabilityRange(CPDefinitionAvailabilityRangeId);
	}

	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
		long cpDefinitionId) {
		return getService()
				   .fetchCPDefinitionAvailabilityRangeByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the cp definition availability range matching the UUID and group.
	*
	* @param uuid the cp definition availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCPDefinitionAvailabilityRangeByUuidAndGroupId(uuid,
			groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition availability range with the primary key.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range
	* @throws PortalException if a cp definition availability range with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionAvailabilityRange(CPDefinitionAvailabilityRangeId);
	}

	/**
	* Returns the cp definition availability range matching the UUID and group.
	*
	* @param uuid the cp definition availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition availability range
	* @throws PortalException if a matching cp definition availability range could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionAvailabilityRangeByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp definition availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @return the range of cp definition availability ranges
	*/
	public static java.util.List<com.liferay.commerce.model.CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRanges(
		int start, int end) {
		return getService().getCPDefinitionAvailabilityRanges(start, end);
	}

	/**
	* Returns all the cp definition availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition availability ranges
	* @param companyId the primary key of the company
	* @return the matching cp definition availability ranges, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCPDefinitionAvailabilityRangesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition availability ranges
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition availability ranges, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CPDefinitionAvailabilityRange> orderByComparator) {
		return getService()
				   .getCPDefinitionAvailabilityRangesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition availability ranges.
	*
	* @return the number of cp definition availability ranges
	*/
	public static int getCPDefinitionAvailabilityRangesCount() {
		return getService().getCPDefinitionAvailabilityRangesCount();
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
	* Updates the cp definition availability range in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	* @return the cp definition availability range that was updated
	*/
	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		com.liferay.commerce.model.CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return getService()
				   .updateCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRange);
	}

	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId, long cpDefinitionId,
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRangeId,
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	public static CPDefinitionAvailabilityRangeLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionAvailabilityRangeLocalService, CPDefinitionAvailabilityRangeLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionAvailabilityRangeLocalService.class);

		ServiceTracker<CPDefinitionAvailabilityRangeLocalService, CPDefinitionAvailabilityRangeLocalService> serviceTracker =
			new ServiceTracker<CPDefinitionAvailabilityRangeLocalService, CPDefinitionAvailabilityRangeLocalService>(bundle.getBundleContext(),
				CPDefinitionAvailabilityRangeLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}