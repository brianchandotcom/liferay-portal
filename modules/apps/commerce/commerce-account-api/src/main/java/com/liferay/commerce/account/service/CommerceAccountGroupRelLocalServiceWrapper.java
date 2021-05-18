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
 * Provides a wrapper for {@link CommerceAccountGroupRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelLocalService
 * @generated
 */
public class CommerceAccountGroupRelLocalServiceWrapper
	implements CommerceAccountGroupRelLocalService,
			   ServiceWrapper<CommerceAccountGroupRelLocalService> {

	public CommerceAccountGroupRelLocalServiceWrapper(
		CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		_commerceAccountGroupRelLocalService =
			commerceAccountGroupRelLocalService;
	}

	/**
	 * Adds the commerce account group rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 * @return the commerce account group rel that was added
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		addCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return _commerceAccountGroupRelLocalService.addCommerceAccountGroupRel(
			commerceAccountGroupRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			addCommerceAccountGroupRel(
				String className, long classPK, long commerceAccountGroupId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.addCommerceAccountGroupRel(
			className, classPK, commerceAccountGroupId, serviceContext);
	}

	/**
	 * Creates a new commerce account group rel with the primary key. Does not add the commerce account group rel to the database.
	 *
	 * @param commerceAccountGroupRelId the primary key for the new commerce account group rel
	 * @return the new commerce account group rel
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		createCommerceAccountGroupRel(long commerceAccountGroupRelId) {

		return _commerceAccountGroupRelLocalService.
			createCommerceAccountGroupRel(commerceAccountGroupRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce account group rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 * @return the commerce account group rel that was removed
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		deleteCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return _commerceAccountGroupRelLocalService.
			deleteCommerceAccountGroupRel(commerceAccountGroupRel);
	}

	/**
	 * Deletes the commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel that was removed
	 * @throws PortalException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			deleteCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.
			deleteCommerceAccountGroupRel(commerceAccountGroupRelId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(long commerceAccountGroupId) {
		_commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRels(
			commerceAccountGroupId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(String className, long classPK) {
		_commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRels(
			className, classPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAccountGroupRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceAccountGroupRelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAccountGroupRelLocalService.dynamicQuery();
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

		return _commerceAccountGroupRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupRelModelImpl</code>.
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

		return _commerceAccountGroupRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupRelModelImpl</code>.
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

		return _commerceAccountGroupRelLocalService.dynamicQuery(
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

		return _commerceAccountGroupRelLocalService.dynamicQueryCount(
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

		return _commerceAccountGroupRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		fetchCommerceAccountGroupRel(long commerceAccountGroupRelId) {

		return _commerceAccountGroupRelLocalService.
			fetchCommerceAccountGroupRel(commerceAccountGroupRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceAccountGroupRelLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account group rel with the primary key.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel
	 * @throws PortalException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			getCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	/**
	 * Returns a range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of commerce account group rels
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(int start, int end) {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(
				long commerceAccountGroupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroupRel>
						orderByComparator) {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroupRel>
						orderByComparator) {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			className, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce account group rels.
	 *
	 * @return the number of commerce account group rels
	 */
	@Override
	public int getCommerceAccountGroupRelsCount() {
		return _commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount();
	}

	@Override
	public int getCommerceAccountGroupRelsCount(long commerceAccountGroupId) {
		return _commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount(commerceAccountGroupId);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(
		String className, long classPK) {

		return _commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceAccountGroupRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupRelLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce account group rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 * @return the commerce account group rel that was updated
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		updateCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return _commerceAccountGroupRelLocalService.
			updateCommerceAccountGroupRel(commerceAccountGroupRel);
	}

	@Override
	public CommerceAccountGroupRelLocalService getWrappedService() {
		return _commerceAccountGroupRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		_commerceAccountGroupRelLocalService =
			commerceAccountGroupRelLocalService;
	}

	private CommerceAccountGroupRelLocalService
		_commerceAccountGroupRelLocalService;

}