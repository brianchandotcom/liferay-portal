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
import com.liferay.gradle.util.Validator;
import com.liferay.lang.builder.LangBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;

/**
 * @author Andrea Di Giorgi
 */
public class BuildLangTask extends JavaExec {

	public BuildLangTask() {
		setMain("com.liferay.lang.builder.LangBuilder");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@Input
	public File getLangDir() {
		return GradleUtil.toFile(getProject(), _langDir);
	}

	@Input
	public String getLangFileName() {
		return GradleUtil.toString(_langFileName);
	}

	@InputFile
	@Optional
	public File getPortalLanguagePropertiesFile() {
		return GradleUtil.toFile(getProject(), _portalLanguagePropertiesFile);
	}

	@Input
	@Optional
	public String getTranslateSubscriptionKey() {
		return GradleUtil.toString(_translateSubscriptionKey);
	}

	@Input
	public boolean isPlugin() {
		return _plugin;
	}

	@Input
	public boolean isTranslate() {
		return _translate;
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

	public void setTranslateSubscriptionKey(Object translateSubscriptionKey) {
		_translateSubscriptionKey = translateSubscriptionKey;
	}

	private List<String> _getCompleteArgs() {
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

		boolean translate = isTranslate();

		if (translate) {
			String translateSubscriptionKey = getTranslateSubscriptionKey();

			if (Validator.isNull(translateSubscriptionKey)) {
				if (_logger.isWarnEnabled()) {
					_logger.warn(
						"Translation is disabled because credentials are not " +
							"specified");
				}

				translate = false;
			}
			else {
				args.add(
					"lang.translate.subscription.key=" +
						translateSubscriptionKey);
			}
		}

		args.add("lang.translate=" + translate);

		return args;
	}

	private static final Logger _logger = Logging.getLogger(
		BuildLangTask.class);

	private Object _langDir;
	private Object _langFileName = LangBuilderArgs.LANG_FILE_NAME;
	private boolean _plugin = LangBuilderArgs.PLUGIN;
	private Object _portalLanguagePropertiesFile;
	private boolean _translate = LangBuilderArgs.TRANSLATE;
	private Object _translateSubscriptionKey;

}