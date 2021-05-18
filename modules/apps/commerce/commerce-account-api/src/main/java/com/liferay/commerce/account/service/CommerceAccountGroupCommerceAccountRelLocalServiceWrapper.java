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
 * Provides a wrapper for {@link CommerceAccountGroupCommerceAccountRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRelLocalService
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelLocalServiceWrapper
	implements CommerceAccountGroupCommerceAccountRelLocalService,
			   ServiceWrapper
				   <CommerceAccountGroupCommerceAccountRelLocalService> {

	public CommerceAccountGroupCommerceAccountRelLocalServiceWrapper(
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService) {

		_commerceAccountGroupCommerceAccountRelLocalService =
			commerceAccountGroupCommerceAccountRelLocalService;
	}

	/**
	 * Adds the commerce account group commerce account rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupCommerceAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupCommerceAccountRel the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel that was added
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				addCommerceAccountGroupCommerceAccountRel(
					com.liferay.commerce.account.model.
						CommerceAccountGroupCommerceAccountRel
							commerceAccountGroupCommerceAccountRel) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRel);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					addCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId, serviceContext);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					addCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId,
						String externalReferenceCode,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId,
				externalReferenceCode, serviceContext);
	}

	/**
	 * Creates a new commerce account group commerce account rel with the primary key. Does not add the commerce account group commerce account rel to the database.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key for the new commerce account group commerce account rel
	 * @return the new commerce account group commerce account rel
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				createCommerceAccountGroupCommerceAccountRel(
					long commerceAccountGroupCommerceAccountRelId) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			createCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce account group commerce account rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupCommerceAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupCommerceAccountRel the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel that was removed
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				deleteCommerceAccountGroupCommerceAccountRel(
					com.liferay.commerce.account.model.
						CommerceAccountGroupCommerceAccountRel
							commerceAccountGroupCommerceAccountRel) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			deleteCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRel);
	}

	/**
	 * Deletes the commerce account group commerce account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupCommerceAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel that was removed
	 * @throws PortalException if a commerce account group commerce account rel with the primary key could not be found
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					deleteCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupCommerceAccountRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			deleteCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRelId);
	}

	@Override
	public void deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
		long commerceAccountGroupId) {

		_commerceAccountGroupCommerceAccountRelLocalService.
			deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
				commerceAccountGroupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAccountGroupCommerceAccountRelLocalService.dslQuery(
			dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAccountGroupCommerceAccountRelLocalService.
			dynamicQuery();
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

		return _commerceAccountGroupCommerceAccountRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupCommerceAccountRelModelImpl</code>.
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

		return _commerceAccountGroupCommerceAccountRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupCommerceAccountRelModelImpl</code>.
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

		return _commerceAccountGroupCommerceAccountRelLocalService.dynamicQuery(
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

		return _commerceAccountGroupCommerceAccountRelLocalService.
			dynamicQueryCount(dynamicQuery);
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

		return _commerceAccountGroupCommerceAccountRelLocalService.
			dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				fetchCommerceAccountGroupCommerceAccountRel(
					long commerceAccountGroupCommerceAccountRelId) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			fetchCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRelId);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				fetchCommerceAccountGroupCommerceAccountRel(
					long commerceAccountGroupId, long commerceAccountId) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			fetchCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId);
	}

	/**
	 * Returns the commerce account group commerce account rel with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account group commerce account rel's external reference code
	 * @return the matching commerce account group commerce account rel, or <code>null</code> if a matching commerce account group commerce account rel could not be found
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				fetchCommerceAccountGroupCommerceAccountRelByExternalReferenceCode(
					long companyId, String externalReferenceCode) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			fetchCommerceAccountGroupCommerceAccountRelByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceAccountGroupCommerceAccountRelByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				fetchCommerceAccountGroupCommerceAccountRelByReferenceCode(
					long companyId, String externalReferenceCode) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			fetchCommerceAccountGroupCommerceAccountRelByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account group commerce account rel with the primary key.
	 *
	 * @param commerceAccountGroupCommerceAccountRelId the primary key of the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel
	 * @throws PortalException if a commerce account group commerce account rel with the primary key could not be found
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					getCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupCommerceAccountRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRelId);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					getCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId);
	}

	/**
	 * Returns the commerce account group commerce account rel with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account group commerce account rel's external reference code
	 * @return the matching commerce account group commerce account rel
	 * @throws PortalException if a matching commerce account group commerce account rel could not be found
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					getCommerceAccountGroupCommerceAccountRelByExternalReferenceCode(
						long companyId, String externalReferenceCode)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRelByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * Returns a range of all the commerce account group commerce account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupCommerceAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group commerce account rels
	 * @param end the upper bound of the range of commerce account group commerce account rels (not inclusive)
	 * @return the range of commerce account group commerce account rels
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel>
				getCommerceAccountGroupCommerceAccountRels(int start, int end) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRels(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel>
				getCommerceAccountGroupCommerceAccountRels(
					long commerceAccountGroupId, int start, int end) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRels(
				commerceAccountGroupId, start, end);
	}

	/**
	 * Returns the number of commerce account group commerce account rels.
	 *
	 * @return the number of commerce account group commerce account rels
	 */
	@Override
	public int getCommerceAccountGroupCommerceAccountRelsCount() {
		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRelsCount();
	}

	@Override
	public int getCommerceAccountGroupCommerceAccountRelsCount(
		long commerceAccountGroupId) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRelsCount(
				commerceAccountGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupCommerceAccountRelLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the commerce account group commerce account rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupCommerceAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupCommerceAccountRel the commerce account group commerce account rel
	 * @return the commerce account group commerce account rel that was updated
	 */
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				updateCommerceAccountGroupCommerceAccountRel(
					com.liferay.commerce.account.model.
						CommerceAccountGroupCommerceAccountRel
							commerceAccountGroupCommerceAccountRel) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			updateCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRel);
	}

	@Override
	public CommerceAccountGroupCommerceAccountRelLocalService
		getWrappedService() {

		return _commerceAccountGroupCommerceAccountRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService) {

		_commerceAccountGroupCommerceAccountRelLocalService =
			commerceAccountGroupCommerceAccountRelLocalService;
	}

	private CommerceAccountGroupCommerceAccountRelLocalService
		_commerceAccountGroupCommerceAccountRelLocalService;

}