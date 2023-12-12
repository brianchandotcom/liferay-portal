/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
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
				_dependenciesFileLocationsMap.entrySet()) {

			Set<String> referencesFiles = entry.getValue();

			Set<String> removedDuplicatedFilePaths = new HashSet<>();

			for (String referencesFile : referencesFiles) {
				String referencesFilePath = referencesFile.substring(
					0, referencesFile.lastIndexOf("/"));

				removedDuplicatedFilePaths.add(referencesFilePath);
			}

			if (removedDuplicatedFilePaths.size() <= 1) {
				continue;
			}

			if (referencesFiles.size() > 1) {
				for (String referencesFile : referencesFiles) {
					if (referencesFile.equals(absolutePath)) {
						addMessage(
							fileName,
							StringBundler.concat(
								"Test dependencies file '", entry.getKey(),
								"' is referenced by multiple modules, move it ",
								"to global dependencies directory"));

						break;
					}
				}
			}
		}
	}

	private synchronized void _checkGlobalDependenciesFileReferences(
		String absolutePath, String fileName) {

		for (Map.Entry<String, Set<String>> entry :
				_dependenciesGlobalFileLocationsMap.entrySet()) {

			Set<String> referencesFiles = entry.getValue();

			if (referencesFiles.size() == 1) {
				for (String referencesFile : referencesFiles) {
					if (referencesFile.equals(absolutePath)) {
						addMessage(
							fileName,
							StringBundler.concat(
								"Test dependencies file '", entry.getKey(),
								"' is only referenced by one module, move it ",
								"to module dependencies directory"));

						break;
					}
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
				break;
			}

			char previousChar = content.charAt(x - 1);

			if ((previousChar != CharPool.QUOTE) &&
				(previousChar != CharPool.COMMA)) {

				continue;
			}

			if ((x + dependenciesFileName.length()) >= content.length()) {
				break;
			}

			char nextChar = content.charAt(x + dependenciesFileName.length());

			if ((nextChar != CharPool.QUOTE) && (nextChar != CharPool.COMMA)) {
				continue;
			}

			return true;
		}

		return false;
	}

	private synchronized void _populateTestCaseAndDependenciesFileNames()
		throws IOException {

		if (_testCaseFileNames != null) {
			return;
		}

		File file = null;
		List<String> fileNames = null;
		File portalDir = getPortalDir();

		_dependenciesFileLocationsMap = new HashMap<>();
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

					_dependenciesFileLocationsMap.put(
						fileName, new TreeSet<>());
				}
			}
		}

		_dependenciesGlobalFileLocationsMap = new HashMap<>();

		file = new File(portalDir, _GLOBAL_DEPENDENCIES_LOCATION);

		fileNames = SourceFormatterUtil.scanForFileNames(
			file.getCanonicalPath(), new String[0]);

		for (String fileName : fileNames) {
			if (!fileName.contains(".lar/") && !fileName.contains(".war/") &&
				!fileName.contains(".zip/")) {

				_dependenciesGlobalFileLocationsMap.put(
					fileName, new TreeSet<>());
			}
		}

		for (String testCaseFileName : _testCaseFileNames) {
			File testCaseFile = new File(testCaseFileName);

			String testCaseFileContent = FileUtil.read(testCaseFile);

			for (Map.Entry<String, Set<String>> entry :
					_dependenciesFileLocationsMap.entrySet()) {

				String dependenciesFileLocation = entry.getKey();

				String dependenciesFileName =
					dependenciesFileLocation.replaceFirst(".*/(.+)", "$1");

				if (_containsFileName(
						testCaseFileContent, dependenciesFileName)) {

					Set<String> referencesFiles = entry.getValue();

					referencesFiles.add(testCaseFileName);

					_dependenciesFileLocationsMap.put(
						dependenciesFileLocation, referencesFiles);
				}
			}

			for (Map.Entry<String, Set<String>> entry :
					_dependenciesGlobalFileLocationsMap.entrySet()) {

				String dependenciesFileLocation = entry.getKey();

				String dependenciesFileName =
					dependenciesFileLocation.replaceFirst(".*/(.+)", "$1");

				if (_containsFileName(
						testCaseFileContent, dependenciesFileName)) {

					Set<String> referencesFiles = entry.getValue();

					referencesFiles.add(testCaseFileName);

					_dependenciesGlobalFileLocationsMap.put(
						dependenciesFileLocation, referencesFiles);
				}
			}
		}
	}

	private static final String _GLOBAL_DEPENDENCIES_LOCATION =
		"portal-web/test/functional/com/liferay/portalweb/dependencies";

	private static final String[] _TEST_FILE_LOCATIONS = {
		"modules", "portal-web/test/functional/com/liferay/portalweb/tests"
	};

	private Map<String, Set<String>> _dependenciesFileLocationsMap;
	private Map<String, Set<String>> _dependenciesGlobalFileLocationsMap;
	private List<String> _testCaseFileNames;

}