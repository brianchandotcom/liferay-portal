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

package com.liferay.commerce.order.rule.service.persistence;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
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
 * The persistence utility for the commerce order rule entry service. This utility wraps <code>com.liferay.commerce.order.rule.service.persistence.impl.CommerceOrderRuleEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryPersistence
 * @generated
 */
public class CommerceOrderRuleEntryUtil {

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
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		getPersistence().clearCache(commerceOrderRuleEntry);
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
	public static Map<Serializable, CommerceOrderRuleEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderRuleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderRuleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderRuleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderRuleEntry update(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		return getPersistence().update(commerceOrderRuleEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderRuleEntry update(
		CommerceOrderRuleEntry commerceOrderRuleEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceOrderRuleEntry, serviceContext);
	}

	/**
	 * Returns all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active) {

		return getPersistence().findByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry[] findByC_A_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_A_PrevAndNext(
			commerceOrderRuleEntryId, companyId, active, orderByComparator);
	}

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_A(
		long companyId, boolean active) {

		return getPersistence().filterFindByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().filterFindByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().filterFindByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry[] filterFindByC_A_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().filterFindByC_A_PrevAndNext(
			commerceOrderRuleEntryId, companyId, active, orderByComparator);
	}

	/**
	 * Removes all the commerce order rule entries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order rule entries
	 */
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public static int filterCountByC_A(long companyId, boolean active) {
		return getPersistence().filterCountByC_A(companyId, active);
	}

	/**
	 * Returns all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type) {

		return getPersistence().findByC_LikeType(companyId, type);
	}

	/**
	 * Returns a range of all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type, int start, int end) {

		return getPersistence().findByC_LikeType(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().findByC_LikeType(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LikeType(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry findByC_LikeType_First(
			long companyId, String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_LikeType_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_LikeType_First(
		long companyId, String type,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().fetchByC_LikeType_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry findByC_LikeType_Last(
			long companyId, String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_LikeType_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_LikeType_Last(
		long companyId, String type,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().fetchByC_LikeType_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry[] findByC_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_LikeType_PrevAndNext(
			commerceOrderRuleEntryId, companyId, type, orderByComparator);
	}

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_LikeType(
		long companyId, String type) {

		return getPersistence().filterFindByC_LikeType(companyId, type);
	}

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end) {

		return getPersistence().filterFindByC_LikeType(
			companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().filterFindByC_LikeType(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry[] filterFindByC_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().filterFindByC_LikeType_PrevAndNext(
			commerceOrderRuleEntryId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the commerce order rule entries where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_LikeType(long companyId, String type) {
		getPersistence().removeByC_LikeType(companyId, type);
	}

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce order rule entries
	 */
	public static int countByC_LikeType(long companyId, String type) {
		return getPersistence().countByC_LikeType(companyId, type);
	}

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public static int filterCountByC_LikeType(long companyId, String type) {
		return getPersistence().filterCountByC_LikeType(companyId, type);
	}

	/**
	 * Returns all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().findByC_A_LikeType(companyId, active, type);
	}

	/**
	 * Returns a range of all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end) {

		return getPersistence().findByC_A_LikeType(
			companyId, active, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().findByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_A_LikeType_First(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().fetchByC_A_LikeType_First(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry findByC_A_LikeType_Last(
			long companyId, boolean active, String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_A_LikeType_Last(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_A_LikeType_Last(
		long companyId, boolean active, String type,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().fetchByC_A_LikeType_Last(
			companyId, active, type, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry[] findByC_A_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_A_LikeType_PrevAndNext(
			commerceOrderRuleEntryId, companyId, active, type,
			orderByComparator);
	}

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().filterFindByC_A_LikeType(
			companyId, active, type);
	}

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end) {

		return getPersistence().filterFindByC_A_LikeType(
			companyId, active, type, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public static List<CommerceOrderRuleEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().filterFindByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry[] filterFindByC_A_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			String type,
			OrderByComparator<CommerceOrderRuleEntry> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().filterFindByC_A_LikeType_PrevAndNext(
			commerceOrderRuleEntryId, companyId, active, type,
			orderByComparator);
	}

	/**
	 * Removes all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	public static void removeByC_A_LikeType(
		long companyId, boolean active, String type) {

		getPersistence().removeByC_A_LikeType(companyId, active, type);
	}

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce order rule entries
	 */
	public static int countByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().countByC_A_LikeType(companyId, active, type);
	}

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public static int filterCountByC_A_LikeType(
		long companyId, boolean active, String type) {

		return getPersistence().filterCountByC_A_LikeType(
			companyId, active, type);
	}

	/**
	 * Returns the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderRuleEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public static CommerceOrderRuleEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order rule entry that was removed
	 */
	public static CommerceOrderRuleEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order rule entries
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce order rule entry in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 */
	public static void cacheResult(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		getPersistence().cacheResult(commerceOrderRuleEntry);
	}

	/**
	 * Caches the commerce order rule entries in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntries the commerce order rule entries
	 */
	public static void cacheResult(
		List<CommerceOrderRuleEntry> commerceOrderRuleEntries) {

		getPersistence().cacheResult(commerceOrderRuleEntries);
	}

	/**
	 * Creates a new commerce order rule entry with the primary key. Does not add the commerce order rule entry to the database.
	 *
	 * @param commerceOrderRuleEntryId the primary key for the new commerce order rule entry
	 * @return the new commerce order rule entry
	 */
	public static CommerceOrderRuleEntry create(long commerceOrderRuleEntryId) {
		return getPersistence().create(commerceOrderRuleEntryId);
	}

	/**
	 * Removes the commerce order rule entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry remove(long commerceOrderRuleEntryId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().remove(commerceOrderRuleEntryId);
	}

	public static CommerceOrderRuleEntry updateImpl(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		return getPersistence().updateImpl(commerceOrderRuleEntry);
	}

	/**
	 * Returns the commerce order rule entry with the primary key or throws a <code>NoSuchOrderRuleEntryException</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry findByPrimaryKey(
			long commerceOrderRuleEntryId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchOrderRuleEntryException {

		return getPersistence().findByPrimaryKey(commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry, or <code>null</code> if a commerce order rule entry with the primary key could not be found
	 */
	public static CommerceOrderRuleEntry fetchByPrimaryKey(
		long commerceOrderRuleEntryId) {

		return getPersistence().fetchByPrimaryKey(commerceOrderRuleEntryId);
	}

	/**
	 * Returns all the commerce order rule entries.
	 *
	 * @return the commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order rule entries
	 */
	public static List<CommerceOrderRuleEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderRuleEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce order rule entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce order rule entries.
	 *
	 * @return the number of commerce order rule entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderRuleEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceOrderRuleEntryPersistence, CommerceOrderRuleEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceOrderRuleEntryPersistence.class);

		ServiceTracker
			<CommerceOrderRuleEntryPersistence,
			 CommerceOrderRuleEntryPersistence> serviceTracker =
				new ServiceTracker
					<CommerceOrderRuleEntryPersistence,
					 CommerceOrderRuleEntryPersistence>(
						 bundle.getBundleContext(),
						 CommerceOrderRuleEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}