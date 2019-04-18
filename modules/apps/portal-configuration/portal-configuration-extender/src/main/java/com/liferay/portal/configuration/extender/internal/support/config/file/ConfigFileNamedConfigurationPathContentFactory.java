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

package com.liferay.portal.configuration.extender.internal.support.config.file;

import com.liferay.portal.configuration.extender.internal.BundleStorage;
import com.liferay.portal.configuration.extender.internal.NamedConfigurationContent;
import com.liferay.portal.configuration.extender.internal.NamedConfigurationContentFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MappingEnumeration;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = NamedConfigurationContentFactory.class)
public class ConfigFileNamedConfigurationPathContentFactory
	implements NamedConfigurationContentFactory {

	@Override
	public List<NamedConfigurationContent> create(BundleStorage bundleStorage) {
		Dictionary<String, String> headers = bundleStorage.getHeaders();

		String configurationPath = headers.get("Liferay-Configuration-Path");

		Enumeration<URL> enumeration = bundleStorage.findEntries(
			configurationPath, "*.config", true);

		return ListUtil.fromEnumeration(
			new MappingEnumeration<>(
				enumeration,
				new MappingEnumeration.Mapper
					<URL, NamedConfigurationContent>() {

					@Override
					public NamedConfigurationContent map(URL url) {
						return new ConfigFileNamedConfigurationContent(url);
					}

				}));
	}

}