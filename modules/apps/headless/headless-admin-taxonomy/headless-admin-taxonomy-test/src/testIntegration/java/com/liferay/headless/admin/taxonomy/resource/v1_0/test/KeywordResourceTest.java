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

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.permission.Permission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class KeywordResourceTest extends BaseKeywordResourceTestCase {

	@Override
	@Test
	public void testGetKeywordsRankedPage() throws Exception {
		Page<Keyword> page = keywordResource.getKeywordsRankedPage(
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Keyword keyword1 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());
		Keyword keyword2 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		page = keywordResource.getKeywordsRankedPage(
			testGroup.getGroupId(), null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(page);

		keywordResource.deleteKeyword(keyword1.getId());
		keywordResource.deleteKeyword(keyword2.getId());
	}

	@Test
	public void testGetKeywordsRankedPageWithPagination() throws Exception {
		Keyword keyword1 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());
		Keyword keyword2 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());
		Keyword keyword3 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Page<Keyword> page1 = keywordResource.getKeywordsRankedPage(
			testGroup.getGroupId(), null, Pagination.of(1, 2));

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getKeywordsRankedPage(
			testGroup.getGroupId(), null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getKeywordsRankedPage(
			testGroup.getGroupId(), null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2, keyword3),
			(List<Keyword>)page3.getItems());
	}

	@Override
	@Test
	public void testPutKeywordPermission() throws Exception {
		Keyword keyword = testPutKeywordPermission_addKeyword();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			keywordResource.putKeywordPermissionHttpResponse(
				keyword.getId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {ActionKeys.MANAGE_TAG});
							setRoleName(role.getName());
						}
					}
				}));

		assertHttpResponseStatusCode(
			404,
			keywordResource.putKeywordPermissionHttpResponse(
				0L,
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"-"});
							setRoleName("-");
						}
					}
				}));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Keyword randomKeyword() throws Exception {
		Keyword keyword = super.randomKeyword();

		keyword.setName(StringUtil.toLowerCase(keyword.getName()));

		return keyword;
	}

	@Override
	protected Keyword testGetKeywordsRankedPage_addKeyword(Keyword keyword)
		throws Exception {

		keyword = testPostSiteKeyword_addKeyword(keyword);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			testGroup.getGroupId());

		AssetTagLocalServiceUtil.addAssetEntryAssetTag(
			assetEntry.getEntryId(), keyword.getId());

		return keyword;
	}

}