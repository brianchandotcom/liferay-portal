/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NumericalStringComparator;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
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
import java.util.TreeMap;

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

			InputStream inputStream = classLoader.getResourceAsStream(
				"content/Language.properties");

			_portalLanguageProperties.load(inputStream);
		}

		File renameKeysFile = new File(_langDir + "/rename.properties");

		if (renameKeysFile.exists()) {
			_renameKeys = PropertiesUtil.load(FileUtil.read(renameKeysFile));
		}

		String content = _orderProperties(
			new File(_langDir + "/" + _langFile + ".properties"));

		// Locales that are not invoked by _createProperties should still be
		// rewritten to use the right line separator

		_orderProperties(
			new File(_langDir + "/" + _langFile + "_en_AU.properties"));
		_orderProperties(
			new File(_langDir + "/" + _langFile + "_en_GB.properties"));
		_orderProperties(
			new File(_langDir + "/" + _langFile + "_fr_CA.properties"));

		_createProperties(content, "ar"); // Arabic
		_createProperties(content, "eu"); // Basque
		_createProperties(content, "bg"); // Bulgarian
		_createProperties(content, "ca"); // Catalan
		_createProperties(content, "zh_CN"); // Chinese (China)
		_createProperties(content, "zh_TW"); // Chinese (Taiwan)
		_createProperties(content, "hr"); // Croatian
		_createProperties(content, "cs"); // Czech
		_createProperties(content, "da"); // Danish
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
		_createProperties(content, "lo"); // Lao
		_createProperties(content, "lt"); // Lithuanian
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

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));
		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new OutputStreamWriter(
				new FileOutputStream(propertiesFile), StringPool.UTF8));

		boolean firstLine = true;
		int state = 0;

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = line.trim();

			int pos = line.indexOf("=");

			if (pos != -1) {
				String key = line.substring(0, pos);
				String value = line.substring(pos + 1);

				if (((state == 1) && !key.startsWith("lang.")) ||
					((state == 2) && !key.startsWith("javax.portlet.")) ||
					((state == 3) && !key.startsWith("category.")) ||
					((state == 4) && !key.startsWith("model.resource.")) ||
					((state == 5) && !key.startsWith("action.")) ||
					((state == 7) && !key.startsWith("country.")) ||
					((state == 8) && !key.startsWith("currency.")) ||
					((state == 9) && !key.startsWith("language.")) ||
					((state != 9) && key.startsWith("language."))) {

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

				if (translatedText != null) {
					if (translatedText.contains("Babel Fish") ||
						translatedText.contains("Yahoo! - 999")) {

						translatedText = "";
					}
					else if (translatedText.endsWith(AUTOMATIC_COPY)) {
						translatedText = value + AUTOMATIC_COPY;
					}
				}

				if ((translatedText == null) || translatedText.equals("")) {
					if (line.contains("{") || line.contains("<")) {
						translatedText = value + AUTOMATIC_COPY;
					}
					else if (line.contains("[")) {
						pos = line.indexOf("[");

						String baseKey = line.substring(0, pos);

						String translatedBaseKey = properties.getProperty(
							baseKey);

						if (Validator.isNotNull(translatedBaseKey)) {
							translatedText = translatedBaseKey;
						}
						else {
							translatedText = value + AUTOMATIC_COPY;
						}
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
					else if (languageId.equals("el") &&
							 (key.equals("enabled") || key.equals("on") ||
							  key.equals("on-date"))) {

						translatedText = "";
					}
					else if (languageId.equals("es") && key.equals("am")) {
						translatedText = "";
					}
					else if (languageId.equals("fi") &&
							 (key.equals("on") || key.equals("the"))) {

						translatedText = "";
					}
					else if (languageId.equals("it") && key.equals("am")) {
						translatedText = "";
					}
					else if (languageId.equals("ja") &&
							 (key.equals("any") || key.equals("anytime") ||
							  key.equals("down") || key.equals("on") ||
							  key.equals("on-date") || key.equals("the"))) {

						translatedText = "";
					}
					else if (languageId.equals("ko") && key.equals("the")) {
						translatedText = "";
					}
					else {
						translatedText = _translate(
							"en", languageId, key, value, 0);

						if (Validator.isNull(translatedText)) {
							translatedText = value + AUTOMATIC_COPY;
						}
						else if (!key.startsWith("country.") &&
								 !key.startsWith("language.")) {

							translatedText =
								translatedText + AUTOMATIC_TRANSLATION;
						}
					}
				}

				if (Validator.isNotNull(translatedText)) {
					if (translatedText.contains("Babel Fish") ||
						translatedText.contains("Yahoo! - 999")) {

						throw new IOException(
							"IP was blocked because of over usage. Please " +
								"use another IP.");
					}

					translatedText = _fixTranslation(translatedText);

					if (firstLine) {
						firstLine = false;
					}
					else {
						unsyncBufferedWriter.newLine();
					}

					unsyncBufferedWriter.write(key + "=" + translatedText);

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
				else if (line.startsWith("## Country")) {
					if (state == 7) {
						throw new RuntimeException(languageId);
					}

					state = 7;
				}
				else if (line.startsWith("## Currency")) {
					if (state == 8) {
						throw new RuntimeException(languageId);
					}

					state = 8;
				}
				else if (line.startsWith("## Language")) {
					if (state == 9) {
						throw new RuntimeException(languageId);
					}

					state = 9;
				}

				if (firstLine) {
					firstLine = false;
				}
				else {
					unsyncBufferedWriter.newLine();
				}

				unsyncBufferedWriter.write(line);

				unsyncBufferedWriter.flush();
			}
		}

		unsyncBufferedReader.close();
		unsyncBufferedWriter.close();
	}

	private String _fixEnglishTranslation(String key, String value) {

		// http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles
		// http://www.imdb.com

		if (value.contains(" this ")) {
			if (value.contains(".") || value.contains("?") ||
				value.contains(":") ||
				key.equals("the-url-of-the-page-comparing-this-page-content-with-the-previous-version")) {
			}
			else {
				value = StringUtil.replace(value, " this ", " This ");
			}
		}
		else {
			value = StringUtil.replace(value, " From ", " from ");
		}

		return value;
	}

	private String _fixTranslation(String value) {
		value = StringUtil.replace(
			value.trim(),
			new String[] {
				"  ", "<b>", "</b>", "<i>", "</i>", " url ", "&#39;", "&#39 ;",
				"&quot;", "&quot ;", "ReCaptcha", "Captcha"
			},
			new String[] {
				" ", "<strong>", "</strong>", "<em>", "</em>", " URL ", "\'",
				"\'", "\"", "\"", "reCAPTCHA", "CAPTCHA"
			});

		return value;
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

		Map<String, String> messages = new TreeMap<String, String>(
			new NumericalStringComparator(true, true));

		boolean begin = false;
		boolean firstLine = true;

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			int pos = line.indexOf("=");

			if (pos != -1) {
				String key = line.substring(0, pos);

				String value = line.substring(pos + 1);

				if (Validator.isNotNull(value)) {
					value = _fixTranslation(line.substring(pos + 1));

					value = _fixEnglishTranslation(key, value);

					if (_portalLanguageProperties != null) {
						String portalValue = String.valueOf(
							_portalLanguageProperties.get(key));

						if (value.equals(portalValue)) {
							System.out.println("Duplicate key " + key);
						}
					}

					messages.put(key, value);
				}
			}
			else {
				if (begin && line.equals(StringPool.BLANK)) {
					_sortAndWrite(unsyncBufferedWriter, messages, firstLine);
				}

				if (line.equals(StringPool.BLANK)) {
					begin = !begin;
				}

				if (firstLine) {
					firstLine = false;
				}
				else {
					unsyncBufferedWriter.newLine();
				}

				unsyncBufferedWriter.write(line);
			}

			unsyncBufferedWriter.flush();
		}

		if (!messages.isEmpty()) {
			_sortAndWrite(unsyncBufferedWriter, messages, firstLine);
		}

		unsyncBufferedReader.close();
		unsyncBufferedWriter.close();

		return FileUtil.read(propertiesFile);
	}

	private void _sortAndWrite(
			UnsyncBufferedWriter unsyncBufferedWriter,
			Map<String, String> messages, boolean firstLine)
		throws IOException {

		boolean firstEntry = true;

		for (Map.Entry<String, String> entry : messages.entrySet()) {
			if (!firstLine || !firstEntry) {
				unsyncBufferedWriter.newLine();
			}

			firstEntry = false;

			unsyncBufferedWriter.write(entry.getKey() + "=" + entry.getValue());
		}

		messages.clear();
	}

	private String _translate(
		String fromLanguageId, String toLanguageId, String key, String fromText,
		int limit) {

		if (toLanguageId.equals("ar") ||
			toLanguageId.equals("eu") ||
			toLanguageId.equals("bg") ||
			toLanguageId.equals("ca") ||
			toLanguageId.equals("hr") ||
			toLanguageId.equals("cs") ||
			toLanguageId.equals("da") ||
			toLanguageId.equals("et") ||
			toLanguageId.equals("fi") ||
			toLanguageId.equals("gl") ||

			// LPS-26741

			toLanguageId.equals("de") ||

			toLanguageId.equals("iw") ||
			toLanguageId.equals("hi") ||
			toLanguageId.equals("hu") ||
			toLanguageId.equals("in") ||
			toLanguageId.equals("lo") ||
			toLanguageId.equals("lt") ||
			toLanguageId.equals("nb") ||
			toLanguageId.equals("fa") ||
			toLanguageId.equals("pl") ||
			toLanguageId.equals("ro") ||
			toLanguageId.equals("ru") ||
			toLanguageId.equals("sr_RS") ||
			toLanguageId.equals("sr_RS_latin") ||
			toLanguageId.equals("sk") ||
			toLanguageId.equals("sl") ||
			toLanguageId.equals("sv") ||
			toLanguageId.equals("tr") ||
			toLanguageId.equals("uk") ||
			toLanguageId.equals("vi")) {

			// Automatic translator does not support Arabic, Basque, Bulgarian,
			// Catalan, Croatian, Czech, Danish, Estonian, Finnish, Galician,
			// German, Hebrew, Hindi, Hungarian, Indonesian, Lao, Norwegian
			// Bokmål, Persian, Polish, Romanian, Russian, Serbian, Slovak,
			// Slovene, Swedish, Turkish, Ukrainian, or Vietnamese

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
			StringBundler sb = new StringBundler(8);

			sb.append("Translating ");
			sb.append(fromLanguageId);
			sb.append("_");
			sb.append(toLanguageId);
			sb.append(" ");
			sb.append(key);
			sb.append(" ");
			sb.append(fromText);

			System.out.println(sb.toString());

			WebCacheItem wci = new TranslationWebCacheItem(
				fromLanguageId, toLanguageId, fromText);

			Translation translation = (Translation)wci.convert("");

			toText = translation.getToText();
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof MicrosoftTranslatorException) {
				System.out.println(
					cause.getClass().getName() + ": " + cause.getMessage());
			}
			else {
				e.printStackTrace();
			}
		}

		// Keep trying

		if (toText == null) {
			return _translate(
				fromLanguageId, toLanguageId, key, fromText, ++limit);
		}

		return toText;
	}

	private String _langDir;
	private String _langFile;
	private boolean _langTranslate;
	private Properties _portalLanguageProperties;
	private Properties _renameKeys;

}