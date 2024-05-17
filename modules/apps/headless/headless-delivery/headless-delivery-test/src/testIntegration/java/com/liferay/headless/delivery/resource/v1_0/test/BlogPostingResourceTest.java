/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingResourceTest extends BaseBlogPostingResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PrincipalThreadLocal.setName(_originalName);
	}

	@Override
	@Test
	public void testDeleteBlogPostingMyRating() throws Exception {
		super.testDeleteBlogPostingMyRating();

		BlogPosting blogPosting =
			testDeleteBlogPostingMyRating_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.deleteBlogPostingMyRatingHttpResponse(
				blogPosting.getId()));
		assertHttpResponseStatusCode(
			404,
			blogPostingResource.deleteBlogPostingMyRatingHttpResponse(
				blogPosting.getId()));

		BlogPosting irrelevantBlogPosting = randomIrrelevantBlogPosting();

		assertHttpResponseStatusCode(
			404,
			blogPostingResource.deleteBlogPostingMyRatingHttpResponse(
				irrelevantBlogPosting.getId()));
	}

	@Override
	@Test
	public void testGetBlogPostingRenderedContentByDisplayPageDisplayPageKey()
		throws Exception {
	}

	@Override
	@Test
	public void testGetSiteBlogPostingsPage() throws Exception {
		super.testGetSiteBlogPostingsPage();

		BlogPosting blogPosting = randomBlogPosting();

		blogPosting.setKeywords(new String[] {"TaG"});

		blogPosting = testGetSiteBlogPostingsPage_addBlogPosting(
			testGroup.getGroupId(), blogPosting);

		Page<BlogPosting> siteBlogPostingsPage =
			blogPostingResource.getSiteBlogPostingsPage(
				testGroup.getGroupId(), null, null,
				"keywords/any(k:k eq 'tag')", Pagination.of(1, 10), null);

		Assert.assertThat(
			siteBlogPostingsPage.getItems(), CoreMatchers.hasItem(blogPosting));
	}

	@Override
	@Test
	public void testPutSiteBlogPostingSubscribe() throws Exception {
		BlogPosting blogPosting =
			testPutSiteBlogPostingSubscribe_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.putSiteBlogPostingSubscribeHttpResponse(
				blogPosting.getSiteId()));
	}

	@Override
	@Test
	public void testPutSiteBlogPostingUnsubscribe() throws Exception {
		BlogPosting blogPosting =
			testPutSiteBlogPostingUnsubscribe_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.putSiteBlogPostingUnsubscribeHttpResponse(
				blogPosting.getSiteId()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"articleBody", "description", "headline"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected BlogPosting testDeleteBlogPostingMyRating_addBlogPosting()
		throws Exception {

		BlogPosting blogPosting =
			super.testDeleteBlogPostingMyRating_addBlogPosting();

		blogPostingResource.putBlogPostingMyRating(
			blogPosting.getId(), randomRating());

		return blogPosting;
	}

	private String _originalName;

}