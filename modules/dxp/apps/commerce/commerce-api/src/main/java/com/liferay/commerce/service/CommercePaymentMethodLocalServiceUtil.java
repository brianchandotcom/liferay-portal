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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommercePaymentMethod. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePaymentMethodLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodLocalService
 * @see com.liferay.commerce.service.base.CommercePaymentMethodLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePaymentMethodLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommercePaymentMethodLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePaymentMethodLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce payment method to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethod the commerce payment method
	* @return the commerce payment method that was added
	*/
	public static com.liferay.commerce.model.CommercePaymentMethod addCommercePaymentMethod(
		com.liferay.commerce.model.CommercePaymentMethod commercePaymentMethod) {
		return getService().addCommercePaymentMethod(commercePaymentMethod);
	}

	public static com.liferay.commerce.model.CommercePaymentMethod addCommercePaymentMethod(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String engineKey,
		java.util.Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePaymentMethod(nameMap, descriptionMap,
			engineKey, engineParameterMap, priority, active, serviceContext);
	}

	/**
	* Creates a new commerce payment method with the primary key. Does not add the commerce payment method to the database.
	*
	* @param commercePaymentMethodId the primary key for the new commerce payment method
	* @return the new commerce payment method
	*/
	public static com.liferay.commerce.model.CommercePaymentMethod createCommercePaymentMethod(
		long commercePaymentMethodId) {
		return getService().createCommercePaymentMethod(commercePaymentMethodId);
	}

	/**
	* Deletes the commerce payment method from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethod the commerce payment method
	* @return the commerce payment method that was removed
	*/
	public static com.liferay.commerce.model.CommercePaymentMethod deleteCommercePaymentMethod(
		com.liferay.commerce.model.CommercePaymentMethod commercePaymentMethod) {
		return getService().deleteCommercePaymentMethod(commercePaymentMethod);
	}

	/**
	* Deletes the commerce payment method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method that was removed
	* @throws PortalException if a commerce payment method with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePaymentMethod deleteCommercePaymentMethod(
		long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommercePaymentMethod(commercePaymentMethodId);
	}

	public static void deleteCommercePaymentMethods(long groupId) {
		getService().deleteCommercePaymentMethods(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommercePaymentMethod fetchCommercePaymentMethod(
		long commercePaymentMethodId) {
		return getService().fetchCommercePaymentMethod(commercePaymentMethodId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce payment method with the primary key.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method
	* @throws PortalException if a commerce payment method with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePaymentMethod getCommercePaymentMethod(
		long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommercePaymentMethod(commercePaymentMethodId);
	}

	/**
	* Returns a range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of commerce payment methods
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		int start, int end) {
		return getService().getCommercePaymentMethods(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId) {
		return getService().getCommercePaymentMethods(groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, boolean active) {
		return getService().getCommercePaymentMethods(groupId, active);
	}

	/**
	* Returns the number of commerce payment methods.
	*
	* @return the number of commerce payment methods
	*/
	public static int getCommercePaymentMethodsCount() {
		return getService().getCommercePaymentMethodsCount();
	}

	public static int getCommercePaymentMethodsCount(long groupId,
		boolean active) {
		return getService().getCommercePaymentMethodsCount(groupId, active);
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
	* Updates the commerce payment method in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethod the commerce payment method
	* @return the commerce payment method that was updated
	*/
	public static com.liferay.commerce.model.CommercePaymentMethod updateCommercePaymentMethod(
		com.liferay.commerce.model.CommercePaymentMethod commercePaymentMethod) {
		return getService().updateCommercePaymentMethod(commercePaymentMethod);
	}

	public static com.liferay.commerce.model.CommercePaymentMethod updateCommercePaymentMethod(
		long commercePaymentMethodId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePaymentMethod(commercePaymentMethodId,
			nameMap, descriptionMap, engineParameterMap, priority, active,
			serviceContext);
	}

	public static CommercePaymentMethodLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePaymentMethodLocalService, CommercePaymentMethodLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommercePaymentMethodLocalService.class);
}