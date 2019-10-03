/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.content.repository.service.persistence;

import com.liferay.content.repository.exception.NoSuchEntryException;
import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the content repository entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ContentRepositoryEntryUtil
 * @generated
 */
@ProviderType
public interface ContentRepositoryEntryPersistence
	extends BasePersistence<ContentRepositoryEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContentRepositoryEntryUtil} to access the content repository entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the content repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator);

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator);

	/**
	 * Returns the content repository entries before and after the current content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param contentRepositoryEntryId the primary key of the current content repository entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public ContentRepositoryEntry[] findByUuid_PrevAndNext(
			long contentRepositoryEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the content repository entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of content repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching content repository entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the content repository entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the content repository entry that was removed
	 */
	public ContentRepositoryEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of content repository entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching content repository entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator);

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public ContentRepositoryEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator);

	/**
	 * Returns the content repository entries before and after the current content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param contentRepositoryEntryId the primary key of the current content repository entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public ContentRepositoryEntry[] findByUuid_C_PrevAndNext(
			long contentRepositoryEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the content repository entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching content repository entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the content repository entry in the entity cache if it is enabled.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 */
	public void cacheResult(ContentRepositoryEntry contentRepositoryEntry);

	/**
	 * Caches the content repository entries in the entity cache if it is enabled.
	 *
	 * @param contentRepositoryEntries the content repository entries
	 */
	public void cacheResult(
		java.util.List<ContentRepositoryEntry> contentRepositoryEntries);

	/**
	 * Creates a new content repository entry with the primary key. Does not add the content repository entry to the database.
	 *
	 * @param contentRepositoryEntryId the primary key for the new content repository entry
	 * @return the new content repository entry
	 */
	public ContentRepositoryEntry create(long contentRepositoryEntryId);

	/**
	 * Removes the content repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry that was removed
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public ContentRepositoryEntry remove(long contentRepositoryEntryId)
		throws NoSuchEntryException;

	public ContentRepositoryEntry updateImpl(
		ContentRepositoryEntry contentRepositoryEntry);

	/**
	 * Returns the content repository entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	public ContentRepositoryEntry findByPrimaryKey(
			long contentRepositoryEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the content repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry, or <code>null</code> if a content repository entry with the primary key could not be found
	 */
	public ContentRepositoryEntry fetchByPrimaryKey(
		long contentRepositoryEntryId);

	/**
	 * Returns all the content repository entries.
	 *
	 * @return the content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findAll();

	/**
	 * Returns a range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of content repository entries
	 */
	public java.util.List<ContentRepositoryEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContentRepositoryEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the content repository entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of content repository entries.
	 *
	 * @return the number of content repository entries
	 */
	public int countAll();

}