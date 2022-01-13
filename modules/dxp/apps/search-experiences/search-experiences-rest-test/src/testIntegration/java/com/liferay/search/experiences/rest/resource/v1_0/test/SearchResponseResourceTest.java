/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.client.dto.v1_0.SearchHits;
import com.liferay.search.experiences.rest.client.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.client.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.rest.client.pagination.Pagination;
import com.liferay.search.experiences.rest.client.problem.Problem;

import java.util.Collections;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SearchResponseResourceTest
	extends BaseSearchResponseResourceTestCase {

	@Override
	@Test
	public void testPostSearch() throws Exception {
		super.testPostSearch();

		_testPostSearch();
		_testPostSearchZeroResults();

		if (false) {

			// TODO Tests pass with remote Elastic but sidecar does not play
			// well with ConfigurationTemporarySwapper

			_testPostSearchThrowsElasticsearchStatusException();
			_testPostSearchThrowsInvalidQueryEntryExceptionAndUnresolvedTemplateVariableException();

			// TODO SXPBlueprint.toDTO with "{ ... }" freezes and never returns

			_testPostSearchThrowsJsonParseException();

			// TODO SXPBlueprint.toDTO with bad JSON returns a half empty DTO

			_testPostSearchThrowsUnrecognizedPropertyException();
		}
	}

	@Override
	protected SearchResponse testPostSearch_addSearchResponse(
			SearchResponse searchResponse)
		throws Exception {

		return searchResponse;
	}

	private SearchResponse _postSearch(String sxpBlueprintJSON)
		throws Exception {

		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(
			sxpBlueprintJSON);

		Assert.assertNotNull(sxpBlueprint);

		return searchResponseResource.postSearch(
			null, _PAGINATION, sxpBlueprint);
	}

	private String _read() throws Exception {
		Class<?> clazz = getClass();

		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		return StringUtil.read(
			clazz.getResourceAsStream(
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					stackTraceElements[2].getMethodName(), ".json")));
	}

	private void _testPostSearch() throws Exception {
		_postSearch("{}");
		_postSearch(_read());
	}

	private void _testPostSearchThrowsElasticsearchStatusException()
		throws Exception {

		String message = StringBundler.concat(
			"org.elasticsearch.ElasticsearchStatusException: ",
			"ElasticsearchStatusException[Elasticsearch exception [type=",
			"parsing_exception, reason=[INVALID] query malformed, no ",
			"start_object after query name]]");

		try {
			try (ConfigurationTemporarySwapper configurationTemporarySwapper =
					new ConfigurationTemporarySwapper(
						_CONFIGURATION_PID_ELASTICSEARCH,
						new HashMapDictionary<>(
							Collections.singletonMap(
								"logExceptionsOnly", false)))) {

				try (LogCapture logCapture =
						LoggerTestUtil.configureLog4JLogger(
							_CLASS_NAME_EXCEPTION_MAPPER,
							LoggerTestUtil.ERROR)) {

					_postSearch(_read());
				}
			}

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.containsString(message));
		}

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID_ELASTICSEARCH,
					new HashMapDictionary<>(
						Collections.singletonMap("logExceptionsOnly", true)))) {

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER,
					LoggerTestUtil.ERROR)) {

				SearchResponse searchResponse = _postSearch(_read());

				Assert.assertNull(searchResponse.getResponse());
				Assert.assertThat(
					searchResponse.getResponseString(),
					CoreMatchers.containsString(message));
			}
		}
	}

	private void _testPostSearchThrowsInvalidQueryEntryExceptionAndUnresolvedTemplateVariableException()
		throws Exception {

		try {
			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_EXCEPTION_MAPPER, LoggerTestUtil.ERROR)) {

				_postSearch(_read());

				Assert.fail();
			}
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.allOf(
					CoreMatchers.containsString("Invalid query entry at: 0"),
					CoreMatchers.containsString("Invalid query entry at: 1"),
					CoreMatchers.containsString("The key \"value\" is not set"),
					CoreMatchers.containsString(
						"Unresolved template variables: [ipstack.latitude, " +
							"ipstack.longitude]")));
		}
	}

	private void _testPostSearchThrowsJsonParseException() throws Exception {
		try {
			_postSearch("{ ... }");

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.containsString("Input is invalid JSON"));
		}
	}

	private void _testPostSearchThrowsUnrecognizedPropertyException()
		throws Exception {

		try {
			_postSearch(_read());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {

			// TODO The property "INVALID_2" should be in the message

			Assert.assertThat(
				problemException.getMessage(),
				CoreMatchers.containsString(
					StringBundler.concat(
						"The property \"configuration\" is not defined in ",
						"SXPBlueprint. The property \"general\" is not ",
						"defined in Configuration. The property \"INVALID_1\" ",
						"is not defined in General.")));
		}
	}

	private void _testPostSearchZeroResults() throws Exception {
		SearchResponse searchResponse = _postSearch(_read());

		SearchHits searchHits = searchResponse.getSearchHits();

		Assert.assertEquals(Long.valueOf(0), searchHits.getTotalHits());

		String response = String.valueOf(searchResponse.getResponse());

		Assert.assertThat(response, CoreMatchers.containsString("hits"));
		Assert.assertThat(
			response,
			CoreMatchers.not(CoreMatchers.containsString("max_score")));
	}

	private static final String _CLASS_NAME_ELASTICSEARCH_INDEX_SEARCHER =
		"com.liferay.portal.search.elasticsearch7.internal." +
			"ElasticsearchIndexSearcher";

	private static final String _CLASS_NAME_EXCEPTION_MAPPER =
		"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
			"ExceptionMapper";

	private static final String _CONFIGURATION_PID_ELASTICSEARCH =
		"com.liferay.portal.search.elasticsearch7.configuration." +
			"ElasticsearchConfiguration";

	private static final Pagination _PAGINATION = Pagination.of(1, 1);

}