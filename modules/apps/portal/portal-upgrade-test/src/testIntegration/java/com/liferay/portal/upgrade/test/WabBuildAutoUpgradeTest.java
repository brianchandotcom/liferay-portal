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

package com.liferay.portal.upgrade.test;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;
import java.net.URL;

import java.sql.Types;

import java.util.jar.JarOutputStream;

import org.junit.After;
import org.junit.Before;

import org.osgi.framework.Constants;

/**
 * @author Preston Crary
 */
public class WabBuildAutoUpgradeTest extends BaseBuildAutoUpgradeTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_warFiles[0] = _createWabBundleFile(
			new Object[][] {{"id_", Types.BIGINT}, {"data_", Types.VARCHAR}},
			1);

		_warFiles[1] = _createWabBundleFile(
			new Object[][] {
				{"id_", Types.BIGINT}, {"data_", Types.VARCHAR},
				{"data2", Types.VARCHAR}
			},
			2);

		_warFiles[2] = _createWabBundleFile(
			new Object[][] {{"id_", Types.BIGINT}, {"data2", Types.VARCHAR}},
			3);

		_warFiles[3] = _createWabBundleFile(
			new Object[][] {{"id_", Types.BIGINT}, {"data_", Types.VARCHAR}},
			4);

		for (int i = 0; i < _warURLs.length; i++) {
			URI uri = _warFiles[i].toURI();

			URL url = uri.toURL();

			StringBundler sb = new StringBundler(5);

			sb.append(url.getPath());
			sb.append("?");
			sb.append(Constants.BUNDLE_SYMBOLICNAME);
			sb.append("=BuildAutoUpgradeTest&Web-ContextPath=");
			sb.append("/BuildAutoUpgradeTest&protocol=file");

			_warURLs[i] = new URL("webbundle", null, sb.toString());
		}
	}

	@After
	@Override
	public void tearDown() throws Throwable {
		super.tearDown();

		for (File file : _warFiles) {
			if (file != null) {
				file.delete();
			}
		}
	}

	@Override
	protected InputStream getBundleInputStream(int version) throws IOException {
		return _warURLs[version - 1].openStream();
	}

	private File _createWabBundleFile(Object[][] tableColumns, int version)
		throws IOException {

		File file = FileUtil.createTempFile("war");

		try (OutputStream outputStream = new FileOutputStream(file);
			JarOutputStream jarOutputStream = new JarOutputStream(
				outputStream)) {

			String createSQL = toCreateSQL(tableColumns);

			addClass(
				"WEB-INF/classes/" + ENTITY_PATH, jarOutputStream, tableColumns,
				createSQL);

			addResource(
				"dependencies/portlet.xml", "WEB-INF/portlet.xml",
				jarOutputStream);
			addResource(
				"dependencies/service/META-INF/portlet-model-hints.xml",
				"WEB-INF/classes/META-INF/portlet-model-hints.xml",
				jarOutputStream);
			addResource(
				"dependencies/service/META-INF/spring/module-spring.xml",
				"WEB-INF/classes/META-INF/portlet-spring.xml", jarOutputStream);

			addEmptyResource("WEB-INF/sql/indexes.sql", jarOutputStream);
			addEmptyResource("WEB-INF/sql/sequences.sql", jarOutputStream);

			addResource(
				"WEB-INF/sql/tables.sql", createSQL.getBytes(StringPool.UTF8),
				jarOutputStream);

			addServiceProperties(
				version, "WEB-INF/classes/service.properties", jarOutputStream);

			jarOutputStream.finish();

			return file;
		}
	}

	private final File[] _warFiles = new File[4];
	private final URL[] _warURLs = new URL[4];

}