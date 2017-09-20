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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchTirePriceEntryException;
import com.liferay.commerce.model.CommerceTirePriceEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce tire price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceTirePriceEntryPersistenceImpl
 * @see CommerceTirePriceEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceTirePriceEntryPersistence extends BasePersistence<CommerceTirePriceEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTirePriceEntryUtil} to access the commerce tire price entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce tire price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the commerce tire price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry[] findByUuid_PrevAndNext(
		long CommerceTirePriceEntryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Removes all the commerce tire price entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce tire price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce tire price entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTirePriceEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchTirePriceEntryException;

	/**
	* Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce tire price entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce tire price entry that was removed
	*/
	public CommerceTirePriceEntry removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchTirePriceEntryException;

	/**
	* Returns the number of commerce tire price entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce tire price entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry[] findByUuid_C_PrevAndNext(
		long CommerceTirePriceEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Removes all the commerce tire price entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce tire price entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce tire price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce tire price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce tire price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tire price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the first commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the last commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the last commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry[] findByGroupId_PrevAndNext(
		long CommerceTirePriceEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Removes all the commerce tire price entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce tire price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce tire price entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce tire price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce tire price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce tire price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tire price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the first commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the last commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the last commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry[] findByCompanyId_PrevAndNext(
		long CommerceTirePriceEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Removes all the commerce tire price entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce tire price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce tire price entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId);

	/**
	* Returns a range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByCommercePriceEntryId_First(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the first commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByCommercePriceEntryId_First(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the last commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry findByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Returns the last commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public CommerceTirePriceEntry fetchByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry[] findByCommercePriceEntryId_PrevAndNext(
		long CommerceTirePriceEntryId, long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException;

	/**
	* Removes all the commerce tire price entries where commercePriceEntryId = &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	*/
	public void removeByCommercePriceEntryId(long commercePriceEntryId);

	/**
	* Returns the number of commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the number of matching commerce tire price entries
	*/
	public int countByCommercePriceEntryId(long commercePriceEntryId);

	/**
	* Caches the commerce tire price entry in the entity cache if it is enabled.
	*
	* @param commerceTirePriceEntry the commerce tire price entry
	*/
	public void cacheResult(CommerceTirePriceEntry commerceTirePriceEntry);

	/**
	* Caches the commerce tire price entries in the entity cache if it is enabled.
	*
	* @param commerceTirePriceEntries the commerce tire price entries
	*/
	public void cacheResult(
		java.util.List<CommerceTirePriceEntry> commerceTirePriceEntries);

	/**
	* Creates a new commerce tire price entry with the primary key. Does not add the commerce tire price entry to the database.
	*
	* @param CommerceTirePriceEntryId the primary key for the new commerce tire price entry
	* @return the new commerce tire price entry
	*/
	public CommerceTirePriceEntry create(long CommerceTirePriceEntryId);

	/**
	* Removes the commerce tire price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry that was removed
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry remove(long CommerceTirePriceEntryId)
		throws NoSuchTirePriceEntryException;

	public CommerceTirePriceEntry updateImpl(
		CommerceTirePriceEntry commerceTirePriceEntry);

	/**
	* Returns the commerce tire price entry with the primary key or throws a {@link NoSuchTirePriceEntryException} if it could not be found.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry findByPrimaryKey(
		long CommerceTirePriceEntryId) throws NoSuchTirePriceEntryException;

	/**
	* Returns the commerce tire price entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry, or <code>null</code> if a commerce tire price entry with the primary key could not be found
	*/
	public CommerceTirePriceEntry fetchByPrimaryKey(
		long CommerceTirePriceEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceTirePriceEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce tire price entries.
	*
	* @return the commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findAll();

	/**
	* Returns a range of all the commerce tire price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce tire price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tire price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tire price entries
	*/
	public java.util.List<CommerceTirePriceEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce tire price entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce tire price entries.
	*
	* @return the number of commerce tire price entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}