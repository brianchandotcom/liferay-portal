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

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition specification option value service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDefinitionSpecificationOptionValuePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValuePersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionSpecificationOptionValuePersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueUtil {
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
	public static void clearCache(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		getPersistence().clearCache(cpDefinitionSpecificationOptionValue);
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
	public static List<CPDefinitionSpecificationOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionSpecificationOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionSpecificationOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionSpecificationOptionValue update(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		return getPersistence().update(cpDefinitionSpecificationOptionValue);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionSpecificationOptionValue update(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(cpDefinitionSpecificationOptionValue, serviceContext);
	}

	/**
	* Returns all the cp definition specification option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByUuid_Last(
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue[] findByUuid_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionSpecificationOptionValueId,
			uuid, orderByComparator);
	}

	/**
	* Removes all the cp definition specification option values where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition specification option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition specification option values
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionSpecificationOptionValueException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByUUID_G(
		String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByUUID_G(
		String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition specification option value where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition specification option value that was removed
	*/
	public static CPDefinitionSpecificationOptionValue removeByUUID_G(
		String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition specification option values where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

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
	public static CPDefinitionSpecificationOptionValue[] findByUuid_C_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, String uuid,
		long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionSpecificationOptionValueId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition specification option values where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition specification option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp definition specification option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByGroupId_Last(
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where groupId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue[] findByGroupId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPDefinitionSpecificationOptionValueId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the cp definition specification option values where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp definition specification option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the cp definition specification option values where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId, start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPDefinitionId_First(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_First(CPDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPDefinitionId_PrevAndNext(CPDefinitionSpecificationOptionValueId,
			CPDefinitionId, orderByComparator);
	}

	/**
	* Removes all the cp definition specification option values where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition specification option values where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the matching cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId) {
		return getPersistence()
				   .findByCPSpecificationOptionId(CPSpecificationOptionId);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end) {
		return getPersistence()
				   .findByCPSpecificationOptionId(CPSpecificationOptionId,
			start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .findByCPSpecificationOptionId(CPSpecificationOptionId,
			start, end, orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPSpecificationOptionId(CPSpecificationOptionId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByCPSpecificationOptionId_First(
		long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPSpecificationOptionId_First(CPSpecificationOptionId,
			orderByComparator);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByCPSpecificationOptionId_First(
		long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPSpecificationOptionId_First(CPSpecificationOptionId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByCPSpecificationOptionId_Last(
		long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPSpecificationOptionId_Last(CPSpecificationOptionId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByCPSpecificationOptionId_Last(
		long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPSpecificationOptionId_Last(CPSpecificationOptionId,
			orderByComparator);
	}

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param CPSpecificationOptionId the cp specification option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue[] findByCPSpecificationOptionId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId,
		long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPSpecificationOptionId_PrevAndNext(CPDefinitionSpecificationOptionValueId,
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	* Removes all the cp definition specification option values where CPSpecificationOptionId = &#63; from the database.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	*/
	public static void removeByCPSpecificationOptionId(
		long CPSpecificationOptionId) {
		getPersistence().removeByCPSpecificationOptionId(CPSpecificationOptionId);
	}

	/**
	* Returns the number of cp definition specification option values where CPSpecificationOptionId = &#63;.
	*
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByCPSpecificationOptionId(
		long CPSpecificationOptionId) {
		return getPersistence()
				   .countByCPSpecificationOptionId(CPSpecificationOptionId);
	}

	/**
	* Returns all the cp definition specification option values where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the matching cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId) {
		return getPersistence().findByCPOptionCategoryId(CPOptionCategoryId);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end) {
		return getPersistence()
				   .findByCPOptionCategoryId(CPOptionCategoryId, start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .findByCPOptionCategoryId(CPOptionCategoryId, start, end,
			orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPOptionCategoryId(CPOptionCategoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPOptionCategoryId_First(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPOptionCategoryId_First(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPOptionCategoryId_Last(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCPOptionCategoryId_Last(CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue[] findByCPOptionCategoryId_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByCPOptionCategoryId_PrevAndNext(CPDefinitionSpecificationOptionValueId,
			CPOptionCategoryId, orderByComparator);
	}

	/**
	* Removes all the cp definition specification option values where CPOptionCategoryId = &#63; from the database.
	*
	* @param CPOptionCategoryId the cp option category ID
	*/
	public static void removeByCPOptionCategoryId(long CPOptionCategoryId) {
		getPersistence().removeByCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	* Returns the number of cp definition specification option values where CPOptionCategoryId = &#63;.
	*
	* @param CPOptionCategoryId the cp option category ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByCPOptionCategoryId(long CPOptionCategoryId) {
		return getPersistence().countByCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	* Returns the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; or throws a {@link NoSuchCPDefinitionSpecificationOptionValueException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByC_CSO(CPDefinitionId, CPSpecificationOptionId);
	}

	/**
	* Returns the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId) {
		return getPersistence()
				   .fetchByC_CSO(CPDefinitionId, CPSpecificationOptionId);
	}

	/**
	* Returns the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_CSO(CPDefinitionId, CPSpecificationOptionId,
			retrieveFromCache);
	}

	/**
	* Removes the cp definition specification option value where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the cp definition specification option value that was removed
	*/
	public static CPDefinitionSpecificationOptionValue removeByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .removeByC_CSO(CPDefinitionId, CPSpecificationOptionId);
	}

	/**
	* Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPSpecificationOptionId the cp specification option ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByC_CSO(long CPDefinitionId,
		long CPSpecificationOptionId) {
		return getPersistence()
				   .countByC_CSO(CPDefinitionId, CPSpecificationOptionId);
	}

	/**
	* Returns all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @return the matching cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId) {
		return getPersistence().findByC_COC(CPDefinitionId, CPOptionCategoryId);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end) {
		return getPersistence()
				   .findByC_COC(CPDefinitionId, CPOptionCategoryId, start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .findByC_COC(CPDefinitionId, CPOptionCategoryId, start, end,
			orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_COC(CPDefinitionId, CPOptionCategoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByC_COC_First(
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByC_COC_First(CPDefinitionId, CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByC_COC_First(
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByC_COC_First(CPDefinitionId, CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByC_COC_Last(
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByC_COC_Last(CPDefinitionId, CPOptionCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByC_COC_Last(
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByC_COC_Last(CPDefinitionId, CPOptionCategoryId,
			orderByComparator);
	}

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
	public static CPDefinitionSpecificationOptionValue[] findByC_COC_PrevAndNext(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByC_COC_PrevAndNext(CPDefinitionSpecificationOptionValueId,
			CPDefinitionId, CPOptionCategoryId, orderByComparator);
	}

	/**
	* Removes all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	*/
	public static void removeByC_COC(long CPDefinitionId,
		long CPOptionCategoryId) {
		getPersistence().removeByC_COC(CPDefinitionId, CPOptionCategoryId);
	}

	/**
	* Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param CPOptionCategoryId the cp option category ID
	* @return the number of matching cp definition specification option values
	*/
	public static int countByC_COC(long CPDefinitionId, long CPOptionCategoryId) {
		return getPersistence().countByC_COC(CPDefinitionId, CPOptionCategoryId);
	}

	/**
	* Caches the cp definition specification option value in the entity cache if it is enabled.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	*/
	public static void cacheResult(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		getPersistence().cacheResult(cpDefinitionSpecificationOptionValue);
	}

	/**
	* Caches the cp definition specification option values in the entity cache if it is enabled.
	*
	* @param cpDefinitionSpecificationOptionValues the cp definition specification option values
	*/
	public static void cacheResult(
		List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues) {
		getPersistence().cacheResult(cpDefinitionSpecificationOptionValues);
	}

	/**
	* Creates a new cp definition specification option value with the primary key. Does not add the cp definition specification option value to the database.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key for the new cp definition specification option value
	* @return the new cp definition specification option value
	*/
	public static CPDefinitionSpecificationOptionValue create(
		long CPDefinitionSpecificationOptionValueId) {
		return getPersistence().create(CPDefinitionSpecificationOptionValueId);
	}

	/**
	* Removes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value that was removed
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue remove(
		long CPDefinitionSpecificationOptionValueId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence().remove(CPDefinitionSpecificationOptionValueId);
	}

	public static CPDefinitionSpecificationOptionValue updateImpl(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		return getPersistence().updateImpl(cpDefinitionSpecificationOptionValue);
	}

	/**
	* Returns the cp definition specification option value with the primary key or throws a {@link NoSuchCPDefinitionSpecificationOptionValueException} if it could not be found.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value
	* @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue findByPrimaryKey(
		long CPDefinitionSpecificationOptionValueId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException {
		return getPersistence()
				   .findByPrimaryKey(CPDefinitionSpecificationOptionValueId);
	}

	/**
	* Returns the cp definition specification option value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value, or <code>null</code> if a cp definition specification option value with the primary key could not be found
	*/
	public static CPDefinitionSpecificationOptionValue fetchByPrimaryKey(
		long CPDefinitionSpecificationOptionValueId) {
		return getPersistence()
				   .fetchByPrimaryKey(CPDefinitionSpecificationOptionValueId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionSpecificationOptionValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition specification option values.
	*
	* @return the cp definition specification option values
	*/
	public static List<CPDefinitionSpecificationOptionValue> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition specification option values from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition specification option values.
	*
	* @return the number of cp definition specification option values
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionSpecificationOptionValuePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionSpecificationOptionValuePersistence, CPDefinitionSpecificationOptionValuePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionSpecificationOptionValuePersistence.class);

		ServiceTracker<CPDefinitionSpecificationOptionValuePersistence, CPDefinitionSpecificationOptionValuePersistence> serviceTracker =
			new ServiceTracker<CPDefinitionSpecificationOptionValuePersistence, CPDefinitionSpecificationOptionValuePersistence>(bundle.getBundleContext(),
				CPDefinitionSpecificationOptionValuePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}