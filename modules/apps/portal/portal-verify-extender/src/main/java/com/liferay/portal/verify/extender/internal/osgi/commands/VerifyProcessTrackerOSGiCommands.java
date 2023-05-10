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
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.upgrade.log.UpgradeLogContext;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;

import org.apache.felix.service.command.Descriptor;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
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
	property = {
		"osgi.command.function=check", "osgi.command.function=checkAll",
		"osgi.command.function=execute", "osgi.command.function=executeAll",
		"osgi.command.function=help", "osgi.command.function=list",
		"osgi.command.function=show", "osgi.command.scope=verify"
	},
	service = {}
)
public class VerifyProcessTrackerOSGiCommands {

	@Descriptor(
		"List latest execution result for a module's verify process by symbolic name"
	)
	public void check(String bundleSymbolicName) {
		VerifyProcess verifyProcess;

		try {
			verifyProcess = _getVerifyProcess(
				_serviceTrackerMap, bundleSymbolicName);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalArgumentException);
			}

			System.out.println(
				"No verify process exists for " + bundleSymbolicName);

			return;
		}

		String message =
			"Verify process " + ClassUtil.getClassName(verifyProcess);

		Release release = _fetchRelease(verifyProcess);

		if ((release == null) ||
			(!release.isVerified() &&
			 (release.getState() == ReleaseConstants.STATE_GOOD))) {

			System.out.println(message + " was not executed");
		}
		else {
			if (release.isVerified()) {
				System.out.println(message + " succeeded");
			}
			else if (release.getState() ==
						ReleaseConstants.STATE_VERIFY_FAILURE) {

				System.out.println(message + " failed");
			}
		}
	}

	@Descriptor("List latest execution result for all verify processes")
	public void checkAll() {
		for (String bundleSymbolicName : _serviceTrackerMap.keySet()) {
			check(bundleSymbolicName);
		}
	}

	@Descriptor("Execute a module's verify process by symbolic name")
	public void execute(String bundleSymbolicName) {
		TeeLoggingUtil.runWithTeeLogging(
			() -> {
				VerifyProcess verifyProcess = _getVerifyProcess(
					_serviceTrackerMap, bundleSymbolicName);

				_executeVerifyProcess(
					verifyProcess, _fetchRelease(verifyProcess));
			});
	}

	@Descriptor("Execute all verify processes")
	public void executeAll() {
		TeeLoggingUtil.runWithTeeLogging(
			() -> {
				for (String bundleSymbolicName : _serviceTrackerMap.keySet()) {
					VerifyProcess verifyProcess = _getVerifyProcess(
						_serviceTrackerMap, bundleSymbolicName);

					_executeVerifyProcess(
						verifyProcess, _fetchRelease(verifyProcess));
				}
			});
	}

	@Descriptor("List all registered verify processes")
	public void list() {
		for (String bundleSymbolicName : _serviceTrackerMap.keySet()) {
			show(bundleSymbolicName);
		}
	}

	@Descriptor(
		"Show the verify process class name by a module's symbolic name"
	)
	public void show(String bundleSymbolicName) {
		VerifyProcess verifyProcess;

		try {
			verifyProcess = _getVerifyProcess(
				_serviceTrackerMap, bundleSymbolicName);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalArgumentException);
			}

			System.out.println(
				"No verify process exists for " + bundleSymbolicName);

			return;
		}

		System.out.println(
			StringBundler.concat(
				"Registered verify process ",
				ClassUtil.getClassName(verifyProcess), " for module ",
				bundleSymbolicName));
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, VerifyProcess.class, null,
			new ServiceReferenceMapper<String, VerifyProcess>() {

				@Override
				public void map(
					ServiceReference<VerifyProcess> serviceReference,
					Emitter<String> emitter) {

					Bundle bundle = serviceReference.getBundle();

					emitter.emit(bundle.getSymbolicName());
				}

			},
			new EagerServiceTrackerCustomizer<VerifyProcess, VerifyProcess>() {

				@Override
				public VerifyProcess addingService(
					ServiceReference<VerifyProcess> serviceReference) {

					VerifyProcess verifyProcess = bundleContext.getService(
						serviceReference);

					Release release = _fetchRelease(verifyProcess);

					boolean initialDeployment = _isInitialDeployment(
						bundleContext, release);

					if ((!initialDeployment && !release.isVerified()) ||
						(GetterUtil.getBoolean(
							serviceReference.getProperty(
								"initial.deployment")) &&
						 initialDeployment) ||
						(StartupHelperUtil.isUpgrading() &&
						 GetterUtil.getBoolean(
							 serviceReference.getProperty(
								 "run.on.portal.upgrade")))) {

						_executeVerifyProcess(verifyProcess, release);
					}
					else if (release == null) {
						release = _releaseLocalService.createRelease(
							_counterLocalService.increment());

						Bundle bundle = FrameworkUtil.getBundle(
							verifyProcess.getClass());

						release.setServletContextName(bundle.getSymbolicName());

						release.setVerified(true);
						release.setState(ReleaseConstants.STATE_GOOD);

						_releaseLocalService.updateRelease(release);
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

					bundleContext.ungetService(serviceReference);
				}

			});

		Dictionary<String, Object> osgiCommandProperties =
			new HashMapDictionary<>();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith("osgi.command.")) {
				osgiCommandProperties.put(key, entry.getValue());
			}
		}

		_serviceRegistration = bundleContext.registerService(
			VerifyProcessTrackerOSGiCommands.class, this,
			osgiCommandProperties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();

		_serviceTrackerMap.close();
	}

	private void _executeVerifyProcess(
		VerifyProcess verifyProcess, Release release) {

		NotificationThreadLocal.setEnabled(false);
		StagingAdvicesThreadLocal.setEnabled(false);
		WorkflowThreadLocal.setEnabled(false);

		try {
			Bundle bundle = FrameworkUtil.getBundle(verifyProcess.getClass());

			if (release == null) {

				// Verification state must be persisted even though not all
				// verifiers are associated with a database service

				release = _releaseLocalService.createRelease(
					_counterLocalService.increment());

				release.setServletContextName(bundle.getSymbolicName());

				release.setVerified(false);
			}

			System.out.println(
				"Executing verify " + ClassUtil.getClassName(verifyProcess));

			try {
				UpgradeLogContext.setContext(bundle.getSymbolicName());

				verifyProcess.verify();

				release.setVerified(true);
				release.setState(ReleaseConstants.STATE_GOOD);
			}
			catch (VerifyException verifyException) {
				_log.error(verifyException);

				release.setVerified(false);
				release.setState(ReleaseConstants.STATE_VERIFY_FAILURE);
			}
			finally {
				UpgradeLogContext.clearContext();
			}

			_releaseLocalService.updateRelease(release);
		}
		finally {
			NotificationThreadLocal.setEnabled(true);
			StagingAdvicesThreadLocal.setEnabled(true);
			WorkflowThreadLocal.setEnabled(true);
		}
	}

	private Release _fetchRelease(VerifyProcess verifyProcess) {
		Bundle bundle = FrameworkUtil.getBundle(verifyProcess.getClass());

		return _releaseLocalService.fetchRelease(bundle.getSymbolicName());
	}

	private VerifyProcess _getVerifyProcess(
		ServiceTrackerMap<String, VerifyProcess> verifyProcessTrackerMap,
		String bundleSymbolicName) {

		VerifyProcess verifyProcess = verifyProcessTrackerMap.getService(
			bundleSymbolicName);

		if (verifyProcess == null) {
			throw new IllegalArgumentException(
				"No verify processes exists for " + bundleSymbolicName);
		}

		return verifyProcess;
	}

	private boolean _isInitialDeployment(
		BundleContext bundleContext, Release release) {

		if (release == null) {
			return true;
		}

		try {
			Collection<ServiceReference<Release>> releases =
				bundleContext.getServiceReferences(
					Release.class,
					"(&(release.bundle.symbolic.name=" +
						release.getBundleSymbolicName() +
							")(release.initial=true))");

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

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	private ServiceRegistration<?> _serviceRegistration;
	private ServiceTrackerMap<String, VerifyProcess> _serviceTrackerMap;

}