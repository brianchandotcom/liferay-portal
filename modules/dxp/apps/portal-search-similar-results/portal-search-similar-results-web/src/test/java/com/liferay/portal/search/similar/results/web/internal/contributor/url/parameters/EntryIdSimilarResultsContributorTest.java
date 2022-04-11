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

package com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.similar.results.web.internal.builder.DestinationBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.RouteBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.internal.contributor.BaseSimilarResultsContributorTestCase;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.Criteria;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.CriteriaBuilderImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class EntryIdSimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_entryIdSimilarResultsContributor =
			new EntryIdSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		_entryIdSimilarResultsContributor.setHttpHelper(setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () -> "http://localhost:8080?entryId=1234";

		_entryIdSimilarResultsContributor.detectRoute(
			routeBuilderImpl, routeHelper);

		SimilarResultsRoute similarResultsRoute = routeBuilderImpl.build();

		Assert.assertEquals(
			1234L, similarResultsRoute.getRouteParameter("entryId"));

		Assert.assertFalse(routeBuilderImpl.hasNoAttributes());
	}

	@Test
	public void testResolveCriteria() {
		_entryIdSimilarResultsContributor.setAssetEntryLocalService(
			assetEntryLocalService);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpAssetEntryLocalServiceFetchAssetEntry(
			setUpAssetEntry("assetEntryClassName", 42790L));

		setUpCriteriaHelper("entryId", RandomTestUtil.randomLong());

		_entryIdSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Optional<Criteria> criteraOptional = criteriaBuilderImpl.build();

		Criteria criteria = criteraOptional.get();

		Assert.assertEquals(
			"assetEntryClassName_PORTLET_42790", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl("http://localhost:8080?entryId=1234");

		setUpDestinationHelper(setUpAssetEntry(4321L));

		setUpPortalUtil();

		_entryIdSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			"http://localhost:8080?entryId=4321",
			destinationBuilderImpl.build());
	}

	private EntryIdSimilarResultsContributor _entryIdSimilarResultsContributor;

}