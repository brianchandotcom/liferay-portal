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

package com.liferay.portal.log.assertor;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Shuyang Zhou
 */
public class PortalLogAssertorTest {

	@BeforeClass
	public static void setUpClass() {
		Assume.assumeNotNull(System.getenv("JENKINS_HOME"));
	}

	@Test
	public void testScanOsgiLog() throws IOException {
		StringBundler sb = new StringBundler();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
			Paths.get(System.getProperty("osgi.state.dir")), "*.log")) {

			for (Path path : directoryStream) {
				if (!Files.isRegularFile(path)) {
					continue;
				}

				sb.append("\nPortal log assert failure, OSGi log found: ");
				sb.append(path);
				sb.append(StringPool.COLON);
				sb.append(StringPool.NEW_LINE);
				sb.append(
					new String(
						Files.readAllBytes(path), Charset.defaultCharset()));
				sb.append(StringPool.NEW_LINE);
			}
		}

		if (sb.index() != 0) {
			Assert.fail(sb.toString());
		}
	}

	@Test
	public void testScanXmlLog() throws IOException {
		Files.walkFileTree(
			Paths.get(System.getProperty("liferay.log.dir")),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String pathString = StringUtil.toLowerCase(path.toString());

					if (pathString.endsWith(".xml")) {
						scanXmlLogFile(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	protected void scanXmlLogFile(Path path) throws IOException {
		String content = StringUtil.replace(
			new String(Files.readAllBytes(path), StringPool.UTF8), "log4j:",
			"");

		content = "<log4j>" + content + "</log4j>";

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(
				new InputSource(new UnsyncStringReader(content)));

			NodeList nodelist = document.getElementsByTagName("event");

			for (int i = 0; i < nodelist.getLength(); i++) {
				Node node = nodelist.item(i);

				NamedNodeMap namedNodeMap = node.getAttributes();

				Node levelNode = namedNodeMap.getNamedItem("level");

				String levelString = levelNode.getNodeValue();

				if (levelString.equals("ERROR") ||
					levelString.equals("FATAL") || levelString.equals("WARN")) {

					NodeList childNodelist = node.getChildNodes();

					String message =
						"\nPortal log assert failure, see above log for more " +
							"information: \n";

					for (int j = 0; j < childNodelist.getLength(); j++) {
						Node childNode = childNodelist.item(j);

						String nodeName = childNode.getNodeName();

						if (nodeName.equals("message")) {
							message += childNode.getTextContent();
						}
						else if (nodeName.equals("throwable")) {
							message += "\n" + childNode.getTextContent();
						}
					}

					System.out.println(
						"Detected error, dumpping full log for reference:");

					Files.copy(
						Paths.get(
							StringUtil.replace(
								path.toString(), ".xml", ".log")),
						System.out);

					Assert.fail(message);
				}
			}
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

}