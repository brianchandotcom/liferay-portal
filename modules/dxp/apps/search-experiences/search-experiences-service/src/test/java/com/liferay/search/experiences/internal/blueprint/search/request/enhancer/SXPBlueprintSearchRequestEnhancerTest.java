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
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.internal.aggregation.AggregationsImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.highlight.FieldConfigBuilderFactoryImpl;
import com.liferay.portal.search.internal.highlight.HighlightBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.HighlightSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.rest.dto.v1_0.Aggregation;
import com.liferay.search.experiences.rest.dto.v1_0.Avg;
import com.liferay.search.experiences.rest.dto.v1_0.Cardinality;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.Highlight;
import com.liferay.search.experiences.rest.dto.v1_0.HighlightField;
import com.liferay.search.experiences.rest.dto.v1_0.Parameter;
import com.liferay.search.experiences.rest.dto.v1_0.Query;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SXPBlueprintSearchRequestEnhancerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAggregations() {
		SXPBlueprintSearchRequestEnhancer sxpBlueprintSearchRequestEnhancer =
			_createSXPBlueprintSearchRequestEnhancer();

		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		configuration.setAggregations(
			HashMapBuilder.put(
				RandomTestUtil.randomString(),
				() -> {
					Aggregation aggregation = new Aggregation();

					Avg avg = new Avg();

					avg.setField(RandomTestUtil.randomString());

					aggregation.setAvg(avg);

					return aggregation;
				}
			).put(
				RandomTestUtil.randomString(),
				() -> {
					Aggregation aggregation = new Aggregation();

					Cardinality cardinality = new Cardinality();

					cardinality.setField(RandomTestUtil.randomString());
					cardinality.setPrecision_threshold(
						RandomTestUtil.randomInt());

					aggregation.setCardinality(cardinality);

					return aggregation;
				}
			).build());

		sxpBlueprintSearchRequestEnhancer.enhance(
			_searchRequestBuilder, sxpBlueprint);

		SearchRequest searchRequest = _searchRequestBuilder.build();

		Map<String, com.liferay.portal.search.aggregation.Aggregation>
			aggregationsMap = searchRequest.getAggregationsMap();

		Assert.assertEquals(
			aggregationsMap.toString(), 2, aggregationsMap.size());
	}

	@Test
	public void testHighlight() {
		SXPBlueprintSearchRequestEnhancer sxpBlueprintSearchRequestEnhancer =
			_createSXPBlueprintSearchRequestEnhancer();

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

		sxpBlueprintSearchRequestEnhancer.enhance(
			_searchRequestBuilder, sxpBlueprint);

		SearchRequest searchRequest = _searchRequestBuilder.build();

		com.liferay.portal.search.highlight.Highlight highlight =
			searchRequest.getHighlight();

		Assert.assertArrayEquals(postTags, highlight.getPostTags());
		Assert.assertArrayEquals(preTags, highlight.getPreTags());

		List<FieldConfig> fieldConfigs = highlight.getFieldConfigs();

		FieldConfig fieldConfig = fieldConfigs.get(0);

		Assert.assertEquals(fragmentOffset, fieldConfig.getFragmentOffset());
		Assert.assertNull(fieldConfig.getRequireFieldMatch());
	}

	@Test
	public void testParameters() {
		SXPBlueprintSearchRequestEnhancer sxpBlueprintSearchRequestEnhancer =
			_createSXPBlueprintSearchRequestEnhancer();

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

		sxpBlueprintSearchRequestEnhancer.enhance(
			_searchRequestBuilder, sxpBlueprint);

		SearchRequest searchRequest = _searchRequestBuilder.build();

		Assert.assertNull(searchRequest.getQueryString());
	}

	@Test
	public void testQueries() {
		SXPBlueprintSearchRequestEnhancer sxpBlueprintSearchRequestEnhancer =
			_createSXPBlueprintSearchRequestEnhancer();

		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		configuration.setQueries(
			new Query[] {
				new Query() {
					{
						clauses = new Clause[] {
							new Clause() {
								{
									occur = "must_not";
									queryJSON = JSONUtil.put(
										"term", JSONUtil.put("status", 0)
									).toString();
								}
							}
						};
						enabled = true;
					}
				}
			});

		sxpBlueprintSearchRequestEnhancer.enhance(
			_searchRequestBuilder, sxpBlueprint);

		SearchRequest searchRequest = _searchRequestBuilder.build();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		Assert.assertEquals("must_not", complexQueryPart.getOccur());

		WrapperQuery wrapperQuery = (WrapperQuery)complexQueryPart.getQuery();

		Assert.assertEquals(
			JSONUtil.put(
				"term", JSONUtil.put("status", 0)
			).toString(),
			new String(wrapperQuery.getSource()));
	}

	private SXPBlueprint _createSXPBlueprint() {
		return new SXPBlueprint() {
			{
				configuration = new Configuration();
			}
		};
	}

	private SXPBlueprintSearchRequestEnhancer
		_createSXPBlueprintSearchRequestEnhancer() {

		SXPBlueprintSearchRequestEnhancer sxpBlueprintSearchRequestEnhancer =
			new SXPBlueprintSearchRequestEnhancer();

		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancer, "_aggregations",
			new AggregationsImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancer,
			"_complexQueryPartBuilderFactory",
			new ComplexQueryPartBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancer, "_queries", new QueriesImpl());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancer, "_sxpParameterDataCreator",
			new SXPParameterDataCreator());
		ReflectionTestUtil.setFieldValue(
			sxpBlueprintSearchRequestEnhancer,
			"_sxpSearchRequestBodyContributors",
			Arrays.asList(
				new HighlightSXPSearchRequestBodyContributor(
					new FieldConfigBuilderFactoryImpl(),
					new HighlightBuilderFactoryImpl(),
					JSONFactoryUtil.getJSONFactory())));

		return sxpBlueprintSearchRequestEnhancer;
	}

	private final SearchRequestBuilder _searchRequestBuilder =
		new SearchRequestBuilderImpl(new SearchRequestBuilderFactoryImpl());

}