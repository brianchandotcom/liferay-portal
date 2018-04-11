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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceTaxFixedRate. This utility wraps
 * {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateLocalService
 * @see com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateLocalServiceBaseImpl
 * @see com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce tax fixed rate to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRate the commerce tax fixed rate
	* @return the commerce tax fixed rate that was added
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate addCommerceTaxFixedRate(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate commerceTaxFixedRate) {
		return getService().addCommerceTaxFixedRate(commerceTaxFixedRate);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate addCommerceTaxFixedRate(
		long commerceTaxMethodId, long cpTaxCategoryId, double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxFixedRate(commerceTaxMethodId,
			cpTaxCategoryId, rate, serviceContext);
	}

	/**
	* Creates a new commerce tax fixed rate with the primary key. Does not add the commerce tax fixed rate to the database.
	*
	* @param commerceTaxFixedRateId the primary key for the new commerce tax fixed rate
	* @return the new commerce tax fixed rate
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate createCommerceTaxFixedRate(
		long commerceTaxFixedRateId) {
		return getService().createCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	/**
	* Deletes the commerce tax fixed rate from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRate the commerce tax fixed rate
	* @return the commerce tax fixed rate that was removed
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate deleteCommerceTaxFixedRate(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate commerceTaxFixedRate) {
		return getService().deleteCommerceTaxFixedRate(commerceTaxFixedRate);
	}

	/**
	* Deletes the commerce tax fixed rate with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	* @return the commerce tax fixed rate that was removed
	* @throws PortalException if a commerce tax fixed rate with the primary key could not be found
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate deleteCommerceTaxFixedRate(
		long commerceTaxFixedRateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	public static void deleteCommerceTaxFixedRateByCommerceTaxMethodId(
		long commerceTaxMethodId) {
		getService()
			.deleteCommerceTaxFixedRateByCommerceTaxMethodId(commerceTaxMethodId);
	}

	public static void deleteCommerceTaxFixedRateByCPTaxCategoryId(
		long cpTaxCategoryId) {
		getService().deleteCommerceTaxFixedRateByCPTaxCategoryId(cpTaxCategoryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		long commerceTaxFixedRateId) {
		return getService().fetchCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		long cpTaxCategoryId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCommerceTaxFixedRate(cpTaxCategoryId,
			commerceTaxMethodId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce tax fixed rate with the primary key.
	*
	* @param commerceTaxFixedRateId the primary key of the commerce tax fixed rate
	* @return the commerce tax fixed rate
	* @throws PortalException if a commerce tax fixed rate with the primary key could not be found
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate getCommerceTaxFixedRate(
		long commerceTaxFixedRateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate getCommerceTaxFixedRate(
		long cpTaxCategoryId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceTaxFixedRate(cpTaxCategoryId, commerceTaxMethodId);
	}

	/**
	* Returns a range of all the commerce tax fixed rates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax fixed rates
	* @param end the upper bound of the range of commerce tax fixed rates (not inclusive)
	* @return the range of commerce tax fixed rates
	*/
	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> getCommerceTaxFixedRates(
		int start, int end) {
		return getService().getCommerceTaxFixedRates(start, end);
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> orderByComparator) {
		return getService()
				   .getCommerceTaxFixedRates(commerceTaxMethodId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of commerce tax fixed rates.
	*
	* @return the number of commerce tax fixed rates
	*/
	public static int getCommerceTaxFixedRatesCount() {
		return getService().getCommerceTaxFixedRatesCount();
	}

	public static int getCommerceTaxFixedRatesCount(long commerceTaxMethodId) {
		return getService().getCommerceTaxFixedRatesCount(commerceTaxMethodId);
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
	* Updates the commerce tax fixed rate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRate the commerce tax fixed rate
	* @return the commerce tax fixed rate that was updated
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate updateCommerceTaxFixedRate(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate commerceTaxFixedRate) {
		return getService().updateCommerceTaxFixedRate(commerceTaxFixedRate);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate updateCommerceTaxFixedRate(
		long commerceTaxFixedRateId, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxFixedRate(commerceTaxFixedRateId, rate);
	}

	public static CommerceTaxFixedRateLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxFixedRateLocalService, CommerceTaxFixedRateLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxFixedRateLocalService.class);

		ServiceTracker<CommerceTaxFixedRateLocalService, CommerceTaxFixedRateLocalService> serviceTracker =
			new ServiceTracker<CommerceTaxFixedRateLocalService, CommerceTaxFixedRateLocalService>(bundle.getBundleContext(),
				CommerceTaxFixedRateLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}