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
 * Provides the local service utility for CProduct. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CProductLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CProductLocalService
 * @generated
 */
public class CProductLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CProductLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the c product to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CProductLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cProduct the c product
	 * @return the c product that was added
	 */
	public static com.liferay.commerce.product.model.CProduct addCProduct(
		com.liferay.commerce.product.model.CProduct cProduct) {

		return getService().addCProduct(cProduct);
	}

	public static com.liferay.commerce.product.model.CProduct addCProduct(
			long groupId, long userId, String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCProduct(
			groupId, userId, externalReferenceCode, serviceContext);
	}

	/**
	 * Creates a new c product with the primary key. Does not add the c product to the database.
	 *
	 * @param CProductId the primary key for the new c product
	 * @return the new c product
	 */
	public static com.liferay.commerce.product.model.CProduct createCProduct(
		long CProductId) {

		return getService().createCProduct(CProductId);
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
	 * Deletes the c product from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CProductLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cProduct the c product
	 * @return the c product that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.product.model.CProduct deleteCProduct(
			com.liferay.commerce.product.model.CProduct cProduct)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCProduct(cProduct);
	}

	/**
	 * Deletes the c product with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CProductLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CProductId the primary key of the c product
	 * @return the c product that was removed
	 * @throws PortalException if a c product with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CProduct deleteCProduct(
			long CProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCProduct(CProductId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CProductModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CProductModelImpl</code>.
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

	public static com.liferay.commerce.product.model.CProduct fetchCProduct(
		long CProductId) {

		return getService().fetchCProduct(CProductId);
	}

	/**
	 * Returns the c product with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the c product's external reference code
	 * @return the matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public static com.liferay.commerce.product.model.CProduct
		fetchCProductByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCProductByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the c product matching the UUID and group.
	 *
	 * @param uuid the c product's UUID
	 * @param groupId the primary key of the group
	 * @return the matching c product, or <code>null</code> if a matching c product could not be found
	 */
	public static com.liferay.commerce.product.model.CProduct
		fetchCProductByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchCProductByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the c product with the primary key.
	 *
	 * @param CProductId the primary key of the c product
	 * @return the c product
	 * @throws PortalException if a c product with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CProduct getCProduct(
			long CProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCProduct(CProductId);
	}

	public static com.liferay.commerce.product.model.CProduct
			getCProductByCPInstanceUuid(String cpInstanceUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCProductByCPInstanceUuid(cpInstanceUuid);
	}

	/**
	 * Returns the c product matching the UUID and group.
	 *
	 * @param uuid the c product's UUID
	 * @param groupId the primary key of the group
	 * @return the matching c product
	 * @throws PortalException if a matching c product could not be found
	 */
	public static com.liferay.commerce.product.model.CProduct
			getCProductByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCProductByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the c products.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CProductModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @return the range of c products
	 */
	public static java.util.List<com.liferay.commerce.product.model.CProduct>
		getCProducts(int start, int end) {

		return getService().getCProducts(start, end);
	}

	/**
	 * Returns all the c products matching the UUID and company.
	 *
	 * @param uuid the UUID of the c products
	 * @param companyId the primary key of the company
	 * @return the matching c products, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.commerce.product.model.CProduct>
		getCProductsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getCProductsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of c products matching the UUID and company.
	 *
	 * @param uuid the UUID of the c products
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of c products
	 * @param end the upper bound of the range of c products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching c products, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.commerce.product.model.CProduct>
		getCProductsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.product.model.CProduct>
					orderByComparator) {

		return getService().getCProductsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of c products.
	 *
	 * @return the number of c products
	 */
	public static int getCProductsCount() {
		return getService().getCProductsCount();
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

	public static int increment(long cProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().increment(cProductId);
	}

	/**
	 * Updates the c product in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CProductLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cProduct the c product
	 * @return the c product that was updated
	 */
	public static com.liferay.commerce.product.model.CProduct updateCProduct(
		com.liferay.commerce.product.model.CProduct cProduct) {

		return getService().updateCProduct(cProduct);
	}

	public static com.liferay.commerce.product.model.CProduct
			updateCProductExternalReferenceCode(
				long cProductId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCProductExternalReferenceCode(
			cProductId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CProduct
			updatePublishedCPDefinitionId(
				long cProductId, long publishedCPDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updatePublishedCPDefinitionId(
			cProductId, publishedCPDefinitionId);
	}

	public static CProductLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CProductLocalService, CProductLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CProductLocalService.class);

		ServiceTracker<CProductLocalService, CProductLocalService>
			serviceTracker =
				new ServiceTracker<CProductLocalService, CProductLocalService>(
					bundle.getBundleContext(), CProductLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}