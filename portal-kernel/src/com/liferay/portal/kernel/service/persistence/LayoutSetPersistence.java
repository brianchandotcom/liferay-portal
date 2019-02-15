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

import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.model.LayoutSet;

/**
 * The persistence interface for the layout set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetUtil
 * @generated
 */
@ProviderType
public interface LayoutSetPersistence extends BasePersistence<LayoutSet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetUtil} to access the layout set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout sets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout sets
	*/
	public java.util.List<LayoutSet> findByGroupId(long groupId);

	/**
	* Returns a range of all the layout sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the layout sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns an ordered range of all the layout sets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws NoSuchLayoutSetException;

	/**
	* Returns the first layout set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns the last layout set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws NoSuchLayoutSetException;

	/**
	* Returns the last layout set in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns the layout sets before and after the current layout set in the ordered set where groupId = &#63;.
	*
	* @param layoutSetId the primary key of the current layout set
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set
	* @throws NoSuchLayoutSetException if a layout set with the primary key could not be found
	*/
	public LayoutSet[] findByGroupId_PrevAndNext(long layoutSetId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws NoSuchLayoutSetException;

	/**
	* Removes all the layout sets where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout sets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout sets
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the matching layout sets
	*/
	public java.util.List<LayoutSet> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid);

	/**
	* Returns a range of all the layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end);

	/**
	* Returns an ordered range of all the layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns an ordered range of all the layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout sets
	*/
	public java.util.List<LayoutSet> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws NoSuchLayoutSetException;

	/**
	* Returns the first layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns the last layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws NoSuchLayoutSetException;

	/**
	* Returns the last layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns the layout sets before and after the current layout set in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetId the primary key of the current layout set
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set
	* @throws NoSuchLayoutSetException if a layout set with the primary key could not be found
	*/
	public LayoutSet[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetId, String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator)
		throws NoSuchLayoutSetException;

	/**
	* Removes all the layout sets where layoutSetPrototypeUuid = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	*/
	public void removeByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid);

	/**
	* Returns the number of layout sets where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the number of matching layout sets
	*/
	public int countByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid);

	/**
	* Returns the layout set where groupId = &#63; and privateLayout = &#63; and head = &#63; or throws a <code>NoSuchLayoutSetException</code> if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param head the head
	* @return the matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByG_P(long groupId, boolean privateLayout, boolean head)
		throws NoSuchLayoutSetException;

	/**
	* Returns the layout set where groupId = &#63; and privateLayout = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param head the head
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByG_P(long groupId, boolean privateLayout,
		boolean head);

	/**
	* Returns the layout set where groupId = &#63; and privateLayout = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param head the head
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByG_P(long groupId, boolean privateLayout,
		boolean head, boolean retrieveFromCache);

	/**
	* Removes the layout set where groupId = &#63; and privateLayout = &#63; and head = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param head the head
	* @return the layout set that was removed
	*/
	public LayoutSet removeByG_P(long groupId, boolean privateLayout,
		boolean head) throws NoSuchLayoutSetException;

	/**
	* Returns the number of layout sets where groupId = &#63; and privateLayout = &#63; and head = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param head the head
	* @return the number of matching layout sets
	*/
	public int countByG_P(long groupId, boolean privateLayout, boolean head);

	/**
	* Returns the layout set where privateLayout = &#63; and logoId = &#63; or throws a <code>NoSuchLayoutSetException</code> if it could not be found.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByP_L(boolean privateLayout, long logoId)
		throws NoSuchLayoutSetException;

	/**
	* Returns the layout set where privateLayout = &#63; and logoId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByP_L(boolean privateLayout, long logoId);

	/**
	* Returns the layout set where privateLayout = &#63; and logoId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByP_L(boolean privateLayout, long logoId,
		boolean retrieveFromCache);

	/**
	* Removes the layout set where privateLayout = &#63; and logoId = &#63; from the database.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the layout set that was removed
	*/
	public LayoutSet removeByP_L(boolean privateLayout, long logoId)
		throws NoSuchLayoutSetException;

	/**
	* Returns the number of layout sets where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the number of matching layout sets
	*/
	public int countByP_L(boolean privateLayout, long logoId);

	/**
	* Returns the layout set where headId = &#63; or throws a <code>NoSuchLayoutSetException</code> if it could not be found.
	*
	* @param headId the head ID
	* @return the matching layout set
	* @throws NoSuchLayoutSetException if a matching layout set could not be found
	*/
	public LayoutSet findByHeadId(long headId) throws NoSuchLayoutSetException;

	/**
	* Returns the layout set where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param headId the head ID
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByHeadId(long headId);

	/**
	* Returns the layout set where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param headId the head ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	*/
	public LayoutSet fetchByHeadId(long headId, boolean retrieveFromCache);

	/**
	* Removes the layout set where headId = &#63; from the database.
	*
	* @param headId the head ID
	* @return the layout set that was removed
	*/
	public LayoutSet removeByHeadId(long headId)
		throws NoSuchLayoutSetException;

	/**
	* Returns the number of layout sets where headId = &#63;.
	*
	* @param headId the head ID
	* @return the number of matching layout sets
	*/
	public int countByHeadId(long headId);

	/**
	* Caches the layout set in the entity cache if it is enabled.
	*
	* @param layoutSet the layout set
	*/
	public void cacheResult(LayoutSet layoutSet);

	/**
	* Caches the layout sets in the entity cache if it is enabled.
	*
	* @param layoutSets the layout sets
	*/
	public void cacheResult(java.util.List<LayoutSet> layoutSets);

	/**
	* Creates a new layout set with the primary key. Does not add the layout set to the database.
	*
	* @param layoutSetId the primary key for the new layout set
	* @return the new layout set
	*/
	public LayoutSet create(long layoutSetId);

	/**
	* Removes the layout set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set that was removed
	* @throws NoSuchLayoutSetException if a layout set with the primary key could not be found
	*/
	public LayoutSet remove(long layoutSetId) throws NoSuchLayoutSetException;

	public LayoutSet updateImpl(LayoutSet layoutSet);

	/**
	* Returns the layout set with the primary key or throws a <code>NoSuchLayoutSetException</code> if it could not be found.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set
	* @throws NoSuchLayoutSetException if a layout set with the primary key could not be found
	*/
	public LayoutSet findByPrimaryKey(long layoutSetId)
		throws NoSuchLayoutSetException;

	/**
	* Returns the layout set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set, or <code>null</code> if a layout set with the primary key could not be found
	*/
	public LayoutSet fetchByPrimaryKey(long layoutSetId);

	/**
	* Returns all the layout sets.
	*
	* @return the layout sets
	*/
	public java.util.List<LayoutSet> findAll();

	/**
	* Returns a range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of layout sets
	*/
	public java.util.List<LayoutSet> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout sets
	*/
	public java.util.List<LayoutSet> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator);

	/**
	* Returns an ordered range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout sets
	*/
	public java.util.List<LayoutSet> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSet> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout sets from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout sets.
	*
	* @return the number of layout sets
	*/
	public int countAll();
}