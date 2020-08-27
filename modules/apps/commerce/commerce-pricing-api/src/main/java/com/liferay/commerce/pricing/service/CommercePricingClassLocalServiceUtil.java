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

package com.liferay.commerce.pricing.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommercePricingClass. This utility wraps
 * <code>com.liferay.commerce.pricing.service.impl.CommercePricingClassLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassLocalService
 * @generated
 */
public class CommercePricingClassLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.pricing.service.impl.CommercePricingClassLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce pricing class to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePricingClass the commerce pricing class
	 * @return the commerce pricing class that was added
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
		addCommercePricingClass(
			com.liferay.commerce.pricing.model.CommercePricingClass
				commercePricingClass) {

		return getService().addCommercePricingClass(commercePricingClass);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			addCommercePricingClass(
				long userId, java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePricingClass(
			userId, titleMap, descriptionMap, serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			addCommercePricingClass(
				long userId, java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePricingClass(
			userId, titleMap, descriptionMap, externalReferenceCode,
			serviceContext);
	}

	/**
	 * Creates a new commerce pricing class with the primary key. Does not add the commerce pricing class to the database.
	 *
	 * @param commercePricingClassId the primary key for the new commerce pricing class
	 * @return the new commerce pricing class
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
		createCommercePricingClass(long commercePricingClassId) {

		return getService().createCommercePricingClass(commercePricingClassId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce pricing class from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePricingClass the commerce pricing class
	 * @return the commerce pricing class that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
			deleteCommercePricingClass(
				com.liferay.commerce.pricing.model.CommercePricingClass
					commercePricingClass)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePricingClass(commercePricingClass);
	}

	/**
	 * Deletes the commerce pricing class with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePricingClassId the primary key of the commerce pricing class
	 * @return the commerce pricing class that was removed
	 * @throws PortalException if a commerce pricing class with the primary key could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
			deleteCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePricingClass(commercePricingClassId);
	}

	public static void deleteCommercePricingClasses(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePricingClasses(companyId);
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

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePricingClassModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePricingClassModelImpl</code>.
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

	public static com.liferay.commerce.pricing.model.CommercePricingClass
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
		fetchCommercePricingClass(long commercePricingClassId) {

		return getService().fetchCommercePricingClass(commercePricingClassId);
	}

	/**
	 * Returns the commerce pricing class with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce pricing class's external reference code
	 * @return the matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
		fetchCommercePricingClassByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommercePricingClassByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce pricing class with the matching UUID and company.
	 *
	 * @param uuid the commerce pricing class's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce pricing class, or <code>null</code> if a matching commerce pricing class could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
		fetchCommercePricingClassByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCommercePricingClassByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce pricing class with the primary key.
	 *
	 * @param commercePricingClassId the primary key of the commerce pricing class
	 * @return the commerce pricing class
	 * @throws PortalException if a commerce pricing class with the primary key could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
			getCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePricingClass(commercePricingClassId);
	}

	public static long[] getCommercePricingClassByCPDefinition(
		long cpDefinitionId) {

		return getService().getCommercePricingClassByCPDefinition(
			cpDefinitionId);
	}

	/**
	 * Returns the commerce pricing class with the matching UUID and company.
	 *
	 * @param uuid the commerce pricing class's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce pricing class
	 * @throws PortalException if a matching commerce pricing class could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
			getCommercePricingClassByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePricingClassByUuidAndCompanyId(
			uuid, companyId);
	}

	public static int getCommercePricingClassCountByCPDefinitionId(
		long cpDefinitionId, String title) {

		return getService().getCommercePricingClassCountByCPDefinitionId(
			cpDefinitionId, title);
	}

	/**
	 * Returns a range of all the commerce pricing classes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePricingClassModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing classes
	 * @param end the upper bound of the range of commerce pricing classes (not inclusive)
	 * @return the range of commerce pricing classes
	 */
	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePricingClass>
			getCommercePricingClasses(int start, int end) {

		return getService().getCommercePricingClasses(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePricingClass>
			getCommercePricingClasses(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.pricing.model.CommercePricingClass>
						orderByComparator) {

		return getService().getCommercePricingClasses(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce pricing classes.
	 *
	 * @return the number of commerce pricing classes
	 */
	public static int getCommercePricingClassesCount() {
		return getService().getCommercePricingClassesCount();
	}

	public static int getCommercePricingClassesCount(long companyId) {
		return getService().getCommercePricingClassesCount(companyId);
	}

	public static int getCommercePricingClassesCount(
		long cpDefinitionId, String title) {

		return getService().getCommercePricingClassesCount(
			cpDefinitionId, title);
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.pricing.model.CommercePricingClass>
				searchCommercePricingClasses(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePricingClasses(
			companyId, keywords, start, end, sort);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePricingClass>
			searchCommercePricingClassesByCPDefinitionId(
				long cpDefinitionId, String title, int start, int end) {

		return getService().searchCommercePricingClassesByCPDefinitionId(
			cpDefinitionId, title, start, end);
	}

	/**
	 * Updates the commerce pricing class in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePricingClassLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePricingClass the commerce pricing class
	 * @return the commerce pricing class that was updated
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
		updateCommercePricingClass(
			com.liferay.commerce.pricing.model.CommercePricingClass
				commercePricingClass) {

		return getService().updateCommercePricingClass(commercePricingClass);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			updateCommercePricingClass(
				long commercePricingClassId, long userId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePricingClass(
			commercePricingClassId, userId, titleMap, descriptionMap,
			serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			updateCommercePricingClassExternalReferenceCode(
				long commercePricingClassId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePricingClassExternalReferenceCode(
			commercePricingClassId, externalReferenceCode);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			upsertCommercePricingClass(
				long commercePricingClassId, long userId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePricingClass(
			commercePricingClassId, userId, titleMap, descriptionMap,
			externalReferenceCode, serviceContext);
	}

	public static CommercePricingClassLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePricingClassLocalService, CommercePricingClassLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePricingClassLocalService.class);

		ServiceTracker
			<CommercePricingClassLocalService, CommercePricingClassLocalService>
				serviceTracker =
					new ServiceTracker
						<CommercePricingClassLocalService,
						 CommercePricingClassLocalService>(
							 bundle.getBundleContext(),
							 CommercePricingClassLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}