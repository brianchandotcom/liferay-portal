/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchIndexEntryException;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the index entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntryUtil
 * @generated
 */
@ProviderType
public interface IndexEntryPersistence
	extends BasePersistence<IndexEntry>, CTPersistence<IndexEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link IndexEntryUtil} to access the index entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the index entries where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByOwnerId(long ownerId);

	/**
	 * Returns a range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByOwnerId_First(
			long ownerId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByOwnerId_First(
		long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByOwnerId_Last(
			long ownerId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByOwnerId_Last(
		long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByOwnerId_PrevAndNext(
			long indexEntryId, long ownerId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where ownerId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 */
	public void removeByOwnerId(long ownerId);

	/**
	 * Returns the number of index entries where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the number of matching index entries
	 */
	public int countByOwnerId(long ownerId);

	/**
	 * Returns all the index entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByPlid(long plid);

	/**
	 * Returns a range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByPlid(long plid, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByPlid_First(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByPlid_Last(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where plid = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByPlid_PrevAndNext(
			long indexEntryId, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public void removeByPlid(long plid);

	/**
	 * Returns the number of index entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching index entries
	 */
	public int countByPlid(long plid);

	/**
	 * Returns all the index entries where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByPortletId(String portletId);

	/**
	 * Returns a range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByPortletId(
		String portletId, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByPortletId(
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByPortletId(
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByPortletId_First(
			String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByPortletId_First(
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByPortletId_Last(
			String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByPortletId_Last(
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByPortletId_PrevAndNext(
			long indexEntryId, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 */
	public void removeByPortletId(String portletId);

	/**
	 * Returns the number of index entries where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public int countByPortletId(String portletId);

	/**
	 * Returns all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P(
		int ownerType, String portletId);

	/**
	 * Returns a range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_P_First(
			int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_P_First(
		int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_P_Last(
			int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_P_Last(
		int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByO_P_PrevAndNext(
			long indexEntryId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public void removeByO_P(int ownerType, String portletId);

	/**
	 * Returns the number of index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public int countByO_P(int ownerType, String portletId);

	/**
	 * Returns all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByP_P(long plid, String portletId);

	/**
	 * Returns a range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByP_P_First(
			long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByP_P_First(
		long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByP_P_Last(
			long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByP_P_Last(
		long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByP_P_PrevAndNext(
			long indexEntryId, long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public void removeByP_P(long plid, String portletId);

	/**
	 * Returns the number of index entries where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public int countByP_P(long plid, String portletId);

	/**
	 * Returns all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid);

	/**
	 * Returns a range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_O_P_First(
			long ownerId, int ownerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_O_P_Last(
			long ownerId, int ownerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByO_O_P_PrevAndNext(
			long indexEntryId, long ownerId, int ownerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 */
	public void removeByO_O_P(long ownerId, int ownerType, long plid);

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the number of matching index entries
	 */
	public int countByO_O_P(long ownerId, int ownerType, long plid);

	/**
	 * Returns all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId);

	/**
	 * Returns a range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_O_PI_First(
			long ownerId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_O_PI_First(
		long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_O_PI_Last(
			long ownerId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_O_PI_Last(
		long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByO_O_PI_PrevAndNext(
			long indexEntryId, long ownerId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public void removeByO_O_PI(long ownerId, int ownerType, String portletId);

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public int countByO_O_PI(long ownerId, int ownerType, String portletId);

	/**
	 * Returns all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId);

	/**
	 * Returns a range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end);

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_P_P_First(
			int ownerType, long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_P_P_First(
		int ownerType, long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_P_P_Last(
			int ownerType, long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_P_P_Last(
		int ownerType, long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByO_P_P_PrevAndNext(
			long indexEntryId, int ownerType, long plid, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public void removeByO_P_P(int ownerType, long plid, String portletId);

	/**
	 * Returns the number of index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public int countByO_P_P(int ownerType, long plid, String portletId);

	/**
	 * Returns all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public java.util.List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId);

	/**
	 * Returns a range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public java.util.List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end);

	/**
	 * Returns an ordered range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public java.util.List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByC_O_O_LikeP_First(
			long companyId, long ownerId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the first index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByC_O_O_LikeP_First(
		long companyId, long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the last index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByC_O_O_LikeP_Last(
			long companyId, long ownerId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the last index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByC_O_O_LikeP_Last(
		long companyId, long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry[] findByC_O_O_LikeP_PrevAndNext(
			long indexEntryId, long companyId, long ownerId, int ownerType,
			String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
				orderByComparator)
		throws NoSuchIndexEntryException;

	/**
	 * Removes all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public void removeByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId);

	/**
	 * Returns the number of index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public int countByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId);

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId);

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId,
		boolean useFinderCache);

	/**
	 * Removes the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the index entry that was removed
	 */
	public IndexEntry removeByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public int countByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId);

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public IndexEntry findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public IndexEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the index entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the index entry that was removed
	 */
	public IndexEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the number of index entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching index entries
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the index entry in the entity cache if it is enabled.
	 *
	 * @param indexEntry the index entry
	 */
	public void cacheResult(IndexEntry indexEntry);

	/**
	 * Caches the index entries in the entity cache if it is enabled.
	 *
	 * @param indexEntries the index entries
	 */
	public void cacheResult(java.util.List<IndexEntry> indexEntries);

	/**
	 * Creates a new index entry with the primary key. Does not add the index entry to the database.
	 *
	 * @param indexEntryId the primary key for the new index entry
	 * @return the new index entry
	 */
	public IndexEntry create(long indexEntryId);

	/**
	 * Removes the index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry that was removed
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry remove(long indexEntryId)
		throws NoSuchIndexEntryException;

	public IndexEntry updateImpl(IndexEntry indexEntry);

	/**
	 * Returns the index entry with the primary key or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public IndexEntry findByPrimaryKey(long indexEntryId)
		throws NoSuchIndexEntryException;

	/**
	 * Returns the index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry, or <code>null</code> if a index entry with the primary key could not be found
	 */
	public IndexEntry fetchByPrimaryKey(long indexEntryId);

	/**
	 * Returns all the index entries.
	 *
	 * @return the index entries
	 */
	public java.util.List<IndexEntry> findAll();

	/**
	 * Returns a range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of index entries
	 */
	public java.util.List<IndexEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of index entries
	 */
	public java.util.List<IndexEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of index entries
	 */
	public java.util.List<IndexEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<IndexEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the index entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of index entries.
	 *
	 * @return the number of index entries
	 */
	public int countAll();

}