/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp specification option list type definition rel service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPSpecificationOptionListTypeDefinitionRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPSpecificationOptionListTypeDefinitionRelPersistence
 * @generated
 */
public class CPSpecificationOptionListTypeDefinitionRelUtil {

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
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		getPersistence().clearCache(cpSpecificationOptionListTypeDefinitionRel);
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
	public static Map<Serializable, CPSpecificationOptionListTypeDefinitionRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPSpecificationOptionListTypeDefinitionRel update(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		return getPersistence().update(
			cpSpecificationOptionListTypeDefinitionRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPSpecificationOptionListTypeDefinitionRel update(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			cpSpecificationOptionListTypeDefinitionRel, serviceContext);
	}

	/**
	 * Returns all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(long CPSpecificationOptionId) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId);
	}

	/**
	 * Returns a range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
			findByCPSpecificationOptionId_First(
				long CPSpecificationOptionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByCPSpecificationOptionId_First(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
		fetchByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByCPSpecificationOptionId_First(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
			findByCPSpecificationOptionId_Last(
				long CPSpecificationOptionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByCPSpecificationOptionId_Last(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
		fetchByCPSpecificationOptionId_Last(
			long CPSpecificationOptionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByCPSpecificationOptionId_Last(
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Returns the cp specification option list type definition rels before and after the current cp specification option list type definition rel in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the current cp specification option list type definition rel
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel[]
			findByCPSpecificationOptionId_PrevAndNext(
				long CPSpecificationOptionListTypeDefinitionRelId,
				long CPSpecificationOptionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByCPSpecificationOptionId_PrevAndNext(
			CPSpecificationOptionListTypeDefinitionRelId,
			CPSpecificationOptionId, orderByComparator);
	}

	/**
	 * Removes all the cp specification option list type definition rels where CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	public static void removeByCPSpecificationOptionId(
		long CPSpecificationOptionId) {

		getPersistence().removeByCPSpecificationOptionId(
			CPSpecificationOptionId);
	}

	/**
	 * Returns the number of cp specification option list type definition rels where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	public static int countByCPSpecificationOptionId(
		long CPSpecificationOptionId) {

		return getPersistence().countByCPSpecificationOptionId(
			CPSpecificationOptionId);
	}

	/**
	 * Returns all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(long listTypeDefinitionId) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	/**
	 * Returns a range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel>
		findByListTypeDefinitionId(
			long listTypeDefinitionId, int start, int end,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
			findByListTypeDefinitionId_First(
				long listTypeDefinitionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByListTypeDefinitionId_First(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
		fetchByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByListTypeDefinitionId_First(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
			findByListTypeDefinitionId_Last(
				long listTypeDefinitionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByListTypeDefinitionId_Last(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel
		fetchByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
				orderByComparator) {

		return getPersistence().fetchByListTypeDefinitionId_Last(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the cp specification option list type definition rels before and after the current cp specification option list type definition rel in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the current cp specification option list type definition rel
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel[]
			findByListTypeDefinitionId_PrevAndNext(
				long CPSpecificationOptionListTypeDefinitionRelId,
				long listTypeDefinitionId,
				OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
					orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByListTypeDefinitionId_PrevAndNext(
			CPSpecificationOptionListTypeDefinitionRelId, listTypeDefinitionId,
			orderByComparator);
	}

	/**
	 * Removes all the cp specification option list type definition rels where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	public static void removeByListTypeDefinitionId(long listTypeDefinitionId) {
		getPersistence().removeByListTypeDefinitionId(listTypeDefinitionId);
	}

	/**
	 * Returns the number of cp specification option list type definition rels where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	public static int countByListTypeDefinitionId(long listTypeDefinitionId) {
		return getPersistence().countByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or throws a <code>NoSuchCPSpecificationOptionListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel findByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId) {

		return getPersistence().fetchByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp specification option list type definition rel, or <code>null</code> if a matching cp specification option list type definition rel could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel fetchByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_L(
			CPSpecificationOptionId, listTypeDefinitionId, useFinderCache);
	}

	/**
	 * Removes the cp specification option list type definition rel where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the cp specification option list type definition rel that was removed
	 */
	public static CPSpecificationOptionListTypeDefinitionRel removeByC_L(
			long CPSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().removeByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the number of cp specification option list type definition rels where CPSpecificationOptionId = &#63; and listTypeDefinitionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching cp specification option list type definition rels
	 */
	public static int countByC_L(
		long CPSpecificationOptionId, long listTypeDefinitionId) {

		return getPersistence().countByC_L(
			CPSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Caches the cp specification option list type definition rel in the entity cache if it is enabled.
	 *
	 * @param cpSpecificationOptionListTypeDefinitionRel the cp specification option list type definition rel
	 */
	public static void cacheResult(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		getPersistence().cacheResult(
			cpSpecificationOptionListTypeDefinitionRel);
	}

	/**
	 * Caches the cp specification option list type definition rels in the entity cache if it is enabled.
	 *
	 * @param cpSpecificationOptionListTypeDefinitionRels the cp specification option list type definition rels
	 */
	public static void cacheResult(
		List<CPSpecificationOptionListTypeDefinitionRel>
			cpSpecificationOptionListTypeDefinitionRels) {

		getPersistence().cacheResult(
			cpSpecificationOptionListTypeDefinitionRels);
	}

	/**
	 * Creates a new cp specification option list type definition rel with the primary key. Does not add the cp specification option list type definition rel to the database.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key for the new cp specification option list type definition rel
	 * @return the new cp specification option list type definition rel
	 */
	public static CPSpecificationOptionListTypeDefinitionRel create(
		long CPSpecificationOptionListTypeDefinitionRelId) {

		return getPersistence().create(
			CPSpecificationOptionListTypeDefinitionRelId);
	}

	/**
	 * Removes the cp specification option list type definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel that was removed
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel remove(
			long CPSpecificationOptionListTypeDefinitionRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().remove(
			CPSpecificationOptionListTypeDefinitionRelId);
	}

	public static CPSpecificationOptionListTypeDefinitionRel updateImpl(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		return getPersistence().updateImpl(
			cpSpecificationOptionListTypeDefinitionRel);
	}

	/**
	 * Returns the cp specification option list type definition rel with the primary key or throws a <code>NoSuchCPSpecificationOptionListTypeDefinitionRelException</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel
	 * @throws NoSuchCPSpecificationOptionListTypeDefinitionRelException if a cp specification option list type definition rel with the primary key could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel findByPrimaryKey(
			long CPSpecificationOptionListTypeDefinitionRelId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPSpecificationOptionListTypeDefinitionRelException {

		return getPersistence().findByPrimaryKey(
			CPSpecificationOptionListTypeDefinitionRelId);
	}

	/**
	 * Returns the cp specification option list type definition rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the primary key of the cp specification option list type definition rel
	 * @return the cp specification option list type definition rel, or <code>null</code> if a cp specification option list type definition rel with the primary key could not be found
	 */
	public static CPSpecificationOptionListTypeDefinitionRel fetchByPrimaryKey(
		long CPSpecificationOptionListTypeDefinitionRelId) {

		return getPersistence().fetchByPrimaryKey(
			CPSpecificationOptionListTypeDefinitionRelId);
	}

	/**
	 * Returns all the cp specification option list type definition rels.
	 *
	 * @return the cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @return the range of cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp specification option list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPSpecificationOptionListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp specification option list type definition rels
	 * @param end the upper bound of the range of cp specification option list type definition rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp specification option list type definition rels
	 */
	public static List<CPSpecificationOptionListTypeDefinitionRel> findAll(
		int start, int end,
		OrderByComparator<CPSpecificationOptionListTypeDefinitionRel>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp specification option list type definition rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp specification option list type definition rels.
	 *
	 * @return the number of cp specification option list type definition rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPSpecificationOptionListTypeDefinitionRelPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		CPSpecificationOptionListTypeDefinitionRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile
		CPSpecificationOptionListTypeDefinitionRelPersistence _persistence;

}