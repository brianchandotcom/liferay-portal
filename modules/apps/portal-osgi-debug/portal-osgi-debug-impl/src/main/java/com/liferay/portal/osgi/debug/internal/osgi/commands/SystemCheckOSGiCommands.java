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

package com.liferay.portal.osgi.debug.internal.osgi.commands;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.debug.SystemChecker;

import java.util.Collection;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Tina Tian
 */
@Component(
	immediate = true,
	property = {"osgi.command.function=check", "osgi.command.scope=system"},
	service = SystemCheckOSGiCommands.class
)
public class SystemCheckOSGiCommands {

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, SystemChecker.class, null);

		_serviceTracker.open();

		if (_log.isInfoEnabled()) {
			_log.info(
				"System check is enabled. You can run a system check with " +
					"the command \"system:check\" in Gogo shell.");
		}

		if (GetterUtil.getBoolean(
				bundleContext.getProperty("initial.system.check.enabled"),
				true)) {

			DependencyManagerSyncUtil.sync();

			if (_log.isInfoEnabled()) {
				_log.info("Running system check");
			}

			_check(false);
		}
	}

	public void check() {
		_check(true);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private void _check(boolean useSystemOut) {
		Map<ServiceReference<SystemChecker>, SystemChecker> systemCheckerMap =
			_serviceTracker.getTracked();

		Collection<SystemChecker> systemCheckers = systemCheckerMap.values();

		if (useSystemOut) {
			System.out.println("Available checkers: " + systemCheckers);
		}
		else if (_log.isInfoEnabled()) {
			_log.info("Available checkers :" + systemCheckers);
		}

		for (SystemChecker systemChecker : systemCheckers) {
			StringBundler sb = new StringBundler(5);

			sb.append("Running \"");
			sb.append(systemChecker.getName());
			sb.append("\". You can run this by itself with command \"");
			sb.append(systemChecker.getOSGiCommand());
			sb.append("\" in gogo shell.");

			if (useSystemOut) {
				System.out.println(sb.toString());
			}
			else if (_log.isInfoEnabled()) {
				_log.info(sb.toString());
			}

			String result = systemChecker.check();

			if (Validator.isNull(result)) {
				if (useSystemOut) {
					System.out.println(
						systemChecker.getName() +
							" check result: No issues were found.");
				}
				else if (_log.isInfoEnabled()) {
					_log.info(
						systemChecker.getName() +
							" check result: No issues were found.");
				}
			}
			else {
				if (useSystemOut) {
					System.out.println(
						systemChecker.getName() + " check result: " + result);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(
						systemChecker.getName() + " check result: " + result);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemCheckOSGiCommands.class);

	private ServiceTracker<SystemChecker, SystemChecker> _serviceTracker;

}