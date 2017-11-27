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

import com.liferay.commerce.product.exception.NoSuchCPGroupException;
import com.liferay.commerce.product.model.CPGroup;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPGroupPersistenceImpl
 * @see CPGroupUtil
 * @generated
 */
@ProviderType
public interface CPGroupPersistence extends BasePersistence<CPGroup> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPGroupUtil} to access the cp group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the cp groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid(java.lang.String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator);

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public CPGroup findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator)
		throws NoSuchCPGroupException;

	/**
	* Returns the first cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator);

	/**
	* Returns the last cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public CPGroup findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator)
		throws NoSuchCPGroupException;

	/**
	* Returns the last cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator);

	/**
	* Returns the cp groups before and after the current cp group in the ordered set where uuid = &#63;.
	*
	* @param CPGroupId the primary key of the current cp group
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp group
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public CPGroup[] findByUuid_PrevAndNext(long CPGroupId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator)
		throws NoSuchCPGroupException;

	/**
	* Removes all the cp groups where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp groups
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp group where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPGroupException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public CPGroup findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPGroupException;

	/**
	* Returns the cp group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp group where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp group that was removed
	*/
	public CPGroup removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPGroupException;

	/**
	* Returns the number of cp groups where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp groups
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator);

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp groups
	*/
	public java.util.List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public CPGroup findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator)
		throws NoSuchCPGroupException;

	/**
	* Returns the first cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator);

	/**
	* Returns the last cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public CPGroup findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator)
		throws NoSuchCPGroupException;

	/**
	* Returns the last cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator);

	/**
	* Returns the cp groups before and after the current cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPGroupId the primary key of the current cp group
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp group
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public CPGroup[] findByUuid_C_PrevAndNext(long CPGroupId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator)
		throws NoSuchCPGroupException;

	/**
	* Removes all the cp groups where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp groups
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the cp group where groupId = &#63; or throws a {@link NoSuchCPGroupException} if it could not be found.
	*
	* @param groupId the group ID
	* @return the matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public CPGroup findByGroupId(long groupId) throws NoSuchCPGroupException;

	/**
	* Returns the cp group where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByGroupId(long groupId);

	/**
	* Returns the cp group where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public CPGroup fetchByGroupId(long groupId, boolean retrieveFromCache);

	/**
	* Removes the cp group where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @return the cp group that was removed
	*/
	public CPGroup removeByGroupId(long groupId) throws NoSuchCPGroupException;

	/**
	* Returns the number of cp groups where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp groups
	*/
	public int countByGroupId(long groupId);

	/**
	* Caches the cp group in the entity cache if it is enabled.
	*
	* @param cpGroup the cp group
	*/
	public void cacheResult(CPGroup cpGroup);

	/**
	* Caches the cp groups in the entity cache if it is enabled.
	*
	* @param cpGroups the cp groups
	*/
	public void cacheResult(java.util.List<CPGroup> cpGroups);

	/**
	* Creates a new cp group with the primary key. Does not add the cp group to the database.
	*
	* @param CPGroupId the primary key for the new cp group
	* @return the new cp group
	*/
	public CPGroup create(long CPGroupId);

	/**
	* Removes the cp group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group that was removed
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public CPGroup remove(long CPGroupId) throws NoSuchCPGroupException;

	public CPGroup updateImpl(CPGroup cpGroup);

	/**
	* Returns the cp group with the primary key or throws a {@link NoSuchCPGroupException} if it could not be found.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public CPGroup findByPrimaryKey(long CPGroupId)
		throws NoSuchCPGroupException;

	/**
	* Returns the cp group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group, or <code>null</code> if a cp group with the primary key could not be found
	*/
	public CPGroup fetchByPrimaryKey(long CPGroupId);

	@Override
	public java.util.Map<java.io.Serializable, CPGroup> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp groups.
	*
	* @return the cp groups
	*/
	public java.util.List<CPGroup> findAll();

	/**
	* Returns a range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of cp groups
	*/
	public java.util.List<CPGroup> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp groups
	*/
	public java.util.List<CPGroup> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator);

	/**
	* Returns an ordered range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp groups
	*/
	public java.util.List<CPGroup> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPGroup> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp groups from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp groups.
	*
	* @return the number of cp groups
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}