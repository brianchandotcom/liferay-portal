/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.check.UpgradeCatchAllCheck;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Kyle Miho
 */
@RunWith(Parameterized.class)
public class UpgradeCatchAllCheckTest extends BaseSourceProcessorTestCase {

	@Parameterized.Parameters(name = "Testcase-{index}: Testing {0} .{1}")
	public static Iterable<Object[]> data() throws Exception {
		List<Object[]> objectsArray = new ArrayList<>();

		String[] issueKeys = StringUtil.split(
			System.getProperty("issue.key", null), StringPool.COMMA);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		ClassLoader classLoader = UpgradeCatchAllCheck.class.getClassLoader();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			StringUtil.read(
				classLoader.getResourceAsStream(
					"dependencies/replacements.json")));

		List<String> jsonArrayIssueKeys = new ArrayList<>();

		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject)object;

			String issueKey = jsonObject.getString("issueKey");

			if ((ArrayUtil.isNotEmpty(issueKeys) &&
				 !ArrayUtil.contains(issueKeys, issueKey)) ||
				jsonArrayIssueKeys.contains(issueKey)) {

				continue;
			}

			String[] fileTypes = _getValidExtensions(jsonObject);

			for (String fileType : fileTypes) {
				if (_hasValidUpgradeFiles(issueKey, fileType)) {
					objectsArray.add(new Object[] {issueKey, fileType});
				}
			}

			jsonArrayIssueKeys.add(issueKey);
		}

		return objectsArray;
	}

	public UpgradeCatchAllCheckTest(String issueKey, String fileType) {
		_issueKey = issueKey;
		_fileType = fileType;
	}

	@Before
	public void setUp() {
		UpgradeCatchAllCheck.setIssueKey(_issueKey);
		UpgradeCatchAllCheck.setTestMode(true);
	}

	@After
	public void tearDown() {
		UpgradeCatchAllCheck.setIssueKey(null);
		UpgradeCatchAllCheck.setTestMode(false);
	}

	@Test
	public void testUpgradeCatchAllCheck() throws Exception {
		String fileName =
			StringUtil.replace(_issueKey, CharPool.DASH, CharPool.UNDERLINE) +
				".test" + _fileType;

		if (_hasValidUpgradeFiles(_issueKey, _fileType)) {
			_testUpgradeCatchAllCheck(
				"upgrade/upgrade-catch-all-check/" + fileName);
		}
	}

	@Override
	protected SourceFormatterArgs getSourceFormatterArgs() {
		SourceFormatterArgs sourceFormatterArgs =
			super.getSourceFormatterArgs();

		sourceFormatterArgs.setCheckCategoryNames(Arrays.asList("Upgrade"));
		sourceFormatterArgs.setJavaParserEnabled(false);
		sourceFormatterArgs.setSourceFormatterProperties(
			Arrays.asList(
				"upgrade.to.liferay.version=" + _UPGRADE_TO_LIFERAY_VERSION,
				"upgrade.to.release.version=" + _UPGRADE_TO_RELEASE_VERSION));

		return sourceFormatterArgs;
	}

	private static String[] _getValidExtensions(JSONObject jsonObject) {
		String[] validExtensions = JSONUtil.toStringArray(
			jsonObject.getJSONArray("validExtensions"));

		if (ArrayUtil.isEmpty(validExtensions)) {
			return new String[] {"java"};
		}

		return validExtensions;
	}

	private static boolean _hasValidUpgradeFiles(
		String issueKey, String fileType) {

		String fileName =
			StringUtil.replace(issueKey, CharPool.DASH, CharPool.UNDERLINE) +
				".test" + fileType;

		Path filePath = Paths.get(
			"src/test/resources/com/liferay/source/formatter/dependencies" +
				"/upgrade/upgrade-catch-all-check/" + fileName);

		return Files.exists(filePath);
	}

	private void _testUpgradeCatchAllCheck(String fileName) throws Exception {
		if (fileName.endsWith(".testjava")) {
			test(fileName, UpgradeCatchAllCheck.getExpectedMessages());
		}
		else {
			test(fileName);
		}
	}

	private static final String _UPGRADE_TO_LIFERAY_VERSION = "7.4.13.u27";

	private static final String _UPGRADE_TO_RELEASE_VERSION = "2024.q1.1";

	private final String _fileType;
	private final String _issueKey;

}