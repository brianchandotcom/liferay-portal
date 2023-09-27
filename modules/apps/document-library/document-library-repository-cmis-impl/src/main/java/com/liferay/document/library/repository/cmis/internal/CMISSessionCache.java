/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.TransientValue;

import java.lang.ref.Reference;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

import org.apache.chemistry.opencmis.client.api.Session;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(service = CMISSessionCache.class)
public class CMISSessionCache {

	public Session get(String key) {
		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		if (httpSession == null) {
			return null;
		}

		TransientValue<Session> transientValue =
			(TransientValue<Session>)httpSession.getAttribute(key);

		if (transientValue == null) {
			return null;
		}

		Object value = transientValue.getValue();

		if (value instanceof Session) {
			return (Session)value;
		}

		httpSession.removeAttribute(key);

		return null;
	}

	public void put(String key, Session session) {
		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		if (httpSession == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get HTTP session");
			}

			return;
		}

		httpSession.setAttribute(key, new TransientValue<>(session));

		_sessions.putIfAbsent(httpSession.getId(), httpSession);
	}

	@Deactivate
	protected void deactivate() {
		for (Map.Entry<String, HttpSession> entry : _sessions.entrySet()) {
			_clearSession(entry.getValue());
		}

		_sessions.clear();
	}

	private void _clearSession(HttpSession httpSession) {
		Enumeration<String> enumeration = httpSession.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			if (attributeName.startsWith(Session.class.getName())) {
				httpSession.removeAttribute(attributeName);

				break;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMISSessionCache.class);

	private final ConcurrentMap<String, HttpSession> _sessions =
		new ConcurrentReferenceValueHashMap<>(
			new ConcurrentHashMap<String, Reference<HttpSession>>(),
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}