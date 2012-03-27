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

import com.liferay.portal.model.Resource;

/**
 * The persistence interface for the resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePersistenceImpl
 * @see ResourceUtil
 * @generated
 */
public interface ResourcePersistence extends BasePersistence<Resource> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceUtil} to access the resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the resource in the entity cache if it is enabled.
	*
	* @param resource the resource
	*/
	public void cacheResult(com.liferay.portal.model.Resource resource);

	/**
	* Caches the resources in the entity cache if it is enabled.
	*
	* @param resources the resources
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Resource> resources);

	/**
	* Creates a new resource with the primary key. Does not add the resource to the database.
	*
	* @param resourceId the primary key for the new resource
	* @return the new resource
	*/
	public com.liferay.portal.model.Resource create(long resourceId);

	/**
	* Removes the resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceId the primary key of the resource
	* @return the resource that was removed
	* @throws com.liferay.portal.NoSuchResourceException if a resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource remove(long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource updateImpl(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource with the primary key or throws a {@link com.liferay.portal.NoSuchResourceException} if it could not be found.
	*
	* @param resourceId the primary key of the resource
	* @return the resource
	* @throws com.liferay.portal.NoSuchResourceException if a resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource findByPrimaryKey(long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceId the primary key of the resource
	* @return the resource, or <code>null</code> if a resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource fetchByPrimaryKey(long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resources where codeId = &#63;.
	*
	* @param codeId the code ID
	* @return the matching resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resources where codeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param codeId the code ID
	* @param start the lower bound of the range of resources
	* @param end the upper bound of the range of resources (not inclusive)
	* @return the range of matching resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resources where codeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param codeId the code ID
	* @param start the lower bound of the range of resources
	* @param end the upper bound of the range of resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource in the ordered set where codeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param codeId the code ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource
	* @throws com.liferay.portal.NoSuchResourceException if a matching resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource findByCodeId_First(long codeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource in the ordered set where codeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param codeId the code ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource
	* @throws com.liferay.portal.NoSuchResourceException if a matching resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource findByCodeId_Last(long codeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resources before and after the current resource in the ordered set where codeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceId the primary key of the current resource
	* @param codeId the code ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource
	* @throws com.liferay.portal.NoSuchResourceException if a resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource[] findByCodeId_PrevAndNext(
		long resourceId, long codeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource where codeId = &#63; and primKey = &#63; or throws a {@link com.liferay.portal.NoSuchResourceException} if it could not be found.
	*
	* @param codeId the code ID
	* @param primKey the prim key
	* @return the matching resource
	* @throws com.liferay.portal.NoSuchResourceException if a matching resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource findByC_P(long codeId,
		java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource where codeId = &#63; and primKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param codeId the code ID
	* @param primKey the prim key
	* @return the matching resource, or <code>null</code> if a matching resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource where codeId = &#63; and primKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param codeId the code ID
	* @param primKey the prim key
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource, or <code>null</code> if a matching resource could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resources.
	*
	* @return the resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Resource> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resources
	* @param end the upper bound of the range of resources (not inclusive)
	* @return the range of resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resources
	* @param end the upper bound of the range of resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resources where codeId = &#63; from the database.
	*
	* @param codeId the code ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCodeId(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the resource where codeId = &#63; and primKey = &#63; from the database.
	*
	* @param codeId the code ID
	* @param primKey the prim key
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resources from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resources where codeId = &#63;.
	*
	* @param codeId the code ID
	* @return the number of matching resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByCodeId(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resources where codeId = &#63; and primKey = &#63;.
	*
	* @param codeId the code ID
	* @param primKey the prim key
	* @return the number of matching resources
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resources.
	*
	* @return the number of resources
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}