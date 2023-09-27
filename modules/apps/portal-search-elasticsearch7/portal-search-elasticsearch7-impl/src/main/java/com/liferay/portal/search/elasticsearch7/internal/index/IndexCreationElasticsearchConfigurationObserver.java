/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.index.util.IndexFactoryCompanyIdRegistryUtil;
import com.liferay.portal.search.spi.index.lifecycle.IndexLifecycleManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.nio.ByteBuffer;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = {})
public class IndexCreationElasticsearchConfigurationObserver
	implements ElasticsearchConfigurationObserver {

	@Override
	public int compareTo(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		return _elasticsearchConfigurationWrapper.compare(
			this, elasticsearchConfigurationObserver);
	}

	@Override
	public int getPriority() {
		return 4;
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		_createIndexesOnElasticsearchConfigurationChange();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_elasticsearchConfigurationWrapper.register(this);

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, IndexLifecycleManager.class);

		_createIndexesOnElasticsearchConfigurationChange();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();

		_elasticsearchConfigurationWrapper.unregister(this);
	}

	private void _createApplicationAndExternalIndexes() {
		for (Long companyId :
				IndexFactoryCompanyIdRegistryUtil.getCompanyIds()) {

			for (IndexLifecycleManager indexLifecycleManager :
					_serviceTrackerList) {

				indexLifecycleManager.createIndex(companyId);
			}
		}
	}

	private void _createIndexesOnElasticsearchConfigurationChange() {
		File dataFile = _bundleContext.getDataFile(
			"elasticsearch_configuration_state.data");

		if (dataFile.exists() && !StartupHelperUtil.isDBNew()) {
			try {
				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(FileUtil.getBytes(dataFile)));

				if (deserializer.readBoolean() ==
						_elasticsearchConfigurationWrapper.
							productionModeEnabled()) {

					return;
				}

				_createApplicationAndExternalIndexes();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to read Elasticsearch configuration",
						exception);
				}
			}
		}

		Serializer serializer = new Serializer();

		serializer.writeBoolean(
			_elasticsearchConfigurationWrapper.productionModeEnabled());

		try (OutputStream outputStream = new FileOutputStream(dataFile)) {
			serializer.writeTo(outputStream);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update Elasticsearch configuration", exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexCreationElasticsearchConfigurationObserver.class);

	private BundleContext _bundleContext;

	@Reference
	private volatile ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;

	private ServiceTrackerList<IndexLifecycleManager> _serviceTrackerList;

}