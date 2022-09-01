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
			long userId, String authRequestParametersJSON,
			String authServerWellKnownURI, String infoJSON,
			String oidcUserInfoMapperJSON, String tokenRequestParametersJSON)
		throws PortalException {

		return getService().addOAuthClientEntry(
			userId, authRequestParametersJSON, authServerWellKnownURI, infoJSON,
			oidcUserInfoMapperJSON, tokenRequestParametersJSON);
	}

	public static OAuthClientEntry deleteOAuthClientEntry(
			long oAuthClientEntryId)
		throws PortalException {

		return getService().deleteOAuthClientEntry(oAuthClientEntryId);
	}

	public static OAuthClientEntry deleteOAuthClientEntry(
			long companyId, String authServerWellKnownURI, String clientId)
		throws PortalException {

		return getService().deleteOAuthClientEntry(
			companyId, authServerWellKnownURI, clientId);
	}

	public static List<OAuthClientEntry>
			getAuthServerWellKnownURISuffixOAuthClientEntries(
				long companyId, String authServerWellKnownURISuffix)
		throws PortalException {

		return getService().getAuthServerWellKnownURISuffixOAuthClientEntries(
			companyId, authServerWellKnownURISuffix);
	}

	public static List<OAuthClientEntry> getCompanyOAuthClientEntries(
		long companyId) {

		return getService().getCompanyOAuthClientEntries(companyId);
	}

	public static List<OAuthClientEntry> getCompanyOAuthClientEntries(
		long companyId, int start, int end) {

		return getService().getCompanyOAuthClientEntries(companyId, start, end);
	}

	public static OAuthClientEntry getOAuthClientEntry(
			long companyId, String authServerWellKnownURI, String clientId)
		throws PortalException {

		return getService().getOAuthClientEntry(
			companyId, authServerWellKnownURI, clientId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<OAuthClientEntry> getUserOAuthClientEntries(
		long userId) {

		return getService().getUserOAuthClientEntries(userId);
	}

	public static List<OAuthClientEntry> getUserOAuthClientEntries(
		long userId, int start, int end) {

		return getService().getUserOAuthClientEntries(userId, start, end);
	}

	public static OAuthClientEntry updateOAuthClientEntry(
			long oAuthClientEntryId, String authRequestParametersJSON,
			String authServerWellKnownURI, String infoJSON,
			String oidcUserInfoMapperJSON, String tokenRequestParametersJSON)
		throws PortalException {

		return getService().updateOAuthClientEntry(
			oAuthClientEntryId, authRequestParametersJSON,
			authServerWellKnownURI, infoJSON, oidcUserInfoMapperJSON,
			tokenRequestParametersJSON);
	}

	public static OAuthClientEntryService getService() {
		return _service;
	}

	private static volatile OAuthClientEntryService _service;

}