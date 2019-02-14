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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the service component service. This utility wraps <code>com.liferay.portal.service.persistence.impl.ServiceComponentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentPersistence
 * @generated
 */
@ProviderType
public class ServiceComponentUtil {
	/*
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
	public static void clearCache(ServiceComponent serviceComponent) {
		getPersistence().clearCache(serviceComponent);
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
	public static Map<Serializable, ServiceComponent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ServiceComponent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ServiceComponent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ServiceComponent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ServiceComponent update(ServiceComponent serviceComponent) {
		return getPersistence().update(serviceComponent);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ServiceComponent update(ServiceComponent serviceComponent,
		ServiceContext serviceContext) {
		return getPersistence().update(serviceComponent, serviceContext);
	}

	/**
	* Returns all the service components where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @return the matching service components
	*/
	public static List<ServiceComponent> findByBuildNamespace(
		String buildNamespace) {
		return getPersistence().findByBuildNamespace(buildNamespace);
	}

	/**
	* Returns a range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param buildNamespace the build namespace
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @return the range of matching service components
	*/
	public static List<ServiceComponent> findByBuildNamespace(
		String buildNamespace, int start, int end) {
		return getPersistence().findByBuildNamespace(buildNamespace, start, end);
	}

	/**
	* Returns an ordered range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param buildNamespace the build namespace
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service components
	*/
	public static List<ServiceComponent> findByBuildNamespace(
		String buildNamespace, int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator) {
		return getPersistence()
				   .findByBuildNamespace(buildNamespace, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param buildNamespace the build namespace
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching service components
	*/
	public static List<ServiceComponent> findByBuildNamespace(
		String buildNamespace, int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByBuildNamespace(buildNamespace, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service component
	* @throws NoSuchServiceComponentException if a matching service component could not be found
	*/
	public static ServiceComponent findByBuildNamespace_First(
		String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchServiceComponentException {
		return getPersistence()
				   .findByBuildNamespace_First(buildNamespace, orderByComparator);
	}

	/**
	* Returns the first service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public static ServiceComponent fetchByBuildNamespace_First(
		String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator) {
		return getPersistence()
				   .fetchByBuildNamespace_First(buildNamespace,
			orderByComparator);
	}

	/**
	* Returns the last service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service component
	* @throws NoSuchServiceComponentException if a matching service component could not be found
	*/
	public static ServiceComponent findByBuildNamespace_Last(
		String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchServiceComponentException {
		return getPersistence()
				   .findByBuildNamespace_Last(buildNamespace, orderByComparator);
	}

	/**
	* Returns the last service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public static ServiceComponent fetchByBuildNamespace_Last(
		String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator) {
		return getPersistence()
				   .fetchByBuildNamespace_Last(buildNamespace, orderByComparator);
	}

	/**
	* Returns the service components before and after the current service component in the ordered set where buildNamespace = &#63;.
	*
	* @param serviceComponentId the primary key of the current service component
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service component
	* @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	*/
	public static ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchServiceComponentException {
		return getPersistence()
				   .findByBuildNamespace_PrevAndNext(serviceComponentId,
			buildNamespace, orderByComparator);
	}

	/**
	* Removes all the service components where buildNamespace = &#63; from the database.
	*
	* @param buildNamespace the build namespace
	*/
	public static void removeByBuildNamespace(String buildNamespace) {
		getPersistence().removeByBuildNamespace(buildNamespace);
	}

	/**
	* Returns the number of service components where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @return the number of matching service components
	*/
	public static int countByBuildNamespace(String buildNamespace) {
		return getPersistence().countByBuildNamespace(buildNamespace);
	}

	/**
	* Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or throws a <code>NoSuchServiceComponentException</code> if it could not be found.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the matching service component
	* @throws NoSuchServiceComponentException if a matching service component could not be found
	*/
	public static ServiceComponent findByBNS_BNU(String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.kernel.exception.NoSuchServiceComponentException {
		return getPersistence().findByBNS_BNU(buildNamespace, buildNumber);
	}

	/**
	* Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public static ServiceComponent fetchByBNS_BNU(String buildNamespace,
		long buildNumber) {
		return getPersistence().fetchByBNS_BNU(buildNamespace, buildNumber);
	}

	/**
	* Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public static ServiceComponent fetchByBNS_BNU(String buildNamespace,
		long buildNumber, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByBNS_BNU(buildNamespace, buildNumber,
			retrieveFromCache);
	}

	/**
	* Removes the service component where buildNamespace = &#63; and buildNumber = &#63; from the database.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the service component that was removed
	*/
	public static ServiceComponent removeByBNS_BNU(String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.kernel.exception.NoSuchServiceComponentException {
		return getPersistence().removeByBNS_BNU(buildNamespace, buildNumber);
	}

	/**
	* Returns the number of service components where buildNamespace = &#63; and buildNumber = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the number of matching service components
	*/
	public static int countByBNS_BNU(String buildNamespace, long buildNumber) {
		return getPersistence().countByBNS_BNU(buildNamespace, buildNumber);
	}

	/**
	* Caches the service component in the entity cache if it is enabled.
	*
	* @param serviceComponent the service component
	*/
	public static void cacheResult(ServiceComponent serviceComponent) {
		getPersistence().cacheResult(serviceComponent);
	}

	/**
	* Caches the service components in the entity cache if it is enabled.
	*
	* @param serviceComponents the service components
	*/
	public static void cacheResult(List<ServiceComponent> serviceComponents) {
		getPersistence().cacheResult(serviceComponents);
	}

	/**
	* Creates a new service component with the primary key. Does not add the service component to the database.
	*
	* @param serviceComponentId the primary key for the new service component
	* @return the new service component
	*/
	public static ServiceComponent create(long serviceComponentId) {
		return getPersistence().create(serviceComponentId);
	}

	/**
	* Removes the service component with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceComponentId the primary key of the service component
	* @return the service component that was removed
	* @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	*/
	public static ServiceComponent remove(long serviceComponentId)
		throws com.liferay.portal.kernel.exception.NoSuchServiceComponentException {
		return getPersistence().remove(serviceComponentId);
	}

	public static ServiceComponent updateImpl(ServiceComponent serviceComponent) {
		return getPersistence().updateImpl(serviceComponent);
	}

	/**
	* Returns the service component with the primary key or throws a <code>NoSuchServiceComponentException</code> if it could not be found.
	*
	* @param serviceComponentId the primary key of the service component
	* @return the service component
	* @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	*/
	public static ServiceComponent findByPrimaryKey(long serviceComponentId)
		throws com.liferay.portal.kernel.exception.NoSuchServiceComponentException {
		return getPersistence().findByPrimaryKey(serviceComponentId);
	}

	/**
	* Returns the service component with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param serviceComponentId the primary key of the service component
	* @return the service component, or <code>null</code> if a service component with the primary key could not be found
	*/
	public static ServiceComponent fetchByPrimaryKey(long serviceComponentId) {
		return getPersistence().fetchByPrimaryKey(serviceComponentId);
	}

	/**
	* Returns all the service components.
	*
	* @return the service components
	*/
	public static List<ServiceComponent> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @return the range of service components
	*/
	public static List<ServiceComponent> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of service components
	*/
	public static List<ServiceComponent> findAll(int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of service components
	*/
	public static List<ServiceComponent> findAll(int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the service components from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of service components.
	*
	* @return the number of service components
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ServiceComponentPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ServiceComponentPersistence)PortalBeanLocatorUtil.locate(ServiceComponentPersistence.class.getName());

			ReferenceRegistry.registerReference(ServiceComponentUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static ServiceComponentPersistence _persistence;
}