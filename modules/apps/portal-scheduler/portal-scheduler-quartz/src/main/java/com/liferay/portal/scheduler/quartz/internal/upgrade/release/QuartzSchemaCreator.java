/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.release;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.db.DBResourceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.upgrade.release.SchemaCreator;

import java.sql.Connection;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = SchemaCreator.class)
public class QuartzSchemaCreator implements SchemaCreator {

	@Override
	public void create() throws UpgradeException {
		DB db = DBManagerUtil.getDB();

		String indexesSQL = DBResourceUtil.getModuleIndexesSQL(_bundle);
		String tablesSQL = DBResourceUtil.getModuleTablesSQL(_bundle);

		try (Connection connection = DataAccess.getConnection()) {
			db.runSQLTemplate(connection, tablesSQL, true);

			db.runSQLTemplate(connection, indexesSQL, true);
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

	@Override
	public String getBundleSymbolicName() {
		return _bundle.getSymbolicName();
	}

	@Override
	public String getSchemaVersion() {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(
			headers.get("Liferay-Require-SchemaVersion"), "1.0.0");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	private Bundle _bundle;

}