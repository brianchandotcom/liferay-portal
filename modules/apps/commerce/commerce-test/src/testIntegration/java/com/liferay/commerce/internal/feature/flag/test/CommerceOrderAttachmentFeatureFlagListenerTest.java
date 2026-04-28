/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.feature.flag.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderAttachmentLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceOrderAttachmentTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class CommerceOrderAttachmentFeatureFlagListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		CommerceOrderAttachmentTestUtil.initialize(
			CommerceOrderAttachmentFeatureFlagListenerTest.class);

		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_user = UserTestUtil.addUser();

		_accountEntry = CommerceAccountTestUtil.addPersonAccountEntry(
			_user.getUserId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_accountEntry.getAccountEntryId(), _commerceCurrency.getCode(), 0);
	}

	@Test
	public void testOnValue() throws Exception {
		FileEntry fileEntry1 = _addAttachmentFileEntry();
		FileEntry fileEntry2 = _addAttachmentFileEntry();

		Assert.assertEquals(
			0,
			_commerceOrderAttachmentLocalService.
				getCommerceOrderAttachmentsCount(
					_commerceOrder.getCommerceOrderId()));

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					FeatureFlagConstants.getKey("LPD-6252"),
					Boolean.TRUE.toString())) {

			FeatureFlagTestUtil.invokeFeatureFlagListeners(
				_group.getCompanyId(), true, "LPD-6252");

			List<CommerceOrderAttachment> commerceOrderAttachments =
				_commerceOrderAttachmentLocalService.
					getCommerceOrderAttachments(
						_commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				commerceOrderAttachments.toString(), 2,
				commerceOrderAttachments.size());

			for (CommerceOrderAttachment commerceOrderAttachment :
					commerceOrderAttachments) {

				Assert.assertEquals(
					_commerceOrder.getCommerceOrderId(),
					commerceOrderAttachment.getCommerceOrderId());
				Assert.assertTrue(
					(commerceOrderAttachment.getFileEntryId() ==
						fileEntry1.getFileEntryId()) ||
					(commerceOrderAttachment.getFileEntryId() ==
						fileEntry2.getFileEntryId()));
				Assert.assertEquals(
					"purchaseOrderDocument", commerceOrderAttachment.getType());
				Assert.assertFalse(commerceOrderAttachment.isRestricted());
			}

			FeatureFlagTestUtil.invokeFeatureFlagListeners(
				_group.getCompanyId(), true, "LPD-6252");

			Assert.assertEquals(
				2,
				_commerceOrderAttachmentLocalService.
					getCommerceOrderAttachmentsCount(
						_commerceOrder.getCommerceOrderId()));
		}
	}

	private FileEntry _addAttachmentFileEntry() throws Exception {
		return _commerceOrderLocalService.addAttachmentFileEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			_commerceOrder.getCommerceOrderId(), RandomTestUtil.randomString(),
			getClass().getResourceAsStream("dependencies/attachment.txt"));
	}

	private AccountEntry _accountEntry;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderAttachmentLocalService
		_commerceOrderAttachmentLocalService;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private Group _group;
	private User _user;

}