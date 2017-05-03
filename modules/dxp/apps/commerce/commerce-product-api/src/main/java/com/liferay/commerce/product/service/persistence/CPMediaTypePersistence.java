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

import com.liferay.commerce.product.exception.NoSuchCPMediaTypeException;
import com.liferay.commerce.product.model.CPMediaType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp media type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPMediaTypePersistenceImpl
 * @see CPMediaTypeUtil
 * @generated
 */
@ProviderType
public interface CPMediaTypePersistence extends BasePersistence<CPMediaType> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPMediaTypeUtil} to access the cp media type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp media types where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the cp media types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the cp media types before and after the current cp media type in the ordered set where uuid = &#63;.
	*
	* @param CPMediaTypeId the primary key of the current cp media type
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public CPMediaType[] findByUuid_PrevAndNext(long CPMediaTypeId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Removes all the cp media types where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp media types where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp media types
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp media type where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPMediaTypeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the cp media type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp media type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp media type where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp media type that was removed
	*/
	public CPMediaType removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the number of cp media types where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp media types
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the cp media types before and after the current cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPMediaTypeId the primary key of the current cp media type
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public CPMediaType[] findByUuid_C_PrevAndNext(long CPMediaTypeId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Removes all the cp media types where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp media types where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp media types
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cp media types where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp media types
	*/
	public java.util.List<CPMediaType> findByGroupId(long groupId);

	/**
	* Returns a range of all the cp media types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the cp media types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns an ordered range of all the cp media types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp media type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the first cp media type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the last cp media type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the last cp media type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the cp media types before and after the current cp media type in the ordered set where groupId = &#63;.
	*
	* @param CPMediaTypeId the primary key of the current cp media type
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public CPMediaType[] findByGroupId_PrevAndNext(long CPMediaTypeId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns all the cp media types that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp media types that the user has permission to view
	*/
	public java.util.List<CPMediaType> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the cp media types that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of matching cp media types that the user has permission to view
	*/
	public java.util.List<CPMediaType> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp media types that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp media types that the user has permission to view
	*/
	public java.util.List<CPMediaType> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the cp media types before and after the current cp media type in the ordered set of cp media types that the user has permission to view where groupId = &#63;.
	*
	* @param CPMediaTypeId the primary key of the current cp media type
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public CPMediaType[] filterFindByGroupId_PrevAndNext(long CPMediaTypeId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Removes all the cp media types where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp media types where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp media types
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of cp media types that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp media types that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the cp media types where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp media types
	*/
	public java.util.List<CPMediaType> findByCompanyId(long companyId);

	/**
	* Returns a range of all the cp media types where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp media types where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns an ordered range of all the cp media types where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp media types
	*/
	public java.util.List<CPMediaType> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp media type in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the first cp media type in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the last cp media type in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public CPMediaType findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the last cp media type in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public CPMediaType fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns the cp media types before and after the current cp media type in the ordered set where companyId = &#63;.
	*
	* @param CPMediaTypeId the primary key of the current cp media type
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public CPMediaType[] findByCompanyId_PrevAndNext(long CPMediaTypeId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator)
		throws NoSuchCPMediaTypeException;

	/**
	* Removes all the cp media types where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of cp media types where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp media types
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the cp media type in the entity cache if it is enabled.
	*
	* @param cpMediaType the cp media type
	*/
	public void cacheResult(CPMediaType cpMediaType);

	/**
	* Caches the cp media types in the entity cache if it is enabled.
	*
	* @param cpMediaTypes the cp media types
	*/
	public void cacheResult(java.util.List<CPMediaType> cpMediaTypes);

	/**
	* Creates a new cp media type with the primary key. Does not add the cp media type to the database.
	*
	* @param CPMediaTypeId the primary key for the new cp media type
	* @return the new cp media type
	*/
	public CPMediaType create(long CPMediaTypeId);

	/**
	* Removes the cp media type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type that was removed
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public CPMediaType remove(long CPMediaTypeId)
		throws NoSuchCPMediaTypeException;

	public CPMediaType updateImpl(CPMediaType cpMediaType);

	/**
	* Returns the cp media type with the primary key or throws a {@link NoSuchCPMediaTypeException} if it could not be found.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public CPMediaType findByPrimaryKey(long CPMediaTypeId)
		throws NoSuchCPMediaTypeException;

	/**
	* Returns the cp media type with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type, or <code>null</code> if a cp media type with the primary key could not be found
	*/
	public CPMediaType fetchByPrimaryKey(long CPMediaTypeId);

	@Override
	public java.util.Map<java.io.Serializable, CPMediaType> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp media types.
	*
	* @return the cp media types
	*/
	public java.util.List<CPMediaType> findAll();

	/**
	* Returns a range of all the cp media types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of cp media types
	*/
	public java.util.List<CPMediaType> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp media types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp media types
	*/
	public java.util.List<CPMediaType> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator);

	/**
	* Returns an ordered range of all the cp media types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp media types
	*/
	public java.util.List<CPMediaType> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp media types from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp media types.
	*
	* @return the number of cp media types
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}