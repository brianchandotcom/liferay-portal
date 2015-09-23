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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ArgumentsUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zeroturnaround.zip.ByteSource;
import org.zeroturnaround.zip.FileSource;
import org.zeroturnaround.zip.ZipEntrySource;
import org.zeroturnaround.zip.ZipUtil;

/**
 * @author Andrea Di Giorgi
 */
public class DeploymentHelper {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String deploymentFileNames = arguments.get("deployment.files");

		if (Validator.isNull(deploymentFileNames)) {
			throw new IllegalArgumentException(
				"The deployment.files argument is required");
		}

		String deploymentPath = arguments.get("deployment.path");

		if (Validator.isNull(deploymentPath)) {
			throw new IllegalArgumentException(
				"The deployment.path argument is required");
		}

		String outputFileName = arguments.get("deployment.output.file");

		if (Validator.isNull(outputFileName)) {
			throw new IllegalArgumentException(
				"The deployment.output.file argument is required");
		}

		try {
			new DeploymentHelper(
				deploymentFileNames, deploymentPath, outputFileName);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public DeploymentHelper(
			String deploymentFileNames, String deploymentPath,
			String outputFileName)
		throws Exception {

		List<ZipEntrySource> zipEntrySources = new ArrayList<>();

		StringBuilder webXmlDeploymentFileNames = new StringBuilder();

		for (String deploymentFileName : deploymentFileNames.split(",")) {
			File file = new File(deploymentFileName.trim());

			String webXmlDeploymentFileName = "WEB-INF/" + file.getName();

			webXmlDeploymentFileNames.append('/');
			webXmlDeploymentFileNames.append(webXmlDeploymentFileName);
			webXmlDeploymentFileNames.append(',');

			zipEntrySources.add(new FileSource(webXmlDeploymentFileName, file));
		}

		webXmlDeploymentFileNames.setLength(
			webXmlDeploymentFileNames.length() - 1);

		zipEntrySources.add(
			getWebXmlZipEntrySource(
				webXmlDeploymentFileNames.toString(), deploymentPath));

		zipEntrySources.add(
			getClassZipEntrySource(
				_SERVLET_FILES_ROOT_PATH +
					"DeploymentHelperContextListener.class"));

		ZipUtil.pack(
			zipEntrySources.toArray(new ZipEntrySource[zipEntrySources.size()]),
			new File(outputFileName));
	}

	protected ZipEntrySource getClassZipEntrySource(String name)
		throws Exception {

		byte[] bytes = read(name);

		return new ByteSource("WEB-INF/classes/" + name, bytes);
	}

	protected ZipEntrySource getWebXmlZipEntrySource(
			String deploymentFileNames, String deploymentPath)
		throws Exception {

		byte[] bytes = read(_SERVLET_FILES_ROOT_PATH + "dependencies/web.xml");

		String webXml = new String(bytes);

		webXml = webXml.replace("${deployment.files}", deploymentFileNames);
		webXml = webXml.replace("${deployment.path}", deploymentPath);

		return new ByteSource(
			"WEB-INF/web.xml", webXml.getBytes(StandardCharsets.UTF_8));
	}

	protected byte[] read(String name) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		ClassLoader classLoader = DeploymentHelper.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(name)) {
			byte[] buffer = new byte[1024];

			int length = 0;

			while ((length = inputStream.read(buffer)) > 0) {
				byteArrayOutputStream.write(buffer, 0, length);
			}
		}

		return byteArrayOutputStream.toByteArray();
	}

	private static final String _SERVLET_FILES_ROOT_PATH =
		"com/liferay/deployment/helper/servlet/";

}