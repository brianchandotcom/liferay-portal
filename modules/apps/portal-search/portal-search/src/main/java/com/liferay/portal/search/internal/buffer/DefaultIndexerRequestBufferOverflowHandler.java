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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.buffer.IndexerRequestBufferExecutor;
import com.liferay.portal.search.buffer.IndexerRequestBufferOverflowHandler;
import com.liferay.portal.search.configuration.IndexerRegistryConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.IndexerRegistryConfiguration",
	immediate = true, property = "mode=DEFAULT",
	service = IndexerRequestBufferOverflowHandler.class
)
public class DefaultIndexerRequestBufferOverflowHandler
	implements IndexerRequestBufferOverflowHandler {

	@Override
	public boolean bufferOverflowed(
		IndexerRequestBuffer indexerRequestBuffer, int maxBufferSize) {

		int currentBufferSize = indexerRequestBuffer.size();

		if (currentBufferSize < maxBufferSize) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Buffer size is less than maximum: " + maxBufferSize);
			}

			return false;
		}

		int numRequests = Math.round(
			currentBufferSize -
				Math.abs(maxBufferSize * _minimumBufferAvailabilityPercentage));

		if (numRequests > 0) {
			try {
				BufferOverflowThreadLocal.setOverflowMode(true);

				IndexerRequestBufferExecutor indexerRequestBufferExecutor =
					indexerRequestBufferExecutorWatcher.
						getIndexerRequestBufferExecutor();

				indexerRequestBufferExecutor.execute(
					indexerRequestBuffer, numRequests);
			}
			finally {
				BufferOverflowThreadLocal.setOverflowMode(false);
			}
		}

		return true;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		IndexerRegistryConfiguration indexerRegistryConfiguration =
			ConfigurableUtil.createConfigurable(
				IndexerRegistryConfiguration.class, properties);

		if ((indexerRegistryConfiguration.
				minimumBufferAvailabilityPercentage() > 1) ||
			(indexerRegistryConfiguration.
				minimumBufferAvailabilityPercentage() < 0.1)) {

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Invalid minimum buffer availability percentage: ");
				sb.append(
					indexerRegistryConfiguration.
						minimumBufferAvailabilityPercentage());
				sb.append(", using default value");
				sb.append(_DEFAULT_MINIMUM_BUFFER_AVAILABILITY_PERCENTAGE);

				_log.warn(sb.toString());
			}
		}

		_minimumBufferAvailabilityPercentage =
			indexerRegistryConfiguration.minimumBufferAvailabilityPercentage();
	}

	@Reference
	protected IndexerRequestBufferExecutorWatcher
		indexerRequestBufferExecutorWatcher;

	private static final float _DEFAULT_MINIMUM_BUFFER_AVAILABILITY_PERCENTAGE =
		0.90F;

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultIndexerRequestBufferOverflowHandler.class);

	private volatile float _minimumBufferAvailabilityPercentage;

}