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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryVersionException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the versioned entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryVersionUtil
 * @generated
 */
@ProviderType
public interface VersionedEntryVersionPersistence
	extends BasePersistence<VersionedEntryVersion> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VersionedEntryVersionUtil} to access the versioned entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId);

	/**
	 * Returns a range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the versioned entry versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion findByVersionedEntryId_First(
			long versionedEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the first versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByVersionedEntryId_First(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns the last versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion findByVersionedEntryId_Last(
			long versionedEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the last versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByVersionedEntryId_Last(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns the versioned entry versions before and after the current versioned entry version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryVersionId the primary key of the current versioned entry version
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public VersionedEntryVersion[] findByVersionedEntryId_PrevAndNext(
			long versionedEntryVersionId, long versionedEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Removes all the versioned entry versions where versionedEntryId = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 */
	public void removeByVersionedEntryId(long versionedEntryId);

	/**
	 * Returns the number of versioned entry versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the number of matching versioned entry versions
	 */
	public int countByVersionedEntryId(long versionedEntryId);

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or throws a <code>NoSuchVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion findByVersionedEntryId_Version(
			long versionedEntryId, int version)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByVersionedEntryId_Version(
		long versionedEntryId, int version);

	/**
	 * Returns the versioned entry version where versionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByVersionedEntryId_Version(
		long versionedEntryId, int version, boolean useFinderCache);

	/**
	 * Removes the versioned entry version where versionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the versioned entry version that was removed
	 */
	public VersionedEntryVersion removeByVersionedEntryId_Version(
			long versionedEntryId, int version)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the number of versioned entry versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the number of matching versioned entry versions
	 */
	public int countByVersionedEntryId_Version(
		long versionedEntryId, int version);

	/**
	 * Returns all the versioned entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId(long groupId);

	/**
	 * Returns a range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns the versioned entry versions before and after the current versioned entry version in the ordered set where groupId = &#63;.
	 *
	 * @param versionedEntryVersionId the primary key of the current versioned entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public VersionedEntryVersion[] findByGroupId_PrevAndNext(
			long versionedEntryVersionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Removes all the versioned entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of versioned entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching versioned entry versions
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version);

	/**
	 * Returns a range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end);

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the first versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion findByGroupId_Version_Last(
			long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the last versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry version, or <code>null</code> if a matching versioned entry version could not be found
	 */
	public VersionedEntryVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns the versioned entry versions before and after the current versioned entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryVersionId the primary key of the current versioned entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public VersionedEntryVersion[] findByGroupId_Version_PrevAndNext(
			long versionedEntryVersionId, long groupId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<VersionedEntryVersion> orderByComparator)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Removes all the versioned entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	public void removeByGroupId_Version(long groupId, int version);

	/**
	 * Returns the number of versioned entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching versioned entry versions
	 */
	public int countByGroupId_Version(long groupId, int version);

	/**
	 * Caches the versioned entry version in the entity cache if it is enabled.
	 *
	 * @param versionedEntryVersion the versioned entry version
	 */
	public void cacheResult(VersionedEntryVersion versionedEntryVersion);

	/**
	 * Caches the versioned entry versions in the entity cache if it is enabled.
	 *
	 * @param versionedEntryVersions the versioned entry versions
	 */
	public void cacheResult(
		java.util.List<VersionedEntryVersion> versionedEntryVersions);

	/**
	 * Creates a new versioned entry version with the primary key. Does not add the versioned entry version to the database.
	 *
	 * @param versionedEntryVersionId the primary key for the new versioned entry version
	 * @return the new versioned entry version
	 */
	public VersionedEntryVersion create(long versionedEntryVersionId);

	/**
	 * Removes the versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version that was removed
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public VersionedEntryVersion remove(long versionedEntryVersionId)
		throws NoSuchVersionedEntryVersionException;

	public VersionedEntryVersion updateImpl(
		VersionedEntryVersion versionedEntryVersion);

	/**
	 * Returns the versioned entry version with the primary key or throws a <code>NoSuchVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version
	 * @throws NoSuchVersionedEntryVersionException if a versioned entry version with the primary key could not be found
	 */
	public VersionedEntryVersion findByPrimaryKey(long versionedEntryVersionId)
		throws NoSuchVersionedEntryVersionException;

	/**
	 * Returns the versioned entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionedEntryVersionId the primary key of the versioned entry version
	 * @return the versioned entry version, or <code>null</code> if a versioned entry version with the primary key could not be found
	 */
	public VersionedEntryVersion fetchByPrimaryKey(
		long versionedEntryVersionId);

	/**
	 * Returns all the versioned entry versions.
	 *
	 * @return the versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findAll();

	/**
	 * Returns a range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @return the range of versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator);

	/**
	 * Returns an ordered range of all the versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry versions
	 * @param end the upper bound of the range of versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of versioned entry versions
	 */
	public java.util.List<VersionedEntryVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryVersion>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the versioned entry versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of versioned entry versions.
	 *
	 * @return the number of versioned entry versions
	 */
	public int countAll();

}