/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceApplicationBrand. This utility wraps
 * <code>com.liferay.commerce.application.service.impl.CommerceApplicationBrandLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrandLocalService
 * @generated
 */
public class CommerceApplicationBrandLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.application.service.impl.CommerceApplicationBrandLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce application brand to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationBrandLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationBrand the commerce application brand
	 * @return the commerce application brand that was added
	 */
	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
			addCommerceApplicationBrand(
				com.liferay.commerce.application.model.CommerceApplicationBrand
					commerceApplicationBrand) {

		return getService().addCommerceApplicationBrand(
			commerceApplicationBrand);
	}

	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
				addCommerceApplicationBrand(
					long userId, String name, boolean logo, byte[] logoBytes)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceApplicationBrand(
			userId, name, logo, logoBytes);
	}

	/**
	 * Creates a new commerce application brand with the primary key. Does not add the commerce application brand to the database.
	 *
	 * @param commerceApplicationBrandId the primary key for the new commerce application brand
	 * @return the new commerce application brand
	 */
	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
			createCommerceApplicationBrand(long commerceApplicationBrandId) {

		return getService().createCommerceApplicationBrand(
			commerceApplicationBrandId);
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
	 * Deletes the commerce application brand from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationBrandLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationBrand the commerce application brand
	 * @return the commerce application brand that was removed
	 * @throws PortalException
	 */
	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
				deleteCommerceApplicationBrand(
					com.liferay.commerce.application.model.
						CommerceApplicationBrand commerceApplicationBrand)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceApplicationBrand(
			commerceApplicationBrand);
	}

	/**
	 * Deletes the commerce application brand with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationBrandLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand that was removed
	 * @throws PortalException if a commerce application brand with the primary key could not be found
	 */
	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
				deleteCommerceApplicationBrand(long commerceApplicationBrandId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceApplicationBrand(
			commerceApplicationBrandId);
	}

	public static void deleteCommerceApplicationBrands(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceApplicationBrands(companyId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationBrandModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationBrandModelImpl</code>.
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
		com.liferay.commerce.application.model.CommerceApplicationBrand
			fetchCommerceApplicationBrand(long commerceApplicationBrandId) {

		return getService().fetchCommerceApplicationBrand(
			commerceApplicationBrandId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce application brand with the primary key.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand
	 * @throws PortalException if a commerce application brand with the primary key could not be found
	 */
	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
				getCommerceApplicationBrand(long commerceApplicationBrandId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceApplicationBrand(
			commerceApplicationBrandId);
	}

	/**
	 * Returns a range of all the commerce application brands.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationBrandModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application brands
	 * @param end the upper bound of the range of commerce application brands (not inclusive)
	 * @return the range of commerce application brands
	 */
	public static java.util.List
		<com.liferay.commerce.application.model.CommerceApplicationBrand>
			getCommerceApplicationBrands(int start, int end) {

		return getService().getCommerceApplicationBrands(start, end);
	}

	/**
	 * Returns the number of commerce application brands.
	 *
	 * @return the number of commerce application brands
	 */
	public static int getCommerceApplicationBrandsCount() {
		return getService().getCommerceApplicationBrandsCount();
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
	 * Updates the commerce application brand in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationBrandLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationBrand the commerce application brand
	 * @return the commerce application brand that was updated
	 */
	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
			updateCommerceApplicationBrand(
				com.liferay.commerce.application.model.CommerceApplicationBrand
					commerceApplicationBrand) {

		return getService().updateCommerceApplicationBrand(
			commerceApplicationBrand);
	}

	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
				updateCommerceApplicationBrand(
					long commerceApplicationBrandId, String name, boolean logo,
					byte[] logoBytes)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceApplicationBrand(
			commerceApplicationBrandId, name, logo, logoBytes);
	}

	public static CommerceApplicationBrandLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceApplicationBrandLocalService,
		 CommerceApplicationBrandLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceApplicationBrandLocalService.class);

		ServiceTracker
			<CommerceApplicationBrandLocalService,
			 CommerceApplicationBrandLocalService> serviceTracker =
				new ServiceTracker
					<CommerceApplicationBrandLocalService,
					 CommerceApplicationBrandLocalService>(
						 bundle.getBundleContext(),
						 CommerceApplicationBrandLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}