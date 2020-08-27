/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.service.persistence;

import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
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
 * The persistence utility for the commerce application model c product rel service. This utility wraps <code>com.liferay.commerce.application.service.persistence.impl.CommerceApplicationModelCProductRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRelPersistence
 * @generated
 */
public class CommerceApplicationModelCProductRelUtil {

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
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		getPersistence().clearCache(commerceApplicationModelCProductRel);
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
	public static Map<Serializable, CommerceApplicationModelCProductRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceApplicationModelCProductRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceApplicationModelCProductRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceApplicationModelCProductRel>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceApplicationModelCProductRel update(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		return getPersistence().update(commerceApplicationModelCProductRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceApplicationModelCProductRel update(
		CommerceApplicationModelCProductRel commerceApplicationModelCProductRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceApplicationModelCProductRel, serviceContext);
	}

	/**
	 * Returns all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(long commerceApplicationModelId) {

		return getPersistence().findByCommerceApplicationModelId(
			commerceApplicationModelId);
	}

	/**
	 * Returns a range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end) {

		return getPersistence().findByCommerceApplicationModelId(
			commerceApplicationModelId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator) {

		return getPersistence().findByCommerceApplicationModelId(
			commerceApplicationModelId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceApplicationModelId(
			commerceApplicationModelId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel
			findByCommerceApplicationModelId_First(
				long commerceApplicationModelId,
				OrderByComparator<CommerceApplicationModelCProductRel>
					orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().findByCommerceApplicationModelId_First(
			commerceApplicationModelId, orderByComparator);
	}

	/**
	 * Returns the first commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel
		fetchByCommerceApplicationModelId_First(
			long commerceApplicationModelId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceApplicationModelId_First(
			commerceApplicationModelId, orderByComparator);
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel
			findByCommerceApplicationModelId_Last(
				long commerceApplicationModelId,
				OrderByComparator<CommerceApplicationModelCProductRel>
					orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().findByCommerceApplicationModelId_Last(
			commerceApplicationModelId, orderByComparator);
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel
		fetchByCommerceApplicationModelId_Last(
			long commerceApplicationModelId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceApplicationModelId_Last(
			commerceApplicationModelId, orderByComparator);
	}

	/**
	 * Returns the commerce application model c product rels before and after the current commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the current commerce application model c product rel
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public static CommerceApplicationModelCProductRel[]
			findByCommerceApplicationModelId_PrevAndNext(
				long commerceApplicationModelCProductRelId,
				long commerceApplicationModelId,
				OrderByComparator<CommerceApplicationModelCProductRel>
					orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().findByCommerceApplicationModelId_PrevAndNext(
			commerceApplicationModelCProductRelId, commerceApplicationModelId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce application model c product rels where commerceApplicationModelId = &#63; from the database.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 */
	public static void removeByCommerceApplicationModelId(
		long commerceApplicationModelId) {

		getPersistence().removeByCommerceApplicationModelId(
			commerceApplicationModelId);
	}

	/**
	 * Returns the number of commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the number of matching commerce application model c product rels
	 */
	public static int countByCommerceApplicationModelId(
		long commerceApplicationModelId) {

		return getPersistence().countByCommerceApplicationModelId(
			commerceApplicationModelId);
	}

	/**
	 * Returns all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId) {

		return getPersistence().findByCProductId(CProductId);
	}

	/**
	 * Returns a range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end) {

		return getPersistence().findByCProductId(CProductId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		return getPersistence().findByCProductId(
			CProductId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCProductId(
			CProductId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel findByCProductId_First(
			long CProductId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().findByCProductId_First(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the first commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel fetchByCProductId_First(
		long CProductId,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		return getPersistence().fetchByCProductId_First(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel findByCProductId_Last(
			long CProductId,
			OrderByComparator<CommerceApplicationModelCProductRel>
				orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().findByCProductId_Last(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the last commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public static CommerceApplicationModelCProductRel fetchByCProductId_Last(
		long CProductId,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		return getPersistence().fetchByCProductId_Last(
			CProductId, orderByComparator);
	}

	/**
	 * Returns the commerce application model c product rels before and after the current commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the current commerce application model c product rel
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public static CommerceApplicationModelCProductRel[]
			findByCProductId_PrevAndNext(
				long commerceApplicationModelCProductRelId, long CProductId,
				OrderByComparator<CommerceApplicationModelCProductRel>
					orderByComparator)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().findByCProductId_PrevAndNext(
			commerceApplicationModelCProductRelId, CProductId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce application model c product rels where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	public static void removeByCProductId(long CProductId) {
		getPersistence().removeByCProductId(CProductId);
	}

	/**
	 * Returns the number of commerce application model c product rels where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching commerce application model c product rels
	 */
	public static int countByCProductId(long CProductId) {
		return getPersistence().countByCProductId(CProductId);
	}

	/**
	 * Caches the commerce application model c product rel in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModelCProductRel the commerce application model c product rel
	 */
	public static void cacheResult(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		getPersistence().cacheResult(commerceApplicationModelCProductRel);
	}

	/**
	 * Caches the commerce application model c product rels in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModelCProductRels the commerce application model c product rels
	 */
	public static void cacheResult(
		List<CommerceApplicationModelCProductRel>
			commerceApplicationModelCProductRels) {

		getPersistence().cacheResult(commerceApplicationModelCProductRels);
	}

	/**
	 * Creates a new commerce application model c product rel with the primary key. Does not add the commerce application model c product rel to the database.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key for the new commerce application model c product rel
	 * @return the new commerce application model c product rel
	 */
	public static CommerceApplicationModelCProductRel create(
		long commerceApplicationModelCProductRelId) {

		return getPersistence().create(commerceApplicationModelCProductRelId);
	}

	/**
	 * Removes the commerce application model c product rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel that was removed
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public static CommerceApplicationModelCProductRel remove(
			long commerceApplicationModelCProductRelId)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().remove(commerceApplicationModelCProductRelId);
	}

	public static CommerceApplicationModelCProductRel updateImpl(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel) {

		return getPersistence().updateImpl(commerceApplicationModelCProductRel);
	}

	/**
	 * Returns the commerce application model c product rel with the primary key or throws a <code>NoSuchApplicationModelCProductRelException</code> if it could not be found.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public static CommerceApplicationModelCProductRel findByPrimaryKey(
			long commerceApplicationModelCProductRelId)
		throws com.liferay.commerce.application.exception.
			NoSuchApplicationModelCProductRelException {

		return getPersistence().findByPrimaryKey(
			commerceApplicationModelCProductRelId);
	}

	/**
	 * Returns the commerce application model c product rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel, or <code>null</code> if a commerce application model c product rel with the primary key could not be found
	 */
	public static CommerceApplicationModelCProductRel fetchByPrimaryKey(
		long commerceApplicationModelCProductRelId) {

		return getPersistence().fetchByPrimaryKey(
			commerceApplicationModelCProductRelId);
	}

	/**
	 * Returns all the commerce application model c product rels.
	 *
	 * @return the commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce application model c product rels
	 */
	public static List<CommerceApplicationModelCProductRel> findAll(
		int start, int end,
		OrderByComparator<CommerceApplicationModelCProductRel>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce application model c product rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce application model c product rels.
	 *
	 * @return the number of commerce application model c product rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceApplicationModelCProductRelPersistence
		getPersistence() {

		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceApplicationModelCProductRelPersistence,
		 CommerceApplicationModelCProductRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceApplicationModelCProductRelPersistence.class);

		ServiceTracker
			<CommerceApplicationModelCProductRelPersistence,
			 CommerceApplicationModelCProductRelPersistence> serviceTracker =
				new ServiceTracker
					<CommerceApplicationModelCProductRelPersistence,
					 CommerceApplicationModelCProductRelPersistence>(
						 bundle.getBundleContext(),
						 CommerceApplicationModelCProductRelPersistence.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}