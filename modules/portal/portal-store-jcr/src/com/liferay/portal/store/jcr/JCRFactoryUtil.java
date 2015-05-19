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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Young
 * @author Manuel de la Peña
 */
@Component(
	configurationPid = "com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true
)
public class JCRFactoryUtil {

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

	@Reference
	public void setJCRFactory(JCRFactory jcrFactory) {
		_jcrFactory = jcrFactory;
	}

	public void shutdown() {
		JCRFactory jcrFactory = getJCRFactory();

		jcrFactory.shutdown();
	}

	@Activate
	protected void activate(Map<String, Object> properties)
		throws RepositoryException {

		_jcrStoreConfiguration = Configurable.createConfigurable(
			JCRStoreConfiguration.class, properties);
	}

	private JCRFactory _jcrFactory;

	private volatile JCRStoreConfiguration _jcrStoreConfiguration;

	private static final ThreadLocal<Map<String, Session>> _sessions =
		new AutoResetThreadLocal<Map<String, Session>>(
			JCRFactoryUtil.class + "._sessions",
			new HashMap<String, Session>());

}