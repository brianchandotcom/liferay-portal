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

import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageValidator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.GitException;
import com.liferay.portal.tools.GitUtil;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class LangBuilder {

	public static final String AUTOMATIC_COPY =
		com.liferay.portal.tools.LangBuilder.AUTOMATIC_COPY;

	public static final String AUTOMATIC_TRANSLATION =
		com.liferay.portal.tools.LangBuilder.AUTOMATIC_TRANSLATION;

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		System.setProperty("line.separator", StringPool.NEW_LINE);

		String langDirName = GetterUtil.getString(
			arguments.get(LanguageConstants.KEY_DIR),
			LangBuilderArgs.LANG_DIR_NAME);
		String langFileName = GetterUtil.getString(
			arguments.get("lang.file"), LangBuilderArgs.LANG_FILE_NAME);
		boolean plugin = GetterUtil.getBoolean(
			arguments.get("lang.plugin"), LangBuilderArgs.PLUGIN);
		String portalLanguagePropertiesFileName = arguments.get(
			"lang.portal.language.properties.file");
		boolean translate = GetterUtil.getBoolean(
			arguments.get("lang.translate"), LangBuilderArgs.TRANSLATE);
		String translateClientId = arguments.get("lang.translate.client.id");
		String translateClientSecret = arguments.get(
			"lang.translate.client.secret");

		boolean buildCurrentBranch = ArgumentsUtil.getBoolean(
			arguments, "build.current.branch", false);

		if (buildCurrentBranch) {
			String gitWorkingBranchName = ArgumentsUtil.getString(
				arguments, "git.working.branch.name", "master");

			_processCurrentBranch(
				langFileName, plugin, portalLanguagePropertiesFileName,
				translate, translateClientId, translateClientSecret,
				gitWorkingBranchName);

			return;
		}

		try {
			new LangBuilder(
				langDirName, langFileName, plugin,
				portalLanguagePropertiesFileName, translate, translateClientId,
				translateClientSecret);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public LangBuilder(
			String langDirName, String langFileName, boolean plugin,
			String portalLanguagePropertiesFileName, boolean translate,
			String translateClientId, String translateClientSecret)
		throws Exception {

		_langDirName = langDirName;
		_langFileName = langFileName;
		_translate = translate;

		Translate.setClientId(translateClientId);
		Translate.setClientSecret(translateClientSecret);

		_initKeysWithUpdatedValues();

		if (plugin) {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			InputStream inputStream = classLoader.getResourceAsStream(
				"content/Language.properties");

			if ((inputStream == null) &&
				Validator.isNotNull(portalLanguagePropertiesFileName)) {

				inputStream = new FileInputStream(
					portalLanguagePropertiesFileName);
			}

			if (inputStream != null) {
				try {
					_portalLanguageProperties = PropertiesUtil.load(
						inputStream, StringPool.UTF8);
				}
				finally {
					inputStream.close();
				}
			}
			else {
				_portalLanguageProperties = null;
			}
		}
		else {
			_portalLanguageProperties = null;
		}

		File renameKeysFile = new File(_langDirName + "/rename.properties");

		if (renameKeysFile.exists()) {
			_renameKeys = _readProperties(renameKeysFile);
		}
		else {
			_renameKeys = null;
		}

		File propertiesFile = new File(
			_langDirName + "/" + _langFileName + ".properties");

		String content = _orderProperties(propertiesFile);

		if (Validator.isNull(content)) {
			return;
		}

		// Locales that are not invoked by _createProperties should still be
		// rewritten to use the right line separator

		_orderProperties(
			new File(_langDirName + "/" + _langFileName + "_en_AU.properties"));
		_orderProperties(
			new File(_langDirName + "/" + _langFileName + "_en_GB.properties"));
		_orderProperties(
			new File(_langDirName + "/" + _langFileName + "_fr_CA.properties"));

		_copyProperties(propertiesFile, "en");

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
		_createProperties(content, "th"); // Thai
		_createProperties(content, "tr"); // Turkish
		_createProperties(content, "uk"); // Ukrainian
		_createProperties(content, "vi"); // Vietnamese
	}

	private static String _getSpecialPropertyValue(String key) {
		if (key.equals(LanguageConstants.KEY_DIR)) {
			return LanguageConstants.VALUE_LTR;
		}
		else if (key.equals(LanguageConstants.KEY_LINE_BEGIN)) {
			return LanguageConstants.VALUE_LEFT;
		}
		else if (key.equals(LanguageConstants.KEY_LINE_END)) {
			return LanguageConstants.VALUE_RIGHT;
		}

		return StringPool.BLANK;
	}

	private static void _processCurrentBranch(
			String langFileName, boolean plugin,
			String portalLanguagePropertiesFileName, boolean translate,
			String translateClientId, String translateClientSecret,
			String gitWorkingBranchName)
		throws Exception {

		try {
			String basedir = ".././";

			List<String> fileNames = GitUtil.getCurrentBranchFileNames(
				basedir, gitWorkingBranchName);

			for (String fileName : fileNames) {
				int pos = fileName.indexOf(
					"content/" + langFileName + ".properties");

				if (pos == -1) {
					continue;
				}

				String langDirName = basedir + fileName.substring(0, pos + 7);

				new LangBuilder(
					langDirName, langFileName, plugin,
					portalLanguagePropertiesFileName, translate,
					translateClientId, translateClientSecret);
			}
		}
		catch (GitException ge) {
			System.out.println(ge.getMessage());
		}
	}

	private void _copyProperties(File file, String languageId)
		throws IOException {

		Path path = Paths.get(
			_langDirName, _langFileName + "_" + languageId + ".properties");

		Files.copy(file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
	}

	private void _createProperties(String content, String languageId)
		throws IOException {

		_createProperties(content, languageId, null);
	}

	private void _createProperties(
			String content, String languageId, String parentLanguageId)
		throws IOException {

		File propertiesFile = new File(
			_langDirName + "/" + _langFileName + "_" + languageId +
				".properties");

		Properties properties = new Properties();

		if (propertiesFile.exists()) {
			properties = _readProperties(propertiesFile);
		}

		Properties parentProperties = null;

		if (parentLanguageId != null) {
			File parentPropertiesFile = new File(
				_langDirName + "/" + _langFileName + "_" + parentLanguageId +
					".properties");

			if (parentPropertiesFile.exists()) {
				parentProperties = _readProperties(parentPropertiesFile);
			}
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content));
			UnsyncBufferedWriter unsyncBufferedWriter =
				new UnsyncBufferedWriter(
					new OutputStreamWriter(
						new FileOutputStream(propertiesFile),
						StringPool.UTF8))) {

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

					if (_keysWithUpdatedValues.contains(key)) {
						translatedText = null;
					}

					if ((translatedText == null) &&
						(parentProperties != null)) {

						translatedText = parentProperties.getProperty(key);
					}

					if ((translatedText == null) && (_renameKeys != null)) {
						String renameKey = _renameKeys.getProperty(key);

						if (renameKey != null) {
							translatedText = properties.getProperty(key);

							if ((translatedText == null) &&
								(parentProperties != null)) {

								translatedText = parentProperties.getProperty(
									key);
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
						else if (LanguageValidator.isSpecialPropertyKey(key)) {
							translatedText = _getSpecialPropertyValue(key);
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
								"IP was blocked because of over usage. " +
									"Please use another IP.");
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
		}
	}

	private String _fixEnglishTranslation(String key, String value) {

		// http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles
		// http://titlecapitalization.com
		// http://www.imdb.com

		if (value.contains(" this ")) {
			if (value.contains(".") || value.contains("?") ||
				value.contains(":") ||
				key.equals(
					"the-url-of-the-page-comparing-this-page-content-with-the-previous-version")) {
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

	private String _getMicrosoftLanguageId(String languageId) {
		if (languageId.equals("pt_BR") || languageId.equals("pt_PT")) {
			return "pt";
		}
		else if (languageId.equals("hi_IN")) {
			return "hi";
		}
		else if (languageId.equals("in")) {
			return "id";
		}
		else if (languageId.equals("iw")) {
			return "he";
		}
		else if (languageId.equals("nb")) {
			return "no";
		}
		else if (languageId.equals("zh_CN")) {
			return "zh-CHS";
		}
		else if (languageId.equals("zh_TW")) {
			return "zh-CHT";
		}

		return languageId;
	}

	private void _initKeysWithUpdatedValues() throws Exception {
		File backupLanguageFile = new File(
			_langDirName + "/" + _langFileName + "_en.properties");

		if (!backupLanguageFile.exists()) {
			return;
		}

		Properties backupLanguageProperties = _readProperties(
			backupLanguageFile);

		File languageFile = new File(
			_langDirName + "/" + _langFileName + ".properties");

		Properties languageProperties = _readProperties(languageFile);

		Set<Map.Entry<Object, Object>> set = languageProperties.entrySet();

		for (Map.Entry<Object, Object> entry : set) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			if (!value.equals(backupLanguageProperties.get(key))) {
				_keysWithUpdatedValues.add(key);
			}
		}
	}

	private String _orderProperties(File propertiesFile) throws IOException {
		if (!propertiesFile.exists()) {
			return null;
		}

		String content = _read(propertiesFile);

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content));
			UnsyncBufferedWriter unsyncBufferedWriter =
				new UnsyncBufferedWriter(new FileWriter(propertiesFile))) {

			Map<String, String> messages = new TreeMap<>(
				new NaturalOrderStringComparator(true, true));

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
						_sortAndWrite(
							unsyncBufferedWriter, messages, firstLine);
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
		}

		return _read(propertiesFile);
	}

	private String _read(File file) throws IOException {
		String s = new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	private Properties _readProperties(File file) throws IOException {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			return PropertiesUtil.load(fileInputStream, StringPool.UTF8);
		}
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

		if (!_translate) {
			return null;
		}

		// LPS-61961

		if (toLanguageId.equals("da") || toLanguageId.equals("de") ||
			toLanguageId.equals("fi") || toLanguageId.equals("ja") ||
			toLanguageId.equals("nl") || toLanguageId.equals("pt_PT") ||
			toLanguageId.equals("sv")) {

			return null;
		}

		// Limit the number of retries to 3

		if (limit == 3) {
			return null;
		}

		Language fromLanguage = Language.fromString(
			_getMicrosoftLanguageId(fromLanguageId));

		Language toLanguage = Language.fromString(
			_getMicrosoftLanguageId(toLanguageId));

		if (toLanguage == null) {
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

			toText = Translate.execute(fromText, fromLanguage, toLanguage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Keep trying

		if (toText == null) {
			return _translate(
				fromLanguageId, toLanguageId, key, fromText, ++limit);
		}

		return toText;
	}

	private final Set<String> _keysWithUpdatedValues = new HashSet<>();
	private final String _langDirName;
	private final String _langFileName;
	private final Properties _portalLanguageProperties;
	private final Properties _renameKeys;
	private final boolean _translate;

}