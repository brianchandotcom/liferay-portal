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

import com.liferay.gradle.plugins.lang.builder.internal.util.StringUtil;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.lang.builder.LangBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class BuildLangTask extends JavaExec {

	public BuildLangTask() {
		setExcludedLanguageIds((Object[])LangBuilderArgs.EXCLUDED_LANGUAGE_IDS);
		setMain("com.liferay.lang.builder.LangBuilder");
	}

	public BuildLangTask excludedLanguageIds(Iterable<?> excludedLanguageIds) {
		GUtil.addToCollection(_excludedLanguageIds, excludedLanguageIds);

		return this;
	}

	public BuildLangTask excludedLanguageIds(Object... excludedLanguageIds) {
		return excludedLanguageIds(Arrays.asList(excludedLanguageIds));
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@Input
	public Set<?> getExcludedLanguageIds() {
		return _excludedLanguageIds;
	}

	@Input
	public File getLangDir() {
		return GradleUtil.toFile(getProject(), _langDir);
	}

	@Input
	public String getLangFileName() {
		return GradleUtil.toString(_langFileName);
	}

	@Input
	@Optional
	public String getTranslateSubscriptionKey() {
		return GradleUtil.toString(_translateSubscriptionKey);
	}

	@Input
	public boolean isTitleCapitalization() {
		return _titleCapitalization;
	}

	@Input
	public boolean isTranslate() {
		return _translate;
	}

	public void setExcludedLanguageIds(Iterable<?> excludedLanguageIds) {
		_excludedLanguageIds.clear();

		excludedLanguageIds(excludedLanguageIds);
	}

	public void setExcludedLanguageIds(Object... excludedLanguageIds) {
		setExcludedLanguageIds(Arrays.asList(excludedLanguageIds));
	}

	public void setLangDir(Object langDir) {
		_langDir = langDir;
	}

	public void setLangFileName(Object langFileName) {
		_langFileName = langFileName;
	}

	public void setTitleCapitalization(boolean titleCapitalization) {
		_titleCapitalization = titleCapitalization;
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
		args.add(
			"lang.excluded.language.ids=" +
				StringUtil.merge(getExcludedLanguageIds(), ","));
		args.add("lang.file=" + getLangFileName());
		args.add("lang.title.capitalization=" + isTitleCapitalization());

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

	private Set<Object> _excludedLanguageIds = new LinkedHashSet<>();
	private Object _langDir;
	private Object _langFileName = LangBuilderArgs.LANG_FILE_NAME;
	private boolean _titleCapitalization = LangBuilderArgs.TITLE_CAPITALIZATION;
	private boolean _translate = LangBuilderArgs.TRANSLATE;
	private Object _translateSubscriptionKey;

}