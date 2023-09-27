/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchNullConvertibleEntryException;
import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the null convertible entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see NullConvertibleEntryUtil
 * @generated
 */
@ProviderType
public interface NullConvertibleEntryPersistence
	extends BasePersistence<NullConvertibleEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NullConvertibleEntryUtil} to access the null convertible entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the null convertible entry where name = &#63; or throws a <code>NoSuchNullConvertibleEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching null convertible entry
	 * @throws NoSuchNullConvertibleEntryException if a matching null convertible entry could not be found
	 */
	public NullConvertibleEntry findByName(String name)
		throws NoSuchNullConvertibleEntryException;

	/**
	 * Returns the null convertible entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching null convertible entry, or <code>null</code> if a matching null convertible entry could not be found
	 */
	public NullConvertibleEntry fetchByName(String name);

	/**
	 * Returns the null convertible entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching null convertible entry, or <code>null</code> if a matching null convertible entry could not be found
	 */
	public NullConvertibleEntry fetchByName(
		String name, boolean useFinderCache);

	/**
	 * Removes the null convertible entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the null convertible entry that was removed
	 */
	public NullConvertibleEntry removeByName(String name)
		throws NoSuchNullConvertibleEntryException;

	/**
	 * Returns the number of null convertible entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching null convertible entries
	 */
	public int countByName(String name);

	/**
	 * Caches the null convertible entry in the entity cache if it is enabled.
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 */
	public void cacheResult(NullConvertibleEntry nullConvertibleEntry);

	/**
	 * Caches the null convertible entries in the entity cache if it is enabled.
	 *
	 * @param nullConvertibleEntries the null convertible entries
	 */
	public void cacheResult(
		java.util.List<NullConvertibleEntry> nullConvertibleEntries);

	/**
	 * Creates a new null convertible entry with the primary key. Does not add the null convertible entry to the database.
	 *
	 * @param nullConvertibleEntryId the primary key for the new null convertible entry
	 * @return the new null convertible entry
	 */
	public NullConvertibleEntry create(long nullConvertibleEntryId);

	/**
	 * Removes the null convertible entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry that was removed
	 * @throws NoSuchNullConvertibleEntryException if a null convertible entry with the primary key could not be found
	 */
	public NullConvertibleEntry remove(long nullConvertibleEntryId)
		throws NoSuchNullConvertibleEntryException;

	public NullConvertibleEntry updateImpl(
		NullConvertibleEntry nullConvertibleEntry);

	/**
	 * Returns the null convertible entry with the primary key or throws a <code>NoSuchNullConvertibleEntryException</code> if it could not be found.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry
	 * @throws NoSuchNullConvertibleEntryException if a null convertible entry with the primary key could not be found
	 */
	public NullConvertibleEntry findByPrimaryKey(long nullConvertibleEntryId)
		throws NoSuchNullConvertibleEntryException;

	/**
	 * Returns the null convertible entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry, or <code>null</code> if a null convertible entry with the primary key could not be found
	 */
	public NullConvertibleEntry fetchByPrimaryKey(long nullConvertibleEntryId);

	/**
	 * Returns all the null convertible entries.
	 *
	 * @return the null convertible entries
	 */
	public java.util.List<NullConvertibleEntry> findAll();

	/**
	 * Returns a range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @return the range of null convertible entries
	 */
	public java.util.List<NullConvertibleEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of null convertible entries
	 */
	public java.util.List<NullConvertibleEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NullConvertibleEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of null convertible entries
	 */
	public java.util.List<NullConvertibleEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NullConvertibleEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the null convertible entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of null convertible entries.
	 *
	 * @return the number of null convertible entries
	 */
	public int countAll();

}