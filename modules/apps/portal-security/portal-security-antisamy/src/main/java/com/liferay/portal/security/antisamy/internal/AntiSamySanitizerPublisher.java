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
import com.liferay.portal.security.antisamy.configuration.AntiSamyModelConfiguration;

import java.net.URL;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.security.antisamy.configuration.AntiSamyConfiguration",
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.portal.security.antisamy.configuration.AntiSamyModelConfiguration",
	service = ManagedServiceFactory.class
)
public class AntiSamySanitizerPublisher implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		String model = _antiSamyModelConfigurationByPid.get(pid);

		_antiSamySanitizerImpl.removeAntiSamySanitizerByModel(model);
	}

	@Override
	public String getName() {
		return AntiSamySanitizerPublisher.class.getName();
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		AntiSamyModelConfiguration antiSamyModelConfiguration =
			ConfigurableUtil.createConfigurable(
				AntiSamyModelConfiguration.class, properties);

		String model = antiSamyModelConfiguration.model();

		Bundle bundle = FrameworkUtil.getBundle(
			AntiSamyModelConfiguration.class);

		URL url = bundle.getResource(
			antiSamyModelConfiguration.configurationFileURL());

		if (url == null) {
			throw new IllegalStateException(
				"Unable to find " +
					antiSamyModelConfiguration.configurationFileURL());
		}

		_antiSamyModelConfigurationByPid.put(pid, model);
		_antiSamySanitizerImpl.addAntiSamySanitizerByModel(model, url);
	}

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

		_antiSamySanitizerImpl = new AntiSamySanitizerImpl(
			antiSamyConfiguration.blacklist(), url,
			antiSamyConfiguration.whitelist());

		_sanitizerServiceRegistration = bundleContext.registerService(
			Sanitizer.class, _antiSamySanitizerImpl, null);
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

	private final Map<String, String> _antiSamyModelConfigurationByPid =
		new ConcurrentHashMap<>();
	private AntiSamySanitizerImpl _antiSamySanitizerImpl;
	private ServiceRegistration<Sanitizer> _sanitizerServiceRegistration;

}