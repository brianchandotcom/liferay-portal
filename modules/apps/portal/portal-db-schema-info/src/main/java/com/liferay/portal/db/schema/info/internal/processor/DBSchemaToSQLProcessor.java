/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.info.internal.processor;

import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.db.schema.info.internal.sql.FakeDBFactory;
import com.liferay.portal.db.schema.info.internal.sql.SQLRecorder;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.release.SchemaCreator;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaToSQLProcessor {

	public DBSchemaToSQLProcessor(DBType dbType, SQLRecorder sqlRecorder) {
		_sqlRecorder = sqlRecorder;

		_fakeDB = FakeDBFactory.getDB(dbType);
	}

	public void process() throws Exception {
		_generatePortalSQL();
		_generateMiscellaneousSQL();
		_generateModulesSQL();
	}

	private void _generateMiscellaneousSQL() throws Exception {
		for (String sqlTemplate :
				DBResourceUtil.getMiscellaneousSQLTemplates()) {

			String[] lines = StringUtil.splitLines(
				_fakeDB.buildSQL(
					StringUtil.removeSubstring(
						sqlTemplate, "COMMIT_TRANSACTION;")));

			for (String line : lines) {
				_sqlRecorder.recordSQL(line);
			}
		}
	}

	private void _generateModulesSQL() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Collection<ServiceReference<SchemaCreator>> serviceReferences =
			bundleContext.getServiceReferences(SchemaCreator.class, null);

		for (ServiceReference<SchemaCreator> serviceReference :
				serviceReferences) {

			_sqlRecorder.recordIndexesSQL(
				_fakeDB.buildSQL(
					DBResourceUtil.getModuleIndexesSQL(
						serviceReference.getBundle())));
			_sqlRecorder.recordTablesSQL(
				_fakeDB.buildSQL(
					DBResourceUtil.getModuleTablesSQL(
						serviceReference.getBundle())));
		}
	}

	private void _generatePortalSQL() throws Exception {
		_sqlRecorder.recordIndexesSQL(
			_fakeDB.buildSQL(DBResourceUtil.getPortalIndexesSQL()));
		_sqlRecorder.recordTablesSQL(
			_fakeDB.buildSQL(DBResourceUtil.getPortalTablesSQL()));
	}

	private final DB _fakeDB;
	private final SQLRecorder _sqlRecorder;

}