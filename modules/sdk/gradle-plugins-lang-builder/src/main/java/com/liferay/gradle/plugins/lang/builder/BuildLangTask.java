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

package com.liferay.gradle.plugins.lang.builder;

import com.liferay.lang.builder.LangBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildLangTask extends JavaExec {

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("lang.dir=" + getLangDirName());
		args.add("lang.file=" + getLangFileName());
		args.add("lang.plugin=" + isPlugin());
		args.add(
			"lang.portal.language.properties.file=" +
				getPortalLanguagePropertiesFileName());
		args.add("lang.translate=" + isTranslate());
		args.add("lang.translate.client.id=" + getTranslateClientId());
		args.add("lang.translate.client.secret=" + getTranslateClientSecret());

		return args;
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			LangBuilderPlugin.CONFIGURATION_NAME);
	}

	public String getLangDirName() {
		return _langBuilderArgs.getLangDirName();
	}

	public String getLangFileName() {
		return _langBuilderArgs.getLangFileName();
	}

	@Override
	public String getMain() {
		return "com.liferay.lang.builder.LangBuilder";
	}

	public String getPortalLanguagePropertiesFileName() {
		return _langBuilderArgs.getPortalLanguagePropertiesFileName();
	}

	public String getTranslateClientId() {
		return _langBuilderArgs.getTranslateClientId();
	}

	public String getTranslateClientSecret() {
		return _langBuilderArgs.getTranslateClientSecret();
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	public boolean isPlugin() {
		return _langBuilderArgs.isPlugin();
	}

	public boolean isTranslate() {
		return _langBuilderArgs.isTranslate();
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setLangDirName(String langDirName) {
		_langBuilderArgs.setLangDirName(langDirName);
	}

	public void setLangFileName(String langFileName) {
		_langBuilderArgs.setLangFileName(langFileName);
	}

	public void setPlugin(boolean plugin) {
		_langBuilderArgs.setPlugin(plugin);
	}

	public void setPortalLanguagePropertiesFileName(
		String portalLanguagePropertiesFileName) {

		_langBuilderArgs.setPortalLanguagePropertiesFileName(
			portalLanguagePropertiesFileName);
	}

	public void setTranslate(boolean translate) {
		_langBuilderArgs.setTranslate(translate);
	}

	public void setTranslateClientId(String translateClientId) {
		_langBuilderArgs.setTranslateClientId(translateClientId);
	}

	public void setTranslateClientSecret(String translateClientSecret) {
		_langBuilderArgs.setTranslateClientSecret(translateClientSecret);
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private final LangBuilderArgs _langBuilderArgs = new LangBuilderArgs();

}