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

package com.liferay.commerce.order.importer.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItem;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterType;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterTypeRegistry;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.product.constants.CPInstanceConstants;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.math.BigDecimal;

import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceOrderImporterTypeTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		PrincipalThreadLocal.setName(_user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_group = GroupTestUtil.addGroup(
			_user.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_user.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_user.getCompanyId(), _group.getGroupId(), _user.getUserId());

		_commerceChannel = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, _group.getGroupId(), "Test Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_commerceAccount = CommerceAccountTestUtil.addBusinessCommerceAccount(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _commerceChannel.getUserId(),
			_commerceAccount.getCommerceAccountId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceContext = new TestCommerceContext(
			_commerceCurrency, _commerceChannel, _user, _group,
			_commerceOrder.getCommerceAccount(), _commerceOrder);
	}

	@After
	public void tearDown() throws PortalException {
		_commerceOrderLocalService.deleteCommerceOrder(_commerceOrder);
	}

	@Test
	public void testFailedCSVImport() throws Exception {
		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CPTestUtil.addCPInstanceFromCatalogWithERC(
			commerceCatalog.getGroupId(), "erc-test", new BigDecimal(100),
			CPInstanceConstants.DEFAULT_SKU);

		CommerceOrderImporterType commerceOrderImporterType =
			_commerceOrderImporterTypeRegistry.getCommerceOrderImporterType(
				"csv");

		String fileName = "test_failed_csv_import.csv";

		String fileContent = StringBundler.concat(
			"sku,quantity", StringPool.NEW_LINE, "erc-test-fail,1",
			StringPool.NEW_LINE, "erc-test,0");

		File file = FileUtil.createTempFile(fileContent.getBytes());

		List<CommerceOrderImporterItem> commerceOrderImporterItems =
			commerceOrderImporterType.getCommerceOrderImporterItems(
				_commerceOrderLocalService.addCommerceOrder(
					_user.getUserId(), _commerceChannel.getGroupId(),
					_commerceAccount.getCommerceAccountId(),
					_commerceCurrency.getCommerceCurrencyId(), 0),
				DLAppLocalServiceUtil.addFileEntry(
					null, _serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
					MimeTypesUtil.getContentType(file, fileName), fileName,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, file,
					null, null, _serviceContext));

		Assert.assertEquals(
			commerceOrderImporterItems.toString(), 2,
			commerceOrderImporterItems.size());

		Stream<CommerceOrderImporterItem> stream =
			commerceOrderImporterItems.stream();

		stream.map(
			CommerceOrderImporterItem::getErrorMessages
		).forEach(
			errorMessages -> Assert.assertNotNull(errorMessages)
		);
	}

	@Test
	public void testSuccessfulCSVImport() throws Exception {
		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				_commerceCurrency.getCode(), LocaleUtil.US.getDisplayLanguage(),
				_serviceContext);

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalogWithERC(
			commerceCatalog.getGroupId(), "erc-test", new BigDecimal(100),
			CPInstanceConstants.DEFAULT_SKU);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);

		CommerceTestUtil.addWarehouseCommerceChannelRel(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			_commerceChannel.getCommerceChannelId());

		CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
			_user.getUserId(), commerceInventoryWarehouse, cpInstance.getSku(),
			100);

		CPTestUtil.addBaseCommerceCatalogCommercePriceList(
			commerceCatalog.getGroupId(), _commerceCurrency.getCode(),
			CommercePriceListConstants.TYPE_PRICE_LIST, _serviceContext);

		CPTestUtil.addBaseCommerceCatalogCommercePriceList(
			commerceCatalog.getGroupId(), _commerceCurrency.getCode(),
			CommercePriceListConstants.TYPE_PROMOTION, _serviceContext);

		CommerceOrderImporterType commerceOrderImporterType =
			_commerceOrderImporterTypeRegistry.getCommerceOrderImporterType(
				"csv");

		String fileName = "test_successful_csv_import.csv";

		String fileContent = StringBundler.concat(
			"sku,quantity", StringPool.NEW_LINE, "erc-test,1");

		File file = FileUtil.createTempFile(fileContent.getBytes());

		List<CommerceOrderImporterItem> commerceOrderImporterItems =
			commerceOrderImporterType.getCommerceOrderImporterItems(
				_commerceOrderLocalService.addCommerceOrder(
					_user.getUserId(), _commerceChannel.getGroupId(),
					_commerceAccount.getCommerceAccountId(),
					_commerceCurrency.getCommerceCurrencyId(), 0),
				DLAppLocalServiceUtil.addFileEntry(
					null, _serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
					MimeTypesUtil.getContentType(file, fileName), fileName,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, file,
					null, null, _serviceContext));

		Assert.assertEquals(
			commerceOrderImporterItems.toString(), 1,
			commerceOrderImporterItems.size());

		CommerceOrderImporterItem commerceOrderImporterItem =
			commerceOrderImporterItems.get(0);

		Assert.assertNull(commerceOrderImporterItem.getErrorMessages());
	}

	@Inject
	private static CommerceOrderImporterTypeRegistry
		_commerceOrderImporterTypeRegistry;

	private static User _user;

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	private CommerceContext _commerceContext;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}