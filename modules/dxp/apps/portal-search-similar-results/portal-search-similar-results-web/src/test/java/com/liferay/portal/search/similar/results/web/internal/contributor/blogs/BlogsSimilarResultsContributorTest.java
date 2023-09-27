/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.blogs;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class BlogsSimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_blogsSimilarResultsContributor = new BlogsSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		ReflectionTestUtil.setFieldValue(
			_blogsSimilarResultsContributor, "_httpHelper", setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () -> StringBundler.concat(
			"http://localhost:8080/blog-page/-/blogs/blog-1?",
			"_com_liferay_blogs_web_portlet_BlogsPortlet_redirect=",
			"http://localhost:8080/blog-page?",
			"p_p_id=com_liferay_blogs_web_portlet_BlogsPortlet&",
			"p_p_lifecycle=0&p_p_state=normal&p_p_mode=view");

		_blogsSimilarResultsContributor.detectRoute(
			routeBuilderImpl, routeHelper);

		SimilarResultsRoute similarResultsRoute = routeBuilderImpl.build();

		Assert.assertEquals(
			"blog-1", similarResultsRoute.getRouteParameter("urlTitle"));

		Assert.assertFalse(routeBuilderImpl.hasNoAttributes());
	}

	@Test
	public void testResolveCriteria() {
		Mockito.doReturn(
			Mockito.mock(BlogsEntry.class)
		).when(
			_blogsEntryLocalService
		).fetchEntry(
			Mockito.anyLong(), Mockito.anyString()
		);

		ReflectionTestUtil.setFieldValue(
			_blogsSimilarResultsContributor, "_blogsEntryLocalService",
			_blogsEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_blogsSimilarResultsContributor, "_uidFactory",
			setUpUIDFactory("uid"));

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		setUpCriteriaHelper(RandomTestUtil.randomLong());
		setUpCriteriaHelper("urlTitle", RandomTestUtil.randomString());

		_blogsSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Criteria criteria = criteriaBuilderImpl.build();

		Assert.assertEquals("uid", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		_setUpDestinationHelper(1234L);

		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			1234L
		).when(
			assetRenderer
		).getGroupId();

		Mockito.doReturn(
			"blog-2"
		).when(
			assetRenderer
		).getUrlTitle();

		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				StringBundler.concat(
					"http://localhost:8080/blog-page/-/blogs/blog-1?",
					"_com_liferay_blogs_web_portlet_BlogsPortlet_redirect=",
					"http://localhost:8080/blog-page?",
					"p_p_id=com_liferay_blogs_web_portlet_BlogsPortlet&",
					"p_p_lifecycle=0&p_p_state=normal&p_p_mode=view"));

		setUpDestinationHelper(assetRenderer);

		setUpDestinationHelperGetRouteParameter("urlTitle", "blog-1");

		_blogsSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/blog-page/-/blogs/blog-2?",
				"_com_liferay_blogs_web_portlet_BlogsPortlet_redirect=",
				"http://localhost:8080/blog-page?",
				"p_p_id=com_liferay_blogs_web_portlet_BlogsPortlet&",
				"p_p_lifecycle=0&p_p_state=normal&p_p_mode=view"),
			destinationBuilderImpl.build());

		_setUpDestinationHelper(1235L);

		Mockito.doReturn(
			"http://localhost:8080/errorPage"
		).when(
			destinationHelper
		).getAssetViewURL();

		_blogsSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			"http://localhost:8080/errorPage", destinationBuilderImpl.build());
	}

	private void _setUpDestinationHelper(long scopeGroupId) {
		Mockito.doReturn(
			scopeGroupId
		).when(
			destinationHelper
		).getScopeGroupId();
	}

	private final BlogsEntryLocalService _blogsEntryLocalService = Mockito.mock(
		BlogsEntryLocalService.class);
	private BlogsSimilarResultsContributor _blogsSimilarResultsContributor;

}