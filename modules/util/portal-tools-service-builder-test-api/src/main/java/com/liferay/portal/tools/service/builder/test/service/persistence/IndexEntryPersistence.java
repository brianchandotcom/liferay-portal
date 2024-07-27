/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchIndexEntryException;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the index entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntryUtil
 * @generated
 */
@ProviderType
public interface IndexEntryPersistence extends BasePersistence<IndexEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link IndexEntryUtil} to access the index entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the index entry in the entity cache if it is enabled.
	 *
	 * @param indexEntry the index entry
	 */
	public void cacheResult(IndexEntry indexEntry);

	/**
	 * Caches the index entries in the entity cache if it is enabled.
	 *
	 * @param indexEntries the index entries
	 */
	public void cacheResult(java.util.List<IndexEntry> indexEntries);

	/**
	 * Creates a new index entry with the primary key. Does not add the index entry to the database.
	 *
	 * @param indexEntryId the primary key for the new index entry
	 * @return the new index entry
	 */
	public IndexEntry create(long indexEntryId);

	/**
	 * Removes the index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry that was removed
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry remove(long indexEntryId)
		throws NoSuchIndexEntryException;

	public IndexEntry updateImpl(IndexEntry indexEntry);

	/**
	 * Returns the index entry with the primary key or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry findByPrimaryKey(long indexEntryId)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry, or <code>null</code> if a index entry with the primary key could not be found
	 */
	public IndexEntry fetchByPrimaryKey(long indexEntryId);

	/**
	 * Returns all the index entries.
	 *
	 * @return the index entries
	 */
	public java.util.List<IndexEntry> findAll();

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
	public java.util.List<IndexEntry> findAll(int start, int end);

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
	public java.util.List<IndexEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

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
	public java.util.List<IndexEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the index entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of index entries.
	 *
	 * @return the number of index entries
	 */
	public int countAll();

	/**
	 * Returns the primaryKeys of keywords entries associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @return long[] of the primaryKeys of keywords entries associated with the index entry
	 */
	public long[] getKeywordsEntryPrimaryKeys(long pk);

	/**
	 * Returns all the keywords entries associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @return the keywords entries associated with the index entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getKeywordsEntries(long pk);

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
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getKeywordsEntries(long pk, int start, int end);

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
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getKeywordsEntries(
				long pk, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						KeywordsEntry> orderByComparator);

	/**
	 * Returns the number of keywords entries associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @return the number of keywords entries associated with the index entry
	 */
	public int getKeywordsEntriesSize(long pk);

	/**
	 * Returns <code>true</code> if the keywords entry is associated with the index entry.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPK the primary key of the keywords entry
	 * @return <code>true</code> if the keywords entry is associated with the index entry; <code>false</code> otherwise
	 */
	public boolean containsKeywordsEntry(long pk, long keywordsEntryPK);

	/**
	 * Returns <code>true</code> if the index entry has any keywords entries associated with it.
	 *
	 * @param pk the primary key of the index entry to check for associations with keywords entries
	 * @return <code>true</code> if the index entry has any keywords entries associated with it; <code>false</code> otherwise
	 */
	public boolean containsKeywordsEntries(long pk);

	/**
	 * Adds an association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPK the primary key of the keywords entry
	 * @return <code>true</code> if an association between the index entry and the keywords entry was added; <code>false</code> if they were already associated
	 */
	public boolean addKeywordsEntry(long pk, long keywordsEntryPK);

	/**
	 * Adds an association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntry the keywords entry
	 * @return <code>true</code> if an association between the index entry and the keywords entry was added; <code>false</code> if they were already associated
	 */
	public boolean addKeywordsEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			keywordsEntry);

	/**
	 * Adds an association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPKs the primary keys of the keywords entries
	 * @return <code>true</code> if at least one association between the index entry and the keywords entries was added; <code>false</code> if they were all already associated
	 */
	public boolean addKeywordsEntries(long pk, long[] keywordsEntryPKs);

	/**
	 * Adds an association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntries the keywords entries
	 * @return <code>true</code> if at least one association between the index entry and the keywords entries was added; <code>false</code> if they were all already associated
	 */
	public boolean addKeywordsEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
				keywordsEntries);

	/**
	 * Clears all associations between the index entry and its keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry to clear the associated keywords entries from
	 */
	public void clearKeywordsEntries(long pk);

	/**
	 * Removes the association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPK the primary key of the keywords entry
	 */
	public void removeKeywordsEntry(long pk, long keywordsEntryPK);

	/**
	 * Removes the association between the index entry and the keywords entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntry the keywords entry
	 */
	public void removeKeywordsEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			keywordsEntry);

	/**
	 * Removes the association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPKs the primary keys of the keywords entries
	 */
	public void removeKeywordsEntries(long pk, long[] keywordsEntryPKs);

	/**
	 * Removes the association between the index entry and the keywords entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntries the keywords entries
	 */
	public void removeKeywordsEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
				keywordsEntries);

	/**
	 * Sets the keywords entries associated with the index entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntryPKs the primary keys of the keywords entries to be associated with the index entry
	 */
	public void setKeywordsEntries(long pk, long[] keywordsEntryPKs);

	/**
	 * Sets the keywords entries associated with the index entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the index entry
	 * @param keywordsEntries the keywords entries to be associated with the index entry
	 */
	public void setKeywordsEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
				keywordsEntries);

}