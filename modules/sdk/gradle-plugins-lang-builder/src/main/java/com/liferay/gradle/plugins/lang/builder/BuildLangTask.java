/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.lang.builder;

import com.liferay.gradle.plugins.lang.builder.internal.util.StringUtil;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.lang.builder.LangBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;

/**
 * @author Andrea Di Giorgi
 */
public class BuildLangTask extends JavaExec {

	public BuildLangTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.lang.builder.LangBuilder");

		setExcludedLanguageIds((Object[])LangBuilderArgs.EXCLUDED_LANGUAGE_IDS);
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

	@Internal
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
		args.add("lang.translate=" + isTranslate());

		String translateSubscriptionKey = getTranslateSubscriptionKey();

		if (Validator.isNotNull(translateSubscriptionKey)) {
			args.add(
				"lang.translate.subscription.key=" + translateSubscriptionKey);
		}

		return args;
	}

	private final Set<Object> _excludedLanguageIds = new LinkedHashSet<>();
	private Object _langDir;
	private Object _langFileName = LangBuilderArgs.LANG_FILE_NAME;
	private boolean _titleCapitalization = LangBuilderArgs.TITLE_CAPITALIZATION;
	private boolean _translate = LangBuilderArgs.TRANSLATE;
	private Object _translateSubscriptionKey;

}