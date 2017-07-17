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

package com.liferay.friendly.url.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.model.FriendlyURLMapping;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the friendly url mapping service. This utility wraps {@link com.liferay.friendly.url.service.persistence.impl.FriendlyURLMappingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLMappingPersistence
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLMappingPersistenceImpl
 * @generated
 */
@ProviderType
public class FriendlyURLMappingUtil {
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
	public static void clearCache(FriendlyURLMapping friendlyURLMapping) {
		getPersistence().clearCache(friendlyURLMapping);
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
	public static List<FriendlyURLMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FriendlyURLMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FriendlyURLMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FriendlyURLMapping> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FriendlyURLMapping update(
		FriendlyURLMapping friendlyURLMapping) {
		return getPersistence().update(friendlyURLMapping);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FriendlyURLMapping update(
		FriendlyURLMapping friendlyURLMapping, ServiceContext serviceContext) {
		return getPersistence().update(friendlyURLMapping, serviceContext);
	}

	/**
	* Caches the friendly url mapping in the entity cache if it is enabled.
	*
	* @param friendlyURLMapping the friendly url mapping
	*/
	public static void cacheResult(FriendlyURLMapping friendlyURLMapping) {
		getPersistence().cacheResult(friendlyURLMapping);
	}

	/**
	* Caches the friendly url mappings in the entity cache if it is enabled.
	*
	* @param friendlyURLMappings the friendly url mappings
	*/
	public static void cacheResult(List<FriendlyURLMapping> friendlyURLMappings) {
		getPersistence().cacheResult(friendlyURLMappings);
	}

	/**
	* Creates a new friendly url mapping with the primary key. Does not add the friendly url mapping to the database.
	*
	* @param friendlyURLMappingPK the primary key for the new friendly url mapping
	* @return the new friendly url mapping
	*/
	public static FriendlyURLMapping create(
		FriendlyURLMappingPK friendlyURLMappingPK) {
		return getPersistence().create(friendlyURLMappingPK);
	}

	/**
	* Removes the friendly url mapping with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLMappingPK the primary key of the friendly url mapping
	* @return the friendly url mapping that was removed
	* @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	*/
	public static FriendlyURLMapping remove(
		FriendlyURLMappingPK friendlyURLMappingPK)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLMappingException {
		return getPersistence().remove(friendlyURLMappingPK);
	}

	public static FriendlyURLMapping updateImpl(
		FriendlyURLMapping friendlyURLMapping) {
		return getPersistence().updateImpl(friendlyURLMapping);
	}

	/**
	* Returns the friendly url mapping with the primary key or throws a {@link NoSuchFriendlyURLMappingException} if it could not be found.
	*
	* @param friendlyURLMappingPK the primary key of the friendly url mapping
	* @return the friendly url mapping
	* @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	*/
	public static FriendlyURLMapping findByPrimaryKey(
		FriendlyURLMappingPK friendlyURLMappingPK)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLMappingException {
		return getPersistence().findByPrimaryKey(friendlyURLMappingPK);
	}

	/**
	* Returns the friendly url mapping with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLMappingPK the primary key of the friendly url mapping
	* @return the friendly url mapping, or <code>null</code> if a friendly url mapping with the primary key could not be found
	*/
	public static FriendlyURLMapping fetchByPrimaryKey(
		FriendlyURLMappingPK friendlyURLMappingPK) {
		return getPersistence().fetchByPrimaryKey(friendlyURLMappingPK);
	}

	public static java.util.Map<java.io.Serializable, FriendlyURLMapping> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the friendly url mappings.
	*
	* @return the friendly url mappings
	*/
	public static List<FriendlyURLMapping> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the friendly url mappings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url mappings
	* @param end the upper bound of the range of friendly url mappings (not inclusive)
	* @return the range of friendly url mappings
	*/
	public static List<FriendlyURLMapping> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the friendly url mappings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url mappings
	* @param end the upper bound of the range of friendly url mappings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of friendly url mappings
	*/
	public static List<FriendlyURLMapping> findAll(int start, int end,
		OrderByComparator<FriendlyURLMapping> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the friendly url mappings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url mappings
	* @param end the upper bound of the range of friendly url mappings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of friendly url mappings
	*/
	public static List<FriendlyURLMapping> findAll(int start, int end,
		OrderByComparator<FriendlyURLMapping> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the friendly url mappings from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of friendly url mappings.
	*
	* @return the number of friendly url mappings
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FriendlyURLMappingPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FriendlyURLMappingPersistence, FriendlyURLMappingPersistence> _serviceTracker =
		ServiceTrackerFactory.open(FriendlyURLMappingPersistence.class);
}