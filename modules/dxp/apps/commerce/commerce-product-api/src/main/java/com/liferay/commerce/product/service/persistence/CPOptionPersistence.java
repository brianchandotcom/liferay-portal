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

import com.liferay.commerce.product.exception.NoSuchCPOptionException;
import com.liferay.commerce.product.model.CPOption;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPOptionPersistenceImpl
 * @see CPOptionUtil
 * @generated
 */
@ProviderType
public interface CPOptionPersistence extends BasePersistence<CPOption> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionUtil} to access the cp option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp options
	*/
	public java.util.List<CPOption> findByUuid(String uuid);

	/**
	* Returns a range of all the cp options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options
	*/
	public java.util.List<CPOption> findByUuid(String uuid, int start, int end);

	/**
	* Returns an ordered range of all the cp options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options
	*/
	public java.util.List<CPOption> findByUuid(String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp options
	*/
	public java.util.List<CPOption> findByUuid(String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Returns the first cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the last cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Returns the last cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the cp options before and after the current cp option in the ordered set where uuid = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public CPOption[] findByUuid_PrevAndNext(long CPOptionId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Removes all the cp options where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp options
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp option where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPOptionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionException;

	/**
	* Returns the cp option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp option where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp option that was removed
	*/
	public CPOption removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionException;

	/**
	* Returns the number of cp options where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp options
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp options
	*/
	public java.util.List<CPOption> findByUuid_C(String uuid, long companyId);

	/**
	* Returns a range of all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options
	*/
	public java.util.List<CPOption> findByUuid_C(String uuid, long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options
	*/
	public java.util.List<CPOption> findByUuid_C(String uuid, long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp options
	*/
	public java.util.List<CPOption> findByUuid_C(String uuid, long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Returns the first cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the last cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Returns the last cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the cp options before and after the current cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public CPOption[] findByUuid_C_PrevAndNext(long CPOptionId, String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Removes all the cp options where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp options
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the cp options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp options
	*/
	public java.util.List<CPOption> findByGroupId(long groupId);

	/**
	* Returns a range of all the cp options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options
	*/
	public java.util.List<CPOption> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the cp options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options
	*/
	public java.util.List<CPOption> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp options
	*/
	public java.util.List<CPOption> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Returns the first cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the last cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Returns the last cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the cp options before and after the current cp option in the ordered set where groupId = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public CPOption[] findByGroupId_PrevAndNext(long CPOptionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Returns all the cp options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp options that the user has permission to view
	*/
	public java.util.List<CPOption> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the cp options that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options that the user has permission to view
	*/
	public java.util.List<CPOption> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp options that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options that the user has permission to view
	*/
	public java.util.List<CPOption> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the cp options before and after the current cp option in the ordered set of cp options that the user has permission to view where groupId = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public CPOption[] filterFindByGroupId_PrevAndNext(long CPOptionId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator)
		throws NoSuchCPOptionException;

	/**
	* Removes all the cp options where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp options
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of cp options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp options that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the cp option where groupId = &#63; and key = &#63; or throws a {@link NoSuchCPOptionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public CPOption findByG_K(long groupId, String key)
		throws NoSuchCPOptionException;

	/**
	* Returns the cp option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByG_K(long groupId, String key);

	/**
	* Returns the cp option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public CPOption fetchByG_K(long groupId, String key,
		boolean retrieveFromCache);

	/**
	* Removes the cp option where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the cp option that was removed
	*/
	public CPOption removeByG_K(long groupId, String key)
		throws NoSuchCPOptionException;

	/**
	* Returns the number of cp options where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching cp options
	*/
	public int countByG_K(long groupId, String key);

	/**
	* Caches the cp option in the entity cache if it is enabled.
	*
	* @param cpOption the cp option
	*/
	public void cacheResult(CPOption cpOption);

	/**
	* Caches the cp options in the entity cache if it is enabled.
	*
	* @param cpOptions the cp options
	*/
	public void cacheResult(java.util.List<CPOption> cpOptions);

	/**
	* Creates a new cp option with the primary key. Does not add the cp option to the database.
	*
	* @param CPOptionId the primary key for the new cp option
	* @return the new cp option
	*/
	public CPOption create(long CPOptionId);

	/**
	* Removes the cp option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option that was removed
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public CPOption remove(long CPOptionId) throws NoSuchCPOptionException;

	public CPOption updateImpl(CPOption cpOption);

	/**
	* Returns the cp option with the primary key or throws a {@link NoSuchCPOptionException} if it could not be found.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public CPOption findByPrimaryKey(long CPOptionId)
		throws NoSuchCPOptionException;

	/**
	* Returns the cp option with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option, or <code>null</code> if a cp option with the primary key could not be found
	*/
	public CPOption fetchByPrimaryKey(long CPOptionId);

	@Override
	public java.util.Map<java.io.Serializable, CPOption> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp options.
	*
	* @return the cp options
	*/
	public java.util.List<CPOption> findAll();

	/**
	* Returns a range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of cp options
	*/
	public java.util.List<CPOption> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp options
	*/
	public java.util.List<CPOption> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns an ordered range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp options
	*/
	public java.util.List<CPOption> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp options from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp options.
	*
	* @return the number of cp options
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}