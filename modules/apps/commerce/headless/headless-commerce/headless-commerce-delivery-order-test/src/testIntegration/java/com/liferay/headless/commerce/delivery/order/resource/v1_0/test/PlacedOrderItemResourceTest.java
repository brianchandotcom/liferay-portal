/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.commerce.product.type.virtual.order.util.CommerceVirtualOrderItemChecker;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.PlacedOrderItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class PlacedOrderItemResourceTest
	extends BasePlacedOrderItemResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(), "business", 1, _serviceContext);

		_commerceCurrency = _commerceCurrencyLocalService.addCommerceCurrency(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), BigDecimal.ONE, new HashMap<>(), 2,
			2, "HALF_EVEN", false, RandomTestUtil.nextDouble(), true);

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId(), _commerceCurrency.getCode());

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			testGroup.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_COMPLETED);

		_commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			_commerceOrder);

		_commercePriceList =
			_commercePriceListLocalService.addCommercePriceList(
				RandomTestUtil.randomString(), testGroup.getGroupId(),
				_user.getUserId(), _commerceCurrency.getCommerceCurrencyId(),
				true, CommercePriceListConstants.TYPE_PRICE_LIST, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.nextDouble(), 1,
				1, 2022, 12, 0, 0, 0, 0, 0, 0, true, _serviceContext);
	}

	@Override
	@Test
	public void testGetPlacedOrderItem() throws Exception {
		super.testGetPlacedOrderItem();

		_testGetPlacedOrderItemWithFileEntry();
		_testGetPlacedOrderItemWithURL();
	}

	@Ignore
	@Override
	@Test
	public void testGetPlacedOrderPlacedOrderItemsPageWithPagination()
		throws Exception {

		super.testGetPlacedOrderPlacedOrderItemsPageWithPagination();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"productId", "quantity", "sku", "skuId", "subscription",
			"virtualItemURLs"
		};
	}

	@Override
	protected PlacedOrderItem randomPlacedOrderItem() throws Exception {
		return _toPlacedOrderItem(
			_addCPDefinition(
				0, "https://liferay.com/" + RandomTestUtil.randomString()));
	}

	@Override
	protected PlacedOrderItem testGetPlacedOrderItem_addPlacedOrderItem()
		throws Exception {

		return _addPlacedOrderItem(randomPlacedOrderItem());
	}

	@Override
	protected PlacedOrderItem
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				Long placedOrderId, PlacedOrderItem placedOrderItem)
		throws Exception {

		return _addPlacedOrderItem(placedOrderItem);
	}

	@Override
	protected Long testGetPlacedOrderPlacedOrderItemsPage_getPlacedOrderId()
		throws Exception {

		return _commerceOrder.getCommerceOrderId();
	}

	@Override
	protected PlacedOrderItem testGraphQLPlacedOrderItem_addPlacedOrderItem()
		throws Exception {

		return _addPlacedOrderItem(randomPlacedOrderItem());
	}

	private CPDefinition _addCPDefinition(long fileEntryId, String url)
		throws Exception {

		CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME, true,
			true);

		_commerceCPDefinitions.add(cpDefinition);

		_cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingLocalService.
				addCPDefinitionVirtualSetting(
					cpDefinition.getModelClassName(),
					cpDefinition.getCPDefinitionId(), fileEntryId, url,
					CommerceOrderConstants.ORDER_STATUS_PENDING, 0,
					RandomTestUtil.randomInt(), true, 0, "https://liferay.com",
					false, null, 0, _serviceContext);

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(cpDefinition);

		return cpDefinition;
	}

	private PlacedOrderItem _addPlacedOrderItem(PlacedOrderItem placedOrderItem)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_user.getUserId(), _commerceOrder.getCommerceOrderId(),
				placedOrderItem.getSkuId(), null, placedOrderItem.getQuantity(),
				0, placedOrderItem.getQuantity(), StringPool.BLANK,
				new TestCommerceContext(
					_accountEntry, _commerceCurrency, _commerceChannel, _user,
					testGroup, _commerceOrder),
				_serviceContext);

		_commerceOrderItems.add(commerceOrderItem);

		_commerceVirtualOrderItemChecker.checkCommerceVirtualOrderItems(
			_commerceOrder.getCommerceOrderId());

		return new PlacedOrderItem() {
			{
				id = commerceOrderItem.getCommerceOrderItemId();
				name = commerceOrderItem.getName();
				productId = commerceOrderItem.getCProductId();
				quantity = commerceOrderItem.getQuantity();
				sku = commerceOrderItem.getSku();
				skuId = commerceOrderItem.getCPInstanceId();
				subscription = commerceOrderItem.isSubscription();
				valid = true;

				setVirtualItemURLs(
					() -> {
						CommerceVirtualOrderItem commerceVirtualOrderItem =
							_commerceVirtualOrderItemLocalService.
								fetchCommerceVirtualOrderItemByCommerceOrderItemId(
									commerceOrderItem.getCommerceOrderItemId());

						if (commerceVirtualOrderItem == null) {
							return null;
						}

						return new String[] {commerceVirtualOrderItem.getUrl()};
					});
			}
		};
	}

	private void _testGetPlacedOrderItemWithFileEntry() throws Exception {
		_fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(
				PlacedOrderItemResourceTest.class, "dependencies/image.jpg"),
			null, null, _serviceContext);

		PlacedOrderItem postPlacedOrderItem = _addPlacedOrderItem(
			_toPlacedOrderItem(
				_addCPDefinition(_fileEntry.getFileEntryId(), null)));

		PlacedOrderItem getPlacedOrderItem =
			placedOrderItemResource.getPlacedOrderItem(
				postPlacedOrderItem.getId());

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			_commerceVirtualOrderItemLocalService.
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					getPlacedOrderItem.getId());

		String[] virtualItemURLs = {
			StringBundler.concat(
				_portal.getPathModule(), StringPool.SLASH,
				CommerceMediaConstants.SERVLET_PATH,
				CommerceMediaConstants.URL_SEPARATOR_VIRTUAL_ORDER_ITEM,
				commerceVirtualOrderItem.getCommerceVirtualOrderItemId(),
				CommerceMediaConstants.URL_SEPARATOR_FILE,
				_fileEntry.getFileEntryId())
		};

		Assert.assertEquals(
			virtualItemURLs, getPlacedOrderItem.getVirtualItemURLs());
	}

	private void _testGetPlacedOrderItemWithURL() throws Exception {
		String url = "https://liferay.com/myfiles/download";

		PlacedOrderItem postPlacedOrderItem = _addPlacedOrderItem(
			_toPlacedOrderItem(_addCPDefinition(0, url)));

		PlacedOrderItem getPlacedOrderItem =
			placedOrderItemResource.getPlacedOrderItem(
				postPlacedOrderItem.getId());

		String[] virtualItemURLs = {url};

		Assert.assertEquals(
			virtualItemURLs, getPlacedOrderItem.getVirtualItemURLs());
	}

	private PlacedOrderItem _toPlacedOrderItem(CPDefinition cpDefinition) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		CPInstance cpInstance = cpInstances.get(0);

		return new PlacedOrderItem() {
			{
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				productId = cpDefinition.getCProductId();
				quantity = BigDecimal.valueOf(RandomTestUtil.randomInt(1, 100));
				sku = cpInstance.getSku();
				skuId = cpInstance.getCPInstanceId();
				subscription = false;
				valid = true;
			}
		};
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private final List<CPDefinition> _commerceCPDefinitions = new ArrayList<>();

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@DeleteAfterTestRun
	private final List<CommerceOrderItem> _commerceOrderItems =
		new ArrayList<>();

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@DeleteAfterTestRun
	private CommercePriceList _commercePriceList;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject
	private CommerceVirtualOrderItemChecker _commerceVirtualOrderItemChecker;

	@Inject
	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

	@DeleteAfterTestRun
	private CPDefinitionVirtualSetting _cpDefinitionVirtualSetting;

	@Inject
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private FileEntry _fileEntry;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}