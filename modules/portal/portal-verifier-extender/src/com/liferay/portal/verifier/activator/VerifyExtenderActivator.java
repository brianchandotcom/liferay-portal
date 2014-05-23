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

package com.liferay.portal.verifier.activator;

import com.liferay.portal.verifier.tracker.VerifyProcessTracker;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Miguel Pastor
 */
public class VerifyExtenderActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_verifyProcessTracker = new VerifyProcessTracker(bundleContext);

		_verifyProcessTracker.open();

		_verifyCommand = _registerConsoleCommand(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_verifyProcessTracker.close();

		_verifyCommand.unregister();

		_verifyCommand = null;
		_verifyProcessTracker = null;
	}

	private ServiceRegistration<Object> _registerConsoleCommand(
		BundleContext bundleContext) {

		Dictionary<String, Object> serviceProperties =
			new Hashtable<String, Object>();

		serviceProperties.put(
			"osgi.command.function",
			new String[] { "execute", "list" });
		serviceProperties.put("osgi.command.scope", "verifier-extender");

		return bundleContext.registerService(
			Object.class, _verifyProcessTracker, serviceProperties);
	}

	ServiceRegistration<Object> _verifyCommand;
	VerifyProcessTracker _verifyProcessTracker;

}