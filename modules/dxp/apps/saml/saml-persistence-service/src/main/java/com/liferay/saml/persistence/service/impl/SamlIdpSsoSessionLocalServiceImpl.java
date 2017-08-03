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

package com.liferay.saml.persistence.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.saml.persistence.exception.DuplicateSamlIdpSsoSessionException;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.service.base.SamlIdpSsoSessionLocalServiceBaseImpl;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.util.Date;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mika Koivisto
 */
public class SamlIdpSsoSessionLocalServiceImpl
	extends SamlIdpSsoSessionLocalServiceBaseImpl {

	@Override
	public SamlIdpSsoSession addSamlIdpSsoSession(
			String samlIdpSsoSessionKey, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUserById(serviceContext.getUserId());
		Date now = new Date();

		SamlIdpSsoSession samlIdpSsoSession =
			samlIdpSsoSessionPersistence.fetchBySamlIdpSsoSessionKey(
				samlIdpSsoSessionKey);

		if (samlIdpSsoSession != null) {
			throw new DuplicateSamlIdpSsoSessionException(
				"Duplicate SAML IDP SSO session for " + samlIdpSsoSessionKey);
		}

		long samlIdpSsoSessionId = counterLocalService.increment(
			SamlIdpSsoSession.class.getName());

		samlIdpSsoSession = samlIdpSsoSessionPersistence.create(
			samlIdpSsoSessionId);

		samlIdpSsoSession.setCompanyId(serviceContext.getCompanyId());
		samlIdpSsoSession.setUserId(user.getUserId());
		samlIdpSsoSession.setUserName(user.getFullName());
		samlIdpSsoSession.setCreateDate(now);
		samlIdpSsoSession.setModifiedDate(now);
		samlIdpSsoSession.setSamlIdpSsoSessionKey(samlIdpSsoSessionKey);

		samlIdpSsoSessionPersistence.update(samlIdpSsoSession);

		return samlIdpSsoSession;
	}

	@Override
	public void deleteExpiredSamlIdpSsoSessions() {
		Date createDate = new Date();

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		createDate.setTime(
			createDate.getTime() -
				samlProviderConfiguration.sessionMaximumAge());

		samlIdpSsoSessionPersistence.removeByCreateDate(createDate);
		samlIdpSpSessionPersistence.removeByCreateDate(createDate);
	}

	@Override
	public SamlIdpSsoSession fetchSamlIdpSso(String samlIdpSsoSessionKey) {
		return samlIdpSsoSessionPersistence.fetchBySamlIdpSsoSessionKey(
			samlIdpSsoSessionKey);
	}

	@Override
	public SamlIdpSsoSession getSamlIdpSso(String samlIdpSsoSessionKey)
		throws PortalException {

		return samlIdpSsoSessionPersistence.findBySamlIdpSsoSessionKey(
			samlIdpSsoSessionKey);
	}

	@Override
	public SamlIdpSsoSession updateModifiedDate(String samlIdpSsoSessionKey)
		throws PortalException {

		SamlIdpSsoSession samlIdpSsoSession =
			samlIdpSsoSessionPersistence.findBySamlIdpSsoSessionKey(
				samlIdpSsoSessionKey);

		samlIdpSsoSession.setModifiedDate(new Date());

		samlIdpSsoSessionPersistence.update(samlIdpSsoSession);

		return samlIdpSsoSession;
	}

	@ServiceReference(type = ConfigurationAdmin.class)
	private ConfigurationAdmin _configurationAdmin;

	@ServiceReference(type = SamlProviderConfigurationHelper.class)
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}