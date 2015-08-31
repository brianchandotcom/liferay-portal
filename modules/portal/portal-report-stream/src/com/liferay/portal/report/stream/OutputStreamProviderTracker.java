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

package com.liferay.portal.report.stream;

import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = OutputStreamProviderTracker.class)
public class OutputStreamProviderTracker {

	public OutputStreamProvider getDefaultOutputStreamProvider() {
		return _outputStreamProvider;
	}

	public OutputStreamProvider getOutputStreamProvider(
		String outputStreamProviderName) {

		OutputStreamProvider outputStreamProvider =
			_outputStreamProviders.getService(outputStreamProviderName);

		if (outputStreamProvider == null) {
			throw new IllegalArgumentException(
				"Report provider with name " + outputStreamProviderName +
					" is not registered");
		}

		return outputStreamProvider;
	}

	public Set<String> getOutputStreamProviderNames() {
		return _outputStreamProviders.keySet();
	}

	@Reference
	public void setOutputStreamProvider(
		OutputStreamProvider outputStreamProvider) {

		_outputStreamProvider = outputStreamProvider;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		try {
			_outputStreamProviders = ServiceTrackerMapFactory.singleValueMap(
				bundleContext, OutputStreamProvider.class, "name");
		}
		catch (InvalidSyntaxException e) {
			throw new IllegalStateException(e);
		}

		_outputStreamProviders.open();
	}

	@Deactivate
	protected void deactivate() {
		_outputStreamProviders.close();
	}

	private OutputStreamProvider _outputStreamProvider;
	private ServiceTrackerMap<String, OutputStreamProvider>
		_outputStreamProviders;

}