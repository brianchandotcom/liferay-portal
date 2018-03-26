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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceTaxFixedRateAddressRel. This utility wraps
 * {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelLocalService
 * @see com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelLocalServiceBaseImpl
 * @see com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce tax fixed rate address rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was added
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		return getService()
				   .addCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRel);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long commerceTaxCategoryId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxFixedRateAddressRel(commerceTaxMethodId,
			commerceTaxCategoryId, commerceCountryId, commerceRegionId, zip,
			rate, serviceContext);
	}

	/**
	* Creates a new commerce tax fixed rate address rel with the primary key. Does not add the commerce tax fixed rate address rel to the database.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key for the new commerce tax fixed rate address rel
	* @return the new commerce tax fixed rate address rel
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel createCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) {
		return getService()
				   .createCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	/**
	* Deletes the commerce tax fixed rate address rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was removed
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel deleteCommerceTaxFixedRateAddressRel(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		return getService()
				   .deleteCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRel);
	}

	/**
	* Deletes the commerce tax fixed rate address rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was removed
	* @throws PortalException if a commerce tax fixed rate address rel with the primary key could not be found
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel deleteCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	public static void deleteCommerceTaxFixedRateAddressRels(
		long commerceTaxCategoryId) {
		getService().deleteCommerceTaxFixedRateAddressRels(commerceTaxCategoryId);
	}

	public static void deleteCommerceTaxMethodFixedRateAddressRels(
		long commerceTaxMethodId) {
		getService()
			.deleteCommerceTaxMethodFixedRateAddressRels(commerceTaxMethodId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) {
		return getService()
				   .fetchCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip) {
		return getService()
				   .fetchCommerceTaxFixedRateAddressRel(commerceTaxMethodId,
			commerceCountryId, commerceRegionId, zip);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce tax fixed rate address rel with the primary key.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel
	* @throws PortalException if a commerce tax fixed rate address rel with the primary key could not be found
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel getCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	/**
	* Returns a range of all the commerce tax fixed rate address rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax fixed rate address rels
	* @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	* @return the range of commerce tax fixed rate address rels
	*/
	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		int start, int end) {
		return getService().getCommerceTaxFixedRateAddressRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		long commerceTaxCategoryId, int start, int end) {
		return getService()
				   .getCommerceTaxFixedRateAddressRels(commerceTaxCategoryId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		long commerceTaxCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> orderByComparator) {
		return getService()
				   .getCommerceTaxFixedRateAddressRels(commerceTaxCategoryId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce tax fixed rate address rels.
	*
	* @return the number of commerce tax fixed rate address rels
	*/
	public static int getCommerceTaxFixedRateAddressRelsCount() {
		return getService().getCommerceTaxFixedRateAddressRelsCount();
	}

	public static int getCommerceTaxFixedRateAddressRelsCount(
		long commerceTaxCategoryId) {
		return getService()
				   .getCommerceTaxFixedRateAddressRelsCount(commerceTaxCategoryId);
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long commerceTaxMethodId, int start, int end) {
		return getService()
				   .getCommerceTaxMethodFixedRateAddressRels(commerceTaxMethodId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> orderByComparator) {
		return getService()
				   .getCommerceTaxMethodFixedRateAddressRels(commerceTaxMethodId,
			start, end, orderByComparator);
	}

	public static int getCommerceTaxMethodFixedRateAddressRelsCount(
		long commerceTaxMethodId) {
		return getService()
				   .getCommerceTaxMethodFixedRateAddressRelsCount(commerceTaxMethodId);
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
	* Updates the commerce tax fixed rate address rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was updated
	*/
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		return getService()
				   .updateCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRel);
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId,
			commerceCountryId, commerceRegionId, zip, rate);
	}

	public static CommerceTaxFixedRateAddressRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxFixedRateAddressRelLocalService, CommerceTaxFixedRateAddressRelLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceTaxFixedRateAddressRelLocalService.class);
}