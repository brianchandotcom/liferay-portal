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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceTaxCategoryRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce tax category rel service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceTaxCategoryRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceTaxCategoryRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelUtil {
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
	public static void clearCache(CommerceTaxCategoryRel commerceTaxCategoryRel) {
		getPersistence().clearCache(commerceTaxCategoryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceTaxCategoryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTaxCategoryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTaxCategoryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTaxCategoryRel update(
		CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return getPersistence().update(commerceTaxCategoryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTaxCategoryRel update(
		CommerceTaxCategoryRel commerceTaxCategoryRel,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceTaxCategoryRel, serviceContext);
	}

	/**
	* Returns all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @return the matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId) {
		return getPersistence()
				   .findByCommerceTaxCategoryId(commerceTaxCategoryId);
	}

	/**
	* Returns a range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end) {
		return getPersistence()
				   .findByCommerceTaxCategoryId(commerceTaxCategoryId, start,
			end);
	}

	/**
	* Returns an ordered range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceTaxCategoryId(commerceTaxCategoryId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceTaxCategoryId(commerceTaxCategoryId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel findByCommerceTaxCategoryId_First(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .findByCommerceTaxCategoryId_First(commerceTaxCategoryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel fetchByCommerceTaxCategoryId_First(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceTaxCategoryId_First(commerceTaxCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel findByCommerceTaxCategoryId_Last(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .findByCommerceTaxCategoryId_Last(commerceTaxCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel fetchByCommerceTaxCategoryId_Last(
		long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceTaxCategoryId_Last(commerceTaxCategoryId,
			orderByComparator);
	}

	/**
	* Returns the commerce tax category rels before and after the current commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryRelId the primary key of the current commerce tax category rel
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public static CommerceTaxCategoryRel[] findByCommerceTaxCategoryId_PrevAndNext(
		long commerceTaxCategoryRelId, long commerceTaxCategoryId,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .findByCommerceTaxCategoryId_PrevAndNext(commerceTaxCategoryRelId,
			commerceTaxCategoryId, orderByComparator);
	}

	/**
	* Removes all the commerce tax category rels where commerceTaxCategoryId = &#63; from the database.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	*/
	public static void removeByCommerceTaxCategoryId(long commerceTaxCategoryId) {
		getPersistence().removeByCommerceTaxCategoryId(commerceTaxCategoryId);
	}

	/**
	* Returns the number of commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @return the number of matching commerce tax category rels
	*/
	public static int countByCommerceTaxCategoryId(long commerceTaxCategoryId) {
		return getPersistence()
				   .countByCommerceTaxCategoryId(commerceTaxCategoryId);
	}

	/**
	* Returns all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel findByC_C_First(long classNameId,
		long classPK,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel fetchByC_C_First(long classNameId,
		long classPK,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel findByC_C_Last(long classNameId,
		long classPK,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel fetchByC_C_Last(long classNameId,
		long classPK,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the commerce tax category rels before and after the current commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceTaxCategoryRelId the primary key of the current commerce tax category rel
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public static CommerceTaxCategoryRel[] findByC_C_PrevAndNext(
		long commerceTaxCategoryRelId, long classNameId, long classPK,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .findByC_C_PrevAndNext(commerceTaxCategoryRelId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the commerce tax category rels where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce tax category rels
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchTaxCategoryRelException} if it could not be found.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel findByC_C_C(
		long commerceTaxCategoryId, long classNameId, long classPK)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .findByC_C_C(commerceTaxCategoryId, classNameId, classPK);
	}

	/**
	* Returns the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel fetchByC_C_C(
		long commerceTaxCategoryId, long classNameId, long classPK) {
		return getPersistence()
				   .fetchByC_C_C(commerceTaxCategoryId, classNameId, classPK);
	}

	/**
	* Returns the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public static CommerceTaxCategoryRel fetchByC_C_C(
		long commerceTaxCategoryId, long classNameId, long classPK,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C_C(commerceTaxCategoryId, classNameId, classPK,
			retrieveFromCache);
	}

	/**
	* Removes the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the commerce tax category rel that was removed
	*/
	public static CommerceTaxCategoryRel removeByC_C_C(
		long commerceTaxCategoryId, long classNameId, long classPK)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence()
				   .removeByC_C_C(commerceTaxCategoryId, classNameId, classPK);
	}

	/**
	* Returns the number of commerce tax category rels where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce tax category rels
	*/
	public static int countByC_C_C(long commerceTaxCategoryId,
		long classNameId, long classPK) {
		return getPersistence()
				   .countByC_C_C(commerceTaxCategoryId, classNameId, classPK);
	}

	/**
	* Caches the commerce tax category rel in the entity cache if it is enabled.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	*/
	public static void cacheResult(
		CommerceTaxCategoryRel commerceTaxCategoryRel) {
		getPersistence().cacheResult(commerceTaxCategoryRel);
	}

	/**
	* Caches the commerce tax category rels in the entity cache if it is enabled.
	*
	* @param commerceTaxCategoryRels the commerce tax category rels
	*/
	public static void cacheResult(
		List<CommerceTaxCategoryRel> commerceTaxCategoryRels) {
		getPersistence().cacheResult(commerceTaxCategoryRels);
	}

	/**
	* Creates a new commerce tax category rel with the primary key. Does not add the commerce tax category rel to the database.
	*
	* @param commerceTaxCategoryRelId the primary key for the new commerce tax category rel
	* @return the new commerce tax category rel
	*/
	public static CommerceTaxCategoryRel create(long commerceTaxCategoryRelId) {
		return getPersistence().create(commerceTaxCategoryRelId);
	}

	/**
	* Removes the commerce tax category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel that was removed
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public static CommerceTaxCategoryRel remove(long commerceTaxCategoryRelId)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence().remove(commerceTaxCategoryRelId);
	}

	public static CommerceTaxCategoryRel updateImpl(
		CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return getPersistence().updateImpl(commerceTaxCategoryRel);
	}

	/**
	* Returns the commerce tax category rel with the primary key or throws a {@link NoSuchTaxCategoryRelException} if it could not be found.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public static CommerceTaxCategoryRel findByPrimaryKey(
		long commerceTaxCategoryRelId)
		throws com.liferay.commerce.exception.NoSuchTaxCategoryRelException {
		return getPersistence().findByPrimaryKey(commerceTaxCategoryRelId);
	}

	/**
	* Returns the commerce tax category rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel, or <code>null</code> if a commerce tax category rel with the primary key could not be found
	*/
	public static CommerceTaxCategoryRel fetchByPrimaryKey(
		long commerceTaxCategoryRelId) {
		return getPersistence().fetchByPrimaryKey(commerceTaxCategoryRelId);
	}

	public static java.util.Map<java.io.Serializable, CommerceTaxCategoryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce tax category rels.
	*
	* @return the commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tax category rels
	*/
	public static List<CommerceTaxCategoryRel> findAll(int start, int end,
		OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce tax category rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce tax category rels.
	*
	* @return the number of commerce tax category rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceTaxCategoryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxCategoryRelPersistence, CommerceTaxCategoryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxCategoryRelPersistence.class);

		ServiceTracker<CommerceTaxCategoryRelPersistence, CommerceTaxCategoryRelPersistence> serviceTracker =
			new ServiceTracker<CommerceTaxCategoryRelPersistence, CommerceTaxCategoryRelPersistence>(bundle.getBundleContext(),
				CommerceTaxCategoryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}