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

package com.liferay.commerce.product.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class CPDisplayLayoutLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group1.getCompanyId(), _group1.getGroupId(), _user.getUserId());

		_group2 = GroupTestUtil.addGroup(
			_group1.getCompanyId(), _user.getUserId(), 0);

		_commerceChannel1 = CommerceChannelLocalServiceUtil.addCommerceChannel(
			StringPool.BLANK, _group2.getGroupId(),
			_group2.getName(_serviceContext.getLanguageId()) + " Portal1",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null, StringPool.BLANK,
			_serviceContext);

		_group3 = GroupTestUtil.addGroup(
			_group1.getCompanyId(), _user.getUserId(), 0);

		_commerceChannel2 = CommerceChannelLocalServiceUtil.addCommerceChannel(
			StringPool.BLANK, _group3.getGroupId(),
			_group3.getName(_serviceContext.getLanguageId()) + " Portal2",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null, StringPool.BLANK,
			_serviceContext);

		_assetVocabulary = AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
			_group1.getGroupId());

		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.US.getDisplayLanguage(),
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		_cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, false,
			false);
	}

	@Test
	public void testManageCPDisplayLayout() throws Exception {
		frutillaRule.scenario(
			"It should be possible to add same Asset Category and Product " +
				"layouts to different channels"
		).given(
			"2 channels"
		).when(
			"The same Asset Category and Product are added to different " +
				"Channels"
		).then(
			"No exception is raised"
		);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			_user.getUserId(), _group1.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			_serviceContext);

		_cpDisplayLayoutLocalService.addCPDisplayLayout(
			_user.getUserId(), _commerceChannel1.getSiteGroupId(),
			AssetCategory.class, assetCategory.getCategoryId(),
			RandomTestUtil.randomString());

		_cpDisplayLayoutLocalService.addCPDisplayLayout(
			_user.getUserId(), _commerceChannel1.getSiteGroupId(),
			CPDefinition.class, _cpDefinition.getCPDefinitionId(),
			RandomTestUtil.randomString());
		_cpDisplayLayoutLocalService.addCPDisplayLayout(
			_user.getUserId(), _commerceChannel2.getSiteGroupId(),
			AssetCategory.class, assetCategory.getCategoryId(),
			RandomTestUtil.randomString());
		_cpDisplayLayoutLocalService.addCPDisplayLayout(
			_user.getUserId(), _commerceChannel2.getSiteGroupId(),
			CPDefinition.class, _cpDefinition.getCPDefinitionId(),
			RandomTestUtil.randomString());

		Assert.assertEquals(
			4, _cpDisplayLayoutLocalService.getCPDisplayLayoutsCount());

		CPDefinitionLocalServiceUtil.deleteCPDefinition(
			_cpDefinition.getCPDefinitionId());

		Assert.assertEquals(
			2, _cpDisplayLayoutLocalService.getCPDisplayLayoutsCount());

		CommerceChannelLocalServiceUtil.deleteCommerceChannel(
			_commerceChannel2);

		Assert.assertEquals(
			2, _cpDisplayLayoutLocalService.getCPDisplayLayoutsCount());
		AssetCategoryLocalServiceUtil.deleteAssetCategory(assetCategory);
		Assert.assertEquals(
			0, _cpDisplayLayoutLocalService.getCPDisplayLayoutsCount());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static User _user;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel1;

	private CommerceChannel _commerceChannel2;
	private CPDefinition _cpDefinition;

	@Inject
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@DeleteAfterTestRun
	private Group _group3;

	private ServiceContext _serviceContext;

}