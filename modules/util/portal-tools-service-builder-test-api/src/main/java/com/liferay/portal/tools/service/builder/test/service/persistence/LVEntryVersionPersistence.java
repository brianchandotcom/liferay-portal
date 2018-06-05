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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersion;

/**
 * The persistence interface for the lv entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryVersionPersistenceImpl
 * @see LVEntryVersionUtil
 * @generated
 */
@ProviderType
public interface LVEntryVersionPersistence extends BasePersistence<LVEntryVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LVEntryVersionUtil} to access the lv entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the lv entry versions where lvEntryId = &#63;.
	*
	* @param lvEntryId the lv entry ID
	* @return the matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByLvEntryId(long lvEntryId);

	/**
	* Returns a range of all the lv entry versions where lvEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param lvEntryId the lv entry ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByLvEntryId(long lvEntryId,
		int start, int end);

	/**
	* Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param lvEntryId the lv entry ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByLvEntryId(long lvEntryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param lvEntryId the lv entry ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByLvEntryId(long lvEntryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	*
	* @param lvEntryId the lv entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public LVEntryVersion findByLvEntryId_First(long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	*
	* @param lvEntryId the lv entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByLvEntryId_First(long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	*
	* @param lvEntryId the lv entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public LVEntryVersion findByLvEntryId_Last(long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	*
	* @param lvEntryId the lv entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByLvEntryId_Last(long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns the lv entry versions before and after the current lv entry version in the ordered set where lvEntryId = &#63;.
	*
	* @param lvEntryVersionId the primary key of the current lv entry version
	* @param lvEntryId the lv entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public LVEntryVersion[] findByLvEntryId_PrevAndNext(long lvEntryVersionId,
		long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Removes all the lv entry versions where lvEntryId = &#63; from the database.
	*
	* @param lvEntryId the lv entry ID
	*/
	public void removeByLvEntryId(long lvEntryId);

	/**
	* Returns the number of lv entry versions where lvEntryId = &#63;.
	*
	* @param lvEntryId the lv entry ID
	* @return the number of matching lv entry versions
	*/
	public int countByLvEntryId(long lvEntryId);

	/**
	* Returns the lv entry version where lvEntryId = &#63; and version = &#63; or throws a {@link NoSuchLVEntryVersionException} if it could not be found.
	*
	* @param lvEntryId the lv entry ID
	* @param version the version
	* @return the matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public LVEntryVersion findByLvEntryId_Version(long lvEntryId, int version)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param lvEntryId the lv entry ID
	* @param version the version
	* @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByLvEntryId_Version(long lvEntryId, int version);

	/**
	* Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param lvEntryId the lv entry ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByLvEntryId_Version(long lvEntryId, int version,
		boolean retrieveFromCache);

	/**
	* Removes the lv entry version where lvEntryId = &#63; and version = &#63; from the database.
	*
	* @param lvEntryId the lv entry ID
	* @param version the version
	* @return the lv entry version that was removed
	*/
	public LVEntryVersion removeByLvEntryId_Version(long lvEntryId, int version)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the number of lv entry versions where lvEntryId = &#63; and version = &#63;.
	*
	* @param lvEntryId the lv entry ID
	* @param version the version
	* @return the number of matching lv entry versions
	*/
	public int countByLvEntryId_Version(long lvEntryId, int version);

	/**
	* Returns all the lv entry versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId(long groupId);

	/**
	* Returns a range of all the lv entry versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public LVEntryVersion findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public LVEntryVersion findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63;.
	*
	* @param lvEntryVersionId the primary key of the current lv entry version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public LVEntryVersion[] findByGroupId_PrevAndNext(long lvEntryVersionId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Removes all the lv entry versions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of lv entry versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching lv entry versions
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version);

	/**
	* Returns a range of all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end);

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entry versions
	*/
	public java.util.List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public LVEntryVersion findByGroupId_Version_First(long groupId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByGroupId_Version_First(long groupId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public LVEntryVersion findByGroupId_Version_Last(long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public LVEntryVersion fetchByGroupId_Version_Last(long groupId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param lvEntryVersionId the primary key of the current lv entry version
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public LVEntryVersion[] findByGroupId_Version_PrevAndNext(
		long lvEntryVersionId, long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException;

	/**
	* Removes all the lv entry versions where groupId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param version the version
	*/
	public void removeByGroupId_Version(long groupId, int version);

	/**
	* Returns the number of lv entry versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the number of matching lv entry versions
	*/
	public int countByGroupId_Version(long groupId, int version);

	/**
	* Caches the lv entry version in the entity cache if it is enabled.
	*
	* @param lvEntryVersion the lv entry version
	*/
	public void cacheResult(LVEntryVersion lvEntryVersion);

	/**
	* Caches the lv entry versions in the entity cache if it is enabled.
	*
	* @param lvEntryVersions the lv entry versions
	*/
	public void cacheResult(java.util.List<LVEntryVersion> lvEntryVersions);

	/**
	* Creates a new lv entry version with the primary key. Does not add the lv entry version to the database.
	*
	* @param lvEntryVersionId the primary key for the new lv entry version
	* @return the new lv entry version
	*/
	public LVEntryVersion create(long lvEntryVersionId);

	/**
	* Removes the lv entry version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntryVersionId the primary key of the lv entry version
	* @return the lv entry version that was removed
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public LVEntryVersion remove(long lvEntryVersionId)
		throws NoSuchLVEntryVersionException;

	public LVEntryVersion updateImpl(LVEntryVersion lvEntryVersion);

	/**
	* Returns the lv entry version with the primary key or throws a {@link NoSuchLVEntryVersionException} if it could not be found.
	*
	* @param lvEntryVersionId the primary key of the lv entry version
	* @return the lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public LVEntryVersion findByPrimaryKey(long lvEntryVersionId)
		throws NoSuchLVEntryVersionException;

	/**
	* Returns the lv entry version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param lvEntryVersionId the primary key of the lv entry version
	* @return the lv entry version, or <code>null</code> if a lv entry version with the primary key could not be found
	*/
	public LVEntryVersion fetchByPrimaryKey(long lvEntryVersionId);

	@Override
	public java.util.Map<java.io.Serializable, LVEntryVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the lv entry versions.
	*
	* @return the lv entry versions
	*/
	public java.util.List<LVEntryVersion> findAll();

	/**
	* Returns a range of all the lv entry versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of lv entry versions
	*/
	public java.util.List<LVEntryVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the lv entry versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of lv entry versions
	*/
	public java.util.List<LVEntryVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator);

	/**
	* Returns an ordered range of all the lv entry versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of lv entry versions
	*/
	public java.util.List<LVEntryVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the lv entry versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of lv entry versions.
	*
	* @return the number of lv entry versions
	*/
	public int countAll();
}