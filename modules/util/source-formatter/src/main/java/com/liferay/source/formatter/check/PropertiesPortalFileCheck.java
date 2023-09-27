/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.processor.PropertiesSourceProcessor;

import java.io.IOException;

import java.net.URL;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private synchronized String _getPortalPortalPropertiesContent(
			String absolutePath)
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

	private String _getPropertyCluster(String content, int lineNumber) {
		StringBundler sb = new StringBundler();

		while (true) {
			String line = getLine(content, lineNumber);

			if (Validator.isNull(line)) {
				sb.setIndex(sb.index() - 1);

				return sb.toString();
			}

			sb.append(line);
			sb.append("\n");

			lineNumber++;
		}
	}

	private String _sortPortalProperties(
		String content, int lineNumber, Collection<Integer> positions,
		Map<Integer, Collection<Integer>> propertyClusterPositionsMap) {

		if (propertyClusterPositionsMap.isEmpty()) {
			return content;
		}

		outerLoop:
		for (Map.Entry<Integer, Collection<Integer>> entry :
				propertyClusterPositionsMap.entrySet()) {

			for (int curPosition : entry.getValue()) {
				for (int position : positions) {
					if (curPosition <= position) {
						continue outerLoop;
					}
				}

				int previousLineNumber = entry.getKey();

				String previousPropertyCluster = _getPropertyCluster(
					content, previousLineNumber);

				String propertyCluster = _getPropertyCluster(
					content, lineNumber);

				content = StringUtil.replaceFirst(
					content, propertyCluster, previousPropertyCluster,
					getLineStartPos(content, lineNumber) - 1);
				content = StringUtil.replaceFirst(
					content, previousPropertyCluster, propertyCluster,
					getLineStartPos(content, previousLineNumber) - 1);

				return content;
			}
		}

		return content;
	}

	private String _sortPortalProperties(
		String content, int lineNumber, int pos,
		Map<Integer, Integer> propertyPositionsMap) {

		for (Map.Entry<Integer, Integer> entry :
				propertyPositionsMap.entrySet()) {

			int curPos = entry.getValue();

			if (curPos <= pos) {
				continue;
			}

			int curLineNumber = entry.getKey();

			String curProperty = getLine(content, curLineNumber);

			String property = getLine(content, lineNumber);

			content = StringUtil.replaceFirst(
				content, property, curProperty,
				getLineStartPos(content, lineNumber) - 1);
			content = StringUtil.replaceFirst(
				content, curProperty, property,
				getLineStartPos(content, curLineNumber) - 1);

			return content;
		}

		return content;
	}

	private String _sortPortalProperties(String absolutePath, String content)
		throws IOException {

		if (absolutePath.endsWith("/portal-impl/src/portal.properties")) {
			return content;
		}

		String portalPortalPropertiesContent =
			_getPortalPortalPropertiesContent(absolutePath);

		Map<Integer, Integer> propertyPositionsMap = new HashMap<>();
		Map<Integer, Collection<Integer>> propertyClusterPositionsMap =
			new HashMap<>();

		int startLineNumber = 1;

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				if (Validator.isNull(line)) {
					if (!propertyPositionsMap.isEmpty()) {
						Collection<Integer> positions =
							propertyPositionsMap.values();

						String newContent = _sortPortalProperties(
							content, startLineNumber, positions,
							propertyClusterPositionsMap);

						if (!newContent.equals(content)) {
							return newContent;
						}

						propertyClusterPositionsMap.put(
							startLineNumber, positions);

						propertyPositionsMap = new HashMap<>();
					}

					startLineNumber = lineNumber + 1;

					continue;
				}

				if (line.matches(" *#.*")) {
					continue;
				}

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					continue;
				}

				String property = StringUtil.trim(line.substring(0, pos + 1));

				pos = portalPortalPropertiesContent.indexOf(
					StringPool.FOUR_SPACES + property);

				if (pos == -1) {
					continue;
				}

				String newContent = _sortPortalProperties(
					content, lineNumber, pos, propertyPositionsMap);

				if (!newContent.equals(content)) {
					return newContent;
				}

				propertyPositionsMap.put(lineNumber, pos);
			}
		}

		if (!propertyPositionsMap.isEmpty()) {
			return _sortPortalProperties(
				content, startLineNumber, propertyPositionsMap.values(),
				propertyClusterPositionsMap);
		}

		return content;
	}

	private static final String _ALLOWED_SINGLE_LINE_PROPERTY_KEYS =
		"allowedSingleLinePropertyKeys";

	private String _portalPortalPropertiesContent;

}