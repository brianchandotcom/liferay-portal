/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import java.io.Serializable;

import org.opensaml.messaging.context.MessageContext;

/**
 * @author Mika Koivisto
 */
public class SamlSsoRequestContext implements Serializable {

	public static final int STAGE_AUTHENTICATED = 1;

	public static final int STAGE_INITIAL = 0;

	public SamlSsoRequestContext(
		String peerEntityId, String relayState,
		MessageContext<?> messageContext) {

		this(null, peerEntityId, relayState, messageContext);
	}

	public SamlSsoRequestContext(
		String authnRequestXml, String peerEntityId, String relayState,
		MessageContext<?> messageContext) {

		_authnRequestXml = authnRequestXml;
		_peerEntityId = peerEntityId;
		_relayState = relayState;
		_messageContext = messageContext;
	}

	public String getAuthnRequestXml() {
		return _authnRequestXml;
	}

	public String getPeerEntityId() {
		return _peerEntityId;
	}

	public String getRelayState() {
		return _relayState;
	}

	public MessageContext<?> getSAMLMessageContext() {
		return _messageContext;
	}

	public String getSamlSsoSessionId() {
		return _samlSsoSessionId;
	}

	public int getStage() {
		return _stage;
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

	public void setSAMLMessageContext(MessageContext<?> messageContext) {
		_messageContext = messageContext;
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
	private volatile MessageContext<?> _messageContext;
	private boolean _newSession;
	private final String _peerEntityId;
	private final String _relayState;
	private String _samlSsoSessionId;
	private int _stage;
	private long _userId;

}