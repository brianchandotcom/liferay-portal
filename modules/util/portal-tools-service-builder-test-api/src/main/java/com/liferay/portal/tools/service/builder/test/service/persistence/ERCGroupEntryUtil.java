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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the erc group entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.ERCGroupEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntryPersistence
 * @generated
 */
public class ERCGroupEntryUtil {

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
	public static void clearCache(ERCGroupEntry ercGroupEntry) {
		getPersistence().clearCache(ercGroupEntry);
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
	public static Map<Serializable, ERCGroupEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ERCGroupEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ERCGroupEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ERCGroupEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ERCGroupEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ERCGroupEntry update(ERCGroupEntry ercGroupEntry) {
		return getPersistence().update(ercGroupEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ERCGroupEntry update(
		ERCGroupEntry ercGroupEntry, ServiceContext serviceContext) {

		return getPersistence().update(ercGroupEntry, serviceContext);
	}

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCGroupEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entry
	 * @throws NoSuchERCGroupEntryException if a matching erc group entry could not be found
	 */
	public static ERCGroupEntry findByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntryException {

		return getPersistence().findByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	public static ERCGroupEntry fetchByG_ERC(
		long groupId, String externalReferenceCode) {

		return getPersistence().fetchByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc group entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	public static ERCGroupEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByG_ERC(
			groupId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the erc group entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc group entry that was removed
	 */
	public static ERCGroupEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntryException {

		return getPersistence().removeByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the number of erc group entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc group entries
	 */
	public static int countByG_ERC(long groupId, String externalReferenceCode) {
		return getPersistence().countByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Caches the erc group entry in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntry the erc group entry
	 */
	public static void cacheResult(ERCGroupEntry ercGroupEntry) {
		getPersistence().cacheResult(ercGroupEntry);
	}

	/**
	 * Caches the erc group entries in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntries the erc group entries
	 */
	public static void cacheResult(List<ERCGroupEntry> ercGroupEntries) {
		getPersistence().cacheResult(ercGroupEntries);
	}

	/**
	 * Creates a new erc group entry with the primary key. Does not add the erc group entry to the database.
	 *
	 * @param ercGroupEntryId the primary key for the new erc group entry
	 * @return the new erc group entry
	 */
	public static ERCGroupEntry create(long ercGroupEntryId) {
		return getPersistence().create(ercGroupEntryId);
	}

	/**
	 * Removes the erc group entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry that was removed
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	public static ERCGroupEntry remove(long ercGroupEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntryException {

		return getPersistence().remove(ercGroupEntryId);
	}

	public static ERCGroupEntry updateImpl(ERCGroupEntry ercGroupEntry) {
		return getPersistence().updateImpl(ercGroupEntry);
	}

	/**
	 * Returns the erc group entry with the primary key or throws a <code>NoSuchERCGroupEntryException</code> if it could not be found.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry
	 * @throws NoSuchERCGroupEntryException if a erc group entry with the primary key could not be found
	 */
	public static ERCGroupEntry findByPrimaryKey(long ercGroupEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntryException {

		return getPersistence().findByPrimaryKey(ercGroupEntryId);
	}

	/**
	 * Returns the erc group entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry, or <code>null</code> if a erc group entry with the primary key could not be found
	 */
	public static ERCGroupEntry fetchByPrimaryKey(long ercGroupEntryId) {
		return getPersistence().fetchByPrimaryKey(ercGroupEntryId);
	}

	/**
	 * Returns all the erc group entries.
	 *
	 * @return the erc group entries
	 */
	public static List<ERCGroupEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @return the range of erc group entries
	 */
	public static List<ERCGroupEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc group entries
	 */
	public static List<ERCGroupEntry> findAll(
		int start, int end,
		OrderByComparator<ERCGroupEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc group entries
	 */
	public static List<ERCGroupEntry> findAll(
		int start, int end, OrderByComparator<ERCGroupEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the erc group entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of erc group entries.
	 *
	 * @return the number of erc group entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ERCGroupEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ERCGroupEntryPersistence, ERCGroupEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ERCGroupEntryPersistence.class);

		ServiceTracker<ERCGroupEntryPersistence, ERCGroupEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<ERCGroupEntryPersistence, ERCGroupEntryPersistence>(
						bundle.getBundleContext(),
						ERCGroupEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}