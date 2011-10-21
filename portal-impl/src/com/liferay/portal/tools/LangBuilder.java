/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.util.InitUtil;
import com.liferay.portlet.translator.model.Translation;
import com.liferay.portlet.translator.util.TranslationWebCacheItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class LangBuilder {

	public static final String AUTOMATIC_COPY = " (Automatic Copy)";

	public static final String AUTOMATIC_TRANSLATION =
		" (Automatic Translation)";

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		System.setProperty("line.separator", StringPool.NEW_LINE);

		InitUtil.initWithSpring();

		String langDir = arguments.get("lang.dir");
		String langFile = arguments.get("lang.file");
		boolean langPlugin = GetterUtil.getBoolean(
			arguments.get("lang.plugin"));
		boolean langTranslate = GetterUtil.getBoolean(
			arguments.get("lang.translate"), true);

		try {
			new LangBuilder(langDir, langFile, langPlugin, langTranslate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LangBuilder(
			String langDir, String langFile, boolean langPlugin,
			boolean langTranslate)
		throws Exception {

		_langDir = langDir;
		_langFile = langFile;
		_langTranslate = langTranslate;

		if (langPlugin) {
			_portalLanguageProperties = new Properties();

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			InputStream is = classLoader.getResourceAsStream(
				"content/Language.properties");

			_portalLanguageProperties.load(is);
		}

		File renameKeysFile = new File(_langDir + "/rename.properties");

		if (renameKeysFile.exists()) {
			_renameKeys = PropertiesUtil.load(
				FileUtil.read(renameKeysFile));
		}

		String content = _orderProperties(
			new File(_langDir + "/" + _langFile + ".properties"));

		// Locales that are not invoked by _createProperties should still be
		// rewritten to use the rignt line separator

		_orderProperties(
			new File(_langDir + "/" + _langFile + "_en_GB.properties"));

		_createProperties(content, "ar"); // Arabic
		_createProperties(content, "eu"); // Basque
		_createProperties(content, "bg"); // Bulgarian
		_createProperties(content, "ca"); // Catalan
		_createProperties(content, "zh_CN"); // Chinese (China)
		_createProperties(content, "zh_TW"); // Chinese (Taiwan)
		_createProperties(content, "hr"); // Croatian
		_createProperties(content, "cs"); // Czech
		_createProperties(content, "nl"); // Dutch (Netherlands)
		_createProperties(content, "nl_BE", "nl"); // Dutch (Belgium)
		_createProperties(content, "et"); // Estonian
		_createProperties(content, "fi"); // Finnish
		_createProperties(content, "fr"); // French
		_createProperties(content, "gl"); // Galician
		_createProperties(content, "de"); // German
		_createProperties(content, "el"); // Greek
		_createProperties(content, "iw"); // Hebrew
		_createProperties(content, "hi_IN"); // Hindi (India)
		_createProperties(content, "hu"); // Hungarian
		_createProperties(content, "in"); // Indonesian
		_createProperties(content, "it"); // Italian
		_createProperties(content, "ja"); // Japanese
		_createProperties(content, "ko"); // Korean
		_createProperties(content, "nb"); // Norwegian Bokmål
		_createProperties(content, "fa"); // Persian
		_createProperties(content, "pl"); // Polish
		_createProperties(content, "pt_BR"); // Portuguese (Brazil)
		_createProperties(content, "pt_PT", "pt_BR"); // Portuguese (Portugal)
		_createProperties(content, "ro"); // Romanian
		_createProperties(content, "ru"); // Russian
		_createProperties(content, "sr_RS"); // Serbian (Cyrillic)
		_createProperties(content, "sr_RS_latin"); // Serbian (Latin)
		_createProperties(content, "sk"); // Slovak
		_createProperties(content, "sl"); // Slovene
		_createProperties(content, "es"); // Spanish
		_createProperties(content, "sv"); // Swedish
		_createProperties(content, "tr"); // Turkish
		_createProperties(content, "uk"); // Ukrainian
		_createProperties(content, "vi"); // Vietnamese
	}

	private void _createProperties(String content, String languageId)
		throws IOException {

		_createProperties(content, languageId, null);
	}

	private void _createProperties(
			String content, String languageId, String parentLanguageId)
		throws IOException {

		File propertiesFile = new File(
			_langDir + "/" + _langFile + "_" + languageId + ".properties");

		Properties properties = new Properties();

		if (propertiesFile.exists()) {
			properties = PropertiesUtil.load(
				new FileInputStream(propertiesFile), StringPool.UTF8);
		}

		Properties parentProperties = null;

		if (parentLanguageId != null) {
			File parentPropertiesFile = new File(
				_langDir + "/" + _langFile + "_" + parentLanguageId +
					".properties");

			if (parentPropertiesFile.exists()) {
				parentProperties = new Properties();

				parentProperties = PropertiesUtil.load(
					new FileInputStream(parentPropertiesFile), StringPool.UTF8);
			}
		}

		String translationId = "en_" + languageId;

		if (translationId.equals("en_pt_BR")) {
			translationId = "en_pt";
		}
		else if (translationId.equals("en_pt_PT")) {
			translationId = "en_pt";
		}
		else if (translationId.equals("en_zh_CN")) {
			translationId = "en_zh";
		}
		else if (translationId.equals("en_zh_TW")) {
			translationId = "en_zt";
		}
		else if (translationId.equals("en_hi_IN")) {
			translationId = "en_hi";
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new OutputStreamWriter(
				new FileOutputStream(propertiesFile), StringPool.UTF8));

		int state = 0;

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = line.trim();

			int pos = line.indexOf("=");

			if (pos != -1) {
				String key = line.substring(0, pos);
				String value = line.substring(pos + 1, line.length());

				if (((state == 1) && !key.startsWith("lang.")) ||
					((state == 2) && !key.startsWith("javax.portlet.")) ||
					((state == 3) && !key.startsWith("category.")) ||
					((state == 4) && !key.startsWith("model.resource.")) ||
					((state == 5) && !key.startsWith("action.")) ||
					((state == 7) && !key.startsWith("currency.")) ||
					((state != 7) && key.startsWith("currency."))) {

					throw new RuntimeException(
						"File " + languageId + " with state " + state +
							" has key " + key);
				}

				String translatedText = properties.getProperty(key);

				if ((translatedText == null) && (parentProperties != null)) {
					translatedText = parentProperties.getProperty(key);
				}

				if ((translatedText == null) && (_renameKeys != null)) {
					String renameKey = _renameKeys.getProperty(key);

					if (renameKey != null) {
						translatedText = properties.getProperty(key);

						if ((translatedText == null) &&
							(parentProperties != null)) {

							translatedText = parentProperties.getProperty(key);
						}
					}
				}

				if ((translatedText != null) &&
					((translatedText.indexOf("Babel Fish") != -1) ||
					 (translatedText.indexOf("Yahoo! - 999") != -1))) {

					translatedText = "";
				}

				if ((translatedText == null) || translatedText.equals("")) {
					if (line.indexOf("{") != -1 || line.indexOf("<") != -1) {
						translatedText = value + AUTOMATIC_COPY;
					}
					else if (line.indexOf("[") != -1) {
						pos = line.indexOf("[");

						String baseKey = line.substring(0, pos);

						translatedText =
							properties.getProperty(baseKey) + AUTOMATIC_COPY;
					}
					else if (key.equals("lang.dir")) {
						translatedText = "ltr";
					}
					else if (key.equals("lang.line.begin")) {
						translatedText = "left";
					}
					else if (key.equals("lang.line.end")) {
						translatedText = "right";
					}
					else if (translationId.equals("en_el") &&
							 (key.equals("enabled") || key.equals("on") ||
							  key.equals("on-date"))) {

						translatedText = "";
					}
					else if (translationId.equals("en_es") &&
							 key.equals("am")) {

						translatedText = "";
					}
					else if (translationId.equals("en_it") &&
							 key.equals("am")) {

						translatedText = "";
					}
					else if (translationId.equals("en_ja") &&
							 (key.equals("any") || key.equals("anytime") ||
							  key.equals("down") || key.equals("on") ||
							  key.equals("on-date") || key.equals("the"))) {

						translatedText = "";
					}
					else if (translationId.equals("en_ko") &&
							 key.equals("the")) {

						translatedText = "";
					}
					else {
						translatedText = _translate(
							translationId, key, value, 0);

						if (Validator.isNull(translatedText)) {
							translatedText = value + AUTOMATIC_COPY;
						}
						else {
							translatedText =
								translatedText + AUTOMATIC_TRANSLATION;
						}
					}
				}

				if (Validator.isNotNull(translatedText)) {
					if ((translatedText.indexOf("Babel Fish") != -1) ||
						(translatedText.indexOf("Yahoo! - 999") != -1)) {

						throw new IOException(
							"IP was blocked because of over usage. Please " +
								"use another IP.");
					}

					if (translatedText.indexOf("&#39;") != -1) {
						translatedText = StringUtil.replace(
							translatedText, "&#39;", "\'");
					}

					translatedText = StringUtil.replace(
						translatedText.trim(),
						new String[] {
							"  ", "<b>", "</b>", "<i>", "</i>"
						},
						new String[] {
							" ", "<strong>", "</strong>", "<em>", "</em>"
						});

					unsyncBufferedWriter.write(key + "=" + translatedText);

					unsyncBufferedWriter.newLine();
					unsyncBufferedWriter.flush();
				}
			}
			else {
				if (line.startsWith("## Language settings")) {
					if (state == 1) {
						throw new RuntimeException(languageId);
					}

					state = 1;
				}
				else if (line.startsWith(
							"## Portlet descriptions and titles")) {

					if (state == 2) {
						throw new RuntimeException(languageId);
					}

					state = 2;
				}
				else if (line.startsWith("## Category titles")) {
					if (state == 3) {
						throw new RuntimeException(languageId);
					}

					state = 3;
				}
				else if (line.startsWith("## Model resources")) {
					if (state == 4) {
						throw new RuntimeException(languageId);
					}

					state = 4;
				}
				else if (line.startsWith("## Action names")) {
					if (state == 5) {
						throw new RuntimeException(languageId);
					}

					state = 5;
				}
				else if (line.startsWith("## Messages")) {
					if (state == 6) {
						throw new RuntimeException(languageId);
					}

					state = 6;
				}
				else if (line.startsWith("## Currency")) {
					if (state == 7) {
						throw new RuntimeException(languageId);
					}

					state = 7;
				}

				unsyncBufferedWriter.write(line);

				unsyncBufferedWriter.newLine();
				unsyncBufferedWriter.flush();
			}
		}

		unsyncBufferedReader.close();
		unsyncBufferedWriter.close();
	}

	private String _orderProperties(File propertiesFile) throws IOException {
		if (!propertiesFile.exists()) {
			return null;
		}

		String content = FileUtil.read(propertiesFile);

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new FileWriter(propertiesFile));

		Set<String> messages = new TreeSet<String>();

		boolean begin = false;

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			int pos = line.indexOf("=");

			if (pos != -1) {
				String key = line.substring(0, pos);
				String value = line.substring(pos + 1, line.length());

				if (_portalLanguageProperties != null) {
					String portalValue = String.valueOf(
						_portalLanguageProperties.get(key));

					if (value.equals(portalValue)) {
						System.out.println("Duplicate key " + key);
					}
				}

				messages.add(key + "=" + value);
			}
			else {
				if (begin == true && line.equals("")) {
					_sortAndWrite(unsyncBufferedWriter, messages);
				}

				if (line.equals("")) {
					begin = !begin;
				}

				unsyncBufferedWriter.write(line);
				unsyncBufferedWriter.newLine();
			}

			unsyncBufferedWriter.flush();
		}

		if (messages.size() > 0) {
			_sortAndWrite(unsyncBufferedWriter, messages);
		}

		unsyncBufferedReader.close();
		unsyncBufferedWriter.close();

		return FileUtil.read(propertiesFile);
	}

	private void _sortAndWrite(
			UnsyncBufferedWriter unsyncBufferedWriter, Set<String> messages)
		throws IOException {

		String[] messagesArray = messages.toArray(new String[messages.size()]);

		for (int i = 0; i < messagesArray.length; i++) {
			unsyncBufferedWriter.write(messagesArray[i]);
			unsyncBufferedWriter.newLine();
		}

		messages.clear();
	}

	private String _translate(
		String translationId, String key, String fromText, int limit) {

		if (translationId.equals("en_ar") ||
			translationId.equals("en_eu") ||
			translationId.equals("en_bg") ||
			translationId.equals("en_ca") ||
			translationId.equals("en_hr") ||
			translationId.equals("en_cs") ||
			translationId.equals("en_fi") ||
			translationId.equals("en_gl") ||
			translationId.equals("en_iw") ||
			translationId.equals("en_hi") ||
			translationId.equals("en_hu") ||
			translationId.equals("en_in") ||
			translationId.equals("en_nb") ||
			translationId.equals("en_fa") ||
			translationId.equals("en_pl") ||
			translationId.equals("en_ro") ||
			translationId.equals("en_ru") ||
			translationId.equals("en_sr_RS") ||
			translationId.equals("en_sr_RS_latin") ||
			translationId.equals("en_sk") ||
			translationId.equals("en_sl") ||
			translationId.equals("en_sv") ||
			translationId.equals("en_tr") ||
			translationId.equals("en_uk") ||
			translationId.equals("en_vi") ||
			translationId.equals("en_et")) {

			// Automatic translator does not support Arabic, Basque, Bulgarian,
			// Catalan, Czech, Croatian, Finnish, Galician, Hebrew, Hindi,
			// Hungarian, Indonesian, Norwegian Bokmål, Persian, Polish,
			// Romanian, Russian, Serbian, Slovak, Slovene, Swedish, Turkish,
			// Ukrainian, or Vietnamese

			return null;
		}

		if (!_langTranslate) {
			return null;
		}

		// Limit the number of retries to 3

		if (limit == 3) {
			return null;
		}

		String toText = null;

		try {
			System.out.println(
				"Translating " + translationId + " " + key + " " + fromText);

			WebCacheItem wci = new TranslationWebCacheItem(
				translationId, fromText);

			Translation translation = (Translation)wci.convert("");

			toText = translation.getToText();

			if ((toText != null) &&
				(toText.indexOf("Babel Fish") != -1)) {

				toText = null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Keep trying

		if (toText == null) {
			return _translate(translationId, key, fromText, ++limit);
		}

		return toText;
	}

	private String _langDir;
	private String _langFile;
	private boolean _langTranslate;
	private Properties _portalLanguageProperties;
	private Properties _renameKeys;

}