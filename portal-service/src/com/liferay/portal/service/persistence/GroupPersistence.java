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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.exception.NoSuchGroupException;
import com.liferay.portal.model.Group;

/**
 * The persistence interface for the group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.GroupPersistenceImpl
 * @see GroupUtil
 * @generated
 */
@ProviderType
public interface GroupPersistence extends BasePersistence<Group> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link GroupUtil} to access the group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching groups
	*/
	public java.util.List<Group> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByUuid(java.lang.String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where uuid = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByUuid_PrevAndNext(long groupId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching groups
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the group where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchGroupException;

	/**
	* Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the group where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the group that was removed
	*/
	public Group removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchGroupException;

	/**
	* Returns the number of groups where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching groups
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching groups
	*/
	public java.util.List<Group> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByUuid_C_PrevAndNext(long groupId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching groups
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching groups
	*/
	public java.util.List<Group> findByCompanyId(long companyId);

	/**
	* Returns a range of all the groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByCompanyId(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByCompanyId_PrevAndNext(long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching groups
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the group where liveGroupId = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param liveGroupId the live group ID
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException;

	/**
	* Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param liveGroupId the live group ID
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByLiveGroupId(long liveGroupId);

	/**
	* Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param liveGroupId the live group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByLiveGroupId(long liveGroupId, boolean retrieveFromCache);

	/**
	* Removes the group where liveGroupId = &#63; from the database.
	*
	* @param liveGroupId the live group ID
	* @return the group that was removed
	*/
	public Group removeByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException;

	/**
	* Returns the number of groups where liveGroupId = &#63;.
	*
	* @param liveGroupId the live group ID
	* @return the number of matching groups
	*/
	public int countByLiveGroupId(long liveGroupId);

	/**
	* Returns all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching groups
	*/
	public java.util.List<Group> findByC_C(long companyId, long classNameId);

	/**
	* Returns a range of all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByC_C(long companyId, long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_C(long companyId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_C(long companyId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByC_C_PrevAndNext(long groupId, long companyId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public void removeByC_C(long companyId, long classNameId);

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching groups
	*/
	public int countByC_C(long companyId, long classNameId);

	/**
	* Returns all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the matching groups
	*/
	public java.util.List<Group> findByC_P(long companyId, long parentGroupId);

	/**
	* Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByC_P(long companyId, long parentGroupId,
		int start, int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_P(long companyId, long parentGroupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_P(long companyId, long parentGroupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_P_First(long companyId, long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_P_First(long companyId, long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_P_Last(long companyId, long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_P_Last(long companyId, long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByC_P_PrevAndNext(long groupId, long companyId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; and parentGroupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	*/
	public void removeByC_P(long companyId, long parentGroupId);

	/**
	* Returns the number of groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the number of matching groups
	*/
	public int countByC_P(long companyId, long parentGroupId);

	/**
	* Returns the group where companyId = &#63; and groupKey = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_GK(long companyId, java.lang.String groupKey)
		throws NoSuchGroupException;

	/**
	* Returns the group where companyId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_GK(long companyId, java.lang.String groupKey);

	/**
	* Returns the group where companyId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_GK(long companyId, java.lang.String groupKey,
		boolean retrieveFromCache);

	/**
	* Removes the group where companyId = &#63; and groupKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the group that was removed
	*/
	public Group removeByC_GK(long companyId, java.lang.String groupKey)
		throws NoSuchGroupException;

	/**
	* Returns the number of groups where companyId = &#63; and groupKey = &#63;.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the number of matching groups
	*/
	public int countByC_GK(long companyId, java.lang.String groupKey);

	/**
	* Returns the group where companyId = &#63; and friendlyURL = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_F(long companyId, java.lang.String friendlyURL)
		throws NoSuchGroupException;

	/**
	* Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_F(long companyId, java.lang.String friendlyURL);

	/**
	* Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_F(long companyId, java.lang.String friendlyURL,
		boolean retrieveFromCache);

	/**
	* Removes the group where companyId = &#63; and friendlyURL = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the group that was removed
	*/
	public Group removeByC_F(long companyId, java.lang.String friendlyURL)
		throws NoSuchGroupException;

	/**
	* Returns the number of groups where companyId = &#63; and friendlyURL = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the number of matching groups
	*/
	public int countByC_F(long companyId, java.lang.String friendlyURL);

	/**
	* Returns all the groups where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @return the matching groups
	*/
	public java.util.List<Group> findByC_S(long companyId, boolean site);

	/**
	* Returns a range of all the groups where companyId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByC_S(long companyId, boolean site,
		int start, int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_S(long companyId, boolean site,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_S(long companyId, boolean site,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_S_First(long companyId, boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_S_First(long companyId, boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_S_Last(long companyId, boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_S_Last(long companyId, boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByC_S_PrevAndNext(long groupId, long companyId,
		boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; and site = &#63; from the database.
	*
	* @param companyId the company ID
	* @param site the site
	*/
	public void removeByC_S(long companyId, boolean site);

	/**
	* Returns the number of groups where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @return the number of matching groups
	*/
	public int countByC_S(long companyId, boolean site);

	/**
	* Returns all the groups where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the matching groups
	*/
	public java.util.List<Group> findByC_A(long companyId, boolean active);

	/**
	* Returns a range of all the groups where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByC_A(long companyId, boolean active,
		int start, int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_A(long companyId, boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_A(long companyId, boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_A_First(long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_A_First(long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_A_Last(long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_A_Last(long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByC_A_PrevAndNext(long groupId, long companyId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; and active = &#63; from the database.
	*
	* @param companyId the company ID
	* @param active the active
	*/
	public void removeByC_A(long companyId, boolean active);

	/**
	* Returns the number of groups where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the number of matching groups
	*/
	public int countByC_A(long companyId, boolean active);

	/**
	* Returns all the groups where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @return the matching groups
	*/
	public java.util.List<Group> findByT_A(int type, boolean active);

	/**
	* Returns a range of all the groups where type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByT_A(int type, boolean active, int start,
		int end);

	/**
	* Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByT_A(int type, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByT_A(int type, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByT_A_First(int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByT_A_First(int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByT_A_Last(int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByT_A_Last(int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByT_A_PrevAndNext(long groupId, int type,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where type = &#63; and active = &#63; from the database.
	*
	* @param type the type
	* @param active the active
	*/
	public void removeByT_A(int type, boolean active);

	/**
	* Returns the number of groups where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @return the number of matching groups
	*/
	public int countByT_A(int type, boolean active);

	/**
	* Returns all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the matching groups
	*/
	public java.util.List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId);

	/**
	* Returns a range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end);

	/**
	* Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByG_C_P_First(long groupId, long companyId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByG_C_P_First(long groupId, long companyId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByG_C_P_Last(long groupId, long companyId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByG_C_P_Last(long groupId, long companyId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Removes all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	*/
	public void removeByG_C_P(long groupId, long companyId, long parentGroupId);

	/**
	* Returns the number of groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the number of matching groups
	*/
	public int countByG_C_P(long groupId, long companyId, long parentGroupId);

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException;

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_C(long companyId, long classNameId, long classPK);

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_C(long companyId, long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the group that was removed
	*/
	public Group removeByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException;

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching groups
	*/
	public int countByC_C_C(long companyId, long classNameId, long classPK);

	/**
	* Returns all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @return the matching groups
	*/
	public java.util.List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId);

	/**
	* Returns a range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_C_P_First(long companyId, long classNameId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_P_First(long companyId, long classNameId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_C_P_Last(long companyId, long classNameId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_P_Last(long companyId, long classNameId,
		long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByC_C_P_PrevAndNext(long groupId, long companyId,
		long classNameId, long parentGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	*/
	public void removeByC_C_P(long companyId, long classNameId,
		long parentGroupId);

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @return the number of matching groups
	*/
	public int countByC_C_P(long companyId, long classNameId, long parentGroupId);

	/**
	* Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @return the matching groups
	*/
	public java.util.List<Group> findByC_P_S(long companyId,
		long parentGroupId, boolean site);

	/**
	* Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByC_P_S(long companyId,
		long parentGroupId, boolean site, int start, int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_P_S(long companyId,
		long parentGroupId, boolean site, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_P_S(long companyId,
		long parentGroupId, boolean site, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_P_S_First(long companyId, long parentGroupId,
		boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_P_S_First(long companyId, long parentGroupId,
		boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_P_S_Last(long companyId, long parentGroupId,
		boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_P_S_Last(long companyId, long parentGroupId,
		boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByC_P_S_PrevAndNext(long groupId, long companyId,
		long parentGroupId, boolean site,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	*/
	public void removeByC_P_S(long companyId, long parentGroupId, boolean site);

	/**
	* Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @return the number of matching groups
	*/
	public int countByC_P_S(long companyId, long parentGroupId, boolean site);

	/**
	* Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey) throws NoSuchGroupException;

	/**
	* Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey);

	/**
	* Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey, boolean retrieveFromCache);

	/**
	* Removes the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the group that was removed
	*/
	public Group removeByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey) throws NoSuchGroupException;

	/**
	* Returns the number of groups where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63;.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the number of matching groups
	*/
	public int countByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey);

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey)
		throws NoSuchGroupException;

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey);

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey, boolean retrieveFromCache);

	/**
	* Removes the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the group that was removed
	*/
	public Group removeByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey)
		throws NoSuchGroupException;

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the number of matching groups
	*/
	public int countByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey);

	/**
	* Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @return the matching groups
	*/
	public java.util.List<Group> findByC_P_S_I(long companyId,
		long parentGroupId, boolean site, boolean inheritContent);

	/**
	* Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public java.util.List<Group> findByC_P_S_I(long companyId,
		long parentGroupId, boolean site, boolean inheritContent, int start,
		int end);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_P_S_I(long companyId,
		long parentGroupId, boolean site, boolean inheritContent, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public java.util.List<Group> findByC_P_S_I(long companyId,
		long parentGroupId, boolean site, boolean inheritContent, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_P_S_I_First(long companyId, long parentGroupId,
		boolean site, boolean inheritContent,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_P_S_I_First(long companyId, long parentGroupId,
		boolean site, boolean inheritContent,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public Group findByC_P_S_I_Last(long companyId, long parentGroupId,
		boolean site, boolean inheritContent,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public Group fetchByC_P_S_I_Last(long companyId, long parentGroupId,
		boolean site, boolean inheritContent,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group[] findByC_P_S_I_PrevAndNext(long groupId, long companyId,
		long parentGroupId, boolean site, boolean inheritContent,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator)
		throws NoSuchGroupException;

	/**
	* Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	*/
	public void removeByC_P_S_I(long companyId, long parentGroupId,
		boolean site, boolean inheritContent);

	/**
	* Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @return the number of matching groups
	*/
	public int countByC_P_S_I(long companyId, long parentGroupId, boolean site,
		boolean inheritContent);

	/**
	* Caches the group in the entity cache if it is enabled.
	*
	* @param group the group
	*/
	public void cacheResult(Group group);

	/**
	* Caches the groups in the entity cache if it is enabled.
	*
	* @param groups the groups
	*/
	public void cacheResult(java.util.List<Group> groups);

	/**
	* Creates a new group with the primary key. Does not add the group to the database.
	*
	* @param groupId the primary key for the new group
	* @return the new group
	*/
	public Group create(long groupId);

	/**
	* Removes the group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupId the primary key of the group
	* @return the group that was removed
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group remove(long groupId) throws NoSuchGroupException;

	public Group updateImpl(Group group);

	/**
	* Returns the group with the primary key or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param groupId the primary key of the group
	* @return the group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public Group findByPrimaryKey(long groupId) throws NoSuchGroupException;

	/**
	* Returns the group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param groupId the primary key of the group
	* @return the group, or <code>null</code> if a group with the primary key could not be found
	*/
	public Group fetchByPrimaryKey(long groupId);

	@Override
	public java.util.Map<java.io.Serializable, Group> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the groups.
	*
	* @return the groups
	*/
	public java.util.List<Group> findAll();

	/**
	* Returns a range of all the groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of groups
	*/
	public java.util.List<Group> findAll(int start, int end);

	/**
	* Returns an ordered range of all the groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups
	*/
	public java.util.List<Group> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator);

	/**
	* Returns an ordered range of all the groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of groups
	*/
	public java.util.List<Group> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the groups from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of groups.
	*
	* @return the number of groups
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of organizations associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of organizations associated with the group
	*/
	public long[] getOrganizationPrimaryKeys(long pk);

	/**
	* Returns all the organizations associated with the group.
	*
	* @param pk the primary key of the group
	* @return the organizations associated with the group
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk);

	/**
	* Returns a range of all the organizations associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of organizations associated with the group
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the organizations associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of organizations associated with the group
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Organization> orderByComparator);

	/**
	* Returns the number of organizations associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of organizations associated with the group
	*/
	public int getOrganizationsSize(long pk);

	/**
	* Returns <code>true</code> if the organization is associated with the group.
	*
	* @param pk the primary key of the group
	* @param organizationPK the primary key of the organization
	* @return <code>true</code> if the organization is associated with the group; <code>false</code> otherwise
	*/
	public boolean containsOrganization(long pk, long organizationPK);

	/**
	* Returns <code>true</code> if the group has any organizations associated with it.
	*
	* @param pk the primary key of the group to check for associations with organizations
	* @return <code>true</code> if the group has any organizations associated with it; <code>false</code> otherwise
	*/
	public boolean containsOrganizations(long pk);

	/**
	* Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPK the primary key of the organization
	*/
	public void addOrganization(long pk, long organizationPK);

	/**
	* Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organization the organization
	*/
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization);

	/**
	* Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPKs the primary keys of the organizations
	*/
	public void addOrganizations(long pk, long[] organizationPKs);

	/**
	* Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizations the organizations
	*/
	public void addOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations);

	/**
	* Clears all associations between the group and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated organizations from
	*/
	public void clearOrganizations(long pk);

	/**
	* Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPK the primary key of the organization
	*/
	public void removeOrganization(long pk, long organizationPK);

	/**
	* Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organization the organization
	*/
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization);

	/**
	* Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPKs the primary keys of the organizations
	*/
	public void removeOrganizations(long pk, long[] organizationPKs);

	/**
	* Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizations the organizations
	*/
	public void removeOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations);

	/**
	* Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPKs the primary keys of the organizations to be associated with the group
	*/
	public void setOrganizations(long pk, long[] organizationPKs);

	/**
	* Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizations the organizations to be associated with the group
	*/
	public void setOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations);

	/**
	* Returns the primaryKeys of roles associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of roles associated with the group
	*/
	public long[] getRolePrimaryKeys(long pk);

	/**
	* Returns all the roles associated with the group.
	*
	* @param pk the primary key of the group
	* @return the roles associated with the group
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk);

	/**
	* Returns a range of all the roles associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of roles associated with the group
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end);

	/**
	* Returns an ordered range of all the roles associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of roles associated with the group
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Role> orderByComparator);

	/**
	* Returns the number of roles associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of roles associated with the group
	*/
	public int getRolesSize(long pk);

	/**
	* Returns <code>true</code> if the role is associated with the group.
	*
	* @param pk the primary key of the group
	* @param rolePK the primary key of the role
	* @return <code>true</code> if the role is associated with the group; <code>false</code> otherwise
	*/
	public boolean containsRole(long pk, long rolePK);

	/**
	* Returns <code>true</code> if the group has any roles associated with it.
	*
	* @param pk the primary key of the group to check for associations with roles
	* @return <code>true</code> if the group has any roles associated with it; <code>false</code> otherwise
	*/
	public boolean containsRoles(long pk);

	/**
	* Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePK the primary key of the role
	*/
	public void addRole(long pk, long rolePK);

	/**
	* Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param role the role
	*/
	public void addRole(long pk, com.liferay.portal.model.Role role);

	/**
	* Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePKs the primary keys of the roles
	*/
	public void addRoles(long pk, long[] rolePKs);

	/**
	* Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param roles the roles
	*/
	public void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles);

	/**
	* Clears all associations between the group and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated roles from
	*/
	public void clearRoles(long pk);

	/**
	* Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePK the primary key of the role
	*/
	public void removeRole(long pk, long rolePK);

	/**
	* Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param role the role
	*/
	public void removeRole(long pk, com.liferay.portal.model.Role role);

	/**
	* Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePKs the primary keys of the roles
	*/
	public void removeRoles(long pk, long[] rolePKs);

	/**
	* Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param roles the roles
	*/
	public void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles);

	/**
	* Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePKs the primary keys of the roles to be associated with the group
	*/
	public void setRoles(long pk, long[] rolePKs);

	/**
	* Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param roles the roles to be associated with the group
	*/
	public void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles);

	/**
	* Returns the primaryKeys of user groups associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of user groups associated with the group
	*/
	public long[] getUserGroupPrimaryKeys(long pk);

	/**
	* Returns all the user groups associated with the group.
	*
	* @param pk the primary key of the group
	* @return the user groups associated with the group
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk);

	/**
	* Returns a range of all the user groups associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of user groups associated with the group
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the user groups associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups associated with the group
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> orderByComparator);

	/**
	* Returns the number of user groups associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of user groups associated with the group
	*/
	public int getUserGroupsSize(long pk);

	/**
	* Returns <code>true</code> if the user group is associated with the group.
	*
	* @param pk the primary key of the group
	* @param userGroupPK the primary key of the user group
	* @return <code>true</code> if the user group is associated with the group; <code>false</code> otherwise
	*/
	public boolean containsUserGroup(long pk, long userGroupPK);

	/**
	* Returns <code>true</code> if the group has any user groups associated with it.
	*
	* @param pk the primary key of the group to check for associations with user groups
	* @return <code>true</code> if the group has any user groups associated with it; <code>false</code> otherwise
	*/
	public boolean containsUserGroups(long pk);

	/**
	* Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPK the primary key of the user group
	*/
	public void addUserGroup(long pk, long userGroupPK);

	/**
	* Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroup the user group
	*/
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup);

	/**
	* Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPKs the primary keys of the user groups
	*/
	public void addUserGroups(long pk, long[] userGroupPKs);

	/**
	* Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroups the user groups
	*/
	public void addUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	/**
	* Clears all associations between the group and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated user groups from
	*/
	public void clearUserGroups(long pk);

	/**
	* Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPK the primary key of the user group
	*/
	public void removeUserGroup(long pk, long userGroupPK);

	/**
	* Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroup the user group
	*/
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup);

	/**
	* Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPKs the primary keys of the user groups
	*/
	public void removeUserGroups(long pk, long[] userGroupPKs);

	/**
	* Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroups the user groups
	*/
	public void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	/**
	* Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPKs the primary keys of the user groups to be associated with the group
	*/
	public void setUserGroups(long pk, long[] userGroupPKs);

	/**
	* Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroups the user groups to be associated with the group
	*/
	public void setUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	/**
	* Returns the primaryKeys of users associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of users associated with the group
	*/
	public long[] getUserPrimaryKeys(long pk);

	/**
	* Returns all the users associated with the group.
	*
	* @param pk the primary key of the group
	* @return the users associated with the group
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk);

	/**
	* Returns a range of all the users associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of users associated with the group
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end);

	/**
	* Returns an ordered range of all the users associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the group
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.User> orderByComparator);

	/**
	* Returns the number of users associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of users associated with the group
	*/
	public int getUsersSize(long pk);

	/**
	* Returns <code>true</code> if the user is associated with the group.
	*
	* @param pk the primary key of the group
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the group; <code>false</code> otherwise
	*/
	public boolean containsUser(long pk, long userPK);

	/**
	* Returns <code>true</code> if the group has any users associated with it.
	*
	* @param pk the primary key of the group to check for associations with users
	* @return <code>true</code> if the group has any users associated with it; <code>false</code> otherwise
	*/
	public boolean containsUsers(long pk);

	/**
	* Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPK the primary key of the user
	*/
	public void addUser(long pk, long userPK);

	/**
	* Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param user the user
	*/
	public void addUser(long pk, com.liferay.portal.model.User user);

	/**
	* Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPKs the primary keys of the users
	*/
	public void addUsers(long pk, long[] userPKs);

	/**
	* Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param users the users
	*/
	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users);

	/**
	* Clears all associations between the group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated users from
	*/
	public void clearUsers(long pk);

	/**
	* Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPK the primary key of the user
	*/
	public void removeUser(long pk, long userPK);

	/**
	* Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param user the user
	*/
	public void removeUser(long pk, com.liferay.portal.model.User user);

	/**
	* Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPKs the primary keys of the users
	*/
	public void removeUsers(long pk, long[] userPKs);

	/**
	* Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param users the users
	*/
	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users);

	/**
	* Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPKs the primary keys of the users to be associated with the group
	*/
	public void setUsers(long pk, long[] userPKs);

	/**
	* Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param users the users to be associated with the group
	*/
	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users);

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}