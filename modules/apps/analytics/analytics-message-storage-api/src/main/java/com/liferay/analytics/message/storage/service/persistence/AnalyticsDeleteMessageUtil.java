/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.service.persistence;

import com.liferay.analytics.message.storage.model.AnalyticsDeleteMessage;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the analytics delete message service. This utility wraps <code>com.liferay.analytics.message.storage.service.persistence.impl.AnalyticsDeleteMessagePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsDeleteMessagePersistence
 * @generated
 */
public class AnalyticsDeleteMessageUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		getPersistence().clearCache(analyticsDeleteMessage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AnalyticsDeleteMessage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AnalyticsDeleteMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnalyticsDeleteMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnalyticsDeleteMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AnalyticsDeleteMessage update(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		return getPersistence().update(analyticsDeleteMessage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AnalyticsDeleteMessage update(
		AnalyticsDeleteMessage analyticsDeleteMessage,
		ServiceContext serviceContext) {

		return getPersistence().update(analyticsDeleteMessage, serviceContext);
	}

	/**
	 * Returns all the analytics delete messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage findByCompanyId_First(
			long companyId,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage findByCompanyId_Last(
			long companyId,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the analytics delete messages before and after the current analytics delete message in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsDeleteMessageId the primary key of the current analytics delete message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public static AnalyticsDeleteMessage[] findByCompanyId_PrevAndNext(
			long analyticsDeleteMessageId, long companyId,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().findByCompanyId_PrevAndNext(
			analyticsDeleteMessageId, companyId, orderByComparator);
	}

	/**
	 * Removes all the analytics delete messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of analytics delete messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics delete messages
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate) {

		return getPersistence().findByC_GtM(companyId, modifiedDate);
	}

	/**
	 * Returns a range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end) {

		return getPersistence().findByC_GtM(
			companyId, modifiedDate, start, end);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().findByC_GtM(
			companyId, modifiedDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findByC_GtM(
		long companyId, Date modifiedDate, int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_GtM(
			companyId, modifiedDate, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage findByC_GtM_First(
			long companyId, Date modifiedDate,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().findByC_GtM_First(
			companyId, modifiedDate, orderByComparator);
	}

	/**
	 * Returns the first analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage fetchByC_GtM_First(
		long companyId, Date modifiedDate,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().fetchByC_GtM_First(
			companyId, modifiedDate, orderByComparator);
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message
	 * @throws NoSuchDeleteMessageException if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage findByC_GtM_Last(
			long companyId, Date modifiedDate,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().findByC_GtM_Last(
			companyId, modifiedDate, orderByComparator);
	}

	/**
	 * Returns the last analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics delete message, or <code>null</code> if a matching analytics delete message could not be found
	 */
	public static AnalyticsDeleteMessage fetchByC_GtM_Last(
		long companyId, Date modifiedDate,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().fetchByC_GtM_Last(
			companyId, modifiedDate, orderByComparator);
	}

	/**
	 * Returns the analytics delete messages before and after the current analytics delete message in the ordered set where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param analyticsDeleteMessageId the primary key of the current analytics delete message
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public static AnalyticsDeleteMessage[] findByC_GtM_PrevAndNext(
			long analyticsDeleteMessageId, long companyId, Date modifiedDate,
			OrderByComparator<AnalyticsDeleteMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().findByC_GtM_PrevAndNext(
			analyticsDeleteMessageId, companyId, modifiedDate,
			orderByComparator);
	}

	/**
	 * Removes all the analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 */
	public static void removeByC_GtM(long companyId, Date modifiedDate) {
		getPersistence().removeByC_GtM(companyId, modifiedDate);
	}

	/**
	 * Returns the number of analytics delete messages where companyId = &#63; and modifiedDate &gt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the number of matching analytics delete messages
	 */
	public static int countByC_GtM(long companyId, Date modifiedDate) {
		return getPersistence().countByC_GtM(companyId, modifiedDate);
	}

	/**
	 * Caches the analytics delete message in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessage the analytics delete message
	 */
	public static void cacheResult(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		getPersistence().cacheResult(analyticsDeleteMessage);
	}

	/**
	 * Caches the analytics delete messages in the entity cache if it is enabled.
	 *
	 * @param analyticsDeleteMessages the analytics delete messages
	 */
	public static void cacheResult(
		List<AnalyticsDeleteMessage> analyticsDeleteMessages) {

		getPersistence().cacheResult(analyticsDeleteMessages);
	}

	/**
	 * Creates a new analytics delete message with the primary key. Does not add the analytics delete message to the database.
	 *
	 * @param analyticsDeleteMessageId the primary key for the new analytics delete message
	 * @return the new analytics delete message
	 */
	public static AnalyticsDeleteMessage create(long analyticsDeleteMessageId) {
		return getPersistence().create(analyticsDeleteMessageId);
	}

	/**
	 * Removes the analytics delete message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message that was removed
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public static AnalyticsDeleteMessage remove(long analyticsDeleteMessageId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().remove(analyticsDeleteMessageId);
	}

	public static AnalyticsDeleteMessage updateImpl(
		AnalyticsDeleteMessage analyticsDeleteMessage) {

		return getPersistence().updateImpl(analyticsDeleteMessage);
	}

	/**
	 * Returns the analytics delete message with the primary key or throws a <code>NoSuchDeleteMessageException</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message
	 * @throws NoSuchDeleteMessageException if a analytics delete message with the primary key could not be found
	 */
	public static AnalyticsDeleteMessage findByPrimaryKey(
			long analyticsDeleteMessageId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchDeleteMessageException {

		return getPersistence().findByPrimaryKey(analyticsDeleteMessageId);
	}

	/**
	 * Returns the analytics delete message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsDeleteMessageId the primary key of the analytics delete message
	 * @return the analytics delete message, or <code>null</code> if a analytics delete message with the primary key could not be found
	 */
	public static AnalyticsDeleteMessage fetchByPrimaryKey(
		long analyticsDeleteMessageId) {

		return getPersistence().fetchByPrimaryKey(analyticsDeleteMessageId);
	}

	/**
	 * Returns all the analytics delete messages.
	 *
	 * @return the analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @return the range of analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics delete messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsDeleteMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics delete messages
	 * @param end the upper bound of the range of analytics delete messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics delete messages
	 */
	public static List<AnalyticsDeleteMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsDeleteMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the analytics delete messages from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of analytics delete messages.
	 *
	 * @return the number of analytics delete messages
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AnalyticsDeleteMessagePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		AnalyticsDeleteMessagePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile AnalyticsDeleteMessagePersistence _persistence;

}