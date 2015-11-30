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
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.charset.Charset;

import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;

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
		_logger = new org.apache.felix.utils.log.Logger(bundleContext);

		try {
			Logger rootLogger = Logger.getRootLogger();

			_writerAppender = new WriterAppender(
				new SimpleLayout(), new ThreadLocalWriter());

			_writerAppender.setThreshold(Level.ALL);

			_writerAppender.activateOptions();

			rootLogger.addAppender(_writerAppender);

			_outputStreamContainerFactories =
				ServiceTrackerMapFactory.openSingleValueMap(
					bundleContext, OutputStreamContainerFactory.class, "name");
		}
		catch (InvalidSyntaxException ise) {
			throw new IllegalStateException(ise);
		}
	}

	@Deactivate
	protected void deactivate() {
		Logger rootLogger = Logger.getRootLogger();

		rootLogger.removeAppender(_writerAppender);

		_outputStreamContainerFactories.close();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind ="-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private org.apache.felix.utils.log.Logger _logger;
	private ServiceTrackerMap<String, OutputStreamContainerFactory>
		_outputStreamContainerFactories;
	private volatile OutputStreamContainerFactory _outputStreamContainerFactory;
	private WriterAppender _writerAppender;
	private final ThreadLocal<Writer> _writerThreadLocal = new ThreadLocal<>();

	private class ThreadLocalWriter extends Writer {

		@Override
		public void write(char[] cbuf, int off, int len)
			throws IOException {

			Writer writer = _writerThreadLocal.get();

			if (writer != null) {
				writer.write(cbuf, off, len);
			}
		}

		@Override
		public void flush() throws IOException {
			Writer writer = _writerThreadLocal.get();

			if (writer != null) {
				writer.flush();
			}
		}

		@Override
		public void close() throws IOException {
			Writer writer = _writerThreadLocal.get();

			if (writer != null) {
				writer.close();
			}
		}

	}
}