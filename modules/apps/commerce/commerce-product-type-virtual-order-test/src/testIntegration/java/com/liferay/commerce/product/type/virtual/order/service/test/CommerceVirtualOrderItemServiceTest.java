/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.configuration.CProductVersionConfiguration;
import com.liferay.commerce.product.constants.CPInstanceConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemService;
import com.liferay.commerce.product.type.virtual.order.util.CommerceVirtualOrderItemChecker;
import com.liferay.commerce.product.type.virtual.service.CPDVirtualSettingFileEntryLocalService;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.product.type.virtual.test.util.VirtualCPTypeTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michele Vigilante
 */
@RunWith(Arquillian.class)
public class CommerceVirtualOrderItemServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_user = UserTestUtil.addUser();

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId(),
			_commerceCurrency.getCode());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		_cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME, true,
			true);

		_cpDefinitionVirtualSetting =
			VirtualCPTypeTestUtil.addCPDefinitionVirtualSetting(
				_commerceCatalog.getGroupId(),
				_cpDefinition.getModelClassName(),
				_cpDefinition.getCPDefinitionId(), 0,
				CommerceOrderConstants.ORDER_STATUS_PENDING, 0, 0, 0);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(_cpDefinition);

		_cpInstance = _cpInstanceLocalService.getCPInstance(
			_cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);
	}

	@Test
	public void testPropagateCPDVirtualSettingFileEntry() throws Exception {
		frutillaRule.scenario(
			"Propagate a virtual product file"
		).given(
			"A placed, an expired, a cancelled and an open order of a " +
				"virtual product"
		).when(
			"A new URL and a new Document Library file are added to the " +
				"product and propagated twice"
		).then(
			"Only the placed and the expired orders should receive the new " +
				"files, only once"
		);

		CommerceOrderItem commerceOrderItem = _addCommerceOrderItem();

		CommerceOrderItem expiredCommerceOrderItem = _addCommerceOrderItem();

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			_commerceVirtualOrderItemLocalService.
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					expiredCommerceOrderItem.getCommerceOrderItemId());

		commerceVirtualOrderItem.setActive(false);
		commerceVirtualOrderItem.setEndDate(
			new Date(System.currentTimeMillis() - Time.DAY));

		_commerceVirtualOrderItemLocalService.updateCommerceVirtualOrderItem(
			commerceVirtualOrderItem);

		CommerceOrderItem cancelledCommerceOrderItem = _addCommerceOrderItem();

		CommerceOrder cancelledCommerceOrder =
			cancelledCommerceOrderItem.getCommerceOrder();

		cancelledCommerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_CANCELLED);

		_commerceOrderLocalService.updateCommerceOrder(cancelledCommerceOrder);

		CommerceOrder openCommerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency);

		CommerceOrderItem openCommerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				openCommerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), BigDecimal.ONE);

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			_cpdVirtualSettingFileEntryLocalService.
				addCPDVirtualSettingFileEntry(
					TestPropsValues.getUserId(),
					_cpDefinitionVirtualSetting.getGroupId(),
					_cpDefinitionVirtualSetting.
						getCPDefinitionVirtualSettingId(),
					0,
					"http://www.example.com/" + RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

		_commerceVirtualOrderItemService.propagateCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntry.
				getCPDefinitionVirtualSettingFileEntryId());

		_assertCommerceVirtualOrderItemFileEntriesCount(
			0, cancelledCommerceOrderItem, cpdVirtualSettingFileEntry);
		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, commerceOrderItem, cpdVirtualSettingFileEntry);
		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, expiredCommerceOrderItem, cpdVirtualSettingFileEntry);

		Assert.assertNull(
			_commerceVirtualOrderItemLocalService.
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					openCommerceOrderItem.getCommerceOrderItemId()));

		List<CommerceVirtualOrderItem> commerceVirtualOrderItems =
			_commerceVirtualOrderItemLocalService.
				getCommerceVirtualOrderItemsWithoutCPDVirtualSettingFileEntry(
					cpdVirtualSettingFileEntry.
						getCPDefinitionVirtualSettingFileEntryId());

		Assert.assertTrue(commerceVirtualOrderItems.isEmpty());

		_commerceVirtualOrderItemService.propagateCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntry.
				getCPDefinitionVirtualSettingFileEntryId());

		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, commerceOrderItem, cpdVirtualSettingFileEntry);
		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, expiredCommerceOrderItem, cpdVirtualSettingFileEntry);

		DLFolder dlFolder = DLTestUtil.addDLFolder(
			_commerceCatalog.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		CPDVirtualSettingFileEntry dlFileEntryCPDVirtualSettingFileEntry =
			_cpdVirtualSettingFileEntryLocalService.
				addCPDVirtualSettingFileEntry(
					TestPropsValues.getUserId(),
					_cpDefinitionVirtualSetting.getGroupId(),
					_cpDefinitionVirtualSetting.
						getCPDefinitionVirtualSettingId(),
					dlFileEntry.getFileEntryId(), null,
					RandomTestUtil.randomString());

		_commerceVirtualOrderItemService.propagateCPDVirtualSettingFileEntry(
			dlFileEntryCPDVirtualSettingFileEntry.
				getCPDefinitionVirtualSettingFileEntryId());

		_assertCommerceVirtualOrderItemFileEntriesCount(
			0, cancelledCommerceOrderItem,
			dlFileEntryCPDVirtualSettingFileEntry);
		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, commerceOrderItem, dlFileEntryCPDVirtualSettingFileEntry);
		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, expiredCommerceOrderItem, dlFileEntryCPDVirtualSettingFileEntry);

		commerceVirtualOrderItems =
			_commerceVirtualOrderItemLocalService.
				getCommerceVirtualOrderItemsWithoutCPDVirtualSettingFileEntry(
					dlFileEntryCPDVirtualSettingFileEntry.
						getCPDefinitionVirtualSettingFileEntryId());

		Assert.assertTrue(commerceVirtualOrderItems.isEmpty());
	}

	@Test
	public void testPropagateCPDVirtualSettingFileEntryWithCPInstanceOverride()
		throws Exception {

		frutillaRule.scenario(
			"Propagate virtual product and SKU files"
		).given(
			"A placed order of a SKU that overrides the virtual settings of " +
				"the product"
		).when(
			"A new file is propagated from the product and from the SKU"
		).then(
			"The order should receive only the file of the SKU"
		);

		CommerceOrderItem commerceOrderItem = _addCommerceOrderItem();

		CPDefinitionVirtualSetting overrideCPDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingLocalService.
				addCPDefinitionVirtualSetting(
					_cpInstance.getModelClassName(),
					_cpInstance.getCPInstanceId(), 0,
					"http://www.example.com/" + RandomTestUtil.randomString(),
					CommerceOrderConstants.ORDER_STATUS_PENDING, 0, 0, false, 0,
					null, false, null, 0, true,
					ServiceContextTestUtil.getServiceContext(
						_commerceCatalog.getGroupId(),
						TestPropsValues.getUserId()));

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			_cpdVirtualSettingFileEntryLocalService.
				addCPDVirtualSettingFileEntry(
					TestPropsValues.getUserId(),
					_cpDefinitionVirtualSetting.getGroupId(),
					_cpDefinitionVirtualSetting.
						getCPDefinitionVirtualSettingId(),
					0,
					"http://www.example.com/" + RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

		_commerceVirtualOrderItemService.propagateCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntry.
				getCPDefinitionVirtualSettingFileEntryId());

		_assertCommerceVirtualOrderItemFileEntriesCount(
			0, commerceOrderItem, cpdVirtualSettingFileEntry);

		CPDVirtualSettingFileEntry overrideCPDVirtualSettingFileEntry =
			_cpdVirtualSettingFileEntryLocalService.
				addCPDVirtualSettingFileEntry(
					TestPropsValues.getUserId(),
					overrideCPDefinitionVirtualSetting.getGroupId(),
					overrideCPDefinitionVirtualSetting.
						getCPDefinitionVirtualSettingId(),
					0,
					"http://www.example.com/" + RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

		_commerceVirtualOrderItemService.propagateCPDVirtualSettingFileEntry(
			overrideCPDVirtualSettingFileEntry.
				getCPDefinitionVirtualSettingFileEntryId());

		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, commerceOrderItem, overrideCPDVirtualSettingFileEntry);
	}

	@Test
	public void testPropagateCPDVirtualSettingFileEntryWithCProductVersion()
		throws Exception {

		frutillaRule.scenario(
			"Propagate a virtual product file with product versioning"
		).given(
			"A placed order of the first version of a virtual product"
		).when(
			"A new file is added to a draft version and propagated"
		).then(
			"The propagation should fail"
		).and(
			"The order of the first version should receive the new file once " +
				"the draft version is published"
		);

		CommerceOrderItem commerceOrderItem = _addCommerceOrderItem();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CProductVersionConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"enabled", true
						).put(
							"versionThreshold", 10
						).build())) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_commerceCatalog.getGroupId(), TestPropsValues.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.copyCPDefinition(
					_cpDefinition.getCPDefinitionId());

			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingLocalService.
					fetchCPDefinitionVirtualSetting(
						cpDefinition.getModelClassName(),
						cpDefinition.getCPDefinitionId());

			CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
				_cpdVirtualSettingFileEntryLocalService.
					addCPDVirtualSettingFileEntry(
						TestPropsValues.getUserId(),
						cpDefinitionVirtualSetting.getGroupId(),
						cpDefinitionVirtualSetting.
							getCPDefinitionVirtualSettingId(),
						0,
						"http://www.example.com/" +
							RandomTestUtil.randomString(),
						RandomTestUtil.randomString());

			Assert.assertThrows(
				CommerceVirtualOrderItemException.class,
				() ->
					_commerceVirtualOrderItemService.
						propagateCPDVirtualSettingFileEntry(
							cpdVirtualSettingFileEntry.
								getCPDefinitionVirtualSettingFileEntryId()));

			_assertCommerceVirtualOrderItemFileEntriesCount(
				0, commerceOrderItem, cpdVirtualSettingFileEntry);

			_cpDefinitionLocalService.updateStatus(
				TestPropsValues.getUserId(), cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				Collections.emptyMap());

			_commerceVirtualOrderItemService.
				propagateCPDVirtualSettingFileEntry(
					cpdVirtualSettingFileEntry.
						getCPDefinitionVirtualSettingFileEntryId());

			_assertCommerceVirtualOrderItemFileEntriesCount(
				1, commerceOrderItem, cpdVirtualSettingFileEntry);
		}
	}

	@Test
	public void testPropagateCPDVirtualSettingFileEntryWithPermissions()
		throws Exception {

		frutillaRule.scenario(
			"Propagate a virtual product file with different permissions"
		).given(
			"Two placed orders of a virtual product on different accounts"
		).when(
			"Users with different permissions propagate a new file"
		).then(
			"A user who cannot update the catalog should not propagate the file"
		).and(
			"A user who cannot manage orders should not update any order"
		).and(
			"A user who manages the orders of only one account should update " +
				"only the order of that account"
		);

		CommerceOrderItem commerceOrderItem1 = _addCommerceOrderItem();
		CommerceOrderItem commerceOrderItem2 = _addCommerceOrderItem();

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			_cpdVirtualSettingFileEntryLocalService.
				addCPDVirtualSettingFileEntry(
					TestPropsValues.getUserId(),
					_cpDefinitionVirtualSetting.getGroupId(),
					_cpDefinitionVirtualSetting.
						getCPDefinitionVirtualSettingId(),
					0,
					"http://www.example.com/" + RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

		User user = UserTestUtil.addUser();

		Assert.assertThrows(
			PrincipalException.class,
			() -> _propagateCPDVirtualSettingFileEntry(
				user, cpdVirtualSettingFileEntry));

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, CommerceCatalog.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_group.getCompanyId()), ActionKeys.UPDATE);

		_roleLocalService.addUserRole(user.getUserId(), role.getRoleId());

		_propagateCPDVirtualSettingFileEntry(user, cpdVirtualSettingFileEntry);

		_assertCommerceVirtualOrderItemFileEntriesCount(
			0, commerceOrderItem1, cpdVirtualSettingFileEntry);
		_assertCommerceVirtualOrderItemFileEntriesCount(
			0, commerceOrderItem2, cpdVirtualSettingFileEntry);

		CommerceOrder commerceOrder = commerceOrderItem1.getCommerceOrder();

		AccountEntry accountEntry = commerceOrder.getAccountEntry();

		RoleTestUtil.addResourcePermission(
			role, CommerceOrderConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(accountEntry.getAccountEntryGroupId()),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS);

		_propagateCPDVirtualSettingFileEntry(user, cpdVirtualSettingFileEntry);

		_assertCommerceVirtualOrderItemFileEntriesCount(
			1, commerceOrderItem1, cpdVirtualSettingFileEntry);
		_assertCommerceVirtualOrderItemFileEntriesCount(
			0, commerceOrderItem2, cpdVirtualSettingFileEntry);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private CommerceOrderItem _addCommerceOrderItem() throws Exception {
		User user = UserTestUtil.addUser();

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			user.getUserId(), _commerceChannel.getGroupId(), _commerceCurrency);

		CommerceOrderItem commerceOrderItem =
			CommerceTestUtil.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				_cpInstance.getCPInstanceId(), BigDecimal.ONE);

		commerceOrder = _commerceOrderLocalService.getCommerceOrder(
			commerceOrder.getCommerceOrderId());

		commerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_PENDING);
		commerceOrder.setPaymentStatus(
			CommerceOrderPaymentConstants.STATUS_COMPLETED);

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		_commerceVirtualOrderItemChecker.checkCommerceVirtualOrderItems(
			commerceOrder.getCommerceOrderId());

		return commerceOrderItem;
	}

	private void _assertCommerceVirtualOrderItemFileEntriesCount(
		int expectedCount, CommerceOrderItem commerceOrderItem,
		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			_commerceVirtualOrderItemLocalService.
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					commerceOrderItem.getCommerceOrderItemId());

		Assert.assertEquals(
			expectedCount,
			ListUtil.count(
				commerceVirtualOrderItem.
					getCommerceVirtualOrderItemFileEntries(),
				commerceVirtualOrderItemFileEntry ->
					(commerceVirtualOrderItemFileEntry.getFileEntryId() ==
						cpdVirtualSettingFileEntry.getFileEntryId()) &&
					Objects.equals(
						commerceVirtualOrderItemFileEntry.getUrl(),
						cpdVirtualSettingFileEntry.getUrl()) &&
					Objects.equals(
						commerceVirtualOrderItemFileEntry.getVersion(),
						cpdVirtualSettingFileEntry.getVersion())));
	}

	private void _propagateCPDVirtualSettingFileEntry(
			User user, CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry)
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			_commerceVirtualOrderItemService.
				propagateCPDVirtualSettingFileEntry(
					cpdVirtualSettingFileEntry.
						getCPDefinitionVirtualSettingFileEntryId());
		}
	}

	private CommerceCatalog _commerceCatalog;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private CommerceVirtualOrderItemChecker _commerceVirtualOrderItemChecker;

	@Inject
	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

	@Inject
	private CommerceVirtualOrderItemService _commerceVirtualOrderItemService;

	private CPDefinition _cpDefinition;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	private CPDefinitionVirtualSetting _cpDefinitionVirtualSetting;

	@Inject
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	private CPInstance _cpInstance;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@Inject
	private CPDVirtualSettingFileEntryLocalService
		_cpdVirtualSettingFileEntryLocalService;

	private Group _group;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

}