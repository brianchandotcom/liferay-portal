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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.LayoutFriendlyURL;

/**
 * The persistence interface for the layout friendly u r l service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.LayoutFriendlyURLPersistenceImpl
 * @see LayoutFriendlyURLUtil
 * @generated
 */
@ProviderType
public interface LayoutFriendlyURLPersistence extends BasePersistence<LayoutFriendlyURL> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutFriendlyURLUtil} to access the layout friendly u r l persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout friendly u r ls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the layout friendly u r ls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the first layout friendly u r l in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly u r l in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly u r l in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly u r l in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly u r ls before and after the current layout friendly u r l in the ordered set where uuid = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly u r l
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByUuid_PrevAndNext(
		long layoutFriendlyURLId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly u r ls where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of layout friendly u r ls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching layout friendly u r ls
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the layout friendly u r l where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchLayoutFriendlyURLException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly u r l where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the layout friendly u r l where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the layout friendly u r l where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the layout friendly u r l that was removed
	*/
	public LayoutFriendlyURL removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the number of layout friendly u r ls where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching layout friendly u r ls
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the layout friendly u r ls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the layout friendly u r ls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the first layout friendly u r l in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly u r l in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly u r l in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly u r l in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly u r ls before and after the current layout friendly u r l in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly u r l
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByUuid_C_PrevAndNext(
		long layoutFriendlyURLId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly u r ls where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of layout friendly u r ls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching layout friendly u r ls
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the layout friendly u r ls where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByGroupId(long groupId);

	/**
	* Returns a range of all the layout friendly u r ls where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the first layout friendly u r l in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly u r l in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly u r l in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly u r l in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly u r ls before and after the current layout friendly u r l in the ordered set where groupId = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly u r l
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByGroupId_PrevAndNext(
		long layoutFriendlyURLId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly u r ls where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout friendly u r ls where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout friendly u r ls
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the layout friendly u r ls where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByCompanyId(long companyId);

	/**
	* Returns a range of all the layout friendly u r ls where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the first layout friendly u r l in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly u r l in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly u r l in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly u r l in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly u r ls before and after the current layout friendly u r l in the ordered set where companyId = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly u r l
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByCompanyId_PrevAndNext(
		long layoutFriendlyURLId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly u r ls where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of layout friendly u r ls where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching layout friendly u r ls
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the layout friendly u r ls where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByPlid(long plid);

	/**
	* Returns a range of all the layout friendly u r ls where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByPlid(long plid, int start,
		int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByPlid(long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the first layout friendly u r l in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly u r l in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly u r l in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly u r l in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly u r ls before and after the current layout friendly u r l in the ordered set where plid = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly u r l
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByPlid_PrevAndNext(
		long layoutFriendlyURLId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly u r ls where plid = &#63; from the database.
	*
	* @param plid the plid
	*/
	public void removeByPlid(long plid);

	/**
	* Returns the number of layout friendly u r ls where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching layout friendly u r ls
	*/
	public int countByPlid(long plid);

	/**
	* Returns all the layout friendly u r ls where groupId = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @return the matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByG_F(long groupId,
		java.lang.String friendlyURL);

	/**
	* Returns a range of all the layout friendly u r ls where groupId = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByG_F(long groupId,
		java.lang.String friendlyURL, int start, int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls where groupId = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByG_F(long groupId,
		java.lang.String friendlyURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the first layout friendly u r l in the ordered set where groupId = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByG_F_First(long groupId,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly u r l in the ordered set where groupId = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByG_F_First(long groupId,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly u r l in the ordered set where groupId = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByG_F_Last(long groupId,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly u r l in the ordered set where groupId = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByG_F_Last(long groupId,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly u r ls before and after the current layout friendly u r l in the ordered set where groupId = &#63; and friendlyURL = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly u r l
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByG_F_PrevAndNext(long layoutFriendlyURLId,
		long groupId, java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly u r ls where groupId = &#63; and friendlyURL = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	*/
	public void removeByG_F(long groupId, java.lang.String friendlyURL);

	/**
	* Returns the number of layout friendly u r ls where groupId = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @return the number of matching layout friendly u r ls
	*/
	public int countByG_F(long groupId, java.lang.String friendlyURL);

	/**
	* Returns all the layout friendly u r ls where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @return the matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_F(long plid,
		java.lang.String friendlyURL);

	/**
	* Returns a range of all the layout friendly u r ls where plid = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_F(long plid,
		java.lang.String friendlyURL, int start, int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls where plid = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_F(long plid,
		java.lang.String friendlyURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the first layout friendly u r l in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByP_F_First(long plid,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly u r l in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByP_F_First(long plid,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly u r l in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByP_F_Last(long plid,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly u r l in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByP_F_Last(long plid,
		java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly u r ls before and after the current layout friendly u r l in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly u r l
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByP_F_PrevAndNext(long layoutFriendlyURLId,
		long plid, java.lang.String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly u r ls where plid = &#63; and friendlyURL = &#63; from the database.
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	*/
	public void removeByP_F(long plid, java.lang.String friendlyURL);

	/**
	* Returns the number of layout friendly u r ls where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly u r l
	* @return the number of matching layout friendly u r ls
	*/
	public int countByP_F(long plid, java.lang.String friendlyURL);

	/**
	* Returns the layout friendly u r l where plid = &#63; and languageId = &#63; or throws a {@link NoSuchLayoutFriendlyURLException} if it could not be found.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByP_L(long plid, java.lang.String languageId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly u r l where plid = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByP_L(long plid, java.lang.String languageId);

	/**
	* Returns the layout friendly u r l where plid = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByP_L(long plid, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the layout friendly u r l where plid = &#63; and languageId = &#63; from the database.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the layout friendly u r l that was removed
	*/
	public LayoutFriendlyURL removeByP_L(long plid, java.lang.String languageId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the number of layout friendly u r ls where plid = &#63; and languageId = &#63;.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the number of matching layout friendly u r ls
	*/
	public int countByP_L(long plid, java.lang.String languageId);

	/**
	* Returns the layout friendly u r l where groupId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a {@link NoSuchLayoutFriendlyURLException} if it could not be found.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param languageId the language ID
	* @return the matching layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL findByG_F_L(long groupId,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly u r l where groupId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param languageId the language ID
	* @return the matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByG_F_L(long groupId,
		java.lang.String friendlyURL, java.lang.String languageId);

	/**
	* Returns the layout friendly u r l where groupId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param languageId the language ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	public LayoutFriendlyURL fetchByG_F_L(long groupId,
		java.lang.String friendlyURL, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the layout friendly u r l where groupId = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param languageId the language ID
	* @return the layout friendly u r l that was removed
	*/
	public LayoutFriendlyURL removeByG_F_L(long groupId,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the number of layout friendly u r ls where groupId = &#63; and friendlyURL = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURL the friendly u r l
	* @param languageId the language ID
	* @return the number of matching layout friendly u r ls
	*/
	public int countByG_F_L(long groupId, java.lang.String friendlyURL,
		java.lang.String languageId);

	/**
	* Caches the layout friendly u r l in the entity cache if it is enabled.
	*
	* @param layoutFriendlyURL the layout friendly u r l
	*/
	public void cacheResult(LayoutFriendlyURL layoutFriendlyURL);

	/**
	* Caches the layout friendly u r ls in the entity cache if it is enabled.
	*
	* @param layoutFriendlyURLs the layout friendly u r ls
	*/
	public void cacheResult(
		java.util.List<LayoutFriendlyURL> layoutFriendlyURLs);

	/**
	* Creates a new layout friendly u r l with the primary key. Does not add the layout friendly u r l to the database.
	*
	* @param layoutFriendlyURLId the primary key for the new layout friendly u r l
	* @return the new layout friendly u r l
	*/
	public LayoutFriendlyURL create(long layoutFriendlyURLId);

	/**
	* Removes the layout friendly u r l with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly u r l
	* @return the layout friendly u r l that was removed
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL remove(long layoutFriendlyURLId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	public LayoutFriendlyURL updateImpl(LayoutFriendlyURL layoutFriendlyURL);

	/**
	* Returns the layout friendly u r l with the primary key or throws a {@link NoSuchLayoutFriendlyURLException} if it could not be found.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly u r l
	* @return the layout friendly u r l
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL findByPrimaryKey(long layoutFriendlyURLId)
		throws com.liferay.portal.NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly u r l with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly u r l
	* @return the layout friendly u r l, or <code>null</code> if a layout friendly u r l with the primary key could not be found
	*/
	public LayoutFriendlyURL fetchByPrimaryKey(long layoutFriendlyURLId);

	@Override
	public java.util.Map<java.io.Serializable, LayoutFriendlyURL> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout friendly u r ls.
	*
	* @return the layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findAll();

	/**
	* Returns a range of all the layout friendly u r ls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout friendly u r ls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout friendly u r ls
	*/
	public java.util.List<LayoutFriendlyURL> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Removes all the layout friendly u r ls from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout friendly u r ls.
	*
	* @return the number of layout friendly u r ls
	*/
	public int countAll();
}