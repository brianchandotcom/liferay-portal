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
 * Provides a wrapper for {@link CommerceAccountGroupLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupLocalService
 * @generated
 */
public class CommerceAccountGroupLocalServiceWrapper
	implements CommerceAccountGroupLocalService,
			   ServiceWrapper<CommerceAccountGroupLocalService> {

	public CommerceAccountGroupLocalServiceWrapper(
		CommerceAccountGroupLocalService commerceAccountGroupLocalService) {

		_commerceAccountGroupLocalService = commerceAccountGroupLocalService;
	}

	/**
	 * Adds the commerce account group to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroup the commerce account group
	 * @return the commerce account group that was added
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		addCommerceAccountGroup(
			com.liferay.commerce.account.model.CommerceAccountGroup
				commerceAccountGroup) {

		return _commerceAccountGroupLocalService.addCommerceAccountGroup(
			commerceAccountGroup);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			addCommerceAccountGroup(
				long companyId, String name, int type, boolean system,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.addCommerceAccountGroup(
			companyId, name, type, system, externalReferenceCode,
			serviceContext);
	}

	@Override
	public void checkGuestCommerceAccountGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountGroupLocalService.checkGuestCommerceAccountGroup(
			companyId);
	}

	/**
	 * Creates a new commerce account group with the primary key. Does not add the commerce account group to the database.
	 *
	 * @param commerceAccountGroupId the primary key for the new commerce account group
	 * @return the new commerce account group
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		createCommerceAccountGroup(long commerceAccountGroupId) {

		return _commerceAccountGroupLocalService.createCommerceAccountGroup(
			commerceAccountGroupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce account group from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroup the commerce account group
	 * @return the commerce account group that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			deleteCommerceAccountGroup(
				com.liferay.commerce.account.model.CommerceAccountGroup
					commerceAccountGroup)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.deleteCommerceAccountGroup(
			commerceAccountGroup);
	}

	/**
	 * Deletes the commerce account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group that was removed
	 * @throws PortalException if a commerce account group with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			deleteCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.deleteCommerceAccountGroup(
			commerceAccountGroupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAccountGroupLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAccountGroupLocalService.dynamicQuery();
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

		return _commerceAccountGroupLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupModelImpl</code>.
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

		return _commerceAccountGroupLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupModelImpl</code>.
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

		return _commerceAccountGroupLocalService.dynamicQuery(
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

		return _commerceAccountGroupLocalService.dynamicQueryCount(
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

		return _commerceAccountGroupLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountGroupLocalService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		fetchCommerceAccountGroup(long commerceAccountGroupId) {

		return _commerceAccountGroupLocalService.fetchCommerceAccountGroup(
			commerceAccountGroupId);
	}

	/**
	 * Returns the commerce account group with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account group's external reference code
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		fetchCommerceAccountGroupByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountGroupLocalService.
			fetchCommerceAccountGroupByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceAccountGroupLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account group with the primary key.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group
	 * @throws PortalException if a commerce account group with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			getCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.getCommerceAccountGroup(
			commerceAccountGroupId);
	}

	/**
	 * Returns a range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of commerce account groups
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroups(int start, int end) {

		return _commerceAccountGroupLocalService.getCommerceAccountGroups(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroups(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroup>
						orderByComparator) {

		return _commerceAccountGroupLocalService.getCommerceAccountGroups(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroupsByCommerceAccountId(
				long commerceAccountId) {

		return _commerceAccountGroupLocalService.
			getCommerceAccountGroupsByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns the number of commerce account groups.
	 *
	 * @return the number of commerce account groups
	 */
	@Override
	public int getCommerceAccountGroupsCount() {
		return _commerceAccountGroupLocalService.
			getCommerceAccountGroupsCount();
	}

	@Override
	public int getCommerceAccountGroupsCount(long companyId) {
		return _commerceAccountGroupLocalService.getCommerceAccountGroupsCount(
			companyId);
	}

	@Override
	public java.util.List<Long> getCommerceAccountUserIdsFromAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		return _commerceAccountGroupLocalService.
			getCommerceAccountUserIdsFromAccountGroupIds(
				commerceAccountGroupIds, start, end);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceAccountGroupLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
				searchCommerceAccountGroups(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.searchCommerceAccountGroups(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceAccountsGroupCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.
			searchCommerceAccountsGroupCount(companyId, keywords);
	}

	/**
	 * Updates the commerce account group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroup the commerce account group
	 * @return the commerce account group that was updated
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		updateCommerceAccountGroup(
			com.liferay.commerce.account.model.CommerceAccountGroup
				commerceAccountGroup) {

		return _commerceAccountGroupLocalService.updateCommerceAccountGroup(
			commerceAccountGroup);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			updateCommerceAccountGroup(
				long commerceAccountGroupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.updateCommerceAccountGroup(
			commerceAccountGroupId, name, serviceContext);
	}

	@Override
	public CommerceAccountGroupLocalService getWrappedService() {
		return _commerceAccountGroupLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupLocalService commerceAccountGroupLocalService) {

		_commerceAccountGroupLocalService = commerceAccountGroupLocalService;
	}

	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

}