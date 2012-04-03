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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

/**
 * The persistence interface for the d d m structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructurePersistenceImpl
 * @see DDMStructureUtil
 * @generated
 */
public interface DDMStructurePersistence extends BasePersistence<DDMStructure> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureUtil} to access the d d m structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d d m structure in the entity cache if it is enabled.
	*
	* @param ddmStructure the d d m structure
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure);

	/**
	* Caches the d d m structures in the entity cache if it is enabled.
	*
	* @param ddmStructures the d d m structures
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures);

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure create(
		long structureId);

	/**
	* Removes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure remove(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m structure with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureException} if it could not be found.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByPrimaryKey(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structure with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure, or <code>null</code> if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchByPrimaryKey(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the primary key of the current d d m structure
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure[] findByUuid_PrevAndNext(
		long structureId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure[] findByGroupId_PrevAndNext(
		long structureId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByGroupId(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByGroupId(
		long[] groupIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure[] filterFindByGroupId_PrevAndNext(
		long structureId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByGroupId(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByGroupId(
		long[] groupIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m structures where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m structure in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the primary key of the current d d m structure
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure[] findByClassNameId_PrevAndNext(
		long structureId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structure where groupId = &#63; and structureKey = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureException} if it could not be found.
	*
	* @param groupId the group ID
	* @param structureKey the structure key
	* @return the matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByG_S(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structure where groupId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param structureKey the structure key
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchByG_S(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m structure where groupId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param structureKey the structure key
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchByG_S(
		long groupId, java.lang.String structureKey, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByG_N_D_First(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a matching d d m structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure findByG_N_D_Last(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure[] findByG_N_D_PrevAndNext(
		long structureId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure[] filterFindByG_N_D_PrevAndNext(
		long structureId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Returns all the d d m structures.
	*
	* @return the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m structures where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d m structure where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m structure that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Removes all the d d m structures where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m structures where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d m structure where groupId = &#63; and structureKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param structureKey the structure key
	* @return the d d m structure that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure removeByG_S(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;

	/**
	* Removes all the d d m structures where groupId = &#63; and name = &#63; and description = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m structures from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures where groupId = &#63; and structureKey = &#63;.
	*
	* @param groupId the group ID
	* @param structureKey the structure key
	* @return the number of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_S(long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching d d m structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures.
	*
	* @return the number of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}