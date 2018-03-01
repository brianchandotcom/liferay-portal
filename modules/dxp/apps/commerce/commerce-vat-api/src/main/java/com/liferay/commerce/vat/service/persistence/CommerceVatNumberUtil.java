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

package com.liferay.commerce.vat.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.model.CommerceVatNumber;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce vat number service. This utility wraps {@link com.liferay.commerce.vat.service.persistence.impl.CommerceVatNumberPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberPersistence
 * @see com.liferay.commerce.vat.service.persistence.impl.CommerceVatNumberPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceVatNumberUtil {
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
	public static void clearCache(CommerceVatNumber commerceVatNumber) {
		getPersistence().clearCache(commerceVatNumber);
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
	public static List<CommerceVatNumber> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceVatNumber> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceVatNumber> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceVatNumber update(CommerceVatNumber commerceVatNumber) {
		return getPersistence().update(commerceVatNumber);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceVatNumber update(
		CommerceVatNumber commerceVatNumber, ServiceContext serviceContext) {
		return getPersistence().update(commerceVatNumber, serviceContext);
	}

	/**
	* Returns all the commerce vat numbers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce vat numbers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce vat numbers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce vat numbers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber findByGroupId_First(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber findByGroupId_Last(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce vat numbers before and after the current commerce vat number in the ordered set where groupId = &#63;.
	*
	* @param commerceVatNumberId the primary key of the current commerce vat number
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce vat number
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public static CommerceVatNumber[] findByGroupId_PrevAndNext(
		long commerceVatNumberId, long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceVatNumberId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce vat numbers where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce vat numbers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce vat numbers
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce vat numbers
	*/
	public static List<CommerceVatNumber> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber findByC_C_First(long classNameId,
		long classPK, OrderByComparator<CommerceVatNumber> orderByComparator)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber findByC_C_Last(long classNameId,
		long classPK, OrderByComparator<CommerceVatNumber> orderByComparator)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the commerce vat numbers before and after the current commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceVatNumberId the primary key of the current commerce vat number
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce vat number
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public static CommerceVatNumber[] findByC_C_PrevAndNext(
		long commerceVatNumberId, long classNameId, long classPK,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence()
				   .findByC_C_PrevAndNext(commerceVatNumberId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the commerce vat numbers where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce vat numbers
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchVatNumberException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce vat number
	* @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber findByG_C_C(long groupId, long classNameId,
		long classPK)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber fetchByG_C_C(long groupId,
		long classNameId, long classPK) {
		return getPersistence().fetchByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	*/
	public static CommerceVatNumber fetchByG_C_C(long groupId,
		long classNameId, long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C(groupId, classNameId, classPK,
			retrieveFromCache);
	}

	/**
	* Removes the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the commerce vat number that was removed
	*/
	public static CommerceVatNumber removeByG_C_C(long groupId,
		long classNameId, long classPK)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of commerce vat numbers where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce vat numbers
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Caches the commerce vat number in the entity cache if it is enabled.
	*
	* @param commerceVatNumber the commerce vat number
	*/
	public static void cacheResult(CommerceVatNumber commerceVatNumber) {
		getPersistence().cacheResult(commerceVatNumber);
	}

	/**
	* Caches the commerce vat numbers in the entity cache if it is enabled.
	*
	* @param commerceVatNumbers the commerce vat numbers
	*/
	public static void cacheResult(List<CommerceVatNumber> commerceVatNumbers) {
		getPersistence().cacheResult(commerceVatNumbers);
	}

	/**
	* Creates a new commerce vat number with the primary key. Does not add the commerce vat number to the database.
	*
	* @param commerceVatNumberId the primary key for the new commerce vat number
	* @return the new commerce vat number
	*/
	public static CommerceVatNumber create(long commerceVatNumberId) {
		return getPersistence().create(commerceVatNumberId);
	}

	/**
	* Removes the commerce vat number with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number that was removed
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public static CommerceVatNumber remove(long commerceVatNumberId)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence().remove(commerceVatNumberId);
	}

	public static CommerceVatNumber updateImpl(
		CommerceVatNumber commerceVatNumber) {
		return getPersistence().updateImpl(commerceVatNumber);
	}

	/**
	* Returns the commerce vat number with the primary key or throws a {@link NoSuchVatNumberException} if it could not be found.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number
	* @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	*/
	public static CommerceVatNumber findByPrimaryKey(long commerceVatNumberId)
		throws com.liferay.commerce.vat.exception.NoSuchVatNumberException {
		return getPersistence().findByPrimaryKey(commerceVatNumberId);
	}

	/**
	* Returns the commerce vat number with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number, or <code>null</code> if a commerce vat number with the primary key could not be found
	*/
	public static CommerceVatNumber fetchByPrimaryKey(long commerceVatNumberId) {
		return getPersistence().fetchByPrimaryKey(commerceVatNumberId);
	}

	public static java.util.Map<java.io.Serializable, CommerceVatNumber> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce vat numbers.
	*
	* @return the commerce vat numbers
	*/
	public static List<CommerceVatNumber> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of commerce vat numbers
	*/
	public static List<CommerceVatNumber> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce vat numbers
	*/
	public static List<CommerceVatNumber> findAll(int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce vat numbers
	*/
	public static List<CommerceVatNumber> findAll(int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce vat numbers from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce vat numbers.
	*
	* @return the number of commerce vat numbers
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceVatNumberPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceVatNumberPersistence, CommerceVatNumberPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceVatNumberPersistence.class);
}