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

import com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product definition option value rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionOptionValueRelPersistenceImpl
 * @see CommerceProductDefinitionOptionValueRelUtil
 * @generated
 */
@ProviderType
public interface CommerceProductDefinitionOptionValueRelPersistence
	extends BasePersistence<CommerceProductDefinitionOptionValueRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductDefinitionOptionValueRelUtil} to access the commerce product definition option value rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product definition option value rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the commerce product definition option value rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the first commerce product definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the last commerce product definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the last commerce product definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel[] findByUuid_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Removes all the commerce product definition option value rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce product definition option value rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product definition option value rels
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce product definition option value rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductDefinitionOptionValueRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the commerce product definition option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the commerce product definition option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce product definition option value rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product definition option value rel that was removed
	*/
	public CommerceProductDefinitionOptionValueRel removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the number of commerce product definition option value rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product definition option value rels
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce product definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce product definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the first commerce product definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the last commerce product definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the last commerce product definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel[] findByUuid_C_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Removes all the commerce product definition option value rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce product definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product definition option value rels
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce product definition option value rels where commerceProductDefinitionOptionRelId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @return the matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId);

	/**
	* Returns a range of all the commerce product definition option value rels where commerceProductDefinitionOptionRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where commerceProductDefinitionOptionRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where commerceProductDefinitionOptionRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option value rel in the ordered set where commerceProductDefinitionOptionRelId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByCommerceProductDefinitionOptionRelId_First(
		long commerceProductDefinitionOptionRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the first commerce product definition option value rel in the ordered set where commerceProductDefinitionOptionRelId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByCommerceProductDefinitionOptionRelId_First(
		long commerceProductDefinitionOptionRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the last commerce product definition option value rel in the ordered set where commerceProductDefinitionOptionRelId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByCommerceProductDefinitionOptionRelId_Last(
		long commerceProductDefinitionOptionRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the last commerce product definition option value rel in the ordered set where commerceProductDefinitionOptionRelId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByCommerceProductDefinitionOptionRelId_Last(
		long commerceProductDefinitionOptionRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where commerceProductDefinitionOptionRelId = &#63;.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel[] findByCommerceProductDefinitionOptionRelId_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId,
		long commerceProductDefinitionOptionRelId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Removes all the commerce product definition option value rels where commerceProductDefinitionOptionRelId = &#63; from the database.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	*/
	public void removeByCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId);

	/**
	* Returns the number of commerce product definition option value rels where commerceProductDefinitionOptionRelId = &#63;.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID
	* @return the number of matching commerce product definition option value rels
	*/
	public int countByCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId);

	/**
	* Returns all the commerce product definition option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the commerce product definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the first commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the last commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the last commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Removes all the commerce product definition option value rels where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce product definition option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product definition option value rels
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce product definition option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce product definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the first commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the last commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the last commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns the commerce product definition option value rels before and after the current commerce product definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the current commerce product definition option value rel
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionOptionValueRelId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Removes all the commerce product definition option value rels where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce product definition option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product definition option value rels
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the commerce product definition option value rel in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	*/
	public void cacheResult(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel);

	/**
	* Caches the commerce product definition option value rels in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionOptionValueRels the commerce product definition option value rels
	*/
	public void cacheResult(
		java.util.List<CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels);

	/**
	* Creates a new commerce product definition option value rel with the primary key. Does not add the commerce product definition option value rel to the database.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key for the new commerce product definition option value rel
	* @return the new commerce product definition option value rel
	*/
	public CommerceProductDefinitionOptionValueRel create(
		long commerceProductDefinitionOptionValueRelId);

	/**
	* Removes the commerce product definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel remove(
		long commerceProductDefinitionOptionValueRelId)
		throws NoSuchProductDefinitionOptionValueRelException;

	public CommerceProductDefinitionOptionValueRel updateImpl(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel);

	/**
	* Returns the commerce product definition option value rel with the primary key or throws a {@link NoSuchProductDefinitionOptionValueRelException} if it could not be found.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel
	* @throws NoSuchProductDefinitionOptionValueRelException if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel findByPrimaryKey(
		long commerceProductDefinitionOptionValueRelId)
		throws NoSuchProductDefinitionOptionValueRelException;

	/**
	* Returns the commerce product definition option value rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel, or <code>null</code> if a commerce product definition option value rel with the primary key could not be found
	*/
	public CommerceProductDefinitionOptionValueRel fetchByPrimaryKey(
		long commerceProductDefinitionOptionValueRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductDefinitionOptionValueRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product definition option value rels.
	*
	* @return the commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findAll();

	/**
	* Returns a range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product definition option value rels
	*/
	public java.util.List<CommerceProductDefinitionOptionValueRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product definition option value rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product definition option value rels.
	*
	* @return the number of commerce product definition option value rels
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}