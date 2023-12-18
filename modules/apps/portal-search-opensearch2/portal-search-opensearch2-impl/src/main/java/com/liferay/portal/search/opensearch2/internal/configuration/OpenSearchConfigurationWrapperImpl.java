/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.opensearch2.configuration.OpenSearchConfiguration;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.opensearch2.configuration.OpenSearchConfiguration",
	service = OpenSearchConfigurationWrapper.class
)
public class OpenSearchConfigurationWrapperImpl
	implements OpenSearchConfigurationWrapper {

	public String additionalIndexConfigurations() {
		return _openSearchConfiguration.additionalIndexConfigurations();
	}

	public String additionalTypeMappings() {
		return _openSearchConfiguration.additionalTypeMappings();
	}

	@Override
	public int compare(
		OpenSearchConfigurationObserver openSearchConfigurationObserver1,
		OpenSearchConfigurationObserver openSearchConfigurationObserver2) {

		if (openSearchConfigurationObserver1.getPriority() >
				openSearchConfigurationObserver2.getPriority()) {

			return 1;
		}
		else if (openSearchConfigurationObserver1.getPriority() ==
					openSearchConfigurationObserver2.getPriority()) {

			return 0;
		}

		return -1;
	}

	public int indexMaxResultWindow() {
		return _openSearchConfiguration.indexMaxResultWindow();
	}

	public String indexNamePrefix() {
		return _openSearchConfiguration.indexNamePrefix();
	}

	public String indexNumberOfReplicas() {
		return _openSearchConfiguration.indexNumberOfReplicas();
	}

	public String indexNumberOfShards() {
		return _openSearchConfiguration.indexNumberOfShards();
	}

	public boolean logExceptionsOnly() {
		return _openSearchConfiguration.logExceptionsOnly();
	}

	public String minimumRequiredNodeVersion() {
		return _openSearchConfiguration.minimumRequiredNodeVersion();
	}

	public String overrideTypeMappings() {
		return _openSearchConfiguration.overrideTypeMappings();
	}

	public void register(
		OpenSearchConfigurationObserver openSearchConfigurationObserver) {

		_openSearchConfigurationObservers.add(openSearchConfigurationObserver);
	}

	public String remoteClusterConnectionId() {
		return _openSearchConfiguration.remoteClusterConnectionId();
	}

	public boolean trackTotalHits() {
		return _openSearchConfiguration.trackTotalHits();
	}

	public void unregister(
		OpenSearchConfigurationObserver openSearchConfigurationObserver) {

		_openSearchConfigurationObservers.remove(
			openSearchConfigurationObserver);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> map) {
		_openSearchConfiguration = ConfigurableUtil.createConfigurable(
			OpenSearchConfiguration.class, map);

		_openSearchConfigurationObservers.forEach(
			OpenSearchConfigurationObserver::onOpenSearchConfigurationUpdate);
	}

	protected void setOpenSearchConfiguration(
		OpenSearchConfiguration openSearchConfiguration) {

		_openSearchConfiguration = openSearchConfiguration;
	}

	private volatile OpenSearchConfiguration _openSearchConfiguration;
	private final Set<OpenSearchConfigurationObserver>
		_openSearchConfigurationObservers = new ConcurrentSkipListSet<>();

}