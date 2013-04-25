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

package com.liferay.portal.security.pacl.jndi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.security.lang.DoPrivilegedFactory;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLUtil;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLInitialContextFactory implements InitialContextFactory {

	public PACLInitialContextFactory(
		InitialContextFactoryBuilder initialContextFactoryBuilder,
		Hashtable<?, ?> environment) {

		_initialContextFactoryBuilder = initialContextFactoryBuilder;

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

		InitialContextFactory initialContextFactory = null;

		if (_initialContextFactoryBuilder != null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Use " + _initialContextFactoryBuilder.getClass() +
						" to instantiate initial context factory");
			}

			initialContextFactory =
				_initialContextFactoryBuilder.createInitialContextFactory(
					environment);
		}
		else {
			if (environment == null) {
				environment = _environment;
			}

			String initialContextFactoryClassName = null;

			if (environment != null) {
				initialContextFactoryClassName = (String)environment.get(
					Context.INITIAL_CONTEXT_FACTORY);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Environment initial context factory " +
							initialContextFactoryClassName);
				}
			}

			if (initialContextFactoryClassName == null) {
				initialContextFactoryClassName = System.getProperty(
					Context.INITIAL_CONTEXT_FACTORY);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"System initial context factory " +
							initialContextFactoryClassName);
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Instantiating " + initialContextFactoryClassName);
			}

			initialContextFactory =
				(InitialContextFactory)InstanceFactory.newInstance(
					initialContextFactoryClassName);
		}

		Context context = initialContextFactory.getInitialContext(environment);

		context = new SchemeAwareContextWrapper(context);

		PACLPolicy paclPolicy = PACLUtil.getPACLPolicy();

		if (paclPolicy == null) {
			return context;
		}

		context = DoPrivilegedFactory.wrap(context);
		paclPolicy = DoPrivilegedFactory.wrap(paclPolicy);

		return new PACLContext(context, paclPolicy);
	}

	private Hashtable<?, ?> _environment;
	private InitialContextFactoryBuilder _initialContextFactoryBuilder;

	// This must not be static because of LPS-33404

	private Log _log = LogFactoryUtil.getLog(
		PACLInitialContextFactory.class.getName());

}