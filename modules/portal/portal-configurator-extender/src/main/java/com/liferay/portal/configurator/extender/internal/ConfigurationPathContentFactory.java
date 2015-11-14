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

package com.liferay.portal.configurator.extender.internal;

import com.liferay.portal.configurator.extender.ConfigurationContent;
import com.liferay.portal.configurator.extender.ConfigurationContentFactory;
import com.liferay.portal.configurator.extender.PropertiesFileConfigurationContent;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MappingEnumeration;
import com.liferay.portal.kernel.util.MappingEnumeration.Mapper;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
* @author Carlos Sierra Andrés
*/
@Component(immediate = true)
public class ConfigurationPathContentFactory
	implements ConfigurationContentFactory {

	@Override
	public List<ConfigurationContent> create(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String configurationPath = headers.get("ConfigurationPath");

		if (configurationPath == null) {
			return null;
		}

		final Enumeration<URL> entries = bundle.findEntries(
			configurationPath, "*", true);

		return ListUtil.fromEnumeration(
			new MappingEnumeration<>(
				entries, new Mapper<URL, ConfigurationContent>() {

				@Override
				public ConfigurationContent map(URL url) {
					return new PropertiesFileConfigurationContent(url);
				}
			})
		);
	}

}