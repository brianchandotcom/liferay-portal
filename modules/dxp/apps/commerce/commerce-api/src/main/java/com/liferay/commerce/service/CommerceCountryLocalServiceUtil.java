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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceCountry. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceCountryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryLocalService
 * @see com.liferay.commerce.service.base.CommerceCountryLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceCountryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCountryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceCountryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce country to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountry the commerce country
	* @return the commerce country that was added
	*/
	public static com.liferay.commerce.model.CommerceCountry addCommerceCountry(
		com.liferay.commerce.model.CommerceCountry commerceCountry) {
		return getService().addCommerceCountry(commerceCountry);
	}

	public static com.liferay.commerce.model.CommerceCountry addCommerceCountry(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceCountry(nameMap, billingAllowed,
			shippingAllowed, twoLettersISOCode, threeLettersISOCode,
			numericISOCode, subjectToVAT, priority, active, serviceContext);
	}

	/**
	* Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	*
	* @param commerceCountryId the primary key for the new commerce country
	* @return the new commerce country
	*/
	public static com.liferay.commerce.model.CommerceCountry createCommerceCountry(
		long commerceCountryId) {
		return getService().createCommerceCountry(commerceCountryId);
	}

	public static void deleteCommerceCountries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceCountries(groupId);
	}

	/**
	* Deletes the commerce country from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountry the commerce country
	* @return the commerce country that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.model.CommerceCountry deleteCommerceCountry(
		com.liferay.commerce.model.CommerceCountry commerceCountry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceCountry(commerceCountry);
	}

	/**
	* Deletes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country that was removed
	* @throws PortalException if a commerce country with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceCountry deleteCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceCountry(commerceCountryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.commerce.model.CommerceCountry fetchCommerceCountry(
		long commerceCountryId) {
		return getService().fetchCommerceCountry(commerceCountryId);
	}

	public static com.liferay.commerce.model.CommerceCountry fetchCommerceCountry(
		long groupId, int numericISOCode)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceCountry(groupId, numericISOCode);
	}

	/**
	* Returns the commerce country matching the UUID and group.
	*
	* @param uuid the commerce country's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static com.liferay.commerce.model.CommerceCountry fetchCommerceCountryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchCommerceCountryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getBillingCommerceCountries(
		long groupId, boolean billingAllowed, boolean active) {
		return getService()
				   .getBillingCommerceCountries(groupId, billingAllowed, active);
	}

	/**
	* Returns a range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of commerce countries
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		int start, int end) {
		return getService().getCommerceCountries(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, boolean active) {
		return getService().getCommerceCountries(groupId, active);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		return getService()
				   .getCommerceCountries(groupId, active, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		return getService()
				   .getCommerceCountries(groupId, start, end, orderByComparator);
	}

	/**
	* Returns all the commerce countries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce countries
	* @param companyId the primary key of the company
	* @return the matching commerce countries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommerceCountriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of commerce countries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce countries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce countries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		return getService()
				   .getCommerceCountriesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce countries.
	*
	* @return the number of commerce countries
	*/
	public static int getCommerceCountriesCount() {
		return getService().getCommerceCountriesCount();
	}

	public static int getCommerceCountriesCount(long groupId) {
		return getService().getCommerceCountriesCount(groupId);
	}

	public static int getCommerceCountriesCount(long groupId, boolean active) {
		return getService().getCommerceCountriesCount(groupId, active);
	}

	/**
	* Returns the commerce country with the primary key.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country
	* @throws PortalException if a commerce country with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceCountry getCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceCountry(commerceCountryId);
	}

	/**
	* Returns the commerce country matching the UUID and group.
	*
	* @param uuid the commerce country's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce country
	* @throws PortalException if a matching commerce country could not be found
	*/
	public static com.liferay.commerce.model.CommerceCountry getCommerceCountryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceCountryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getShippingCommerceCountries(
		long groupId, boolean shippingAllowed, boolean active) {
		return getService()
				   .getShippingCommerceCountries(groupId, shippingAllowed,
			active);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getWarehouseCommerceCountries(
		long groupId, boolean all) {
		return getService().getWarehouseCommerceCountries(groupId, all);
	}

	public static void importDefaultCountries(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws java.lang.Exception {
		getService().importDefaultCountries(serviceContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCommerceCountries(searchContext);
	}

	/**
	* Updates the commerce country in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCountry the commerce country
	* @return the commerce country that was updated
	*/
	public static com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
		com.liferay.commerce.model.CommerceCountry commerceCountry) {
		return getService().updateCommerceCountry(commerceCountry);
	}

	public static com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
		long commerceCountryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceCountry(commerceCountryId, nameMap,
			billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority,
			active, serviceContext);
	}

	public static CommerceCountryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCountryLocalService, CommerceCountryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCountryLocalService.class);
}