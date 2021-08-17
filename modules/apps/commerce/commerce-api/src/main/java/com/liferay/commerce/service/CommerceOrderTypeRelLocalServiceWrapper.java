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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderTypeRelLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelLocalService
 * @generated
 */
public class CommerceOrderTypeRelLocalServiceWrapper
	implements CommerceOrderTypeRelLocalService,
			   ServiceWrapper<CommerceOrderTypeRelLocalService> {

	public CommerceOrderTypeRelLocalServiceWrapper(
		CommerceOrderTypeRelLocalService commerceOrderTypeRelLocalService) {

		_commerceOrderTypeRelLocalService = commerceOrderTypeRelLocalService;
	}

	/**
	 * Adds the commerce order type rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderTypeRel the commerce order type rel
	 * @return the commerce order type rel that was added
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
		addCommerceOrderTypeRel(
			com.liferay.commerce.model.CommerceOrderTypeRel
				commerceOrderTypeRel) {

		return _commerceOrderTypeRelLocalService.addCommerceOrderTypeRel(
			commerceOrderTypeRel);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
			addCommerceOrderTypeRel(
				long userId, String className, long classPK,
				long commerceOrderTypeId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.addCommerceOrderTypeRel(
			userId, className, classPK, commerceOrderTypeId, serviceContext);
	}

	/**
	 * Creates a new commerce order type rel with the primary key. Does not add the commerce order type rel to the database.
	 *
	 * @param commerceOrderTypeRelId the primary key for the new commerce order type rel
	 * @return the new commerce order type rel
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
		createCommerceOrderTypeRel(long commerceOrderTypeRelId) {

		return _commerceOrderTypeRelLocalService.createCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce order type rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderTypeRel the commerce order type rel
	 * @return the commerce order type rel that was removed
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
		deleteCommerceOrderTypeRel(
			com.liferay.commerce.model.CommerceOrderTypeRel
				commerceOrderTypeRel) {

		return _commerceOrderTypeRelLocalService.deleteCommerceOrderTypeRel(
			commerceOrderTypeRel);
	}

	/**
	 * Deletes the commerce order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel that was removed
	 * @throws PortalException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
			deleteCommerceOrderTypeRel(long commerceOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.deleteCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	@Override
	public void deleteCommerceOrderTypeRels(long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderTypeRelLocalService.deleteCommerceOrderTypeRels(
			commerceOrderTypeId);
	}

	@Override
	public void deleteCommerceOrderTypeRels(
			String className, long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderTypeRelLocalService.deleteCommerceOrderTypeRels(
			className, commerceOrderTypeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceOrderTypeRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceOrderTypeRelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceOrderTypeRelLocalService.dynamicQuery();
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

		return _commerceOrderTypeRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderTypeRelModelImpl</code>.
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

		return _commerceOrderTypeRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderTypeRelModelImpl</code>.
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

		return _commerceOrderTypeRelLocalService.dynamicQuery(
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

		return _commerceOrderTypeRelLocalService.dynamicQueryCount(
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

		return _commerceOrderTypeRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
		fetchCommerceOrderTypeRel(long commerceOrderTypeRelId) {

		return _commerceOrderTypeRelLocalService.fetchCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	/**
	 * Returns the commerce order type rel with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order type rel's external reference code
	 * @return the matching commerce order type rel, or <code>null</code> if a matching commerce order type rel could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
		fetchCommerceOrderTypeRelByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceOrderTypeRelLocalService.
			fetchCommerceOrderTypeRelByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceOrderTypeRelByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
		fetchCommerceOrderTypeRelByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceOrderTypeRelLocalService.
			fetchCommerceOrderTypeRelByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceOrderTypeRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderTypeRel>
			getCommerceOrderTypeCommerceChannelRels(
				long commerceOrderTypeId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.
			getCommerceOrderTypeCommerceChannelRels(
				commerceOrderTypeId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceChannelRelsCount(
			long commerceOrderTypeId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.
			getCommerceOrderTypeCommerceChannelRelsCount(
				commerceOrderTypeId, keywords);
	}

	/**
	 * Returns the commerce order type rel with the primary key.
	 *
	 * @param commerceOrderTypeRelId the primary key of the commerce order type rel
	 * @return the commerce order type rel
	 * @throws PortalException if a commerce order type rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
			getCommerceOrderTypeRel(long commerceOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.getCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	/**
	 * Returns the commerce order type rel with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order type rel's external reference code
	 * @return the matching commerce order type rel
	 * @throws PortalException if a matching commerce order type rel could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
			getCommerceOrderTypeRelByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.
			getCommerceOrderTypeRelByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * Returns a range of all the commerce order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order type rels
	 * @param end the upper bound of the range of commerce order type rels (not inclusive)
	 * @return the range of commerce order type rels
	 */
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderTypeRel>
		getCommerceOrderTypeRels(int start, int end) {

		return _commerceOrderTypeRelLocalService.getCommerceOrderTypeRels(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderTypeRel>
		getCommerceOrderTypeRels(
			String className, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceOrderTypeRel>
					orderByComparator) {

		return _commerceOrderTypeRelLocalService.getCommerceOrderTypeRels(
			className, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce order type rels.
	 *
	 * @return the number of commerce order type rels
	 */
	@Override
	public int getCommerceOrderTypeRelsCount() {
		return _commerceOrderTypeRelLocalService.
			getCommerceOrderTypeRelsCount();
	}

	@Override
	public int getCommerceOrderTypeRelsCount(String className, long classPK) {
		return _commerceOrderTypeRelLocalService.getCommerceOrderTypeRelsCount(
			className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceOrderTypeRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderTypeRelLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce order type rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderTypeRel the commerce order type rel
	 * @return the commerce order type rel that was updated
	 */
	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
		updateCommerceOrderTypeRel(
			com.liferay.commerce.model.CommerceOrderTypeRel
				commerceOrderTypeRel) {

		return _commerceOrderTypeRelLocalService.updateCommerceOrderTypeRel(
			commerceOrderTypeRel);
	}

	@Override
	public CommerceOrderTypeRelLocalService getWrappedService() {
		return _commerceOrderTypeRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderTypeRelLocalService commerceOrderTypeRelLocalService) {

		_commerceOrderTypeRelLocalService = commerceOrderTypeRelLocalService;
	}

	private CommerceOrderTypeRelLocalService _commerceOrderTypeRelLocalService;

}