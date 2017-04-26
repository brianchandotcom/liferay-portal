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
 * Provides the local service utility for CommerceProductInstance. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductInstanceLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceProductInstanceLocalService
 * @see com.liferay.commerce.product.service.base.CommerceProductInstanceLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductInstanceLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductInstanceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductInstanceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce product instance to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductInstance the commerce product instance
	* @return the commerce product instance that was added
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance addCommerceProductInstance(
		com.liferay.commerce.product.model.CommerceProductInstance commerceProductInstance) {
		return getService().addCommerceProductInstance(commerceProductInstance);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance addCommerceProductInstance(
		long commerceProductDefinitionId, java.lang.String sku,
		java.lang.String ddmContent, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductInstance(commerceProductDefinitionId,
			sku, ddmContent, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	/**
	* Creates a new commerce product instance with the primary key. Does not add the commerce product instance to the database.
	*
	* @param commerceProductInstanceId the primary key for the new commerce product instance
	* @return the new commerce product instance
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance createCommerceProductInstance(
		long commerceProductInstanceId) {
		return getService()
				   .createCommerceProductInstance(commerceProductInstanceId);
	}

	/**
	* Deletes the commerce product instance from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductInstance the commerce product instance
	* @return the commerce product instance that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance deleteCommerceProductInstance(
		com.liferay.commerce.product.model.CommerceProductInstance commerceProductInstance)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductInstance(commerceProductInstance);
	}

	/**
	* Deletes the commerce product instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance that was removed
	* @throws PortalException if a commerce product instance with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance deleteCommerceProductInstance(
		long commerceProductInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductInstance(commerceProductInstanceId);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance fetchCommerceProductInstance(
		long commerceProductInstanceId) {
		return getService()
				   .fetchCommerceProductInstance(commerceProductInstanceId);
	}

	/**
	* Returns the commerce product instance matching the UUID and group.
	*
	* @param uuid the commerce product instance's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance fetchCommerceProductInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCommerceProductInstanceByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the commerce product instance with the primary key.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance
	* @throws PortalException if a commerce product instance with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance getCommerceProductInstance(
		long commerceProductInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceProductInstance(commerceProductInstanceId);
	}

	/**
	* Returns the commerce product instance matching the UUID and group.
	*
	* @param uuid the commerce product instance's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product instance
	* @throws PortalException if a matching commerce product instance could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance getCommerceProductInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductInstanceByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the commerce product instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductInstance the commerce product instance
	* @return the commerce product instance that was updated
	*/
	public static com.liferay.commerce.product.model.CommerceProductInstance updateCommerceProductInstance(
		com.liferay.commerce.product.model.CommerceProductInstance commerceProductInstance) {
		return getService()
				   .updateCommerceProductInstance(commerceProductInstance);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance updateCommerceProductInstance(
		long commerceProductInstanceId, java.lang.String sku,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductInstance(commerceProductInstanceId,
			sku, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductInstance updateStatus(
		long userId, long commerceProductInstanceId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, commerceProductInstanceId, status,
			serviceContext, workflowContext);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
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
	* Returns the number of commerce product instances.
	*
	* @return the number of commerce product instances
	*/
	public static int getCommerceProductInstancesCount() {
		return getService().getCommerceProductInstancesCount();
	}

	public static int getCommerceProductInstancesCount(
		long commerceProductDefinitionId) {
		return getService()
				   .getCommerceProductInstancesCount(commerceProductDefinitionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce product instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of commerce product instances
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstances(
		int start, int end) {
		return getService().getCommerceProductInstances(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end) {
		return getService()
				   .getCommerceProductInstances(commerceProductDefinitionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductInstance> orderByComparator) {
		return getService()
				   .getCommerceProductInstances(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce product instances matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product instances
	* @param companyId the primary key of the company
	* @return the matching commerce product instances, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommerceProductInstancesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce product instances matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product instances
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce product instances, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> getCommerceProductInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductInstance> orderByComparator) {
		return getService()
				   .getCommerceProductInstancesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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

	public static void autoGeneratePommerceProductInstances(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.autoGeneratePommerceProductInstances(commerceProductDefinitionId);
	}

	public static void deleteCommerceProductInstances(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceProductInstances(commerceProductDefinitionId);
	}

	public static CommerceProductInstanceLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductInstanceLocalService, CommerceProductInstanceLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductInstanceLocalService.class);
}