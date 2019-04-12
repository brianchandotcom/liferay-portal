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

package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 * @author Lucas Marques
 */
@RunWith(Arquillian.class)
public class AssetCategoryIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpAssetCategoryFixture();

		setUpAssetCategoryIndexerFixture();
	}

	@Test
	public void testReindexing() throws Exception {
		Locale locale = LocaleUtil.US;

		AssetCategory assetCategory =
			assetCategoryFixture.createAssetCategory();

		String searchTerm = assetCategory.getName();

		assetCategoryIndexerFixture.searchOnlyOne(searchTerm);

		Document document = assetCategoryIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		assetCategoryIndexerFixture.deleteDocument(document);

		assetCategoryIndexerFixture.searchNoOne(searchTerm, locale);

		assetCategoryIndexerFixture.reindex(assetCategory.getCompanyId());

		assetCategoryIndexerFixture.searchOnlyOne(searchTerm);
	}

	protected void setUpAssetCategoryFixture() throws Exception {
		assetCategoryFixture = new AssetCategoryFixture(_group);

		_assetCategories = assetCategoryFixture.getAssetCategories();

		_assetVocabularies = assetCategoryFixture.getAssetVocabularies();
	}

	protected void setUpAssetCategoryIndexerFixture() {
		assetCategoryIndexerFixture = new IndexerFixture<>(AssetCategory.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected AssetCategoryFixture assetCategoryFixture;
	protected IndexerFixture<AssetCategory> assetCategoryIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	@DeleteAfterTestRun
	private List<AssetCategory> _assetCategories;

	@DeleteAfterTestRun
	private List<AssetVocabulary> _assetVocabularies;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}