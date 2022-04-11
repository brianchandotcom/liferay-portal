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

package com.liferay.portal.search.similar.results.web.internal.contributor.wiki;

import com.liferay.petra.string.StringBundler;
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

import java.util.Optional;

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
		super.setUp();

		_wikiDisplaySimilarResultsContributor =
			new WikiDisplaySimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		_wikiDisplaySimilarResultsContributor.setHttpHelper(setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () -> StringBundler.concat(
			"http://localhost:8080/wiki-display?",
			"p_p_id=com_liferay_wiki_web_portlet_",
			"WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N&",
			"_com_liferay_wiki_web_portlet_WikiDisplayPortlet_",
			"INSTANCE_U7Z1QHX4C38N_redirect=http://localhost:8080",
			"/wiki-display?p_p_id=com_liferay_wiki_web_portlet_",
			"WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N&",
			"nodeName=Main&_com_liferay_wiki_web_portlet_",
			"WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N_title=page+1");

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
		_wikiDisplaySimilarResultsContributor.setAssetEntryLocalService(
			assetEntryLocalService);
		_wikiDisplaySimilarResultsContributor.setUIDFactory(
			setUpUIDFactory("uid"));
		_wikiDisplaySimilarResultsContributor.setWikiNodeLocalService(
			wikiNodeLocalService);
		_wikiDisplaySimilarResultsContributor.setWikiPageLocalService(
			wikiPageLocalService);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpAssetEntryLocalServiceFetchEntry(
			setUpAssetEntry("assetEntryClassName"));

		setUpCriteriaHelper("nodeName", RandomTestUtil.randomString());
		setUpCriteriaHelper("title", RandomTestUtil.randomString());

		setUpWikiNodeLocalService(Mockito.mock(WikiNode.class));

		setUpWikiPageLocalService(Mockito.mock(WikiPage.class));

		_wikiDisplaySimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Optional<Criteria> criteraOptional = criteriaBuilderImpl.build();

		Criteria criteria = criteraOptional.get();

		Assert.assertEquals(
			Optional.of("assetEntryClassName"), criteria.getTypeOptional());
		Assert.assertEquals("uid", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				StringBundler.concat(
					"http://localhost:8080/wiki-display?",
					"p_p_id=com_liferay_wiki_web_portlet_",
					"WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N&",
					"_com_liferay_wiki_web_portlet_WikiDisplayPortlet",
					"_INSTANCE_U7Z1QHX4C38N_redirect",
					"=http://localhost:8080/wiki-display?",
					"p_p_id=com_liferay_wiki_web_portlet_",
					"WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N&",
					"nodeName=Main&_com_liferay_wiki_web_portlet",
					"_WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N_title=page+1"));

		setUpDestinationHelper(WikiPage.class.getName());

		setUpDestinationHelperGetRouteParameter("nodeName", "Main");
		setUpDestinationHelperGetRouteParameter("title", "page 1");

		setUpDestinationHelper(
			setUpAssetRenderer(
				setUpWikiPage("page 2", setUpWikiNode("newMain"))));

		_wikiDisplaySimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/wiki-display?",
				"p_p_id=com_liferay_wiki_web_portlet_",
				"WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N&",
				"_com_liferay_wiki_web_portlet_WikiDisplayPortlet",
				"_INSTANCE_U7Z1QHX4C38N_redirect",
				"=http://localhost:8080/wiki-display?",
				"p_p_id=com_liferay_wiki_web_portlet_",
				"WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N&",
				"nodeName=newMain&_com_liferay_wiki_web_portlet",
				"_WikiDisplayPortlet_INSTANCE_U7Z1QHX4C38N_title=page 2"),
			destinationBuilderImpl.build());
	}

	private WikiDisplaySimilarResultsContributor
		_wikiDisplaySimilarResultsContributor;

}