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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CShippingFixedOptionRel. This utility wraps
 * {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CShippingFixedOptionRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRelLocalService
 * @see com.liferay.commerce.shipping.engine.fixed.service.base.CShippingFixedOptionRelLocalServiceBaseImpl
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CShippingFixedOptionRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CShippingFixedOptionRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the c shipping fixed option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	* @return the c shipping fixed option rel that was added
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel addCShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel cShippingFixedOptionRel) {
		return getService().addCShippingFixedOptionRel(cShippingFixedOptionRel);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel addCShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, double fixedPrice, double rateUnitWeightPrice,
		double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCShippingFixedOptionRel(commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage, serviceContext);
	}

	/**
	* Creates a new c shipping fixed option rel with the primary key. Does not add the c shipping fixed option rel to the database.
	*
	* @param CShippingFixedOptionRelId the primary key for the new c shipping fixed option rel
	* @return the new c shipping fixed option rel
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel createCShippingFixedOptionRel(
		long CShippingFixedOptionRelId) {
		return getService()
				   .createCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	/**
	* Deletes the c shipping fixed option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	* @return the c shipping fixed option rel that was removed
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel deleteCShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel cShippingFixedOptionRel) {
		return getService()
				   .deleteCShippingFixedOptionRel(cShippingFixedOptionRel);
	}

	/**
	* Deletes the c shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel that was removed
	* @throws PortalException if a c shipping fixed option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel deleteCShippingFixedOptionRel(
		long CShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	public static void deleteCShippingFixedOptionRels(
		long commerceShippingFixedOptionId) {
		getService()
			.deleteCShippingFixedOptionRels(commerceShippingFixedOptionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long CShippingFixedOptionRelId) {
		return getService()
				   .fetchCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weight) {
		return getService()
				   .fetchCShippingFixedOptionRel(commerceShippingFixedOptionId,
			commerceCountryId, commerceRegionId, zip, weight);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);
	}

	/**
	* Returns the c shipping fixed option rel with the primary key.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel
	* @throws PortalException if a c shipping fixed option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel getCShippingFixedOptionRel(
		long CShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	/**
	* Returns a range of all the c shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @return the range of c shipping fixed option rels
	*/
	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		int start, int end) {
		return getService().getCShippingFixedOptionRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {
		return getService()
				   .getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of c shipping fixed option rels.
	*
	* @return the number of c shipping fixed option rels
	*/
	public static int getCShippingFixedOptionRelsCount() {
		return getService().getCShippingFixedOptionRelsCount();
	}

	public static int getCShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {
		return getService()
				   .getCShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
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
	* Updates the c shipping fixed option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	* @return the c shipping fixed option rel that was updated
	*/
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel updateCShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel cShippingFixedOptionRel) {
		return getService()
				   .updateCShippingFixedOptionRel(cShippingFixedOptionRel);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel updateCShippingFixedOptionRel(
		long cShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, double fixedPrice,
		double rateUnitWeightPrice, double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCShippingFixedOptionRel(cShippingFixedOptionRelId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage);
	}

	public static CShippingFixedOptionRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CShippingFixedOptionRelLocalService, CShippingFixedOptionRelLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CShippingFixedOptionRelLocalService.class);
}