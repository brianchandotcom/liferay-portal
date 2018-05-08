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

package com.liferay.portal.search.internal.buffer;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.buffer.IndexerRequestBufferExecutor;
import com.liferay.portal.search.configuration.IndexerRegistryConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.IndexerRegistryConfiguration",
	immediate = true, service = IndexerRequestBufferExecutorWatcher.class
)
public class IndexerRequestBufferExecutorWatcher {

	public IndexerRequestBufferExecutor getIndexerRequestBufferExecutor() {
		String bufferedExecutionMode =
			_indexerRegistryConfiguration.bufferedExecutionMode();

		IndexerRequestBufferExecutor indexerRequestBufferExecutor =
			_indexerRequestBufferExecutors.get(bufferedExecutionMode);

		if (indexerRequestBufferExecutor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Using default indexer request buffered executor for " +
						bufferedExecutionMode);
			}

			indexerRequestBufferExecutor = _defaultIndexerRequestBufferExecutor;
		}

		return indexerRequestBufferExecutor;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_indexerRegistryConfiguration = ConfigurableUtil.createConfigurable(
			IndexerRegistryConfiguration.class, properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addIndexerRequestBufferExecutor(
		IndexerRequestBufferExecutor indexerRequestBufferExecutor,
		Map<String, Object> properties) {

		String bufferedExecutionMode = _getBufferedExecutionMode(properties);

		_indexerRequestBufferExecutors.put(
			bufferedExecutionMode, indexerRequestBufferExecutor);
	}

	protected void removeIndexerRequestBufferExecutor(
		IndexerRequestBufferExecutor indexerRequestBufferExecutor,
		Map<String, Object> properties) {

		String bufferedExecutionMode = _getBufferedExecutionMode(properties);

		_indexerRequestBufferExecutors.remove(bufferedExecutionMode);
	}

	private String _getBufferedExecutionMode(Map<String, Object> properties) {
		String bufferedExecutionMode = GetterUtil.getString(
			properties.get("buffered.execution.mode"));

		if (Validator.isNull(bufferedExecutionMode)) {
			throw new IllegalArgumentException(
				"The property \"buffered.execution.mode\" is invalid for " +
					ClassUtil.getClassName(bufferedExecutionMode));
		}

		return bufferedExecutionMode;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRequestBufferExecutorWatcher.class);

	@Reference(target = "(buffered.execution.mode=DEFAULT)")
	private IndexerRequestBufferExecutor _defaultIndexerRequestBufferExecutor;

	private IndexerRegistryConfiguration _indexerRegistryConfiguration;
	private final Map<String, IndexerRequestBufferExecutor>
		_indexerRequestBufferExecutors = new ConcurrentHashMap<>();

}