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

package com.liferay.search.experiences.internal.blueprint.search.spi.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryEntry;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.io.Serializable;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SXPBlueprintSearchRequestContributorFederatedTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testFederatedSearch() throws Exception {
		SearchRequestBuilder searchRequestBuilder1 = _getSearchRequestBuilder();
		SearchRequestBuilder searchRequestBuilder2 = _getSearchRequestBuilder();
		SearchRequestBuilder searchRequestBuilder3 = _getSearchRequestBuilder();

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder1.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.experiences.blueprint.json",
					_getBlueprintJSON("R2D2"))
			).addFederatedSearchRequest(
				searchRequestBuilder2.federatedSearchKey(
					"jedi"
				).withSearchContext(
					searchContext -> searchContext.setAttribute(
						"search.experiences.blueprint.json",
						_getBlueprintJSON("Yoda"))
				).build()
			).addFederatedSearchRequest(
				searchRequestBuilder3.federatedSearchKey(
					"sith"
				).withSearchContext(
					searchContext -> searchContext.setAttribute(
						"search.experiences.blueprint.json",
						_getBlueprintJSON("Vader"))
				).build()
			).build());

		_assertRequestStringContains(searchResponse, "R2D2");
		_assertRequestStringContains(
			searchResponse.getFederatedSearchResponse("jedi"), "Yoda");
		_assertRequestStringContains(
			searchResponse.getFederatedSearchResponse("sith"), "Vader");
	}

	private void _assertRequestStringContains(
		SearchResponse searchResponse, String name) {

		Assert.assertThat(
			searchResponse.getRequestString(),
			CoreMatchers.containsString(name));
	}

	private Serializable _getBlueprintJSON(String string) {
		return String.valueOf(
			new SXPBlueprint() {
				{
					configuration = new Configuration() {
						{
							queryConfiguration = new QueryConfiguration() {
								{
									queryEntries = new QueryEntry[] {
										new QueryEntry() {
											{
												clauses = new Clause[] {
													new Clause() {
														{
															field = "friend";
															type = "match";
															value = string;
														}
													}
												};
											}
										}
									};
								}
							};
						}
					};
				}
			});
	}

	private SearchRequestBuilder _getSearchRequestBuilder() throws Exception {
		return _searchRequestBuilderFactory.builder(
		).companyId(
			TestPropsValues.getCompanyId()
		).emptySearchEnabled(
			true
		);
	}

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}