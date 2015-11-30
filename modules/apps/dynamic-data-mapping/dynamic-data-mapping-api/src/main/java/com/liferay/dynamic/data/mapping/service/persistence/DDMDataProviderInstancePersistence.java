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

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m data provider instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMDataProviderInstancePersistenceImpl
 * @see DDMDataProviderInstanceUtil
 * @generated
 */
@ProviderType
public interface DDMDataProviderInstancePersistence extends BasePersistence<DDMDataProviderInstance> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderInstanceUtil} to access the d d m data provider instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m data provider instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the d d m data provider instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data provider instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the first d d m data provider instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the last d d m data provider instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the last d d m data provider instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where uuid = &#63;.
	*
	* @param dataProviderInstanceId the primary key of the current d d m data provider instance
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance[] findByUuid_PrevAndNext(
		long dataProviderInstanceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Removes all the d d m data provider instances where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of d d m data provider instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m data provider instances
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the d d m data provider instance where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the d d m data provider instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the d d m data provider instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the d d m data provider instance where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m data provider instance that was removed
	*/
	public DDMDataProviderInstance removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the number of d d m data provider instances where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m data provider instances
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the first d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the last d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the last d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param dataProviderInstanceId the primary key of the current d d m data provider instance
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance[] findByUuid_C_PrevAndNext(
		long dataProviderInstanceId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Removes all the d d m data provider instances where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of d d m data provider instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m data provider instances
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the d d m data provider instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(long groupId);

	/**
	* Returns a range of all the d d m data provider instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data provider instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the first d d m data provider instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the last d d m data provider instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the last d d m data provider instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where groupId = &#63;.
	*
	* @param dataProviderInstanceId the primary key of the current d d m data provider instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance[] findByGroupId_PrevAndNext(
		long dataProviderInstanceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns all the d d m data provider instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m data provider instances that the user has permission to view
	*/
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the d d m data provider instances that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of matching d d m data provider instances that the user has permission to view
	*/
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data provider instances that the user has permission to view
	*/
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set of d d m data provider instances that the user has permission to view where groupId = &#63;.
	*
	* @param dataProviderInstanceId the primary key of the current d d m data provider instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance[] filterFindByGroupId_PrevAndNext(
		long dataProviderInstanceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns all the d d m data provider instances that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching d d m data provider instances that the user has permission to view
	*/
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long[] groupIds);

	/**
	* Returns a range of all the d d m data provider instances that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of matching d d m data provider instances that the user has permission to view
	*/
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data provider instances that the user has permission to view
	*/
	public java.util.List<DDMDataProviderInstance> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns all the d d m data provider instances where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds);

	/**
	* Returns a range of all the d d m data provider instances where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data provider instances where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m data provider instances where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of d d m data provider instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m data provider instances
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of d d m data provider instances where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m data provider instances
	*/
	public int countByGroupId(long[] groupIds);

	/**
	* Returns the number of d d m data provider instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m data provider instances that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the number of d d m data provider instances that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m data provider instances that the user has permission to view
	*/
	public int filterCountByGroupId(long[] groupIds);

	/**
	* Returns all the d d m data provider instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the d d m data provider instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data provider instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the first d d m data provider instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the last d d m data provider instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the last d d m data provider instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public DDMDataProviderInstance fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where companyId = &#63;.
	*
	* @param dataProviderInstanceId the primary key of the current d d m data provider instance
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance[] findByCompanyId_PrevAndNext(
		long dataProviderInstanceId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Removes all the d d m data provider instances where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of d d m data provider instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching d d m data provider instances
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the d d m data provider instance in the entity cache if it is enabled.
	*
	* @param ddmDataProviderInstance the d d m data provider instance
	*/
	public void cacheResult(DDMDataProviderInstance ddmDataProviderInstance);

	/**
	* Caches the d d m data provider instances in the entity cache if it is enabled.
	*
	* @param ddmDataProviderInstances the d d m data provider instances
	*/
	public void cacheResult(
		java.util.List<DDMDataProviderInstance> ddmDataProviderInstances);

	/**
	* Creates a new d d m data provider instance with the primary key. Does not add the d d m data provider instance to the database.
	*
	* @param dataProviderInstanceId the primary key for the new d d m data provider instance
	* @return the new d d m data provider instance
	*/
	public DDMDataProviderInstance create(long dataProviderInstanceId);

	/**
	* Removes the d d m data provider instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderInstanceId the primary key of the d d m data provider instance
	* @return the d d m data provider instance that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance remove(long dataProviderInstanceId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	public DDMDataProviderInstance updateImpl(
		DDMDataProviderInstance ddmDataProviderInstance);

	/**
	* Returns the d d m data provider instance with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException} if it could not be found.
	*
	* @param dataProviderInstanceId the primary key of the d d m data provider instance
	* @return the d d m data provider instance
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance findByPrimaryKey(long dataProviderInstanceId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;

	/**
	* Returns the d d m data provider instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param dataProviderInstanceId the primary key of the d d m data provider instance
	* @return the d d m data provider instance, or <code>null</code> if a d d m data provider instance with the primary key could not be found
	*/
	public DDMDataProviderInstance fetchByPrimaryKey(
		long dataProviderInstanceId);

	@Override
	public java.util.Map<java.io.Serializable, DDMDataProviderInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m data provider instances.
	*
	* @return the d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findAll();

	/**
	* Returns a range of all the d d m data provider instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m data provider instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data provider instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m data provider instances
	*/
	public java.util.List<DDMDataProviderInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m data provider instances from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m data provider instances.
	*
	* @return the number of d d m data provider instances
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}