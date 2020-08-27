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

package com.liferay.commerce.price.list.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommercePriceListDiscountRel. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListDiscountRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListDiscountRelLocalService
 * @generated
 */
public class CommercePriceListDiscountRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListDiscountRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce price list discount rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListDiscountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceListDiscountRel the commerce price list discount rel
	 * @return the commerce price list discount rel that was added
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
			addCommercePriceListDiscountRel(
				com.liferay.commerce.price.list.model.
					CommercePriceListDiscountRel commercePriceListDiscountRel) {

		return getService().addCommercePriceListDiscountRel(
			commercePriceListDiscountRel);
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				addCommercePriceListDiscountRel(
					long commercePriceListId, long commerceDiscountId,
					int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceListDiscountRel(
			commercePriceListId, commerceDiscountId, order, serviceContext);
	}

	/**
	 * Creates a new commerce price list discount rel with the primary key. Does not add the commerce price list discount rel to the database.
	 *
	 * @param commercePriceListDiscountRelId the primary key for the new commerce price list discount rel
	 * @return the new commerce price list discount rel
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
			createCommercePriceListDiscountRel(
				long commercePriceListDiscountRelId) {

		return getService().createCommercePriceListDiscountRel(
			commercePriceListDiscountRelId);
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
	 * Deletes the commerce price list discount rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListDiscountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceListDiscountRel the commerce price list discount rel
	 * @return the commerce price list discount rel that was removed
	 * @throws PortalException
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				deleteCommercePriceListDiscountRel(
					com.liferay.commerce.price.list.model.
						CommercePriceListDiscountRel
							commercePriceListDiscountRel)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePriceListDiscountRel(
			commercePriceListDiscountRel);
	}

	/**
	 * Deletes the commerce price list discount rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListDiscountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel that was removed
	 * @throws PortalException if a commerce price list discount rel with the primary key could not be found
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				deleteCommercePriceListDiscountRel(
					long commercePriceListDiscountRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePriceListDiscountRel(
			commercePriceListDiscountRelId);
	}

	public static void deleteCommercePriceListDiscountRels(
		long commercePriceListId) {

		getService().deleteCommercePriceListDiscountRels(commercePriceListId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceListDiscountRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceListDiscountRelModelImpl</code>.
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

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
			fetchCommercePriceListDiscountRel(
				long commercePriceListDiscountRelId) {

		return getService().fetchCommercePriceListDiscountRel(
			commercePriceListDiscountRelId);
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
			fetchCommercePriceListDiscountRel(
				long commerceDiscountId, long commercePriceListId) {

		return getService().fetchCommercePriceListDiscountRel(
			commerceDiscountId, commercePriceListId);
	}

	/**
	 * Returns the commerce price list discount rel with the matching UUID and company.
	 *
	 * @param uuid the commerce price list discount rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
			fetchCommercePriceListDiscountRelByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().fetchCommercePriceListDiscountRelByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce price list discount rel with the primary key.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel
	 * @throws PortalException if a commerce price list discount rel with the primary key could not be found
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				getCommercePriceListDiscountRel(
					long commercePriceListDiscountRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListDiscountRel(
			commercePriceListDiscountRelId);
	}

	/**
	 * Returns the commerce price list discount rel with the matching UUID and company.
	 *
	 * @param uuid the commerce price list discount rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce price list discount rel
	 * @throws PortalException if a matching commerce price list discount rel could not be found
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				getCommercePriceListDiscountRelByUuidAndCompanyId(
					String uuid, long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListDiscountRelByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce price list discount rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of commerce price list discount rels
	 */
	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListDiscountRel>
			getCommercePriceListDiscountRels(int start, int end) {

		return getService().getCommercePriceListDiscountRels(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListDiscountRel>
			getCommercePriceListDiscountRels(long commercePriceListId) {

		return getService().getCommercePriceListDiscountRels(
			commercePriceListId);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListDiscountRel>
			getCommercePriceListDiscountRels(
				long commercePriceListId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.price.list.model.
						CommercePriceListDiscountRel> orderByComparator) {

		return getService().getCommercePriceListDiscountRels(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce price list discount rels.
	 *
	 * @return the number of commerce price list discount rels
	 */
	public static int getCommercePriceListDiscountRelsCount() {
		return getService().getCommercePriceListDiscountRelsCount();
	}

	public static int getCommercePriceListDiscountRelsCount(
		long commercePriceListId) {

		return getService().getCommercePriceListDiscountRelsCount(
			commercePriceListId);
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

	/**
	 * Updates the commerce price list discount rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListDiscountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceListDiscountRel the commerce price list discount rel
	 * @return the commerce price list discount rel that was updated
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
			updateCommercePriceListDiscountRel(
				com.liferay.commerce.price.list.model.
					CommercePriceListDiscountRel commercePriceListDiscountRel) {

		return getService().updateCommercePriceListDiscountRel(
			commercePriceListDiscountRel);
	}

	public static CommercePriceListDiscountRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListDiscountRelLocalService,
		 CommercePriceListDiscountRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListDiscountRelLocalService.class);

		ServiceTracker
			<CommercePriceListDiscountRelLocalService,
			 CommercePriceListDiscountRelLocalService> serviceTracker =
				new ServiceTracker
					<CommercePriceListDiscountRelLocalService,
					 CommercePriceListDiscountRelLocalService>(
						 bundle.getBundleContext(),
						 CommercePriceListDiscountRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}