/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.repository.cmis.Session;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.PrincipalThreadLocal;

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
	public Session getSession() throws PortalException, SystemException {
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put(
			SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());
		parameters.put(SessionParameter.COMPRESSION, Boolean.TRUE.toString());

		Locale locale = LocaleUtil.getDefault();

		parameters.put(
			SessionParameter.LOCALE_ISO3166_COUNTRY, locale.getCountry());
		parameters.put(
			SessionParameter.LOCALE_ISO639_LANGUAGE, locale.getLanguage());

		String password = PrincipalThreadLocal.getPassword();

		parameters.put(SessionParameter.PASSWORD, password);

		String login = getLogin();

		parameters.put(SessionParameter.USER, login);

		parameters.put(
			SessionParameter.WEBSERVICES_ACL_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_ACL_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_DISCOVERY_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_DISCOVERY_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_MULTIFILING_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_MULTIFILING_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_NAVIGATION_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_NAVIGATION_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_OBJECT_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_OBJECT_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_POLICY_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_POLICY_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_RELATIONSHIP_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_REPOSITORY_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_REPOSITORY_SERVICE));
		parameters.put(
			SessionParameter.WEBSERVICES_VERSIONING_SERVICE,
			getTypeSettingsValue(_WEBSERVICES_VERSIONING_SERVICE));

		CMISRepositoryUtil.checkRepository(
			getRepositoryId(), parameters, getTypeSettingsProperties(),
			_REPOSITORY_ID);

		return CMISRepositoryUtil.createSession(parameters);
	}

	@Override
	public String[] getSupportedConfigurations() {
		return _SUPPORTED_CONFIGURATIONS;
	}

	@Override
	public String[][] getSupportedParameters() {
		return _SUPPORTED_PARAMETERS;
	}

	protected String getTypeSettingsValue(String typeSettingsKey)
		throws InvalidRepositoryException {

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return CMISRepositoryUtil.getTypeSettingsValue(
			typeSettingsProperties, typeSettingsKey);
	}

	private static final String _CONFIGURATION_WEBSERVICES = "WEBSERVICES";

	private static final String _REPOSITORY_ID = "REPOSITORY_ID";

	private static final String[] _SUPPORTED_CONFIGURATIONS = {
		_CONFIGURATION_WEBSERVICES
	};

	private static final String[][] _SUPPORTED_PARAMETERS = new String[][] {
		{
			CMISWebServicesRepository._REPOSITORY_ID,
			CMISWebServicesRepository._WEBSERVICES_ACL_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_DISCOVERY_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_MULTIFILING_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_NAVIGATION_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_OBJECT_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_POLICY_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_RELATIONSHIP_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_REPOSITORY_SERVICE,
			CMISWebServicesRepository._WEBSERVICES_VERSIONING_SERVICE
		}
	};

	private static final String _WEBSERVICES_ACL_SERVICE =
		"WEBSERVICES_ACL_SERVICE";

	private static final String _WEBSERVICES_DISCOVERY_SERVICE =
		"WEBSERVICES_DISCOVERY_SERVICE";

	private static final String _WEBSERVICES_MULTIFILING_SERVICE =
		"WEBSERVICES_MULTIFILING_SERVICE";

	private static final String _WEBSERVICES_NAVIGATION_SERVICE =
		"WEBSERVICES_NAVIGATION_SERVICE";

	private static final String _WEBSERVICES_OBJECT_SERVICE =
		"WEBSERVICES_OBJECT_SERVICE";

	private static final String _WEBSERVICES_POLICY_SERVICE =
		"WEBSERVICES_POLICY_SERVICE";

	private static final String _WEBSERVICES_RELATIONSHIP_SERVICE =
		"WEBSERVICES_RELATIONSHIP_SERVICE";

	private static final String _WEBSERVICES_REPOSITORY_SERVICE =
		"WEBSERVICES_REPOSITORY_SERVICE";

	private static final String _WEBSERVICES_VERSIONING_SERVICE =
		"WEBSERVICES_VERSIONING_SERVICE";

}