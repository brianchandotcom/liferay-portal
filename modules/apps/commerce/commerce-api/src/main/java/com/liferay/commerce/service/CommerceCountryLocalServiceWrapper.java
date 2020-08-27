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
 * Provides a wrapper for {@link CommerceCountryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryLocalService
 * @generated
 */
public class CommerceCountryLocalServiceWrapper
	implements CommerceCountryLocalService,
			   ServiceWrapper<CommerceCountryLocalService> {

	public CommerceCountryLocalServiceWrapper(
		CommerceCountryLocalService commerceCountryLocalService) {

		_commerceCountryLocalService = commerceCountryLocalService;
	}

	/**
	 * Adds the commerce country to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceCountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceCountry the commerce country
	 * @return the commerce country that was added
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry addCommerceCountry(
		com.liferay.commerce.model.CommerceCountry commerceCountry) {

		return _commerceCountryLocalService.addCommerceCountry(commerceCountry);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry addCommerceCountry(
			java.util.Map<java.util.Locale, String> nameMap,
			boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.addCommerceCountry(
			nameMap, billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority, active,
			serviceContext);
	}

	/**
	 * Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	 *
	 * @param commerceCountryId the primary key for the new commerce country
	 * @return the new commerce country
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry createCommerceCountry(
		long commerceCountryId) {

		return _commerceCountryLocalService.createCommerceCountry(
			commerceCountryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteCommerceCountries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceCountryLocalService.deleteCommerceCountries(companyId);
	}

	/**
	 * Deletes the commerce country from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceCountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceCountry the commerce country
	 * @return the commerce country that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry deleteCommerceCountry(
			com.liferay.commerce.model.CommerceCountry commerceCountry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.deleteCommerceCountry(
			commerceCountry);
	}

	/**
	 * Deletes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceCountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country that was removed
	 * @throws PortalException if a commerce country with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry deleteCommerceCountry(
			long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.deleteCommerceCountry(
			commerceCountryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceCountryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceCountryLocalService.dynamicQuery();
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

		return _commerceCountryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceCountryModelImpl</code>.
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

		return _commerceCountryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceCountryModelImpl</code>.
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

		return _commerceCountryLocalService.dynamicQuery(
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

		return _commerceCountryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _commerceCountryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry fetchCommerceCountry(
		long commerceCountryId) {

		return _commerceCountryLocalService.fetchCommerceCountry(
			commerceCountryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry fetchCommerceCountry(
			long companyId, int numericISOCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.fetchCommerceCountry(
			companyId, numericISOCode);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry fetchCommerceCountry(
		long companyId, String twoLettersISOCode) {

		return _commerceCountryLocalService.fetchCommerceCountry(
			companyId, twoLettersISOCode);
	}

	/**
	 * Returns the commerce country with the matching UUID and company.
	 *
	 * @param uuid the commerce country's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry
		fetchCommerceCountryByUuidAndCompanyId(String uuid, long companyId) {

		return _commerceCountryLocalService.
			fetchCommerceCountryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceCountryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getBillingCommerceCountries(
			long companyId, boolean billingAllowed, boolean active) {

		return _commerceCountryLocalService.getBillingCommerceCountries(
			companyId, billingAllowed, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getBillingCommerceCountriesByChannelId(
			long commerceChannelId, int start, int end) {

		return _commerceCountryLocalService.
			getBillingCommerceCountriesByChannelId(
				commerceChannelId, start, end);
	}

	/**
	 * Returns a range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of commerce countries
	 */
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getCommerceCountries(int start, int end) {

		return _commerceCountryLocalService.getCommerceCountries(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getCommerceCountries(long companyId, boolean active) {

		return _commerceCountryLocalService.getCommerceCountries(
			companyId, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getCommerceCountries(
			long companyId, boolean active, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceCountry>
					orderByComparator) {

		return _commerceCountryLocalService.getCommerceCountries(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getCommerceCountries(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceCountry>
					orderByComparator) {

		return _commerceCountryLocalService.getCommerceCountries(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce countries.
	 *
	 * @return the number of commerce countries
	 */
	@Override
	public int getCommerceCountriesCount() {
		return _commerceCountryLocalService.getCommerceCountriesCount();
	}

	@Override
	public int getCommerceCountriesCount(long companyId) {
		return _commerceCountryLocalService.getCommerceCountriesCount(
			companyId);
	}

	@Override
	public int getCommerceCountriesCount(long companyId, boolean active) {
		return _commerceCountryLocalService.getCommerceCountriesCount(
			companyId, active);
	}

	/**
	 * Returns the commerce country with the primary key.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country
	 * @throws PortalException if a commerce country with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry(
			long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.getCommerceCountry(
			commerceCountryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry(
			long companyId, String twoLettersISOCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.getCommerceCountry(
			companyId, twoLettersISOCode);
	}

	/**
	 * Returns the commerce country with the matching UUID and company.
	 *
	 * @param uuid the commerce country's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce country
	 * @throws PortalException if a matching commerce country could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry
			getCommerceCountryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.
			getCommerceCountryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _commerceCountryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceCountryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceCountryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getShippingCommerceCountries(
			long companyId, boolean shippingAllowed, boolean active) {

		return _commerceCountryLocalService.getShippingCommerceCountries(
			companyId, shippingAllowed, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getShippingCommerceCountriesByChannelId(
			long commerceChannelId, int start, int end) {

		return _commerceCountryLocalService.
			getShippingCommerceCountriesByChannelId(
				commerceChannelId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getWarehouseCommerceCountries(long companyId, boolean all) {

		return _commerceCountryLocalService.getWarehouseCommerceCountries(
			companyId, all);
	}

	@Override
	public void importDefaultCountries(
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws Exception {

		_commerceCountryLocalService.importDefaultCountries(serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
				long companyId, Boolean active, String keywords, int start,
				int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.searchCommerceCountries(
			companyId, active, keywords, start, end, sort);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
				com.liferay.portal.kernel.search.SearchContext searchContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.searchCommerceCountries(
			searchContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry setActive(
			long commerceCountryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.setActive(
			commerceCountryId, active);
	}

	/**
	 * Updates the commerce country in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceCountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceCountry the commerce country
	 * @return the commerce country that was updated
	 */
	@Override
	public com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
		com.liferay.commerce.model.CommerceCountry commerceCountry) {

		return _commerceCountryLocalService.updateCommerceCountry(
			commerceCountry);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
			long commerceCountryId,
			java.util.Map<java.util.Locale, String> nameMap,
			boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.updateCommerceCountry(
			commerceCountryId, nameMap, billingAllowed, shippingAllowed,
			twoLettersISOCode, threeLettersISOCode, numericISOCode,
			subjectToVAT, priority, active, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry
			updateCommerceCountryChannelFilter(
				long commerceCountryId, boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryLocalService.updateCommerceCountryChannelFilter(
			commerceCountryId, enable);
	}

	@Override
	public CommerceCountryLocalService getWrappedService() {
		return _commerceCountryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceCountryLocalService commerceCountryLocalService) {

		_commerceCountryLocalService = commerceCountryLocalService;
	}

	private CommerceCountryLocalService _commerceCountryLocalService;

}