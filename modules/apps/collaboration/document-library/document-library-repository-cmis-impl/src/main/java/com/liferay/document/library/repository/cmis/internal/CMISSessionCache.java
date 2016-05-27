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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.memory.FinalizeManager;
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
@Component(immediate = true, service = CMISSessionCache.class)
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

		return transientValue.getValue();
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
		Enumeration<String> attributeNames = httpSession.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

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