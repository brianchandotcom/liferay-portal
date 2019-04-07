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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class KeywordResourceTest extends BaseKeywordResourceTestCase {

	protected void assertValid(Keyword keyword) {
		boolean valid = false;

		if (Objects.equals(
				keyword.getContentSpaceId(), testGroup.getGroupId()) &&
			(keyword.getDateCreated() != null) &&
			(keyword.getDateModified() != null) && (keyword.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(Keyword keyword1, Keyword keyword2) {
		if (Objects.equals(
				keyword1.getContentSpaceId(), keyword2.getContentSpaceId()) &&
			StringUtil.equalsIgnoreCase(
				keyword1.getName(), keyword2.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected Keyword randomKeyword() {
		Keyword keyword = super.randomKeyword();

		keyword.setContentSpaceId(testGroup.getGroupId());

		return keyword;
	}

	@Override
	protected Keyword testDeleteKeyword_addKeyword() throws Exception {
		return invokePostContentSpaceKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Override
	protected Keyword testGetContentSpaceKeywordsPage_addKeyword(
			Long contentSpaceId, Keyword keyword)
		throws Exception {

		return invokePostContentSpaceKeyword(contentSpaceId, keyword);
	}

	@Override
	protected Keyword testGetKeyword_addKeyword() throws Exception {
		return invokePostContentSpaceKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Override
	protected Keyword testPostContentSpaceKeyword_addKeyword(Keyword keyword)
		throws Exception {

		return invokePostContentSpaceKeyword(testGroup.getGroupId(), keyword);
	}

	@Override
	protected Keyword testPutKeyword_addKeyword() throws Exception {
		return invokePostContentSpaceKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

}