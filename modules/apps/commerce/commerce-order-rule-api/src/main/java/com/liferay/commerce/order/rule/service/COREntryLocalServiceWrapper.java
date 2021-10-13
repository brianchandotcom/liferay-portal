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

package com.liferay.commerce.order.rule.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link COREntryLocalService}.
 *
 * @author Luca Pellizzon
 * @see COREntryLocalService
 * @generated
 */
public class COREntryLocalServiceWrapper
	implements COREntryLocalService, ServiceWrapper<COREntryLocalService> {

	public COREntryLocalServiceWrapper(
		COREntryLocalService corEntryLocalService) {

		_corEntryLocalService = corEntryLocalService;
	}

	/**
	 * Adds the cor entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect COREntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param corEntry the cor entry
	 * @return the cor entry that was added
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry addCOREntry(
		com.liferay.commerce.order.rule.model.COREntry corEntry) {

		return _corEntryLocalService.addCOREntry(corEntry);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry addCOREntry(
			String externalReferenceCode, long userId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String type, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.addCOREntry(
			externalReferenceCode, userId, active, description,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, name, priority, type, typeSettings, serviceContext);
	}

	@Override
	public void checkCOREntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		_corEntryLocalService.checkCOREntries();
	}

	/**
	 * Creates a new cor entry with the primary key. Does not add the cor entry to the database.
	 *
	 * @param COREntryId the primary key for the new cor entry
	 * @return the new cor entry
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry createCOREntry(
		long COREntryId) {

		return _corEntryLocalService.createCOREntry(COREntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the cor entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect COREntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param corEntry the cor entry
	 * @return the cor entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry deleteCOREntry(
			com.liferay.commerce.order.rule.model.COREntry corEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.deleteCOREntry(corEntry);
	}

	/**
	 * Deletes the cor entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect COREntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param COREntryId the primary key of the cor entry
	 * @return the cor entry that was removed
	 * @throws PortalException if a cor entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry deleteCOREntry(
			long COREntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.deleteCOREntry(COREntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _corEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _corEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _corEntryLocalService.dynamicQuery();
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

		return _corEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.COREntryModelImpl</code>.
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

		return _corEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.COREntryModelImpl</code>.
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

		return _corEntryLocalService.dynamicQuery(
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

		return _corEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _corEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry fetchCOREntry(
		long COREntryId) {

		return _corEntryLocalService.fetchCOREntry(COREntryId);
	}

	/**
	 * Returns the cor entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the cor entry's external reference code
	 * @return the matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry
		fetchCOREntryByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _corEntryLocalService.fetchCOREntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCOREntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.order.rule.model.COREntry
		fetchCOREntryByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _corEntryLocalService.fetchCOREntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountEntryAndCommerceChannelAndCommerceOrderTypeCOREntries(
			long companyId, long accountEntryId, long commerceChannelId,
			long commerceOrderTypeId) {

		return _corEntryLocalService.
			getAccountEntryAndCommerceChannelAndCommerceOrderTypeCOREntries(
				companyId, accountEntryId, commerceChannelId,
				commerceOrderTypeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountEntryAndCommerceChannelCOREntries(
			long companyId, long accountEntryId, long commerceChannelId) {

		return _corEntryLocalService.
			getAccountEntryAndCommerceChannelCOREntries(
				companyId, accountEntryId, commerceChannelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountEntryAndCommerceOrderTypeCOREntries(
			long companyId, long accountEntryId, long commerceOrderTypeId) {

		return _corEntryLocalService.
			getAccountEntryAndCommerceOrderTypeCOREntries(
				companyId, accountEntryId, commerceOrderTypeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountEntryCOREntries(long companyId, long accountEntryId) {

		return _corEntryLocalService.getAccountEntryCOREntries(
			companyId, accountEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountGroupsAndCommerceChannelAndCommerceOrderTypeCOREntries(
			long companyId, long[] accountGroupIds, long commerceChannelId,
			long commerceOrderTypeId) {

		return _corEntryLocalService.
			getAccountGroupsAndCommerceChannelAndCommerceOrderTypeCOREntries(
				companyId, accountGroupIds, commerceChannelId,
				commerceOrderTypeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountGroupsAndCommerceChannelCOREntries(
			long companyId, long[] accountGroupIds, long commerceChannelId) {

		return _corEntryLocalService.
			getAccountGroupsAndCommerceChannelCOREntries(
				companyId, accountGroupIds, commerceChannelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountGroupsAndCommerceOrderTypeCOREntries(
			long companyId, long[] accountGroupIds, long commerceOrderTypeId) {

		return _corEntryLocalService.
			getAccountGroupsAndCommerceOrderTypeCOREntries(
				companyId, accountGroupIds, commerceOrderTypeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getAccountGroupsCOREntries(long companyId, long[] accountGroupIds) {

		return _corEntryLocalService.getAccountGroupsCOREntries(
			companyId, accountGroupIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _corEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getCommerceChannelAndCommerceOrderTypeCOREntries(
			long companyId, long commerceChannelId, long commerceOrderTypeId) {

		return _corEntryLocalService.
			getCommerceChannelAndCommerceOrderTypeCOREntries(
				companyId, commerceChannelId, commerceOrderTypeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getCommerceChannelCOREntries(long companyId, long commerceChannelId) {

		return _corEntryLocalService.getCommerceChannelCOREntries(
			companyId, commerceChannelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getCommerceOrderTypeCOREntries(
			long companyId, long commerceOrderTypeId) {

		return _corEntryLocalService.getCommerceOrderTypeCOREntries(
			companyId, commerceOrderTypeId);
	}

	/**
	 * Returns a range of all the cor entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.COREntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entries
	 * @param end the upper bound of the range of cor entries (not inclusive)
	 * @return the range of cor entries
	 */
	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getCOREntries(int start, int end) {

		return _corEntryLocalService.getCOREntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getCOREntries(long companyId, boolean active, int start, int end) {

		return _corEntryLocalService.getCOREntries(
			companyId, active, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getCOREntries(
			long companyId, boolean active, String type, int start, int end) {

		return _corEntryLocalService.getCOREntries(
			companyId, active, type, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.order.rule.model.COREntry>
		getCOREntries(long companyId, String type, int start, int end) {

		return _corEntryLocalService.getCOREntries(companyId, type, start, end);
	}

	/**
	 * Returns the number of cor entries.
	 *
	 * @return the number of cor entries
	 */
	@Override
	public int getCOREntriesCount() {
		return _corEntryLocalService.getCOREntriesCount();
	}

	/**
	 * Returns the cor entry with the primary key.
	 *
	 * @param COREntryId the primary key of the cor entry
	 * @return the cor entry
	 * @throws PortalException if a cor entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry getCOREntry(
			long COREntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.getCOREntry(COREntryId);
	}

	/**
	 * Returns the cor entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the cor entry's external reference code
	 * @return the matching cor entry
	 * @throws PortalException if a matching cor entry could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry
			getCOREntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.getCOREntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _corEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _corEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the cor entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect COREntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param corEntry the cor entry
	 * @return the cor entry that was updated
	 */
	@Override
	public com.liferay.commerce.order.rule.model.COREntry updateCOREntry(
		com.liferay.commerce.order.rule.model.COREntry corEntry) {

		return _corEntryLocalService.updateCOREntry(corEntry);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry updateCOREntry(
			long userId, long corEntryId, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.updateCOREntry(
			userId, corEntryId, active, description, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, name,
			priority, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry
			updateCOREntryExternalReferenceCode(
				String externalReferenceCode, long corEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.updateCOREntryExternalReferenceCode(
			externalReferenceCode, corEntryId);
	}

	@Override
	public com.liferay.commerce.order.rule.model.COREntry updateStatus(
			long userId, long corEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _corEntryLocalService.updateStatus(
			userId, corEntryId, status, serviceContext);
	}

	@Override
	public COREntryLocalService getWrappedService() {
		return _corEntryLocalService;
	}

	@Override
	public void setWrappedService(COREntryLocalService corEntryLocalService) {
		_corEntryLocalService = corEntryLocalService;
	}

	private COREntryLocalService _corEntryLocalService;

}