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

package com.liferay.commerce.products.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.products.exception.NoSuchProductDefinitionException;
import com.liferay.commerce.products.model.CommerceProductDefinition;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.products.service.persistence.impl.CommerceProductDefinitionPersistenceImpl
 * @see CommerceProductDefinitionUtil
 * @generated
 */
@ProviderType
public interface CommerceProductDefinitionPersistence extends BasePersistence<CommerceProductDefinition> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductDefinitionUtil} to access the commerce product definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the commerce product definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where uuid = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public CommerceProductDefinition[] findByUuid_PrevAndNext(
		long commerceProductDefinitionId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Removes all the commerce product definitions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce product definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product definitions
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce product definition where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductDefinitionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchProductDefinitionException;

	/**
	* Returns the commerce product definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the commerce product definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce product definition where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product definition that was removed
	*/
	public CommerceProductDefinition removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchProductDefinitionException;

	/**
	* Returns the number of commerce product definitions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product definitions
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the first commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the last commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public CommerceProductDefinition[] findByUuid_C_PrevAndNext(
		long commerceProductDefinitionId, java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Removes all the commerce product definitions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce product definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product definitions
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce product definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce product definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the first commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the last commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the last commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where groupId = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public CommerceProductDefinition[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Removes all the commerce product definitions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce product definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product definitions
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce product definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce product definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the first commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the last commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition
	* @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the last commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	public CommerceProductDefinition fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns the commerce product definitions before and after the current commerce product definition in the ordered set where companyId = &#63;.
	*
	* @param commerceProductDefinitionId the primary key of the current commerce product definition
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public CommerceProductDefinition[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException;

	/**
	* Removes all the commerce product definitions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce product definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product definitions
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the commerce product definition in the entity cache if it is enabled.
	*
	* @param commerceProductDefinition the commerce product definition
	*/
	public void cacheResult(CommerceProductDefinition commerceProductDefinition);

	/**
	* Caches the commerce product definitions in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitions the commerce product definitions
	*/
	public void cacheResult(
		java.util.List<CommerceProductDefinition> commerceProductDefinitions);

	/**
	* Creates a new commerce product definition with the primary key. Does not add the commerce product definition to the database.
	*
	* @param commerceProductDefinitionId the primary key for the new commerce product definition
	* @return the new commerce product definition
	*/
	public CommerceProductDefinition create(long commerceProductDefinitionId);

	/**
	* Removes the commerce product definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition that was removed
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public CommerceProductDefinition remove(long commerceProductDefinitionId)
		throws NoSuchProductDefinitionException;

	public CommerceProductDefinition updateImpl(
		CommerceProductDefinition commerceProductDefinition);

	/**
	* Returns the commerce product definition with the primary key or throws a {@link NoSuchProductDefinitionException} if it could not be found.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition
	* @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	*/
	public CommerceProductDefinition findByPrimaryKey(
		long commerceProductDefinitionId)
		throws NoSuchProductDefinitionException;

	/**
	* Returns the commerce product definition with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition, or <code>null</code> if a commerce product definition with the primary key could not be found
	*/
	public CommerceProductDefinition fetchByPrimaryKey(
		long commerceProductDefinitionId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductDefinition> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product definitions.
	*
	* @return the commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findAll();

	/**
	* Returns a range of all the commerce product definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce product definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product definitions
	*/
	public java.util.List<CommerceProductDefinition> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product definitions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product definitions.
	*
	* @return the number of commerce product definitions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}