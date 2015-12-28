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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the user tracker path service. This utility wraps {@link com.liferay.portal.service.persistence.impl.UserTrackerPathPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPathPersistence
 * @see com.liferay.portal.service.persistence.impl.UserTrackerPathPersistenceImpl
 * @generated
 */
@ProviderType
public class UserTrackerPathUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(UserTrackerPath userTrackerPath) {
		getPersistence().clearCache(userTrackerPath);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UserTrackerPath> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserTrackerPath> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserTrackerPath> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static UserTrackerPath update(UserTrackerPath userTrackerPath) {
		return getPersistence().update(userTrackerPath);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static UserTrackerPath update(UserTrackerPath userTrackerPath,
		ServiceContext serviceContext) {
		return getPersistence().update(userTrackerPath, serviceContext);
	}

	/**
	* Returns all the user tracker paths where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @return the matching user tracker paths
	*/
	public static List<UserTrackerPath> findByUserTrackerId(long userTrackerId) {
		return getPersistence().findByUserTrackerId(userTrackerId);
	}

	/**
	* Returns a range of all the user tracker paths where userTrackerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userTrackerId the user tracker ID
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @return the range of matching user tracker paths
	*/
	public static List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end) {
		return getPersistence().findByUserTrackerId(userTrackerId, start, end);
	}

	/**
	* Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userTrackerId the user tracker ID
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user tracker paths
	*/
	public static List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator) {
		return getPersistence()
				   .findByUserTrackerId(userTrackerId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userTrackerId the user tracker ID
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user tracker paths
	*/
	public static List<UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserTrackerId(userTrackerId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker path
	* @throws NoSuchUserTrackerPathException if a matching user tracker path could not be found
	*/
	public static UserTrackerPath findByUserTrackerId_First(
		long userTrackerId, OrderByComparator<UserTrackerPath> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence()
				   .findByUserTrackerId_First(userTrackerId, orderByComparator);
	}

	/**
	* Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	*/
	public static UserTrackerPath fetchByUserTrackerId_First(
		long userTrackerId, OrderByComparator<UserTrackerPath> orderByComparator) {
		return getPersistence()
				   .fetchByUserTrackerId_First(userTrackerId, orderByComparator);
	}

	/**
	* Returns the last user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker path
	* @throws NoSuchUserTrackerPathException if a matching user tracker path could not be found
	*/
	public static UserTrackerPath findByUserTrackerId_Last(long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence()
				   .findByUserTrackerId_Last(userTrackerId, orderByComparator);
	}

	/**
	* Returns the last user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	*/
	public static UserTrackerPath fetchByUserTrackerId_Last(
		long userTrackerId, OrderByComparator<UserTrackerPath> orderByComparator) {
		return getPersistence()
				   .fetchByUserTrackerId_Last(userTrackerId, orderByComparator);
	}

	/**
	* Returns the user tracker paths before and after the current user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerPathId the primary key of the current user tracker path
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker path
	* @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	*/
	public static UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId,
		OrderByComparator<UserTrackerPath> orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence()
				   .findByUserTrackerId_PrevAndNext(userTrackerPathId,
			userTrackerId, orderByComparator);
	}

	/**
	* Removes all the user tracker paths where userTrackerId = &#63; from the database.
	*
	* @param userTrackerId the user tracker ID
	*/
	public static void removeByUserTrackerId(long userTrackerId) {
		getPersistence().removeByUserTrackerId(userTrackerId);
	}

	/**
	* Returns the number of user tracker paths where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @return the number of matching user tracker paths
	*/
	public static int countByUserTrackerId(long userTrackerId) {
		return getPersistence().countByUserTrackerId(userTrackerId);
	}

	/**
	* Caches the user tracker path in the entity cache if it is enabled.
	*
	* @param userTrackerPath the user tracker path
	*/
	public static void cacheResult(UserTrackerPath userTrackerPath) {
		getPersistence().cacheResult(userTrackerPath);
	}

	/**
	* Caches the user tracker paths in the entity cache if it is enabled.
	*
	* @param userTrackerPaths the user tracker paths
	*/
	public static void cacheResult(List<UserTrackerPath> userTrackerPaths) {
		getPersistence().cacheResult(userTrackerPaths);
	}

	/**
	* Creates a new user tracker path with the primary key. Does not add the user tracker path to the database.
	*
	* @param userTrackerPathId the primary key for the new user tracker path
	* @return the new user tracker path
	*/
	public static UserTrackerPath create(long userTrackerPathId) {
		return getPersistence().create(userTrackerPathId);
	}

	/**
	* Removes the user tracker path with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userTrackerPathId the primary key of the user tracker path
	* @return the user tracker path that was removed
	* @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	*/
	public static UserTrackerPath remove(long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence().remove(userTrackerPathId);
	}

	public static UserTrackerPath updateImpl(UserTrackerPath userTrackerPath) {
		return getPersistence().updateImpl(userTrackerPath);
	}

	/**
	* Returns the user tracker path with the primary key or throws a {@link NoSuchUserTrackerPathException} if it could not be found.
	*
	* @param userTrackerPathId the primary key of the user tracker path
	* @return the user tracker path
	* @throws NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	*/
	public static UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException {
		return getPersistence().findByPrimaryKey(userTrackerPathId);
	}

	/**
	* Returns the user tracker path with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userTrackerPathId the primary key of the user tracker path
	* @return the user tracker path, or <code>null</code> if a user tracker path with the primary key could not be found
	*/
	public static UserTrackerPath fetchByPrimaryKey(long userTrackerPathId) {
		return getPersistence().fetchByPrimaryKey(userTrackerPathId);
	}

	public static java.util.Map<java.io.Serializable, UserTrackerPath> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the user tracker paths.
	*
	* @return the user tracker paths
	*/
	public static List<UserTrackerPath> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the user tracker paths.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @return the range of user tracker paths
	*/
	public static List<UserTrackerPath> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the user tracker paths.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user tracker paths
	*/
	public static List<UserTrackerPath> findAll(int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the user tracker paths.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of user tracker paths
	*/
	public static List<UserTrackerPath> findAll(int start, int end,
		OrderByComparator<UserTrackerPath> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the user tracker paths from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of user tracker paths.
	*
	* @return the number of user tracker paths
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static UserTrackerPathPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserTrackerPathPersistence)PortalBeanLocatorUtil.locate(UserTrackerPathPersistence.class.getName());

			ReferenceRegistry.registerReference(UserTrackerPathUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static UserTrackerPathPersistence _persistence;
}