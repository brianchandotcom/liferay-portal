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

package com.liferay.portal.search;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Sanz
 */
@Sync
public class DocumentImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_indexer = IndexerRegistryUtil.getIndexer(User.class);

		_indexerPostProcessor = new BaseIndexerPostProcessor() {

			@Override
			public void postProcessDocument(Document document, Object obj)
				throws Exception {

				String screenName = document.get("screenName");

				document.addNumber(
					_FIELD_DOUBLE_ARRAY, _doubleArrays.get(screenName));
				document.addNumber(
					_FIELD_FLOAT_ARRAY, _floatArrays.get(screenName));
				document.addNumber(
					_FIELD_INTEGER_ARRAY, _integerArrays.get(screenName));
				document.addNumber(
					_FIELD_LONG_ARRAY, _longArrays.get(screenName));
				document.addNumber(_FIELD_DOUBLE, _doubles.get(screenName));
				document.addNumber(_FIELD_FLOAT, _floats.get(screenName));
				document.addNumber(_FIELD_INTEGER, _integers.get(screenName));
				document.addNumber(_FIELD_LONG, _longs.get(screenName));
			}

		};

		_indexer.registerIndexerPostProcessor(_indexerPostProcessor);

		populateNumbers();

		for (String screenName : _doubles.keySet()) {
			String firstName = screenName.replaceFirst(
				"user", StringPool.BLANK);

			User user = UserTestUtil.addUser(
				screenName, LocaleUtil.getDefault(), firstName, "Smith", null);

			_indexer.reindex(user);
		}
	}

	@After
	public void tearDown() throws Exception {
		_indexer.unregisterIndexerPostProcessor(_indexerPostProcessor);
	}

	@Test
	public void testFirstNameSearchResultsCount1() throws Exception {
		checkSearchContext(buildSearchContext("first"), 1);
		checkSearchContext(buildSearchContext("second"), 1);
		checkSearchContext(buildSearchContext("third"), 1);
		checkSearchContext(buildSearchContext("fourth"), 1);
		checkSearchContext(buildSearchContext("fifth"), 1);
		checkSearchContext(buildSearchContext("sixth"), 1);
	}

	@Test
	public void testFirstNameSearchResultsCount2() throws Exception {
		for (String keywords : _KEYWORDS) {
			checkSearchContext(buildSearchContext(keywords), 6);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleDouble() throws Exception {
		for (String keywords : _KEYWORDS) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_MIXED, _FIELD_DOUBLE, Sort.DOUBLE_TYPE);
		}

		for (String keywords : _KEYWORDS_ODD) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_ODD_MIXED, _FIELD_DOUBLE,
				Sort.DOUBLE_TYPE);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleFloat() throws Exception {
		for (String keywords : _KEYWORDS) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_DESCENDING, _FIELD_FLOAT,
				Sort.FLOAT_TYPE);
		}

		for (String keywords : _KEYWORDS_ODD) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_ODD_DESCENDING, _FIELD_FLOAT,
				Sort.FLOAT_TYPE);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleInteger() throws Exception {
		for (String keywords : _KEYWORDS) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_DESCENDING, _FIELD_INTEGER,
				Sort.INT_TYPE);
		}

		for (String keywords : _KEYWORDS_ODD) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_ODD_DESCENDING, _FIELD_INTEGER,
				Sort.INT_TYPE);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleLong() throws Exception {
		for (String keywords : _KEYWORDS) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_ASCENDING, _FIELD_LONG, Sort.LONG_TYPE);
		}

		for (String keywords : _KEYWORDS_ODD) {
			checkSearchContext(
				keywords, _SCREEN_NAMES_ODD_ASCENDING, _FIELD_LONG,
				Sort.LONG_TYPE);
		}
	}

	@Test
	public void testFirstNamesSearchResults() throws Exception {
		for (String keywords : _KEYWORDS) {
			checkSearchContext(buildSearchContext(keywords));
		}
	}

	@Test
	public void testLastNameSearchResults() throws Exception {
		checkSearchContext(buildSearchContext("Smith"));
	}

	@Test
	public void testLastNameSearchResultsCount() throws Exception {
		checkSearchContext(buildSearchContext("Smith"), 6);
	}

	@Test
	public void testLastNameSearchSortedBySingleDouble() throws Exception {
		checkSearchContext(
			"Smith", _SCREEN_NAMES_MIXED, _FIELD_DOUBLE, Sort.DOUBLE_TYPE);
	}

	@Test
	public void testLastNameSearchSortedBySingleFloat() throws Exception {
		checkSearchContext(
			"Smith", _SCREEN_NAMES_DESCENDING, _FIELD_FLOAT, Sort.FLOAT_TYPE);
	}

	@Test
	public void testLastNameSearchSortedBySingleInteger() throws Exception {
		checkSearchContext(
			"Smith", _SCREEN_NAMES_DESCENDING, _FIELD_INTEGER, Sort.INT_TYPE);
	}

	@Test
	public void testLastNameSearchSortedBySingleLong() throws Exception {
		checkSearchContext(
			"Smith", _SCREEN_NAMES_ASCENDING, _FIELD_LONG, Sort.LONG_TYPE);
	}

	protected void assertSort(
			Sort sort, Query query, SearchContext searchContext,
			String... screenNames)
		throws Exception {

		Hits results = IndexSearcherHelperUtil.search(searchContext, query);

		List<String> searchResultValues = new ArrayList<>(screenNames.length);
		List<String> screenNamesList = new ArrayList<>(screenNames.length);

		for (int i = 0; i < screenNames.length; i++) {
			Document document = results.doc(i);

			searchResultValues.add(document.get(sort.getFieldName()));

			screenNamesList.add(document.get("screenName"));
		}

		Assert.assertEquals(
			StringUtil.merge(searchResultValues), StringUtil.merge(screenNames),
			StringUtil.merge(screenNamesList));
	}

	protected SearchContext buildSearchContext(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setKeywords(keywords);
		searchContext.setGroupIds(new long[0]);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(getSelectedFieldNames());

		return searchContext;
	}

	protected void checkSearchContext(SearchContext searchContext)
		throws Exception {

		Hits results = _indexer.search(searchContext, getSelectedFieldNames());

		for (Document document : results.getDocs()) {
			String screenName = document.get("screenName");

			Assert.assertEquals(
				Double.valueOf(document.get(_FIELD_DOUBLE)),
				_doubles.get(screenName), 0);

			Assert.assertEquals(
				Long.valueOf(document.get(_FIELD_LONG)), _longs.get(screenName),
				0);

			Assert.assertEquals(
				Float.valueOf(document.get(_FIELD_FLOAT)),
				_floats.get(screenName), 0);

			Assert.assertEquals(
				Integer.valueOf(document.get(_FIELD_INTEGER)),
				_integers.get(screenName), 0);

			Assert.assertArrayEquals(
				getDoubleArray(document), _doubleArrays.get(screenName));

			Assert.assertArrayEquals(
				getLongArray(document), _longArrays.get(screenName));

			Assert.assertArrayEquals(
				getFloatArray(document), _floatArrays.get(screenName));

			Assert.assertArrayEquals(
				getIntegerArray(document), _integerArrays.get(screenName));
		}
	}

	protected void checkSearchContext(
			SearchContext searchContext, long expectedHitsLength)
		throws Exception {

		Hits hits = _indexer.search(searchContext);

		Assert.assertEquals(
			hits.toString(), expectedHitsLength, hits.getLength());
	}

	protected void checkSearchContext(
			SearchContext searchContext, Sort sort, String[] screenNames)
		throws Exception {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(getSelectedFieldNames());

		searchContext.setSorts(sort);

		Query query = _indexer.getFullQuery(searchContext);

		assertSort(sort, query, searchContext, screenNames);
	}

	protected void checkSearchContext(
			String keywords, String[] ascendingScreenNames, String field,
			int type)
		throws Exception {

		SearchContext searchContext = buildSearchContext(keywords);

		checkSearchContext(
			searchContext, SortFactoryUtil.create(field, type, false),
			ascendingScreenNames);

		String[] descendingScreenNames = Arrays.copyOf(
			ascendingScreenNames, ascendingScreenNames.length);

		ArrayUtil.reverse(descendingScreenNames);

		checkSearchContext(
			searchContext, SortFactoryUtil.create(field, type, true),
			descendingScreenNames);
	}

	protected Double[] getDoubleArray(Document document) {
		List<Double> list = new ArrayList<>();

		for (String value : document.getValues(_FIELD_DOUBLE_ARRAY)) {
			list.add(Double.valueOf(value));
		}

		return list.toArray(new Double[list.size()]);
	}

	protected Float[] getFloatArray(Document document) {
		List<Float> list = new ArrayList<>();

		for (String value : document.getValues(_FIELD_FLOAT_ARRAY)) {
			list.add(Float.valueOf(value));
		}

		return list.toArray(new Float[list.size()]);
	}

	protected Integer[] getIntegerArray(Document document) {
		List<Integer> list = new ArrayList<>();

		for (String value : document.getValues(_FIELD_INTEGER_ARRAY)) {
			list.add(Integer.valueOf(value));
		}

		return list.toArray(new Integer[list.size()]);
	}

	protected Long[] getLongArray(Document document) {
		List<Long> list = new ArrayList<>();

		for (String value : document.getValues(_FIELD_LONG_ARRAY)) {
			list.add(Long.valueOf(value));
		}

		return list.toArray(new Long[list.size()]);
	}

	protected String[] getSelectedFieldNames() {
		return new String[] {
			_FIELD_DOUBLE, _FIELD_DOUBLE_ARRAY, _FIELD_FLOAT,
			_FIELD_FLOAT_ARRAY, _FIELD_INTEGER, _FIELD_INTEGER_ARRAY,
			_FIELD_LONG, _FIELD_LONG_ARRAY, "screenName"
		};
	}

	protected void populateNumberArrays(
		String screenName, Double[] doubleArray, Float[] floatArray,
		Integer[] integerArray, Long[] longArray) {

		_doubleArrays.put(screenName, doubleArray);
		_floatArrays.put(screenName, floatArray);
		_integerArrays.put(screenName, integerArray);
		_longArrays.put(screenName, longArray);
	}

	protected void populateNumbers() {
		populateNumbers(
			"firstuser", 1e-11, 8e-5f, Integer.MAX_VALUE, Long.MIN_VALUE);
		populateNumberArrays(
			"firstuser", new Double[] {1e-11, 2e-11, 3e-11},
			new Float[] {8e-5f, 8e-5f, 8e-5f}, new Integer[] {1, 2, 3},
			new Long[] {-3L, -2L, -1L});

		populateNumbers(
			"seconduser", 3e-11, 7e-5f, Integer.MAX_VALUE - 1,
			Long.MIN_VALUE + 1L);
		populateNumberArrays(
			"seconduser", new Double[] {1e-11, 2e-11, 5e-11},
			new Float[] {9e-5f, 8e-5f, 7e-5f}, new Integer[] {1, 3, 4},
			new Long[] {-3L, -2L, -2L});

		populateNumbers(
			"thirduser", 5e-11, 6e-5f, Integer.MAX_VALUE - 2,
			Long.MIN_VALUE + 2L);
		populateNumberArrays(
			"thirduser", new Double[] {1e-11, 3e-11, 2e-11},
			new Float[] {9e-5f, 8e-5f, 9e-5f}, new Integer[] {2, 1, 1},
			new Long[] {-3L, -3L, -1L});

		populateNumbers(
			"fourthuser", 2e-11, 5e-5f, Integer.MAX_VALUE - 3,
			Long.MIN_VALUE + 3L);
		populateNumberArrays(
			"fourthuser", new Double[] {1e-11, 2e-11, 4e-11},
			new Float[] {9e-5f, 9e-5f, 7e-5f}, new Integer[] {1, 2, 4},
			new Long[] {-3L, -3L, -2L});

		populateNumbers(
			"fifthuser", 4e-11, 4e-5f, Integer.MAX_VALUE - 4,
			Long.MIN_VALUE + 4L);
		populateNumberArrays(
			"fifthuser", new Double[] {1e-11, 3e-11, 1e-11},
			new Float[] {9e-5f, 9e-5f, 8e-5f}, new Integer[] {1, 4, 4},
			new Long[] {-4L, -2L, -1L});

		populateNumbers(
			"sixthuser", 6e-11, 3e-5f, Integer.MAX_VALUE - 5,
			Long.MIN_VALUE + 5L);
		populateNumberArrays(
			"sixthuser", new Double[] {2e-11, 1e-11, 1e-11},
			new Float[] {9e-5f, 9e-5f, 9e-5f}, new Integer[] {2, 1, 2},
			new Long[] {-4L, -2L, -2L});
	}

	protected void populateNumbers(
		String screenName, Double numberDouble, Float floatNumber,
		Integer numberInteger, Long longNumber) {

		_doubles.put(screenName, numberDouble);
		_floats.put(screenName, floatNumber);
		_integers.put(screenName, numberInteger);
		_longs.put(screenName, longNumber);
	}

	private static final String _FIELD_DOUBLE = "sd";

	private static final String _FIELD_DOUBLE_ARRAY = "md";

	private static final String _FIELD_FLOAT = "sf";

	private static final String _FIELD_FLOAT_ARRAY = "mf";

	private static final String _FIELD_INTEGER = "si";

	private static final String _FIELD_INTEGER_ARRAY = "mi";

	private static final String _FIELD_LONG = "sl";

	private static final String _FIELD_LONG_ARRAY = "ml";

	private static final String[] _KEYWORDS = {
		"sixth second first fourth fifth third",
		"second first fourth fifth third sixth",
		"first fourth fifth third sixth second",
		"fourth fifth third sixth second first",
		"fifth third sixth second first fourth",
		"third sixth second first fourth fifth"
	};

	private static final String[] _KEYWORDS_ODD = {
		"first fifth third", "fifth third first", "third first fifth",
		"first third fifth", "fifth first third"
	};

	private static final String[] _SCREEN_NAMES_ASCENDING = {
		"firstuser", "seconduser", "thirduser", "fourthuser", "fifthuser",
		"sixthuser"
	};

	private static final String[] _SCREEN_NAMES_DESCENDING = {
		"sixthuser", "fifthuser", "fourthuser", "thirduser", "seconduser",
		"firstuser"
	};

	private static final String[] _SCREEN_NAMES_MIXED = {
		"firstuser", "fourthuser", "seconduser", "fifthuser", "thirduser",
		"sixthuser"
	};

	private static final String[] _SCREEN_NAMES_ODD_ASCENDING =
		{"firstuser", "thirduser", "fifthuser"};

	private static final String[] _SCREEN_NAMES_ODD_DESCENDING =
		{"fifthuser", "thirduser", "firstuser"};

	private static final String[] _SCREEN_NAMES_ODD_MIXED =
		new String[] {"firstuser", "fifthuser", "thirduser"};

	private final Map<String, Double[]> _doubleArrays = new HashMap<>();
	private final Map<String, Double> _doubles = new HashMap<>();
	private final Map<String, Float[]> _floatArrays = new HashMap<>();
	private final Map<String, Float> _floats = new HashMap<>();

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<User> _indexer;
	private IndexerPostProcessor _indexerPostProcessor;
	private final Map<String, Integer[]> _integerArrays = new HashMap<>();
	private final Map<String, Integer> _integers = new HashMap<>();
	private final Map<String, Long[]> _longArrays = new HashMap<>();
	private final Map<String, Long> _longs = new HashMap<>();

}