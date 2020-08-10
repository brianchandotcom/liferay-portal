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

package com.liferay.commerce.account.service.persistence;

import com.liferay.commerce.account.model.CommerceAccountGroupRel;
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
 * The persistence utility for the commerce account group rel service. This utility wraps <code>com.liferay.commerce.account.service.persistence.impl.CommerceAccountGroupRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelPersistence
 * @generated
 */
public class CommerceAccountGroupRelUtil {

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
		CommerceAccountGroupRel commerceAccountGroupRel) {

		getPersistence().clearCache(commerceAccountGroupRel);
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
	public static Map<Serializable, CommerceAccountGroupRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceAccountGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAccountGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAccountGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAccountGroupRel update(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		return getPersistence().update(commerceAccountGroupRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAccountGroupRel update(
		CommerceAccountGroupRel commerceAccountGroupRel,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceAccountGroupRel, serviceContext);
	}

	/**
	 * Returns all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Returns a range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel findByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByCommerceAccountGroupId_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the first commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel fetchByCommerceAccountGroupId_First(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupId_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel findByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByCommerceAccountGroupId_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel fetchByCommerceAccountGroupId_Last(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupId_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the commerce account group rels before and after the current commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupRelId the primary key of the current commerce account group rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public static CommerceAccountGroupRel[]
			findByCommerceAccountGroupId_PrevAndNext(
				long commerceAccountGroupRelId, long commerceAccountGroupId,
				OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByCommerceAccountGroupId_PrevAndNext(
			commerceAccountGroupRelId, commerceAccountGroupId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public static void removeByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		getPersistence().removeByCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group rels
	 */
	public static int countByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		return getPersistence().countByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Returns all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the commerce account group rels before and after the current commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceAccountGroupRelId the primary key of the current commerce account group rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public static CommerceAccountGroupRel[] findByC_C_PrevAndNext(
			long commerceAccountGroupRelId, long classNameId, long classPK,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceAccountGroupRelId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the commerce account group rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce account group rels
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchAccountGroupRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel findByC_C_C(
			long classNameId, long classPK, long commerceAccountGroupId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByC_C_C(
			classNameId, classPK, commerceAccountGroupId);
	}

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel fetchByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceAccountGroupId);
	}

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	public static CommerceAccountGroupRel fetchByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, commerceAccountGroupId, useFinderCache);
	}

	/**
	 * Removes the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce account group rel that was removed
	 */
	public static CommerceAccountGroupRel removeByC_C_C(
			long classNameId, long classPK, long commerceAccountGroupId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce account group rels where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group rels
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, commerceAccountGroupId);
	}

	/**
	 * Caches the commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 */
	public static void cacheResult(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		getPersistence().cacheResult(commerceAccountGroupRel);
	}

	/**
	 * Caches the commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupRels the commerce account group rels
	 */
	public static void cacheResult(
		List<CommerceAccountGroupRel> commerceAccountGroupRels) {

		getPersistence().cacheResult(commerceAccountGroupRels);
	}

	/**
	 * Creates a new commerce account group rel with the primary key. Does not add the commerce account group rel to the database.
	 *
	 * @param commerceAccountGroupRelId the primary key for the new commerce account group rel
	 * @return the new commerce account group rel
	 */
	public static CommerceAccountGroupRel create(
		long commerceAccountGroupRelId) {

		return getPersistence().create(commerceAccountGroupRelId);
	}

	/**
	 * Removes the commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel that was removed
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public static CommerceAccountGroupRel remove(long commerceAccountGroupRelId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().remove(commerceAccountGroupRelId);
	}

	public static CommerceAccountGroupRel updateImpl(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		return getPersistence().updateImpl(commerceAccountGroupRel);
	}

	/**
	 * Returns the commerce account group rel with the primary key or throws a <code>NoSuchAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	public static CommerceAccountGroupRel findByPrimaryKey(
			long commerceAccountGroupRelId)
		throws com.liferay.commerce.account.exception.
			NoSuchAccountGroupRelException {

		return getPersistence().findByPrimaryKey(commerceAccountGroupRelId);
	}

	/**
	 * Returns the commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel, or <code>null</code> if a commerce account group rel with the primary key could not be found
	 */
	public static CommerceAccountGroupRel fetchByPrimaryKey(
		long commerceAccountGroupRelId) {

		return getPersistence().fetchByPrimaryKey(commerceAccountGroupRelId);
	}

	/**
	 * Returns all the commerce account group rels.
	 *
	 * @return the commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account group rels
	 */
	public static List<CommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce account group rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce account group rels.
	 *
	 * @return the number of commerce account group rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceAccountGroupRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountGroupRelPersistence, CommerceAccountGroupRelPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupRelPersistence.class);

		ServiceTracker
			<CommerceAccountGroupRelPersistence,
			 CommerceAccountGroupRelPersistence> serviceTracker =
				new ServiceTracker
					<CommerceAccountGroupRelPersistence,
					 CommerceAccountGroupRelPersistence>(
						 bundle.getBundleContext(),
						 CommerceAccountGroupRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}