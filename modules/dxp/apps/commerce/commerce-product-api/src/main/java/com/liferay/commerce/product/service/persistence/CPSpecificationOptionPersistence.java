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

import com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException;
import com.liferay.commerce.product.model.CPSpecificationOption;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp specification option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPSpecificationOptionPersistenceImpl
 * @see CPSpecificationOptionUtil
 * @generated
 */
@ProviderType
public interface CPSpecificationOptionPersistence extends BasePersistence<CPSpecificationOption> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPSpecificationOptionUtil} to access the cp specification option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp specification options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid(String uuid);

	/**
	* Returns a range of all the cp specification options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid(String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where uuid = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption[] findByUuid_PrevAndNext(
		long CPSpecificationOptionId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Removes all the cp specification options where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp specification options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp specification options
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp specification option where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPSpecificationOptionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByUUID_G(String uuid, long groupId)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the cp specification option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp specification option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp specification option where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp specification option that was removed
	*/
	public CPSpecificationOption removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the number of cp specification options where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp specification options
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption[] findByUuid_C_PrevAndNext(
		long CPSpecificationOptionId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Removes all the cp specification options where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp specification options
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the cp specification options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByGroupId(long groupId);

	/**
	* Returns a range of all the cp specification options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp specification options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp specification options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the first cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the last cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the last cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where groupId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption[] findByGroupId_PrevAndNext(
		long CPSpecificationOptionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns all the cp specification options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp specification options that the user has permission to view
	*/
	public java.util.List<CPSpecificationOption> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the cp specification options that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options that the user has permission to view
	*/
	public java.util.List<CPSpecificationOption> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the cp specification options that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options that the user has permission to view
	*/
	public java.util.List<CPSpecificationOption> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set of cp specification options that the user has permission to view where groupId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption[] filterFindByGroupId_PrevAndNext(
		long CPSpecificationOptionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Removes all the cp specification options where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp specification options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp specification options
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of cp specification options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp specification options that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId);

	/**
	* Returns a range of all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public java.util.List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the first cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the last cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the last cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption[] findByCPOptionCategoryId_PrevAndNext(
		long CPSpecificationOptionId, long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Removes all the cp specification options where CPOptionCategoryId = &#63; from the database.
	*
	* @param CPOptionCategoryId the cp option category ID
	*/
	public void removeByCPOptionCategoryId(long CPOptionCategoryId);

	/**
	* Returns the number of cp specification options where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the number of matching cp specification options
	*/
	public int countByCPOptionCategoryId(long CPOptionCategoryId);

	/**
	* Returns the cp specification option where groupId = &#63; and key = &#63; or throws a {@link NoSuchCPSpecificationOptionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public CPSpecificationOption findByG_K(long groupId, String key)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the cp specification option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByG_K(long groupId, String key);

	/**
	* Returns the cp specification option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public CPSpecificationOption fetchByG_K(long groupId, String key,
		boolean retrieveFromCache);

	/**
	* Removes the cp specification option where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the cp specification option that was removed
	*/
	public CPSpecificationOption removeByG_K(long groupId, String key)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the number of cp specification options where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching cp specification options
	*/
	public int countByG_K(long groupId, String key);

	/**
	* Caches the cp specification option in the entity cache if it is enabled.
	*
	* @param cpSpecificationOption the cp specification option
	*/
	public void cacheResult(CPSpecificationOption cpSpecificationOption);

	/**
	* Caches the cp specification options in the entity cache if it is enabled.
	*
	* @param cpSpecificationOptions the cp specification options
	*/
	public void cacheResult(
		java.util.List<CPSpecificationOption> cpSpecificationOptions);

	/**
	* Creates a new cp specification option with the primary key. Does not add the cp specification option to the database.
	*
	* @param CPSpecificationOptionId the primary key for the new cp specification option
	* @return the new cp specification option
	*/
	public CPSpecificationOption create(long CPSpecificationOptionId);

	/**
	* Removes the cp specification option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option that was removed
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption remove(long CPSpecificationOptionId)
		throws NoSuchCPSpecificationOptionException;

	public CPSpecificationOption updateImpl(
		CPSpecificationOption cpSpecificationOption);

	/**
	* Returns the cp specification option with the primary key or throws a {@link NoSuchCPSpecificationOptionException} if it could not be found.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption findByPrimaryKey(long CPSpecificationOptionId)
		throws NoSuchCPSpecificationOptionException;

	/**
	* Returns the cp specification option with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option, or <code>null</code> if a cp specification option with the primary key could not be found
	*/
	public CPSpecificationOption fetchByPrimaryKey(long CPSpecificationOptionId);

	@Override
	public java.util.Map<java.io.Serializable, CPSpecificationOption> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp specification options.
	*
	* @return the cp specification options
	*/
	public java.util.List<CPSpecificationOption> findAll();

	/**
	* Returns a range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of cp specification options
	*/
	public java.util.List<CPSpecificationOption> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp specification options
	*/
	public java.util.List<CPSpecificationOption> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp specification options
	*/
	public java.util.List<CPSpecificationOption> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp specification options from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp specification options.
	*
	* @return the number of cp specification options
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}