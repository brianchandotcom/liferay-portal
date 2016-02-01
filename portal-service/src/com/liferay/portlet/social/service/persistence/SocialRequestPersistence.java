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

package com.liferay.portlet.social.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.exception.NoSuchRequestException;
import com.liferay.portlet.social.model.SocialRequest;

/**
 * The persistence interface for the social request service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.persistence.impl.SocialRequestPersistenceImpl
 * @see SocialRequestUtil
 * @generated
 */
@ProviderType
public interface SocialRequestPersistence extends BasePersistence<SocialRequest> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialRequestUtil} to access the social request persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social requests where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the social requests where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the social requests where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where uuid = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByUuid_PrevAndNext(long requestId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of social requests where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching social requests
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the social request where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRequestException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchRequestException;

	/**
	* Returns the social request where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the social request where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the social request where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the social request that was removed
	*/
	public SocialRequest removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchRequestException;

	/**
	* Returns the number of social requests where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching social requests
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the social requests where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the social requests where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the social requests where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByUuid_C_PrevAndNext(long requestId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of social requests where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching social requests
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the social requests where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByCompanyId(long companyId);

	/**
	* Returns a range of all the social requests where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the social requests where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where companyId = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByCompanyId_PrevAndNext(long requestId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of social requests where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social requests
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the social requests where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByUserId(long userId);

	/**
	* Returns a range of all the social requests where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUserId(long userId, int start,
		int end);

	/**
	* Returns an ordered range of all the social requests where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where userId = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByUserId_PrevAndNext(long requestId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of social requests where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social requests
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the social requests where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByReceiverUserId(
		long receiverUserId);

	/**
	* Returns a range of all the social requests where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end);

	/**
	* Returns an ordered range of all the social requests where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByReceiverUserId_First(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByReceiverUserId_First(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByReceiverUserId_Last(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByReceiverUserId_Last(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where receiverUserId = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByReceiverUserId_PrevAndNext(long requestId,
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where receiverUserId = &#63; from the database.
	*
	* @param receiverUserId the receiver user ID
	*/
	public void removeByReceiverUserId(long receiverUserId);

	/**
	* Returns the number of social requests where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the number of matching social requests
	*/
	public int countByReceiverUserId(long receiverUserId);

	/**
	* Returns all the social requests where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByU_S(long userId, int status);

	/**
	* Returns a range of all the social requests where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByU_S(long userId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the social requests where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByU_S(long userId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByU_S(long userId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByU_S_First(long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByU_S_First(long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByU_S_Last(long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByU_S_Last(long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByU_S_PrevAndNext(long requestId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where userId = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param status the status
	*/
	public void removeByU_S(long userId, int status);

	/**
	* Returns the number of social requests where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the number of matching social requests
	*/
	public int countByU_S(long userId, int status);

	/**
	* Returns all the social requests where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the social requests where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the social requests where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByC_C_PrevAndNext(long requestId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of social requests where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social requests
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns all the social requests where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByR_S(long receiverUserId,
		int status);

	/**
	* Returns a range of all the social requests where receiverUserId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByR_S(long receiverUserId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the social requests where receiverUserId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByR_S(long receiverUserId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where receiverUserId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByR_S(long receiverUserId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByR_S_First(long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByR_S_First(long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByR_S_Last(long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByR_S_Last(long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByR_S_PrevAndNext(long requestId,
		long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where receiverUserId = &#63; and status = &#63; from the database.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	*/
	public void removeByR_S(long receiverUserId, int status);

	/**
	* Returns the number of social requests where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @return the number of matching social requests
	*/
	public int countByR_S(long receiverUserId, int status);

	/**
	* Returns the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or throws a {@link NoSuchRequestException} if it could not be found.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws NoSuchRequestException;

	/**
	* Returns the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId);

	/**
	* Returns the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId, boolean retrieveFromCache);

	/**
	* Removes the social request where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the social request that was removed
	*/
	public SocialRequest removeByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws NoSuchRequestException;

	/**
	* Returns the number of social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the number of matching social requests
	*/
	public int countByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId);

	/**
	* Returns all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status);

	/**
	* Returns a range of all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status, int start, int end);

	/**
	* Returns an ordered range of all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByU_C_C_T_S_First(long userId, long classNameId,
		long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByU_C_C_T_S_First(long userId, long classNameId,
		long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByU_C_C_T_S_Last(long userId, long classNameId,
		long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByU_C_C_T_S_Last(long userId, long classNameId,
		long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByU_C_C_T_S_PrevAndNext(long requestId,
		long userId, long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	*/
	public void removeByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status);

	/**
	* Returns the number of social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	* @return the number of matching social requests
	*/
	public int countByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status);

	/**
	* Returns all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @return the matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status);

	/**
	* Returns a range of all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status, int start,
		int end);

	/**
	* Returns an ordered range of all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social requests
	*/
	public java.util.List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByC_C_T_R_S_First(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByC_C_T_R_S_First(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public SocialRequest findByC_C_T_R_S_Last(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public SocialRequest fetchByC_C_T_R_S_Last(long classNameId, long classPK,
		int type, long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns the social requests before and after the current social request in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest[] findByC_C_T_R_S_PrevAndNext(long requestId,
		long classNameId, long classPK, int type, long receiverUserId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator)
		throws NoSuchRequestException;

	/**
	* Removes all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	*/
	public void removeByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status);

	/**
	* Returns the number of social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @return the number of matching social requests
	*/
	public int countByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status);

	/**
	* Caches the social request in the entity cache if it is enabled.
	*
	* @param socialRequest the social request
	*/
	public void cacheResult(SocialRequest socialRequest);

	/**
	* Caches the social requests in the entity cache if it is enabled.
	*
	* @param socialRequests the social requests
	*/
	public void cacheResult(java.util.List<SocialRequest> socialRequests);

	/**
	* Creates a new social request with the primary key. Does not add the social request to the database.
	*
	* @param requestId the primary key for the new social request
	* @return the new social request
	*/
	public SocialRequest create(long requestId);

	/**
	* Removes the social request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param requestId the primary key of the social request
	* @return the social request that was removed
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest remove(long requestId) throws NoSuchRequestException;

	public SocialRequest updateImpl(SocialRequest socialRequest);

	/**
	* Returns the social request with the primary key or throws a {@link NoSuchRequestException} if it could not be found.
	*
	* @param requestId the primary key of the social request
	* @return the social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public SocialRequest findByPrimaryKey(long requestId)
		throws NoSuchRequestException;

	/**
	* Returns the social request with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param requestId the primary key of the social request
	* @return the social request, or <code>null</code> if a social request with the primary key could not be found
	*/
	public SocialRequest fetchByPrimaryKey(long requestId);

	@Override
	public java.util.Map<java.io.Serializable, SocialRequest> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the social requests.
	*
	* @return the social requests
	*/
	public java.util.List<SocialRequest> findAll();

	/**
	* Returns a range of all the social requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @return the range of social requests
	*/
	public java.util.List<SocialRequest> findAll(int start, int end);

	/**
	* Returns an ordered range of all the social requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social requests
	*/
	public java.util.List<SocialRequest> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator);

	/**
	* Returns an ordered range of all the social requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social requests
	* @param end the upper bound of the range of social requests (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social requests
	*/
	public java.util.List<SocialRequest> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the social requests from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of social requests.
	*
	* @return the number of social requests
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}