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
import com.liferay.saml.persistence.exception.DuplicateSamlIdpSpSessionException;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.service.base.SamlIdpSpSessionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public class SamlIdpSpSessionLocalServiceImpl
	extends SamlIdpSpSessionLocalServiceBaseImpl {

	@Override
	public SamlIdpSpSession addSamlIdpSpSession(
			long samlIdpSsoSessionId, String samlSpEntityId,
			String nameIdFormat, String nameIdValue,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUserById(serviceContext.getUserId());
		Date now = new Date();

		SamlIdpSpSession samlIdpSpSession =
			samlIdpSpSessionPersistence.fetchBySISSI_SSEI(
				samlIdpSsoSessionId, samlSpEntityId);

		if (samlIdpSpSession != null) {
			throw new DuplicateSamlIdpSpSessionException(
				"Duplicate SAML IDP SP ssession " + samlIdpSsoSessionId +
					" for " + samlSpEntityId);
		}

		long samlIdpSpSessionId = counterLocalService.increment(
			SamlIdpSpSession.class.getName());

		samlIdpSpSession = samlIdpSpSessionPersistence.create(
			samlIdpSpSessionId);

		samlIdpSpSession.setCompanyId(serviceContext.getCompanyId());
		samlIdpSpSession.setUserId(user.getUserId());
		samlIdpSpSession.setUserName(user.getFullName());
		samlIdpSpSession.setCreateDate(now);
		samlIdpSpSession.setModifiedDate(now);
		samlIdpSpSession.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
		samlIdpSpSession.setSamlSpEntityId(samlSpEntityId);
		samlIdpSpSession.setNameIdFormat(nameIdFormat);
		samlIdpSpSession.setNameIdValue(nameIdValue);

		samlIdpSpSessionPersistence.update(samlIdpSpSession);

		return samlIdpSpSession;
	}

	@Override
	public SamlIdpSpSession getSamlIdpSpSession(
			long samlIdpSsoSessionId, String samlSpEntityId)
		throws PortalException {

		return samlIdpSpSessionPersistence.findBySISSI_SSEI(
			samlIdpSsoSessionId, samlSpEntityId);
	}

	@Override
	public List<SamlIdpSpSession> getSamlIdpSpSessions(
		long samlIdpSsoSessionId) {

		return samlIdpSpSessionPersistence.findBySamlIdpSsoSessionId(
			samlIdpSsoSessionId);
	}

	@Override
	public SamlIdpSpSession updateModifiedDate(
			long samlIdpSsoSessionId, String samlSpEntityId)
		throws PortalException {

		SamlIdpSpSession samlIdpSpSession =
			samlIdpSpSessionPersistence.findBySISSI_SSEI(
				samlIdpSsoSessionId, samlSpEntityId);

		samlIdpSpSession.setModifiedDate(new Date());

		samlIdpSpSessionPersistence.update(samlIdpSpSession);

		return samlIdpSpSession;
	}

}