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

import com.liferay.commerce.forecast.model.CommerceForecastEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce forecast entry service. This utility wraps {@link com.liferay.commerce.forecast.service.persistence.impl.CommerceForecastEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntryPersistence
 * @see com.liferay.commerce.forecast.service.persistence.impl.CommerceForecastEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceForecastEntryUtil {
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
	public static void clearCache(CommerceForecastEntry commerceForecastEntry) {
		getPersistence().clearCache(commerceForecastEntry);
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
	public static List<CommerceForecastEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceForecastEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceForecastEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceForecastEntry update(
		CommerceForecastEntry commerceForecastEntry) {
		return getPersistence().update(commerceForecastEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceForecastEntry update(
		CommerceForecastEntry commerceForecastEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceForecastEntry, serviceContext);
	}

	/**
	* Returns all the commerce forecast entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce forecast entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @return the range of matching commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce forecast entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce forecast entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast entry
	* @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	*/
	public static CommerceForecastEntry findByCompanyId_First(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public static CommerceForecastEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast entry
	* @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	*/
	public static CommerceForecastEntry findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public static CommerceForecastEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce forecast entries before and after the current commerce forecast entry in the ordered set where companyId = &#63;.
	*
	* @param commerceForecastEntryId the primary key of the current commerce forecast entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce forecast entry
	* @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	*/
	public static CommerceForecastEntry[] findByCompanyId_PrevAndNext(
		long commerceForecastEntryId, long companyId,
		OrderByComparator<CommerceForecastEntry> orderByComparator)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceForecastEntryId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce forecast entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce forecast entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce forecast entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or throws a {@link NoSuchForecastEntryException} if it could not be found.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the matching commerce forecast entry
	* @throws NoSuchForecastEntryException if a matching commerce forecast entry could not be found
	*/
	public static CommerceForecastEntry findByC_P_T_C_S(long companyId,
		int period, int target, long customerId, String sku)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastEntryException {
		return getPersistence()
				   .findByC_P_T_C_S(companyId, period, target, customerId, sku);
	}

	/**
	* Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public static CommerceForecastEntry fetchByC_P_T_C_S(long companyId,
		int period, int target, long customerId, String sku) {
		return getPersistence()
				   .fetchByC_P_T_C_S(companyId, period, target, customerId, sku);
	}

	/**
	* Returns the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce forecast entry, or <code>null</code> if a matching commerce forecast entry could not be found
	*/
	public static CommerceForecastEntry fetchByC_P_T_C_S(long companyId,
		int period, int target, long customerId, String sku,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_P_T_C_S(companyId, period, target, customerId,
			sku, retrieveFromCache);
	}

	/**
	* Removes the commerce forecast entry where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63; from the database.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the commerce forecast entry that was removed
	*/
	public static CommerceForecastEntry removeByC_P_T_C_S(long companyId,
		int period, int target, long customerId, String sku)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastEntryException {
		return getPersistence()
				   .removeByC_P_T_C_S(companyId, period, target, customerId, sku);
	}

	/**
	* Returns the number of commerce forecast entries where companyId = &#63; and period = &#63; and target = &#63; and customerId = &#63; and sku = &#63;.
	*
	* @param companyId the company ID
	* @param period the period
	* @param target the target
	* @param customerId the customer ID
	* @param sku the sku
	* @return the number of matching commerce forecast entries
	*/
	public static int countByC_P_T_C_S(long companyId, int period, int target,
		long customerId, String sku) {
		return getPersistence()
				   .countByC_P_T_C_S(companyId, period, target, customerId, sku);
	}

	/**
	* Caches the commerce forecast entry in the entity cache if it is enabled.
	*
	* @param commerceForecastEntry the commerce forecast entry
	*/
	public static void cacheResult(CommerceForecastEntry commerceForecastEntry) {
		getPersistence().cacheResult(commerceForecastEntry);
	}

	/**
	* Caches the commerce forecast entries in the entity cache if it is enabled.
	*
	* @param commerceForecastEntries the commerce forecast entries
	*/
	public static void cacheResult(
		List<CommerceForecastEntry> commerceForecastEntries) {
		getPersistence().cacheResult(commerceForecastEntries);
	}

	/**
	* Creates a new commerce forecast entry with the primary key. Does not add the commerce forecast entry to the database.
	*
	* @param commerceForecastEntryId the primary key for the new commerce forecast entry
	* @return the new commerce forecast entry
	*/
	public static CommerceForecastEntry create(long commerceForecastEntryId) {
		return getPersistence().create(commerceForecastEntryId);
	}

	/**
	* Removes the commerce forecast entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry that was removed
	* @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	*/
	public static CommerceForecastEntry remove(long commerceForecastEntryId)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastEntryException {
		return getPersistence().remove(commerceForecastEntryId);
	}

	public static CommerceForecastEntry updateImpl(
		CommerceForecastEntry commerceForecastEntry) {
		return getPersistence().updateImpl(commerceForecastEntry);
	}

	/**
	* Returns the commerce forecast entry with the primary key or throws a {@link NoSuchForecastEntryException} if it could not be found.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry
	* @throws NoSuchForecastEntryException if a commerce forecast entry with the primary key could not be found
	*/
	public static CommerceForecastEntry findByPrimaryKey(
		long commerceForecastEntryId)
		throws com.liferay.commerce.forecast.exception.NoSuchForecastEntryException {
		return getPersistence().findByPrimaryKey(commerceForecastEntryId);
	}

	/**
	* Returns the commerce forecast entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry, or <code>null</code> if a commerce forecast entry with the primary key could not be found
	*/
	public static CommerceForecastEntry fetchByPrimaryKey(
		long commerceForecastEntryId) {
		return getPersistence().fetchByPrimaryKey(commerceForecastEntryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceForecastEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce forecast entries.
	*
	* @return the commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @return the range of commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findAll(int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce forecast entries
	*/
	public static List<CommerceForecastEntry> findAll(int start, int end,
		OrderByComparator<CommerceForecastEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce forecast entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce forecast entries.
	*
	* @return the number of commerce forecast entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceForecastEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceForecastEntryPersistence, CommerceForecastEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceForecastEntryPersistence.class);

		ServiceTracker<CommerceForecastEntryPersistence, CommerceForecastEntryPersistence> serviceTracker =
			new ServiceTracker<CommerceForecastEntryPersistence, CommerceForecastEntryPersistence>(bundle.getBundleContext(),
				CommerceForecastEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}