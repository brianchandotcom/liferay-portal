/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.oro.io.GlobFilenameFilter;
import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class PluginsEnvironmentBuilder {

	public static void main(String[] args) throws Exception {
		try {
			File dir = new File(System.getProperty("plugins.env.dir"));

			new PluginsEnvironmentBuilder(dir);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PluginsEnvironmentBuilder(File dir) throws Exception {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(dir);
		ds.setIncludes(new String[] {"**\\liferay-plugin-package.properties"});

		ds.scan();

		String dirName = dir.getCanonicalPath();

		String[] fileNames = ds.getIncludedFiles();

		for (String fileName : fileNames) {
			setupProject(dirName, fileName);
		}
	}

	protected void addClasspathEntry(StringBundler sb, String jar)
		throws Exception {

		sb.append("\t<classpathentry kind=\"lib\" path=\"");
		sb.append(jar);
		sb.append("\" />\n");
	}

	protected void setupProject(String dirName, String fileName)
		throws Exception {

		File propertiesFile = new File(dirName + "/" + fileName);

		File libDir = new File(propertiesFile.getParent() + "/lib");

		String libDirPath = StringUtil.replace(
			libDir.getPath(), StringPool.BACK_SLASH, StringPool.SLASH);

		if (libDirPath.contains("/themes/")) {
			return;
		}

		File projectDir = new File(propertiesFile.getParent() + "/../..");

		Properties properties = new Properties();

		properties.load(new FileInputStream(propertiesFile));

		String[] dependencyJars = StringUtil.split(
			properties.getProperty(
				"portal-dependency-jars",
				properties.getProperty("portal.dependency.jars")));

		List<String> jars = ListUtil.toList(dependencyJars);

		jars.add("commons-logging.jar");
		jars.add("log4j.jar");
		jars.add("util-bridges.jar");
		jars.add("util-java.jar");
		jars.add("util-taglib.jar");

		Collections.sort(jars);

		writeEclipseFiles(libDir, projectDir, dependencyJars);

		List<String> ignores = ListUtil.fromFile(
			libDir.getCanonicalPath() + "/../.gitignore");

		if (!ignores.contains("lib")) {
			File gitignoreFile = new File(
				libDir.getCanonicalPath() + "/.gitignore");

			System.out.println("Updating " + gitignoreFile);

			_fileUtil.write(
				gitignoreFile,
				StringUtil.merge(jars.toArray(new String[jars.size()]), "\n"));
		}
	}

	protected void writeClasspathFile(
			File libDir, String[] dependencyJars, String projectDirName,
			String projectName, boolean javaProject)
		throws Exception {

		File classpathFile = new File(projectDirName + "/.classpath");

		if (!javaProject) {
			classpathFile.delete();

			return;
		}

		List<String> portalJars = ListUtil.toList(dependencyJars);

		portalJars.add("commons-logging.jar");
		portalJars.add("log4j.jar");

		portalJars = ListUtil.sort(portalJars);

		String[] customJarsArray = libDir.list(new GlobFilenameFilter("*.jar"));

		List<String> customJars = null;

		if (customJarsArray != null) {
			customJars = ListUtil.toList(customJarsArray);

			for (String jar : portalJars) {
				customJars.remove(jar);
			}

			customJars.remove(projectName + "-service.jar");
			customJars.remove("util-bridges.jar");
			customJars.remove("util-java.jar");
			customJars.remove("util-taglib.jar");

			Collections.sort(customJars);
		}
		else {
			customJars = new ArrayList<String>();
		}

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		sb.append("<classpath>\n");

		if (_fileUtil.exists(projectDirName + "/docroot/WEB-INF/service")) {
			sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
			sb.append("kind=\"src\" path=\"docroot/WEB-INF/service\" />\n");
		}

		sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
		sb.append("kind=\"src\" path=\"docroot/WEB-INF/src\" />\n");
		sb.append("\t<classpathentry kind=\"src\" path=\"/portal\" />\n");
		sb.append("\t<classpathentry kind=\"con\" ");
		sb.append("path=\"org.eclipse.jdt.launching.JRE_CONTAINER\" />\n");

		if (_fileUtil.exists(projectDirName + "/test")) {
			sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
			sb.append("kind=\"src\" path=\"test\" />\n");

			addClasspathEntry(sb, "/portal/lib/development/junit.jar");
			addClasspathEntry(sb, "/portal/lib/portal/commons-io.jar");
		}

		addClasspathEntry(sb, "/portal/lib/development/activation.jar");
		addClasspathEntry(sb, "/portal/lib/development/annotations.jar");
		addClasspathEntry(sb, "/portal/lib/development/jsp-api.jar");
		addClasspathEntry(sb, "/portal/lib/development/mail.jar");
		addClasspathEntry(sb, "/portal/lib/development/servlet-api.jar");
		addClasspathEntry(sb, "/portal/lib/global/portlet.jar");

		for (String jar : portalJars) {
			addClasspathEntry(sb, "/portal/lib/portal/" + jar);
		}

		addClasspathEntry(sb, "/portal/portal-service/portal-service.jar");
		addClasspathEntry(sb, "/portal/util-bridges/util-bridges.jar");
		addClasspathEntry(sb, "/portal/util-java/util-java.jar");
		addClasspathEntry(sb, "/portal/util-taglib/util-taglib.jar");

		String libDirPath = StringUtil.replace(
			libDir.getPath(), StringPool.BACK_SLASH, StringPool.SLASH);

		for (String jar : customJars) {
			if (libDirPath.contains("/tmp/WEB-INF/lib")) {
				addClasspathEntry(sb, "tmp/WEB-INF/lib/" + jar);
			}
			else {
				addClasspathEntry(sb, "docroot/WEB-INF/lib/" + jar);
			}
		}

		sb.append("\t<classpathentry kind=\"output\" path=\"bin\" />\n");
		sb.append("</classpath>");

		System.out.println("Updating " + classpathFile);

		String content = StringUtil.replace(
			sb.toString(), "\"/portal", "\"/portal-" + _BRANCH);

		_fileUtil.write(classpathFile, content);
	}

	protected void writeEclipseFiles(
			File libDir, File projectDir, String[] dependencyJars)
		throws Exception {

		String projectDirName = projectDir.getCanonicalPath();

		String projectName = StringUtil.extractLast(
			projectDirName, File.separatorChar);

		boolean javaProject = false;

		if (_fileUtil.exists(projectDirName + "/docroot/WEB-INF/src")) {
			javaProject = true;
		}
		else {
			System.out.println(
				"Eclipse Java project will not be used because " +
					projectDirName + "/docroot/WEB-INF/src does not exist");
		}

		writeProjectFile(projectDirName, projectName, javaProject);

		writeClasspathFile(
			libDir, dependencyJars, projectDirName, projectName, javaProject);

		if (_fileUtil.exists(projectDirName + "/test")) {
			_fileUtil.write(
				projectDirName + "/.gitignore", "test-classes\ntest-results");
		}
		else {
			_fileUtil.delete(projectDirName + "/.gitignore");
		}
	}

	protected void writeProjectFile(
			String projectDirName, String projectName, boolean javaProject)
		throws Exception {

		StringBundler sb = new StringBundler(17);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		sb.append("<projectDescription>\n");
		sb.append("\t<name>");
		sb.append(projectName);
		sb.append("-");
		sb.append(_BRANCH);
		sb.append("</name>\n");
		sb.append("\t<comment></comment>\n");
		sb.append("\t<projects></projects>\n");
		sb.append("\t<buildSpec>\n");

		if (javaProject) {
			sb.append("\t\t<buildCommand>\n");
			sb.append("\t\t\t<name>org.eclipse.jdt.core.javabuilder</name>\n");
			sb.append("\t\t\t<arguments></arguments>\n");
			sb.append("\t\t</buildCommand>\n");
		}

		sb.append("\t</buildSpec>\n");
		sb.append("\t<natures>\n");

		if (javaProject) {
			sb.append("\t\t<nature>org.eclipse.jdt.core.javanature</nature>\n");
		}

		sb.append("\t</natures>\n");
		sb.append("</projectDescription>");

		File projectFile = new File(projectDirName + "/.project");

		System.out.println("Updating " + projectFile);

		_fileUtil.write(projectFile, sb.toString());
	}

	private static final String _BRANCH = "trunk";

	private static FileImpl _fileUtil = FileImpl.getInstance();

}