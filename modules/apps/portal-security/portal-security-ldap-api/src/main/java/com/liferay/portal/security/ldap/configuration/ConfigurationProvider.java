/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.configuration;

import java.util.Dictionary;
import java.util.List;

import org.osgi.service.cm.Configuration;

/**
 * @author Michael C. Han
 */
public interface ConfigurationProvider<T> {

	public boolean delete(long companyId);

	public boolean delete(long companyId, long index);

	public T getConfiguration(long companyId);

	public T getConfiguration(long companyId, long index);

	public Dictionary<String, Object> getConfigurationProperties(
		long companyId);

	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long index);

	public List<T> getConfigurations(long companyId);

	public List<T> getConfigurations(long companyId, boolean useDefault);

	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId);

	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId, boolean useDefault);

	public Class<T> getMetatype();

	public void registerConfiguration(Configuration configuration);

	public void unregisterConfiguration(String pid);

	public void updateProperties(
		long companyId, Dictionary<String, Object> properties);

	public void updateProperties(
		long companyId, long index, Dictionary<String, Object> properties);

}