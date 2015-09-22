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
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
		HashMap<String, String> arguments = parseArgs(args);

		String deploymentFiles = arguments.get("deployment.files");
		String deploymentPath = arguments.get("deployment.path");
		String outputPath = arguments.get("output.file");

		try (Scanner scanner = new Scanner(System.in)) {
			while ((deploymentFiles == null) || deploymentFiles.equals("")) {
				System.out.println(
					"Please input a comma delimited list of files to include " +
						"in the war (absolute paths required)");

				deploymentFiles = scanner.nextLine();
			}

			while ((deploymentPath == null) || deploymentPath.equals("")) {
				System.out.println(
					"Please input the path to the Liferay deploy folder on " +
						"the target system (absolute path required)");

				deploymentPath = scanner.nextLine();
			}

			if ((outputPath == null) || outputPath.equals("")) {
				System.out.println(
					"Please input the a name for your war (absolute path " +
						"required)");

				outputPath = scanner.nextLine();
			}
		}

		DeploymentHelper deploymentHelper = new DeploymentHelper(
			deploymentFiles, deploymentPath, outputPath);

		deploymentHelper.run();
	}

	public static HashMap<String, String> parseArgs(String[] args) {
		HashMap<String, String> arguments = new HashMap<>();

		for (String arg : args) {
			int pos = arg.indexOf('=');

			if (pos <= 0) {
				continue;
			}

			String key = arg.substring(0, pos).trim();
			String value = arg.substring(pos + 1).trim();

			if (key.startsWith("-D")) {
				key = key.substring(2);

				System.setProperty(key, value);
			}
			else {
				arguments.put(key, value);
			}
		}

		return arguments;
	}

	public DeploymentHelper(
		String deploymentFiles, String deploymentPath, String outputPath) {

		_deploymentFiles = deploymentFiles;
		_deploymentPath = deploymentPath;
		_outputPath = outputPath;
	}

	public void run() {
		try {
			URL url = this.getClass().getResource(
				"dependencies/deployment-helper-web.war");

			Date now = new Date();

			Path tempPath = Paths.get(_TMP_DIR);
			Path basePath = Files.createTempDirectory(tempPath, null);
			File baseDir = basePath.toFile();
			baseDir.deleteOnExit();

			ZipFile source = new ZipFile(url.getFile());
			source.extractAll(basePath.toString());

			File webXml = new File(basePath + "/WEB-INF/web.xml");

			updateWebXml(webXml);

			addDeploymentFiles(basePath);

			ZipFile zipFile = new ZipFile(_outputPath);
			ZipParameters parameters = new ZipParameters();

			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setIncludeRootFolder(false);

			zipFile.addFolder(baseDir, parameters);
		}
		catch (Exception e) {
			System.err.println("Error writing " + _outputPath);

			e.printStackTrace();
		}
	}

	private void addDeploymentFiles(Path basePath) {
		for (String file : _deploymentFiles.split(",")) {
			Path path = Paths.get(file);

			try {
				Path target = basePath.resolve(path.getFileName());

				Files.copy(path, target);
			}
			catch (IOException ioe) {
				System.err.println("Error adding " + file.toString());

				ioe.printStackTrace();
			}
		}
	}

	private void updateWebXml(File webXml) {
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

			e.printStackTrace();
		}
	}

	private static final String _TMP_DIR = System.getProperty("java.io.tmpdir");

	private final String _deploymentFiles;
	private final String _deploymentPath;
	private final String _outputPath;

}