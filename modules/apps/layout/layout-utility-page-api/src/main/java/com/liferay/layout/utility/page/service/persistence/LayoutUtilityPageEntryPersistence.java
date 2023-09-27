/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.service.persistence;

import com.liferay.layout.utility.page.exception.NoSuchLayoutUtilityPageEntryException;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout utility page entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutUtilityPageEntryUtil
 * @generated
 */
@ProviderType
public interface LayoutUtilityPageEntryPersistence
	extends BasePersistence<LayoutUtilityPageEntry>,
			CTPersistence<LayoutUtilityPageEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutUtilityPageEntryUtil} to access the layout utility page entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout utility page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout utility page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] findByUuid_PrevAndNext(
			long LayoutUtilityPageEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Removes all the layout utility page entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout utility page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout utility page entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout utility page entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the layout utility page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the layout utility page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout utility page entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout utility page entry that was removed
	 */
	public LayoutUtilityPageEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the number of layout utility page entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] findByUuid_C_PrevAndNext(
			long LayoutUtilityPageEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Removes all the layout utility page entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout utility page entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout utility page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the layout utility page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] findByGroupId_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns all the layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByGroupId(
		long groupId);

	/**
	 * Returns a range of all the layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set of layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] filterFindByGroupId_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Removes all the layout utility page entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of layout utility page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the layout utility page entry where plid = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param plid the plid
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByPlid(long plid)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the layout utility page entry where plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param plid the plid
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByPlid(long plid);

	/**
	 * Returns the layout utility page entry where plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByPlid(
		long plid, boolean useFinderCache);

	/**
	 * Removes the layout utility page entry where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @return the layout utility page entry that was removed
	 */
	public LayoutUtilityPageEntry removeByPlid(long plid)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the number of layout utility page entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout utility page entries
	 */
	public int countByPlid(long plid);

	/**
	 * Returns all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type);

	/**
	 * Returns a range of all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByG_T_First(
			long groupId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByG_T_First(
		long groupId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByG_T_Last(
			long groupId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByG_T_Last(
		long groupId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] findByG_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns all the layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByG_T(
		long groupId, String type);

	/**
	 * Returns a range of all the layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByG_T(
		long groupId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByG_T(
		long groupId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set of layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] filterFindByG_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Removes all the layout utility page entries where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public void removeByG_T(long groupId, String type);

	/**
	 * Returns the number of layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout utility page entries
	 */
	public int countByG_T(long groupId, String type);

	/**
	 * Returns the number of layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout utility page entries that the user has permission to view
	 */
	public int filterCountByG_T(long groupId, String type);

	/**
	 * Returns all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type);

	/**
	 * Returns a range of all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByG_D_T_First(
			long groupId, boolean defaultLayoutUtilityPageEntry, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByG_D_T_First(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByG_D_T_Last(
			long groupId, boolean defaultLayoutUtilityPageEntry, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByG_D_T_Last(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] findByG_D_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			boolean defaultLayoutUtilityPageEntry, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns all the layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type);

	/**
	 * Returns a range of all the layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries that the user has permissions to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries that the user has permission to view
	 */
	public java.util.List<LayoutUtilityPageEntry> filterFindByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set of layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry[] filterFindByG_D_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			boolean defaultLayoutUtilityPageEntry, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutUtilityPageEntry> orderByComparator)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Removes all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 */
	public void removeByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type);

	/**
	 * Returns the number of layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the number of matching layout utility page entries
	 */
	public int countByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type);

	/**
	 * Returns the number of layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the number of matching layout utility page entries that the user has permission to view
	 */
	public int filterCountByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type);

	/**
	 * Returns the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByG_N_T(
			long groupId, String name, String type)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByG_N_T(
		long groupId, String name, String type);

	/**
	 * Returns the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByG_N_T(
		long groupId, String name, String type, boolean useFinderCache);

	/**
	 * Removes the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the layout utility page entry that was removed
	 */
	public LayoutUtilityPageEntry removeByG_N_T(
			long groupId, String name, String type)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the number of layout utility page entries where groupId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout utility page entries
	 */
	public int countByG_N_T(long groupId, String name, String type);

	/**
	 * Returns the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry findByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByERC_G(
		String externalReferenceCode, long groupId);

	/**
	 * Returns the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public LayoutUtilityPageEntry fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the layout utility page entry that was removed
	 */
	public LayoutUtilityPageEntry removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the number of layout utility page entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries
	 */
	public int countByERC_G(String externalReferenceCode, long groupId);

	/**
	 * Caches the layout utility page entry in the entity cache if it is enabled.
	 *
	 * @param layoutUtilityPageEntry the layout utility page entry
	 */
	public void cacheResult(LayoutUtilityPageEntry layoutUtilityPageEntry);

	/**
	 * Caches the layout utility page entries in the entity cache if it is enabled.
	 *
	 * @param layoutUtilityPageEntries the layout utility page entries
	 */
	public void cacheResult(
		java.util.List<LayoutUtilityPageEntry> layoutUtilityPageEntries);

	/**
	 * Creates a new layout utility page entry with the primary key. Does not add the layout utility page entry to the database.
	 *
	 * @param LayoutUtilityPageEntryId the primary key for the new layout utility page entry
	 * @return the new layout utility page entry
	 */
	public LayoutUtilityPageEntry create(long LayoutUtilityPageEntryId);

	/**
	 * Removes the layout utility page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the layout utility page entry
	 * @return the layout utility page entry that was removed
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry remove(long LayoutUtilityPageEntryId)
		throws NoSuchLayoutUtilityPageEntryException;

	public LayoutUtilityPageEntry updateImpl(
		LayoutUtilityPageEntry layoutUtilityPageEntry);

	/**
	 * Returns the layout utility page entry with the primary key or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the layout utility page entry
	 * @return the layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry findByPrimaryKey(
			long LayoutUtilityPageEntryId)
		throws NoSuchLayoutUtilityPageEntryException;

	/**
	 * Returns the layout utility page entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the layout utility page entry
	 * @return the layout utility page entry, or <code>null</code> if a layout utility page entry with the primary key could not be found
	 */
	public LayoutUtilityPageEntry fetchByPrimaryKey(
		long LayoutUtilityPageEntryId);

	/**
	 * Returns all the layout utility page entries.
	 *
	 * @return the layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findAll();

	/**
	 * Returns a range of all the layout utility page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout utility page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the layout utility page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout utility page entries
	 */
	public java.util.List<LayoutUtilityPageEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout utility page entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout utility page entries.
	 *
	 * @return the number of layout utility page entries
	 */
	public int countAll();

}