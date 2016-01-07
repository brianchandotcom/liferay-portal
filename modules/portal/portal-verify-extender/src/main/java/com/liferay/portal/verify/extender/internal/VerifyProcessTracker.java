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

package com.liferay.portal.verify.extender.internal;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.model.Release;
import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactoryTracker;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.extender.internal.configuration.VerifyProcessTrackerConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.verify.extender.internal.configuration.VerifyProcessTrackerConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"osgi.command.function=execute", "osgi.command.function=executeAll",
		"osgi.command.function=list", "osgi.command.function=show",
		"osgi.command.function=showReports", "osgi.command.scope=verify"
	},
	service = {VerifyProcessTracker.class}
)
public class VerifyProcessTracker {

	public void execute(final String verifyProcessName) {
		executeVerifyProcess(
			verifyProcessName, null, "verify-" + verifyProcessName);
	}

	public void execute(
		String verifyProcessName, String outputStreamContainerFactoryName) {

		executeVerifyProcess(
			verifyProcessName, outputStreamContainerFactoryName,
			"verify-" + verifyProcessName);
	}

	public void executeAll() {
		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactory();

		_runAllVerifiersWithFactory(outputStreamContainerFactory);
	}

	public void executeAll(String outputStreamContainerFactoryName) {
		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactory(
					outputStreamContainerFactoryName);

		_runAllVerifiersWithFactory(outputStreamContainerFactory);
	}

	public void list() {
		for (String verifyProcessName : _verifyProcesses.keySet()) {
			show(verifyProcessName);
		}
	}

	public void show(String verifyProcessName) {
		try {
			getVerifyProcess(verifyProcessName);
		}
		catch (IllegalArgumentException iae) {
			System.out.println(
				"No verify process with name " + verifyProcessName);

			return;
		}

		System.out.println("Registered verify process " + verifyProcessName);
	}

	public void showReports() {
		Set<String> outputStreamContainerFactoryNames =
			_outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactoryNames();

		for (String outputStreamContainerFactoryName :
				outputStreamContainerFactoryNames) {

			System.out.println(outputStreamContainerFactoryName);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_verifyProcessTrackerConfiguration = Configurable.createConfigurable(
			VerifyProcessTrackerConfiguration.class, properties);

		try {
			VerifyServiceTrackerMapListener verifyServiceTrackerMapListener =
				null;

			if (_verifyProcessTrackerConfiguration.autoVerify()) {
				verifyServiceTrackerMapListener =
					new VerifyServiceTrackerMapListener();
			}

			_verifyProcesses = ServiceTrackerMapFactory.singleValueMap(
				bundleContext, VerifyProcess.class, "verify.process.name",
				verifyServiceTrackerMapListener);

			_verifyProcesses.open();
		}
		catch (InvalidSyntaxException ise) {
			throw new IllegalStateException(ise);
		}
	}

	protected void close(OutputStream outputStream) {
		try {
			outputStream.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Deactivate
	protected void deactivate() {
		_verifyProcesses.close();
	}

	protected void executeVerifyProcess(
		String verifyProcessName, OutputStream outputStream) {

		PrintWriter printWriter = new PrintWriter(outputStream, true);

		printWriter.println("Executing " + verifyProcessName);

		VerifyProcess verifyProcess = getVerifyProcess(verifyProcessName);

		try {
			verifyProcess.verify();
		}
		catch (VerifyException ve) {
			_log.error(ve, ve);
		}
	}

	protected void executeVerifyProcess(
		final String verifyProcessName, String outputStreamContainerFactoryName,
		String outputStreamName) {

		OutputStreamContainerFactory outputStreamContainerFactory;

		if (outputStreamContainerFactoryName != null) {
			outputStreamContainerFactory =
				_outputStreamContainerFactoryTracker.
					getOutputStreamContainerFactory(
						outputStreamContainerFactoryName);
		}
		else {
			outputStreamContainerFactory =
				_outputStreamContainerFactoryTracker.
					getOutputStreamContainerFactory();
		}

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create(outputStreamName);

		final OutputStream outputStream =
			outputStreamContainer.getOutputStream();

		_outputStreamContainerFactoryTracker.runWithSwappedLog(
			new Runnable() {

				@Override
				public void run() {
					executeVerifyProcess(verifyProcessName, outputStream);
				}

			},
			outputStreamName, outputStream);

		close(outputStream);
	}

	protected VerifyProcess getVerifyProcess(String verifyProcessName) {
		VerifyProcess verifyProcess = _verifyProcesses.getService(
			verifyProcessName);

		if (verifyProcess == null) {
			throw new IllegalArgumentException(
				"No verify process with name " + verifyProcessName);
		}

		return verifyProcess;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setOutputStreamTracker(
		OutputStreamContainerFactoryTracker
			outputStreamContainerFactoryTracker) {

		_outputStreamContainerFactoryTracker =
			outputStreamContainerFactoryTracker;
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	private void _runAllVerifiersWithFactory(
		OutputStreamContainerFactory outputStreamContainerFactory) {

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create("all-verifiers");

		final OutputStream outputStream =
			outputStreamContainer.getOutputStream();

		_outputStreamContainerFactoryTracker.runWithSwappedLog(
			new AllVerifiersRunnable(outputStream),
			outputStreamContainer.getDescription(), outputStream);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyProcessTracker.class);

	private OutputStreamContainerFactoryTracker
		_outputStreamContainerFactoryTracker;
	private ReleaseLocalService _releaseLocalService;
	private ServiceTrackerMap<String, VerifyProcess> _verifyProcesses;
	private VerifyProcessTrackerConfiguration
		_verifyProcessTrackerConfiguration;

	private class AllVerifiersRunnable implements Runnable {

		public AllVerifiersRunnable(OutputStream outputStream) {
			_outputStream = outputStream;
		}

		@Override
		public void run() {
			Set<String> verifyProcessNames = _verifyProcesses.keySet();

			for (String verifyProcessName : verifyProcessNames) {
				executeVerifyProcess(verifyProcessName, _outputStream);
			}
		}

		private final OutputStream _outputStream;

	}

	private class VerifyServiceTrackerMapListener
		implements ServiceTrackerMapListener
			<String, VerifyProcess, VerifyProcess> {

		@Override
		public void keyEmitted(
			ServiceTrackerMap<String, VerifyProcess> verifyProcessTrackerMap,
			String key, VerifyProcess serviceVerifyProcess,
			VerifyProcess contentVerifyProcess) {

			Release release = _releaseLocalService.fetchRelease(key);

			if ((release == null) || release.isVerified()) {
				return;
			}

			execute(key);

			release.setVerified(true);

			_releaseLocalService.updateRelease(release);
		}

		@Override
		public void keyRemoved(
			ServiceTrackerMap<String, VerifyProcess> serviceTrackerMap,
			String key, VerifyProcess service, VerifyProcess content) {
		}

	}

}