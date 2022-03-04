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
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
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

	@Test
	public void testBoostAClause() throws Exception {
		_test(
			null,
			() -> Assert.assertEquals("should", _complexQueryPart.getOccur()));
	}

	@Test
	public void testMustBeClause() throws Exception {
		_test(
			null,
			() -> Assert.assertEquals("must", _complexQueryPart.getOccur()));
	}

	@Test
	public void testMustNotBeClause() throws Exception {
		_test(
			null,
			() -> Assert.assertEquals(
				"must_not", _complexQueryPart.getOccur()));
	}

	@Test
	public void testTextMatch() throws Exception {
		_test(
			"Los Angeles",
			() -> {
				WrapperQuery wrapperQuery =
					(WrapperQuery)_complexQueryPart.getQuery();

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
									"query", "Los Angeles"
								)))),
					_formatJSON(new String(wrapperQuery.getSource())));
			});
	}

	@Rule
	public TestName testName = new TestName();

	private String _formatJSON(Object object) throws Exception {
		return JSONUtil.toString(
			JSONFactoryUtil.createJSONObject(String.valueOf(object)));
	}

	private void _test(
			String keywords, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		_sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			TestPropsValues.getUserId(),
			SXPBlueprintSearchResultTestUtil.JSON_QUERY_CONFIGURATION,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			SXPBlueprintSearchResultTestUtil.getElementInstancesJSON(
				null, new String[] {"Custom Element"},
				Arrays.asList(
					_sxpElementLocalService.addSXPElement(
						TestPropsValues.getUserId(),
						Collections.singletonMap(
							LocaleUtil.US, "Custom Element"),
						StringUtil.read(
							getClass(),
							StringBundler.concat(
								"dependencies/", getClass().getSimpleName(),
								StringPool.PERIOD, testName.getMethodName(),
								".json")),
						false, "",
						Collections.singletonMap(
							LocaleUtil.US, "Custom Element"),
						1, ServiceContextTestUtil.getServiceContext()))),
			"",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				TestPropsValues.getCompanyId()
			);

		if (!Validator.isBlank(keywords)) {
			searchRequestBuilder.queryString(keywords);
		}

		_sxpBlueprintSearchRequestEnhancer.enhance(
			searchRequestBuilder, _sxpBlueprint);

		SearchRequest searchRequest = searchRequestBuilder.build();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		_complexQueryPart = complexQueryParts.get(0);

		unsafeRunnable.run();
	}

	@Inject
	private static SXPElementLocalService _sxpElementLocalService;

	private ComplexQueryPart _complexQueryPart;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}