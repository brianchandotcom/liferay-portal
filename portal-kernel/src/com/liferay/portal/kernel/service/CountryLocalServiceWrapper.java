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

/**
 * Provides a wrapper for {@link CountryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CountryLocalService
 * @generated
 */
public class CountryLocalServiceWrapper
	implements CountryLocalService, ServiceWrapper<CountryLocalService> {

	public CountryLocalServiceWrapper(CountryLocalService countryLocalService) {
		_countryLocalService = countryLocalService;
	}

	/**
	 * Adds the country to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param country the country
	 * @return the country that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.Country addCountry(
		com.liferay.portal.kernel.model.Country country) {

		return _countryLocalService.addCountry(country);
	}

	@Override
	public com.liferay.portal.kernel.model.Country addCountry(
			java.lang.String a2, java.lang.String a3, boolean active,
			boolean billingAllowed, java.lang.String idd, java.lang.String name,
			java.lang.String number, double position, boolean shippingAllowed,
			boolean subjectToVAT, boolean zipRequired,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.addCountry(
			a2, a3, active, billingAllowed, idd, name, number, position,
			shippingAllowed, subjectToVAT, zipRequired, serviceContext);
	}

	/**
	 * Creates a new country with the primary key. Does not add the country to the database.
	 *
	 * @param countryId the primary key for the new country
	 * @return the new country
	 */
	@Override
	public com.liferay.portal.kernel.model.Country createCountry(
		long countryId) {

		return _countryLocalService.createCountry(countryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteCompanyCountries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_countryLocalService.deleteCompanyCountries(companyId);
	}

	/**
	 * Deletes the country from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param country the country
	 * @return the country that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.Country deleteCountry(
			com.liferay.portal.kernel.model.Country country)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.deleteCountry(country);
	}

	/**
	 * Deletes the country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param countryId the primary key of the country
	 * @return the country that was removed
	 * @throws PortalException if a country with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Country deleteCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.deleteCountry(countryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _countryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _countryLocalService.dynamicQuery();
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

		return _countryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.CountryModelImpl</code>.
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

		return _countryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.CountryModelImpl</code>.
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

		return _countryLocalService.dynamicQuery(
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

		return _countryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _countryLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountry(
		long countryId) {

		return _countryLocalService.fetchCountry(countryId);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA2(
		long companyId, java.lang.String a2) {

		return _countryLocalService.fetchCountryByA2(companyId, a2);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA3(
		long companyId, java.lang.String a3) {

		return _countryLocalService.fetchCountryByA3(companyId, a3);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByName(
		long companyId, java.lang.String name) {

		return _countryLocalService.fetchCountryByName(companyId, name);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByNumber(
		long companyId, java.lang.String number) {

		return _countryLocalService.fetchCountryByNumber(companyId, number);
	}

	/**
	 * Returns the country with the matching UUID and company.
	 *
	 * @param uuid the country's UUID
	 * @param companyId the primary key of the company
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Country
		fetchCountryByUuidAndCompanyId(java.lang.String uuid, long companyId) {

		return _countryLocalService.fetchCountryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.model.CountryLocalization
		fetchCountryLocalization(long countryId, java.lang.String languageId) {

		return _countryLocalService.fetchCountryLocalization(
			countryId, languageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _countryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(long companyId) {

		return _countryLocalService.getCompanyCountries(companyId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(long companyId, boolean active) {

		return _countryLocalService.getCompanyCountries(companyId, active);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			long companyId, boolean active, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		return _countryLocalService.getCompanyCountries(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		return _countryLocalService.getCompanyCountries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyCountriesCount(long companyId) {
		return _countryLocalService.getCompanyCountriesCount(companyId);
	}

	@Override
	public int getCompanyCountriesCount(long companyId, boolean active) {
		return _countryLocalService.getCompanyCountriesCount(companyId, active);
	}

	/**
	 * Returns a range of all the countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.CountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of countries
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country> getCountries(
		int start, int end) {

		return _countryLocalService.getCountries(start, end);
	}

	/**
	 * Returns the number of countries.
	 *
	 * @return the number of countries
	 */
	@Override
	public int getCountriesCount() {
		return _countryLocalService.getCountriesCount();
	}

	/**
	 * Returns the country with the primary key.
	 *
	 * @param countryId the primary key of the country
	 * @return the country
	 * @throws PortalException if a country with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Country getCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getCountry(countryId);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByA2(
			long companyId, java.lang.String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getCountryByA2(companyId, a2);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByA3(
			long companyId, java.lang.String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getCountryByA3(companyId, a3);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByName(
			long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getCountryByName(companyId, name);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByNumber(
			long companyId, java.lang.String number)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getCountryByNumber(companyId, number);
	}

	/**
	 * Returns the country with the matching UUID and company.
	 *
	 * @param uuid the country's UUID
	 * @param companyId the primary key of the company
	 * @return the matching country
	 * @throws PortalException if a matching country could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Country getCountryByUuidAndCompanyId(
			java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getCountryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.model.CountryLocalization
			getCountryLocalization(long countryId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getCountryLocalization(
			countryId, languageId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.CountryLocalization>
		getCountryLocalizations(long countryId) {

		return _countryLocalService.getCountryLocalizations(countryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _countryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _countryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _countryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.portal.kernel.model.Country> searchCountries(
				long companyId, java.lang.Boolean active,
				java.lang.String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.Country> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.searchCountries(
			companyId, active, keywords, start, end, orderByComparator);
	}

	@Override
	public com.liferay.portal.kernel.model.Country updateActive(
			long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.updateActive(countryId, active);
	}

	/**
	 * Updates the country in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CountryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param country the country
	 * @return the country that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.Country updateCountry(
		com.liferay.portal.kernel.model.Country country) {

		return _countryLocalService.updateCountry(country);
	}

	@Override
	public com.liferay.portal.kernel.model.Country updateCountry(
			long countryId, java.lang.String a2, java.lang.String a3,
			boolean active, boolean billingAllowed, java.lang.String idd,
			java.lang.String name, java.lang.String number, double position,
			boolean shippingAllowed, boolean subjectToVAT)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.updateCountry(
			countryId, a2, a3, active, billingAllowed, idd, name, number,
			position, shippingAllowed, subjectToVAT);
	}

	@Override
	public com.liferay.portal.kernel.model.CountryLocalization
			updateCountryLocalization(
				com.liferay.portal.kernel.model.Country country,
				java.lang.String languageId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.updateCountryLocalization(
			country, languageId, title);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.CountryLocalization>
			updateCountryLocalizations(
				com.liferay.portal.kernel.model.Country country,
				java.util.Map<java.lang.String, java.lang.String> titleMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.updateCountryLocalizations(
			country, titleMap);
	}

	@Override
	public com.liferay.portal.kernel.model.Country updateGroupFilterEnabled(
			long countryId, boolean groupFilterEnabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryLocalService.updateGroupFilterEnabled(
			countryId, groupFilterEnabled);
	}

	@Override
	public CountryLocalService getWrappedService() {
		return _countryLocalService;
	}

	@Override
	public void setWrappedService(CountryLocalService countryLocalService) {
		_countryLocalService = countryLocalService;
	}

	private CountryLocalService _countryLocalService;

}