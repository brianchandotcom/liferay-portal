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

import com.liferay.commerce.order.rule.model.COREntryRel;
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
 * The persistence utility for the cor entry rel service. This utility wraps <code>com.liferay.commerce.order.rule.service.persistence.impl.COREntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see COREntryRelPersistence
 * @generated
 */
public class COREntryRelUtil {

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
	public static void clearCache(COREntryRel corEntryRel) {
		getPersistence().clearCache(corEntryRel);
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
	public static Map<Serializable, COREntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<COREntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<COREntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<COREntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static COREntryRel update(COREntryRel corEntryRel) {
		return getPersistence().update(corEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static COREntryRel update(
		COREntryRel corEntryRel, ServiceContext serviceContext) {

		return getPersistence().update(corEntryRel, serviceContext);
	}

	/**
	 * Returns all the cor entry rels where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rels
	 */
	public static List<COREntryRel> findByCOREntryId(long COREntryId) {
		return getPersistence().findByCOREntryId(COREntryId);
	}

	/**
	 * Returns a range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of matching cor entry rels
	 */
	public static List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end) {

		return getPersistence().findByCOREntryId(COREntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entry rels
	 */
	public static List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().findByCOREntryId(
			COREntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entry rels
	 */
	public static List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCOREntryId(
			COREntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public static COREntryRel findByCOREntryId_First(
			long COREntryId, OrderByComparator<COREntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByCOREntryId_First(
			COREntryId, orderByComparator);
	}

	/**
	 * Returns the first cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public static COREntryRel fetchByCOREntryId_First(
		long COREntryId, OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().fetchByCOREntryId_First(
			COREntryId, orderByComparator);
	}

	/**
	 * Returns the last cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public static COREntryRel findByCOREntryId_Last(
			long COREntryId, OrderByComparator<COREntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByCOREntryId_Last(
			COREntryId, orderByComparator);
	}

	/**
	 * Returns the last cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public static COREntryRel fetchByCOREntryId_Last(
		long COREntryId, OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().fetchByCOREntryId_Last(
			COREntryId, orderByComparator);
	}

	/**
	 * Returns the cor entry rels before and after the current cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryRelId the primary key of the current cor entry rel
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public static COREntryRel[] findByCOREntryId_PrevAndNext(
			long COREntryRelId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByCOREntryId_PrevAndNext(
			COREntryRelId, COREntryId, orderByComparator);
	}

	/**
	 * Removes all the cor entry rels where COREntryId = &#63; from the database.
	 *
	 * @param COREntryId the cor entry ID
	 */
	public static void removeByCOREntryId(long COREntryId) {
		getPersistence().removeByCOREntryId(COREntryId);
	}

	/**
	 * Returns the number of cor entry rels where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	public static int countByCOREntryId(long COREntryId) {
		return getPersistence().countByCOREntryId(COREntryId);
	}

	/**
	 * Returns all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rels
	 */
	public static List<COREntryRel> findByC_C(
		long classNameId, long COREntryId) {

		return getPersistence().findByC_C(classNameId, COREntryId);
	}

	/**
	 * Returns a range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of matching cor entry rels
	 */
	public static List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end) {

		return getPersistence().findByC_C(classNameId, COREntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entry rels
	 */
	public static List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, COREntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entry rels
	 */
	public static List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, COREntryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public static COREntryRel findByC_C_First(
			long classNameId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByC_C_First(
			classNameId, COREntryId, orderByComparator);
	}

	/**
	 * Returns the first cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public static COREntryRel fetchByC_C_First(
		long classNameId, long COREntryId,
		OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, COREntryId, orderByComparator);
	}

	/**
	 * Returns the last cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public static COREntryRel findByC_C_Last(
			long classNameId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByC_C_Last(
			classNameId, COREntryId, orderByComparator);
	}

	/**
	 * Returns the last cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public static COREntryRel fetchByC_C_Last(
		long classNameId, long COREntryId,
		OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, COREntryId, orderByComparator);
	}

	/**
	 * Returns the cor entry rels before and after the current cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param COREntryRelId the primary key of the current cor entry rel
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public static COREntryRel[] findByC_C_PrevAndNext(
			long COREntryRelId, long classNameId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByC_C_PrevAndNext(
			COREntryRelId, classNameId, COREntryId, orderByComparator);
	}

	/**
	 * Removes all the cor entry rels where classNameId = &#63; and COREntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 */
	public static void removeByC_C(long classNameId, long COREntryId) {
		getPersistence().removeByC_C(classNameId, COREntryId);
	}

	/**
	 * Returns the number of cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	public static int countByC_C(long classNameId, long COREntryId) {
		return getPersistence().countByC_C(classNameId, COREntryId);
	}

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or throws a <code>NoSuchCOREntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	public static COREntryRel findByC_C_C(
			long classNameId, long classPK, long COREntryId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByC_C_C(classNameId, classPK, COREntryId);
	}

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public static COREntryRel fetchByC_C_C(
		long classNameId, long classPK, long COREntryId) {

		return getPersistence().fetchByC_C_C(classNameId, classPK, COREntryId);
	}

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	public static COREntryRel fetchByC_C_C(
		long classNameId, long classPK, long COREntryId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, COREntryId, useFinderCache);
	}

	/**
	 * Removes the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the cor entry rel that was removed
	 */
	public static COREntryRel removeByC_C_C(
			long classNameId, long classPK, long COREntryId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().removeByC_C_C(classNameId, classPK, COREntryId);
	}

	/**
	 * Returns the number of cor entry rels where classNameId = &#63; and classPK = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, long COREntryId) {

		return getPersistence().countByC_C_C(classNameId, classPK, COREntryId);
	}

	/**
	 * Caches the cor entry rel in the entity cache if it is enabled.
	 *
	 * @param corEntryRel the cor entry rel
	 */
	public static void cacheResult(COREntryRel corEntryRel) {
		getPersistence().cacheResult(corEntryRel);
	}

	/**
	 * Caches the cor entry rels in the entity cache if it is enabled.
	 *
	 * @param corEntryRels the cor entry rels
	 */
	public static void cacheResult(List<COREntryRel> corEntryRels) {
		getPersistence().cacheResult(corEntryRels);
	}

	/**
	 * Creates a new cor entry rel with the primary key. Does not add the cor entry rel to the database.
	 *
	 * @param COREntryRelId the primary key for the new cor entry rel
	 * @return the new cor entry rel
	 */
	public static COREntryRel create(long COREntryRelId) {
		return getPersistence().create(COREntryRelId);
	}

	/**
	 * Removes the cor entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel that was removed
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public static COREntryRel remove(long COREntryRelId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().remove(COREntryRelId);
	}

	public static COREntryRel updateImpl(COREntryRel corEntryRel) {
		return getPersistence().updateImpl(corEntryRel);
	}

	/**
	 * Returns the cor entry rel with the primary key or throws a <code>NoSuchCOREntryRelException</code> if it could not be found.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	public static COREntryRel findByPrimaryKey(long COREntryRelId)
		throws com.liferay.commerce.order.rule.exception.
			NoSuchCOREntryRelException {

		return getPersistence().findByPrimaryKey(COREntryRelId);
	}

	/**
	 * Returns the cor entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel, or <code>null</code> if a cor entry rel with the primary key could not be found
	 */
	public static COREntryRel fetchByPrimaryKey(long COREntryRelId) {
		return getPersistence().fetchByPrimaryKey(COREntryRelId);
	}

	/**
	 * Returns all the cor entry rels.
	 *
	 * @return the cor entry rels
	 */
	public static List<COREntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of cor entry rels
	 */
	public static List<COREntryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cor entry rels
	 */
	public static List<COREntryRel> findAll(
		int start, int end, OrderByComparator<COREntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cor entry rels
	 */
	public static List<COREntryRel> findAll(
		int start, int end, OrderByComparator<COREntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cor entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cor entry rels.
	 *
	 * @return the number of cor entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static COREntryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<COREntryRelPersistence, COREntryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(COREntryRelPersistence.class);

		ServiceTracker<COREntryRelPersistence, COREntryRelPersistence>
			serviceTracker =
				new ServiceTracker
					<COREntryRelPersistence, COREntryRelPersistence>(
						bundle.getBundleContext(), COREntryRelPersistence.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}