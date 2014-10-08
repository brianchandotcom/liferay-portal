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

package com.liferay.portal.jcr;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.util.ClassLoaderUtil;
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
			JCRFactory jcrFactory = getJCRFactory();

			return jcrFactory.createSession(workspaceName);
		}

		Map<String, Session> sessions = _sessions.get();

		Session session = sessions.get(workspaceName);

		if (session != null) {
			return session;
		}

		JCRFactory jcrFactory = getJCRFactory();

		Session jcrSession = jcrFactory.createSession(workspaceName);

		JCRSessionInvocationHandler jcrSessionInvocationHandler =
			new JCRSessionInvocationHandler(jcrSession);

		Object sessionProxy = ProxyUtil.newProxyInstance(
			ClassLoaderUtil.getPortalClassLoader(),
			new Class<?>[] {Map.class, Session.class},
			jcrSessionInvocationHandler);

		FinalizeManager.register(
			sessionProxy, jcrSessionInvocationHandler,
			FinalizeManager.PHANTOM_REFERENCE_FACTORY);

		session = (Session)sessionProxy;

		sessions.put(workspaceName, session);

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
		JCRFactory jcrFactory = getJCRFactory();

		jcrFactory.initialize();
	}

	public static void prepare() throws RepositoryException {
		JCRFactory jcrFactory = getJCRFactory();

		jcrFactory.prepare();
	}

	public static void shutdown() {
		JCRFactory jcrFactory = getJCRFactory();

		jcrFactory.shutdown();
	}

	private static JCRFactory _jcrFactory;
	private static final ThreadLocal<Map<String, Session>> _sessions =
		new AutoResetThreadLocal<Map<String, Session>>(
			JCRFactoryUtil.class + "._sessions",
			new HashMap<String, Session>());

}