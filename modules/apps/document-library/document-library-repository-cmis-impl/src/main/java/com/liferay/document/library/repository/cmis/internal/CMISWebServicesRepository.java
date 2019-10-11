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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.repository.cmis.CMISRepositoryHandler;
import com.liferay.document.library.repository.cmis.Session;
import com.liferay.document.library.repository.cmis.internal.constants.CMISRepositoryConstants;
import com.liferay.portal.kernel.exception.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

/**
 * @author Alexander Chow
 */
public class CMISWebServicesRepository extends CMISRepositoryHandler {

	@Override
	public Session getSession() throws PortalException {
		Map<String, String> parameters = new HashMap<>();

		parameters.put(
			SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());
		parameters.put(SessionParameter.COMPRESSION, Boolean.TRUE.toString());

		Locale locale = LocaleUtil.getSiteDefault();

		parameters.put(
			SessionParameter.LOCALE_ISO639_LANGUAGE, locale.getLanguage());
		parameters.put(
			SessionParameter.LOCALE_ISO3166_COUNTRY, locale.getCountry());

		parameters.put(
			SessionParameter.PASSWORD, PrincipalThreadLocal.getPassword());

		parameters.put(SessionParameter.USER, getLogin());

		parameters.put(
			SessionParameter.WEBSERVICES_ACL_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_ACL_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_DISCOVERY_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_DISCOVERY_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_MULTIFILING_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_MULTIFILING_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_NAVIGATION_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_NAVIGATION_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_OBJECT_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_OBJECT_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_POLICY_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_POLICY_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_RELATIONSHIP_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_REPOSITORY_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_REPOSITORY_SERVICE_PARAMETER));
		parameters.put(
			SessionParameter.WEBSERVICES_VERSIONING_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_VERSIONING_SERVICE_PARAMETER));

		CMISRepositoryUtil.checkRepository(
			getRepositoryId(), parameters, getTypeSettingsProperties(),
			CMISRepositoryConstants.CMIS_WEBSERVICES_REPOSITORY_ID_PARAMETER);

		return CMISRepositoryUtil.createSession(parameters);
	}

	protected String getTypeSettingsValue(String typeSettingsKey)
		throws InvalidRepositoryException {

		return CMISRepositoryUtil.getTypeSettingsValue(
			getTypeSettingsProperties(), typeSettingsKey);
	}

}