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

package com.liferay.saml.opensaml.integration.internal.profile;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;

/**
 * @author Mika Koivisto
 */
public class SamlSsoRequestContext implements Serializable {

	public static final int STAGE_AUTHENTICATED = 1;

	public static final int STAGE_INITIAL = 0;

	public SamlSsoRequestContext(
		String peerEntityId, String relayState,
		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext,
		UserLocalService userLocalService) {

		this(
			null, peerEntityId, relayState, samlMessageContext,
			userLocalService);
	}

	public SamlSsoRequestContext(
		String authnRequestXml, String peerEntityId, String relayState,
		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext,
		UserLocalService userLocalService) {

		_authnRequestXml = authnRequestXml;
		_peerEntityId = peerEntityId;
		_relayState = relayState;
		_samlMessageContext = samlMessageContext;
		_userLocalService = userLocalService;
	}

	public String getAutnRequestXml() {
		return _authnRequestXml;
	}

	public String getPeerEntityId() {
		return _peerEntityId;
	}

	public String getRelayState() {
		return _relayState;
	}

	public SAMLMessageContext<AuthnRequest, Response, NameID>
		getSAMLMessageContext() {

		return _samlMessageContext;
	}

	public String getSamlSsoSessionId() {
		return _samlSsoSessionId;
	}

	public int getStage() {
		return _stage;
	}

	public User getUser() {
		try {
			return _userLocalService.fetchUserById(_userId);
		}
		catch (Exception e) {
			return null;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isNewSession() {
		return _newSession;
	}

	public void setNewSession(boolean newSession) {
		_newSession = newSession;
	}

	public void setSAMLMessageContext(
		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext) {

		_samlMessageContext = samlMessageContext;
	}

	public void setSamlSsoSessionId(String samlSsoSessionId) {
		_samlSsoSessionId = samlSsoSessionId;
	}

	public void setStage(int stage) {
		_stage = stage;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private final String _authnRequestXml;
	private boolean _newSession;
	private final String _peerEntityId;
	private final String _relayState;
	private volatile SAMLMessageContext<AuthnRequest, Response, NameID>
		_samlMessageContext;
	private String _samlSsoSessionId;
	private int _stage;
	private long _userId;
	private final UserLocalService _userLocalService;

}