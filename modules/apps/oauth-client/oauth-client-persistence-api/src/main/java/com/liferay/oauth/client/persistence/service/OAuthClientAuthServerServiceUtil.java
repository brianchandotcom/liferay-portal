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
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for OAuthClientAuthServer. This utility wraps
 * <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientAuthServerServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientAuthServerService
 * @generated
 */
public class OAuthClientAuthServerServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientAuthServerServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static OAuthClientAuthServer addOAuthClientAuthServer(
			long userId, String discoveryEndpoint, String metadataJSON,
			String type)
		throws PortalException {

		return getService().addOAuthClientAuthServer(
			userId, discoveryEndpoint, metadataJSON, type);
	}

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

	public static List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId) {

		return getService().getCompanyOAuthClientAuthServers(companyId);
	}

	public static List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId, int start, int end) {

		return getService().getCompanyOAuthClientAuthServers(
			companyId, start, end);
	}

	public static OAuthClientAuthServer getOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		return getService().getOAuthClientAuthServer(companyId, issuer);
	}

	public static List<OAuthClientAuthServer> getOAuthClientAuthServers(
		long companyId, String type) {

		return getService().getOAuthClientAuthServers(companyId, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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

	public static OAuthClientAuthServerService getService() {
		return _service;
	}

	private static volatile OAuthClientAuthServerService _service;

}