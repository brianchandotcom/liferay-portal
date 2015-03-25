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
import com.liferay.document.library.repository.cmis.internal.constants.PortalPropsValues;
import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalThreadLocal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

/**
 * @author Alexander Chow
 */
public class CMISAtomPubRepository extends CMISRepositoryHandler {

	@Override
	public Session getSession() throws PortalException {
		Map<String, String> parameters = new HashMap<>();

		parameters.put(
			SessionParameter.ATOMPUB_URL, getTypeSettingsValue(_ATOMPUB_URL));
		parameters.put(
			SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameters.put(SessionParameter.COMPRESSION, Boolean.TRUE.toString());

		Locale locale = LocaleUtil.getSiteDefault();

		parameters.put(
			SessionParameter.LOCALE_ISO3166_COUNTRY, locale.getCountry());
		parameters.put(
			SessionParameter.LOCALE_ISO639_LANGUAGE, locale.getLanguage());

		String login = getLogin();
		String password = null;

		if (Validator.isNotNull(login)) {
			password = PrincipalThreadLocal.getPassword();
		}
		else {
			login = PortalPropsValues.DL_REPOSITORY_GUEST_USERNAME;
			password = PortalPropsValues.DL_REPOSITORY_GUEST_PASSWORD;
		}

		parameters.put(SessionParameter.PASSWORD, password);
		parameters.put(SessionParameter.USER, login);

		CMISRepositoryUtil.checkRepository(
			getRepositoryId(), parameters, getTypeSettingsProperties(),
			_REPOSITORY_ID);

		return CMISRepositoryUtil.createSession(parameters);
	}

	@Deprecated
	@Override
	public String[] getSupportedConfigurations() {
		return _SUPPORTED_CONFIGURATIONS;
	}

	@Deprecated
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

	private static final String _ATOMPUB_URL = "ATOMPUB_URL";

	private static final String _CONFIGURATION_ATOMPUB = "ATOMPUB";

	private static final String _REPOSITORY_ID = "REPOSITORY_ID";

	private static final String[] _SUPPORTED_CONFIGURATIONS = {
		_CONFIGURATION_ATOMPUB
	};

	private static final String[][] _SUPPORTED_PARAMETERS = {
		{_ATOMPUB_URL, _REPOSITORY_ID}
	};

}