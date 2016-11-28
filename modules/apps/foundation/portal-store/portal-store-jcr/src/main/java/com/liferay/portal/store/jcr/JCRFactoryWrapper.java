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

package com.liferay.portal.store.jcr;

import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration;
import com.liferay.portal.store.jcr.jackrabbit.JCRFactoryImpl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author Michael Young
 * @author Manuel de la Peña
 */
public class JCRFactoryWrapper {

	public JCRFactoryWrapper(JCRStoreConfiguration jcrStoreConfiguration)
		throws RepositoryException {

		_jcrStoreConfiguration = jcrStoreConfiguration;

		_jcrFactory = new JCRFactoryImpl(jcrStoreConfiguration);
	}

	public void closeSession(Session session) {
		if (session != null) {
			session.logout();
		}
	}

	public Session createSession() throws RepositoryException {
		return createSession(null);
	}

	public Session createSession(String workspaceName)
		throws RepositoryException {

		if (workspaceName == null) {
			workspaceName = _jcrStoreConfiguration.workspaceName();
		}

		if (!_jcrStoreConfiguration.wrapSession()) {
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
			ClassLoaderUtil.getClassLoader(getClass()),
			new Class<?>[] {Map.class, Session.class},
			jcrSessionInvocationHandler);

		FinalizeManager.register(
			sessionProxy, jcrSessionInvocationHandler,
			FinalizeManager.PHANTOM_REFERENCE_FACTORY);

		session = (Session)sessionProxy;

		sessions.put(workspaceName, session);

		return session;
	}

	public JCRFactory getJCRFactory() {
		return _jcrFactory;
	}

	public void initialize() throws RepositoryException {
		JCRFactory jcrFactory = getJCRFactory();

		jcrFactory.initialize();
	}

	public void prepare() throws RepositoryException {
		JCRFactory jcrFactory = getJCRFactory();

		jcrFactory.prepare();
	}

	public void shutdown() {
		JCRFactory jcrFactory = getJCRFactory();

		jcrFactory.shutdown();
	}

	private static final ThreadLocal<Map<String, Session>> _sessions =
		new AutoResetThreadLocal<Map<String, Session>>(
			JCRFactoryWrapper.class + "._sessions",
			new HashMap<String, Session>());

	private final JCRFactory _jcrFactory;
	private final JCRStoreConfiguration _jcrStoreConfiguration;

}