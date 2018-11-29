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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.internal.index.updater.IndexUpdaterUtil;

import java.sql.Connection;

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

	@Descriptor("Update database indexes for a specific module via bundle ID")
	public String updateIndexes(long bundleId) throws Exception {
		Bundle bundle = _bundleContext.getBundle(bundleId);

		if (bundle == null) {
			throw new IllegalArgumentException(
				"Module " + bundleId + " does not exist");
		}

		if (IndexUpdaterUtil.isLiferayServiceBundle(bundle)) {
			IndexUpdaterUtil.updateIndexes(bundle);

			return "Completed update of indexes for module " + bundleId;
		}

		return "Module " + bundleId + " has no indexes associated with it";
	}

	@Descriptor(
		"Update database indexes for specific a module via symbolic name"
	)
	public String updateIndexes(String bundleSymbolicName) throws Exception {
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
	public String updateIndexesAll() throws Exception {
		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			for (Bundle bundle : _bundleContext.getBundles()) {
				if (!IndexUpdaterUtil.isLiferayServiceBundle(bundle)) {
					continue;
				}

				String indexesSQL = IndexUpdaterUtil.getSQLTemplateString(
					bundle, "indexes.sql");

				if (indexesSQL == null) {
					continue;
				}

				String tablesSQL = IndexUpdaterUtil.getSQLTemplateString(
					bundle, "tables.sql");

				if (tablesSQL == null) {
					continue;
				}

				String loggingTimerName =
					"Updating database indexes for " + bundle.getSymbolicName();

				try (LoggingTimer loggingTimer = new LoggingTimer(
						loggingTimerName)) {

					db.updateIndexes(connection, tablesSQL, indexesSQL, true);
				}
				catch (Exception e) {
					System.out.println(
						StringBundler.concat(
							"Unable to update indexes for ",
							bundle.getSymbolicName(), ": ", e));
				}
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