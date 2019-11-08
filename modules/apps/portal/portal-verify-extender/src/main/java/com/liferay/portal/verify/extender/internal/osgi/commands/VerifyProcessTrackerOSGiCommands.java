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

package com.liferay.portal.verify.extender.internal.osgi.commands;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactoryTracker;
import com.liferay.portal.output.stream.container.constants.OutputStreamContainerConstants;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.extender.internal.configuration.VerifyProcessTrackerConfiguration;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.felix.service.command.Descriptor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
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
		"osgi.command.function=check", "osgi.command.function=checkAll",
		"osgi.command.function=execute", "osgi.command.function=executeAll",
		"osgi.command.function=help", "osgi.command.function=list",
		"osgi.command.function=show", "osgi.command.function=showReports",
		"osgi.command.scope=verify"
	},
	service = VerifyProcessTrackerOSGiCommands.class
)
public class VerifyProcessTrackerOSGiCommands {

	@Descriptor("List latest execution result for a specific verify process")
	public void check(final String verifyProcessName) {
		try {
			getVerifyProcesses(_verifyProcesses, verifyProcessName);
		}
		catch (IllegalArgumentException iae) {
			System.out.println(
				"No verify process with name " + verifyProcessName);

			return;
		}

		Release release = releaseLocalService.fetchRelease(verifyProcessName);

		if ((release == null) ||
			(!release.isVerified() &&
			 (release.getState() == ReleaseConstants.STATE_GOOD))) {

			System.out.println(
				verifyProcessName + " verify process has not executed");
		}
		else {
			if (release.isVerified()) {
				System.out.println(
					verifyProcessName + " verify process succeeded");
			}
			else if (release.getState() ==
						ReleaseConstants.STATE_VERIFY_FAILURE) {

				System.out.println(
					verifyProcessName + " verify process failed");
			}
		}
	}

	@Descriptor("List latest execution result for all verify processes")
	public void checkAll() {
		for (String verifyProcessName : _verifyProcesses.keySet()) {
			check(verifyProcessName);
		}
	}

	@Descriptor("Execute a specific verify process")
	public void execute(final String verifyProcessName) {
		_execute(_verifyProcesses, verifyProcessName, null, true);
	}

	@Descriptor("Execute a specific verify process with a specific output")
	public void execute(
		String verifyProcessName, String outputStreamContainerFactoryName) {

		_execute(
			_verifyProcesses, verifyProcessName,
			outputStreamContainerFactoryName, true);
	}

	@Descriptor("Execute all verify processes")
	public void executeAll() {
		_runAllVerifiersWithFactory(
			outputStreamContainerFactoryTracker.getOutputStreamContainerFactory(
				null),
			true);
	}

	@Descriptor("Execute all verify processes with a specific output")
	public void executeAll(String outputStreamContainerFactoryName) {
		_runAllVerifiersWithFactory(
			outputStreamContainerFactoryTracker.getOutputStreamContainerFactory(
				outputStreamContainerFactoryName),
			true);
	}

	@Descriptor("List all registered verify processes")
	public void list() {
		for (String verifyProcessName : _verifyProcesses.keySet()) {
			show(verifyProcessName);
		}
	}

	@Descriptor("Show all verify processes for a specific verify process name")
	public void show(String verifyProcessName) {
		try {
			getVerifyProcesses(_verifyProcesses, verifyProcessName);
		}
		catch (IllegalArgumentException iae) {
			System.out.println(
				"No verify process with name " + verifyProcessName);

			return;
		}

		System.out.println("Registered verify process " + verifyProcessName);
	}

	@Descriptor("Show all available outputs")
	public void showReports() {
		Set<String> outputStreamContainerFactoryNames =
			outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactoryNames();

		for (String outputStreamContainerFactoryName :
				outputStreamContainerFactoryNames) {

			System.out.println(outputStreamContainerFactoryName);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_verifyProcessTrackerConfiguration =
			ConfigurableUtil.createConfigurable(
				VerifyProcessTrackerConfiguration.class, properties);

		ServiceTrackerMapListener<String, VerifyProcess, List<VerifyProcess>>
			verifyServiceTrackerMapListener = null;

		if (_verifyProcessTrackerConfiguration.autoVerify()) {
			verifyServiceTrackerMapListener =
				new VerifyServiceTrackerMapListener();
		}

		_serviceRegistrations = new ConcurrentHashMap<>();

		_verifyProcesses = ServiceTrackerMapFactory.openMultiValueMap(
			_bundleContext, VerifyProcess.class, null,
			new PropertyServiceReferenceMapper<String, VerifyProcess>(
				"verify.process.name"),
			new PropertyServiceReferenceComparator("service.ranking"),
			verifyServiceTrackerMapListener);
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

		for (Map.Entry<String, ServiceRegistration<Object>>
				serviceRegistrationEntry : _serviceRegistrations.entrySet()) {

			ServiceRegistration<Object> serviceRegistration =
				serviceRegistrationEntry.getValue();

			serviceRegistration.unregister();
		}

		_serviceRegistrations = null;
	}

	protected void executeVerifyProcesses(
		ServiceTrackerMap<String, List<VerifyProcess>> verifyProcessTrackerMap,
		String verifyProcessName, OutputStream outputStream, boolean force) {

		PrintWriter printWriter = new PrintWriter(outputStream, true);

		List<VerifyProcess> verifyProcesses = getVerifyProcesses(
			verifyProcessTrackerMap, verifyProcessName);

		boolean indexReadOnly = indexStatusManager.isIndexReadOnly();

		indexStatusManager.setIndexReadOnly(
			_verifyProcessTrackerConfiguration.indexReadOnly());

		NotificationThreadLocal.setEnabled(false);
		StagingAdvicesThreadLocal.setEnabled(false);
		WorkflowThreadLocal.setEnabled(false);

		try {
			Release release = releaseLocalService.fetchRelease(
				verifyProcessName);

			if ((release != null) && !force && release.isVerified()) {
				if (!_serviceRegistrations.containsKey(verifyProcessName)) {
					_registerMarkerObject(verifyProcessName);
				}

				return;
			}

			if (release == null) {

				// Verification state must be persisted even though not all
				// verifiers are associated with a database service

				release = releaseLocalService.createRelease(
					counterLocalService.increment());

				release.setServletContextName(verifyProcessName);
				release.setVerified(false);
			}

			printWriter.println(
				"Executing verifiers registered for " + verifyProcessName);

			VerifyException verifyException = null;

			for (VerifyProcess verifyProcess : verifyProcesses) {
				try {
					verifyProcess.verify();
				}
				catch (VerifyException ve) {
					_log.error(ve, ve);

					verifyException = ve;
				}
			}

			if (verifyException == null) {
				release.setVerified(true);
				release.setState(ReleaseConstants.STATE_GOOD);

				releaseLocalService.updateRelease(release);

				_registerMarkerObject(verifyProcessName);
			}
			else {
				release.setState(ReleaseConstants.STATE_VERIFY_FAILURE);
				release.setVerified(false);

				releaseLocalService.updateRelease(release);
			}
		}
		finally {
			indexStatusManager.setIndexReadOnly(indexReadOnly);
			NotificationThreadLocal.setEnabled(true);
			StagingAdvicesThreadLocal.setEnabled(true);
			WorkflowThreadLocal.setEnabled(true);
		}
	}

	protected void executeVerifyProcesses(
		final ServiceTrackerMap<String, List<VerifyProcess>>
			verifyProcessTrackerMap,
		final String verifyProcessName, String outputStreamContainerFactoryName,
		String outputStreamName, final boolean force) {

		OutputStreamContainerFactory outputStreamContainerFactory =
			outputStreamContainerFactoryTracker.getOutputStreamContainerFactory(
				outputStreamContainerFactoryName);

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create(outputStreamName);

		final OutputStream outputStream =
			outputStreamContainer.getOutputStream();

		outputStreamContainerFactoryTracker.runWithSwappedLog(
			new Runnable() {

				@Override
				public void run() {
					executeVerifyProcesses(
						verifyProcessTrackerMap, verifyProcessName,
						outputStream, force);
				}

			},
			outputStreamName, outputStream);

		close(outputStream);
	}

	protected List<VerifyProcess> getVerifyProcesses(
		ServiceTrackerMap<String, List<VerifyProcess>> verifyProcessTrackerMap,
		String verifyProcessName) {

		List<VerifyProcess> verifyProcesses =
			verifyProcessTrackerMap.getService(verifyProcessName);

		if (verifyProcesses == null) {
			throw new IllegalArgumentException(
				"No verify processes with name " + verifyProcessName);
		}

		return verifyProcesses;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	protected CounterLocalService counterLocalService;

	@Reference
	protected IndexStatusManager indexStatusManager;

	@Reference
	protected OutputStreamContainerFactoryTracker
		outputStreamContainerFactoryTracker;

	@Reference
	protected ReleaseLocalService releaseLocalService;

	private void _execute(
		ServiceTrackerMap<String, List<VerifyProcess>> verifyProcessTrackerMap,
		final String verifyProcessName, String outputStreamContainerFactoryName,
		final boolean force) {

		executeVerifyProcesses(
			verifyProcessTrackerMap, verifyProcessName,
			outputStreamContainerFactoryName, "verify-" + verifyProcessName,
			force);
	}

	private void _registerMarkerObject(String verifyProcessName) {
		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		dictionary.put("verify.process.name", verifyProcessName);

		ServiceRegistration<Object> serviceRegistration =
			_bundleContext.registerService(
				Object.class, new Object(), dictionary);

		_serviceRegistrations.put(verifyProcessName, serviceRegistration);
	}

	private void _runAllVerifiersWithFactory(
		OutputStreamContainerFactory outputStreamContainerFactory,
		boolean force) {

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create("all-verifiers");

		final OutputStream outputStream =
			outputStreamContainer.getOutputStream();

		outputStreamContainerFactoryTracker.runWithSwappedLog(
			new AllVerifiersRunnable(outputStream, force),
			outputStreamContainer.getDescription(), outputStream);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyProcessTrackerOSGiCommands.class);

	private BundleContext _bundleContext;
	private Map<String, ServiceRegistration<Object>> _serviceRegistrations;
	private ServiceTrackerMap<String, List<VerifyProcess>> _verifyProcesses;
	private VerifyProcessTrackerConfiguration
		_verifyProcessTrackerConfiguration;

	private class AllVerifiersRunnable implements Runnable {

		public AllVerifiersRunnable(OutputStream outputStream, boolean force) {
			_outputStream = outputStream;
			_force = force;
		}

		@Override
		public void run() {
			Set<String> verifyProcessNames = _verifyProcesses.keySet();

			for (String verifyProcessName : verifyProcessNames) {
				executeVerifyProcesses(
					_verifyProcesses, verifyProcessName, _outputStream, _force);
			}
		}

		private final boolean _force;
		private final OutputStream _outputStream;

	}

	private class VerifyServiceTrackerMapListener
		implements ServiceTrackerMapListener
			<String, VerifyProcess, List<VerifyProcess>> {

		@Override
		public void keyEmitted(
			ServiceTrackerMap<String, List<VerifyProcess>>
				verifyProcessTrackerMap,
			String key, VerifyProcess serviceVerifyProcess,
			List<VerifyProcess> contentVerifyProcesses) {

			_execute(
				verifyProcessTrackerMap, key,
				OutputStreamContainerConstants.FACTORY_NAME_DUMMY, false);
		}

		@Override
		public void keyRemoved(
			ServiceTrackerMap<String, List<VerifyProcess>> serviceTrackerMap,
			String key, VerifyProcess serviceVerifyProcess,
			List<VerifyProcess> contentVerifyProcess) {
		}

	}

}