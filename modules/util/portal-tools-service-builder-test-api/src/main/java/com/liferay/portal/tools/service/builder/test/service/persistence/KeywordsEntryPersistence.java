/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchKeywordsEntryException;
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the keywords entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KeywordsEntryUtil
 * @generated
 */
@ProviderType
public interface KeywordsEntryPersistence
	extends BasePersistence<KeywordsEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KeywordsEntryUtil} to access the keywords entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the keywords entry in the entity cache if it is enabled.
	 *
	 * @param keywordsEntry the keywords entry
	 */
	public void cacheResult(KeywordsEntry keywordsEntry);

	/**
	 * Caches the keywords entries in the entity cache if it is enabled.
	 *
	 * @param keywordsEntries the keywords entries
	 */
	public void cacheResult(java.util.List<KeywordsEntry> keywordsEntries);

	/**
	 * Creates a new keywords entry with the primary key. Does not add the keywords entry to the database.
	 *
	 * @param keywordsEntryId the primary key for the new keywords entry
	 * @return the new keywords entry
	 */
	public KeywordsEntry create(long keywordsEntryId);

	/**
	 * Removes the keywords entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry that was removed
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	public KeywordsEntry remove(long keywordsEntryId)
		throws NoSuchKeywordsEntryException;

	public KeywordsEntry updateImpl(KeywordsEntry keywordsEntry);

	/**
	 * Returns the keywords entry with the primary key or throws a <code>NoSuchKeywordsEntryException</code> if it could not be found.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry
	 * @throws NoSuchKeywordsEntryException if a keywords entry with the primary key could not be found
	 */
	public KeywordsEntry findByPrimaryKey(long keywordsEntryId)
		throws NoSuchKeywordsEntryException;

	/**
	 * Returns the keywords entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry, or <code>null</code> if a keywords entry with the primary key could not be found
	 */
	public KeywordsEntry fetchByPrimaryKey(long keywordsEntryId);

	/**
	 * Returns all the keywords entries.
	 *
	 * @return the keywords entries
	 */
	public java.util.List<KeywordsEntry> findAll();

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
	public java.util.List<KeywordsEntry> findAll(int start, int end);

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
	public java.util.List<KeywordsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KeywordsEntry>
			orderByComparator);

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
	public java.util.List<KeywordsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KeywordsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the keywords entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of keywords entries.
	 *
	 * @return the number of keywords entries
	 */
	public int countAll();

	/**
	 * Returns the primaryKeys of index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return long[] of the primaryKeys of index entries associated with the keywords entry
	 */
	public long[] getIndexEntryPrimaryKeys(long pk);

	/**
	 * Returns all the index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return the index entries associated with the keywords entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getIndexEntries(long pk);

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
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getIndexEntries(long pk, int start, int end);

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
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getIndexEntries(
				long pk, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						IndexEntry> orderByComparator);

	/**
	 * Returns the number of index entries associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @return the number of index entries associated with the keywords entry
	 */
	public int getIndexEntriesSize(long pk);

	/**
	 * Returns <code>true</code> if the index entry is associated with the keywords entry.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 * @return <code>true</code> if the index entry is associated with the keywords entry; <code>false</code> otherwise
	 */
	public boolean containsIndexEntry(long pk, long indexEntryPK);

	/**
	 * Returns <code>true</code> if the keywords entry has any index entries associated with it.
	 *
	 * @param pk the primary key of the keywords entry to check for associations with index entries
	 * @return <code>true</code> if the keywords entry has any index entries associated with it; <code>false</code> otherwise
	 */
	public boolean containsIndexEntries(long pk);

	/**
	 * Adds an association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 * @return <code>true</code> if an association between the keywords entry and the index entry was added; <code>false</code> if they were already associated
	 */
	public boolean addIndexEntry(long pk, long indexEntryPK);

	/**
	 * Adds an association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntry the index entry
	 * @return <code>true</code> if an association between the keywords entry and the index entry was added; <code>false</code> if they were already associated
	 */
	public boolean addIndexEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry);

	/**
	 * Adds an association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries
	 * @return <code>true</code> if at least one association between the keywords entry and the index entries was added; <code>false</code> if they were all already associated
	 */
	public boolean addIndexEntries(long pk, long[] indexEntryPKs);

	/**
	 * Adds an association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries
	 * @return <code>true</code> if at least one association between the keywords entry and the index entries was added; <code>false</code> if they were all already associated
	 */
	public boolean addIndexEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
				indexEntries);

	/**
	 * Clears all associations between the keywords entry and its index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry to clear the associated index entries from
	 */
	public void clearIndexEntries(long pk);

	/**
	 * Removes the association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPK the primary key of the index entry
	 */
	public void removeIndexEntry(long pk, long indexEntryPK);

	/**
	 * Removes the association between the keywords entry and the index entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntry the index entry
	 */
	public void removeIndexEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry);

	/**
	 * Removes the association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries
	 */
	public void removeIndexEntries(long pk, long[] indexEntryPKs);

	/**
	 * Removes the association between the keywords entry and the index entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries
	 */
	public void removeIndexEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
				indexEntries);

	/**
	 * Sets the index entries associated with the keywords entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntryPKs the primary keys of the index entries to be associated with the keywords entry
	 */
	public void setIndexEntries(long pk, long[] indexEntryPKs);

	/**
	 * Sets the index entries associated with the keywords entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the keywords entry
	 * @param indexEntries the index entries to be associated with the keywords entry
	 */
	public void setIndexEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
				indexEntries);

}