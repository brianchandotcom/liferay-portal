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
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the virtual host service. This utility wraps {@link com.liferay.portal.service.persistence.impl.VirtualHostPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostPersistence
 * @see com.liferay.portal.service.persistence.impl.VirtualHostPersistenceImpl
 * @generated
 */
@ProviderType
public class VirtualHostUtil {
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
	public static void clearCache(VirtualHost virtualHost) {
		getPersistence().clearCache(virtualHost);
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
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VirtualHost> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<VirtualHost> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static VirtualHost update(VirtualHost virtualHost) {
		return getPersistence().update(virtualHost);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static VirtualHost update(VirtualHost virtualHost,
		ServiceContext serviceContext) {
		return getPersistence().update(virtualHost, serviceContext);
	}

	/**
	* Returns the virtual host where hostname = &#63; or throws a {@link NoSuchVirtualHostException} if it could not be found.
	*
	* @param hostname the hostname
	* @return the matching virtual host
	* @throws NoSuchVirtualHostException if a matching virtual host could not be found
	*/
	public static VirtualHost findByHostname(java.lang.String hostname)
		throws com.liferay.portal.NoSuchVirtualHostException {
		return getPersistence().findByHostname(hostname);
	}

	/**
	* Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param hostname the hostname
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public static VirtualHost fetchByHostname(java.lang.String hostname) {
		return getPersistence().fetchByHostname(hostname);
	}

	/**
	* Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param hostname the hostname
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public static VirtualHost fetchByHostname(java.lang.String hostname,
		boolean retrieveFromCache) {
		return getPersistence().fetchByHostname(hostname, retrieveFromCache);
	}

	/**
	* Removes the virtual host where hostname = &#63; from the database.
	*
	* @param hostname the hostname
	* @return the virtual host that was removed
	*/
	public static VirtualHost removeByHostname(java.lang.String hostname)
		throws com.liferay.portal.NoSuchVirtualHostException {
		return getPersistence().removeByHostname(hostname);
	}

	/**
	* Returns the number of virtual hosts where hostname = &#63;.
	*
	* @param hostname the hostname
	* @return the number of matching virtual hosts
	*/
	public static int countByHostname(java.lang.String hostname) {
		return getPersistence().countByHostname(hostname);
	}

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or throws a {@link NoSuchVirtualHostException} if it could not be found.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the matching virtual host
	* @throws NoSuchVirtualHostException if a matching virtual host could not be found
	*/
	public static VirtualHost findByC_L(long companyId, long layoutSetId)
		throws com.liferay.portal.NoSuchVirtualHostException {
		return getPersistence().findByC_L(companyId, layoutSetId);
	}

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public static VirtualHost fetchByC_L(long companyId, long layoutSetId) {
		return getPersistence().fetchByC_L(companyId, layoutSetId);
	}

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public static VirtualHost fetchByC_L(long companyId, long layoutSetId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_L(companyId, layoutSetId, retrieveFromCache);
	}

	/**
	* Removes the virtual host where companyId = &#63; and layoutSetId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the virtual host that was removed
	*/
	public static VirtualHost removeByC_L(long companyId, long layoutSetId)
		throws com.liferay.portal.NoSuchVirtualHostException {
		return getPersistence().removeByC_L(companyId, layoutSetId);
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
		throws com.liferay.portal.NoSuchVirtualHostException {
		return getPersistence().remove(virtualHostId);
	}

	public static VirtualHost updateImpl(VirtualHost virtualHost) {
		return getPersistence().updateImpl(virtualHost);
	}

	/**
	* Returns the virtual host with the primary key or throws a {@link NoSuchVirtualHostException} if it could not be found.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host
	* @throws NoSuchVirtualHostException if a virtual host with the primary key could not be found
	*/
	public static VirtualHost findByPrimaryKey(long virtualHostId)
		throws com.liferay.portal.NoSuchVirtualHostException {
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

	public static java.util.Map<java.io.Serializable, VirtualHost> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of virtual hosts
	*/
	public static List<VirtualHost> findAll(int start, int end,
		OrderByComparator<VirtualHost> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of virtual hosts
	*/
	public static List<VirtualHost> findAll(int start, int end,
		OrderByComparator<VirtualHost> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
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
			_persistence = (VirtualHostPersistence)PortalBeanLocatorUtil.locate(VirtualHostPersistence.class.getName());

			ReferenceRegistry.registerReference(VirtualHostUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static VirtualHostPersistence _persistence;
}