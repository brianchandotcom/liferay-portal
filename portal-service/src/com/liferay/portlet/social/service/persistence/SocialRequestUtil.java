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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialRequest;

import java.util.List;

/**
 * The persistence utility for the social request service. This utility wraps {@link com.liferay.portlet.social.service.persistence.impl.SocialRequestPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestPersistence
 * @see com.liferay.portlet.social.service.persistence.impl.SocialRequestPersistenceImpl
 * @generated
 */
@ProviderType
public class SocialRequestUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(SocialRequest socialRequest) {
		getPersistence().clearCache(socialRequest);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SocialRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialRequest> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialRequest update(SocialRequest socialRequest) {
		return getPersistence().update(socialRequest);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialRequest update(SocialRequest socialRequest,
		ServiceContext serviceContext) {
		return getPersistence().update(socialRequest, serviceContext);
	}

	/**
	* Returns all the social requests where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<SocialRequest> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<SocialRequest> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByUuid_First(java.lang.String uuid,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByUuid_Last(java.lang.String uuid,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the social requests before and after the current social request in the ordered set where uuid = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public static SocialRequest[] findByUuid_PrevAndNext(long requestId,
		java.lang.String uuid,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByUuid_PrevAndNext(requestId, uuid, orderByComparator);
	}

	/**
	* Removes all the social requests where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of social requests where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching social requests
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the social request where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRequestException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the social request where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the social request where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the social request where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the social request that was removed
	*/
	public static SocialRequest removeByUUID_G(java.lang.String uuid,
		long groupId) throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of social requests where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching social requests
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the social requests where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

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
	public static SocialRequest[] findByUuid_C_PrevAndNext(long requestId,
		java.lang.String uuid, long companyId,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(requestId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the social requests where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of social requests where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching social requests
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the social requests where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

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
	public static List<SocialRequest> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

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
	public static List<SocialRequest> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByCompanyId_First(long companyId,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByCompanyId_First(long companyId,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByCompanyId_Last(long companyId,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByCompanyId_Last(long companyId,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the social requests before and after the current social request in the ordered set where companyId = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public static SocialRequest[] findByCompanyId_PrevAndNext(long requestId,
		long companyId, OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(requestId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the social requests where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of social requests where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social requests
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the social requests where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

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
	public static List<SocialRequest> findByUserId(long userId, int start,
		int end) {
		return getPersistence().findByUserId(userId, start, end);
	}

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
	public static List<SocialRequest> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findByUserId(long userId, int start,
		int end, OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByUserId_First(long userId,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUserId_First(long userId,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByUserId_Last(long userId,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByUserId_Last(long userId,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the social requests before and after the current social request in the ordered set where userId = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public static SocialRequest[] findByUserId_PrevAndNext(long requestId,
		long userId, OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByUserId_PrevAndNext(requestId, userId,
			orderByComparator);
	}

	/**
	* Removes all the social requests where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of social requests where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social requests
	*/
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the social requests where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByReceiverUserId(long receiverUserId) {
		return getPersistence().findByReceiverUserId(receiverUserId);
	}

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
	public static List<SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end) {
		return getPersistence().findByReceiverUserId(receiverUserId, start, end);
	}

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
	public static List<SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByReceiverUserId(receiverUserId, start, end,
			orderByComparator);
	}

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
	public static List<SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end,
		OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByReceiverUserId(receiverUserId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByReceiverUserId_First(
		long receiverUserId, OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByReceiverUserId_First(receiverUserId, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByReceiverUserId_First(
		long receiverUserId, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByReceiverUserId_First(receiverUserId,
			orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByReceiverUserId_Last(long receiverUserId,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByReceiverUserId_Last(receiverUserId, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByReceiverUserId_Last(
		long receiverUserId, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByReceiverUserId_Last(receiverUserId, orderByComparator);
	}

	/**
	* Returns the social requests before and after the current social request in the ordered set where receiverUserId = &#63;.
	*
	* @param requestId the primary key of the current social request
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public static SocialRequest[] findByReceiverUserId_PrevAndNext(
		long requestId, long receiverUserId,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByReceiverUserId_PrevAndNext(requestId, receiverUserId,
			orderByComparator);
	}

	/**
	* Removes all the social requests where receiverUserId = &#63; from the database.
	*
	* @param receiverUserId the receiver user ID
	*/
	public static void removeByReceiverUserId(long receiverUserId) {
		getPersistence().removeByReceiverUserId(receiverUserId);
	}

	/**
	* Returns the number of social requests where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the number of matching social requests
	*/
	public static int countByReceiverUserId(long receiverUserId) {
		return getPersistence().countByReceiverUserId(receiverUserId);
	}

	/**
	* Returns all the social requests where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByU_S(long userId, int status) {
		return getPersistence().findByU_S(userId, status);
	}

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
	public static List<SocialRequest> findByU_S(long userId, int status,
		int start, int end) {
		return getPersistence().findByU_S(userId, status, start, end);
	}

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
	public static List<SocialRequest> findByU_S(long userId, int status,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByU_S(userId, status, start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findByU_S(long userId, int status,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_S(userId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByU_S_First(long userId, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_S_First(userId, status, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByU_S_First(long userId, int status,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByU_S_First(userId, status, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByU_S_Last(long userId, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByU_S_Last(userId, status, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByU_S_Last(long userId, int status,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByU_S_Last(userId, status, orderByComparator);
	}

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
	public static SocialRequest[] findByU_S_PrevAndNext(long requestId,
		long userId, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_S_PrevAndNext(requestId, userId, status,
			orderByComparator);
	}

	/**
	* Removes all the social requests where userId = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param status the status
	*/
	public static void removeByU_S(long userId, int status) {
		getPersistence().removeByU_S(userId, status);
	}

	/**
	* Returns the number of social requests where userId = &#63; and status = &#63;.
	*
	* @param userId the user ID
	* @param status the status
	* @return the number of matching social requests
	*/
	public static int countByU_S(long userId, int status) {
		return getPersistence().countByU_S(userId, status);
	}

	/**
	* Returns all the social requests where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByC_C(long classNameId, long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

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
	public static List<SocialRequest> findByC_C(long classNameId, long classPK,
		int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

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
	public static List<SocialRequest> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

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
	public static List<SocialRequest> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByC_C_First(long classNameId, long classPK,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

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
	public static SocialRequest[] findByC_C_PrevAndNext(long requestId,
		long classNameId, long classPK,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_PrevAndNext(requestId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the social requests where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of social requests where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social requests
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the social requests where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @return the matching social requests
	*/
	public static List<SocialRequest> findByR_S(long receiverUserId, int status) {
		return getPersistence().findByR_S(receiverUserId, status);
	}

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
	public static List<SocialRequest> findByR_S(long receiverUserId,
		int status, int start, int end) {
		return getPersistence().findByR_S(receiverUserId, status, start, end);
	}

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
	public static List<SocialRequest> findByR_S(long receiverUserId,
		int status, int start, int end,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByR_S(receiverUserId, status, start, end,
			orderByComparator);
	}

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
	public static List<SocialRequest> findByR_S(long receiverUserId,
		int status, int start, int end,
		OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByR_S(receiverUserId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByR_S_First(long receiverUserId,
		int status, OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByR_S_First(receiverUserId, status, orderByComparator);
	}

	/**
	* Returns the first social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByR_S_First(long receiverUserId,
		int status, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByR_S_First(receiverUserId, status, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request
	* @throws NoSuchRequestException if a matching social request could not be found
	*/
	public static SocialRequest findByR_S_Last(long receiverUserId, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByR_S_Last(receiverUserId, status, orderByComparator);
	}

	/**
	* Returns the last social request in the ordered set where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social request, or <code>null</code> if a matching social request could not be found
	*/
	public static SocialRequest fetchByR_S_Last(long receiverUserId,
		int status, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByR_S_Last(receiverUserId, status, orderByComparator);
	}

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
	public static SocialRequest[] findByR_S_PrevAndNext(long requestId,
		long receiverUserId, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByR_S_PrevAndNext(requestId, receiverUserId, status,
			orderByComparator);
	}

	/**
	* Removes all the social requests where receiverUserId = &#63; and status = &#63; from the database.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	*/
	public static void removeByR_S(long receiverUserId, int status) {
		getPersistence().removeByR_S(receiverUserId, status);
	}

	/**
	* Returns the number of social requests where receiverUserId = &#63; and status = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param status the status
	* @return the number of matching social requests
	*/
	public static int countByR_S(long receiverUserId, int status) {
		return getPersistence().countByR_S(receiverUserId, status);
	}

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
	public static SocialRequest findByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

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
	public static SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId) {
		return getPersistence()
				   .fetchByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

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
	public static SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId, retrieveFromCache);
	}

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
	public static SocialRequest removeByU_C_C_T_R(long userId,
		long classNameId, long classPK, int type, long receiverUserId)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .removeByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

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
	public static int countByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId) {
		return getPersistence()
				   .countByU_C_C_T_R(userId, classNameId, classPK, type,
			receiverUserId);
	}

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
	public static List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status) {
		return getPersistence()
				   .findByU_C_C_T_S(userId, classNameId, classPK, type, status);
	}

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
	public static List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status, int start, int end) {
		return getPersistence()
				   .findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			start, end);
	}

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
	public static List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findByU_C_C_T_S(long userId,
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C_C_T_S(userId, classNameId, classPK, type, status,
			start, end, orderByComparator, retrieveFromCache);
	}

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
	public static SocialRequest findByU_C_C_T_S_First(long userId,
		long classNameId, long classPK, int type, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_S_First(userId, classNameId, classPK, type,
			status, orderByComparator);
	}

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
	public static SocialRequest fetchByU_C_C_T_S_First(long userId,
		long classNameId, long classPK, int type, int status,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_T_S_First(userId, classNameId, classPK, type,
			status, orderByComparator);
	}

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
	public static SocialRequest findByU_C_C_T_S_Last(long userId,
		long classNameId, long classPK, int type, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_S_Last(userId, classNameId, classPK, type,
			status, orderByComparator);
	}

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
	public static SocialRequest fetchByU_C_C_T_S_Last(long userId,
		long classNameId, long classPK, int type, int status,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_C_T_S_Last(userId, classNameId, classPK, type,
			status, orderByComparator);
	}

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
	public static SocialRequest[] findByU_C_C_T_S_PrevAndNext(long requestId,
		long userId, long classNameId, long classPK, int type, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByU_C_C_T_S_PrevAndNext(requestId, userId, classNameId,
			classPK, type, status, orderByComparator);
	}

	/**
	* Removes all the social requests where userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63; from the database.
	*
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param status the status
	*/
	public static void removeByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status) {
		getPersistence()
			.removeByU_C_C_T_S(userId, classNameId, classPK, type, status);
	}

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
	public static int countByU_C_C_T_S(long userId, long classNameId,
		long classPK, int type, int status) {
		return getPersistence()
				   .countByU_C_C_T_S(userId, classNameId, classPK, type, status);
	}

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
	public static List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status) {
		return getPersistence()
				   .findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status);
	}

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
	public static List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status, int start,
		int end) {
		return getPersistence()
				   .findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, start, end);
	}

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
	public static List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status, int start,
		int end, OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findByC_C_T_R_S(long classNameId,
		long classPK, int type, long receiverUserId, int status, int start,
		int end, OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status, start, end, orderByComparator, retrieveFromCache);
	}

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
	public static SocialRequest findByC_C_T_R_S_First(long classNameId,
		long classPK, int type, long receiverUserId, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_T_R_S_First(classNameId, classPK, type,
			receiverUserId, status, orderByComparator);
	}

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
	public static SocialRequest fetchByC_C_T_R_S_First(long classNameId,
		long classPK, int type, long receiverUserId, int status,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_R_S_First(classNameId, classPK, type,
			receiverUserId, status, orderByComparator);
	}

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
	public static SocialRequest findByC_C_T_R_S_Last(long classNameId,
		long classPK, int type, long receiverUserId, int status,
		OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_T_R_S_Last(classNameId, classPK, type,
			receiverUserId, status, orderByComparator);
	}

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
	public static SocialRequest fetchByC_C_T_R_S_Last(long classNameId,
		long classPK, int type, long receiverUserId, int status,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_R_S_Last(classNameId, classPK, type,
			receiverUserId, status, orderByComparator);
	}

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
	public static SocialRequest[] findByC_C_T_R_S_PrevAndNext(long requestId,
		long classNameId, long classPK, int type, long receiverUserId,
		int status, OrderByComparator<SocialRequest> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence()
				   .findByC_C_T_R_S_PrevAndNext(requestId, classNameId,
			classPK, type, receiverUserId, status, orderByComparator);
	}

	/**
	* Removes all the social requests where classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; and status = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param status the status
	*/
	public static void removeByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status) {
		getPersistence()
			.removeByC_C_T_R_S(classNameId, classPK, type, receiverUserId,
			status);
	}

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
	public static int countByC_C_T_R_S(long classNameId, long classPK,
		int type, long receiverUserId, int status) {
		return getPersistence()
				   .countByC_C_T_R_S(classNameId, classPK, type,
			receiverUserId, status);
	}

	/**
	* Caches the social request in the entity cache if it is enabled.
	*
	* @param socialRequest the social request
	*/
	public static void cacheResult(SocialRequest socialRequest) {
		getPersistence().cacheResult(socialRequest);
	}

	/**
	* Caches the social requests in the entity cache if it is enabled.
	*
	* @param socialRequests the social requests
	*/
	public static void cacheResult(List<SocialRequest> socialRequests) {
		getPersistence().cacheResult(socialRequests);
	}

	/**
	* Creates a new social request with the primary key. Does not add the social request to the database.
	*
	* @param requestId the primary key for the new social request
	* @return the new social request
	*/
	public static SocialRequest create(long requestId) {
		return getPersistence().create(requestId);
	}

	/**
	* Removes the social request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param requestId the primary key of the social request
	* @return the social request that was removed
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public static SocialRequest remove(long requestId)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().remove(requestId);
	}

	public static SocialRequest updateImpl(SocialRequest socialRequest) {
		return getPersistence().updateImpl(socialRequest);
	}

	/**
	* Returns the social request with the primary key or throws a {@link NoSuchRequestException} if it could not be found.
	*
	* @param requestId the primary key of the social request
	* @return the social request
	* @throws NoSuchRequestException if a social request with the primary key could not be found
	*/
	public static SocialRequest findByPrimaryKey(long requestId)
		throws com.liferay.portlet.social.NoSuchRequestException {
		return getPersistence().findByPrimaryKey(requestId);
	}

	/**
	* Returns the social request with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param requestId the primary key of the social request
	* @return the social request, or <code>null</code> if a social request with the primary key could not be found
	*/
	public static SocialRequest fetchByPrimaryKey(long requestId) {
		return getPersistence().fetchByPrimaryKey(requestId);
	}

	public static java.util.Map<java.io.Serializable, SocialRequest> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the social requests.
	*
	* @return the social requests
	*/
	public static List<SocialRequest> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<SocialRequest> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<SocialRequest> findAll(int start, int end,
		OrderByComparator<SocialRequest> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<SocialRequest> findAll(int start, int end,
		OrderByComparator<SocialRequest> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the social requests from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social requests.
	*
	* @return the number of social requests
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SocialRequestPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialRequestPersistence)PortalBeanLocatorUtil.locate(SocialRequestPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialRequestUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SocialRequestPersistence _persistence;
}