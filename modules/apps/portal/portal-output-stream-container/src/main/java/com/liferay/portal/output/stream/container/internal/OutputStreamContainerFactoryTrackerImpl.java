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

package com.liferay.portal.output.stream.container.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactoryTracker;

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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true, service = OutputStreamContainerFactoryTracker.class
)
public class OutputStreamContainerFactoryTrackerImpl
	implements OutputStreamContainerFactoryTracker {

	@Override
	public OutputStreamContainerFactory getOutputStreamContainerFactory() {
		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactory;

		if (outputStreamContainerFactory != null) {
			return outputStreamContainerFactory;
		}

		return _consoleOutputStreamContainerFactory;
	}

	@Override
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

	@Override
	public Set<String> getOutputStreamContainerFactoryNames() {
		return _outputStreamContainerFactories.keySet();
	}

	@Override
	public void runWithSwappedLog(Runnable runnable, String outputStreamHint) {
		OutputStreamContainerFactory outputStreamContainerFactory =
			getOutputStreamContainerFactory();

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create(outputStreamHint);

		runWithSwappedLog(
			runnable, outputStreamContainer.getDescription(),
			outputStreamContainer.getOutputStream());
	}

	@Override
	public void runWithSwappedLog(
		Runnable runnable, String outputStreamName, OutputStream outputStream) {

		if (_log.isInfoEnabled()) {
			_log.info("Using " + outputStreamName + " as output");
		}

		Writer writer = _writerThreadLocal.get();

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			outputStream, Charset.forName("UTF-8"));

		_writerThreadLocal.set(outputStreamWriter);

		try {
			runnable.run();
		}
		finally {
			_writerThreadLocal.set(writer);

			try {
				outputStreamWriter.flush();
			}
			catch (IOException ioe) {
				_log.error(ioe.getLocalizedMessage());
			}
		}
	}

	@Override
	public void runWithSwappedLog(
		Runnable runnable, String outputStreamHint,
		String outputStreamContainerName) {

		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactories.getService(
				outputStreamContainerName);

		if (outputStreamContainerFactory == null) {
			runWithSwappedLog(runnable, outputStreamHint);

			return;
		}

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create(outputStreamHint);

		runWithSwappedLog(
			runnable, outputStreamContainer.getDescription(),
			outputStreamContainer.getOutputStream());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
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

	@Deactivate
	protected void deactivate() {
		Logger rootLogger = Logger.getRootLogger();

		if (_outputStreamContainerFactories != null) {
			_outputStreamContainerFactories.close();
		}

		if (rootLogger != null) {
			rootLogger.removeAppender(_writerAppender);
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OutputStreamContainerFactoryTrackerImpl.class);

	private final OutputStreamContainerFactory
		_consoleOutputStreamContainerFactory =
			new ConsoleOutputStreamContainerFactory();
	private ServiceTrackerMap<String, OutputStreamContainerFactory>
		_outputStreamContainerFactories;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile OutputStreamContainerFactory _outputStreamContainerFactory;

	private WriterAppender _writerAppender;
	private final ThreadLocal<Writer> _writerThreadLocal = new ThreadLocal<>();

	private class ThreadLocalWriter extends Writer {

		@Override
		public void close() throws IOException {
			Writer writer = _writerThreadLocal.get();

			if (writer != null) {
				writer.close();
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
		public void write(char[] chars, int offset, int length)
			throws IOException {

			Writer writer = _writerThreadLocal.get();

			if (writer != null) {
				writer.write(chars, offset, length);
			}
		}

	}

}