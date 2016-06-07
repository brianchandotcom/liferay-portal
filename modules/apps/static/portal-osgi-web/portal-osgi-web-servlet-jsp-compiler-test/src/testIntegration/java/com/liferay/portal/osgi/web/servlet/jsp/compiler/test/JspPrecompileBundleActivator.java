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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.test;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import javax.portlet.Portlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Matthew Tambara
 */
public class JspPrecompileBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		dictionary.put("javax.portlet.display-name", "Jsp Precompile Portlet");
		dictionary.put("javax.portlet.name", JspPrecompilePortlet.PORTLET_NAME);

		_serviceRegistration = bundleContext.registerService(
			Portlet.class, new JspPrecompilePortlet(), dictionary);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceRegistration.unregister();
	}

	private ServiceRegistration<Portlet> _serviceRegistration;

}