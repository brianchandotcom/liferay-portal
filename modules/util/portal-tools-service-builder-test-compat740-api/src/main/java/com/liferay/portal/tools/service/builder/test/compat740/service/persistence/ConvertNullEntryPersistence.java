/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchConvertNullEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.ConvertNullEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the convert null entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConvertNullEntryUtil
 * @generated
 */
@ProviderType
public interface ConvertNullEntryPersistence
	extends BasePersistence<ConvertNullEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ConvertNullEntryUtil} to access the convert null entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the convert null entry where name = &#63; or throws a <code>NoSuchConvertNullEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching convert null entry
	 * @throws NoSuchConvertNullEntryException if a matching convert null entry could not be found
	 */
	public ConvertNullEntry findByName(String name)
		throws NoSuchConvertNullEntryException;

	/**
	 * Returns the convert null entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching convert null entry, or <code>null</code> if a matching convert null entry could not be found
	 */
	public ConvertNullEntry fetchByName(String name);

	/**
	 * Returns the convert null entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching convert null entry, or <code>null</code> if a matching convert null entry could not be found
	 */
	public ConvertNullEntry fetchByName(String name, boolean useFinderCache);

	/**
	 * Removes the convert null entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the convert null entry that was removed
	 */
	public ConvertNullEntry removeByName(String name)
		throws NoSuchConvertNullEntryException;

	/**
	 * Returns the number of convert null entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching convert null entries
	 */
	public int countByName(String name);

	/**
	 * Caches the convert null entry in the entity cache if it is enabled.
	 *
	 * @param convertNullEntry the convert null entry
	 */
	public void cacheResult(ConvertNullEntry convertNullEntry);

	/**
	 * Caches the convert null entries in the entity cache if it is enabled.
	 *
	 * @param convertNullEntries the convert null entries
	 */
	public void cacheResult(
		java.util.List<ConvertNullEntry> convertNullEntries);

	/**
	 * Creates a new convert null entry with the primary key. Does not add the convert null entry to the database.
	 *
	 * @param convertNullEntryId the primary key for the new convert null entry
	 * @return the new convert null entry
	 */
	public ConvertNullEntry create(long convertNullEntryId);

	/**
	 * Removes the convert null entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry that was removed
	 * @throws NoSuchConvertNullEntryException if a convert null entry with the primary key could not be found
	 */
	public ConvertNullEntry remove(long convertNullEntryId)
		throws NoSuchConvertNullEntryException;

	public ConvertNullEntry updateImpl(ConvertNullEntry convertNullEntry);

	/**
	 * Returns the convert null entry with the primary key or throws a <code>NoSuchConvertNullEntryException</code> if it could not be found.
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry
	 * @throws NoSuchConvertNullEntryException if a convert null entry with the primary key could not be found
	 */
	public ConvertNullEntry findByPrimaryKey(long convertNullEntryId)
		throws NoSuchConvertNullEntryException;

	/**
	 * Returns the convert null entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry, or <code>null</code> if a convert null entry with the primary key could not be found
	 */
	public ConvertNullEntry fetchByPrimaryKey(long convertNullEntryId);

	/**
	 * Returns all the convert null entries.
	 *
	 * @return the convert null entries
	 */
	public java.util.List<ConvertNullEntry> findAll();

	/**
	 * Returns a range of all the convert null entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConvertNullEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of convert null entries
	 * @param end the upper bound of the range of convert null entries (not inclusive)
	 * @return the range of convert null entries
	 */
	public java.util.List<ConvertNullEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the convert null entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConvertNullEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of convert null entries
	 * @param end the upper bound of the range of convert null entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of convert null entries
	 */
	public java.util.List<ConvertNullEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConvertNullEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the convert null entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConvertNullEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of convert null entries
	 * @param end the upper bound of the range of convert null entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of convert null entries
	 */
	public java.util.List<ConvertNullEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ConvertNullEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the convert null entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of convert null entries.
	 *
	 * @return the number of convert null entries
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:1002356193