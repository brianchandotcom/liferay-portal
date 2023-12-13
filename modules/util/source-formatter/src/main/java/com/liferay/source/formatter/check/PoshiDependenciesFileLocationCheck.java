/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Alan Huang
 */
public class PoshiDependenciesFileLocationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith(".testcase")) {
			return content;
		}

		_populateTestCaseAndDependenciesFileNames();

		_checkDependenciesFileReferences(absolutePath, fileName);
		_checkGlobalDependenciesFileReferences(absolutePath, fileName);

		return content;
	}

	private void _checkDependenciesFileReferences(
		String absolutePath, String fileName) {

		for (Map.Entry<String, Set<String>> entry :
				_dependenciesFileReferencesMap.entrySet()) {

			Set<String> referencesFileNames = entry.getValue();

			if (referencesFileNames.size() <= 1) {
				continue;
			}

			Set<String> removedDuplicatedFilePaths = new HashSet<>();

			for (String referencesFileName : referencesFileNames) {
				String referencesFilePath = referencesFileName.substring(
					0, referencesFileName.lastIndexOf("/"));

				removedDuplicatedFilePaths.add(referencesFilePath);
			}

			if (removedDuplicatedFilePaths.size() <= 1) {
				continue;
			}

			for (String referencesFileName : referencesFileNames) {
				if (referencesFileName.equals(absolutePath)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Test dependencies file '", entry.getKey(),
							"' is referenced by multiple modules, move it to ",
							"global dependencies directory"));

					break;
				}
			}
		}
	}

	private void _checkGlobalDependenciesFileReferences(
		String absolutePath, String fileName) {

		for (Map.Entry<String, Set<String>> entry :
				_dependenciesGlobalFileReferencesMap.entrySet()) {

			Set<String> referencesFileNames = entry.getValue();

			if (referencesFileNames.size() != 1) {
				continue;
			}

			for (String referencesFileName : referencesFileNames) {
				if (referencesFileName.equals(absolutePath)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Test dependencies file '", entry.getKey(),
							"' is only referenced by one module, move it to ",
							"module dependencies directory"));

					break;
				}
			}
		}
	}

	private boolean _containsFileName(
		String content, String dependenciesFileName) {

		int x = -1;

		while (true) {
			x = content.indexOf(dependenciesFileName, x + 1);

			if (x == -1) {
				return false;
			}

			char c = content.charAt(x - 1);

			if ((c != CharPool.QUOTE) && (c != CharPool.COMMA)) {
				x = x + 1;

				continue;
			}

			if ((x + dependenciesFileName.length()) >= content.length()) {
				return false;
			}

			c = content.charAt(x + dependenciesFileName.length());

			if ((c != CharPool.QUOTE) && (c != CharPool.COMMA)) {
				x = x + 1;

				continue;
			}

			return true;
		}
	}

	private synchronized void _populateTestCaseAndDependenciesFileNames()
		throws IOException {

		if (_testCaseFileNames != null) {
			return;
		}

		File file = null;
		List<String> fileNames = null;
		File portalDir = getPortalDir();
		_dependenciesFileReferencesMap = new HashMap<>();
		_testCaseFileNames = new ArrayList<>();

		for (String testCaseFileLocation : _TEST_FILE_LOCATIONS) {
			file = new File(portalDir, testCaseFileLocation);

			fileNames = SourceFormatterUtil.scanForFileNames(
				file.getCanonicalPath(), new String[] {"**/*.testcase"});

			for (String fileName : fileNames) {
				if (fileName.contains("portal-web") ||
					fileName.matches(
						".+/modules/.+-test/src/testFunctional(/.*)?")) {

					_testCaseFileNames.add(fileName);
				}
			}

			fileNames = SourceFormatterUtil.scanForFileNames(
				file.getCanonicalPath(),
				new String[] {
					"**/test/**/dependencies/*", "**/tests/**/dependencies/*"
				});

			for (String fileName : fileNames) {
				if (!fileName.contains("/poshi/") &&
					!fileName.contains("/source-formatter/")) {

					_dependenciesFileReferencesMap.put(
						fileName, new TreeSet<>());
				}
			}
		}

		_dependenciesGlobalFileReferencesMap = new HashMap<>();

		file = new File(portalDir, _GLOBAL_DEPENDENCIES_LOCATION);

		fileNames = SourceFormatterUtil.scanForFileNames(
			file.getCanonicalPath(), new String[0]);

		for (String fileName : fileNames) {
			if (!fileName.contains(".lar/") && !fileName.contains(".war/") &&
				!fileName.contains(".zip/")) {

				_dependenciesGlobalFileReferencesMap.put(
					fileName, new TreeSet<>());
			}
		}

		for (String testCaseFileName : _testCaseFileNames) {
			String testCaseFileContent = FileUtil.read(
				new File(testCaseFileName));

			for (Map.Entry<String, Set<String>> entry :
					_dependenciesFileReferencesMap.entrySet()) {

				String dependenciesFileName = entry.getKey();

				int pos = dependenciesFileName.lastIndexOf(StringPool.SLASH);

				String shortFileName = dependenciesFileName.substring(pos + 1);

				if (_containsFileName(testCaseFileContent, shortFileName)) {
					Set<String> referencesFileNames = entry.getValue();

					referencesFileNames.add(testCaseFileName);

					_dependenciesFileReferencesMap.put(
						dependenciesFileName, referencesFileNames);
				}
			}

			for (Map.Entry<String, Set<String>> entry :
					_dependenciesGlobalFileReferencesMap.entrySet()) {

				String dependenciesGlobalFileName = entry.getKey();

				int pos = dependenciesGlobalFileName.lastIndexOf(
					StringPool.SLASH);

				String shortFileName = dependenciesGlobalFileName.substring(
					pos + 1);

				if (_containsFileName(testCaseFileContent, shortFileName)) {
					Set<String> referencesFileNames = entry.getValue();

					referencesFileNames.add(testCaseFileName);

					_dependenciesGlobalFileReferencesMap.put(
						dependenciesGlobalFileName, referencesFileNames);
				}
			}
		}
	}

	private static final String _GLOBAL_DEPENDENCIES_LOCATION =
		"portal-web/test/functional/com/liferay/portalweb/dependencies";

	private static final String[] _TEST_FILE_LOCATIONS = {
		"modules", "portal-web/test/functional/com/liferay/portalweb"
	};

	private Map<String, Set<String>> _dependenciesFileReferencesMap;
	private Map<String, Set<String>> _dependenciesGlobalFileReferencesMap;
	private List<String> _testCaseFileNames;

}