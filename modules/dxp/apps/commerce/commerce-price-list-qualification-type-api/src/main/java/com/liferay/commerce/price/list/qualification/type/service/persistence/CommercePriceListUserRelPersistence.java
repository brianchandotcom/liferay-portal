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

package com.liferay.commerce.price.list.qualification.type.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce price list user rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.price.list.qualification.type.service.persistence.impl.CommercePriceListUserRelPersistenceImpl
 * @see CommercePriceListUserRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListUserRelPersistence extends BasePersistence<CommercePriceListUserRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListUserRelUtil} to access the commerce price list user rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce price list user rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the commerce price list user rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel[] findByUuid_PrevAndNext(
		long commercePriceListUserRelId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Removes all the commerce price list user rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce price list user rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price list user rels
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListUserRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchPriceListUserRelException;

	/**
	* Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce price list user rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list user rel that was removed
	*/
	public CommercePriceListUserRel removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchPriceListUserRelException;

	/**
	* Returns the number of commerce price list user rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price list user rels
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel[] findByUuid_C_PrevAndNext(
		long commercePriceListUserRelId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Removes all the commerce price list user rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price list user rels
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @return the matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId);

	/**
	* Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByCommercePriceListQualificationTypeRelId_First(
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByCommercePriceListQualificationTypeRelId_First(
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByCommercePriceListQualificationTypeRelId_Last(
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByCommercePriceListQualificationTypeRelId_Last(
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel[] findByCommercePriceListQualificationTypeRelId_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; from the database.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	*/
	public void removeByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId);

	/**
	* Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @return the number of matching commerce price list user rels
	*/
	public int countByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId);

	/**
	* Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @return the matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId);

	/**
	* Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByC_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByC_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByC_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByC_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel[] findByC_C_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; from the database.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	*/
	public void removeByC_C(long commercePriceListQualificationTypeRelId,
		long classNameId);

	/**
	* Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @return the number of matching commerce price list user rels
	*/
	public int countByC_C(long commercePriceListQualificationTypeRelId,
		long classNameId);

	/**
	* Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK);

	/**
	* Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByC_C_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByC_C_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel findByC_C_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public CommercePriceListUserRel fetchByC_C_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel[] findByC_C_C_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException;

	/**
	* Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByC_C_C(long commercePriceListQualificationTypeRelId,
		long classNameId, long classPK);

	/**
	* Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce price list user rels
	*/
	public int countByC_C_C(long commercePriceListQualificationTypeRelId,
		long classNameId, long classPK);

	/**
	* Caches the commerce price list user rel in the entity cache if it is enabled.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	*/
	public void cacheResult(CommercePriceListUserRel commercePriceListUserRel);

	/**
	* Caches the commerce price list user rels in the entity cache if it is enabled.
	*
	* @param commercePriceListUserRels the commerce price list user rels
	*/
	public void cacheResult(
		java.util.List<CommercePriceListUserRel> commercePriceListUserRels);

	/**
	* Creates a new commerce price list user rel with the primary key. Does not add the commerce price list user rel to the database.
	*
	* @param commercePriceListUserRelId the primary key for the new commerce price list user rel
	* @return the new commerce price list user rel
	*/
	public CommercePriceListUserRel create(long commercePriceListUserRelId);

	/**
	* Removes the commerce price list user rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel that was removed
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel remove(long commercePriceListUserRelId)
		throws NoSuchPriceListUserRelException;

	public CommercePriceListUserRel updateImpl(
		CommercePriceListUserRel commercePriceListUserRel);

	/**
	* Returns the commerce price list user rel with the primary key or throws a {@link NoSuchPriceListUserRelException} if it could not be found.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel findByPrimaryKey(
		long commercePriceListUserRelId) throws NoSuchPriceListUserRelException;

	/**
	* Returns the commerce price list user rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel, or <code>null</code> if a commerce price list user rel with the primary key could not be found
	*/
	public CommercePriceListUserRel fetchByPrimaryKey(
		long commercePriceListUserRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommercePriceListUserRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce price list user rels.
	*
	* @return the commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findAll();

	/**
	* Returns a range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price list user rels
	*/
	public java.util.List<CommercePriceListUserRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce price list user rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce price list user rels.
	*
	* @return the number of commerce price list user rels
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}