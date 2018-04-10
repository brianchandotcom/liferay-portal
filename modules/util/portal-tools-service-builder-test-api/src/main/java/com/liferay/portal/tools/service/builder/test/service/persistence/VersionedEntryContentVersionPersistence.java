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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContentVersion;

/**
 * The persistence interface for the versioned entry content version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryContentVersionPersistenceImpl
 * @see VersionedEntryContentVersionUtil
 * @generated
 */
@ProviderType
public interface VersionedEntryContentVersionPersistence extends BasePersistence<VersionedEntryContentVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VersionedEntryContentVersionUtil} to access the versioned entry content version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @return the matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId);

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryContentId_First(
		long versionedEntryContentId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_First(
		long versionedEntryContentId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryContentId_Last(
		long versionedEntryContentId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_Last(
		long versionedEntryContentId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryContentId the versioned entry content ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public VersionedEntryContentVersion[] findByVersionedEntryContentId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryContentId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Removes all the versioned entry content versions where versionedEntryContentId = &#63; from the database.
	*
	* @param versionedEntryContentId the versioned entry content ID
	*/
	public void removeByVersionedEntryContentId(long versionedEntryContentId);

	/**
	* Returns the number of versioned entry content versions where versionedEntryContentId = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @return the number of matching versioned entry content versions
	*/
	public int countByVersionedEntryContentId(long versionedEntryContentId);

	/**
	* Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version);

	/**
	* Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version, boolean retrieveFromCache);

	/**
	* Removes the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; from the database.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the versioned entry content version that was removed
	*/
	public VersionedEntryContentVersion removeByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the number of versioned entry content versions where versionedEntryContentId = &#63; and version = &#63;.
	*
	* @param versionedEntryContentId the versioned entry content ID
	* @param version the version
	* @return the number of matching versioned entry content versions
	*/
	public int countByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version);

	/**
	* Returns all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId);

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryId_First(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_First(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryId_Last(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_Last(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public VersionedEntryContentVersion[] findByVersionedEntryId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Removes all the versioned entry content versions where versionedEntryId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	*/
	public void removeByVersionedEntryId(long versionedEntryId);

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the number of matching versioned entry content versions
	*/
	public int countByVersionedEntryId(long versionedEntryId);

	/**
	* Returns all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @return the matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version);

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryId_Version_First(
		long versionedEntryId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_Version_First(
		long versionedEntryId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryId_Version_Last(
		long versionedEntryId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_Version_Last(
		long versionedEntryId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public VersionedEntryContentVersion[] findByVersionedEntryId_Version_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Removes all the versioned entry content versions where versionedEntryId = &#63; and version = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	*/
	public void removeByVersionedEntryId_Version(long versionedEntryId,
		int version);

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param version the version
	* @return the number of matching versioned entry content versions
	*/
	public int countByVersionedEntryId_Version(long versionedEntryId,
		int version);

	/**
	* Returns all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId);

	/**
	* Returns a range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId, int start, int end);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryId_LanguageId_First(
		long versionedEntryId, java.lang.String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_First(
		long versionedEntryId, java.lang.String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryId_LanguageId_Last(
		long versionedEntryId, java.lang.String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Last(
		long versionedEntryId, java.lang.String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public VersionedEntryContentVersion[] findByVersionedEntryId_LanguageId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		java.lang.String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Removes all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	*/
	public void removeByVersionedEntryId_LanguageId(long versionedEntryId,
		java.lang.String languageId);

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the number of matching versioned entry content versions
	*/
	public int countByVersionedEntryId_LanguageId(long versionedEntryId,
		java.lang.String languageId);

	/**
	* Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the matching versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion findByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version);

	/**
	* Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	*/
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version,
		boolean retrieveFromCache);

	/**
	* Removes the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the versioned entry content version that was removed
	*/
	public VersionedEntryContentVersion removeByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the number of versioned entry content versions where versionedEntryId = &#63; and languageId = &#63; and version = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param version the version
	* @return the number of matching versioned entry content versions
	*/
	public int countByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, java.lang.String languageId, int version);

	/**
	* Caches the versioned entry content version in the entity cache if it is enabled.
	*
	* @param versionedEntryContentVersion the versioned entry content version
	*/
	public void cacheResult(
		VersionedEntryContentVersion versionedEntryContentVersion);

	/**
	* Caches the versioned entry content versions in the entity cache if it is enabled.
	*
	* @param versionedEntryContentVersions the versioned entry content versions
	*/
	public void cacheResult(
		java.util.List<VersionedEntryContentVersion> versionedEntryContentVersions);

	/**
	* Creates a new versioned entry content version with the primary key. Does not add the versioned entry content version to the database.
	*
	* @param versionedEntryContentVersionId the primary key for the new versioned entry content version
	* @return the new versioned entry content version
	*/
	public VersionedEntryContentVersion create(
		long versionedEntryContentVersionId);

	/**
	* Removes the versioned entry content version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionedEntryContentVersionId the primary key of the versioned entry content version
	* @return the versioned entry content version that was removed
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public VersionedEntryContentVersion remove(
		long versionedEntryContentVersionId)
		throws NoSuchVersionedEntryContentVersionException;

	public VersionedEntryContentVersion updateImpl(
		VersionedEntryContentVersion versionedEntryContentVersion);

	/**
	* Returns the versioned entry content version with the primary key or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	*
	* @param versionedEntryContentVersionId the primary key of the versioned entry content version
	* @return the versioned entry content version
	* @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	*/
	public VersionedEntryContentVersion findByPrimaryKey(
		long versionedEntryContentVersionId)
		throws NoSuchVersionedEntryContentVersionException;

	/**
	* Returns the versioned entry content version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param versionedEntryContentVersionId the primary key of the versioned entry content version
	* @return the versioned entry content version, or <code>null</code> if a versioned entry content version with the primary key could not be found
	*/
	public VersionedEntryContentVersion fetchByPrimaryKey(
		long versionedEntryContentVersionId);

	@Override
	public java.util.Map<java.io.Serializable, VersionedEntryContentVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the versioned entry content versions.
	*
	* @return the versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findAll();

	/**
	* Returns a range of all the versioned entry content versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @return the range of versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the versioned entry content versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator);

	/**
	* Returns an ordered range of all the versioned entry content versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry content versions
	* @param end the upper bound of the range of versioned entry content versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of versioned entry content versions
	*/
	public java.util.List<VersionedEntryContentVersion> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the versioned entry content versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of versioned entry content versions.
	*
	* @return the number of versioned entry content versions
	*/
	public int countAll();
}