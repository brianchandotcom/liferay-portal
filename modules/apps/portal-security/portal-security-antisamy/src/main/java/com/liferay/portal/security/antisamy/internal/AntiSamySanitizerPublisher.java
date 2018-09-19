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

package com.liferay.portal.security.antisamy.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.security.antisamy.configuration.AntiSamyConfiguration;

import java.net.URL;

import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.security.antisamy.configuration.AntiSamyConfiguration",
	immediate = true, service = {}
)
public class AntiSamySanitizerPublisher {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		AntiSamyConfiguration antiSamyConfiguration =
			ConfigurableUtil.createConfigurable(
				AntiSamyConfiguration.class, properties);

		if (!antiSamyConfiguration.enabled()) {
			return;
		}

		Bundle bundle = bundleContext.getBundle();

		URL url = bundle.getResource(
			antiSamyConfiguration.configurationFileURL());

		if (url == null) {
			throw new IllegalStateException(
				"Unable to find " +
					antiSamyConfiguration.configurationFileURL());
		}

		Sanitizer sanitizer = new AntiSamySanitizerImpl(
			antiSamyConfiguration.blacklist(), url,
			antiSamyConfiguration.whitelist());

		_sanitizerServiceRegistration = bundleContext.registerService(
			Sanitizer.class, sanitizer, null);
	}

	@Deactivate
	protected void deactivate() {
		if (_sanitizerServiceRegistration != null) {
			_sanitizerServiceRegistration.unregister();

			_sanitizerServiceRegistration = null;
		}
	}

	@Modified
	protected void modified(
		BundleContext bundleContext, Map<String, Object> properties) {

		deactivate();

		activate(bundleContext, properties);
	}

	private ServiceRegistration<Sanitizer> _sanitizerServiceRegistration;

}