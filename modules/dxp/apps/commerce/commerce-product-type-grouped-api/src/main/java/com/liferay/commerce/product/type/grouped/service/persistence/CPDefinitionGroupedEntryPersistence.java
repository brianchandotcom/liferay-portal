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

package com.liferay.commerce.product.type.grouped.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition grouped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.product.type.grouped.service.persistence.impl.CPDefinitionGroupedEntryPersistenceImpl
 * @see CPDefinitionGroupedEntryUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionGroupedEntryPersistence extends BasePersistence<CPDefinitionGroupedEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionGroupedEntryUtil} to access the cp definition grouped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition grouped entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid(String uuid);

	/**
	* Returns a range of all the cp definition grouped entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid(String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public CPDefinitionGroupedEntry[] findByUuid_PrevAndNext(
		long CPDefinitionGroupedEntryId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Removes all the cp definition grouped entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp definition grouped entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition grouped entries
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition grouped entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition grouped entry that was removed
	*/
	public CPDefinitionGroupedEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the number of cp definition grouped entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition grouped entries
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public CPDefinitionGroupedEntry[] findByUuid_C_PrevAndNext(
		long CPDefinitionGroupedEntryId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Removes all the cp definition grouped entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition grouped entries
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId);

	/**
	* Returns a range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public CPDefinitionGroupedEntry[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionGroupedEntryId, long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Removes all the cp definition grouped entries where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public void removeByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the number of cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition grouped entries
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry findByC_E(long CPDefinitionId,
		long entryCPDefinitionId)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByC_E(long CPDefinitionId,
		long entryCPDefinitionId);

	/**
	* Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public CPDefinitionGroupedEntry fetchByC_E(long CPDefinitionId,
		long entryCPDefinitionId, boolean retrieveFromCache);

	/**
	* Removes the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the cp definition grouped entry that was removed
	*/
	public CPDefinitionGroupedEntry removeByC_E(long CPDefinitionId,
		long entryCPDefinitionId)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the number of cp definition grouped entries where CPDefinitionId = &#63; and entryCPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the number of matching cp definition grouped entries
	*/
	public int countByC_E(long CPDefinitionId, long entryCPDefinitionId);

	/**
	* Caches the cp definition grouped entry in the entity cache if it is enabled.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	*/
	public void cacheResult(CPDefinitionGroupedEntry cpDefinitionGroupedEntry);

	/**
	* Caches the cp definition grouped entries in the entity cache if it is enabled.
	*
	* @param cpDefinitionGroupedEntries the cp definition grouped entries
	*/
	public void cacheResult(
		java.util.List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries);

	/**
	* Creates a new cp definition grouped entry with the primary key. Does not add the cp definition grouped entry to the database.
	*
	* @param CPDefinitionGroupedEntryId the primary key for the new cp definition grouped entry
	* @return the new cp definition grouped entry
	*/
	public CPDefinitionGroupedEntry create(long CPDefinitionGroupedEntryId);

	/**
	* Removes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry that was removed
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public CPDefinitionGroupedEntry remove(long CPDefinitionGroupedEntryId)
		throws NoSuchCPDefinitionGroupedEntryException;

	public CPDefinitionGroupedEntry updateImpl(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry);

	/**
	* Returns the cp definition grouped entry with the primary key or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public CPDefinitionGroupedEntry findByPrimaryKey(
		long CPDefinitionGroupedEntryId)
		throws NoSuchCPDefinitionGroupedEntryException;

	/**
	* Returns the cp definition grouped entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry, or <code>null</code> if a cp definition grouped entry with the primary key could not be found
	*/
	public CPDefinitionGroupedEntry fetchByPrimaryKey(
		long CPDefinitionGroupedEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionGroupedEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition grouped entries.
	*
	* @return the cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findAll();

	/**
	* Returns a range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition grouped entries
	*/
	public java.util.List<CPDefinitionGroupedEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition grouped entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition grouped entries.
	*
	* @return the number of cp definition grouped entries
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}