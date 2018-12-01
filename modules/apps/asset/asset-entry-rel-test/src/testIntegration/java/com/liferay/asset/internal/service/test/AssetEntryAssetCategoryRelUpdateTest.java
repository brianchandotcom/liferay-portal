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

package com.liferay.asset.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Samuel Trong Tran
 */
@RunWith(Arquillian.class)
public class AssetEntryAssetCategoryRelUpdateTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		_userSearchFixture = new UserSearchFixture();

		_userSearchFixture.setUp();
		_groups = _userSearchFixture.getGroups();
		_users = _userSearchFixture.getUsers();

		_assetCategories = new ArrayList<>();
		_assetVocabularies = new ArrayList<>();

		Locale locale = LocaleUtil.US;

		_group = _userSearchFixture.addGroup(
			new GroupBlueprint() {
				{
					defaultLocale = locale;
				}
			});

		_user = _userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		String categoryTitleString1 = "testCategory1";
		String categoryTitleString2 = "testCategory2";
		String vocabularyTitleString1 = "testVocabulary1";
		String vocabularyTitleString2 = "testVocabulary2";

		addCategory(
			_group, addVocabulary(_group, vocabularyTitleString1),
			categoryTitleString1, locale);
		addCategory(
			_group, addVocabulary(_group, vocabularyTitleString2),
			categoryTitleString2, locale);
	}

	@After
	public void tearDown() throws Exception {
		_userSearchFixture.tearDown();
	}

	@Test
	public void testUserCategoryIds() throws Exception {

		User user = _userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		// Add assetCategoryIds

		long[] setAssetCategoryIds = new long[] {_assetCategories.get(0).getCategoryId()};

		assetEntryLocalService.updateEntry(
			user.getUserId(), _group.getGroupId(), user.getCreateDate(),
			user.getModifiedDate(), User.class.getName(), user.getUserId(),
			user.getUuid(), 0, setAssetCategoryIds, null, true, false,
			null, null, null, null, null, user.getFullName(), null, null, null,
			null, 0, 0, null);

		List<AssetCategory> getAssetCategories =
			AssetCategoryServiceUtil.getCategories(
				User.class.getName(), user.getUserId());

		Assert.assertEquals(1, getAssetCategories.size());

		// Update assetCategoryIds

		setAssetCategoryIds = new long[] {
			_assetCategories.get(0).getCategoryId(),
			_assetCategories.get(1).getCategoryId()
		};

		assetEntryLocalService.updateEntry(
			user.getUserId(), _group.getGroupId(), user.getCreateDate(),
			user.getModifiedDate(), User.class.getName(), user.getUserId(),
			user.getUuid(), 0, setAssetCategoryIds, null, true, false,
			null, null, null, null, null, user.getFullName(), null, null, null,
			null, 0, 0, null);

		getAssetCategories =
			AssetCategoryServiceUtil.getCategories(
				User.class.getName(), user.getUserId());

		Assert.assertEquals(2, getAssetCategories.size());

		// Update user without updating assetCategoryIds

		assetEntryLocalService.updateEntry(
			user.getUserId(), _group.getGroupId(), user.getCreateDate(),
			user.getModifiedDate(), User.class.getName(), user.getUserId(),
			user.getUuid(), 0, null, null, true, false,
			null, null, null, null, null, user.getFullName(), null, null, null,
			null, 0, 0, null);

		getAssetCategories =
			AssetCategoryServiceUtil.getCategories(
				User.class.getName(), user.getUserId());

		Assert.assertEquals(2, getAssetCategories.size());

		// Remove assetCategoryIds

		assetEntryLocalService.updateEntry(
			user.getUserId(), _group.getGroupId(), user.getCreateDate(),
			user.getModifiedDate(), User.class.getName(), user.getUserId(),
			user.getUuid(), 0, new long[0], null, true, false,
			null, null, null, null, null, user.getFullName(), null, null, null,
			null, 0, 0, null);

		getAssetCategories =
			AssetCategoryServiceUtil.getCategories(
				User.class.getName(), user.getUserId());

		Assert.assertEquals(0, getAssetCategories.size());

		return;
	}

	protected AssetCategory addCategory(
		Group group, AssetVocabulary assetVocabulary, String title,
		Locale locale)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), _user.getUserId());

		Map<Locale, String> titleMap = new HashMap<Locale, String>() {
			{
				put(locale, title);
			}
		};

		Locale previousLocale = LocaleThreadLocal.getSiteDefaultLocale();

		LocaleThreadLocal.setSiteDefaultLocale(locale);

		try {
			AssetCategory assetCategory = assetCategoryLocalService.addCategory(
				_user.getUserId(), group.getGroupId(),
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
				new HashMap<>(), assetVocabulary.getVocabularyId(),
				new String[0], serviceContext);

			_assetCategories.add(assetCategory);

			return assetCategory;
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(previousLocale);
		}
	}

	protected AssetVocabulary addVocabulary(Group group, String title)
		throws Exception {

		AssetVocabulary assetVocabulary =
			assetVocabularyLocalService.addDefaultVocabulary(
				group.getGroupId());

		assetVocabulary.setName(title);
		assetVocabulary.setTitle(title);

		assetVocabulary = assetVocabularyLocalService.updateAssetVocabulary(
			assetVocabulary);

		_assetVocabularies.add(assetVocabulary);

		return assetVocabulary;
	}

	@Inject
	protected static AssetEntryLocalService assetEntryLocalService;

	@Inject
	protected static AssetCategoryLocalService assetCategoryLocalService;

	@Inject
	protected static AssetVocabularyLocalService assetVocabularyLocalService;

	@DeleteAfterTestRun
	private List<AssetCategory> _assetCategories;

	@DeleteAfterTestRun
	private List<AssetVocabulary> _assetVocabularies;

	private UserSearchFixture _userSearchFixture;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

	private Group _group;
	private User _user;

}