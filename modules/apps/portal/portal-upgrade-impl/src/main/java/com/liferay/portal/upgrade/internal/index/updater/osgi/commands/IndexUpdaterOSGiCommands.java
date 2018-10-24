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

import com.liferay.portal.upgrade.internal.index.updater.IndexUpdaterUtil;

import org.apache.felix.service.command.Descriptor;

import org.osgi.framework.Bundle;
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

	@Descriptor("Update database indexes for a specific module via bundle id")
	public String updateIndexes(long bundleId) {
		Bundle bundle = IndexUpdaterUtil.getBundle(_bundleContext, bundleId);

		if (IndexUpdaterUtil.isLiferayServiceBundle(bundle)) {
			IndexUpdaterUtil.updateIndexes(bundle);

			return "Completed update of indexes for module with id " + bundleId;
		}

		return "Module with id " + bundleId +
			" has no indexes associated with it";
	}

	@Descriptor(
		"Update database indexes for specific a module via symbolic name"
	)
	public String updateIndexes(String bundleSymbolicName) {
		Bundle bundle = IndexUpdaterUtil.getBundle(
			_bundleContext, bundleSymbolicName);

		if (IndexUpdaterUtil.isLiferayServiceBundle(bundle)) {
			IndexUpdaterUtil.updateIndexes(bundle);

			return "Completed update of indexes for module " +
				bundleSymbolicName;
		}

		return "Module " + bundleSymbolicName +
			" has no indexes associated with it";
	}

	@Descriptor("Update database indexes for all modules")
	public String updateIndexesAll() {
		IndexUpdaterUtil.updateIndexesAll(_bundleContext);

		return "Completed update of indexes for all modules";
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

}