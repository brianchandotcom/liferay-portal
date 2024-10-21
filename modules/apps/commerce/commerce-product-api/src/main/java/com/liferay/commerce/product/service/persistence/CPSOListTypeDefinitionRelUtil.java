/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cpso list type definition rel service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPSOListTypeDefinitionRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPSOListTypeDefinitionRelPersistence
 * @generated
 */
public class CPSOListTypeDefinitionRelUtil {

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
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		getPersistence().clearCache(cpsoListTypeDefinitionRel);
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
	public static Map<Serializable, CPSOListTypeDefinitionRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPSOListTypeDefinitionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPSOListTypeDefinitionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPSOListTypeDefinitionRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPSOListTypeDefinitionRel update(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		return getPersistence().update(cpsoListTypeDefinitionRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPSOListTypeDefinitionRel update(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			cpsoListTypeDefinitionRel, serviceContext);
	}

	/**
	 * Returns all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByCPSpecificationOptionId(
		long CPSpecificationOptionId) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId);
	}

	/**
	 * Returns a range of all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @return the range of matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByCPSpecificationOptionId(
		long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel findByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByCPSpecificationOptionId_First(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the first cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel
		fetchByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().fetchByCPSpecificationOptionId_First(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the last cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel findByCPSpecificationOptionId_Last(
			long CPSpecificationOptionId,
			OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByCPSpecificationOptionId_Last(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the last cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel fetchByCPSpecificationOptionId_Last(
		long CPSpecificationOptionId,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().fetchByCPSpecificationOptionId_Last(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the cpso list type definition rels before and after the current cpso list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the current cpso list type definition rel
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public static CPSOListTypeDefinitionRel[]
			findByCPSpecificationOptionId_PrevAndNext(
				long CPSOListTypeDefinitionRelId, long CPSpecificationOptionId,
				OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByCPSpecificationOptionId_PrevAndNext(
			CPSOListTypeDefinitionRelId, CPSpecificationOptionId,
			orderByComparator);
	}

	/**
	 * Removes all the cpso list type definition rels where CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	public static void removeByCPSpecificationOptionId(
		long CPSpecificationOptionId) {

		getPersistence().removeByCPSpecificationOptionId(
			CPSpecificationOptionId);
	}

	/**
	 * Returns the number of cpso list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cpso list type definition rels
	 */
	public static int countByCPSpecificationOptionId(
		long CPSpecificationOptionId) {

		return getPersistence().countByCPSpecificationOptionId(
			CPSpecificationOptionId);
	}

	/**
	 * Returns all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	/**
	 * Returns a range of all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @return the range of matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel findByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByListTypeDefinitionId_First(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel fetchByListTypeDefinitionId_First(
		long listTypeDefinitionId,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().fetchByListTypeDefinitionId_First(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel findByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByListTypeDefinitionId_Last(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel fetchByListTypeDefinitionId_Last(
		long listTypeDefinitionId,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().fetchByListTypeDefinitionId_Last(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the cpso list type definition rels before and after the current cpso list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the current cpso list type definition rel
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public static CPSOListTypeDefinitionRel[]
			findByListTypeDefinitionId_PrevAndNext(
				long CPSOListTypeDefinitionRelId, long listTypeDefinitionId,
				OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByListTypeDefinitionId_PrevAndNext(
			CPSOListTypeDefinitionRelId, listTypeDefinitionId,
			orderByComparator);
	}

	/**
	 * Removes all the cpso list type definition rels where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	public static void removeByListTypeDefinitionId(long listTypeDefinitionId) {
		getPersistence().removeByListTypeDefinitionId(listTypeDefinitionId);
	}

	/**
	 * Returns the number of cpso list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cpso list type definition rels
	 */
	public static int countByListTypeDefinitionId(long listTypeDefinitionId) {
		return getPersistence().countByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	/**
	 * Returns the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or throws a <code>NoSuchCPSOListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel findByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId) {

		return getPersistence().fetchByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cpso list type definition rel, or <code>null</code> if a matching cpso list type definition rel could not be found
	 */
	public static CPSOListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_L(
			CPSpecificationOptionId, listTypeDefinitionId, useFinderCache);
	}

	/**
	 * Removes the cpso list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the cpso list type definition rel that was removed
	 */
	public static CPSOListTypeDefinitionRel removeByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().removeByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the number of cpso list type definition rels where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cpso list type definition rels
	 */
	public static int countByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId) {

		return getPersistence().countByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Caches the cpso list type definition rel in the entity cache if it is enabled.
	 *
	 * @param cpsoListTypeDefinitionRel the cpso list type definition rel
	 */
	public static void cacheResult(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		getPersistence().cacheResult(cpsoListTypeDefinitionRel);
	}

	/**
	 * Caches the cpso list type definition rels in the entity cache if it is enabled.
	 *
	 * @param cpsoListTypeDefinitionRels the cpso list type definition rels
	 */
	public static void cacheResult(
		List<CPSOListTypeDefinitionRel> cpsoListTypeDefinitionRels) {

		getPersistence().cacheResult(cpsoListTypeDefinitionRels);
	}

	/**
	 * Creates a new cpso list type definition rel with the primary key. Does not add the cpso list type definition rel to the database.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key for the new cpso list type definition rel
	 * @return the new cpso list type definition rel
	 */
	public static CPSOListTypeDefinitionRel create(
		long CPSOListTypeDefinitionRelId) {

		return getPersistence().create(CPSOListTypeDefinitionRelId);
	}

	/**
	 * Removes the cpso list type definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel that was removed
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public static CPSOListTypeDefinitionRel remove(
			long CPSOListTypeDefinitionRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().remove(CPSOListTypeDefinitionRelId);
	}

	public static CPSOListTypeDefinitionRel updateImpl(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		return getPersistence().updateImpl(cpsoListTypeDefinitionRel);
	}

	/**
	 * Returns the cpso list type definition rel with the primary key or throws a <code>NoSuchCPSOListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel
	 * @throws NoSuchCPSOListTypeDefinitionRelException if a cpso list type definition rel with the primary key could not be found
	 */
	public static CPSOListTypeDefinitionRel findByPrimaryKey(
			long CPSOListTypeDefinitionRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSOListTypeDefinitionRelException {

		return getPersistence().findByPrimaryKey(CPSOListTypeDefinitionRelId);
	}

	/**
	 * Returns the cpso list type definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel, or <code>null</code> if a cpso list type definition rel with the primary key could not be found
	 */
	public static CPSOListTypeDefinitionRel fetchByPrimaryKey(
		long CPSOListTypeDefinitionRelId) {

		return getPersistence().fetchByPrimaryKey(CPSOListTypeDefinitionRelId);
	}

	/**
	 * Returns all the cpso list type definition rels.
	 *
	 * @return the cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cpso list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @return the range of cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cpso list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cpso list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cpso list type definition rels
	 */
	public static List<CPSOListTypeDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CPSOListTypeDefinitionRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cpso list type definition rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cpso list type definition rels.
	 *
	 * @return the number of cpso list type definition rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPSOListTypeDefinitionRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CPSOListTypeDefinitionRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CPSOListTypeDefinitionRelPersistence _persistence;

}