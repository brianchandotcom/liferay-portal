/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.AutoEscapeEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the auto escape entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.AutoEscapeEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AutoEscapeEntryPersistence
 * @generated
 */
public class AutoEscapeEntryUtil {

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
	public static void clearCache(AutoEscapeEntry autoEscapeEntry) {
		getPersistence().clearCache(autoEscapeEntry);
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
	public static Map<Serializable, AutoEscapeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AutoEscapeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AutoEscapeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AutoEscapeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AutoEscapeEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AutoEscapeEntry update(AutoEscapeEntry autoEscapeEntry) {
		return getPersistence().update(autoEscapeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AutoEscapeEntry update(
		AutoEscapeEntry autoEscapeEntry, ServiceContext serviceContext) {

		return getPersistence().update(autoEscapeEntry, serviceContext);
	}

	/**
	 * Caches the auto escape entry in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntry the auto escape entry
	 */
	public static void cacheResult(AutoEscapeEntry autoEscapeEntry) {
		getPersistence().cacheResult(autoEscapeEntry);
	}

	/**
	 * Caches the auto escape entries in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntries the auto escape entries
	 */
	public static void cacheResult(List<AutoEscapeEntry> autoEscapeEntries) {
		getPersistence().cacheResult(autoEscapeEntries);
	}

	/**
	 * Creates a new auto escape entry with the primary key. Does not add the auto escape entry to the database.
	 *
	 * @param autoEscapeEntryId the primary key for the new auto escape entry
	 * @return the new auto escape entry
	 */
	public static AutoEscapeEntry create(long autoEscapeEntryId) {
		return getPersistence().create(autoEscapeEntryId);
	}

	/**
	 * Removes the auto escape entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry that was removed
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	public static AutoEscapeEntry remove(long autoEscapeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchAutoEscapeEntryException {

		return getPersistence().remove(autoEscapeEntryId);
	}

	public static AutoEscapeEntry updateImpl(AutoEscapeEntry autoEscapeEntry) {
		return getPersistence().updateImpl(autoEscapeEntry);
	}

	/**
	 * Returns the auto escape entry with the primary key or throws a <code>NoSuchAutoEscapeEntryException</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	public static AutoEscapeEntry findByPrimaryKey(long autoEscapeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchAutoEscapeEntryException {

		return getPersistence().findByPrimaryKey(autoEscapeEntryId);
	}

	/**
	 * Returns the auto escape entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry, or <code>null</code> if a auto escape entry with the primary key could not be found
	 */
	public static AutoEscapeEntry fetchByPrimaryKey(long autoEscapeEntryId) {
		return getPersistence().fetchByPrimaryKey(autoEscapeEntryId);
	}

	/**
	 * Returns all the auto escape entries.
	 *
	 * @return the auto escape entries
	 */
	public static List<AutoEscapeEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @return the range of auto escape entries
	 */
	public static List<AutoEscapeEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of auto escape entries
	 */
	public static List<AutoEscapeEntry> findAll(
		int start, int end,
		OrderByComparator<AutoEscapeEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the auto escape entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AutoEscapeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of auto escape entries
	 * @param end the upper bound of the range of auto escape entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of auto escape entries
	 */
	public static List<AutoEscapeEntry> findAll(
		int start, int end,
		OrderByComparator<AutoEscapeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the auto escape entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of auto escape entries.
	 *
	 * @return the number of auto escape entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AutoEscapeEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AutoEscapeEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AutoEscapeEntryPersistence _persistence;

}