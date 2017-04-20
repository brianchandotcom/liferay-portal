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

import com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionRelException;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product definition option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionOptionRelPersistenceImpl
 * @see CommerceProductDefinitionOptionRelUtil
 * @generated
 */
@ProviderType
public interface CommerceProductDefinitionOptionRelPersistence
	extends BasePersistence<CommerceProductDefinitionOptionRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductDefinitionOptionRelUtil} to access the commerce product definition option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product definition option rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the commerce product definition option rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the first commerce product definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the last commerce product definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the last commerce product definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where uuid = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel[] findByUuid_PrevAndNext(
		long commerceProductDefinitionOptionRelId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Removes all the commerce product definition option rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce product definition option rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product definition option rels
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce product definition option rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductDefinitionOptionRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the commerce product definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the commerce product definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce product definition option rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product definition option rel that was removed
	*/
	public CommerceProductDefinitionOptionRel removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the number of commerce product definition option rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product definition option rels
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce product definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce product definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the first commerce product definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the last commerce product definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the last commerce product definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel[] findByUuid_C_PrevAndNext(
		long commerceProductDefinitionOptionRelId, java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Removes all the commerce product definition option rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce product definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product definition option rels
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce product definition option rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the commerce product definition option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the first commerce product definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the last commerce product definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the last commerce product definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where groupId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionOptionRelId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Removes all the commerce product definition option rels where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce product definition option rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product definition option rels
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce product definition option rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce product definition option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the first commerce product definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the last commerce product definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the last commerce product definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where companyId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionOptionRelId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Removes all the commerce product definition option rels where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce product definition option rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product definition option rels
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @return the matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId);

	/**
	* Returns a range of all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option rels where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the first commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the last commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel findByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the last commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns the commerce product definition option rels before and after the current commerce product definition option rel in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the current commerce product definition option rel
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel[] findByCommerceProductDefinitionId_PrevAndNext(
		long commerceProductDefinitionOptionRelId,
		long commerceProductDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Removes all the commerce product definition option rels where commerceProductDefinitionId = &#63; from the database.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	*/
	public void removeByCommerceProductDefinitionId(
		long commerceProductDefinitionId);

	/**
	* Returns the number of commerce product definition option rels where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @return the number of matching commerce product definition option rels
	*/
	public int countByCommerceProductDefinitionId(
		long commerceProductDefinitionId);

	/**
	* Caches the commerce product definition option rel in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionOptionRel the commerce product definition option rel
	*/
	public void cacheResult(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel);

	/**
	* Caches the commerce product definition option rels in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionOptionRels the commerce product definition option rels
	*/
	public void cacheResult(
		java.util.List<CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels);

	/**
	* Creates a new commerce product definition option rel with the primary key. Does not add the commerce product definition option rel to the database.
	*
	* @param commerceProductDefinitionOptionRelId the primary key for the new commerce product definition option rel
	* @return the new commerce product definition option rel
	*/
	public CommerceProductDefinitionOptionRel create(
		long commerceProductDefinitionOptionRelId);

	/**
	* Removes the commerce product definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	* @return the commerce product definition option rel that was removed
	* @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel remove(
		long commerceProductDefinitionOptionRelId)
		throws NoSuchProductDefinitionOptionRelException;

	public CommerceProductDefinitionOptionRel updateImpl(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel);

	/**
	* Returns the commerce product definition option rel with the primary key or throws a {@link NoSuchProductDefinitionOptionRelException} if it could not be found.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	* @return the commerce product definition option rel
	* @throws NoSuchProductDefinitionOptionRelException if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel findByPrimaryKey(
		long commerceProductDefinitionOptionRelId)
		throws NoSuchProductDefinitionOptionRelException;

	/**
	* Returns the commerce product definition option rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	* @return the commerce product definition option rel, or <code>null</code> if a commerce product definition option rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionRel fetchByPrimaryKey(
		long commerceProductDefinitionOptionRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductDefinitionOptionRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product definition option rels.
	*
	* @return the commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findAll();

	/**
	* Returns a range of all the commerce product definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product definition option rels
	*/
	public java.util.List<CommerceProductDefinitionOptionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product definition option rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product definition option rels.
	*
	* @return the number of commerce product definition option rels
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}