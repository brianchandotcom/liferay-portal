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

import com.liferay.commerce.product.exception.NoSuchProductInstanceException;
import com.liferay.commerce.product.model.CommerceProductInstance;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductInstancePersistenceImpl
 * @see CommerceProductInstanceUtil
 * @generated
 */
@ProviderType
public interface CommerceProductInstancePersistence extends BasePersistence<CommerceProductInstance> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductInstanceUtil} to access the commerce product instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the commerce product instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance[] findByUuid_PrevAndNext(
		long commerceProductInstanceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Removes all the commerce product instances where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce product instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product instances
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce product instance where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductInstanceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchProductInstanceException;

	/**
	* Returns the commerce product instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the commerce product instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce product instance where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product instance that was removed
	*/
	public CommerceProductInstance removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchProductInstanceException;

	/**
	* Returns the number of commerce product instances where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product instances
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance[] findByUuid_C_PrevAndNext(
		long commerceProductInstanceId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Removes all the commerce product instances where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product instances
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce product instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce product instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce product instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the first commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the last commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the last commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance[] findByGroupId_PrevAndNext(
		long commerceProductInstanceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Removes all the commerce product instances where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce product instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product instances
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce product instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce product instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the first commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the last commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the last commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance[] findByCompanyId_PrevAndNext(
		long commerceProductInstanceId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Removes all the commerce product instances where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce product instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product instances
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @return the matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId);

	/**
	* Returns a range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the first commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the last commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Returns the last commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance[] findByCommerceProductDefinitionId_PrevAndNext(
		long commerceProductInstanceId, long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException;

	/**
	* Removes all the commerce product instances where commerceProductDefinitionId = &#63; from the database.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	*/
	public void removeByCommerceProductDefinitionId(
		long commerceProductDefinitionId);

	/**
	* Returns the number of commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @return the number of matching commerce product instances
	*/
	public int countByCommerceProductDefinitionId(
		long commerceProductDefinitionId);

	/**
	* Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or throws a {@link NoSuchProductInstanceException} if it could not be found.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance findByC_S(long commerceProductDefinitionId,
		java.lang.String sku) throws NoSuchProductInstanceException;

	/**
	* Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByC_S(
		long commerceProductDefinitionId, java.lang.String sku);

	/**
	* Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public CommerceProductInstance fetchByC_S(
		long commerceProductDefinitionId, java.lang.String sku,
		boolean retrieveFromCache);

	/**
	* Removes the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; from the database.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the commerce product instance that was removed
	*/
	public CommerceProductInstance removeByC_S(
		long commerceProductDefinitionId, java.lang.String sku)
		throws NoSuchProductInstanceException;

	/**
	* Returns the number of commerce product instances where commerceProductDefinitionId = &#63; and sku = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the number of matching commerce product instances
	*/
	public int countByC_S(long commerceProductDefinitionId, java.lang.String sku);

	/**
	* Caches the commerce product instance in the entity cache if it is enabled.
	*
	* @param commerceProductInstance the commerce product instance
	*/
	public void cacheResult(CommerceProductInstance commerceProductInstance);

	/**
	* Caches the commerce product instances in the entity cache if it is enabled.
	*
	* @param commerceProductInstances the commerce product instances
	*/
	public void cacheResult(
		java.util.List<CommerceProductInstance> commerceProductInstances);

	/**
	* Creates a new commerce product instance with the primary key. Does not add the commerce product instance to the database.
	*
	* @param commerceProductInstanceId the primary key for the new commerce product instance
	* @return the new commerce product instance
	*/
	public CommerceProductInstance create(long commerceProductInstanceId);

	/**
	* Removes the commerce product instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance that was removed
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance remove(long commerceProductInstanceId)
		throws NoSuchProductInstanceException;

	public CommerceProductInstance updateImpl(
		CommerceProductInstance commerceProductInstance);

	/**
	* Returns the commerce product instance with the primary key or throws a {@link NoSuchProductInstanceException} if it could not be found.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance findByPrimaryKey(
		long commerceProductInstanceId) throws NoSuchProductInstanceException;

	/**
	* Returns the commerce product instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance, or <code>null</code> if a commerce product instance with the primary key could not be found
	*/
	public CommerceProductInstance fetchByPrimaryKey(
		long commerceProductInstanceId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product instances.
	*
	* @return the commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findAll();

	/**
	* Returns a range of all the commerce product instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce product instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product instances
	*/
	public java.util.List<CommerceProductInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product instances from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product instances.
	*
	* @return the number of commerce product instances
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}