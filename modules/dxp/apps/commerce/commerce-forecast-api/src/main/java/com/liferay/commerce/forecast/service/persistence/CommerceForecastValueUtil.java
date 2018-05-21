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

package com.liferay.commerce.forecast.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.forecast.model.CommerceForecastValue;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce forecast value service. This utility wraps {@link com.liferay.commerce.forecast.service.persistence.impl.CommerceForecastValuePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastValuePersistence
 * @see com.liferay.commerce.forecast.service.persistence.impl.CommerceForecastValuePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceForecastValueUtil {
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
	public static void clearCache(CommerceForecastValue commerceForecastValue) {
		getPersistence().clearCache(commerceForecastValue);
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
	public static List<CommerceForecastValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceForecastValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceForecastValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceForecastValue update(
		CommerceForecastValue commerceForecastValue) {
		return getPersistence().update(commerceForecastValue);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceForecastValue update(
		CommerceForecastValue commerceForecastValue,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceForecastValue, serviceContext);
	}

	/**
	* Returns all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @return the matching commerce forecast values
	*/
	public static List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId) {
		return getPersistence()
				   .findByCommerceForecastEntryId(commerceForecastEntryId);
	}

	/**
	* Returns a range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @return the range of matching commerce forecast values
	*/
	public static List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end) {
		return getPersistence()
				   .findByCommerceForecastEntryId(commerceForecastEntryId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce forecast values
	*/
	public static List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		return getPersistence()
				   .findByCommerceForecastEntryId(commerceForecastEntryId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce forecast values
	*/
	public static List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceForecastEntryId(commerceForecastEntryId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast value
	* @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	*/
	public static CommerceForecastValue findByCommerceForecastEntryId_First(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastValueException {
		return getPersistence()
				   .findByCommerceForecastEntryId_First(commerceForecastEntryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public static CommerceForecastValue fetchByCommerceForecastEntryId_First(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceForecastEntryId_First(commerceForecastEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast value
	* @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	*/
	public static CommerceForecastValue findByCommerceForecastEntryId_Last(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastValueException {
		return getPersistence()
				   .findByCommerceForecastEntryId_Last(commerceForecastEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public static CommerceForecastValue fetchByCommerceForecastEntryId_Last(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceForecastEntryId_Last(commerceForecastEntryId,
			orderByComparator);
	}

	/**
	* Returns the commerce forecast values before and after the current commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastValueId the primary key of the current commerce forecast value
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce forecast value
	* @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	*/
	public static CommerceForecastValue[] findByCommerceForecastEntryId_PrevAndNext(
		long commerceForecastValueId, long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastValueException {
		return getPersistence()
				   .findByCommerceForecastEntryId_PrevAndNext(commerceForecastValueId,
			commerceForecastEntryId, orderByComparator);
	}

	/**
	* Removes all the commerce forecast values where commerceForecastEntryId = &#63; from the database.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	*/
	public static void removeByCommerceForecastEntryId(
		long commerceForecastEntryId) {
		getPersistence().removeByCommerceForecastEntryId(commerceForecastEntryId);
	}

	/**
	* Returns the number of commerce forecast values where commerceForecastEntryId = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @return the number of matching commerce forecast values
	*/
	public static int countByCommerceForecastEntryId(
		long commerceForecastEntryId) {
		return getPersistence()
				   .countByCommerceForecastEntryId(commerceForecastEntryId);
	}

	/**
	* Returns the commerce forecast value where commerceForecastEntryId = &#63; and time = &#63; or throws a {@link NoSuchForecastValueException} if it could not be found.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param time the time
	* @return the matching commerce forecast value
	* @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	*/
	public static CommerceForecastValue findByC_T(
		long commerceForecastEntryId, long time)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastValueException {
		return getPersistence().findByC_T(commerceForecastEntryId, time);
	}

	/**
	* Returns the commerce forecast value where commerceForecastEntryId = &#63; and time = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param time the time
	* @return the matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public static CommerceForecastValue fetchByC_T(
		long commerceForecastEntryId, long time) {
		return getPersistence().fetchByC_T(commerceForecastEntryId, time);
	}

	/**
	* Returns the commerce forecast value where commerceForecastEntryId = &#63; and time = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param time the time
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	*/
	public static CommerceForecastValue fetchByC_T(
		long commerceForecastEntryId, long time, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_T(commerceForecastEntryId, time, retrieveFromCache);
	}

	/**
	* Removes the commerce forecast value where commerceForecastEntryId = &#63; and time = &#63; from the database.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param time the time
	* @return the commerce forecast value that was removed
	*/
	public static CommerceForecastValue removeByC_T(
		long commerceForecastEntryId, long time)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastValueException {
		return getPersistence().removeByC_T(commerceForecastEntryId, time);
	}

	/**
	* Returns the number of commerce forecast values where commerceForecastEntryId = &#63; and time = &#63;.
	*
	* @param commerceForecastEntryId the commerce forecast entry ID
	* @param time the time
	* @return the number of matching commerce forecast values
	*/
	public static int countByC_T(long commerceForecastEntryId, long time) {
		return getPersistence().countByC_T(commerceForecastEntryId, time);
	}

	/**
	* Caches the commerce forecast value in the entity cache if it is enabled.
	*
	* @param commerceForecastValue the commerce forecast value
	*/
	public static void cacheResult(CommerceForecastValue commerceForecastValue) {
		getPersistence().cacheResult(commerceForecastValue);
	}

	/**
	* Caches the commerce forecast values in the entity cache if it is enabled.
	*
	* @param commerceForecastValues the commerce forecast values
	*/
	public static void cacheResult(
		List<CommerceForecastValue> commerceForecastValues) {
		getPersistence().cacheResult(commerceForecastValues);
	}

	/**
	* Creates a new commerce forecast value with the primary key. Does not add the commerce forecast value to the database.
	*
	* @param commerceForecastValueId the primary key for the new commerce forecast value
	* @return the new commerce forecast value
	*/
	public static CommerceForecastValue create(long commerceForecastValueId) {
		return getPersistence().create(commerceForecastValueId);
	}

	/**
	* Removes the commerce forecast value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value that was removed
	* @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	*/
	public static CommerceForecastValue remove(long commerceForecastValueId)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastValueException {
		return getPersistence().remove(commerceForecastValueId);
	}

	public static CommerceForecastValue updateImpl(
		CommerceForecastValue commerceForecastValue) {
		return getPersistence().updateImpl(commerceForecastValue);
	}

	/**
	* Returns the commerce forecast value with the primary key or throws a {@link NoSuchForecastValueException} if it could not be found.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value
	* @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	*/
	public static CommerceForecastValue findByPrimaryKey(
		long commerceForecastValueId)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastValueException {
		return getPersistence().findByPrimaryKey(commerceForecastValueId);
	}

	/**
	* Returns the commerce forecast value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value, or <code>null</code> if a commerce forecast value with the primary key could not be found
	*/
	public static CommerceForecastValue fetchByPrimaryKey(
		long commerceForecastValueId) {
		return getPersistence().fetchByPrimaryKey(commerceForecastValueId);
	}

	public static java.util.Map<java.io.Serializable, CommerceForecastValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce forecast values.
	*
	* @return the commerce forecast values
	*/
	public static List<CommerceForecastValue> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @return the range of commerce forecast values
	*/
	public static List<CommerceForecastValue> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce forecast values
	*/
	public static List<CommerceForecastValue> findAll(int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce forecast values
	*/
	public static List<CommerceForecastValue> findAll(int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce forecast values from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce forecast values.
	*
	* @return the number of commerce forecast values
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceForecastValuePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceForecastValuePersistence, CommerceForecastValuePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceForecastValuePersistence.class);

		ServiceTracker<CommerceForecastValuePersistence, CommerceForecastValuePersistence> serviceTracker =
			new ServiceTracker<CommerceForecastValuePersistence, CommerceForecastValuePersistence>(bundle.getBundleContext(),
				CommerceForecastValuePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}