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
 * Provides a wrapper for {@link CommerceAddressLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressLocalService
 * @generated
 */
public class CommerceAddressLocalServiceWrapper
	implements CommerceAddressLocalService,
			   ServiceWrapper<CommerceAddressLocalService> {

	public CommerceAddressLocalServiceWrapper(
		CommerceAddressLocalService commerceAddressLocalService) {

		_commerceAddressLocalService = commerceAddressLocalService;
	}

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
	@Override
	public com.liferay.commerce.model.CommerceAddress addCommerceAddress(
		com.liferay.commerce.model.CommerceAddress commerceAddress) {

		return _commerceAddressLocalService.addCommerceAddress(commerceAddress);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress addCommerceAddress(
			String externalReferenceCode, String className, long classPK,
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, String phoneNumber, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.addCommerceAddress(
			externalReferenceCode, className, classPK, name, description,
			street1, street2, street3, city, zip, regionId, countryId,
			phoneNumber, type, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress copyCommerceAddress(
			long commerceAddressId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.copyCommerceAddress(
			commerceAddressId, className, classPK, serviceContext);
	}

	/**
	 * Creates a new commerce address with the primary key. Does not add the commerce address to the database.
	 *
	 * @param commerceAddressId the primary key for the new commerce address
	 * @return the new commerce address
	 */
	@Override
	public com.liferay.commerce.model.CommerceAddress createCommerceAddress(
		long commerceAddressId) {

		return _commerceAddressLocalService.createCommerceAddress(
			commerceAddressId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.createPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.commerce.model.CommerceAddress deleteCommerceAddress(
			com.liferay.commerce.model.CommerceAddress commerceAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.deleteCommerceAddress(
			commerceAddress);
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
	@Override
	public com.liferay.commerce.model.CommerceAddress deleteCommerceAddress(
			long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.deleteCommerceAddress(
			commerceAddressId);
	}

	@Override
	public void deleteCommerceAddresses(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAddressLocalService.deleteCommerceAddresses(
			className, classPK);
	}

	@Override
	public void deleteCountryCommerceAddresses(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAddressLocalService.deleteCountryCommerceAddresses(countryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deleteRegionCommerceAddresses(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAddressLocalService.deleteRegionCommerceAddresses(regionId);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAddressLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceAddressLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAddressLocalService.dynamicQuery();
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

		return _commerceAddressLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _commerceAddressLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _commerceAddressLocalService.dynamicQuery(
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

		return _commerceAddressLocalService.dynamicQueryCount(dynamicQuery);
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

		return _commerceAddressLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress
		fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId) {

		return _commerceAddressLocalService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress fetchCommerceAddress(
		long commerceAddressId) {

		return _commerceAddressLocalService.fetchCommerceAddress(
			commerceAddressId);
	}

	/**
	 * Returns the commerce address with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce address's external reference code
	 * @return the matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceAddress
		fetchCommerceAddressByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAddressLocalService.
			fetchCommerceAddressByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceAddressByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceAddress
		fetchCommerceAddressByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAddressLocalService.fetchCommerceAddressByReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress geolocateCommerceAddress(
			long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.geolocateCommerceAddress(
			commerceAddressId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceAddressLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
		getBillingAndShippingCommerceAddresses(
			long companyId, String className, long classPK) {

		return _commerceAddressLocalService.
			getBillingAndShippingCommerceAddresses(
				companyId, className, classPK);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
			getBillingCommerceAddresses(
				long companyId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getBillingCommerceAddresses(
			companyId, className, classPK);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
			getBillingCommerceAddresses(
				long companyId, String className, long classPK, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getBillingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getBillingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	/**
	 * Returns the commerce address with the primary key.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address
	 * @throws PortalException if a commerce address with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceAddress getCommerceAddress(
			long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getCommerceAddress(
			commerceAddressId);
	}

	/**
	 * Returns the commerce address with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce address's external reference code
	 * @return the matching commerce address
	 * @throws PortalException if a matching commerce address could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceAddress
			getCommerceAddressByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.
			getCommerceAddressByExternalReferenceCode(
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
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
		getCommerceAddresses(int start, int end) {

		return _commerceAddressLocalService.getCommerceAddresses(start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
		getCommerceAddresses(long groupId, String className, long classPK) {

		return _commerceAddressLocalService.getCommerceAddresses(
			groupId, className, classPK);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
		getCommerceAddresses(
			long groupId, String className, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceAddress>
					orderByComparator) {

		return _commerceAddressLocalService.getCommerceAddresses(
			groupId, className, classPK, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
		getCommerceAddresses(
			String className, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceAddress>
					orderByComparator) {

		return _commerceAddressLocalService.getCommerceAddresses(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
		getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK) {

		return _commerceAddressLocalService.getCommerceAddressesByCompanyId(
			companyId, className, classPK);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
		getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceAddress>
					orderByComparator) {

		return _commerceAddressLocalService.getCommerceAddressesByCompanyId(
			companyId, className, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce addresses.
	 *
	 * @return the number of commerce addresses
	 */
	@Override
	public int getCommerceAddressesCount() {
		return _commerceAddressLocalService.getCommerceAddressesCount();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public int getCommerceAddressesCount(
		long groupId, String className, long classPK) {

		return _commerceAddressLocalService.getCommerceAddressesCount(
			groupId, className, classPK);
	}

	@Override
	public int getCommerceAddressesCount(String className, long classPK) {
		return _commerceAddressLocalService.getCommerceAddressesCount(
			className, classPK);
	}

	@Override
	public int getCommerceAddressesCountByCompanyId(
		long companyId, String className, long classPK) {

		return _commerceAddressLocalService.
			getCommerceAddressesCountByCompanyId(companyId, className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceAddressLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAddressLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
			getShippingCommerceAddresses(
				long companyId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getShippingCommerceAddresses(
			companyId, className, classPK);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress>
			getShippingCommerceAddresses(
				long companyId, String className, long classPK, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getShippingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.getShippingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company. Don't need to pass groupId
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceAddress> searchCommerceAddresses(
				long companyId, long groupId, String className, long classPK,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.searchCommerceAddresses(
			companyId, groupId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceAddress> searchCommerceAddresses(
				long companyId, String className, long classPK, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.searchCommerceAddresses(
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
	@Override
	public com.liferay.commerce.model.CommerceAddress updateCommerceAddress(
		com.liferay.commerce.model.CommerceAddress commerceAddress) {

		return _commerceAddressLocalService.updateCommerceAddress(
			commerceAddress);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.updateCommerceAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAddressLocalService.updateCommerceAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	@Override
	public CommerceAddressLocalService getWrappedService() {
		return _commerceAddressLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAddressLocalService commerceAddressLocalService) {

		_commerceAddressLocalService = commerceAddressLocalService;
	}

	private CommerceAddressLocalService _commerceAddressLocalService;

}