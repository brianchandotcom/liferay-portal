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

import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for OAuthClientAuthServer. This utility wraps
 * <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientAuthServerLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientAuthServerLocalService
 * @generated
 */
public class OAuthClientAuthServerLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientAuthServerLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static OAuthClientAuthServer addOAuthClientAuthServer(
			long userId, String discoveryEndpoint, String metadataJSON,
			String type)
		throws PortalException {

		return getService().addOAuthClientAuthServer(
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
	public static OAuthClientAuthServer addOAuthClientAuthServer(
		OAuthClientAuthServer oAuthClientAuthServer) {

		return getService().addOAuthClientAuthServer(oAuthClientAuthServer);
	}

	/**
	 * Creates a new o auth client auth server with the primary key. Does not add the o auth client auth server to the database.
	 *
	 * @param oAuthClientAuthServerId the primary key for the new o auth client auth server
	 * @return the new o auth client auth server
	 */
	public static OAuthClientAuthServer createOAuthClientAuthServer(
		long oAuthClientAuthServerId) {

		return getService().createOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static OAuthClientAuthServer deleteOAuthClientAuthServer(
			long oAuthClientAuthServerId)
		throws PortalException {

		return getService().deleteOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	public static OAuthClientAuthServer deleteOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		return getService().deleteOAuthClientAuthServer(companyId, issuer);
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
	 */
	public static OAuthClientAuthServer deleteOAuthClientAuthServer(
		OAuthClientAuthServer oAuthClientAuthServer) {

		return getService().deleteOAuthClientAuthServer(oAuthClientAuthServer);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static OAuthClientAuthServer fetchOAuthClientAuthServer(
		long oAuthClientAuthServerId) {

		return getService().fetchOAuthClientAuthServer(oAuthClientAuthServerId);
	}

	public static OAuthClientAuthServer fetchOAuthClientAuthServer(
		long companyId, String issuer) {

		return getService().fetchOAuthClientAuthServer(companyId, issuer);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId) {

		return getService().getCompanyOAuthClientAuthServers(companyId);
	}

	public static List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId, int start, int end) {

		return getService().getCompanyOAuthClientAuthServers(
			companyId, start, end);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the o auth client auth server with the primary key.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server
	 * @throws PortalException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer getOAuthClientAuthServer(
			long oAuthClientAuthServerId)
		throws PortalException {

		return getService().getOAuthClientAuthServer(oAuthClientAuthServerId);
	}

	public static OAuthClientAuthServer getOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		return getService().getOAuthClientAuthServer(companyId, issuer);
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
	public static List<OAuthClientAuthServer> getOAuthClientAuthServers(
		int start, int end) {

		return getService().getOAuthClientAuthServers(start, end);
	}

	/**
	 * Returns the number of o auth client auth servers.
	 *
	 * @return the number of o auth client auth servers
	 */
	public static int getOAuthClientAuthServersCount() {
		return getService().getOAuthClientAuthServersCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<OAuthClientAuthServer> getTypeOAuthClientAuthServers(
		long companyId, String type) {

		return getService().getTypeOAuthClientAuthServers(companyId, type);
	}

	public static List<OAuthClientAuthServer> getUserOAuthClientAuthServers(
		long userId) {

		return getService().getUserOAuthClientAuthServers(userId);
	}

	public static List<OAuthClientAuthServer> getUserOAuthClientAuthServers(
		long userId, int start, int end) {

		return getService().getUserOAuthClientAuthServers(userId, start, end);
	}

	public static OAuthClientAuthServer updateOAuthClientAuthServer(
			long oAuthClientAuthServerId, String discoveryEndpoint,
			String metadataJSON, String type)
		throws PortalException {

		return getService().updateOAuthClientAuthServer(
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
	public static OAuthClientAuthServer updateOAuthClientAuthServer(
		OAuthClientAuthServer oAuthClientAuthServer) {

		return getService().updateOAuthClientAuthServer(oAuthClientAuthServer);
	}

	public static OAuthClientAuthServerLocalService getService() {
		return _service;
	}

	private static volatile OAuthClientAuthServerLocalService _service;

}