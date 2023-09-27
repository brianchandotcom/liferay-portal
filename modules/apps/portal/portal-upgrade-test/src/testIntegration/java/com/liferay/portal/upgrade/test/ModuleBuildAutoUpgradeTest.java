/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.InputStream;

import java.sql.Types;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.junit.Before;

import org.osgi.framework.Constants;

/**
 * @author Preston Crary
 */
public class ModuleBuildAutoUpgradeTest extends BaseBuildAutoUpgradeTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_jarBytes[0] = _createBundleBytes(
			new Object[][] {{"id_", Types.BIGINT}, {"data_", Types.VARCHAR}},
			1);

		_jarBytes[1] = _createBundleBytes(
			new Object[][] {
				{"id_", Types.BIGINT}, {"data_", Types.VARCHAR},
				{"data2", Types.VARCHAR}
			},
			2);

		_jarBytes[2] = _createBundleBytes(
			new Object[][] {{"id_", Types.BIGINT}, {"data2", Types.VARCHAR}},
			3);

		_jarBytes[3] = _createBundleBytes(
			new Object[][] {{"id_", Types.BIGINT}, {"data_", Types.VARCHAR}},
			4);
	}

	@Override
	protected InputStream getBundleInputStream(int version) {
		return new UnsyncByteArrayInputStream(_jarBytes[version - 1]);
	}

	private byte[] _createBundleBytes(Object[][] tableColumns, int version)
		throws Exception {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();
			JarOutputStream jarOutputStream = new JarOutputStream(
				unsyncByteArrayOutputStream)) {

			Manifest manifest = new Manifest();

			Attributes attributes = manifest.getMainAttributes();

			attributes.putValue("Manifest-Version", "1.0");
			attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
			attributes.putValue(
				Constants.BUNDLE_NAME, "Build Auto Upgrade Test");
			attributes.putValue(
				Constants.BUNDLE_SYMBOLICNAME, BUNDLE_SYMBOLIC_NAME);
			attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
			attributes.putValue("Liferay-Require-SchemaVersion", "1.0.0");
			attributes.putValue("Liferay-Service", Boolean.TRUE.toString());
			attributes.putValue("Liferay-Spring-Context", "META-INF/spring");

			jarOutputStream.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));

			manifest.write(jarOutputStream);

			jarOutputStream.closeEntry();

			String createSQL = toCreateSQL(tableColumns);

			addClass(ENTITY_PATH, jarOutputStream, tableColumns, createSQL);

			addResource("META-INF/portlet-model-hints.xml", jarOutputStream);
			addResource("META-INF/spring/module-spring.xml", jarOutputStream);

			addEmptyResource("META-INF/sql/indexes.sql", jarOutputStream);
			addEmptyResource("META-INF/sql/sequences.sql", jarOutputStream);

			addResource(
				"META-INF/sql/tables.sql", createSQL.getBytes(StringPool.UTF8),
				jarOutputStream);

			addServiceProperties(
				version, "service.properties", jarOutputStream);

			jarOutputStream.finish();

			return unsyncByteArrayOutputStream.toByteArray();
		}
	}

	private final byte[][] _jarBytes = new byte[4][];

}