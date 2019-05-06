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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #unregisterConfiguration(String)}
	 */
	@Deprecated
	public default void unregisterConfiguration(Configuration configuration) {
		unregisterConfiguration(configuration.getPid());
	}

	public void unregisterConfiguration(String pid);

	public void updateProperties(
		long companyId, Dictionary<String, Object> properties);

	public void updateProperties(
		long companyId, long index, Dictionary<String, Object> properties);

}