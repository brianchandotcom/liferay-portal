/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the index entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.IndexEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntryPersistence
 * @generated
 */
public class IndexEntryUtil {

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
	public static void clearCache(IndexEntry indexEntry) {
		getPersistence().clearCache(indexEntry);
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
	public static Map<Serializable, IndexEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<IndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<IndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<IndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static IndexEntry update(IndexEntry indexEntry) {
		return getPersistence().update(indexEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static IndexEntry update(
		IndexEntry indexEntry, ServiceContext serviceContext) {

		return getPersistence().update(indexEntry, serviceContext);
	}

	/**
	 * Caches the index entry in the entity cache if it is enabled.
	 *
	 * @param indexEntry the index entry
	 */
	public static void cacheResult(IndexEntry indexEntry) {
		getPersistence().cacheResult(indexEntry);
	}

	/**
	 * Caches the index entries in the entity cache if it is enabled.
	 *
	 * @param indexEntries the index entries
	 */
	public static void cacheResult(List<IndexEntry> indexEntries) {
		getPersistence().cacheResult(indexEntries);
	}

	/**
	 * Creates a new index entry with the primary key. Does not add the index entry to the database.
	 *
	 * @param indexEntryId the primary key for the new index entry
	 * @return the new index entry
	 */
	public static IndexEntry create(long indexEntryId) {
		return getPersistence().create(indexEntryId);
	}

	/**
	 * Removes the index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry that was removed
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry remove(long indexEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().remove(indexEntryId);
	}

	public static IndexEntry updateImpl(IndexEntry indexEntry) {
		return getPersistence().updateImpl(indexEntry);
	}

	/**
	 * Returns the index entry with the primary key or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry findByPrimaryKey(long indexEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPrimaryKey(indexEntryId);
	}

	/**
	 * Returns the index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry, or <code>null</code> if a index entry with the primary key could not be found
	 */
	public static IndexEntry fetchByPrimaryKey(long indexEntryId) {
		return getPersistence().fetchByPrimaryKey(indexEntryId);
	}

	/**
	 * Returns all the index entries.
	 *
	 * @return the index entries
	 */
	public static List<IndexEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of index entries
	 */
	public static List<IndexEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of index entries
	 */
	public static List<IndexEntry> findAll(
		int start, int end, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of index entries
	 */
	public static List<IndexEntry> findAll(
		int start, int end, OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the index entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of index entries.
	 *
	 * @return the number of index entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	 * Returns the primaryKeys of keywords entries associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @return long[] of the primaryKeys of keywords entries associated with the index entry
	 */
	public static long[] getKeywordsEntryPrimaryKeys(long pk) {
		return getPersistence().getKeywordsEntryPrimaryKeys(pk);
	}

	/**
	 * Returns all the keywords entries associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @return the keywords entries associated with the index entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getKeywordsEntries(long pk) {

		return getPersistence().getKeywordsEntries(pk);
	}

	/**
	 * Returns a range of all the keywords entries associated with the index entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the index entry
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of keywords entries associated with the index entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getKeywordsEntries(long pk, int start, int end) {

		return getPersistence().getKeywordsEntries(pk, start, end);
	}

	/**
	 * Returns an ordered range of all the keywords entries associated with the index entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the index entry
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of keywords entries associated with the index entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getKeywordsEntries(
				long pk, int start, int end,
				OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						KeywordsEntry> orderByComparator) {

		return getPersistence().getKeywordsEntries(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of keywords entries associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @return the number of keywords entries associated with the index entry
	 */
	public static int getKeywordsEntriesSize(long pk) {
		return getPersistence().getKeywordsEntriesSize(pk);
	}

	/**
	 * Returns <code>true</code> if the keywords entry is associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPK the primary key of the keywords entry
	 * @return <code>true</code> if the keywords entry is associated with the index entry; <code>false</code> otherwise
	 */
	public static boolean containsKeywordsEntry(long pk, long keywordsEntryPK) {
		return getPersistence().containsKeywordsEntry(pk, keywordsEntryPK);
	}

	/**
	 * Returns <code>true</code> if the index entry has any keywords entries associated with it.
	 *
	 * @param pk the primary key of the index entry to check for associations with keywords entries
	 * @return <code>true</code> if the index entry has any keywords entries associated with it; <code>false</code> otherwise
	 */
	public static boolean containsKeywordsEntries(long pk) {
		return getPersistence().containsKeywordsEntries(pk);
	}

	/**
	 * Adds an association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPK the primary key of the keywords entry
	 * @return <code>true</code> if an association between the index entry and the keywords entry was added; <code>false</code> if they were already associated
	 */
	public static boolean addKeywordsEntry(long pk, long keywordsEntryPK) {
		return getPersistence().addKeywordsEntry(pk, keywordsEntryPK);
	}

	/**
	 * Adds an association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntry the keywords entry
	 * @return <code>true</code> if an association between the index entry and the keywords entry was added; <code>false</code> if they were already associated
	 */
	public static boolean addKeywordsEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			keywordsEntry) {

		return getPersistence().addKeywordsEntry(pk, keywordsEntry);
	}

	/**
	 * Adds an association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPKs the primary keys of the keywords entries
	 * @return <code>true</code> if at least one association between the index entry and the keywords entries was added; <code>false</code> if they were all already associated
	 */
	public static boolean addKeywordsEntries(long pk, long[] keywordsEntryPKs) {
		return getPersistence().addKeywordsEntries(pk, keywordsEntryPKs);
	}

	/**
	 * Adds an association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntries the keywords entries
	 * @return <code>true</code> if at least one association between the index entry and the keywords entries was added; <code>false</code> if they were all already associated
	 */
	public static boolean addKeywordsEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			keywordsEntries) {

		return getPersistence().addKeywordsEntries(pk, keywordsEntries);
	}

	/**
	 * Clears all associations between the index entry and its keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry to clear the associated keywords entries from
	 */
	public static void clearKeywordsEntries(long pk) {
		getPersistence().clearKeywordsEntries(pk);
	}

	/**
	 * Removes the association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPK the primary key of the keywords entry
	 */
	public static void removeKeywordsEntry(long pk, long keywordsEntryPK) {
		getPersistence().removeKeywordsEntry(pk, keywordsEntryPK);
	}

	/**
	 * Removes the association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntry the keywords entry
	 */
	public static void removeKeywordsEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			keywordsEntry) {

		getPersistence().removeKeywordsEntry(pk, keywordsEntry);
	}

	/**
	 * Removes the association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPKs the primary keys of the keywords entries
	 */
	public static void removeKeywordsEntries(long pk, long[] keywordsEntryPKs) {
		getPersistence().removeKeywordsEntries(pk, keywordsEntryPKs);
	}

	/**
	 * Removes the association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntries the keywords entries
	 */
	public static void removeKeywordsEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			keywordsEntries) {

		getPersistence().removeKeywordsEntries(pk, keywordsEntries);
	}

	/**
	 * Sets the keywords entries associated with the index entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPKs the primary keys of the keywords entries to be associated with the index entry
	 */
	public static void setKeywordsEntries(long pk, long[] keywordsEntryPKs) {
		getPersistence().setKeywordsEntries(pk, keywordsEntryPKs);
	}

	/**
	 * Sets the keywords entries associated with the index entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntries the keywords entries to be associated with the index entry
	 */
	public static void setKeywordsEntries(
		long pk,
		List<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			keywordsEntries) {

		getPersistence().setKeywordsEntries(pk, keywordsEntries);
	}

	public static IndexEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(IndexEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile IndexEntryPersistence _persistence;

}