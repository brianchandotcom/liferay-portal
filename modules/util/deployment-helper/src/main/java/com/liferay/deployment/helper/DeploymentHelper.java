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

import org.apache.tools.ant.DirectoryScanner;

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
				"The \"deployment.files\" argument is required");
		}

		String deploymentPath = arguments.get("deployment.path");

		if (Validator.isNull(deploymentPath)) {
			deploymentPath = "";
		}

		String outputFileName = arguments.get("deployment.output.file");

		if (Validator.isNull(outputFileName)) {
			throw new IllegalArgumentException(
				"The \"deployment.output.file\" argument is required");
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

		StringBuilder sb = new StringBuilder();

		DirectoryScanner scanner = new DirectoryScanner();

		scanner.setIncludes(new String[] {"**/*.jar"});
		scanner.setCaseSensitive(false);

		for (String deploymentFileName : deploymentFileNames.split(",")) {
			File deploymentFile = new File(deploymentFileName.trim());

			if (deploymentFile.isDirectory()) {
				String deploymentFilePath = deploymentFile.getCanonicalPath();

				scanner.setBasedir(deploymentFilePath);
				scanner.scan();
				String[] fileNames = scanner.getIncludedFiles();

				System.out.println("Adding jars in " + deploymentFilePath);

				for (String fileName : fileNames) {
					File file = new File(deploymentFilePath, fileName);

					String webInfDeploymentFileName =
						"WEB-INF/" + file.getName();

					zipEntrySources.add(
						new FileSource(webInfDeploymentFileName, file));

					sb.append('/');
					sb.append(webInfDeploymentFileName);
					sb.append(',');
				}
			}
			else {
				String webInfDeploymentFileName =
					"WEB-INF/" + deploymentFile.getName();

				zipEntrySources.add(
					new FileSource(webInfDeploymentFileName, deploymentFile));

				sb.append('/');
				sb.append(webInfDeploymentFileName);
				sb.append(',');
			}
		}

		sb.setLength(sb.length() - 1);

		zipEntrySources.add(
			getWebXmlZipEntrySource(sb.toString(), deploymentPath));

		zipEntrySources.add(
			getClassZipEntrySource(
				"com/liferay/deployment/helper/servlet/" +
					"DeploymentHelperContextListener.class"));

		ZipUtil.pack(
			zipEntrySources.toArray(new ZipEntrySource[zipEntrySources.size()]),
			new File(outputFileName));
	}

	protected ZipEntrySource getClassZipEntrySource(String fileName)
		throws Exception {

		byte[] bytes = read(fileName);

		return new ByteSource("WEB-INF/classes/" + fileName, bytes);
	}

	protected ZipEntrySource getWebXmlZipEntrySource(
			String deploymentFileNames, String deploymentPath)
		throws Exception {

		byte[] bytes = read(
			"com/liferay/deployment/helper/servlet/dependencies/web.xml");

		String content = new String(bytes);

		content = content.replace("${deployment.files}", deploymentFileNames);
		content = content.replace("${deployment.path}", deploymentPath);

		return new ByteSource(
			"WEB-INF/web.xml", content.getBytes(StandardCharsets.UTF_8));
	}

	protected byte[] read(String fileName) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		ClassLoader classLoader = DeploymentHelper.class.getClassLoader();

		try (InputStream inputStream =
				classLoader.getResourceAsStream(fileName)) {

			byte[] bytes = new byte[1024];
			int length = 0;

			while ((length = inputStream.read(bytes)) > 0) {
				byteArrayOutputStream.write(bytes, 0, length);
			}
		}

		return byteArrayOutputStream.toByteArray();
	}

}