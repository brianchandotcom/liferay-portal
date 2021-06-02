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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.saml.persistence.exception.DuplicateSamlIdpSpSessionException;
import com.liferay.saml.persistence.exception.NoSuchIdpSpSessionException;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlPeerBinding;
import com.liferay.saml.persistence.service.SamlPeerBindingLocalService;
import com.liferay.saml.persistence.service.base.SamlIdpSpSessionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 * @author Mika Koivisto
 */
@Component(
	property = "model.class.name=com.liferay.saml.persistence.model.SamlIdpSpSession",
	service = AopService.class
)
public class SamlIdpSpSessionLocalServiceImpl
	extends SamlIdpSpSessionLocalServiceBaseImpl {

	@Override
	public SamlIdpSpSession addSamlIdpSpSession(
			long samlIdpSsoSessionId, String samlSpEntityId,
			String nameIdFormat, String nameIdValue,
			ServiceContext serviceContext)
		throws PortalException {

		SamlIdpSpSession samlIdpSpSession = _fetchSamlIdpSpSession(
			samlIdpSsoSessionId, samlSpEntityId);

		if (samlIdpSpSession != null) {
			throw new DuplicateSamlIdpSpSessionException(
				StringBundler.concat(
					"Duplicate SAML IDP SP session ", samlIdpSsoSessionId,
					" for ", samlSpEntityId));
		}

		User user = userLocalService.getUserById(serviceContext.getUserId());

		SamlPeerBinding samlPeerBinding =
			samlPeerBindingPersistence.fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
				user.getCompanyId(), false, nameIdFormat, null, nameIdValue,
				samlSpEntityId, null);

		if (samlPeerBinding == null) {
			samlPeerBinding = _samlPeerBindingLocalService.addSamlPeerBinding(
				user.getUserId(), nameIdFormat, null, null, null, nameIdValue,
				samlSpEntityId);
		}

		long samlIdpSpSessionId = counterLocalService.increment(
			SamlIdpSpSession.class.getName());

		samlIdpSpSession = samlIdpSpSessionPersistence.create(
			samlIdpSpSessionId);

		samlIdpSpSession.setCompanyId(user.getCompanyId());
		samlIdpSpSession.setUserId(user.getUserId());
		samlIdpSpSession.setUserName(user.getFullName());
		samlIdpSpSession.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
		samlIdpSpSession.setSamlPeerBindingId(
			samlPeerBinding.getSamlPeerBindingId());

		return samlIdpSpSessionPersistence.update(samlIdpSpSession);
	}

	@Override
	public SamlIdpSpSession getSamlIdpSpSession(
			long samlIdpSsoSessionId, String samlSpEntityId)
		throws PortalException {

		SamlIdpSpSession samlIdpSpSession = _fetchSamlIdpSpSession(
			samlIdpSsoSessionId, samlSpEntityId);

		if (samlIdpSpSession == null) {
			throw new NoSuchIdpSpSessionException();
		}

		return samlIdpSpSession;
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

		SamlIdpSpSession samlIdpSpSession = getSamlIdpSpSession(
			samlIdpSsoSessionId, samlSpEntityId);

		samlIdpSpSession.setModifiedDate(new Date());

		return samlIdpSpSessionPersistence.update(samlIdpSpSession);
	}

	private SamlIdpSpSession _fetchSamlIdpSpSession(
		long samlIdpSsoSessionId, String samlSpEntityId) {

		List<SamlIdpSpSession> samlIdpSsoSessions =
			samlIdpSpSessionPersistence.findBySamlIdpSsoSessionId(
				samlIdpSsoSessionId);

		Stream<SamlIdpSpSession> stream = samlIdpSsoSessions.stream();

		return stream.filter(
			samlIdpSsoSession -> {
				SamlPeerBinding samlPeerBinding =
					_samlPeerBindingLocalService.fetchSamlPeerBinding(
						samlIdpSsoSession.getSamlPeerBindingId());

				return Objects.equals(
					samlSpEntityId, samlPeerBinding.getSamlPeerEntityId());
			}
		).findFirst(
		).orElse(
			null
		);
	}

	@Reference
	private SamlPeerBindingLocalService _samlPeerBindingLocalService;

}