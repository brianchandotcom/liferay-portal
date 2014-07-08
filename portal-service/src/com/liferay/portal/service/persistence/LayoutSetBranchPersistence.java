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

import com.liferay.portal.model.LayoutSetBranch;

/**
 * The persistence interface for the layout set branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranchPersistenceImpl
 * @see LayoutSetBranchUtil
 * @generated
 */
@ProviderType
public interface LayoutSetBranchPersistence extends BasePersistence<LayoutSetBranch> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetBranchUtil} to access the layout set branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @return the range of matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the first layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the last layout set branch in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63;.
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch[] findByGroupId_PrevAndNext(
		long layoutSetBranchId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns all the layout set branchs that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the layout set branchs that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @return the range of matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the layout set branchs that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout set branchs before and after the current layout set branch in the ordered set of layout set branchs that the user has permission to view where groupId = &#63;.
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch[] filterFindByGroupId_PrevAndNext(
		long layoutSetBranchId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Removes all the layout set branchs where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout set branchs
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of layout set branchs that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout set branchs that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P(
		long groupId, boolean privateLayout);

	/**
	* Returns a range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @return the range of matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P(
		long groupId, boolean privateLayout, int start, int end);

	/**
	* Returns an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByG_P_Last(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByG_P_Last(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch[] findByG_P_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P(
		long groupId, boolean privateLayout);

	/**
	* Returns a range of all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @return the range of matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end);

	/**
	* Returns an ordered range of all the layout set branchs that the user has permissions to view where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout set branchs before and after the current layout set branch in the ordered set of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch[] filterFindByG_P_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Removes all the layout set branchs where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	*/
	public void removeByG_P(long groupId, boolean privateLayout);

	/**
	* Returns the number of layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layout set branchs
	*/
	public int countByG_P(long groupId, boolean privateLayout);

	/**
	* Returns the number of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layout set branchs that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, boolean privateLayout);

	/**
	* Returns the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutSetBranchException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param name the name
	* @return the matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByG_P_N(long groupId,
		boolean privateLayout, java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param name the name
	* @return the matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByG_P_N(long groupId,
		boolean privateLayout, java.lang.String name);

	/**
	* Returns the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByG_P_N(long groupId,
		boolean privateLayout, java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param name the name
	* @return the layout set branch that was removed
	*/
	public com.liferay.portal.model.LayoutSetBranch removeByG_P_N(
		long groupId, boolean privateLayout, java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the number of layout set branchs where groupId = &#63; and privateLayout = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param name the name
	* @return the number of matching layout set branchs
	*/
	public int countByG_P_N(long groupId, boolean privateLayout,
		java.lang.String name);

	/**
	* Returns all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @return the matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P_M(
		long groupId, boolean privateLayout, boolean master);

	/**
	* Returns a range of all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @return the range of matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P_M(
		long groupId, boolean privateLayout, boolean master, int start, int end);

	/**
	* Returns an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P_M(
		long groupId, boolean privateLayout, boolean master, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByG_P_M_First(
		long groupId, boolean privateLayout, boolean master,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByG_P_M_First(
		long groupId, boolean privateLayout, boolean master,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByG_P_M_Last(
		long groupId, boolean privateLayout, boolean master,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByG_P_M_Last(
		long groupId, boolean privateLayout, boolean master,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch[] findByG_P_M_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		boolean master,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @return the matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P_M(
		long groupId, boolean privateLayout, boolean master);

	/**
	* Returns a range of all the layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @return the range of matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P_M(
		long groupId, boolean privateLayout, boolean master, int start, int end);

	/**
	* Returns an ordered range of all the layout set branchs that the user has permissions to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P_M(
		long groupId, boolean privateLayout, boolean master, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout set branchs before and after the current layout set branch in the ordered set of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch[] filterFindByG_P_M_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		boolean master,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Removes all the layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	*/
	public void removeByG_P_M(long groupId, boolean privateLayout,
		boolean master);

	/**
	* Returns the number of layout set branchs where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @return the number of matching layout set branchs
	*/
	public int countByG_P_M(long groupId, boolean privateLayout, boolean master);

	/**
	* Returns the number of layout set branchs that the user has permission to view where groupId = &#63; and privateLayout = &#63; and master = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param master the master
	* @return the number of matching layout set branchs that the user has permission to view
	*/
	public int filterCountByG_P_M(long groupId, boolean privateLayout,
		boolean master);

	/**
	* Caches the layout set branch in the entity cache if it is enabled.
	*
	* @param layoutSetBranch the layout set branch
	*/
	public void cacheResult(
		com.liferay.portal.model.LayoutSetBranch layoutSetBranch);

	/**
	* Caches the layout set branchs in the entity cache if it is enabled.
	*
	* @param layoutSetBranchs the layout set branchs
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutSetBranch> layoutSetBranchs);

	/**
	* Creates a new layout set branch with the primary key. Does not add the layout set branch to the database.
	*
	* @param layoutSetBranchId the primary key for the new layout set branch
	* @return the new layout set branch
	*/
	public com.liferay.portal.model.LayoutSetBranch create(
		long layoutSetBranchId);

	/**
	* Removes the layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetBranchId the primary key of the layout set branch
	* @return the layout set branch that was removed
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch remove(
		long layoutSetBranchId)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	public com.liferay.portal.model.LayoutSetBranch updateImpl(
		com.liferay.portal.model.LayoutSetBranch layoutSetBranch);

	/**
	* Returns the layout set branch with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutSetBranchException} if it could not be found.
	*
	* @param layoutSetBranchId the primary key of the layout set branch
	* @return the layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch findByPrimaryKey(
		long layoutSetBranchId)
		throws com.liferay.portal.NoSuchLayoutSetBranchException;

	/**
	* Returns the layout set branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetBranchId the primary key of the layout set branch
	* @return the layout set branch, or <code>null</code> if a layout set branch with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutSetBranch fetchByPrimaryKey(
		long layoutSetBranchId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.LayoutSetBranch> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout set branchs.
	*
	* @return the layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findAll();

	/**
	* Returns a range of all the layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @return the range of layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set branchs
	* @param end the upper bound of the range of layout set branchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout set branchs
	*/
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the layout set branchs from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout set branchs.
	*
	* @return the number of layout set branchs
	*/
	public int countAll();
}