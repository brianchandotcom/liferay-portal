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

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.lang.builder.LangBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;

/**
 * @author Andrea Di Giorgi
 */
public class BuildLangTask extends JavaExec {

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.setClasspath(getClasspath());

		super.exec();
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			LangBuilderPlugin.CONFIGURATION_NAME);
	}

	@Input
	public File getLangDir() {
		return GradleUtil.toFile(getProject(), _langDir);
	}

	@Input
	public String getLangFileName() {
		return GradleUtil.toString(_langFileName);
	}

	@Override
	public String getMain() {
		return "com.liferay.lang.builder.LangBuilder";
	}

	@InputFile
	@Optional
	public File getPortalLanguagePropertiesFile() {
		return GradleUtil.toFile(getProject(), _portalLanguagePropertiesFile);
	}

	@Input
	public String getTranslateClientId() {
		return GradleUtil.toString(_translateClientId);
	}

	@Input
	public String getTranslateClientSecret() {
		return GradleUtil.toString(_translateClientSecret);
	}

	@Input
	public boolean isPlugin() {
		return _plugin;
	}

	@Input
	public boolean isTranslate() {
		return _translate;
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setLangDir(Object langDir) {
		_langDir = langDir;
	}

	public void setLangFileName(Object langFileName) {
		_langFileName = langFileName;
	}

	public void setPlugin(boolean plugin) {
		_plugin = plugin;
	}

	public void setPortalLanguagePropertiesFile(
		Object portalLanguagePropertiesFile) {

		_portalLanguagePropertiesFile = portalLanguagePropertiesFile;
	}

	public void setTranslate(boolean translate) {
		_translate = translate;
	}

	public void setTranslateClientId(Object translateClientId) {
		_translateClientId = translateClientId;
	}

	public void setTranslateClientSecret(Object translateClientSecret) {
		_translateClientSecret = translateClientSecret;
	}

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add(
			"lang.dir=" + FileUtil.relativize(getLangDir(), getWorkingDir()));
		args.add("lang.file=" + getLangFileName());
		args.add("lang.plugin=" + isPlugin());

		File portalLanguagePropertiesFile = getPortalLanguagePropertiesFile();

		if (portalLanguagePropertiesFile != null) {
			args.add(
				"lang.portal.language.properties.file=" +
					FileUtil.relativize(
						getPortalLanguagePropertiesFile(), getWorkingDir()));
		}

		args.add("lang.translate=" + isTranslate());
		args.add("lang.translate.client.id=" + getTranslateClientId());
		args.add("lang.translate.client.secret=" + getTranslateClientSecret());

		return args;
	}

	private Object _langDir;
	private Object _langFileName = LangBuilderArgs.LANG_FILE_NAME;
	private boolean _plugin = LangBuilderArgs.PLUGIN;
	private Object _portalLanguagePropertiesFile;
	private boolean _translate = LangBuilderArgs.TRANSLATE;
	private Object _translateClientId;
	private Object _translateClientSecret;

}