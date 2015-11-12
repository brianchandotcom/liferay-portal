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

package com.liferay.portal.output.stream.container;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

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
@Component(
	immediate = true, service = OutputStreamContainerFactoryTracker.class
)
public class OutputStreamContainerFactoryTracker {

	public OutputStreamContainerFactory getOutputStreamContainerFactory() {
		return _outputStreamContainerFactory;
	}

	public OutputStreamContainerFactory getOutputStreamContainerFactory(
		String outputStreamContainerFactoryName) {

		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactories.getService(
				outputStreamContainerFactoryName);

		if (outputStreamContainerFactory == null) {
			throw new IllegalArgumentException(
				"No output stream container factory registered with name " +
					outputStreamContainerFactoryName);
		}

		return outputStreamContainerFactory;
	}

	public Set<String> getOutputStreamContainerFactoryNames() {
		return _outputStreamContainerFactories.keySet();
	}

	@Reference(unbind = "-")
	public void setOutputStreamContainerFactory(
		OutputStreamContainerFactory outputStreamContainerFactory) {

		_outputStreamContainerFactory = outputStreamContainerFactory;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		try {
			_outputStreamContainerFactories =
				ServiceTrackerMapFactory.singleValueMap(
					bundleContext, OutputStreamContainerFactory.class, "name");
		}
		catch (InvalidSyntaxException ise) {
			throw new IllegalStateException(ise);
		}

		_outputStreamContainerFactories.open();
	}

	@Deactivate
	protected void deactivate() {
		_outputStreamContainerFactories.close();
	}

	private ServiceTrackerMap<String, OutputStreamContainerFactory>
		_outputStreamContainerFactories;
	private OutputStreamContainerFactory _outputStreamContainerFactory;

}