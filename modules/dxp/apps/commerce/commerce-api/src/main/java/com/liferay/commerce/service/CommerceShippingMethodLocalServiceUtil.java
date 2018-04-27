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
 * Provides the local service utility for CommerceShippingMethod. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceShippingMethodLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodLocalService
 * @see com.liferay.commerce.service.base.CommerceShippingMethodLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceShippingMethodLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShippingMethodLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceShippingMethodLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce shipping method to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingMethod the commerce shipping method
	* @return the commerce shipping method that was added
	*/
	public static com.liferay.commerce.model.CommerceShippingMethod addCommerceShippingMethod(
		com.liferay.commerce.model.CommerceShippingMethod commerceShippingMethod) {
		return getService().addCommerceShippingMethod(commerceShippingMethod);
	}

	public static com.liferay.commerce.model.CommerceShippingMethod addCommerceShippingMethod(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, String engineKey, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShippingMethod(nameMap, descriptionMap,
			imageFile, engineKey, priority, active, serviceContext);
	}

	/**
	* Creates a new commerce shipping method with the primary key. Does not add the commerce shipping method to the database.
	*
	* @param commerceShippingMethodId the primary key for the new commerce shipping method
	* @return the new commerce shipping method
	*/
	public static com.liferay.commerce.model.CommerceShippingMethod createCommerceShippingMethod(
		long commerceShippingMethodId) {
		return getService()
				   .createCommerceShippingMethod(commerceShippingMethodId);
	}

	/**
	* Deletes the commerce shipping method from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingMethod the commerce shipping method
	* @return the commerce shipping method that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.model.CommerceShippingMethod deleteCommerceShippingMethod(
		com.liferay.commerce.model.CommerceShippingMethod commerceShippingMethod)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceShippingMethod(commerceShippingMethod);
	}

	/**
	* Deletes the commerce shipping method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingMethodId the primary key of the commerce shipping method
	* @return the commerce shipping method that was removed
	* @throws PortalException if a commerce shipping method with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceShippingMethod deleteCommerceShippingMethod(
		long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceShippingMethod(commerceShippingMethodId);
	}

	public static void deleteCommerceShippingMethods(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceShippingMethods(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceShippingMethod fetchCommerceShippingMethod(
		long commerceShippingMethodId) {
		return getService().fetchCommerceShippingMethod(commerceShippingMethodId);
	}

	public static com.liferay.commerce.model.CommerceShippingMethod fetchCommerceShippingMethod(
		long groupId, String engineKey) {
		return getService().fetchCommerceShippingMethod(groupId, engineKey);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce shipping method with the primary key.
	*
	* @param commerceShippingMethodId the primary key of the commerce shipping method
	* @return the commerce shipping method
	* @throws PortalException if a commerce shipping method with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceShippingMethod getCommerceShippingMethod(
		long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceShippingMethod(commerceShippingMethodId);
	}

	/**
	* Returns a range of all the commerce shipping methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping methods
	* @param end the upper bound of the range of commerce shipping methods (not inclusive)
	* @return the range of commerce shipping methods
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		int start, int end) {
		return getService().getCommerceShippingMethods(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		long groupId) {
		return getService().getCommerceShippingMethods(groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		long groupId, boolean active) {
		return getService().getCommerceShippingMethods(groupId, active);
	}

	/**
	* Returns the number of commerce shipping methods.
	*
	* @return the number of commerce shipping methods
	*/
	public static int getCommerceShippingMethodsCount() {
		return getService().getCommerceShippingMethodsCount();
	}

	public static int getCommerceShippingMethodsCount(long groupId,
		boolean active) {
		return getService().getCommerceShippingMethodsCount(groupId, active);
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

	public static com.liferay.commerce.model.CommerceShippingMethod setActive(
		long commerceShippingMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commerceShippingMethodId, active);
	}

	/**
	* Updates the commerce shipping method in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingMethod the commerce shipping method
	* @return the commerce shipping method that was updated
	*/
	public static com.liferay.commerce.model.CommerceShippingMethod updateCommerceShippingMethod(
		com.liferay.commerce.model.CommerceShippingMethod commerceShippingMethod) {
		return getService().updateCommerceShippingMethod(commerceShippingMethod);
	}

	public static com.liferay.commerce.model.CommerceShippingMethod updateCommerceShippingMethod(
		long commerceShippingMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, double priority, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShippingMethod(commerceShippingMethodId,
			nameMap, descriptionMap, imageFile, priority, active);
	}

	public static CommerceShippingMethodLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShippingMethodLocalService, CommerceShippingMethodLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceShippingMethodLocalService.class);

		ServiceTracker<CommerceShippingMethodLocalService, CommerceShippingMethodLocalService> serviceTracker =
			new ServiceTracker<CommerceShippingMethodLocalService, CommerceShippingMethodLocalService>(bundle.getBundleContext(),
				CommerceShippingMethodLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}