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

package com.liferay.portal.mobile.device.detection.fiftyonedegrees.enterprise.internal.data;

import com.liferay.portal.kernel.util.GetterUtil;
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
	enabled = false, immediate = true,
	property = {
		"file.name=META-INF/51Degrees-EnterpriseV3_2.dat.gz",
		"service.ranking:Integer=100"
	},
	service = DataFileProvider.class
)
public class FiftyOneDegreesEnterpriseDataFileProvider
	implements DataFileProvider {

	@Override
	public InputStream getDataFileInputStream() throws IOException {
		String fileName = _fileName;

		Bundle bundle = _bundleContext.getBundle();

		URL url = bundle.getResource(fileName);

		InputStream inputStream = url.openStream();

		if (fileName.endsWith(".gz")) {
			return new GZIPInputStream(inputStream);
		}
		else if (fileName.endsWith(".jar") || fileName.endsWith(".zip")) {
			return new ZipInputStream(inputStream);
		}
		else {
			return inputStream;
		}
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_fileName = GetterUtil.getString(properties.get("file.name"));
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
	}

	private BundleContext _bundleContext;
	private volatile String _fileName;

}