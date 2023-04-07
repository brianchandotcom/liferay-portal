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

package com.liferay.wiki.engine.creole.internal.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.wiki.configuration.WikiGroupServiceConfiguration",
	service = {}
)
public class WikiEngineCreoleComponentProvider {

	public static WikiEngineCreoleComponentProvider
		getWikiEngineCreoleComponentProvider() {

		return _wikiEngineCreoleComponentProvider;
	}

	public WikiGroupServiceConfiguration getWikiGroupServiceConfiguration() {
		return _wikiGroupServiceConfiguration;
	}

	public void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_wikiGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			WikiGroupServiceConfiguration.class, properties);

		_wikiEngineCreoleComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_wikiEngineCreoleComponentProvider = null;
	}

	private static WikiEngineCreoleComponentProvider
		_wikiEngineCreoleComponentProvider;

	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

}