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

package com.liferay.gradle.templates;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.gradle.templates.internal.util.FileUtil;
import com.liferay.gradle.templates.internal.util.StringUtil;
import com.liferay.gradle.templates.internal.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Andrea Di Giorgi
 */
public class GradleTemplates {

	public static String[] getTemplates() throws Exception {
		final List<String> templates = new ArrayList<>();

		File file = _getJarFile();

		if (file.isDirectory()) {
			Path jarDirPath = file.toPath();

			try (DirectoryStream<Path> directoryStream =
					Files.newDirectoryStream(
						jarDirPath.resolve(TEMPLATES_STANDALONE_DIR),
						FileUtil.directoriesOnlyFilter)) {

				for (Path path : directoryStream) {
					Path fileNamePath = path.getFileName();

					templates.add(fileNamePath.toString());
				}
			}
		}
		else {
			try (JarFile jarFile = new JarFile(file)) {
				Enumeration<JarEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();

					if (!jarEntry.isDirectory()) {
						continue;
					}

					String name = jarEntry.getName();

					if (name.startsWith(TEMPLATES_STANDALONE_DIR + "/") &&
						(name.indexOf(
							'/', TEMPLATES_STANDALONE_DIR.length() + 1) ==
								(name.length() - 1))) {

						String template = name.substring(
							TEMPLATES_STANDALONE_DIR.length() + 1,
							name.length() - 1);

						templates.add(template);
					}
				}
			}
		}

		Collections.sort(templates);

		return templates.toArray(new String[templates.size()]);
	}

	public static void main(String[] args) {
		GradleTemplatesArgs gradleTemplatesArgs = new GradleTemplatesArgs();

		JCommander jCommander = new JCommander(gradleTemplatesArgs);

		try {
			File jarFile = _getJarFile();

			if (jarFile.isFile()) {
				jCommander.setProgramName("java -jar " + jarFile.getName());
			}
			else {
				jCommander.setProgramName(GradleTemplates.class.getName());
			}

			jCommander.parse(args);

			if (gradleTemplatesArgs.isHelp()) {
				_printHelp(jCommander);
			}
			else if (gradleTemplatesArgs.isList()) {
				_printList();
			}
			else {
				new GradleTemplates(gradleTemplatesArgs);
			}
		}
		catch (ParameterException pe) {
			System.err.println(pe.getMessage());

			try {
				_printHelp(jCommander);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			System.exit(1);
		}
		catch (Exception e) {
			e.printStackTrace();

			System.exit(1);
		}
	}

	public GradleTemplates(GradleTemplatesArgs gradleTemplatesArgs)
		throws Exception {

		_checkArgs(gradleTemplatesArgs);

		Map<String, String> replacements = _getReplacements(
			gradleTemplatesArgs);

		File dir = new File(
			gradleTemplatesArgs.getDestinationDir(),
			gradleTemplatesArgs.getName());

		_extractDirectory(
			TEMPLATES_STANDALONE_DIR + "/" + gradleTemplatesArgs.getTemplate(),
			dir, replacements);

		if (gradleTemplatesArgs.getWorkspaceDir() != null) {
			_extractDirectory(
				TEMPLATES_WORKSPACE_DIR + "/" +
					gradleTemplatesArgs.getTemplate(),
				dir, replacements);

			File settingsGradleFile = new File(dir, "settings.gradle");

			settingsGradleFile.delete();
		}
	}

	protected static final String TEMPLATES_STANDALONE_DIR = "standalone";

	protected static final String TEMPLATES_WORKSPACE_DIR = "workspace";

	private static File _getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			GradleTemplates.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	private static void _printHelp(JCommander jCommander) throws Exception {
		System.out.println();

		System.out.println(
			"Create a new Liferay module project from several available " +
				"templates:");

		String[] templates = getTemplates();

		int lineLength = 0;

		for (int i = 0; i < templates.length; i++) {
			String template = templates[i];

			if ((lineLength + template.length() + 1) >
					jCommander.getColumnSize()) {

				System.out.println();

				lineLength = 0;
			}

			System.out.print(template);

			lineLength += template.length();

			if (i < (templates.length - 1)) {
				System.out.print(", ");

				lineLength += 2;
			}
		}

		System.out.println();
		System.out.println();

		jCommander.usage();
	}

	private static void _printList() throws Exception {
		for (String template : getTemplates()) {
			System.out.println(template);
		}
	}

	private void _checkArgs(GradleTemplatesArgs gradleTemplatesArgs) {
		String name = gradleTemplatesArgs.getName();

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is required");
		}

		File destinationDir = gradleTemplatesArgs.getDestinationDir();

		if (destinationDir == null) {
			throw new IllegalArgumentException("Destination dir is required");
		}

		File dir = new File(destinationDir, name);

		if (dir.exists()) {
			String[] fileNames = dir.list();

			if ((fileNames == null) || (fileNames.length > 0)) {
				throw new IllegalArgumentException(
					dir + " is not empty or it is a file");
			}
		}

		String template = gradleTemplatesArgs.getTemplate();

		if (Validator.isNull(gradleTemplatesArgs.getTemplate())) {
			throw new IllegalArgumentException("Template is required");
		}

		String className = gradleTemplatesArgs.getClassName();

		if (Validator.isNull(className)) {
			className = _getClassName(name);
		}

		if (template.equals("activator") && !className.endsWith("Activator")) {
			className += "Activator";
		}
		else if ((template.equals("mvcportlet") ||
				  template.equals("portlet")) &&
				 className.endsWith("Portlet")) {

			className = className.substring(0, className.length() - 7);
		}

		gradleTemplatesArgs.setClassName(className);

		if (Validator.isNull(gradleTemplatesArgs.getPackageName())) {
			gradleTemplatesArgs.setPackageName(_getPackageName(name));
		}
	}

	private void _extractDirectory(
			String dirName, File destinationDir,
			final Map<String, String> replacements)
		throws Exception {

		final Path rootDestinationDirPath = destinationDir.toPath();

		File file = _getJarFile();

		if (file.isDirectory()) {
			Path jarDirPath = file.toPath();

			final Path rootDirPath = jarDirPath.resolve(dirName);

			Files.walkFileTree(
				rootDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path relativePath = rootDirPath.relativize(path);

						String fileName = StringUtil.replace(
							relativePath.toString(), replacements);

						Path destinationPath = rootDestinationDirPath.resolve(
							fileName);

						Files.createDirectories(destinationPath.getParent());

						Files.copy(
							path, destinationPath,
							StandardCopyOption.REPLACE_EXISTING);

						FileUtil.replace(destinationPath, replacements);

						return FileVisitResult.CONTINUE;
					}

				});
		}
		else {
			try (JarFile jarFile = new JarFile(file)) {
				Enumeration<JarEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();

					if (jarEntry.isDirectory()) {
						continue;
					}

					String name = jarEntry.getName();

					if (!name.startsWith(dirName + "/")) {
						continue;
					}

					String fileName = StringUtil.replace(
						name.substring(dirName.length() + 1), replacements);

					Path destinationPath = rootDestinationDirPath.resolve(
						fileName);

					Files.createDirectories(destinationPath.getParent());

					try (InputStream inputStream = jarFile.getInputStream(
							jarEntry)) {

						Files.copy(
							inputStream, destinationPath,
							StandardCopyOption.REPLACE_EXISTING);
					}

					FileUtil.replace(destinationPath, replacements);
				}
			}
		}
	}

	private String _getCapitalizedName(String name) {
		name = name.replace('-', ' ');
		name = name.replace('.', ' ');

		return StringUtil.capitalize(name, ' ');
	}

	private String _getClassName(String name) {
		name = _getCapitalizedName(name);

		return StringUtil.removeChar(name, ' ');
	}

	private String _getPackageName(String name) {
		name = name.replace('-', '.');
		name = name.replace(' ', '.');

		return name.toLowerCase();
	}

	private Map<String, String> _getReplacements(
		GradleTemplatesArgs gradleTemplatesArgs) {

		Map<String, String> replacements = new HashMap<>();

		String className = gradleTemplatesArgs.getClassName();
		String name = gradleTemplatesArgs.getName();
		String template = gradleTemplatesArgs.getTemplate();
		String packageName = gradleTemplatesArgs.getPackageName();

		replacements.put("_CLASS_", className);
		replacements.put("_NAME_", _getCapitalizedName(name));
		replacements.put("_name_", name);
		replacements.put("_package_", packageName);
		replacements.put("_package_path_", packageName.replace('.', '/'));

		if (template.equals("fragment")) {
			_populateFragmentReplacements(replacements, gradleTemplatesArgs);
		}
		else if (template.equals("mvcportlet")) {
			_populateMVCPortletReplacements(replacements, gradleTemplatesArgs);
		}
		else if (template.equals("servicebuilder")) {
			_populateServiceBuilderReplacements(
				replacements, gradleTemplatesArgs);
		}
		else if (template.equals("service") ||
				 template.equals("servicewrapper")) {

			_populateServiceReplacements(replacements, gradleTemplatesArgs);
		}

		return replacements;
	}

	private void _populateFragmentReplacements(
		Map<String, String> replacements,
		GradleTemplatesArgs gradleTemplatesArgs) {

		String hostBundleSymbolicName =
			gradleTemplatesArgs.getHostBundleSymbolicName();
		String hostBundleVersion = gradleTemplatesArgs.getHostBundleVersion();

		if (Validator.isNull(hostBundleSymbolicName)) {
			throw new IllegalArgumentException(
				"Host bundle symbolic name is required");
		}

		if (Validator.isNull(hostBundleVersion)) {
			throw new IllegalArgumentException(
				"Host bundle version is required");
		}

		replacements.put("_HOST_BUNDLE_BSN_", hostBundleSymbolicName);
		replacements.put("_HOST_BUNDLE_VERSION_", hostBundleVersion);
	}

	private void _populateMVCPortletReplacements(
		Map<String, String> replacements,
		GradleTemplatesArgs gradleTemplatesArgs) {

		String portletFQN = gradleTemplatesArgs.getName();

		portletFQN = portletFQN.toLowerCase();
		portletFQN = portletFQN.replace('-', '_');

		String className = gradleTemplatesArgs.getClassName();

		portletFQN = portletFQN + "_" + className.replace('.', '_');

		replacements.put("_portlet_fqn_", portletFQN);
	}

	private void _populateServiceBuilderReplacements(
		Map<String, String> replacements,
		GradleTemplatesArgs gradleTemplatesArgs) {

		String apiPath = "";

		String name = gradleTemplatesArgs.getName();
		File workspaceDir = gradleTemplatesArgs.getWorkspaceDir();

		if (workspaceDir != null) {
			Path workspaceDirPath = workspaceDir.toPath();

			File destinationDir = gradleTemplatesArgs.getDestinationDir();

			Path relativePath = workspaceDirPath.relativize(
				destinationDir.toPath());

			apiPath = relativePath.toString();

			apiPath =
				":" + apiPath.replace(File.separatorChar, ':') + ":" + name;
		}

		replacements.put("_api_path_", apiPath);

		char separator = '.';

		if (name.indexOf(separator) == -1) {
			separator = '-';
		}

		replacements.put("_api_", name + separator + "api");
		replacements.put("_service_", name + separator + "svc");
	}

	private void _populateServiceReplacements(
		Map<String, String> replacements,
		GradleTemplatesArgs gradleTemplatesArgs) {

		String service = gradleTemplatesArgs.getService();

		if (Validator.isNull(service)) {
			throw new IllegalArgumentException("Service is required");
		}

		replacements.put("_SERVICE_FULL_", service);

		int pos = service.lastIndexOf('.');

		if (pos == -1) {
			throw new IllegalArgumentException(
				"Service must be a fully qualified class name");
		}

		replacements.put("_SERVICE_SHORT_", service.substring(pos + 1));
	}

}