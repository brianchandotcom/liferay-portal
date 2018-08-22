/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.web.internal.suggestions.display.context;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
public class SuggestionsPortletDisplayBuilderTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpHtml();
		setUpHttp();

		setUpDisplayBuilder();
	}

	@Test
	public void testGetRelatedQueriesSuggestions() {
		List<SuggestionDisplayContext> suggestionDisplayContexts =
			buildRelatedQueriesSuggestions(Arrays.asList("alpha"));

		assertSuggestion(
			"[alpha] | q=X(q<<alpha)", suggestionDisplayContexts.get(0));
	}

	@Test
	public void testGetRelatedQueriesSuggestionsEmptyByDefault() {
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		List<SuggestionDisplayContext> suggestionDisplayContexts =
			suggestionsPortletDisplayContext.getRelatedQueriesSuggestions();

		Assert.assertTrue(suggestionDisplayContexts.isEmpty());
	}

	@Test
	public void testGetRelatedQueriesSuggestionsMultiple() {
		setUpSearchedKeywords("q", "a b");

		List<SuggestionDisplayContext> suggestionDisplayContexts =
			buildRelatedQueriesSuggestions(Arrays.asList("a C", "a b C"));

		Assert.assertEquals(
			suggestionDisplayContexts.toString(), 2,
			suggestionDisplayContexts.size());

		assertSuggestion(
			"a [C] | q=a b(q<<a C)", suggestionDisplayContexts.get(0));

		assertSuggestion(
			"a b [C] | q=a b(q<<a b C)", suggestionDisplayContexts.get(1));
	}

	@Test
	public void testGetRelatedSuggestionsWithEmptyList() {
		setUpSearchedKeywords("q", "a b");

		List<SuggestionDisplayContext> suggestionDisplayContexts =
			buildRelatedQueriesSuggestions(Collections.emptyList());

		Assert.assertTrue(suggestionDisplayContexts.isEmpty());
	}

	@Test
	public void testGetRelatedSuggestionsWithKeywordsAsSuggestions() {
		setUpSearchedKeywords("q", "a b");

		List<SuggestionDisplayContext> suggestionDisplayContexts =
			buildRelatedQueriesSuggestions(Arrays.asList("a b", "a b C"));

		Assert.assertEquals(
			suggestionDisplayContexts.toString(), 1,
			suggestionDisplayContexts.size());

		assertSuggestion(
			"a b [C] | q=a b(q<<a b C)", suggestionDisplayContexts.get(0));
	}

	@Test
	public void testGetRelatedSuggestionsWithNullSuggestions() {
		setUpSearchedKeywords("q", "a b");

		List<SuggestionDisplayContext> suggestionDisplayContexts =
			buildRelatedQueriesSuggestions(Arrays.asList(null, "", "a b C"));

		Assert.assertEquals(
			suggestionDisplayContexts.toString(), 1,
			suggestionDisplayContexts.size());

		assertSuggestion(
			"a b [C] | q=a b(q<<a b C)", suggestionDisplayContexts.get(0));
	}

	@Test
	public void testGetSpellCheckSuggestion() {
		SuggestionDisplayContext suggestionDisplayContext =
			buildSpellCheckSuggestion("alpha");

		assertSuggestion("[alpha] | q=X(q<<alpha)", suggestionDisplayContext);
	}

	@Test
	public void testGetSpellCheckSuggestionEqualsToKeywords() {
		setUpSearchedKeywords("q", "a b");

		Assert.assertNull(buildSpellCheckSuggestion("a b"));
	}

	@Test
	public void testGetSpellCheckSuggestionFormatted() {
		setUpSearchedKeywords("q", "a b");

		SuggestionDisplayContext suggestionDisplayContext =
			buildSpellCheckSuggestion("a C");

		assertSuggestion("a [C] | q=a b(q<<a C)", suggestionDisplayContext);
	}

	@Test
	public void testGetSpellCheckSuggestionsNullByDefault() {
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertNull(
			suggestionsPortletDisplayContext.getSpellCheckSuggestion());
	}

	@Test
	public void testGetSpellCheckSuggestionWithEmptySuggestion() {
		setUpSearchedKeywords("q", "a b");

		Assert.assertNull(buildSpellCheckSuggestion(""));
	}

	@Test
	public void testGetSpellCheckSuggestionWithNullSuggestion() {
		setUpSearchedKeywords("q", "a b");

		Assert.assertNull(buildSpellCheckSuggestion(null));
	}

	@Test
	public void testHasRelatedQueriesSuggestionsFalseByDefault() {
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.hasRelatedQueriesSuggestions());
	}

	@Test
	public void testHasRelatedSuggestionsFalseWithDisabledAndNonemptyList() {
		_displayBuilder.setRelatedQueriesSuggestions(
			Arrays.asList(RandomTestUtil.randomString()));
		_displayBuilder.setRelatedQueriesSuggestionsEnabled(false);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.hasRelatedQueriesSuggestions());
	}

	@Test
	public void testHasRelatedSuggestionsFalseWithEnabledAndEmptyList() {
		_displayBuilder.setRelatedQueriesSuggestions(Collections.emptyList());
		_displayBuilder.setRelatedQueriesSuggestionsEnabled(true);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.hasRelatedQueriesSuggestions());
	}

	@Test
	public void testHasRelatedSuggestionsTrueWithEnabledAndNonemptyList() {
		_displayBuilder.setRelatedQueriesSuggestions(
			Arrays.asList(RandomTestUtil.randomString()));
		_displayBuilder.setRelatedQueriesSuggestionsEnabled(true);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertTrue(
			suggestionsPortletDisplayContext.hasRelatedQueriesSuggestions());
	}

	@Test
	public void testHasSpellCheckSuggestionFalseByDefault() {
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.hasSpellCheckSuggestion());
	}

	@Test
	public void testHasSpellCheckSuggestionsFalseWithDisabledAndSuggestion() {
		_displayBuilder.setSpellCheckSuggestion(RandomTestUtil.randomString());
		_displayBuilder.setSpellCheckSuggestionEnabled(false);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.hasSpellCheckSuggestion());
	}

	@Test
	public void testHasSpellCheckSuggestionsFalseWithEnabledAndNull() {
		_displayBuilder.setSpellCheckSuggestion(null);
		_displayBuilder.setSpellCheckSuggestionEnabled(true);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.hasSpellCheckSuggestion());
	}

	@Test
	public void testHasSpellCheckSuggestionsTrueWithEnabledAndNonemptyList() {
		_displayBuilder.setSpellCheckSuggestion(RandomTestUtil.randomString());
		_displayBuilder.setSpellCheckSuggestionEnabled(true);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertTrue(
			suggestionsPortletDisplayContext.hasSpellCheckSuggestion());
	}

	@Test
	public void testIsRelatedSuggestions() {
		_displayBuilder.setRelatedQueriesSuggestions(
			Arrays.asList(RandomTestUtil.randomString()));
		_displayBuilder.setRelatedQueriesSuggestionsEnabled(true);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertTrue(
			suggestionsPortletDisplayContext.
				isRelatedQueriesSuggestionsEnabled());
	}

	@Test
	public void testIsRelatedSuggestionsFalseByDefault() {
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.
				isRelatedQueriesSuggestionsEnabled());
	}

	@Test
	public void testIsSpellCheckSuggestionEnabled() {
		_displayBuilder.setSpellCheckSuggestionEnabled(true);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertTrue(
			suggestionsPortletDisplayContext.isSpellCheckSuggestionEnabled());
	}

	@Test
	public void testIsSpellCheckSuggestionEnabledFalseByDefault() {
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_displayBuilder.build();

		Assert.assertFalse(
			suggestionsPortletDisplayContext.isSpellCheckSuggestionEnabled());
	}

	protected void assertSuggestion(
		String expected, SuggestionDisplayContext suggestionDisplayContext) {

		String[] parts = StringUtil.split(expected, CharPool.PIPE);

		String suggestedKeywordsFormatted = formatSimplifiedSuggestedKeywords(
			StringUtil.trim(parts[0]));

		String fullURL = toFullURL(StringUtil.trim(parts[1]));

		Assert.assertEquals(
			suggestedKeywordsFormatted + " | " + fullURL,
			suggestionDisplayContext.getSuggestedKeywordsFormatted() + " | " +
				suggestionDisplayContext.getURL());
	}

	protected List<SuggestionDisplayContext> buildRelatedQueriesSuggestions(
		List<String> suggestions) {

		_displayBuilder.setRelatedQueriesSuggestions(suggestions);
		_displayBuilder.setRelatedQueriesSuggestionsEnabled(true);

		SuggestionsPortletDisplayContext displayContext =
			_displayBuilder.build();

		return displayContext.getRelatedQueriesSuggestions();
	}

	protected SuggestionDisplayContext buildSpellCheckSuggestion(
		String spellCheckSuggestion) {

		_displayBuilder.setSpellCheckSuggestion(spellCheckSuggestion);
		_displayBuilder.setSpellCheckSuggestionEnabled(true);

		SuggestionsPortletDisplayContext displayContext =
			_displayBuilder.build();

		return displayContext.getSpellCheckSuggestion();
	}

	protected String formatKeyword(String keyword) {
		if (keyword.charAt(0) == CharPool.OPEN_BRACKET) {
			return "<span class=\"changed-keyword\">" +
				keyword.substring(1, keyword.length() - 1) + "</span>";
		}

		return "<span class=\"unchanged-keyword\">" + keyword + "</span>";
	}

	protected String formatSimplifiedSuggestedKeywords(String simplifiedQuery) {
		return Stream.of(
			StringUtil.split(simplifiedQuery, CharPool.SPACE)
		).map(
			this::formatKeyword
		).collect(
			Collectors.joining(StringPool.SPACE)
		);
	}

	protected void setUpDisplayBuilder() {
		_displayBuilder = new SuggestionsPortletDisplayBuilder(html, http);

		setUpSearchedKeywords("q", "X");
	}

	protected void setUpHtml() {
		Mockito.doAnswer(
			AdditionalAnswers.returnsFirstArg()
		).when(
			html
		).escape(
			Mockito.anyString()
		);
	}

	protected void setUpHttp() {
		Mockito.doAnswer(
			invocation -> StringBundler.concat(
				invocation.getArgumentAt(0, String.class),
				StringPool.OPEN_PARENTHESIS,
				invocation.getArgumentAt(1, String.class), "<<",
				invocation.getArgumentAt(2, String.class),
				StringPool.CLOSE_PARENTHESIS)
		).when(
			http
		).setParameter(
			Mockito.anyString(), Mockito.anyString(), Mockito.anyString()
		);
	}

	protected void setUpSearchedKeywords(
		String keywordsParameterName, String keywords) {

		_displayBuilder.setKeywords(keywords);
		_displayBuilder.setKeywordsParameterName(keywordsParameterName);
		_displayBuilder.setSearchURL(
			StringBundler.concat(
				_URL_PREFIX, keywordsParameterName, StringPool.EQUAL,
				keywords));
	}

	protected String toFullURL(String parameters) {
		return _URL_PREFIX + parameters;
	}

	@Mock
	protected Html html;

	@Mock
	protected Http http;

	private static final String _URL_PREFIX = "http://localhost:8080/?";

	private SuggestionsPortletDisplayBuilder _displayBuilder;

}