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

package com.liferay.portal.search.internal.reindexer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.configuration.ReindexerConfiguration;
import com.liferay.portal.search.reindexer.Reindexer;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.ReindexerConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = Reindexer.class
)
public class ReindexerImpl implements Reindexer {

	@Override
	public void reindex(long companyId, String className, long... classPKs) {
		Reindex reindex = getReindex(companyId);

		reindex.reindex(className, classPKs);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_reindexerConfiguration = ConfigurableUtil.createConfigurable(
			ReindexerConfiguration.class, properties);

		_executorService = Executors.newSingleThreadExecutor(this::getThread);
		_reindexRequestsHolder = new ReindexRequestsHolder();
	}

	@Deactivate
	protected void deactivate() {
		_executorService.shutdown();

		_executorService = null;
		_reindexRequestsHolder = null;
	}

	protected Reindex getReindex(long companyId) {
		Reindex reindex = new Reindex(
			indexerRegistry, bulkReindexersHolder, _executorService,
			_reindexRequestsHolder);

		reindex.setCompanyId(companyId);
		reindex.setNonbulkIndexing(
			Boolean.valueOf(_reindexerConfiguration.nonbulkIndexingOverride()));
		reindex.setSynchronousExecution(
			GetterUtil.getBoolean(
				_reindexerConfiguration.synchronousExecutionOverride(),
				ProxyModeThreadLocal.isForceSync()));

		return reindex;
	}

	protected Thread getThread(Runnable runnable) {
		Thread thread = _threadFactory.newThread(runnable);

		thread.setDaemon(true);
		thread.setName(this + StringPool.DASH + thread.getName());

		return thread;
	}

	@Reference
	protected BulkReindexersHolder bulkReindexersHolder;

	@Reference
	protected IndexerRegistry indexerRegistry;

	private static final ThreadFactory _threadFactory =
		Executors.defaultThreadFactory();

	private volatile ExecutorService _executorService;
	private volatile ReindexerConfiguration _reindexerConfiguration;
	private volatile ReindexRequestsHolder _reindexRequestsHolder;

}