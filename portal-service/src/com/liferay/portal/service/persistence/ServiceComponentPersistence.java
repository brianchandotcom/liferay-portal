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

import com.liferay.portal.exception.NoSuchServiceComponentException;
import com.liferay.portal.model.ServiceComponent;

/**
 * The persistence interface for the service component service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.ServiceComponentPersistenceImpl
 * @see ServiceComponentUtil
 * @generated
 */
@ProviderType
public interface ServiceComponentPersistence extends BasePersistence<ServiceComponent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ServiceComponentUtil} to access the service component persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the service components where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @return the matching service components
	*/
	public java.util.List<ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace);

	/**
	* Returns a range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param buildNamespace the build namespace
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @return the range of matching service components
	*/
	public java.util.List<ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end);

	/**
	* Returns an ordered range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param buildNamespace the build namespace
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service components
	*/
	public java.util.List<ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator);

	/**
	* Returns an ordered range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param buildNamespace the build namespace
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching service components
	*/
	public java.util.List<ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service component
	* @throws NoSuchServiceComponentException if a matching service component could not be found
	*/
	public ServiceComponent findByBuildNamespace_First(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator)
		throws NoSuchServiceComponentException;

	/**
	* Returns the first service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public ServiceComponent fetchByBuildNamespace_First(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator);

	/**
	* Returns the last service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service component
	* @throws NoSuchServiceComponentException if a matching service component could not be found
	*/
	public ServiceComponent findByBuildNamespace_Last(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator)
		throws NoSuchServiceComponentException;

	/**
	* Returns the last service component in the ordered set where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public ServiceComponent fetchByBuildNamespace_Last(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator);

	/**
	* Returns the service components before and after the current service component in the ordered set where buildNamespace = &#63;.
	*
	* @param serviceComponentId the primary key of the current service component
	* @param buildNamespace the build namespace
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service component
	* @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	*/
	public ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator)
		throws NoSuchServiceComponentException;

	/**
	* Removes all the service components where buildNamespace = &#63; from the database.
	*
	* @param buildNamespace the build namespace
	*/
	public void removeByBuildNamespace(java.lang.String buildNamespace);

	/**
	* Returns the number of service components where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace
	* @return the number of matching service components
	*/
	public int countByBuildNamespace(java.lang.String buildNamespace);

	/**
	* Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or throws a {@link NoSuchServiceComponentException} if it could not be found.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the matching service component
	* @throws NoSuchServiceComponentException if a matching service component could not be found
	*/
	public ServiceComponent findByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber) throws NoSuchServiceComponentException;

	/**
	* Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public ServiceComponent fetchByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber);

	/**
	* Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching service component, or <code>null</code> if a matching service component could not be found
	*/
	public ServiceComponent fetchByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber, boolean retrieveFromCache);

	/**
	* Removes the service component where buildNamespace = &#63; and buildNumber = &#63; from the database.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the service component that was removed
	*/
	public ServiceComponent removeByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber) throws NoSuchServiceComponentException;

	/**
	* Returns the number of service components where buildNamespace = &#63; and buildNumber = &#63;.
	*
	* @param buildNamespace the build namespace
	* @param buildNumber the build number
	* @return the number of matching service components
	*/
	public int countByBNS_BNU(java.lang.String buildNamespace, long buildNumber);

	/**
	* Caches the service component in the entity cache if it is enabled.
	*
	* @param serviceComponent the service component
	*/
	public void cacheResult(ServiceComponent serviceComponent);

	/**
	* Caches the service components in the entity cache if it is enabled.
	*
	* @param serviceComponents the service components
	*/
	public void cacheResult(java.util.List<ServiceComponent> serviceComponents);

	/**
	* Creates a new service component with the primary key. Does not add the service component to the database.
	*
	* @param serviceComponentId the primary key for the new service component
	* @return the new service component
	*/
	public ServiceComponent create(long serviceComponentId);

	/**
	* Removes the service component with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceComponentId the primary key of the service component
	* @return the service component that was removed
	* @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	*/
	public ServiceComponent remove(long serviceComponentId)
		throws NoSuchServiceComponentException;

	public ServiceComponent updateImpl(ServiceComponent serviceComponent);

	/**
	* Returns the service component with the primary key or throws a {@link NoSuchServiceComponentException} if it could not be found.
	*
	* @param serviceComponentId the primary key of the service component
	* @return the service component
	* @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	*/
	public ServiceComponent findByPrimaryKey(long serviceComponentId)
		throws NoSuchServiceComponentException;

	/**
	* Returns the service component with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param serviceComponentId the primary key of the service component
	* @return the service component, or <code>null</code> if a service component with the primary key could not be found
	*/
	public ServiceComponent fetchByPrimaryKey(long serviceComponentId);

	@Override
	public java.util.Map<java.io.Serializable, ServiceComponent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the service components.
	*
	* @return the service components
	*/
	public java.util.List<ServiceComponent> findAll();

	/**
	* Returns a range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @return the range of service components
	*/
	public java.util.List<ServiceComponent> findAll(int start, int end);

	/**
	* Returns an ordered range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of service components
	*/
	public java.util.List<ServiceComponent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator);

	/**
	* Returns an ordered range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service components
	* @param end the upper bound of the range of service components (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of service components
	*/
	public java.util.List<ServiceComponent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ServiceComponent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the service components from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of service components.
	*
	* @return the number of service components
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}