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

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for COREntry. This utility wraps
 * <code>com.liferay.commerce.order.rule.service.impl.COREntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Luca Pellizzon
 * @see COREntryLocalService
 * @generated
 */
public class COREntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.order.rule.service.impl.COREntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static COREntry addCOREntry(COREntry corEntry) {
		return getService().addCOREntry(corEntry);
	}

	public static COREntry addCOREntry(
			String externalReferenceCode, long userId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String type, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCOREntry(
			externalReferenceCode, userId, active, description,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, name, priority, type, typeSettings, serviceContext);
	}

	public static void checkCOREntries() throws PortalException {
		getService().checkCOREntries();
	}

	/**
	 * Creates a new cor entry with the primary key. Does not add the cor entry to the database.
	 *
	 * @param COREntryId the primary key for the new cor entry
	 * @return the new cor entry
	 */
	public static COREntry createCOREntry(long COREntryId) {
		return getService().createCOREntry(COREntryId);
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
	public static COREntry deleteCOREntry(COREntry corEntry)
		throws PortalException {

		return getService().deleteCOREntry(corEntry);
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
	public static COREntry deleteCOREntry(long COREntryId)
		throws PortalException {

		return getService().deleteCOREntry(COREntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.COREntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.COREntryModelImpl</code>.
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

	public static COREntry fetchCOREntry(long COREntryId) {
		return getService().fetchCOREntry(COREntryId);
	}

	/**
	 * Returns the cor entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the cor entry's external reference code
	 * @return the matching cor entry, or <code>null</code> if a matching cor entry could not be found
	 */
	public static COREntry fetchCOREntryByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchCOREntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCOREntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	public static COREntry fetchCOREntryByReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchCOREntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static List<COREntry>
		getAccountEntryAndCommerceChannelAndCommerceOrderTypeCOREntries(
			long companyId, long accountEntryId, long commerceChannelId,
			long commerceOrderTypeId) {

		return getService().
			getAccountEntryAndCommerceChannelAndCommerceOrderTypeCOREntries(
				companyId, accountEntryId, commerceChannelId,
				commerceOrderTypeId);
	}

	public static List<COREntry> getAccountEntryAndCommerceChannelCOREntries(
		long companyId, long accountEntryId, long commerceChannelId) {

		return getService().getAccountEntryAndCommerceChannelCOREntries(
			companyId, accountEntryId, commerceChannelId);
	}

	public static List<COREntry> getAccountEntryAndCommerceOrderTypeCOREntries(
		long companyId, long accountEntryId, long commerceOrderTypeId) {

		return getService().getAccountEntryAndCommerceOrderTypeCOREntries(
			companyId, accountEntryId, commerceOrderTypeId);
	}

	public static List<COREntry> getAccountEntryCOREntries(
		long companyId, long accountEntryId) {

		return getService().getAccountEntryCOREntries(
			companyId, accountEntryId);
	}

	public static List<COREntry>
		getAccountGroupsAndCommerceChannelAndCommerceOrderTypeCOREntries(
			long companyId, long[] accountGroupIds, long commerceChannelId,
			long commerceOrderTypeId) {

		return getService().
			getAccountGroupsAndCommerceChannelAndCommerceOrderTypeCOREntries(
				companyId, accountGroupIds, commerceChannelId,
				commerceOrderTypeId);
	}

	public static List<COREntry> getAccountGroupsAndCommerceChannelCOREntries(
		long companyId, long[] accountGroupIds, long commerceChannelId) {

		return getService().getAccountGroupsAndCommerceChannelCOREntries(
			companyId, accountGroupIds, commerceChannelId);
	}

	public static List<COREntry> getAccountGroupsAndCommerceOrderTypeCOREntries(
		long companyId, long[] accountGroupIds, long commerceOrderTypeId) {

		return getService().getAccountGroupsAndCommerceOrderTypeCOREntries(
			companyId, accountGroupIds, commerceOrderTypeId);
	}

	public static List<COREntry> getAccountGroupsCOREntries(
		long companyId, long[] accountGroupIds) {

		return getService().getAccountGroupsCOREntries(
			companyId, accountGroupIds);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<COREntry>
		getCommerceChannelAndCommerceOrderTypeCOREntries(
			long companyId, long commerceChannelId, long commerceOrderTypeId) {

		return getService().getCommerceChannelAndCommerceOrderTypeCOREntries(
			companyId, commerceChannelId, commerceOrderTypeId);
	}

	public static List<COREntry> getCommerceChannelCOREntries(
		long companyId, long commerceChannelId) {

		return getService().getCommerceChannelCOREntries(
			companyId, commerceChannelId);
	}

	public static List<COREntry> getCommerceOrderTypeCOREntries(
		long companyId, long commerceOrderTypeId) {

		return getService().getCommerceOrderTypeCOREntries(
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
	public static List<COREntry> getCOREntries(int start, int end) {
		return getService().getCOREntries(start, end);
	}

	public static List<COREntry> getCOREntries(
		long companyId, boolean active, int start, int end) {

		return getService().getCOREntries(companyId, active, start, end);
	}

	public static List<COREntry> getCOREntries(
		long companyId, boolean active, String type, int start, int end) {

		return getService().getCOREntries(companyId, active, type, start, end);
	}

	public static List<COREntry> getCOREntries(
		long companyId, String type, int start, int end) {

		return getService().getCOREntries(companyId, type, start, end);
	}

	/**
	 * Returns the number of cor entries.
	 *
	 * @return the number of cor entries
	 */
	public static int getCOREntriesCount() {
		return getService().getCOREntriesCount();
	}

	/**
	 * Returns the cor entry with the primary key.
	 *
	 * @param COREntryId the primary key of the cor entry
	 * @return the cor entry
	 * @throws PortalException if a cor entry with the primary key could not be found
	 */
	public static COREntry getCOREntry(long COREntryId) throws PortalException {
		return getService().getCOREntry(COREntryId);
	}

	/**
	 * Returns the cor entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the cor entry's external reference code
	 * @return the matching cor entry
	 * @throws PortalException if a matching cor entry could not be found
	 */
	public static COREntry getCOREntryByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getCOREntryByExternalReferenceCode(
			companyId, externalReferenceCode);
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
	public static COREntry updateCOREntry(COREntry corEntry) {
		return getService().updateCOREntry(corEntry);
	}

	public static COREntry updateCOREntry(
			long userId, long corEntryId, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCOREntry(
			userId, corEntryId, active, description, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, name,
			priority, typeSettings, serviceContext);
	}

	public static COREntry updateCOREntryExternalReferenceCode(
			String externalReferenceCode, long corEntryId)
		throws PortalException {

		return getService().updateCOREntryExternalReferenceCode(
			externalReferenceCode, corEntryId);
	}

	public static COREntry updateStatus(
			long userId, long corEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateStatus(
			userId, corEntryId, status, serviceContext);
	}

	public static COREntryLocalService getService() {
		return _service;
	}

	private static volatile COREntryLocalService _service;

}