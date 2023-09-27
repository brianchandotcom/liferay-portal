/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLazyBlobEntryException;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the lazy blob entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntryUtil
 * @generated
 */
@ProviderType
public interface LazyBlobEntryPersistence
	extends BasePersistence<LazyBlobEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LazyBlobEntryUtil} to access the lazy blob entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the lazy blob entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the lazy blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entries
	 * @param end the upper bound of the range of lazy blob entries (not inclusive)
	 * @return the range of matching lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the lazy blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entries
	 * @param end the upper bound of the range of lazy blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lazy blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entries
	 * @param end the upper bound of the range of lazy blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lazy blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entry
	 * @throws NoSuchLazyBlobEntryException if a matching lazy blob entry could not be found
	 */
	public LazyBlobEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
				orderByComparator)
		throws NoSuchLazyBlobEntryException;

	/**
	 * Returns the first lazy blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entry, or <code>null</code> if a matching lazy blob entry could not be found
	 */
	public LazyBlobEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
			orderByComparator);

	/**
	 * Returns the last lazy blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entry
	 * @throws NoSuchLazyBlobEntryException if a matching lazy blob entry could not be found
	 */
	public LazyBlobEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
				orderByComparator)
		throws NoSuchLazyBlobEntryException;

	/**
	 * Returns the last lazy blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entry, or <code>null</code> if a matching lazy blob entry could not be found
	 */
	public LazyBlobEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
			orderByComparator);

	/**
	 * Returns the lazy blob entries before and after the current lazy blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param lazyBlobEntryId the primary key of the current lazy blob entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lazy blob entry
	 * @throws NoSuchLazyBlobEntryException if a lazy blob entry with the primary key could not be found
	 */
	public LazyBlobEntry[] findByUuid_PrevAndNext(
			long lazyBlobEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
				orderByComparator)
		throws NoSuchLazyBlobEntryException;

	/**
	 * Removes all the lazy blob entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of lazy blob entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lazy blob entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the lazy blob entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLazyBlobEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entry
	 * @throws NoSuchLazyBlobEntryException if a matching lazy blob entry could not be found
	 */
	public LazyBlobEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchLazyBlobEntryException;

	/**
	 * Returns the lazy blob entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entry, or <code>null</code> if a matching lazy blob entry could not be found
	 */
	public LazyBlobEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the lazy blob entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lazy blob entry, or <code>null</code> if a matching lazy blob entry could not be found
	 */
	public LazyBlobEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the lazy blob entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the lazy blob entry that was removed
	 */
	public LazyBlobEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchLazyBlobEntryException;

	/**
	 * Returns the number of lazy blob entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lazy blob entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Caches the lazy blob entry in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntry the lazy blob entry
	 */
	public void cacheResult(LazyBlobEntry lazyBlobEntry);

	/**
	 * Caches the lazy blob entries in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntries the lazy blob entries
	 */
	public void cacheResult(java.util.List<LazyBlobEntry> lazyBlobEntries);

	/**
	 * Creates a new lazy blob entry with the primary key. Does not add the lazy blob entry to the database.
	 *
	 * @param lazyBlobEntryId the primary key for the new lazy blob entry
	 * @return the new lazy blob entry
	 */
	public LazyBlobEntry create(long lazyBlobEntryId);

	/**
	 * Removes the lazy blob entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntryId the primary key of the lazy blob entry
	 * @return the lazy blob entry that was removed
	 * @throws NoSuchLazyBlobEntryException if a lazy blob entry with the primary key could not be found
	 */
	public LazyBlobEntry remove(long lazyBlobEntryId)
		throws NoSuchLazyBlobEntryException;

	public LazyBlobEntry updateImpl(LazyBlobEntry lazyBlobEntry);

	/**
	 * Returns the lazy blob entry with the primary key or throws a <code>NoSuchLazyBlobEntryException</code> if it could not be found.
	 *
	 * @param lazyBlobEntryId the primary key of the lazy blob entry
	 * @return the lazy blob entry
	 * @throws NoSuchLazyBlobEntryException if a lazy blob entry with the primary key could not be found
	 */
	public LazyBlobEntry findByPrimaryKey(long lazyBlobEntryId)
		throws NoSuchLazyBlobEntryException;

	/**
	 * Returns the lazy blob entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lazyBlobEntryId the primary key of the lazy blob entry
	 * @return the lazy blob entry, or <code>null</code> if a lazy blob entry with the primary key could not be found
	 */
	public LazyBlobEntry fetchByPrimaryKey(long lazyBlobEntryId);

	/**
	 * Returns all the lazy blob entries.
	 *
	 * @return the lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findAll();

	/**
	 * Returns a range of all the lazy blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entries
	 * @param end the upper bound of the range of lazy blob entries (not inclusive)
	 * @return the range of lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the lazy blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entries
	 * @param end the upper bound of the range of lazy blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lazy blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entries
	 * @param end the upper bound of the range of lazy blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lazy blob entries
	 */
	public java.util.List<LazyBlobEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the lazy blob entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of lazy blob entries.
	 *
	 * @return the number of lazy blob entries
	 */
	public int countAll();

}