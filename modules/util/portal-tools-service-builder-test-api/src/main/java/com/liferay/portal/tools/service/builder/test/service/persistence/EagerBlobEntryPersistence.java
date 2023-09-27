/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchEagerBlobEntryException;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the eager blob entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntryUtil
 * @generated
 */
@ProviderType
public interface EagerBlobEntryPersistence
	extends BasePersistence<EagerBlobEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link EagerBlobEntryUtil} to access the eager blob entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the eager blob entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the eager blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @return the range of matching eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the eager blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the eager blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a matching eager blob entry could not be found
	 */
	public EagerBlobEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
				orderByComparator)
		throws NoSuchEagerBlobEntryException;

	/**
	 * Returns the first eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public EagerBlobEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
			orderByComparator);

	/**
	 * Returns the last eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a matching eager blob entry could not be found
	 */
	public EagerBlobEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
				orderByComparator)
		throws NoSuchEagerBlobEntryException;

	/**
	 * Returns the last eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public EagerBlobEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
			orderByComparator);

	/**
	 * Returns the eager blob entries before and after the current eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param eagerBlobEntryId the primary key of the current eager blob entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a eager blob entry with the primary key could not be found
	 */
	public EagerBlobEntry[] findByUuid_PrevAndNext(
			long eagerBlobEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
				orderByComparator)
		throws NoSuchEagerBlobEntryException;

	/**
	 * Removes all the eager blob entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of eager blob entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching eager blob entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the eager blob entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEagerBlobEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a matching eager blob entry could not be found
	 */
	public EagerBlobEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEagerBlobEntryException;

	/**
	 * Returns the eager blob entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public EagerBlobEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the eager blob entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public EagerBlobEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the eager blob entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the eager blob entry that was removed
	 */
	public EagerBlobEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEagerBlobEntryException;

	/**
	 * Returns the number of eager blob entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching eager blob entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Caches the eager blob entry in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntry the eager blob entry
	 */
	public void cacheResult(EagerBlobEntry eagerBlobEntry);

	/**
	 * Caches the eager blob entries in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntries the eager blob entries
	 */
	public void cacheResult(java.util.List<EagerBlobEntry> eagerBlobEntries);

	/**
	 * Creates a new eager blob entry with the primary key. Does not add the eager blob entry to the database.
	 *
	 * @param eagerBlobEntryId the primary key for the new eager blob entry
	 * @return the new eager blob entry
	 */
	public EagerBlobEntry create(long eagerBlobEntryId);

	/**
	 * Removes the eager blob entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry that was removed
	 * @throws NoSuchEagerBlobEntryException if a eager blob entry with the primary key could not be found
	 */
	public EagerBlobEntry remove(long eagerBlobEntryId)
		throws NoSuchEagerBlobEntryException;

	public EagerBlobEntry updateImpl(EagerBlobEntry eagerBlobEntry);

	/**
	 * Returns the eager blob entry with the primary key or throws a <code>NoSuchEagerBlobEntryException</code> if it could not be found.
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a eager blob entry with the primary key could not be found
	 */
	public EagerBlobEntry findByPrimaryKey(long eagerBlobEntryId)
		throws NoSuchEagerBlobEntryException;

	/**
	 * Returns the eager blob entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry, or <code>null</code> if a eager blob entry with the primary key could not be found
	 */
	public EagerBlobEntry fetchByPrimaryKey(long eagerBlobEntryId);

	/**
	 * Returns all the eager blob entries.
	 *
	 * @return the eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findAll();

	/**
	 * Returns a range of all the eager blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @return the range of eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the eager blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the eager blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of eager blob entries
	 */
	public java.util.List<EagerBlobEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the eager blob entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of eager blob entries.
	 *
	 * @return the number of eager blob entries
	 */
	public int countAll();

}