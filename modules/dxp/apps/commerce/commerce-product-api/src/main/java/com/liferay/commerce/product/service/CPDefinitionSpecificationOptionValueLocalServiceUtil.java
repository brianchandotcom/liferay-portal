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
 * Provides the local service utility for CPDefinitionSpecificationOptionValue. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueLocalService
 * @see com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition specification option value to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	* @return the cp definition specification option value that was added
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		return getService()
				   .addCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValue);
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId,
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> valueMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionSpecificationOptionValue(cpDefinitionId,
			cpSpecificationOptionId, cpOptionCategoryId, valueMap, priority,
			serviceContext);
	}

	/**
	* Creates a new cp definition specification option value with the primary key. Does not add the cp definition specification option value to the database.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key for the new cp definition specification option value
	* @return the new cp definition specification option value
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue createCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId) {
		return getService()
				   .createCPDefinitionSpecificationOptionValue(CPDefinitionSpecificationOptionValueId);
	}

	/**
	* Deletes the cp definition specification option value from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	* @return the cp definition specification option value that was removed
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue deleteCPDefinitionSpecificationOptionValue(
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		return getService()
				   .deleteCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValue);
	}

	/**
	* Deletes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value that was removed
	* @throws PortalException if a cp definition specification option value with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue deleteCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionSpecificationOptionValue(CPDefinitionSpecificationOptionValueId);
	}

	public static void deleteCPDefinitionSpecificationOptionValues(
		long cpDefinitionId) {
		getService().deleteCPDefinitionSpecificationOptionValues(cpDefinitionId);
	}

	public static void deleteCPSpecificationOptionDefinitionValues(
		long cpSpecificationOptionId) {
		getService()
			.deleteCPSpecificationOptionDefinitionValues(cpSpecificationOptionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId) {
		return getService()
				   .fetchCPDefinitionSpecificationOptionValue(CPDefinitionSpecificationOptionValueId);
	}

	/**
	* Returns the cp definition specification option value matching the UUID and group.
	*
	* @param uuid the cp definition specification option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValueByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCPDefinitionSpecificationOptionValueByUuidAndGroupId(uuid,
			groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition specification option value with the primary key.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value
	* @throws PortalException if a cp definition specification option value with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionSpecificationOptionValue(CPDefinitionSpecificationOptionValueId);
	}

	/**
	* Returns the cp definition specification option value matching the UUID and group.
	*
	* @param uuid the cp definition specification option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition specification option value
	* @throws PortalException if a matching cp definition specification option value could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValueByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionSpecificationOptionValueByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp definition specification option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of cp definition specification option values
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		int start, int end) {
		return getService().getCPDefinitionSpecificationOptionValues(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId) {
		return getService()
				   .getCPDefinitionSpecificationOptionValues(cpDefinitionId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId, long cpOptionCategoryId) {
		return getService()
				   .getCPDefinitionSpecificationOptionValues(cpDefinitionId,
			cpOptionCategoryId);
	}

	/**
	* Returns all the cp definition specification option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition specification option values
	* @param companyId the primary key of the company
	* @return the matching cp definition specification option values, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition specification option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition specification option values
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition specification option values, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getService()
				   .getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition specification option values.
	*
	* @return the number of cp definition specification option values
	*/
	public static int getCPDefinitionSpecificationOptionValuesCount() {
		return getService().getCPDefinitionSpecificationOptionValuesCount();
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

	/**
	* Updates the cp definition specification option value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	* @return the cp definition specification option value that was updated
	*/
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		return getService()
				   .updateCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValue);
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> valueMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId,
			cpOptionCategoryId, valueMap, priority, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue updateCPOptionCategoryId(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPOptionCategoryId(cpDefinitionSpecificationOptionValueId,
			cpOptionCategoryId);
	}

	public static CPDefinitionSpecificationOptionValueLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionSpecificationOptionValueLocalService, CPDefinitionSpecificationOptionValueLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CPDefinitionSpecificationOptionValueLocalService.class);
}