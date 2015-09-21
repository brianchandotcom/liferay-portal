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

package com.liferay.gradle.plugins.alloy.taglib;

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
import org.gradle.api.tasks.JavaExec;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class BuildTaglibsTask extends JavaExec {

	public BuildTaglibsTask componentsXmlFiles(Iterable<?> componentsXmlFiles) {
		GUtil.addToCollection(_componentsXmlFiles, componentsXmlFiles);

		return this;
	}

	public BuildTaglibsTask componentsXmlFiles(Object ... componentsXmlFiles) {
		return componentsXmlFiles(Arrays.asList(componentsXmlFiles));
	}

	@Override
	public void exec() {
		super.setSystemProperties(getSystemProperties());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	public FileCollection getComponentsXmlFiles() {
		Project project = getProject();

		return project.files(_componentsXmlFiles.toArray());
	}

	public String getCopyrightYear() {
		return GradleUtil.toString(_copyrightYear);
	}

	public File getJavaDir() {
		return GradleUtil.toFile(getProject(), _javaDir);
	}

	public String getJavaPackage() {
		return GradleUtil.toString(_javaPackage);
	}

	public String getJspCommonInitPath() {
		return GradleUtil.toString(_jspCommonInitPath);
	}

	public String getJspDirName() {
		return GradleUtil.toString(_jspDirName);
	}

	public File getJspParentDir() {
		return GradleUtil.toFile(getProject(), _jspParentDir);
	}

	@Override
	public String getMain() {
		return "com.liferay.alloy.tools.tagbuilder.TagBuilder";
	}

	public String getOsgiModuleSymbolicName() {
		return GradleUtil.toString(_osgiModuleSymbolicName);
	}

	@Override
	public Map<String, Object> getSystemProperties() {
		Map<String, Object> systemProperties = new HashMap<>();

		systemProperties.put(
			"tagbuilder.components.xml",
			getRelativePaths(getComponentsXmlFiles()));
		systemProperties.put("tagbuilder.copyright.year", getCopyrightYear());
		systemProperties.put(
			"tagbuilder.java.dir", getRelativePath(getJavaDir()) + "/");
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
			getRelativePath(getJspParentDir()) + "/");

		String osgiModuleSymbolicName = getOsgiModuleSymbolicName();

		if (Validator.isNotNull(osgiModuleSymbolicName)) {
			systemProperties.put(
				"tagbuilder.osgi.module.symbolic.name", osgiModuleSymbolicName);
		}

		systemProperties.put("tagbuilder.templates.dir", getTemplatesDirName());
		systemProperties.put(
			"tagbuilder.tld.dir", getRelativePath(getTldDir()) + "/");

		return systemProperties;
	}

	public String getTemplatesDirName() {
		return GradleUtil.toString(_templatesDirName);
	}

	public File getTldDir() {
		return GradleUtil.toFile(getProject(), _tldDir);
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	public void setComponentsXmlFiles(Iterable<?> componentsXmlFiles) {
		_componentsXmlFiles.clear();

		componentsXmlFiles(componentsXmlFiles);
	}

	public void setComponentsXmlFiles(Object ... componentsXmlFiles) {
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

	@Override
	public void setSystemProperties(Map<String, ?> properties) {
		throw new UnsupportedOperationException();
	}

	public void setTemplatesDirName(Object templatesDirName) {
		_templatesDirName = templatesDirName;
	}

	public void setTldDir(Object tldDir) {
		_tldDir = tldDir;
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec systemProperties(Map<String, ?> properties) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec systemProperty(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	protected String getComponentsXml() {
		FileCollection fileCollection = getComponentsXmlFiles();

		if (fileCollection.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		Project project = getProject();

		for (File file : fileCollection) {
			sb.append(project.relativePath(file));
			sb.append(',');
		}

		return sb.substring(0, sb.length() - 1);
	}

	protected String getRelativePath(File file) {
		Project project = getProject();

		String relativePath = project.relativePath(file);

		return relativePath.replace('\\', '/');
	}

	protected String getRelativePaths(Iterable<File> files) {
		List<String> relativePaths = new ArrayList<>();

		for (File file : files) {
			relativePaths.add(getRelativePath(file));
		}

		return StringUtil.merge(
			relativePaths.toArray(new String[relativePaths.size()]), ",");
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