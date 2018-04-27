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

import com.liferay.commerce.product.model.CPOptionValue;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp option value service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPOptionValuePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPOptionValuePersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPOptionValuePersistenceImpl
 * @generated
 */
@ProviderType
public class CPOptionValueUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CPOptionValue cpOptionValue) {
		getPersistence().clearCache(cpOptionValue);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPOptionValue update(CPOptionValue cpOptionValue) {
		return getPersistence().update(cpOptionValue);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPOptionValue update(CPOptionValue cpOptionValue,
		ServiceContext serviceContext) {
		return getPersistence().update(cpOptionValue, serviceContext);
	}

	/**
	* Returns all the cp option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp option values
	*/
	public static List<CPOptionValue> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public static List<CPOptionValue> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByUuid_First(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByUuid_First(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByUuid_Last(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByUuid_Last(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where uuid = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue[] findByUuid_PrevAndNext(long CPOptionValueId,
		String uuid, OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPOptionValueId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp option values where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp option values
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp option value where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp option value where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp option value that was removed
	*/
	public static CPOptionValue removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp option values where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp option values
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp option values
	*/
	public static List<CPOptionValue> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public static List<CPOptionValue> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue[] findByUuid_C_PrevAndNext(
		long CPOptionValueId, String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPOptionValueId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the cp option values where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp option values
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp option values
	*/
	public static List<CPOptionValue> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public static List<CPOptionValue> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByGroupId_First(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByGroupId_First(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByGroupId_Last(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByGroupId_Last(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where groupId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue[] findByGroupId_PrevAndNext(
		long CPOptionValueId, long groupId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPOptionValueId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cp option values where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp option values
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the cp option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp option values
	*/
	public static List<CPOptionValue> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the cp option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public static List<CPOptionValue> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByCompanyId_First(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByCompanyId_First(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByCompanyId_Last(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByCompanyId_Last(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where companyId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue[] findByCompanyId_PrevAndNext(
		long CPOptionValueId, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(CPOptionValueId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the cp option values where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of cp option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp option values
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the cp option values where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @return the matching cp option values
	*/
	public static List<CPOptionValue> findByCPOptionId(long CPOptionId) {
		return getPersistence().findByCPOptionId(CPOptionId);
	}

	/**
	* Returns a range of all the cp option values where CPOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionId the cp option ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of matching cp option values
	*/
	public static List<CPOptionValue> findByCPOptionId(long CPOptionId,
		int start, int end) {
		return getPersistence().findByCPOptionId(CPOptionId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionId the cp option ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByCPOptionId(long CPOptionId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .findByCPOptionId(CPOptionId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionId the cp option ID
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option values
	*/
	public static List<CPOptionValue> findByCPOptionId(long CPOptionId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPOptionId(CPOptionId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByCPOptionId_First(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByCPOptionId_First(CPOptionId, orderByComparator);
	}

	/**
	* Returns the first cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByCPOptionId_First(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPOptionId_First(CPOptionId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByCPOptionId_Last(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByCPOptionId_Last(CPOptionId, orderByComparator);
	}

	/**
	* Returns the last cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByCPOptionId_Last(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPOptionId_Last(CPOptionId, orderByComparator);
	}

	/**
	* Returns the cp option values before and after the current cp option value in the ordered set where CPOptionId = &#63;.
	*
	* @param CPOptionValueId the primary key of the current cp option value
	* @param CPOptionId the cp option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue[] findByCPOptionId_PrevAndNext(
		long CPOptionValueId, long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence()
				   .findByCPOptionId_PrevAndNext(CPOptionValueId, CPOptionId,
			orderByComparator);
	}

	/**
	* Removes all the cp option values where CPOptionId = &#63; from the database.
	*
	* @param CPOptionId the cp option ID
	*/
	public static void removeByCPOptionId(long CPOptionId) {
		getPersistence().removeByCPOptionId(CPOptionId);
	}

	/**
	* Returns the number of cp option values where CPOptionId = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @return the number of matching cp option values
	*/
	public static int countByCPOptionId(long CPOptionId) {
		return getPersistence().countByCPOptionId(CPOptionId);
	}

	/**
	* Returns the cp option value where CPOptionId = &#63; and key = &#63; or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the matching cp option value
	* @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	*/
	public static CPOptionValue findByC_K(long CPOptionId, String key)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().findByC_K(CPOptionId, key);
	}

	/**
	* Returns the cp option value where CPOptionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByC_K(long CPOptionId, String key) {
		return getPersistence().fetchByC_K(CPOptionId, key);
	}

	/**
	* Returns the cp option value where CPOptionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	public static CPOptionValue fetchByC_K(long CPOptionId, String key,
		boolean retrieveFromCache) {
		return getPersistence().fetchByC_K(CPOptionId, key, retrieveFromCache);
	}

	/**
	* Removes the cp option value where CPOptionId = &#63; and key = &#63; from the database.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the cp option value that was removed
	*/
	public static CPOptionValue removeByC_K(long CPOptionId, String key)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().removeByC_K(CPOptionId, key);
	}

	/**
	* Returns the number of cp option values where CPOptionId = &#63; and key = &#63;.
	*
	* @param CPOptionId the cp option ID
	* @param key the key
	* @return the number of matching cp option values
	*/
	public static int countByC_K(long CPOptionId, String key) {
		return getPersistence().countByC_K(CPOptionId, key);
	}

	/**
	* Caches the cp option value in the entity cache if it is enabled.
	*
	* @param cpOptionValue the cp option value
	*/
	public static void cacheResult(CPOptionValue cpOptionValue) {
		getPersistence().cacheResult(cpOptionValue);
	}

	/**
	* Caches the cp option values in the entity cache if it is enabled.
	*
	* @param cpOptionValues the cp option values
	*/
	public static void cacheResult(List<CPOptionValue> cpOptionValues) {
		getPersistence().cacheResult(cpOptionValues);
	}

	/**
	* Creates a new cp option value with the primary key. Does not add the cp option value to the database.
	*
	* @param CPOptionValueId the primary key for the new cp option value
	* @return the new cp option value
	*/
	public static CPOptionValue create(long CPOptionValueId) {
		return getPersistence().create(CPOptionValueId);
	}

	/**
	* Removes the cp option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value that was removed
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue remove(long CPOptionValueId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().remove(CPOptionValueId);
	}

	public static CPOptionValue updateImpl(CPOptionValue cpOptionValue) {
		return getPersistence().updateImpl(cpOptionValue);
	}

	/**
	* Returns the cp option value with the primary key or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value
	* @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue findByPrimaryKey(long CPOptionValueId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionValueException {
		return getPersistence().findByPrimaryKey(CPOptionValueId);
	}

	/**
	* Returns the cp option value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value, or <code>null</code> if a cp option value with the primary key could not be found
	*/
	public static CPOptionValue fetchByPrimaryKey(long CPOptionValueId) {
		return getPersistence().fetchByPrimaryKey(CPOptionValueId);
	}

	public static java.util.Map<java.io.Serializable, CPOptionValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp option values.
	*
	* @return the cp option values
	*/
	public static List<CPOptionValue> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of cp option values
	*/
	public static List<CPOptionValue> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp option values
	*/
	public static List<CPOptionValue> findAll(int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp option values
	*/
	public static List<CPOptionValue> findAll(int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp option values from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp option values.
	*
	* @return the number of cp option values
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPOptionValuePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionValuePersistence, CPOptionValuePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionValuePersistence.class);

		ServiceTracker<CPOptionValuePersistence, CPOptionValuePersistence> serviceTracker =
			new ServiceTracker<CPOptionValuePersistence, CPOptionValuePersistence>(bundle.getBundleContext(),
				CPOptionValuePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}