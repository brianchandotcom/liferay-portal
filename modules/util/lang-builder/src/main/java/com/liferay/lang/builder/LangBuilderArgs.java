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

package com.liferay.lang.builder;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderArgs {

	public static final String LANG_DIR_NAME = "src/content";

	public static final String LANG_FILE_NAME = "Language";

	public static final boolean PLUGIN = true;

	public static final boolean TRANSLATE = true;

	public String getLangDirName() {
		return _langDirName;
	}

	public String getLangFileName() {
		return _langFileName;
	}

	public String getPortalLanguagePropertiesFileName() {
		return _portalLanguagePropertiesFileName;
	}

	public String getTranslateClientId() {
		return _translateClientId;
	}

	public String getTranslateClientSecret() {
		return _translateClientSecret;
	}

	public boolean isPlugin() {
		return _plugin;
	}

	public boolean isTranslate() {
		return _translate;
	}

	public void setLangDirName(String langDirName) {
		_langDirName = langDirName;
	}

	public void setLangFileName(String langFileName) {
		_langFileName = langFileName;
	}

	public void setPlugin(boolean plugin) {
		_plugin = plugin;
	}

	public void setPortalLanguagePropertiesFileName(
		String portalLanguagePropertiesFileName) {

		_portalLanguagePropertiesFileName = portalLanguagePropertiesFileName;
	}

	public void setTranslate(boolean translate) {
		_translate = translate;
	}

	public void setTranslateClientId(String translateClientId) {
		_translateClientId = translateClientId;
	}

	public void setTranslateClientSecret(String translateClientSecret) {
		_translateClientSecret = translateClientSecret;
	}

	private String _langDirName = LANG_DIR_NAME;
	private String _langFileName = LANG_FILE_NAME;
	private boolean _plugin = PLUGIN;
	private String _portalLanguagePropertiesFileName;
	private boolean _translate = TRANSLATE;
	private String _translateClientId;
	private String _translateClientSecret;

}