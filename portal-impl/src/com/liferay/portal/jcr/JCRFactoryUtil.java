/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jcr;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author Michael Young
 */
public class JCRFactoryUtil {

	public static void closeSession(Session session) {
		if (session != null) {
			session.logout();
		}
	}

	public static Session createSession() throws RepositoryException {
		return createSession(null);
	}

	public static Session createSession(String workspaceName)
		throws RepositoryException {

		if (workspaceName == null) {
			workspaceName = JCRFactory.WORKSPACE_NAME;
		}

		if (!PropsValues.JCR_WRAP_SESSION) {
			return getJCRFactory().createSession(workspaceName);
		}

		Map<String, Session> sessionMap = _sessionMap.get();

		Session session = sessionMap.get(workspaceName);

		if (session == null) {
			Session jcrSession = getJCRFactory().createSession(workspaceName);

			JCRSessionInvocationHandler jcrSessionInvocationHandler =
				new JCRSessionInvocationHandler(jcrSession);

			Object sessionProxy = ProxyUtil.newProxyInstance(
				PortalClassLoaderUtil.getClassLoader(),
				new Class<?>[] {Map.class, Session.class},
				jcrSessionInvocationHandler);

			FinalizeManager.register(sessionProxy, jcrSessionInvocationHandler);

			session = (Session)sessionProxy;

			sessionMap.put(workspaceName, session);
		}

		return session;
	}

	public static JCRFactory getJCRFactory() {
		if (_jcrFactory == null) {
			_jcrFactory = (JCRFactory)PortalBeanLocatorUtil.locate(
				JCRFactory.class.getName());
		}

		return _jcrFactory;
	}

	public static void initialize() throws RepositoryException {
		getJCRFactory().initialize();
	}

	public static void prepare() throws RepositoryException {
		getJCRFactory().prepare();
	}

	public static void shutdown() {
		getJCRFactory().shutdown();
	}

	private static JCRFactory _jcrFactory;

	private static ThreadLocal<Map<String, Session>> _sessionMap =
		new AutoResetThreadLocal<Map<String, Session>>(
			JCRFactoryUtil.class + "._session", new HashMap<String, Session>());

}