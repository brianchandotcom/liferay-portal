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

package com.liferay.wsrp.internal.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.TransientValue;
import com.liferay.wsrp.model.WSRPConsumer;
import com.liferay.wsrp.util.WSRPConsumerManager;
import com.liferay.wsrp.util.WSRPConsumerManagerFactory;
import com.liferay.wsrp.util.WebKeys;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import oasis.names.tc.wsrp.v2.types.RegistrationContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true)
public class WSRPConsumerManagerFactoryImpl
	implements WSRPConsumerManagerFactory {

	@Override
	public void destroyWSRPConsumerManager(String url) {
		_wsrpConsumerManagers.remove(url);

		HttpSession session = getSession();

		if (session != null) {
			session.removeAttribute(WebKeys.WSRP_CONSUMER_MANAGERS);
		}
	}

	@Override
	public HttpSession getSession() {
		return _session.get();
	}

	@Override
	public WSRPConsumerManager getWSRPConsumerManager(WSRPConsumer wsrpConsumer)
		throws Exception {

		return _getWSRPConsumerManager(
			wsrpConsumer.getUrl(), wsrpConsumer.getRegistrationContext(),
			wsrpConsumer.getForwardCookies(), wsrpConsumer.getForwardHeaders());
	}

	@Override
	public void setSession(HttpSession session) {
		_session.set(session);
	}

	@Override
	public boolean testWSRPConsumerManager(WSRPConsumer wsrpConsumer) {
		try {
			String userToken = _getUserToken();

			new WSRPConsumerManagerImpl(
				wsrpConsumer.getUrl(), wsrpConsumer.getRegistrationContext(),
				wsrpConsumer.getForwardCookies(),
				wsrpConsumer.getForwardHeaders(), userToken);

			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	private String _getUserToken() throws Exception {
		String userToken = null;

		HttpSession session = getSession();

		if (session != null) {
			Long userId = (Long)session.getAttribute(WebKeys.USER_ID);

			User user = null;

			if (userId != null) {
				user = _userLocalService.fetchUser(userId);
			}

			if (user != null) {
				userToken = user.getLogin();
			}
		}

		return userToken;
	}

	private WSRPConsumerManager _getWSRPConsumerManager(
			String url, RegistrationContext registrationContext,
			String forwardCookies, String forwardHeaders)
		throws Exception {

		HttpSession session = getSession();

		Map<String, WSRPConsumerManager> wsrpConsumerManagers = null;

		if (session != null) {
			TransientValue<Map<String, WSRPConsumerManager>> transientValue =
				(TransientValue<Map<String, WSRPConsumerManager>>)
					session.getAttribute(WebKeys.WSRP_CONSUMER_MANAGERS);

			if (transientValue == null) {
				transientValue = new TransientValue<>(
					(Map<String, WSRPConsumerManager>)
						new ConcurrentHashMap<String, WSRPConsumerManager>());

				session.setAttribute(
					WebKeys.WSRP_CONSUMER_MANAGERS, transientValue);
			}

			wsrpConsumerManagers = transientValue.getValue();
		}

		if (wsrpConsumerManagers == null) {
			wsrpConsumerManagers = _wsrpConsumerManagers;
		}

		WSRPConsumerManager wsrpConsumerManager = wsrpConsumerManagers.get(url);

		if (wsrpConsumerManager == null) {
			String userToken = _getUserToken();

			wsrpConsumerManager = new WSRPConsumerManagerImpl(
				url, registrationContext, forwardCookies, forwardHeaders,
				userToken);

			wsrpConsumerManagers.put(url, wsrpConsumerManager);
		}

		return wsrpConsumerManager;
	}

	private final CentralizedThreadLocal<HttpSession> _session =
		new CentralizedThreadLocal<>(HttpSession.class + "._session");

	@Reference
	private UserLocalService _userLocalService;

	private final Map<String, WSRPConsumerManager> _wsrpConsumerManagers =
		new ConcurrentHashMap<>();

}