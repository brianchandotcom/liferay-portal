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
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.BaseSearchTestCase;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Istvan Andras Dezsi
 * @author Tibor Lipusz
 */
@RunWith(Arquillian.class)
@Sync
public class AssetCategorySearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();

		_permissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		PrincipalThreadLocal.setName(_name);
	}

	@Ignore
	@Override
	@Test
	public void testSearchAttachments() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchBaseModelWithTrash() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchComments() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireAllVersions() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireLatestVersion() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchMyEntries() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchRecentEntries() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchVersions() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			Map<Locale, String> keywordsMap, ServiceContext serviceContext)
		throws Exception {

		AssetVocabulary vocabulary = (AssetVocabulary)parentBaseModel;

		return AssetCategoryServiceUtil.addCategory(
			serviceContext.getScopeGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, keywordsMap,
			null, vocabulary.getVocabularyId(), null, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		AssetVocabulary vocabulary = (AssetVocabulary)parentBaseModel;

		return AssetCategoryServiceUtil.addCategory(
			serviceContext.getScopeGroupId(), keywords,
			vocabulary.getVocabularyId(), serviceContext);
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		AssetCategoryServiceUtil.deleteCategory(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return AssetCategory.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return AssetVocabularyServiceUtil.addVocabulary(
			serviceContext.getScopeGroupId(), RandomTestUtil.randomString(),
			serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
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

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		AssetCategory category = (AssetCategory)baseModel;

		category.setTitle(keywords);

		return AssetCategoryLocalServiceUtil.updateAssetCategory(category);
	}

	private String _name;
	private PermissionChecker _permissionChecker;

}