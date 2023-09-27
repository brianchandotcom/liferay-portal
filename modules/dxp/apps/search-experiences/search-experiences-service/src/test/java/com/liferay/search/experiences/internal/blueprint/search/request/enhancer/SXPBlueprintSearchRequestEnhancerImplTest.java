/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.internal.aggregation.AggregationsImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.highlight.FieldConfigBuilderFactoryImpl;
import com.liferay.portal.search.internal.highlight.HighlightBuilderFactoryImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.internal.rescore.RescoreBuilderFactoryImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.sort.SortsImpl;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.rescore.Rescore;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.search.experiences.blueprint.exception.InvalidElementInstanceException;
import com.liferay.search.experiences.blueprint.exception.InvalidParameterException;
import com.liferay.search.experiences.blueprint.exception.InvalidQueryEntryException;
import com.liferay.search.experiences.blueprint.exception.UnresolvedTemplateVariableException;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.ContextSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.SXPParameterContributor;
import com.liferay.search.experiences.rest.dto.v1_0.AdvancedConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.AggregationConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.HighlightConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.HighlightField;
import com.liferay.search.experiences.rest.dto.v1_0.ParameterConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.SortConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 * @author Gustavo Lima
 * @author Wade Cao
 */
public class SXPBlueprintSearchRequestEnhancerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_sxpBlueprintSearchRequestEnhancerImpl =
			new SXPBlueprintSearchRequestEnhancerImpl();

		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_aggregations",
			new AggregationsImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl,
			"_complexQueryPartBuilderFactory",
			new ComplexQueryPartBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_dtoConverterRegistry",
			_dtoConverterRegistry);
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl,
			"_fieldConfigBuilderFactory", new FieldConfigBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_geoBuilders",
			new GeoBuildersImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_highlightBuilderFactory",
			new HighlightBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_jsonFactory",
			new JSONFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_queries",
			new QueriesImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_rescoreBuilderFactory",
			new RescoreBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_scripts",
			new ScriptsImpl());
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_sorts", new SortsImpl());

		Language language = Mockito.mock(Language.class);

		SXPParameterDataCreator sxpParameterDataCreator =
			new SXPParameterDataCreator();

		ReflectionTestUtil.setFieldValue(
			sxpParameterDataCreator, "_sxpParameterContributors",
			new SXPParameterContributor[] {
				new ContextSXPParameterContributor(null, language)
			});

		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSearchRequestEnhancerImpl, "_sxpParameterDataCreator",
			sxpParameterDataCreator);

		_sxpBlueprintSearchRequestEnhancerImpl.activate();

		Mockito.doReturn(
			"en_US"
		).when(
			language
		).getLanguageId(
			LocaleUtil.US
		);
	}

	@Test
	public void testAggregationConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		Map<String, Aggregation> aggregationsMap =
			searchRequest.getAggregationsMap();

		Assert.assertEquals(
			aggregationsMap.toString(), 10, aggregationsMap.size());

		_assert(sxpBlueprint);
	}

	@Test
	public void testBoostAClause() throws Exception {
		ComplexQueryPart complexQueryPart = _getComplexQueryPart(
			SXPBlueprintUtil.toSXPBlueprint(_read()));

		Assert.assertEquals("should", complexQueryPart.getOccur());
	}

	@Test
	public void testConfigurationEntry() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		SearchRequest searchRequest = _toSearchRequest(
			sxpBlueprint,
			searchRequestBuilder -> searchRequestBuilder.queryString(
				"search that"));

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		Assert.assertEquals("filter", complexQueryPart.getOccur());

		WrapperQuery wrapperQuery = (WrapperQuery)complexQueryPart.getQuery();

		Assert.assertEquals(
			_formatJSON(
				JSONUtil.put(
					"multi_match",
					JSONUtil.put(
						"fuzziness", "2"
					).put(
						"query", "search that"
					))),
			_formatJSON(new String(wrapperQuery.getSource())));
	}

	@Test
	public void testElementInstances() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart1 = complexQueryParts.get(0);

		Assert.assertEquals("must", complexQueryPart1.getOccur());

		TermQuery termQuery1 = (TermQuery)complexQueryPart1.getQuery();

		Assert.assertEquals("version", termQuery1.getField());
		Assert.assertEquals("7.4", termQuery1.getValue());

		ComplexQueryPart complexQueryPart2 = complexQueryParts.get(1);

		Assert.assertEquals("should", complexQueryPart2.getOccur());

		WrapperQuery wrapperQuery1 = (WrapperQuery)complexQueryPart2.getQuery();

		Assert.assertEquals(
			_formatJSON(JSONUtil.put("match", JSONUtil.put("ranking", 5))),
			_formatJSON(new String(wrapperQuery1.getSource())));

		ComplexQueryPart complexQueryPart3 = complexQueryParts.get(2);

		Assert.assertEquals("should", complexQueryPart3.getOccur());

		TermQuery termQuery2 = (TermQuery)complexQueryPart3.getQuery();

		Assert.assertEquals(Float.valueOf(142857), termQuery2.getBoost());
		Assert.assertEquals("entryClassName", termQuery2.getField());
		Assert.assertEquals(
			"com.liferay.journal.model.JournalArticle", termQuery2.getValue());

		List<Sort> sorts = searchRequest.getSorts();

		FieldSort fieldSort1 = (FieldSort)sorts.get(0);

		Assert.assertEquals("department", fieldSort1.getField());
		Assert.assertEquals(SortOrder.ASC, fieldSort1.getSortOrder());

		FieldSort fieldSort2 = (FieldSort)sorts.get(1);

		Assert.assertEquals("jobTitle_ja_JP", fieldSort2.getField());
		Assert.assertEquals(SortOrder.ASC, fieldSort2.getSortOrder());

		FieldSort fieldSort3 = (FieldSort)sorts.get(2);

		Assert.assertEquals("lastName", fieldSort3.getField());
		Assert.assertEquals(SortOrder.DESC, fieldSort3.getSortOrder());

		ComplexQueryPart complexQueryPart4 = complexQueryParts.get(3);

		WrapperQuery wrapperQuery2 = (WrapperQuery)complexQueryPart4.getQuery();

		Assert.assertEquals(
			_formatJSON(
				JSONUtil.put(
					"multi_match",
					JSONUtil.put(
						"fields",
						JSONUtil.putAll(
							"localized_title_en_US^2", "content_fi_FI^1")))),
			_formatJSON(new String(wrapperQuery2.getSource())));

		_assert(sxpBlueprint);
	}

	@Test
	public void testEmptyConfigurations() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		configuration.setAdvancedConfiguration(new AdvancedConfiguration());
		configuration.setAggregationConfiguration(
			new AggregationConfiguration());
		configuration.setGeneralConfiguration(new GeneralConfiguration());
		configuration.setHighlightConfiguration(new HighlightConfiguration());
		configuration.setParameterConfiguration(new ParameterConfiguration());
		configuration.setQueryConfiguration(new QueryConfiguration());
		configuration.setSortConfiguration(new SortConfiguration());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		Assert.assertTrue(MapUtil.isEmpty(searchRequest.getAggregationsMap()));
		Assert.assertTrue(
			ListUtil.isEmpty(searchRequest.getComplexQueryParts()));
		Assert.assertNull(searchRequest.getHighlight());
		Assert.assertTrue(ListUtil.isEmpty(searchRequest.getSorts()));

		_assert(sxpBlueprint);
	}

	@Test
	public void testEmptyElementInstances() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		sxpBlueprint.setElementInstances(
			new ElementInstance[] {
				new ElementInstance(),
				new ElementInstance() {
					{
						sxpElement = new SXPElement();
					}
				},
				new ElementInstance() {
					{
						sxpElement = new SXPElement() {
							{
								elementDefinition = new ElementDefinition();
							}
						};
					}
				}
			});

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		Assert.assertTrue(
			ListUtil.isEmpty(searchRequest.getComplexQueryParts()));

		_assert(sxpBlueprint);
	}

	@Test
	public void testHighlightConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = _createSXPBlueprint();

		Configuration configuration = sxpBlueprint.getConfiguration();

		Integer fragmentOffset = RandomTestUtil.randomInt();
		String[] postTags = {RandomTestUtil.randomString()};
		String[] preTags = {RandomTestUtil.randomString()};

		configuration.setHighlightConfiguration(
			new HighlightConfiguration() {
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

		Highlight highlight = searchRequest.getHighlight();

		List<FieldConfig> fieldConfigs = highlight.getFieldConfigs();

		FieldConfig fieldConfig = fieldConfigs.get(0);

		Assert.assertEquals(fragmentOffset, fieldConfig.getFragmentOffset());
		Assert.assertNull(fieldConfig.getRequireFieldMatch());

		Assert.assertArrayEquals(postTags, highlight.getPostTags());
		Assert.assertArrayEquals(preTags, highlight.getPreTags());

		_assert(sxpBlueprint);
	}

	@Test
	public void testMustBeClause() throws Exception {
		ComplexQueryPart complexQueryPart = _getComplexQueryPart(
			SXPBlueprintUtil.toSXPBlueprint(_read()));

		Assert.assertEquals("must", complexQueryPart.getOccur());
	}

	@Test
	public void testMustNotBeClause() throws Exception {
		ComplexQueryPart complexQueryPart = _getComplexQueryPart(
			SXPBlueprintUtil.toSXPBlueprint(_read()));

		Assert.assertEquals("must_not", complexQueryPart.getOccur());
	}

	@Test
	public void testNullable() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		try {
			_toSearchRequest(sxpBlueprint);

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {

			// TODO Remove the whole key just like client side expansion

			InvalidElementInstanceException invalidElementInstanceException =
				(InvalidElementInstanceException)
					runtimeException.getSuppressed()[0];

			Assert.assertEquals(0, invalidElementInstanceException.getIndex());

			Throwable throwable =
				invalidElementInstanceException.getSuppressed()[0];

			InvalidQueryEntryException invalidQueryEntryException =
				(InvalidQueryEntryException)throwable.getSuppressed()[0];

			Assert.assertEquals(0, invalidQueryEntryException.getIndex());

			UnresolvedTemplateVariableException
				unresolvedTemplateVariableException =
					(UnresolvedTemplateVariableException)
						invalidQueryEntryException.getSuppressed()[0];

			Assert.assertEquals(
				"[configuration.fuzziness, " +
					"configuration.minimum_should_match, configuration.slop]",
				Arrays.toString(
					unresolvedTemplateVariableException.
						getTemplateVariables()));

			_assert(sxpBlueprint);
		}
	}

	@Test
	public void testParameterConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		try {
			_toSearchRequest(sxpBlueprint);

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Throwable throwable = runtimeException.getSuppressed()[0];

			InvalidQueryEntryException invalidQueryEntryException1 =
				(InvalidQueryEntryException)throwable.getSuppressed()[0];

			Assert.assertEquals(0, invalidQueryEntryException1.getIndex());

			UnresolvedTemplateVariableException
				unresolvedTemplateVariableException1 =
					(UnresolvedTemplateVariableException)
						invalidQueryEntryException1.getSuppressed()[0];

			Assert.assertEquals(
				"[version.number]",
				Arrays.toString(
					unresolvedTemplateVariableException1.
						getTemplateVariables()));

			InvalidQueryEntryException invalidQueryEntryException2 =
				(InvalidQueryEntryException)throwable.getSuppressed()[1];

			Assert.assertEquals(1, invalidQueryEntryException2.getIndex());

			InvalidParameterException invalidParameterException =
				(InvalidParameterException)
					invalidQueryEntryException2.getSuppressed()[0];

			Assert.assertEquals(
				"product.code", invalidParameterException.getName());

			InvalidQueryEntryException invalidQueryEntryException3 =
				(InvalidQueryEntryException)throwable.getSuppressed()[2];

			Assert.assertEquals(2, invalidQueryEntryException3.getIndex());

			UnresolvedTemplateVariableException
				unresolvedTemplateVariableException2 =
					(UnresolvedTemplateVariableException)
						invalidQueryEntryException3.getSuppressed()[0];

			Assert.assertEquals(
				"[product.code]",
				Arrays.toString(
					unresolvedTemplateVariableException2.
						getTemplateVariables()));
		}

		SearchRequest searchRequest = _toSearchRequest(
			sxpBlueprint,
			searchRequestBuilder -> searchRequestBuilder.withSearchContext(
				searchContext -> {
					searchContext.setAttribute("product.code", "dxp");
					searchContext.setAttribute("version.number", "7.4");
				}));

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart1 = complexQueryParts.get(0);

		TermQuery termQuery1 = (TermQuery)complexQueryPart1.getQuery();

		Assert.assertEquals("version", termQuery1.getField());
		Assert.assertEquals("7.4", termQuery1.getValue());

		ComplexQueryPart complexQueryPart2 = complexQueryParts.get(1);

		TermQuery termQuery2 = (TermQuery)complexQueryPart2.getQuery();

		Assert.assertEquals("ranking", termQuery2.getField());
		Assert.assertEquals(5, termQuery2.getValue());

		ComplexQueryPart complexQueryPart3 = complexQueryParts.get(2);

		TermQuery termQuery3 = (TermQuery)complexQueryPart3.getQuery();

		Assert.assertEquals("ranking", termQuery3.getField());
		Assert.assertEquals("1", termQuery3.getValue());

		_assert(sxpBlueprint);
	}

	@Test
	public void testParameterValueWithUnitSuffix() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart1 = complexQueryParts.get(0);

		Assert.assertEquals("must", complexQueryPart1.getOccur());

		TermQuery termQuery1 = (TermQuery)complexQueryPart1.getQuery();

		Assert.assertEquals("version", termQuery1.getField());
		Assert.assertEquals("7.4", termQuery1.getValue());

		ComplexQueryPart complexQueryPart2 = complexQueryParts.get(1);

		Assert.assertEquals("should", complexQueryPart2.getOccur());

		WrapperQuery wrapperQuery = (WrapperQuery)complexQueryPart2.getQuery();

		Assert.assertEquals(
			_formatJSON(
				JSONUtil.put(
					"function_score",
					JSONUtil.put(
						"boost", "100"
					).put(
						"gauss",
						JSONUtil.put(
							"modified",
							JSONUtil.put(
								"decay", "0.01"
							).put(
								"offset", "0d"
							).put(
								"origin", "20211209082600"
							).put(
								"scale", "9d"
							))
					))),
			_formatJSON(new String(wrapperQuery.getSource())));
	}

	@Test
	public void testQueryConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		List<ComplexQueryPart> complexQueryParts1 =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart1 = complexQueryParts1.get(0);

		Assert.assertEquals("must", complexQueryPart1.getOccur());

		TermQuery termQuery1 = (TermQuery)complexQueryPart1.getQuery();

		Assert.assertEquals("version", termQuery1.getField());
		Assert.assertEquals("7.4", termQuery1.getValue());
		Assert.assertEquals(Float.valueOf(142857), termQuery1.getBoost());

		ComplexQueryPart complexQueryPart2 = complexQueryParts1.get(1);

		Assert.assertEquals("should", complexQueryPart2.getOccur());

		WrapperQuery wrapperQuery1 = (WrapperQuery)complexQueryPart2.getQuery();

		Assert.assertEquals(
			_formatJSON(JSONUtil.put("match", JSONUtil.put("ranking", 5))),
			_formatJSON(new String(wrapperQuery1.getSource())));

		List<ComplexQueryPart> complexQueryParts2 =
			searchRequest.getPostFilterComplexQueryParts();

		ComplexQueryPart complexQueryPart3 = complexQueryParts2.get(0);

		Assert.assertEquals("must_not", complexQueryPart3.getOccur());

		TermQuery termQuery2 = (TermQuery)complexQueryPart3.getQuery();

		Assert.assertEquals("status", termQuery2.getField());
		Assert.assertEquals(0, termQuery2.getValue());

		List<Rescore> rescores = searchRequest.getRescores();

		Rescore rescore = rescores.get(0);

		WrapperQuery wrapperQuery2 = (WrapperQuery)rescore.getQuery();

		Assert.assertEquals(
			_formatJSON(
				JSONUtil.put("exists", JSONUtil.put("field", "priority"))),
			_formatJSON(new String(wrapperQuery2.getSource())));

		Assert.assertEquals(Float.valueOf(2.3F), rescore.getQueryWeight());
		Assert.assertEquals(
			Float.valueOf(0.7F), rescore.getRescoreQueryWeight());
		Assert.assertEquals(Rescore.ScoreMode.MULTIPLY, rescore.getScoreMode());
		Assert.assertEquals(Integer.valueOf(6), rescore.getWindowSize());

		_assert(sxpBlueprint);
	}

	@Test
	public void testSearchContextAttributes() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		TermQuery termQuery = (TermQuery)complexQueryPart.getQuery();

		Assert.assertEquals("version", termQuery.getField());
		Assert.assertEquals("7.4", termQuery.getValue());

		_assert(sxpBlueprint);
	}

	@Test
	public void testSortConfiguration() throws Exception {
		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(_read());

		SearchRequest searchRequest = _toSearchRequest(sxpBlueprint);

		List<Sort> sorts = searchRequest.getSorts();

		Assert.assertEquals(sorts.toString(), 9, sorts.size());

		_assert(sxpBlueprint);
	}

	@Test
	public void testTextMatch() throws Exception {
		ComplexQueryPart complexQueryPart = _getComplexQueryPart(
			SXPBlueprintUtil.toSXPBlueprint(_read()));

		WrapperQuery wrapperQuery = (WrapperQuery)complexQueryPart.getQuery();

		Assert.assertEquals(
			_formatJSON(
				JSONUtil.put(
					"match",
					JSONUtil.put(
						"localized_title",
						JSONUtil.put(
							"boost", 2
						).put(
							"operator", "or"
						).put(
							"query", ""
						)))),
			_formatJSON(new String(wrapperQuery.getSource())));
	}

	private void _assert(SXPBlueprint sxpBlueprint) throws Exception {
		Assert.assertEquals(
			_formatJSON(sxpBlueprint),
			_formatJSON(
				SXPBlueprintUtil.toSXPBlueprint(sxpBlueprint.toString())));
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

	private ComplexQueryPart _getComplexQueryPart(SXPBlueprint sxpBlueprint)
		throws Exception {

		DTOConverter
			<com.liferay.search.experiences.model.SXPBlueprint, SXPBlueprint>
				dtoConverter = Mockito.mock(DTOConverter.class);

		Mockito.doReturn(
			sxpBlueprint
		).when(
			dtoConverter
		).toDTO(
			Mockito.any(com.liferay.search.experiences.model.SXPBlueprint.class)
		);

		Mockito.doReturn(
			dtoConverter
		).when(
			_dtoConverterRegistry
		).getDTOConverter(
			Mockito.anyString()
		);

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			new SearchRequestBuilderFactoryImpl();

		SearchRequest searchRequest = searchRequestBuilderFactory.builder(
		).withSearchRequestBuilder(
			searchRequestBuilder ->
				_sxpBlueprintSearchRequestEnhancerImpl.enhance(
					searchRequestBuilder,
					Mockito.mock(
						com.liferay.search.experiences.model.SXPBlueprint.
							class))
		).build();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		return complexQueryParts.get(0);
	}

	private String _read() throws Exception {
		Class<?> clazz = getClass();

		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		try (InputStream inputStream = clazz.getResourceAsStream(
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					stackTraceElements[2].getMethodName(), ".json"))) {

			return StringUtil.read(inputStream);
		}
	}

	private SearchRequest _toSearchRequest(
		SXPBlueprint sxpBlueprint,
		Consumer<SearchRequestBuilder>... searchRequestBuilderConsumers) {

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			new SearchRequestBuilderFactoryImpl();

		return searchRequestBuilderFactory.builder(
		).withSearchRequestBuilder(
			searchRequestBuilderConsumers
		).withSearchRequestBuilder(
			searchRequestBuilder ->
				_sxpBlueprintSearchRequestEnhancerImpl.enhance(
					searchRequestBuilder, sxpBlueprint.toString())
		).build();
	}

	private final DTOConverterRegistry _dtoConverterRegistry = Mockito.mock(
		DTOConverterRegistry.class);
	private SXPBlueprintSearchRequestEnhancerImpl
		_sxpBlueprintSearchRequestEnhancerImpl;

}