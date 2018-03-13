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
 * Provides the local service utility for CommerceTaxCategory. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTaxCategoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryLocalService
 * @see com.liferay.commerce.service.base.CommerceTaxCategoryLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxCategoryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxCategoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce tax category to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategory the commerce tax category
	* @return the commerce tax category that was added
	*/
	public static com.liferay.commerce.model.CommerceTaxCategory addCommerceTaxCategory(
		com.liferay.commerce.model.CommerceTaxCategory commerceTaxCategory) {
		return getService().addCommerceTaxCategory(commerceTaxCategory);
	}

	public static com.liferay.commerce.model.CommerceTaxCategory addCommerceTaxCategory(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxCategory(nameMap, descriptionMap,
			serviceContext);
	}

	/**
	* Creates a new commerce tax category with the primary key. Does not add the commerce tax category to the database.
	*
	* @param commerceTaxCategoryId the primary key for the new commerce tax category
	* @return the new commerce tax category
	*/
	public static com.liferay.commerce.model.CommerceTaxCategory createCommerceTaxCategory(
		long commerceTaxCategoryId) {
		return getService().createCommerceTaxCategory(commerceTaxCategoryId);
	}

	public static void deleteCommerceTaxCategories(long groupId) {
		getService().deleteCommerceTaxCategories(groupId);
	}

	/**
	* Deletes the commerce tax category from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategory the commerce tax category
	* @return the commerce tax category that was removed
	*/
	public static com.liferay.commerce.model.CommerceTaxCategory deleteCommerceTaxCategory(
		com.liferay.commerce.model.CommerceTaxCategory commerceTaxCategory) {
		return getService().deleteCommerceTaxCategory(commerceTaxCategory);
	}

	/**
	* Deletes the commerce tax category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category that was removed
	* @throws PortalException if a commerce tax category with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceTaxCategory deleteCommerceTaxCategory(
		long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceTaxCategory(commerceTaxCategoryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceTaxCategory fetchCommerceTaxCategory(
		long commerceTaxCategoryId) {
		return getService().fetchCommerceTaxCategory(commerceTaxCategoryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @return the range of commerce tax categories
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		int start, int end) {
		return getService().getCommerceTaxCategories(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		long groupId) {
		return getService().getCommerceTaxCategories(groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTaxCategory> orderByComparator) {
		return getService()
				   .getCommerceTaxCategories(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of commerce tax categories.
	*
	* @return the number of commerce tax categories
	*/
	public static int getCommerceTaxCategoriesCount() {
		return getService().getCommerceTaxCategoriesCount();
	}

	public static int getCommerceTaxCategoriesCount(long groupId) {
		return getService().getCommerceTaxCategoriesCount(groupId);
	}

	/**
	* Returns the commerce tax category with the primary key.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category
	* @throws PortalException if a commerce tax category with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceTaxCategory getCommerceTaxCategory(
		long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceTaxCategory(commerceTaxCategoryId);
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
	* Updates the commerce tax category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategory the commerce tax category
	* @return the commerce tax category that was updated
	*/
	public static com.liferay.commerce.model.CommerceTaxCategory updateCommerceTaxCategory(
		com.liferay.commerce.model.CommerceTaxCategory commerceTaxCategory) {
		return getService().updateCommerceTaxCategory(commerceTaxCategory);
	}

	public static com.liferay.commerce.model.CommerceTaxCategory updateCommerceTaxCategory(
		long commerceTaxCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxCategory(commerceTaxCategoryId, nameMap,
			descriptionMap);
	}

	public static CommerceTaxCategoryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxCategoryLocalService, CommerceTaxCategoryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceTaxCategoryLocalService.class);
}