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

package com.liferay.portal.rolling.restart.sample.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.rolling.restart.sample.model.RollingRestartSampleEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the rolling restart sample entry service. This utility wraps <code>com.liferay.portal.rolling.restart.sample.service.persistence.impl.RollingRestartSampleEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RollingRestartSampleEntryPersistence
 * @generated
 */
public class RollingRestartSampleEntryUtil {

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
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		getPersistence().clearCache(rollingRestartSampleEntry);
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
	public static Map<Serializable, RollingRestartSampleEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RollingRestartSampleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RollingRestartSampleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RollingRestartSampleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RollingRestartSampleEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RollingRestartSampleEntry update(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		return getPersistence().update(rollingRestartSampleEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RollingRestartSampleEntry update(
		RollingRestartSampleEntry rollingRestartSampleEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			rollingRestartSampleEntry, serviceContext);
	}

	/**
	 * Caches the rolling restart sample entry in the entity cache if it is enabled.
	 *
	 * @param rollingRestartSampleEntry the rolling restart sample entry
	 */
	public static void cacheResult(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		getPersistence().cacheResult(rollingRestartSampleEntry);
	}

	/**
	 * Caches the rolling restart sample entries in the entity cache if it is enabled.
	 *
	 * @param rollingRestartSampleEntries the rolling restart sample entries
	 */
	public static void cacheResult(
		List<RollingRestartSampleEntry> rollingRestartSampleEntries) {

		getPersistence().cacheResult(rollingRestartSampleEntries);
	}

	/**
	 * Creates a new rolling restart sample entry with the primary key. Does not add the rolling restart sample entry to the database.
	 *
	 * @param entryId the primary key for the new rolling restart sample entry
	 * @return the new rolling restart sample entry
	 */
	public static RollingRestartSampleEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	 * Removes the rolling restart sample entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry that was removed
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	public static RollingRestartSampleEntry remove(long entryId)
		throws com.liferay.portal.rolling.restart.sample.exception.
			NoSuchRollingRestartSampleEntryException {

		return getPersistence().remove(entryId);
	}

	public static RollingRestartSampleEntry updateImpl(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		return getPersistence().updateImpl(rollingRestartSampleEntry);
	}

	/**
	 * Returns the rolling restart sample entry with the primary key or throws a <code>NoSuchRollingRestartSampleEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry
	 * @throws NoSuchRollingRestartSampleEntryException if a rolling restart sample entry with the primary key could not be found
	 */
	public static RollingRestartSampleEntry findByPrimaryKey(long entryId)
		throws com.liferay.portal.rolling.restart.sample.exception.
			NoSuchRollingRestartSampleEntryException {

		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	 * Returns the rolling restart sample entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the rolling restart sample entry
	 * @return the rolling restart sample entry, or <code>null</code> if a rolling restart sample entry with the primary key could not be found
	 */
	public static RollingRestartSampleEntry fetchByPrimaryKey(long entryId) {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns all the rolling restart sample entries.
	 *
	 * @return the rolling restart sample entries
	 */
	public static List<RollingRestartSampleEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @return the range of rolling restart sample entries
	 */
	public static List<RollingRestartSampleEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rolling restart sample entries
	 */
	public static List<RollingRestartSampleEntry> findAll(
		int start, int end,
		OrderByComparator<RollingRestartSampleEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the rolling restart sample entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RollingRestartSampleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rolling restart sample entries
	 * @param end the upper bound of the range of rolling restart sample entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rolling restart sample entries
	 */
	public static List<RollingRestartSampleEntry> findAll(
		int start, int end,
		OrderByComparator<RollingRestartSampleEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the rolling restart sample entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of rolling restart sample entries.
	 *
	 * @return the number of rolling restart sample entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RollingRestartSampleEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RollingRestartSampleEntryPersistence,
		 RollingRestartSampleEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RollingRestartSampleEntryPersistence.class);

		ServiceTracker
			<RollingRestartSampleEntryPersistence,
			 RollingRestartSampleEntryPersistence> serviceTracker =
				new ServiceTracker
					<RollingRestartSampleEntryPersistence,
					 RollingRestartSampleEntryPersistence>(
						 bundle.getBundleContext(),
						 RollingRestartSampleEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}