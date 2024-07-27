/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the keywords entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.KeywordsEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KeywordsEntryPersistence
 * @generated
 */
public class KeywordsEntryUtil {

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
	public static void clearCache(KeywordsEntry keywordsEntry) {
		getPersistence().clearCache(keywordsEntry);
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
	public static Map<Serializable, KeywordsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KeywordsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KeywordsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KeywordsEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KeywordsEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KeywordsEntry update(KeywordsEntry keywordsEntry) {
		return getPersistence().update(keywordsEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KeywordsEntry update(
		KeywordsEntry keywordsEntry, ServiceContext serviceContext) {

		return getPersistence().update(keywordsEntry, serviceContext);
	}

	/**
	 * Caches the keywords entry in the entity cache if it is enabled.
	 *
	 * @param keywordsEntry the keywords entry
	 */
	public static void cacheResult(KeywordsEntry keywordsEntry) {
		getPersistence().cacheResult(keywordsEntry);
	}

	/**
	 * Caches the keywords entries in the entity cache if it is enabled.
	 *
	 * @param keywordsEntries the keywords entries
	 */
	public static void cacheResult(List<KeywordsEntry> keywordsEntries) {
		getPersistence().cacheResult(keywordsEntries);
	}

	/**
	 * Creates a new keywords entry with the primary key. Does not add the keywords entry to the database.
	 *
	 * @param keywordsEntryId the primary key for the new keywords entry
	 * @return the new keywords entry
	 */
	public static KeywordsEntry create(long keywordsEntryId) {
		return getPersistence().create(keywordsEntryId);
	}

	/**
	 * Removes the keywords entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry that was removed
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	public static KeywordsEntry remove(long keywordsEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchKeywordsEntryException {

		return getPersistence().remove(keywordsEntryId);
	}

	public static KeywordsEntry updateImpl(KeywordsEntry keywordsEntry) {
		return getPersistence().updateImpl(keywordsEntry);
	}

	/**
	 * Returns the keywords entry with the primary key or throws a <code>NoSuchKeywordsEntryException</code> if it could not be found.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	public static KeywordsEntry findByPrimaryKey(long keywordsEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchKeywordsEntryException {

		return getPersistence().findByPrimaryKey(keywordsEntryId);
	}

	/**
	 * Returns the keywords entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry, or <code>null</code> if a keywords entry with the primary key could not be found
	 */
	public static KeywordsEntry fetchByPrimaryKey(long keywordsEntryId) {
		return getPersistence().fetchByPrimaryKey(keywordsEntryId);
	}

	/**
	 * Returns all the keywords entries.
	 *
	 * @return the keywords entries
	 */
	public static List<KeywordsEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @return the range of keywords entries
	 */
	public static List<KeywordsEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of keywords entries
	 */
	public static List<KeywordsEntry> findAll(
		int start, int end,
		OrderByComparator<KeywordsEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of keywords entries
	 */
	public static List<KeywordsEntry> findAll(
		int start, int end, OrderByComparator<KeywordsEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the keywords entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of keywords entries.
	 *
	 * @return the number of keywords entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	 * Returns the primaryKeys of index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return long[] of the primaryKeys of index entries associated with the keywords entry
	 */
	public static long[] getIndexEntryPrimaryKeys(long pk) {
		return getPersistence().getIndexEntryPrimaryKeys(pk);
	}

	/**
	 * Returns all the index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return the index entries associated with the keywords entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getIndexEntries(long pk) {

		return getPersistence().getIndexEntries(pk);
	}

	/**
	 * Returns a range of all the index entries associated with the keywords entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the keywords entry
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @return the range of index entries associated with the keywords entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getIndexEntries(long pk, int start, int end) {

		return getPersistence().getIndexEntries(pk, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries associated with the keywords entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the keywords entry
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of index entries associated with the keywords entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getIndexEntries(
				long pk, int start, int end,
				OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						IndexEntry> orderByComparator) {

		return getPersistence().getIndexEntries(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return the number of index entries associated with the keywords entry
	 */
	public static int getIndexEntriesSize(long pk) {
		return getPersistence().getIndexEntriesSize(pk);
	}

	/**
	 * Returns <code>true</code> if the index entry is associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 * @return <code>true</code> if the index entry is associated with the keywords entry; <code>false</code> otherwise
	 */
	public static boolean containsIndexEntry(long pk, long indexEntryPK) {
		return getPersistence().containsIndexEntry(pk, indexEntryPK);
	}

	/**
	 * Returns <code>true</code> if the keywords entry has any index entries associated with it.
	 *
	 * @param pk the primary key of the keywords entry to check for associations with index entries
	 * @return <code>true</code> if the keywords entry has any index entries associated with it; <code>false</code> otherwise
	 */
	public static boolean containsIndexEntries(long pk) {
		return getPersistence().containsIndexEntries(pk);
	}

	/**
	 * Adds an association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 * @return <code>true</code> if an association between the keywords entry and the index entry was added; <code>false</code> if they were already associated
	 */
	public static boolean addIndexEntry(long pk, long indexEntryPK) {
		return getPersistence().addIndexEntry(pk, indexEntryPK);
	}

	/**
	 * Adds an association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntry the index entry
	 * @return <code>true</code> if an association between the keywords entry and the index entry was added; <code>false</code> if they were already associated
	 */
	public static boolean addIndexEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry) {

		return getPersistence().addIndexEntry(pk, indexEntry);
	}

	/**
	 * Adds an association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries
	 * @return <code>true</code> if at least one association between the keywords entry and the index entries was added; <code>false</code> if they were all already associated
	 */
	public static boolean addIndexEntries(long pk, long[] indexEntryPKs) {
		return getPersistence().addIndexEntries(pk, indexEntryPKs);
	}

	/**
	 * Adds an association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries
	 * @return <code>true</code> if at least one association between the keywords entry and the index entries was added; <code>false</code> if they were all already associated
	 */
	public static boolean addIndexEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			indexEntries) {

		return getPersistence().addIndexEntries(pk, indexEntries);
	}

	/**
	 * Clears all associations between the keywords entry and its index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry to clear the associated index entries from
	 */
	public static void clearIndexEntries(long pk) {
		getPersistence().clearIndexEntries(pk);
	}

	/**
	 * Removes the association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 */
	public static void removeIndexEntry(long pk, long indexEntryPK) {
		getPersistence().removeIndexEntry(pk, indexEntryPK);
	}

	/**
	 * Removes the association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntry the index entry
	 */
	public static void removeIndexEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry) {

		getPersistence().removeIndexEntry(pk, indexEntry);
	}

	/**
	 * Removes the association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries
	 */
	public static void removeIndexEntries(long pk, long[] indexEntryPKs) {
		getPersistence().removeIndexEntries(pk, indexEntryPKs);
	}

	/**
	 * Removes the association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries
	 */
	public static void removeIndexEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			indexEntries) {

		getPersistence().removeIndexEntries(pk, indexEntries);
	}

	/**
	 * Sets the index entries associated with the keywords entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries to be associated with the keywords entry
	 */
	public static void setIndexEntries(long pk, long[] indexEntryPKs) {
		getPersistence().setIndexEntries(pk, indexEntryPKs);
	}

	/**
	 * Sets the index entries associated with the keywords entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries to be associated with the keywords entry
	 */
	public static void setIndexEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			indexEntries) {

		getPersistence().setIndexEntries(pk, indexEntries);
	}

	public static KeywordsEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(KeywordsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile KeywordsEntryPersistence _persistence;

}