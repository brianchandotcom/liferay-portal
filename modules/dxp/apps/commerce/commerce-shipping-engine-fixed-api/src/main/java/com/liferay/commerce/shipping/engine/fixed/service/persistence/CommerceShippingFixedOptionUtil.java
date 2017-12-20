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

package com.liferay.commerce.shipping.engine.fixed.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce shipping fixed option service. This utility wraps {@link com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CommerceShippingFixedOptionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionPersistence
 * @see com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.CommerceShippingFixedOptionPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionUtil {
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
		CommerceShippingFixedOption commerceShippingFixedOption) {
		getPersistence().clearCache(commerceShippingFixedOption);
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
	public static List<CommerceShippingFixedOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceShippingFixedOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceShippingFixedOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceShippingFixedOption update(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		return getPersistence().update(commerceShippingFixedOption);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceShippingFixedOption update(
		CommerceShippingFixedOption commerceShippingFixedOption,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceShippingFixedOption, serviceContext);
	}

	/**
	* Returns all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the matching commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Returns a range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of commerce shipping fixed options
	* @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	* @return the range of matching commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of commerce shipping fixed options
	* @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce shipping fixed options where commerceShippingMethodId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param start the lower bound of the range of commerce shipping fixed options
	* @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findByCommerceShippingMethodId(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceShippingMethodId(commerceShippingMethodId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option
	* @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	*/
	public static CommerceShippingFixedOption findByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException {
		return getPersistence()
				   .findByCommerceShippingMethodId_First(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the first commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	*/
	public static CommerceShippingFixedOption fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingMethodId_First(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option
	* @throws NoSuchShippingFixedOptionException if a matching commerce shipping fixed option could not be found
	*/
	public static CommerceShippingFixedOption findByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException {
		return getPersistence()
				   .findByCommerceShippingMethodId_Last(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipping fixed option, or <code>null</code> if a matching commerce shipping fixed option could not be found
	*/
	public static CommerceShippingFixedOption fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceShippingMethodId_Last(commerceShippingMethodId,
			orderByComparator);
	}

	/**
	* Returns the commerce shipping fixed options before and after the current commerce shipping fixed option in the ordered set where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingFixedOptionId the primary key of the current commerce shipping fixed option
	* @param commerceShippingMethodId the commerce shipping method ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipping fixed option
	* @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	*/
	public static CommerceShippingFixedOption[] findByCommerceShippingMethodId_PrevAndNext(
		long commerceShippingFixedOptionId, long commerceShippingMethodId,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException {
		return getPersistence()
				   .findByCommerceShippingMethodId_PrevAndNext(commerceShippingFixedOptionId,
			commerceShippingMethodId, orderByComparator);
	}

	/**
	* Removes all the commerce shipping fixed options where commerceShippingMethodId = &#63; from the database.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	*/
	public static void removeByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		getPersistence()
			.removeByCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Returns the number of commerce shipping fixed options where commerceShippingMethodId = &#63;.
	*
	* @param commerceShippingMethodId the commerce shipping method ID
	* @return the number of matching commerce shipping fixed options
	*/
	public static int countByCommerceShippingMethodId(
		long commerceShippingMethodId) {
		return getPersistence()
				   .countByCommerceShippingMethodId(commerceShippingMethodId);
	}

	/**
	* Caches the commerce shipping fixed option in the entity cache if it is enabled.
	*
	* @param commerceShippingFixedOption the commerce shipping fixed option
	*/
	public static void cacheResult(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		getPersistence().cacheResult(commerceShippingFixedOption);
	}

	/**
	* Caches the commerce shipping fixed options in the entity cache if it is enabled.
	*
	* @param commerceShippingFixedOptions the commerce shipping fixed options
	*/
	public static void cacheResult(
		List<CommerceShippingFixedOption> commerceShippingFixedOptions) {
		getPersistence().cacheResult(commerceShippingFixedOptions);
	}

	/**
	* Creates a new commerce shipping fixed option with the primary key. Does not add the commerce shipping fixed option to the database.
	*
	* @param commerceShippingFixedOptionId the primary key for the new commerce shipping fixed option
	* @return the new commerce shipping fixed option
	*/
	public static CommerceShippingFixedOption create(
		long commerceShippingFixedOptionId) {
		return getPersistence().create(commerceShippingFixedOptionId);
	}

	/**
	* Removes the commerce shipping fixed option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	* @return the commerce shipping fixed option that was removed
	* @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	*/
	public static CommerceShippingFixedOption remove(
		long commerceShippingFixedOptionId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException {
		return getPersistence().remove(commerceShippingFixedOptionId);
	}

	public static CommerceShippingFixedOption updateImpl(
		CommerceShippingFixedOption commerceShippingFixedOption) {
		return getPersistence().updateImpl(commerceShippingFixedOption);
	}

	/**
	* Returns the commerce shipping fixed option with the primary key or throws a {@link NoSuchShippingFixedOptionException} if it could not be found.
	*
	* @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	* @return the commerce shipping fixed option
	* @throws NoSuchShippingFixedOptionException if a commerce shipping fixed option with the primary key could not be found
	*/
	public static CommerceShippingFixedOption findByPrimaryKey(
		long commerceShippingFixedOptionId)
		throws com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException {
		return getPersistence().findByPrimaryKey(commerceShippingFixedOptionId);
	}

	/**
	* Returns the commerce shipping fixed option with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	* @return the commerce shipping fixed option, or <code>null</code> if a commerce shipping fixed option with the primary key could not be found
	*/
	public static CommerceShippingFixedOption fetchByPrimaryKey(
		long commerceShippingFixedOptionId) {
		return getPersistence().fetchByPrimaryKey(commerceShippingFixedOptionId);
	}

	public static java.util.Map<java.io.Serializable, CommerceShippingFixedOption> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce shipping fixed options.
	*
	* @return the commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce shipping fixed options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed options
	* @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	* @return the range of commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce shipping fixed options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed options
	* @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findAll(int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce shipping fixed options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed options
	* @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce shipping fixed options
	*/
	public static List<CommerceShippingFixedOption> findAll(int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce shipping fixed options from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce shipping fixed options.
	*
	* @return the number of commerce shipping fixed options
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceShippingFixedOptionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShippingFixedOptionPersistence, CommerceShippingFixedOptionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceShippingFixedOptionPersistence.class);
}