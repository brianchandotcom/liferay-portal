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

package com.liferay.segments.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.exception.NoSuchEntryRoleException;
import com.liferay.segments.model.SegmentsEntryRole;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the segments entry role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRoleUtil
 * @generated
 */
@ProviderType
public interface SegmentsEntryRolePersistence
	extends BasePersistence<SegmentsEntryRole> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsEntryRoleUtil} to access the segments entry role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the segments entry roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findByRoleId(long roleId);

	/**
	 * Returns a range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end);

	/**
	 * Returns an ordered range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole findByRoleId_First(
			long roleId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
				orderByComparator)
		throws NoSuchEntryRoleException;

	/**
	 * Returns the first segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole fetchByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator);

	/**
	 * Returns the last segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole findByRoleId_Last(
			long roleId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
				orderByComparator)
		throws NoSuchEntryRoleException;

	/**
	 * Returns the last segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole fetchByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator);

	/**
	 * Returns the segments entry roles before and after the current segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param segmentsEntryRoleId the primary key of the current segments entry role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public SegmentsEntryRole[] findByRoleId_PrevAndNext(
			long segmentsEntryRoleId, long roleId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
				orderByComparator)
		throws NoSuchEntryRoleException;

	/**
	 * Removes all the segments entry roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	public void removeByRoleId(long roleId);

	/**
	 * Returns the number of segments entry roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching segments entry roles
	 */
	public int countByRoleId(long roleId);

	/**
	 * Returns all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId);

	/**
	 * Returns a range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole findBySegmentsEntryId_First(
			long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
				orderByComparator)
		throws NoSuchEntryRoleException;

	/**
	 * Returns the first segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator);

	/**
	 * Returns the last segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole findBySegmentsEntryId_Last(
			long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
				orderByComparator)
		throws NoSuchEntryRoleException;

	/**
	 * Returns the last segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator);

	/**
	 * Returns the segments entry roles before and after the current segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryRoleId the primary key of the current segments entry role
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public SegmentsEntryRole[] findBySegmentsEntryId_PrevAndNext(
			long segmentsEntryRoleId, long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
				orderByComparator)
		throws NoSuchEntryRoleException;

	/**
	 * Removes all the segments entry roles where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	public void removeBySegmentsEntryId(long segmentsEntryId);

	/**
	 * Returns the number of segments entry roles where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entry roles
	 */
	public int countBySegmentsEntryId(long segmentsEntryId);

	/**
	 * Returns the segments entry role where roleId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchEntryRoleException</code> if it could not be found.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole findByR_S(long roleId, long segmentsEntryId)
		throws NoSuchEntryRoleException;

	/**
	 * Returns the segments entry role where roleId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole fetchByR_S(long roleId, long segmentsEntryId);

	/**
	 * Returns the segments entry role where roleId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public SegmentsEntryRole fetchByR_S(
		long roleId, long segmentsEntryId, boolean useFinderCache);

	/**
	 * Removes the segments entry role where roleId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the segments entry role that was removed
	 */
	public SegmentsEntryRole removeByR_S(long roleId, long segmentsEntryId)
		throws NoSuchEntryRoleException;

	/**
	 * Returns the number of segments entry roles where roleId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entry roles
	 */
	public int countByR_S(long roleId, long segmentsEntryId);

	/**
	 * Caches the segments entry role in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRole the segments entry role
	 */
	public void cacheResult(SegmentsEntryRole segmentsEntryRole);

	/**
	 * Caches the segments entry roles in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRoles the segments entry roles
	 */
	public void cacheResult(
		java.util.List<SegmentsEntryRole> segmentsEntryRoles);

	/**
	 * Creates a new segments entry role with the primary key. Does not add the segments entry role to the database.
	 *
	 * @param segmentsEntryRoleId the primary key for the new segments entry role
	 * @return the new segments entry role
	 */
	public SegmentsEntryRole create(long segmentsEntryRoleId);

	/**
	 * Removes the segments entry role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role that was removed
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public SegmentsEntryRole remove(long segmentsEntryRoleId)
		throws NoSuchEntryRoleException;

	public SegmentsEntryRole updateImpl(SegmentsEntryRole segmentsEntryRole);

	/**
	 * Returns the segments entry role with the primary key or throws a <code>NoSuchEntryRoleException</code> if it could not be found.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public SegmentsEntryRole findByPrimaryKey(long segmentsEntryRoleId)
		throws NoSuchEntryRoleException;

	/**
	 * Returns the segments entry role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role, or <code>null</code> if a segments entry role with the primary key could not be found
	 */
	public SegmentsEntryRole fetchByPrimaryKey(long segmentsEntryRoleId);

	/**
	 * Returns all the segments entry roles.
	 *
	 * @return the segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findAll();

	/**
	 * Returns a range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments entry roles
	 */
	public java.util.List<SegmentsEntryRole> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRole>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the segments entry roles from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of segments entry roles.
	 *
	 * @return the number of segments entry roles
	 */
	public int countAll();

}