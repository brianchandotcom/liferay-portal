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

package com.liferay.portal.upgrade.internal.index.updater.osgi.commands;

import com.liferay.portal.upgrade.internal.index.updater.IndexUpdater;

import org.apache.felix.service.command.Descriptor;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Ricardo Couso
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=updateIndexes",
		"osgi.command.function=updateIndexesAll", "osgi.command.scope=upgrade"
	},
	service = IndexUpdaterOSGiCommands.class
)
public class IndexUpdaterOSGiCommands {

	@Activate
	public void activate(final BundleContext bundleContext) {
		_bundleContext = bundleContext;
		_indexUpdater = new IndexUpdater();
	}

	@Descriptor("Update database indexes for a specific module via bundle id")
	public String updateIndexes(long bundleId) {
		try {
			if (_indexUpdater.hasIndexes(bundleId)) {
				_indexUpdater.updateIndexes(bundleId);

				return "Completed update of indexes for module with id " +
					bundleId;
			}
		}
		catch (IllegalArgumentException iae) {
			return "Module with id " + bundleId + " does not exist";
		}

		return "Module with id " + bundleId +
			" has no indexes associated with it";
	}

	@Descriptor(
		"Update database indexes for specific a module via symbolic name"
	)
	public String updateIndexes(String bundleSymbolicName) {
		try {
			if (_indexUpdater.hasIndexes(bundleSymbolicName)) {
				_indexUpdater.updateIndexes(bundleSymbolicName);

				return "Completed update of indexes for module " +
					bundleSymbolicName;
			}
		}
		catch (IllegalArgumentException iae) {
			return "Module with symbolic name " + bundleSymbolicName +
				" does not exist";
		}

		return "Module " + bundleSymbolicName +
			" has no indexes associated with it";
	}

	@Descriptor("Update database indexes for all modules")
	public String updateIndexesAll() {
		_indexUpdater.updateIndexesAll();

		return "Completed update of indexes for all modules";
	}

	private BundleContext _bundleContext;
	private IndexUpdater _indexUpdater;

}