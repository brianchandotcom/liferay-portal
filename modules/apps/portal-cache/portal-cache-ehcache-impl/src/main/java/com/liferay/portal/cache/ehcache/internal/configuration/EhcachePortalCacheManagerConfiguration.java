/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.configuration;

import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.internal.EhcacheExpiryPolicy;

import java.util.Set;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.FluentCacheConfigurationBuilder;
import org.ehcache.expiry.ExpiryPolicy;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheManagerConfiguration
	extends PortalCacheManagerConfiguration {

	public EhcachePortalCacheManagerConfiguration(
		CacheConfiguration<?, ?> defaultCacheConfiguration,
		PortalCacheConfiguration defaultPortalCacheConfiguration,
		Set<PortalCacheConfiguration> portalCacheConfigurations) {

		super(defaultPortalCacheConfiguration, portalCacheConfigurations);

		_defaultCacheConfiguration = defaultCacheConfiguration;
	}

	public CacheConfiguration<?, ?> getDefaultCacheConfiguration() {
		return _defaultCacheConfiguration;
	}

	public FluentCacheConfigurationBuilder<?, ?, ?> newBuilder() {
		if (_defaultCacheConfiguration == null) {
			return null;
		}

		FluentCacheConfigurationBuilder<?, ?, ?>
			fluentCacheConfigurationBuilder =
				_defaultCacheConfiguration.derive();

		return fluentCacheConfigurationBuilder.withExpiry(
			new EhcacheExpiryPolicy(
				(ExpiryPolicy<Object, Object>)
					_defaultCacheConfiguration.getExpiryPolicy()));
	}

	public void setDefaultCacheConfiguration(
		CacheConfiguration<?, ?> defaultCacheConfiguration) {

		_defaultCacheConfiguration = defaultCacheConfiguration;
	}

	private CacheConfiguration<?, ?> _defaultCacheConfiguration;

}