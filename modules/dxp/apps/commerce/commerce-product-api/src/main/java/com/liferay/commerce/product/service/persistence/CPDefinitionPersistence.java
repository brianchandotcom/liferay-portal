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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the cp definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionPersistenceImpl
 * @see CPDefinitionUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionPersistence extends BasePersistence<CPDefinition> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionUtil} to access the cp definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the cp definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByUuid_PrevAndNext(long CPDefinitionId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definitions
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp definition where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition that was removed
	*/
	public CPDefinition removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the number of cp definitions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definitions
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByUuid_C_PrevAndNext(long CPDefinitionId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definitions
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cp definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByGroupId(long groupId);

	/**
	* Returns a range of all the cp definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the cp definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByGroupId_PrevAndNext(long CPDefinitionId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns all the cp definitions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the cp definitions that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definitions that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set of cp definitions that the user has permission to view where groupId = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] filterFindByGroupId_PrevAndNext(long CPDefinitionId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp definitions
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of cp definitions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp definitions that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the cp definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCompanyId(long companyId);

	/**
	* Returns a range of all the cp definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where companyId = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByCompanyId_PrevAndNext(long CPDefinitionId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of cp definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp definitions
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the cp definitions where CPTaxCategoryId = &#63;.
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId);

	/**
	* Returns a range of all the cp definitions where CPTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByCPTaxCategoryId_First(long CPTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByCPTaxCategoryId_First(long CPTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where CPTaxCategoryId = &#63;.
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByCPTaxCategoryId_Last(long CPTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where CPTaxCategoryId = &#63;.
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByCPTaxCategoryId_Last(long CPTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where CPTaxCategoryId = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param CPTaxCategoryId the cp tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByCPTaxCategoryId_PrevAndNext(
		long CPDefinitionId, long CPTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where CPTaxCategoryId = &#63; from the database.
	*
	* @param CPTaxCategoryId the cp tax category ID
	*/
	public void removeByCPTaxCategoryId(long CPTaxCategoryId);

	/**
	* Returns the number of cp definitions where CPTaxCategoryId = &#63;.
	*
	* @param CPTaxCategoryId the cp tax category ID
	* @return the number of matching cp definitions
	*/
	public int countByCPTaxCategoryId(long CPTaxCategoryId);

	/**
	* Returns all the cp definitions where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_S(long groupId, int status);

	/**
	* Returns a range of all the cp definitions where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_S(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_S(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_S(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByG_S_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByG_S_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByG_S_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByG_S_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByG_S_PrevAndNext(long CPDefinitionId,
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns all the cp definitions that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByG_S(long groupId, int status);

	/**
	* Returns a range of all the cp definitions that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByG_S(long groupId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the cp definitions that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByG_S(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set of cp definitions that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] filterFindByG_S_PrevAndNext(long CPDefinitionId,
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_S(long groupId, int status);

	/**
	* Returns the number of cp definitions where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching cp definitions
	*/
	public int countByG_S(long groupId, int status);

	/**
	* Returns the number of cp definitions that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching cp definitions that the user has permission to view
	*/
	public int filterCountByG_S(long groupId, int status);

	/**
	* Returns all the cp definitions where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_NotS(long groupId, int status);

	/**
	* Returns a range of all the cp definitions where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_NotS(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definitions where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_NotS(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByG_NotS(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByG_NotS_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByG_NotS_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByG_NotS_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByG_NotS_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByG_NotS_PrevAndNext(long CPDefinitionId,
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns all the cp definitions that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByG_NotS(long groupId,
		int status);

	/**
	* Returns a range of all the cp definitions that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByG_NotS(long groupId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the cp definitions that the user has permissions to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions that the user has permission to view
	*/
	public java.util.List<CPDefinition> filterFindByG_NotS(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set of cp definitions that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] filterFindByG_NotS_PrevAndNext(long CPDefinitionId,
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where groupId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_NotS(long groupId, int status);

	/**
	* Returns the number of cp definitions where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching cp definitions
	*/
	public int countByG_NotS(long groupId, int status);

	/**
	* Returns the number of cp definitions that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching cp definitions that the user has permission to view
	*/
	public int filterCountByG_NotS(long groupId, int status);

	/**
	* Returns all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching cp definitions
	*/
	public java.util.List<CPDefinition> findByLtD_S(Date displayDate, int status);

	/**
	* Returns a range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByLtD_S(Date displayDate,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definitions
	*/
	public java.util.List<CPDefinition> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the last cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition
	* @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	*/
	public CPDefinition findByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the last cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public CPDefinition fetchByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the cp definitions before and after the current cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param CPDefinitionId the primary key of the current cp definition
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition[] findByLtD_S_PrevAndNext(long CPDefinitionId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException;

	/**
	* Removes all the cp definitions where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByLtD_S(Date displayDate, int status);

	/**
	* Returns the number of cp definitions where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching cp definitions
	*/
	public int countByLtD_S(Date displayDate, int status);

	/**
	* Caches the cp definition in the entity cache if it is enabled.
	*
	* @param cpDefinition the cp definition
	*/
	public void cacheResult(CPDefinition cpDefinition);

	/**
	* Caches the cp definitions in the entity cache if it is enabled.
	*
	* @param cpDefinitions the cp definitions
	*/
	public void cacheResult(java.util.List<CPDefinition> cpDefinitions);

	/**
	* Creates a new cp definition with the primary key. Does not add the cp definition to the database.
	*
	* @param CPDefinitionId the primary key for the new cp definition
	* @return the new cp definition
	*/
	public CPDefinition create(long CPDefinitionId);

	/**
	* Removes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition that was removed
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition remove(long CPDefinitionId)
		throws NoSuchCPDefinitionException;

	public CPDefinition updateImpl(CPDefinition cpDefinition);

	/**
	* Returns the cp definition with the primary key or throws a {@link NoSuchCPDefinitionException} if it could not be found.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition
	* @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	*/
	public CPDefinition findByPrimaryKey(long CPDefinitionId)
		throws NoSuchCPDefinitionException;

	/**
	* Returns the cp definition with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition, or <code>null</code> if a cp definition with the primary key could not be found
	*/
	public CPDefinition fetchByPrimaryKey(long CPDefinitionId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinition> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definitions.
	*
	* @return the cp definitions
	*/
	public java.util.List<CPDefinition> findAll();

	/**
	* Returns a range of all the cp definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of cp definitions
	*/
	public java.util.List<CPDefinition> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definitions
	*/
	public java.util.List<CPDefinition> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the cp definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definitions
	*/
	public java.util.List<CPDefinition> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definitions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definitions.
	*
	* @return the number of cp definitions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}