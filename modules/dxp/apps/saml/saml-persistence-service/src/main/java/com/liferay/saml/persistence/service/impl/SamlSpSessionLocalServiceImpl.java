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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.exception.NoSuchSpSessionException;
import com.liferay.saml.persistence.model.SamlPeerBinding;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlPeerBindingLocalService;
import com.liferay.saml.persistence.service.base.SamlSpSessionLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = "model.class.name=com.liferay.saml.persistence.model.SamlSpSession",
	service = AopService.class
)
public class SamlSpSessionLocalServiceImpl
	extends SamlSpSessionLocalServiceBaseImpl {

	@Override
	public SamlSpSession addSamlSpSession(
			String assertionXml, String jSessionId, String nameIdFormat,
			String nameIdNameQualifier, String nameIdSPNameQualifier,
			String nameIdValue, String samlIdpEntityId, String samlSpSessionKey,
			String sessionIndex, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUserById(serviceContext.getUserId());

		SamlPeerBinding samlPeerBinding =
			samlPeerBindingPersistence.fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
				user.getCompanyId(), false, nameIdFormat, nameIdNameQualifier,
				nameIdValue, samlIdpEntityId, null);

		if ((samlPeerBinding != null) &&
			(user.getUserId() != samlPeerBinding.getUserId())) {

			samlPeerBinding.setDeleted(true);

			samlPeerBindingPersistence.update(samlPeerBinding);

			samlPeerBinding = null;
		}

		if (samlPeerBinding == null) {
			_deleteSamlPeerBindings(
				samlPeerBindingPersistence.findByC_U_D_SNIF_SNINQ_SPEI(
					user.getCompanyId(), user.getUserId(), false, nameIdFormat,
					nameIdNameQualifier, samlIdpEntityId));

			samlPeerBinding = _samlPeerBindingLocalService.addSamlPeerBinding(
				user.getUserId(), nameIdFormat, nameIdNameQualifier,
				nameIdSPNameQualifier, null, nameIdValue, samlIdpEntityId);
		}

		long samlSpSessionId = counterLocalService.increment(
			SamlSpSession.class.getName());

		SamlSpSession samlSpSession = samlSpSessionPersistence.create(
			samlSpSessionId);

		samlSpSession.setCompanyId(user.getCompanyId());
		samlSpSession.setUserId(user.getUserId());
		samlSpSession.setUserName(user.getFullName());
		samlSpSession.setSamlPeerBindingId(
			samlPeerBinding.getSamlPeerBindingId());
		samlSpSession.setAssertionXml(assertionXml);
		samlSpSession.setJSessionId(jSessionId);
		samlSpSession.setSamlSpSessionKey(samlSpSessionKey);
		samlSpSession.setSessionIndex(sessionIndex);
		samlSpSession.setTerminated(false);

		return samlSpSessionPersistence.update(samlSpSession);
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
	public SamlSpSession fetchSamlSpSessionBySessionIndex(
		long companyId, String sessionIndex) {

		if (Validator.isNull(sessionIndex)) {
			return null;
		}

		return samlSpSessionPersistence.fetchByC_SI(companyId, sessionIndex);
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
	public SamlSpSession getSamlSpSessionBySessionIndex(
			long companyId, String sessionIndex)
		throws PortalException {

		if (Validator.isNull(sessionIndex)) {
			throw new NoSuchSpSessionException(sessionIndex);
		}

		return samlSpSessionPersistence.findByC_SI(companyId, sessionIndex);
	}

	@Override
	public List<SamlSpSession> getSamlSpSessions(
		long companyId, String nameIdFormat, String nameIdNameQualifier,
		String nameIdSPNameQualifier, String nameIdValue,
		String samlIdpEntityId) {

		List<SamlPeerBinding> samlPeerBindings = new ArrayList<>();

		samlPeerBindings.addAll(
			samlPeerBindingPersistence.findByC_D_SNIF_SNINQ_SNIV_SPEI(
				companyId, false, nameIdFormat, nameIdNameQualifier,
				nameIdValue, samlIdpEntityId));
		samlPeerBindings.addAll(
			samlPeerBindingPersistence.findByC_D_SNIF_SNINQ_SNIV_SPEI(
				companyId, true, nameIdFormat, nameIdNameQualifier, nameIdValue,
				samlIdpEntityId));

		Stream<SamlPeerBinding> stream = samlPeerBindings.stream();

		return stream.map(
			SamlPeerBinding::getSamlPeerBindingId
		).flatMap(
			samlPeerBindingId -> {
				List<SamlSpSession> samlSpSessions =
					samlSpSessionPersistence.findBySamlPeerBindingId(
						samlPeerBindingId);

				return samlSpSessions.stream();
			}
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public SamlSpSession updateSamlSpSession(
			long samlSpSessionId, String jSessionId)
		throws PortalException {

		SamlSpSession samlSpSession = samlSpSessionPersistence.findByPrimaryKey(
			samlSpSessionId);

		samlSpSession.setModifiedDate(new Date());
		samlSpSession.setJSessionId(jSessionId);

		return samlSpSessionPersistence.update(samlSpSession);
	}

	@Override
	public SamlSpSession updateSamlSpSession(
			long samlSpSessionId, String assertionXml, String jSessionId,
			String nameIdFormat, String nameIdNameQualifier,
			String nameIdSPNameQualifier, String nameIdValue,
			String samlIdpEntityId, String samlSpSessionKey,
			String sessionIndex, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUserById(serviceContext.getUserId());

		SamlPeerBinding samlPeerBinding =
			samlPeerBindingPersistence.fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
				user.getCompanyId(), false, nameIdFormat, nameIdNameQualifier,
				nameIdValue, samlIdpEntityId, null);

		if (samlPeerBinding == null) {
			samlPeerBinding = _samlPeerBindingLocalService.addSamlPeerBinding(
				user.getUserId(), nameIdFormat, nameIdNameQualifier,
				nameIdSPNameQualifier, null, nameIdValue, samlIdpEntityId);
		}

		SamlSpSession samlSpSession = samlSpSessionPersistence.findByPrimaryKey(
			samlSpSessionId);

		samlSpSession.setCompanyId(user.getCompanyId());
		samlSpSession.setUserId(user.getUserId());
		samlSpSession.setUserName(user.getFullName());
		samlSpSession.setModifiedDate(new Date());
		samlSpSession.setSamlPeerBindingId(
			samlPeerBinding.getSamlPeerBindingId());
		samlSpSession.setAssertionXml(assertionXml);
		samlSpSession.setJSessionId(jSessionId);
		samlSpSession.setSamlSpSessionKey(samlSpSessionKey);
		samlSpSession.setSessionIndex(sessionIndex);

		return samlSpSessionPersistence.update(samlSpSession);
	}

	private void _deleteSamlPeerBindings(
		List<SamlPeerBinding> samlPeerBindings) {

		for (SamlPeerBinding samlPeerBinding : samlPeerBindings) {
			samlPeerBinding.setDeleted(true);

			samlPeerBindingPersistence.update(samlPeerBinding);
		}
	}

	@Reference
	private SamlPeerBindingLocalService _samlPeerBindingLocalService;

}