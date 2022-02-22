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

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.internal.blueprint.test.SXPBlueprintSearchResultTestUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Gustavo Lima
 */
@RunWith(Arquillian.class)
public class SXPBlueprintSearchRequestEnhancerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		_group = GroupTestUtil.addGroup();

		_sxpBlueprint = _addSXPBlueprint();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testBoostAClause() throws Exception {
		SXPElement sxpElement = _addSXPElement();

		_addCustomSXPElementToSXPBlueprint(sxpElement);

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder();

		_enhance(searchRequestBuilder);

		SearchRequest searchRequest = searchRequestBuilder.build();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		Assert.assertEquals("should", complexQueryPart.getOccur());
	}

	@Test
	public void testMustBeClause() throws Exception {
		SXPElement sxpElement = _addSXPElement();

		_addCustomSXPElementToSXPBlueprint(sxpElement);

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder();

		_enhance(searchRequestBuilder);

		SearchRequest searchRequest = searchRequestBuilder.build();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		Assert.assertEquals("must", complexQueryPart.getOccur());
	}

	@Test
	public void testMustNotBeClause() throws Exception {
		SXPElement sxpElement = _addSXPElement();

		_addCustomSXPElementToSXPBlueprint(sxpElement);

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder();

		_enhance(searchRequestBuilder);

		SearchRequest searchRequest = searchRequestBuilder.build();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

		Assert.assertEquals("must_not", complexQueryPart.getOccur());
	}

	@Test
	public void testTextMatch() throws Exception {
		SXPElement sxpElement = _addSXPElement();

		_addCustomSXPElementToSXPBlueprint(sxpElement);

		String keywords = "Los Angeles";

		SearchRequestBuilder searchRequestBuilder =
			_getSearchRequestBuilder().queryString(keywords);

		_enhance(searchRequestBuilder);

		SearchRequest searchRequest = searchRequestBuilder.build();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		ComplexQueryPart complexQueryPart = complexQueryParts.get(0);

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
							"query", keywords
						)))),
			_formatJSON(new String(wrapperQuery.getSource())));
	}

	@Rule
	public TestName testName = new TestName();

	private void _addCustomSXPElementToSXPBlueprint(SXPElement sxpElement)
		throws Exception {

		List<SXPElement> sxpElementList = new ArrayList<>();
		String[] sxpElementNames = {"Custom Element"};
		Object[] configurationValuesArray = new Object[1];

		sxpElementList.add(sxpElement);

		String elementInstancesJSON =
			SXPBlueprintSearchResultTestUtil.getElementInstancesJSON(
				configurationValuesArray, sxpElementNames, sxpElementList);

		_sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);

		_sxpBlueprintLocalService.updateSXPBlueprint(
			_sxpBlueprint.getUserId(), _sxpBlueprint.getSXPBlueprintId(),
			_sxpBlueprint.getConfigurationJSON(),
			_sxpBlueprint.getDescriptionMap(),
			_sxpBlueprint.getElementInstancesJSON(),
			_sxpBlueprint.getSchemaVersion(), _sxpBlueprint.getTitleMap(),
			_serviceContext);
	}

	private SXPBlueprint _addSXPBlueprint() throws Exception {
		return _sxpBlueprintLocalService.addSXPBlueprint(
			TestPropsValues.getUserId(),
			SXPBlueprintSearchResultTestUtil.JSON_QUERY_CONFIGURATION,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			"", "",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());
	}

	private SXPElement _addSXPElement() throws Exception {
		return _sxpElementLocalService.addSXPElement(
			TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, "Custom Element"),
			StringUtil.read(
				getClass(),
				StringBundler.concat(
					"dependencies/", getClass().getSimpleName(),
					StringPool.PERIOD, testName.getMethodName(), ".json")),
			false, "",
			Collections.singletonMap(LocaleUtil.US, "Custom Element"), 1,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _enhance(SearchRequestBuilder searchRequestBuilder) {
		_sxpBlueprintSearchRequestEnhancer.enhance(
			searchRequestBuilder, _sxpBlueprint);
	}

	private String _formatJSON(Object object) throws Exception {
		return JSONUtil.toString(
			JSONFactoryUtil.createJSONObject(String.valueOf(object)));
	}

	private SearchRequestBuilder _getSearchRequestBuilder() throws Exception {
		return _searchRequestBuilderFactory.builder(
		).companyId(
			TestPropsValues.getCompanyId()
		);
	}

	@Inject
	private static SXPElementLocalService _sxpElementLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}