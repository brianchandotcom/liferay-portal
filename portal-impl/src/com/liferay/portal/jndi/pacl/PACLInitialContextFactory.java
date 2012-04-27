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

package com.liferay.portal.jndi.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLInitialContextFactory implements InitialContextFactory {

	public PACLInitialContextFactory(Hashtable<?, ?> environment) {
		if (environment != null) {
			_environment = new Hashtable<Object, Object>(environment);
		}
	}

	public Context getInitialContext(Hashtable<?, ?> environment)
		throws NamingException {

		try {
			return doGetInitialContext(environment);
		}
		catch (NamingException ne) {
			throw ne;
		}
		catch (Exception e) {
			NamingException ne = new NamingException();

			ne.initCause(e);

			throw ne;
		}
	}

	protected Context doGetInitialContext(Hashtable<?, ?> environment)
		throws Exception {

		if (environment == null) {
			environment = _environment;
		}

		String initialContextFactoryClassName = null;

		if (environment == null) {
			initialContextFactoryClassName = System.getProperty(
				Context.INITIAL_CONTEXT_FACTORY);
		}
		else {
			initialContextFactoryClassName = (String)environment.get(
				Context.INITIAL_CONTEXT_FACTORY);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + initialContextFactoryClassName);
		}

		InitialContextFactory initialContextFactory =
			(InitialContextFactory)InstanceFactory.newInstance(
				initialContextFactoryClassName);

		Context context = initialContextFactory.getInitialContext(environment);

		if (!PACLPolicyManager.isActive() ||
			!PortalSecurityManagerThreadLocal.isEnabled()) {

			return context;
		}

		PACLPolicy paclPolicy = PACLClassUtil.getPACLPolicyByReflection(
			_log.isDebugEnabled());

		if ((paclPolicy == null) || !paclPolicy.isActive()) {
			return context;
		}

		return new PACLContext(context, paclPolicy);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PACLInitialContextFactory.class.getName());

	private Hashtable<?, ?> _environment;

}