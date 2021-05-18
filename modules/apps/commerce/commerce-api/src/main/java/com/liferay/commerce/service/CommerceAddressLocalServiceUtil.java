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

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CommerceAddress. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceAddressLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressLocalService
 * @generated
 */
public class CommerceAddressLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceAddressLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce address to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAddress the commerce address
	 * @return the commerce address that was added
	 */
	public static CommerceAddress addCommerceAddress(
		CommerceAddress commerceAddress) {

		return getService().addCommerceAddress(commerceAddress);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	public static CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	public static CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	public static CommerceAddress addCommerceAddress(
			String externalReferenceCode, String className, long classPK,
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, String phoneNumber, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddress(
			externalReferenceCode, className, classPK, name, description,
			street1, street2, street3, city, zip, regionId, countryId,
			phoneNumber, type, serviceContext);
	}

	public static CommerceAddress copyCommerceAddress(
			long commerceAddressId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyCommerceAddress(
			commerceAddressId, className, classPK, serviceContext);
	}

	/**
	 * Creates a new commerce address with the primary key. Does not add the commerce address to the database.
	 *
	 * @param commerceAddressId the primary key for the new commerce address
	 * @return the new commerce address
	 */
	public static CommerceAddress createCommerceAddress(
		long commerceAddressId) {

		return getService().createCommerceAddress(commerceAddressId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce address from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAddress the commerce address
	 * @return the commerce address that was removed
	 * @throws PortalException
	 */
	public static CommerceAddress deleteCommerceAddress(
			CommerceAddress commerceAddress)
		throws PortalException {

		return getService().deleteCommerceAddress(commerceAddress);
	}

	/**
	 * Deletes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address that was removed
	 * @throws PortalException if a commerce address with the primary key could not be found
	 */
	public static CommerceAddress deleteCommerceAddress(long commerceAddressId)
		throws PortalException {

		return getService().deleteCommerceAddress(commerceAddressId);
	}

	public static void deleteCommerceAddresses(String className, long classPK)
		throws PortalException {

		getService().deleteCommerceAddresses(className, classPK);
	}

	public static void deleteCountryCommerceAddresses(long countryId)
		throws PortalException {

		getService().deleteCountryCommerceAddresses(countryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRegionCommerceAddresses(long regionId)
		throws PortalException {

		getService().deleteRegionCommerceAddresses(regionId);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CommerceAddress fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceAddress fetchCommerceAddress(long commerceAddressId) {
		return getService().fetchCommerceAddress(commerceAddressId);
	}

	/**
	 * Returns the commerce address with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce address's external reference code
	 * @return the matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	public static CommerceAddress fetchCommerceAddressByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchCommerceAddressByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceAddressByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	public static CommerceAddress fetchCommerceAddressByReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchCommerceAddressByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceAddress geolocateCommerceAddress(
			long commerceAddressId)
		throws PortalException {

		return getService().geolocateCommerceAddress(commerceAddressId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<CommerceAddress> getBillingAndShippingCommerceAddresses(
		long companyId, String className, long classPK) {

		return getService().getBillingAndShippingCommerceAddresses(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		return getService().getBillingCommerceAddresses(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getBillingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	public static int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		return getService().getBillingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	/**
	 * Returns the commerce address with the primary key.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address
	 * @throws PortalException if a commerce address with the primary key could not be found
	 */
	public static CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		return getService().getCommerceAddress(commerceAddressId);
	}

	/**
	 * Returns the commerce address with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce address's external reference code
	 * @return the matching commerce address
	 * @throws PortalException if a matching commerce address could not be found
	 */
	public static CommerceAddress getCommerceAddressByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getCommerceAddressByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns a range of all the commerce addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceAddressModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of commerce addresses
	 */
	public static List<CommerceAddress> getCommerceAddresses(
		int start, int end) {

		return getService().getCommerceAddresses(start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static List<CommerceAddress> getCommerceAddresses(
		long groupId, String className, long classPK) {

		return getService().getCommerceAddresses(groupId, className, classPK);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static List<CommerceAddress> getCommerceAddresses(
		long groupId, String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return getService().getCommerceAddresses(
			groupId, className, classPK, start, end, orderByComparator);
	}

	public static List<CommerceAddress> getCommerceAddresses(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return getService().getCommerceAddresses(
			className, classPK, start, end, orderByComparator);
	}

	public static List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK) {

		return getService().getCommerceAddressesByCompanyId(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return getService().getCommerceAddressesByCompanyId(
			companyId, className, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce addresses.
	 *
	 * @return the number of commerce addresses
	 */
	public static int getCommerceAddressesCount() {
		return getService().getCommerceAddressesCount();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static int getCommerceAddressesCount(
		long groupId, String className, long classPK) {

		return getService().getCommerceAddressesCount(
			groupId, className, classPK);
	}

	public static int getCommerceAddressesCount(
		String className, long classPK) {

		return getService().getCommerceAddressesCount(className, classPK);
	}

	public static int getCommerceAddressesCountByCompanyId(
		long companyId, String className, long classPK) {

		return getService().getCommerceAddressesCountByCompanyId(
			companyId, className, classPK);
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		return getService().getShippingCommerceAddresses(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getShippingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	public static int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		return getService().getShippingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company. Don't need to pass groupId
	 */
	@Deprecated
	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceAddress> searchCommerceAddresses(
				long companyId, long groupId, String className, long classPK,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceAddresses(
			companyId, groupId, className, classPK, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceAddress> searchCommerceAddresses(
				long companyId, String className, long classPK, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	/**
	 * Updates the commerce address in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAddress the commerce address
	 * @return the commerce address that was updated
	 */
	public static CommerceAddress updateCommerceAddress(
		CommerceAddress commerceAddress) {

		return getService().updateCommerceAddress(commerceAddress);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	public static CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	public static CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	public static CommerceAddressLocalService getService() {
		return _service;
	}

	private static volatile CommerceAddressLocalService _service;

}