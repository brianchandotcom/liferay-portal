/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

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

		for (String fileName : ds.getIncludedFiles()) {
			setupWarProject(dirName, fileName);
		}

		ds = new DirectoryScanner();

		ds.setBasedir(dir);
		ds.setIncludes(new String[] {"**\\build.xml"});

		ds.scan();

		for (String fileName : ds.getIncludedFiles()) {
			String content = _fileUtil.read(dirName + "/" + fileName);

			boolean isOsgiProject = content.contains(
				"<import file=\"../../build-common-osgi-plugin.xml\" />");

			boolean isSharedProject = content.contains(
				"<import file=\"../build-common-shared.xml\" />");

			if (!isOsgiProject && !isSharedProject) {
				continue;
			}

			setupJarProject(dirName, fileName);
		}
	}

	protected void addClasspathEntry(StringBundler sb, String jar) {
		addClasspathEntry(sb, jar, null);
	}

	protected void addClasspathEntry(
		StringBundler sb, String jar, Map<String, String> attributes) {

		sb.append("\t<classpathentry kind=\"lib\" path=\"");
		sb.append(jar);

		if ((attributes == null) || attributes.isEmpty()) {
			sb.append("\" />\n");

			return;
		}

		sb.append("\">\n\t\t<attributes>\n");

		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			sb.append("\t\t\t<attribute name=\"");
			sb.append(entry.getKey());
			sb.append("\" value=\"");
			sb.append(entry.getValue());
			sb.append("\" />\n");
		}

		sb.append("\t\t</attributes>\n\t</classpathentry>\n");
	}

	protected List<String> getCommonJars() {
		List<String> jars = new ArrayList<String>();

		jars.add("commons-logging.jar");
		jars.add("log4j.jar");
		jars.add("util-bridges.jar");
		jars.add("util-java.jar");
		jars.add("util-taglib.jar");

		return jars;
	}

	protected List<String> getImportSharedJars(File projectDir)
		throws Exception {

		File buildXmlFile = new File(projectDir, "build.xml");

		String content = _fileUtil.read(buildXmlFile);

		int x = content.indexOf("import.shared");

		if (x == -1) {
			return Collections.emptyList();
		}

		x = content.indexOf("value=\"", x);
		x = content.indexOf("\"", x);

		int y = content.indexOf("\" />", x);

		if ((x == -1) || (y == -1)) {
			return Collections.emptyList();
		}

		String[] importShared = StringUtil.split(content.substring(x + 1, y));

		if (importShared.length == 0) {
			return Collections.emptyList();
		}

		List<String> jars = new ArrayList<String>();

		for (String currentImportShared : importShared) {
			jars.add(currentImportShared + ".jar");

			File currentImportSharedLibDir = new File(
				projectDir, "/../../shared/" + currentImportShared + "/lib");

			if (!currentImportSharedLibDir.exists()) {
				continue;
			}

			for (File f : currentImportSharedLibDir.listFiles()) {
				jars.add(f.getName());
			}
		}

		return jars;
	}

	protected List<String> getPortalDependencyJars(Properties properties) {
		String[] dependencyJars = StringUtil.split(
			properties.getProperty(
				"portal-dependency-jars",
				properties.getProperty("portal.dependency.jars")));

		return ListUtil.toList(dependencyJars);
	}

	protected List<String> getRequiredDeploymentContextsJars(
			File libDir, Properties properties)
		throws Exception {

		List<String> jars = new ArrayList<String>();

		String[] requiredDeploymentContexts = StringUtil.split(
			properties.getProperty("required-deployment-contexts"));

		for (String requiredDeploymentContext : requiredDeploymentContexts) {
			if (_fileUtil.exists(
					libDir.getCanonicalPath() + "/" +
						requiredDeploymentContext + "-service.jar")) {

				jars.add(requiredDeploymentContext + "-service.jar");
			}
		}

		return jars;
	}

	protected void setupJarProject(String dirName, String fileName)
		throws Exception {

		File buildFile = new File(dirName + "/" + fileName);

		File projectDir = new File(buildFile.getParent());

		File libDir = new File(projectDir, "lib");

		List<String> dependencyJars = Collections.emptyList();

		writeEclipseFiles(libDir, projectDir, dependencyJars);
	}

	protected void setupWarProject(String dirName, String fileName)
		throws Exception {

		File propertiesFile = new File(dirName + "/" + fileName);

		Properties properties = new Properties();

		properties.load(new FileInputStream(propertiesFile));

		Set<String> jars = new TreeSet<String>();

		jars.addAll(getCommonJars());

		List<String> dependencyJars = getPortalDependencyJars(properties);

		jars.addAll(dependencyJars);

		File projectDir = new File(propertiesFile.getParent() + "/../..");

		jars.addAll(getImportSharedJars(projectDir));

		File libDir = new File(propertiesFile.getParent() + "/lib");

		jars.addAll(getRequiredDeploymentContextsJars(libDir, properties));

		writeEclipseFiles(libDir, projectDir, dependencyJars);

		String libDirPath = StringUtil.replace(
			libDir.getPath(), StringPool.BACK_SLASH, StringPool.SLASH);

		List<String> ignores = ListUtil.fromFile(
			libDir.getCanonicalPath() + "/../.gitignore");

		if (!libDirPath.contains("/ext/") && !ignores.contains("/lib")) {
			File gitignoreFile = new File(
				libDir.getCanonicalPath() + "/.gitignore");

			System.out.println("Updating " + gitignoreFile);

			String[] gitIgnores = jars.toArray(new String[jars.size()]);

			for (int i = 0; i < gitIgnores.length; i++) {
				String gitIgnore = gitIgnores[i];

				if (Validator.isNotNull(gitIgnore) &&
					!gitIgnore.startsWith("/")) {

					gitIgnores[i] = "/" + gitIgnore;
				}
			}

			_fileUtil.write(gitignoreFile, StringUtil.merge(gitIgnores, "\n"));
		}
	}

	protected void writeClasspathFile(
			File libDir, List<String> dependencyJars, String projectDirName,
			String projectName, boolean javaProject)
		throws Exception {

		File classpathFile = new File(projectDirName + "/.classpath");

		if (!javaProject) {
			classpathFile.delete();

			return;
		}

		List<String> globalJars = new UniqueList<String>();
		List<String> portalJars = new UniqueList<String>();

		List<String> extGlobalJars = new UniqueList<String>();
		List<String> extPortalJars = new UniqueList<String>();

		String libDirPath = StringUtil.replace(
			libDir.getPath(), StringPool.BACK_SLASH, StringPool.SLASH);

		if (libDirPath.contains("/ext/")) {
			FilenameFilter filenameFilter = new GlobFilenameFilter("*.jar");

			for (String dirName : new String[] {"global", "portal"}) {
				File file = new File(libDirPath + "/../ext-lib/" + dirName);

				List<String> jars = ListUtil.toList(file.list(filenameFilter));

				if (dirName.equals("global")) {
					extGlobalJars.addAll(ListUtil.sort(jars));

					File dir = new File(PropsValues.LIFERAY_LIB_GLOBAL_DIR);

					String[] fileNames = dir.list(filenameFilter);

					globalJars.addAll(
						ListUtil.sort(ListUtil.toList(fileNames)));
					globalJars.removeAll(extGlobalJars);
				}
				else if (dirName.equals("portal")) {
					extPortalJars.addAll(ListUtil.sort(jars));

					File dir = new File(PropsValues.LIFERAY_LIB_PORTAL_DIR);

					String[] fileNames = dir.list(filenameFilter);

					portalJars.addAll(
						ListUtil.sort(ListUtil.toList(fileNames)));
					portalJars.removeAll(extPortalJars);
				}
			}
		}
		else {
			globalJars.add("portlet.jar");

			portalJars.addAll(dependencyJars);
			portalJars.add("commons-logging.jar");
			portalJars.add("log4j.jar");

			Collections.sort(portalJars);
		}

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

		for (String sourceDirName : _SOURCE_DIR_NAMES) {
			if (_fileUtil.exists(projectDirName + "/" + sourceDirName)) {
				sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
				sb.append("kind=\"src\" path=\"" + sourceDirName + "\" />\n");
			}
		}

		sb.append("\t<classpathentry kind=\"src\" path=\"/portal\" />\n");
		sb.append("\t<classpathentry kind=\"con\" ");
		sb.append("path=\"org.eclipse.jdt.launching.JRE_CONTAINER\" />\n");

		boolean addJunitJars = false;

		for (String testType : _TEST_TYPES) {
			String testFolder = "test/" + testType;

			if (_fileUtil.exists(projectDirName + "/" + testFolder)) {
				addJunitJars = true;

				sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
				sb.append("kind=\"src\" path=\""+ testFolder + "\" />\n");
			}
		}

		if (addJunitJars) {
			addClasspathEntry(sb, "/portal/lib/development/junit.jar");
			addClasspathEntry(sb, "/portal/lib/development/mockito.jar");
			addClasspathEntry(
				sb, "/portal/lib/development/powermock-mockito.jar");
			addClasspathEntry(sb, "/portal/lib/development/spring-test.jar");
			addClasspathEntry(sb, "/portal/lib/portal/commons-io.jar");
		}

		addClasspathEntry(sb, "/portal/lib/development/activation.jar");
		addClasspathEntry(sb, "/portal/lib/development/annotations.jar");
		addClasspathEntry(sb, "/portal/lib/development/jsp-api.jar");
		addClasspathEntry(sb, "/portal/lib/development/mail.jar");
		addClasspathEntry(sb, "/portal/lib/development/servlet-api.jar");

		Map<String, String> attributes = new HashMap<String, String>();

		if (libDirPath.contains("/ext/")) {
			attributes.put("optional", "true");
		}

		for (String jar : globalJars) {
			addClasspathEntry(sb, "/portal/lib/global/" + jar, attributes);
		}

		for (String jar : portalJars) {
			addClasspathEntry(sb, "/portal/lib/portal/" + jar, attributes);
		}

		addClasspathEntry(sb, "/portal/portal-service/portal-service.jar");
		addClasspathEntry(sb, "/portal/util-bridges/util-bridges.jar");
		addClasspathEntry(sb, "/portal/util-java/util-java.jar");
		addClasspathEntry(sb, "/portal/util-taglib/util-taglib.jar");

		for (String jar : extGlobalJars) {
			addClasspathEntry(sb, "docroot/WEB-INF/ext-lib/global/" + jar);
		}

		for (String jar : extPortalJars) {
			addClasspathEntry(sb, "docroot/WEB-INF/ext-lib/portal/" + jar);
		}

		for (String jar : customJars) {
			if (libDirPath.contains("/tmp/WEB-INF/lib")) {
				addClasspathEntry(sb, "tmp/WEB-INF/lib/" + jar);
			}
			else if (libDirPath.contains("/docroot/WEB-INF/lib")) {
				addClasspathEntry(sb, "docroot/WEB-INF/lib/" + jar);
			}
			else {
				addClasspathEntry(sb, "lib/" + jar);
			}
		}

		writeOsgiBundleDependencies(sb, projectDirName);

		sb.append("\t<classpathentry kind=\"output\" path=\"bin\" />\n");
		sb.append("</classpath>");

		System.out.println("Updating " + classpathFile);

		String content = StringUtil.replace(
			sb.toString(), "\"/portal", "\"/portal-" + _BRANCH);

		_fileUtil.write(classpathFile, content);
	}

	protected void writeEclipseFiles(
			File libDir, File projectDir, List<String> dependencyJars)
		throws Exception {

		String projectDirName = projectDir.getCanonicalPath();

		String projectName = StringUtil.extractLast(
			projectDirName, File.separatorChar);

		boolean javaProject = false;

		for (String sourceDirName : _SOURCE_DIR_NAMES) {
			if (_fileUtil.exists(projectDirName + "/" + sourceDirName)) {
				javaProject = true;

				break;
			}
		}

		if (!javaProject) {
			System.out.println(
				"Eclipse Java project will not be used because a source " +
					"folder does not exist");
		}

		writeProjectFile(projectDirName, projectName, javaProject);

		writeClasspathFile(
			libDir, dependencyJars, projectDirName, projectName, javaProject);

		for (String sourceDirName : _SOURCE_DIR_NAMES) {
			if (_fileUtil.exists(projectDirName + "/" + sourceDirName)) {
				List<String> gitIgnores = new ArrayList<String>();

				if (sourceDirName.endsWith("ext-impl/src")) {
					gitIgnores.add("/classes");
					gitIgnores.add("/ext-impl.jar");
				}
				else if (sourceDirName.endsWith("ext-service/src")) {
					gitIgnores.add("/classes");
					gitIgnores.add("/ext-service.jar");
				}
				else if (sourceDirName.endsWith("ext-util-bridges/src")) {
					gitIgnores.add("/classes");
					gitIgnores.add("/ext-util-bridges.jar");
				}
				else if (sourceDirName.endsWith("ext-util-java/src")) {
					gitIgnores.add("/classes");
					gitIgnores.add("/ext-util-java.jar");
				}
				else if (sourceDirName.endsWith("ext-util-taglib/src")) {
					gitIgnores.add("/classes");
					gitIgnores.add("/ext-util-taglib.jar");
				}
				else {
					continue;
				}

				String dirName = projectDirName + "/" + sourceDirName + "/../";

				if (gitIgnores.isEmpty()) {
					_fileUtil.delete(dirName + ".gitignore");
				}
				else {
					String gitIgnoresString = StringUtil.merge(
						gitIgnores, "\n");

					_fileUtil.write(dirName + ".gitignore", gitIgnoresString);
				}
			}
		}

		if (_fileUtil.exists(projectDirName + "/test")) {
			_fileUtil.write(
				projectDirName + "/.gitignore", "/test-classes\n/test-results");
		}
		else {
			_fileUtil.delete(projectDirName + "/.gitignore");
		}
	}

	protected void writeOsgiBundleDependencies(
			StringBundler sb, String projectDir)
		throws Exception {

		String content = _fileUtil.read(projectDir + "/build.xml");

		int index = content.indexOf("plugin-lib.classpath");

		if (index == -1) {
			return;
		}

		int start = content.indexOf("includes=\"", index);
		start = content.indexOf("\"", start);

		int end = content.indexOf("\"", start + 1);

		if ((start == -1) || (end == -1)) {
			return;
		}

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(projectDir + "/../../lib/");
		ds.setIncludes(StringUtil.split(content.substring(start + 1, end)));

		ds.scan();

		for (String file : ds.getIncludedFiles()) {
			addClasspathEntry(sb, "../../lib/" + file);
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

	private static final String _BRANCH = "master";

	private static final String[] _SOURCE_DIR_NAMES = new String[] {
		"docroot/WEB-INF/ext-impl/src", "docroot/WEB-INF/ext-service/src",
		"docroot/WEB-INF/ext-util-bridges/src",
		"docroot/WEB-INF/ext-util-java/src",
		"docroot/WEB-INF/ext-util-taglib/src", "docroot/WEB-INF/service",
		"docroot/WEB-INF/src", "src"
	};

	private static final String[] _TEST_TYPES = {"integration", "unit"};

	private static FileImpl _fileUtil = FileImpl.getInstance();

}