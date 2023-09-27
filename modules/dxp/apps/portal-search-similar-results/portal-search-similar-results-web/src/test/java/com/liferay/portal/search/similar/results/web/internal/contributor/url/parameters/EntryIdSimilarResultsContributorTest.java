/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.similar.results.web.internal.builder.DestinationBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.RouteBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.internal.contributor.BaseSimilarResultsContributorTestCase;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.Criteria;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.CriteriaBuilderImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
		_entryIdSimilarResultsContributor =
			new EntryIdSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		ReflectionTestUtil.setFieldValue(
			_entryIdSimilarResultsContributor, "_httpHelper",
			setUpHttpHelper());

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
		ReflectionTestUtil.setFieldValue(
			_entryIdSimilarResultsContributor, "_assetEntryLocalService",
			assetEntryLocalService);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpAssetEntryLocalServiceFetchAssetEntry(
			setUpAssetEntry("assetEntryClassName", 42790L));

		setUpCriteriaHelper("entryId", RandomTestUtil.randomLong());

		_entryIdSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Criteria criteria = criteriaBuilderImpl.build();

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