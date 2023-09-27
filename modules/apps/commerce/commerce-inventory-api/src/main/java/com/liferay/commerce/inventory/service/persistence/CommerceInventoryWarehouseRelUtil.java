/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce inventory warehouse rel service. This utility wraps <code>com.liferay.commerce.inventory.service.persistence.impl.CommerceInventoryWarehouseRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseRelPersistence
 * @generated
 */
public class CommerceInventoryWarehouseRelUtil {

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
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		getPersistence().clearCache(commerceInventoryWarehouseRel);
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
	public static Map<Serializable, CommerceInventoryWarehouseRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceInventoryWarehouseRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceInventoryWarehouseRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceInventoryWarehouseRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceInventoryWarehouseRel update(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		return getPersistence().update(commerceInventoryWarehouseRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceInventoryWarehouseRel update(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceInventoryWarehouseRel, serviceContext);
	}

	/**
	 * Returns all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(long commerceInventoryWarehouseId) {

		return getPersistence().findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end) {

		return getPersistence().findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouseRel>
				orderByComparator) {

		return getPersistence().findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel>
		findByCommerceInventoryWarehouseId(
			long commerceInventoryWarehouseId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel
			findByCommerceInventoryWarehouseId_First(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByCommerceInventoryWarehouseId_First(
			commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel
		fetchByCommerceInventoryWarehouseId_First(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceInventoryWarehouseId_First(
			commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel
			findByCommerceInventoryWarehouseId_Last(
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByCommerceInventoryWarehouseId_Last(
			commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel
		fetchByCommerceInventoryWarehouseId_Last(
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceInventoryWarehouseId_Last(
			commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouse rels before and after the current commerce inventory warehouse rel in the ordered set where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the current commerce inventory warehouse rel
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public static CommerceInventoryWarehouseRel[]
			findByCommerceInventoryWarehouseId_PrevAndNext(
				long commerceInventoryWarehouseRelId,
				long commerceInventoryWarehouseId,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByCommerceInventoryWarehouseId_PrevAndNext(
			commerceInventoryWarehouseRelId, commerceInventoryWarehouseId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	public static void removeByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		getPersistence().removeByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the number of commerce inventory warehouse rels where commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	public static int countByCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		return getPersistence().countByCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId) {

		return getPersistence().findByC_C(
			classNameId, commerceInventoryWarehouseId);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start,
		int end) {

		return getPersistence().findByC_C(
			classNameId, commerceInventoryWarehouseId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, commerceInventoryWarehouseId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findByC_C(
		long classNameId, long commerceInventoryWarehouseId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, commerceInventoryWarehouseId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel findByC_C_First(
			long classNameId, long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByC_C_First(
			classNameId, commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel fetchByC_C_First(
		long classNameId, long commerceInventoryWarehouseId,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel findByC_C_Last(
			long classNameId, long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByC_C_Last(
			classNameId, commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel fetchByC_C_Last(
		long classNameId, long commerceInventoryWarehouseId,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Returns the commerce inventory warehouse rels before and after the current commerce inventory warehouse rel in the ordered set where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the current commerce inventory warehouse rel
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public static CommerceInventoryWarehouseRel[] findByC_C_PrevAndNext(
			long commerceInventoryWarehouseRelId, long classNameId,
			long commerceInventoryWarehouseId,
			OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceInventoryWarehouseRelId, classNameId,
			commerceInventoryWarehouseId, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 */
	public static void removeByC_C(
		long classNameId, long commerceInventoryWarehouseId) {

		getPersistence().removeByC_C(classNameId, commerceInventoryWarehouseId);
	}

	/**
	 * Returns the number of commerce inventory warehouse rels where classNameId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	public static int countByC_C(
		long classNameId, long commerceInventoryWarehouseId) {

		return getPersistence().countByC_C(
			classNameId, commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or throws a <code>NoSuchInventoryWarehouseRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel findByC_C_CIWI(
			long classNameId, long classPK, long commerceInventoryWarehouseId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByC_C_CIWI(
			classNameId, classPK, commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel fetchByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId) {

		return getPersistence().fetchByC_C_CIWI(
			classNameId, classPK, commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse rel, or <code>null</code> if a matching commerce inventory warehouse rel could not be found
	 */
	public static CommerceInventoryWarehouseRel fetchByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_CIWI(
			classNameId, classPK, commerceInventoryWarehouseId, useFinderCache);
	}

	/**
	 * Removes the commerce inventory warehouse rel where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the commerce inventory warehouse rel that was removed
	 */
	public static CommerceInventoryWarehouseRel removeByC_C_CIWI(
			long classNameId, long classPK, long commerceInventoryWarehouseId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().removeByC_C_CIWI(
			classNameId, classPK, commerceInventoryWarehouseId);
	}

	/**
	 * Returns the number of commerce inventory warehouse rels where classNameId = &#63; and classPK = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce inventory warehouse rels
	 */
	public static int countByC_C_CIWI(
		long classNameId, long classPK, long commerceInventoryWarehouseId) {

		return getPersistence().countByC_C_CIWI(
			classNameId, classPK, commerceInventoryWarehouseId);
	}

	/**
	 * Caches the commerce inventory warehouse rel in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseRel the commerce inventory warehouse rel
	 */
	public static void cacheResult(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		getPersistence().cacheResult(commerceInventoryWarehouseRel);
	}

	/**
	 * Caches the commerce inventory warehouse rels in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouseRels the commerce inventory warehouse rels
	 */
	public static void cacheResult(
		List<CommerceInventoryWarehouseRel> commerceInventoryWarehouseRels) {

		getPersistence().cacheResult(commerceInventoryWarehouseRels);
	}

	/**
	 * Creates a new commerce inventory warehouse rel with the primary key. Does not add the commerce inventory warehouse rel to the database.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key for the new commerce inventory warehouse rel
	 * @return the new commerce inventory warehouse rel
	 */
	public static CommerceInventoryWarehouseRel create(
		long commerceInventoryWarehouseRelId) {

		return getPersistence().create(commerceInventoryWarehouseRelId);
	}

	/**
	 * Removes the commerce inventory warehouse rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel that was removed
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public static CommerceInventoryWarehouseRel remove(
			long commerceInventoryWarehouseRelId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().remove(commerceInventoryWarehouseRelId);
	}

	public static CommerceInventoryWarehouseRel updateImpl(
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel) {

		return getPersistence().updateImpl(commerceInventoryWarehouseRel);
	}

	/**
	 * Returns the commerce inventory warehouse rel with the primary key or throws a <code>NoSuchInventoryWarehouseRelException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel
	 * @throws NoSuchInventoryWarehouseRelException if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public static CommerceInventoryWarehouseRel findByPrimaryKey(
			long commerceInventoryWarehouseRelId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryWarehouseRelException {

		return getPersistence().findByPrimaryKey(
			commerceInventoryWarehouseRelId);
	}

	/**
	 * Returns the commerce inventory warehouse rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseRelId the primary key of the commerce inventory warehouse rel
	 * @return the commerce inventory warehouse rel, or <code>null</code> if a commerce inventory warehouse rel with the primary key could not be found
	 */
	public static CommerceInventoryWarehouseRel fetchByPrimaryKey(
		long commerceInventoryWarehouseRelId) {

		return getPersistence().fetchByPrimaryKey(
			commerceInventoryWarehouseRelId);
	}

	/**
	 * Returns all the commerce inventory warehouse rels.
	 *
	 * @return the commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @return the range of commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory warehouse rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse rels
	 * @param end the upper bound of the range of commerce inventory warehouse rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory warehouse rels
	 */
	public static List<CommerceInventoryWarehouseRel> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryWarehouseRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce inventory warehouse rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce inventory warehouse rels.
	 *
	 * @return the number of commerce inventory warehouse rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceInventoryWarehouseRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceInventoryWarehouseRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceInventoryWarehouseRelPersistence
		_persistence;

}