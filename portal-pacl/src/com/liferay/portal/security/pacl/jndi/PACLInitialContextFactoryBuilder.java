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

import java.util.Hashtable;

import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLInitialContextFactoryBuilder
	implements InitialContextFactoryBuilder {

	@Override
	public InitialContextFactory createInitialContextFactory(
		Hashtable<?, ?> environment) {

		if (_log.isDebugEnabled()) {
			_log.debug("Creating " + PACLInitialContextFactory.class.getName());
		}

		return new PACLInitialContextFactory(
			_initialContextFactoryBuilder, environment);
	}

	public void setInitialContextFactoryBuilder(
		InitialContextFactoryBuilder initialContextFactoryBuilder) {

		_initialContextFactoryBuilder = initialContextFactoryBuilder;
	}

	private InitialContextFactoryBuilder _initialContextFactoryBuilder;

	// This must not be static because of LPS-33404

	private Log _log = LogFactoryUtil.getLog(
		PACLInitialContextFactoryBuilder.class.getName());

}