/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service.impl;

import com.liferay.oauth2.provider.service.impl.OAuth2ApplicationScopeAliasesLocalServiceImpl.ScopeNamespace;
import com.liferay.oauth2.provider.util.builder.OAuth2ScopeBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.compress.utils.Sets;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Stian Sigvartsen
 */
public class OAuth2ScopeBuilderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplicationIsolation() {
		String applicationName1 = "Test.Application1";
		String applicationName2 = "Test.Application2";

		String[] scopes = {"everything.read", "everything.write"};

		String scopeAlias = "everything";

		Map<Map.Entry<String, String>, Set<String>> simpeEntryScopeAliases =
			_exerciseBuilder(
				builder -> {
					builder.forApplication(
						applicationName1, _TEST_BUNDLE_SYMBOLIC_NAME,
						applicationScopeAssigner ->
							applicationScopeAssigner.assignScope(
								scopes
							).mapToScopeAlias(
								_getApplicationScopeAlias(
									applicationName1, scopeAlias)
							));

					builder.forApplication(
						applicationName2, _TEST_BUNDLE_SYMBOLIC_NAME,
						applicationScopeAssigner ->
							applicationScopeAssigner.assignScope(
								scopes
							).mapToScopeAlias(
								_getApplicationScopeAlias(
									applicationName2, scopeAlias)
							));
				});

		simpeEntryScopeAliases.forEach(
			(key, value) -> {
				Assert.assertThat(
					value, CoreMatchers.not(CoreMatchers.hasItems(scopes)));
				Assert.assertEquals(1, value.size());
				Assert.assertThat(
					value,
					CoreMatchers.hasItems(
						_getApplicationScopeAlias(key.getKey(), scopeAlias)));
			});
	}

	@Test
	public void testApplicationIsolationWithScopeAliases() {
		String[] scopes = {"everything.read", "everything.write"};

		Map<String, Set<String>> applicationScopeAlias =
			HashMapBuilder.<String, Set<String>>put(
				"Test.Application1", Sets.newHashSet("application1.everything")
			).put(
				"Test.Application2", Sets.newHashSet("application2.everything")
			).build();

		Map<Map.Entry<String, String>, Set<String>> simpeEntryScopeAliases =
			_exerciseBuilder(
				builder -> applicationScopeAlias.forEach(
					(applicationName, scopeAliases) -> builder.forApplication(
						applicationName, _TEST_BUNDLE_SYMBOLIC_NAME,
						applicationScopeAssigner ->
							applicationScopeAssigner.assignScope(
								scopes
							).mapToScopeAlias(
								scopeAliases
							))));

		simpeEntryScopeAliases.forEach(
			(key, value) -> {
				Assert.assertThat(
					value, CoreMatchers.not(CoreMatchers.hasItems(scopes)));
				Assert.assertEquals(
					value, applicationScopeAlias.get(key.getKey()));
			});
	}

	@Test
	public void testMapToScopeAlias() {
		String applicationName = "Test.Application";

		String[] scopes = {"everything.read", "everything.write"};

		String scopeAlias = "everything";

		Map<Map.Entry<String, String>, Set<String>> simpeEntryScopeAliases =
			_exerciseBuilder(
				builder -> builder.forApplication(
					applicationName, _TEST_BUNDLE_SYMBOLIC_NAME,
					applicationScopeAssigner ->
						applicationScopeAssigner.assignScope(
							scopes
						).mapToScopeAlias(
							scopeAlias
						)));

		simpeEntryScopeAliases.forEach(
			(key, value) -> {
				Assert.assertThat(
					value, CoreMatchers.not(CoreMatchers.hasItems(scopes)));
				Assert.assertEquals(1, value.size());
				Assert.assertThat(value, CoreMatchers.hasItems(scopeAlias));
			});
	}

	@Test
	public void testMultipleSeparateAssignmentsToSameScopeAlias() {
		String applicationName = "Test.Application";

		Set<String> scopes = Sets.newHashSet(
			"everything.read", "everything.write");

		Collection<String> scopeAlias = Collections.singleton("everything");

		Map<Map.Entry<String, String>, Set<String>> simpeEntryScopeAliases =
			_exerciseBuilder(
				builder -> builder.forApplication(
					applicationName, _TEST_BUNDLE_SYMBOLIC_NAME,
					applicationScopeAssigner -> {
						applicationScopeAssigner.assignScope(
							"everything.read"
						).mapToScopeAlias(
							scopeAlias
						);

						applicationScopeAssigner.assignScope(
							"everything.write"
						).mapToScopeAlias(
							scopeAlias
						);
					}));

		Assert.assertEquals(
			simpeEntryScopeAliases.toString(), 2,
			simpeEntryScopeAliases.size());

		simpeEntryScopeAliases.forEach(
			(key, value) -> {
				Assert.assertEquals(scopeAlias, value);

				scopes.remove(key.getValue());
			});

		Assert.assertEquals(scopes.toString(), 0, scopes.size());
	}

	@Test
	public void testNoSpecifiedScopeAlias() {
		String applicationName = "Test.Application";

		String[] scopesArray = {"everything.read", "everything.write"};

		Set<String> scopes = Sets.newHashSet(scopesArray);

		// Test assigning scopes using Collection

		Map<Map.Entry<String, String>, Set<String>> simpeEntryScopeAliases1 =
			_exerciseBuilder(
				builder -> builder.forApplication(
					applicationName, _TEST_BUNDLE_SYMBOLIC_NAME,
					applicationScopeAssigner ->
						applicationScopeAssigner.assignScope(scopes)));

		// Test that the resulting scope aliases all map to all scopes

		simpeEntryScopeAliases1.forEach(
			(key, value) -> Assert.assertEquals(value, scopes));

		// Repeat using VarArgs

		Map<Map.Entry<String, String>, Set<String>> simpeEntryScopeAliases2 =
			_exerciseBuilder(
				builder -> builder.forApplication(
					applicationName, _TEST_BUNDLE_SYMBOLIC_NAME,
					applicationScopeAssigner ->
						applicationScopeAssigner.assignScope(scopesArray)));

		// Assert the result is identical

		Assert.assertEquals(
			simpeEntryScopeAliases2.toString(), simpeEntryScopeAliases1.size(),
			simpeEntryScopeAliases2.size());

		for (Map.Entry<Map.Entry<String, String>, Set<String>> entry :
				simpeEntryScopeAliases1.entrySet()) {

			Assert.assertEquals(
				entry.getValue(), simpeEntryScopeAliases2.get(entry.getKey()));
		}

		// Test separate calls result in each scope mapping to a different scope
		// alias

		simpeEntryScopeAliases1 = _exerciseBuilder(
			builder -> builder.forApplication(
				applicationName, _TEST_BUNDLE_SYMBOLIC_NAME,
				applicationScopeAssigner -> {
					applicationScopeAssigner.assignScope(scopesArray[0]);
					applicationScopeAssigner.assignScope(scopesArray[1]);
				}));

		simpeEntryScopeAliases1.forEach(
			(key, value) -> Assert.assertEquals(
				Collections.singleton(key.getValue()), value));
	}

	private Map<Map.Entry<String, String>, Set<String>> _exerciseBuilder(
		Consumer<OAuth2ScopeBuilder> builderConsumer) {

		Map<Map.Entry<ScopeNamespace, String>, List<String>>
			simpleEntryScopeAliases = new HashMap<>();

		OAuth2ScopeBuilder builder =
			new OAuth2ApplicationScopeAliasesLocalServiceImpl.
				OAuth2ScopeBuilderImpl(simpleEntryScopeAliases);

		builderConsumer.accept(builder);

		Map<Map.Entry<String, String>, Set<String>> simpleEntryScopeAliasesSet =
			new HashMap<>();

		simpleEntryScopeAliases.forEach(
			(key, value) -> {
				ScopeNamespace scopeNamespace = key.getKey();

				simpleEntryScopeAliasesSet.put(
					new AbstractMap.SimpleEntry<String, String>(
						scopeNamespace.getApplicationName(), key.getValue()),
					new HashSet<>(value));
			});

		return simpleEntryScopeAliasesSet;
	}

	private String _getApplicationScopeAlias(
		String applicationName, String scope) {

		return applicationName + StringPool.PERIOD + scope;
	}

	private static final String _TEST_BUNDLE_SYMBOLIC_NAME =
		"test.bundle.symbolic.name";

}