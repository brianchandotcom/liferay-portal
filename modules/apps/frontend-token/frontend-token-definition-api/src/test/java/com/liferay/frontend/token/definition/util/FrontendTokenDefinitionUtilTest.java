/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.util;

import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gabriel Lima
 */
public class FrontendTokenDefinitionUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetFrontendTokenNames() {
		_testGetFrontendTokenNamesWithBlankDefinition();
		_testGetFrontendTokenNamesWithDuplicateName();
		_testGetFrontendTokenNamesWithInvalidCategories();
		_testGetFrontendTokenNamesWithInvalidSetsAndTokens();
		_testGetFrontendTokenNamesWithMalformedDefinition();
		_testGetFrontendTokenNamesWithThemeAndOwnDefinitions();
		_testGetFrontendTokenNamesWithValidDefinition();
		_testGetFrontendTokenNamesWithoutThemeDefinition();
	}

	private String _createFrontendTokenDefinitionJSON(
		JSONArray frontendTokenSetsJSONArray) {

		return JSONUtil.put(
			"frontendTokenCategories",
			JSONUtil.putAll(
				JSONUtil.put(
					"frontendTokenSets", frontendTokenSetsJSONArray
				).put(
					"name", RandomTestUtil.randomString()
				))
		).toString();
	}

	private FrontendTokenDefinition _mockFrontendTokenDefinition(
		String... frontendTokenNames) {

		FrontendTokenDefinition frontendTokenDefinition = Mockito.mock(
			FrontendTokenDefinition.class);

		List<FrontendToken> frontendTokens = TransformUtil.transformToList(
			frontendTokenNames,
			frontendTokenName -> {
				FrontendToken frontendToken = Mockito.mock(FrontendToken.class);

				Mockito.when(
					frontendToken.getName()
				).thenReturn(
					frontendTokenName
				);

				return frontendToken;
			});

		Mockito.when(
			frontendTokenDefinition.getFrontendTokens()
		).thenReturn(
			frontendTokens
		);

		return frontendTokenDefinition;
	}

	private void _testGetFrontendTokenNamesWithBlankDefinition() {
		List<String> frontendTokenNames =
			FrontendTokenDefinitionUtil.getFrontendTokenNames(null);

		Assert.assertTrue(frontendTokenNames.isEmpty());

		frontendTokenNames = FrontendTokenDefinitionUtil.getFrontendTokenNames(
			"");

		Assert.assertTrue(frontendTokenNames.isEmpty());

		frontendTokenNames = FrontendTokenDefinitionUtil.getFrontendTokenNames(
			null, null);

		Assert.assertTrue(frontendTokenNames.isEmpty());

		frontendTokenNames = FrontendTokenDefinitionUtil.getFrontendTokenNames(
			null, "");

		Assert.assertTrue(frontendTokenNames.isEmpty());
	}

	private void _testGetFrontendTokenNamesWithDuplicateName() {
		String frontendTokenName = "customPrimaryColor";

		List<String> frontendTokenNames =
			FrontendTokenDefinitionUtil.getFrontendTokenNames(
				_createFrontendTokenDefinitionJSON(
					JSONUtil.putAll(
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								JSONUtil.put("name", frontendTokenName))
						).put(
							"name", RandomTestUtil.randomString()
						),
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								JSONUtil.put("name", frontendTokenName))
						).put(
							"name", RandomTestUtil.randomString()
						))));

		Assert.assertEquals(
			frontendTokenNames.toString(), 2, frontendTokenNames.size());
		Assert.assertEquals(frontendTokenName, frontendTokenNames.get(0));
		Assert.assertEquals(frontendTokenName, frontendTokenNames.get(1));
	}

	private void _testGetFrontendTokenNamesWithInvalidCategories() {
		List<String> frontendTokenNames =
			FrontendTokenDefinitionUtil.getFrontendTokenNames(
				JSONUtil.put(
					"frontendTokenCategories", JSONUtil.putAll("not-an-object")
				).toString());

		Assert.assertTrue(frontendTokenNames.isEmpty());
	}

	private void _testGetFrontendTokenNamesWithInvalidSetsAndTokens() {
		List<String> frontendTokenNames =
			FrontendTokenDefinitionUtil.getFrontendTokenNames(
				_createFrontendTokenDefinitionJSON(
					JSONUtil.putAll(
						"not-an-object",
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								"not-an-object",
								JSONUtil.put("name", "customPrimaryColor"))
						).put(
							"name", RandomTestUtil.randomString()
						))));

		Assert.assertEquals(
			frontendTokenNames.toString(), 1, frontendTokenNames.size());
		Assert.assertTrue(frontendTokenNames.contains("customPrimaryColor"));
	}

	private void _testGetFrontendTokenNamesWithMalformedDefinition() {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				FrontendTokenDefinitionUtil.class.getName(),
				LoggerTestUtil.WARN)) {

			List<String> frontendTokenNames =
				FrontendTokenDefinitionUtil.getFrontendTokenNames(
					"{not valid json");

			Assert.assertTrue(frontendTokenNames.isEmpty());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to parse frontend token definition",
				logEntry.getMessage());
		}
	}

	private void _testGetFrontendTokenNamesWithoutThemeDefinition() {
		String frontendTokenName = RandomTestUtil.randomString();

		Assert.assertEquals(
			ListUtil.fromArray(frontendTokenName),
			FrontendTokenDefinitionUtil.getFrontendTokenNames(
				null,
				_createFrontendTokenDefinitionJSON(
					JSONUtil.putAll(
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								JSONUtil.put("name", frontendTokenName))
						).put(
							"name", RandomTestUtil.randomString()
						)))));
	}

	private void _testGetFrontendTokenNamesWithThemeAndOwnDefinitions() {
		String secondaryFrontendTokenName = RandomTestUtil.randomString();
		String primaryFrontendTokenName = RandomTestUtil.randomString();

		Assert.assertEquals(
			ListUtil.fromArray(
				secondaryFrontendTokenName, primaryFrontendTokenName),
			FrontendTokenDefinitionUtil.getFrontendTokenNames(
				_mockFrontendTokenDefinition(secondaryFrontendTokenName),
				_createFrontendTokenDefinitionJSON(
					JSONUtil.putAll(
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								JSONUtil.put("name", primaryFrontendTokenName))
						).put(
							"name", RandomTestUtil.randomString()
						)))));
	}

	private void _testGetFrontendTokenNamesWithValidDefinition() {
		List<String> frontendTokenNames =
			FrontendTokenDefinitionUtil.getFrontendTokenNames(
				_createFrontendTokenDefinitionJSON(
					JSONUtil.putAll(
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								JSONUtil.put("name", "customPrimaryColor"),
								JSONUtil.put("name", "customSecondaryColor"))
						).put(
							"name", RandomTestUtil.randomString()
						),
						JSONUtil.put(
							"frontendTokens",
							JSONUtil.putAll(
								JSONUtil.put("name", "customHeadingColor"))
						).put(
							"name", RandomTestUtil.randomString()
						))));

		Assert.assertTrue(frontendTokenNames.contains("customPrimaryColor"));
		Assert.assertTrue(frontendTokenNames.contains("customSecondaryColor"));
		Assert.assertTrue(frontendTokenNames.contains("customHeadingColor"));
		Assert.assertEquals(
			frontendTokenNames.toString(), 3, frontendTokenNames.size());
	}

}