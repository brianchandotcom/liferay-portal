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

package com.liferay.oauth.client.persistence.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthClientAuthServerLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientAuthServerLocalService
 * @generated
 */
public class OAuthClientAuthServerLocalServiceWrapper
	implements OAuthClientAuthServerLocalService,
			   ServiceWrapper<OAuthClientAuthServerLocalService> {

	public OAuthClientAuthServerLocalServiceWrapper() {
		this(null);
	}

	public OAuthClientAuthServerLocalServiceWrapper(
		OAuthClientAuthServerLocalService oAuthClientAuthServerLocalService) {

		_oAuthClientAuthServerLocalService = oAuthClientAuthServerLocalService;
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			addOAuthClientAuthServer(
				long userId, String discoveryEndpoint, String metadataJSON,
				String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.addOAuthClientAuthServer(
			userId, discoveryEndpoint, metadataJSON, type);
	}

	/**
	 * Adds the o auth client auth server to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthClientAuthServerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthClientAuthServer the o auth client auth server
	 * @return the o auth client auth server that was added
	 */
	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
		addOAuthClientAuthServer(
			com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
				oAuthClientAuthServer) {

		return _oAuthClientAuthServerLocalService.addOAuthClientAuthServer(
			oAuthClientAuthServer);
	}

	/**
	 * Creates a new o auth client auth server with the primary key. Does not add the o auth client auth server to the database.
	 *
	 * @param oAuthClientAuthServerId the primary key for the new o auth client auth server
	 * @return the new o auth client auth server
	 */
	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
		createOAuthClientAuthServer(long oAuthClientAuthServerId) {

		return _oAuthClientAuthServerLocalService.createOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the o auth client auth server with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthClientAuthServerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server that was removed
	 * @throws PortalException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			deleteOAuthClientAuthServer(long oAuthClientAuthServerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.deleteOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			deleteOAuthClientAuthServer(long companyId, String issuer)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.deleteOAuthClientAuthServer(
			companyId, issuer);
	}

	/**
	 * Deletes the o auth client auth server from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthClientAuthServerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthClientAuthServer the o auth client auth server
	 * @return the o auth client auth server that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			deleteOAuthClientAuthServer(
				com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
					oAuthClientAuthServer)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.deleteOAuthClientAuthServer(
			oAuthClientAuthServer);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _oAuthClientAuthServerLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _oAuthClientAuthServerLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _oAuthClientAuthServerLocalService.dynamicQuery();
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

		return _oAuthClientAuthServerLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth.client.persistence.model.impl.OAuthClientAuthServerModelImpl</code>.
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

		return _oAuthClientAuthServerLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth.client.persistence.model.impl.OAuthClientAuthServerModelImpl</code>.
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

		return _oAuthClientAuthServerLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _oAuthClientAuthServerLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _oAuthClientAuthServerLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
		fetchOAuthClientAuthServer(long oAuthClientAuthServerId) {

		return _oAuthClientAuthServerLocalService.fetchOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
		fetchOAuthClientAuthServer(long companyId, String issuer) {

		return _oAuthClientAuthServerLocalService.fetchOAuthClientAuthServer(
			companyId, issuer);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _oAuthClientAuthServerLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getCompanyOAuthClientAuthServers(long companyId) {

		return _oAuthClientAuthServerLocalService.
			getCompanyOAuthClientAuthServers(companyId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getCompanyOAuthClientAuthServers(
				long companyId, int start, int end) {

		return _oAuthClientAuthServerLocalService.
			getCompanyOAuthClientAuthServers(companyId, start, end);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _oAuthClientAuthServerLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the o auth client auth server with the primary key.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server
	 * @throws PortalException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			getOAuthClientAuthServer(long oAuthClientAuthServerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			getOAuthClientAuthServer(long companyId, String issuer)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
			companyId, issuer);
	}

	/**
	 * Returns a range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth.client.persistence.model.impl.OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of o auth client auth servers
	 */
	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getOAuthClientAuthServers(int start, int end) {

		return _oAuthClientAuthServerLocalService.getOAuthClientAuthServers(
			start, end);
	}

	/**
	 * Returns the number of o auth client auth servers.
	 *
	 * @return the number of o auth client auth servers
	 */
	@Override
	public int getOAuthClientAuthServersCount() {
		return _oAuthClientAuthServerLocalService.
			getOAuthClientAuthServersCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthClientAuthServerLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getTypeOAuthClientAuthServers(long companyId, String type) {

		return _oAuthClientAuthServerLocalService.getTypeOAuthClientAuthServers(
			companyId, type);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getUserOAuthClientAuthServers(long userId) {

		return _oAuthClientAuthServerLocalService.getUserOAuthClientAuthServers(
			userId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getUserOAuthClientAuthServers(long userId, int start, int end) {

		return _oAuthClientAuthServerLocalService.getUserOAuthClientAuthServers(
			userId, start, end);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			updateOAuthClientAuthServer(
				long oAuthClientAuthServerId, String discoveryEndpoint,
				String metadataJSON, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerLocalService.updateOAuthClientAuthServer(
			oAuthClientAuthServerId, discoveryEndpoint, metadataJSON, type);
	}

	/**
	 * Updates the o auth client auth server in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthClientAuthServerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthClientAuthServer the o auth client auth server
	 * @return the o auth client auth server that was updated
	 */
	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
		updateOAuthClientAuthServer(
			com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
				oAuthClientAuthServer) {

		return _oAuthClientAuthServerLocalService.updateOAuthClientAuthServer(
			oAuthClientAuthServer);
	}

	@Override
	public OAuthClientAuthServerLocalService getWrappedService() {
		return _oAuthClientAuthServerLocalService;
	}

	@Override
	public void setWrappedService(
		OAuthClientAuthServerLocalService oAuthClientAuthServerLocalService) {

		_oAuthClientAuthServerLocalService = oAuthClientAuthServerLocalService;
	}

	private OAuthClientAuthServerLocalService
		_oAuthClientAuthServerLocalService;

}