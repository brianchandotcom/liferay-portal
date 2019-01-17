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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.UADPartialEntry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the uad partial entry service. This utility wraps {@link com.liferay.portal.tools.service.builder.test.service.persistence.impl.UADPartialEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntryPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.UADPartialEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class UADPartialEntryUtil {
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
	public static void clearCache(UADPartialEntry uadPartialEntry) {
		getPersistence().clearCache(uadPartialEntry);
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
	public static Map<Serializable, UADPartialEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UADPartialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UADPartialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UADPartialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UADPartialEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static UADPartialEntry update(UADPartialEntry uadPartialEntry) {
		return getPersistence().update(uadPartialEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static UADPartialEntry update(UADPartialEntry uadPartialEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(uadPartialEntry, serviceContext);
	}

	/**
	* Caches the uad partial entry in the entity cache if it is enabled.
	*
	* @param uadPartialEntry the uad partial entry
	*/
	public static void cacheResult(UADPartialEntry uadPartialEntry) {
		getPersistence().cacheResult(uadPartialEntry);
	}

	/**
	* Caches the uad partial entries in the entity cache if it is enabled.
	*
	* @param uadPartialEntries the uad partial entries
	*/
	public static void cacheResult(List<UADPartialEntry> uadPartialEntries) {
		getPersistence().cacheResult(uadPartialEntries);
	}

	/**
	* Creates a new uad partial entry with the primary key. Does not add the uad partial entry to the database.
	*
	* @param uadPartialEntryId the primary key for the new uad partial entry
	* @return the new uad partial entry
	*/
	public static UADPartialEntry create(long uadPartialEntryId) {
		return getPersistence().create(uadPartialEntryId);
	}

	/**
	* Removes the uad partial entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param uadPartialEntryId the primary key of the uad partial entry
	* @return the uad partial entry that was removed
	* @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	*/
	public static UADPartialEntry remove(long uadPartialEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchUADPartialEntryException {
		return getPersistence().remove(uadPartialEntryId);
	}

	public static UADPartialEntry updateImpl(UADPartialEntry uadPartialEntry) {
		return getPersistence().updateImpl(uadPartialEntry);
	}

	/**
	* Returns the uad partial entry with the primary key or throws a {@link NoSuchUADPartialEntryException} if it could not be found.
	*
	* @param uadPartialEntryId the primary key of the uad partial entry
	* @return the uad partial entry
	* @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	*/
	public static UADPartialEntry findByPrimaryKey(long uadPartialEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchUADPartialEntryException {
		return getPersistence().findByPrimaryKey(uadPartialEntryId);
	}

	/**
	* Returns the uad partial entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param uadPartialEntryId the primary key of the uad partial entry
	* @return the uad partial entry, or <code>null</code> if a uad partial entry with the primary key could not be found
	*/
	public static UADPartialEntry fetchByPrimaryKey(long uadPartialEntryId) {
		return getPersistence().fetchByPrimaryKey(uadPartialEntryId);
	}

	/**
	* Returns all the uad partial entries.
	*
	* @return the uad partial entries
	*/
	public static List<UADPartialEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the uad partial entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UADPartialEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of uad partial entries
	* @param end the upper bound of the range of uad partial entries (not inclusive)
	* @return the range of uad partial entries
	*/
	public static List<UADPartialEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the uad partial entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UADPartialEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of uad partial entries
	* @param end the upper bound of the range of uad partial entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of uad partial entries
	*/
	public static List<UADPartialEntry> findAll(int start, int end,
		OrderByComparator<UADPartialEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the uad partial entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UADPartialEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of uad partial entries
	* @param end the upper bound of the range of uad partial entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of uad partial entries
	*/
	public static List<UADPartialEntry> findAll(int start, int end,
		OrderByComparator<UADPartialEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the uad partial entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of uad partial entries.
	*
	* @return the number of uad partial entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static UADPartialEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<UADPartialEntryPersistence, UADPartialEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(UADPartialEntryPersistence.class);

		ServiceTracker<UADPartialEntryPersistence, UADPartialEntryPersistence> serviceTracker =
			new ServiceTracker<UADPartialEntryPersistence, UADPartialEntryPersistence>(bundle.getBundleContext(),
				UADPartialEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}