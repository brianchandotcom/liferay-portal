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

package com.liferay.commerce.pricing.service.persistence;

import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce price modifier rel service. This utility wraps <code>com.liferay.commerce.pricing.service.persistence.impl.CommercePriceModifierRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierRelPersistence
 * @generated
 */
public class CommercePriceModifierRelUtil {

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
		CommercePriceModifierRel commercePriceModifierRel) {

		getPersistence().clearCache(commercePriceModifierRel);
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
	public static Map<Serializable, CommercePriceModifierRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePriceModifierRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceModifierRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceModifierRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceModifierRel update(
		CommercePriceModifierRel commercePriceModifierRel) {

		return getPersistence().update(commercePriceModifierRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceModifierRel update(
		CommercePriceModifierRel commercePriceModifierRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePriceModifierRel, serviceContext);
	}

	/**
	 * Returns all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @return the matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId) {

		return getPersistence().findByCommercePriceModifierId(
			commercePriceModifierId);
	}

	/**
	 * Returns a range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId, int start, int end) {

		return getPersistence().findByCommercePriceModifierId(
			commercePriceModifierId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().findByCommercePriceModifierId(
			commercePriceModifierId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCommercePriceModifierId(
		long commercePriceModifierId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommercePriceModifierId(
			commercePriceModifierId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel findByCommercePriceModifierId_First(
			long commercePriceModifierId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCommercePriceModifierId_First(
			commercePriceModifierId, orderByComparator);
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCommercePriceModifierId_First(
		long commercePriceModifierId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().fetchByCommercePriceModifierId_First(
			commercePriceModifierId, orderByComparator);
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel findByCommercePriceModifierId_Last(
			long commercePriceModifierId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCommercePriceModifierId_Last(
			commercePriceModifierId, orderByComparator);
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCommercePriceModifierId_Last(
		long commercePriceModifierId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().fetchByCommercePriceModifierId_Last(
			commercePriceModifierId, orderByComparator);
	}

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public static CommercePriceModifierRel[]
			findByCommercePriceModifierId_PrevAndNext(
				long commercePriceModifierRelId, long commercePriceModifierId,
				OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCommercePriceModifierId_PrevAndNext(
			commercePriceModifierRelId, commercePriceModifierId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price modifier rels where commercePriceModifierId = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 */
	public static void removeByCommercePriceModifierId(
		long commercePriceModifierId) {

		getPersistence().removeByCommercePriceModifierId(
			commercePriceModifierId);
	}

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @return the number of matching commerce price modifier rels
	 */
	public static int countByCommercePriceModifierId(
		long commercePriceModifierId) {

		return getPersistence().countByCommercePriceModifierId(
			commercePriceModifierId);
	}

	/**
	 * Returns all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @return the matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId) {

		return getPersistence().findByCPM_CN(
			commercePriceModifierId, classNameId);
	}

	/**
	 * Returns a range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end) {

		return getPersistence().findByCPM_CN(
			commercePriceModifierId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().findByCPM_CN(
			commercePriceModifierId, classNameId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCPM_CN(
		long commercePriceModifierId, long classNameId, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPM_CN(
			commercePriceModifierId, classNameId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel findByCPM_CN_First(
			long commercePriceModifierId, long classNameId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCPM_CN_First(
			commercePriceModifierId, classNameId, orderByComparator);
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCPM_CN_First(
		long commercePriceModifierId, long classNameId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().fetchByCPM_CN_First(
			commercePriceModifierId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel findByCPM_CN_Last(
			long commercePriceModifierId, long classNameId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCPM_CN_Last(
			commercePriceModifierId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCPM_CN_Last(
		long commercePriceModifierId, long classNameId,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().fetchByCPM_CN_Last(
			commercePriceModifierId, classNameId, orderByComparator);
	}

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public static CommercePriceModifierRel[] findByCPM_CN_PrevAndNext(
			long commercePriceModifierRelId, long commercePriceModifierId,
			long classNameId,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCPM_CN_PrevAndNext(
			commercePriceModifierRelId, commercePriceModifierId, classNameId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 */
	public static void removeByCPM_CN(
		long commercePriceModifierId, long classNameId) {

		getPersistence().removeByCPM_CN(commercePriceModifierId, classNameId);
	}

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @return the number of matching commerce price modifier rels
	 */
	public static int countByCPM_CN(
		long commercePriceModifierId, long classNameId) {

		return getPersistence().countByCPM_CN(
			commercePriceModifierId, classNameId);
	}

	/**
	 * Returns all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK) {

		return getPersistence().findByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns a range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByCN_CPK(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel findByCN_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCN_CPK_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCN_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().fetchByCN_CPK_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel findByCN_CPK_Last(
			long classNameId, long classPK,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCN_CPK_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCN_CPK_Last(
		long classNameId, long classPK,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().fetchByCN_CPK_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the commerce price modifier rels before and after the current commerce price modifier rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceModifierRelId the primary key of the current commerce price modifier rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public static CommercePriceModifierRel[] findByCN_CPK_PrevAndNext(
			long commercePriceModifierRelId, long classNameId, long classPK,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCN_CPK_PrevAndNext(
			commercePriceModifierRelId, classNameId, classPK,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price modifier rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByCN_CPK(long classNameId, long classPK) {
		getPersistence().removeByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns the number of commerce price modifier rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce price modifier rels
	 */
	public static int countByCN_CPK(long classNameId, long classPK) {
		return getPersistence().countByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPriceModifierRelException</code> if it could not be found.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel findByCPM_CN_CPK(
			long commercePriceModifierId, long classNameId, long classPK)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK);
	}

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK) {

		return getPersistence().fetchByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK);
	}

	/**
	 * Returns the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price modifier rel, or <code>null</code> if a matching commerce price modifier rel could not be found
	 */
	public static CommercePriceModifierRel fetchByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK,
		boolean useFinderCache) {

		return getPersistence().fetchByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the commerce price modifier rel where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the commerce price modifier rel that was removed
	 */
	public static CommercePriceModifierRel removeByCPM_CN_CPK(
			long commercePriceModifierId, long classNameId, long classPK)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().removeByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK);
	}

	/**
	 * Returns the number of commerce price modifier rels where commercePriceModifierId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce price modifier rels
	 */
	public static int countByCPM_CN_CPK(
		long commercePriceModifierId, long classNameId, long classPK) {

		return getPersistence().countByCPM_CN_CPK(
			commercePriceModifierId, classNameId, classPK);
	}

	/**
	 * Caches the commerce price modifier rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifierRel the commerce price modifier rel
	 */
	public static void cacheResult(
		CommercePriceModifierRel commercePriceModifierRel) {

		getPersistence().cacheResult(commercePriceModifierRel);
	}

	/**
	 * Caches the commerce price modifier rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifierRels the commerce price modifier rels
	 */
	public static void cacheResult(
		List<CommercePriceModifierRel> commercePriceModifierRels) {

		getPersistence().cacheResult(commercePriceModifierRels);
	}

	/**
	 * Creates a new commerce price modifier rel with the primary key. Does not add the commerce price modifier rel to the database.
	 *
	 * @param commercePriceModifierRelId the primary key for the new commerce price modifier rel
	 * @return the new commerce price modifier rel
	 */
	public static CommercePriceModifierRel create(
		long commercePriceModifierRelId) {

		return getPersistence().create(commercePriceModifierRelId);
	}

	/**
	 * Removes the commerce price modifier rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel that was removed
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public static CommercePriceModifierRel remove(
			long commercePriceModifierRelId)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().remove(commercePriceModifierRelId);
	}

	public static CommercePriceModifierRel updateImpl(
		CommercePriceModifierRel commercePriceModifierRel) {

		return getPersistence().updateImpl(commercePriceModifierRel);
	}

	/**
	 * Returns the commerce price modifier rel with the primary key or throws a <code>NoSuchPriceModifierRelException</code> if it could not be found.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel
	 * @throws NoSuchPriceModifierRelException if a commerce price modifier rel with the primary key could not be found
	 */
	public static CommercePriceModifierRel findByPrimaryKey(
			long commercePriceModifierRelId)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPriceModifierRelException {

		return getPersistence().findByPrimaryKey(commercePriceModifierRelId);
	}

	/**
	 * Returns the commerce price modifier rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceModifierRelId the primary key of the commerce price modifier rel
	 * @return the commerce price modifier rel, or <code>null</code> if a commerce price modifier rel with the primary key could not be found
	 */
	public static CommercePriceModifierRel fetchByPrimaryKey(
		long commercePriceModifierRelId) {

		return getPersistence().fetchByPrimaryKey(commercePriceModifierRelId);
	}

	/**
	 * Returns all the commerce price modifier rels.
	 *
	 * @return the commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @return the range of commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price modifier rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifier rels
	 * @param end the upper bound of the range of commerce price modifier rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price modifier rels
	 */
	public static List<CommercePriceModifierRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce price modifier rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce price modifier rels.
	 *
	 * @return the number of commerce price modifier rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePriceModifierRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommercePriceModifierRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommercePriceModifierRelPersistence _persistence;

}