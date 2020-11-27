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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.internal.executor.SwappedLogExecutor;
import com.liferay.portal.upgrade.internal.executor.UpgradeExecutor;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;
import com.liferay.portal.upgrade.internal.release.ReleaseManager;

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
	immediate = true,
	property = {
		"osgi.command.function=check", "osgi.command.function=execute",
		"osgi.command.function=executeAll", "osgi.command.function=list",
		"osgi.command.scope=upgrade"
	},
	service = ReleaseManagerOSGiCommands.class
)
public class ReleaseManagerOSGiCommands {

	@Descriptor("List pending or running upgrades")
	public String check() {
		StringBundler sb = new StringBundler(0);

		Set<String> bundleSymbolicNames =
			_releaseManager.getBundleSymbolicNames();

		for (String bundleSymbolicName : bundleSymbolicNames) {
			String currentSchemaVersion =
				_releaseManager.getCurrentSchemaVersion(bundleSymbolicName);

			ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
				_releaseManager.getUpgradeInfos(bundleSymbolicName));

			List<List<UpgradeInfo>> upgradeInfosList =
				releaseGraphManager.getUpgradeInfosList(currentSchemaVersion);

			int size = upgradeInfosList.size();

			if (size > 1) {
				sb.append("There are ");
				sb.append(size);
				sb.append(" possible end nodes for ");
				sb.append(currentSchemaVersion);
				sb.append(StringPool.NEW_LINE);
			}

			if (size == 0) {
				continue;
			}

			sb.append("There is an upgrade process available for ");
			sb.append(bundleSymbolicName);
			sb.append(" from ");
			sb.append(currentSchemaVersion);
			sb.append(" to ");

			List<UpgradeInfo> upgradeInfos = upgradeInfosList.get(0);

			UpgradeInfo lastUpgradeInfo = upgradeInfos.get(
				upgradeInfos.size() - 1);

			sb.append(lastUpgradeInfo.getToSchemaVersionString());

			sb.append(StringPool.NEW_LINE);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);

			return sb.toString();
		}

		return null;
	}

	@Descriptor("Execute upgrade for a specific module")
	public String execute(String bundleSymbolicName) {
		List<UpgradeInfo> upgradeInfos = _releaseManager.getUpgradeInfos(
			bundleSymbolicName);

		if (upgradeInfos == null) {
			return "No upgrade processes registered for " + bundleSymbolicName;
		}

		try {
			_upgradeExecutor.execute(bundleSymbolicName, upgradeInfos, null);
		}
		catch (Throwable throwable) {
			_swappedLogExecutor.execute(
				bundleSymbolicName,
				() -> _log.error(
					"Failed upgrade process for module ".concat(
						bundleSymbolicName),
					throwable),
				null);
		}

		return null;
	}

	@Descriptor("Execute upgrade for a specific module and final version")
	public String execute(String bundleSymbolicName, String toVersionString) {
		List<UpgradeInfo> upgradeInfos = _releaseManager.getUpgradeInfos(
			bundleSymbolicName);

		if (upgradeInfos == null) {
			return "No upgrade processes registered for " + bundleSymbolicName;
		}

		String currentSchemaVersion = _releaseManager.getCurrentSchemaVersion(
			bundleSymbolicName);

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			upgradeInfos);

		_upgradeExecutor.executeUpgradeInfos(
			bundleSymbolicName,
			releaseGraphManager.getUpgradeInfos(
				currentSchemaVersion, toVersionString),
			null);

		return null;
	}

	@Descriptor("Execute all pending upgrades")
	public String executeAll() {
		Set<String> upgradeThrewExceptionBundleSymbolicNames = new HashSet<>();

		executeAll(upgradeThrewExceptionBundleSymbolicNames);

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
			_releaseManager.getBundleSymbolicNames();

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
		List<UpgradeInfo> upgradeInfos = _releaseManager.getUpgradeInfos(
			bundleSymbolicName);

		StringBundler sb = new StringBundler(5 + (3 * upgradeInfos.size()));

		sb.append("Registered upgrade processes for ");
		sb.append(bundleSymbolicName);
		sb.append(StringPool.SPACE);
		sb.append(_releaseManager.getCurrentSchemaVersion(bundleSymbolicName));
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

		Set<String> upgradableBundleSymbolicNames =
			_releaseManager.getUpgradableBundleSymbolicNames();

		upgradableBundleSymbolicNames.removeAll(
			upgradeThrewExceptionBundleSymbolicNames);

		if (upgradableBundleSymbolicNames.isEmpty()) {
			return;
		}

		for (String upgradableBundleSymbolicName :
				upgradableBundleSymbolicNames) {

			try {
				List<UpgradeInfo> upgradeInfos =
					_releaseManager.getUpgradeInfos(
						upgradableBundleSymbolicName);

				_upgradeExecutor.execute(
					upgradableBundleSymbolicName, upgradeInfos, null);
			}
			catch (Throwable throwable) {
				_swappedLogExecutor.execute(
					upgradableBundleSymbolicName,
					() -> _log.error(
						"Failed upgrade process for module ".concat(
							upgradableBundleSymbolicName),
						throwable),
					null);

				upgradeThrewExceptionBundleSymbolicNames.add(
					upgradableBundleSymbolicName);
			}
		}

		executeAll(upgradeThrewExceptionBundleSymbolicNames);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseManagerOSGiCommands.class);

	@Reference
	private ReleaseManager _releaseManager;

	@Reference
	private SwappedLogExecutor _swappedLogExecutor;

	@Reference
	private UpgradeExecutor _upgradeExecutor;

}