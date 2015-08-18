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

package com.liferay.dynamic.data.mapping.form.evaluator.internal;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 */
@Component(immediate = true)
public class DDMFormEvaluatorConfigurationComponent {

	@Activate
	public void activate() throws IOException {
		_cxfConfiguration = _configurationAdmin.createFactoryConfiguration(
			"com.liferay.portal.cxf.common.configuration." +
				"CXFEndpointPublisherConfiguration",
			null);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("contextPath", "/ddm-evaluator");

		_cxfConfiguration.update(properties);

		_restConfiguration = _configurationAdmin.createFactoryConfiguration(
			"com.liferay.portal.rest.extender.configuration." +
				"RestExtenderConfiguration",
			null);

		properties = new Hashtable<>();

		properties.put("contextPaths", new String[] {"/ddm-evaluator"});
		properties.put(
			"jaxRsApplicationFilterStrings",
			new String[] {"(ddm.form.evaluator=true)"});
		properties.put(
			"jaxRsProviderFilterStrings",
			new String[] {"(&(ddm.form.evaluator=true)(jaxrs.provider=true))"}
		);

		_restConfiguration.update(properties);
	}

	@Deactivate
	public void deactivate() throws IOException {
		if (_restConfiguration != null) {
			_restConfiguration.delete();
		}

		if (_cxfConfiguration != null) {
			_cxfConfiguration.delete();
		}
	}

	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		_configurationAdmin = configurationAdmin;
	}

	private ConfigurationAdmin _configurationAdmin;
	private Configuration _cxfConfiguration;
	private Configuration _restConfiguration;

}