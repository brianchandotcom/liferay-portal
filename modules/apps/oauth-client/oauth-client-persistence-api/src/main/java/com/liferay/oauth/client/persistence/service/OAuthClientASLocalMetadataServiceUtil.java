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

import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for OAuthClientASLocalMetadata. This utility wraps
 * <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientASLocalMetadataServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientASLocalMetadataService
 * @generated
 */
public class OAuthClientASLocalMetadataServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientASLocalMetadataServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static OAuthClientASLocalMetadata addOAuthClientASLocalMetadata(
			long userId, String metadataJSON, String wellKnownURISuffix)
		throws PortalException {

		return getService().addOAuthClientASLocalMetadata(
			userId, metadataJSON, wellKnownURISuffix);
	}

	public static OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			long oAuthClientASLocalMetadataId)
		throws PortalException {

		return getService().deleteOAuthClientASLocalMetadata(
			oAuthClientASLocalMetadataId);
	}

	public static OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			String localWellKnownURI)
		throws PortalException {

		return getService().deleteOAuthClientASLocalMetadata(localWellKnownURI);
	}

	public static List<OAuthClientASLocalMetadata>
		getCompanyOAuthClientASLocalMetadata(long companyId) {

		return getService().getCompanyOAuthClientASLocalMetadata(companyId);
	}

	public static List<OAuthClientASLocalMetadata>
		getCompanyOAuthClientASLocalMetadata(
			long companyId, int start, int end) {

		return getService().getCompanyOAuthClientASLocalMetadata(
			companyId, start, end);
	}

	public static OAuthClientASLocalMetadata getOAuthClientASLocalMetadata(
			String localWellKnownURI)
		throws PortalException {

		return getService().getOAuthClientASLocalMetadata(localWellKnownURI);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<OAuthClientASLocalMetadata>
		getUserOAuthClientASLocalMetadata(long userId) {

		return getService().getUserOAuthClientASLocalMetadata(userId);
	}

	public static List<OAuthClientASLocalMetadata>
		getUserOAuthClientASLocalMetadata(long userId, int start, int end) {

		return getService().getUserOAuthClientASLocalMetadata(
			userId, start, end);
	}

	public static OAuthClientASLocalMetadata updateOAuthClientASLocalMetadata(
			long oAuthClientASLocalMetadataId, String metadataJSON,
			String wellKnownURISuffix)
		throws PortalException {

		return getService().updateOAuthClientASLocalMetadata(
			oAuthClientASLocalMetadataId, metadataJSON, wellKnownURISuffix);
	}

	public static OAuthClientASLocalMetadataService getService() {
		return _service;
	}

	public static void setService(OAuthClientASLocalMetadataService service) {
		_service = service;
	}

	private static volatile OAuthClientASLocalMetadataService _service;

}