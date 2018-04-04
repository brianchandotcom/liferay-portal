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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the cp instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPInstancePersistenceImpl
 * @see CPInstanceUtil
 * @generated
 */
@ProviderType
public interface CPInstancePersistence extends BasePersistence<CPInstance> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPInstanceUtil} to access the cp instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the cp instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where uuid = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByUuid_PrevAndNext(long CPInstanceId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp instances
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp instance where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPInstanceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPInstanceException;

	/**
	* Returns the cp instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp instance where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp instance that was removed
	*/
	public CPInstance removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPInstanceException;

	/**
	* Returns the number of cp instances where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp instances
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the cp instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByUuid_C_PrevAndNext(long CPInstanceId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp instances
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cp instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByGroupId(long groupId);

	/**
	* Returns a range of all the cp instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the cp instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where groupId = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByGroupId_PrevAndNext(long CPInstanceId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp instances
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the cp instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByCompanyId(long companyId);

	/**
	* Returns a range of all the cp instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where companyId = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByCompanyId_PrevAndNext(long CPInstanceId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of cp instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp instances
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the cp instances where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns a range of all the cp instances where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByCPDefinitionId(
		long CPDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByCPDefinitionId_First(long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByCPDefinitionId_First(long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByCPDefinitionId_Last(long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByCPDefinitionId_Last(long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByCPDefinitionId_PrevAndNext(long CPInstanceId,
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public void removeByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the number of cp instances where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp instances
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns all the cp instances where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByG_ST(long groupId, int status);

	/**
	* Returns a range of all the cp instances where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByG_ST(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the cp instances where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByG_ST(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByG_ST(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByG_ST_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByG_ST_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByG_ST_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByG_ST_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByG_ST_PrevAndNext(long CPInstanceId, long groupId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_ST(long groupId, int status);

	/**
	* Returns the number of cp instances where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching cp instances
	*/
	public int countByG_ST(long groupId, int status);

	/**
	* Returns all the cp instances where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByG_NotST(long groupId, int status);

	/**
	* Returns a range of all the cp instances where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByG_NotST(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the cp instances where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByG_NotST(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByG_NotST(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByG_NotST_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByG_NotST_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByG_NotST_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByG_NotST_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByG_NotST_PrevAndNext(long CPInstanceId,
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where groupId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_NotST(long groupId, int status);

	/**
	* Returns the number of cp instances where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching cp instances
	*/
	public int countByG_NotST(long groupId, int status);

	/**
	* Returns the cp instance where CPDefinitionId = &#63; and sku = &#63; or throws a {@link NoSuchCPInstanceException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @param sku the sku
	* @return the matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByC_S(long CPDefinitionId, java.lang.String sku)
		throws NoSuchCPInstanceException;

	/**
	* Returns the cp instance where CPDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param sku the sku
	* @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_S(long CPDefinitionId, java.lang.String sku);

	/**
	* Returns the cp instance where CPDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param sku the sku
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_S(long CPDefinitionId, java.lang.String sku,
		boolean retrieveFromCache);

	/**
	* Removes the cp instance where CPDefinitionId = &#63; and sku = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param sku the sku
	* @return the cp instance that was removed
	*/
	public CPInstance removeByC_S(long CPDefinitionId, java.lang.String sku)
		throws NoSuchCPInstanceException;

	/**
	* Returns the number of cp instances where CPDefinitionId = &#63; and sku = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param sku the sku
	* @return the number of matching cp instances
	*/
	public int countByC_S(long CPDefinitionId, java.lang.String sku);

	/**
	* Returns all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByC_ST(long CPDefinitionId, int status);

	/**
	* Returns a range of all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_ST(long CPDefinitionId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_ST(long CPDefinitionId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_ST(long CPDefinitionId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByC_ST_First(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_ST_First(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByC_ST_Last(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_ST_Last(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByC_ST_PrevAndNext(long CPInstanceId,
		long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where CPDefinitionId = &#63; and status = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	*/
	public void removeByC_ST(long CPDefinitionId, int status);

	/**
	* Returns the number of cp instances where CPDefinitionId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @return the number of matching cp instances
	*/
	public int countByC_ST(long CPDefinitionId, int status);

	/**
	* Returns all the cp instances where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByC_NotST(long CPDefinitionId,
		int status);

	/**
	* Returns a range of all the cp instances where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_NotST(long CPDefinitionId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_NotST(long CPDefinitionId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_NotST(long CPDefinitionId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByC_NotST_First(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_NotST_First(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByC_NotST_Last(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_NotST_Last(long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByC_NotST_PrevAndNext(long CPInstanceId,
		long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where CPDefinitionId = &#63; and status &ne; &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	*/
	public void removeByC_NotST(long CPDefinitionId, int status);

	/**
	* Returns the number of cp instances where CPDefinitionId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param status the status
	* @return the number of matching cp instances
	*/
	public int countByC_NotST(long CPDefinitionId, int status);

	/**
	* Returns all the cp instances where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByLtD_S(Date displayDate, int status);

	/**
	* Returns a range of all the cp instances where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByLtD_S(Date displayDate, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the cp instances where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByLtD_S(Date displayDate, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByLtD_S(Date displayDate, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByLtD_S_PrevAndNext(long CPInstanceId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByLtD_S(Date displayDate, int status);

	/**
	* Returns the number of cp instances where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching cp instances
	*/
	public int countByLtD_S(Date displayDate, int status);

	/**
	* Returns all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching cp instances
	*/
	public java.util.List<CPInstance> findByC_LtD_S(long CPDefinitionId,
		Date displayDate, int status);

	/**
	* Returns a range of all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_LtD_S(long CPDefinitionId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_LtD_S(long CPDefinitionId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp instances
	*/
	public java.util.List<CPInstance> findByC_LtD_S(long CPDefinitionId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByC_LtD_S_First(long CPDefinitionId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_LtD_S_First(long CPDefinitionId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance
	* @throws NoSuchCPInstanceException if a matching cp instance could not be found
	*/
	public CPInstance findByC_LtD_S_Last(long CPDefinitionId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	public CPInstance fetchByC_LtD_S_Last(long CPDefinitionId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the cp instances before and after the current cp instance in the ordered set where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPInstanceId the primary key of the current cp instance
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance[] findByC_LtD_S_PrevAndNext(long CPInstanceId,
		long CPDefinitionId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	* Removes all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByC_LtD_S(long CPDefinitionId, Date displayDate,
		int status);

	/**
	* Returns the number of cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching cp instances
	*/
	public int countByC_LtD_S(long CPDefinitionId, Date displayDate, int status);

	/**
	* Caches the cp instance in the entity cache if it is enabled.
	*
	* @param cpInstance the cp instance
	*/
	public void cacheResult(CPInstance cpInstance);

	/**
	* Caches the cp instances in the entity cache if it is enabled.
	*
	* @param cpInstances the cp instances
	*/
	public void cacheResult(java.util.List<CPInstance> cpInstances);

	/**
	* Creates a new cp instance with the primary key. Does not add the cp instance to the database.
	*
	* @param CPInstanceId the primary key for the new cp instance
	* @return the new cp instance
	*/
	public CPInstance create(long CPInstanceId);

	/**
	* Removes the cp instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPInstanceId the primary key of the cp instance
	* @return the cp instance that was removed
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance remove(long CPInstanceId)
		throws NoSuchCPInstanceException;

	public CPInstance updateImpl(CPInstance cpInstance);

	/**
	* Returns the cp instance with the primary key or throws a {@link NoSuchCPInstanceException} if it could not be found.
	*
	* @param CPInstanceId the primary key of the cp instance
	* @return the cp instance
	* @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	*/
	public CPInstance findByPrimaryKey(long CPInstanceId)
		throws NoSuchCPInstanceException;

	/**
	* Returns the cp instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPInstanceId the primary key of the cp instance
	* @return the cp instance, or <code>null</code> if a cp instance with the primary key could not be found
	*/
	public CPInstance fetchByPrimaryKey(long CPInstanceId);

	@Override
	public java.util.Map<java.io.Serializable, CPInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp instances.
	*
	* @return the cp instances
	*/
	public java.util.List<CPInstance> findAll();

	/**
	* Returns a range of all the cp instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of cp instances
	*/
	public java.util.List<CPInstance> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp instances
	*/
	public java.util.List<CPInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns an ordered range of all the cp instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp instances
	*/
	public java.util.List<CPInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp instances from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp instances.
	*
	* @return the number of cp instances
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}