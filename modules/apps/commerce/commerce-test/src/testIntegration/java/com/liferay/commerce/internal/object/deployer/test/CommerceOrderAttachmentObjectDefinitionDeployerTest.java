/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.deployer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.test.util.CommerceOrderAttachmentTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-6252")
@RunWith(Arquillian.class)
@Sync
public class CommerceOrderAttachmentObjectDefinitionDeployerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CommerceOrderAttachmentTestUtil.initialize(
			CommerceOrderAttachmentObjectDefinitionDeployerTest.class);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			TestPropsValues.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			TestPropsValues.getGroupId(), _commerceCurrency.getCode());

		_objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_COMMERCE_ORDER_ATTACHMENT",
					TestPropsValues.getCompanyId());
	}

	@Test
	public void testDeploy() throws Exception {
		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				_objectDefinition.getClassName());

		Class<? extends ModelResourcePermission> clazz =
			modelResourcePermission.getClass();

		Assert.assertEquals(
			"CommerceOrderAttachmentObjectEntryModelResourcePermission",
			clazz.getSimpleName());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			TestPropsValues.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"r_accountToCommerceOrderAttachments_accountEntryId",
				commerceOrder.getCommerceAccountId()
			).put(
				"r_commerceOrderToCommerceOrderAttachments_commerceOrderId",
				commerceOrder.getCommerceOrderId()
			).put(
				"title", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));

		Assert.assertTrue(
			modelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()),
				objectEntry, ActionKeys.VIEW));

		User user = UserTestUtil.addUser();

		Assert.assertFalse(
			modelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(user), objectEntry,
				ActionKeys.VIEW));

		commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			user.getUserId(), _commerceChannel.getGroupId(), _commerceCurrency);

		objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"r_accountToCommerceOrderAttachments_accountEntryId",
				commerceOrder.getCommerceAccountId()
			).put(
				"r_commerceOrderToCommerceOrderAttachments_commerceOrderId",
				commerceOrder.getCommerceOrderId()
			).put(
				"title", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));

		Assert.assertTrue(
			modelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(user), objectEntry,
				ActionKeys.VIEW));
	}

	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}