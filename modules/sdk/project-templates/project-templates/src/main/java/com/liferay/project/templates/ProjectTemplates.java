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

package com.liferay.project.templates;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.project.templates.internal.ProjectGenerator;
import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.ProjectTemplatesUtil;
import com.liferay.project.templates.internal.util.StringUtil;
import com.liferay.project.templates.internal.util.Validator;

import java.io.File;
import java.io.InputStream;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Andrea Di Giorgi
 */
public class ProjectTemplates {

	public static final String TEMPLATE_BUNDLE_PREFIX =
		"com.liferay.project.templates.";

	public static Map<String, String> getTemplates() throws Exception {
		return getTemplates(new HashSet<>());
	}

	public static Map<String, String> getTemplates(
			Collection<File> templatesFiles)
		throws Exception {

		Map<String, String> templates = new TreeMap<>();

		for (File templatesFile : templatesFiles) {
			if (templatesFile.isDirectory()) {
				try (DirectoryStream<Path> directoryStream =
						Files.newDirectoryStream(
							templatesFile.toPath(), "*.project.templates.*")) {

					Iterator<Path> iterator = directoryStream.iterator();

					while (iterator.hasNext()) {
						Path path = iterator.next();

						String fileName = String.valueOf(path.getFileName());

						String template = ProjectTemplatesUtil.getTemplateName(
							fileName);

						if (!template.startsWith(WorkspaceUtil.WORKSPACE)) {
							String bundleDescription =
								FileUtil.getManifestProperty(
									path.toFile(), "Bundle-Description");

							templates.put(template, bundleDescription);
						}
					}
				}
			}
			else {
				try (JarFile jarFile = new JarFile(templatesFile)) {
					Enumeration<JarEntry> enumeration = jarFile.entries();

					while (enumeration.hasMoreElements()) {
						JarEntry jarEntry = enumeration.nextElement();

						if (jarEntry.isDirectory()) {
							continue;
						}

						String template = jarEntry.getName();

						if (!template.startsWith(TEMPLATE_BUNDLE_PREFIX)) {
							continue;
						}

						template = ProjectTemplatesUtil.getTemplateName(
							template);

						if (!template.startsWith(WorkspaceUtil.WORKSPACE)) {
							try (InputStream inputStream =
									jarFile.getInputStream(jarEntry);
								JarInputStream jarInputStream =
									new JarInputStream(inputStream)) {

								Manifest manifest =
									jarInputStream.getManifest();

								Attributes attributes =
									manifest.getMainAttributes();

								String bundleDescription = attributes.getValue(
									"Bundle-Description");

								templates.put(template, bundleDescription);
							}
						}
					}
				}
			}
		}

		List<String> archetypeJarNames =
			ProjectTemplatesUtil.getArchetypeJarNames();

		for (String projectTemplateJarName : archetypeJarNames) {
			String templateName = ProjectTemplatesUtil.getTemplateName(
				projectTemplateJarName);

			if (!templateName.startsWith(WorkspaceUtil.WORKSPACE)) {
				try (InputStream inputStream =
						ProjectTemplates.class.getResourceAsStream(
							projectTemplateJarName);
					JarInputStream jarInputStream =
						new JarInputStream(inputStream)) {

					Manifest manifest = jarInputStream.getManifest();

					Attributes attributes = manifest.getMainAttributes();

					String bundleDescription = attributes.getValue(
						"Bundle-Description");

					templates.put(templateName, bundleDescription);
				}
			}
		}

		return templates;
	}

	public static void main(String[] args) throws Exception {
		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		JCommander jCommander = new JCommander(projectTemplatesArgs);

		try {
			Path jarPath = FileUtil.getJarPath();

			if (Files.isDirectory(jarPath)) {
				jCommander.setProgramName(ProjectTemplates.class.getName());
			}
			else {
				jCommander.setProgramName("java -jar " + jarPath.getFileName());
			}

			jCommander.parse(args);

			if (projectTemplatesArgs.isHelp()) {
				_printHelp(jCommander, projectTemplatesArgs);
			}
			else if (projectTemplatesArgs.isList()) {
				_printList(projectTemplatesArgs);
			}
			else {
				new ProjectTemplates(projectTemplatesArgs);
			}
		}
		catch (ParameterException pe) {
			System.err.println(pe.getMessage());

			_printHelp(jCommander, projectTemplatesArgs);
		}
	}

	public ProjectTemplates(ProjectTemplatesArgs projectTemplatesArgs)
		throws Exception {

		_checkArgs(projectTemplatesArgs);

		File destinationDir = projectTemplatesArgs.getDestinationDir();

		ProjectGenerator projectGenerator = new ProjectGenerator();

		ArchetypeGenerationResult archetypeGenerationResult =
			projectGenerator.generateProject(
				projectTemplatesArgs, destinationDir);

		if (archetypeGenerationResult != null) {
			Exception cause = archetypeGenerationResult.getCause();

			if (cause != null) {
				throw cause;
			}
		}

		Path templateDirPath = destinationDir.toPath();

		templateDirPath = templateDirPath.resolve(
			projectTemplatesArgs.getName());

		if (WorkspaceUtil.isWorkspace(destinationDir)) {
			Files.deleteIfExists(templateDirPath.resolve("settings.gradle"));
		}
		else {
			if (projectTemplatesArgs.isGradle()) {
				FileUtil.extractDirectory("gradle-wrapper", templateDirPath);

				FileUtil.setPosixFilePermissions(
					templateDirPath.resolve("gradlew"),
					_wrapperPosixFilePermissions);
			}

			if (projectTemplatesArgs.isMaven()) {
				FileUtil.extractDirectory("maven-wrapper", templateDirPath);

				FileUtil.setPosixFilePermissions(
					templateDirPath.resolve("mvnw"),
					_wrapperPosixFilePermissions);
			}
		}

		if (!projectTemplatesArgs.isGradle()) {
			FileUtil.deleteFiles(
				templateDirPath, "build.gradle", "settings.gradle");
		}

		if (!projectTemplatesArgs.isMaven()) {
			FileUtil.deleteFiles(templateDirPath, "pom.xml");
		}
	}

	private static void _printHelp(
			JCommander jCommander, ProjectTemplatesArgs projectTemplatesArgs)
		throws Exception {

		System.out.println();

		System.out.println(
			"Create a new Liferay module project from several available " +
				"templates:");

		Map<String, String> templates = getTemplates(
			projectTemplatesArgs.getArchetypesDirs());

		int lineLength = 0;

		Set<String> templateNames = templates.keySet();

		Iterator<String> iterator = templateNames.iterator();

		while (iterator.hasNext()) {
			String template = iterator.next();

			if ((lineLength + template.length() + 1) >
					jCommander.getColumnSize()) {

				System.out.println();

				lineLength = 0;
			}

			System.out.print(template);

			lineLength += template.length();

			if (iterator.hasNext()) {
				System.out.print(", ");

				lineLength += 2;
			}
		}

		System.out.println();
		System.out.println();

		jCommander.usage();
	}

	private static void _printList(ProjectTemplatesArgs projectTemplatesArgs)
		throws Exception {

		Map<String, String> templates = getTemplates(
			projectTemplatesArgs.getArchetypesDirs());

		for (Map.Entry<String, String> entry : templates.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	private void _checkArgs(ProjectTemplatesArgs projectTemplatesArgs) {
		if (Validator.isNull(projectTemplatesArgs.getAuthor())) {
			throw new IllegalArgumentException("Author is required");
		}

		String template = projectTemplatesArgs.getTemplate();

		if (Validator.isNull(projectTemplatesArgs.getTemplate())) {
			throw new IllegalArgumentException("Template is required");
		}

		String name = projectTemplatesArgs.getName();

		if (Validator.isNull(name) &&
			!template.equals(WorkspaceUtil.WORKSPACE)) {

			throw new IllegalArgumentException("Name is required");
		}

		File destinationDir = projectTemplatesArgs.getDestinationDir();

		if (destinationDir == null) {
			throw new IllegalArgumentException("Destination dir is required");
		}

		File dir = destinationDir;

		if (Validator.isNotNull(name)) {
			dir = new File(dir, name);
		}

		if (!projectTemplatesArgs.isForce() && dir.exists()) {
			String[] fileNames = dir.list();

			if ((fileNames == null) || (fileNames.length > 0)) {
				throw new IllegalArgumentException(
					dir + " is not empty or it is a file");
			}
		}

		String className = projectTemplatesArgs.getClassName();

		if (Validator.isNull(className) && Validator.isNotNull(name)) {
			className = _getClassName(name);
		}

		if (template.equals("activator") && !className.endsWith("Activator")) {
			className += "Activator";
		}
		else if ((template.equals("freemarker-portlet") ||
				  template.equals("mvc-portlet") ||
				  template.equals("npm-angular-portlet") ||
				  template.equals("npm-billboardjs-portlet") ||
				  template.equals("npm-isomorphic-portlet") ||
				  template.equals("npm-jquery-portlet") ||
				  template.equals("npm-metaljs-portlet") ||
				  template.equals("npm-portlet") ||
				  template.equals("npm-react-portlet") ||
				  template.equals("npm-vuejs-portlet") ||
				  template.equals("spring-mvc-portlet") ||
				  template.equals("portlet")) &&
				 (className.length() > 7) && className.endsWith("Portlet")) {

			className = className.substring(0, className.length() - 7);
		}

		projectTemplatesArgs.setClassName(className);

		if (Validator.isNull(projectTemplatesArgs.getPackageName()) &&
			Validator.isNotNull(name)) {

			projectTemplatesArgs.setPackageName(_getPackageName(name));
		}

		String contributorType = projectTemplatesArgs.getContributorType();

		if (Validator.isNull(contributorType)) {
			projectTemplatesArgs.setContributorType(name);
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

	private static final Set<PosixFilePermission> _wrapperPosixFilePermissions =
		PosixFilePermissions.fromString("rwxrwxr--");

}