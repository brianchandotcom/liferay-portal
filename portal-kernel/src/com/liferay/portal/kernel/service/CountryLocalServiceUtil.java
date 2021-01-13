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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for Country. This utility wraps
 * <code>com.liferay.portal.service.impl.CountryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CountryLocalService
 * @generated
 */
public class CountryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.CountryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static com.liferay.portal.kernel.model.Country addCountry(
		com.liferay.portal.kernel.model.Country country) {

		return getService().addCountry(country);
	}

	public static com.liferay.portal.kernel.model.Country addCountry(
			String a2, String a3, boolean active, boolean billingAllowed,
			String idd, String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCountry(
			a2, a3, active, billingAllowed, idd, name, number, position,
			shippingAllowed, subjectToVAT, zipRequired, serviceContext);
	}

	/**
	 * Creates a new country with the primary key. Does not add the country to the database.
	 *
	 * @param countryId the primary key for the new country
	 * @return the new country
	 */
	public static com.liferay.portal.kernel.model.Country createCountry(
		long countryId) {

		return getService().createCountry(countryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteCompanyCountries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCompanyCountries(companyId);
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
	public static com.liferay.portal.kernel.model.Country deleteCountry(
			com.liferay.portal.kernel.model.Country country)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCountry(country);
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
	public static com.liferay.portal.kernel.model.Country deleteCountry(
			long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCountry(countryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.kernel.model.Country fetchCountry(
		long countryId) {

		return getService().fetchCountry(countryId);
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByA2(
		long companyId, String a2) {

		return getService().fetchCountryByA2(companyId, a2);
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByA3(
		long companyId, String a3) {

		return getService().fetchCountryByA3(companyId, a3);
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByName(
		long companyId, String name) {

		return getService().fetchCountryByName(companyId, name);
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByNumber(
		long companyId, String number) {

		return getService().fetchCountryByNumber(companyId, number);
	}

	/**
	 * Returns the country with the matching UUID and company.
	 *
	 * @param uuid the country's UUID
	 * @param companyId the primary key of the company
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static com.liferay.portal.kernel.model.Country
		fetchCountryByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchCountryByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.model.CountryLocalization
		fetchCountryLocalization(long countryId, String languageId) {

		return getService().fetchCountryLocalization(countryId, languageId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(long companyId) {

		return getService().getCompanyCountries(companyId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(long companyId, boolean active) {

		return getService().getCompanyCountries(companyId, active);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			long companyId, boolean active, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		return getService().getCompanyCountries(
			companyId, active, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		return getService().getCompanyCountries(
			companyId, start, end, orderByComparator);
	}

	public static int getCompanyCountriesCount(long companyId) {
		return getService().getCompanyCountriesCount(companyId);
	}

	public static int getCompanyCountriesCount(long companyId, boolean active) {
		return getService().getCompanyCountriesCount(companyId, active);
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
	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCountries(int start, int end) {

		return getService().getCountries(start, end);
	}

	/**
	 * Returns the number of countries.
	 *
	 * @return the number of countries
	 */
	public static int getCountriesCount() {
		return getService().getCountriesCount();
	}

	/**
	 * Returns the country with the primary key.
	 *
	 * @param countryId the primary key of the country
	 * @return the country
	 * @throws PortalException if a country with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Country getCountry(
			long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCountry(countryId);
	}

	public static com.liferay.portal.kernel.model.Country getCountryByA2(
			long companyId, String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCountryByA2(companyId, a2);
	}

	public static com.liferay.portal.kernel.model.Country getCountryByA3(
			long companyId, String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCountryByA3(companyId, a3);
	}

	public static com.liferay.portal.kernel.model.Country getCountryByName(
			long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCountryByName(companyId, name);
	}

	public static com.liferay.portal.kernel.model.Country getCountryByNumber(
			long companyId, String number)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCountryByNumber(companyId, number);
	}

	/**
	 * Returns the country with the matching UUID and company.
	 *
	 * @param uuid the country's UUID
	 * @param companyId the primary key of the company
	 * @return the matching country
	 * @throws PortalException if a matching country could not be found
	 */
	public static com.liferay.portal.kernel.model.Country
			getCountryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCountryByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.model.CountryLocalization
			getCountryLocalization(long countryId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCountryLocalization(countryId, languageId);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.CountryLocalization>
			getCountryLocalizations(long countryId) {

		return getService().getCountryLocalizations(countryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.portal.kernel.model.Country> searchCountries(
				long companyId, Boolean active, String keywords, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.Country> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCountries(
			companyId, active, keywords, start, end, orderByComparator);
	}

	public static com.liferay.portal.kernel.model.Country updateActive(
			long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateActive(countryId, active);
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
	public static com.liferay.portal.kernel.model.Country updateCountry(
		com.liferay.portal.kernel.model.Country country) {

		return getService().updateCountry(country);
	}

	public static com.liferay.portal.kernel.model.Country updateCountry(
			long countryId, String a2, String a3, boolean active,
			boolean billingAllowed, String idd, String name, String number,
			double position, boolean shippingAllowed, boolean subjectToVAT)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCountry(
			countryId, a2, a3, active, billingAllowed, idd, name, number,
			position, shippingAllowed, subjectToVAT);
	}

	public static com.liferay.portal.kernel.model.CountryLocalization
			updateCountryLocalization(
				com.liferay.portal.kernel.model.Country country,
				String languageId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCountryLocalization(
			country, languageId, title);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.CountryLocalization>
				updateCountryLocalizations(
					com.liferay.portal.kernel.model.Country country,
					java.util.Map<String, String> titleMap)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCountryLocalizations(country, titleMap);
	}

	public static com.liferay.portal.kernel.model.Country
			updateGroupFilterEnabled(long countryId, boolean groupFilterEnabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateGroupFilterEnabled(
			countryId, groupFilterEnabled);
	}

	public static CountryLocalService getService() {
		if (_service == null) {
			_service = (CountryLocalService)PortalBeanLocatorUtil.locate(
				CountryLocalService.class.getName());
		}

		return _service;
	}

	private static CountryLocalService _service;

}