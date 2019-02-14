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

import com.liferay.portal.kernel.exception.NoSuchTeamException;
import com.liferay.portal.kernel.model.Team;

/**
 * The persistence interface for the team service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TeamUtil
 * @generated
 */
@ProviderType
public interface TeamPersistence extends BasePersistence<Team> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamUtil} to access the team persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the teams where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching teams
	*/
	public java.util.List<Team> findByUuid(String uuid);

	/**
	* Returns a range of all the teams where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of matching teams
	*/
	public java.util.List<Team> findByUuid(String uuid, int start, int end);

	/**
	* Returns an ordered range of all the teams where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching teams
	*/
	public java.util.List<Team> findByUuid(String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns an ordered range of all the teams where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching teams
	*/
	public java.util.List<Team> findByUuid(String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first team in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Returns the first team in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns the last team in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Returns the last team in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns the teams before and after the current team in the ordered set where uuid = &#63;.
	*
	* @param teamId the primary key of the current team
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next team
	* @throws NoSuchTeamException if a team with the primary key could not be found
	*/
	public Team[] findByUuid_PrevAndNext(long teamId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Removes all the teams where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of teams where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching teams
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the team where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTeamException</code> if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByUUID_G(String uuid, long groupId)
		throws NoSuchTeamException;

	/**
	* Returns the team where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the team where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the team where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the team that was removed
	*/
	public Team removeByUUID_G(String uuid, long groupId)
		throws NoSuchTeamException;

	/**
	* Returns the number of teams where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching teams
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the teams where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching teams
	*/
	public java.util.List<Team> findByUuid_C(String uuid, long companyId);

	/**
	* Returns a range of all the teams where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of matching teams
	*/
	public java.util.List<Team> findByUuid_C(String uuid, long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the teams where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching teams
	*/
	public java.util.List<Team> findByUuid_C(String uuid, long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns an ordered range of all the teams where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching teams
	*/
	public java.util.List<Team> findByUuid_C(String uuid, long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first team in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Returns the first team in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns the last team in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Returns the last team in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns the teams before and after the current team in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param teamId the primary key of the current team
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next team
	* @throws NoSuchTeamException if a team with the primary key could not be found
	*/
	public Team[] findByUuid_C_PrevAndNext(long teamId, String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Removes all the teams where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of teams where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching teams
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the teams where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching teams
	*/
	public java.util.List<Team> findByGroupId(long groupId);

	/**
	* Returns a range of all the teams where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of matching teams
	*/
	public java.util.List<Team> findByGroupId(long groupId, int start, int end);

	/**
	* Returns an ordered range of all the teams where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching teams
	*/
	public java.util.List<Team> findByGroupId(long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns an ordered range of all the teams where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching teams
	*/
	public java.util.List<Team> findByGroupId(long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Returns the first team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns the last team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Returns the last team in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns the teams before and after the current team in the ordered set where groupId = &#63;.
	*
	* @param teamId the primary key of the current team
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next team
	* @throws NoSuchTeamException if a team with the primary key could not be found
	*/
	public Team[] findByGroupId_PrevAndNext(long teamId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Returns all the teams that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching teams that the user has permission to view
	*/
	public java.util.List<Team> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the teams that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of matching teams that the user has permission to view
	*/
	public java.util.List<Team> filterFindByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the teams that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching teams that the user has permission to view
	*/
	public java.util.List<Team> filterFindByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns the teams before and after the current team in the ordered set of teams that the user has permission to view where groupId = &#63;.
	*
	* @param teamId the primary key of the current team
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next team
	* @throws NoSuchTeamException if a team with the primary key could not be found
	*/
	public Team[] filterFindByGroupId_PrevAndNext(long teamId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator)
		throws NoSuchTeamException;

	/**
	* Removes all the teams where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of teams where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching teams
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of teams that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching teams that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the team where groupId = &#63; and name = &#63; or throws a <code>NoSuchTeamException</code> if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching team
	* @throws NoSuchTeamException if a matching team could not be found
	*/
	public Team findByG_N(long groupId, String name) throws NoSuchTeamException;

	/**
	* Returns the team where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByG_N(long groupId, String name);

	/**
	* Returns the team where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching team, or <code>null</code> if a matching team could not be found
	*/
	public Team fetchByG_N(long groupId, String name, boolean retrieveFromCache);

	/**
	* Removes the team where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the team that was removed
	*/
	public Team removeByG_N(long groupId, String name)
		throws NoSuchTeamException;

	/**
	* Returns the number of teams where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching teams
	*/
	public int countByG_N(long groupId, String name);

	/**
	* Caches the team in the entity cache if it is enabled.
	*
	* @param team the team
	*/
	public void cacheResult(Team team);

	/**
	* Caches the teams in the entity cache if it is enabled.
	*
	* @param teams the teams
	*/
	public void cacheResult(java.util.List<Team> teams);

	/**
	* Creates a new team with the primary key. Does not add the team to the database.
	*
	* @param teamId the primary key for the new team
	* @return the new team
	*/
	public Team create(long teamId);

	/**
	* Removes the team with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param teamId the primary key of the team
	* @return the team that was removed
	* @throws NoSuchTeamException if a team with the primary key could not be found
	*/
	public Team remove(long teamId) throws NoSuchTeamException;

	public Team updateImpl(Team team);

	/**
	* Returns the team with the primary key or throws a <code>NoSuchTeamException</code> if it could not be found.
	*
	* @param teamId the primary key of the team
	* @return the team
	* @throws NoSuchTeamException if a team with the primary key could not be found
	*/
	public Team findByPrimaryKey(long teamId) throws NoSuchTeamException;

	/**
	* Returns the team with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param teamId the primary key of the team
	* @return the team, or <code>null</code> if a team with the primary key could not be found
	*/
	public Team fetchByPrimaryKey(long teamId);

	/**
	* Returns all the teams.
	*
	* @return the teams
	*/
	public java.util.List<Team> findAll();

	/**
	* Returns a range of all the teams.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of teams
	*/
	public java.util.List<Team> findAll(int start, int end);

	/**
	* Returns an ordered range of all the teams.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of teams
	*/
	public java.util.List<Team> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator);

	/**
	* Returns an ordered range of all the teams.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of teams
	*/
	public java.util.List<Team> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Team> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the teams from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of teams.
	*
	* @return the number of teams
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of users associated with the team.
	*
	* @param pk the primary key of the team
	* @return long[] of the primaryKeys of users associated with the team
	*/
	public long[] getUserPrimaryKeys(long pk);

	/**
	* Returns all the users associated with the team.
	*
	* @param pk the primary key of the team
	* @return the users associated with the team
	*/
	public java.util.List<com.liferay.portal.kernel.model.User> getUsers(
		long pk);

	/**
	* Returns a range of all the users associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of users associated with the team
	*/
	public java.util.List<com.liferay.portal.kernel.model.User> getUsers(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the users associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the team
	*/
	public java.util.List<com.liferay.portal.kernel.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.User> orderByComparator);

	/**
	* Returns the number of users associated with the team.
	*
	* @param pk the primary key of the team
	* @return the number of users associated with the team
	*/
	public int getUsersSize(long pk);

	/**
	* Returns <code>true</code> if the user is associated with the team.
	*
	* @param pk the primary key of the team
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the team; <code>false</code> otherwise
	*/
	public boolean containsUser(long pk, long userPK);

	/**
	* Returns <code>true</code> if the team has any users associated with it.
	*
	* @param pk the primary key of the team to check for associations with users
	* @return <code>true</code> if the team has any users associated with it; <code>false</code> otherwise
	*/
	public boolean containsUsers(long pk);

	/**
	* Adds an association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPK the primary key of the user
	*/
	public void addUser(long pk, long userPK);

	/**
	* Adds an association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param user the user
	*/
	public void addUser(long pk, com.liferay.portal.kernel.model.User user);

	/**
	* Adds an association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPKs the primary keys of the users
	*/
	public void addUsers(long pk, long[] userPKs);

	/**
	* Adds an association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param users the users
	*/
	public void addUsers(long pk,
		java.util.List<com.liferay.portal.kernel.model.User> users);

	/**
	* Clears all associations between the team and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team to clear the associated users from
	*/
	public void clearUsers(long pk);

	/**
	* Removes the association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPK the primary key of the user
	*/
	public void removeUser(long pk, long userPK);

	/**
	* Removes the association between the team and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param user the user
	*/
	public void removeUser(long pk, com.liferay.portal.kernel.model.User user);

	/**
	* Removes the association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPKs the primary keys of the users
	*/
	public void removeUsers(long pk, long[] userPKs);

	/**
	* Removes the association between the team and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param users the users
	*/
	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.kernel.model.User> users);

	/**
	* Sets the users associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userPKs the primary keys of the users to be associated with the team
	*/
	public void setUsers(long pk, long[] userPKs);

	/**
	* Sets the users associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param users the users to be associated with the team
	*/
	public void setUsers(long pk,
		java.util.List<com.liferay.portal.kernel.model.User> users);

	/**
	* Returns the primaryKeys of user groups associated with the team.
	*
	* @param pk the primary key of the team
	* @return long[] of the primaryKeys of user groups associated with the team
	*/
	public long[] getUserGroupPrimaryKeys(long pk);

	/**
	* Returns all the user groups associated with the team.
	*
	* @param pk the primary key of the team
	* @return the user groups associated with the team
	*/
	public java.util.List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk);

	/**
	* Returns a range of all the user groups associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @return the range of user groups associated with the team
	*/
	public java.util.List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the user groups associated with the team.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the team
	* @param start the lower bound of the range of teams
	* @param end the upper bound of the range of teams (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups associated with the team
	*/
	public java.util.List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.UserGroup> orderByComparator);

	/**
	* Returns the number of user groups associated with the team.
	*
	* @param pk the primary key of the team
	* @return the number of user groups associated with the team
	*/
	public int getUserGroupsSize(long pk);

	/**
	* Returns <code>true</code> if the user group is associated with the team.
	*
	* @param pk the primary key of the team
	* @param userGroupPK the primary key of the user group
	* @return <code>true</code> if the user group is associated with the team; <code>false</code> otherwise
	*/
	public boolean containsUserGroup(long pk, long userGroupPK);

	/**
	* Returns <code>true</code> if the team has any user groups associated with it.
	*
	* @param pk the primary key of the team to check for associations with user groups
	* @return <code>true</code> if the team has any user groups associated with it; <code>false</code> otherwise
	*/
	public boolean containsUserGroups(long pk);

	/**
	* Adds an association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPK the primary key of the user group
	*/
	public void addUserGroup(long pk, long userGroupPK);

	/**
	* Adds an association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroup the user group
	*/
	public void addUserGroup(long pk,
		com.liferay.portal.kernel.model.UserGroup userGroup);

	/**
	* Adds an association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPKs the primary keys of the user groups
	*/
	public void addUserGroups(long pk, long[] userGroupPKs);

	/**
	* Adds an association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroups the user groups
	*/
	public void addUserGroups(long pk,
		java.util.List<com.liferay.portal.kernel.model.UserGroup> userGroups);

	/**
	* Clears all associations between the team and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team to clear the associated user groups from
	*/
	public void clearUserGroups(long pk);

	/**
	* Removes the association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPK the primary key of the user group
	*/
	public void removeUserGroup(long pk, long userGroupPK);

	/**
	* Removes the association between the team and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroup the user group
	*/
	public void removeUserGroup(long pk,
		com.liferay.portal.kernel.model.UserGroup userGroup);

	/**
	* Removes the association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPKs the primary keys of the user groups
	*/
	public void removeUserGroups(long pk, long[] userGroupPKs);

	/**
	* Removes the association between the team and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroups the user groups
	*/
	public void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.kernel.model.UserGroup> userGroups);

	/**
	* Sets the user groups associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroupPKs the primary keys of the user groups to be associated with the team
	*/
	public void setUserGroups(long pk, long[] userGroupPKs);

	/**
	* Sets the user groups associated with the team, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the team
	* @param userGroups the user groups to be associated with the team
	*/
	public void setUserGroups(long pk,
		java.util.List<com.liferay.portal.kernel.model.UserGroup> userGroups);
}