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

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.internal.aggregation.AggregationsImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.highlight.FieldConfigBuilderFactoryImpl;
import com.liferay.portal.search.internal.highlight.HighlightBuilderFactoryImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.sort.SortsImpl;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.rest.dto.v1_0.AggregationConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.Highlight;
import com.liferay.search.experiences.rest.dto.v1_0.HighlightField;
import com.liferay.search.experiences.rest.dto.v1_0.Parameter;
import com.liferay.search.experiences.rest.dto.v1_0.QueryConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryEntry;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SortConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SXPBlueprintSearchRequestEnhancerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAggregationConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		configuration.setAggregationConfiguration(
			new AggregationConfiguration() {
				{
					aggs = JSONFactoryUtil.createJSONObject(
						_read(
							"SXPBlueprintSearchRequestEnhancerImplTest." +
								"testAggregationConfiguration.json"));
				}
			});

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		Map<String, Aggregation> aggregationsMap =
			searchRequest.getAggregationsMap();

		Assert.assertEquals(
			aggregationsMap.toString(), 10, aggregationsMap.size());

		_assert(sxpBlueprint);
	}

	@Test
	public void testHighlight() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		Integer fragmentOffset = RandomTestUtil.randomInt();
		String[] postTags = {RandomTestUtil.randomString()};
		String[] preTags = {RandomTestUtil.randomString()};

		configuration.setHighlight(
			new Highlight() {
				{
					fields = HashMapBuilder.<String, HighlightField>put(
						RandomTestUtil.randomString(),
						new HighlightField() {
							{
								fragment_offset = fragmentOffset;
							}
						}
					).build();
					post_tags = postTags;
					pre_tags = preTags;
				}
			});

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		com.liferay.portal.search.highlight.Highlight highlight =
			searchRequest.getHighlight();

		List<FieldConfig> fieldConfigs = highlight.getFieldConfigs();

		FieldConfig fieldConfig = fieldConfigs.get(0);

		Assert.assertEquals(fragmentOffset, fieldConfig.getFragmentOffset());
		Assert.assertNull(fieldConfig.getRequireFieldMatch());

		Assert.assertArrayEquals(postTags, highlight.getPostTags());
		Assert.assertArrayEquals(preTags, highlight.getPreTags());

		_assert(sxpBlueprint);
	}

	@Test
	public void testParameters() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		configuration.setParameters(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				() -> {
					Parameter parameter = new Parameter();

					parameter.setDefaultValueString(
						RandomTestUtil.randomString());

					return parameter;
				}
			).build());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		Assert.assertNull(searchRequest.getQueryString());

		_assert(sxpBlueprint);
	}

	@Test
	public void testQueryConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		configuration.setQueryConfiguration(
			new QueryConfiguration() {
				{
					queryEntries = new QueryEntry[] {
						new QueryEntry() {
							{
								clauses = new Clause[] {
									new Clause() {
										{
											occur = "must_not";
											query = JSONUtil.put(
												"term",
												JSONUtil.put("status", 0));
										}
									}
								};
								enabled = true;
							}
						}
					};
				}
			});

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		Assert.assertEquals("must_not", complexQueryPart.getOccur());

		WrapperQuery wrapperQuery = (WrapperQuery)complexQueryPart.getQuery();

		Assert.assertEquals(
			_formatJSON(JSONUtil.put("term", JSONUtil.put("status", 0))),
			_formatJSON(new String(wrapperQuery.getSource())));

		_assert(sxpBlueprint);
	}

	@Test
	public void testSortConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		configuration.setSortConfiguration(
			new SortConfiguration() {
				{
					sorts = JSONFactoryUtil.createJSONArray(
						_read(
							"SXPBlueprintSearchRequestEnhancerImplTest." +
								"testSortConfiguration.json"));
				}
			});

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		List<Sort> sorts = searchRequest.getSorts();

		Assert.assertEquals(sorts.toString(), 9, sorts.size());

		_assert(sxpBlueprint);
	}

	private void _assert(SXPBlueprint sxpBlueprint) throws Exception {
		Assert.assertEquals(
			_formatJSON(sxpBlueprint),
			_formatJSON(
				SXPBlueprintUtil.toSXPBlueprint(String.valueOf(sxpBlueprint))));
	}

	private SXPBlueprint _createSXPBlueprint() {
		return new SXPBlueprint() {
			{
				configuration = new Configuration();
			}
		};
	}

	private String _formatJSON(Object object) throws Exception {
		return JSONUtil.toString(
			JSONFactoryUtil.createJSONObject(String.valueOf(object)));
	}

	private String _read(String resourceName) {
		Class<?> clazz = getClass();

		try (InputStream inputStream = clazz.getResourceAsStream(
				resourceName)) {

			return StringUtil.read(inputStream);
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to load resource: " + resourceName, exception);
		}
	}

	private SearchRequest _toSearchRequest(SXPBlueprint sxpBlueprint) {
		SearchRequestBuilderFactory searchRequestBuilderFactory =
			new SearchRequestBuilderFactoryImpl();

		SXPBlueprintSearchRequestEnhancerImpl
			sxpBlueprintSearchRequestEnhancerImpl =
				new SXPBlueprintSearchRequestEnhancerImpl();

		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_aggregations",
			new AggregationsImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl,
			"_complexQueryPartBuilderFactory",
			new ComplexQueryPartBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_fieldConfigBuilderFactory",
			new FieldConfigBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_geoBuilders",
			new GeoBuildersImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_highlightBuilderFactory",
			new HighlightBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_queries",
			new QueriesImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_scripts",
			new ScriptsImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_sorts", new SortsImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancerImpl, "_sxpParameterDataCreator",
			new SXPParameterDataCreator());

		sxpBlueprintSearchRequestEnhancerImpl.activate();

		return searchRequestBuilderFactory.builder(
		).withSearchRequestBuilder(
			searchRequestBuilder ->
				sxpBlueprintSearchRequestEnhancerImpl.enhance(
					searchRequestBuilder, sxpBlueprint.toString())
		).build();
	}

}