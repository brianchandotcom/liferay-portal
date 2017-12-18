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

package com.liferay.commerce.service;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceServiceXmlTest {

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.setProps(new PropsImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReader = new SAXReaderImpl();

		secureSAXReader.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReader);

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReader = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReader);
	}

	@Test
	public void testServiceXml() throws IOException {
		Files.walkFileTree(
			Paths.get("../"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path serviceXmlPath = dirPath.resolve("service.xml");

					if (Files.exists(serviceXmlPath)) {
						try {
							_testServiceXml(serviceXmlPath);
						}
						catch (IOException ioe) {
							throw ioe;
						}
						catch (Exception e) {
							throw new IOException(e);
						}

						return FileVisitResult.SKIP_SUBTREE;
					}

					if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _testServiceXml(Path serviceXmlPath) throws Exception {
		Document document = SAXReaderUtil.read(serviceXmlPath.toFile());

		Element serviceBuilderElement = document.getRootElement();

		String tableNamePrefix = StringPool.BLANK;

		boolean autoNamespaceTables = GetterUtil.getBoolean(
			serviceBuilderElement.attributeValue(
				"auto-namespace-tables", StringPool.TRUE));

		if (autoNamespaceTables) {
			tableNamePrefix =
				serviceBuilderElement.elementText("namespace") +
					StringPool.UNDERLINE;
		}

		for (Element entityElement : serviceBuilderElement.elements("entity")) {
			String entityName = entityElement.attributeValue("name");

			String tableName = entityElement.attributeValue(
				"table", tableNamePrefix + entityName);

			Assert.assertTrue(
				"Table name \"" + tableName + "\" of entity \"" + entityName +
					"\" defined in " + serviceXmlPath +
						" must be at most 30 characters long",
				tableName.length() <= 30);

			for (Element columnElement : entityElement.elements("column")) {
				String columnName = columnElement.attributeValue("name");

				String columnDbName = columnElement.attributeValue(
					"db-name", columnName);

				Assert.assertTrue(
					"Column name \"" + columnDbName + "\" of entity \"" +
						entityName + "\" defined in " + serviceXmlPath +
							" must be at most 30 characters long",
					columnDbName.length() <= 30);
			}
		}
	}

}