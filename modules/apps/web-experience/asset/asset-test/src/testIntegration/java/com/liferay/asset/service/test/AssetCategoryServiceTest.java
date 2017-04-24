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

package com.liferay.asset.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author José Manuel Navarro
 */
@RunWith(Arquillian.class)
public class AssetCategoryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();

		_group = GroupTestUtil.addGroup();

		_permissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		PrincipalThreadLocal.setName(_name);
	}

	@Test
	public void testDeleteVocabularyAlsoUpdatesCategoriesTree()
		throws Exception {

		long groupId = _group.getGroupId();

		AssetVocabulary vocabulary1 = AssetTestUtil.addVocabulary(groupId);
		AssetVocabulary vocabulary2 = AssetTestUtil.addVocabulary(groupId);

		AssetCategory category1a = AssetTestUtil.addCategory(
			groupId, vocabulary1.getVocabularyId());

		assertLeftRightCategory(1, category1a);

		AssetCategory category1b = AssetTestUtil.addCategory(
			groupId, vocabulary1.getVocabularyId());

		assertLeftRightCategory(3, category1b);

		AssetCategory category1c = AssetTestUtil.addCategory(
			groupId, vocabulary1.getVocabularyId());

		assertLeftRightCategory(5, category1c);

		AssetCategory category2a = AssetTestUtil.addCategory(
			groupId, vocabulary2.getVocabularyId());

		assertLeftRightCategory(7, category2a);

		AssetCategory category2b = AssetTestUtil.addCategory(
			groupId, vocabulary2.getVocabularyId());

		assertLeftRightCategory(9, category2b);

		AssetCategory category2c = AssetTestUtil.addCategory(
			groupId, vocabulary2.getVocabularyId());

		assertLeftRightCategory(11, category2c);

		AssetVocabularyServiceUtil.deleteVocabulary(
			vocabulary1.getVocabularyId());

		int count = AssetCategoryServiceUtil.getVocabularyCategoriesCount(
			groupId, vocabulary1.getVocabularyId());

		Assert.assertEquals(0, count);

		count = AssetCategoryServiceUtil.getVocabularyCategoriesCount(
			groupId, vocabulary2.getVocabularyId());

		Assert.assertEquals(3, count);

		category2a = AssetCategoryServiceUtil.getCategory(
			category2a.getCategoryId());

		assertLeftRightCategory(1, category2a);

		category2b = AssetCategoryServiceUtil.getCategory(
			category2b.getCategoryId());

		assertLeftRightCategory(3, category2b);

		category2c = AssetCategoryServiceUtil.getCategory(
			category2c.getCategoryId());

		assertLeftRightCategory(5, category2c);
	}

	protected void assertLeftRightCategory(
			long expectedLeft, AssetCategory category)
		throws Exception {

		Assert.assertEquals(expectedLeft, category.getLeftCategoryId());
		Assert.assertEquals(expectedLeft + 1, category.getRightCategoryId());
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {
				{
					init(TestPropsValues.getUser());
				}

				@Override
				public boolean hasOwnerPermission(
					long companyId, String name, String primKey, long ownerId,
					String actionId) {

					return true;
				}

			});
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@DeleteAfterTestRun
	private Group _group;

	private String _name;
	private PermissionChecker _permissionChecker;

}