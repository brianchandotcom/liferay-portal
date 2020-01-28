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

package com.liferay.portal.tools;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.FileComparator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public PluginsEnvironmentBuilder(File dir) throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(dir);
		directoryScanner.setIncludes(
			new String[] {"**\\liferay-plugin-package.properties"});

		directoryScanner.scan();

		String dirName = dir.getCanonicalPath();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			setupWarProject(dirName, fileName);
		}

		directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(dir);
		directoryScanner.setIncludes(new String[] {"**\\build.xml"});

		directoryScanner.scan();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			String content = _fileImpl.read(dirName + "/" + fileName);

			boolean osgiProject = false;

			if (content.contains("../build-common-osgi-plugin.xml\" />") ||
				content.contains(
					"../tools/sdk/build-common-osgi-plugin.xml\" />")) {

				osgiProject = true;
			}

			boolean sharedProject = false;

			if (content.contains(
					"<import file=\"../build-common-shared.xml\" />") ||
				content.contains("../tools/sdk/build-common-shared.xml\" />")) {

				sharedProject = true;
			}

			List<String> dependencyJars = Collections.emptyList();

			if (osgiProject) {
				int x = content.indexOf("osgi.ide.dependencies");

				if (x != -1) {
					x = content.indexOf("value=\"", x);
					x = content.indexOf("\"", x);

					int y = content.indexOf("\"", x + 1);

					dependencyJars = Arrays.asList(
						StringUtil.split(content.substring(x + 1, y)));
				}
			}

			if (osgiProject || sharedProject) {
				setupJarProject(
					dirName, fileName, dependencyJars, sharedProject);
			}
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

	protected void addIvyCacheJar(
			StringBundler sb, String ivyDirName, String dependencyName,
			String version)
		throws Exception {

		String string = sb.toString();

		if (string.contains(dependencyName)) {
			System.out.println(
				StringBundler.concat(
					"Skipping duplicate ", dependencyName, " ", version));

			return;
		}

		System.out.println(
			StringBundler.concat("Adding ", dependencyName, " ", version));

		if (version.equals("latest.integration")) {
			File dir = new File(ivyDirName + "/cache/" + dependencyName);

			File[] files = dir.listFiles();

			Arrays.sort(files, new FileComparator());

			for (int i = files.length - 1; i >= 0; i--) {
				File file = files[i];

				if (!file.isFile()) {
					continue;
				}

				String fileName = file.getName();

				if (!fileName.endsWith(".xml")) {
					continue;
				}

				version = fileName.substring(4, fileName.length() - 4);

				System.out.println(
					"Substituting " + version + " for latest.integration");
			}
		}

		String ivyFileName = StringBundler.concat(
			ivyDirName, "/cache/", dependencyName, "/ivy-", version, ".xml");

		if (_fileImpl.exists(ivyFileName)) {
			Document document = _saxReader.read(new File(ivyFileName));

			Element rootElement = document.getRootElement();

			Element dependenciesElement = rootElement.element("dependencies");

			if (dependenciesElement != null) {
				List<Element> dependencyElements = dependenciesElement.elements(
					"dependency");

				for (Element dependencyElement : dependencyElements) {
					String conf = GetterUtil.getString(
						dependencyElement.attributeValue("conf"));

					if (!conf.startsWith("compile")) {
						continue;
					}

					String name = GetterUtil.getString(
						dependencyElement.attributeValue("name"));

					string = sb.toString();

					if (string.contains(name)) {
						continue;
					}

					String org = GetterUtil.getString(
						dependencyElement.attributeValue("org"));
					String rev = GetterUtil.getString(
						dependencyElement.attributeValue("rev"));

					addIvyCacheJar(sb, ivyDirName, org + "/" + name, rev);
				}
			}
		}

		String dirName = StringBundler.concat(
			ivyDirName, "/cache/", dependencyName, "/bundles");

		if (!_fileImpl.exists(dirName)) {
			dirName = StringBundler.concat(
				ivyDirName, "/cache/", dependencyName, "/jars");

			if (!_fileImpl.exists(dirName)) {
				System.out.println("Unable to find jars in " + dirName);

				return;
			}
		}

		File dir = new File(dirName);

		File[] files = dir.listFiles();

		for (File file : files) {
			if (!file.isFile()) {
				continue;
			}

			String fileName = file.getName();

			if (!fileName.endsWith("-" + version + ".jar")) {
				continue;
			}

			int index = dirName.indexOf("/.ivy");

			String eclipseRelativeDirName =
				"/portal" + dirName.substring(index);

			addClasspathEntry(sb, eclipseRelativeDirName + "/" + fileName);

			return;
		}

		System.out.println(
			StringBundler.concat(
				"Unable to find jars in ", dirName, " for ", version));
	}

	protected void addIvyCacheJars(
			StringBundler sb, String content, String ivyDirName)
		throws Exception {

		Document document = _saxReader.read(content);

		Element rootElement = document.getRootElement();

		Element dependenciesElement = rootElement.element("dependencies");

		List<Element> dependencyElements = dependenciesElement.elements(
			"dependency");

		for (Element dependencyElement : dependencyElements) {
			String conf = GetterUtil.getString(
				dependencyElement.attributeValue("conf"));

			if (!conf.equals("test->default")) {
				continue;
			}

			String name = GetterUtil.getString(
				dependencyElement.attributeValue("name"));
			String org = GetterUtil.getString(
				dependencyElement.attributeValue("org"));
			String rev = GetterUtil.getString(
				dependencyElement.attributeValue("rev"));

			addIvyCacheJar(sb, ivyDirName, org + "/" + name, rev);
		}
	}

	protected List<String> getCommonJars() {
		List<String> jars = new ArrayList<>();

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

		String content = _fileImpl.read(buildXmlFile);

		int x = content.indexOf("import.shared");

		if (x == -1) {
			return new ArrayList<>();
		}

		x = content.indexOf("value=\"", x);
		x = content.indexOf("\"", x);

		int y = content.indexOf("\" />", x);

		if ((x == -1) || (y == -1)) {
			return new ArrayList<>();
		}

		String[] importShared = StringUtil.split(content.substring(x + 1, y));

		if (importShared.length == 0) {
			return new ArrayList<>();
		}

		List<String> jars = new ArrayList<>();

		for (String currentImportShared : importShared) {
			jars.add(currentImportShared + ".jar");

			File currentImportSharedLibDir = new File(
				projectDir, "../../shared/" + currentImportShared + "/lib");

			if (!currentImportSharedLibDir.exists()) {
				continue;
			}

			for (File file : currentImportSharedLibDir.listFiles()) {
				jars.add(file.getName());
			}
		}

		return jars;
	}

	protected List<String> getPortalDependencyJars(Properties properties) {
		String[] dependencyJars = StringUtil.split(
			properties.getProperty(
				"portal-dependency-jars",
				properties.getProperty("portal.dependency.jars")));

		return ListUtil.fromArray(dependencyJars);
	}

	protected List<String> getRequiredDeploymentContextsJars(
			File libDir, Properties properties)
		throws Exception {

		List<String> jars = new ArrayList<>();

		String[] requiredDeploymentContexts = StringUtil.split(
			properties.getProperty("required-deployment-contexts"));

		for (String requiredDeploymentContext : requiredDeploymentContexts) {
			if (_fileImpl.exists(
					StringBundler.concat(
						libDir.getCanonicalPath(), "/",
						requiredDeploymentContext, "-service.jar"))) {

				jars.add(requiredDeploymentContext + "-service.jar");
			}
		}

		return jars;
	}

	protected boolean hasModulesGitIgnore(String dirName) {
		int index = dirName.indexOf("/modules/");

		if (index == -1) {
			return false;
		}

		return _fileImpl.exists(
			dirName.substring(0, index) + "/modules/.gitignore");
	}

	protected void setupJarProject(
			String dirName, String fileName, List<String> dependencyJars,
			boolean sharedProject)
		throws Exception {

		File buildFile = new File(dirName + "/" + fileName);

		File projectDir = new File(buildFile.getParent());

		File libDir = new File(projectDir, "lib");

		if (!libDir.exists()) {
			libDir = new File(projectDir, "docroot/WEB-INF/lib");
		}

		writeEclipseFiles(libDir, projectDir, dependencyJars);

		List<String> importSharedJars = getImportSharedJars(projectDir);

		if (sharedProject &&
			!importSharedJars.contains("portal-compat-shared.jar")) {

			importSharedJars.add("portal-compat-shared.jar");
		}

		File gitignoreFile = new File(
			projectDir.getCanonicalPath() + "/.gitignore");

		if (hasModulesGitIgnore(dirName)) {
			gitignoreFile.delete();

			return;
		}

		String[] gitIgnores = importSharedJars.toArray(new String[0]);

		for (int i = 0; i < gitIgnores.length; i++) {
			String gitIgnore = gitIgnores[i];

			gitIgnore = "/lib/" + gitIgnore;

			gitIgnores[i] = gitIgnore;
		}

		if (gitIgnores.length > 0) {
			System.out.println("Updating " + gitignoreFile);

			_fileImpl.write(gitignoreFile, StringUtil.merge(gitIgnores, "\n"));
		}
	}

	protected void setupWarProject(String dirName, String fileName)
		throws Exception {

		File propertiesFile = new File(dirName + "/" + fileName);

		Properties properties = new Properties();

		properties.load(new FileInputStream(propertiesFile));

		Set<String> jars = new TreeSet<>();

		jars.addAll(getCommonJars());

		List<String> dependencyJars = getPortalDependencyJars(properties);

		jars.addAll(dependencyJars);

		File projectDir = new File(propertiesFile.getParent() + "/../..");

		jars.addAll(getImportSharedJars(projectDir));

		File libDir = new File(propertiesFile.getParent() + "/lib");

		jars.addAll(getRequiredDeploymentContextsJars(libDir, properties));

		writeEclipseFiles(libDir, projectDir, dependencyJars);

		String libDirPath = StringUtil.replace(
			libDir.getPath(), CharPool.BACK_SLASH, CharPool.SLASH);

		List<String> ignores = ListUtil.fromFile(
			libDir.getCanonicalPath() + "/../.gitignore");

		if (libDirPath.contains("/ext/") || ignores.contains("/lib")) {
			return;
		}

		File gitignoreFile = new File(
			libDir.getCanonicalPath() + "/.gitignore");

		System.out.println("Updating " + gitignoreFile);

		String[] gitIgnores = jars.toArray(new String[0]);

		for (int i = 0; i < gitIgnores.length; i++) {
			String gitIgnore = gitIgnores[i];

			if (Validator.isNotNull(gitIgnore) && !gitIgnore.startsWith("/")) {
				gitIgnores[i] = "/" + gitIgnore;
			}
		}

		_fileImpl.write(gitignoreFile, StringUtil.merge(gitIgnores, "\n"));
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

		Set<String> globalJars = new LinkedHashSet<>();
		List<String> portalJars = new ArrayList<>();

		Set<String> extGlobalJars = new LinkedHashSet<>();
		Set<String> extPortalJars = new LinkedHashSet<>();

		String libDirPath = StringUtil.replace(
			libDir.getPath(), CharPool.BACK_SLASH, CharPool.SLASH);

		if (libDirPath.contains("/ext/")) {
			FilenameFilter filenameFilter = new GlobFilenameFilter("*.jar");

			for (String dirName : new String[] {"global", "portal"}) {
				File file = new File(libDirPath + "/../ext-lib/" + dirName);

				List<String> jars = ListUtil.fromArray(
					file.list(filenameFilter));

				if (dirName.equals("global")) {
					extGlobalJars.addAll(ListUtil.sort(jars));

					File dir = new File(PropsValues.LIFERAY_LIB_GLOBAL_DIR);

					String[] fileNames = dir.list(filenameFilter);

					globalJars.addAll(
						ListUtil.sort(ListUtil.fromArray(fileNames)));

					globalJars.removeAll(extGlobalJars);
				}
				else if (dirName.equals("portal")) {
					extPortalJars.addAll(ListUtil.sort(jars));

					File dir = new File(PropsValues.LIFERAY_LIB_PORTAL_DIR);

					String[] fileNames = dir.list(filenameFilter);

					portalJars.addAll(
						ListUtil.sort(ListUtil.fromArray(fileNames)));

					portalJars.removeAll(extPortalJars);
				}
			}
		}
		else {
			globalJars.add("portlet.jar");

			portalJars.addAll(dependencyJars);
			portalJars.add("commons-logging.jar");
			portalJars.add("log4j.jar");

			portalJars = ListUtil.unique(portalJars);

			Collections.sort(portalJars);
		}

		String[] customJarsArray = libDir.list(new GlobFilenameFilter("*.jar"));

		List<String> customJars = null;

		if (customJarsArray != null) {
			customJars = ListUtil.fromArray(customJarsArray);

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
			customJars = new ArrayList<>();
		}

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		sb.append("<classpath>\n");

		for (String sourceDirName : _SOURCE_DIR_NAMES) {
			if (_fileImpl.exists(projectDirName + "/" + sourceDirName)) {
				sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
				sb.append("kind=\"src\" path=\"");
				sb.append(sourceDirName);
				sb.append("\" />\n");
			}
		}

		sb.append("\t<classpathentry kind=\"src\" path=\"/portal\" />\n");
		sb.append("\t<classpathentry kind=\"con\" ");
		sb.append("path=\"org.eclipse.jdt.launching.JRE_CONTAINER\" />\n");

		boolean addJunitJars = false;

		for (String testType : _TEST_TYPES) {
			String testFolder = "test/" + testType;

			if (_fileImpl.exists(projectDirName + "/" + testFolder)) {
				addJunitJars = true;

				sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
				sb.append("kind=\"src\" path=\"");
				sb.append(testFolder);
				sb.append("\" />\n");
			}
		}

		if (addJunitJars) {
			addClasspathEntry(sb, "/portal/lib/development/junit.jar");
			addClasspathEntry(sb, "/portal/lib/development/mockito.jar");
			addClasspathEntry(
				sb, "/portal/lib/development/powermock-api-mockito.jar");
			addClasspathEntry(
				sb, "/portal/lib/development/powermock-api-support.jar");
			addClasspathEntry(sb, "/portal/lib/development/powermock-core.jar");
			addClasspathEntry(
				sb, "/portal/lib/development/powermock-module-junit4.jar");
			addClasspathEntry(
				sb,
				"/portal/lib/development/powermock-module-junit4-common.jar");
			addClasspathEntry(sb, "/portal/lib/development/spring-test.jar");

			portalJars.add("commons-io.jar");
			portalJars.add("commons-lang.jar");
		}

		addClasspathEntry(sb, "/portal/lib/development/activation.jar");
		addClasspathEntry(sb, "/portal/lib/development/annotations.jar");
		addClasspathEntry(sb, "/portal/lib/development/jsp-api.jar");
		addClasspathEntry(sb, "/portal/lib/development/mail.jar");
		addClasspathEntry(sb, "/portal/lib/development/servlet-api.jar");

		Map<String, String> attributes = new HashMap<>();

		if (libDirPath.contains("/ext/")) {
			attributes.put("optional", "true");
		}

		for (String jar : globalJars) {
			addClasspathEntry(sb, "/portal/lib/global/" + jar, attributes);
		}

		portalJars = ListUtil.unique(portalJars);

		Collections.sort(portalJars);

		for (String jar : portalJars) {
			if (!jar.equals("util-slf4j.jar")) {
				addClasspathEntry(sb, "/portal/lib/portal/" + jar, attributes);
			}
		}

		addClasspathEntry(sb, "/portal/portal-kernel/portal-kernel.jar");
		addClasspathEntry(sb, "/portal/util-bridges/util-bridges.jar");
		addClasspathEntry(sb, "/portal/util-java/util-java.jar");

		if (portalJars.contains("util-slf4j.jar")) {
			addClasspathEntry(sb, "/portal/util-slf4j/util-slf4j.jar");
		}

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

		File ivyXmlFile = new File(projectDirName, "ivy.xml");

		if (ivyXmlFile.exists()) {
			String content = _fileImpl.read(ivyXmlFile);

			if (content.contains("test->default")) {
				String ivyDirName = ".ivy";

				for (int i = 0; i < 10; i++) {
					if (_fileImpl.exists(ivyDirName)) {
						break;
					}

					ivyDirName = "../" + ivyDirName;
				}

				addIvyCacheJars(sb, content, ivyDirName);
			}
		}

		sb.append("\t<classpathentry kind=\"output\" path=\"bin\" />\n");
		sb.append("</classpath>");

		System.out.println("Updating " + classpathFile);

		String content = StringUtil.replace(
			sb.toString(), "\"/portal", "\"/portal-" + _BRANCH);

		_fileImpl.write(classpathFile, content);
	}

	protected void writeEclipseFiles(
			File libDir, File projectDir, List<String> dependencyJars)
		throws Exception {

		String projectDirName = projectDir.getCanonicalPath();

		String projectName = StringUtil.extractLast(
			projectDirName, File.separatorChar);

		boolean javaProject = false;

		for (String sourceDirName : _SOURCE_DIR_NAMES) {
			if (_fileImpl.exists(projectDirName + "/" + sourceDirName)) {
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
			if (!_fileImpl.exists(projectDirName + "/" + sourceDirName)) {
				continue;
			}

			List<String> gitIgnores = new ArrayList<>();

			if (sourceDirName.endsWith("ext-impl/src")) {
				gitIgnores.add("/classes");
				gitIgnores.add("/ext-impl.jar");
			}
			else if (sourceDirName.endsWith("ext-kernel/src")) {
				gitIgnores.add("/classes");
				gitIgnores.add("/ext-kernel.jar");
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

			String dirName = StringBundler.concat(
				projectDirName, "/", sourceDirName, "/../");

			if (gitIgnores.isEmpty()) {
				_fileImpl.delete(dirName + ".gitignore");
			}
			else {
				String gitIgnoresString = StringUtil.merge(gitIgnores, "\n");

				_fileImpl.write(dirName + ".gitignore", gitIgnoresString);
			}
		}

		if (_fileImpl.exists(projectDirName + "/test")) {
			_fileImpl.write(
				projectDirName + "/.gitignore", "/test-classes\n/test-results");
		}
		else {
			_fileImpl.delete(projectDirName + "/.gitignore");
		}
	}

	protected void writeProjectFile(
			String projectDirName, String projectName, boolean javaProject)
		throws Exception {

		StringBundler sb = new StringBundler(19);

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

		_fileImpl.write(projectFile, sb.toString());
	}

	private static final String _BRANCH = "master";

	private static final String[] _SOURCE_DIR_NAMES = {
		"docroot/WEB-INF/ext-impl/src", "docroot/WEB-INF/ext-kernel/src",
		"docroot/WEB-INF/ext-util-bridges/src",
		"docroot/WEB-INF/ext-util-java/src",
		"docroot/WEB-INF/ext-util-taglib/src", "docroot/WEB-INF/service",
		"docroot/WEB-INF/src", "src"
	};

	private static final String[] _TEST_TYPES = {"integration", "unit"};

	private static final FileImpl _fileImpl = FileImpl.getInstance();
	private static final SAXReader _saxReader = new SAXReaderImpl();

}