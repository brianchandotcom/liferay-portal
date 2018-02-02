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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition specification option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionSpecificationOptionValuePersistenceImpl
 * @see CPDefinitionSpecificationOptionValueUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionSpecificationOptionValuePersistence
	extends BasePersistence<CPDefinitionSpecificationOptionValue> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionSpecificationOptionValueUtil} to access the cp definition specification option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition specification option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the cp definition specification option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue[] findByUuid_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Removes all the cp definition specification option values where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp definition specification option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition specification option values
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionSpecificationOptionValueException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the cp definition specification option value where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition specification option value that was removed
	*/
	public CPDefinitionSpecificationOptionValue removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the number of cp definition specification option values where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue[] findByUuid_C_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Removes all the cp definition specification option values where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp definition specification option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cp definition specification option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the cp definition specification option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the last cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the last cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue[] findByGroupId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Removes all the cp definition specification option values where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp definition specification option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the cp definition specification option values where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId);

	/**
	* Returns a range of all the cp definition specification option values where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Removes all the cp definition specification option values where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public void removeByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the number of cp definition specification option values where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId);

	/**
	* Returns a range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByCPSpecificationOptionId_First(
		long CPSpecificationOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByCPSpecificationOptionId_First(
		long CPSpecificationOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the last cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByCPSpecificationOptionId_Last(
		long CPSpecificationOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the last cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByCPSpecificationOptionId_Last(
		long CPSpecificationOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue[] findByCPSpecificationOptionId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId,
		long CPSpecificationOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Removes all the cp definition specification option values where CPSpecificationOptionId = &#63; from the database.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	*/
	public void removeByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	* Returns the number of cp definition specification option values where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByCPSpecificationOptionId(long CPSpecificationOptionId);

	/**
	* Returns all the cp definition specification option values where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId);

	/**
	* Returns a range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the last cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the last cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue[] findByCPOptionCategoryId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Removes all the cp definition specification option values where CPOptionCategoryId = &#63; from the database.
	*
	* @param CPOptionCategoryId the cp option category ID
	*/
	public void removeByCPOptionCategoryId(long CPOptionCategoryId);

	/**
	* Returns the number of cp definition specification option values where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByCPOptionCategoryId(long CPOptionCategoryId);

	/**
	* Returns the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; or throws a {@link NoSuchCPDefinitionSpecificationOptionValueException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId);

	/**
	* Returns the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the cp definition specification option value that was removed
	*/
	public CPDefinitionSpecificationOptionValue removeByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByC_CSO(long CPDefinitionId, long CPSpecificationOptionId);

	/**
	* Returns all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @return the matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId);

	/**
	* Returns a range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByC_COC_First(
		long CPDefinitionId, long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByC_COC_First(
		long CPDefinitionId, long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByC_COC_Last(
		long CPDefinitionId, long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByC_COC_Last(
		long CPDefinitionId, long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue[] findByC_COC_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
		long CPOptionCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Removes all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	*/
	public void removeByC_COC(long CPDefinitionId, long CPOptionCategoryId);

	/**
	* Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @return the number of matching cp definition specification option values
	*/
	public int countByC_COC(long CPDefinitionId, long CPOptionCategoryId);

	/**
	* Caches the cp definition specification option value in the entity cache if it is enabled.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	*/
	public void cacheResult(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue);

	/**
	* Caches the cp definition specification option values in the entity cache if it is enabled.
	*
	* @param cpDefinitionSpecificationOptionValues the cp definition specification option values
	*/
	public void cacheResult(
		java.util.List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues);

	/**
	* Creates a new cp definition specification option value with the primary key. Does not add the cp definition specification option value to the database.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key for the new cp definition specification option value
	* @return the new cp definition specification option value
	*/
	public CPDefinitionSpecificationOptionValue create(
		long CPDefinitionSpecificationOptionValueId);

	/**
	* Removes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value that was removed
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue remove(
		long CPDefinitionSpecificationOptionValueId)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	public CPDefinitionSpecificationOptionValue updateImpl(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue);

	/**
	* Returns the cp definition specification option value with the primary key or throws a {@link NoSuchCPDefinitionSpecificationOptionValueException} if it could not be found.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue findByPrimaryKey(
		long CPDefinitionSpecificationOptionValueId)
		throws NoSuchCPDefinitionSpecificationOptionValueException;

	/**
	* Returns the cp definition specification option value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value, or <code>null</code> if a cp definition specification option value with the primary key could not be found
	*/
	public CPDefinitionSpecificationOptionValue fetchByPrimaryKey(
		long CPDefinitionSpecificationOptionValueId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionSpecificationOptionValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition specification option values.
	*
	* @return the cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findAll();

	/**
	* Returns a range of all the cp definition specification option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the cp definition specification option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition specification option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition specification option values
	*/
	public java.util.List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition specification option values from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition specification option values.
	*
	* @return the number of cp definition specification option values
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}