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

package com.liferay.powwow.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.InvokableLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.powwow.model.PowwowServer;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for PowwowServer. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Shinn Lok
 * @see PowwowServerLocalServiceUtil
 * @see com.liferay.powwow.service.base.PowwowServerLocalServiceBaseImpl
 * @see com.liferay.powwow.service.impl.PowwowServerLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PowwowServerLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PowwowServerLocalServiceUtil} to access the powwow server local service. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowServerLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
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
	* Adds the powwow server to the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServer the powwow server
	* @return the powwow server that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public PowwowServer addPowwowServer(PowwowServer powwowServer);

	public PowwowServer addPowwowServer(long userId, java.lang.String name,
		java.lang.String providerType, java.lang.String url,
		java.lang.String apiKey, java.lang.String secret,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new powwow server with the primary key. Does not add the powwow server to the database.
	*
	* @param powwowServerId the primary key for the new powwow server
	* @return the new powwow server
	*/
	public PowwowServer createPowwowServer(long powwowServerId);

	/**
	* Deletes the powwow server from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServer the powwow server
	* @return the powwow server that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public PowwowServer deletePowwowServer(PowwowServer powwowServer);

	/**
	* Deletes the powwow server with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server that was removed
	* @throws PortalException if a powwow server with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public PowwowServer deletePowwowServer(long powwowServerId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowServer fetchPowwowServer(long powwowServerId);

	/**
	* Returns the powwow server with the primary key.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server
	* @throws PortalException if a powwow server with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowServer getPowwowServer(long powwowServerId)
		throws PortalException;

	/**
	* Updates the powwow server in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param powwowServer the powwow server
	* @return the powwow server that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public PowwowServer updatePowwowServer(PowwowServer powwowServer);

	public PowwowServer updatePowwowServer(long powwowServerId,
		java.lang.String name, java.lang.String providerType,
		java.lang.String url, java.lang.String apiKey, java.lang.String secret,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the number of powwow servers.
	*
	* @return the number of powwow servers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowServersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowServersCount(java.lang.String providerType,
		boolean active);

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the powwow servers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @return the range of powwow servers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowServer> getPowwowServers(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowServer> getPowwowServers(int start, int end,
		OrderByComparator obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowServer> getPowwowServers(java.lang.String providerType,
		boolean active);

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

	public void checkPowwowServers();
}