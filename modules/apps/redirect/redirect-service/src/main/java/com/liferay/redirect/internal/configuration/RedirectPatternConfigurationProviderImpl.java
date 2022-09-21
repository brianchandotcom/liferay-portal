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

package com.liferay.redirect.internal.configuration;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;

import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	immediate = true, service = RedirectPatternConfigurationProvider.class
)
public class RedirectPatternConfigurationProviderImpl
	implements RedirectPatternConfigurationProvider {

	public Map<String, String> getRedirectionPatternsMap(long groupId)
		throws ConfigurationException {

		RedirectPatternConfiguration redirectPatternConfiguration =
			_configurationProvider.getGroupConfiguration(
				RedirectPatternConfiguration.class, groupId);

		Map<String, String> redirectionPatternMap = new LinkedHashMap<>();

		for (String patternString : redirectPatternConfiguration.patterns()) {
			String[] parts = patternString.split("\\s+", 2);

			if ((parts.length != 2) || parts[0].isEmpty() ||
				parts[1].isEmpty()) {

				continue;
			}

			redirectionPatternMap.put(parts[0], parts[1]);
		}

		return redirectionPatternMap;
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

}