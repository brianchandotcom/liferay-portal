/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.asset.publisher;

import com.liferay.petra.string.StringBundler;
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
import com.liferay.wiki.model.WikiPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class AssetPublisherSimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_assetPublisherSimilarResultsContributor =
			new AssetPublisherSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		ReflectionTestUtil.setFieldValue(
			_assetPublisherSimilarResultsContributor, "_httpHelper",
			setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () -> StringBundler.concat(
			"http://localhost:8080/web/guest/ap-page/-/asset_publisher",
			"/BNPTUvWUBXIr/content/id",
			"?_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_assetEntryId=43152",
			"&_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_",
			"INSTANCE_BNPTUvWUBXIr_redirect=");

		_assetPublisherSimilarResultsContributor.detectRoute(
			routeBuilderImpl, routeHelper);

		SimilarResultsRoute similarResultsRoute = routeBuilderImpl.build();

		Assert.assertEquals(
			"content", similarResultsRoute.getRouteParameter("type"));
		Assert.assertEquals(
			43152L, similarResultsRoute.getRouteParameter("entryId"));

		Assert.assertFalse(routeBuilderImpl.hasNoAttributes());
	}

	@Test
	public void testResolveCriteria() {
		ReflectionTestUtil.setFieldValue(
			_assetPublisherSimilarResultsContributor, "_assetEntryLocalService",
			assetEntryLocalService);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpAssetEntryLocalServiceFetchAssetEntry(
			setUpAssetEntry("assetEntryClassName"));

		setUpCriteriaHelper("entryId", RandomTestUtil.randomLong());

		_assetPublisherSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Criteria criteria = criteriaBuilderImpl.build();

		Assert.assertEquals("assetEntryClassName", criteria.getType());
		Assert.assertEquals("assetEntryClassName_PORTLET_0", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				StringBundler.concat(
					"http://localhost:8080/web/guest/ap-page/-/asset_publisher",
					"/BNPTUvWUBXIr/content/id",
					"?_com_liferay_asset_publisher_web_portlet_",
					"AssetPublisherPortlet_INSTANCE_BNPTUvWUBXIr_assetEntryId",
					"=12345&_com_liferay_asset_publisher_web_portlet_",
					"AssetPublisherPortlet_INSTANCE_BNPTUvWUBXIr_redirect="));

		setUpDestinationHelper(WikiPage.class.getName());
		setUpDestinationHelper(setUpAssetEntry(54321L));

		setUpDestinationHelperGetRouteParameter(12345L, "entryId");
		setUpDestinationHelperGetRouteParameter("type", "content");

		_assetPublisherSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/web/guest/ap-page/-/asset_publisher",
				"/BNPTUvWUBXIr/wiki/id?_com_liferay_asset_publisher_web",
				"_portlet_AssetPublisherPortlet_INSTANCE_BNPTUvWUBXIr",
				"_assetEntryId=54321&_com_liferay_asset_publisher_web_portlet_",
				"AssetPublisherPortlet_INSTANCE_BNPTUvWUBXIr_redirect="),
			destinationBuilderImpl.build());
	}

	private AssetPublisherSimilarResultsContributor
		_assetPublisherSimilarResultsContributor;

}