/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.jakarta.ee.transformer.function;

import com.liferay.portal.tools.jakarta.ee.transformer.TransformerAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * @author Shuyang Zhou
 */
public class BundleHeaderReplacerBiFunction
	implements BiFunction<String, Map<Object, Object>, Map<Object, Object>> {

	@Override
	public Map<Object, Object> apply(
		String invoker, Map<Object, Object> headerMap) {

		Map<Object, Object> modifiedHeaderMap = new HashMap<>();

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<Object, Object> entry : headerMap.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();

			if ((value instanceof String) &&
				!Objects.equals(key.toString(), "Bundle-ClassPath")) {

				String newValue = TransformerAgent.replace(
					TransformerAgent.replacementDotMap, (String)value);

				if (Objects.equals(key.toString(), "Import-Package")) {
					newValue = _fixDuplication(newValue);

					newValue = _fixVersion(newValue);
				}
				else if (Objects.equals(key.toString(), "Export-Package")) {
					newValue = _fixVersion(newValue);
				}

				if (!Objects.equals(value, newValue)) {
					sb.append("key: ");
					sb.append(entry.getKey());
					sb.append(", old value: ");
					sb.append(value);
					sb.append(", new value: ");
					sb.append(newValue);
					sb.append(", ");
				}

				value = newValue;
			}

			modifiedHeaderMap.put(key, value);
		}

		if (sb.length() != 0) {
			sb.setLength(sb.length() - 2);
			sb.append(']');
		}

		if (!_JAKARTA_EE_TRANSFORMER_BUNDLE_HEADER_REPLACER_LOGGING_DISABLED) {
			System.err.println(
				"JakartaEETransformer#BundleHeaderReplacer#" + invoker + "[" +
					sb.toString());
		}

		return modifiedHeaderMap;
	}

	private static Map<String, String> _loadPackageVersions()
		throws IOException {

		Map<String, String> packageVersions = new HashMap<>();

		try (InputStream inputStream =
				BundleHeaderReplacerBiFunction.class.getResourceAsStream(
					"dependencies/package-version.properties");
			Reader reader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(reader)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}

				String[] parts = line.split("=");

				packageVersions.put(parts[0], parts[1]);
			}
		}

		return packageVersions;
	}

	private String _fixDuplication(String importPackages) {
		Set<String> cleanPackages = new HashSet<>();

		StringBuilder sb = new StringBuilder();

		for (String importPackage : importPackages.split(",(?=\\D)")) {
			String cleanPackage = importPackage;

			int index = importPackage.indexOf(';');

			if (index != -1) {
				cleanPackage = importPackage.substring(0, index);
			}

			if (cleanPackages.add(cleanPackage)) {
				sb.append(importPackage);
				sb.append(',');
			}
		}

		if (sb.length() != 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private String _fixVersion(String content) {
		List<String> packages = _splitPackages(content);

		for (int i = 0; i < packages.size(); i++) {
			String packageString = packages.get(i);

			String packageKey = packageString;

			int index = packageString.indexOf(';');

			if (index != -1) {
				packageKey = packageString.substring(0, index);
			}

			String version = _packageVersions.get(packageKey);

			if (version == null) {
				continue;
			}

			index = packageString.indexOf(";version=\"");

			if (index == -1) {
				packageString += ";version=\"" + version + "\"";
			}
			else {
				int closeIndex = packageString.indexOf('"', index + 10);

				packageString =
					packageString.substring(0, index + 10) + version +
						packageString.substring(closeIndex);
			}

			packages.set(i, packageString);
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < packages.size(); i++) {
			sb.append(packages.get(i));

			if (i < (packages.size() - 1)) {
				sb.append(',');
			}
		}

		return sb.toString();
	}

	private boolean _isValidComma(String content, int start, int end) {
		int count = 0;

		for (int i = start; i < end; i++) {
			if (content.charAt(i) == '"') {
				count++;
			}
		}

		if ((count % 2) == 0) {
			return true;
		}

		return false;
	}

	private List<String> _splitPackages(String content) {
		List<String> packages = new ArrayList<>();

		int startIndex = 0;

		int scanIndex = 0;

		int index = -1;

		while ((index = content.indexOf(',', scanIndex)) != -1) {
			scanIndex = index + 1;

			if (_isValidComma(content, startIndex, index)) {
				packages.add(content.substring(startIndex, index));

				startIndex = scanIndex;
			}
		}

		packages.add(content.substring(startIndex));

		return packages;
	}

	private static final boolean
		_JAKARTA_EE_TRANSFORMER_BUNDLE_HEADER_REPLACER_LOGGING_DISABLED =
			Boolean.getBoolean(
				"jakarta.ee.transformer.bundle.header.replacer.logging." +
					"disabled");

	private static final Map<String, String> _packageVersions;

	static {
		try {
			_packageVersions = _loadPackageVersions();
		}
		catch (IOException ioException) {
			throw new ExceptionInInitializerError(ioException);
		}
	}

}