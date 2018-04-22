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
 * Provides the local service utility for CPInstance. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPInstanceLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPInstanceLocalService
 * @see com.liferay.commerce.product.service.base.CPInstanceLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPInstanceLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPInstanceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPInstanceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp instance to the database. Also notifies the appropriate model listeners.
	*
	* @param cpInstance the cp instance
	* @return the cp instance that was added
	*/
	public static com.liferay.commerce.product.model.CPInstance addCPInstance(
		com.liferay.commerce.product.model.CPInstance cpInstance) {
		return getService().addCPInstance(cpInstance);
	}

	public static com.liferay.commerce.product.model.CPInstance addCPInstance(
		long cpDefinitionId, java.lang.String sku, java.lang.String gtin,
		java.lang.String manufacturerPartNumber, boolean purchasable,
		java.lang.String ddmContent, boolean published, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPInstance(cpDefinitionId, sku, gtin,
			manufacturerPartNumber, purchasable, ddmContent, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance addCPInstance(
		long cpDefinitionId, java.lang.String sku, java.lang.String gtin,
		java.lang.String manufacturerPartNumber, boolean purchasable,
		java.lang.String ddmContent, double width, double height, double depth,
		double weight, java.math.BigDecimal cost, java.math.BigDecimal price,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPInstance(cpDefinitionId, sku, gtin,
			manufacturerPartNumber, purchasable, ddmContent, width, height,
			depth, weight, cost, price, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void buildCPInstances(long cpDefinitionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().buildCPInstances(cpDefinitionId, serviceContext);
	}

	public static void checkCPInstances()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkCPInstances();
	}

	public static void checkCPInstancesByDisplayDate(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkCPInstancesByDisplayDate(cpDefinitionId);
	}

	/**
	* Creates a new cp instance with the primary key. Does not add the cp instance to the database.
	*
	* @param CPInstanceId the primary key for the new cp instance
	* @return the new cp instance
	*/
	public static com.liferay.commerce.product.model.CPInstance createCPInstance(
		long CPInstanceId) {
		return getService().createCPInstance(CPInstanceId);
	}

	/**
	* Deletes the cp instance from the database. Also notifies the appropriate model listeners.
	*
	* @param cpInstance the cp instance
	* @return the cp instance that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CPInstance deleteCPInstance(
		com.liferay.commerce.product.model.CPInstance cpInstance)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPInstance(cpInstance);
	}

	/**
	* Deletes the cp instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPInstanceId the primary key of the cp instance
	* @return the cp instance that was removed
	* @throws PortalException if a cp instance with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPInstance deleteCPInstance(
		long CPInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPInstance(CPInstanceId);
	}

	public static void deleteCPInstances(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPInstances(cpDefinitionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPInstance fetchCPInstance(
		long CPInstanceId) {
		return getService().fetchCPInstance(CPInstanceId);
	}

	/**
	* Returns the cp instance matching the UUID and group.
	*
	* @param uuid the cp instance's UUID
	* @param groupId the primary key of the group
	* @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public static com.liferay.commerce.product.model.CPInstance fetchCPInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchCPInstanceByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPDefinitionInstances(
		long cpDefinitionId) {
		return getService().getCPDefinitionInstances(cpDefinitionId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPDefinitionInstances(
		long cpDefinitionId, int start, int end) {
		return getService().getCPDefinitionInstances(cpDefinitionId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPDefinitionInstances(
		long cpDefinitionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator) {
		return getService()
				   .getCPDefinitionInstances(cpDefinitionId, status, start,
			end, orderByComparator);
	}

	public static int getCPDefinitionInstancesCount(long cpDefinitionId,
		int status) {
		return getService().getCPDefinitionInstancesCount(cpDefinitionId, status);
	}

	/**
	* Returns the cp instance with the primary key.
	*
	* @param CPInstanceId the primary key of the cp instance
	* @return the cp instance
	* @throws PortalException if a cp instance with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPInstance getCPInstance(
		long CPInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstance(CPInstanceId);
	}

	public static com.liferay.commerce.product.model.CPInstance getCPInstance(
		long cpDefinitionId, java.lang.String sku)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstance(cpDefinitionId, sku);
	}

	/**
	* Returns the cp instance matching the UUID and group.
	*
	* @param uuid the cp instance's UUID
	* @param groupId the primary key of the group
	* @return the matching cp instance
	* @throws PortalException if a matching cp instance could not be found
	*/
	public static com.liferay.commerce.product.model.CPInstance getCPInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstanceByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cp instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of cp instances
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstances(
		int start, int end) {
		return getService().getCPInstances(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstances(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPInstances(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns all the cp instances matching the UUID and company.
	*
	* @param uuid the UUID of the cp instances
	* @param companyId the primary key of the company
	* @return the matching cp instances, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getCPInstancesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of cp instances matching the UUID and company.
	*
	* @param uuid the UUID of the cp instances
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp instances, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator) {
		return getService()
				   .getCPInstancesByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of cp instances.
	*
	* @return the number of cp instances
	*/
	public static int getCPInstancesCount() {
		return getService().getCPInstancesCount();
	}

	public static int getCPInstancesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstancesCount(groupId, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	public static java.lang.String[] getSKUs(long cpDefinitionId) {
		return getService().getSKUs(cpDefinitionId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPDefinitionInstances(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int status, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPDefinitionInstances(companyId, groupId,
			cpDefinitionId, keywords, status, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
		long companyId, long groupId, java.lang.String keywords, int status,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPInstances(companyId, groupId, keywords, status,
			start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCPInstances(searchContext);
	}

	/**
	* Updates the cp instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpInstance the cp instance
	* @return the cp instance that was updated
	*/
	public static com.liferay.commerce.product.model.CPInstance updateCPInstance(
		com.liferay.commerce.product.model.CPInstance cpInstance) {
		return getService().updateCPInstance(cpInstance);
	}

	public static com.liferay.commerce.product.model.CPInstance updateCPInstance(
		long cpInstanceId, java.lang.String sku, java.lang.String gtin,
		java.lang.String manufacturerPartNumber, boolean purchasable,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPInstance(cpInstanceId, sku, gtin,
			manufacturerPartNumber, purchasable, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updateCPInstance(
		long cpInstanceId, java.lang.String sku, java.lang.String gtin,
		java.lang.String manufacturerPartNumber, boolean purchasable,
		double width, double height, double depth, double weight,
		java.math.BigDecimal cost, java.math.BigDecimal price,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPInstance(cpInstanceId, sku, gtin,
			manufacturerPartNumber, purchasable, width, height, depth, weight,
			cost, price, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updatePricingInfo(
		long cpInstanceId, java.math.BigDecimal cost,
		java.math.BigDecimal price,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePricingInfo(cpInstanceId, cost, price, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updateShippingInfo(
		long cpInstanceId, double width, double height, double depth,
		double weight,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateShippingInfo(cpInstanceId, width, height, depth,
			weight, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updateStatus(
		long userId, long cpInstanceId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, cpInstanceId, status, serviceContext,
			workflowContext);
	}

	public static CPInstanceLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPInstanceLocalService, CPInstanceLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPInstanceLocalService.class);

		ServiceTracker<CPInstanceLocalService, CPInstanceLocalService> serviceTracker =
			new ServiceTracker<CPInstanceLocalService, CPInstanceLocalService>(bundle.getBundleContext(),
				CPInstanceLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}