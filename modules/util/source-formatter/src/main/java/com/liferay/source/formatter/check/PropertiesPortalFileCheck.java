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

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
			 fileName.matches(".*/portal(-[^-/]+)*\\.properties") &&
			 !fileName.contains("-legacy-") &&
			 !fileName.endsWith("portal-osgi-configuration.properties")) ||
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
				sb.append("\n");
			}
			else {
				sb.append(values.get(0));
			}

			sb.append("\n");
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private synchronized String _getPortalPropertiesContent(String absolutePath)
		throws IOException {

		if (_portalPropertiesContent != null) {
			return _portalPropertiesContent;
		}

		if (isPortalSource() || isSubrepository()) {
			_portalPropertiesContent = getPortalContent(
				"portal-impl/src/portal.properties", absolutePath);

			if (_portalPropertiesContent == null) {
				_portalPropertiesContent = StringPool.BLANK;
			}

			return _portalPropertiesContent;
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			_portalPropertiesContent = IOUtils.toString(url);
		}
		else {
			_portalPropertiesContent = StringPool.BLANK;
		}

		return _portalPropertiesContent;
	}

	private boolean _hasPortalPropertiesCommonPrefixes(String propertyKey) {
		for (String portalPropertiesCommonPrefix :
				_PORTAL_PROPERTIES_COMMON_PREFIXES) {

			if (propertyKey.startsWith(portalPropertiesCommonPrefix)) {
				return true;
			}
		}

		return false;
	}

	private String _mergeValues(List<String> values) {
		StringBundler sb = new StringBundler(3 * values.size());

		for (String value : values) {
			if (!StringUtil.equals(value, StringPool.CLOSE_BRACKET) &&
				!StringUtil.equals(value, StringPool.OPEN_BRACKET)) {

				sb.append(StringPool.FOUR_SPACES);
			}

			sb.append(value);

			if (StringUtil.equals(value, StringPool.BACK_SLASH)) {
				sb.append("\n");
			}
			else if (StringUtil.equals(value, StringPool.OPEN_BRACKET)) {
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
			String value = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (Validator.isNull(line) ||
					line.startsWith(StringPool.POUND)) {

					continue;
				}

				if (line.indexOf('=') >= 0) {
					key = line.substring(0, line.indexOf('='));
					value = line.substring(line.indexOf('=') + 1);

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
					value = line;

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

		String portalPropertiesContent = _getPortalPropertiesContent(
			absolutePath);

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

			if (portalPropertiesContent.contains(propertyKey) ||
				portalPropertiesContent.contains("#" + propertyKey) ||
				_hasPortalPropertiesCommonPrefixes(propertyKey)) {

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

	private static final String[] _PORTAL_PROPERTIES_COMMON_PREFIXES = {
		"com.liferay.portal.servlet.filters.",
		"data.limit.model.max.count[com.liferay.", "module.framework."
	};

	private String _portalPropertiesContent;

	private class PortalPropertiesComparator
		extends NaturalOrderStringComparator {

		public PortalPropertiesComparator() {
			for (String portalPropertiesCommonPrefix :
					_PORTAL_PROPERTIES_COMMON_PREFIXES) {

				int x = _portalPropertiesContent.lastIndexOf(
					StringPool.FOUR_SPACES + portalPropertiesCommonPrefix);

				if (x == -1) {
					x = _portalPropertiesContent.lastIndexOf(
						StringPool.FOUR_SPACES + StringPool.POUND +
							portalPropertiesCommonPrefix);
				}

				_commonPrefixesLastPositionsMap.put(
					portalPropertiesCommonPrefix, x);
			}
		}

		@Override
		public int compare(String propertyKey1, String propertyKey2) {
			int propertyKey1Position = _getPortalPropertiesPosition(
				_portalPropertiesContent, propertyKey1);
			int propertyKey2Position = _getPortalPropertiesPosition(
				_portalPropertiesContent, propertyKey2);

			if ((propertyKey1Position != -1) && (propertyKey2Position != -1)) {
				return propertyKey1Position - propertyKey2Position;
			}

			int propertiesLastPosition1 = _getCommonPrefixLastPosition(
				propertyKey1);

			if ((propertyKey1Position == -1) && (propertyKey2Position != -1)) {
				if (propertyKey2Position <= propertiesLastPosition1) {
					return 1;
				}

				return -1;
			}

			int propertiesLastPosition2 = _getCommonPrefixLastPosition(
				propertyKey2);

			if ((propertyKey1Position != -1) && (propertyKey2Position == -1)) {
				if (propertyKey1Position <= propertiesLastPosition2) {
					return -1;
				}

				return 1;
			}

			if (propertiesLastPosition1 != propertiesLastPosition2) {
				return propertiesLastPosition1 - propertiesLastPosition2;
			}

			return super.compare(propertyKey1, propertyKey2);
		}

		private int _getCommonPrefixLastPosition(String propertyKey) {
			for (Map.Entry<String, Integer> entry :
					_commonPrefixesLastPositionsMap.entrySet()) {

				if (StringUtil.startsWith(propertyKey, entry.getKey())) {
					return entry.getValue();
				}
			}

			return -1;
		}

		private int _getPortalPropertiesPosition(
			String content, String propertyKey) {

			int x = content.indexOf(StringPool.FOUR_SPACES + propertyKey);

			if (x == -1) {
				x = content.indexOf(
					StringPool.FOUR_SPACES + StringPool.POUND + propertyKey);
			}

			return x;
		}

		private final Map<String, Integer> _commonPrefixesLastPositionsMap =
			new HashMap<>();

	}

}