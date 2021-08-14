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

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce notification queue entry service. This utility wraps <code>com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationQueueEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryPersistence
 * @generated
 */
public class CommerceNotificationQueueEntryUtil {

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
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		getPersistence().clearCache(commerceNotificationQueueEntry);
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
	public static Map<Serializable, CommerceNotificationQueueEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceNotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceNotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceNotificationQueueEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceNotificationQueueEntry update(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		return getPersistence().update(commerceNotificationQueueEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceNotificationQueueEntry update(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceNotificationQueueEntry, serviceContext);
	}

	/**
	 * Returns all the commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId) {

		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry[] findByGroupId_PrevAndNext(
			long commerceNotificationQueueEntryId, long groupId,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			commerceNotificationQueueEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the commerce notification queue entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification queue entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator<CommerceNotificationQueueEntry>
				orderByComparator) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry
			findByCommerceNotificationTemplateId_First(
				long commerceNotificationTemplateId,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByCommerceNotificationTemplateId_First(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry
		fetchByCommerceNotificationTemplateId_First(
			long commerceNotificationTemplateId,
			OrderByComparator<CommerceNotificationQueueEntry>
				orderByComparator) {

		return getPersistence().fetchByCommerceNotificationTemplateId_First(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry
			findByCommerceNotificationTemplateId_Last(
				long commerceNotificationTemplateId,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByCommerceNotificationTemplateId_Last(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry
		fetchByCommerceNotificationTemplateId_Last(
			long commerceNotificationTemplateId,
			OrderByComparator<CommerceNotificationQueueEntry>
				orderByComparator) {

		return getPersistence().fetchByCommerceNotificationTemplateId_Last(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry[]
			findByCommerceNotificationTemplateId_PrevAndNext(
				long commerceNotificationQueueEntryId,
				long commerceNotificationTemplateId,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().
			findByCommerceNotificationTemplateId_PrevAndNext(
				commerceNotificationQueueEntryId,
				commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Removes all the commerce notification queue entries where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	public static void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		getPersistence().removeByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns the number of commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification queue entries
	 */
	public static int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		return getPersistence().countByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns all the commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findBySent(
		boolean sent) {

		return getPersistence().findBySent(sent);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end) {

		return getPersistence().findBySent(sent, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().findBySent(sent, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findBySent(
		boolean sent, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySent(
			sent, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findBySent_First(
			boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findBySent_First(sent, orderByComparator);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchBySent_First(
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchBySent_First(sent, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findBySent_Last(
			boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findBySent_Last(sent, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchBySent_Last(
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchBySent_Last(sent, orderByComparator);
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry[] findBySent_PrevAndNext(
			long commerceNotificationQueueEntryId, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findBySent_PrevAndNext(
			commerceNotificationQueueEntryId, sent, orderByComparator);
	}

	/**
	 * Removes all the commerce notification queue entries where sent = &#63; from the database.
	 *
	 * @param sent the sent
	 */
	public static void removeBySent(boolean sent) {
		getPersistence().removeBySent(sent);
	}

	/**
	 * Returns the number of commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the number of matching commerce notification queue entries
	 */
	public static int countBySent(boolean sent) {
		return getPersistence().countBySent(sent);
	}

	/**
	 * Returns all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate) {

		return getPersistence().findByLtSentDate(sentDate);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end) {

		return getPersistence().findByLtSentDate(sentDate, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().findByLtSentDate(
			sentDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByLtSentDate(
		Date sentDate, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtSentDate(
			sentDate, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findByLtSentDate_First(
			Date sentDate,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByLtSentDate_First(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchByLtSentDate_First(
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByLtSentDate_First(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findByLtSentDate_Last(
			Date sentDate,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByLtSentDate_Last(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchByLtSentDate_Last(
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByLtSentDate_Last(
			sentDate, orderByComparator);
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry[] findByLtSentDate_PrevAndNext(
			long commerceNotificationQueueEntryId, Date sentDate,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByLtSentDate_PrevAndNext(
			commerceNotificationQueueEntryId, sentDate, orderByComparator);
	}

	/**
	 * Removes all the commerce notification queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	public static void removeByLtSentDate(Date sentDate) {
		getPersistence().removeByLtSentDate(sentDate);
	}

	/**
	 * Returns the number of commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching commerce notification queue entries
	 */
	public static int countByLtSentDate(Date sentDate) {
		return getPersistence().countByLtSentDate(sentDate);
	}

	/**
	 * Returns all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent, int start,
		int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_C_S(
			groupId, classNameId, classPK, sent, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findByG_C_C_S_First(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByG_C_C_S_First(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchByG_C_C_S_First(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByG_C_C_S_First(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry findByG_C_C_S_Last(
			long groupId, long classNameId, long classPK, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByG_C_C_S_Last(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	public static CommerceNotificationQueueEntry fetchByG_C_C_S_Last(
		long groupId, long classNameId, long classPK, boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().fetchByG_C_C_S_Last(
			groupId, classNameId, classPK, sent, orderByComparator);
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry[] findByG_C_C_S_PrevAndNext(
			long commerceNotificationQueueEntryId, long groupId,
			long classNameId, long classPK, boolean sent,
			OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByG_C_C_S_PrevAndNext(
			commerceNotificationQueueEntryId, groupId, classNameId, classPK,
			sent, orderByComparator);
	}

	/**
	 * Removes all the commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 */
	public static void removeByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		getPersistence().removeByG_C_C_S(groupId, classNameId, classPK, sent);
	}

	/**
	 * Returns the number of commerce notification queue entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and sent = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param sent the sent
	 * @return the number of matching commerce notification queue entries
	 */
	public static int countByG_C_C_S(
		long groupId, long classNameId, long classPK, boolean sent) {

		return getPersistence().countByG_C_C_S(
			groupId, classNameId, classPK, sent);
	}

	/**
	 * Caches the commerce notification queue entry in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntry the commerce notification queue entry
	 */
	public static void cacheResult(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		getPersistence().cacheResult(commerceNotificationQueueEntry);
	}

	/**
	 * Caches the commerce notification queue entries in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntries the commerce notification queue entries
	 */
	public static void cacheResult(
		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries) {

		getPersistence().cacheResult(commerceNotificationQueueEntries);
	}

	/**
	 * Creates a new commerce notification queue entry with the primary key. Does not add the commerce notification queue entry to the database.
	 *
	 * @param commerceNotificationQueueEntryId the primary key for the new commerce notification queue entry
	 * @return the new commerce notification queue entry
	 */
	public static CommerceNotificationQueueEntry create(
		long commerceNotificationQueueEntryId) {

		return getPersistence().create(commerceNotificationQueueEntryId);
	}

	/**
	 * Removes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry remove(
			long commerceNotificationQueueEntryId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().remove(commerceNotificationQueueEntryId);
	}

	public static CommerceNotificationQueueEntry updateImpl(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {

		return getPersistence().updateImpl(commerceNotificationQueueEntry);
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or throws a <code>NoSuchNotificationQueueEntryException</code> if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry findByPrimaryKey(
			long commerceNotificationQueueEntryId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationQueueEntryException {

		return getPersistence().findByPrimaryKey(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry, or <code>null</code> if a commerce notification queue entry with the primary key could not be found
	 */
	public static CommerceNotificationQueueEntry fetchByPrimaryKey(
		long commerceNotificationQueueEntryId) {

		return getPersistence().fetchByPrimaryKey(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Returns all the commerce notification queue entries.
	 *
	 * @return the commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationQueueEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification queue entries
	 */
	public static List<CommerceNotificationQueueEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce notification queue entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce notification queue entries.
	 *
	 * @return the number of commerce notification queue entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceNotificationQueueEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceNotificationQueueEntryPersistence,
		 CommerceNotificationQueueEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceNotificationQueueEntryPersistence.class);

		ServiceTracker
			<CommerceNotificationQueueEntryPersistence,
			 CommerceNotificationQueueEntryPersistence> serviceTracker =
				new ServiceTracker
					<CommerceNotificationQueueEntryPersistence,
					 CommerceNotificationQueueEntryPersistence>(
						 bundle.getBundleContext(),
						 CommerceNotificationQueueEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}