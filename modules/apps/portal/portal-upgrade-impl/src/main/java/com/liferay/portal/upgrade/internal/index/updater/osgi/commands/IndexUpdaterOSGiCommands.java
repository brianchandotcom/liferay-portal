/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.index.updater.osgi.commands;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.kernel.module.util.BundleUtil;

import org.apache.felix.service.command.Descriptor;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Ricardo Couso
 */
@Component(
	property = {
		"osgi.command.function=updateIndexes",
		"osgi.command.function=updateIndexesAll", "osgi.command.scope=upgrade"
	},
	service = IndexUpdaterOSGiCommands.class
)
public class IndexUpdaterOSGiCommands {

	@Descriptor("Update database indexes for a specific module via bundle ID")
	public String updateIndexes(long bundleId) throws Exception {
		Bundle bundle = _bundleContext.getBundle(bundleId);

		if (bundle == null) {
			throw new IllegalArgumentException(
				"Module " + bundleId + " does not exist");
		}

		if (BundleUtil.isLiferayServiceBundle(bundle)) {
			IndexUpdaterUtil.updateIndexes(bundle);

			return "Completed update of indexes for module " + bundleId;
		}

		return "Module " + bundleId + " has no indexes associated with it";
	}

	@Descriptor(
		"Update database indexes for specific a module via symbolic name"
	)
	public String updateIndexes(String bundleSymbolicName) throws Exception {
		Bundle bundle = BundleUtil.getBundle(
			_bundleContext, bundleSymbolicName);

		if (bundle == null) {
			throw new IllegalArgumentException(
				"Module with symbolic name " + bundleSymbolicName +
					" does not exist");
		}

		if (BundleUtil.isLiferayServiceBundle(bundle)) {
			IndexUpdaterUtil.updateIndexes(bundle);

			return "Completed update of indexes for module " +
				bundleSymbolicName;
		}

		return "Module " + bundleSymbolicName +
			" has no indexes associated with it";
	}

	@Descriptor("Update database indexes for all modules")
	public String updateIndexesAll() throws Exception {
		for (Bundle bundle : _bundleContext.getBundles()) {
			try {
				IndexUpdaterUtil.updateIndexes(bundle);
			}
			catch (Exception exception) {
				System.out.println(
					StringBundler.concat(
						"Unable to update indexes for ",
						bundle.getSymbolicName(), ": ", exception));
			}
		}

		return "Completed updating module database indexes";
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

}