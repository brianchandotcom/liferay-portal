/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.style.book.exception.NoSuchEntryException;
import com.liferay.style.book.model.StyleBookEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the style book entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryUtil
 * @generated
 */
@ProviderType
public interface StyleBookEntryPersistence
	extends BasePersistence<StyleBookEntry>, CTPersistence<StyleBookEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link StyleBookEntryUtil} to access the style book entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the style book entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByUuid_PrevAndNext(
			long styleBookEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of style book entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching style book entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head);

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_Head_First(
			String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_Head_Last(
			String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByUuid_Head_PrevAndNext(
			long styleBookEntryId, String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	public void removeByUuid_Head(String uuid, boolean head);

	/**
	 * Returns the number of style book entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public int countByUuid_Head(String uuid, boolean head);

	/**
	 * Returns all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUUID_G_First(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUUID_G_First(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUUID_G_Last(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByUUID_G_PrevAndNext(
			long styleBookEntryId, String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public void removeByUUID_G(String uuid, long groupId);

	/**
	 * Returns the number of style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching style book entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head);

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache);

	/**
	 * Removes the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the style book entry that was removed
	 */
	public StyleBookEntry removeByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the number of style book entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public int countByUUID_G_Head(String uuid, long groupId, boolean head);

	/**
	 * Returns all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByUuid_C_PrevAndNext(
			long styleBookEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching style book entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head);

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByUuid_C_Head_PrevAndNext(
			long styleBookEntryId, String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head);

	/**
	 * Returns the number of style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public int countByUuid_C_Head(String uuid, long companyId, boolean head);

	/**
	 * Returns all the style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByGroupId_PrevAndNext(
			long styleBookEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching style book entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head);

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByGroupId_Head_First(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByGroupId_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	public void removeByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns the number of style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public int countByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry);

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_D_First(
			long groupId, boolean defaultStyleBookEntry,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_D_First(
		long groupId, boolean defaultStyleBookEntry,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_D_Last(
			long groupId, boolean defaultStyleBookEntry,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_D_Last(
		long groupId, boolean defaultStyleBookEntry,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByG_D_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 */
	public void removeByG_D(long groupId, boolean defaultStyleBookEntry);

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the number of matching style book entries
	 */
	public int countByG_D(long groupId, boolean defaultStyleBookEntry);

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head);

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_D_Head_First(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_D_Head_First(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_D_Head_Last(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_D_Head_Last(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByG_D_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 */
	public void removeByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head);

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public int countByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head);

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN(
		long groupId, String name);

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_LikeN_First(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_LikeN_First(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_LikeN_Last(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_LikeN_Last(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByG_LikeN_PrevAndNext(
			long styleBookEntryId, long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	public void removeByG_LikeN(long groupId, String name);

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching style book entries
	 */
	public int countByG_LikeN(long groupId, String name);

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head);

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_LikeN_Head_First(
			long groupId, String name, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_LikeN_Head_First(
		long groupId, String name, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_LikeN_Head_Last(
			long groupId, String name, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_LikeN_Head_Last(
		long groupId, String name, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByG_LikeN_Head_PrevAndNext(
			long styleBookEntryId, long groupId, String name, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 */
	public void removeByG_LikeN_Head(long groupId, String name, boolean head);

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public int countByG_LikeN_Head(long groupId, String name, boolean head);

	/**
	 * Returns all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey);

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	public java.util.List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_SBEK_First(
			long groupId, String styleBookEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_SBEK_First(
		long groupId, String styleBookEntryKey,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_SBEK_Last(
			long groupId, String styleBookEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_SBEK_Last(
		long groupId, String styleBookEntryKey,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry[] findByG_SBEK_PrevAndNext(
			long styleBookEntryId, long groupId, String styleBookEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the style book entries where groupId = &#63; and styleBookEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 */
	public void removeByG_SBEK(long groupId, String styleBookEntryKey);

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the number of matching style book entries
	 */
	public int countByG_SBEK(long groupId, String styleBookEntryKey);

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head);

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head,
		boolean useFinderCache);

	/**
	 * Removes the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the style book entry that was removed
	 */
	public StyleBookEntry removeByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	public int countByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head);

	/**
	 * Returns the style book entry where headId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	public StyleBookEntry findByHeadId(long headId) throws NoSuchEntryException;

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByHeadId(long headId);

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	public StyleBookEntry fetchByHeadId(long headId, boolean useFinderCache);

	/**
	 * Removes the style book entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the style book entry that was removed
	 */
	public StyleBookEntry removeByHeadId(long headId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of style book entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching style book entries
	 */
	public int countByHeadId(long headId);

	/**
	 * Caches the style book entry in the entity cache if it is enabled.
	 *
	 * @param styleBookEntry the style book entry
	 */
	public void cacheResult(StyleBookEntry styleBookEntry);

	/**
	 * Caches the style book entries in the entity cache if it is enabled.
	 *
	 * @param styleBookEntries the style book entries
	 */
	public void cacheResult(java.util.List<StyleBookEntry> styleBookEntries);

	/**
	 * Creates a new style book entry with the primary key. Does not add the style book entry to the database.
	 *
	 * @param styleBookEntryId the primary key for the new style book entry
	 * @return the new style book entry
	 */
	public StyleBookEntry create(long styleBookEntryId);

	/**
	 * Removes the style book entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry that was removed
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry remove(long styleBookEntryId)
		throws NoSuchEntryException;

	public StyleBookEntry updateImpl(StyleBookEntry styleBookEntry);

	/**
	 * Returns the style book entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry findByPrimaryKey(long styleBookEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the style book entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry, or <code>null</code> if a style book entry with the primary key could not be found
	 */
	public StyleBookEntry fetchByPrimaryKey(long styleBookEntryId);

	/**
	 * Returns all the style book entries.
	 *
	 * @return the style book entries
	 */
	public java.util.List<StyleBookEntry> findAll();

	/**
	 * Returns a range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of style book entries
	 */
	public java.util.List<StyleBookEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of style book entries
	 */
	public java.util.List<StyleBookEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of style book entries
	 */
	public java.util.List<StyleBookEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the style book entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of style book entries.
	 *
	 * @return the number of style book entries
	 */
	public int countAll();

}