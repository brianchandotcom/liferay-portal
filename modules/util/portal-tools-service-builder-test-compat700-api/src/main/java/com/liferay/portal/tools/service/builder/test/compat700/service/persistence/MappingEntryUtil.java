/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat700.model.MappingEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the mapping entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat700.service.persistence.impl.MappingEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntryPersistence
 * @generated
 */
public class MappingEntryUtil {

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
	public static void clearCache(MappingEntry mappingEntry) {
		getPersistence().clearCache(mappingEntry);
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
	public static Map<Serializable, MappingEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MappingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MappingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MappingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MappingEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MappingEntry update(MappingEntry mappingEntry) {
		return getPersistence().update(mappingEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MappingEntry update(
		MappingEntry mappingEntry, ServiceContext serviceContext) {

		return getPersistence().update(mappingEntry, serviceContext);
	}

	/**
	 * Caches the mapping entry in the entity cache if it is enabled.
	 *
	 * @param mappingEntry the mapping entry
	 */
	public static void cacheResult(MappingEntry mappingEntry) {
		getPersistence().cacheResult(mappingEntry);
	}

	/**
	 * Caches the mapping entries in the entity cache if it is enabled.
	 *
	 * @param mappingEntries the mapping entries
	 */
	public static void cacheResult(List<MappingEntry> mappingEntries) {
		getPersistence().cacheResult(mappingEntries);
	}

	/**
	 * Creates a new mapping entry with the primary key. Does not add the mapping entry to the database.
	 *
	 * @param mappingEntryId the primary key for the new mapping entry
	 * @return the new mapping entry
	 */
	public static MappingEntry create(long mappingEntryId) {
		return getPersistence().create(mappingEntryId);
	}

	/**
	 * Removes the mapping entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry that was removed
	 * @throws NoSuchMappingEntryException if a mapping entry with the primary key could not be found
	 */
	public static MappingEntry remove(long mappingEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchMappingEntryException {

		return getPersistence().remove(mappingEntryId);
	}

	public static MappingEntry updateImpl(MappingEntry mappingEntry) {
		return getPersistence().updateImpl(mappingEntry);
	}

	/**
	 * Returns the mapping entry with the primary key or throws a <code>NoSuchMappingEntryException</code> if it could not be found.
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry
	 * @throws NoSuchMappingEntryException if a mapping entry with the primary key could not be found
	 */
	public static MappingEntry findByPrimaryKey(long mappingEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchMappingEntryException {

		return getPersistence().findByPrimaryKey(mappingEntryId);
	}

	/**
	 * Returns the mapping entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry, or <code>null</code> if a mapping entry with the primary key could not be found
	 */
	public static MappingEntry fetchByPrimaryKey(long mappingEntryId) {
		return getPersistence().fetchByPrimaryKey(mappingEntryId);
	}

	/**
	 * Returns all the mapping entries.
	 *
	 * @return the mapping entries
	 */
	public static List<MappingEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the mapping entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @return the range of mapping entries
	 */
	public static List<MappingEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the mapping entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mapping entries
	 */
	public static List<MappingEntry> findAll(
		int start, int end, OrderByComparator<MappingEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mapping entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mapping entries
	 */
	public static List<MappingEntry> findAll(
		int start, int end, OrderByComparator<MappingEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the mapping entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of mapping entries.
	 *
	 * @return the number of mapping entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	 * Returns the primaryKeys of basic entries associated with the mapping entry.
	 *
	 * @param pk the primary key of the mapping entry
	 * @return long[] of the primaryKeys of basic entries associated with the mapping entry
	 */
	public static long[] getBasicEntryPrimaryKeys(long pk) {
		return getPersistence().getBasicEntryPrimaryKeys(pk);
	}

	/**
	 * Returns all the basic entries associated with the mapping entry.
	 *
	 * @param pk the primary key of the mapping entry
	 * @return the basic entries associated with the mapping entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			BasicEntry> getBasicEntries(long pk) {

		return getPersistence().getBasicEntries(pk);
	}

	/**
	 * Returns a range of all the basic entries associated with the mapping entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the mapping entry
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @return the range of basic entries associated with the mapping entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			BasicEntry> getBasicEntries(long pk, int start, int end) {

		return getPersistence().getBasicEntries(pk, start, end);
	}

	/**
	 * Returns an ordered range of all the basic entries associated with the mapping entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the mapping entry
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of basic entries associated with the mapping entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			BasicEntry> getBasicEntries(
				long pk, int start, int end,
				OrderByComparator
					<com.liferay.portal.tools.service.builder.test.compat700.
						model.BasicEntry> orderByComparator) {

		return getPersistence().getBasicEntries(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of basic entries associated with the mapping entry.
	 *
	 * @param pk the primary key of the mapping entry
	 * @return the number of basic entries associated with the mapping entry
	 */
	public static int getBasicEntriesSize(long pk) {
		return getPersistence().getBasicEntriesSize(pk);
	}

	/**
	 * Returns <code>true</code> if the basic entry is associated with the mapping entry.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntryPK the primary key of the basic entry
	 * @return <code>true</code> if the basic entry is associated with the mapping entry; <code>false</code> otherwise
	 */
	public static boolean containsBasicEntry(long pk, long basicEntryPK) {
		return getPersistence().containsBasicEntry(pk, basicEntryPK);
	}

	/**
	 * Returns <code>true</code> if the mapping entry has any basic entries associated with it.
	 *
	 * @param pk the primary key of the mapping entry to check for associations with basic entries
	 * @return <code>true</code> if the mapping entry has any basic entries associated with it; <code>false</code> otherwise
	 */
	public static boolean containsBasicEntries(long pk) {
		return getPersistence().containsBasicEntries(pk);
	}

	/**
	 * Adds an association between the mapping entry and the basic entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntryPK the primary key of the basic entry
	 */
	public static void addBasicEntry(long pk, long basicEntryPK) {
		getPersistence().addBasicEntry(pk, basicEntryPK);
	}

	/**
	 * Adds an association between the mapping entry and the basic entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntry the basic entry
	 */
	public static void addBasicEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			basicEntry) {

		getPersistence().addBasicEntry(pk, basicEntry);
	}

	/**
	 * Adds an association between the mapping entry and the basic entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntryPKs the primary keys of the basic entries
	 */
	public static void addBasicEntries(long pk, long[] basicEntryPKs) {
		getPersistence().addBasicEntries(pk, basicEntryPKs);
	}

	/**
	 * Adds an association between the mapping entry and the basic entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntries the basic entries
	 */
	public static void addBasicEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				BasicEntry> basicEntries) {

		getPersistence().addBasicEntries(pk, basicEntries);
	}

	/**
	 * Clears all associations between the mapping entry and its basic entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry to clear the associated basic entries from
	 */
	public static void clearBasicEntries(long pk) {
		getPersistence().clearBasicEntries(pk);
	}

	/**
	 * Removes the association between the mapping entry and the basic entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntryPK the primary key of the basic entry
	 */
	public static void removeBasicEntry(long pk, long basicEntryPK) {
		getPersistence().removeBasicEntry(pk, basicEntryPK);
	}

	/**
	 * Removes the association between the mapping entry and the basic entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntry the basic entry
	 */
	public static void removeBasicEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			basicEntry) {

		getPersistence().removeBasicEntry(pk, basicEntry);
	}

	/**
	 * Removes the association between the mapping entry and the basic entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntryPKs the primary keys of the basic entries
	 */
	public static void removeBasicEntries(long pk, long[] basicEntryPKs) {
		getPersistence().removeBasicEntries(pk, basicEntryPKs);
	}

	/**
	 * Removes the association between the mapping entry and the basic entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntries the basic entries
	 */
	public static void removeBasicEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				BasicEntry> basicEntries) {

		getPersistence().removeBasicEntries(pk, basicEntries);
	}

	/**
	 * Sets the basic entries associated with the mapping entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntryPKs the primary keys of the basic entries to be associated with the mapping entry
	 */
	public static void setBasicEntries(long pk, long[] basicEntryPKs) {
		getPersistence().setBasicEntries(pk, basicEntryPKs);
	}

	/**
	 * Sets the basic entries associated with the mapping entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the mapping entry
	 * @param basicEntries the basic entries to be associated with the mapping entry
	 */
	public static void setBasicEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				BasicEntry> basicEntries) {

		getPersistence().setBasicEntries(pk, basicEntries);
	}

	public static MappingEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(MappingEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile MappingEntryPersistence _persistence;

}
// SB-Hash:1955959603