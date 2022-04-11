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
public class ClassNameClassPKSimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_classNameClassPKSimilarResultsContributor =
			new ClassNameClassPKSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		_classNameClassPKSimilarResultsContributor.setHttpHelper(
			setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () ->
			"http://localhost:8080?classUuid=uid&" +
				"className=AssetClassName&classPK=112233";

		_classNameClassPKSimilarResultsContributor.detectRoute(
			routeBuilderImpl, routeHelper);

		SimilarResultsRoute similarResultsRoute = routeBuilderImpl.build();

		Assert.assertEquals(
			112233L, similarResultsRoute.getRouteParameter("classPK"));
		Assert.assertEquals(
			"AssetClassName",
			similarResultsRoute.getRouteParameter("className"));

		Assert.assertFalse(routeBuilderImpl.hasNoAttributes());
	}

	@Test
	public void testResolveCriteria() {
		setUpCriteriaHelper("className", "assetEntryClassName");
		setUpCriteriaHelper("classPK", 42790L);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		_classNameClassPKSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Optional<Criteria> criteraOptional = criteriaBuilderImpl.build();

		Criteria criteria = criteraOptional.get();

		Assert.assertEquals(
			"assetEntryClassName_PORTLET_42790", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				"http://localhost:8080?classUuid=uid&" +
					"className=AssetClassName&classPK=112233");

		setUpDestinationHelper(setUpAssetEntry("newAssetClassName", 54321L));

		setUpPortalUtil();

		_classNameClassPKSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			"http://localhost:8080?classUuid=uid&" +
				"className=newAssetClassName&classPK=54321",
			destinationBuilderImpl.build());
	}

	private ClassNameClassPKSimilarResultsContributor
		_classNameClassPKSimilarResultsContributor;

}