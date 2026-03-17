/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchUndefinedDefaultOrderEntryException;
import com.liferay.portal.tools.service.builder.test.model.UndefinedDefaultOrderEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the undefined default order entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UndefinedDefaultOrderEntryUtil
 * @generated
 */
@ProviderType
public interface UndefinedDefaultOrderEntryPersistence
	extends BasePersistence<UndefinedDefaultOrderEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UndefinedDefaultOrderEntryUtil} to access the undefined default order entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the undefined default order entry where name = &#63; or throws a <code>NoSuchUndefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	public UndefinedDefaultOrderEntry findByName(String name)
		throws NoSuchUndefinedDefaultOrderEntryException;

	/**
	 * Returns the undefined default order entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public UndefinedDefaultOrderEntry fetchByName(String name);

	/**
	 * Returns the undefined default order entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public UndefinedDefaultOrderEntry fetchByName(
		String name, boolean useFinderCache);

	/**
	 * Removes the undefined default order entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the undefined default order entry that was removed
	 */
	public UndefinedDefaultOrderEntry removeByName(String name)
		throws NoSuchUndefinedDefaultOrderEntryException;

	/**
	 * Returns the number of undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching undefined default order entries
	 */
	public int countByName(String name);

	/**
	 * Returns all the undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name);

	/**
	 * Returns a range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @return the range of matching undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end);

	/**
	 * Returns an ordered range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UndefinedDefaultOrderEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UndefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	public UndefinedDefaultOrderEntry findByName_Collection_First(
			String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<UndefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchUndefinedDefaultOrderEntryException;

	/**
	 * Returns the first undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public UndefinedDefaultOrderEntry fetchByName_Collection_First(
		String name,
		com.liferay.portal.kernel.util.OrderByComparator
			<UndefinedDefaultOrderEntry> orderByComparator);

	/**
	 * Returns the last undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	public UndefinedDefaultOrderEntry findByName_Collection_Last(
			String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<UndefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchUndefinedDefaultOrderEntryException;

	/**
	 * Returns the last undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public UndefinedDefaultOrderEntry fetchByName_Collection_Last(
		String name,
		com.liferay.portal.kernel.util.OrderByComparator
			<UndefinedDefaultOrderEntry> orderByComparator);

	/**
	 * Returns the undefined default order entries before and after the current undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the current undefined default order entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	public UndefinedDefaultOrderEntry[] findByName_Collection_PrevAndNext(
			long undefinedDefaultOrderEntryId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<UndefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchUndefinedDefaultOrderEntryException;

	/**
	 * Removes all the undefined default order entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	public void removeByName_Collection(String name);

	/**
	 * Returns the number of undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching undefined default order entries
	 */
	public int countByName_Collection(String name);

	/**
	 * Caches the undefined default order entry in the entity cache if it is enabled.
	 *
	 * @param undefinedDefaultOrderEntry the undefined default order entry
	 */
	public void cacheResult(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry);

	/**
	 * Caches the undefined default order entries in the entity cache if it is enabled.
	 *
	 * @param undefinedDefaultOrderEntries the undefined default order entries
	 */
	public void cacheResult(
		java.util.List<UndefinedDefaultOrderEntry>
			undefinedDefaultOrderEntries);

	/**
	 * Creates a new undefined default order entry with the primary key. Does not add the undefined default order entry to the database.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key for the new undefined default order entry
	 * @return the new undefined default order entry
	 */
	public UndefinedDefaultOrderEntry create(long undefinedDefaultOrderEntryId);

	/**
	 * Removes the undefined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry that was removed
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	public UndefinedDefaultOrderEntry remove(long undefinedDefaultOrderEntryId)
		throws NoSuchUndefinedDefaultOrderEntryException;

	public UndefinedDefaultOrderEntry updateImpl(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry);

	/**
	 * Returns the undefined default order entry with the primary key or throws a <code>NoSuchUndefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	public UndefinedDefaultOrderEntry findByPrimaryKey(
			long undefinedDefaultOrderEntryId)
		throws NoSuchUndefinedDefaultOrderEntryException;

	/**
	 * Returns the undefined default order entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry, or <code>null</code> if a undefined default order entry with the primary key could not be found
	 */
	public UndefinedDefaultOrderEntry fetchByPrimaryKey(
		long undefinedDefaultOrderEntryId);

	/**
	 * Returns all the undefined default order entries.
	 *
	 * @return the undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findAll();

	/**
	 * Returns a range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @return the range of undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UndefinedDefaultOrderEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of undefined default order entries
	 */
	public java.util.List<UndefinedDefaultOrderEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<UndefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the undefined default order entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of undefined default order entries.
	 *
	 * @return the number of undefined default order entries
	 */
	public int countAll();

}
// SB-Hash:-401402857