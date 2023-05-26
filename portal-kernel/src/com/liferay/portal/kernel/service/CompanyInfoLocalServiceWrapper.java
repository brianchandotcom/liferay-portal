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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link CompanyInfoLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CompanyInfoLocalService
 * @generated
 */
public class CompanyInfoLocalServiceWrapper
	implements CompanyInfoLocalService,
			   ServiceWrapper<CompanyInfoLocalService> {

	public CompanyInfoLocalServiceWrapper() {
		this(null);
	}

	public CompanyInfoLocalServiceWrapper(
		CompanyInfoLocalService companyInfoLocalService) {

		_companyInfoLocalService = companyInfoLocalService;
	}

	/**
	 * Adds the company info to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyInfoLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param companyInfo the company info
	 * @return the company info that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.CompanyInfo addCompanyInfo(
		com.liferay.portal.kernel.model.CompanyInfo companyInfo) {

		return _companyInfoLocalService.addCompanyInfo(companyInfo);
	}

	/**
	 * Creates a new company info with the primary key. Does not add the company info to the database.
	 *
	 * @param companyInfoId the primary key for the new company info
	 * @return the new company info
	 */
	@Override
	public com.liferay.portal.kernel.model.CompanyInfo createCompanyInfo(
		long companyInfoId) {

		return _companyInfoLocalService.createCompanyInfo(companyInfoId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _companyInfoLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the company info from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyInfoLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param companyInfo the company info
	 * @return the company info that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.CompanyInfo deleteCompanyInfo(
		com.liferay.portal.kernel.model.CompanyInfo companyInfo) {

		return _companyInfoLocalService.deleteCompanyInfo(companyInfo);
	}

	/**
	 * Deletes the company info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyInfoLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info that was removed
	 * @throws PortalException if a company info with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.CompanyInfo deleteCompanyInfo(
			long companyInfoId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _companyInfoLocalService.deleteCompanyInfo(companyInfoId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _companyInfoLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _companyInfoLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _companyInfoLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _companyInfoLocalService.dynamicQuery();
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

		return _companyInfoLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.CompanyInfoModelImpl</code>.
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

		return _companyInfoLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.CompanyInfoModelImpl</code>.
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

		return _companyInfoLocalService.dynamicQuery(
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

		return _companyInfoLocalService.dynamicQueryCount(dynamicQuery);
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

		return _companyInfoLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.CompanyInfo fetchCompany(
		long companyId) {

		return _companyInfoLocalService.fetchCompany(companyId);
	}

	@Override
	public com.liferay.portal.kernel.model.CompanyInfo fetchCompanyInfo(
		long companyInfoId) {

		return _companyInfoLocalService.fetchCompanyInfo(companyInfoId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _companyInfoLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the company info with the primary key.
	 *
	 * @param companyInfoId the primary key of the company info
	 * @return the company info
	 * @throws PortalException if a company info with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.CompanyInfo getCompanyInfo(
			long companyInfoId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _companyInfoLocalService.getCompanyInfo(companyInfoId);
	}

	/**
	 * Returns a range of all the company infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.CompanyInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company infos
	 * @param end the upper bound of the range of company infos (not inclusive)
	 * @return the range of company infos
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.CompanyInfo>
		getCompanyInfos(int start, int end) {

		return _companyInfoLocalService.getCompanyInfos(start, end);
	}

	/**
	 * Returns the number of company infos.
	 *
	 * @return the number of company infos
	 */
	@Override
	public int getCompanyInfosCount() {
		return _companyInfoLocalService.getCompanyInfosCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _companyInfoLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _companyInfoLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _companyInfoLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the company info in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyInfoLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param companyInfo the company info
	 * @return the company info that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.CompanyInfo updateCompanyInfo(
		com.liferay.portal.kernel.model.CompanyInfo companyInfo) {

		return _companyInfoLocalService.updateCompanyInfo(companyInfo);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _companyInfoLocalService.getBasePersistence();
	}

	@Override
	public CompanyInfoLocalService getWrappedService() {
		return _companyInfoLocalService;
	}

	@Override
	public void setWrappedService(
		CompanyInfoLocalService companyInfoLocalService) {

		_companyInfoLocalService = companyInfoLocalService;
	}

	private CompanyInfoLocalService _companyInfoLocalService;

}