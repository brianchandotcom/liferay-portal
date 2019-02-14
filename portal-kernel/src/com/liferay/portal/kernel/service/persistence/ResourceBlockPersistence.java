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

import com.liferay.portal.kernel.exception.NoSuchResourceBlockException;
import com.liferay.portal.kernel.model.ResourceBlock;

/**
 * The persistence interface for the resource block service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockUtil
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public interface ResourceBlockPersistence extends BasePersistence<ResourceBlock> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceBlockUtil} to access the resource block persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_N(long companyId, String name);

	/**
	* Returns a range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_N(long companyId, String name,
		int start, int end);

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_N(long companyId, String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator);

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_N(long companyId, String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public ResourceBlock findByC_N_First(long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException;

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public ResourceBlock fetchByC_N_First(long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator);

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public ResourceBlock findByC_N_Last(long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException;

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public ResourceBlock fetchByC_N_Last(long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator);

	/**
	* Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param resourceBlockId the primary key of the current resource block
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public ResourceBlock[] findByC_N_PrevAndNext(long resourceBlockId,
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException;

	/**
	* Removes all the resource blocks where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	*/
	public void removeByC_N(long companyId, String name);

	/**
	* Returns the number of resource blocks where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching resource blocks
	*/
	public int countByC_N(long companyId, String name);

	/**
	* Returns all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @return the matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_G_N(long companyId,
		long groupId, String name);

	/**
	* Returns a range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_G_N(long companyId,
		long groupId, String name, int start, int end);

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_G_N(long companyId,
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator);

	/**
	* Returns an ordered range of all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource blocks
	*/
	public java.util.List<ResourceBlock> findByC_G_N(long companyId,
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public ResourceBlock findByC_G_N_First(long companyId, long groupId,
		String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException;

	/**
	* Returns the first resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public ResourceBlock fetchByC_G_N_First(long companyId, long groupId,
		String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator);

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public ResourceBlock findByC_G_N_Last(long companyId, long groupId,
		String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException;

	/**
	* Returns the last resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public ResourceBlock fetchByC_G_N_Last(long companyId, long groupId,
		String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator);

	/**
	* Returns the resource blocks before and after the current resource block in the ordered set where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param resourceBlockId the primary key of the current resource block
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public ResourceBlock[] findByC_G_N_PrevAndNext(long resourceBlockId,
		long companyId, long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator)
		throws NoSuchResourceBlockException;

	/**
	* Removes all the resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByC_G_N(long companyId, long groupId, String name);

	/**
	* Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching resource blocks
	*/
	public int countByC_G_N(long companyId, long groupId, String name);

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or throws a <code>NoSuchResourceBlockException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the matching resource block
	* @throws NoSuchResourceBlockException if a matching resource block could not be found
	*/
	public ResourceBlock findByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash)
		throws NoSuchResourceBlockException;

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public ResourceBlock fetchByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash);

	/**
	* Returns the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching resource block, or <code>null</code> if a matching resource block could not be found
	*/
	public ResourceBlock fetchByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash, boolean retrieveFromCache);

	/**
	* Removes the resource block where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the resource block that was removed
	*/
	public ResourceBlock removeByC_G_N_P(long companyId, long groupId,
		String name, String permissionsHash)
		throws NoSuchResourceBlockException;

	/**
	* Returns the number of resource blocks where companyId = &#63; and groupId = &#63; and name = &#63; and permissionsHash = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param permissionsHash the permissions hash
	* @return the number of matching resource blocks
	*/
	public int countByC_G_N_P(long companyId, long groupId, String name,
		String permissionsHash);

	/**
	* Caches the resource block in the entity cache if it is enabled.
	*
	* @param resourceBlock the resource block
	*/
	public void cacheResult(ResourceBlock resourceBlock);

	/**
	* Caches the resource blocks in the entity cache if it is enabled.
	*
	* @param resourceBlocks the resource blocks
	*/
	public void cacheResult(java.util.List<ResourceBlock> resourceBlocks);

	/**
	* Creates a new resource block with the primary key. Does not add the resource block to the database.
	*
	* @param resourceBlockId the primary key for the new resource block
	* @return the new resource block
	*/
	public ResourceBlock create(long resourceBlockId);

	/**
	* Removes the resource block with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block that was removed
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public ResourceBlock remove(long resourceBlockId)
		throws NoSuchResourceBlockException;

	public ResourceBlock updateImpl(ResourceBlock resourceBlock);

	/**
	* Returns the resource block with the primary key or throws a <code>NoSuchResourceBlockException</code> if it could not be found.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block
	* @throws NoSuchResourceBlockException if a resource block with the primary key could not be found
	*/
	public ResourceBlock findByPrimaryKey(long resourceBlockId)
		throws NoSuchResourceBlockException;

	/**
	* Returns the resource block with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockId the primary key of the resource block
	* @return the resource block, or <code>null</code> if a resource block with the primary key could not be found
	*/
	public ResourceBlock fetchByPrimaryKey(long resourceBlockId);

	/**
	* Returns all the resource blocks.
	*
	* @return the resource blocks
	*/
	public java.util.List<ResourceBlock> findAll();

	/**
	* Returns a range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @return the range of resource blocks
	*/
	public java.util.List<ResourceBlock> findAll(int start, int end);

	/**
	* Returns an ordered range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource blocks
	*/
	public java.util.List<ResourceBlock> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator);

	/**
	* Returns an ordered range of all the resource blocks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceBlockModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource blocks
	* @param end the upper bound of the range of resource blocks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of resource blocks
	*/
	public java.util.List<ResourceBlock> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlock> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the resource blocks from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of resource blocks.
	*
	* @return the number of resource blocks
	*/
	public int countAll();
}