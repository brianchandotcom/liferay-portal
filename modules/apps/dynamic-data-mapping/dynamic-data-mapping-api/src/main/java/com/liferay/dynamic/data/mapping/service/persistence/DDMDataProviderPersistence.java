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

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMDataProvider;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m data provider service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMDataProviderPersistenceImpl
 * @see DDMDataProviderUtil
 * @generated
 */
@ProviderType
public interface DDMDataProviderPersistence extends BasePersistence<DDMDataProvider> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderUtil} to access the d d m data provider persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m data providers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the d d m data providers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public DDMDataProvider[] findByUuid_PrevAndNext(long dataProviderId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Removes all the d d m data providers where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of d d m data providers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m data providers
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the d d m data provider where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the d d m data provider where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the d d m data provider where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the d d m data provider where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m data provider that was removed
	*/
	public DDMDataProvider removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the number of d d m data providers where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m data providers
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public DDMDataProvider[] findByUuid_C_PrevAndNext(long dataProviderId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Removes all the d d m data providers where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m data providers
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the d d m data providers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long groupId);

	/**
	* Returns a range of all the d d m data providers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m data providers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data providers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the first d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the last d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the last d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public DDMDataProvider[] findByGroupId_PrevAndNext(long dataProviderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns all the d d m data providers where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long[] groupIds);

	/**
	* Returns a range of all the d d m data providers where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long[] groupIds,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m data providers where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data providers where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m data providers where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of d d m data providers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m data providers
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of d d m data providers where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m data providers
	*/
	public int countByGroupId(long[] groupIds);

	/**
	* Returns all the d d m data providers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByCompanyId(long companyId);

	/**
	* Returns a range of all the d d m data providers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m data providers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data providers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public java.util.List<DDMDataProvider> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the first d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the last d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public DDMDataProvider findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the last d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public DDMDataProvider fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public DDMDataProvider[] findByCompanyId_PrevAndNext(long dataProviderId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Removes all the d d m data providers where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of d d m data providers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching d d m data providers
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the d d m data provider in the entity cache if it is enabled.
	*
	* @param ddmDataProvider the d d m data provider
	*/
	public void cacheResult(DDMDataProvider ddmDataProvider);

	/**
	* Caches the d d m data providers in the entity cache if it is enabled.
	*
	* @param ddmDataProviders the d d m data providers
	*/
	public void cacheResult(java.util.List<DDMDataProvider> ddmDataProviders);

	/**
	* Creates a new d d m data provider with the primary key. Does not add the d d m data provider to the database.
	*
	* @param dataProviderId the primary key for the new d d m data provider
	* @return the new d d m data provider
	*/
	public DDMDataProvider create(long dataProviderId);

	/**
	* Removes the d d m data provider with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public DDMDataProvider remove(long dataProviderId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	public DDMDataProvider updateImpl(DDMDataProvider ddmDataProvider);

	/**
	* Returns the d d m data provider with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderException} if it could not be found.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public DDMDataProvider findByPrimaryKey(long dataProviderId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;

	/**
	* Returns the d d m data provider with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider, or <code>null</code> if a d d m data provider with the primary key could not be found
	*/
	public DDMDataProvider fetchByPrimaryKey(long dataProviderId);

	@Override
	public java.util.Map<java.io.Serializable, DDMDataProvider> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m data providers.
	*
	* @return the d d m data providers
	*/
	public java.util.List<DDMDataProvider> findAll();

	/**
	* Returns a range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of d d m data providers
	*/
	public java.util.List<DDMDataProvider> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m data providers
	*/
	public java.util.List<DDMDataProvider> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator);

	/**
	* Returns an ordered range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m data providers
	*/
	public java.util.List<DDMDataProvider> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m data providers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m data providers.
	*
	* @return the number of d d m data providers
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}