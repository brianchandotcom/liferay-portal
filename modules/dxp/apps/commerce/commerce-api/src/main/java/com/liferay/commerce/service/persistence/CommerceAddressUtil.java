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

import com.liferay.commerce.model.CommerceAddress;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce address service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceAddressPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceAddressPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceAddressUtil {
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
	public static void clearCache(CommerceAddress commerceAddress) {
		getPersistence().clearCache(commerceAddress);
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
	public static List<CommerceAddress> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAddress> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAddress> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAddress update(CommerceAddress commerceAddress) {
		return getPersistence().update(commerceAddress);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAddress update(CommerceAddress commerceAddress,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceAddress, serviceContext);
	}

	/**
	* Returns all the commerce addresses where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @return the matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId) {
		return getPersistence().findByCommerceRegionId(commerceRegionId);
	}

	/**
	* Returns a range of all the commerce addresses where commerceRegionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceRegionId the commerce region ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end) {
		return getPersistence()
				   .findByCommerceRegionId(commerceRegionId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceRegionId the commerce region ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .findByCommerceRegionId(commerceRegionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceRegionId the commerce region ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceRegionId(commerceRegionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByCommerceRegionId_First(
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByCommerceRegionId_First(commerceRegionId,
			orderByComparator);
	}

	/**
	* Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByCommerceRegionId_First(
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceRegionId_First(commerceRegionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByCommerceRegionId_Last(
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByCommerceRegionId_Last(commerceRegionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByCommerceRegionId_Last(
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceRegionId_Last(commerceRegionId,
			orderByComparator);
	}

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress[] findByCommerceRegionId_PrevAndNext(
		long commerceAddressId, long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByCommerceRegionId_PrevAndNext(commerceAddressId,
			commerceRegionId, orderByComparator);
	}

	/**
	* Removes all the commerce addresses where commerceRegionId = &#63; from the database.
	*
	* @param commerceRegionId the commerce region ID
	*/
	public static void removeByCommerceRegionId(long commerceRegionId) {
		getPersistence().removeByCommerceRegionId(commerceRegionId);
	}

	/**
	* Returns the number of commerce addresses where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @return the number of matching commerce addresses
	*/
	public static int countByCommerceRegionId(long commerceRegionId) {
		return getPersistence().countByCommerceRegionId(commerceRegionId);
	}

	/**
	* Returns all the commerce addresses where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId) {
		return getPersistence().findByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns a range of all the commerce addresses where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress[] findByCommerceCountryId_PrevAndNext(
		long commerceAddressId, long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByCommerceCountryId_PrevAndNext(commerceAddressId,
			commerceCountryId, orderByComparator);
	}

	/**
	* Removes all the commerce addresses where commerceCountryId = &#63; from the database.
	*
	* @param commerceCountryId the commerce country ID
	*/
	public static void removeByCommerceCountryId(long commerceCountryId) {
		getPersistence().removeByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns the number of commerce addresses where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce addresses
	*/
	public static int countByCommerceCountryId(long commerceCountryId) {
		return getPersistence().countByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce addresses
	*/
	public static List<CommerceAddress> findByC_C(long classNameId, long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByC_C_First(long classNameId,
		long classPK, OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByC_C_Last(long classNameId,
		long classPK, OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress[] findByC_C_PrevAndNext(
		long commerceAddressId, long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByC_C_PrevAndNext(commerceAddressId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the commerce addresses where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce addresses
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK) {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress[] findByG_C_C_PrevAndNext(
		long commerceAddressId, long groupId, long classNameId, long classPK,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(commerceAddressId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByG_C_C(long groupId, long classNameId,
		long classPK) {
		getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce addresses
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @return the matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling) {
		return getPersistence()
				   .findByG_C_C_DB(groupId, classNameId, classPK, defaultBilling);
	}

	/**
	* Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling, int start,
		int end) {
		return getPersistence()
				   .findByG_C_C_DB(groupId, classNameId, classPK,
			defaultBilling, start, end);
	}

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling, int start,
		int end, OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_DB(groupId, classNameId, classPK,
			defaultBilling, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling, int start,
		int end, OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_DB(groupId, classNameId, classPK,
			defaultBilling, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByG_C_C_DB_First(long groupId,
		long classNameId, long classPK, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_DB_First(groupId, classNameId, classPK,
			defaultBilling, orderByComparator);
	}

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByG_C_C_DB_First(long groupId,
		long classNameId, long classPK, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_DB_First(groupId, classNameId, classPK,
			defaultBilling, orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByG_C_C_DB_Last(long groupId,
		long classNameId, long classPK, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_DB_Last(groupId, classNameId, classPK,
			defaultBilling, orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByG_C_C_DB_Last(long groupId,
		long classNameId, long classPK, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_DB_Last(groupId, classNameId, classPK,
			defaultBilling, orderByComparator);
	}

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress[] findByG_C_C_DB_PrevAndNext(
		long commerceAddressId, long groupId, long classNameId, long classPK,
		boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_DB_PrevAndNext(commerceAddressId, groupId,
			classNameId, classPK, defaultBilling, orderByComparator);
	}

	/**
	* Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	*/
	public static void removeByG_C_C_DB(long groupId, long classNameId,
		long classPK, boolean defaultBilling) {
		getPersistence()
			.removeByG_C_C_DB(groupId, classNameId, classPK, defaultBilling);
	}

	/**
	* Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @return the number of matching commerce addresses
	*/
	public static int countByG_C_C_DB(long groupId, long classNameId,
		long classPK, boolean defaultBilling) {
		return getPersistence()
				   .countByG_C_C_DB(groupId, classNameId, classPK,
			defaultBilling);
	}

	/**
	* Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @return the matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping) {
		return getPersistence()
				   .findByG_C_C_DS(groupId, classNameId, classPK,
			defaultShipping);
	}

	/**
	* Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping, int start,
		int end) {
		return getPersistence()
				   .findByG_C_C_DS(groupId, classNameId, classPK,
			defaultShipping, start, end);
	}

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping, int start,
		int end, OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_DS(groupId, classNameId, classPK,
			defaultShipping, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public static List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping, int start,
		int end, OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_DS(groupId, classNameId, classPK,
			defaultShipping, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByG_C_C_DS_First(long groupId,
		long classNameId, long classPK, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_DS_First(groupId, classNameId, classPK,
			defaultShipping, orderByComparator);
	}

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByG_C_C_DS_First(long groupId,
		long classNameId, long classPK, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_DS_First(groupId, classNameId, classPK,
			defaultShipping, orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public static CommerceAddress findByG_C_C_DS_Last(long groupId,
		long classNameId, long classPK, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_DS_Last(groupId, classNameId, classPK,
			defaultShipping, orderByComparator);
	}

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public static CommerceAddress fetchByG_C_C_DS_Last(long groupId,
		long classNameId, long classPK, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_DS_Last(groupId, classNameId, classPK,
			defaultShipping, orderByComparator);
	}

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress[] findByG_C_C_DS_PrevAndNext(
		long commerceAddressId, long groupId, long classNameId, long classPK,
		boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence()
				   .findByG_C_C_DS_PrevAndNext(commerceAddressId, groupId,
			classNameId, classPK, defaultShipping, orderByComparator);
	}

	/**
	* Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	*/
	public static void removeByG_C_C_DS(long groupId, long classNameId,
		long classPK, boolean defaultShipping) {
		getPersistence()
			.removeByG_C_C_DS(groupId, classNameId, classPK, defaultShipping);
	}

	/**
	* Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @return the number of matching commerce addresses
	*/
	public static int countByG_C_C_DS(long groupId, long classNameId,
		long classPK, boolean defaultShipping) {
		return getPersistence()
				   .countByG_C_C_DS(groupId, classNameId, classPK,
			defaultShipping);
	}

	/**
	* Caches the commerce address in the entity cache if it is enabled.
	*
	* @param commerceAddress the commerce address
	*/
	public static void cacheResult(CommerceAddress commerceAddress) {
		getPersistence().cacheResult(commerceAddress);
	}

	/**
	* Caches the commerce addresses in the entity cache if it is enabled.
	*
	* @param commerceAddresses the commerce addresses
	*/
	public static void cacheResult(List<CommerceAddress> commerceAddresses) {
		getPersistence().cacheResult(commerceAddresses);
	}

	/**
	* Creates a new commerce address with the primary key. Does not add the commerce address to the database.
	*
	* @param commerceAddressId the primary key for the new commerce address
	* @return the new commerce address
	*/
	public static CommerceAddress create(long commerceAddressId) {
		return getPersistence().create(commerceAddressId);
	}

	/**
	* Removes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address that was removed
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress remove(long commerceAddressId)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence().remove(commerceAddressId);
	}

	public static CommerceAddress updateImpl(CommerceAddress commerceAddress) {
		return getPersistence().updateImpl(commerceAddress);
	}

	/**
	* Returns the commerce address with the primary key or throws a {@link NoSuchAddressException} if it could not be found.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress findByPrimaryKey(long commerceAddressId)
		throws com.liferay.commerce.exception.NoSuchAddressException {
		return getPersistence().findByPrimaryKey(commerceAddressId);
	}

	/**
	* Returns the commerce address with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address, or <code>null</code> if a commerce address with the primary key could not be found
	*/
	public static CommerceAddress fetchByPrimaryKey(long commerceAddressId) {
		return getPersistence().fetchByPrimaryKey(commerceAddressId);
	}

	public static java.util.Map<java.io.Serializable, CommerceAddress> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce addresses.
	*
	* @return the commerce addresses
	*/
	public static List<CommerceAddress> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of commerce addresses
	*/
	public static List<CommerceAddress> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce addresses
	*/
	public static List<CommerceAddress> findAll(int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce addresses
	*/
	public static List<CommerceAddress> findAll(int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce addresses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce addresses.
	*
	* @return the number of commerce addresses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceAddressPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAddressPersistence, CommerceAddressPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceAddressPersistence.class);
}