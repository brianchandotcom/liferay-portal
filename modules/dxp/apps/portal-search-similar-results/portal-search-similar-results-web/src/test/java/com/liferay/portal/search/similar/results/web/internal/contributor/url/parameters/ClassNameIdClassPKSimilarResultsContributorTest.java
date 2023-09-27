/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters;

import com.liferay.asset.kernel.model.AssetEntry;
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

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class ClassNameIdClassPKSimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_classNameIdClassPKSimilarResultsContributor =
			new ClassNameIdClassPKSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		ReflectionTestUtil.setFieldValue(
			_classNameIdClassPKSimilarResultsContributor, "_httpHelper",
			setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () ->
			"http://localhost:8080?classUuid=uid&" +
				"classNameId=332211&classPK=112233";

		_classNameIdClassPKSimilarResultsContributor.detectRoute(
			routeBuilderImpl, routeHelper);

		SimilarResultsRoute similarResultsRoute = routeBuilderImpl.build();

		Assert.assertEquals(
			112233L, similarResultsRoute.getRouteParameter("classPK"));
		Assert.assertEquals(
			332211L, similarResultsRoute.getRouteParameter("classNameId"));

		Assert.assertFalse(routeBuilderImpl.hasNoAttributes());
	}

	@Test
	public void testResolveCriteria() {
		ReflectionTestUtil.setFieldValue(
			_classNameIdClassPKSimilarResultsContributor,
			"_assetEntryLocalService", assetEntryLocalService);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpAssetEntryLocalServiceFetchEntry(
			setUpAssetEntry("assetEntryClassName"));

		setUpCriteriaHelper("classNameId", RandomTestUtil.randomLong());
		setUpCriteriaHelper("classPK", 42790L);

		_classNameIdClassPKSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Criteria criteria = criteriaBuilderImpl.build();

		Assert.assertEquals(
			"assetEntryClassName_PORTLET_42790", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.doReturn(
			12345L
		).when(
			assetEntry
		).getClassNameId();

		Mockito.doReturn(
			54321L
		).when(
			assetEntry
		).getClassPK();

		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				"http://localhost:8080?classUuid=uid&" +
					"classNameId=332211&classPK=112233");

		setUpDestinationHelper(assetEntry);

		setUpPortalUtil();

		_classNameIdClassPKSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			"http://localhost:8080?classUuid=uid&" +
				"classNameId=12345&classPK=54321",
			destinationBuilderImpl.build());
	}

	private ClassNameIdClassPKSimilarResultsContributor
		_classNameIdClassPKSimilarResultsContributor;

}