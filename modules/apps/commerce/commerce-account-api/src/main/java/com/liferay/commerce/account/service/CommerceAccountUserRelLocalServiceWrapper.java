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
 * Provides a wrapper for {@link CommerceAccountUserRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountUserRelLocalService
 * @generated
 */
public class CommerceAccountUserRelLocalServiceWrapper
	implements CommerceAccountUserRelLocalService,
			   ServiceWrapper<CommerceAccountUserRelLocalService> {

	public CommerceAccountUserRelLocalServiceWrapper(
		CommerceAccountUserRelLocalService commerceAccountUserRelLocalService) {

		_commerceAccountUserRelLocalService =
			commerceAccountUserRelLocalService;
	}

	/**
	 * Adds the commerce account user rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountUserRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountUserRel the commerce account user rel
	 * @return the commerce account user rel that was added
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		addCommerceAccountUserRel(
			com.liferay.commerce.account.model.CommerceAccountUserRel
				commerceAccountUserRel) {

		return _commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			addCommerceAccountUserRel(
				long commerceAccountId, long commerceAccountUserId,
				long[] roleIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, roleIds, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			addCommerceAccountUserRel(
				long commerceAccountId, long commerceAccountUserId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, serviceContext);
	}

	@Override
	public void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelLocalService.addCommerceAccountUserRels(
			commerceAccountId, userIds, emailAddresses, roleIds,
			serviceContext);
	}

	@Override
	public void addDefaultRoles(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelLocalService.addDefaultRoles(userId);
	}

	/**
	 * Creates a new commerce account user rel with the primary key. Does not add the commerce account user rel to the database.
	 *
	 * @param commerceAccountUserRelPK the primary key for the new commerce account user rel
	 * @return the new commerce account user rel
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		createCommerceAccountUserRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK commerceAccountUserRelPK) {

		return _commerceAccountUserRelLocalService.createCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce account user rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountUserRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountUserRel the commerce account user rel
	 * @return the commerce account user rel that was removed
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		deleteCommerceAccountUserRel(
			com.liferay.commerce.account.model.CommerceAccountUserRel
				commerceAccountUserRel) {

		return _commerceAccountUserRelLocalService.deleteCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	/**
	 * Deletes the commerce account user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountUserRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountUserRelPK the primary key of the commerce account user rel
	 * @return the commerce account user rel that was removed
	 * @throws PortalException if a commerce account user rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			deleteCommerceAccountUserRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.deleteCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelLocalService.deleteCommerceAccountUserRels(
			commerceAccountId, userIds);
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountId(
		long commerceAccountId) {

		_commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountId(commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountUserId(
		long userId) {

		_commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountUserId(userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAccountUserRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceAccountUserRelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAccountUserRelLocalService.dynamicQuery();
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

		return _commerceAccountUserRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountUserRelModelImpl</code>.
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

		return _commerceAccountUserRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountUserRelModelImpl</code>.
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

		return _commerceAccountUserRelLocalService.dynamicQuery(
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

		return _commerceAccountUserRelLocalService.dynamicQueryCount(
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

		return _commerceAccountUserRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		fetchCommerceAccountUserRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK commerceAccountUserRelPK) {

		return _commerceAccountUserRelLocalService.fetchCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceAccountUserRelLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account user rel with the primary key.
	 *
	 * @param commerceAccountUserRelPK the primary key of the commerce account user rel
	 * @return the commerce account user rel
	 * @throws PortalException if a commerce account user rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			getCommerceAccountUserRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	/**
	 * Returns a range of all the commerce account user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account user rels
	 * @param end the upper bound of the range of commerce account user rels (not inclusive)
	 * @return the range of commerce account user rels
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRels(int start, int end) {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRels(long commerceAccountId) {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			commerceAccountId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRels(
				long commerceAccountId, int start, int end) {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			commerceAccountId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRelsByCommerceAccountUserId(
				long commerceAccountUserId) {

		return _commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsByCommerceAccountUserId(
				commerceAccountUserId);
	}

	/**
	 * Returns the number of commerce account user rels.
	 *
	 * @return the number of commerce account user rels
	 */
	@Override
	public int getCommerceAccountUserRelsCount() {
		return _commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsCount();
	}

	@Override
	public int getCommerceAccountUserRelsCount(long commerceAccountId) {
		return _commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsCount(commerceAccountId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceAccountUserRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountUserRelLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.inviteUser(
			commerceAccountId, emailAddress, roleIds, userExternalReferenceCode,
			serviceContext);
	}

	/**
	 * Updates the commerce account user rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountUserRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountUserRel the commerce account user rel
	 * @return the commerce account user rel that was updated
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		updateCommerceAccountUserRel(
			com.liferay.commerce.account.model.CommerceAccountUserRel
				commerceAccountUserRel) {

		return _commerceAccountUserRelLocalService.updateCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	@Override
	public CommerceAccountUserRelLocalService getWrappedService() {
		return _commerceAccountUserRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountUserRelLocalService commerceAccountUserRelLocalService) {

		_commerceAccountUserRelLocalService =
			commerceAccountUserRelLocalService;
	}

	private CommerceAccountUserRelLocalService
		_commerceAccountUserRelLocalService;

}