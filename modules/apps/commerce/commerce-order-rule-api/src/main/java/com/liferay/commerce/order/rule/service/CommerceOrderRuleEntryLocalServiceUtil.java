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

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CommerceOrderRuleEntry. This utility wraps
 * <code>com.liferay.commerce.order.rule.service.impl.CommerceOrderRuleEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryLocalService
 * @generated
 */
public class CommerceOrderRuleEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.order.rule.service.impl.CommerceOrderRuleEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce order rule entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was added
	 */
	public static CommerceOrderRuleEntry addCommerceOrderRuleEntry(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		return getService().addCommerceOrderRuleEntry(commerceOrderRuleEntry);
	}

	public static CommerceOrderRuleEntry addCommerceOrderRuleEntry(
			String externalReferenceCode, long userId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String type, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceOrderRuleEntry(
			externalReferenceCode, userId, active, description,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, name, priority, type, typeSettings, serviceContext);
	}

	public static void checkCommerceOrderRuleEntries() throws PortalException {
		getService().checkCommerceOrderRuleEntries();
	}

	/**
	 * Creates a new commerce order rule entry with the primary key. Does not add the commerce order rule entry to the database.
	 *
	 * @param commerceOrderRuleEntryId the primary key for the new commerce order rule entry
	 * @return the new commerce order rule entry
	 */
	public static CommerceOrderRuleEntry createCommerceOrderRuleEntry(
		long commerceOrderRuleEntryId) {

		return getService().createCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
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
	 * Deletes the commerce order rule entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws PortalException
	 */
	public static CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			CommerceOrderRuleEntry commerceOrderRuleEntry)
		throws PortalException {

		return getService().deleteCommerceOrderRuleEntry(
			commerceOrderRuleEntry);
	}

	/**
	 * Deletes the commerce order rule entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException {

		return getService().deleteCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
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

	public static CommerceOrderRuleEntry fetchCommerceOrderRuleEntry(
		long commerceOrderRuleEntryId) {

		return getService().fetchCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry
		fetchCommerceOrderRuleEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommerceOrderRuleEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceOrderRuleEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	public static CommerceOrderRuleEntry
		fetchCommerceOrderRuleEntryByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommerceOrderRuleEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static List<CommerceOrderRuleEntry>
		getAccountAndChannelAndOrderTypeCommerceRuleEntries(
			long companyId, long accountEntryId, long commerceChannelId,
			long commerceOrderTypeId) {

		return getService().getAccountAndChannelAndOrderTypeCommerceRuleEntries(
			companyId, accountEntryId, commerceChannelId, commerceOrderTypeId);
	}

	public static List<CommerceOrderRuleEntry>
		getAccountAndChannelCommerceRuleEntries(
			long companyId, long accountEntryId, long commerceChannelId) {

		return getService().getAccountAndChannelCommerceRuleEntries(
			companyId, accountEntryId, commerceChannelId);
	}

	public static List<CommerceOrderRuleEntry>
		getAccountAndOrderTypeCommerceRuleEntries(
			long companyId, long accountEntryId, long commerceOrderTypeId) {

		return getService().getAccountAndOrderTypeCommerceRuleEntries(
			companyId, accountEntryId, commerceOrderTypeId);
	}

	public static List<CommerceOrderRuleEntry> getAccountCommerceRuleEntries(
		long companyId, long accountEntryId) {

		return getService().getAccountCommerceRuleEntries(
			companyId, accountEntryId);
	}

	public static List<CommerceOrderRuleEntry>
		getAccountGroupsAndChannelAndOrderTypeCommerceRuleEntries(
			long companyId, long[] accountGroupIds, long commerceChannelId,
			long commerceOrderTypeId) {

		return getService().
			getAccountGroupsAndChannelAndOrderTypeCommerceRuleEntries(
				companyId, accountGroupIds, commerceChannelId,
				commerceOrderTypeId);
	}

	public static List<CommerceOrderRuleEntry>
		getAccountGroupsAndChannelCommerceRuleEntries(
			long companyId, long[] accountGroupIds, long commerceChannelId) {

		return getService().getAccountGroupsAndChannelCommerceRuleEntries(
			companyId, accountGroupIds, commerceChannelId);
	}

	public static List<CommerceOrderRuleEntry>
		getAccountGroupsAndOrderTypeCommerceRuleEntries(
			long companyId, long[] accountGroupIds, long commerceOrderTypeId) {

		return getService().getAccountGroupsAndOrderTypeCommerceRuleEntries(
			companyId, accountGroupIds, commerceOrderTypeId);
	}

	public static List<CommerceOrderRuleEntry>
		getAccountGroupsCommerceRuleEntries(
			long companyId, long[] accountGroupIds) {

		return getService().getAccountGroupsCommerceRuleEntries(
			companyId, accountGroupIds);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<CommerceOrderRuleEntry>
		getChannelAndOrderTypeCommerceRuleEntries(
			long companyId, long commerceChannelId, long commerceOrderTypeId) {

		return getService().getChannelAndOrderTypeCommerceRuleEntries(
			companyId, commerceChannelId, commerceOrderTypeId);
	}

	public static List<CommerceOrderRuleEntry> getChannelCommerceRuleEntries(
		long companyId, long commerceChannelId) {

		return getService().getChannelCommerceRuleEntries(
			companyId, commerceChannelId);
	}

	/**
	 * Returns a range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		int start, int end) {

		return getService().getCommerceOrderRuleEntries(start, end);
	}

	public static List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, int start, int end) {

		return getService().getCommerceOrderRuleEntries(
			companyId, active, start, end);
	}

	public static List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, String type, int start, int end) {

		return getService().getCommerceOrderRuleEntries(
			companyId, active, type, start, end);
	}

	public static List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, String type, int start, int end) {

		return getService().getCommerceOrderRuleEntries(
			companyId, type, start, end);
	}

	/**
	 * Returns the number of commerce order rule entries.
	 *
	 * @return the number of commerce order rule entries
	 */
	public static int getCommerceOrderRuleEntriesCount() {
		return getService().getCommerceOrderRuleEntriesCount();
	}

	/**
	 * Returns the commerce order rule entry with the primary key.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry getCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException {

		return getService().getCommerceOrderRuleEntry(commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry
	 * @throws PortalException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry
			getCommerceOrderRuleEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static List<CommerceOrderRuleEntry> getOrderTypeCommerceRuleEntries(
		long companyId, long commerceOrderTypeId) {

		return getService().getOrderTypeCommerceRuleEntries(
			companyId, commerceOrderTypeId);
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
	 * Updates the commerce order rule entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was updated
	 */
	public static CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		return getService().updateCommerceOrderRuleEntry(
			commerceOrderRuleEntry);
	}

	public static CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
			long userId, long commerceOrderRuleEntryId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceOrderRuleEntry(
			userId, commerceOrderRuleEntryId, active, description,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, name, priority, typeSettings, serviceContext);
	}

	public static CommerceOrderRuleEntry updateStatus(
			long userId, long commerceOrderRuleEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateStatus(
			userId, commerceOrderRuleEntryId, status, serviceContext);
	}

	public static CommerceOrderRuleEntryLocalService getService() {
		return _service;
	}

	private static volatile CommerceOrderRuleEntryLocalService _service;

}