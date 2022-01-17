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

package com.liferay.commerce.term.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTermLocalService}.
 *
 * @author Luca Pellizzon
 * @see CommerceTermLocalService
 * @generated
 */
public class CommerceTermLocalServiceWrapper
	implements CommerceTermLocalService,
			   ServiceWrapper<CommerceTermLocalService> {

	public CommerceTermLocalServiceWrapper() {
		this(null);
	}

	public CommerceTermLocalServiceWrapper(
		CommerceTermLocalService commerceTermLocalService) {

		_commerceTermLocalService = commerceTermLocalService;
	}

	/**
	 * Adds the commerce term to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTermLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTerm the commerce term
	 * @return the commerce term that was added
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm addCommerceTerm(
		com.liferay.commerce.term.model.CommerceTerm commerceTerm) {

		return _commerceTermLocalService.addCommerceTerm(commerceTerm);
	}

	/**
	 * Creates a new commerce term with the primary key. Does not add the commerce term to the database.
	 *
	 * @param commerceTermId the primary key for the new commerce term
	 * @return the new commerce term
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm createCommerceTerm(
		long commerceTermId) {

		return _commerceTermLocalService.createCommerceTerm(commerceTermId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce term from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTermLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTerm the commerce term
	 * @return the commerce term that was removed
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm deleteCommerceTerm(
		com.liferay.commerce.term.model.CommerceTerm commerceTerm) {

		return _commerceTermLocalService.deleteCommerceTerm(commerceTerm);
	}

	/**
	 * Deletes the commerce term with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTermLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term that was removed
	 * @throws PortalException if a commerce term with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm deleteCommerceTerm(
			long commerceTermId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermLocalService.deleteCommerceTerm(commerceTermId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceTermLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceTermLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceTermLocalService.dynamicQuery();
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

		return _commerceTermLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.term.model.impl.CommerceTermModelImpl</code>.
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

		return _commerceTermLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.term.model.impl.CommerceTermModelImpl</code>.
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

		return _commerceTermLocalService.dynamicQuery(
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

		return _commerceTermLocalService.dynamicQueryCount(dynamicQuery);
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

		return _commerceTermLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.term.model.CommerceTerm fetchCommerceTerm(
		long commerceTermId) {

		return _commerceTermLocalService.fetchCommerceTerm(commerceTermId);
	}

	/**
	 * Returns the commerce term with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce term's external reference code
	 * @return the matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm
		fetchCommerceTermByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceTermLocalService.
			fetchCommerceTermByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceTermByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.term.model.CommerceTerm
		fetchCommerceTermByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceTermLocalService.fetchCommerceTermByReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceTermLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce term with the primary key.
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term
	 * @throws PortalException if a commerce term with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm getCommerceTerm(
			long commerceTermId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermLocalService.getCommerceTerm(commerceTermId);
	}

	/**
	 * Returns the commerce term with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce term's external reference code
	 * @return the matching commerce term
	 * @throws PortalException if a matching commerce term could not be found
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm
			getCommerceTermByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermLocalService.getCommerceTermByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns a range of all the commerce terms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.term.model.impl.CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of commerce terms
	 */
	@Override
	public java.util.List<com.liferay.commerce.term.model.CommerceTerm>
		getCommerceTerms(int start, int end) {

		return _commerceTermLocalService.getCommerceTerms(start, end);
	}

	/**
	 * Returns the number of commerce terms.
	 *
	 * @return the number of commerce terms
	 */
	@Override
	public int getCommerceTermsCount() {
		return _commerceTermLocalService.getCommerceTermsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceTermLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTermLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTermLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the commerce term in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTermLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTerm the commerce term
	 * @return the commerce term that was updated
	 */
	@Override
	public com.liferay.commerce.term.model.CommerceTerm updateCommerceTerm(
		com.liferay.commerce.term.model.CommerceTerm commerceTerm) {

		return _commerceTermLocalService.updateCommerceTerm(commerceTerm);
	}

	@Override
	public CommerceTermLocalService getWrappedService() {
		return _commerceTermLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceTermLocalService commerceTermLocalService) {

		_commerceTermLocalService = commerceTermLocalService;
	}

	private CommerceTermLocalService _commerceTermLocalService;

}