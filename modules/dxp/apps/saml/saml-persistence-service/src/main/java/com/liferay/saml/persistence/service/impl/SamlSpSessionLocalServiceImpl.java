/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.exception.NoSuchSpSessionException;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.base.SamlSpSessionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public class SamlSpSessionLocalServiceImpl
	extends SamlSpSessionLocalServiceBaseImpl {

	@Override
	public SamlSpSession addSamlSpSession(
			String samlSpSessionKey, String assertionXml, String jSessionId,
			String nameIdFormat, String nameIdNameQualifier,
			String nameIdSPNameQualifier, String nameIdValue,
			String sessionIndex, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUserById(serviceContext.getUserId());
		Date now = new Date();

		long samlSpSessionId = counterLocalService.increment(
			SamlSpSession.class.getName());

		SamlSpSession samlSpSession = samlSpSessionPersistence.create(
			samlSpSessionId);

		samlSpSession.setCompanyId(serviceContext.getCompanyId());
		samlSpSession.setUserId(user.getUserId());
		samlSpSession.setUserName(user.getFullName());
		samlSpSession.setCreateDate(now);
		samlSpSession.setModifiedDate(now);
		samlSpSession.setSamlSpSessionKey(samlSpSessionKey);
		samlSpSession.setAssertionXml(assertionXml);
		samlSpSession.setJSessionId(jSessionId);
		samlSpSession.setNameIdFormat(nameIdFormat);
		samlSpSession.setNameIdNameQualifier(nameIdNameQualifier);
		samlSpSession.setNameIdSPNameQualifier(nameIdSPNameQualifier);
		samlSpSession.setNameIdValue(nameIdValue);
		samlSpSession.setSessionIndex(sessionIndex);
		samlSpSession.setTerminated(false);

		samlSpSessionPersistence.update(samlSpSession);

		return samlSpSession;
	}

	@Override
	public SamlSpSession fetchSamlSpSessionByJSessionId(String jSessionId) {
		return samlSpSessionPersistence.fetchByJSessionId(jSessionId);
	}

	@Override
	public SamlSpSession fetchSamlSpSessionBySamlSpSessionKey(
		String samlSpSessionKey) {

		return samlSpSessionPersistence.fetchBySamlSpSessionKey(
			samlSpSessionKey);
	}

	@Override
	public SamlSpSession fetchSamlSpSessionBySessionIndex(String sessionIndex) {
		if (Validator.isNull(sessionIndex)) {
			return null;
		}

		return samlSpSessionPersistence.fetchBySessionIndex(sessionIndex);
	}

	@Override
	public SamlSpSession getSamlSpSessionByJSessionId(String jSessionId)
		throws PortalException {

		return samlSpSessionPersistence.findByJSessionId(jSessionId);
	}

	@Override
	public SamlSpSession getSamlSpSessionBySamlSpSessionKey(
			String samlSpSessionKey)
		throws PortalException {

		return samlSpSessionPersistence.findBySamlSpSessionKey(
			samlSpSessionKey);
	}

	@Override
	public SamlSpSession getSamlSpSessionBySessionIndex(String sessionIndex)
		throws PortalException {

		if (Validator.isNull(sessionIndex)) {
			throw new NoSuchSpSessionException(sessionIndex);
		}

		return samlSpSessionPersistence.findBySessionIndex(sessionIndex);
	}

	@Override
	public List<SamlSpSession> getSamlSpSessions(String nameIdValue) {
		return samlSpSessionPersistence.findByNameIdValue(nameIdValue);
	}

	@Override
	public SamlSpSession updateSamlSpSession(
			long samlSpSessionId, String jSessionId)
		throws PortalException {

		SamlSpSession samlSpSession = samlSpSessionPersistence.findByPrimaryKey(
			samlSpSessionId);

		samlSpSession.setModifiedDate(new Date());
		samlSpSession.setJSessionId(jSessionId);

		samlSpSessionPersistence.update(samlSpSession);

		return samlSpSession;
	}

	@Override
	public SamlSpSession updateSamlSpSession(
			long samlSpSessionId, String samlSpSessionKey, String assertionXml,
			String jSessionId, String nameIdFormat, String nameIdNameQualifier,
			String nameIdSPNameQualifier, String nameIdValue,
			String sessionIndex, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUserById(serviceContext.getUserId());

		SamlSpSession samlSpSession = samlSpSessionPersistence.findByPrimaryKey(
			samlSpSessionId);

		samlSpSession.setCompanyId(serviceContext.getCompanyId());
		samlSpSession.setUserId(user.getUserId());
		samlSpSession.setUserName(user.getFullName());
		samlSpSession.setModifiedDate(new Date());
		samlSpSession.setSamlSpSessionKey(samlSpSessionKey);
		samlSpSession.setAssertionXml(assertionXml);
		samlSpSession.setJSessionId(jSessionId);
		samlSpSession.setNameIdFormat(nameIdFormat);
		samlSpSession.setNameIdNameQualifier(nameIdNameQualifier);
		samlSpSession.setNameIdSPNameQualifier(nameIdSPNameQualifier);
		samlSpSession.setNameIdValue(nameIdValue);
		samlSpSession.setSessionIndex(sessionIndex);

		samlSpSessionPersistence.update(samlSpSession);

		return samlSpSession;
	}

}