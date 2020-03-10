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

package com.liferay.revert.schema.version.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.revert.schema.version.model.SchemaEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the schema entry service. This utility wraps <code>com.liferay.revert.schema.version.service.persistence.impl.SchemaEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SchemaEntryPersistence
 * @generated
 */
public class SchemaEntryUtil {

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
	public static void clearCache(SchemaEntry schemaEntry) {
		getPersistence().clearCache(schemaEntry);
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
	public static Map<Serializable, SchemaEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SchemaEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SchemaEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SchemaEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SchemaEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SchemaEntry update(SchemaEntry schemaEntry) {
		return getPersistence().update(schemaEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SchemaEntry update(
		SchemaEntry schemaEntry, ServiceContext serviceContext) {

		return getPersistence().update(schemaEntry, serviceContext);
	}

	/**
	 * Caches the schema entry in the entity cache if it is enabled.
	 *
	 * @param schemaEntry the schema entry
	 */
	public static void cacheResult(SchemaEntry schemaEntry) {
		getPersistence().cacheResult(schemaEntry);
	}

	/**
	 * Caches the schema entries in the entity cache if it is enabled.
	 *
	 * @param schemaEntries the schema entries
	 */
	public static void cacheResult(List<SchemaEntry> schemaEntries) {
		getPersistence().cacheResult(schemaEntries);
	}

	/**
	 * Creates a new schema entry with the primary key. Does not add the schema entry to the database.
	 *
	 * @param entryId the primary key for the new schema entry
	 * @return the new schema entry
	 */
	public static SchemaEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	 * Removes the schema entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry that was removed
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	public static SchemaEntry remove(long entryId)
		throws com.liferay.revert.schema.version.exception.
			NoSuchSchemaEntryException {

		return getPersistence().remove(entryId);
	}

	public static SchemaEntry updateImpl(SchemaEntry schemaEntry) {
		return getPersistence().updateImpl(schemaEntry);
	}

	/**
	 * Returns the schema entry with the primary key or throws a <code>NoSuchSchemaEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	public static SchemaEntry findByPrimaryKey(long entryId)
		throws com.liferay.revert.schema.version.exception.
			NoSuchSchemaEntryException {

		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	 * Returns the schema entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry, or <code>null</code> if a schema entry with the primary key could not be found
	 */
	public static SchemaEntry fetchByPrimaryKey(long entryId) {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns all the schema entries.
	 *
	 * @return the schema entries
	 */
	public static List<SchemaEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @return the range of schema entries
	 */
	public static List<SchemaEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of schema entries
	 */
	public static List<SchemaEntry> findAll(
		int start, int end, OrderByComparator<SchemaEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of schema entries
	 */
	public static List<SchemaEntry> findAll(
		int start, int end, OrderByComparator<SchemaEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the schema entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of schema entries.
	 *
	 * @return the number of schema entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SchemaEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SchemaEntryPersistence, SchemaEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SchemaEntryPersistence.class);

		ServiceTracker<SchemaEntryPersistence, SchemaEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<SchemaEntryPersistence, SchemaEntryPersistence>(
						bundle.getBundleContext(), SchemaEntryPersistence.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}