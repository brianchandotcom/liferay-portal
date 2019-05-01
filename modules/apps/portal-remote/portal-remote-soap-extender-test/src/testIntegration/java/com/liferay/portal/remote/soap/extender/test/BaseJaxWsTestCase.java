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

package com.liferay.portal.remote.soap.extender.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.concurrent.Callable;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.After;
import org.junit.Before;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Preston Crary
 */
public abstract class BaseJaxWsTestCase {

	@Before
	public void setUp() throws Exception {
		_bundleActivator = getBundleActivator();

		Bundle bundle = FrameworkUtil.getBundle(BaseJaxWsTestCase.class);

		_bundleContext = bundle.getBundleContext();

		_bundleActivator.start(_bundleContext);
	}

	@After
	public void tearDown() throws Exception {
		_bundleActivator.stop(_bundleContext);
	}

	public static class GreetingCallable implements Callable<String> {

		public GreetingCallable(String spec) {
			_spec = spec;
		}

		@Override
		public String call() throws Exception {
			URL url = new URL(_spec);

			QName qName = new QName(
				"http://test.extender.soap.remote.portal.liferay.com/",
				"GreeterImplService");

			Service service = Service.create(url, qName);

			Greeter greeter = service.getPort(Greeter.class);

			return greeter.greet();
		}

		private final String _spec;

	}

	protected abstract BundleActivator getBundleActivator();

	protected String getGreeting(String spec) throws Exception {
		ProtectionDomain protectionDomain =
			GreetingCallable.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URLClassLoader urlClassLoader = new URLClassLoader(
			new URL[] {codeSource.getLocation()}, null);

		Class<?> clazz = urlClassLoader.loadClass(
			GreetingCallable.class.getName());

		Constructor<?> constructor = clazz.getConstructor(String.class);

		Method method = clazz.getMethod("call");

		return (String)method.invoke(constructor.newInstance(spec));
	}

	private BundleActivator _bundleActivator;
	private BundleContext _bundleContext;

}