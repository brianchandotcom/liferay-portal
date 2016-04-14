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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.ResourceBlockPermission;
import com.liferay.portal.kernel.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides the local service interface for ResourceBlockPermission. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionLocalServiceUtil
 * @see com.liferay.portal.service.base.ResourceBlockPermissionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourceBlockPermissionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ResourceBlockPermissionLocalService extends BaseLocalService,
	PermissionedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceBlockPermissionLocalServiceUtil} to access the resource block permission local service. Add custom service methods to {@link com.liferay.portal.service.impl.ResourceBlockPermissionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Adds the resource block permission to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ResourceBlockPermission addResourceBlockPermission(
		ResourceBlockPermission resourceBlockPermission);

	/**
	* Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	*
	* @param resourceBlockPermissionId the primary key for the new resource block permission
	* @return the new resource block permission
	*/
	public ResourceBlockPermission createResourceBlockPermission(
		long resourceBlockPermissionId);

	/**
	* Deletes the resource block permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ResourceBlockPermission deleteResourceBlockPermission(
		ResourceBlockPermission resourceBlockPermission);

	/**
	* Deletes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission that was removed
	* @throws PortalException if a resource block permission with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ResourceBlockPermission deleteResourceBlockPermission(
		long resourceBlockPermissionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ResourceBlockPermission fetchResourceBlockPermission(
		long resourceBlockPermissionId);

	/**
	* Returns the resource block permission with the primary key.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission
	* @throws PortalException if a resource block permission with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ResourceBlockPermission getResourceBlockPermission(
		long resourceBlockPermissionId) throws PortalException;

	/**
	* Updates the resource block permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermission the resource block permission
	* @return the resource block permission that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ResourceBlockPermission updateResourceBlockPermission(
		ResourceBlockPermission resourceBlockPermission);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ResourceBlockPermissionsContainer getResourceBlockPermissionsContainer(
		long resourceBlockId);

	/**
	* Returns the number of resource block permissions.
	*
	* @return the number of resource block permissions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getResourceBlockPermissionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getResourceBlockPermissionsCount(long resourceBlockId,
		long roleId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of resource block permissions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ResourceBlockPermission> getResourceBlockPermissions(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<java.lang.Long, Set<java.lang.String>> getAvailableResourceBlockPermissionActionIds(
		java.lang.String name, long primKey, List<java.lang.String> actionIds)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getAvailableResourceBlockPermissionActionIds(String, long,
	List)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<java.lang.Long, Set<java.lang.String>> getAvailableResourceBlockPermissionActionIds(
		long[] roleIds, java.lang.String name, long primKey,
		List<java.lang.String> actionIds) throws PortalException;

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void addResourceBlockPermissions(long resourceBlockId,
		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer);

	public void deleteResourceBlockPermissions(long resourceBlockId);

	public void updateResourceBlockPermission(long resourceBlockId,
		long roleId, long actionIdsLong, int operator);
}