/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ResourceBlock;

/**
 * The persistence interface for the resource block service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPersistenceImpl
 * @see ResourceBlockUtil
 * @generated
 */
public interface ResourceBlockPersistence extends BasePersistence<ResourceBlock> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceBlockUtil} to access the resource block persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the resource block in the entity cache if it is enabled.
	*
	* @param resourceBlock the resource block
	*/
	public void cacheResult(
		com.liferay.portal.model.ResourceBlock resourceBlock);

	/**
	* Caches the resource blocks in the entity cache if it is enabled.
	*
	* @param resourceBlocks the resource blocks
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceBlock> resourceBlocks);

	/**
	* Creates a new resource block with the primary key. Does not add the resource block to the database.
	*
	* @param resourceBlockId the primary key for the new resource block
	* @return the new resource block
	*/
	public com.liferay.portal.model.ResourceBlock create(long resourceBlockId);

	/**
	* Removes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block that was removed
	* @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock remove(long resourceBlockId)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceBlock updateImpl(
		com.liferay.portal.model.ResourceBlock resourceBlock, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockException} if it could not be found.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock findByPrimaryKey(
		long resourceBlockId)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block, or <code>null</code> if a resource block with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock fetchByPrimaryKey(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findByC_N(
		long companyId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findByC_N(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock findByC_N_First(
		long companyId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock findByC_N_Last(
		long companyId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the primary key of the current resource block
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock[] findByC_N_PrevAndNext(
		long resourceBlockId, long companyId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @return the matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findByC_G_N(
		long companyId, long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findByC_G_N(
		long companyId, long groupId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findByC_G_N(
		long companyId, long groupId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock findByC_G_N_First(
		long companyId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock findByC_G_N_Last(
		long companyId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the primary key of the current resource block
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a resource block with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock[] findByC_G_N_PrevAndNext(
		long resourceBlockId, long companyId, long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or throws a {@link com.liferay.portal.NoSuchResourceBlockException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the matching resource block
	* @throws com.liferay.portal.NoSuchResourceBlockException if a matching resource block could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock findByC_G_N_P(
		long companyId, long groupId, java.lang.String name,
		java.lang.String permissionsHash)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock fetchByC_G_N_P(
		long companyId, long groupId, java.lang.String name,
		java.lang.String permissionsHash)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock fetchByC_G_N_P(
		long companyId, long groupId, java.lang.String name,
		java.lang.String permissionsHash, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource blocks.
	*
	* @return the resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlock> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource blocks where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_G_N(long companyId, long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the resource block that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlock removeByC_G_N_P(
		long companyId, long groupId, java.lang.String name,
		java.lang.String permissionsHash)
		throws com.liferay.portal.NoSuchResourceBlockException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource blocks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource blocks where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_G_N(long companyId, long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the number of matching resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_G_N_P(long companyId, long groupId,
		java.lang.String name, java.lang.String permissionsHash)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource blocks.
	*
	* @return the number of resource blocks
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}