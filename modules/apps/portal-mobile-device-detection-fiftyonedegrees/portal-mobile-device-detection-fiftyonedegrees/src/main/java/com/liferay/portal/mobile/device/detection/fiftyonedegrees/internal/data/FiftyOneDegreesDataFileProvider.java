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

package com.liferay.portal.mobile.device.detection.fiftyonedegrees.internal.data;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.mobile.device.detection.fiftyonedegrees.configuration.FiftyOneDegreesConfiguration;
import com.liferay.portal.mobile.device.detection.fiftyonedegrees.data.DataFileProvider;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.mobile.device.detection.fiftyonedegrees.configuration.FiftyOneDegreesConfiguration",
	enabled = false, immediate = true, property = "service.ranking:Integer=1",
	service = DataFileProvider.class
)
public class FiftyOneDegreesDataFileProvider implements DataFileProvider {

	@Override
	public InputStream getDataFileInputStream() throws IOException {
		Bundle bundle = _bundleContext.getBundle();

		String fiftyOneDegreesDataFileName =
			_fiftyOneDegreesConfiguration.fiftyOneDegreesDataFileName();

		URL url = bundle.getResource(fiftyOneDegreesDataFileName);

		InputStream inputStream = url.openStream();

		if (fiftyOneDegreesDataFileName.endsWith(".gz")) {
			return new GZIPInputStream(inputStream);
		}
		else if (fiftyOneDegreesDataFileName.endsWith(".jar") ||
				 fiftyOneDegreesDataFileName.endsWith(".zip")) {

			return new ZipInputStream(inputStream);
		}

		return inputStream;
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_fiftyOneDegreesConfiguration = ConfigurableUtil.createConfigurable(
			FiftyOneDegreesConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
		_fiftyOneDegreesConfiguration = null;
	}

	private BundleContext _bundleContext;
	private volatile FiftyOneDegreesConfiguration _fiftyOneDegreesConfiguration;

}