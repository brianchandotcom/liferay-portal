/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.configuration.PortalCacheConfiguration;

import java.util.Properties;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheConfiguration extends PortalCacheConfiguration {

	public EhcachePortalCacheConfiguration(
		String portalCacheName,
		Set<Properties> portalCacheListenerPropertiesSet, Class<?> keyType,
		Class<?> valueType, boolean requireSerialization) {

		super(portalCacheName, portalCacheListenerPropertiesSet);

		_keyType = keyType;
		_valueType = valueType;
		_requireSerialization = requireSerialization;
	}

	public Class<?> getKeyType() {
		return _keyType;
	}

	public Class<?> getValueType() {
		return _valueType;
	}

	public boolean isRequireSerialization() {
		return _requireSerialization;
	}

	@Override
	public PortalCacheConfiguration newPortalCacheConfiguration(
		String portalCacheName) {

		return new EhcachePortalCacheConfiguration(
			portalCacheName, getPortalCacheReplicatorPropertiesSet(), _keyType,
			_valueType, _requireSerialization);
	}

	private final Class<?> _keyType;
	private final boolean _requireSerialization;
	private final Class<?> _valueType;

}