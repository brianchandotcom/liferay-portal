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

import com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce price list qualification type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommercePriceListQualificationTypeRelPersistenceImpl
 * @see CommercePriceListQualificationTypeRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListQualificationTypeRelPersistence
	extends BasePersistence<CommercePriceListQualificationTypeRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListQualificationTypeRelUtil} to access the commerce price list qualification type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce price list qualification type rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the commerce price list qualification type rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public CommercePriceListQualificationTypeRel[] findByUuid_PrevAndNext(
		long commercePriceListQualificationTypeRelId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Removes all the commerce price list qualification type rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce price list qualification type rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price list qualification type rels
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list qualification type rel that was removed
	*/
	public CommercePriceListQualificationTypeRel removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the number of commerce price list qualification type rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public CommercePriceListQualificationTypeRel[] findByUuid_C_PrevAndNext(
		long commercePriceListQualificationTypeRelId, java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Removes all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId);

	/**
	* Returns a range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public CommercePriceListQualificationTypeRel[] findByCommercePriceListId_PrevAndNext(
		long commercePriceListQualificationTypeRelId, long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Removes all the commerce price list qualification type rels where commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	*/
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	* Returns the number of commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	* Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel findByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId);

	/**
	* Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId, boolean retrieveFromCache);

	/**
	* Removes the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the commerce price list qualification type rel that was removed
	*/
	public CommercePriceListQualificationTypeRel removeByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the number of commerce price list qualification type rels where commercePriceListQualificationType = &#63; and commercePriceListId = &#63;.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public int countByC_C(java.lang.String commercePriceListQualificationType,
		long commercePriceListId);

	/**
	* Caches the commerce price list qualification type rel in the entity cache if it is enabled.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	*/
	public void cacheResult(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel);

	/**
	* Caches the commerce price list qualification type rels in the entity cache if it is enabled.
	*
	* @param commercePriceListQualificationTypeRels the commerce price list qualification type rels
	*/
	public void cacheResult(
		java.util.List<CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels);

	/**
	* Creates a new commerce price list qualification type rel with the primary key. Does not add the commerce price list qualification type rel to the database.
	*
	* @param commercePriceListQualificationTypeRelId the primary key for the new commerce price list qualification type rel
	* @return the new commerce price list qualification type rel
	*/
	public CommercePriceListQualificationTypeRel create(
		long commercePriceListQualificationTypeRelId);

	/**
	* Removes the commerce price list qualification type rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was removed
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public CommercePriceListQualificationTypeRel remove(
		long commercePriceListQualificationTypeRelId)
		throws NoSuchPriceListQualificationTypeRelException;

	public CommercePriceListQualificationTypeRel updateImpl(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel);

	/**
	* Returns the commerce price list qualification type rel with the primary key or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public CommercePriceListQualificationTypeRel findByPrimaryKey(
		long commercePriceListQualificationTypeRelId)
		throws NoSuchPriceListQualificationTypeRelException;

	/**
	* Returns the commerce price list qualification type rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel, or <code>null</code> if a commerce price list qualification type rel with the primary key could not be found
	*/
	public CommercePriceListQualificationTypeRel fetchByPrimaryKey(
		long commercePriceListQualificationTypeRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommercePriceListQualificationTypeRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce price list qualification type rels.
	*
	* @return the commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findAll();

	/**
	* Returns a range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price list qualification type rels
	*/
	public java.util.List<CommercePriceListQualificationTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce price list qualification type rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce price list qualification type rels.
	*
	* @return the number of commerce price list qualification type rels
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}