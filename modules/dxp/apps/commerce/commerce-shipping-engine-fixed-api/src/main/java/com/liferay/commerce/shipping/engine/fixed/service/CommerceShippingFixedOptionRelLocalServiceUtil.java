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

package com.liferay.commerce.shipping.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceShippingFixedOptionRel. This utility wraps
 * {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelLocalService
 * @see com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionRelLocalServiceBaseImpl
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce shipping fixed option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel that was added
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		return getService()
				   .addCommerceShippingFixedOptionRel(commerceShippingFixedOptionRel);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, String zip, double weightFrom, double weightTo,
		java.math.BigDecimal fixedPrice,
		java.math.BigDecimal rateUnitWeightPrice, double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShippingFixedOptionRel(commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage, serviceContext);
	}

	/**
	* Creates a new commerce shipping fixed option rel with the primary key. Does not add the commerce shipping fixed option rel to the database.
	*
	* @param commerceShippingFixedOptionRelId the primary key for the new commerce shipping fixed option rel
	* @return the new commerce shipping fixed option rel
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel createCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId) {
		return getService()
				   .createCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	/**
	* Deletes the commerce shipping fixed option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel that was removed
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel deleteCommerceShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		return getService()
				   .deleteCommerceShippingFixedOptionRel(commerceShippingFixedOptionRel);
	}

	/**
	* Deletes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel that was removed
	* @throws PortalException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel deleteCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	public static void deleteCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId) {
		getService()
			.deleteCommerceShippingFixedOptionRels(commerceShippingFixedOptionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId) {
		return getService()
				   .fetchCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {
		return getService()
				   .fetchCommerceShippingFixedOptionRel(commerceShippingFixedOptionId,
			commerceCountryId, commerceRegionId, zip, weight);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce shipping fixed option rel with the primary key.
	*
	* @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel
	* @throws PortalException if a commerce shipping fixed option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel getCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	/**
	* Returns a range of all the commerce shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed option rels
	* @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	* @return the range of commerce shipping fixed option rels
	*/
	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		int start, int end) {
		return getService().getCommerceShippingFixedOptionRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {
		return getService()
				   .getCommerceShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCommerceShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce shipping fixed option rels.
	*
	* @return the number of commerce shipping fixed option rels
	*/
	public static int getCommerceShippingFixedOptionRelsCount() {
		return getService().getCommerceShippingFixedOptionRelsCount();
	}

	public static int getCommerceShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {
		return getService()
				   .getCommerceShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);
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

	/**
	* Updates the commerce shipping fixed option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	* @return the commerce shipping fixed option rel that was updated
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
		return getService()
				   .updateCommerceShippingFixedOptionRel(commerceShippingFixedOptionRel);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, String zip,
		double weightFrom, double weightTo, java.math.BigDecimal fixedPrice,
		java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage);
	}

	public static CommerceShippingFixedOptionRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShippingFixedOptionRelLocalService, CommerceShippingFixedOptionRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceShippingFixedOptionRelLocalService.class);

		ServiceTracker<CommerceShippingFixedOptionRelLocalService, CommerceShippingFixedOptionRelLocalService> serviceTracker =
			new ServiceTracker<CommerceShippingFixedOptionRelLocalService, CommerceShippingFixedOptionRelLocalService>(bundle.getBundleContext(),
				CommerceShippingFixedOptionRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}