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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPTaxCategory. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPTaxCategoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPTaxCategoryLocalService
 * @see com.liferay.commerce.product.service.base.CPTaxCategoryLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPTaxCategoryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPTaxCategoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPTaxCategoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp tax category to the database. Also notifies the appropriate model listeners.
	*
	* @param cpTaxCategory the cp tax category
	* @return the cp tax category that was added
	*/
	public static com.liferay.commerce.product.model.CPTaxCategory addCPTaxCategory(
		com.liferay.commerce.product.model.CPTaxCategory cpTaxCategory) {
		return getService().addCPTaxCategory(cpTaxCategory);
	}

	public static com.liferay.commerce.product.model.CPTaxCategory addCPTaxCategory(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPTaxCategory(nameMap, descriptionMap, serviceContext);
	}

	/**
	* Creates a new cp tax category with the primary key. Does not add the cp tax category to the database.
	*
	* @param CPTaxCategoryId the primary key for the new cp tax category
	* @return the new cp tax category
	*/
	public static com.liferay.commerce.product.model.CPTaxCategory createCPTaxCategory(
		long CPTaxCategoryId) {
		return getService().createCPTaxCategory(CPTaxCategoryId);
	}

	public static void deleteCPTaxCategories(long groupId) {
		getService().deleteCPTaxCategories(groupId);
	}

	/**
	* Deletes the cp tax category from the database. Also notifies the appropriate model listeners.
	*
	* @param cpTaxCategory the cp tax category
	* @return the cp tax category that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CPTaxCategory deleteCPTaxCategory(
		com.liferay.commerce.product.model.CPTaxCategory cpTaxCategory)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPTaxCategory(cpTaxCategory);
	}

	/**
	* Deletes the cp tax category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPTaxCategoryId the primary key of the cp tax category
	* @return the cp tax category that was removed
	* @throws PortalException if a cp tax category with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPTaxCategory deleteCPTaxCategory(
		long CPTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPTaxCategory(CPTaxCategoryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPTaxCategory fetchCPTaxCategory(
		long CPTaxCategoryId) {
		return getService().fetchCPTaxCategory(CPTaxCategoryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the cp tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp tax categories
	* @param end the upper bound of the range of cp tax categories (not inclusive)
	* @return the range of cp tax categories
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		int start, int end) {
		return getService().getCPTaxCategories(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId) {
		return getService().getCPTaxCategories(groupId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPTaxCategory> orderByComparator) {
		return getService()
				   .getCPTaxCategories(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp tax categories.
	*
	* @return the number of cp tax categories
	*/
	public static int getCPTaxCategoriesCount() {
		return getService().getCPTaxCategoriesCount();
	}

	public static int getCPTaxCategoriesCount(long groupId) {
		return getService().getCPTaxCategoriesCount(groupId);
	}

	/**
	* Returns the cp tax category with the primary key.
	*
	* @param CPTaxCategoryId the primary key of the cp tax category
	* @return the cp tax category
	* @throws PortalException if a cp tax category with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory(
		long CPTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPTaxCategory(CPTaxCategoryId);
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
	* Updates the cp tax category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpTaxCategory the cp tax category
	* @return the cp tax category that was updated
	*/
	public static com.liferay.commerce.product.model.CPTaxCategory updateCPTaxCategory(
		com.liferay.commerce.product.model.CPTaxCategory cpTaxCategory) {
		return getService().updateCPTaxCategory(cpTaxCategory);
	}

	public static com.liferay.commerce.product.model.CPTaxCategory updateCPTaxCategory(
		long cpTaxCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPTaxCategory(cpTaxCategoryId, nameMap, descriptionMap);
	}

	public static CPTaxCategoryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPTaxCategoryLocalService, CPTaxCategoryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPTaxCategoryLocalService.class);

		ServiceTracker<CPTaxCategoryLocalService, CPTaxCategoryLocalService> serviceTracker =
			new ServiceTracker<CPTaxCategoryLocalService, CPTaxCategoryLocalService>(bundle.getBundleContext(),
				CPTaxCategoryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}