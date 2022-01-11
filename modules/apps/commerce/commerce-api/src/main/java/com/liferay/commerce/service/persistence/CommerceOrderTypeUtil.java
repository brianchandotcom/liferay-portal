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

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce order type service. This utility wraps <code>com.liferay.commerce.service.persistence.impl.CommerceOrderTypePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypePersistence
 * @generated
 */
public class CommerceOrderTypeUtil {

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
	public static void clearCache(CommerceOrderType commerceOrderType) {
		getPersistence().clearCache(commerceOrderType);
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
	public static Map<Serializable, CommerceOrderType> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderType> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderType update(
		CommerceOrderType commerceOrderType) {

		return getPersistence().update(commerceOrderType);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderType update(
		CommerceOrderType commerceOrderType, ServiceContext serviceContext) {

		return getPersistence().update(commerceOrderType, serviceContext);
	}

	/**
	 * Returns all the commerce order types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce order types
	 */
	public static List<CommerceOrderType> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce order types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] findByCompanyId_PrevAndNext(
			long commerceOrderTypeId, long companyId,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceOrderTypeId, companyId, orderByComparator);
	}

	/**
	 * Returns all the commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] filterFindByCompanyId_PrevAndNext(
			long commerceOrderTypeId, long companyId,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			commerceOrderTypeId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce order types where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce order types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce order types
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order types
	 */
	public static List<CommerceOrderType> findByC_A(
		long companyId, boolean active) {

		return getPersistence().findByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] findByC_A_PrevAndNext(
			long commerceOrderTypeId, long companyId, boolean active,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByC_A_PrevAndNext(
			commerceOrderTypeId, companyId, active, orderByComparator);
	}

	/**
	 * Returns all the commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByC_A(
		long companyId, boolean active) {

		return getPersistence().filterFindByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().filterFindByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().filterFindByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] filterFindByC_A_PrevAndNext(
			long commerceOrderTypeId, long companyId, boolean active,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().filterFindByC_A_PrevAndNext(
			commerceOrderTypeId, companyId, active, orderByComparator);
	}

	/**
	 * Removes all the commerce order types where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order types
	 */
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public static int filterCountByC_A(long companyId, boolean active) {
		return getPersistence().filterCountByC_A(companyId, active);
	}

	/**
	 * Returns all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status) {

		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] findByLtD_S_PrevAndNext(
			long commerceOrderTypeId, Date displayDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByLtD_S_PrevAndNext(
			commerceOrderTypeId, displayDate, status, orderByComparator);
	}

	/**
	 * Returns all the commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByLtD_S(
		Date displayDate, int status) {

		return getPersistence().filterFindByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().filterFindByLtD_S(
			displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().filterFindByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] filterFindByLtD_S_PrevAndNext(
			long commerceOrderTypeId, Date displayDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().filterFindByLtD_S_PrevAndNext(
			commerceOrderTypeId, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the commerce order types where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce order types
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public static int filterCountByLtD_S(Date displayDate, int status) {
		return getPersistence().filterCountByLtD_S(displayDate, status);
	}

	/**
	 * Returns all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status) {

		return getPersistence().findByLtE_S(expirationDate, status);
	}

	/**
	 * Returns a range of all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return getPersistence().findByLtE_S(expirationDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public static List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByLtE_S_Last(
			Date expirationDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByLtE_S_Last(
		Date expirationDate, int status,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().fetchByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] findByLtE_S_PrevAndNext(
			long commerceOrderTypeId, Date expirationDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByLtE_S_PrevAndNext(
			commerceOrderTypeId, expirationDate, status, orderByComparator);
	}

	/**
	 * Returns all the commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByLtE_S(
		Date expirationDate, int status) {

		return getPersistence().filterFindByLtE_S(expirationDate, status);
	}

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return getPersistence().filterFindByLtE_S(
			expirationDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public static List<CommerceOrderType> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().filterFindByLtE_S(
			expirationDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType[] filterFindByLtE_S_PrevAndNext(
			long commerceOrderTypeId, Date expirationDate, int status,
			OrderByComparator<CommerceOrderType> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().filterFindByLtE_S_PrevAndNext(
			commerceOrderTypeId, expirationDate, status, orderByComparator);
	}

	/**
	 * Removes all the commerce order types where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public static void removeByLtE_S(Date expirationDate, int status) {
		getPersistence().removeByLtE_S(expirationDate, status);
	}

	/**
	 * Returns the number of commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce order types
	 */
	public static int countByLtE_S(Date expirationDate, int status) {
		return getPersistence().countByLtE_S(expirationDate, status);
	}

	/**
	 * Returns the number of commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public static int filterCountByLtE_S(Date expirationDate, int status) {
		return getPersistence().filterCountByLtE_S(expirationDate, status);
	}

	/**
	 * Returns the commerce order type where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderTypeException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public static CommerceOrderType findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order type where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order type where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public static CommerceOrderType fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce order type where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order type that was removed
	 */
	public static CommerceOrderType removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce order types where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order types
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce order type in the entity cache if it is enabled.
	 *
	 * @param commerceOrderType the commerce order type
	 */
	public static void cacheResult(CommerceOrderType commerceOrderType) {
		getPersistence().cacheResult(commerceOrderType);
	}

	/**
	 * Caches the commerce order types in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypes the commerce order types
	 */
	public static void cacheResult(List<CommerceOrderType> commerceOrderTypes) {
		getPersistence().cacheResult(commerceOrderTypes);
	}

	/**
	 * Creates a new commerce order type with the primary key. Does not add the commerce order type to the database.
	 *
	 * @param commerceOrderTypeId the primary key for the new commerce order type
	 * @return the new commerce order type
	 */
	public static CommerceOrderType create(long commerceOrderTypeId) {
		return getPersistence().create(commerceOrderTypeId);
	}

	/**
	 * Removes the commerce order type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type that was removed
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType remove(long commerceOrderTypeId)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().remove(commerceOrderTypeId);
	}

	public static CommerceOrderType updateImpl(
		CommerceOrderType commerceOrderType) {

		return getPersistence().updateImpl(commerceOrderType);
	}

	/**
	 * Returns the commerce order type with the primary key or throws a <code>NoSuchOrderTypeException</code> if it could not be found.
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType findByPrimaryKey(long commerceOrderTypeId)
		throws com.liferay.commerce.exception.NoSuchOrderTypeException {

		return getPersistence().findByPrimaryKey(commerceOrderTypeId);
	}

	/**
	 * Returns the commerce order type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type, or <code>null</code> if a commerce order type with the primary key could not be found
	 */
	public static CommerceOrderType fetchByPrimaryKey(
		long commerceOrderTypeId) {

		return getPersistence().fetchByPrimaryKey(commerceOrderTypeId);
	}

	/**
	 * Returns all the commerce order types.
	 *
	 * @return the commerce order types
	 */
	public static List<CommerceOrderType> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce order types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of commerce order types
	 */
	public static List<CommerceOrderType> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order types
	 */
	public static List<CommerceOrderType> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order types
	 */
	public static List<CommerceOrderType> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderType> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce order types from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce order types.
	 *
	 * @return the number of commerce order types
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderTypePersistence getPersistence() {
		return _persistence;
	}

	private static volatile CommerceOrderTypePersistence _persistence;

}