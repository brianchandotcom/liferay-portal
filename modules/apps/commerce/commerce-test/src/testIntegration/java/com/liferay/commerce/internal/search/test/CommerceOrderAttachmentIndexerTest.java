/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderAttachmentLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.HashSet;

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
@Sync
public class CommerceOrderAttachmentIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_user = UserTestUtil.addUser();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_accountEntryA = CommerceAccountTestUtil.addPersonAccountEntry(
			_user.getUserId(), serviceContext);

		_commerceOrderA = _commerceOrderLocalService.addCommerceOrder(
			TestPropsValues.getUserId(), _commerceChannel.getGroupId(),
			_accountEntryA.getAccountEntryId(), _commerceCurrency.getCode(), 0);

		_attachmentA1 = _addCommerceOrderAttachment(_commerceOrderA, false);
		_attachmentA2 = _addCommerceOrderAttachment(_commerceOrderA, true);

		_accountEntryB = CommerceAccountTestUtil.addPersonAccountEntry(
			_user.getUserId(), serviceContext);

		_commerceOrderB = _commerceOrderLocalService.addCommerceOrder(
			TestPropsValues.getUserId(), _commerceChannel.getGroupId(),
			_accountEntryB.getAccountEntryId(), _commerceCurrency.getCode(), 0);

		_attachmentB1 = _addCommerceOrderAttachment(_commerceOrderB, false);
		_attachmentB2 = _addCommerceOrderAttachment(_commerceOrderB, true);

		_indexer = _indexerRegistry.getIndexer(
			CommerceOrderAttachment.class.getName());

		_role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null,
			RoleConstants.TYPE_REGULAR, null, serviceContext);

		_roleLocalService.addUserRole(_user.getUserId(), _role);
	}

	@Test
	public void testSearch() throws Exception {
		_assertSearch(0L, TestPropsValues.getUserId());
		_assertSearch(
			_commerceOrderA.getCommerceOrderId(), TestPropsValues.getUserId(),
			_attachmentA1, _attachmentA2);
		_assertSearch(
			_commerceOrderB.getCommerceOrderId(), TestPropsValues.getUserId(),
			_attachmentB1, _attachmentB2);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_assertSearch(
				_commerceOrderA.getCommerceOrderId(), _user.getUserId());
			_assertSearch(
				_commerceOrderB.getCommerceOrderId(), _user.getUserId());
		}

		_setResourcePermissions(
			_accountEntryA, CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_assertSearch(
				_commerceOrderA.getCommerceOrderId(), _user.getUserId(),
				_attachmentA1);
			_assertSearch(
				_commerceOrderB.getCommerceOrderId(), _user.getUserId());
		}

		_setResourcePermissions(
			_accountEntryA, CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS,
			CommerceOrderActionKeys.VIEW_RESTRICTED_COMMERCE_ORDER_ATTACHMENTS);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_assertSearch(
				_commerceOrderA.getCommerceOrderId(), _user.getUserId(),
				_attachmentA1, _attachmentA2);
			_assertSearch(
				_commerceOrderB.getCommerceOrderId(), _user.getUserId());
		}

		_setResourcePermissions(
			_accountEntryB, CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS,
			CommerceOrderActionKeys.VIEW_RESTRICTED_COMMERCE_ORDER_ATTACHMENTS);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_assertSearch(
				_commerceOrderA.getCommerceOrderId(), _user.getUserId(),
				_attachmentA1, _attachmentA2);
			_assertSearch(
				_commerceOrderB.getCommerceOrderId(), _user.getUserId(),
				_attachmentB1, _attachmentB2);
		}
	}

	private CommerceOrderAttachment _addCommerceOrderAttachment(
			CommerceOrder commerceOrder, boolean restricted)
		throws Exception {

		return _commerceOrderAttachmentLocalService.addCommerceOrderAttachment(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			commerceOrder.getCommerceOrderId(), RandomTestUtil.nextDouble(),
			restricted, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			getClass().getResourceAsStream("dependencies/attachment.txt"));
	}

	private void _assertSearch(
			long commerceOrderId, long userId,
			CommerceOrderAttachment... expectedCommerceOrderAttachments)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setUserId(userId);

		if (commerceOrderId > 0) {
			searchContext.setAttribute("commerceOrderId", commerceOrderId);
		}

		Hits hits = _indexer.search(searchContext);

		Assert.assertEquals(
			Arrays.toString(hits.getDocs()),
			new HashSet<>(
				TransformUtil.transformToList(
					expectedCommerceOrderAttachments,
					CommerceOrderAttachment::getCommerceOrderAttachmentId)),
			new HashSet<>(
				TransformUtil.transformToList(
					hits.getDocs(),
					document -> Long.valueOf(
						document.get(Field.ENTRY_CLASS_PK)))));
	}

	private void _setResourcePermissions(
			AccountEntry accountEntry, String... actionIds)
		throws Exception {

		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), CommerceOrderConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(accountEntry.getAccountEntryGroupId()),
			_role.getRoleId(), actionIds);
	}

	private AccountEntry _accountEntryA;
	private AccountEntry _accountEntryB;
	private CommerceOrderAttachment _attachmentA1;
	private CommerceOrderAttachment _attachmentA2;
	private CommerceOrderAttachment _attachmentB1;
	private CommerceOrderAttachment _attachmentB2;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;
	private CommerceOrder _commerceOrderA;

	@Inject
	private CommerceOrderAttachmentLocalService
		_commerceOrderAttachmentLocalService;

	private CommerceOrder _commerceOrderB;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private Group _group;
	private Indexer<CommerceOrderAttachment> _indexer;

	@Inject
	private IndexerRegistry _indexerRegistry;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

}