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

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructurePersistenceImpl
 * @see DDMStructureUtil
 * @generated
 */
@ProviderType
public interface DDMStructurePersistence extends BasePersistence<DDMStructure> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureUtil} to access the d d m structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the d d m structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByUuid_PrevAndNext(long structureId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of d d m structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m structures
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchStructureException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchStructureException;

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the d d m structure where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m structure that was removed
	*/
	public DDMStructure removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchStructureException;

	/**
	* Returns the number of d d m structures where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m structures
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByUuid_C_PrevAndNext(long structureId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m structures
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the d d m structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long groupId);

	/**
	* Returns a range of all the d d m structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByGroupId_PrevAndNext(long structureId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] filterFindByGroupId_PrevAndNext(long structureId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByGroupId(long[] groupIds);

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByGroupId(long[] groupIds,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long[] groupIds);

	/**
	* Returns a range of all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long[] groupIds,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m structures where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of d d m structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m structures
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of d d m structures where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m structures
	*/
	public int countByGroupId(long[] groupIds);

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public int filterCountByGroupId(long[] groupIds);

	/**
	* Returns all the d d m structures where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByParentStructureId(
		long parentStructureId);

	/**
	* Returns a range of all the d d m structures where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByParentStructureId(
		long parentStructureId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByParentStructureId(
		long parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByParentStructureId(
		long parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByParentStructureId_First(long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByParentStructureId_First(long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByParentStructureId_Last(long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByParentStructureId_Last(long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByParentStructureId_PrevAndNext(
		long structureId, long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where parentStructureId = &#63; from the database.
	*
	* @param parentStructureId the parent structure ID
	*/
	public void removeByParentStructureId(long parentStructureId);

	/**
	* Returns the number of d d m structures where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @return the number of matching d d m structures
	*/
	public int countByParentStructureId(long parentStructureId);

	/**
	* Returns all the d d m structures where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByClassNameId(long classNameId);

	/**
	* Returns a range of all the d d m structures where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByClassNameId(long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByClassNameId_PrevAndNext(long structureId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public void removeByClassNameId(long classNameId);

	/**
	* Returns the number of d d m structures where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public int countByClassNameId(long classNameId);

	/**
	* Returns all the d d m structures where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByStructureKey(
		java.lang.String structureKey);

	/**
	* Returns a range of all the d d m structures where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureKey the structure key
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByStructureKey(
		java.lang.String structureKey, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureKey the structure key
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByStructureKey(
		java.lang.String structureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureKey the structure key
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByStructureKey(
		java.lang.String structureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByStructureKey_First(
		java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByStructureKey_First(
		java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByStructureKey_Last(java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByStructureKey_Last(
		java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByStructureKey_PrevAndNext(long structureId,
		java.lang.String structureKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where structureKey = &#63; from the database.
	*
	* @param structureKey the structure key
	*/
	public void removeByStructureKey(java.lang.String structureKey);

	/**
	* Returns the number of d d m structures where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @return the number of matching d d m structures
	*/
	public int countByStructureKey(java.lang.String structureKey);

	/**
	* Returns all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_P(long groupId,
		long parentStructureId);

	/**
	* Returns a range of all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_P(long groupId,
		long parentStructureId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_P(long groupId,
		long parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_P(long groupId,
		long parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByG_P_First(long groupId, long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_P_First(long groupId, long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByG_P_Last(long groupId, long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_P_Last(long groupId, long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByG_P_PrevAndNext(long structureId, long groupId,
		long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId);

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] filterFindByG_P_PrevAndNext(long structureId,
		long groupId, long parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where groupId = &#63; and parentStructureId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	*/
	public void removeByG_P(long groupId, long parentStructureId);

	/**
	* Returns the number of d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the number of matching d d m structures
	*/
	public int countByG_P(long groupId, long parentStructureId);

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long parentStructureId);

	/**
	* Returns all the d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long groupId, long classNameId);

	/**
	* Returns a range of all the d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long groupId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByG_C_PrevAndNext(long structureId, long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_C(long groupId,
		long classNameId);

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_C(long groupId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] filterFindByG_C_PrevAndNext(long structureId,
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId);

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long[] groupIds,
		long classNameId);

	/**
	* Returns a range of all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long[] groupIds,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long[] groupIds,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and classNameId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_C(long[] groupIds,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m structures where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns the number of d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public int countByG_C(long[] groupIds, long classNameId);

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public int filterCountByG_C(long groupId, long classNameId);

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public int filterCountByG_C(long[] groupIds, long classNameId);

	/**
	* Returns all the d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByC_C(long companyId,
		long classNameId);

	/**
	* Returns a range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByC_C(long companyId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByC_C(long companyId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByC_C(long companyId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByC_C_First(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByC_C_Last(long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByC_C_PrevAndNext(long structureId,
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public void removeByC_C(long companyId, long classNameId);

	/**
	* Returns the number of d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public int countByC_C(long companyId, long classNameId);

	/**
	* Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or throws a {@link NoSuchStructureException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey) throws NoSuchStructureException;

	/**
	* Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey);

	/**
	* Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey, boolean retrieveFromCache);

	/**
	* Removes the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the d d m structure that was removed
	*/
	public DDMStructure removeByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey) throws NoSuchStructureException;

	/**
	* Returns the number of d d m structures where groupId = &#63; and classNameId = &#63; and structureKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the number of matching d d m structures
	*/
	public int countByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey);

	/**
	* Returns all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_N_D(long groupId,
		java.lang.String name, java.lang.String description);

	/**
	* Returns a range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structures
	*/
	public java.util.List<DDMStructure> findByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByG_N_D_First(long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_N_D_First(long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public DDMStructure findByG_N_D_Last(long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public DDMStructure fetchByG_N_D_Last(long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] findByG_N_D_PrevAndNext(long structureId,
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_N_D(long groupId,
		java.lang.String name, java.lang.String description);

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start, int end);

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public java.util.List<DDMStructure> filterFindByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure[] filterFindByG_N_D_PrevAndNext(long structureId,
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException;

	/**
	* Removes all the d d m structures where groupId = &#63; and name = &#63; and description = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	*/
	public void removeByG_N_D(long groupId, java.lang.String name,
		java.lang.String description);

	/**
	* Returns the number of d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching d d m structures
	*/
	public int countByG_N_D(long groupId, java.lang.String name,
		java.lang.String description);

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public int filterCountByG_N_D(long groupId, java.lang.String name,
		java.lang.String description);

	/**
	* Caches the d d m structure in the entity cache if it is enabled.
	*
	* @param ddmStructure the d d m structure
	*/
	public void cacheResult(DDMStructure ddmStructure);

	/**
	* Caches the d d m structures in the entity cache if it is enabled.
	*
	* @param ddmStructures the d d m structures
	*/
	public void cacheResult(java.util.List<DDMStructure> ddmStructures);

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public DDMStructure create(long structureId);

	/**
	* Removes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure that was removed
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure remove(long structureId)
		throws NoSuchStructureException;

	public DDMStructure updateImpl(DDMStructure ddmStructure);

	/**
	* Returns the d d m structure with the primary key or throws a {@link NoSuchStructureException} if it could not be found.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public DDMStructure findByPrimaryKey(long structureId)
		throws NoSuchStructureException;

	/**
	* Returns the d d m structure with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure, or <code>null</code> if a d d m structure with the primary key could not be found
	*/
	public DDMStructure fetchByPrimaryKey(long structureId);

	@Override
	public java.util.Map<java.io.Serializable, DDMStructure> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m structures.
	*
	* @return the d d m structures
	*/
	public java.util.List<DDMStructure> findAll();

	/**
	* Returns a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of d d m structures
	*/
	public java.util.List<DDMStructure> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structures
	*/
	public java.util.List<DDMStructure> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m structures
	*/
	public java.util.List<DDMStructure> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructure> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m structures from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m structures.
	*
	* @return the number of d d m structures
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}