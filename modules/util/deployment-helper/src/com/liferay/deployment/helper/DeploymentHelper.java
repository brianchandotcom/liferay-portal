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

package com.liferay.deployment.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.net.URL;

import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 * @author David Truong
 */
public class DeploymentHelper {

	public static void main(String[] args) {
	}

	public DeploymentHelper(
		String deploymentFiles, String deploymentPath, String outputFile) {

		_deploymentFiles = deploymentFiles;
		_deploymentPath = deploymentPath;
		_outputFile = outputFile;
	}

	public void run() {
		try {
			URL url = this.getClass().getResource(
				"dependencies/deployment-helper-web.war");

			ZipFile source = new ZipFile(url.getFile());
			source.extractAll(_TMP_DIR);

			File webXml = new File(_TMP_DIR + "/WEB-INF/web.xml");

			updateWebXml(webXml);

			ZipFile zipFile = new ZipFile(_outputFile);
			ZipParameters parameters = new ZipParameters();

			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			zipFile.addFolder(new File(_TMP_DIR + "/WEB-INF"), parameters);
		}
		catch (Exception e) {
			System.err.println("Error writing " + _outputFile);
		}
	}

	public void updateWebXml(File webXml) {
		SAXBuilder builder = new SAXBuilder();

		try {
			Document document = builder.build(webXml);
			Namespace ns = Namespace.getNamespace(
				"http://java.sun.com/xml/ns/j2ee");
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("context-param", ns);

			for (Element el : list) {
				Element paramName = el.getChild("param-name", ns);
				Element paramValue = el.getChild("param-value", ns);

				if (paramName.getValue().equals("deployment-files")) {
					paramValue.setText(_deploymentFiles);
				}
				else if (paramName.getValue().equals("deployment-path")) {
					paramValue.setText(_deploymentPath);
				}
			}

			try (Writer writer = new OutputStreamWriter(
				new FileOutputStream(webXml, false), "UTF-8")) {

				XMLOutputter outputter = new XMLOutputter();
				outputter.output(document, writer);
			}
		}
		catch (Exception e) {
			System.err.println("Error writing the web.xml");
		}
	}

	private static final String _TMP_DIR = System.getProperty("java.io.tmpdir");

	private final String _deploymentFiles;
	private final String _deploymentPath;
	private final String _outputFile;

}