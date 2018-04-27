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

import com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException;
import com.liferay.commerce.product.model.CPOptionCategory;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp option category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPOptionCategoryPersistenceImpl
 * @see CPOptionCategoryUtil
 * @generated
 */
@ProviderType
public interface CPOptionCategoryPersistence extends BasePersistence<CPOptionCategory> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionCategoryUtil} to access the cp option category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp option categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid(String uuid);

	/**
	* Returns a range of all the cp option categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the first cp option category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the last cp option category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the last cp option category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the cp option categories before and after the current cp option category in the ordered set where uuid = &#63;.
	*
	* @param CPOptionCategoryId the primary key of the current cp option category
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory[] findByUuid_PrevAndNext(long CPOptionCategoryId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Removes all the cp option categories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp option categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp option categories
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp option category where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPOptionCategoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the cp option category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp option category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp option category where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp option category that was removed
	*/
	public CPOptionCategory removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the number of cp option categories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp option categories
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the first cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the last cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the last cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the cp option categories before and after the current cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPOptionCategoryId the primary key of the current cp option category
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory[] findByUuid_C_PrevAndNext(
		long CPOptionCategoryId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Removes all the cp option categories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp option categories
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the cp option categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByGroupId(long groupId);

	/**
	* Returns a range of all the cp option categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp option categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns an ordered range of all the cp option categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the first cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the last cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the last cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the cp option categories before and after the current cp option category in the ordered set where groupId = &#63;.
	*
	* @param CPOptionCategoryId the primary key of the current cp option category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory[] findByGroupId_PrevAndNext(
		long CPOptionCategoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns all the cp option categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp option categories that the user has permission to view
	*/
	public java.util.List<CPOptionCategory> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the cp option categories that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories that the user has permission to view
	*/
	public java.util.List<CPOptionCategory> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp option categories that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories that the user has permission to view
	*/
	public java.util.List<CPOptionCategory> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the cp option categories before and after the current cp option category in the ordered set of cp option categories that the user has permission to view where groupId = &#63;.
	*
	* @param CPOptionCategoryId the primary key of the current cp option category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory[] filterFindByGroupId_PrevAndNext(
		long CPOptionCategoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Removes all the cp option categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp option categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp option categories
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of cp option categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp option categories that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the cp option categories where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByCompanyId(long companyId);

	/**
	* Returns a range of all the cp option categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp option categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns an ordered range of all the cp option categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public java.util.List<CPOptionCategory> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the first cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the last cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the last cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns the cp option categories before and after the current cp option category in the ordered set where companyId = &#63;.
	*
	* @param CPOptionCategoryId the primary key of the current cp option category
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory[] findByCompanyId_PrevAndNext(
		long CPOptionCategoryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator)
		throws NoSuchCPOptionCategoryException;

	/**
	* Removes all the cp option categories where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of cp option categories where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp option categories
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the cp option category where groupId = &#63; and key = &#63; or throws a {@link NoSuchCPOptionCategoryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public CPOptionCategory findByG_K(long groupId, String key)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the cp option category where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByG_K(long groupId, String key);

	/**
	* Returns the cp option category where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public CPOptionCategory fetchByG_K(long groupId, String key,
		boolean retrieveFromCache);

	/**
	* Removes the cp option category where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the cp option category that was removed
	*/
	public CPOptionCategory removeByG_K(long groupId, String key)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the number of cp option categories where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching cp option categories
	*/
	public int countByG_K(long groupId, String key);

	/**
	* Caches the cp option category in the entity cache if it is enabled.
	*
	* @param cpOptionCategory the cp option category
	*/
	public void cacheResult(CPOptionCategory cpOptionCategory);

	/**
	* Caches the cp option categories in the entity cache if it is enabled.
	*
	* @param cpOptionCategories the cp option categories
	*/
	public void cacheResult(java.util.List<CPOptionCategory> cpOptionCategories);

	/**
	* Creates a new cp option category with the primary key. Does not add the cp option category to the database.
	*
	* @param CPOptionCategoryId the primary key for the new cp option category
	* @return the new cp option category
	*/
	public CPOptionCategory create(long CPOptionCategoryId);

	/**
	* Removes the cp option category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionCategoryId the primary key of the cp option category
	* @return the cp option category that was removed
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory remove(long CPOptionCategoryId)
		throws NoSuchCPOptionCategoryException;

	public CPOptionCategory updateImpl(CPOptionCategory cpOptionCategory);

	/**
	* Returns the cp option category with the primary key or throws a {@link NoSuchCPOptionCategoryException} if it could not be found.
	*
	* @param CPOptionCategoryId the primary key of the cp option category
	* @return the cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory findByPrimaryKey(long CPOptionCategoryId)
		throws NoSuchCPOptionCategoryException;

	/**
	* Returns the cp option category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPOptionCategoryId the primary key of the cp option category
	* @return the cp option category, or <code>null</code> if a cp option category with the primary key could not be found
	*/
	public CPOptionCategory fetchByPrimaryKey(long CPOptionCategoryId);

	@Override
	public java.util.Map<java.io.Serializable, CPOptionCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp option categories.
	*
	* @return the cp option categories
	*/
	public java.util.List<CPOptionCategory> findAll();

	/**
	* Returns a range of all the cp option categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of cp option categories
	*/
	public java.util.List<CPOptionCategory> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp option categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp option categories
	*/
	public java.util.List<CPOptionCategory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator);

	/**
	* Returns an ordered range of all the cp option categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp option categories
	*/
	public java.util.List<CPOptionCategory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp option categories from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp option categories.
	*
	* @return the number of cp option categories
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}