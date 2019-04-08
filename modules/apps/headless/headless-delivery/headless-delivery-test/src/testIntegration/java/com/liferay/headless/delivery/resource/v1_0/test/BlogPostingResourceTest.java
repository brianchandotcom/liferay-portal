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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.dto.v1_0.BlogPosting;

import java.util.Objects;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingResourceTest extends BaseBlogPostingResourceTestCase {

	@Override
	protected void assertValid(BlogPosting blogPosting) {
		boolean valid = false;

		if ((blogPosting.getDateCreated() != null) &&
			(blogPosting.getDateModified() != null) &&
			(blogPosting.getHeadline() != null) &&
			(blogPosting.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		if (Objects.equals(
				blogPosting1.getContentSpaceId(),
				blogPosting2.getContentSpaceId()) &&
			Objects.equals(
				blogPosting1.getDescription(), blogPosting2.getDescription()) &&
			Objects.equals(
				blogPosting1.getHeadline(), blogPosting2.getHeadline())) {

			return true;
		}

		return false;
	}

	@Override
	protected BlogPosting randomBlogPosting() {
		BlogPosting blogPosting = super.randomBlogPosting();

		blogPosting.setContentSpaceId(testGroup.getGroupId());

		return blogPosting;
	}

	@Override
	protected BlogPosting testDeleteBlogPosting_addBlogPosting()
		throws Exception {

		return invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Override
	protected BlogPosting testDeleteBlogPostingMyRating_addBlogPosting()
		throws Exception {

		return invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Override
	protected BlogPosting testGetBlogPosting_addBlogPosting() throws Exception {
		return invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Override
	protected BlogPosting testGetContentSpaceBlogPostingsPage_addBlogPosting(
			Long contentSpaceId, BlogPosting blogPosting)
		throws Exception {

		return invokePostContentSpaceBlogPosting(contentSpaceId, blogPosting);
	}

	@Override
	protected BlogPosting testPatchBlogPosting_addBlogPosting()
		throws Exception {

		return invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Override
	protected BlogPosting testPostContentSpaceBlogPosting_addBlogPosting(
			BlogPosting blogPosting)
		throws Exception {

		return invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), blogPosting);
	}

	@Override
	protected BlogPosting testPutBlogPosting_addBlogPosting() throws Exception {
		return invokePostContentSpaceBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

}