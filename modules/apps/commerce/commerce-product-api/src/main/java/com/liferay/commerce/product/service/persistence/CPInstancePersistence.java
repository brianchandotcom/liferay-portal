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

import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceUtil
 * @generated
 */
@ProviderType
public interface CPInstancePersistence
	extends BasePersistence<CPInstance>, CTPersistence<CPInstance> {

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
	public java.util.List<CPInstance> findByUuid(String uuid);

	/**
	 * Returns a range of all the cp instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the cp instances before and after the current cp instance in the ordered set where uuid = &#63;.
	 *
	 * @param CPInstanceId the primary key of the current cp instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance
	 * @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	 */
	public CPInstance[] findByUuid_PrevAndNext(
			long CPInstanceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Removes all the cp instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cp instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp instances
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the cp instance where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPInstanceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByUUID_G(String uuid, long groupId)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the cp instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the cp instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the cp instance where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp instance that was removed
	 */
	public CPInstance removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the number of cp instances where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp instances
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the cp instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp instances
	 */
	public java.util.List<CPInstance> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the cp instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

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
	public CPInstance[] findByUuid_C_PrevAndNext(
			long CPInstanceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Removes all the cp instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cp instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp instances
	 */
	public int countByUuid_C(String uuid, long companyId);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the cp instances before and after the current cp instance in the ordered set where groupId = &#63;.
	 *
	 * @param CPInstanceId the primary key of the current cp instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance
	 * @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	 */
	public CPInstance[] findByGroupId_PrevAndNext(
			long CPInstanceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the cp instances before and after the current cp instance in the ordered set where companyId = &#63;.
	 *
	 * @param CPInstanceId the primary key of the current cp instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance
	 * @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	 */
	public CPInstance[] findByCompanyId_PrevAndNext(
			long CPInstanceId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByCPDefinitionId_First(
			long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByCPDefinitionId_Last(
			long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the cp instances before and after the current cp instance in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPInstanceId the primary key of the current cp instance
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance
	 * @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	 */
	public CPInstance[] findByCPDefinitionId_PrevAndNext(
			long CPInstanceId, long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	 * Returns all the cp instances where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching cp instances
	 */
	public java.util.List<CPInstance> findByCPInstanceUuid(
		String CPInstanceUuid);

	/**
	 * Returns a range of all the cp instances where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByCPInstanceUuid_First(
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCPInstanceUuid_First(
		String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByCPInstanceUuid_Last(
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCPInstanceUuid_Last(
		String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the cp instances before and after the current cp instance in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceId the primary key of the current cp instance
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance
	 * @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	 */
	public CPInstance[] findByCPInstanceUuid_PrevAndNext(
			long CPInstanceId, String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Removes all the cp instances where CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public void removeByCPInstanceUuid(String CPInstanceUuid);

	/**
	 * Returns the number of cp instances where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching cp instances
	 */
	public int countByCPInstanceUuid(String CPInstanceUuid);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByG_ST(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByG_ST(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByG_ST(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByG_ST_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByG_ST_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByG_ST_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByG_ST_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

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
	public CPInstance[] findByG_ST_PrevAndNext(
			long CPInstanceId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	 * Returns all the cp instances where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the matching cp instances
	 */
	public java.util.List<CPInstance> findByC_SKU(long companyId, String sku);

	/**
	 * Returns a range of all the cp instances where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_SKU(
		long companyId, String sku, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_SKU(
		long companyId, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where companyId = &#63; and sku = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_SKU(
		long companyId, String sku, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByC_SKU_First(
			long companyId, String sku,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_SKU_First(
		long companyId, String sku,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByC_SKU_Last(
			long companyId, String sku,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_SKU_Last(
		long companyId, String sku,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the cp instances before and after the current cp instance in the ordered set where companyId = &#63; and sku = &#63;.
	 *
	 * @param CPInstanceId the primary key of the current cp instance
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance
	 * @throws NoSuchCPInstanceException if a cp instance with the primary key could not be found
	 */
	public CPInstance[] findByC_SKU_PrevAndNext(
			long CPInstanceId, long companyId, String sku,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Removes all the cp instances where companyId = &#63; and sku = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 */
	public void removeByC_SKU(long companyId, String sku);

	/**
	 * Returns the number of cp instances where companyId = &#63; and sku = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @return the number of matching cp instances
	 */
	public int countByC_SKU(long companyId, String sku);

	/**
	 * Returns the cp instance where CPDefinitionId = &#63; and CPInstanceUuid = &#63; or throws a <code>NoSuchCPInstanceException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByC_C(long CPDefinitionId, String CPInstanceUuid)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the cp instance where CPDefinitionId = &#63; and CPInstanceUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_C(long CPDefinitionId, String CPInstanceUuid);

	/**
	 * Returns the cp instance where CPDefinitionId = &#63; and CPInstanceUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_C(
		long CPDefinitionId, String CPInstanceUuid, boolean useFinderCache);

	/**
	 * Removes the cp instance where CPDefinitionId = &#63; and CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the cp instance that was removed
	 */
	public CPInstance removeByC_C(long CPDefinitionId, String CPInstanceUuid)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the number of cp instances where CPDefinitionId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching cp instances
	 */
	public int countByC_C(long CPDefinitionId, String CPInstanceUuid);

	/**
	 * Returns the cp instance where CPDefinitionId = &#63; and sku = &#63; or throws a <code>NoSuchCPInstanceException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sku the sku
	 * @return the matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByCPDI_SKU(long CPDefinitionId, String sku)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the cp instance where CPDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sku the sku
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCPDI_SKU(long CPDefinitionId, String sku);

	/**
	 * Returns the cp instance where CPDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sku the sku
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByCPDI_SKU(
		long CPDefinitionId, String sku, boolean useFinderCache);

	/**
	 * Removes the cp instance where CPDefinitionId = &#63; and sku = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sku the sku
	 * @return the cp instance that was removed
	 */
	public CPInstance removeByCPDI_SKU(long CPDefinitionId, String sku)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the number of cp instances where CPDefinitionId = &#63; and sku = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sku the sku
	 * @return the number of matching cp instances
	 */
	public int countByCPDI_SKU(long CPDefinitionId, String sku);

	/**
	 * Returns all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @return the matching cp instances
	 */
	public java.util.List<CPInstance> findByC_ST(
		long CPDefinitionId, int status);

	/**
	 * Returns a range of all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_ST(
		long CPDefinitionId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_ST(
		long CPDefinitionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_ST(
		long CPDefinitionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByC_ST_First(
			long CPDefinitionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_ST_First(
		long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByC_ST_Last(
			long CPDefinitionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where CPDefinitionId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_ST_Last(
		long CPDefinitionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

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
	public CPInstance[] findByC_ST_PrevAndNext(
			long CPInstanceId, long CPDefinitionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the first cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns the last cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the last cp instance in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

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
	public CPInstance[] findByLtD_S_PrevAndNext(
			long CPInstanceId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	public java.util.List<CPInstance> findByC_LtD_S(
		long CPDefinitionId, Date displayDate, int status);

	/**
	 * Returns a range of all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @return the range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_LtD_S(
		long CPDefinitionId, Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
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
	public java.util.List<CPInstance> findByC_LtD_S(
		long CPDefinitionId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instances
	 */
	public java.util.List<CPInstance> findByC_LtD_S(
		long CPDefinitionId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

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
	public CPInstance findByC_LtD_S_First(
			long CPDefinitionId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	public CPInstance fetchByC_LtD_S_First(
		long CPDefinitionId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

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
	public CPInstance findByC_LtD_S_Last(
			long CPDefinitionId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
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
	public CPInstance fetchByC_LtD_S_Last(
		long CPDefinitionId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

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
	public CPInstance[] findByC_LtD_S_PrevAndNext(
			long CPInstanceId, long CPDefinitionId, Date displayDate,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
				orderByComparator)
		throws NoSuchCPInstanceException;

	/**
	 * Removes all the cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByC_LtD_S(
		long CPDefinitionId, Date displayDate, int status);

	/**
	 * Returns the number of cp instances where CPDefinitionId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp instances
	 */
	public int countByC_LtD_S(
		long CPDefinitionId, Date displayDate, int status);

	/**
	 * Returns the cp instance where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCPInstanceException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp instance
	 * @throws NoSuchCPInstanceException if a matching cp instance could not be found
	 */
	public CPInstance findByC_ERC(long companyId, String externalReferenceCode)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the cp instance where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the cp instance where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	 */
	public CPInstance fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the cp instance where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the cp instance that was removed
	 */
	public CPInstance removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPInstanceException;

	/**
	 * Returns the number of cp instances where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching cp instances
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

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
	 * Returns the cp instance with the primary key or throws a <code>NoSuchCPInstanceException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp instances
	 */
	public java.util.List<CPInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instances
	 * @param end the upper bound of the range of cp instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp instances
	 */
	public java.util.List<CPInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPInstance>
			orderByComparator,
		boolean useFinderCache);

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

}