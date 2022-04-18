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

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for OAuthClientEntry. This utility wraps
 * <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientEntryService
 * @generated
 */
public class OAuthClientEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static OAuthClientEntry addOAuthClientEntry(
			long companyId, long userId, String authServerIssuer,
			String infoJSON, String requestParamsJSON)
		throws PortalException {

		return getService().addOAuthClientEntry(
			companyId, userId, authServerIssuer, infoJSON, requestParamsJSON);
	}

	public static OAuthClientEntry deleteOAuthClientEntry(
			long oAuthClientEntryId)
		throws PortalException {

		return getService().deleteOAuthClientEntry(oAuthClientEntryId);
	}

	public static OAuthClientEntry deleteOAuthClientEntry(
			long companyId, String authServerIssuer, String clientID)
		throws PortalException {

		return getService().deleteOAuthClientEntry(
			companyId, authServerIssuer, clientID);
	}

	public static OAuthClientEntry fetchOAuthClientEntry(
		long companyId, String authServerIssuer, String clientID) {

		return getService().fetchOAuthClientEntry(
			companyId, authServerIssuer, clientID);
	}

	public static List<OAuthClientEntry> getOAuthClientEntries(long companyId) {
		return getService().getOAuthClientEntries(companyId);
	}

	public static List<OAuthClientEntry> getOAuthClientEntries(
		long companyId, long userId) {

		return getService().getOAuthClientEntries(companyId, userId);
	}

	public static List<OAuthClientEntry> getOAuthClientEntries(
		long companyId, String authServerIssuer) {

		return getService().getOAuthClientEntries(companyId, authServerIssuer);
	}

	public static List<OAuthClientEntry> getOAuthClientEntriesByAuthServerType(
		long companyId, String authServerType) {

		return getService().getOAuthClientEntriesByAuthServerType(
			companyId, authServerType);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static OAuthClientEntry updateOAuthClientEntry(
			long oAuthClientEntryId, String authServerIssuer, String infoJSON,
			String requestParamsJSON)
		throws PortalException {

		return getService().updateOAuthClientEntry(
			oAuthClientEntryId, authServerIssuer, infoJSON, requestParamsJSON);
	}

	public static OAuthClientEntryService getService() {
		return _service;
	}

	private static volatile OAuthClientEntryService _service;

}