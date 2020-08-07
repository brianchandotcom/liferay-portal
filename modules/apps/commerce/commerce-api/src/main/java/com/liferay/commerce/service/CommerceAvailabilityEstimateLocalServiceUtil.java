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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceAvailabilityEstimate. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceAvailabilityEstimateLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateLocalService
 * @generated
 */
public class CommerceAvailabilityEstimateLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceAvailabilityEstimateLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce availability estimate to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimate the commerce availability estimate
	 * @return the commerce availability estimate that was added
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
		addCommerceAvailabilityEstimate(
			com.liferay.commerce.model.CommerceAvailabilityEstimate
				commerceAvailabilityEstimate) {

		return getService().addCommerceAvailabilityEstimate(
			commerceAvailabilityEstimate);
	}

	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			addCommerceAvailabilityEstimate(
				java.util.Map<java.util.Locale, String> titleMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAvailabilityEstimate(
			titleMap, priority, serviceContext);
	}

	/**
	 * Creates a new commerce availability estimate with the primary key. Does not add the commerce availability estimate to the database.
	 *
	 * @param commerceAvailabilityEstimateId the primary key for the new commerce availability estimate
	 * @return the new commerce availability estimate
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
		createCommerceAvailabilityEstimate(
			long commerceAvailabilityEstimateId) {

		return getService().createCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce availability estimate from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimate the commerce availability estimate
	 * @return the commerce availability estimate that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			deleteCommerceAvailabilityEstimate(
				com.liferay.commerce.model.CommerceAvailabilityEstimate
					commerceAvailabilityEstimate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceAvailabilityEstimate(
			commerceAvailabilityEstimate);
	}

	/**
	 * Deletes the commerce availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate that was removed
	 * @throws PortalException if a commerce availability estimate with the primary key could not be found
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			deleteCommerceAvailabilityEstimate(
				long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId);
	}

	public static void deleteCommerceAvailabilityEstimates(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAvailabilityEstimates(companyId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
		fetchCommerceAvailabilityEstimate(long commerceAvailabilityEstimateId) {

		return getService().fetchCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId);
	}

	/**
	 * Returns the commerce availability estimate with the matching UUID and company.
	 *
	 * @param uuid the commerce availability estimate's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
		fetchCommerceAvailabilityEstimateByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCommerceAvailabilityEstimateByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce availability estimate with the primary key.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate
	 * @throws PortalException if a commerce availability estimate with the primary key could not be found
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			getCommerceAvailabilityEstimate(long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId);
	}

	/**
	 * Returns the commerce availability estimate with the matching UUID and company.
	 *
	 * @param uuid the commerce availability estimate's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce availability estimate
	 * @throws PortalException if a matching commerce availability estimate could not be found
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			getCommerceAvailabilityEstimateByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAvailabilityEstimateByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of commerce availability estimates
	 */
	public static java.util.List
		<com.liferay.commerce.model.CommerceAvailabilityEstimate>
			getCommerceAvailabilityEstimates(int start, int end) {

		return getService().getCommerceAvailabilityEstimates(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceAvailabilityEstimate>
			getCommerceAvailabilityEstimates(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceAvailabilityEstimate>
						orderByComparator) {

		return getService().getCommerceAvailabilityEstimates(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce availability estimates.
	 *
	 * @return the number of commerce availability estimates
	 */
	public static int getCommerceAvailabilityEstimatesCount() {
		return getService().getCommerceAvailabilityEstimatesCount();
	}

	public static int getCommerceAvailabilityEstimatesCount(long companyId) {
		return getService().getCommerceAvailabilityEstimatesCount(companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the commerce availability estimate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimate the commerce availability estimate
	 * @return the commerce availability estimate that was updated
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
		updateCommerceAvailabilityEstimate(
			com.liferay.commerce.model.CommerceAvailabilityEstimate
				commerceAvailabilityEstimate) {

		return getService().updateCommerceAvailabilityEstimate(
			commerceAvailabilityEstimate);
	}

	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			updateCommerceAvailabilityEstimate(
				long commerceAvailabilityId,
				java.util.Map<java.util.Locale, String> titleMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAvailabilityEstimate(
			commerceAvailabilityId, titleMap, priority, serviceContext);
	}

	public static CommerceAvailabilityEstimateLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAvailabilityEstimateLocalService,
		 CommerceAvailabilityEstimateLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAvailabilityEstimateLocalService.class);

		ServiceTracker
			<CommerceAvailabilityEstimateLocalService,
			 CommerceAvailabilityEstimateLocalService> serviceTracker =
				new ServiceTracker
					<CommerceAvailabilityEstimateLocalService,
					 CommerceAvailabilityEstimateLocalService>(
						 bundle.getBundleContext(),
						 CommerceAvailabilityEstimateLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}