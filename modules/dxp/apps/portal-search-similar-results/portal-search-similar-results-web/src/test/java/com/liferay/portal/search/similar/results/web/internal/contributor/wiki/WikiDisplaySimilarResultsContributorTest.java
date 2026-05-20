/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.wiki;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.similar.results.web.internal.builder.DestinationBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.RouteBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.internal.contributor.BaseSimilarResultsContributorTestCase;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.Criteria;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.CriteriaBuilderImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class WikiDisplaySimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_wikiDisplaySimilarResultsContributor =
			new WikiDisplaySimilarResultsContributor(
				assetEntryLocalService, uidFactory, wikiNodeLocalService,
				wikiPageLocalService);
	}

	@Test
	public void testDetectRoute() {
		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		String portalURL = StringBundler.concat(
			"http://localhost:", PortalUtil.getPortalServerPort(false),
			"/wiki-display?p_p_id=com_liferay_wiki_web_portlet_Wiki",
			"DisplayPortlet_INSTANCE_U7Z1QHX4C38N&");

		RouteHelper routeHelper = () -> StringBundler.concat(
			portalURL, "_com_liferay_wiki_web_portlet_WikiDisplayPortlet_",
			"INSTANCE_U7Z1QHX4C38N_redirect=", portalURL, "nodeName=Main&_com",
			"_liferay_wiki_web_portlet_WikiDisplayPortlet_INSTANCE_",
			"U7Z1QHX4C38N_title=page+1");

		_wikiDisplaySimilarResultsContributor.detectRoute(
			routeBuilderImpl, routeHelper);

		SimilarResultsRoute similarResultsRoute = routeBuilderImpl.build();

		Assert.assertEquals(
			"Main", similarResultsRoute.getRouteParameter("nodeName"));
		Assert.assertEquals(
			"page 1", similarResultsRoute.getRouteParameter("title"));

		Assert.assertFalse(routeBuilderImpl.hasNoAttributes());
	}

	@Test
	public void testResolveCriteria() {
		setUpUIDFactory("uid");

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpAssetEntryLocalServiceFetchEntry(
			setUpAssetEntry("assetEntryClassName"));

		setUpCriteriaHelper("nodeName", RandomTestUtil.randomString());
		setUpCriteriaHelper("title", RandomTestUtil.randomString());

		setUpWikiNodeLocalService(Mockito.mock(WikiNode.class));

		setUpWikiPageLocalService(Mockito.mock(WikiPage.class));

		_wikiDisplaySimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Criteria criteria = criteriaBuilderImpl.build();

		Assert.assertEquals("assetEntryClassName", criteria.getType());
		Assert.assertEquals("uid", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		String portalURL = StringBundler.concat(
			"http://localhost:", PortalUtil.getPortalServerPort(false),
			"/wiki-display?p_p_id=com_liferay_wiki_web_portlet_WikiDisplay",
			"Portlet_INSTANCE_U7Z1QHX4C38N");

		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				StringBundler.concat(
					portalURL, "&_com_liferay_wiki_web_portlet_WikiDisplay",
					"Portlet_INSTANCE_U7Z1QHX4C38N_redirect=", portalURL,
					"&nodeName=Main&_com_liferay_wiki_web_portlet_WikiDisplay",
					"Portlet_INSTANCE_U7Z1QHX4C38N_title=page+1"));

		setUpDestinationHelper(WikiPage.class.getName());

		setUpDestinationHelperGetRouteParameter("nodeName", "Main");
		setUpDestinationHelperGetRouteParameter("title", "page 1");

		setUpDestinationHelper(
			setUpAssetRenderer(
				setUpWikiPage("page 2", setUpWikiNode("newMain"))));

		_wikiDisplaySimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		portalURL = StringBundler.concat(
			"http://localhost:", PortalUtil.getPortalServerPort(false),
			"/wiki-display?p_p_id=com_liferay_wiki_web_portlet_WikiDisplay",
			"Portlet_INSTANCE_U7Z1QHX4C38N");

		Assert.assertEquals(
			StringBundler.concat(
				portalURL, "&_com_liferay_wiki_web_portlet_WikiDisplayPortlet_",
				"INSTANCE_U7Z1QHX4C38N_redirect=", portalURL, "&nodeName=new",
				"Main&_com_liferay_wiki_web_portlet_WikiDisplayPortlet_",
				"INSTANCE_U7Z1QHX4C38N_title=page 2"),
			destinationBuilderImpl.build());
	}

	private WikiDisplaySimilarResultsContributor
		_wikiDisplaySimilarResultsContributor;

}