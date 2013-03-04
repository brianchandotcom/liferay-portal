/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.usersadmin.util.UserIndexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Sanz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DocumentImplTest {

	public static final String MULTI_DOUBLE = "md";

	public static final String MULTI_FLOAT = "mf";

	public static final String MULTI_INT = "mi";

	public static final String MULTI_LONG = "ml";

	public static final String SINGLE_DOUBLE = "sd";

	public static final String SINGLE_FLOAT = "sf";

	public static final String SINGLE_INT = "si";

	public static final String SINGLE_LONG = "sl";

	@Before
	public void setUp() throws Exception {
		_indexer = IndexerRegistryUtil.getIndexer(UserIndexer.class);

		_indexer.registerIndexerPostProcessor(
			new ExtendedUserIndexerPostProcessor());

		populateUsersNumbers();

		for (String screenName : _userSingleDouble.keySet()) {
			String firstName = screenName.replaceFirst(
				"user", StringPool.BLANK);

			User user = UserTestUtil.addUser(
				screenName, false, firstName, "Smith", null);

			_indexer.reindex(user);
		}
	}

	@Test
	public void testFirstNameSearchResultsCount1() throws Exception {
		checkSearchResultsCount(buildSearchContext("first"), 1);

		checkSearchResultsCount(buildSearchContext("second"), 1);

		checkSearchResultsCount(buildSearchContext("third"), 1);

		checkSearchResultsCount(buildSearchContext("fourth"), 1);

		checkSearchResultsCount(buildSearchContext("fifth"), 1);

		checkSearchResultsCount(buildSearchContext("sixth"), 1);
	}

	@Test
	public void testFirstNameSearchResultsCount2() throws Exception {
		for (String keywords : _searchKeywords) {
			checkSearchResultsCount(buildSearchContext(keywords), 6);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleDouble() throws Exception {
		for (String keywords : _searchKeywords) {
			checkSearchResultsOrder(
				keywords, _mixed, SINGLE_DOUBLE, Sort.DOUBLE_TYPE);
		}

		for (String keywords : _searchKeywordsOdd) {
			checkSearchResultsOrder(
				keywords, _mixedOdd, SINGLE_DOUBLE, Sort.DOUBLE_TYPE);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleFloat() throws Exception {
		for (String keywords : _searchKeywords) {
			checkSearchResultsOrder(
				keywords, _descending, SINGLE_FLOAT, Sort.FLOAT_TYPE);
		}

		for (String keywords : _searchKeywordsOdd) {
			checkSearchResultsOrder(
				keywords, _descendingOdd, SINGLE_FLOAT, Sort.FLOAT_TYPE);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleInteger() throws Exception {
		for (String keywords : _searchKeywords) {
			checkSearchResultsOrder(
				keywords, _descending, SINGLE_INT, Sort.INT_TYPE);
		}

		for (String keywords : _searchKeywordsOdd) {
			checkSearchResultsOrder(
				keywords, _descendingOdd, SINGLE_INT, Sort.INT_TYPE);
		}
	}

	@Test
	public void testFirstNameSearchSortedBySingleLong() throws Exception {
		for (String keywords : _searchKeywords) {
			checkSearchResultsOrder(
				keywords, _ascending, SINGLE_LONG, Sort.LONG_TYPE);
		}

		for (String keywords : _searchKeywordsOdd) {
			checkSearchResultsOrder(
				keywords, _ascendingOdd, SINGLE_LONG, Sort.LONG_TYPE);
		}
	}

	@Test
	public void testFirstNamesSearchResults() throws Exception {
		for (String keywords : _searchKeywords) {
			checkSearchResults(buildSearchContext(keywords));
		}
	}

	@Test
	public void testLastNameSearchResults() throws Exception {
		checkSearchResults(buildSearchContext("Smith"));
	}

	@Test
	public void testLastNameSearchResultsCount() throws Exception {
		checkSearchResultsCount(buildSearchContext("Smith"), 6);
	}

	@Test
	public void testLastNameSearchSortedBySingleDouble() throws Exception {
		checkSearchResultsOrder(
			"Smith", _mixed, SINGLE_DOUBLE, Sort.DOUBLE_TYPE);
	}

	@Test
	public void testLastNameSearchSortedBySingleFloat() throws Exception {
		checkSearchResultsOrder(
			"Smith", _descending, SINGLE_FLOAT, Sort.FLOAT_TYPE);
	}

	@Test
	public void testLastNameSearchSortedBySingleInteger() throws Exception {
		checkSearchResultsOrder(
			"Smith", _descending, SINGLE_INT, Sort.INT_TYPE);
	}

	@Test
	public void testLastNameSearchSortedBySingleLong() throws Exception {
		checkSearchResultsOrder(
			"Smith", _ascending, SINGLE_LONG, Sort.LONG_TYPE);
	}

	protected SearchContext buildSearchContext(String keywords)
		throws Exception {

		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {});
		searchContext.setKeywords(keywords);
		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);

		return searchContext;
	}

	protected void checkSearchResults(SearchContext searchContext)
		throws Exception {

		Hits results = _indexer.search(searchContext);

		for (Document document : results.getDocs()) {
			String screenName = document.get("screenName");

			Assert.assertEquals(
				Double.valueOf(document.get(SINGLE_DOUBLE)),
				_userSingleDouble.get(screenName), 0);

			Assert.assertEquals(
				Long.valueOf(document.get(SINGLE_LONG)),
				_userSingleLong.get(screenName), 0);

			Assert.assertEquals(
				Float.valueOf(document.get(SINGLE_FLOAT)),
				_userSingleFloat.get(screenName), 0);

			Assert.assertEquals(
				Integer.valueOf(document.get(SINGLE_INT)),
				_userSingleInteger.get(screenName), 0);

			Assert.assertArrayEquals(
				getMultiDouble(document), _userMultiDouble.get(screenName));

			Assert.assertArrayEquals(
				getMultiLong(document), _userMultiLong.get(screenName));

			Assert.assertArrayEquals(
				getMultiFloat(document), _userMultiFloat.get(screenName));

			Assert.assertArrayEquals(
				getMultiInteger(document), _userMultiInteger.get(screenName));
		}
	}

	protected void checkSearchResultsCount(
			SearchContext searchContext, long expectedResults)
		throws Exception {

		Hits results = _indexer.search(searchContext);

		Assert.assertEquals(expectedResults, results.getLength());
	}

	protected void checkSearchResultsOrder(
			SearchContext searchContext, Sort sort, String[] expected)
		throws Exception {

		Query query = _indexer.getFullQuery(searchContext);

		Hits results = SearchEngineUtil.search(
			searchContext.getSearchEngineId(), searchContext.getCompanyId(),
			query, sort, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(expected.length, results.getLength());

		for (int i = 0; i < expected.length; i++) {
			Document document = results.doc(i);

			Assert.assertEquals(expected[i], document.get("screenName"));
		}
	}

	protected void checkSearchResultsOrder(
			String keywords, String[] expectedAsc, String field, int type)
		throws Exception {

		String[] expectedDesc = Arrays.copyOf(expectedAsc, expectedAsc.length);

		ArrayUtil.reverse(expectedDesc);

		SearchContext searchContext = buildSearchContext(keywords);

		Sort sortAsc = SortFactoryUtil.create(field, type, false);

		checkSearchResultsOrder(searchContext, sortAsc, expectedAsc);

		Sort sortDesc = SortFactoryUtil.create(field, type, true);

		checkSearchResultsOrder(searchContext, sortDesc, expectedDesc);
	}

	protected Double[] getMultiDouble(Document document) {
		List<Double> multiDouble = new ArrayList<Double>();

		for (String value : document.getValues(MULTI_DOUBLE)) {
			multiDouble.add(Double.valueOf(value));
		}

		return multiDouble.toArray(new Double[]{});
	}

	protected Float[] getMultiFloat(Document document) {
		List<Float> multiFloat = new ArrayList<Float>();

		for (String value : document.getValues(MULTI_FLOAT)) {
			multiFloat.add(Float.valueOf(value));
		}

		return multiFloat.toArray(new Float[]{});
	}

	protected Integer[] getMultiInteger(Document document) {
		List<Integer> multiInt = new ArrayList<Integer>();

		for (String value : document.getValues(MULTI_INT)) {
			multiInt.add(Integer.valueOf(value));
		}

		return multiInt.toArray(new Integer[]{});
	}

	protected Long[] getMultiLong(Document document) {
		List<Long> multiLong = new ArrayList<Long>();

		for (String value : document.getValues(MULTI_LONG)) {
			multiLong.add(Long.valueOf(value));
		}

		return multiLong.toArray(new Long[]{});
	}

	protected void populateMultiUserNumbers(
		String screenName, Double[] numberDoubles, Float[] numberFloats,
		Integer[] numberIntegers, Long[] numberLongs) {

		_userMultiDouble.put(screenName, numberDoubles);

		_userMultiFloat.put(screenName, numberFloats);

		_userMultiInteger.put(screenName, numberIntegers);

		_userMultiLong.put(screenName, numberLongs);
	}

	protected void populateSingleUserNumbers(
		String screenName, Double numberDouble, Float numberFloat,
		Integer numberInteger, Long numberLong) {

		_userSingleDouble.put(screenName, numberDouble);

		_userSingleFloat.put(screenName, numberFloat);

		_userSingleInteger.put(screenName, numberInteger);

		_userSingleLong.put(screenName, numberLong);
	}

	protected void populateUsersNumbers() {
		populateSingleUserNumbers(
			"firstuser", 1e-11, 8e-5f, Integer.MAX_VALUE, Long.MIN_VALUE);
		populateMultiUserNumbers(
			"firstuser", new Double[] {1e-11, 2e-11, 3e-11},
			new Float[] {8e-5f, 8e-5f, 8e-5f}, new Integer[] {1, 2, 3},
			new Long[] {-3L, -2L, -1L});

		populateSingleUserNumbers(
			"seconduser", 3e-11, 7e-5f, Integer.MAX_VALUE - 1,
			Long.MIN_VALUE + 1L);
		populateMultiUserNumbers(
			"seconduser", new Double[] {1e-11, 2e-11, 5e-11},
			new Float[] {9e-5f, 8e-5f, 7e-5f}, new Integer[] {1, 3, 4},
			new Long[] {-3L, -2L, -2L});

		populateSingleUserNumbers(
			"thirduser", 5e-11, 6e-5f, Integer.MAX_VALUE - 2,
			Long.MIN_VALUE + 2L);
		populateMultiUserNumbers(
			"thirduser", new Double[] {1e-11, 3e-11, 2e-11},
			new Float[] {9e-5f, 8e-5f, 9e-5f}, new Integer[] {2, 1, 1},
			new Long[] {-3L, -3L, -1L});

		populateSingleUserNumbers(
			"fourthuser", 2e-11, 5e-5f, Integer.MAX_VALUE - 3,
			Long.MIN_VALUE + 3L);
		populateMultiUserNumbers(
			"fourthuser", new Double[] {1e-11, 2e-11, 4e-11},
			new Float[] {9e-5f, 9e-5f, 7e-5f}, new Integer[] {1, 2, 4},
			new Long[] {-3L, -3L, -2L});

		populateSingleUserNumbers(
			"fifthuser", 4e-11, 4e-5f, Integer.MAX_VALUE - 4,
			Long.MIN_VALUE + 4L);
		populateMultiUserNumbers(
			"fifthuser", new Double[] {1e-11, 3e-11, 1e-11},
			new Float[] {9e-5f, 9e-5f, 8e-5f}, new Integer[] {1, 4, 4},
			new Long[] {-4L, -2L, -1L});

		populateSingleUserNumbers(
			"sixthuser", 6e-11, 3e-5f, Integer.MAX_VALUE - 5,
			Long.MIN_VALUE + 5L);
		populateMultiUserNumbers(
			"sixthuser", new Double[] {2e-11, 1e-11, 1e-11},
			new Float[] {9e-5f, 9e-5f, 9e-5f}, new Integer[] {2, 1, 2},
			new Long[] {-4L, -2L, -2L});
	}

	protected String[] _ascending =
		new String[] {"firstuser", "seconduser", "thirduser", "fourthuser",
			"fifthuser", "sixthuser"};

	protected String[] _ascendingOdd =
		new String[] {"firstuser", "thirduser", "fifthuser"};

	protected String[] _descending =
		new String[] {"sixthuser", "fifthuser", "fourthuser", "thirduser",
			"seconduser","firstuser"};

	protected String[] _descendingOdd =
		new String[] {"fifthuser", "thirduser", "firstuser"};

	protected Indexer _indexer;
	protected String[] _mixed =
		new String[] {"firstuser", "fourthuser", "seconduser", "fifthuser",
			"thirduser", "sixthuser"};

	protected String[] _mixedOdd =
		new String[] {"firstuser", "fifthuser", "thirduser"};

	protected String[] _searchKeywords =
		new String[] {"sixth second first fourth fifth third",
			"second first fourth fifth third sixth",
			"first fourth fifth third sixth second",
			"fourth fifth third sixth second first",
			"fifth third sixth second first fourth",
			"third sixth second first fourth fifth"
		};

	protected String[] _searchKeywordsOdd =
		new String[] {"first fifth third", "fifth third first",
			"third first fifth", "first third fifth", "fifth first third"};

	protected Map<String, Double[]> _userMultiDouble =
		new HashMap<String, Double[]>();
	protected Map<String, Float[]> _userMultiFloat =
		new HashMap<String, Float[]>();
	protected Map<String, Integer[]> _userMultiInteger =
			new HashMap<String, Integer[]>();
	protected Map<String, Long[]> _userMultiLong =
		new HashMap<String, Long[]>();
	protected Map<String, Double> _userSingleDouble =
		new HashMap<String, Double>();
	protected Map<String, Float> _userSingleFloat =
		new HashMap<String, Float>();
	protected Map<String, Integer> _userSingleInteger =
		new HashMap<String, Integer>();
	protected Map<String, Long> _userSingleLong = new HashMap<String, Long>();

	protected class ExtendedUserIndexerPostProcessor extends
		BaseIndexerPostProcessor {

		@Override
		public void postProcessDocument(Document document, Object obj)
			throws Exception {

			String screenName = document.get("screenName");

			document.addNumber(MULTI_DOUBLE, _userMultiDouble.get(screenName));
			document.addNumber(MULTI_FLOAT, _userMultiFloat.get(screenName));
			document.addNumber(MULTI_INT, _userMultiInteger.get(screenName));
			document.addNumber(MULTI_LONG, _userMultiLong.get(screenName));
			document.addNumber(
				SINGLE_DOUBLE, _userSingleDouble.get(screenName));
			document.addNumber(SINGLE_FLOAT, _userSingleFloat.get(screenName));
			document.addNumber(SINGLE_INT, _userSingleInteger.get(screenName));
			document.addNumber(SINGLE_LONG, _userSingleLong.get(screenName));
		}
	}

}