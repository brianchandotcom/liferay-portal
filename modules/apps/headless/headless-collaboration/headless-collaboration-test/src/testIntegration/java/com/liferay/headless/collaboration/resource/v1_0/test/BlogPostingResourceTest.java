/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.collaboration.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingResourceTest extends BaseBlogPostingResourceTestCase {

	@Test
	public void testGetContentSpaceBlogPostingsPageWithFilterDateTimeEquals()
		throws Exception {

		BlogPosting vocabulary = invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());

		Thread.sleep(1000);

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());

		for (EntityField entityField :
				getEntityFields(EntityField.Type.DATE_TIME)) {

			Page<BlogPosting> page = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(),
				getFilterString(entityField, "eq", vocabulary),
				Pagination.of(2, 1), null);

			assertEquals(
				Collections.singletonList(vocabulary),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceBlogPostingsPageWithFilterStringEquals()
		throws Exception {

		BlogPosting vocabulary = randomBlogPosting();

		invokePostContentSpaceBlogPosting(testGroup.getGroupId(), vocabulary);

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());

		for (EntityField entityField :
				getEntityFields(EntityField.Type.STRING)) {

			Page<BlogPosting> page = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(),
				getFilterString(entityField, "eq", vocabulary),
				Pagination.of(2, 1), null);

			assertEquals(
				Collections.singletonList(vocabulary),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceBlogPostingsPageWithSortDateTime()
		throws Exception {

		List<EntityField> stringEntityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		BlogPosting randomBlogPosting1 = randomBlogPosting();

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting1);

		BlogPosting randomBlogPosting2 = randomBlogPosting();

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting2);

		for (EntityField entityField : stringEntityFields) {
			Page<BlogPosting> ascPage = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(), null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(randomBlogPosting1, randomBlogPosting2),
				(List<BlogPosting>)ascPage.getItems());

			Page<BlogPosting> descPage = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(), null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(randomBlogPosting2, randomBlogPosting1),
				(List<BlogPosting>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceBlogPostingsPageWithSortString()
		throws Exception {

		List<EntityField> stringEntityFields = getEntityFields(
			EntityField.Type.STRING);

		BlogPosting randomBlogPosting1 = randomBlogPosting();
		BlogPosting randomBlogPosting2 = randomBlogPosting();

		for (EntityField entityField : stringEntityFields) {
			BeanUtils.setProperty(
				randomBlogPosting1, entityField.getName(), "Value1");

			BeanUtils.setProperty(
				randomBlogPosting2, entityField.getName(), "Value2");
		}

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting1);

		invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting2);

		for (EntityField entityField : stringEntityFields) {
			Page<BlogPosting> ascPage = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(), null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(randomBlogPosting1, randomBlogPosting2),
				(List<BlogPosting>)ascPage.getItems());

			Page<BlogPosting> descPage = invokeGetContentSpaceBlogPostingsPage(
				testGroup.getGroupId(), null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(randomBlogPosting2, randomBlogPosting1),
				(List<BlogPosting>)descPage.getItems());
		}
	}

	@Override
	@Test
	public void testPostContentSpaceBlogPosting() throws Exception {
		BlogPosting blogPosting = randomBlogPosting();

		BlogPosting postBlogPosting = invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), blogPosting);

		assertValid(postBlogPosting);
	}

	@Override
	protected boolean equals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		if (Objects.equals(
				blogPosting1.getArticleBody(), blogPosting2.getArticleBody()) &&
			Objects.equals(
				blogPosting1.getDescription(), blogPosting2.getDescription()) &&
			Objects.equals(
				blogPosting1.getHeadline(), blogPosting2.getHeadline())) {

			return true;
		}

		return false;
	}

	protected BlogPosting randomBlogPosting() {
		return new BlogPosting() {
			{
				articleBody = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				headline = RandomTestUtil.randomString();
			}
		};
	}

}