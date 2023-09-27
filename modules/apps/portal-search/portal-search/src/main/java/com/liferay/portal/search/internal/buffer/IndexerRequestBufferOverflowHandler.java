/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.buffer;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
	service = IndexerRequestBufferOverflowHandler.class
)
public class IndexerRequestBufferOverflowHandler {

	public void bufferOverflowed(
		IndexerRequestBuffer indexerRequestBuffer, int maxBufferSize) {

		int currentBufferSize = indexerRequestBuffer.size();

		if (currentBufferSize < maxBufferSize) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Buffer size is less than maximum: " + maxBufferSize);
			}

			return;
		}

		int numRequests = Math.round(
			currentBufferSize -
				Math.abs(maxBufferSize * _minimumBufferAvailabilityPercentage));

		if (numRequests > 0) {
			try {
				BufferOverflowThreadLocal.setOverflowMode(true);

				_indexerRequestBufferExecutor.execute(
					indexerRequestBuffer, numRequests);
			}
			finally {
				BufferOverflowThreadLocal.setOverflowMode(false);
			}
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		IndexerRegistryConfiguration indexerRegistryConfiguration =
			ConfigurableUtil.createConfigurable(
				IndexerRegistryConfiguration.class, properties);

		float minimumBufferAvailabilityPercentage =
			indexerRegistryConfiguration.minimumBufferAvailabilityPercentage();

		if ((minimumBufferAvailabilityPercentage > 1) ||
			(minimumBufferAvailabilityPercentage < 0.1)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Invalid minimum buffer availability percentage: ",
						minimumBufferAvailabilityPercentage,
						", using default value",
						_DEFAULT_MINIMUM_BUFFER_AVAILABILITY_PERCENTAGE));
			}
		}

		_minimumBufferAvailabilityPercentage =
			minimumBufferAvailabilityPercentage;
	}

	private static final float _DEFAULT_MINIMUM_BUFFER_AVAILABILITY_PERCENTAGE =
		0.90F;

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRequestBufferOverflowHandler.class);

	@Reference
	private IndexerRequestBufferExecutor _indexerRequestBufferExecutor;

	private volatile float _minimumBufferAvailabilityPercentage;

}