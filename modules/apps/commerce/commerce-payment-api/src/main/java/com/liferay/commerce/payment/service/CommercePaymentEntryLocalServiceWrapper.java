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

package com.liferay.commerce.payment.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePaymentEntryLocalService}.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryLocalService
 * @generated
 */
public class CommercePaymentEntryLocalServiceWrapper
	implements CommercePaymentEntryLocalService,
			   ServiceWrapper<CommercePaymentEntryLocalService> {

	public CommercePaymentEntryLocalServiceWrapper() {
		this(null);
	}

	public CommercePaymentEntryLocalServiceWrapper(
		CommercePaymentEntryLocalService commercePaymentEntryLocalService) {

		_commercePaymentEntryLocalService = commercePaymentEntryLocalService;
	}

	/**
	 * Adds the commerce payment entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePaymentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePaymentEntry the commerce payment entry
	 * @return the commerce payment entry that was added
	 */
	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
		addCommercePaymentEntry(
			com.liferay.commerce.payment.model.CommercePaymentEntry
				commercePaymentEntry) {

		return _commercePaymentEntryLocalService.addCommercePaymentEntry(
			commercePaymentEntry);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			addCommercePaymentEntry(
				long userId, long classNameId, long classPK,
				java.math.BigDecimal amount, String currencyCode,
				String paymentMethodName, String transactionCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryLocalService.addCommercePaymentEntry(
			userId, classNameId, classPK, amount, currencyCode,
			paymentMethodName, transactionCode, serviceContext);
	}

	/**
	 * Creates a new commerce payment entry with the primary key. Does not add the commerce payment entry to the database.
	 *
	 * @param commercePaymentEntryId the primary key for the new commerce payment entry
	 * @return the new commerce payment entry
	 */
	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
		createCommercePaymentEntry(long commercePaymentEntryId) {

		return _commercePaymentEntryLocalService.createCommercePaymentEntry(
			commercePaymentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	@Override
	public void deleteCommercePaymentEntries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePaymentEntryLocalService.deleteCommercePaymentEntries(
			companyId);
	}

	/**
	 * Deletes the commerce payment entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePaymentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePaymentEntry the commerce payment entry
	 * @return the commerce payment entry that was removed
	 */
	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
		deleteCommercePaymentEntry(
			com.liferay.commerce.payment.model.CommercePaymentEntry
				commercePaymentEntry) {

		return _commercePaymentEntryLocalService.deleteCommercePaymentEntry(
			commercePaymentEntry);
	}

	/**
	 * Deletes the commerce payment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePaymentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePaymentEntryId the primary key of the commerce payment entry
	 * @return the commerce payment entry that was removed
	 * @throws PortalException if a commerce payment entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			deleteCommercePaymentEntry(long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryLocalService.deleteCommercePaymentEntry(
			commercePaymentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commercePaymentEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commercePaymentEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commercePaymentEntryLocalService.dynamicQuery();
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

		return _commercePaymentEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.payment.model.impl.CommercePaymentEntryModelImpl</code>.
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

		return _commercePaymentEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.payment.model.impl.CommercePaymentEntryModelImpl</code>.
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

		return _commercePaymentEntryLocalService.dynamicQuery(
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

		return _commercePaymentEntryLocalService.dynamicQueryCount(
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

		return _commercePaymentEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
		fetchCommercePaymentEntry(long commercePaymentEntryId) {

		return _commercePaymentEntryLocalService.fetchCommercePaymentEntry(
			commercePaymentEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commercePaymentEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the commerce payment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.payment.model.impl.CommercePaymentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entries
	 * @param end the upper bound of the range of commerce payment entries (not inclusive)
	 * @return the range of commerce payment entries
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry>
			getCommercePaymentEntries(int start, int end) {

		return _commercePaymentEntryLocalService.getCommercePaymentEntries(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry>
			getCommercePaymentEntries(
				long companyId, long classNameId, long classPK, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.payment.model.CommercePaymentEntry>
						orderByComparator) {

		return _commercePaymentEntryLocalService.getCommercePaymentEntries(
			companyId, classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce payment entries.
	 *
	 * @return the number of commerce payment entries
	 */
	@Override
	public int getCommercePaymentEntriesCount() {
		return _commercePaymentEntryLocalService.
			getCommercePaymentEntriesCount();
	}

	@Override
	public int getCommercePaymentEntriesCount(
		long companyId, long classNameId, long classPK) {

		return _commercePaymentEntryLocalService.getCommercePaymentEntriesCount(
			companyId, classNameId, classPK);
	}

	/**
	 * Returns the commerce payment entry with the primary key.
	 *
	 * @param commercePaymentEntryId the primary key of the commerce payment entry
	 * @return the commerce payment entry
	 * @throws PortalException if a commerce payment entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			getCommercePaymentEntry(long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryLocalService.getCommercePaymentEntry(
			commercePaymentEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commercePaymentEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePaymentEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.payment.model.CommercePaymentEntry>
			searchCommercePaymentEntries(
				long companyId, String keywords,
				java.util.LinkedHashMap<String, Object> params, int start,
				int end, String orderByField, boolean reverse) {

		return _commercePaymentEntryLocalService.searchCommercePaymentEntries(
			companyId, keywords, params, start, end, orderByField, reverse);
	}

	/**
	 * Updates the commerce payment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePaymentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePaymentEntry the commerce payment entry
	 * @return the commerce payment entry that was updated
	 */
	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
		updateCommercePaymentEntry(
			com.liferay.commerce.payment.model.CommercePaymentEntry
				commercePaymentEntry) {

		return _commercePaymentEntryLocalService.updateCommercePaymentEntry(
			commercePaymentEntry);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			updateCommercePaymentEntry(
				long commercePaymentEntryId, int paymentStatus,
				String transactionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryLocalService.updateCommercePaymentEntry(
			commercePaymentEntryId, paymentStatus, transactionCode);
	}

	@Override
	public CommercePaymentEntryLocalService getWrappedService() {
		return _commercePaymentEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommercePaymentEntryLocalService commercePaymentEntryLocalService) {

		_commercePaymentEntryLocalService = commercePaymentEntryLocalService;
	}

	private CommercePaymentEntryLocalService _commercePaymentEntryLocalService;

}