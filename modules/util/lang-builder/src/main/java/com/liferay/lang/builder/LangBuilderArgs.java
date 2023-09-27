/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.lang.builder;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderArgs {

	public static final String[] EXCLUDED_LANGUAGE_IDS = {
		"da", "de", "fi", "ja", "nl", "pt_PT", "sv"
	};

	public static final String LANG_DIR_NAME = "src/content";

	public static final String LANG_FILE_NAME = "Language";

	public static final boolean TITLE_CAPITALIZATION = true;

	public static final boolean TRANSLATE = true;

	public String[] getExcludedLanguageIds() {
		return _excludedLanguageIds;
	}

	public String getLangDirName() {
		return _langDirName;
	}

	public String getLangFileName() {
		return _langFileName;
	}

	public String getTranslateSubscriptionKey() {
		return _translateSubscriptionKey;
	}

	public boolean isTitleCapitalization() {
		return _titleCapitalization;
	}

	public boolean isTranslate() {
		return _translate;
	}

	public void setExcludedLanguageIds(String[] excludedLanguageIds) {
		_excludedLanguageIds = excludedLanguageIds;
	}

	public void setLangDirName(String langDirName) {
		_langDirName = langDirName;
	}

	public void setLangFileName(String langFileName) {
		_langFileName = langFileName;
	}

	public void setTitleCapitalization(boolean titleCapitalization) {
		_titleCapitalization = titleCapitalization;
	}

	public void setTranslate(boolean translate) {
		_translate = translate;
	}

	public void setTranslateSubscriptionKey(String translateSubscriptionKey) {
		_translateSubscriptionKey = translateSubscriptionKey;
	}

	private String[] _excludedLanguageIds = EXCLUDED_LANGUAGE_IDS;
	private String _langDirName = LANG_DIR_NAME;
	private String _langFileName = LANG_FILE_NAME;
	private boolean _titleCapitalization = TITLE_CAPITALIZATION;
	private boolean _translate = TRANSLATE;
	private String _translateSubscriptionKey;

}