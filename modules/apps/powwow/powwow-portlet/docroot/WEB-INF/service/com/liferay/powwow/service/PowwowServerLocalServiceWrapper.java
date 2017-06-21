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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PowwowServerLocalService}.
 *
 * @author Shinn Lok
 * @see PowwowServerLocalService
 * @generated
 */
@ProviderType
public class PowwowServerLocalServiceWrapper implements PowwowServerLocalService,
	ServiceWrapper<PowwowServerLocalService> {
	public PowwowServerLocalServiceWrapper(
		PowwowServerLocalService powwowServerLocalService) {
		_powwowServerLocalService = powwowServerLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _powwowServerLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _powwowServerLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _powwowServerLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowServerLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowServerLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the powwow server to the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServer the powwow server
	* @return the powwow server that was added
	*/
	@Override
	public com.liferay.powwow.model.PowwowServer addPowwowServer(
		com.liferay.powwow.model.PowwowServer powwowServer) {
		return _powwowServerLocalService.addPowwowServer(powwowServer);
	}

	@Override
	public com.liferay.powwow.model.PowwowServer addPowwowServer(long userId,
		java.lang.String name, java.lang.String providerType,
		java.lang.String url, java.lang.String apiKey, java.lang.String secret,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowServerLocalService.addPowwowServer(userId, name,
			providerType, url, apiKey, secret, serviceContext);
	}

	/**
	* Creates a new powwow server with the primary key. Does not add the powwow server to the database.
	*
	* @param powwowServerId the primary key for the new powwow server
	* @return the new powwow server
	*/
	@Override
	public com.liferay.powwow.model.PowwowServer createPowwowServer(
		long powwowServerId) {
		return _powwowServerLocalService.createPowwowServer(powwowServerId);
	}

	/**
	* Deletes the powwow server from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServer the powwow server
	* @return the powwow server that was removed
	*/
	@Override
	public com.liferay.powwow.model.PowwowServer deletePowwowServer(
		com.liferay.powwow.model.PowwowServer powwowServer) {
		return _powwowServerLocalService.deletePowwowServer(powwowServer);
	}

	/**
	* Deletes the powwow server with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server that was removed
	* @throws PortalException if a powwow server with the primary key could not be found
	*/
	@Override
	public com.liferay.powwow.model.PowwowServer deletePowwowServer(
		long powwowServerId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowServerLocalService.deletePowwowServer(powwowServerId);
	}

	@Override
	public com.liferay.powwow.model.PowwowServer fetchPowwowServer(
		long powwowServerId) {
		return _powwowServerLocalService.fetchPowwowServer(powwowServerId);
	}

	/**
	* Returns the powwow server with the primary key.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server
	* @throws PortalException if a powwow server with the primary key could not be found
	*/
	@Override
	public com.liferay.powwow.model.PowwowServer getPowwowServer(
		long powwowServerId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowServerLocalService.getPowwowServer(powwowServerId);
	}

	/**
	* Updates the powwow server in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param powwowServer the powwow server
	* @return the powwow server that was updated
	*/
	@Override
	public com.liferay.powwow.model.PowwowServer updatePowwowServer(
		com.liferay.powwow.model.PowwowServer powwowServer) {
		return _powwowServerLocalService.updatePowwowServer(powwowServer);
	}

	@Override
	public com.liferay.powwow.model.PowwowServer updatePowwowServer(
		long powwowServerId, java.lang.String name,
		java.lang.String providerType, java.lang.String url,
		java.lang.String apiKey, java.lang.String secret,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _powwowServerLocalService.updatePowwowServer(powwowServerId,
			name, providerType, url, apiKey, secret, serviceContext);
	}

	/**
	* Returns the number of powwow servers.
	*
	* @return the number of powwow servers
	*/
	@Override
	public int getPowwowServersCount() {
		return _powwowServerLocalService.getPowwowServersCount();
	}

	@Override
	public int getPowwowServersCount(java.lang.String providerType,
		boolean active) {
		return _powwowServerLocalService.getPowwowServersCount(providerType,
			active);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _powwowServerLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _powwowServerLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _powwowServerLocalService.dynamicQuery(dynamicQuery);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _powwowServerLocalService.dynamicQuery(dynamicQuery, start, end);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _powwowServerLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

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
	@Override
	public java.util.List<com.liferay.powwow.model.PowwowServer> getPowwowServers(
		int start, int end) {
		return _powwowServerLocalService.getPowwowServers(start, end);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowServer> getPowwowServers(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _powwowServerLocalService.getPowwowServers(start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.powwow.model.PowwowServer> getPowwowServers(
		java.lang.String providerType, boolean active) {
		return _powwowServerLocalService.getPowwowServers(providerType, active);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _powwowServerLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _powwowServerLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void checkPowwowServers() {
		_powwowServerLocalService.checkPowwowServers();
	}

	@Override
	public PowwowServerLocalService getWrappedService() {
		return _powwowServerLocalService;
	}

	@Override
	public void setWrappedService(
		PowwowServerLocalService powwowServerLocalService) {
		_powwowServerLocalService = powwowServerLocalService;
	}

	private PowwowServerLocalService _powwowServerLocalService;
}