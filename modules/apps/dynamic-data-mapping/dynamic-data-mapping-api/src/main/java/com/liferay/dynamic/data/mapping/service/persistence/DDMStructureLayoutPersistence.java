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

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m structure layout service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureLayoutPersistenceImpl
 * @see DDMStructureLayoutUtil
 * @generated
 */
@ProviderType
public interface DDMStructureLayoutPersistence extends BasePersistence<DDMStructureLayout> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureLayoutUtil} to access the d d m structure layout persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m structure layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the d d m structure layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @return the range of matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout
	* @throws NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator);

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout
	* @throws NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator);

	/**
	* Returns the d d m structure layouts before and after the current d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param structureLayoutId the primary key of the current d d m structure layout
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure layout
	* @throws NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public DDMStructureLayout[] findByUuid_PrevAndNext(long structureLayoutId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	* Removes all the d d m structure layouts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of d d m structure layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m structure layouts
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchStructureLayoutException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure layout
	* @throws NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the d d m structure layout where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m structure layout that was removed
	*/
	public DDMStructureLayout removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the number of d d m structure layouts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m structure layouts
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @return the range of matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout
	* @throws NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator);

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout
	* @throws NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator);

	/**
	* Returns the d d m structure layouts before and after the current d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param structureLayoutId the primary key of the current d d m structure layout
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure layout
	* @throws NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public DDMStructureLayout[] findByUuid_C_PrevAndNext(
		long structureLayoutId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	* Removes all the d d m structure layouts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m structure layouts
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the d d m structure layout where structureVersionId = &#63; or throws a {@link NoSuchStructureLayoutException} if it could not be found.
	*
	* @param structureVersionId the structure version ID
	* @return the matching d d m structure layout
	* @throws NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout findByStructureVersionId(long structureVersionId)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the d d m structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param structureVersionId the structure version ID
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByStructureVersionId(long structureVersionId);

	/**
	* Returns the d d m structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param structureVersionId the structure version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId, boolean retrieveFromCache);

	/**
	* Removes the d d m structure layout where structureVersionId = &#63; from the database.
	*
	* @param structureVersionId the structure version ID
	* @return the d d m structure layout that was removed
	*/
	public DDMStructureLayout removeByStructureVersionId(
		long structureVersionId) throws NoSuchStructureLayoutException;

	/**
	* Returns the number of d d m structure layouts where structureVersionId = &#63;.
	*
	* @param structureVersionId the structure version ID
	* @return the number of matching d d m structure layouts
	*/
	public int countByStructureVersionId(long structureVersionId);

	/**
	* Caches the d d m structure layout in the entity cache if it is enabled.
	*
	* @param ddmStructureLayout the d d m structure layout
	*/
	public void cacheResult(DDMStructureLayout ddmStructureLayout);

	/**
	* Caches the d d m structure layouts in the entity cache if it is enabled.
	*
	* @param ddmStructureLayouts the d d m structure layouts
	*/
	public void cacheResult(
		java.util.List<DDMStructureLayout> ddmStructureLayouts);

	/**
	* Creates a new d d m structure layout with the primary key. Does not add the d d m structure layout to the database.
	*
	* @param structureLayoutId the primary key for the new d d m structure layout
	* @return the new d d m structure layout
	*/
	public DDMStructureLayout create(long structureLayoutId);

	/**
	* Removes the d d m structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLayoutId the primary key of the d d m structure layout
	* @return the d d m structure layout that was removed
	* @throws NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public DDMStructureLayout remove(long structureLayoutId)
		throws NoSuchStructureLayoutException;

	public DDMStructureLayout updateImpl(DDMStructureLayout ddmStructureLayout);

	/**
	* Returns the d d m structure layout with the primary key or throws a {@link NoSuchStructureLayoutException} if it could not be found.
	*
	* @param structureLayoutId the primary key of the d d m structure layout
	* @return the d d m structure layout
	* @throws NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public DDMStructureLayout findByPrimaryKey(long structureLayoutId)
		throws NoSuchStructureLayoutException;

	/**
	* Returns the d d m structure layout with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureLayoutId the primary key of the d d m structure layout
	* @return the d d m structure layout, or <code>null</code> if a d d m structure layout with the primary key could not be found
	*/
	public DDMStructureLayout fetchByPrimaryKey(long structureLayoutId);

	@Override
	public java.util.Map<java.io.Serializable, DDMStructureLayout> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m structure layouts.
	*
	* @return the d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findAll();

	/**
	* Returns a range of all the d d m structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @return the range of d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m structure layouts
	*/
	public java.util.List<DDMStructureLayout> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m structure layouts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m structure layouts.
	*
	* @return the number of d d m structure layouts
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}