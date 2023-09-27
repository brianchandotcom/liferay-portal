/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.redirect.exception.NoSuchNotFoundEntryException;
import com.liferay.redirect.model.RedirectNotFoundEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the redirect not found entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedirectNotFoundEntryUtil
 * @generated
 */
@ProviderType
public interface RedirectNotFoundEntryPersistence
	extends BasePersistence<RedirectNotFoundEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RedirectNotFoundEntryUtil} to access the redirect not found entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the redirect not found entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @return the range of matching redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectNotFoundEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectNotFoundEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	public RedirectNotFoundEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RedirectNotFoundEntry> orderByComparator)
		throws NoSuchNotFoundEntryException;

	/**
	 * Returns the first redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public RedirectNotFoundEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectNotFoundEntry>
			orderByComparator);

	/**
	 * Returns the last redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	public RedirectNotFoundEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RedirectNotFoundEntry> orderByComparator)
		throws NoSuchNotFoundEntryException;

	/**
	 * Returns the last redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public RedirectNotFoundEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectNotFoundEntry>
			orderByComparator);

	/**
	 * Returns the redirect not found entries before and after the current redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param redirectNotFoundEntryId the primary key of the current redirect not found entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	public RedirectNotFoundEntry[] findByGroupId_PrevAndNext(
			long redirectNotFoundEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RedirectNotFoundEntry> orderByComparator)
		throws NoSuchNotFoundEntryException;

	/**
	 * Removes all the redirect not found entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of redirect not found entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching redirect not found entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or throws a <code>NoSuchNotFoundEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	public RedirectNotFoundEntry findByG_U(long groupId, String url)
		throws NoSuchNotFoundEntryException;

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public RedirectNotFoundEntry fetchByG_U(long groupId, String url);

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public RedirectNotFoundEntry fetchByG_U(
		long groupId, String url, boolean useFinderCache);

	/**
	 * Removes the redirect not found entry where groupId = &#63; and url = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the redirect not found entry that was removed
	 */
	public RedirectNotFoundEntry removeByG_U(long groupId, String url)
		throws NoSuchNotFoundEntryException;

	/**
	 * Returns the number of redirect not found entries where groupId = &#63; and url = &#63;.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the number of matching redirect not found entries
	 */
	public int countByG_U(long groupId, String url);

	/**
	 * Caches the redirect not found entry in the entity cache if it is enabled.
	 *
	 * @param redirectNotFoundEntry the redirect not found entry
	 */
	public void cacheResult(RedirectNotFoundEntry redirectNotFoundEntry);

	/**
	 * Caches the redirect not found entries in the entity cache if it is enabled.
	 *
	 * @param redirectNotFoundEntries the redirect not found entries
	 */
	public void cacheResult(
		java.util.List<RedirectNotFoundEntry> redirectNotFoundEntries);

	/**
	 * Creates a new redirect not found entry with the primary key. Does not add the redirect not found entry to the database.
	 *
	 * @param redirectNotFoundEntryId the primary key for the new redirect not found entry
	 * @return the new redirect not found entry
	 */
	public RedirectNotFoundEntry create(long redirectNotFoundEntryId);

	/**
	 * Removes the redirect not found entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry that was removed
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	public RedirectNotFoundEntry remove(long redirectNotFoundEntryId)
		throws NoSuchNotFoundEntryException;

	public RedirectNotFoundEntry updateImpl(
		RedirectNotFoundEntry redirectNotFoundEntry);

	/**
	 * Returns the redirect not found entry with the primary key or throws a <code>NoSuchNotFoundEntryException</code> if it could not be found.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	public RedirectNotFoundEntry findByPrimaryKey(long redirectNotFoundEntryId)
		throws NoSuchNotFoundEntryException;

	/**
	 * Returns the redirect not found entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry, or <code>null</code> if a redirect not found entry with the primary key could not be found
	 */
	public RedirectNotFoundEntry fetchByPrimaryKey(
		long redirectNotFoundEntryId);

	/**
	 * Returns all the redirect not found entries.
	 *
	 * @return the redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findAll();

	/**
	 * Returns a range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @return the range of redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectNotFoundEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redirect not found entries
	 */
	public java.util.List<RedirectNotFoundEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectNotFoundEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the redirect not found entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of redirect not found entries.
	 *
	 * @return the number of redirect not found entries
	 */
	public int countAll();

}