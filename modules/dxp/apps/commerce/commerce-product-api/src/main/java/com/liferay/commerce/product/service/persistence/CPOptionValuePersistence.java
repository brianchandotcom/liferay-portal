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

import com.liferay.commerce.product.exception.NoSuchCPOptionValueException;
import com.liferay.commerce.product.model.CPOptionValue;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPOptionValuePersistenceImpl
 * @see CPOptionValueUtil
 * @generated
 */
@ProviderType
public interface CPOptionValuePersistence extends BasePersistence<CPOptionValue> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionValueUtil} to access the cp option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid(String uuid);

	/**
	* Returns a range of all the cp option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where uuid = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public CPOptionValue[] findByUuid_PrevAndNext(long CPOptionValueId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Removes all the cp option values where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp option values
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp option value where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the cp option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp option value where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp option value that was removed
	*/
	public CPOptionValue removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the number of cp option values where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp option values
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public CPOptionValue[] findByUuid_C_PrevAndNext(long CPOptionValueId,
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Removes all the cp option values where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp option values
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the cp option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp option values
	*/
	public java.util.List<CPOptionValue> findByGroupId(long groupId);

	/**
	* Returns a range of all the cp option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the cp option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the first cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the last cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the last cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where groupId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public CPOptionValue[] findByGroupId_PrevAndNext(long CPOptionValueId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Removes all the cp option values where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp option values
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the cp option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCompanyId(long companyId);

	/**
	* Returns a range of all the cp option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the first cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the last cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the last cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where companyId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public CPOptionValue[] findByCompanyId_PrevAndNext(long CPOptionValueId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Removes all the cp option values where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of cp option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp option values
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the cp option values where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @return the matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCPOptionId(long CPOptionId);

	/**
	* Returns a range of all the cp option values where CPOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionId the cp option ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCPOptionId(long CPOptionId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionId the cp option ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCPOptionId(long CPOptionId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionId the cp option ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public java.util.List<CPOptionValue> findByCPOptionId(long CPOptionId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByCPOptionId_First(long CPOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the first cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByCPOptionId_First(long CPOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the last cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByCPOptionId_Last(long CPOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the last cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByCPOptionId_Last(long CPOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public CPOptionValue[] findByCPOptionId_PrevAndNext(long CPOptionValueId,
		long CPOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException;

	/**
	* Removes all the cp option values where CPOptionId = &#63; from the database.
	*
	* @param CPOptionId the cp option ID
	*/
	public void removeByCPOptionId(long CPOptionId);

	/**
	* Returns the number of cp option values where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @return the number of matching cp option values
	*/
	public int countByCPOptionId(long CPOptionId);

	/**
	* Returns the cp option value where CPOptionId = &#63; and key = &#63; or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public CPOptionValue findByC_K(long CPOptionId, String key)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the cp option value where CPOptionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByC_K(long CPOptionId, String key);

	/**
	* Returns the cp option value where CPOptionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public CPOptionValue fetchByC_K(long CPOptionId, String key,
		boolean retrieveFromCache);

	/**
	* Removes the cp option value where CPOptionId = &#63; and key = &#63; from the database.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the cp option value that was removed
	*/
	public CPOptionValue removeByC_K(long CPOptionId, String key)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the number of cp option values where CPOptionId = &#63; and key = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the number of matching cp option values
	*/
	public int countByC_K(long CPOptionId, String key);

	/**
	* Caches the cp option value in the entity cache if it is enabled.
	*
	* @param cpOptionValue the cp option value
	*/
	public void cacheResult(CPOptionValue cpOptionValue);

	/**
	* Caches the cp option values in the entity cache if it is enabled.
	*
	* @param cpOptionValues the cp option values
	*/
	public void cacheResult(java.util.List<CPOptionValue> cpOptionValues);

	/**
	* Creates a new cp option value with the primary key. Does not add the cp option value to the database.
	*
	* @param CPOptionValueId the primary key for the new cp option value
	* @return the new cp option value
	*/
	public CPOptionValue create(long CPOptionValueId);

	/**
	* Removes the cp option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value that was removed
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public CPOptionValue remove(long CPOptionValueId)
		throws NoSuchCPOptionValueException;

	public CPOptionValue updateImpl(CPOptionValue cpOptionValue);

	/**
	* Returns the cp option value with the primary key or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public CPOptionValue findByPrimaryKey(long CPOptionValueId)
		throws NoSuchCPOptionValueException;

	/**
	* Returns the cp option value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value, or <code>null</code> if a cp option value with the primary key could not be found
	*/
	public CPOptionValue fetchByPrimaryKey(long CPOptionValueId);

	@Override
	public java.util.Map<java.io.Serializable, CPOptionValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp option values.
	*
	* @return the cp option values
	*/
	public java.util.List<CPOptionValue> findAll();

	/**
	* Returns a range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of cp option values
	*/
	public java.util.List<CPOptionValue> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp option values
	*/
	public java.util.List<CPOptionValue> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp option values
	*/
	public java.util.List<CPOptionValue> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp option values from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp option values.
	*
	* @return the number of cp option values
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}