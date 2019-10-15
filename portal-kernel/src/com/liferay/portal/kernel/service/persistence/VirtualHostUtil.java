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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the virtual host service. This utility wraps <code>com.liferay.portal.service.persistence.impl.VirtualHostPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostPersistence
 * @generated
 */
public class VirtualHostUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(VirtualHost virtualHost) {
		getPersistence().clearCache(virtualHost);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, VirtualHost> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<VirtualHost> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static VirtualHost update(VirtualHost virtualHost) {
		return getPersistence().update(virtualHost);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static VirtualHost update(
		VirtualHost virtualHost, ServiceContext serviceContext) {

		return getPersistence().update(virtualHost, serviceContext);
	}

	/**
	 * Returns the virtual host where hostname = &#63; or throws a <code>NoSuchVirtualHostException</code> if it could not be found.
	 *
	 * @param hostname the hostname
	 * @return the matching virtual host
	 * @throws NoSuchVirtualHostException if a matching virtual host could not be found
	 */
	public static VirtualHost findByHostname(String hostname)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().findByHostname(hostname);
	}

	/**
	 * Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param hostname the hostname
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	public static VirtualHost fetchByHostname(String hostname) {
		return getPersistence().fetchByHostname(hostname);
	}

	/**
	 * Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param hostname the hostname
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	public static VirtualHost fetchByHostname(
		String hostname, boolean useFinderCache) {

		return getPersistence().fetchByHostname(hostname, useFinderCache);
	}

	/**
	 * Removes the virtual host where hostname = &#63; from the database.
	 *
	 * @param hostname the hostname
	 * @return the virtual host that was removed
	 */
	public static VirtualHost removeByHostname(String hostname)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().removeByHostname(hostname);
	}

	/**
	 * Returns the number of virtual hosts where hostname = &#63;.
	 *
	 * @param hostname the hostname
	 * @return the number of matching virtual hosts
	 */
	public static int countByHostname(String hostname) {
		return getPersistence().countByHostname(hostname);
	}

	/**
	 * Returns all the virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the matching virtual hosts
	 */
	public static List<VirtualHost> findByC_L(
		long companyId, long layoutSetId) {

		return getPersistence().findByC_L(companyId, layoutSetId);
	}

	/**
	 * Returns a range of all the virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>VirtualHostModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @return the range of matching virtual hosts
	 */
	public static List<VirtualHost> findByC_L(
		long companyId, long layoutSetId, int start, int end) {

		return getPersistence().findByC_L(companyId, layoutSetId, start, end);
	}

	/**
	 * Returns an ordered range of all the virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>VirtualHostModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching virtual hosts
	 */
	public static List<VirtualHost> findByC_L(
		long companyId, long layoutSetId, int start, int end,
		OrderByComparator<VirtualHost> orderByComparator) {

		return getPersistence().findByC_L(
			companyId, layoutSetId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>VirtualHostModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching virtual hosts
	 */
	public static List<VirtualHost> findByC_L(
		long companyId, long layoutSetId, int start, int end,
		OrderByComparator<VirtualHost> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_L(
			companyId, layoutSetId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first virtual host in the ordered set where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching virtual host
	 * @throws NoSuchVirtualHostException if a matching virtual host could not be found
	 */
	public static VirtualHost findByC_L_First(
			long companyId, long layoutSetId,
			OrderByComparator<VirtualHost> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().findByC_L_First(
			companyId, layoutSetId, orderByComparator);
	}

	/**
	 * Returns the first virtual host in the ordered set where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	public static VirtualHost fetchByC_L_First(
		long companyId, long layoutSetId,
		OrderByComparator<VirtualHost> orderByComparator) {

		return getPersistence().fetchByC_L_First(
			companyId, layoutSetId, orderByComparator);
	}

	/**
	 * Returns the last virtual host in the ordered set where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching virtual host
	 * @throws NoSuchVirtualHostException if a matching virtual host could not be found
	 */
	public static VirtualHost findByC_L_Last(
			long companyId, long layoutSetId,
			OrderByComparator<VirtualHost> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().findByC_L_Last(
			companyId, layoutSetId, orderByComparator);
	}

	/**
	 * Returns the last virtual host in the ordered set where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	public static VirtualHost fetchByC_L_Last(
		long companyId, long layoutSetId,
		OrderByComparator<VirtualHost> orderByComparator) {

		return getPersistence().fetchByC_L_Last(
			companyId, layoutSetId, orderByComparator);
	}

	/**
	 * Returns the virtual hosts before and after the current virtual host in the ordered set where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param virtualHostId the primary key of the current virtual host
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next virtual host
	 * @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 */
	public static VirtualHost[] findByC_L_PrevAndNext(
			long virtualHostId, long companyId, long layoutSetId,
			OrderByComparator<VirtualHost> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().findByC_L_PrevAndNext(
			virtualHostId, companyId, layoutSetId, orderByComparator);
	}

	/**
	 * Removes all the virtual hosts where companyId = &#63; and layoutSetId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 */
	public static void removeByC_L(long companyId, long layoutSetId) {
		getPersistence().removeByC_L(companyId, layoutSetId);
	}

	/**
	 * Returns the number of virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @return the number of matching virtual hosts
	 */
	public static int countByC_L(long companyId, long layoutSetId) {
		return getPersistence().countByC_L(companyId, layoutSetId);
	}

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; and defaultVirtualHost = &#63; or throws a <code>NoSuchVirtualHostException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param defaultVirtualHost the default virtual host
	 * @return the matching virtual host
	 * @throws NoSuchVirtualHostException if a matching virtual host could not be found
	 */
	public static VirtualHost findByC_L_D(
			long companyId, long layoutSetId, boolean defaultVirtualHost)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().findByC_L_D(
			companyId, layoutSetId, defaultVirtualHost);
	}

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; and defaultVirtualHost = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param defaultVirtualHost the default virtual host
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	public static VirtualHost fetchByC_L_D(
		long companyId, long layoutSetId, boolean defaultVirtualHost) {

		return getPersistence().fetchByC_L_D(
			companyId, layoutSetId, defaultVirtualHost);
	}

	/**
	 * Returns the virtual host where companyId = &#63; and layoutSetId = &#63; and defaultVirtualHost = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param defaultVirtualHost the default virtual host
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	 */
	public static VirtualHost fetchByC_L_D(
		long companyId, long layoutSetId, boolean defaultVirtualHost,
		boolean useFinderCache) {

		return getPersistence().fetchByC_L_D(
			companyId, layoutSetId, defaultVirtualHost, useFinderCache);
	}

	/**
	 * Removes the virtual host where companyId = &#63; and layoutSetId = &#63; and defaultVirtualHost = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param defaultVirtualHost the default virtual host
	 * @return the virtual host that was removed
	 */
	public static VirtualHost removeByC_L_D(
			long companyId, long layoutSetId, boolean defaultVirtualHost)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().removeByC_L_D(
			companyId, layoutSetId, defaultVirtualHost);
	}

	/**
	 * Returns the number of virtual hosts where companyId = &#63; and layoutSetId = &#63; and defaultVirtualHost = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutSetId the layout set ID
	 * @param defaultVirtualHost the default virtual host
	 * @return the number of matching virtual hosts
	 */
	public static int countByC_L_D(
		long companyId, long layoutSetId, boolean defaultVirtualHost) {

		return getPersistence().countByC_L_D(
			companyId, layoutSetId, defaultVirtualHost);
	}

	/**
	 * Caches the virtual host in the entity cache if it is enabled.
	 *
	 * @param virtualHost the virtual host
	 */
	public static void cacheResult(VirtualHost virtualHost) {
		getPersistence().cacheResult(virtualHost);
	}

	/**
	 * Caches the virtual hosts in the entity cache if it is enabled.
	 *
	 * @param virtualHosts the virtual hosts
	 */
	public static void cacheResult(List<VirtualHost> virtualHosts) {
		getPersistence().cacheResult(virtualHosts);
	}

	/**
	 * Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	 *
	 * @param virtualHostId the primary key for the new virtual host
	 * @return the new virtual host
	 */
	public static VirtualHost create(long virtualHostId) {
		return getPersistence().create(virtualHostId);
	}

	/**
	 * Removes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host that was removed
	 * @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 */
	public static VirtualHost remove(long virtualHostId)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().remove(virtualHostId);
	}

	public static VirtualHost updateImpl(VirtualHost virtualHost) {
		return getPersistence().updateImpl(virtualHost);
	}

	/**
	 * Returns the virtual host with the primary key or throws a <code>NoSuchVirtualHostException</code> if it could not be found.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host
	 * @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	 */
	public static VirtualHost findByPrimaryKey(long virtualHostId)
		throws com.liferay.portal.kernel.exception.NoSuchVirtualHostException {

		return getPersistence().findByPrimaryKey(virtualHostId);
	}

	/**
	 * Returns the virtual host with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	 */
	public static VirtualHost fetchByPrimaryKey(long virtualHostId) {
		return getPersistence().fetchByPrimaryKey(virtualHostId);
	}

	/**
	 * Returns all the virtual hosts.
	 *
	 * @return the virtual hosts
	 */
	public static List<VirtualHost> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VirtualHostModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @return the range of virtual hosts
	 */
	public static List<VirtualHost> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VirtualHostModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of virtual hosts
	 */
	public static List<VirtualHost> findAll(
		int start, int end, OrderByComparator<VirtualHost> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VirtualHostModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of virtual hosts
	 */
	public static List<VirtualHost> findAll(
		int start, int end, OrderByComparator<VirtualHost> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the virtual hosts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of virtual hosts.
	 *
	 * @return the number of virtual hosts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static VirtualHostPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (VirtualHostPersistence)PortalBeanLocatorUtil.locate(
				VirtualHostPersistence.class.getName());
		}

		return _persistence;
	}

	private static VirtualHostPersistence _persistence;

}