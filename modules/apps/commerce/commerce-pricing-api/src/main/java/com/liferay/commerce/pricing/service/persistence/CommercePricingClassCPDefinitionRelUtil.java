/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.service.persistence;

import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce pricing class cp definition rel service. This utility wraps <code>com.liferay.commerce.pricing.service.persistence.impl.CommercePricingClassCPDefinitionRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRelPersistence
 * @generated
 */
public class CommercePricingClassCPDefinitionRelUtil {

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
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		getPersistence().clearCache(commercePricingClassCPDefinitionRel);
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
	public static Map<Serializable, CommercePricingClassCPDefinitionRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePricingClassCPDefinitionRel update(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		return getPersistence().update(commercePricingClassCPDefinitionRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePricingClassCPDefinitionRel update(
		CommercePricingClassCPDefinitionRel commercePricingClassCPDefinitionRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePricingClassCPDefinitionRel, serviceContext);
	}

	/**
	 * Returns all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @return the matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(long commercePricingClassId) {

		return getPersistence().findByCommercePricingClassId(
			commercePricingClassId);
	}

	/**
	 * Returns a range of all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @return the range of matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(
			long commercePricingClassId, int start, int end) {

		return getPersistence().findByCommercePricingClassId(
			commercePricingClassId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(
			long commercePricingClassId, int start, int end,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return getPersistence().findByCommercePricingClassId(
			commercePricingClassId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCommercePricingClassId(
			long commercePricingClassId, int start, int end,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommercePricingClassId(
			commercePricingClassId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel
			findByCommercePricingClassId_First(
				long commercePricingClassId,
				OrderByComparator<CommercePricingClassCPDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByCommercePricingClassId_First(
			commercePricingClassId, orderByComparator);
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel
		fetchByCommercePricingClassId_First(
			long commercePricingClassId,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByCommercePricingClassId_First(
			commercePricingClassId, orderByComparator);
	}

	/**
	 * Returns the last commerce pricing class cp definition rel in the ordered set where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel
			findByCommercePricingClassId_Last(
				long commercePricingClassId,
				OrderByComparator<CommercePricingClassCPDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByCommercePricingClassId_Last(
			commercePricingClassId, orderByComparator);
	}

	/**
	 * Returns the last commerce pricing class cp definition rel in the ordered set where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel
		fetchByCommercePricingClassId_Last(
			long commercePricingClassId,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByCommercePricingClassId_Last(
			commercePricingClassId, orderByComparator);
	}

	/**
	 * Returns the commerce pricing class cp definition rels before and after the current commerce pricing class cp definition rel in the ordered set where commercePricingClassId = &#63;.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the current commerce pricing class cp definition rel
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	public static CommercePricingClassCPDefinitionRel[]
			findByCommercePricingClassId_PrevAndNext(
				long CommercePricingClassCPDefinitionRelId,
				long commercePricingClassId,
				OrderByComparator<CommercePricingClassCPDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByCommercePricingClassId_PrevAndNext(
			CommercePricingClassCPDefinitionRelId, commercePricingClassId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce pricing class cp definition rels where commercePricingClassId = &#63; from the database.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 */
	public static void removeByCommercePricingClassId(
		long commercePricingClassId) {

		getPersistence().removeByCommercePricingClassId(commercePricingClassId);
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels where commercePricingClassId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @return the number of matching commerce pricing class cp definition rels
	 */
	public static int countByCommercePricingClassId(
		long commercePricingClassId) {

		return getPersistence().countByCommercePricingClassId(
			commercePricingClassId);
	}

	/**
	 * Returns all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCPDefinitionId(long CPDefinitionId) {

		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns a range of all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @return the range of matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCPDefinitionId(long CPDefinitionId, int start, int end) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCPDefinitionId(
			long CPDefinitionId, int start, int end,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel>
		findByCPDefinitionId(
			long CPDefinitionId, int start, int end,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel
			findByCPDefinitionId_First(
				long CPDefinitionId,
				OrderByComparator<CommercePricingClassCPDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first commerce pricing class cp definition rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel
		fetchByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last commerce pricing class cp definition rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last commerce pricing class cp definition rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel
		fetchByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CommercePricingClassCPDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the commerce pricing class cp definition rels before and after the current commerce pricing class cp definition rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the current commerce pricing class cp definition rel
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	public static CommercePricingClassCPDefinitionRel[]
			findByCPDefinitionId_PrevAndNext(
				long CommercePricingClassCPDefinitionRelId, long CPDefinitionId,
				OrderByComparator<CommercePricingClassCPDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByCPDefinitionId_PrevAndNext(
			CommercePricingClassCPDefinitionRelId, CPDefinitionId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce pricing class cp definition rels where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching commerce pricing class cp definition rels
	 */
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; or throws a <code>NoSuchPricingClassCPDefinitionRelException</code> if it could not be found.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel findByC_C(
			long commercePricingClassId, long CPDefinitionId)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByC_C(
			commercePricingClassId, CPDefinitionId);
	}

	/**
	 * Returns the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel fetchByC_C(
		long commercePricingClassId, long CPDefinitionId) {

		return getPersistence().fetchByC_C(
			commercePricingClassId, CPDefinitionId);
	}

	/**
	 * Returns the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce pricing class cp definition rel, or <code>null</code> if a matching commerce pricing class cp definition rel could not be found
	 */
	public static CommercePricingClassCPDefinitionRel fetchByC_C(
		long commercePricingClassId, long CPDefinitionId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C(
			commercePricingClassId, CPDefinitionId, useFinderCache);
	}

	/**
	 * Removes the commerce pricing class cp definition rel where commercePricingClassId = &#63; and CPDefinitionId = &#63; from the database.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the commerce pricing class cp definition rel that was removed
	 */
	public static CommercePricingClassCPDefinitionRel removeByC_C(
			long commercePricingClassId, long CPDefinitionId)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().removeByC_C(
			commercePricingClassId, CPDefinitionId);
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels where commercePricingClassId = &#63; and CPDefinitionId = &#63;.
	 *
	 * @param commercePricingClassId the commerce pricing class ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching commerce pricing class cp definition rels
	 */
	public static int countByC_C(
		long commercePricingClassId, long CPDefinitionId) {

		return getPersistence().countByC_C(
			commercePricingClassId, CPDefinitionId);
	}

	/**
	 * Caches the commerce pricing class cp definition rel in the entity cache if it is enabled.
	 *
	 * @param commercePricingClassCPDefinitionRel the commerce pricing class cp definition rel
	 */
	public static void cacheResult(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		getPersistence().cacheResult(commercePricingClassCPDefinitionRel);
	}

	/**
	 * Caches the commerce pricing class cp definition rels in the entity cache if it is enabled.
	 *
	 * @param commercePricingClassCPDefinitionRels the commerce pricing class cp definition rels
	 */
	public static void cacheResult(
		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels) {

		getPersistence().cacheResult(commercePricingClassCPDefinitionRels);
	}

	/**
	 * Creates a new commerce pricing class cp definition rel with the primary key. Does not add the commerce pricing class cp definition rel to the database.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key for the new commerce pricing class cp definition rel
	 * @return the new commerce pricing class cp definition rel
	 */
	public static CommercePricingClassCPDefinitionRel create(
		long CommercePricingClassCPDefinitionRelId) {

		return getPersistence().create(CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Removes the commerce pricing class cp definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel that was removed
	 * @throws NoSuchPricingClassCPDefinitionRelException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	public static CommercePricingClassCPDefinitionRel remove(
			long CommercePricingClassCPDefinitionRelId)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().remove(CommercePricingClassCPDefinitionRelId);
	}

	public static CommercePricingClassCPDefinitionRel updateImpl(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		return getPersistence().updateImpl(commercePricingClassCPDefinitionRel);
	}

	/**
	 * Returns the commerce pricing class cp definition rel with the primary key or throws a <code>NoSuchPricingClassCPDefinitionRelException</code> if it could not be found.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel
	 * @throws NoSuchPricingClassCPDefinitionRelException if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	public static CommercePricingClassCPDefinitionRel findByPrimaryKey(
			long CommercePricingClassCPDefinitionRelId)
		throws com.liferay.commerce.pricing.exception.
			NoSuchPricingClassCPDefinitionRelException {

		return getPersistence().findByPrimaryKey(
			CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Returns the commerce pricing class cp definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the primary key of the commerce pricing class cp definition rel
	 * @return the commerce pricing class cp definition rel, or <code>null</code> if a commerce pricing class cp definition rel with the primary key could not be found
	 */
	public static CommercePricingClassCPDefinitionRel fetchByPrimaryKey(
		long CommercePricingClassCPDefinitionRelId) {

		return getPersistence().fetchByPrimaryKey(
			CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Returns all the commerce pricing class cp definition rels.
	 *
	 * @return the commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce pricing class cp definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @return the range of commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CommercePricingClassCPDefinitionRel>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce pricing class cp definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePricingClassCPDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce pricing class cp definition rels
	 * @param end the upper bound of the range of commerce pricing class cp definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce pricing class cp definition rels
	 */
	public static List<CommercePricingClassCPDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CommercePricingClassCPDefinitionRel>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce pricing class cp definition rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce pricing class cp definition rels.
	 *
	 * @return the number of commerce pricing class cp definition rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePricingClassCPDefinitionRelPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		CommercePricingClassCPDefinitionRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommercePricingClassCPDefinitionRelPersistence
		_persistence;

}