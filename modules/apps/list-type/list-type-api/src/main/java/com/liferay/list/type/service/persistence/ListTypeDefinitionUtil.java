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

package com.liferay.list.type.service.persistence;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the list type definition service. This utility wraps <code>com.liferay.list.type.service.persistence.impl.ListTypeDefinitionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see ListTypeDefinitionPersistence
 * @generated
 */
public class ListTypeDefinitionUtil {

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
	public static void clearCache(ListTypeDefinition listTypeDefinition) {
		getPersistence().clearCache(listTypeDefinition);
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
	public static Map<Serializable, ListTypeDefinition> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ListTypeDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ListTypeDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ListTypeDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ListTypeDefinition update(
		ListTypeDefinition listTypeDefinition) {

		return getPersistence().update(listTypeDefinition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ListTypeDefinition update(
		ListTypeDefinition listTypeDefinition, ServiceContext serviceContext) {

		return getPersistence().update(listTypeDefinition, serviceContext);
	}

	/**
	 * Returns all the list type definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the list type definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public static ListTypeDefinition findByUuid_First(
			String uuid,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public static ListTypeDefinition fetchByUuid_First(
		String uuid, OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public static ListTypeDefinition findByUuid_Last(
			String uuid,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public static ListTypeDefinition fetchByUuid_Last(
		String uuid, OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set where uuid = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public static ListTypeDefinition[] findByUuid_PrevAndNext(
			long listTypeDefinitionId, String uuid,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().findByUuid_PrevAndNext(
			listTypeDefinitionId, uuid, orderByComparator);
	}

	/**
	 * Returns all the list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list type definitions that the user has permission to view
	 */
	public static List<ListTypeDefinition> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions that the user has permission to view
	 */
	public static List<ListTypeDefinition> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the list type definitions that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions that the user has permission to view
	 */
	public static List<ListTypeDefinition> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set of list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public static ListTypeDefinition[] filterFindByUuid_PrevAndNext(
			long listTypeDefinitionId, String uuid,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			listTypeDefinitionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the list type definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of list type definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list type definitions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of list type definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list type definitions that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type definitions
	 */
	public static List<ListTypeDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public static ListTypeDefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public static ListTypeDefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition
	 * @throws NoSuchListTypeDefinitionException if a matching list type definition could not be found
	 */
	public static ListTypeDefinition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	public static ListTypeDefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public static ListTypeDefinition[] findByUuid_C_PrevAndNext(
			long listTypeDefinitionId, String uuid, long companyId,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			listTypeDefinitionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list type definitions that the user has permission to view
	 */
	public static List<ListTypeDefinition> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of matching list type definitions that the user has permission to view
	 */
	public static List<ListTypeDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the list type definitions that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type definitions that the user has permission to view
	 */
	public static List<ListTypeDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the list type definitions before and after the current list type definition in the ordered set of list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeDefinitionId the primary key of the current list type definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public static ListTypeDefinition[] filterFindByUuid_C_PrevAndNext(
			long listTypeDefinitionId, String uuid, long companyId,
			OrderByComparator<ListTypeDefinition> orderByComparator)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			listTypeDefinitionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the list type definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of list type definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list type definitions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of list type definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list type definitions that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the list type definition in the entity cache if it is enabled.
	 *
	 * @param listTypeDefinition the list type definition
	 */
	public static void cacheResult(ListTypeDefinition listTypeDefinition) {
		getPersistence().cacheResult(listTypeDefinition);
	}

	/**
	 * Caches the list type definitions in the entity cache if it is enabled.
	 *
	 * @param listTypeDefinitions the list type definitions
	 */
	public static void cacheResult(
		List<ListTypeDefinition> listTypeDefinitions) {

		getPersistence().cacheResult(listTypeDefinitions);
	}

	/**
	 * Creates a new list type definition with the primary key. Does not add the list type definition to the database.
	 *
	 * @param listTypeDefinitionId the primary key for the new list type definition
	 * @return the new list type definition
	 */
	public static ListTypeDefinition create(long listTypeDefinitionId) {
		return getPersistence().create(listTypeDefinitionId);
	}

	/**
	 * Removes the list type definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition that was removed
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public static ListTypeDefinition remove(long listTypeDefinitionId)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().remove(listTypeDefinitionId);
	}

	public static ListTypeDefinition updateImpl(
		ListTypeDefinition listTypeDefinition) {

		return getPersistence().updateImpl(listTypeDefinition);
	}

	/**
	 * Returns the list type definition with the primary key or throws a <code>NoSuchListTypeDefinitionException</code> if it could not be found.
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition
	 * @throws NoSuchListTypeDefinitionException if a list type definition with the primary key could not be found
	 */
	public static ListTypeDefinition findByPrimaryKey(long listTypeDefinitionId)
		throws com.liferay.list.type.exception.
			NoSuchListTypeDefinitionException {

		return getPersistence().findByPrimaryKey(listTypeDefinitionId);
	}

	/**
	 * Returns the list type definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition, or <code>null</code> if a list type definition with the primary key could not be found
	 */
	public static ListTypeDefinition fetchByPrimaryKey(
		long listTypeDefinitionId) {

		return getPersistence().fetchByPrimaryKey(listTypeDefinitionId);
	}

	/**
	 * Returns all the list type definitions.
	 *
	 * @return the list type definitions
	 */
	public static List<ListTypeDefinition> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the list type definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of list type definitions
	 */
	public static List<ListTypeDefinition> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the list type definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list type definitions
	 */
	public static List<ListTypeDefinition> findAll(
		int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list type definitions
	 */
	public static List<ListTypeDefinition> findAll(
		int start, int end,
		OrderByComparator<ListTypeDefinition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the list type definitions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of list type definitions.
	 *
	 * @return the number of list type definitions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ListTypeDefinitionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ListTypeDefinitionPersistence, ListTypeDefinitionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ListTypeDefinitionPersistence.class);

		ServiceTracker
			<ListTypeDefinitionPersistence, ListTypeDefinitionPersistence>
				serviceTracker =
					new ServiceTracker
						<ListTypeDefinitionPersistence,
						 ListTypeDefinitionPersistence>(
							 bundle.getBundleContext(),
							 ListTypeDefinitionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}