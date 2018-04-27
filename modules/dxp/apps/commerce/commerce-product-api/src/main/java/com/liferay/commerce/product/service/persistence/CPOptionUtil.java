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

import com.liferay.commerce.product.model.CPOption;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp option service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPOptionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPOptionPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPOptionPersistenceImpl
 * @generated
 */
@ProviderType
public class CPOptionUtil {
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
	public static void clearCache(CPOption cpOption) {
		getPersistence().clearCache(cpOption);
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
	public static List<CPOption> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPOption update(CPOption cpOption) {
		return getPersistence().update(cpOption);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPOption update(CPOption cpOption,
		ServiceContext serviceContext) {
		return getPersistence().update(cpOption, serviceContext);
	}

	/**
	* Returns all the cp options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp options
	*/
	public static List<CPOption> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options
	*/
	public static List<CPOption> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options
	*/
	public static List<CPOption> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp options
	*/
	public static List<CPOption> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPOption> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByUuid_First(String uuid,
		OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByUuid_First(String uuid,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByUuid_Last(String uuid,
		OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByUuid_Last(String uuid,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp options before and after the current cp option in the ordered set where uuid = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public static CPOption[] findByUuid_PrevAndNext(long CPOptionId,
		String uuid, OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPOptionId, uuid, orderByComparator);
	}

	/**
	* Removes all the cp options where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp options
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp option where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPOptionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp option where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp option that was removed
	*/
	public static CPOption removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp options where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp options
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp options
	*/
	public static List<CPOption> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options
	*/
	public static List<CPOption> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options
	*/
	public static List<CPOption> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CPOption> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp options
	*/
	public static List<CPOption> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CPOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp options before and after the current cp option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public static CPOption[] findByUuid_C_PrevAndNext(long CPOptionId,
		String uuid, long companyId,
		OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPOptionId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the cp options where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp options
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp options
	*/
	public static List<CPOption> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options
	*/
	public static List<CPOption> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options
	*/
	public static List<CPOption> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPOption> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp options
	*/
	public static List<CPOption> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByGroupId_First(long groupId,
		OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByGroupId_First(long groupId,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByGroupId_Last(long groupId,
		OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByGroupId_Last(long groupId,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp options before and after the current cp option in the ordered set where groupId = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public static CPOption[] findByGroupId_PrevAndNext(long CPOptionId,
		long groupId, OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPOptionId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the cp options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp options that the user has permission to view
	*/
	public static List<CPOption> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp options that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of matching cp options that the user has permission to view
	*/
	public static List<CPOption> filterFindByGroupId(long groupId, int start,
		int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp options that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp options that the user has permission to view
	*/
	public static List<CPOption> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<CPOption> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the cp options before and after the current cp option in the ordered set of cp options that the user has permission to view where groupId = &#63;.
	*
	* @param CPOptionId the primary key of the current cp option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public static CPOption[] filterFindByGroupId_PrevAndNext(long CPOptionId,
		long groupId, OrderByComparator<CPOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(CPOptionId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cp options where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp options
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of cp options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp options that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the cp option where groupId = &#63; and key = &#63; or throws a {@link NoSuchCPOptionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option
	* @throws NoSuchCPOptionException if a matching cp option could not be found
	*/
	public static CPOption findByG_K(long groupId, String key)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().findByG_K(groupId, key);
	}

	/**
	* Returns the cp option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByG_K(long groupId, String key) {
		return getPersistence().fetchByG_K(groupId, key);
	}

	/**
	* Returns the cp option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	public static CPOption fetchByG_K(long groupId, String key,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_K(groupId, key, retrieveFromCache);
	}

	/**
	* Removes the cp option where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the cp option that was removed
	*/
	public static CPOption removeByG_K(long groupId, String key)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().removeByG_K(groupId, key);
	}

	/**
	* Returns the number of cp options where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching cp options
	*/
	public static int countByG_K(long groupId, String key) {
		return getPersistence().countByG_K(groupId, key);
	}

	/**
	* Caches the cp option in the entity cache if it is enabled.
	*
	* @param cpOption the cp option
	*/
	public static void cacheResult(CPOption cpOption) {
		getPersistence().cacheResult(cpOption);
	}

	/**
	* Caches the cp options in the entity cache if it is enabled.
	*
	* @param cpOptions the cp options
	*/
	public static void cacheResult(List<CPOption> cpOptions) {
		getPersistence().cacheResult(cpOptions);
	}

	/**
	* Creates a new cp option with the primary key. Does not add the cp option to the database.
	*
	* @param CPOptionId the primary key for the new cp option
	* @return the new cp option
	*/
	public static CPOption create(long CPOptionId) {
		return getPersistence().create(CPOptionId);
	}

	/**
	* Removes the cp option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option that was removed
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public static CPOption remove(long CPOptionId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().remove(CPOptionId);
	}

	public static CPOption updateImpl(CPOption cpOption) {
		return getPersistence().updateImpl(cpOption);
	}

	/**
	* Returns the cp option with the primary key or throws a {@link NoSuchCPOptionException} if it could not be found.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option
	* @throws NoSuchCPOptionException if a cp option with the primary key could not be found
	*/
	public static CPOption findByPrimaryKey(long CPOptionId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionException {
		return getPersistence().findByPrimaryKey(CPOptionId);
	}

	/**
	* Returns the cp option with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option, or <code>null</code> if a cp option with the primary key could not be found
	*/
	public static CPOption fetchByPrimaryKey(long CPOptionId) {
		return getPersistence().fetchByPrimaryKey(CPOptionId);
	}

	public static java.util.Map<java.io.Serializable, CPOption> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp options.
	*
	* @return the cp options
	*/
	public static List<CPOption> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of cp options
	*/
	public static List<CPOption> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp options
	*/
	public static List<CPOption> findAll(int start, int end,
		OrderByComparator<CPOption> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp options
	*/
	public static List<CPOption> findAll(int start, int end,
		OrderByComparator<CPOption> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp options from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp options.
	*
	* @return the number of cp options
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPOptionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionPersistence, CPOptionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionPersistence.class);

		ServiceTracker<CPOptionPersistence, CPOptionPersistence> serviceTracker = new ServiceTracker<CPOptionPersistence, CPOptionPersistence>(bundle.getBundleContext(),
				CPOptionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}