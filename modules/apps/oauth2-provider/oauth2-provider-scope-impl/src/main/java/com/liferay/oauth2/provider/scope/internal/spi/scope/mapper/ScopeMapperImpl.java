/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.spi.scope.mapper;

import com.liferay.oauth2.provider.scope.internal.configuration.ConfigurableScopeMapperConfiguration;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.scope.internal.configuration.ConfigurableScopeMapperConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = ScopeMapper.class
)
public class ScopeMapperImpl implements ScopeMapper {

	@Override
	public Set<String> map(String scope) {
		Set<String> mappings = _mappingsByScope.get(scope);

		Set<String> result = new HashSet<>();

		if (mappings != null) {
			result.addAll(mappings);
		}

		if (_passthrough || (mappings == null)) {
			result.add(scope);
		}

		return result;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_mappingsByScope.clear();

		ConfigurableScopeMapperConfiguration
			configurableScopeMapperConfiguration =
				ConfigurableUtil.createConfigurable(
					ConfigurableScopeMapperConfiguration.class, properties);

		_passthrough = configurableScopeMapperConfiguration.passthrough();

		for (String mapping : configurableScopeMapperConfiguration.mappings()) {
			String[] mappingParts = StringUtil.split(mapping, StringPool.EQUAL);

			if (mappingParts.length != 2) {
				if (_log.isWarnEnabled()) {
					_log.warn("Invalid mapping " + mapping);
				}

				continue;
			}

			String[] keys = StringUtil.split(mappingParts[0]);
			String[] values = StringUtil.split(mappingParts[1]);

			for (String key : keys) {
				Set<String> set = _mappingsByScope.computeIfAbsent(
					key, __ -> new HashSet<>());

				Collections.addAll(set, values);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScopeMapperImpl.class);

	private final Map<String, Set<String>> _mappingsByScope = new HashMap<>();
	private boolean _passthrough;

}