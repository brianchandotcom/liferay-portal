/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.rankings.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.search.tuning.rankings.exception.NoSuchSTRankingsEntryException;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the st rankings entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @see STRankingsEntryUtil
 * @generated
 */
@ProviderType
public interface STRankingsEntryPersistence
	extends BasePersistence<STRankingsEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link STRankingsEntryUtil} to access the st rankings entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the st rankings entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching st rankings entries
	 */
	public java.util.List<STRankingsEntry> findBycompanyId(long companyId);

	/**
	 * Returns a range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @return the range of matching st rankings entries
	 */
	public java.util.List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching st rankings entries
	 */
	public java.util.List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the st rankings entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching st rankings entries
	 */
	public java.util.List<STRankingsEntry> findBycompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a matching st rankings entry could not be found
	 */
	public STRankingsEntry findBycompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
				orderByComparator)
		throws NoSuchSTRankingsEntryException;

	/**
	 * Returns the first st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st rankings entry, or <code>null</code> if a matching st rankings entry could not be found
	 */
	public STRankingsEntry fetchBycompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
			orderByComparator);

	/**
	 * Returns the last st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a matching st rankings entry could not be found
	 */
	public STRankingsEntry findBycompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
				orderByComparator)
		throws NoSuchSTRankingsEntryException;

	/**
	 * Returns the last st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st rankings entry, or <code>null</code> if a matching st rankings entry could not be found
	 */
	public STRankingsEntry fetchBycompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
			orderByComparator);

	/**
	 * Returns the st rankings entries before and after the current st rankings entry in the ordered set where companyId = &#63;.
	 *
	 * @param STRankingsEntryId the primary key of the current st rankings entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	public STRankingsEntry[] findBycompanyId_PrevAndNext(
			long STRankingsEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
				orderByComparator)
		throws NoSuchSTRankingsEntryException;

	/**
	 * Removes all the st rankings entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeBycompanyId(long companyId);

	/**
	 * Returns the number of st rankings entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching st rankings entries
	 */
	public int countBycompanyId(long companyId);

	/**
	 * Caches the st rankings entry in the entity cache if it is enabled.
	 *
	 * @param stRankingsEntry the st rankings entry
	 */
	public void cacheResult(STRankingsEntry stRankingsEntry);

	/**
	 * Caches the st rankings entries in the entity cache if it is enabled.
	 *
	 * @param stRankingsEntries the st rankings entries
	 */
	public void cacheResult(java.util.List<STRankingsEntry> stRankingsEntries);

	/**
	 * Creates a new st rankings entry with the primary key. Does not add the st rankings entry to the database.
	 *
	 * @param STRankingsEntryId the primary key for the new st rankings entry
	 * @return the new st rankings entry
	 */
	public STRankingsEntry create(long STRankingsEntryId);

	/**
	 * Removes the st rankings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry that was removed
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	public STRankingsEntry remove(long STRankingsEntryId)
		throws NoSuchSTRankingsEntryException;

	public STRankingsEntry updateImpl(STRankingsEntry stRankingsEntry);

	/**
	 * Returns the st rankings entry with the primary key or throws a <code>NoSuchSTRankingsEntryException</code> if it could not be found.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry
	 * @throws NoSuchSTRankingsEntryException if a st rankings entry with the primary key could not be found
	 */
	public STRankingsEntry findByPrimaryKey(long STRankingsEntryId)
		throws NoSuchSTRankingsEntryException;

	/**
	 * Returns the st rankings entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry, or <code>null</code> if a st rankings entry with the primary key could not be found
	 */
	public STRankingsEntry fetchByPrimaryKey(long STRankingsEntryId);

	/**
	 * Returns all the st rankings entries.
	 *
	 * @return the st rankings entries
	 */
	public java.util.List<STRankingsEntry> findAll();

	/**
	 * Returns a range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @return the range of st rankings entries
	 */
	public java.util.List<STRankingsEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of st rankings entries
	 */
	public java.util.List<STRankingsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of st rankings entries
	 */
	public java.util.List<STRankingsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STRankingsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the st rankings entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of st rankings entries.
	 *
	 * @return the number of st rankings entries
	 */
	public int countAll();

}