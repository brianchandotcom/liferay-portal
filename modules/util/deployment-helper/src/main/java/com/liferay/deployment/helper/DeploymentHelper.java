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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.zeroturnaround.zip.ByteSource;
import org.zeroturnaround.zip.FileSource;
import org.zeroturnaround.zip.ZipEntrySource;
import org.zeroturnaround.zip.ZipUtil;

/**
 * @author Andrea Di Giorgi
 */
public class DeploymentHelper {

	public static void main(String[] args) throws Exception {
		try {
			Options options = _getOptions();

			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

			if (commandLine.hasOption("help")) {
				_printOptions();

				return;
			}

			String deploymentFileNames = commandLine.getOptionValue(
				"fileNames");
			String deploymentPath = commandLine.getOptionValue("path", "");
			String outputFileName = commandLine.getOptionValue("outputFile");

			new DeploymentHelper(
				deploymentFileNames, deploymentPath, outputFileName);
		}
		catch (ParseException pe) {
			System.err.println(pe.getMessage());

			_printOptions();
		}
		catch (Exception e) {
			System.err.println("Error running deployment helper");

			e.printStackTrace();
		}
	}

	public DeploymentHelper(
			String deploymentFileNames, String deploymentPath,
			String outputFileName)
		throws Exception {

		List<ZipEntrySource> zipEntrySources = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		for (String deploymentFileName : deploymentFileNames.split(",")) {
			File deploymentFile = new File(deploymentFileName.trim());

			if (deploymentFile.isDirectory()) {
				addDeploymentFiles(deploymentFile, sb, zipEntrySources);
			}
			else {
				addDeploymentFile(deploymentFile, sb, zipEntrySources);
			}
		}

		sb.setLength(sb.length() - 1);

		zipEntrySources.add(
			getWebXmlZipEntrySource(sb.toString(), deploymentPath));

		zipEntrySources.add(
			getClassZipEntrySource(
				"com/liferay/deployment/helper/servlet" +
					"/DeploymentHelperContextListener.class"));

		ZipUtil.pack(
			zipEntrySources.toArray(new ZipEntrySource[zipEntrySources.size()]),
			new File(outputFileName));
	}

	protected void addDeploymentFile(
		File file, StringBuilder sb, List<ZipEntrySource> zipEntrySources) {

		String webInfDeploymentFileName = "WEB-INF/" + file.getName();

		sb.append('/');
		sb.append(webInfDeploymentFileName);
		sb.append(',');

		zipEntrySources.add(new FileSource(webInfDeploymentFileName, file));
	}

	protected void addDeploymentFiles(
			File dir, final StringBuilder sb,
			final List<ZipEntrySource> zipEntrySources)
		throws IOException {

		FileSystem fileSystem = FileSystems.getDefault();

		final PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:**/*.jar");

		Files.walkFileTree(
			dir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (pathMatcher.matches(path)) {
						addDeploymentFile(path.toFile(), sb, zipEntrySources);
					}

					return FileVisitResult.CONTINUE;
				}

			});
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

	private static Options _getOptions() {
		Options options = new Options();

		Option fileNamesOption = new Option(
			"f", "fileNames", true,
			"Set the files you would like to include in the WAR.");

		fileNamesOption.setRequired(true);

		options.addOption(fileNamesOption);

		options.addOption(
			new Option("h", "help", false, "Print this message."));

		Option outputFileOption = new Option(
			"o", "outputFile", true, "Set the name of the output file.");

		outputFileOption.setRequired(true);

		options.addOption(outputFileOption);

		options.addOption(
			new Option(
				"p", "path", true,
				"Set the path the files will be deployed. If this is not " +
					"set, it will deploy to the value set in the portal " +
						"property \"auto.deploy.deploy.dir\"."));

		return options;
	}

	private static void _printOptions() {
		HelpFormatter helpFormatter = new HelpFormatter();

		helpFormatter.printHelp("Liferay Deployment Helper", _getOptions());
	}

}