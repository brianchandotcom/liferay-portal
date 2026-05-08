/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.freemarker.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.FreeMarker;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;

import java.io.File;

import java.util.Collection;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class FreeMarkerUtil {

	public static String processTemplate(
			File copyrightFile, String copyrightYear, String name,
			Map<String, Object> context)
		throws Exception {

		return _freeMarker.processTemplate(
			copyrightFile, copyrightYear,
			"com/liferay/portal/tools/rest/builder/dependencies/" + name +
				".ftl",
			context);
	}

	public static void processTemplate(
			File copyrightFile, String copyrightYear, String name,
			Map<String, Object> context, File outputFile)
		throws Exception {

		processTemplate(
			copyrightFile, copyrightYear, name, context, outputFile, null,
			true);
	}

	public static void processTemplate(
			File copyrightFile, String copyrightYear, String name,
			Map<String, Object> context, File outputFile,
			Collection<File> modifiedFiles)
		throws Exception {

		processTemplate(
			copyrightFile, copyrightYear, name, context, outputFile,
			modifiedFiles, true);
	}

	public static void processTemplate(
			File copyrightFile, String copyrightYear, String name,
			Map<String, Object> context, File outputFile,
			Collection<File> modifiedFiles, boolean addHash)
		throws Exception {

		String content = processTemplate(
			copyrightFile, copyrightYear, name, context);

		if (StringUtil.endsWith(outputFile.getName(), ".java")) {
			content = _removeUnusedSchemaImports(content, context);
		}

		FileUtil.write(outputFile, content, modifiedFiles, addHash);
	}

	private static int _indexOfStandalone(
		String content, String simpleName, int fromIndex) {

		int contentLength = content.length();
		int nameLength = simpleName.length();

		while (fromIndex <= (contentLength - nameLength)) {
			int index = content.indexOf(simpleName, fromIndex);

			if (index == -1) {
				return -1;
			}

			int beforeIndex = index - 1;
			int afterIndex = index + nameLength;

			boolean leftBoundary = false;

			if ((beforeIndex < 0) ||
				!Character.isJavaIdentifierPart(content.charAt(beforeIndex))) {

				leftBoundary = true;
			}

			boolean rightBoundary = false;

			if ((afterIndex >= contentLength) ||
				!Character.isJavaIdentifierPart(content.charAt(afterIndex))) {

				rightBoundary = true;
			}

			if (leftBoundary && rightBoundary) {
				return index;
			}

			fromIndex = index + 1;
		}

		return -1;
	}

	private static String _removeUnusedSchemaImports(
		String content, Map<String, Object> context) {

		for (String key : _SCHEMA_CONTEXT_KEYS) {
			Object value = context.get(key);

			if (!(value instanceof Map)) {
				continue;
			}

			Map<?, ?> map = (Map<?, ?>)value;

			for (Object schemaName : map.keySet()) {
				String baseName = schemaName.toString();

				for (String suffix : _SCHEMA_NAME_SUFFIXES) {
					content = _stripUnusedImport(content, baseName + suffix);
				}
			}
		}

		return content;
	}

	private static String _stripUnusedImport(
		String content, String simpleName) {

		int firstIndex = _indexOfStandalone(content, simpleName, 0);

		if (firstIndex == -1) {
			return content;
		}

		int nextIndex = _indexOfStandalone(content, simpleName, firstIndex + 1);

		if (nextIndex != -1) {
			return content;
		}

		int lineStart = content.lastIndexOf('\n', firstIndex);
		int lineEnd = content.indexOf('\n', firstIndex);

		String line = content.substring(lineStart + 1, lineEnd);

		String trimmedLine = line.trim();

		String importSuffix = "." + simpleName + ";";

		if (!trimmedLine.startsWith("import ") ||
			!trimmedLine.endsWith(importSuffix)) {

			return content;
		}

		return content.substring(0, lineStart) + content.substring(lineEnd);
	}

	private static final String[] _SCHEMA_CONTEXT_KEYS = {
		"allExternalSchemas", "allSchemas", "globalEnumSchemas"
	};

	private static final String[] _SCHEMA_NAME_SUFFIXES = {
		"", "DTOActionMetadataProvider", "Resource", "ResourceImpl", "SerDes"
	};

	private static final FreeMarker _freeMarker = new FreeMarker();

}