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

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalService;

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

	public SamlSloContext(
		SamlIdpSsoSession samlIdpSsoSession, MessageContext<?> messageContext,
		SamlIdpSpConnectionLocalService samlIdpSpConnectionLocalService,
		SamlIdpSpSessionLocalService samlIdpSpSessionLocalService,
		UserLocalService userLocalService) {

		_messageContext = messageContext;
		_samlIdpSpConnectionLocalService = samlIdpSpConnectionLocalService;
		_samlIdpSpSessionLocalService = samlIdpSpSessionLocalService;
		_userLocalService = userLocalService;

		if (samlIdpSsoSession == null) {
			return;
		}

		try {
			List<SamlIdpSpSession> samlIdpSpSessions =
				_samlIdpSpSessionLocalService.getSamlIdpSpSessions(
					samlIdpSsoSession.getSamlIdpSsoSessionId());

			for (SamlIdpSpSession samlIdpSpSession : samlIdpSpSessions) {
				_samlIdpSpSessionLocalService.deleteSamlIdpSpSession(
					samlIdpSpSession);

				String samlSpEntityId = samlIdpSpSession.getSamlSpEntityId();

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
						_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
							samlIdpSpSession.getCompanyId(), samlSpEntityId);

					name = samlIdpSpConnection.getName();
				}
				catch (NoSuchIdpSpConnectionException nsisce) {
				}

				SamlSloRequestInfo samlSloRequestInfo =
					new SamlSloRequestInfo();

				samlSloRequestInfo.setName(name);
				samlSloRequestInfo.setSamlIdpSpSession(samlIdpSpSession);

				_samlRequestInfos.put(samlSpEntityId, samlSloRequestInfo);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage(), e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}
	}

	public SamlSloContext(
		SamlIdpSsoSession samlIdpSsoSession,
		SamlIdpSpConnectionLocalService samlIdpSpConnectionLocalService,
		SamlIdpSpSessionLocalService samlIdpSpSessionLocalService,
		UserLocalService userLocalService) {

		this(
			samlIdpSsoSession, null, samlIdpSpConnectionLocalService,
			samlIdpSpSessionLocalService, userLocalService);
	}

	public MessageContext<?> getMessageContext() {
		return _messageContext;
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
			return _userLocalService.fetchUserById(_userId);
		}
		catch (Exception e) {
			return null;
		}
	}

	public long getUserId() {
		return _userId;
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
	private final SamlIdpSpConnectionLocalService
		_samlIdpSpConnectionLocalService;
	private final SamlIdpSpSessionLocalService _samlIdpSpSessionLocalService;
	private final Map<String, SamlSloRequestInfo> _samlRequestInfos =
		new ConcurrentHashMap<>();
	private String _samlSsoSessionId;
	private long _userId;
	private final UserLocalService _userLocalService;

}