/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.model.SamlPeerBinding;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlPeerBindingLocalServiceUtil;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;

/**
 * @author Mika Koivisto
 */
public class SamlSloContext implements Serializable {

	public SamlSloContext(SamlIdpSsoSession samlIdpSsoSession) {
		this(samlIdpSsoSession, null);
	}

	public SamlSloContext(
		SamlIdpSsoSession samlIdpSsoSession, MessageContext<?> messageContext) {

		_messageContext = messageContext;

		if (samlIdpSsoSession == null) {
			return;
		}

		try {
			List<SamlIdpSpSession> samlIdpSpSessions =
				SamlIdpSpSessionLocalServiceUtil.getSamlIdpSpSessions(
					samlIdpSsoSession.getSamlIdpSsoSessionId());

			for (SamlIdpSpSession samlIdpSpSession : samlIdpSpSessions) {
				SamlIdpSpSessionLocalServiceUtil.deleteSamlIdpSpSession(
					samlIdpSpSession);

				SamlPeerBinding samlPeerBinding =
					SamlPeerBindingLocalServiceUtil.getSamlPeerBinding(
						samlIdpSpSession.getSamlPeerBindingId());

				String samlSpEntityId = samlPeerBinding.getSamlPeerEntityId();

				if (messageContext != null) {
					SAMLPeerEntityContext samlPeerEntityContext =
						messageContext.getSubcontext(
							SAMLPeerEntityContext.class);

					if (samlSpEntityId.equals(
							samlPeerEntityContext.getEntityId())) {

						continue;
					}
				}

				String name = samlSpEntityId;

				try {
					SamlIdpSpConnection samlIdpSpConnection =
						SamlIdpSpConnectionLocalServiceUtil.
							getSamlIdpSpConnection(
								samlIdpSpSession.getCompanyId(),
								samlSpEntityId);

					name = samlIdpSpConnection.getName();
				}
				catch (NoSuchIdpSpConnectionException
							noSuchIdpSpConnectionException) {

					if (_log.isDebugEnabled()) {
						_log.debug(noSuchIdpSpConnectionException);
					}
				}

				SamlSloRequestInfo samlSloRequestInfo =
					new SamlSloRequestInfo();

				samlSloRequestInfo.setName(name);
				samlSloRequestInfo.setSamlIdpSpSession(samlIdpSpSession);
				samlSloRequestInfo.setSamlPeerBinding(samlPeerBinding);

				_samlRequestInfos.put(samlSpEntityId, samlSloRequestInfo);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	public MessageContext<?> getMessageContext() {
		return _messageContext;
	}

	public String getRelayState() {
		return _relayState;
	}

	public SamlSloRequestInfo getSamlSloRequestInfo(String entityId) {
		return _samlRequestInfos.get(entityId);
	}

	public Set<SamlSloRequestInfo> getSamlSloRequestInfos() {
		return new HashSet<>(_samlRequestInfos.values());
	}

	public Set<String> getSamlSpEntityIds() {
		return _samlRequestInfos.keySet();
	}

	public String getSamlSsoSessionId() {
		return _samlSsoSessionId;
	}

	public User getUser() {
		try {
			return UserLocalServiceUtil.fetchUserById(_userId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setRelayState(String relayState) {
		_relayState = relayState;
	}

	public void setSamlSsoSessionId(String samlSsoSessionId) {
		_samlSsoSessionId = samlSsoSessionId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public JSONObject toJSONObject() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SamlSloRequestInfo samlSloRequestInfo :
				_samlRequestInfos.values()) {

			jsonArray.put(samlSloRequestInfo.toJSONObject());
		}

		return JSONUtil.put(
			"samlSloRequestInfos", jsonArray
		).put(
			"userId", getUserId()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(SamlSloContext.class);

	private final MessageContext<?> _messageContext;
	private String _relayState;
	private final Map<String, SamlSloRequestInfo> _samlRequestInfos =
		new ConcurrentHashMap<>();
	private String _samlSsoSessionId;
	private long _userId;

}