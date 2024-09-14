/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.saved.content.exception.NoSuchSavedContentEntryException;
import com.liferay.saved.content.model.SavedContentEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the saved content entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryUtil
 * @generated
 */
@ProviderType
public interface SavedContentEntryPersistence
	extends BasePersistence<SavedContentEntry>,
			CTPersistence<SavedContentEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SavedContentEntryUtil} to access the saved content entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the saved content entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the saved content entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where uuid = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByUuid_PrevAndNext(
			long savedContentEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of saved content entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching saved content entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the saved content entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSavedContentEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the saved content entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the saved content entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the saved content entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the saved content entry that was removed
	 */
	public SavedContentEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the number of saved content entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching saved content entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the saved content entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the saved content entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByUuid_C_PrevAndNext(
			long savedContentEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of saved content entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching saved content entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the saved content entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the saved content entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where groupId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByGroupId_PrevAndNext(
			long savedContentEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns all the saved content entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the saved content entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set of saved content entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] filterFindByGroupId_PrevAndNext(
			long savedContentEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of saved content entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching saved content entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of saved content entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching saved content entries that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns all the saved content entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUserId(long userId);

	/**
	 * Returns a range of all the saved content entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where userId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByUserId_PrevAndNext(
			long savedContentEntryId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of saved content entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching saved content entries
	 */
	public int countByUserId(long userId);

	/**
	 * Returns all the saved content entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_U(
		long groupId, long userId);

	/**
	 * Returns a range of all the saved content entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_U(
		long groupId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByG_U_First(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByG_U_Last(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByG_U_PrevAndNext(
			long savedContentEntryId, long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns all the saved content entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_U(
		long groupId, long userId);

	/**
	 * Returns a range of all the saved content entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_U(
		long groupId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set of saved content entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] filterFindByG_U_PrevAndNext(
			long savedContentEntryId, long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public void removeByG_U(long groupId, long userId);

	/**
	 * Returns the number of saved content entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching saved content entries
	 */
	public int countByG_U(long groupId, long userId);

	/**
	 * Returns the number of saved content entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching saved content entries that the user has permission to view
	 */
	public int filterCountByG_U(long groupId, long userId);

	/**
	 * Returns all the saved content entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_CN(
		long groupId, long classNameId);

	/**
	 * Returns a range of all the saved content entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_CN(
		long groupId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_CN(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_CN(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByG_CN_First(
			long groupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_CN_First(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByG_CN_Last(
			long groupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_CN_Last(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByG_CN_PrevAndNext(
			long savedContentEntryId, long groupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns all the saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_CN(
		long groupId, long classNameId);

	/**
	 * Returns a range of all the saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_CN(
		long groupId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_CN(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set of saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] filterFindByG_CN_PrevAndNext(
			long savedContentEntryId, long groupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	public void removeByG_CN(long groupId, long classNameId);

	/**
	 * Returns the number of saved content entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching saved content entries
	 */
	public int countByG_CN(long groupId, long classNameId);

	/**
	 * Returns the number of saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching saved content entries that the user has permission to view
	 */
	public int filterCountByG_CN(long groupId, long classNameId);

	/**
	 * Returns all the saved content entries where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByU_C(
		long userId, long classNameId);

	/**
	 * Returns a range of all the saved content entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByU_C(
		long userId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByU_C_First(
			long userId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByU_C_First(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByU_C_Last(
			long userId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByU_C_Last(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByU_C_PrevAndNext(
			long savedContentEntryId, long userId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 */
	public void removeByU_C(long userId, long classNameId);

	/**
	 * Returns the number of saved content entries where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching saved content entries
	 */
	public int countByU_C(long userId, long classNameId);

	/**
	 * Returns all the saved content entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_C_C(
		long groupId, long classNameId, long classPK);

	/**
	 * Returns a range of all the saved content entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByG_C_C_PrevAndNext(
			long savedContentEntryId, long groupId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns all the saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_C_C(
		long groupId, long classNameId, long classPK);

	/**
	 * Returns a range of all the saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries that the user has permission to view
	 */
	public java.util.List<SavedContentEntry> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set of saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] filterFindByG_C_C_PrevAndNext(
			long savedContentEntryId, long groupId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Returns the number of saved content entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching saved content entries
	 */
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Returns the number of saved content entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching saved content entries that the user has permission to view
	 */
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Returns all the saved content entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK);

	/**
	 * Returns a range of all the saved content entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saved content entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the first saved content entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the last saved content entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the last saved content entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns the saved content entries before and after the current saved content entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param savedContentEntryId the primary key of the current saved content entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry[] findByC_C_C_PrevAndNext(
			long savedContentEntryId, long companyId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws NoSuchSavedContentEntryException;

	/**
	 * Removes all the saved content entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns the number of saved content entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching saved content entries
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns the saved content entry where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchSavedContentEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByG_U_C_C(
			long groupId, long userId, long classNameId, long classPK)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the saved content entry where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK);

	/**
	 * Returns the saved content entry where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK,
		boolean useFinderCache);

	/**
	 * Removes the saved content entry where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the saved content entry that was removed
	 */
	public SavedContentEntry removeByG_U_C_C(
			long groupId, long userId, long classNameId, long classPK)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the number of saved content entries where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching saved content entries
	 */
	public int countByG_U_C_C(
		long groupId, long userId, long classNameId, long classPK);

	/**
	 * Returns all the saved content entries where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @return the matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_U_C_C(
		long companyId, long userId, long classNameId, long[] classPKs);

	/**
	 * Returns a range of all the saved content entries where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_U_C_C(
		long companyId, long userId, long classNameId, long[] classPKs,
		int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_U_C_C(
		long companyId, long userId, long classNameId, long[] classPKs,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saved content entries
	 */
	public java.util.List<SavedContentEntry> findByC_U_C_C(
		long companyId, long userId, long classNameId, long[] classPKs,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the saved content entry where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchSavedContentEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching saved content entry
	 * @throws NoSuchSavedContentEntryException if a matching saved content entry could not be found
	 */
	public SavedContentEntry findByC_U_C_C(
			long companyId, long userId, long classNameId, long classPK)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the saved content entry where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK);

	/**
	 * Returns the saved content entry where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public SavedContentEntry fetchByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK,
		boolean useFinderCache);

	/**
	 * Removes the saved content entry where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the saved content entry that was removed
	 */
	public SavedContentEntry removeByC_U_C_C(
			long companyId, long userId, long classNameId, long classPK)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the number of saved content entries where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching saved content entries
	 */
	public int countByC_U_C_C(
		long companyId, long userId, long classNameId, long classPK);

	/**
	 * Returns the number of saved content entries where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @return the number of matching saved content entries
	 */
	public int countByC_U_C_C(
		long companyId, long userId, long classNameId, long[] classPKs);

	/**
	 * Caches the saved content entry in the entity cache if it is enabled.
	 *
	 * @param savedContentEntry the saved content entry
	 */
	public void cacheResult(SavedContentEntry savedContentEntry);

	/**
	 * Caches the saved content entries in the entity cache if it is enabled.
	 *
	 * @param savedContentEntries the saved content entries
	 */
	public void cacheResult(
		java.util.List<SavedContentEntry> savedContentEntries);

	/**
	 * Creates a new saved content entry with the primary key. Does not add the saved content entry to the database.
	 *
	 * @param savedContentEntryId the primary key for the new saved content entry
	 * @return the new saved content entry
	 */
	public SavedContentEntry create(long savedContentEntryId);

	/**
	 * Removes the saved content entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry that was removed
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry remove(long savedContentEntryId)
		throws NoSuchSavedContentEntryException;

	public SavedContentEntry updateImpl(SavedContentEntry savedContentEntry);

	/**
	 * Returns the saved content entry with the primary key or throws a <code>NoSuchSavedContentEntryException</code> if it could not be found.
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry
	 * @throws NoSuchSavedContentEntryException if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry findByPrimaryKey(long savedContentEntryId)
		throws NoSuchSavedContentEntryException;

	/**
	 * Returns the saved content entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry, or <code>null</code> if a saved content entry with the primary key could not be found
	 */
	public SavedContentEntry fetchByPrimaryKey(long savedContentEntryId);

	/**
	 * Returns all the saved content entries.
	 *
	 * @return the saved content entries
	 */
	public java.util.List<SavedContentEntry> findAll();

	/**
	 * Returns a range of all the saved content entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of saved content entries
	 */
	public java.util.List<SavedContentEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the saved content entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saved content entries
	 */
	public java.util.List<SavedContentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saved content entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saved content entries
	 */
	public java.util.List<SavedContentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the saved content entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of saved content entries.
	 *
	 * @return the number of saved content entries
	 */
	public int countAll();

}