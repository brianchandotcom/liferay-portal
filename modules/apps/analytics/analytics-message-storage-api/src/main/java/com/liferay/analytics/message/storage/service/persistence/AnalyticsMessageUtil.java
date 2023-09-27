/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.service.persistence;

import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the analytics message service. This utility wraps <code>com.liferay.analytics.message.storage.service.persistence.impl.AnalyticsMessagePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessagePersistence
 * @generated
 */
public class AnalyticsMessageUtil {

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
	public static void clearCache(AnalyticsMessage analyticsMessage) {
		getPersistence().clearCache(analyticsMessage);
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
	public static Map<Serializable, AnalyticsMessage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AnalyticsMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnalyticsMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnalyticsMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AnalyticsMessage update(AnalyticsMessage analyticsMessage) {
		return getPersistence().update(analyticsMessage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AnalyticsMessage update(
		AnalyticsMessage analyticsMessage, ServiceContext serviceContext) {

		return getPersistence().update(analyticsMessage, serviceContext);
	}

	/**
	 * Returns all the analytics messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching analytics messages
	 */
	public static List<AnalyticsMessage> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of matching analytics messages
	 */
	public static List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics messages
	 */
	public static List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics messages where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics messages
	 */
	public static List<AnalyticsMessage> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message
	 * @throws NoSuchMessageException if a matching analytics message could not be found
	 */
	public static AnalyticsMessage findByCompanyId_First(
			long companyId,
			OrderByComparator<AnalyticsMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics message, or <code>null</code> if a matching analytics message could not be found
	 */
	public static AnalyticsMessage fetchByCompanyId_First(
		long companyId, OrderByComparator<AnalyticsMessage> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message
	 * @throws NoSuchMessageException if a matching analytics message could not be found
	 */
	public static AnalyticsMessage findByCompanyId_Last(
			long companyId,
			OrderByComparator<AnalyticsMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics message, or <code>null</code> if a matching analytics message could not be found
	 */
	public static AnalyticsMessage fetchByCompanyId_Last(
		long companyId, OrderByComparator<AnalyticsMessage> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the analytics messages before and after the current analytics message in the ordered set where companyId = &#63;.
	 *
	 * @param analyticsMessageId the primary key of the current analytics message
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics message
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	public static AnalyticsMessage[] findByCompanyId_PrevAndNext(
			long analyticsMessageId, long companyId,
			OrderByComparator<AnalyticsMessage> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageException {

		return getPersistence().findByCompanyId_PrevAndNext(
			analyticsMessageId, companyId, orderByComparator);
	}

	/**
	 * Removes all the analytics messages where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of analytics messages where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching analytics messages
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Caches the analytics message in the entity cache if it is enabled.
	 *
	 * @param analyticsMessage the analytics message
	 */
	public static void cacheResult(AnalyticsMessage analyticsMessage) {
		getPersistence().cacheResult(analyticsMessage);
	}

	/**
	 * Caches the analytics messages in the entity cache if it is enabled.
	 *
	 * @param analyticsMessages the analytics messages
	 */
	public static void cacheResult(List<AnalyticsMessage> analyticsMessages) {
		getPersistence().cacheResult(analyticsMessages);
	}

	/**
	 * Creates a new analytics message with the primary key. Does not add the analytics message to the database.
	 *
	 * @param analyticsMessageId the primary key for the new analytics message
	 * @return the new analytics message
	 */
	public static AnalyticsMessage create(long analyticsMessageId) {
		return getPersistence().create(analyticsMessageId);
	}

	/**
	 * Removes the analytics message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message that was removed
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	public static AnalyticsMessage remove(long analyticsMessageId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageException {

		return getPersistence().remove(analyticsMessageId);
	}

	public static AnalyticsMessage updateImpl(
		AnalyticsMessage analyticsMessage) {

		return getPersistence().updateImpl(analyticsMessage);
	}

	/**
	 * Returns the analytics message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message
	 * @throws NoSuchMessageException if a analytics message with the primary key could not be found
	 */
	public static AnalyticsMessage findByPrimaryKey(long analyticsMessageId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchMessageException {

		return getPersistence().findByPrimaryKey(analyticsMessageId);
	}

	/**
	 * Returns the analytics message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message, or <code>null</code> if a analytics message with the primary key could not be found
	 */
	public static AnalyticsMessage fetchByPrimaryKey(long analyticsMessageId) {
		return getPersistence().fetchByPrimaryKey(analyticsMessageId);
	}

	/**
	 * Returns all the analytics messages.
	 *
	 * @return the analytics messages
	 */
	public static List<AnalyticsMessage> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of analytics messages
	 */
	public static List<AnalyticsMessage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics messages
	 */
	public static List<AnalyticsMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics messages
	 */
	public static List<AnalyticsMessage> findAll(
		int start, int end,
		OrderByComparator<AnalyticsMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the analytics messages from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of analytics messages.
	 *
	 * @return the number of analytics messages
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AnalyticsMessagePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AnalyticsMessagePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AnalyticsMessagePersistence _persistence;

}