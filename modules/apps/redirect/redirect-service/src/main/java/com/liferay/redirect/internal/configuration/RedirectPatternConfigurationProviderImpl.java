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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;
import com.liferay.redirect.provider.RedirectProvider;

import java.util.Dictionary;
import java.util.Map;
import java.util.regex.Pattern;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = RedirectPatternConfigurationProvider.class)
public class RedirectPatternConfigurationProviderImpl
	implements RedirectPatternConfigurationProvider {

	public Map<Pattern, String> getPatternStrings(long groupId) {
		return _redirectProvider.getPatternStrings(groupId);
	}

	@Override
	public void updatePatternStrings(
			long groupId, Map<String, String> patternStrings)
		throws Exception {

		Dictionary<String, Object> properties = null;

		Configuration configuration = null;

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				RedirectPatternConfiguration.class.getName() + ".scoped",
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId));

		if (configurations != null) {
			configuration = configurations[0];
		}

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				RedirectPatternConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

		if (patternStrings.isEmpty()) {
			properties.put("patternStrings", new String[0]);
		}
		else {
			String[] patternStringsArray = new String[patternStrings.size()];

			int i = 0;

			for (Map.Entry<String, String> entry : patternStrings.entrySet()) {
				patternStringsArray[i] =
					entry.getKey() + StringPool.SPACE + entry.getValue();

				i++;
			}

			properties.put("patternStrings", patternStringsArray);
		}

		configuration.update(properties);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private RedirectProvider _redirectProvider;

}