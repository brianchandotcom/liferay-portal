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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTMessage;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ct message service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTMessagePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTMessagePersistence
 * @generated
 */
public class CTMessageUtil {

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
	public static void clearCache(CTMessage ctMessage) {
		getPersistence().clearCache(ctMessage);
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
	public static Map<Serializable, CTMessage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTMessage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTMessage> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTMessage update(CTMessage ctMessage) {
		return getPersistence().update(ctMessage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTMessage update(
		CTMessage ctMessage, ServiceContext serviceContext) {

		return getPersistence().update(ctMessage, serviceContext);
	}

	/**
	 * Returns all the ct messages where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct messages
	 */
	public static List<CTMessage> findByCtCollectionId(long ctCollectionId) {
		return getPersistence().findByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @return the range of matching ct messages
	 */
	public static List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct messages
	 */
	public static List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTMessage> orderByComparator) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct messages where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct messages
	 */
	public static List<CTMessage> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct message
	 * @throws NoSuchMessageException if a matching ct message could not be found
	 */
	public static CTMessage findByCtCollectionId_First(
			long ctCollectionId, OrderByComparator<CTMessage> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchMessageException {

		return getPersistence().findByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct message, or <code>null</code> if a matching ct message could not be found
	 */
	public static CTMessage fetchByCtCollectionId_First(
		long ctCollectionId, OrderByComparator<CTMessage> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct message
	 * @throws NoSuchMessageException if a matching ct message could not be found
	 */
	public static CTMessage findByCtCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTMessage> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchMessageException {

		return getPersistence().findByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct message, or <code>null</code> if a matching ct message could not be found
	 */
	public static CTMessage fetchByCtCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTMessage> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct messages before and after the current ct message in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctMessageId the primary key of the current ct message
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct message
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	public static CTMessage[] findByCtCollectionId_PrevAndNext(
			long ctMessageId, long ctCollectionId,
			OrderByComparator<CTMessage> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchMessageException {

		return getPersistence().findByCtCollectionId_PrevAndNext(
			ctMessageId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct messages where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCtCollectionId(long ctCollectionId) {
		getPersistence().removeByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct messages where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct messages
	 */
	public static int countByCtCollectionId(long ctCollectionId) {
		return getPersistence().countByCtCollectionId(ctCollectionId);
	}

	/**
	 * Caches the ct message in the entity cache if it is enabled.
	 *
	 * @param ctMessage the ct message
	 */
	public static void cacheResult(CTMessage ctMessage) {
		getPersistence().cacheResult(ctMessage);
	}

	/**
	 * Caches the ct messages in the entity cache if it is enabled.
	 *
	 * @param ctMessages the ct messages
	 */
	public static void cacheResult(List<CTMessage> ctMessages) {
		getPersistence().cacheResult(ctMessages);
	}

	/**
	 * Creates a new ct message with the primary key. Does not add the ct message to the database.
	 *
	 * @param ctMessageId the primary key for the new ct message
	 * @return the new ct message
	 */
	public static CTMessage create(long ctMessageId) {
		return getPersistence().create(ctMessageId);
	}

	/**
	 * Removes the ct message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message that was removed
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	public static CTMessage remove(long ctMessageId)
		throws com.liferay.change.tracking.exception.NoSuchMessageException {

		return getPersistence().remove(ctMessageId);
	}

	public static CTMessage updateImpl(CTMessage ctMessage) {
		return getPersistence().updateImpl(ctMessage);
	}

	/**
	 * Returns the ct message with the primary key or throws a <code>NoSuchMessageException</code> if it could not be found.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message
	 * @throws NoSuchMessageException if a ct message with the primary key could not be found
	 */
	public static CTMessage findByPrimaryKey(long ctMessageId)
		throws com.liferay.change.tracking.exception.NoSuchMessageException {

		return getPersistence().findByPrimaryKey(ctMessageId);
	}

	/**
	 * Returns the ct message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctMessageId the primary key of the ct message
	 * @return the ct message, or <code>null</code> if a ct message with the primary key could not be found
	 */
	public static CTMessage fetchByPrimaryKey(long ctMessageId) {
		return getPersistence().fetchByPrimaryKey(ctMessageId);
	}

	/**
	 * Returns all the ct messages.
	 *
	 * @return the ct messages
	 */
	public static List<CTMessage> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @return the range of ct messages
	 */
	public static List<CTMessage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct messages
	 */
	public static List<CTMessage> findAll(
		int start, int end, OrderByComparator<CTMessage> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct messages
	 * @param end the upper bound of the range of ct messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct messages
	 */
	public static List<CTMessage> findAll(
		int start, int end, OrderByComparator<CTMessage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct messages from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct messages.
	 *
	 * @return the number of ct messages
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTMessagePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTMessagePersistence, CTMessagePersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTMessagePersistence.class);

		ServiceTracker<CTMessagePersistence, CTMessagePersistence>
			serviceTracker =
				new ServiceTracker<CTMessagePersistence, CTMessagePersistence>(
					bundle.getBundleContext(), CTMessagePersistence.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}