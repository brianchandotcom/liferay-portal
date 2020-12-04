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

package com.liferay.portal.mobile.device.detection.fiftyonedegrees.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.mobile.device.detection.fiftyonedegrees.configuration.FiftyOneDegreesConfiguration;
import com.liferay.portal.mobile.device.detection.fiftyonedegrees.data.DataFileProvider;

import fiftyone.mobile.detection.Dataset;
import fiftyone.mobile.detection.Match;
import fiftyone.mobile.detection.Provider;
import fiftyone.mobile.detection.factories.StreamFactory;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@Component(
	configurationPid = "com.liferay.portal.mobile.device.detection.fiftyonedegrees.configuration.FiftyOneDegreesConfiguration",
	service = FiftyOneDegreesEngineProxy.class
)
public class FiftyOneDegreesEngineProxy {

	public Dataset getDataset() {
		return _provider.dataSet;
	}

	public Device getDevice(HttpServletRequest httpServletRequest) {
		String userAgent = httpServletRequest.getHeader("User-Agent");

		try {
			Match match = _provider.match(userAgent);

			return new FiftyOneDegreesDevice(match);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get match for user agent: " + userAgent,
					ioException);
			}

			return UnknownDevice.getInstance();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_fiftyOneDegreesConfiguration = ConfigurableUtil.createConfigurable(
			FiftyOneDegreesConfiguration.class, properties);

		try (InputStream inputStream =
				_dataFileProvider.getDataFileInputStream()) {

			_dataset = StreamFactory.create(IOUtils.toByteArray(inputStream));

			_provider = new Provider(
				_dataset, _fiftyOneDegreesConfiguration.cacheSize());
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to load 51Degrees provider data", ioException);
			}

			throw new IllegalStateException(ioException);
		}
	}

	@Deactivate
	protected void deactivate() throws IOException {
		_fiftyOneDegreesConfiguration = null;
		_provider = null;

		if (_dataset != null) {
			_dataset.close();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FiftyOneDegreesEngineProxy.class);

	@Reference
	private DataFileProvider _dataFileProvider;

	private Dataset _dataset;
	private volatile FiftyOneDegreesConfiguration _fiftyOneDegreesConfiguration;
	private Provider _provider;

}