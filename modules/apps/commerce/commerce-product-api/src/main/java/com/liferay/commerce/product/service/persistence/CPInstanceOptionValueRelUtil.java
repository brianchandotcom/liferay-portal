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

import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp instance option value rel service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPInstanceOptionValueRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceOptionValueRelPersistence
 * @generated
 */
public class CPInstanceOptionValueRelUtil {

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
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		getPersistence().clearCache(cpInstanceOptionValueRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CPInstanceOptionValueRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPInstanceOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPInstanceOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPInstanceOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPInstanceOptionValueRel update(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		return getPersistence().update(cpInstanceOptionValueRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPInstanceOptionValueRel update(
		CPInstanceOptionValueRel cpInstanceOptionValueRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			cpInstanceOptionValueRel, serviceContext);
	}

	/**
	 * Returns all the cp instance option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByUuid_First(
			String uuid,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByUuid_Last(
			String uuid,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel[] findByUuid_PrevAndNext(
			long CPInstanceOptionValueRelId, String uuid,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByUuid_PrevAndNext(
			CPInstanceOptionValueRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp instance option value rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the cp instance option value rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp instance option value rel that was removed
	 */
	public static CPInstanceOptionValueRel removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel[] findByUuid_C_PrevAndNext(
			long CPInstanceOptionValueRelId, String uuid, long companyId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPInstanceOptionValueRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp instance option value rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {

		return getPersistence().findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end) {

		return getPersistence().findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCPDefinitionOptionRelId_First(
			long CPDefinitionOptionRelId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCPDefinitionOptionRelId_First(
			CPDefinitionOptionRelId, orderByComparator);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCPDefinitionOptionRelId_First(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByCPDefinitionOptionRelId_First(
			CPDefinitionOptionRelId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCPDefinitionOptionRelId_Last(
			long CPDefinitionOptionRelId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCPDefinitionOptionRelId_Last(
			CPDefinitionOptionRelId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCPDefinitionOptionRelId_Last(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByCPDefinitionOptionRelId_Last(
			CPDefinitionOptionRelId, orderByComparator);
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel[]
			findByCPDefinitionOptionRelId_PrevAndNext(
				long CPInstanceOptionValueRelId, long CPDefinitionOptionRelId,
				OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCPDefinitionOptionRelId_PrevAndNext(
			CPInstanceOptionValueRelId, CPDefinitionOptionRelId,
			orderByComparator);
	}

	/**
	 * Removes all the cp instance option value rels where CPDefinitionOptionRelId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 */
	public static void removeByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {

		getPersistence().removeByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId);
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {

		return getPersistence().countByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId);
	}

	/**
	 * Returns all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId) {

		return getPersistence().findByCPInstanceId(CPInstanceId);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end) {

		return getPersistence().findByCPInstanceId(CPInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCPInstanceId_First(
			long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCPInstanceId_First(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByCPInstanceId_First(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCPInstanceId_Last(
			long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCPInstanceId_Last(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByCPInstanceId_Last(
			CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel[] findByCPInstanceId_PrevAndNext(
			long CPInstanceOptionValueRelId, long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCPInstanceId_PrevAndNext(
			CPInstanceOptionValueRelId, CPInstanceId, orderByComparator);
	}

	/**
	 * Removes all the cp instance option value rels where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	public static void removeByCPInstanceId(long CPInstanceId) {
		getPersistence().removeByCPInstanceId(CPInstanceId);
	}

	/**
	 * Returns the number of cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByCPInstanceId(long CPInstanceId) {
		return getPersistence().countByCPInstanceId(CPInstanceId);
	}

	/**
	 * Returns all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId) {

		return getPersistence().findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end) {

		return getPersistence().findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCDORI_CII_First(
			long CPDefinitionOptionRelId, long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCDORI_CII_First(
			CPDefinitionOptionRelId, CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCDORI_CII_First(
		long CPDefinitionOptionRelId, long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByCDORI_CII_First(
			CPDefinitionOptionRelId, CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCDORI_CII_Last(
			long CPDefinitionOptionRelId, long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCDORI_CII_Last(
			CPDefinitionOptionRelId, CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCDORI_CII_Last(
		long CPDefinitionOptionRelId, long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().fetchByCDORI_CII_Last(
			CPDefinitionOptionRelId, CPInstanceId, orderByComparator);
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel[] findByCDORI_CII_PrevAndNext(
			long CPInstanceOptionValueRelId, long CPDefinitionOptionRelId,
			long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCDORI_CII_PrevAndNext(
			CPInstanceOptionValueRelId, CPDefinitionOptionRelId, CPInstanceId,
			orderByComparator);
	}

	/**
	 * Removes all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 */
	public static void removeByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId) {

		getPersistence().removeByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId);
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId) {

		return getPersistence().countByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCDOVRI_CII(
			long CPDefinitionOptionValueRelId, long CPInstanceId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId) {

		return getPersistence().fetchByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId,
		boolean useFinderCache) {

		return getPersistence().fetchByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId, useFinderCache);
	}

	/**
	 * Removes the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the cp instance option value rel that was removed
	 */
	public static CPInstanceOptionValueRel removeByCDOVRI_CII(
			long CPDefinitionOptionValueRelId, long CPInstanceId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().removeByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId);
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId) {

		return getPersistence().countByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel findByCDORI_CDOVRI_CII(
			long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
			long CPInstanceId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByCDORI_CDOVRI_CII(
			CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
			CPInstanceId);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId) {

		return getPersistence().fetchByCDORI_CDOVRI_CII(
			CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
			CPInstanceId);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	public static CPInstanceOptionValueRel fetchByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId, boolean useFinderCache) {

		return getPersistence().fetchByCDORI_CDOVRI_CII(
			CPDefinitionOptionRelId, CPDefinitionOptionValueRelId, CPInstanceId,
			useFinderCache);
	}

	/**
	 * Removes the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the cp instance option value rel that was removed
	 */
	public static CPInstanceOptionValueRel removeByCDORI_CDOVRI_CII(
			long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
			long CPInstanceId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().removeByCDORI_CDOVRI_CII(
			CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
			CPInstanceId);
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	public static int countByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId) {

		return getPersistence().countByCDORI_CDOVRI_CII(
			CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
			CPInstanceId);
	}

	/**
	 * Caches the cp instance option value rel in the entity cache if it is enabled.
	 *
	 * @param cpInstanceOptionValueRel the cp instance option value rel
	 */
	public static void cacheResult(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		getPersistence().cacheResult(cpInstanceOptionValueRel);
	}

	/**
	 * Caches the cp instance option value rels in the entity cache if it is enabled.
	 *
	 * @param cpInstanceOptionValueRels the cp instance option value rels
	 */
	public static void cacheResult(
		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels) {

		getPersistence().cacheResult(cpInstanceOptionValueRels);
	}

	/**
	 * Creates a new cp instance option value rel with the primary key. Does not add the cp instance option value rel to the database.
	 *
	 * @param CPInstanceOptionValueRelId the primary key for the new cp instance option value rel
	 * @return the new cp instance option value rel
	 */
	public static CPInstanceOptionValueRel create(
		long CPInstanceOptionValueRelId) {

		return getPersistence().create(CPInstanceOptionValueRelId);
	}

	/**
	 * Removes the cp instance option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel that was removed
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel remove(
			long CPInstanceOptionValueRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().remove(CPInstanceOptionValueRelId);
	}

	public static CPInstanceOptionValueRel updateImpl(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		return getPersistence().updateImpl(cpInstanceOptionValueRel);
	}

	/**
	 * Returns the cp instance option value rel with the primary key or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel findByPrimaryKey(
			long CPInstanceOptionValueRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPInstanceOptionValueRelException {

		return getPersistence().findByPrimaryKey(CPInstanceOptionValueRelId);
	}

	/**
	 * Returns the cp instance option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel, or <code>null</code> if a cp instance option value rel with the primary key could not be found
	 */
	public static CPInstanceOptionValueRel fetchByPrimaryKey(
		long CPInstanceOptionValueRelId) {

		return getPersistence().fetchByPrimaryKey(CPInstanceOptionValueRelId);
	}

	/**
	 * Returns all the cp instance option value rels.
	 *
	 * @return the cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp instance option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp instance option value rels
	 */
	public static List<CPInstanceOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp instance option value rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp instance option value rels.
	 *
	 * @return the number of cp instance option value rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPInstanceOptionValueRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CPInstanceOptionValueRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CPInstanceOptionValueRelPersistence _persistence;

}