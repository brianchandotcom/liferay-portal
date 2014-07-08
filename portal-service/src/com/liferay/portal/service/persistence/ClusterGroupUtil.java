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
import com.liferay.portal.model.ClusterGroup;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the cluster group service. This utility wraps {@link ClusterGroupPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClusterGroupPersistence
 * @see ClusterGroupPersistenceImpl
 * @generated
 */
@ProviderType
public class ClusterGroupUtil {
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
	public static void clearCache(ClusterGroup clusterGroup) {
		getPersistence().clearCache(clusterGroup);
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
	public static List<ClusterGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ClusterGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ClusterGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ClusterGroup update(ClusterGroup clusterGroup) {
		return getPersistence().update(clusterGroup);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ClusterGroup update(ClusterGroup clusterGroup,
		ServiceContext serviceContext) {
		return getPersistence().update(clusterGroup, serviceContext);
	}

	/**
	* Caches the cluster group in the entity cache if it is enabled.
	*
	* @param clusterGroup the cluster group
	*/
	public static void cacheResult(
		com.liferay.portal.model.ClusterGroup clusterGroup) {
		getPersistence().cacheResult(clusterGroup);
	}

	/**
	* Caches the cluster groups in the entity cache if it is enabled.
	*
	* @param clusterGroups the cluster groups
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ClusterGroup> clusterGroups) {
		getPersistence().cacheResult(clusterGroups);
	}

	/**
	* Creates a new cluster group with the primary key. Does not add the cluster group to the database.
	*
	* @param clusterGroupId the primary key for the new cluster group
	* @return the new cluster group
	*/
	public static com.liferay.portal.model.ClusterGroup create(
		long clusterGroupId) {
		return getPersistence().create(clusterGroupId);
	}

	/**
	* Removes the cluster group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group that was removed
	* @throws com.liferay.portal.NoSuchClusterGroupException if a cluster group with the primary key could not be found
	*/
	public static com.liferay.portal.model.ClusterGroup remove(
		long clusterGroupId)
		throws com.liferay.portal.NoSuchClusterGroupException {
		return getPersistence().remove(clusterGroupId);
	}

	public static com.liferay.portal.model.ClusterGroup updateImpl(
		com.liferay.portal.model.ClusterGroup clusterGroup) {
		return getPersistence().updateImpl(clusterGroup);
	}

	/**
	* Returns the cluster group with the primary key or throws a {@link com.liferay.portal.NoSuchClusterGroupException} if it could not be found.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group
	* @throws com.liferay.portal.NoSuchClusterGroupException if a cluster group with the primary key could not be found
	*/
	public static com.liferay.portal.model.ClusterGroup findByPrimaryKey(
		long clusterGroupId)
		throws com.liferay.portal.NoSuchClusterGroupException {
		return getPersistence().findByPrimaryKey(clusterGroupId);
	}

	/**
	* Returns the cluster group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group, or <code>null</code> if a cluster group with the primary key could not be found
	*/
	public static com.liferay.portal.model.ClusterGroup fetchByPrimaryKey(
		long clusterGroupId) {
		return getPersistence().fetchByPrimaryKey(clusterGroupId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.ClusterGroup> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cluster groups.
	*
	* @return the cluster groups
	*/
	public static java.util.List<com.liferay.portal.model.ClusterGroup> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cluster groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClusterGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cluster groups
	* @param end the upper bound of the range of cluster groups (not inclusive)
	* @return the range of cluster groups
	*/
	public static java.util.List<com.liferay.portal.model.ClusterGroup> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cluster groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClusterGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cluster groups
	* @param end the upper bound of the range of cluster groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cluster groups
	*/
	public static java.util.List<com.liferay.portal.model.ClusterGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the cluster groups from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cluster groups.
	*
	* @return the number of cluster groups
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ClusterGroupPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ClusterGroupPersistence)PortalBeanLocatorUtil.locate(ClusterGroupPersistence.class.getName());

			ReferenceRegistry.registerReference(ClusterGroupUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ClusterGroupPersistence persistence) {
	}

	private static ClusterGroupPersistence _persistence;
}