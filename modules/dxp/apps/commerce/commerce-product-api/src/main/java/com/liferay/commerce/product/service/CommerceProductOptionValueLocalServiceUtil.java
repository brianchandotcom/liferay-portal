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
 * Provides the local service utility for CommerceProductOptionValue. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductOptionValueLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceProductOptionValueLocalService
 * @see com.liferay.commerce.product.service.base.CommerceProductOptionValueLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductOptionValueLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductOptionValueLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductOptionValueLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce product option value to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionValue the commerce product option value
	* @return the commerce product option value that was added
	*/
	public static com.liferay.commerce.product.model.CommerceProductOptionValue addCommerceProductOptionValue(
		com.liferay.commerce.product.model.CommerceProductOptionValue commerceProductOptionValue) {
		return getService()
				   .addCommerceProductOptionValue(commerceProductOptionValue);
	}

	public static com.liferay.commerce.product.model.CommerceProductOptionValue addCommerceProductOptionValue(
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		long priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductOptionValue(commerceProductOptionId,
			titleMap, priority, serviceContext);
	}

	/**
	* Creates a new commerce product option value with the primary key. Does not add the commerce product option value to the database.
	*
	* @param commerceProductOptionValueId the primary key for the new commerce product option value
	* @return the new commerce product option value
	*/
	public static com.liferay.commerce.product.model.CommerceProductOptionValue createCommerceProductOptionValue(
		long commerceProductOptionValueId) {
		return getService()
				   .createCommerceProductOptionValue(commerceProductOptionValueId);
	}

	/**
	* Deletes the commerce product option value from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionValue the commerce product option value
	* @return the commerce product option value that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CommerceProductOptionValue deleteCommerceProductOptionValue(
		com.liferay.commerce.product.model.CommerceProductOptionValue commerceProductOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductOptionValue(commerceProductOptionValue);
	}

	/**
	* Deletes the commerce product option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value that was removed
	* @throws PortalException if a commerce product option value with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductOptionValue deleteCommerceProductOptionValue(
		long commerceProductOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductOptionValue(commerceProductOptionValueId);
	}

	public static com.liferay.commerce.product.model.CommerceProductOptionValue fetchCommerceProductOptionValue(
		long commerceProductOptionValueId) {
		return getService()
				   .fetchCommerceProductOptionValue(commerceProductOptionValueId);
	}

	/**
	* Returns the commerce product option value with the primary key.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value
	* @throws PortalException if a commerce product option value with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductOptionValue getCommerceProductOptionValue(
		long commerceProductOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductOptionValue(commerceProductOptionValueId);
	}

	/**
	* Updates the commerce product option value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionValue the commerce product option value
	* @return the commerce product option value that was updated
	*/
	public static com.liferay.commerce.product.model.CommerceProductOptionValue updateCommerceProductOptionValue(
		com.liferay.commerce.product.model.CommerceProductOptionValue commerceProductOptionValue) {
		return getService()
				   .updateCommerceProductOptionValue(commerceProductOptionValue);
	}

	public static com.liferay.commerce.product.model.CommerceProductOptionValue updateCommerceProductOptionValue(
		long commerceProductOptionValueId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		long priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductOptionValue(commerceProductOptionValueId,
			titleMap, priority, serviceContext);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
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
	* Returns the number of commerce product option values.
	*
	* @return the number of commerce product option values
	*/
	public static int getCommerceProductOptionValuesCount() {
		return getService().getCommerceProductOptionValuesCount();
	}

	public static int getCommerceProductOptionValuesCount(
		long commerceProductOptionId) {
		return getService()
				   .getCommerceProductOptionValuesCount(commerceProductOptionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce product option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of commerce product option values
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOptionValue> getCommerceProductOptionValues(
		int start, int end) {
		return getService().getCommerceProductOptionValues(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end) {
		return getService()
				   .getCommerceProductOptionValues(commerceProductOptionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductOptionValue> orderByComparator) {
		return getService()
				   .getCommerceProductOptionValues(commerceProductOptionId,
			start, end, orderByComparator);
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

	public static CommerceProductOptionValueLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductOptionValueLocalService, CommerceProductOptionValueLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductOptionValueLocalService.class);
}