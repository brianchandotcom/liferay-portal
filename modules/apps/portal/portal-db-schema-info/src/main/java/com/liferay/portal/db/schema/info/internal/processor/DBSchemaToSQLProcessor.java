/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.info.internal.processor;

import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.db.schema.info.internal.sql.SQLRecorder;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactory;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.release.SchemaCreator;

import java.util.Collection;
import java.util.ServiceLoader;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaToSQLProcessor {

	public DBSchemaToSQLProcessor(DBType dbType, SQLRecorder sqlRecorder) {
		_sqlRecorder = sqlRecorder;

		_db = _getDB(dbType);
	}

	public void process() throws Exception {
		_generatePortalSQL();
		_generateSQLTemplates();
		_generateModulesSQL();
	}

	private void _generateModulesSQL() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Collection<ServiceReference<SchemaCreator>> serviceReferences =
			bundleContext.getServiceReferences(SchemaCreator.class, null);

		for (ServiceReference<SchemaCreator> serviceReference :
				serviceReferences) {

			_sqlRecorder.recordIndexesSQL(
				_db.buildSQL(
					DBResourceUtil.getModuleIndexesSQL(
						serviceReference.getBundle())));
			_sqlRecorder.recordTablesSQL(
				_db.buildSQL(
					DBResourceUtil.getModuleTablesSQL(
						serviceReference.getBundle())));
		}
	}

	private void _generatePortalSQL() throws Exception {
		_sqlRecorder.recordIndexesSQL(
			_db.buildSQL(DBResourceUtil.getPortalIndexesSQL()));
		_sqlRecorder.recordTablesSQL(
			_db.buildSQL(DBResourceUtil.getPortalTablesSQL()));
	}

	private void _generateSQLTemplates() throws Exception {
		for (String sqlTemplate : DBResourceUtil.getSQLTemplates()) {
			String[] lines = StringUtil.splitLines(
				_db.buildSQL(
					StringUtil.removeSubstring(
						sqlTemplate, "COMMIT_TRANSACTION;")));

			for (String line : lines) {
				_sqlRecorder.recordSQL(line);
			}
		}
	}

	private DB _getDB(DBType dbType) {
		ServiceLoader<DBFactory> serviceLoader = ServiceLoader.load(
			DBFactory.class, DBFactory.class.getClassLoader());

		for (DBFactory dbFactory : serviceLoader) {
			if (dbFactory.getDBType() == dbType) {
				return dbFactory.create(0, 0);
			}
		}

		throw new IllegalArgumentException("Illegal database type " + dbType);
	}

	private final DB _db;
	private final SQLRecorder _sqlRecorder;

}