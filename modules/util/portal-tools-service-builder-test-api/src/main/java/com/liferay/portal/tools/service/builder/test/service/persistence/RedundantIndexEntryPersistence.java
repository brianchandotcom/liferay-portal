/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchRedundantIndexEntryException;
import com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the redundant index entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedundantIndexEntryUtil
 * @generated
 */
@ProviderType
public interface RedundantIndexEntryPersistence
	extends BasePersistence<RedundantIndexEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RedundantIndexEntryUtil} to access the redundant index entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchRedundantIndexEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching redundant index entry
	 * @throws NoSuchRedundantIndexEntryException if a matching redundant index entry could not be found
	 */
	public RedundantIndexEntry findByC_N(long companyId, String name)
		throws NoSuchRedundantIndexEntryException;

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching redundant index entry, or <code>null</code> if a matching redundant index entry could not be found
	 */
	public RedundantIndexEntry fetchByC_N(long companyId, String name);

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redundant index entry, or <code>null</code> if a matching redundant index entry could not be found
	 */
	public RedundantIndexEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the redundant index entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the redundant index entry that was removed
	 */
	public RedundantIndexEntry removeByC_N(long companyId, String name)
		throws NoSuchRedundantIndexEntryException;

	/**
	 * Returns the number of redundant index entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching redundant index entries
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the redundant index entry in the entity cache if it is enabled.
	 *
	 * @param redundantIndexEntry the redundant index entry
	 */
	public void cacheResult(RedundantIndexEntry redundantIndexEntry);

	/**
	 * Caches the redundant index entries in the entity cache if it is enabled.
	 *
	 * @param redundantIndexEntries the redundant index entries
	 */
	public void cacheResult(
		java.util.List<RedundantIndexEntry> redundantIndexEntries);

	/**
	 * Creates a new redundant index entry with the primary key. Does not add the redundant index entry to the database.
	 *
	 * @param redundantIndexEntryId the primary key for the new redundant index entry
	 * @return the new redundant index entry
	 */
	public RedundantIndexEntry create(long redundantIndexEntryId);

	/**
	 * Removes the redundant index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry that was removed
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	public RedundantIndexEntry remove(long redundantIndexEntryId)
		throws NoSuchRedundantIndexEntryException;

	public RedundantIndexEntry updateImpl(
		RedundantIndexEntry redundantIndexEntry);

	/**
	 * Returns the redundant index entry with the primary key or throws a <code>NoSuchRedundantIndexEntryException</code> if it could not be found.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	public RedundantIndexEntry findByPrimaryKey(long redundantIndexEntryId)
		throws NoSuchRedundantIndexEntryException;

	/**
	 * Returns the redundant index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry, or <code>null</code> if a redundant index entry with the primary key could not be found
	 */
	public RedundantIndexEntry fetchByPrimaryKey(long redundantIndexEntryId);

	/**
	 * Returns all the redundant index entries.
	 *
	 * @return the redundant index entries
	 */
	public java.util.List<RedundantIndexEntry> findAll();

	/**
	 * Returns a range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @return the range of redundant index entries
	 */
	public java.util.List<RedundantIndexEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redundant index entries
	 */
	public java.util.List<RedundantIndexEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedundantIndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redundant index entries
	 */
	public java.util.List<RedundantIndexEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedundantIndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the redundant index entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of redundant index entries.
	 *
	 * @return the number of redundant index entries
	 */
	public int countAll();

}