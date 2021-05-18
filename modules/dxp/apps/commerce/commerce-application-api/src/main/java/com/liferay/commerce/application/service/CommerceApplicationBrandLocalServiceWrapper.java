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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceApplicationBrandLocalService}.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrandLocalService
 * @generated
 */
public class CommerceApplicationBrandLocalServiceWrapper
	implements CommerceApplicationBrandLocalService,
			   ServiceWrapper<CommerceApplicationBrandLocalService> {

	public CommerceApplicationBrandLocalServiceWrapper(
		CommerceApplicationBrandLocalService
			commerceApplicationBrandLocalService) {

		_commerceApplicationBrandLocalService =
			commerceApplicationBrandLocalService;
	}

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
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
		addCommerceApplicationBrand(
			com.liferay.commerce.application.model.CommerceApplicationBrand
				commerceApplicationBrand) {

		return _commerceApplicationBrandLocalService.
			addCommerceApplicationBrand(commerceApplicationBrand);
	}

	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
			addCommerceApplicationBrand(
				long userId, String name, boolean logo, byte[] logoBytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.
			addCommerceApplicationBrand(userId, name, logo, logoBytes);
	}

	/**
	 * Creates a new commerce application brand with the primary key. Does not add the commerce application brand to the database.
	 *
	 * @param commerceApplicationBrandId the primary key for the new commerce application brand
	 * @return the new commerce application brand
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
		createCommerceApplicationBrand(long commerceApplicationBrandId) {

		return _commerceApplicationBrandLocalService.
			createCommerceApplicationBrand(commerceApplicationBrandId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
			deleteCommerceApplicationBrand(
				com.liferay.commerce.application.model.CommerceApplicationBrand
					commerceApplicationBrand)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.
			deleteCommerceApplicationBrand(commerceApplicationBrand);
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
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
			deleteCommerceApplicationBrand(long commerceApplicationBrandId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.
			deleteCommerceApplicationBrand(commerceApplicationBrandId);
	}

	@Override
	public void deleteCommerceApplicationBrands(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceApplicationBrandLocalService.deleteCommerceApplicationBrands(
			companyId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceApplicationBrandLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceApplicationBrandLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceApplicationBrandLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _commerceApplicationBrandLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _commerceApplicationBrandLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _commerceApplicationBrandLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _commerceApplicationBrandLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _commerceApplicationBrandLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
		fetchCommerceApplicationBrand(long commerceApplicationBrandId) {

		return _commerceApplicationBrandLocalService.
			fetchCommerceApplicationBrand(commerceApplicationBrandId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceApplicationBrandLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce application brand with the primary key.
	 *
	 * @param commerceApplicationBrandId the primary key of the commerce application brand
	 * @return the commerce application brand
	 * @throws PortalException if a commerce application brand with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
			getCommerceApplicationBrand(long commerceApplicationBrandId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.
			getCommerceApplicationBrand(commerceApplicationBrandId);
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
	@Override
	public java.util.List
		<com.liferay.commerce.application.model.CommerceApplicationBrand>
			getCommerceApplicationBrands(int start, int end) {

		return _commerceApplicationBrandLocalService.
			getCommerceApplicationBrands(start, end);
	}

	/**
	 * Returns the number of commerce application brands.
	 *
	 * @return the number of commerce application brands
	 */
	@Override
	public int getCommerceApplicationBrandsCount() {
		return _commerceApplicationBrandLocalService.
			getCommerceApplicationBrandsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceApplicationBrandLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceApplicationBrandLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
		updateCommerceApplicationBrand(
			com.liferay.commerce.application.model.CommerceApplicationBrand
				commerceApplicationBrand) {

		return _commerceApplicationBrandLocalService.
			updateCommerceApplicationBrand(commerceApplicationBrand);
	}

	@Override
	public com.liferay.commerce.application.model.CommerceApplicationBrand
			updateCommerceApplicationBrand(
				long commerceApplicationBrandId, String name, boolean logo,
				byte[] logoBytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationBrandLocalService.
			updateCommerceApplicationBrand(
				commerceApplicationBrandId, name, logo, logoBytes);
	}

	@Override
	public CommerceApplicationBrandLocalService getWrappedService() {
		return _commerceApplicationBrandLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceApplicationBrandLocalService
			commerceApplicationBrandLocalService) {

		_commerceApplicationBrandLocalService =
			commerceApplicationBrandLocalService;
	}

	private CommerceApplicationBrandLocalService
		_commerceApplicationBrandLocalService;

}