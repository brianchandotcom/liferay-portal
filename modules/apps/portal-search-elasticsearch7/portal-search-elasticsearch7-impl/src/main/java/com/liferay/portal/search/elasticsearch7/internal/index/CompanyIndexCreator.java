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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserver;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationObserverComparator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;

import java.util.HashSet;
import java.util.Set;

import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = CompanyIndexCreator.class)
public class CompanyIndexCreator implements ElasticsearchConfigurationObserver {

	@Override
	public int compareTo(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		return elasticsearchConfigurationObserverComparator.compare(
			this, elasticsearchConfigurationObserver);
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public void onElasticsearchConfigurationUpdate() {
		createCompanyIndexes();
	}

	public synchronized void registerCompanyId(long companyId) {
		_companyIds.add(companyId);
	}

	public synchronized void unregisterCompanyId(long companyId) {
		_companyIds.remove(companyId);
	}

	@Activate
	protected void activate() {
		elasticsearchConfigurationWrapper.register(this);

		createCompanyIndexes();
	}

	protected synchronized void createCompanyIndexes() {
		for (Long companyId : _companyIds) {
			try {
				RestHighLevelClient restHighLevelClient =
					elasticsearchConnectionManager.getRestHighLevelClient();

				indexFactory.createIndices(
					restHighLevelClient.indices(), companyId);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to reinitialize index for company " + companyId,
						exception);
				}
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		elasticsearchConfigurationWrapper.unregister(this);
	}

	@Reference
	protected ElasticsearchConfigurationObserverComparator
		elasticsearchConfigurationObserverComparator;

	@Reference
	protected volatile ElasticsearchConfigurationWrapper
		elasticsearchConfigurationWrapper;

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference(unbind = "-")
	protected IndexFactory indexFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexCreator.class);

	private final Set<Long> _companyIds = new HashSet<>();

}