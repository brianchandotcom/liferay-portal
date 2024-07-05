/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.processor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactory;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.release.SchemaCreator;

import java.util.Collection;
import java.util.ServiceLoader;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Mariano Álvaro Sáiz
 */
public class SQLFilesProcessor {

	public SQLFilesProcessor(DBType dbType) throws Exception {
		_db = _getDB(dbType);

		_objectSQLProcessor = new ObjectSQLProcessor(_db);

		_generatePortalSQL();

		_generateModulesSQL();
	}

	public String getIndexesSQL() {
		return _indexesSQLSB.toString() + StringPool.NEW_LINE +
			_objectSQLProcessor.getIndexesSQL();
	}

	public String getTablesSQL() {
		return _tablesSQLSB.toString() + StringPool.NEW_LINE +
			_objectSQLProcessor.getTablesSQL();
	}

	private void _appendSQL(String indexesSQL, String tablesSQL)
		throws Exception {

		if (Validator.isNotNull(indexesSQL)) {
			_indexesSQLSB.append(_db.buildSQL(indexesSQL));
		}

		if (Validator.isNotNull(tablesSQL)) {
			_tablesSQLSB.append(_db.buildSQL(tablesSQL));
		}
	}

	private void _generateModulesSQL() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Collection<ServiceReference<SchemaCreator>> serviceReferences =
			bundleContext.getServiceReferences(SchemaCreator.class, null);

		for (ServiceReference<SchemaCreator> serviceReference :
				serviceReferences) {

			_appendSQL(
				DBResourceUtil.getModuleIndexesSQL(
					serviceReference.getBundle()),
				DBResourceUtil.getModuleTablesSQL(
					serviceReference.getBundle()));
		}
	}

	private void _generatePortalSQL() throws Exception {
		_appendSQL(
			DBResourceUtil.getPortalIndexesSQL(),
			DBResourceUtil.getPortalTablesSQL());
	}

	private DB _getDB(DBType dbType) {
		ServiceLoader<DBFactory> serviceLoader = ServiceLoader.load(
			DBFactory.class, DBFactory.class.getClassLoader());

		for (DBFactory dbFactory : serviceLoader) {
			if (dbFactory.getDBType() == dbType) {
				return dbFactory.create(0, 0);
			}
		}

		throw new IllegalArgumentException("Database type " + dbType);
	}

	private final DB _db;
	private final StringBundler _indexesSQLSB = new StringBundler();
	private final ObjectSQLProcessor _objectSQLProcessor;
	private final StringBundler _tablesSQLSB = new StringBundler();

}