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
 * Provides the local service utility for CommerceAddressRestriction. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceAddressRestrictionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionLocalService
 * @see com.liferay.commerce.service.base.CommerceAddressRestrictionLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceAddressRestrictionLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceAddressRestrictionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce address restriction to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressRestriction the commerce address restriction
	* @return the commerce address restriction that was added
	*/
	public static com.liferay.commerce.model.CommerceAddressRestriction addCommerceAddressRestriction(
		com.liferay.commerce.model.CommerceAddressRestriction commerceAddressRestriction) {
		return getService()
				   .addCommerceAddressRestriction(commerceAddressRestriction);
	}

	public static com.liferay.commerce.model.CommerceAddressRestriction addCommerceAddressRestriction(
		java.lang.String className, long classPK, long commerceCountryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceAddressRestriction(className, classPK,
			commerceCountryId, serviceContext);
	}

	/**
	* Creates a new commerce address restriction with the primary key. Does not add the commerce address restriction to the database.
	*
	* @param commerceAddressRestrictionId the primary key for the new commerce address restriction
	* @return the new commerce address restriction
	*/
	public static com.liferay.commerce.model.CommerceAddressRestriction createCommerceAddressRestriction(
		long commerceAddressRestrictionId) {
		return getService()
				   .createCommerceAddressRestriction(commerceAddressRestrictionId);
	}

	/**
	* Deletes the commerce address restriction from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressRestriction the commerce address restriction
	* @return the commerce address restriction that was removed
	*/
	public static com.liferay.commerce.model.CommerceAddressRestriction deleteCommerceAddressRestriction(
		com.liferay.commerce.model.CommerceAddressRestriction commerceAddressRestriction) {
		return getService()
				   .deleteCommerceAddressRestriction(commerceAddressRestriction);
	}

	/**
	* Deletes the commerce address restriction with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction that was removed
	* @throws PortalException if a commerce address restriction with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceAddressRestriction deleteCommerceAddressRestriction(
		long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceAddressRestriction(commerceAddressRestrictionId);
	}

	public static void deleteCommerceAddressRestrictions(
		java.lang.String className, long classPK) {
		getService().deleteCommerceAddressRestrictions(className, classPK);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceAddressRestriction fetchCommerceAddressRestriction(
		long commerceAddressRestrictionId) {
		return getService()
				   .fetchCommerceAddressRestriction(commerceAddressRestrictionId);
	}

	public static com.liferay.commerce.model.CommerceAddressRestriction fetchCommerceAddressRestriction(
		java.lang.String className, long classPK, long commerceCountryId) {
		return getService()
				   .fetchCommerceAddressRestriction(className, classPK,
			commerceCountryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce address restriction with the primary key.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction
	* @throws PortalException if a commerce address restriction with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceAddressRestriction getCommerceAddressRestriction(
		long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceAddressRestriction(commerceAddressRestrictionId);
	}

	/**
	* Returns a range of all the commerce address restrictions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @return the range of commerce address restrictions
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceAddressRestriction> getCommerceAddressRestrictions(
		int start, int end) {
		return getService().getCommerceAddressRestrictions(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAddressRestriction> getCommerceAddressRestrictions(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAddressRestriction> orderByComparator) {
		return getService()
				   .getCommerceAddressRestrictions(className, classPK, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of commerce address restrictions.
	*
	* @return the number of commerce address restrictions
	*/
	public static int getCommerceAddressRestrictionsCount() {
		return getService().getCommerceAddressRestrictionsCount();
	}

	public static int getCommerceAddressRestrictionsCount(
		java.lang.String className, long classPK) {
		return getService()
				   .getCommerceAddressRestrictionsCount(className, classPK);
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

	public static boolean isCommerceAddressRestricted(
		java.lang.String className, long classPK, long commerceCountryId) {
		return getService()
				   .isCommerceAddressRestricted(className, classPK,
			commerceCountryId);
	}

	public static boolean isCommercePaymentMethodRestricted(
		long commercePaymentMethodId, long commerceCountryId) {
		return getService()
				   .isCommercePaymentMethodRestricted(commercePaymentMethodId,
			commerceCountryId);
	}

	public static boolean isCommerceShippingMethodRestricted(
		long commerceShippingMethodId, long commerceCountryId) {
		return getService()
				   .isCommerceShippingMethodRestricted(commerceShippingMethodId,
			commerceCountryId);
	}

	/**
	* Updates the commerce address restriction in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressRestriction the commerce address restriction
	* @return the commerce address restriction that was updated
	*/
	public static com.liferay.commerce.model.CommerceAddressRestriction updateCommerceAddressRestriction(
		com.liferay.commerce.model.CommerceAddressRestriction commerceAddressRestriction) {
		return getService()
				   .updateCommerceAddressRestriction(commerceAddressRestriction);
	}

	public static CommerceAddressRestrictionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAddressRestrictionLocalService, CommerceAddressRestrictionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceAddressRestrictionLocalService.class);
}