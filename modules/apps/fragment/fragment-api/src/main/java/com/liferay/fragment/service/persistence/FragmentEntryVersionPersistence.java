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

import com.liferay.fragment.exception.NoSuchEntryVersionException;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fragment entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryVersionUtil
 * @generated
 */
@ProviderType
public interface FragmentEntryVersionPersistence
	extends BasePersistence<FragmentEntryVersion>,
			CTPersistence<FragmentEntryVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryVersionUtil} to access the fragment entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId);

	/**
	 * Returns a range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByFragmentEntryId_First(
			long fragmentEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentEntryId_First(
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByFragmentEntryId_Last(
			long fragmentEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentEntryId_Last(
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByFragmentEntryId_PrevAndNext(
			long fragmentEntryVersionId, long fragmentEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where fragmentEntryId = &#63; from the database.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 */
	public void removeByFragmentEntryId(long fragmentEntryId);

	/**
	 * Returns the number of fragment entry versions where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching fragment entry versions
	 */
	public int countByFragmentEntryId(long fragmentEntryId);

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByFragmentEntryId_Version(
			long fragmentEntryId, int version)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentEntryId_Version(
		long fragmentEntryId, int version);

	/**
	 * Returns the fragment entry version where fragmentEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentEntryId_Version(
		long fragmentEntryId, int version, boolean useFinderCache);

	/**
	 * Removes the fragment entry version where fragmentEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	public FragmentEntryVersion removeByFragmentEntryId_Version(
			long fragmentEntryId, int version)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the number of fragment entry versions where fragmentEntryId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByFragmentEntryId_Version(
		long fragmentEntryId, int version);

	/**
	 * Returns all the fragment entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid(String uuid);

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByUuid_PrevAndNext(
			long fragmentEntryVersionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fragment entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entry versions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version);

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_Version_First(
			String uuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_Version_Last(
			String uuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_Version_Last(
		String uuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByUuid_Version_PrevAndNext(
			long fragmentEntryVersionId, String uuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	public void removeByUuid_Version(String uuid, int version);

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByUuid_Version(String uuid, int version);

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUUID_G_Last(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUUID_G_Last(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByUUID_G_PrevAndNext(
			long fragmentEntryVersionId, String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public void removeByUUID_G(String uuid, long groupId);

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entry versions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version);

	/**
	 * Returns the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache);

	/**
	 * Removes the fragment entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	public FragmentEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByUUID_G_Version(String uuid, long groupId, int version);

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByUuid_C_PrevAndNext(
			long fragmentEntryVersionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entry versions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version);

	/**
	 * Returns a range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByUuid_C_Version_PrevAndNext(
			long fragmentEntryVersionId, String uuid, long companyId,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version);

	/**
	 * Returns the number of fragment entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByUuid_C_Version(String uuid, long companyId, int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId(long groupId);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByGroupId_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entry versions
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByGroupId_Version_Last(
			long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByGroupId_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	public void removeByGroupId_Version(long groupId, int version);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByGroupId_Version(long groupId, int version);

	/**
	 * Returns all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId);

	/**
	 * Returns a range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByFragmentCollectionId_First(
			long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByFragmentCollectionId_Last(
			long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentCollectionId_Last(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByFragmentCollectionId_PrevAndNext(
			long fragmentEntryVersionId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where fragmentCollectionId = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 */
	public void removeByFragmentCollectionId(long fragmentCollectionId);

	/**
	 * Returns the number of fragment entry versions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entry versions
	 */
	public int countByFragmentCollectionId(long fragmentCollectionId);

	/**
	 * Returns all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion>
		findByFragmentCollectionId_Version(
			long fragmentCollectionId, int version);

	/**
	 * Returns a range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion>
		findByFragmentCollectionId_Version(
			long fragmentCollectionId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion>
		findByFragmentCollectionId_Version(
			long fragmentCollectionId, int version, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion>
		findByFragmentCollectionId_Version(
			long fragmentCollectionId, int version, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByFragmentCollectionId_Version_First(
			long fragmentCollectionId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentCollectionId_Version_First(
		long fragmentCollectionId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByFragmentCollectionId_Version_Last(
			long fragmentCollectionId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByFragmentCollectionId_Version_Last(
		long fragmentCollectionId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[]
			findByFragmentCollectionId_Version_PrevAndNext(
				long fragmentEntryVersionId, long fragmentCollectionId,
				int version,
				com.liferay.portal.kernel.util.OrderByComparator
					<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where fragmentCollectionId = &#63; and version = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 */
	public void removeByFragmentCollectionId_Version(
		long fragmentCollectionId, int version);

	/**
	 * Returns the number of fragment entry versions where fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByFragmentCollectionId_Version(
		long fragmentCollectionId, int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_First(
			long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_First(
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_Last(
			long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_Last(
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 */
	public void removeByG_FCI(long groupId, long fragmentCollectionId);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI(long groupId, long fragmentCollectionId);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_Version_First(
			long groupId, long fragmentCollectionId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_Version_First(
		long groupId, long fragmentCollectionId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_Version_Last(
			long groupId, long fragmentCollectionId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_Version_Last(
		long groupId, long fragmentCollectionId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 */
	public void removeByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_Version(
		long groupId, long fragmentCollectionId, int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FEK_First(
			long groupId, String fragmentEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FEK_First(
		long groupId, String fragmentEntryKey,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FEK_Last(
			long groupId, String fragmentEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FEK_Last(
		long groupId, String fragmentEntryKey,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FEK_PrevAndNext(
			long fragmentEntryVersionId, long groupId, String fragmentEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 */
	public void removeByG_FEK(long groupId, String fragmentEntryKey);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FEK(long groupId, String fragmentEntryKey);

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FEK_Version(
			long groupId, String fragmentEntryKey, int version)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version);

	/**
	 * Returns the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version,
		boolean useFinderCache);

	/**
	 * Removes the fragment entry version where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the fragment entry version that was removed
	 */
	public FragmentEntryVersion removeByG_FEK_Version(
			long groupId, String fragmentEntryKey, int version)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentEntryKey = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FEK_Version(
		long groupId, String fragmentEntryKey, int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_First(
			long groupId, long fragmentCollectionId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_First(
		long groupId, long fragmentCollectionId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_Last(
			long groupId, long fragmentCollectionId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_Last(
		long groupId, long fragmentCollectionId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_LikeN_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 */
	public void removeByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_Version_First(
			long groupId, long fragmentCollectionId, String name, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_Version_First(
		long groupId, long fragmentCollectionId, String name, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_Version_Last(
			long groupId, long fragmentCollectionId, String name, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_Version_Last(
		long groupId, long fragmentCollectionId, String name, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_LikeN_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 */
	public void removeByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_LikeN_Version(
		long groupId, long fragmentCollectionId, String name, int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_First(
			long groupId, long fragmentCollectionId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_First(
		long groupId, long fragmentCollectionId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_Last(
			long groupId, long fragmentCollectionId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_Last(
		long groupId, long fragmentCollectionId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_T_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 */
	public void removeByG_FCI_T(
		long groupId, long fragmentCollectionId, int type);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_T(
		long groupId, long fragmentCollectionId, int type);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_Version_First(
			long groupId, long fragmentCollectionId, int type, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_Version_First(
		long groupId, long fragmentCollectionId, int type, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_Version_Last(
			long groupId, long fragmentCollectionId, int type, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_Version_Last(
		long groupId, long fragmentCollectionId, int type, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_T_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 */
	public void removeByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_T_Version(
		long groupId, long fragmentCollectionId, int type, int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_S_First(
			long groupId, long fragmentCollectionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_S_First(
		long groupId, long fragmentCollectionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_S_Last(
			long groupId, long fragmentCollectionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_S_Last(
		long groupId, long fragmentCollectionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_S_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 */
	public void removeByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_S_Version_First(
			long groupId, long fragmentCollectionId, int status, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_S_Version_First(
		long groupId, long fragmentCollectionId, int status, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_S_Version_Last(
			long groupId, long fragmentCollectionId, int status, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_S_Version_Last(
		long groupId, long fragmentCollectionId, int status, int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_S_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int status, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 */
	public void removeByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_S_Version(
		long groupId, long fragmentCollectionId, int status, int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_S_First(
			long groupId, long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_First(
		long groupId, long fragmentCollectionId, String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_S_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_LikeN_S_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 */
	public void removeByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_S_Version_First(
			long groupId, long fragmentCollectionId, String name, int status,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_Version_First(
		long groupId, long fragmentCollectionId, String name, int status,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_LikeN_S_Version_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_LikeN_S_Version_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_LikeN_S_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, String name, int status, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 */
	public void removeByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and name = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_LikeN_S_Version(
		long groupId, long fragmentCollectionId, String name, int status,
		int version);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_S_First(
			long groupId, long fragmentCollectionId, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_S_First(
		long groupId, long fragmentCollectionId, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_S_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_S_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_T_S_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 */
	public void removeByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status);

	/**
	 * Returns all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @return the matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version);

	/**
	 * Returns a range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_S_Version_First(
			long groupId, long fragmentCollectionId, int type, int status,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the first fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_S_Version_First(
		long groupId, long fragmentCollectionId, int type, int status,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version
	 * @throws NoSuchEntryVersionException if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion findByG_FCI_T_S_Version_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the last fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry version, or <code>null</code> if a matching fragment entry version could not be found
	 */
	public FragmentEntryVersion fetchByG_FCI_T_S_Version_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns the fragment entry versions before and after the current fragment entry version in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param fragmentEntryVersionId the primary key of the current fragment entry version
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion[] findByG_FCI_T_S_Version_PrevAndNext(
			long fragmentEntryVersionId, long groupId,
			long fragmentCollectionId, int type, int status, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException;

	/**
	 * Removes all the fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 */
	public void removeByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version);

	/**
	 * Returns the number of fragment entry versions where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param version the version
	 * @return the number of matching fragment entry versions
	 */
	public int countByG_FCI_T_S_Version(
		long groupId, long fragmentCollectionId, int type, int status,
		int version);

	/**
	 * Caches the fragment entry version in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryVersion the fragment entry version
	 */
	public void cacheResult(FragmentEntryVersion fragmentEntryVersion);

	/**
	 * Caches the fragment entry versions in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryVersions the fragment entry versions
	 */
	public void cacheResult(
		java.util.List<FragmentEntryVersion> fragmentEntryVersions);

	/**
	 * Creates a new fragment entry version with the primary key. Does not add the fragment entry version to the database.
	 *
	 * @param fragmentEntryVersionId the primary key for the new fragment entry version
	 * @return the new fragment entry version
	 */
	public FragmentEntryVersion create(long fragmentEntryVersionId);

	/**
	 * Removes the fragment entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version that was removed
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion remove(long fragmentEntryVersionId)
		throws NoSuchEntryVersionException;

	public FragmentEntryVersion updateImpl(
		FragmentEntryVersion fragmentEntryVersion);

	/**
	 * Returns the fragment entry version with the primary key or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version
	 * @throws NoSuchEntryVersionException if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion findByPrimaryKey(long fragmentEntryVersionId)
		throws NoSuchEntryVersionException;

	/**
	 * Returns the fragment entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryVersionId the primary key of the fragment entry version
	 * @return the fragment entry version, or <code>null</code> if a fragment entry version with the primary key could not be found
	 */
	public FragmentEntryVersion fetchByPrimaryKey(long fragmentEntryVersionId);

	/**
	 * Returns all the fragment entry versions.
	 *
	 * @return the fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findAll();

	/**
	 * Returns a range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @return the range of fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry versions
	 * @param end the upper bound of the range of fragment entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry versions
	 */
	public java.util.List<FragmentEntryVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fragment entry versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fragment entry versions.
	 *
	 * @return the number of fragment entry versions
	 */
	public int countAll();

}