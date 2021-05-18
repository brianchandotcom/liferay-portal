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

package com.liferay.commerce.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAccountOrganizationRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelLocalService
 * @generated
 */
public class CommerceAccountOrganizationRelLocalServiceWrapper
	implements CommerceAccountOrganizationRelLocalService,
			   ServiceWrapper<CommerceAccountOrganizationRelLocalService> {

	public CommerceAccountOrganizationRelLocalServiceWrapper(
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService) {

		_commerceAccountOrganizationRelLocalService =
			commerceAccountOrganizationRelLocalService;
	}

	/**
	 * Adds the commerce account organization rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountOrganizationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountOrganizationRel the commerce account organization rel
	 * @return the commerce account organization rel that was added
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		addCommerceAccountOrganizationRel(
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				commerceAccountOrganizationRel) {

		return _commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRel(commerceAccountOrganizationRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			addCommerceAccountOrganizationRel(
				long commerceAccountId, long organizationId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRel(
				commerceAccountId, organizationId, serviceContext);
	}

	@Override
	public void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds, serviceContext);
	}

	/**
	 * Creates a new commerce account organization rel with the primary key. Does not add the commerce account organization rel to the database.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key for the new commerce account organization rel
	 * @return the new commerce account organization rel
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		createCommerceAccountOrganizationRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK) {

		return _commerceAccountOrganizationRelLocalService.
			createCommerceAccountOrganizationRel(
				commerceAccountOrganizationRelPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce account organization rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountOrganizationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountOrganizationRel the commerce account organization rel
	 * @return the commerce account organization rel that was removed
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		deleteCommerceAccountOrganizationRel(
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				commerceAccountOrganizationRel) {

		return _commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRel(
				commerceAccountOrganizationRel);
	}

	/**
	 * Deletes the commerce account organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountOrganizationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel that was removed
	 * @throws PortalException if a commerce account organization rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			deleteCommerceAccountOrganizationRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountOrganizationRelPK
						commerceAccountOrganizationRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRel(
				commerceAccountOrganizationRelPK);
	}

	@Override
	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByCommerceAccountId(
		long commerceAccountId) {

		_commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRelsByCommerceAccountId(
				commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByOrganizationId(
		long organizationId) {

		_commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRelsByOrganizationId(
				organizationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAccountOrganizationRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceAccountOrganizationRelLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAccountOrganizationRelLocalService.dynamicQuery();
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

		return _commerceAccountOrganizationRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountOrganizationRelModelImpl</code>.
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

		return _commerceAccountOrganizationRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountOrganizationRelModelImpl</code>.
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

		return _commerceAccountOrganizationRelLocalService.dynamicQuery(
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

		return _commerceAccountOrganizationRelLocalService.dynamicQueryCount(
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

		return _commerceAccountOrganizationRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		fetchCommerceAccountOrganizationRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK) {

		return _commerceAccountOrganizationRelLocalService.
			fetchCommerceAccountOrganizationRel(
				commerceAccountOrganizationRelPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceAccountOrganizationRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account organization rel with the primary key.
	 *
	 * @param commerceAccountOrganizationRelPK the primary key of the commerce account organization rel
	 * @return the commerce account organization rel
	 * @throws PortalException if a commerce account organization rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			getCommerceAccountOrganizationRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountOrganizationRelPK
						commerceAccountOrganizationRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRel(commerceAccountOrganizationRelPK);
	}

	/**
	 * Returns a range of all the commerce account organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account organization rels
	 * @param end the upper bound of the range of commerce account organization rels (not inclusive)
	 * @return the range of commerce account organization rels
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(int start, int end) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(long commerceAccountId) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(commerceAccountId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(
				long commerceAccountId, int start, int end) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(commerceAccountId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRelsByOrganizationId(
				long organizationId, int start, int end) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsByOrganizationId(
				organizationId, start, end);
	}

	@Override
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
		long organizationId) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	/**
	 * Returns the number of commerce account organization rels.
	 *
	 * @return the number of commerce account organization rels
	 */
	@Override
	public int getCommerceAccountOrganizationRelsCount() {
		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsCount();
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId) {
		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsCount(commerceAccountId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceAccountOrganizationRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountOrganizationRelLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce account organization rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountOrganizationRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountOrganizationRel the commerce account organization rel
	 * @return the commerce account organization rel that was updated
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		updateCommerceAccountOrganizationRel(
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				commerceAccountOrganizationRel) {

		return _commerceAccountOrganizationRelLocalService.
			updateCommerceAccountOrganizationRel(
				commerceAccountOrganizationRel);
	}

	@Override
	public CommerceAccountOrganizationRelLocalService getWrappedService() {
		return _commerceAccountOrganizationRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService) {

		_commerceAccountOrganizationRelLocalService =
			commerceAccountOrganizationRelLocalService;
	}

	private CommerceAccountOrganizationRelLocalService
		_commerceAccountOrganizationRelLocalService;

}