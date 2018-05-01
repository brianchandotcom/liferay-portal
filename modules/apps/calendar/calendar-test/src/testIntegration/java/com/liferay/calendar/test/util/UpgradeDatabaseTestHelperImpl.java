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

package com.liferay.calendar.test.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.SQLException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Adam Brandizzi
 */
public class UpgradeDatabaseTestHelperImpl
	extends UpgradeProcess implements UpgradeDatabaseTestHelper {

	public UpgradeDatabaseTestHelperImpl(Connection connection)
		throws SQLException {

		this.connection = connection;
	}

	@Override
	public void close() throws Exception {
		connection.close();

		connection = null;
	}

	@Override
	public void dropColumn(
			String tableClassName, String tableName, String columnName)
		throws Exception {

		if (hasColumn(tableName, columnName)) {

			// Hack through the OSGi classloading, it is not worth exporting
			// the generated *Table packages just to support this test

			alter(
				_CLASS_LOADER.loadClass(tableClassName),
				new AlterTableDropColumn(columnName));
		}
	}

	@Override
	public boolean hasColumn(String tableName, String columnName)
		throws Exception {

		return super.hasColumn(tableName, columnName);
	}

	@Override
	protected void doUpgrade() throws Exception {
		throw new UnsupportedOperationException();
	}

	private static final ClassLoader _CLASS_LOADER;

	static {
		Bundle testBundle = FrameworkUtil.getBundle(
			UpgradeDatabaseTestHelperImpl.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		Bundle calendarServiceBundle = null;

		for (Bundle bundle : bundleContext.getBundles()) {
			String symbolicName = bundle.getSymbolicName();

			if (symbolicName.equals("com.liferay.calendar.service")) {
				calendarServiceBundle = bundle;

				break;
			}
		}

		BundleWiring bundleWiring = calendarServiceBundle.adapt(
			BundleWiring.class);

		_CLASS_LOADER = bundleWiring.getClassLoader();
	}

}