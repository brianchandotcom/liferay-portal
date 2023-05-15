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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp definition service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPDefinitionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionPersistence
 * @generated
 */
public class CPDefinitionUtil {

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
	public static void clearCache(CPDefinition cpDefinition) {
		getPersistence().clearCache(cpDefinition);
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
	public static Map<Serializable, CPDefinition> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinition update(CPDefinition cpDefinition) {
		return getPersistence().update(cpDefinition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinition update(
		CPDefinition cpDefinition, ServiceContext serviceContext) {

		return getPersistence().update(cpDefinition, serviceContext);
	}

	/**
	 * Returns all the cp definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByUuid_First(
			String uuid, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByUuid_First(
		String uuid, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByUuid_Last(
			String uuid, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByUuid_Last(
		String uuid, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByUuid_PrevAndNext(
			long CPDefinitionId, String uuid,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByUuid_PrevAndNext(
			CPDefinitionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definitions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the cp definition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition that was removed
	 */
	public static CPDefinition removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definitions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByUuid_C_PrevAndNext(
			long CPDefinitionId, String uuid, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPDefinitionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definitions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the cp definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByGroupId_First(
			long groupId, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByGroupId_First(
		long groupId, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByGroupId_Last(
			long groupId, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByGroupId_Last(
		long groupId, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByGroupId_PrevAndNext(
			long CPDefinitionId, long groupId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByGroupId_PrevAndNext(
			CPDefinitionId, groupId, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definitions
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the cp definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByCompanyId_First(
			long companyId, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByCompanyId_Last(
			long companyId, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByCompanyId_Last(
		long companyId, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByCompanyId_PrevAndNext(
			long CPDefinitionId, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCompanyId_PrevAndNext(
			CPDefinitionId, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of cp definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp definitions
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the cp definitions where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByCProductId(long CProductId) {
		return getPersistence().findByCProductId(CProductId);
	}

	/**
	 * Returns a range of all the cp definitions where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByCProductId(
		long CProductId, int start, int end) {

		return getPersistence().findByCProductId(CProductId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByCProductId(
			CProductId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCProductId(
			CProductId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByCProductId_First(
			long CProductId, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCProductId_First(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByCProductId_First(
		long CProductId, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByCProductId_First(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByCProductId_Last(
			long CProductId, OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCProductId_Last(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByCProductId_Last(
		long CProductId, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByCProductId_Last(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where CProductId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByCProductId_PrevAndNext(
			long CPDefinitionId, long CProductId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCProductId_PrevAndNext(
			CPDefinitionId, CProductId, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	public static void removeByCProductId(long CProductId) {
		getPersistence().removeByCProductId(CProductId);
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching cp definitions
	 */
	public static int countByCProductId(long CProductId) {
		return getPersistence().countByCProductId(CProductId);
	}

	/**
	 * Returns all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId) {

		return getPersistence().findByCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	 * Returns a range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end) {

		return getPersistence().findByCPTaxCategoryId(
			CPTaxCategoryId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByCPTaxCategoryId(
			CPTaxCategoryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPTaxCategoryId(
			CPTaxCategoryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByCPTaxCategoryId_First(
			long CPTaxCategoryId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCPTaxCategoryId_First(
			CPTaxCategoryId, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByCPTaxCategoryId_First(
		long CPTaxCategoryId,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByCPTaxCategoryId_First(
			CPTaxCategoryId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByCPTaxCategoryId_Last(
			long CPTaxCategoryId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCPTaxCategoryId_Last(
			CPTaxCategoryId, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByCPTaxCategoryId_Last(
		long CPTaxCategoryId,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByCPTaxCategoryId_Last(
			CPTaxCategoryId, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByCPTaxCategoryId_PrevAndNext(
			long CPDefinitionId, long CPTaxCategoryId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByCPTaxCategoryId_PrevAndNext(
			CPDefinitionId, CPTaxCategoryId, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where CPTaxCategoryId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 */
	public static void removeByCPTaxCategoryId(long CPTaxCategoryId) {
		getPersistence().removeByCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	 * Returns the number of cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching cp definitions
	 */
	public static int countByCPTaxCategoryId(long CPTaxCategoryId) {
		return getPersistence().countByCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	 * Returns all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled) {

		return getPersistence().findByG_SE(groupId, subscriptionEnabled);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end) {

		return getPersistence().findByG_SE(
			groupId, subscriptionEnabled, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByG_SE(
			groupId, subscriptionEnabled, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_SE(
			groupId, subscriptionEnabled, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByG_SE_First(
			long groupId, boolean subscriptionEnabled,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByG_SE_First(
			groupId, subscriptionEnabled, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByG_SE_First(
		long groupId, boolean subscriptionEnabled,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByG_SE_First(
			groupId, subscriptionEnabled, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByG_SE_Last(
			long groupId, boolean subscriptionEnabled,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByG_SE_Last(
			groupId, subscriptionEnabled, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByG_SE_Last(
		long groupId, boolean subscriptionEnabled,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByG_SE_Last(
			groupId, subscriptionEnabled, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByG_SE_PrevAndNext(
			long CPDefinitionId, long groupId, boolean subscriptionEnabled,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByG_SE_PrevAndNext(
			CPDefinitionId, groupId, subscriptionEnabled, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 */
	public static void removeByG_SE(long groupId, boolean subscriptionEnabled) {
		getPersistence().removeByG_SE(groupId, subscriptionEnabled);
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the number of matching cp definitions
	 */
	public static int countByG_SE(long groupId, boolean subscriptionEnabled) {
		return getPersistence().countByG_SE(groupId, subscriptionEnabled);
	}

	/**
	 * Returns all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByG_S(long groupId, int status) {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end) {

		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByG_S(
			groupId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_S(
			groupId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByG_S_First(
			long groupId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByG_S_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByG_S_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByG_S_Last(
			long groupId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByG_S_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByG_S_Last(
		long groupId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByG_S_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByG_S_PrevAndNext(
			long CPDefinitionId, long groupId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByG_S_PrevAndNext(
			CPDefinitionId, groupId, status, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public static void removeByG_S(long groupId, int status) {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	public static int countByG_S(long groupId, int status) {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByC_V(long CProductId, int version)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByC_V(CProductId, version);
	}

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByC_V(long CProductId, int version) {
		return getPersistence().fetchByC_V(CProductId, version);
	}

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByC_V(
		long CProductId, int version, boolean useFinderCache) {

		return getPersistence().fetchByC_V(CProductId, version, useFinderCache);
	}

	/**
	 * Removes the cp definition where CProductId = &#63; and version = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the cp definition that was removed
	 */
	public static CPDefinition removeByC_V(long CProductId, int version)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().removeByC_V(CProductId, version);
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63; and version = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the number of matching cp definitions
	 */
	public static int countByC_V(long CProductId, int version) {
		return getPersistence().countByC_V(CProductId, version);
	}

	/**
	 * Returns all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByC_S(long CProductId, int status) {
		return getPersistence().findByC_S(CProductId, status);
	}

	/**
	 * Returns a range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end) {

		return getPersistence().findByC_S(CProductId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByC_S(
			CProductId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S(
			CProductId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByC_S_First(
			long CProductId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByC_S_First(
			CProductId, status, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByC_S_First(
		long CProductId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByC_S_First(
			CProductId, status, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByC_S_Last(
			long CProductId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByC_S_Last(
			CProductId, status, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByC_S_Last(
		long CProductId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByC_S_Last(
			CProductId, status, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByC_S_PrevAndNext(
			long CPDefinitionId, long CProductId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByC_S_PrevAndNext(
			CPDefinitionId, CProductId, status, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where CProductId = &#63; and status = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 */
	public static void removeByC_S(long CProductId, int status) {
		getPersistence().removeByC_S(CProductId, status);
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	public static int countByC_S(long CProductId, int status) {
		return getPersistence().countByC_S(CProductId, status);
	}

	/**
	 * Returns all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp definitions
	 */
	public static List<CPDefinition> findByLtD_S(Date displayDate, int status) {
		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	public static List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	public static List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	public static CPDefinition findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	public static CPDefinition fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition[] findByLtD_S_PrevAndNext(
			long CPDefinitionId, Date displayDate, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByLtD_S_PrevAndNext(
			CPDefinitionId, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the cp definitions where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Caches the cp definition in the entity cache if it is enabled.
	 *
	 * @param cpDefinition the cp definition
	 */
	public static void cacheResult(CPDefinition cpDefinition) {
		getPersistence().cacheResult(cpDefinition);
	}

	/**
	 * Caches the cp definitions in the entity cache if it is enabled.
	 *
	 * @param cpDefinitions the cp definitions
	 */
	public static void cacheResult(List<CPDefinition> cpDefinitions) {
		getPersistence().cacheResult(cpDefinitions);
	}

	/**
	 * Creates a new cp definition with the primary key. Does not add the cp definition to the database.
	 *
	 * @param CPDefinitionId the primary key for the new cp definition
	 * @return the new cp definition
	 */
	public static CPDefinition create(long CPDefinitionId) {
		return getPersistence().create(CPDefinitionId);
	}

	/**
	 * Removes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition that was removed
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition remove(long CPDefinitionId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().remove(CPDefinitionId);
	}

	public static CPDefinition updateImpl(CPDefinition cpDefinition) {
		return getPersistence().updateImpl(cpDefinition);
	}

	/**
	 * Returns the cp definition with the primary key or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	public static CPDefinition findByPrimaryKey(long CPDefinitionId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPDefinitionException {

		return getPersistence().findByPrimaryKey(CPDefinitionId);
	}

	/**
	 * Returns the cp definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition, or <code>null</code> if a cp definition with the primary key could not be found
	 */
	public static CPDefinition fetchByPrimaryKey(long CPDefinitionId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionId);
	}

	/**
	 * Returns all the cp definitions.
	 *
	 * @return the cp definitions
	 */
	public static List<CPDefinition> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of cp definitions
	 */
	public static List<CPDefinition> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definitions
	 */
	public static List<CPDefinition> findAll(
		int start, int end, OrderByComparator<CPDefinition> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definitions
	 */
	public static List<CPDefinition> findAll(
		int start, int end, OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp definitions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp definitions.
	 *
	 * @return the number of cp definitions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPDefinitionPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CPDefinitionPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CPDefinitionPersistence _persistence;

}