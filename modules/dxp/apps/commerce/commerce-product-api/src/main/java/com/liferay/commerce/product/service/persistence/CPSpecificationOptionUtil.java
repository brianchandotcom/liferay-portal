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

import com.liferay.commerce.product.model.CPSpecificationOption;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp specification option service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPSpecificationOptionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPSpecificationOptionPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPSpecificationOptionPersistenceImpl
 * @generated
 */
@ProviderType
public class CPSpecificationOptionUtil {
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
	public static void clearCache(CPSpecificationOption cpSpecificationOption) {
		getPersistence().clearCache(cpSpecificationOption);
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
	public static List<CPSpecificationOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPSpecificationOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPSpecificationOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPSpecificationOption update(
		CPSpecificationOption cpSpecificationOption) {
		return getPersistence().update(cpSpecificationOption);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPSpecificationOption update(
		CPSpecificationOption cpSpecificationOption,
		ServiceContext serviceContext) {
		return getPersistence().update(cpSpecificationOption, serviceContext);
	}

	/**
	* Returns all the cp specification options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp specification options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByUuid_First(String uuid,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByUuid_First(String uuid,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByUuid_Last(String uuid,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByUuid_Last(String uuid,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where uuid = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption[] findByUuid_PrevAndNext(
		long CPSpecificationOptionId, String uuid,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPSpecificationOptionId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp specification options where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp specification options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp specification options
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp specification option where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPSpecificationOptionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp specification option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp specification option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp specification option where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp specification option that was removed
	*/
	public static CPSpecificationOption removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp specification options where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp specification options
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption[] findByUuid_C_PrevAndNext(
		long CPSpecificationOptionId, String uuid, long companyId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPSpecificationOptionId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp specification options where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp specification options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp specification options
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp specification options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp specification options
	*/
	public static List<CPSpecificationOption> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp specification options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp specification options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp specification options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByGroupId_First(long groupId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByGroupId_First(long groupId,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByGroupId_Last(long groupId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByGroupId_Last(long groupId,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where groupId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption[] findByGroupId_PrevAndNext(
		long CPSpecificationOptionId, long groupId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPSpecificationOptionId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the cp specification options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp specification options that the user has permission to view
	*/
	public static List<CPSpecificationOption> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp specification options that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options that the user has permission to view
	*/
	public static List<CPSpecificationOption> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp specification options that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options that the user has permission to view
	*/
	public static List<CPSpecificationOption> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set of cp specification options that the user has permission to view where groupId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption[] filterFindByGroupId_PrevAndNext(
		long CPSpecificationOptionId, long groupId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(CPSpecificationOptionId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the cp specification options where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp specification options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp specification options
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of cp specification options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp specification options that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the matching cp specification options
	*/
	public static List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId) {
		return getPersistence().findByCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	* Returns a range of all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end) {
		return getPersistence()
				   .findByCPOptionCategoryId(CPOptionCategoryId, start, end);
	}

	/**
	* Returns an ordered range of all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .findByCPOptionCategoryId(CPOptionCategoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp specification options where CPOptionCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp specification options
	*/
	public static List<CPSpecificationOption> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPOptionCategoryId(CPOptionCategoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByCPOptionCategoryId_First(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the first cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .fetchByCPOptionCategoryId_First(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByCPOptionCategoryId_Last(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence()
				   .fetchByCPOptionCategoryId_Last(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the cp specification options before and after the current cp specification option in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPSpecificationOptionId the primary key of the current cp specification option
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption[] findByCPOptionCategoryId_PrevAndNext(
		long CPSpecificationOptionId, long CPOptionCategoryId,
		OrderByComparator<CPSpecificationOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence()
				   .findByCPOptionCategoryId_PrevAndNext(CPSpecificationOptionId,
			CPOptionCategoryId, orderByComparator);
	}

	/**
	* Removes all the cp specification options where CPOptionCategoryId = &#63; from the database.
	*
	* @param CPOptionCategoryId the cp option category ID
	*/
	public static void removeByCPOptionCategoryId(long CPOptionCategoryId) {
		getPersistence().removeByCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	* Returns the number of cp specification options where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the number of matching cp specification options
	*/
	public static int countByCPOptionCategoryId(long CPOptionCategoryId) {
		return getPersistence().countByCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	* Returns the cp specification option where groupId = &#63; and key = &#63; or throws a {@link NoSuchCPSpecificationOptionException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp specification option
	* @throws NoSuchCPSpecificationOptionException if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption findByG_K(long groupId, String key)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().findByG_K(groupId, key);
	}

	/**
	* Returns the cp specification option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByG_K(long groupId, String key) {
		return getPersistence().fetchByG_K(groupId, key);
	}

	/**
	* Returns the cp specification option where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	public static CPSpecificationOption fetchByG_K(long groupId, String key,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_K(groupId, key, retrieveFromCache);
	}

	/**
	* Removes the cp specification option where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the cp specification option that was removed
	*/
	public static CPSpecificationOption removeByG_K(long groupId, String key)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().removeByG_K(groupId, key);
	}

	/**
	* Returns the number of cp specification options where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching cp specification options
	*/
	public static int countByG_K(long groupId, String key) {
		return getPersistence().countByG_K(groupId, key);
	}

	/**
	* Caches the cp specification option in the entity cache if it is enabled.
	*
	* @param cpSpecificationOption the cp specification option
	*/
	public static void cacheResult(CPSpecificationOption cpSpecificationOption) {
		getPersistence().cacheResult(cpSpecificationOption);
	}

	/**
	* Caches the cp specification options in the entity cache if it is enabled.
	*
	* @param cpSpecificationOptions the cp specification options
	*/
	public static void cacheResult(
		List<CPSpecificationOption> cpSpecificationOptions) {
		getPersistence().cacheResult(cpSpecificationOptions);
	}

	/**
	* Creates a new cp specification option with the primary key. Does not add the cp specification option to the database.
	*
	* @param CPSpecificationOptionId the primary key for the new cp specification option
	* @return the new cp specification option
	*/
	public static CPSpecificationOption create(long CPSpecificationOptionId) {
		return getPersistence().create(CPSpecificationOptionId);
	}

	/**
	* Removes the cp specification option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option that was removed
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption remove(long CPSpecificationOptionId)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().remove(CPSpecificationOptionId);
	}

	public static CPSpecificationOption updateImpl(
		CPSpecificationOption cpSpecificationOption) {
		return getPersistence().updateImpl(cpSpecificationOption);
	}

	/**
	* Returns the cp specification option with the primary key or throws a {@link NoSuchCPSpecificationOptionException} if it could not be found.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option
	* @throws NoSuchCPSpecificationOptionException if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption findByPrimaryKey(
		long CPSpecificationOptionId)
		throws com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException {
		return getPersistence().findByPrimaryKey(CPSpecificationOptionId);
	}

	/**
	* Returns the cp specification option with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option, or <code>null</code> if a cp specification option with the primary key could not be found
	*/
	public static CPSpecificationOption fetchByPrimaryKey(
		long CPSpecificationOptionId) {
		return getPersistence().fetchByPrimaryKey(CPSpecificationOptionId);
	}

	public static java.util.Map<java.io.Serializable, CPSpecificationOption> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp specification options.
	*
	* @return the cp specification options
	*/
	public static List<CPSpecificationOption> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of cp specification options
	*/
	public static List<CPSpecificationOption> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp specification options
	*/
	public static List<CPSpecificationOption> findAll(int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp specification options
	*/
	public static List<CPSpecificationOption> findAll(int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp specification options from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp specification options.
	*
	* @return the number of cp specification options
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPSpecificationOptionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPSpecificationOptionPersistence, CPSpecificationOptionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPSpecificationOptionPersistence.class);

		ServiceTracker<CPSpecificationOptionPersistence, CPSpecificationOptionPersistence> serviceTracker =
			new ServiceTracker<CPSpecificationOptionPersistence, CPSpecificationOptionPersistence>(bundle.getBundleContext(),
				CPSpecificationOptionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}