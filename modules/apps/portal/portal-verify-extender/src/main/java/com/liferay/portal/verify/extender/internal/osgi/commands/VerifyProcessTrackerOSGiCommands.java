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
import com.liferay.gogo.shell.logging.TeeLoggingUtil;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.felix.service.command.Descriptor;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=check", "osgi.command.function=checkAll",
		"osgi.command.function=execute", "osgi.command.function=executeAll",
		"osgi.command.function=help", "osgi.command.function=list",
		"osgi.command.function=show", "osgi.command.scope=verify"
	},
	service = VerifyProcessTrackerOSGiCommands.class
)
public class VerifyProcessTrackerOSGiCommands {

	@Descriptor("List latest execution result for a specific verify process")
	public void check(String verifyProcessName) {
		try {
			_getVerifyProcesses(_serviceTrackerMap, verifyProcessName);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalArgumentException);
			}

			System.out.println(
				"No verify process with name " + verifyProcessName);

			return;
		}

		Release release = _releaseLocalService.fetchRelease(verifyProcessName);

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
		for (String verifyProcessName : _serviceTrackerMap.keySet()) {
			check(verifyProcessName);
		}
	}

	@Descriptor("Execute a specific verify process")
	public void execute(String verifyProcessName) {
		TeeLoggingUtil.runWithTeeLogging(
			() -> _executeVerifyProcesses(
				_getVerifyProcesses(_serviceTrackerMap, verifyProcessName),
				verifyProcessName, true));
	}

	@Descriptor("Execute all verify processes")
	public void executeAll() {
		TeeLoggingUtil.runWithTeeLogging(
			() -> {
				for (String verifyProcessName : _serviceTrackerMap.keySet()) {
					_executeVerifyProcesses(
						_getVerifyProcesses(
							_serviceTrackerMap, verifyProcessName),
						verifyProcessName, true);
				}
			});
	}

	@Descriptor("List all registered verify processes")
	public void list() {
		for (String verifyProcessName : _serviceTrackerMap.keySet()) {
			show(verifyProcessName);
		}
	}

	@Descriptor("Show all verify processes for a specific verify process name")
	public void show(String verifyProcessName) {
		try {
			_getVerifyProcesses(_serviceTrackerMap, verifyProcessName);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalArgumentException);
			}

			System.out.println(
				"No verify process with name " + verifyProcessName);

			return;
		}

		System.out.println("Registered verify process " + verifyProcessName);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		boolean upgrading = StartupHelperUtil.isUpgrading();

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			_bundleContext, VerifyProcess.class, "verify.process.name",
			new EagerServiceTrackerCustomizer<VerifyProcess, VerifyProcess>() {

				@Override
				public VerifyProcess addingService(
					ServiceReference<VerifyProcess> serviceReference) {

					VerifyProcess verifyProcess = _bundleContext.getService(
						serviceReference);

					if (upgrading ||
						_isInitialDeployment(serviceReference, verifyProcess)) {

						_executeVerifyProcesses(
							Collections.singletonList(verifyProcess),
							String.valueOf(
								serviceReference.getProperty(
									"verify.process.name")),
							false);
					}

					return verifyProcess;
				}

				@Override
				public void modifiedService(
					ServiceReference<VerifyProcess> serviceReference,
					VerifyProcess verifyProcess) {
				}

				@Override
				public void removedService(
					ServiceReference<VerifyProcess> serviceReference,
					VerifyProcess verifyProcess) {

					_bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _executeVerifyProcesses(
		List<VerifyProcess> verifyProcesses, String verifyProcessName,
		boolean force) {

		NotificationThreadLocal.setEnabled(false);
		StagingAdvicesThreadLocal.setEnabled(false);
		WorkflowThreadLocal.setEnabled(false);

		try {
			Release release = _releaseLocalService.fetchRelease(
				verifyProcessName);

			if ((release != null) && !force && release.isVerified()) {
				return;
			}

			if (release == null) {

				// Verification state must be persisted even though not all
				// verifiers are associated with a database service

				release = _releaseLocalService.createRelease(
					_counterLocalService.increment());

				release.setServletContextName(verifyProcessName);
				release.setVerified(false);
			}

			System.out.println(
				"Executing verifiers registered for " + verifyProcessName);

			VerifyException verifyException1 = null;

			for (VerifyProcess verifyProcess : verifyProcesses) {
				try {
					verifyProcess.verify();
				}
				catch (VerifyException verifyException2) {
					_log.error(verifyException2);

					verifyException1 = verifyException2;
				}
			}

			if (verifyException1 == null) {
				release.setVerified(true);
				release.setState(ReleaseConstants.STATE_GOOD);

				_releaseLocalService.updateRelease(release);
			}
			else {
				release.setVerified(false);
				release.setState(ReleaseConstants.STATE_VERIFY_FAILURE);

				_releaseLocalService.updateRelease(release);
			}
		}
		finally {
			NotificationThreadLocal.setEnabled(true);
			StagingAdvicesThreadLocal.setEnabled(true);
			WorkflowThreadLocal.setEnabled(true);
		}
	}

	private List<VerifyProcess> _getVerifyProcesses(
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

	private boolean _isInitialDeployment(
		ServiceReference<VerifyProcess> serviceReference,
		VerifyProcess verifyProcess) {

		if (!GetterUtil.getBoolean(
				serviceReference.getProperty("initial.deployment"))) {

			return false;
		}

		Bundle bundle = FrameworkUtil.getBundle(verifyProcess.getClass());

		try {
			Collection<ServiceReference<Release>> releases =
				_bundleContext.getServiceReferences(
					Release.class,
					"(&(release.bundle.symbolic.name=" +
						bundle.getSymbolicName() + ")(release.initial=true))");

			if (!releases.isEmpty()) {
				return true;
			}
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new RuntimeException(invalidSyntaxException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyProcessTrackerOSGiCommands.class);

	private BundleContext _bundleContext;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	private ServiceTrackerMap<String, List<VerifyProcess>> _serviceTrackerMap;

}