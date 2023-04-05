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

package com.liferay.portal.upgrade.internal.release.osgi.commands;

import com.liferay.gogo.shell.logging.TeeLoggingUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.internal.executor.UpgradeExecutor;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;
import com.liferay.portal.upgrade.internal.release.ReleaseManagerImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {
		"osgi.command.function=check", "osgi.command.function=checkAll",
		"osgi.command.function=execute", "osgi.command.function=executeAll",
		"osgi.command.function=list", "osgi.command.scope=upgrade"
	},
	service = ReleaseManagerOSGiCommands.class
)
public class ReleaseManagerOSGiCommands {

	@Descriptor("List pending upgrades")
	public String check() {
		return _releaseManagerImpl.check(false);
	}

	@Descriptor("List pending upgrade processes and their upgrade steps")
	public String checkAll() {
		return _releaseManagerImpl.check(true);
	}

	@Descriptor("Execute upgrade for a specific module")
	public String execute(String bundleSymbolicName) {
		List<UpgradeInfo> upgradeInfos = _releaseManagerImpl.getUpgradeInfos(
			bundleSymbolicName);

		if (upgradeInfos == null) {
			return "No upgrade processes registered for " + bundleSymbolicName;
		}

		TeeLoggingUtil.runWithTeeLogging(
			() -> {
				try {
					_upgradeExecutor.execute(bundleSymbolicName, upgradeInfos);
				}
				catch (Throwable throwable) {
					_log.error(
						"Failed upgrade process for module ".concat(
							bundleSymbolicName),
						throwable);
				}
			});

		return null;
	}

	@Descriptor("Execute upgrade for a specific module and final version")
	public String execute(String bundleSymbolicName, String toVersionString) {
		List<UpgradeInfo> upgradeInfos = _releaseManagerImpl.getUpgradeInfos(
			bundleSymbolicName);

		if (upgradeInfos == null) {
			return "No upgrade processes registered for " + bundleSymbolicName;
		}

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			upgradeInfos);

		TeeLoggingUtil.runWithTeeLogging(
			() -> _upgradeExecutor.executeUpgradeInfos(
				bundleSymbolicName,
				releaseGraphManager.getUpgradeInfos(
					_releaseManagerImpl.getSchemaVersionString(
						bundleSymbolicName),
					toVersionString)));

		return null;
	}

	@Descriptor("Execute all pending upgrades")
	public String executeAll() {
		Set<String> upgradeThrewExceptionBundleSymbolicNames = new HashSet<>();

		TeeLoggingUtil.runWithTeeLogging(
			() -> executeAll(upgradeThrewExceptionBundleSymbolicNames));

		if (upgradeThrewExceptionBundleSymbolicNames.isEmpty()) {
			return "All modules were successfully upgraded";
		}

		StringBundler sb = new StringBundler(
			(upgradeThrewExceptionBundleSymbolicNames.size() * 3) + 3);

		sb.append("The following modules had errors while upgrading:\n");

		for (String upgradeThrewExceptionBundleSymbolicName :
				upgradeThrewExceptionBundleSymbolicNames) {

			sb.append(StringPool.TAB);
			sb.append(upgradeThrewExceptionBundleSymbolicName);
			sb.append(StringPool.NEW_LINE);
		}

		sb.append("Use the command upgrade:list <module name> to get more ");
		sb.append("details about the status of a specific upgrade.");

		return sb.toString();
	}

	@Descriptor("List registered upgrade processes for all modules")
	public String list() {
		Set<String> bundleSymbolicNames =
			_releaseManagerImpl.getBundleSymbolicNames();

		StringBundler sb = new StringBundler(2 * bundleSymbolicNames.size());

		for (String bundleSymbolicName : bundleSymbolicNames) {
			sb.append(list(bundleSymbolicName));
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	@Descriptor("List registered upgrade processes for a specific module")
	public String list(String bundleSymbolicName) {
		List<UpgradeInfo> upgradeInfos = _releaseManagerImpl.getUpgradeInfos(
			bundleSymbolicName);

		StringBundler sb = new StringBundler(5 + (3 * upgradeInfos.size()));

		sb.append("Registered upgrade processes for ");
		sb.append(bundleSymbolicName);
		sb.append(StringPool.SPACE);
		sb.append(
			_releaseManagerImpl.getSchemaVersionString(bundleSymbolicName));
		sb.append(StringPool.NEW_LINE);

		for (UpgradeInfo upgradeProcess : upgradeInfos) {
			sb.append(StringPool.TAB);
			sb.append(upgradeProcess);
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected void executeAll(
		Set<String> upgradeThrewExceptionBundleSymbolicNames) {

		while (true) {
			Set<String> upgradableBundleSymbolicNames =
				_releaseManagerImpl.getUpgradableBundleSymbolicNames();

			upgradableBundleSymbolicNames.removeAll(
				upgradeThrewExceptionBundleSymbolicNames);

			if (upgradableBundleSymbolicNames.isEmpty()) {
				return;
			}

			for (String upgradableBundleSymbolicName :
					upgradableBundleSymbolicNames) {

				try {
					List<UpgradeInfo> upgradeInfos =
						_releaseManagerImpl.getUpgradeInfos(
							upgradableBundleSymbolicName);

					_upgradeExecutor.execute(
						upgradableBundleSymbolicName, upgradeInfos);
				}
				catch (Throwable throwable) {
					_log.error(
						"Failed upgrade process for module ".concat(
							upgradableBundleSymbolicName),
						throwable);

					upgradeThrewExceptionBundleSymbolicNames.add(
						upgradableBundleSymbolicName);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseManagerOSGiCommands.class);

	@Reference
	private ReleaseManagerImpl _releaseManagerImpl;

	@Reference
	private UpgradeExecutor _upgradeExecutor;

}