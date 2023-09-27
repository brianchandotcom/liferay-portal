/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.alloy.taglib;

import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SkipWhenEmpty;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildTaglibsTask extends JavaExec {

	public BuildTaglibsTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.alloy.tools.tagbuilder.TagBuilder");
	}

	public BuildTaglibsTask componentsXmlFiles(Iterable<?> componentsXmlFiles) {
		GUtil.addToCollection(_componentsXmlFiles, componentsXmlFiles);

		return this;
	}

	public BuildTaglibsTask componentsXmlFiles(Object... componentsXmlFiles) {
		return componentsXmlFiles(Arrays.asList(componentsXmlFiles));
	}

	@Override
	public void exec() {
		setSystemProperties(_getCompleteSystemProperties());

		super.exec();
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	@SkipWhenEmpty
	public FileCollection getComponentsXmlFiles() {
		Project project = getProject();

		return project.files(_componentsXmlFiles.toArray());
	}

	@Input
	public String getCopyrightYear() {
		return GradleUtil.toString(_copyrightYear);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getJavaDir() {
		File file = GradleUtil.toFile(getProject(), _javaDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@Input
	public String getJavaPackage() {
		return GradleUtil.toString(_javaPackage);
	}

	@Input
	public String getJspCommonInitPath() {
		return GradleUtil.toString(_jspCommonInitPath);
	}

	@Input
	public String getJspDirName() {
		return GradleUtil.toString(_jspDirName);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getJspParentDir() {
		File file = GradleUtil.toFile(getProject(), _jspParentDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	@Input
	@Optional
	public String getOsgiModuleSymbolicName() {
		return GradleUtil.toString(_osgiModuleSymbolicName);
	}

	@Input
	public String getTemplatesDirName() {
		return GradleUtil.toString(_templatesDirName);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getTldDir() {
		File file = GradleUtil.toFile(getProject(), _tldDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	public void setComponentsXmlFiles(Iterable<?> componentsXmlFiles) {
		_componentsXmlFiles.clear();

		componentsXmlFiles(componentsXmlFiles);
	}

	public void setComponentsXmlFiles(Object... componentsXmlFiles) {
		setComponentsXmlFiles(Arrays.asList(componentsXmlFiles));
	}

	public void setCopyrightYear(Object copyrightYear) {
		_copyrightYear = copyrightYear;
	}

	public void setJavaDir(Object javaDir) {
		_javaDir = javaDir;
	}

	public void setJavaPackage(Object javaPackage) {
		_javaPackage = javaPackage;
	}

	public void setJspCommonInitPath(Object jspCommonInitPath) {
		_jspCommonInitPath = jspCommonInitPath;
	}

	public void setJspDirName(Object jspDirName) {
		_jspDirName = jspDirName;
	}

	public void setJspParentDir(Object jspParentDir) {
		_jspParentDir = jspParentDir;
	}

	public void setOsgiModuleSymbolicName(Object osgiModuleSymbolicName) {
		_osgiModuleSymbolicName = osgiModuleSymbolicName;
	}

	public void setTemplatesDirName(Object templatesDirName) {
		_templatesDirName = templatesDirName;
	}

	public void setTldDir(Object tldDir) {
		_tldDir = tldDir;
	}

	private Map<String, Object> _getCompleteSystemProperties() {
		Map<String, Object> systemProperties = new HashMap<>(
			getSystemProperties());

		systemProperties.put(
			"tagbuilder.components.xml",
			_getRelativePaths(getComponentsXmlFiles()));
		systemProperties.put("tagbuilder.copyright.year", getCopyrightYear());
		systemProperties.put(
			"tagbuilder.java.dir", _getRelativePath(getJavaDir()) + "/");
		systemProperties.put("tagbuilder.java.package", getJavaPackage());
		systemProperties.put(
			"tagbuilder.jsp.common.init.path", getJspCommonInitPath());

		String jspDirName = getJspDirName();

		if (!jspDirName.endsWith("/")) {
			jspDirName = jspDirName + "/";
		}

		systemProperties.put("tagbuilder.jsp.dir", jspDirName);

		systemProperties.put(
			"tagbuilder.jsp.parent.dir",
			_getRelativePath(getJspParentDir()) + "/");

		String osgiModuleSymbolicName = getOsgiModuleSymbolicName();

		if (Validator.isNotNull(osgiModuleSymbolicName)) {
			systemProperties.put(
				"tagbuilder.osgi.module.symbolic.name", osgiModuleSymbolicName);
		}

		systemProperties.put("tagbuilder.templates.dir", getTemplatesDirName());
		systemProperties.put(
			"tagbuilder.tld.dir", _getRelativePath(getTldDir()) + "/");

		return systemProperties;
	}

	private String _getRelativePath(File file) {
		Project project = getProject();

		String relativePath = project.relativePath(file);

		return relativePath.replace('\\', '/');
	}

	private String _getRelativePaths(Iterable<File> files) {
		List<String> relativePaths = new ArrayList<>();

		for (File file : files) {
			relativePaths.add(_getRelativePath(file));
		}

		return StringUtil.merge(relativePaths.toArray(new String[0]), ",");
	}

	private final List<Object> _componentsXmlFiles = new ArrayList<>();
	private Object _copyrightYear = "present";
	private Object _javaDir;
	private Object _javaPackage;
	private Object _jspCommonInitPath = "/init.jsp";
	private Object _jspDirName = "/";
	private Object _jspParentDir;
	private Object _osgiModuleSymbolicName;
	private Object _templatesDirName =
		"com/liferay/alloy/tools/tagbuilder/templates/";
	private Object _tldDir;

}