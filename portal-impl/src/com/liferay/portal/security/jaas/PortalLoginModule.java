/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.jaas;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalLoginModule implements LoginModule {

	public PortalLoginModule() {
		if (Validator.isNotNull(PropsValues.PORTAL_JAAS_IMPL)) {
			try {
				_loginModule = (LoginModule)InstanceFactory.newInstance(
					PropsValues.PORTAL_JAAS_IMPL);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_loginModule == null) {
			if (ServerDetector.isJBoss()) {
				_loginModule =
					new com.liferay.portal.security.jaas.ext.jboss.
						PortalLoginModule();
			}
			else if (ServerDetector.isJetty()) {
				_loginModule =
					new com.liferay.portal.security.jaas.ext.jetty.
						PortalLoginModule();
			}
			else if (ServerDetector.isJOnAS()) {
				_loginModule =
					new com.liferay.portal.security.jaas.ext.jonas.
						PortalLoginModule();
			}
			else if (ServerDetector.isResin()) {
				_loginModule =
					new com.liferay.portal.security.jaas.ext.resin.
						PortalLoginModule();
			}
			else if (ServerDetector.isTomcat()) {
				_loginModule =
					new com.liferay.portal.security.jaas.ext.tomcat.
						PortalLoginModule();
			}
			else if (ServerDetector.isWebLogic()) {
				_loginModule =
					new com.liferay.portal.security.jaas.ext.weblogic.
						PortalLoginModule();
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(_loginModule.getClass().getName());
		}
	}

	@Override
	public boolean abort() throws LoginException {
		return _loginModule.abort();
	}

	@Override
	public boolean commit() throws LoginException {
		return _loginModule.commit();
	}

	@Override
	public void initialize(
		Subject subject, CallbackHandler callbackHandler,
		Map<String, ?> sharedState, Map<String, ?> options) {

		_loginModule.initialize(subject, callbackHandler, sharedState, options);
	}

	@Override
	public boolean login() throws LoginException {
		return _loginModule.login();
	}

	@Override
	public boolean logout() throws LoginException {
		return _loginModule.logout();
	}

	private static Log _log = LogFactoryUtil.getLog(PortalLoginModule.class);

	private LoginModule _loginModule;

}