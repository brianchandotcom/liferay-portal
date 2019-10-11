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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

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
			SessionParameter.ATOMPUB_URL,
			getTypeSettingsValue(
				CMISRepositoryConstants.CMIS_ATOMPUB_URL_PARAMETER));
		parameters.put(
			SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameters.put(SessionParameter.COMPRESSION, Boolean.TRUE.toString());

		Locale locale = LocaleUtil.getSiteDefault();

		parameters.put(
			SessionParameter.LOCALE_ISO639_LANGUAGE, locale.getLanguage());
		parameters.put(
			SessionParameter.LOCALE_ISO3166_COUNTRY, locale.getCountry());

		String login = getLogin();
		String password = null;

		if (Validator.isNotNull(login)) {
			password = PrincipalThreadLocal.getPassword();
		}
		else {
			login = _DL_REPOSITORY_GUEST_USERNAME;
			password = _DL_REPOSITORY_GUEST_PASSWORD;
		}

		parameters.put(SessionParameter.PASSWORD, password);
		parameters.put(SessionParameter.USER, login);

		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		Class<?> clazz = getClass();

		thread.setContextClassLoader(clazz.getClassLoader());

		try {
			CMISRepositoryUtil.checkRepository(
				getRepositoryId(), parameters, getTypeSettingsProperties(),
				CMISRepositoryConstants.CMIS_ATOMPUB_REPOSITORY_ID_PARAMETER);

			return CMISRepositoryUtil.createSession(parameters);
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

	protected String getTypeSettingsValue(String typeSettingsKey)
		throws InvalidRepositoryException {

		return CMISRepositoryUtil.getTypeSettingsValue(
			getTypeSettingsProperties(), typeSettingsKey);
	}

	private static final String _DL_REPOSITORY_GUEST_PASSWORD =
		GetterUtil.getString(
			PropsUtil.get(PropsKeys.DL_REPOSITORY_GUEST_PASSWORD));

	private static final String _DL_REPOSITORY_GUEST_USERNAME =
		GetterUtil.getString(
			PropsUtil.get(PropsKeys.DL_REPOSITORY_GUEST_USERNAME));

}