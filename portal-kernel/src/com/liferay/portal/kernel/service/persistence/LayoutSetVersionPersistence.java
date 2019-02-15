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

import com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException;
import com.liferay.portal.kernel.model.LayoutSetVersion;

/**
 * The persistence interface for the layout set version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetVersionUtil
 * @generated
 */
@ProviderType
public interface LayoutSetVersionPersistence extends BasePersistence<LayoutSetVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetVersionUtil} to access the layout set version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout set versions where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetId(long layoutSetId);

	/**
	* Returns a range of all the layout set versions where layoutSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetId the layout set ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetId(
		long layoutSetId, int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where layoutSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetId the layout set ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetId(
		long layoutSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where layoutSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetId the layout set ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetId(
		long layoutSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetId_First(long layoutSetId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetId_First(long layoutSetId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetId_Last(long layoutSetId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetId_Last(long layoutSetId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetId the layout set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByLayoutSetId_PrevAndNext(
		long layoutSetVersionId, long layoutSetId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where layoutSetId = &#63; from the database.
	*
	* @param layoutSetId the layout set ID
	*/
	public void removeByLayoutSetId(long layoutSetId);

	/**
	* Returns the number of layout set versions where layoutSetId = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @return the number of matching layout set versions
	*/
	public int countByLayoutSetId(long layoutSetId);

	/**
	* Returns the layout set version where layoutSetId = &#63; and version = &#63; or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetId_Version(long layoutSetId,
		int version) throws NoSuchLayoutSetVersionException;

	/**
	* Returns the layout set version where layoutSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetId_Version(long layoutSetId,
		int version);

	/**
	* Returns the layout set version where layoutSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetId_Version(long layoutSetId,
		int version, boolean retrieveFromCache);

	/**
	* Removes the layout set version where layoutSetId = &#63; and version = &#63; from the database.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the layout set version that was removed
	*/
	public LayoutSetVersion removeByLayoutSetId_Version(long layoutSetId,
		int version) throws NoSuchLayoutSetVersionException;

	/**
	* Returns the number of layout set versions where layoutSetId = &#63; and version = &#63;.
	*
	* @param layoutSetId the layout set ID
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public int countByLayoutSetId_Version(long layoutSetId, int version);

	/**
	* Returns all the layout set versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId(long groupId);

	/**
	* Returns a range of all the layout set versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByGroupId_PrevAndNext(
		long layoutSetVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout set versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout set versions
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId_Version(
		long groupId, int version);

	/**
	* Returns a range of all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId_Version(
		long groupId, int version, int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByGroupId_Version_First(long groupId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByGroupId_Version_First(long groupId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByGroupId_Version_Last(long groupId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByGroupId_Version_Last(long groupId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByGroupId_Version_PrevAndNext(
		long layoutSetVersionId, long groupId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where groupId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param version the version
	*/
	public void removeByGroupId_Version(long groupId, int version);

	/**
	* Returns the number of layout set versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public int countByGroupId_Version(long groupId, int version);

	/**
	* Returns all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid);

	/**
	* Returns a range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where layoutSetPrototypeUuid = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	*/
	public void removeByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid);

	/**
	* Returns the number of layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the number of matching layout set versions
	*/
	public int countByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid);

	/**
	* Returns all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version);

	/**
	* Returns a range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Version_First(
		String layoutSetPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Version_First(
		String layoutSetPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Version_Last(
		String layoutSetPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Version_Last(
		String layoutSetPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByLayoutSetPrototypeUuid_Version_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	*/
	public void removeByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version);

	/**
	* Returns the number of layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public int countByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version);

	/**
	* Returns all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout);

	/**
	* Returns a range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByG_P_First(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByG_P_First(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByG_P_Last(long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByG_P_PrevAndNext(long layoutSetVersionId,
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	*/
	public void removeByG_P(long groupId, boolean privateLayout);

	/**
	* Returns the number of layout set versions where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layout set versions
	*/
	public int countByG_P(long groupId, boolean privateLayout);

	/**
	* Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByG_P_Version(long groupId,
		boolean privateLayout, int version)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByG_P_Version(long groupId,
		boolean privateLayout, int version);

	/**
	* Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByG_P_Version(long groupId,
		boolean privateLayout, int version, boolean retrieveFromCache);

	/**
	* Removes the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the layout set version that was removed
	*/
	public LayoutSetVersion removeByG_P_Version(long groupId,
		boolean privateLayout, int version)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the number of layout set versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public int countByG_P_Version(long groupId, boolean privateLayout,
		int version);

	/**
	* Returns all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId);

	/**
	* Returns a range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId, int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L(boolean privateLayout,
		long logoId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByP_L_First(boolean privateLayout, long logoId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByP_L_First(boolean privateLayout,
		long logoId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByP_L_Last(boolean privateLayout, long logoId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByP_L_Last(boolean privateLayout, long logoId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByP_L_PrevAndNext(long layoutSetVersionId,
		boolean privateLayout, long logoId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where privateLayout = &#63; and logoId = &#63; from the database.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	*/
	public void removeByP_L(boolean privateLayout, long logoId);

	/**
	* Returns the number of layout set versions where privateLayout = &#63; and logoId = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @return the number of matching layout set versions
	*/
	public int countByP_L(boolean privateLayout, long logoId);

	/**
	* Returns all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version);

	/**
	* Returns a range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version, int start, int end);

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByP_L_Version(
		boolean privateLayout, long logoId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByP_L_Version_First(boolean privateLayout,
		long logoId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByP_L_Version_First(boolean privateLayout,
		long logoId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByP_L_Version_Last(boolean privateLayout,
		long logoId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByP_L_Version_Last(boolean privateLayout,
		long logoId, int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByP_L_Version_PrevAndNext(
		long layoutSetVersionId, boolean privateLayout, long logoId,
		int version,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63; from the database.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	*/
	public void removeByP_L_Version(boolean privateLayout, long logoId,
		int version);

	/**
	* Returns the number of layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	*
	* @param privateLayout the private layout
	* @param logoId the logo ID
	* @param version the version
	* @return the number of matching layout set versions
	*/
	public int countByP_L_Version(boolean privateLayout, long logoId,
		int version);

	/**
	* Caches the layout set version in the entity cache if it is enabled.
	*
	* @param layoutSetVersion the layout set version
	*/
	public void cacheResult(LayoutSetVersion layoutSetVersion);

	/**
	* Caches the layout set versions in the entity cache if it is enabled.
	*
	* @param layoutSetVersions the layout set versions
	*/
	public void cacheResult(java.util.List<LayoutSetVersion> layoutSetVersions);

	/**
	* Creates a new layout set version with the primary key. Does not add the layout set version to the database.
	*
	* @param layoutSetVersionId the primary key for the new layout set version
	* @return the new layout set version
	*/
	public LayoutSetVersion create(long layoutSetVersionId);

	/**
	* Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version that was removed
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion remove(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException;

	public LayoutSetVersion updateImpl(LayoutSetVersion layoutSetVersion);

	/**
	* Returns the layout set version with the primary key or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion findByPrimaryKey(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the layout set version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version, or <code>null</code> if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion fetchByPrimaryKey(long layoutSetVersionId);

	/**
	* Returns all the layout set versions.
	*
	* @return the layout set versions
	*/
	public java.util.List<LayoutSetVersion> findAll();

	/**
	* Returns a range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of layout set versions
	*/
	public java.util.List<LayoutSetVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout set versions
	*/
	public java.util.List<LayoutSetVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns an ordered range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout set versions
	*/
	public java.util.List<LayoutSetVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout set versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout set versions.
	*
	* @return the number of layout set versions
	*/
	public int countAll();
}