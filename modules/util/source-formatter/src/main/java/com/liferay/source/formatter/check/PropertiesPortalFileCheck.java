/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.processor.PropertiesSourceProcessor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesPortalFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (((isPortalSource() || isSubrepository()) &&
			 fileName.matches(".*/portal(-[^-/]+)*\\.properties")) ||
			(!isPortalSource() && !isSubrepository() &&
			 fileName.endsWith("portal.properties"))) {

			content = _sortPortalProperties(absolutePath, content);

			content = _formatPortalProperties(absolutePath, content);
		}

		return content;
	}

	private String _formatPortalProperties(String absolutePath, String content)
		throws IOException {

		List<String> allowedSingleLinePropertyKeys = getAttributeValues(
			_ALLOWED_SINGLE_LINE_PROPERTY_KEYS, absolutePath);

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.matches("    [^# ]+?=[^,]+(,[^ ][^,]+)+")) {
					String propertyKey = StringUtil.extractFirst(
						StringUtil.trimLeading(line), "=");

					if (!propertyKey.contains("regex") &&
						!allowedSingleLinePropertyKeys.contains(propertyKey)) {

						line = line.replaceFirst("=", "=\\\\\n        ");

						line = line.replaceAll(",", ",\\\\\n        ");
					}
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private String _generateProperties(Map<String, List<String>> properties) {
		StringBundler sb = new StringBundler();

		for (Map.Entry<String, List<String>> entry : properties.entrySet()) {
			List<String> values = entry.getValue();

			if (values.size() > 1) {
				sb.append("\n");
			}

			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);

			if (values.size() > 1) {
				String mergedValues = _mergeValues(entry.getValue());

				if (!mergedValues.startsWith(StringPool.OPEN_BRACKET)) {
					sb.append("\\\n");
				}

				sb.append(mergedValues);
				sb.append("\n\n");
			}
			else {
				sb.append(values.get(0));
				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private synchronized String _getPortalPropertiesContent(String absolutePath)
		throws IOException {

		if (_portalPortalPropertiesContent != null) {
			return _portalPortalPropertiesContent;
		}

		if (isPortalSource() || isSubrepository()) {
			_portalPortalPropertiesContent = getPortalContent(
				"portal-impl/src/portal.properties", absolutePath);

			if (_portalPortalPropertiesContent == null) {
				_portalPortalPropertiesContent = StringPool.BLANK;
			}

			return _portalPortalPropertiesContent;
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			_portalPortalPropertiesContent = IOUtils.toString(url);
		}
		else {
			_portalPortalPropertiesContent = StringPool.BLANK;
		}

		return _portalPortalPropertiesContent;
	}

	private String _mergeValues(List<String> list) {
		StringBundler sb = new StringBundler(3 * list.size());

		for (String s : list) {
			if (!StringUtil.equals(s, StringPool.CLOSE_BRACKET) &&
				!StringUtil.equals(s, StringPool.OPEN_BRACKET)) {

				sb.append(StringPool.FOUR_SPACES);
			}

			sb.append(s);

			if (StringUtil.equals(s, StringPool.BACK_SLASH)) {
				sb.append("\n");
			}
			else if (StringUtil.equals(s, StringPool.OPEN_BRACKET)) {
				sb.append("\\\n");
			}
			else {
				sb.append(",\\\n");
			}
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _sortPortalProperties(String absolutePath, String content)
		throws IOException {

		if (absolutePath.endsWith("/portal-impl/src/portal.properties")) {
			return content;
		}

		Map<String, List<String>> propertiesMap = new TreeMap<>(
			new NaturalOrderStringComparator());

		try (FileReader fileReader = new FileReader(new File(absolutePath));
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(fileReader)) {

			String key = null;
			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (Validator.isNull(line) ||
					line.startsWith(StringPool.POUND)) {

					continue;
				}

				if (line.indexOf('=') >= 0) {
					key = line.substring(0, line.indexOf('='));

					String value = line.substring(line.indexOf('=') + 1);

					if (!Objects.isNull(value) && !value.equals("\\")) {
						List<String> set = propertiesMap.get(key);

						if (set == null) {
							set = new ArrayList<>();
						}

						if (value.equals("[\\")) {
							value = StringUtil.removeLast(value, "\\");
						}

						set.add(value);

						propertiesMap.put(key, set);
					}
				}
				else {
					String value = line;

					if (value.endsWith(",\\")) {
						value = value.substring(0, value.length() - 2);
					}

					if (key == null) {
						return content;
					}

					List<String> set = propertiesMap.get(key);

					if (set == null) {
						set = new ArrayList<>();
					}

					set.add(value);

					propertiesMap.put(key, set);
				}
			}
		}

		Properties portalProperties = new Properties();

		portalProperties.load(
			new StringReader(_getPortalPropertiesContent(absolutePath)));

		Map<String, List<String>> portalOSGiEnvironmentPropertiesMap =
			new TreeMap<>(new NaturalOrderStringComparator());
		Map<String, List<String>> portalPropertiesMap = new TreeMap<>(
			new PortalPropertiesComparator());

		Set<Map.Entry<String, List<String>>> entrySet =
			propertiesMap.entrySet();

		Iterator<Map.Entry<String, List<String>>> iterator =
			entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, List<String>> properties = iterator.next();

			String propertyKey = properties.getKey();

			if (portalProperties.containsKey(propertyKey) ||
				propertyKey.startsWith("module.framework.")) {

				portalPropertiesMap.put(
					propertyKey, propertiesMap.get(propertyKey));
				iterator.remove();
			}
			else if (propertyKey.startsWith(
						"configuration.override.com.liferay.")) {

				portalOSGiEnvironmentPropertiesMap.put(
					propertyKey, propertiesMap.get(propertyKey));
				iterator.remove();
			}
		}

		String newContent = StringBundler.concat(
			_generateProperties(portalPropertiesMap), "\n\n",
			_generateProperties(propertiesMap), "\n\n",
			_generateProperties(portalOSGiEnvironmentPropertiesMap));

		newContent = StringUtil.replace(newContent, "\n\n\n", "\n\n");

		newContent = newContent.trim();

		if (!StringUtil.equals(content, newContent)) {
			return newContent;
		}

		return content;
	}

	private static final String _ALLOWED_SINGLE_LINE_PROPERTY_KEYS =
		"allowedSingleLinePropertyKeys";

	private String _portalPortalPropertiesContent;

	private class PortalPropertiesComparator
		extends NaturalOrderStringComparator {

		public PortalPropertiesComparator() {
			_lastModuleFrameworkPosiston =
				_portalPortalPropertiesContent.lastIndexOf(
					"    module.framework.");

			if (_lastModuleFrameworkPosiston == -1) {
				_lastModuleFrameworkPosiston =
					_portalPortalPropertiesContent.lastIndexOf(
						"    #module.framework.");
			}
		}

		@Override
		public int compare(String propertyKey1, String propertyKey2) {
			int propertyKey1Posiston = _getPortalPropertiesPosition(
				_portalPortalPropertiesContent, propertyKey1);
			int propertyKey2Posiston = _getPortalPropertiesPosition(
				_portalPortalPropertiesContent, propertyKey2);

			if (propertyKey1Posiston == -1) {
				if (propertyKey2Posiston <= _lastModuleFrameworkPosiston) {
					return 1;
				}

				return -1;
			}

			if (propertyKey2Posiston == -1) {
				if (propertyKey1Posiston <= _lastModuleFrameworkPosiston) {
					return -1;
				}

				return 1;
			}

			return propertyKey1Posiston - propertyKey2Posiston;
		}

		private int _getPortalPropertiesPosition(
			String content, String propertyKey) {

			int pos = content.indexOf(StringPool.FOUR_SPACES + propertyKey);

			if (pos == -1) {
				pos = content.indexOf(
					StringPool.FOUR_SPACES + StringPool.POUND + propertyKey);
			}

			return pos;
		}

		private int _lastModuleFrameworkPosiston = -1;

	}

}