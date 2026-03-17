/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat700.exception.NoSuchWhereClauseEntryException;
import com.liferay.portal.tools.service.builder.test.compat700.model.WhereClauseEntry;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the where clause entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WhereClauseEntryUtil
 * @generated
 */
@ProviderType
public interface WhereClauseEntryPersistence
	extends BasePersistence<WhereClauseEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WhereClauseEntryUtil} to access the where clause entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, WhereClauseEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	 * Returns all the where clause entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching where clause entries
	 */
	public java.util.List<WhereClauseEntry> findByName_Nickname(String name);

	/**
	 * Returns a range of all the where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @return the range of matching where clause entries
	 */
	public java.util.List<WhereClauseEntry> findByName_Nickname(
		String name, int start, int end);

	/**
	 * Returns an ordered range of all the where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching where clause entries
	 */
	public java.util.List<WhereClauseEntry> findByName_Nickname(
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching where clause entries
	 */
	public java.util.List<WhereClauseEntry> findByName_Nickname(
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching where clause entry
	 * @throws NoSuchWhereClauseEntryException if a matching where clause entry could not be found
	 */
	public WhereClauseEntry findByName_Nickname_First(
			String name,
			com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
				orderByComparator)
		throws NoSuchWhereClauseEntryException;

	/**
	 * Returns the first where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching where clause entry, or <code>null</code> if a matching where clause entry could not be found
	 */
	public WhereClauseEntry fetchByName_Nickname_First(
		String name,
		com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
			orderByComparator);

	/**
	 * Returns the last where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching where clause entry
	 * @throws NoSuchWhereClauseEntryException if a matching where clause entry could not be found
	 */
	public WhereClauseEntry findByName_Nickname_Last(
			String name,
			com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
				orderByComparator)
		throws NoSuchWhereClauseEntryException;

	/**
	 * Returns the last where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching where clause entry, or <code>null</code> if a matching where clause entry could not be found
	 */
	public WhereClauseEntry fetchByName_Nickname_Last(
		String name,
		com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
			orderByComparator);

	/**
	 * Returns the where clause entries before and after the current where clause entry in the ordered set where name = &#63;.
	 *
	 * @param whereClauseEntryId the primary key of the current where clause entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next where clause entry
	 * @throws NoSuchWhereClauseEntryException if a where clause entry with the primary key could not be found
	 */
	public WhereClauseEntry[] findByName_Nickname_PrevAndNext(
			long whereClauseEntryId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
				orderByComparator)
		throws NoSuchWhereClauseEntryException;

	/**
	 * Removes all the where clause entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	public void removeByName_Nickname(String name);

	/**
	 * Returns the number of where clause entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching where clause entries
	 */
	public int countByName_Nickname(String name);

	/**
	 * Caches the where clause entry in the entity cache if it is enabled.
	 *
	 * @param whereClauseEntry the where clause entry
	 */
	public void cacheResult(WhereClauseEntry whereClauseEntry);

	/**
	 * Caches the where clause entries in the entity cache if it is enabled.
	 *
	 * @param whereClauseEntries the where clause entries
	 */
	public void cacheResult(
		java.util.List<WhereClauseEntry> whereClauseEntries);

	/**
	 * Creates a new where clause entry with the primary key. Does not add the where clause entry to the database.
	 *
	 * @param whereClauseEntryId the primary key for the new where clause entry
	 * @return the new where clause entry
	 */
	public WhereClauseEntry create(long whereClauseEntryId);

	/**
	 * Removes the where clause entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry that was removed
	 * @throws NoSuchWhereClauseEntryException if a where clause entry with the primary key could not be found
	 */
	public WhereClauseEntry remove(long whereClauseEntryId)
		throws NoSuchWhereClauseEntryException;

	public WhereClauseEntry updateImpl(WhereClauseEntry whereClauseEntry);

	/**
	 * Returns the where clause entry with the primary key or throws a <code>NoSuchWhereClauseEntryException</code> if it could not be found.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry
	 * @throws NoSuchWhereClauseEntryException if a where clause entry with the primary key could not be found
	 */
	public WhereClauseEntry findByPrimaryKey(long whereClauseEntryId)
		throws NoSuchWhereClauseEntryException;

	/**
	 * Returns the where clause entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry, or <code>null</code> if a where clause entry with the primary key could not be found
	 */
	public WhereClauseEntry fetchByPrimaryKey(long whereClauseEntryId);

	/**
	 * Returns all the where clause entries.
	 *
	 * @return the where clause entries
	 */
	public java.util.List<WhereClauseEntry> findAll();

	/**
	 * Returns a range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @return the range of where clause entries
	 */
	public java.util.List<WhereClauseEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of where clause entries
	 */
	public java.util.List<WhereClauseEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of where clause entries
	 */
	public java.util.List<WhereClauseEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WhereClauseEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the where clause entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of where clause entries.
	 *
	 * @return the number of where clause entries
	 */
	public int countAll();

}
// SB-Hash:-297899898