/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.wiki;

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
public class WikiSimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_wikiSimilarResultsContributor = new WikiSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		ReflectionTestUtil.setFieldValue(
			_wikiSimilarResultsContributor, "_httpHelper", setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () -> StringBundler.concat(
			"http://localhost:8080/wiki/-/wiki/Main/page+1?",
			"_com_liferay_wiki_web_portlet_WikiPortlet_redirect=",
			"http://localhost:8080/wiki/-/wiki/Main/all_pages?",
			"p_r_p_http://www.liferay.com/public-render-parameters",
			"/wiki_nodeName=Main&p_r_p_http://www.liferay.com",
			"/public-render-parameters/wiki_title=page+1");

		_wikiSimilarResultsContributor.detectRoute(
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
		ReflectionTestUtil.setFieldValue(
			_wikiSimilarResultsContributor, "_assetEntryLocalService",
			assetEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_wikiSimilarResultsContributor, "_uidFactory",
			setUpUIDFactory("uid"));
		ReflectionTestUtil.setFieldValue(
			_wikiSimilarResultsContributor, "_wikiNodeLocalService",
			wikiNodeLocalService);
		ReflectionTestUtil.setFieldValue(
			_wikiSimilarResultsContributor, "_wikiPageLocalService",
			wikiPageLocalService);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpAssetEntryLocalServiceFetchEntry(
			setUpAssetEntry("assetEntryClassName"));

		setUpCriteriaHelper("nodeName", RandomTestUtil.randomString());
		setUpCriteriaHelper("title", RandomTestUtil.randomString());

		setUpWikiNodeLocalService(Mockito.mock(WikiNode.class));
		setUpWikiPageLocalService(Mockito.mock(WikiPage.class));

		_wikiSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Criteria criteria = criteriaBuilderImpl.build();

		Assert.assertEquals("assetEntryClassName", criteria.getType());
		Assert.assertEquals("uid", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				StringBundler.concat(
					"http://localhost:8080/wiki/-/wiki/Main/page+1?",
					"_com_liferay_wiki_web_portlet_WikiPortlet_redirect=",
					"http://localhost:8080/wiki/-/wiki/Main/all_pages?",
					"p_r_p_http://www.liferay.com/public-render-parameters",
					"/wiki_nodeName=Main&p_r_p_",
					"http://www.liferay.com/public-render-parameters",
					"/wiki_title=page+1"));

		setUpDestinationHelper(WikiPage.class.getName());
		setUpDestinationHelper(
			setUpAssetRenderer(
				setUpWikiPage("page 2", setUpWikiNode("newMain"))));

		setUpDestinationHelperGetRouteParameter("nodeName", "Main");
		setUpDestinationHelperGetRouteParameter("title", "page 1");

		_wikiSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/wiki/-/wiki/newMain/page 2?",
				"_com_liferay_wiki_web_portlet_WikiPortlet_redirect=",
				"http://localhost:8080/wiki/-/wiki/newMain/all_pages?",
				"p_r_p_http://www.liferay.com/public-render-",
				"parameters/wiki_nodeName=newMain&p_r_p_",
				"http://www.liferay.com/public-render-",
				"parameters/wiki_title=page 2"),
			destinationBuilderImpl.build());
	}

	private WikiSimilarResultsContributor _wikiSimilarResultsContributor;

}