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

package com.liferay.sharepoint.rest.oauth2.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the sharepoint o auth2 token entry service. This utility wraps {@link com.liferay.sharepoint.rest.oauth2.service.persistence.impl.SharepointOAuth2TokenEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntryPersistence
 * @see com.liferay.sharepoint.rest.oauth2.service.persistence.impl.SharepointOAuth2TokenEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class SharepointOAuth2TokenEntryUtil {
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
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		getPersistence().clearCache(sharepointOAuth2TokenEntry);
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
	public static List<SharepointOAuth2TokenEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SharepointOAuth2TokenEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SharepointOAuth2TokenEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SharepointOAuth2TokenEntry update(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return getPersistence().update(sharepointOAuth2TokenEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SharepointOAuth2TokenEntry update(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(sharepointOAuth2TokenEntry, serviceContext);
	}

	/**
	* Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or throws a {@link NoSuch2TokenEntryException} if it could not be found.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the matching sharepoint o auth2 token entry
	* @throws NoSuch2TokenEntryException if a matching sharepoint o auth2 token entry could not be found
	*/
	public static SharepointOAuth2TokenEntry findByU_C(long userId,
		java.lang.String configurationPid)
		throws com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException {
		return getPersistence().findByU_C(userId, configurationPid);
	}

	/**
	* Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	*/
	public static SharepointOAuth2TokenEntry fetchByU_C(long userId,
		java.lang.String configurationPid) {
		return getPersistence().fetchByU_C(userId, configurationPid);
	}

	/**
	* Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	*/
	public static SharepointOAuth2TokenEntry fetchByU_C(long userId,
		java.lang.String configurationPid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByU_C(userId, configurationPid, retrieveFromCache);
	}

	/**
	* Removes the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; from the database.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the sharepoint o auth2 token entry that was removed
	*/
	public static SharepointOAuth2TokenEntry removeByU_C(long userId,
		java.lang.String configurationPid)
		throws com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException {
		return getPersistence().removeByU_C(userId, configurationPid);
	}

	/**
	* Returns the number of sharepoint o auth2 token entries where userId = &#63; and configurationPid = &#63;.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the number of matching sharepoint o auth2 token entries
	*/
	public static int countByU_C(long userId, java.lang.String configurationPid) {
		return getPersistence().countByU_C(userId, configurationPid);
	}

	/**
	* Caches the sharepoint o auth2 token entry in the entity cache if it is enabled.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	*/
	public static void cacheResult(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		getPersistence().cacheResult(sharepointOAuth2TokenEntry);
	}

	/**
	* Caches the sharepoint o auth2 token entries in the entity cache if it is enabled.
	*
	* @param sharepointOAuth2TokenEntries the sharepoint o auth2 token entries
	*/
	public static void cacheResult(
		List<SharepointOAuth2TokenEntry> sharepointOAuth2TokenEntries) {
		getPersistence().cacheResult(sharepointOAuth2TokenEntries);
	}

	/**
	* Creates a new sharepoint o auth2 token entry with the primary key. Does not add the sharepoint o auth2 token entry to the database.
	*
	* @param sharepointOAuth2TokenEntryId the primary key for the new sharepoint o auth2 token entry
	* @return the new sharepoint o auth2 token entry
	*/
	public static SharepointOAuth2TokenEntry create(
		long sharepointOAuth2TokenEntryId) {
		return getPersistence().create(sharepointOAuth2TokenEntryId);
	}

	/**
	* Removes the sharepoint o auth2 token entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was removed
	* @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public static SharepointOAuth2TokenEntry remove(
		long sharepointOAuth2TokenEntryId)
		throws com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException {
		return getPersistence().remove(sharepointOAuth2TokenEntryId);
	}

	public static SharepointOAuth2TokenEntry updateImpl(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return getPersistence().updateImpl(sharepointOAuth2TokenEntry);
	}

	/**
	* Returns the sharepoint o auth2 token entry with the primary key or throws a {@link NoSuch2TokenEntryException} if it could not be found.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry
	* @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public static SharepointOAuth2TokenEntry findByPrimaryKey(
		long sharepointOAuth2TokenEntryId)
		throws com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException {
		return getPersistence().findByPrimaryKey(sharepointOAuth2TokenEntryId);
	}

	/**
	* Returns the sharepoint o auth2 token entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry, or <code>null</code> if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public static SharepointOAuth2TokenEntry fetchByPrimaryKey(
		long sharepointOAuth2TokenEntryId) {
		return getPersistence().fetchByPrimaryKey(sharepointOAuth2TokenEntryId);
	}

	public static java.util.Map<java.io.Serializable, SharepointOAuth2TokenEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the sharepoint o auth2 token entries.
	*
	* @return the sharepoint o auth2 token entries
	*/
	public static List<SharepointOAuth2TokenEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the sharepoint o auth2 token entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharepoint o auth2 token entries
	* @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	* @return the range of sharepoint o auth2 token entries
	*/
	public static List<SharepointOAuth2TokenEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the sharepoint o auth2 token entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharepoint o auth2 token entries
	* @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of sharepoint o auth2 token entries
	*/
	public static List<SharepointOAuth2TokenEntry> findAll(int start, int end,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharepoint o auth2 token entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharepoint o auth2 token entries
	* @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of sharepoint o auth2 token entries
	*/
	public static List<SharepointOAuth2TokenEntry> findAll(int start, int end,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the sharepoint o auth2 token entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of sharepoint o auth2 token entries.
	*
	* @return the number of sharepoint o auth2 token entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SharepointOAuth2TokenEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SharepointOAuth2TokenEntryPersistence, SharepointOAuth2TokenEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SharepointOAuth2TokenEntryPersistence.class);

		ServiceTracker<SharepointOAuth2TokenEntryPersistence, SharepointOAuth2TokenEntryPersistence> serviceTracker =
			new ServiceTracker<SharepointOAuth2TokenEntryPersistence, SharepointOAuth2TokenEntryPersistence>(bundle.getBundleContext(),
				SharepointOAuth2TokenEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}