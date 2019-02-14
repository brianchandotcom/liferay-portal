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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.model.RepositoryEntry;

/**
 * The persistence interface for the repository entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryUtil
 * @generated
 */
@ProviderType
public interface RepositoryEntryPersistence extends BasePersistence<RepositoryEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RepositoryEntryUtil} to access the repository entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the repository entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid(String uuid);

	/**
	* Returns a range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63;.
	*
	* @param repositoryEntryId the primary key of the current repository entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public RepositoryEntry[] findByUuid_PrevAndNext(long repositoryEntryId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Removes all the repository entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of repository entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching repository entries
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the repository entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the repository entry that was removed
	*/
	public RepositoryEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the number of repository entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching repository entries
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns an ordered range of all the repository entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the first repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the last repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns the repository entries before and after the current repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param repositoryEntryId the primary key of the current repository entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public RepositoryEntry[] findByUuid_C_PrevAndNext(long repositoryEntryId,
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Removes all the repository entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of repository entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching repository entries
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the repository entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByRepositoryId(long repositoryId);

	/**
	* Returns a range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end);

	/**
	* Returns an ordered range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns an ordered range of all the repository entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching repository entries
	*/
	public java.util.List<RepositoryEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByRepositoryId_First(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the first repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByRepositoryId_First(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns the last repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByRepositoryId_Last(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the last repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByRepositoryId_Last(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns the repository entries before and after the current repository entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryEntryId the primary key of the current repository entry
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public RepositoryEntry[] findByRepositoryId_PrevAndNext(
		long repositoryEntryId, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator)
		throws NoSuchRepositoryEntryException;

	/**
	* Removes all the repository entries where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	*/
	public void removeByRepositoryId(long repositoryId);

	/**
	* Returns the number of repository entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching repository entries
	*/
	public int countByRepositoryId(long repositoryId);

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the matching repository entry
	* @throws NoSuchRepositoryEntryException if a matching repository entry could not be found
	*/
	public RepositoryEntry findByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByR_M(long repositoryId, String mappedId);

	/**
	* Returns the repository entry where repositoryId = &#63; and mappedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	*/
	public RepositoryEntry fetchByR_M(long repositoryId, String mappedId,
		boolean retrieveFromCache);

	/**
	* Removes the repository entry where repositoryId = &#63; and mappedId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the repository entry that was removed
	*/
	public RepositoryEntry removeByR_M(long repositoryId, String mappedId)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the number of repository entries where repositoryId = &#63; and mappedId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param mappedId the mapped ID
	* @return the number of matching repository entries
	*/
	public int countByR_M(long repositoryId, String mappedId);

	/**
	* Caches the repository entry in the entity cache if it is enabled.
	*
	* @param repositoryEntry the repository entry
	*/
	public void cacheResult(RepositoryEntry repositoryEntry);

	/**
	* Caches the repository entries in the entity cache if it is enabled.
	*
	* @param repositoryEntries the repository entries
	*/
	public void cacheResult(java.util.List<RepositoryEntry> repositoryEntries);

	/**
	* Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	*
	* @param repositoryEntryId the primary key for the new repository entry
	* @return the new repository entry
	*/
	public RepositoryEntry create(long repositoryEntryId);

	/**
	* Removes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry that was removed
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public RepositoryEntry remove(long repositoryEntryId)
		throws NoSuchRepositoryEntryException;

	public RepositoryEntry updateImpl(RepositoryEntry repositoryEntry);

	/**
	* Returns the repository entry with the primary key or throws a <code>NoSuchRepositoryEntryException</code> if it could not be found.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry
	* @throws NoSuchRepositoryEntryException if a repository entry with the primary key could not be found
	*/
	public RepositoryEntry findByPrimaryKey(long repositoryEntryId)
		throws NoSuchRepositoryEntryException;

	/**
	* Returns the repository entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry, or <code>null</code> if a repository entry with the primary key could not be found
	*/
	public RepositoryEntry fetchByPrimaryKey(long repositoryEntryId);

	/**
	* Returns all the repository entries.
	*
	* @return the repository entries
	*/
	public java.util.List<RepositoryEntry> findAll();

	/**
	* Returns a range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of repository entries
	*/
	public java.util.List<RepositoryEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of repository entries
	*/
	public java.util.List<RepositoryEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator);

	/**
	* Returns an ordered range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of repository entries
	*/
	public java.util.List<RepositoryEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the repository entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of repository entries.
	*
	* @return the number of repository entries
	*/
	public int countAll();
}