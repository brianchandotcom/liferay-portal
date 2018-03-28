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

package com.liferay.saml.persistence.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.saml.persistence.model.SamlIdpSpConnection;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the saml idp sp connection service. This utility wraps {@link com.liferay.saml.persistence.service.persistence.impl.SamlIdpSpConnectionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnectionPersistence
 * @see com.liferay.saml.persistence.service.persistence.impl.SamlIdpSpConnectionPersistenceImpl
 * @generated
 */
@ProviderType
public class SamlIdpSpConnectionUtil {
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
	public static void clearCache(SamlIdpSpConnection samlIdpSpConnection) {
		getPersistence().clearCache(samlIdpSpConnection);
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
	public static List<SamlIdpSpConnection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SamlIdpSpConnection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SamlIdpSpConnection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SamlIdpSpConnection update(
		SamlIdpSpConnection samlIdpSpConnection) {
		return getPersistence().update(samlIdpSpConnection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SamlIdpSpConnection update(
		SamlIdpSpConnection samlIdpSpConnection, ServiceContext serviceContext) {
		return getPersistence().update(samlIdpSpConnection, serviceContext);
	}

	/**
	* Returns all the saml idp sp connections where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the saml idp sp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml idp sp connections
	* @param end the upper bound of the range of saml idp sp connections (not inclusive)
	* @return the range of matching saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the saml idp sp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml idp sp connections
	* @param end the upper bound of the range of saml idp sp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the saml idp sp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml idp sp connections
	* @param end the upper bound of the range of saml idp sp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first saml idp sp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml idp sp connection
	* @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	*/
	public static SamlIdpSpConnection findByCompanyId_First(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first saml idp sp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	*/
	public static SamlIdpSpConnection fetchByCompanyId_First(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last saml idp sp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml idp sp connection
	* @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	*/
	public static SamlIdpSpConnection findByCompanyId_Last(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last saml idp sp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	*/
	public static SamlIdpSpConnection fetchByCompanyId_Last(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the saml idp sp connections before and after the current saml idp sp connection in the ordered set where companyId = &#63;.
	*
	* @param samlIdpSpConnectionId the primary key of the current saml idp sp connection
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next saml idp sp connection
	* @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	*/
	public static SamlIdpSpConnection[] findByCompanyId_PrevAndNext(
		long samlIdpSpConnectionId, long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(samlIdpSpConnectionId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the saml idp sp connections where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of saml idp sp connections where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching saml idp sp connections
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or throws a {@link NoSuchIdpSpConnectionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param samlSpEntityId the saml sp entity ID
	* @return the matching saml idp sp connection
	* @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	*/
	public static SamlIdpSpConnection findByC_SSEI(long companyId,
		java.lang.String samlSpEntityId)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException {
		return getPersistence().findByC_SSEI(companyId, samlSpEntityId);
	}

	/**
	* Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param samlSpEntityId the saml sp entity ID
	* @return the matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	*/
	public static SamlIdpSpConnection fetchByC_SSEI(long companyId,
		java.lang.String samlSpEntityId) {
		return getPersistence().fetchByC_SSEI(companyId, samlSpEntityId);
	}

	/**
	* Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param samlSpEntityId the saml sp entity ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	*/
	public static SamlIdpSpConnection fetchByC_SSEI(long companyId,
		java.lang.String samlSpEntityId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_SSEI(companyId, samlSpEntityId, retrieveFromCache);
	}

	/**
	* Removes the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param samlSpEntityId the saml sp entity ID
	* @return the saml idp sp connection that was removed
	*/
	public static SamlIdpSpConnection removeByC_SSEI(long companyId,
		java.lang.String samlSpEntityId)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException {
		return getPersistence().removeByC_SSEI(companyId, samlSpEntityId);
	}

	/**
	* Returns the number of saml idp sp connections where companyId = &#63; and samlSpEntityId = &#63;.
	*
	* @param companyId the company ID
	* @param samlSpEntityId the saml sp entity ID
	* @return the number of matching saml idp sp connections
	*/
	public static int countByC_SSEI(long companyId,
		java.lang.String samlSpEntityId) {
		return getPersistence().countByC_SSEI(companyId, samlSpEntityId);
	}

	/**
	* Caches the saml idp sp connection in the entity cache if it is enabled.
	*
	* @param samlIdpSpConnection the saml idp sp connection
	*/
	public static void cacheResult(SamlIdpSpConnection samlIdpSpConnection) {
		getPersistence().cacheResult(samlIdpSpConnection);
	}

	/**
	* Caches the saml idp sp connections in the entity cache if it is enabled.
	*
	* @param samlIdpSpConnections the saml idp sp connections
	*/
	public static void cacheResult(
		List<SamlIdpSpConnection> samlIdpSpConnections) {
		getPersistence().cacheResult(samlIdpSpConnections);
	}

	/**
	* Creates a new saml idp sp connection with the primary key. Does not add the saml idp sp connection to the database.
	*
	* @param samlIdpSpConnectionId the primary key for the new saml idp sp connection
	* @return the new saml idp sp connection
	*/
	public static SamlIdpSpConnection create(long samlIdpSpConnectionId) {
		return getPersistence().create(samlIdpSpConnectionId);
	}

	/**
	* Removes the saml idp sp connection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	* @return the saml idp sp connection that was removed
	* @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	*/
	public static SamlIdpSpConnection remove(long samlIdpSpConnectionId)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException {
		return getPersistence().remove(samlIdpSpConnectionId);
	}

	public static SamlIdpSpConnection updateImpl(
		SamlIdpSpConnection samlIdpSpConnection) {
		return getPersistence().updateImpl(samlIdpSpConnection);
	}

	/**
	* Returns the saml idp sp connection with the primary key or throws a {@link NoSuchIdpSpConnectionException} if it could not be found.
	*
	* @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	* @return the saml idp sp connection
	* @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	*/
	public static SamlIdpSpConnection findByPrimaryKey(
		long samlIdpSpConnectionId)
		throws com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException {
		return getPersistence().findByPrimaryKey(samlIdpSpConnectionId);
	}

	/**
	* Returns the saml idp sp connection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	* @return the saml idp sp connection, or <code>null</code> if a saml idp sp connection with the primary key could not be found
	*/
	public static SamlIdpSpConnection fetchByPrimaryKey(
		long samlIdpSpConnectionId) {
		return getPersistence().fetchByPrimaryKey(samlIdpSpConnectionId);
	}

	public static java.util.Map<java.io.Serializable, SamlIdpSpConnection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the saml idp sp connections.
	*
	* @return the saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the saml idp sp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sp connections
	* @param end the upper bound of the range of saml idp sp connections (not inclusive)
	* @return the range of saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the saml idp sp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sp connections
	* @param end the upper bound of the range of saml idp sp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findAll(int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the saml idp sp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sp connections
	* @param end the upper bound of the range of saml idp sp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of saml idp sp connections
	*/
	public static List<SamlIdpSpConnection> findAll(int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the saml idp sp connections from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of saml idp sp connections.
	*
	* @return the number of saml idp sp connections
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SamlIdpSpConnectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SamlIdpSpConnectionPersistence, SamlIdpSpConnectionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SamlIdpSpConnectionPersistence.class);

		ServiceTracker<SamlIdpSpConnectionPersistence, SamlIdpSpConnectionPersistence> serviceTracker =
			new ServiceTracker<SamlIdpSpConnectionPersistence, SamlIdpSpConnectionPersistence>(bundle.getBundleContext(),
				SamlIdpSpConnectionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}