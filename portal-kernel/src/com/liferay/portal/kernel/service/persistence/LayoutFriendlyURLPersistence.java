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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchLayoutFriendlyURLException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;

/**
 * The persistence interface for the layout friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutFriendlyURLUtil
 * @generated
 */
@ProviderType
public interface LayoutFriendlyURLPersistence extends BasePersistence<LayoutFriendlyURL> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutFriendlyURLUtil} to access the layout friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid(String uuid);

	/**
	* Returns a range of all the layout friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the layout friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly urls before and after the current layout friendly url in the ordered set where uuid = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly url
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByUuid_PrevAndNext(
		long layoutFriendlyURLId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly urls where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of layout friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching layout friendly urls
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the layout friendly url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLayoutFriendlyURLException</code> if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByUUID_G(String uuid, long groupId)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the layout friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the layout friendly url where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the layout friendly url that was removed
	*/
	public LayoutFriendlyURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the number of layout friendly urls where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching layout friendly urls
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the layout friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the layout friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the layout friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly urls before and after the current layout friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly url
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByUuid_C_PrevAndNext(
		long layoutFriendlyURLId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly urls where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of layout friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching layout friendly urls
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the layout friendly urls where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByGroupId(long groupId);

	/**
	* Returns a range of all the layout friendly urls where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the layout friendly urls where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout friendly url in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly url in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly url in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly url in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly urls before and after the current layout friendly url in the ordered set where groupId = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly url
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByGroupId_PrevAndNext(
		long layoutFriendlyURLId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly urls where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout friendly urls where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout friendly urls
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the layout friendly urls where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByCompanyId(long companyId);

	/**
	* Returns a range of all the layout friendly urls where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the layout friendly urls where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout friendly url in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly url in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly url in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly url in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly urls before and after the current layout friendly url in the ordered set where companyId = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly url
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByCompanyId_PrevAndNext(
		long layoutFriendlyURLId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly urls where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of layout friendly urls where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching layout friendly urls
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the layout friendly urls where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByPlid(long plid);

	/**
	* Returns a range of all the layout friendly urls where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByPlid(long plid, int start,
		int end);

	/**
	* Returns an ordered range of all the layout friendly urls where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByPlid(long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByPlid(long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout friendly url in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly url in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly url in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly url in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly urls before and after the current layout friendly url in the ordered set where plid = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly url
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByPlid_PrevAndNext(
		long layoutFriendlyURLId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly urls where plid = &#63; from the database.
	*
	* @param plid the plid
	*/
	public void removeByPlid(long plid);

	/**
	* Returns the number of layout friendly urls where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching layout friendly urls
	*/
	public int countByPlid(long plid);

	/**
	* Returns all the layout friendly urls where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_F(long plid,
		String friendlyURL);

	/**
	* Returns a range of all the layout friendly urls where plid = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_F(long plid,
		String friendlyURL, int start, int end);

	/**
	* Returns an ordered range of all the layout friendly urls where plid = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_F(long plid,
		String friendlyURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where plid = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_F(long plid,
		String friendlyURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout friendly url in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByP_F_First(long plid, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly url in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByP_F_First(long plid, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly url in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByP_F_Last(long plid, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly url in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByP_F_Last(long plid, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly urls before and after the current layout friendly url in the ordered set where plid = &#63; and friendlyURL = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly url
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByP_F_PrevAndNext(long layoutFriendlyURLId,
		long plid, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly urls where plid = &#63; and friendlyURL = &#63; from the database.
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	*/
	public void removeByP_F(long plid, String friendlyURL);

	/**
	* Returns the number of layout friendly urls where plid = &#63; and friendlyURL = &#63;.
	*
	* @param plid the plid
	* @param friendlyURL the friendly url
	* @return the number of matching layout friendly urls
	*/
	public int countByP_F(long plid, String friendlyURL);

	/**
	* Returns all the layout friendly urls where plid = any &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plids the plids
	* @param languageId the language ID
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_L(long[] plids,
		String languageId);

	/**
	* Returns a range of all the layout friendly urls where plid = any &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plids the plids
	* @param languageId the language ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_L(long[] plids,
		String languageId, int start, int end);

	/**
	* Returns an ordered range of all the layout friendly urls where plid = any &#63; and languageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plids the plids
	* @param languageId the language ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_L(long[] plids,
		String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where plid = &#63; and languageId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param languageId the language ID
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByP_L(long[] plids,
		String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the layout friendly url where plid = &#63; and languageId = &#63; or throws a <code>NoSuchLayoutFriendlyURLException</code> if it could not be found.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByP_L(long plid, String languageId)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly url where plid = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByP_L(long plid, String languageId);

	/**
	* Returns the layout friendly url where plid = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByP_L(long plid, String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the layout friendly url where plid = &#63; and languageId = &#63; from the database.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the layout friendly url that was removed
	*/
	public LayoutFriendlyURL removeByP_L(long plid, String languageId)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the number of layout friendly urls where plid = &#63; and languageId = &#63;.
	*
	* @param plid the plid
	* @param languageId the language ID
	* @return the number of matching layout friendly urls
	*/
	public int countByP_L(long plid, String languageId);

	/**
	* Returns the number of layout friendly urls where plid = any &#63; and languageId = &#63;.
	*
	* @param plids the plids
	* @param languageId the language ID
	* @return the number of matching layout friendly urls
	*/
	public int countByP_L(long[] plids, String languageId);

	/**
	* Returns all the layout friendly urls where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @return the matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByG_P_F(long groupId,
		boolean privateLayout, String friendlyURL);

	/**
	* Returns a range of all the layout friendly urls where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByG_P_F(long groupId,
		boolean privateLayout, String friendlyURL, int start, int end);

	/**
	* Returns an ordered range of all the layout friendly urls where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByG_P_F(long groupId,
		boolean privateLayout, String friendlyURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findByG_P_F(long groupId,
		boolean privateLayout, String friendlyURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout friendly url in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByG_P_F_First(long groupId,
		boolean privateLayout, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the first layout friendly url in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByG_P_F_First(long groupId,
		boolean privateLayout, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the last layout friendly url in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByG_P_F_Last(long groupId,
		boolean privateLayout, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the last layout friendly url in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByG_P_F_Last(long groupId,
		boolean privateLayout, String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns the layout friendly urls before and after the current layout friendly url in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param layoutFriendlyURLId the primary key of the current layout friendly url
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL[] findByG_P_F_PrevAndNext(
		long layoutFriendlyURLId, long groupId, boolean privateLayout,
		String friendlyURL,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Removes all the layout friendly urls where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	*/
	public void removeByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL);

	/**
	* Returns the number of layout friendly urls where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @return the number of matching layout friendly urls
	*/
	public int countByG_P_F(long groupId, boolean privateLayout,
		String friendlyURL);

	/**
	* Returns the layout friendly url where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a <code>NoSuchLayoutFriendlyURLException</code> if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the matching layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL findByG_P_F_L(long groupId, boolean privateLayout,
		String friendlyURL, String languageId)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly url where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByG_P_F_L(long groupId,
		boolean privateLayout, String friendlyURL, String languageId);

	/**
	* Returns the layout friendly url where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	public LayoutFriendlyURL fetchByG_P_F_L(long groupId,
		boolean privateLayout, String friendlyURL, String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the layout friendly url where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the layout friendly url that was removed
	*/
	public LayoutFriendlyURL removeByG_P_F_L(long groupId,
		boolean privateLayout, String friendlyURL, String languageId)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the number of layout friendly urls where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the number of matching layout friendly urls
	*/
	public int countByG_P_F_L(long groupId, boolean privateLayout,
		String friendlyURL, String languageId);

	/**
	* Caches the layout friendly url in the entity cache if it is enabled.
	*
	* @param layoutFriendlyURL the layout friendly url
	*/
	public void cacheResult(LayoutFriendlyURL layoutFriendlyURL);

	/**
	* Caches the layout friendly urls in the entity cache if it is enabled.
	*
	* @param layoutFriendlyURLs the layout friendly urls
	*/
	public void cacheResult(
		java.util.List<LayoutFriendlyURL> layoutFriendlyURLs);

	/**
	* Creates a new layout friendly url with the primary key. Does not add the layout friendly url to the database.
	*
	* @param layoutFriendlyURLId the primary key for the new layout friendly url
	* @return the new layout friendly url
	*/
	public LayoutFriendlyURL create(long layoutFriendlyURLId);

	/**
	* Removes the layout friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly url
	* @return the layout friendly url that was removed
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL remove(long layoutFriendlyURLId)
		throws NoSuchLayoutFriendlyURLException;

	public LayoutFriendlyURL updateImpl(LayoutFriendlyURL layoutFriendlyURL);

	/**
	* Returns the layout friendly url with the primary key or throws a <code>NoSuchLayoutFriendlyURLException</code> if it could not be found.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly url
	* @return the layout friendly url
	* @throws NoSuchLayoutFriendlyURLException if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL findByPrimaryKey(long layoutFriendlyURLId)
		throws NoSuchLayoutFriendlyURLException;

	/**
	* Returns the layout friendly url with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly url
	* @return the layout friendly url, or <code>null</code> if a layout friendly url with the primary key could not be found
	*/
	public LayoutFriendlyURL fetchByPrimaryKey(long layoutFriendlyURLId);

	/**
	* Returns all the layout friendly urls.
	*
	* @return the layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findAll();

	/**
	* Returns a range of all the layout friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the layout friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutFriendlyURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout friendly urls
	*/
	public java.util.List<LayoutFriendlyURL> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout friendly urls from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout friendly urls.
	*
	* @return the number of layout friendly urls
	*/
	public int countAll();
}