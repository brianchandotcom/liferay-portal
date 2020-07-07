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
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration",
	service = ConfigurationModelListener.class
)
public class PortalCORSConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterDelete(String pid)
		throws ConfigurationModelListenerException {

		_pidToPatternsMap.remove(pid);

		Long companyId = _pidToComanyId.remove(pid);

		Set<String> companyConfigurationPids = _companyConfigurationPids.get(
			companyId);

		companyConfigurationPids.remove(pid);

		if (companyConfigurationPids.isEmpty()) {
			_companyConfigurationPids.remove(companyId);
		}
	}

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		Set<String> patterns = new HashSet<>();

		PortalCORSConfiguration portalCORSConfiguration =
			ConfigurableUtil.createConfigurable(
				PortalCORSConfiguration.class, properties);

		// Validate that there is no duplicated patterns in this entry itself.

		for (String pattern :
				portalCORSConfiguration.filterMappingURLPatterns()) {

			_validateDuplicates(patterns, pattern, properties);

			patterns.add(pattern);
		}

		long companyId = GetterUtil.getLong(
			properties.get("companyId"), CompanyConstants.SYSTEM);

		Set<String> companyConfigurationPids = _companyConfigurationPids.get(
			companyId);

		if (companyConfigurationPids == null) {
			companyConfigurationPids = new HashSet<>();

			companyConfigurationPids.add(pid);

			_companyConfigurationPids.put(companyId, companyConfigurationPids);

			_pidToPatternsMap.put(pid, patterns);

			_pidToComanyId.put(pid, companyId);
		}
		else if (!companyConfigurationPids.contains(pid)) {

			// If such configuration entry does not exist in this company, it
			// means a new configuration is being added. We need to validate
			// that no pattern in this newly added configuration entry matches
			// previously added patterns.

			for (String configurationPid : companyConfigurationPids) {
				_validateDuplicates(
					patterns, _pidToPatternsMap.get(configurationPid),
					properties);
			}

			companyConfigurationPids.add(pid);

			_pidToPatternsMap.put(pid, patterns);

			_pidToComanyId.put(pid, companyId);
		}
		else {

			// If such configuration entry exists in this company, it means an
			// existing configuration entry is being updated. We need to
			// validate that no pattern in this updated entry matches previously
			// added patterns in other entries.

			for (String configurationPid : companyConfigurationPids) {
				if (configurationPid.equals(pid)) {
					continue;
				}

				_validateDuplicates(
					patterns, _pidToPatternsMap.get(configurationPid),
					properties);
			}

			_pidToPatternsMap.put(pid, patterns);
		}
	}

	private void _validateDuplicates(
			Set<String> patterns1, Set<String> patterns2,
			Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		for (String pattern : patterns2) {
			_validateDuplicates(patterns1, pattern, properties);
		}
	}

	private void _validateDuplicates(
			Set<String> patterns, String pattern,
			Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		if (patterns.contains(pattern)) {
			throw new ConfigurationModelListenerException(
				"Duplicated url path patterns in configuration: " + pattern,
				PortalCORSConfiguration.class,
				PortalCORSConfigurationModelListener.class, properties);
		}
	}

	private final Map<Long, Set<String>> _companyConfigurationPids =
		Collections.synchronizedMap(new HashMap<>());
	private final Map<String, Long> _pidToComanyId =
		Collections.synchronizedMap(new HashMap<>());
	private final Map<String, Set<String>> _pidToPatternsMap =
		Collections.synchronizedMap(new HashMap<>());

}