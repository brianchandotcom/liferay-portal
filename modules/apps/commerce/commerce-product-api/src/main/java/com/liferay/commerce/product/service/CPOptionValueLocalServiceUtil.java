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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPOptionValue. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPOptionValueLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPOptionValueLocalService
 * @generated
 */
public class CPOptionValueLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPOptionValueLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cp option value to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpOptionValue the cp option value
	 * @return the cp option value that was added
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
		addCPOptionValue(
			com.liferay.commerce.product.model.CPOptionValue cpOptionValue) {

		return getService().addCPOptionValue(cpOptionValue);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			addCPOptionValue(
				long cpOptionId,
				java.util.Map<java.util.Locale, String> nameMap,
				double priority, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPOptionValue(
			cpOptionId, nameMap, priority, key, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			addCPOptionValue(
				long cpOptionId,
				java.util.Map<java.util.Locale, String> nameMap,
				double priority, String key, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPOptionValue(
			cpOptionId, nameMap, priority, key, externalReferenceCode,
			serviceContext);
	}

	/**
	 * Creates a new cp option value with the primary key. Does not add the cp option value to the database.
	 *
	 * @param CPOptionValueId the primary key for the new cp option value
	 * @return the new cp option value
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
		createCPOptionValue(long CPOptionValueId) {

		return getService().createCPOptionValue(CPOptionValueId);
	}

	/**
	 * Deletes the cp option value from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpOptionValue the cp option value
	 * @return the cp option value that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
			deleteCPOptionValue(
				com.liferay.commerce.product.model.CPOptionValue cpOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCPOptionValue(cpOptionValue);
	}

	/**
	 * Deletes the cp option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPOptionValueId the primary key of the cp option value
	 * @return the cp option value that was removed
	 * @throws PortalException if a cp option value with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
			deleteCPOptionValue(long CPOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCPOptionValue(CPOptionValueId);
	}

	public static void deleteCPOptionValues(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPOptionValues(cpOptionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPOptionValueModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPOptionValueModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.commerce.product.model.CPOptionValue
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
		fetchCPOptionValue(long CPOptionValueId) {

		return getService().fetchCPOptionValue(CPOptionValueId);
	}

	/**
	 * Returns the cp option value with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the cp option value's external reference code
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
		fetchCPOptionValueByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCPOptionValueByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the cp option value with the matching UUID and company.
	 *
	 * @param uuid the cp option value's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
		fetchCPOptionValueByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchCPOptionValueByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cp option value with the primary key.
	 *
	 * @param CPOptionValueId the primary key of the cp option value
	 * @return the cp option value
	 * @throws PortalException if a cp option value with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
			getCPOptionValue(long CPOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOptionValue(CPOptionValueId);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			getCPOptionValue(long cpOptionId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOptionValue(cpOptionId, key);
	}

	/**
	 * Returns the cp option value with the matching UUID and company.
	 *
	 * @param uuid the cp option value's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp option value
	 * @throws PortalException if a matching cp option value could not be found
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
			getCPOptionValueByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPOptionValueByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of cp option values
	 */
	public static java.util.List
		<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
			int start, int end) {

		return getService().getCPOptionValues(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end) {

		return getService().getCPOptionValues(cpOptionId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.product.model.CPOptionValue>
					orderByComparator) {

		return getService().getCPOptionValues(
			cpOptionId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of cp option values.
	 *
	 * @return the number of cp option values
	 */
	public static int getCPOptionValuesCount() {
		return getService().getCPOptionValuesCount();
	}

	public static int getCPOptionValuesCount(long cpOptionId) {
		return getService().getCPOptionValuesCount(cpOptionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {

		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPOptionValue> searchCPOptionValues(
				long companyId, long groupId, long cpOptionId, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCPOptionValues(
			companyId, groupId, cpOptionId, keywords, start, end, sort);
	}

	/**
	 * Updates the cp option value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPOptionValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpOptionValue the cp option value
	 * @return the cp option value that was updated
	 */
	public static com.liferay.commerce.product.model.CPOptionValue
		updateCPOptionValue(
			com.liferay.commerce.product.model.CPOptionValue cpOptionValue) {

		return getService().updateCPOptionValue(cpOptionValue);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			updateCPOptionValue(
				long cpOptionValueId,
				java.util.Map<java.util.Locale, String> nameMap,
				double priority, String key,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPOptionValue(
			cpOptionValueId, nameMap, priority, key, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPOptionValue
			upsertCPOptionValue(
				long cpOptionId,
				java.util.Map<java.util.Locale, String> nameMap,
				double priority, String key, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCPOptionValue(
			cpOptionId, nameMap, priority, key, externalReferenceCode,
			serviceContext);
	}

	public static CPOptionValueLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPOptionValueLocalService, CPOptionValueLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CPOptionValueLocalService.class);

		ServiceTracker<CPOptionValueLocalService, CPOptionValueLocalService>
			serviceTracker =
				new ServiceTracker
					<CPOptionValueLocalService, CPOptionValueLocalService>(
						bundle.getBundleContext(),
						CPOptionValueLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}