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

package com.liferay.portal.remote.cors.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;
import com.liferay.portal.remote.cors.internal.configuration.manager.PortalCORSConfigurationManager;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration",
	service = ConfigurationModelListener.class
)
public class PortalCORSConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(
			String pid, Dictionary<String, Object> newProperties)
		throws ConfigurationModelListenerException {

		Set<String> pathPatternSet = new HashSet<>();
		Set<String> duplicatedPathPatternsSet = new HashSet<>();

		long companyId = GetterUtil.getLong(newProperties.get("companyId"));

		PortalCORSConfiguration portalCORSConfiguration =
			ConfigurableUtil.createConfigurable(
				PortalCORSConfiguration.class, newProperties);

		String[] pathPatterns =
			portalCORSConfiguration.filterMappingURLPatterns();

		for (String pathPattern : pathPatterns) {
			if (pathPatternSet.contains(pathPattern)) {
				throw new ConfigurationModelListenerException(
					"Duplicated url path patterns: " + pathPattern,
					PortalCORSConfiguration.class,
					PortalCORSConfigurationModelListener.class, newProperties);
			}

			pathPatternSet.add(pathPattern);
		}

		Map<String, Dictionary<String, ?>> configurationPidsProperties =
			_portalCORSConfigurationManager.getConfigurationPidsProperties();

		for (Map.Entry<String, Dictionary<String, ?>> entry :
				configurationPidsProperties.entrySet()) {

			if (StringUtil.equals(pid, entry.getKey())) {
				continue;
			}

			Dictionary<String, ?> properties = entry.getValue();

			if (companyId != GetterUtil.getLong(properties.get("companyId"))) {
				continue;
			}

			portalCORSConfiguration = ConfigurableUtil.createConfigurable(
				PortalCORSConfiguration.class, properties);

			pathPatterns = portalCORSConfiguration.filterMappingURLPatterns();

			for (String pathPattern : pathPatterns) {
				if (!pathPatternSet.add(pathPattern)) {
					duplicatedPathPatternsSet.add(pathPattern);
				}
			}
		}

		if (!duplicatedPathPatternsSet.isEmpty()) {
			throw new ConfigurationModelListenerException(
				"Duplicated url path patterns: " + duplicatedPathPatternsSet,
				PortalCORSConfiguration.class,
				PortalCORSConfigurationModelListener.class, newProperties);
		}
	}

	@Reference
	private PortalCORSConfigurationManager _portalCORSConfigurationManager;

}