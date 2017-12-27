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

import com.liferay.commerce.model.CommerceAddressRestriction;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce address restriction service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceAddressRestrictionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceAddressRestrictionPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionUtil {
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
		CommerceAddressRestriction commerceAddressRestriction) {
		getPersistence().clearCache(commerceAddressRestriction);
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
	public static List<CommerceAddressRestriction> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAddressRestriction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAddressRestriction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAddressRestriction update(
		CommerceAddressRestriction commerceAddressRestriction) {
		return getPersistence().update(commerceAddressRestriction);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAddressRestriction update(
		CommerceAddressRestriction commerceAddressRestriction,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceAddressRestriction, serviceContext);
	}

	/**
	* Returns all the commerce address restrictions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId) {
		return getPersistence().findByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns a range of all the commerce address restrictions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @return the range of matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce address restrictions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce address restrictions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address restriction in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction findByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .findByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce address restriction in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce address restriction in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction findByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .findByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce address restriction in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the commerce address restrictions before and after the current commerce address restriction in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceAddressRestrictionId the primary key of the current commerce address restriction
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address restriction
	* @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	*/
	public static CommerceAddressRestriction[] findByCommerceCountryId_PrevAndNext(
		long commerceAddressRestrictionId, long commerceCountryId,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .findByCommerceCountryId_PrevAndNext(commerceAddressRestrictionId,
			commerceCountryId, orderByComparator);
	}

	/**
	* Removes all the commerce address restrictions where commerceCountryId = &#63; from the database.
	*
	* @param commerceCountryId the commerce country ID
	*/
	public static void removeByCommerceCountryId(long commerceCountryId) {
		getPersistence().removeByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns the number of commerce address restrictions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce address restrictions
	*/
	public static int countByCommerceCountryId(long commerceCountryId) {
		return getPersistence().countByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @return the range of matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction findByC_C_First(long classNameId,
		long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction findByC_C_Last(long classNameId,
		long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction fetchByC_C_Last(long classNameId,
		long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the commerce address restrictions before and after the current commerce address restriction in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceAddressRestrictionId the primary key of the current commerce address restriction
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address restriction
	* @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	*/
	public static CommerceAddressRestriction[] findByC_C_PrevAndNext(
		long commerceAddressRestrictionId, long classNameId, long classPK,
		OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .findByC_C_PrevAndNext(commerceAddressRestrictionId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the commerce address restrictions where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of commerce address restrictions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce address restrictions
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or throws a {@link NoSuchAddressRestrictionException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce address restriction
	* @throws NoSuchAddressRestrictionException if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction findByC_C_C(long classNameId,
		long classPK, long commerceCountryId)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .findByC_C_C(classNameId, classPK, commerceCountryId);
	}

	/**
	* Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction fetchByC_C_C(long classNameId,
		long classPK, long commerceCountryId) {
		return getPersistence()
				   .fetchByC_C_C(classNameId, classPK, commerceCountryId);
	}

	/**
	* Returns the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce address restriction, or <code>null</code> if a matching commerce address restriction could not be found
	*/
	public static CommerceAddressRestriction fetchByC_C_C(long classNameId,
		long classPK, long commerceCountryId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C_C(classNameId, classPK, commerceCountryId,
			retrieveFromCache);
	}

	/**
	* Removes the commerce address restriction where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the commerce address restriction that was removed
	*/
	public static CommerceAddressRestriction removeByC_C_C(long classNameId,
		long classPK, long commerceCountryId)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence()
				   .removeByC_C_C(classNameId, classPK, commerceCountryId);
	}

	/**
	* Returns the number of commerce address restrictions where classNameId = &#63; and classPK = &#63; and commerceCountryId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce address restrictions
	*/
	public static int countByC_C_C(long classNameId, long classPK,
		long commerceCountryId) {
		return getPersistence()
				   .countByC_C_C(classNameId, classPK, commerceCountryId);
	}

	/**
	* Caches the commerce address restriction in the entity cache if it is enabled.
	*
	* @param commerceAddressRestriction the commerce address restriction
	*/
	public static void cacheResult(
		CommerceAddressRestriction commerceAddressRestriction) {
		getPersistence().cacheResult(commerceAddressRestriction);
	}

	/**
	* Caches the commerce address restrictions in the entity cache if it is enabled.
	*
	* @param commerceAddressRestrictions the commerce address restrictions
	*/
	public static void cacheResult(
		List<CommerceAddressRestriction> commerceAddressRestrictions) {
		getPersistence().cacheResult(commerceAddressRestrictions);
	}

	/**
	* Creates a new commerce address restriction with the primary key. Does not add the commerce address restriction to the database.
	*
	* @param commerceAddressRestrictionId the primary key for the new commerce address restriction
	* @return the new commerce address restriction
	*/
	public static CommerceAddressRestriction create(
		long commerceAddressRestrictionId) {
		return getPersistence().create(commerceAddressRestrictionId);
	}

	/**
	* Removes the commerce address restriction with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction that was removed
	* @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	*/
	public static CommerceAddressRestriction remove(
		long commerceAddressRestrictionId)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence().remove(commerceAddressRestrictionId);
	}

	public static CommerceAddressRestriction updateImpl(
		CommerceAddressRestriction commerceAddressRestriction) {
		return getPersistence().updateImpl(commerceAddressRestriction);
	}

	/**
	* Returns the commerce address restriction with the primary key or throws a {@link NoSuchAddressRestrictionException} if it could not be found.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction
	* @throws NoSuchAddressRestrictionException if a commerce address restriction with the primary key could not be found
	*/
	public static CommerceAddressRestriction findByPrimaryKey(
		long commerceAddressRestrictionId)
		throws com.liferay.commerce.exception.NoSuchAddressRestrictionException {
		return getPersistence().findByPrimaryKey(commerceAddressRestrictionId);
	}

	/**
	* Returns the commerce address restriction with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceAddressRestrictionId the primary key of the commerce address restriction
	* @return the commerce address restriction, or <code>null</code> if a commerce address restriction with the primary key could not be found
	*/
	public static CommerceAddressRestriction fetchByPrimaryKey(
		long commerceAddressRestrictionId) {
		return getPersistence().fetchByPrimaryKey(commerceAddressRestrictionId);
	}

	public static java.util.Map<java.io.Serializable, CommerceAddressRestriction> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce address restrictions.
	*
	* @return the commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce address restrictions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @return the range of commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce address restrictions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findAll(int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce address restrictions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressRestrictionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce address restrictions
	* @param end the upper bound of the range of commerce address restrictions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce address restrictions
	*/
	public static List<CommerceAddressRestriction> findAll(int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce address restrictions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce address restrictions.
	*
	* @return the number of commerce address restrictions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceAddressRestrictionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAddressRestrictionPersistence, CommerceAddressRestrictionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceAddressRestrictionPersistence.class);
}