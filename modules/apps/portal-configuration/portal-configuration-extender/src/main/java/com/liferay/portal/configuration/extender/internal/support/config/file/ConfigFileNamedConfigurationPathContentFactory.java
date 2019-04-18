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

import com.liferay.portal.configuration.extender.internal.NamedConfigurationContent;
import com.liferay.portal.configuration.extender.internal.NamedConfigurationContentFactory;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = NamedConfigurationContentFactory.class)
public class ConfigFileNamedConfigurationPathContentFactory
	implements NamedConfigurationContentFactory {

	@Override
	public NamedConfigurationContent create(
		String name, InputStream inputStream) {

		return new ConfigFileNamedConfigurationContent(name, inputStream);
	}

	@Override
	public String getFilePattern() {
		return "*.config";
	}

}