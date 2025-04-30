/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.test.util.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Manuele Castro
 */
public class LDAPAuthConfigurationProviderTemporarySwapper
	implements AutoCloseable {

	public LDAPAuthConfigurationProviderTemporarySwapper(
		long companyId, boolean passwordPolicyEnabled) {

		_companyId = companyId;

		ConfigurationProvider<LDAPAuthConfiguration>
			ldapAuthConfigurationProvider = _serviceTracker.getService();

		Dictionary<String, Object> configurationProperties =
			ldapAuthConfigurationProvider.getConfigurationProperties(companyId);

		configurationProperties.put(
			"passwordPolicyEnabled", passwordPolicyEnabled);

		ldapAuthConfigurationProvider.updateProperties(
			companyId, configurationProperties);
	}

	@Override
	public void close() {
		ConfigurationProvider<LDAPAuthConfiguration>
			ldapAuthConfigurationProvider = _serviceTracker.getService();

		ldapAuthConfigurationProvider.delete(_companyId);
	}

	private static final BundleContext _bundleContext;
	private static final ServiceTracker
		<ConfigurationProvider<LDAPAuthConfiguration>,
		 ConfigurationProvider<LDAPAuthConfiguration>> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LDAPAuthConfigurationProviderTemporarySwapper.class);

		_bundleContext = bundle.getBundleContext();

		try {
			_serviceTracker = new ServiceTracker<>(
				_bundleContext,
				_bundleContext.createFilter(
					StringBundler.concat(
						"(&(factoryPid=com.liferay.portal.security.ldap.",
						"authenticator.configuration.LDAPAuthConfiguration)",
						"(objectClass=", ConfigurationProvider.class.getName(),
						"))")),
				null);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new RuntimeException(invalidSyntaxException);
		}

		_serviceTracker.open();
	}

	private final long _companyId;

}