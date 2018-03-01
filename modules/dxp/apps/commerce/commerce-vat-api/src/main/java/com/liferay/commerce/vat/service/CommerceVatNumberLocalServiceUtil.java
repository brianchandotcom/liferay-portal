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

package com.liferay.commerce.vat.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceVatNumber. This utility wraps
 * {@link com.liferay.commerce.vat.service.impl.CommerceVatNumberLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberLocalService
 * @see com.liferay.commerce.vat.service.base.CommerceVatNumberLocalServiceBaseImpl
 * @see com.liferay.commerce.vat.service.impl.CommerceVatNumberLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceVatNumberLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.vat.service.impl.CommerceVatNumberLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce vat number to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumber the commerce vat number
	* @return the commerce vat number that was added
	*/
	public static com.liferay.commerce.vat.model.CommerceVatNumber addCommerceVatNumber(
		com.liferay.commerce.vat.model.CommerceVatNumber commerceVatNumber) {
		return getService().addCommerceVatNumber(commerceVatNumber);
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber addCommerceVatNumber(
		java.lang.String className, long classPK, java.lang.String vatNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceVatNumber(className, classPK, vatNumber,
			serviceContext);
	}

	/**
	* Creates a new commerce vat number with the primary key. Does not add the commerce vat number to the database.
	*
	* @param commerceVatNumberId the primary key for the new commerce vat number
	* @return the new commerce vat number
	*/
	public static com.liferay.commerce.vat.model.CommerceVatNumber createCommerceVatNumber(
		long commerceVatNumberId) {
		return getService().createCommerceVatNumber(commerceVatNumberId);
	}

	/**
	* Deletes the commerce vat number from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumber the commerce vat number
	* @return the commerce vat number that was removed
	*/
	public static com.liferay.commerce.vat.model.CommerceVatNumber deleteCommerceVatNumber(
		com.liferay.commerce.vat.model.CommerceVatNumber commerceVatNumber) {
		return getService().deleteCommerceVatNumber(commerceVatNumber);
	}

	/**
	* Deletes the commerce vat number with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number that was removed
	* @throws PortalException if a commerce vat number with the primary key could not be found
	*/
	public static com.liferay.commerce.vat.model.CommerceVatNumber deleteCommerceVatNumber(
		long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceVatNumber(commerceVatNumberId);
	}

	public static void deleteCommerceVatNumbers(java.lang.String className,
		long classPK) {
		getService().deleteCommerceVatNumbers(className, classPK);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		long commerceVatNumberId) {
		return getService().fetchCommerceVatNumber(commerceVatNumberId);
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		long groupId, java.lang.String className, long classPK) {
		return getService().fetchCommerceVatNumber(groupId, className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce vat number with the primary key.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number
	* @throws PortalException if a commerce vat number with the primary key could not be found
	*/
	public static com.liferay.commerce.vat.model.CommerceVatNumber getCommerceVatNumber(
		long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceVatNumber(commerceVatNumberId);
	}

	/**
	* Returns a range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of commerce vat numbers
	*/
	public static java.util.List<com.liferay.commerce.vat.model.CommerceVatNumber> getCommerceVatNumbers(
		int start, int end) {
		return getService().getCommerceVatNumbers(start, end);
	}

	public static java.util.List<com.liferay.commerce.vat.model.CommerceVatNumber> getCommerceVatNumbers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.vat.model.CommerceVatNumber> orderByComparator) {
		return getService()
				   .getCommerceVatNumbers(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce vat numbers.
	*
	* @return the number of commerce vat numbers
	*/
	public static int getCommerceVatNumbersCount() {
		return getService().getCommerceVatNumbersCount();
	}

	public static int getCommerceVatNumbersCount(long groupId) {
		return getService().getCommerceVatNumbersCount(groupId);
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.vat.model.CommerceVatNumber> searchCommerceVatNumbers(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommerceVatNumbers(companyId, groupId, keywords,
			start, end, sort);
	}

	/**
	* Updates the commerce vat number in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumber the commerce vat number
	* @return the commerce vat number that was updated
	*/
	public static com.liferay.commerce.vat.model.CommerceVatNumber updateCommerceVatNumber(
		com.liferay.commerce.vat.model.CommerceVatNumber commerceVatNumber) {
		return getService().updateCommerceVatNumber(commerceVatNumber);
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber updateCommerceVatNumber(
		long commerceVatNumberId, java.lang.String vatNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceVatNumber(commerceVatNumberId, vatNumber);
	}

	public static CommerceVatNumberLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceVatNumberLocalService, CommerceVatNumberLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceVatNumberLocalService.class);
}