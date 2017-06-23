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

package com.liferay.powwow.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import com.liferay.powwow.model.PowwowServer;

import java.util.List;

/**
 * The persistence utility for the powwow server service. This utility wraps {@link com.liferay.powwow.service.persistence.impl.PowwowServerPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowServerPersistence
 * @see com.liferay.powwow.service.persistence.impl.PowwowServerPersistenceImpl
 * @generated
 */
@ProviderType
public class PowwowServerUtil {
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
	public static void clearCache(PowwowServer powwowServer) {
		getPersistence().clearCache(powwowServer);
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
	public static List<PowwowServer> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PowwowServer> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PowwowServer> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PowwowServer> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PowwowServer update(PowwowServer powwowServer) {
		return getPersistence().update(powwowServer);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PowwowServer update(PowwowServer powwowServer,
		ServiceContext serviceContext) {
		return getPersistence().update(powwowServer, serviceContext);
	}

	/**
	* Returns all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @return the matching powwow servers
	*/
	public static List<PowwowServer> findByPT_A(java.lang.String providerType,
		boolean active) {
		return getPersistence().findByPT_A(providerType, active);
	}

	/**
	* Returns a range of all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param providerType the provider type
	* @param active the active
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @return the range of matching powwow servers
	*/
	public static List<PowwowServer> findByPT_A(java.lang.String providerType,
		boolean active, int start, int end) {
		return getPersistence().findByPT_A(providerType, active, start, end);
	}

	/**
	* Returns an ordered range of all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param providerType the provider type
	* @param active the active
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow servers
	*/
	public static List<PowwowServer> findByPT_A(java.lang.String providerType,
		boolean active, int start, int end,
		OrderByComparator<PowwowServer> orderByComparator) {
		return getPersistence()
				   .findByPT_A(providerType, active, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param providerType the provider type
	* @param active the active
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow servers
	*/
	public static List<PowwowServer> findByPT_A(java.lang.String providerType,
		boolean active, int start, int end,
		OrderByComparator<PowwowServer> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPT_A(providerType, active, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow server
	* @throws NoSuchServerException if a matching powwow server could not be found
	*/
	public static PowwowServer findByPT_A_First(java.lang.String providerType,
		boolean active, OrderByComparator<PowwowServer> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchServerException {
		return getPersistence()
				   .findByPT_A_First(providerType, active, orderByComparator);
	}

	/**
	* Returns the first powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow server, or <code>null</code> if a matching powwow server could not be found
	*/
	public static PowwowServer fetchByPT_A_First(
		java.lang.String providerType, boolean active,
		OrderByComparator<PowwowServer> orderByComparator) {
		return getPersistence()
				   .fetchByPT_A_First(providerType, active, orderByComparator);
	}

	/**
	* Returns the last powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow server
	* @throws NoSuchServerException if a matching powwow server could not be found
	*/
	public static PowwowServer findByPT_A_Last(java.lang.String providerType,
		boolean active, OrderByComparator<PowwowServer> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchServerException {
		return getPersistence()
				   .findByPT_A_Last(providerType, active, orderByComparator);
	}

	/**
	* Returns the last powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow server, or <code>null</code> if a matching powwow server could not be found
	*/
	public static PowwowServer fetchByPT_A_Last(java.lang.String providerType,
		boolean active, OrderByComparator<PowwowServer> orderByComparator) {
		return getPersistence()
				   .fetchByPT_A_Last(providerType, active, orderByComparator);
	}

	/**
	* Returns the powwow servers before and after the current powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param powwowServerId the primary key of the current powwow server
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow server
	* @throws NoSuchServerException if a powwow server with the primary key could not be found
	*/
	public static PowwowServer[] findByPT_A_PrevAndNext(long powwowServerId,
		java.lang.String providerType, boolean active,
		OrderByComparator<PowwowServer> orderByComparator)
		throws com.liferay.powwow.exception.NoSuchServerException {
		return getPersistence()
				   .findByPT_A_PrevAndNext(powwowServerId, providerType,
			active, orderByComparator);
	}

	/**
	* Removes all the powwow servers where providerType = &#63; and active = &#63; from the database.
	*
	* @param providerType the provider type
	* @param active the active
	*/
	public static void removeByPT_A(java.lang.String providerType,
		boolean active) {
		getPersistence().removeByPT_A(providerType, active);
	}

	/**
	* Returns the number of powwow servers where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @return the number of matching powwow servers
	*/
	public static int countByPT_A(java.lang.String providerType, boolean active) {
		return getPersistence().countByPT_A(providerType, active);
	}

	/**
	* Caches the powwow server in the entity cache if it is enabled.
	*
	* @param powwowServer the powwow server
	*/
	public static void cacheResult(PowwowServer powwowServer) {
		getPersistence().cacheResult(powwowServer);
	}

	/**
	* Caches the powwow servers in the entity cache if it is enabled.
	*
	* @param powwowServers the powwow servers
	*/
	public static void cacheResult(List<PowwowServer> powwowServers) {
		getPersistence().cacheResult(powwowServers);
	}

	/**
	* Creates a new powwow server with the primary key. Does not add the powwow server to the database.
	*
	* @param powwowServerId the primary key for the new powwow server
	* @return the new powwow server
	*/
	public static PowwowServer create(long powwowServerId) {
		return getPersistence().create(powwowServerId);
	}

	/**
	* Removes the powwow server with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server that was removed
	* @throws NoSuchServerException if a powwow server with the primary key could not be found
	*/
	public static PowwowServer remove(long powwowServerId)
		throws com.liferay.powwow.exception.NoSuchServerException {
		return getPersistence().remove(powwowServerId);
	}

	public static PowwowServer updateImpl(PowwowServer powwowServer) {
		return getPersistence().updateImpl(powwowServer);
	}

	/**
	* Returns the powwow server with the primary key or throws a {@link NoSuchServerException} if it could not be found.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server
	* @throws NoSuchServerException if a powwow server with the primary key could not be found
	*/
	public static PowwowServer findByPrimaryKey(long powwowServerId)
		throws com.liferay.powwow.exception.NoSuchServerException {
		return getPersistence().findByPrimaryKey(powwowServerId);
	}

	/**
	* Returns the powwow server with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server, or <code>null</code> if a powwow server with the primary key could not be found
	*/
	public static PowwowServer fetchByPrimaryKey(long powwowServerId) {
		return getPersistence().fetchByPrimaryKey(powwowServerId);
	}

	public static java.util.Map<java.io.Serializable, PowwowServer> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the powwow servers.
	*
	* @return the powwow servers
	*/
	public static List<PowwowServer> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the powwow servers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @return the range of powwow servers
	*/
	public static List<PowwowServer> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the powwow servers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of powwow servers
	*/
	public static List<PowwowServer> findAll(int start, int end,
		OrderByComparator<PowwowServer> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the powwow servers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of powwow servers
	*/
	public static List<PowwowServer> findAll(int start, int end,
		OrderByComparator<PowwowServer> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the powwow servers from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of powwow servers.
	*
	* @return the number of powwow servers
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static PowwowServerPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PowwowServerPersistence)PortletBeanLocatorUtil.locate(com.liferay.powwow.service.ClpSerializer.getServletContextName(),
					PowwowServerPersistence.class.getName());

			ReferenceRegistry.registerReference(PowwowServerUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static PowwowServerPersistence _persistence;
}