/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.internal.osgi.commands;

import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemCheckerUtil;

import java.util.Dictionary;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	property = {"osgi.command.function=check", "osgi.command.scope=system"},
	service = {}
)
public class SystemCheckOSGiCommands {

	public void check() {
		SystemCheckerUtil.runSystemCheckers(
			System.out::println, System.out::println);
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		if (_log.isInfoEnabled()) {
			_log.info(
				"System check is enabled. You can run a system check with " +
					"the command \"system:check\" in Gogo shell.");
		}

		boolean checkEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.INITIAL_SYSTEM_CHECK_ENABLED), true);

		if (checkEnabled) {
			DependencyManagerSyncUtil.sync();

			if (_log.isInfoEnabled()) {
				_log.info("Running system check");
			}

			SystemCheckerUtil.runSystemCheckers(_log::info, _log::warn);
		}

		Dictionary<String, Object> osgiCommandProperties =
			new HashMapDictionary<>();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith("osgi.command.")) {
				osgiCommandProperties.put(key, entry.getValue());
			}
		}

		_serviceRegistration = bundleContext.registerService(
			SystemCheckOSGiCommands.class, this, osgiCommandProperties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemCheckOSGiCommands.class);

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Props _props;

	private ServiceRegistration<?> _serviceRegistration;

}