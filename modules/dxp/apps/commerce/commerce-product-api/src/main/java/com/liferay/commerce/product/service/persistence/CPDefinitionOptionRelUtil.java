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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition option rel service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDefinitionOptionRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionOptionRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelUtil {
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
	public static void clearCache(CPDefinitionOptionRel cpDefinitionOptionRel) {
		getPersistence().clearCache(cpDefinitionOptionRel);
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
	public static List<CPDefinitionOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionOptionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionOptionRel update(
		CPDefinitionOptionRel cpDefinitionOptionRel) {
		return getPersistence().update(cpDefinitionOptionRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionOptionRel update(
		CPDefinitionOptionRel cpDefinitionOptionRel,
		ServiceContext serviceContext) {
		return getPersistence().update(cpDefinitionOptionRel, serviceContext);
	}

	/**
	* Returns all the cp definition option rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp definition option rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel[] findByUuid_PrevAndNext(
		long CPDefinitionOptionRelId, String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionOptionRelId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp definition option rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition option rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition option rels
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionOptionRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition option rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition option rel that was removed
	*/
	public static CPDefinitionOptionRel removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition option rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition option rels
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel[] findByUuid_C_PrevAndNext(
		long CPDefinitionOptionRelId, String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionOptionRelId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition option rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition option rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition option rels
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp definition option rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp definition option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where groupId = &#63;.
	*
	* @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel[] findByGroupId_PrevAndNext(
		long CPDefinitionOptionRelId, long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPDefinitionOptionRelId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cp definition option rels where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp definition option rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp definition option rels
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the cp definition option rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the cp definition option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByCompanyId_First(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByCompanyId_First(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByCompanyId_Last(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByCompanyId_Last(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where companyId = &#63;.
	*
	* @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel[] findByCompanyId_PrevAndNext(
		long CPDefinitionOptionRelId, long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(CPDefinitionOptionRelId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition option rels where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of cp definition option rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp definition option rels
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the cp definition option rels where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns a range of all the cp definition option rels where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByCPDefinitionId_First(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_First(CPDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionOptionRelId, long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByCPDefinitionId_PrevAndNext(CPDefinitionOptionRelId,
			CPDefinitionId, orderByComparator);
	}

	/**
	* Removes all the cp definition option rels where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition option rels where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition option rels
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @return the matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor) {
		return getPersistence().findByC_SC(CPDefinitionId, skuContributor);
	}

	/**
	* Returns a range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor, int start, int end) {
		return getPersistence()
				   .findByC_SC(CPDefinitionId, skuContributor, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .findByC_SC(CPDefinitionId, skuContributor, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_SC(CPDefinitionId, skuContributor, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByC_SC_First(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByC_SC_First(CPDefinitionId, skuContributor,
			orderByComparator);
	}

	/**
	* Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByC_SC_First(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_SC_First(CPDefinitionId, skuContributor,
			orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel findByC_SC_Last(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByC_SC_Last(CPDefinitionId, skuContributor,
			orderByComparator);
	}

	/**
	* Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static CPDefinitionOptionRel fetchByC_SC_Last(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_SC_Last(CPDefinitionId, skuContributor,
			orderByComparator);
	}

	/**
	* Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel[] findByC_SC_PrevAndNext(
		long CPDefinitionOptionRelId, long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence()
				   .findByC_SC_PrevAndNext(CPDefinitionOptionRelId,
			CPDefinitionId, skuContributor, orderByComparator);
	}

	/**
	* Removes all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	*/
	public static void removeByC_SC(long CPDefinitionId, boolean skuContributor) {
		getPersistence().removeByC_SC(CPDefinitionId, skuContributor);
	}

	/**
	* Returns the number of cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param skuContributor the sku contributor
	* @return the number of matching cp definition option rels
	*/
	public static int countByC_SC(long CPDefinitionId, boolean skuContributor) {
		return getPersistence().countByC_SC(CPDefinitionId, skuContributor);
	}

	/**
	* Caches the cp definition option rel in the entity cache if it is enabled.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	*/
	public static void cacheResult(CPDefinitionOptionRel cpDefinitionOptionRel) {
		getPersistence().cacheResult(cpDefinitionOptionRel);
	}

	/**
	* Caches the cp definition option rels in the entity cache if it is enabled.
	*
	* @param cpDefinitionOptionRels the cp definition option rels
	*/
	public static void cacheResult(
		List<CPDefinitionOptionRel> cpDefinitionOptionRels) {
		getPersistence().cacheResult(cpDefinitionOptionRels);
	}

	/**
	* Creates a new cp definition option rel with the primary key. Does not add the cp definition option rel to the database.
	*
	* @param CPDefinitionOptionRelId the primary key for the new cp definition option rel
	* @return the new cp definition option rel
	*/
	public static CPDefinitionOptionRel create(long CPDefinitionOptionRelId) {
		return getPersistence().create(CPDefinitionOptionRelId);
	}

	/**
	* Removes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel that was removed
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel remove(long CPDefinitionOptionRelId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().remove(CPDefinitionOptionRelId);
	}

	public static CPDefinitionOptionRel updateImpl(
		CPDefinitionOptionRel cpDefinitionOptionRel) {
		return getPersistence().updateImpl(cpDefinitionOptionRel);
	}

	/**
	* Returns the cp definition option rel with the primary key or throws a {@link NoSuchCPDefinitionOptionRelException} if it could not be found.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel
	* @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel findByPrimaryKey(
		long CPDefinitionOptionRelId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException {
		return getPersistence().findByPrimaryKey(CPDefinitionOptionRelId);
	}

	/**
	* Returns the cp definition option rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel, or <code>null</code> if a cp definition option rel with the primary key could not be found
	*/
	public static CPDefinitionOptionRel fetchByPrimaryKey(
		long CPDefinitionOptionRelId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionOptionRelId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionOptionRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition option rels.
	*
	* @return the cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition option rels
	*/
	public static List<CPDefinitionOptionRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition option rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition option rels.
	*
	* @return the number of cp definition option rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionOptionRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionOptionRelPersistence, CPDefinitionOptionRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionOptionRelPersistence.class);

		ServiceTracker<CPDefinitionOptionRelPersistence, CPDefinitionOptionRelPersistence> serviceTracker =
			new ServiceTracker<CPDefinitionOptionRelPersistence, CPDefinitionOptionRelPersistence>(bundle.getBundleContext(),
				CPDefinitionOptionRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}