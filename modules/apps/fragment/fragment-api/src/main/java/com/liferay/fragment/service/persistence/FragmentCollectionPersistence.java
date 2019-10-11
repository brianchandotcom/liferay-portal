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

package com.liferay.fragment.service.persistence;

import com.liferay.fragment.exception.NoSuchCollectionException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fragment collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionUtil
 * @generated
 */
@ProviderType
public interface FragmentCollectionPersistence
	extends BasePersistence<FragmentCollection> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentCollectionUtil} to access the fragment collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fragment collections where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid(String uuid);

	/**
	 * Returns a range of all the fragment collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	public FragmentCollection[] findByUuid_PrevAndNext(
			long fragmentCollectionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Removes all the fragment collections where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fragment collections where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment collections
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the fragment collection where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByUUID_G(String uuid, long groupId)
		throws NoSuchCollectionException;

	/**
	 * Returns the fragment collection where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the fragment collection where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the fragment collection where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the fragment collection that was removed
	 */
	public FragmentCollection removeByUUID_G(String uuid, long groupId)
		throws NoSuchCollectionException;

	/**
	 * Returns the number of fragment collections where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment collections
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	public FragmentCollection[] findByUuid_C_PrevAndNext(
			long fragmentCollectionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Removes all the fragment collections where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fragment collections where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment collections
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(long groupId);

	/**
	 * Returns a range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	public FragmentCollection[] findByGroupId_PrevAndNext(
			long fragmentCollectionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns all the fragment collections where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the fragment collections where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fragment collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of fragment collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment collections
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of fragment collections where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching fragment collections
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByG_FCK(
			long groupId, String fragmentCollectionKey)
		throws NoSuchCollectionException;

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByG_FCK(
		long groupId, String fragmentCollectionKey);

	/**
	 * Returns the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByG_FCK(
		long groupId, String fragmentCollectionKey, boolean useFinderCache);

	/**
	 * Removes the fragment collection where groupId = &#63; and fragmentCollectionKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the fragment collection that was removed
	 */
	public FragmentCollection removeByG_FCK(
			long groupId, String fragmentCollectionKey)
		throws NoSuchCollectionException;

	/**
	 * Returns the number of fragment collections where groupId = &#63; and fragmentCollectionKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionKey the fragment collection key
	 * @return the number of matching fragment collections
	 */
	public int countByG_FCK(long groupId, String fragmentCollectionKey);

	/**
	 * Returns all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long groupId, String name);

	/**
	 * Returns a range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long groupId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByG_LikeN_First(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByG_LikeN_First(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection
	 * @throws NoSuchCollectionException if a matching fragment collection could not be found
	 */
	public FragmentCollection findByG_LikeN_Last(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	 */
	public FragmentCollection fetchByG_LikeN_Last(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns the fragment collections before and after the current fragment collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param fragmentCollectionId the primary key of the current fragment collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	public FragmentCollection[] findByG_LikeN_PrevAndNext(
			long fragmentCollectionId, long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
				orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns all the fragment collections where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long[] groupIds, String name);

	/**
	 * Returns a range of all the fragment collections where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long[] groupIds, String name, int start, int end);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment collections where groupId = &#63; and name LIKE &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment collections
	 */
	public java.util.List<FragmentCollection> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fragment collections where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	public void removeByG_LikeN(long groupId, String name);

	/**
	 * Returns the number of fragment collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching fragment collections
	 */
	public int countByG_LikeN(long groupId, String name);

	/**
	 * Returns the number of fragment collections where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the number of matching fragment collections
	 */
	public int countByG_LikeN(long[] groupIds, String name);

	/**
	 * Caches the fragment collection in the entity cache if it is enabled.
	 *
	 * @param fragmentCollection the fragment collection
	 */
	public void cacheResult(FragmentCollection fragmentCollection);

	/**
	 * Caches the fragment collections in the entity cache if it is enabled.
	 *
	 * @param fragmentCollections the fragment collections
	 */
	public void cacheResult(
		java.util.List<FragmentCollection> fragmentCollections);

	/**
	 * Creates a new fragment collection with the primary key. Does not add the fragment collection to the database.
	 *
	 * @param fragmentCollectionId the primary key for the new fragment collection
	 * @return the new fragment collection
	 */
	public FragmentCollection create(long fragmentCollectionId);

	/**
	 * Removes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection that was removed
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	public FragmentCollection remove(long fragmentCollectionId)
		throws NoSuchCollectionException;

	public FragmentCollection updateImpl(FragmentCollection fragmentCollection);

	/**
	 * Returns the fragment collection with the primary key or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection
	 * @throws NoSuchCollectionException if a fragment collection with the primary key could not be found
	 */
	public FragmentCollection findByPrimaryKey(long fragmentCollectionId)
		throws NoSuchCollectionException;

	/**
	 * Returns the fragment collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentCollectionId the primary key of the fragment collection
	 * @return the fragment collection, or <code>null</code> if a fragment collection with the primary key could not be found
	 */
	public FragmentCollection fetchByPrimaryKey(long fragmentCollectionId);

	/**
	 * Returns all the fragment collections.
	 *
	 * @return the fragment collections
	 */
	public java.util.List<FragmentCollection> findAll();

	/**
	 * Returns a range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @return the range of fragment collections
	 */
	public java.util.List<FragmentCollection> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment collections
	 */
	public java.util.List<FragmentCollection> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment collections
	 * @param end the upper bound of the range of fragment collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment collections
	 */
	public java.util.List<FragmentCollection> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fragment collections from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fragment collections.
	 *
	 * @return the number of fragment collections
	 */
	public int countAll();

}